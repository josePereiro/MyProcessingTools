package WrapperPainter;


import PGUIObject.GuidedBoard;
import PGUIObject.PGuiObject;
import processing.core.PApplet;

import java.util.ArrayList;

public class DrawComponentManager extends PGuiObject {

    private static final int HANDLING_GROUPS = 902;
    private static final int HANDLING_SINGLE_COMPONENT = 390;

    private int handlingMode;
    private DrawComponent focusedComponent;
    private boolean focusedComponentChanged = false;
    private ArrayList<String> componentsNames;
    private ArrayList<DrawComponent> drawComponents;
    private OnFocusChangedHandler onFocusChangedHandler;

    public DrawComponentManager(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);
        handlingMode = HANDLING_SINGLE_COMPONENT;
        drawComponents = new ArrayList<>();
        componentsNames = new ArrayList<>();

    }

    @Override
    public void drawFocus() {
    }

    @Override
    public void draw() {
        for (DrawComponent drawComponent : drawComponents) {
            drawComponent.draw();
        }
    }

    public String suggestNewName(int type) {

        if (type == DrawComponent.Types.LINE) {
            return formatNewName("line");
        }

        return "";
    }

    public String formatNewName(String name) {
        int i = 0;
        while (componentsNames.contains(name)) {
            i++;
            name = "line" + i;
        }
        return name;
    }

    public void focusNext() {
        if (focusedComponent != null) {
            boolean afterFocused = false;
            for (int oi = 0; oi < drawComponents.size(); oi++) {
                if (drawComponents.get(oi).equals(focusedComponent)) {
                    afterFocused = true;
                    continue;
                }
                if (afterFocused && drawComponents.get(oi).isFocusable()) {
                    setFocusTo(drawComponents.get(oi));
                    return;
                }
            }
        }
        for (DrawComponent pGuiObject : drawComponents) {
            if (pGuiObject.isFocusable()) {
                setFocusTo(pGuiObject);
                return;
            }
        }

    }

    public boolean setFocusTo(DrawComponent drawComponent) {
        if (focusedComponent != null) {
            focusedComponentChanged = !focusedComponent.equals(drawComponent);
            if (focusedComponentChanged) {
                focusedComponent = drawComponent;
            }
        } else {
            focusedComponentChanged = drawComponent != null;
            focusedComponent = drawComponent;
        }

        for (DrawComponent dComponent : drawComponents) {
            dComponent.setFocus(focusedComponent != null && focusedComponent.equals(dComponent));
        }

        if (focusedComponentChanged) {
            if (onFocusChangedHandler != null) {
                onFocusChangedHandler.handlePEvent(drawComponent);
            }
        }
        return focusedComponentChanged;
    }

    public void addDrawComponent(DrawComponent drawComponent) {
        drawComponents.add(drawComponent);
    }

    public void addComponentName(String name) {
        componentsNames.add(name);
    }

    public ArrayList<DrawComponent> getDrawComponents() {
        return drawComponents;
    }

    public void guideComponents(GuidedBoard guidedBoard) {
        for (DrawComponent drawComponent : drawComponents) {
            drawComponent.guideComponent(guidedBoard);
        }
    }

    public DrawComponent getFocusedComponent() {
        return focusedComponent;
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

    public abstract static class OnFocusChangedHandler {

        public abstract void handlePEvent(DrawComponent drawComponent);
    }
}
