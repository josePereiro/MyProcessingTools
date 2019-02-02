package WrapperPainter;


import Common.PObject;
import PGUIObject.GuidedBoard;
import processing.core.PApplet;

import java.util.ArrayList;

class WrapperPainterObjectsManager extends PObject {

    private ArrayList<WrapperPainterObject> focusedObjects;
    private boolean focusedComponentChanged = false;
    private ArrayList<String> objectsNames;
    private String[] historyBuffer;
    private int lastHash = -1;
    private int historyBufferMaxSize = 100;
    private int historyIndex = 0;
    private final String HISTORY_EMPTY = "$";
    private ArrayList<WrapperPainterObject> wrapperPainterObjects;
    private OnFocusChangedHandler onFocusChangedHandler;

    WrapperPainterObjectsManager(PApplet context) {
        super(context);
        wrapperPainterObjects = new ArrayList<>();
        objectsNames = new ArrayList<>();
        focusedObjects = new ArrayList<>();
        historyBuffer = new String[historyBufferMaxSize];
        for (int i = 0; i < historyBuffer.length; i++) {
            historyBuffer[i] = HISTORY_EMPTY;
        }
        saveInHistory();
    }

    void drawObjects() {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            wrapperPainterObject.draw();
        }
    }

    String suggestNewName(int type) {

        if (type == WrapperPainterObject.Types.LINE) {
            return formatNewName("line");
        } else if (type == WrapperPainterObject.Types.RECTANGLE) {
            return formatNewName("rect");
        }

        return "";
    }

    String formatNewName(String name) {
        int i = 0;
        String virginName = name;
        while (objectsNames.contains(name)) {
            i++;
            name = virginName + i;
        }
        return name;
    }

    void focusNext() {
        if (focusedObjects != null && focusedObjects.size() != 0) {
            boolean afterFocused = false;
            for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
                if (wrapperPainterObject.equals(focusedObjects.get(0))) {
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

    void setFocusTo(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject == null) {
            focusedComponentChanged = focusedObjects.size() > 0;
            focusedObjects = new ArrayList<>();
        } else {
            if (focusedObjects.size() == 1 && focusedObjects.get(0).equals(wrapperPainterObject)) {
                focusedComponentChanged = false;
            } else {
                focusedObjects = new ArrayList<>();
                focusedObjects.add(wrapperPainterObject);
                focusedComponentChanged = true;
            }
        }


        for (WrapperPainterObject dComponent : wrapperPainterObjects) {
            dComponent.setFocus(focusedObjects.contains(dComponent));
        }

        if (focusedComponentChanged) {
            if (onFocusChangedHandler != null) {
                onFocusChangedHandler.handlePEvent(wrapperPainterObject);
            }
        }
    }

    void addFocusTo(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject == null) {
            return;
        }
        if (focusedObjects.contains(wrapperPainterObject)) {
            return;
        }
        focusedObjects.add(wrapperPainterObject);
        wrapperPainterObject.setFocus(true);
        focusedComponentChanged = true;
        if (onFocusChangedHandler != null) {
            onFocusChangedHandler.handlePEvent(wrapperPainterObject);
        }
    }

    void removeObject(WrapperPainterObject wrapperPainterObject) {
        focusedObjects.remove(wrapperPainterObject);
        objectsNames.remove(wrapperPainterObject.getName());
        wrapperPainterObjects.remove(wrapperPainterObject);
    }

    void addDrawComponent(WrapperPainterObject wrapperPainterObject) {
        wrapperPainterObjects.add(wrapperPainterObject);
    }

    void addComponentName(String name) {
        objectsNames.add(name);
    }

    ArrayList<WrapperPainterObject> getWrapperPainterObjects() {
        return wrapperPainterObjects;
    }

    void guideComponents(GuidedBoard guidedBoard) {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            wrapperPainterObject.guideComponent(guidedBoard);
        }
    }

    ArrayList<WrapperPainterObject> getFocusedObjects() {
        return focusedObjects;
    }

    private void moveToBack(WrapperPainterObject wrapperPainterObject) {
        int index = wrapperPainterObjects.indexOf(wrapperPainterObject);
        if (index > 0) {
            WrapperPainterObject temp = wrapperPainterObjects.get(index);
            wrapperPainterObjects.set(index, wrapperPainterObjects.get(index - 1));
            wrapperPainterObjects.set(index - 1, temp);
        }
    }

    void moveToBack(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            moveToBack(wrapperPainterObject);
        }
    }

    private void moveToFront(WrapperPainterObject wrapperPainterObject) {
        int index = wrapperPainterObjects.indexOf(wrapperPainterObject);
        if (index + 1 < wrapperPainterObjects.size()) {
            WrapperPainterObject temp = wrapperPainterObjects.get(index);
            wrapperPainterObjects.set(index, wrapperPainterObjects.get(index + 1));
            wrapperPainterObjects.set(index + 1, temp);
        }
    }

    void moveToFront(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            moveToFront(wrapperPainterObject);
        }
    }

    void saveInHistory() {
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

    void toThePass() {
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

    void toTheFuture() {
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

    void updateNames() {
        objectsNames = new ArrayList<>();
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            objectsNames.add(wrapperPainterObject.getName());
        }
    }

    void setWrapperPainterObjects(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        this.wrapperPainterObjects = wrapperPainterObjects;
    }

    abstract static class OnFocusChangedHandler {

        abstract void handlePEvent(WrapperPainterObject... wrapperPainterObject);
    }

}
