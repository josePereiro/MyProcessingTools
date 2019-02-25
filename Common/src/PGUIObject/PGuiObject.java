package PGUIObject;

import Common.Tools;
import P2DPrimitiveWrappers.PDrawable;
import P2DPrimitiveWrappers.PLocatable;
import P2DPrimitiveWrappers.PRectangle;
import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;


public abstract class PGuiObject extends RectangleWrapper
        implements PDrawable, PLocatable, PFocusable, PRectangle, PEventHandler {

    private static FocusEffect focusEffect;


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

    public static class DefaultFocusEffect extends FocusEffect {

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


    //MOUSE EVENTS//////////////////////////////////////////////////////////////////////////////////////

    private boolean onMouseEventHandlerEnable = true;

    private OnMouseReleasedHandler onMouseReleasedHandler;
    private boolean onMouseReleasedHandlerEnable;

    @Override
    public final OnMouseReleasedHandler getOnMouseReleasedHandler() {
        return onMouseReleasedHandler;
    }

    @Override
    public final void setOnMouseReleasedHandler(OnMouseReleasedHandler onMouseReleasedHandler) {
        this.onMouseReleasedHandler = onMouseReleasedHandler;
    }

    public abstract static class OnMouseReleasedHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMouseWheelHandler onMouseWheelHandler;
    private boolean onMouseWheelHandlerEnable = true;

    @Override
    public final OnMouseWheelHandler getOnMouseWheelHandler() {
        return onMouseWheelHandler;
    }

    @Override
    public final void setOnMouseWheelHandler(OnMouseWheelHandler onMouseWheelHandler) {
        this.onMouseWheelHandler = onMouseWheelHandler;
    }

    public abstract static class OnMouseWheelHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMousePressedHandler onMousePressedHandler;
    private boolean onMousePressedHandlerEnable = true;

    @Override
    public final OnMousePressedHandler getOnMousePressedHandler() {
        return onMousePressedHandler;
    }

    @Override
    public final void setOnMousePressedHandler(OnMousePressedHandler onMousePressedHandler) {
        this.onMousePressedHandler = onMousePressedHandler;
    }

    public abstract static class OnMousePressedHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMouseClickedHandler onMouseClickedHandler;
    private boolean onMouseClickedHandlerEnable = true;

    @Override
    public final OnMouseClickedHandler getOnMouseClickedHandler() {
        return onMouseClickedHandler;
    }

    @Override
    public final void setOnMouseClickedHandler(OnMouseClickedHandler onMouseClickedHandler) {
        this.onMouseClickedHandler = onMouseClickedHandler;
    }

    public abstract static class OnMouseClickedHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMouseDraggedHandler onMouseDraggedHandler;
    private boolean onMouseDraggedHandlerEnable = true;

    @Override
    public final OnMouseDraggedHandler getOnMouseDraggedHandler() {
        return onMouseDraggedHandler;
    }

    @Override
    public final void setOnMouseDraggedHandler(OnMouseDraggedHandler onMouseDraggedHandler) {
        this.onMouseDraggedHandler = onMouseDraggedHandler;
    }

    public abstract static class OnMouseDraggedHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMouseMovedHandler onMouseMovedHandler;
    private boolean onMouseMovedHandlerEnable = true;

    @Override
    public final OnMouseMovedHandler getOnMouseMovedHandler() {
        return onMouseMovedHandler;
    }

    @Override
    public final void setOnMouseMovedHandler(OnMouseMovedHandler onMouseMovedHandler) {
        this.onMouseMovedHandler = onMouseMovedHandler;
    }

    public abstract static class OnMouseMovedHandler extends PGuiManager.PEventHandler<MouseEvent> {

    }


    //KEY EVENTS//////////////////////////////////////////////////////////////////////////////////////
    private boolean onKeyEventHandlerEnable = true;

    private OnKeyPressedHandler onKeyPressedHandler;
    private boolean onKeyPressedHandlerEnable = true;

    @Override
    public final OnKeyPressedHandler getOnKeyPressedHandler() {
        return onKeyPressedHandler;
    }

    @Override
    public final void setOnKeyPressedHandler(OnKeyPressedHandler onKeyPressedHandler) {
        this.onKeyPressedHandler = onKeyPressedHandler;
    }

    public abstract static class OnKeyPressedHandler extends PGuiManager.PEventHandler<KeyEvent> {


    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnKeyReleasedHandler onKeyReleasedHandler;
    private boolean onKeyReleasedHandlerEnable = true;

    @Override
    public final OnKeyReleasedHandler getOnKeyReleasedHandler() {
        return onKeyReleasedHandler;
    }

    @Override
    public final void setOnKeyReleasedHandler(OnKeyReleasedHandler onKeyReleasedHandler) {
        this.onKeyReleasedHandler = onKeyReleasedHandler;
    }

    public abstract static class OnKeyReleasedHandler extends PGuiManager.PEventHandler<KeyEvent> {


    }


    //////////////////////////////////////////////////////////////////////////////////////

    private OnKeyTypedHandler onKeyTypedHandler;
    private boolean onKeyTypedHandlerEnable;

    @Override
    public final OnKeyTypedHandler getOnKeyTypedHandler() {
        return onKeyTypedHandler;
    }

    @Override
    public final void setOnKeyTypedHandler(OnKeyTypedHandler onKeyTypedHandler) {
        this.onKeyTypedHandler = onKeyTypedHandler;
    }

    public abstract static class OnKeyTypedHandler extends PGuiManager.PEventHandler<KeyEvent> {
    }

    public boolean isOnMouseEventHandlerEnable() {
        return onMouseEventHandlerEnable;
    }

    public void setOnMouseEventHandlerEnable(boolean onMouseEventHandlerEnable) {
        this.onMouseEventHandlerEnable = onMouseEventHandlerEnable;
    }

    public boolean isOnMouseReleasedHandlerEnable() {
        return onMouseReleasedHandlerEnable;
    }

    public void setOnMouseReleasedHandlerEnable(boolean onMouseReleasedHandlerEnable) {
        this.onMouseReleasedHandlerEnable = onMouseReleasedHandlerEnable;
    }

    public boolean isOnMouseWheelHandlerEnable() {
        return onMouseWheelHandlerEnable;
    }

    public void setOnMouseWheelHandlerEnable(boolean onMouseWheelHandlerEnable) {
        this.onMouseWheelHandlerEnable = onMouseWheelHandlerEnable;
    }

    public boolean isOnMousePressedHandlerEnable() {
        return onMousePressedHandlerEnable;
    }

    public void setOnMousePressedHandlerEnable(boolean onMousePressedHandlerEnable) {
        this.onMousePressedHandlerEnable = onMousePressedHandlerEnable;
    }

    public boolean isOnMouseClickedHandlerEnable() {
        return onMouseClickedHandlerEnable;
    }

    public void setOnMouseClickedHandlerEnable(boolean onMouseClickedHandlerEnable) {
        this.onMouseClickedHandlerEnable = onMouseClickedHandlerEnable;
    }

    public boolean isOnMouseDraggedHandlerEnable() {
        return onMouseDraggedHandlerEnable;
    }

    public void setOnMouseDraggedHandlerEnable(boolean onMouseDraggedHandlerEnable) {
        this.onMouseDraggedHandlerEnable = onMouseDraggedHandlerEnable;
    }

    public boolean isOnMouseMovedHandlerEnable() {
        return onMouseMovedHandlerEnable;
    }

    public void setOnMouseMovedHandlerEnable(boolean onMouseMovedHandlerEnable) {
        this.onMouseMovedHandlerEnable = onMouseMovedHandlerEnable;
    }

    public boolean isOnKeyEventHandlerEnable() {
        return onKeyEventHandlerEnable;
    }

    public void setOnKeyEventHandlerEnable(boolean onKeyEventHandlerEnable) {
        this.onKeyEventHandlerEnable = onKeyEventHandlerEnable;
    }

    public boolean isOnKeyPressedHandlerEnable() {
        return onKeyPressedHandlerEnable;
    }

    public void setOnKeyPressedHandlerEnable(boolean onKeyPressedHandlerEnable) {
        this.onKeyPressedHandlerEnable = onKeyPressedHandlerEnable;
    }

    public boolean isOnKeyReleasedHandlerEnable() {
        return onKeyReleasedHandlerEnable;
    }

    public void setOnKeyReleasedHandlerEnable(boolean onKeyReleasedHandlerEnable) {
        this.onKeyReleasedHandlerEnable = onKeyReleasedHandlerEnable;
    }

    public boolean isOnKeyTypedHandlerEnable() {
        return onKeyTypedHandlerEnable;
    }

    public void setOnKeyTypedHandlerEnable(boolean onKeyTypedHandlerEnable) {
        this.onKeyTypedHandlerEnable = onKeyTypedHandlerEnable;
    }
}
