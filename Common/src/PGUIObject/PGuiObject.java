package PGUIObject;

import Common.Tools;
import P2DPrimitiveWrappers.PDrawable;
import P2DPrimitiveWrappers.PLocatable;
import P2DPrimitiveWrappers.PRectangle;
import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.Event;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * This is the base interface for building processing graphic user interface objects.
 * A PGUIObject handle key and mouse event inputs. The graphic part
 * will be handle for a implementation of a {@link RectangleWrapper}
 */
public abstract class PGuiObject extends RectangleWrapper
        implements PDrawable, PLocatable, PFocusable, PRectangle {

    private static FocusEffect focusEffect;

    //Focus
    protected boolean focus = false;
    protected boolean focusable = true;

    public PGuiObject(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);
        if (focusEffect == null) {
            focusEffect = new DefaultFocusEffect(context);
        }
    }


    @Override
    public boolean isThisOverMe(float x, float y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    @Override
    public final void setFocus(boolean focus) {
        if (focusable) {
            this.focus = focus;
        }
    }

    @Override
    public boolean isFocusable() {
        return focusable;
    }

    @Override
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    @Override
    public void drawFocus() {
        if (focusEffect != null) {
            focusEffect.draw(this);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (isFocused()) {
            drawFocus();
        }
    }

    public class DefaultFocusEffect extends FocusEffect {

        private Tools.TimeOscillator focusStrokeOscillator;

        public DefaultFocusEffect(PApplet context) {
            super(context);
        }

        @Override
        public void draw(PGuiObject pGuiObject) {
            if (focusStrokeOscillator == null) {
                focusStrokeOscillator = new Tools.TimeOscillator(100) {

                    float factorStrokeWeightValue = 2;
                    float strokeWeightValue;
                    float strokeColor;
                    float ds = 0.3F;

                    @Override
                    public void onOscillationHappened() {
                        factorStrokeWeightValue += ds;
                        if (factorStrokeWeightValue < 1) {
                            ds = -ds;
                            factorStrokeWeightValue = 1;
                        } else if (factorStrokeWeightValue > 3) {
                            factorStrokeWeightValue = 3;
                            ds = -ds;
                        }
                        strokeColor = pGuiObject.getStrokeColor();
                        strokeWeightValue = pGuiObject.getStrokeWeight() * factorStrokeWeightValue;
                        context.strokeWeight(strokeWeightValue);
                        context.stroke(strokeColor);
                    }

                    @Override
                    public void onOscillationDidNotHappen() {
                        context.strokeWeight(strokeWeightValue);
                        context.stroke(strokeColor);
                    }
                };
            }
            focusStrokeOscillator.step();
            context.noFill();
            context.rect(pGuiObject.getX(), pGuiObject.getY(),
                    pGuiObject.getWidth(), pGuiObject.getHeight());

        }
    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * Mouse Released
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mousePressed(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mousePressed()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this PGuiObject. A {@link OnMouseReleasedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMouseReleasedHandler(OnMouseReleasedHandler)}
     * to set your custom handler. The {@link OnMouseReleasedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMouseReleasedHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMouseReleased(MouseEvent event) {
        if (onMouseReleasedHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMouseReleasedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMouseReleasedHandler onMouseReleasedHandler;

    /**
     * Return the {@link OnMouseReleasedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMouseReleased(MouseEvent)} method
     * @return
     */
    public final OnMouseReleasedHandler getOnMouseReleasedHandler() {
        return onMouseReleasedHandler;
    }

    /**
     * Set the {@link OnMouseReleasedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMouseReleased(MouseEvent)} method.
     *
     * @param onMouseReleasedHandler
     */
    public final void setOnMouseReleasedHandler(OnMouseReleasedHandler onMouseReleasedHandler) {
        this.onMouseReleasedHandler = onMouseReleasedHandler;
    }

    public abstract static class OnMouseReleasedHandler extends PEventHandler<MouseEvent> {

    }


    /** //////////////////////////////////////////////////////////////////////////////////////
     * Mouse Wheel
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mouseWheel(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mouseWheel()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this  A {@link OnMouseWheelHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMouseWheelHandler(OnMouseWheelHandler)}
     * to set your custom handler. The {@link OnMouseWheelHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMouseWheelHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMouseWheel(MouseEvent event) {
        if (onMouseWheelHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMouseWheelHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMouseWheelHandler onMouseWheelHandler;

    /**
     * Return the {@link OnMouseWheelHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMouseWheel(MouseEvent)} method
     * @return
     */
    public final OnMouseWheelHandler getOnMouseWheelHandler() {
        return onMouseWheelHandler;
    }

    /**
     * Set the {@link OnMouseWheelHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMouseWheel(MouseEvent)} method.
     *
     * @param onMouseWheelHandler
     */
    public final void setOnMouseWheelHandler(OnMouseWheelHandler onMouseWheelHandler) {
        this.onMouseWheelHandler = onMouseWheelHandler;
    }

    public abstract static class OnMouseWheelHandler extends PEventHandler<MouseEvent> {

    }


    /** //////////////////////////////////////////////////////////////////////////////////////
     * Mouse Pressed
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mousePressed(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mousePressed()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this PGuiObject. A {@link OnMousePressedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMousePressedHandler(OnMousePressedHandler)}
     * to set your custom handler. The {@link OnMousePressedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMousePressedHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMousePressed(MouseEvent event) {
        if (onMousePressedHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMousePressedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMousePressedHandler onMousePressedHandler;

    /**
     * Return the {@link OnMousePressedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMousePressed(MouseEvent)} method
     * @return
     */
    public final OnMousePressedHandler getOnMousePressedHandler() {
        return onMousePressedHandler;
    }

    /**
     * Set the {@link OnMousePressedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMousePressed(MouseEvent)} method.
     *
     * @param onMousePressedHandler
     */
    public final void setOnMousePressedHandler(OnMousePressedHandler onMousePressedHandler) {
        this.onMousePressedHandler = onMousePressedHandler;
    }

    public abstract static class OnMousePressedHandler extends PEventHandler<MouseEvent> {

    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * MouseClicked
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mouseClicked(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mouseClicked()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this PGuiObject. A {@link OnMouseClickedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMouseClickedHandler(OnMouseClickedHandler)}
     * to set your custom handler. The {@link OnMouseClickedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMouseClickedHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMouseClicked(MouseEvent event) {
        if (onMouseClickedHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMouseClickedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMouseClickedHandler onMouseClickedHandler;

    /**
     * Return the {@link OnMouseClickedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMouseClicked(MouseEvent)} method
     * @return
     */
    public final OnMouseClickedHandler getOnMouseClickedHandler() {
        return onMouseClickedHandler;
    }

    /**
     * Set the {@link OnMouseClickedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMouseClicked(MouseEvent)} method.
     *
     * @param onMouseClickedHandler
     */
    public final void setOnMouseClickedHandler(OnMouseClickedHandler onMouseClickedHandler) {
        this.onMouseClickedHandler = onMouseClickedHandler;
    }

    public abstract static class OnMouseClickedHandler extends PEventHandler<MouseEvent> {

    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * MouseDragged
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mouseDragged(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mouseDragged()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this PGuiObject. A {@link OnMouseDraggedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMouseDraggedHandler(OnMouseDraggedHandler)}
     * to set your custom handler. The {@link OnMouseDraggedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMouseDraggedHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMouseDragged(MouseEvent event) {
        if (onMouseDraggedHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMouseDraggedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMouseDraggedHandler onMouseDraggedHandler;

    /**
     * Return the {@link OnMouseDraggedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMouseDragged(MouseEvent)} method
     * @return
     */
    public final OnMouseDraggedHandler getOnMouseDraggedHandler() {
        return onMouseDraggedHandler;
    }

    /**
     * Set the {@link OnMouseDraggedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMouseDragged(MouseEvent)} method.
     *
     * @param onMouseDraggedHandler
     */
    public final void setOnMouseDraggedHandler(OnMouseDraggedHandler onMouseDraggedHandler) {
        this.onMouseDraggedHandler = onMouseDraggedHandler;
    }

    public abstract static class OnMouseDraggedHandler extends PEventHandler<MouseEvent> {

    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * MouseMoved
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#mouseMoved(MouseEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#mouseMoved()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if the {@link PApplet#mouseX} and
     * {@link PApplet#mouseY} current values return {@code true} with
     * the method {@link PGuiObject#isThisOverMe(float mouseX, float mouseY)}
     * of this PGuiObject. A {@link OnMouseMovedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnMouseMovedHandler(OnMouseMovedHandler)}
     * to set your custom handler. The {@link OnMouseMovedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link MouseEvent} will be passed to it.
     * If the {@code OnMouseMovedHandler} is null the event won't be handled.
     *
     * @param event the {@link MouseEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForMouseMoved(MouseEvent event) {
        if (onMouseMovedHandler == null) {
            return false;
        }
        if (isThisOverMe(context.mouseX, context.mouseY)) {
            return onMouseMovedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnMouseMovedHandler onMouseMovedHandler;

    /**
     * Return the {@link OnMouseMovedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForMouseMoved(MouseEvent)} method
     * @return
     */
    public final OnMouseMovedHandler getOnMouseMovedHandler() {
        return onMouseMovedHandler;
    }

    /**
     * Set the {@link OnMouseMovedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForMouseMoved(MouseEvent)} method.
     *
     * @param onMouseMovedHandler
     */
    public final void setOnMouseMovedHandler(OnMouseMovedHandler onMouseMovedHandler) {
        this.onMouseMovedHandler = onMouseMovedHandler;
    }

    public abstract static class OnMouseMovedHandler extends PEventHandler<MouseEvent> {

    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * KeyPressed
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#keyPressed(KeyEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#keyPressed()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if {@link PGuiObject#isFocusable()} and {@link PGuiObject#isFocused()}
     * returns {@code true}. A {@link OnKeyPressedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnKeyPressedHandler(OnKeyPressedHandler)}
     * to set your custom handler. The {@link OnKeyPressedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link KeyEvent} will be passed to it.
     * If the {@code OnKeyPressedHandler} is null the event won't be handled.
     *
     * @param event the {@link KeyEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForKeyPressed(KeyEvent event) {
        if (onKeyPressedHandler == null) {
            return false;
        }
        if (isFocusable() && isFocused()) {
            return onKeyPressedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnKeyPressedHandler onKeyPressedHandler;

    /**
     * Return the {@link OnKeyPressedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForKeyPressed(KeyEvent)} method
     * @return
     */
    public final OnKeyPressedHandler getOnKeyPressedHandler() {
        return onKeyPressedHandler;
    }

    /**
     * Set the {@link OnKeyPressedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForKeyPressed(KeyEvent)} method.
     *
     * @param onKeyPressedHandler
     */
    public final void setOnKeyPressedHandler(OnKeyPressedHandler onKeyPressedHandler) {
        this.onKeyPressedHandler = onKeyPressedHandler;
    }

    public abstract static class OnKeyPressedHandler extends PEventHandler<KeyEvent> {


    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * KeyReleased
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#keyReleased(KeyEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#keyReleased()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if {@link PGuiObject#isFocusable()} and {@link PGuiObject#isFocused()}
     * returns {@code true}. A {@link OnKeyReleasedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnKeyReleasedHandler(OnKeyReleasedHandler)}
     * to set your custom handler. The {@link OnKeyReleasedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link KeyEvent} will be passed to it.
     * If the {@code OnKeyReleasedHandler} is null the event won't be handled.
     *
     * @param event the {@link KeyEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForKeyReleased(KeyEvent event) {
        if (onKeyReleasedHandler == null) {
            return false;
        }
        if (isFocusable() && isFocused()) {
            return onKeyReleasedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnKeyReleasedHandler onKeyReleasedHandler;

    /**
     * Return the {@link OnKeyReleasedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForKeyReleased(KeyEvent)} method
     * @return
     */
    public final OnKeyReleasedHandler getOnKeyReleasedHandler() {
        return onKeyReleasedHandler;
    }

    /**
     * Set the {@link OnKeyReleasedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForKeyReleased(KeyEvent)} method.
     *
     * @param onKeyReleasedHandler
     */
    public final void setOnKeyReleasedHandler(OnKeyReleasedHandler onKeyReleasedHandler) {
        this.onKeyReleasedHandler = onKeyReleasedHandler;
    }

    public abstract static class OnKeyReleasedHandler extends PEventHandler<KeyEvent> {


    }

    /** //////////////////////////////////////////////////////////////////////////////////////
     * KeyTyped
     *///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Put this in the {@link PApplet#keyTyped(KeyEvent event)}
     * method for handling this event for this PGuiObject. You can put it in the
     * {@link PApplet#keyTyped()} and pass {@code event} as null, but it can
     * be dangerous!!!
     * The event will be handle only if {@link PGuiObject#isFocusable()} and {@link PGuiObject#isFocused()}
     * returns {@code true}. A {@link OnKeyTypedHandler} is required for
     * handling the action, you can use {@link PGuiObject#setOnKeyTypedHandler(OnKeyTypedHandler)}
     * to set your custom handler. The {@link OnKeyTypedHandler#handlePEvent(Event, PGuiObject)} will
     * be called inside this method, and the given {@link KeyEvent} will be passed to it.
     * If the {@code OnKeyTypedHandler} is null the event won't be handled.
     *
     * @param event the {@link KeyEvent} given by the current {@link PApplet}
     * @return {@code true} is the event was handle
     */
    public final boolean listeningForKeyTyped(KeyEvent event) {
        if (onKeyTypedHandler == null) {
            return false;
        }
        if (isFocusable() && isFocused()) {
            return onKeyTypedHandler.handlePEvent(event,this);
        } else {
            return false;
        }
    }

    private OnKeyTypedHandler onKeyTypedHandler;

    /**
     * Return the {@link OnKeyTypedHandler} object that handle this kind of
     * event in the {@link PGuiObject#listeningForKeyTyped(KeyEvent)} method
     * @return
     */
    public final OnKeyTypedHandler getOnKeyTypedHandler() {
        return onKeyTypedHandler;
    }

    /**
     * Set the {@link OnKeyTypedHandler} object that will handle this kind of
     * event in the {@link PGuiObject#listeningForKeyTyped(KeyEvent)} method.
     *
     * @param onKeyTypedHandler
     */
    public final void setOnKeyTypedHandler(OnKeyTypedHandler onKeyTypedHandler) {
        this.onKeyTypedHandler = onKeyTypedHandler;
    }

    public abstract static class OnKeyTypedHandler extends PEventHandler<KeyEvent> {
    }

}
