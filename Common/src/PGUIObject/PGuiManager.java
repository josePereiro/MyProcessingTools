package PGUIObject;

import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.Event;
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

    public boolean setFocusTo(PGuiObject pGuiObject) {
        for (PGuiObject guiObject : pGuiObjects) {
            if (pGuiObject.equals(guiObject)) {
                //Focus
                if (pGuiObject.isFocusable()) {
                    if (focusedPGuiObject != null) {
                        focusChanged = !focusedPGuiObject.equals(pGuiObject);
                        if (focusChanged) {
                            focusedPGuiObject = pGuiObject;
                        }
                    } else {
                        focusChanged = true;
                        focusedPGuiObject = pGuiObject;
                    }

                    for (PGuiObject pGO : pGuiObjects) {
                        pGO.setFocus(focusedPGuiObject.equals(pGO));
                    }

                    if (focusChanged) {
                        if (onFocusChangedHandler != null) {
                            onFocusChangedHandler.handlePEvent(guiObject);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void focusNext() {

        boolean afterFocused = false;
        for (int oi = 0; oi < pGuiObjects.size(); oi++) {
            if (pGuiObjects.get(oi).equals(focusedPGuiObject)) {
                afterFocused = true;
            }
            if (afterFocused && pGuiObjects.get(oi).isFocusable()) {
                setFocusTo(pGuiObjects.get(oi));
                return;
            }
        }

        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isFocusable()) {
                setFocusTo(pGuiObject);
            }
        }
    }

    private boolean listeningForMouseReleased = true;

    @Override
    public final boolean isListeningForMouseReleased() {
        return listeningForMouseReleased;
    }

    @Override
    public final void setListeningForMouseReleased(boolean enable) {
        listeningForMouseReleased = enable;
    }

    @Override
    public final boolean listeningForMouseReleased(MouseEvent event) {

        if (!isListeningForMouseReleased()) {
            return false;
        }


        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMouseReleasedHandlerEnable() &&
                    pGuiObject.getOnMouseReleasedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                if (pGuiObject.getOnMouseReleasedHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnMouseReleasedHandler() != null) {
            return getOnMouseReleasedHandler().handlePEvent(event, null);
        }
        return false;
    }

    private boolean listeningForMouseWheel = true;

    @Override
    public final boolean isListeningForMouseWheel() {
        return listeningForMouseWheel;
    }

    @Override
    public final void setListeningForMouseWheel(boolean enable) {
        listeningForMouseWheel = enable;
    }

    @Override
    public final boolean listeningForMouseWheel(MouseEvent event) {

        if (!isListeningForMouseWheel()) {
            return false;
        }


        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMouseWheelHandlerEnable() && pGuiObject.getOnMouseWheelHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                if (pGuiObject.getOnMouseWheelHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnMouseWheelHandler() != null) {
            return getOnMouseWheelHandler().handlePEvent(event, null);
        }
        return false;
    }

    private boolean listeningForMousePressed = true;

    @Override
    public final boolean isListeningForMousePressed() {
        return listeningForMousePressed;
    }

    @Override
    public final void setListeningForMousePressed(boolean enable) {
        listeningForMousePressed = enable;
    }

    @Override
    public final boolean listeningForMousePressed(MouseEvent event) {

        if (!isListeningForMousePressed()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMousePressedHandlerEnable() && pGuiObject.getOnMousePressedHandler() != null &&
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

    private boolean listeningForMouseClicked = true;

    @Override
    public final boolean isListeningForMouseClicked() {
        return listeningForMouseClicked;
    }

    @Override
    public final void setListeningForMouseClicked(boolean enable) {
        listeningForMouseClicked = enable;
    }

    @Override
    public final boolean listeningForMouseClicked(MouseEvent event) {

        if (!isListeningForMouseClicked()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMouseClickedHandlerEnable() &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {

                //Focus
                setFocusTo(pGuiObject);

                //EventHandler
                if (pGuiObject.getOnMouseClickedHandler() != null) {
                    return pGuiObject.getOnMouseClickedHandler().handlePEvent(event, pGuiObject);
                } else {
                    return false;
                }
            }
        }

        //Manager
        if (getOnMouseClickedHandler() != null) {
            return getOnMouseClickedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
    }

    private boolean listeningForMouseDragged = true;

    @Override
    public final boolean isListeningForMouseDragged() {
        return listeningForMouseDragged;
    }

    @Override
    public final void setListeningForMouseDragged(boolean enable) {
        listeningForMouseDragged = enable;
    }

    @Override
    public final boolean listeningForMouseDragged(MouseEvent event) {

        if (!isListeningForMouseDragged()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMouseDraggedHandlerEnable() && pGuiObject.getOnMouseDraggedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                if (pGuiObject.getOnMouseDraggedHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnMouseDraggedHandler() != null) {
            return getOnMouseDraggedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
    }

    private boolean listeningForMouseMoved = true;

    @Override
    public final boolean isListeningForMouseMoved() {
        return listeningForMouseMoved;
    }

    @Override
    public final void setListeningForMouseMoved(boolean enable) {
        listeningForMouseMoved = enable;
    }

    @Override
    public final boolean listeningForMouseMoved(MouseEvent event) {

        if (!isListeningForMouseMoved()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.isOnMouseMovedHandlerEnable() && pGuiObject.getOnMouseMovedHandler() != null &&
                    pGuiObject.isThisOverMe(context.mouseX, context.mouseY)) {
                if (pGuiObject.getOnMouseMovedHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnMouseMovedHandler() != null) {
            return getOnMouseMovedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
    }

    private boolean listeningForKeyPressed = true;

    @Override
    public final boolean isListeningForKeyPressed() {
        return listeningForKeyPressed;
    }

    @Override
    public final void setListeningForKeyPressed(boolean enable) {
        listeningForKeyPressed = enable;
    }

    @Override
    public final boolean listeningForKeyPressed(KeyEvent event) {
        if (!isListeningForKeyPressed()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyPressedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                if (pGuiObject.getOnKeyPressedHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnKeyPressedHandler() != null) {
            return getOnKeyPressedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
    }

    private boolean listeningForKeyReleased = true;

    @Override
    public final boolean isListeningForKeyReleased() {
        return listeningForKeyReleased;
    }

    @Override
    public final void setListeningForKeyReleased(boolean enable) {
        listeningForKeyReleased = enable;
    }

    @Override
    public final boolean listeningForKeyReleased(KeyEvent event) {
        if (!isListeningForKeyReleased()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyReleasedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                if (pGuiObject.getOnKeyReleasedHandler().handlePEvent(event, pGuiObject)) {
                    return false;
                }
            }
        }

        //Manager
        if (getOnKeyReleasedHandler() != null) {
            return getOnKeyReleasedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
    }

    private boolean listeningForKeyTyped = true;

    @Override
    public final boolean isListeningForKeyTyped() {
        return listeningForKeyTyped;
    }

    @Override
    public final void setListeningForKeyTyped(boolean enable) {
        listeningForKeyTyped = enable;
    }

    @Override
    public final boolean listeningForKeyTyped(KeyEvent event) {
        if (!isListeningForKeyTyped()) {
            return false;
        }

        //PGuiObjects
        for (PGuiObject pGuiObject : pGuiObjects) {
            if (pGuiObject.getOnKeyTypedHandler() != null &&
                    pGuiObject.isFocusable() && pGuiObject.isFocused()) {
                if (pGuiObject.getOnKeyTypedHandler().handlePEvent(event, pGuiObject)) {
                    return true;
                }
            }
        }

        //Manager
        if (getOnKeyTypedHandler() != null) {
            return getOnKeyTypedHandler().handlePEvent(event, null);
        } else {
            return false;
        }
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

    public abstract static class OnMouseReleasedHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnMouseWheelHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnMousePressedHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnMouseClickedHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnMouseDraggedHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnMouseMovedHandler extends PEventHandler<MouseEvent> {

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

    public abstract static class OnKeyPressedHandler extends PEventHandler<KeyEvent> {


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

    public abstract static class OnKeyReleasedHandler extends PEventHandler<KeyEvent> {


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

    public abstract static class OnKeyTypedHandler extends PEventHandler<KeyEvent> {
    }

    private OnFocusChangedHandler onFocusChangedHandler;

    public OnFocusChangedHandler getOnFocusChangedHandler() {
        return onFocusChangedHandler;
    }

    public void setOnFocusChangedHandler(OnFocusChangedHandler onFocusChangedHandler) {
        this.onFocusChangedHandler = onFocusChangedHandler;
    }

    public abstract static class OnFocusChangedHandler {

        public abstract void handlePEvent(PGuiObject pGuiObject);
    }

    public static abstract class PEventHandler<T extends Event> {

        /**
         * The implementation of the method will be called in any
         * listening event methods of the {@link PGuiObject} objects.
         * It will be passed the implicated {@link Event} and {@link PGuiObject}
         * to be used inside this method. You are welcome!!!
         *
         * @param event
         * @param pGuiObject
         * @return
         */
        public abstract boolean handlePEvent(T event, PGuiObject pGuiObject);

    }

    public boolean focusChanged() {
        return focusChanged;
    }

    public PGuiObject getFocusedPGuiObject() {
        return focusedPGuiObject;
    }

}
