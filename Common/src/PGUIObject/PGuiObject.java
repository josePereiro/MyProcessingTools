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


    //////////////////////////////////////////////////////////////////////////////////////

    private OnMouseReleasedHandler onMouseReleasedHandler;

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


    //////////////////////////////////////////////////////////////////////////////////////

    private OnKeyPressedHandler onKeyPressedHandler;

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

}
