package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import PGUIObject.GuidedBoard;

import java.util.ArrayList;
import java.util.Random;

class WPObjectsManager {

    private ArrayList<WPObject> focusedObjects;
    private boolean focusedComponentChanged = false;
    private ArrayList<String> objectsNames;
    private String[] historyBuffer;
    private int lastHash = -1;
    private int historyBufferMaxSize = 100;
    private int historyIndex = 0;
    private final String HISTORY_EMPTY = "$";
    private ArrayList<WPObject> WPObjects;
    private OnFocusChangedHandler onFocusChangedHandler;
    protected static final Random r = new Random();
    private WrapperPainter context;


    WPObjectsManager(WrapperPainter context) {
        this.context = context;
        WPObjects = new ArrayList<>();
        objectsNames = new ArrayList<>();
        focusedObjects = new ArrayList<>();
        historyBuffer = new String[historyBufferMaxSize];
        for (int i = 0; i < historyBuffer.length; i++) {
            historyBuffer[i] = HISTORY_EMPTY;
        }
        //saveInHistory();
    }

    void drawObjects() {
        for (int i = WPObjects.size() - 1; i >= 0; i--) {
            WPObject WPObject = WPObjects.get(i);
            WPObject.draw();
        }
        drawFocus();
    }

    String suggestNewName(int type) {

        if (type == WPObject.Types.LINE) {
            return "line";
        } else if (type == WPObject.Types.RECTANGLE) {
            return "rect";
        } else if (type == WPObject.Types.ELLIPSE) {
            return "ellipse";
        } else if (type == WPObject.Types.IMAGE) {
            return "image";
        } else if (type == WPObject.Types.TEXT) {
            return "text";
        }

        return "";
    }

    String formatNewName(String name) {
        updateNames();
        int i = 0;
        String virginName = name;
        do {
            name = virginName + i;
            i++;
        } while (objectsNames.contains(name));
        return name;
    }

    void focusNext() {
        if (focusedObjects != null && focusedObjects.size() != 0) {
            boolean afterFocused = false;
            for (WPObject WPObject : WPObjects) {
                if (WPObject.equals(focusedObjects.get(0))) {
                    afterFocused = true;
                    continue;
                }
                if (afterFocused && WPObject.isFocusable()) {
                    setFocusTo(WPObject);
                    return;
                }
            }
        }
        for (WPObject pGuiObject : WPObjects) {
            if (pGuiObject.isFocusable()) {
                setFocusTo(pGuiObject);
                return;
            }
        }

    }

    void setFocusTo(WPObject WPObject) {
        if (WPObject == null) {
            focusedComponentChanged = focusedObjects.size() > 0;
            focusedObjects = new ArrayList<>();
        } else {
            if (focusedObjects.size() == 1 && focusedObjects.get(0).equals(WPObject)) {
                focusedComponentChanged = false;
            } else {
                focusedObjects = new ArrayList<>();
                focusedObjects.add(WPObject);
                focusedComponentChanged = true;
            }
        }


        for (WPObject dComponent : WPObjects) {
            dComponent.setFocus(focusedObjects.contains(dComponent));
        }

        if (focusedComponentChanged) {
            if (onFocusChangedHandler != null) {
                onFocusChangedHandler.handlePEvent(WPObject);
            }
        }
    }

    void addFocusTo(WPObject WPObject) {
        if (WPObject == null) {
            return;
        }
        if (focusedObjects.contains(WPObject)) {
            return;
        }
        focusedObjects.add(WPObject);
        WPObject.setFocus(true);
        focusedComponentChanged = true;
        if (onFocusChangedHandler != null) {
            onFocusChangedHandler.handlePEvent(WPObject);
        }
    }

    private void remove(WPObject WPObject) {
        objectsNames.remove(WPObject.getName());
        focusedObjects.remove(WPObject);
        WPObjects.remove(WPObject);
    }

    void remove(ArrayList<WPObject> toDelete) {
        while (toDelete.size() > 0) {
            WPObject temp = toDelete.get(0);
            remove(temp);
            toDelete.remove(temp);
        }
    }

    void addDrawComponent(WPObject WPObject) {
        WPObjects.add(WPObject);
        updateNames();
    }

    ArrayList<WPObject> getWPObjects() {
        return WPObjects;
    }

    void guideFocusedObjects(GuidedBoard guidedBoard) {
        for (WPObject focusedObject : focusedObjects) {
            focusedObject.guide(guidedBoard);
        }
    }

    ArrayList<WPObject> getFocusedObjects() {
        return focusedObjects;
    }

    private void moveToBack(WPObject WPObject) {
        int index = WPObjects.indexOf(WPObject);
        if (index == -1) {
            return;
        }
        if (index > 0) {
            WPObject temp = WPObjects.get(index);
            WPObjects.set(index, WPObjects.get(index - 1));
            WPObjects.set(index - 1, temp);
        }
    }

    void moveToBack(ArrayList<WPObject> WPObjects) {
        for (WPObject WPObject : WPObjects) {
            moveToBack(WPObject);
        }
    }

    private void moveToFront(WPObject WPObject) {
        int index = WPObjects.indexOf(WPObject);
        if (index == -1) {
            return;
        }
        if (index + 1 < WPObjects.size()) {
            WPObject temp = WPObjects.get(index);
            WPObjects.set(index, WPObjects.get(index + 1));
            WPObjects.set(index + 1, temp);
        }
    }

    void moveObjects(ArrayList<WPObject> objects, float dx, float dy) {
        for (WPObject WPObject : objects) {
            WPObject.move(dx, dy);
        }
    }

    void moveObjectsToSavingRelation(ArrayList<WPObject> objects, float x, float y) {
        if (objects.size() == 0) {
            return;
        }

        float groupX = 0;
        float groupY = 0;
        for (WPObject object : objects) {
            groupX += object.getX();
            groupY += object.getY();
        }
        groupX /= objects.size();
        groupY /= objects.size();
        float dx = x - groupX;
        float dy = y - groupY;
        for (WPObject object : objects) {
            object.move(dx, dy);
        }

    }

    void moveToFront(ArrayList<WPObject> WPObjects) {
        for (WPObject WPObject : WPObjects) {
            moveToFront(WPObject);
        }
    }

    void saveInHistory() {

        String[] data = WPStoreData.getStoreData();
        StringBuilder dataStore = new StringBuilder();
        for (int li = 0; li < data.length; li++) {
            if (li != 0) {
                dataStore.append(WPStoreData.LINE_SEPARATOR);
            }
            dataStore.append(data[li]);
        }
        int currentHash = dataStore.toString().hashCode();
        if (currentHash != lastHash) {
            lastHash = currentHash;
            if (historyIndex < historyBufferMaxSize - 1) {
                historyIndex++;
            } else {
                historyIndex = 0;
            }
            historyBuffer[historyIndex] = dataStore.toString();
        }
    }

    void toThePass() {
        if (historyIndex > 0) {
            if (!historyBuffer[historyIndex - 1].equals(HISTORY_EMPTY)) {
                historyIndex--;
                WPStoreData.loadStoreData(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        } else {
            if (!historyBuffer[historyBufferMaxSize - 1].equals(HISTORY_EMPTY)) {
                historyIndex = historyBufferMaxSize - 1;
                WPStoreData.loadStoreData(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        }
        WrapperPainter.WPObjectsManager.setFocusTo(null);
    }

    void toTheFuture() {
        if (historyIndex < historyBufferMaxSize - 2) {
            if (!historyBuffer[historyIndex + 1].equals(HISTORY_EMPTY)) {
                historyIndex++;
                WPStoreData.loadStoreData(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        } else {
            if (!historyBuffer[0].equals(HISTORY_EMPTY)) {
                historyIndex = 0;
                WPStoreData.loadStoreData(historyBuffer[historyIndex], context);
                lastHash = historyBuffer[historyIndex].hashCode();
            }
        }
        WrapperPainter.WPObjectsManager.setFocusTo(null);
    }

    private void updateNames() {
        objectsNames = new ArrayList<>();
        for (WPObject WPObject : WPObjects) {
            objectsNames.add(WPObject.getName());
        }
    }

    void duplicateFocused() {
        ArrayList<WPObject> toDuplicate = getFocusedObjects();
        setFocusTo(null);
        WPObject duplicate;
        for (WPObject WPObject : toDuplicate) {

            //TODO implement duplication in this same class!!!
            String wrapperData = WPStoreData.getWrapperStoreData(WPObject);
            duplicate = WPStoreData.getWPObjectFromStoreDataLine(wrapperData, context);
            duplicate.move(15, 15);
            duplicate.setName(formatNewName(WPObject.getName() + "_copy"));
            addDrawComponent(duplicate);
            addFocusTo(duplicate);
        }
    }

    ArrayList<WPObject> getInnerObjects(ArrayList<WPObject> outterObjects) {
        ArrayList<WPObject> innerObjects = new ArrayList<>();
        for (WPObject outerObject : outterObjects) {

            for (WPObject object : WPObjects) {

                if (object.equals(outerObject)) {
                    continue;
                }

                for (EllipseWrapper constructionPoint : object.getConstructionPoints()) {
                    if (innerObjects.contains(object)) {
                        break;
                    }
                    if (outerObject.isThisOverMe(constructionPoint.getX(), constructionPoint.getY())) {
                        innerObjects.add(object);
                        break;
                    }
                }

            }

        }
        return innerObjects;
    }

    void setWPObjects(ArrayList<WPObject> WPObjects) {
        this.WPObjects = WPObjects;
    }

    abstract static class OnFocusChangedHandler {

        abstract void handlePEvent(WPObject... WPObject);
    }

    public void drawFocus() {
        for (WPObject focusedObject : focusedObjects) {

            for (int i = 0; i < focusedObject.constructionPoints.length; i++) {
                EllipseWrapper constructionPoint = focusedObject.constructionPoints[i];
                constructionPoint.setFillEnable(false);
                constructionPoint.setStrokeAlpha(100);
                if (i == 0 || focusedObject.focusedConstructionPoints == constructionPoint) {
                    constructionPoint.setStrokeAlpha(100);
                    constructionPoint.setStrokeWeight(3);
                    constructionPoint.draw();
                } else {
                    constructionPoint.setStrokeWeight(1);
                    constructionPoint.draw();
                }
                focusedObject.getWrapper().getContext().strokeWeight(3);
                focusedObject.getWrapper().getContext().stroke(r.nextInt(255));
                focusedObject.getWrapper().getContext().point(constructionPoint.getX(), constructionPoint.getY());
            }
        }

    }

}
