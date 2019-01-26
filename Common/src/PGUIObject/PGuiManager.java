package PGUIObject;

import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public final class PGuiManager extends RectangleWrapper implements PEventListener, PEventHandler {

    private boolean focusChanged;
    private PGuiObject focusedPGuiObject;

    private final ArrayList<PGuiObject> pGuiObjects = new ArrayList<>();

    private static PGuiManager thisManager;

    public static PGuiManager createPGuiManager(PApplet context) {
        if (thisManager == null) {
            thisManager = new PGuiManager(context);
        }
        return thisManager;
    }

    private PGuiManager(PApplet context) {
        super(0, 0, context.width, context.height, context);
    }

    public final void addPGUIObject(PGuiObject pGuiObject) {
        if (pGuiObject != null) {
            pGuiObjects.add(pGuiObject);
        }
    }

    private void handleFocus() {
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isFocusable() &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {

            }
        }
    }

    @Override
    public final boolean listeningForMouseReleased(MouseEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnMouseReleasedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                return pGuiObject.getOnMouseReleasedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnMouseReleasedHandler() != null) {
            return getOnMouseReleasedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForMouseWheel(MouseEvent event) {

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnMouseWheelHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                return pGuiObject.getOnMouseWheelHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnMouseWheelHandler() != null) {
            return getOnMouseWheelHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForMousePressed(MouseEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnMousePressedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                return pGuiObject.getOnMousePressedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnMousePressedHandler() != null) {
            return getOnMousePressedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForMouseClicked(MouseEvent event) {
        //PGuiObjects
        boolean eventResult = false;
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {

                //Focus
                pGuiObject.setFocus(false);
                if (pGuiObject.isFocusable()) {
                    if (focusedPGuiObject != null) {
                        focusChanged = !focusedPGuiObject.equals(pGuiObject);
                        if (!focusChanged) {
                            focusedPGuiObject = pGuiObject;
                        }
                    } else {
                        focusChanged = true;
                        focusedPGuiObject = pGuiObject;
                        pGuiObject.setFocus(true);
                    }
                }

                //EventHandler
                if (pGuiObject.getOnMouseClickedHandler() != null) {
                    eventResult = pGuiObject.getOnMouseClickedHandler().handlePEvent(event, pGuiObject);
                }
            }
        }

        //Manager
        if (getOnMouseClickedHandler() != null) {
            return getOnMouseClickedHandler().handlePEvent(event, null);
        }
        return eventResult;
    }

    @Override
    public final boolean listeningForMouseDragged(MouseEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnMouseDraggedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                return pGuiObject.getOnMouseDraggedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnMouseDraggedHandler() != null) {
            return getOnMouseDraggedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForMouseMoved(MouseEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnMouseMovedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                return pGuiObject.getOnMouseMovedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnMouseMovedHandler() != null) {
            return getOnMouseMovedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForKeyPressed(KeyEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyPressedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                return pGuiObject.getOnKeyPressedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnKeyPressedHandler() != null) {
            return getOnKeyPressedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForKeyReleased(KeyEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyReleasedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                return pGuiObject.getOnKeyReleasedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnKeyReleasedHandler() != null) {
            return getOnKeyReleasedHandler().handlePEvent(event, null);
        }
        return false;
    }

    @Override
    public final boolean listeningForKeyTyped(KeyEvent event) {
        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyTypedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                return pGuiObject.getOnKeyTypedHandler().handlePEvent(event, pGuiObject);
            }
        }

        //Manager
        if (getOnKeyTypedHandler() != null) {
            return getOnKeyTypedHandler().handlePEvent(event, null);
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMouseReleasedHandler onMouseReleasedHandler;

    @Override
    public final PGuiObject.OnMouseReleasedHandler getOnMouseReleasedHandler() {
        return onMouseReleasedHandler;
    }

    @Override
    public final void setOnMouseReleasedHandler(PGuiObject.OnMouseReleasedHandler onMouseReleasedHandler) {
        this.onMouseReleasedHandler = onMouseReleasedHandler;
    }

    public abstract static class OnMouseReleasedHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMouseWheelHandler onMouseWheelHandler;

    @Override
    public final PGuiObject.OnMouseWheelHandler getOnMouseWheelHandler() {
        return onMouseWheelHandler;
    }

    @Override
    public final void setOnMouseWheelHandler(PGuiObject.OnMouseWheelHandler onMouseWheelHandler) {
        this.onMouseWheelHandler = onMouseWheelHandler;
    }

    public abstract static class OnMouseWheelHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMousePressedHandler onMousePressedHandler;

    @Override
    public final PGuiObject.OnMousePressedHandler getOnMousePressedHandler() {
        return onMousePressedHandler;
    }

    @Override
    public final void setOnMousePressedHandler(PGuiObject.OnMousePressedHandler onMousePressedHandler) {
        this.onMousePressedHandler = onMousePressedHandler;
    }

    public abstract static class OnMousePressedHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMouseClickedHandler onMouseClickedHandler;

    @Override
    public final PGuiObject.OnMouseClickedHandler getOnMouseClickedHandler() {
        return onMouseClickedHandler;
    }

    @Override
    public final void setOnMouseClickedHandler(PGuiObject.OnMouseClickedHandler onMouseClickedHandler) {
        this.onMouseClickedHandler = onMouseClickedHandler;
    }

    public abstract static class OnMouseClickedHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMouseDraggedHandler onMouseDraggedHandler;

    @Override
    public final PGuiObject.OnMouseDraggedHandler getOnMouseDraggedHandler() {
        return onMouseDraggedHandler;
    }

    @Override
    public final void setOnMouseDraggedHandler(PGuiObject.OnMouseDraggedHandler onMouseDraggedHandler) {
        this.onMouseDraggedHandler = onMouseDraggedHandler;
    }

    public abstract static class OnMouseDraggedHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnMouseMovedHandler onMouseMovedHandler;

    @Override
    public final PGuiObject.OnMouseMovedHandler getOnMouseMovedHandler() {
        return onMouseMovedHandler;
    }

    @Override
    public final void setOnMouseMovedHandler(PGuiObject.OnMouseMovedHandler onMouseMovedHandler) {
        this.onMouseMovedHandler = onMouseMovedHandler;
    }

    public abstract static class OnMouseMovedHandler extends PGuiObject.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnKeyPressedHandler onKeyPressedHandler;

    @Override
    public final PGuiObject.OnKeyPressedHandler getOnKeyPressedHandler() {
        return onKeyPressedHandler;
    }

    @Override
    public final void setOnKeyPressedHandler(PGuiObject.OnKeyPressedHandler onKeyPressedHandler) {
        this.onKeyPressedHandler = onKeyPressedHandler;
    }

    public abstract static class OnKeyPressedHandler extends PGuiObject.PEventHandler<KeyEvent> {


    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnKeyReleasedHandler onKeyReleasedHandler;

    @Override
    public final PGuiObject.OnKeyReleasedHandler getOnKeyReleasedHandler() {
        return onKeyReleasedHandler;
    }

    @Override
    public final void setOnKeyReleasedHandler(PGuiObject.OnKeyReleasedHandler onKeyReleasedHandler) {
        this.onKeyReleasedHandler = onKeyReleasedHandler;
    }

    public abstract static class OnKeyReleasedHandler extends PGuiObject.PEventHandler<KeyEvent> {


    }


    //////////////////////////////////////////////////////////////////////////////////////

    private PGuiObject.OnKeyTypedHandler onKeyTypedHandler;

    @Override
    public final PGuiObject.OnKeyTypedHandler getOnKeyTypedHandler() {
        return onKeyTypedHandler;
    }

    @Override
    public final void setOnKeyTypedHandler(PGuiObject.OnKeyTypedHandler onKeyTypedHandler) {
        this.onKeyTypedHandler = onKeyTypedHandler;
    }

    public abstract static class OnKeyTypedHandler extends PGuiObject.PEventHandler<KeyEvent> {
    }
}
