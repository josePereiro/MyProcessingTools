package WrapperPainter;


import PGUIObject.GuidedBoard;
import PGUIObject.PGuiObject;
import processing.core.PApplet;

import java.util.ArrayList;

public class WrapperPainterObjectsManager extends PGuiObject {

    private static final int HANDLING_GROUPS = 902;
    private static final int HANDLING_SINGLE_COMPONENT = 390;

    private int handlingMode;
    private ArrayList<WrapperPainterObject> focusedComponents;
    private boolean focusedComponentChanged = false;
    private ArrayList<String> componentsNames;
    private String[] historyBuffer;
    private int lastHash = -1;
    private int historyBufferMaxSize = 100;
    private int historyIndex = 0;
    private final String HISTORY_EMPTY = "$";
    private ArrayList<WrapperPainterObject> wrapperPainterObjects;
    private OnFocusChangedHandler onFocusChangedHandler;

    public WrapperPainterObjectsManager(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);
        handlingMode = HANDLING_SINGLE_COMPONENT;
        wrapperPainterObjects = new ArrayList<>();
        componentsNames = new ArrayList<>();
        focusedComponents = new ArrayList<>();
        historyBuffer = new String[historyBufferMaxSize];
        for (int i = 0; i < historyBuffer.length; i++) {
            historyBuffer[i] = HISTORY_EMPTY;
        }
        saveInHistory();
    }

    @Override
    public void drawFocus() {
    }

    @Override
    public void draw() {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            wrapperPainterObject.draw();
        }
    }

    public String suggestNewName(int type) {

        if (type == WrapperPainterObject.Types.LINE) {
            return formatNewName("line");
        }else if (type == WrapperPainterObject.Types.RECTANGLE) {
            return formatNewName("rect");
        }

        return "";
    }

    public String formatNewName(String name) {
        int i = 0;
        String virginName = name;
        while (componentsNames.contains(name)) {
            i++;
            name = virginName + i;
        }
        return name;
    }

    public void focusNext() {
        if (focusedComponents != null && focusedComponents.size() != 0) {
            boolean afterFocused = false;
            for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
                if (wrapperPainterObject.equals(focusedComponents.get(0))) {
                    afterFocused = true;
                    continue;
                }
                if (afterFocused && wrapperPainterObject.isFocusable()) {
                    setFocusTo(wrapperPainterObject);
                    return;
                }
            }
        }
        for (WrapperPainterObject pGuiObject : wrapperPainterObjects) {
            if (pGuiObject.isFocusable()) {
                setFocusTo(pGuiObject);
                return;
            }
        }

    }

    public void setFocusTo(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject == null) {
            focusedComponentChanged = focusedComponents.size() > 0;
            focusedComponents = new ArrayList<>();
        } else {
            if (focusedComponents.size() == 1 && focusedComponents.get(0).equals(wrapperPainterObject))
                focusedComponentChanged = false;
            else {
                focusedComponents = new ArrayList<>();
                focusedComponents.add(wrapperPainterObject);
                focusedComponentChanged = true;
            }
        }


        for (WrapperPainterObject dComponent : wrapperPainterObjects) {
            dComponent.setFocus(focusedComponents.contains(dComponent));
        }

        if (focusedComponentChanged) {
            if (onFocusChangedHandler != null) {
                onFocusChangedHandler.handlePEvent(wrapperPainterObject);
            }
        }
    }

    public void addFocusTo(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject == null) {
            return;
        }
        if (focusedComponents.contains(wrapperPainterObject)) {
            return;
        }
        focusedComponents.add(wrapperPainterObject);
        wrapperPainterObject.setFocus(true);
        focusedComponentChanged = true;
        if (onFocusChangedHandler != null) {
            onFocusChangedHandler.handlePEvent(wrapperPainterObject);
        }
    }

    public void addDrawComponent(WrapperPainterObject wrapperPainterObject) {
        wrapperPainterObjects.add(wrapperPainterObject);
    }

    public void addComponentName(String name) {
        componentsNames.add(name);
    }

    public ArrayList<WrapperPainterObject> getWrapperPainterObjects() {
        return wrapperPainterObjects;
    }

    public void guideComponents(GuidedBoard guidedBoard) {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            wrapperPainterObject.guideComponent(guidedBoard);
        }
    }

    public ArrayList<WrapperPainterObject> getFocusedComponents() {
        return focusedComponents;
    }

    public boolean isFocusedComponentChanged() {
        return focusedComponentChanged;
    }

    public ArrayList<String> getComponentsNames() {
        return componentsNames;
    }

    public OnFocusChangedHandler getOnFocusChangedHandler() {
        return onFocusChangedHandler;
    }

    public void setOnFocusChangedHandler(OnFocusChangedHandler onFocusChangedHandler) {
        this.onFocusChangedHandler = onFocusChangedHandler;
    }

    public void saveInHistory() {
        String dataStore = DataStoreFile.generateDataStore(wrapperPainterObjects);
        int currentHash = dataStore.hashCode();
        if (currentHash != lastHash) {
            lastHash = currentHash;
            if (historyIndex < historyBufferMaxSize - 1) {
                historyIndex++;
            } else {
                historyIndex = 0;
            }
            historyBuffer[historyIndex] = dataStore;
        }
    }

    public void toThePass() {
        if (historyIndex > 0) {
            if (!historyBuffer[historyIndex - 1].equals(HISTORY_EMPTY)) {
                historyIndex--;
                wrapperPainterObjects = DataStoreFile.readDataStore(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        } else {
            if (!historyBuffer[historyBufferMaxSize - 1].equals(HISTORY_EMPTY)) {
                historyIndex = historyBufferMaxSize - 1;
                wrapperPainterObjects = DataStoreFile.readDataStore(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        }
    }

    public void toTheFuture() {
        if (historyIndex < historyBufferMaxSize - 2) {
            if (!historyBuffer[historyIndex + 1].equals(HISTORY_EMPTY)) {
                historyIndex++;
                wrapperPainterObjects = DataStoreFile.readDataStore(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        } else {
            if (!historyBuffer[0].equals(HISTORY_EMPTY)) {
                historyIndex = 0;
                wrapperPainterObjects = DataStoreFile.readDataStore(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        }
    }

    public void updateNames() {
        componentsNames = new ArrayList<>();
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            componentsNames.add(wrapperPainterObject.getName());
        }
    }

    public void setWrapperPainterObjects(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        this.wrapperPainterObjects = wrapperPainterObjects;
    }

    public abstract static class OnFocusChangedHandler {

        public abstract void handlePEvent(WrapperPainterObject... wrapperPainterObject);
    }

}
