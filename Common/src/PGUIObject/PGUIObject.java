package PGUIObject;

import Common.Tools;
import P2DPrimitiveWrappers.PDrawable;
import P2DPrimitiveWrappers.PLocatable;
import P2DPrimitiveWrappers.PRectangle;
import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * This is the base interface for building processing graphic user interface objects.
 * A PGUIObject handle key and mouse event inputs. The graphic part
 * will be handle for a implementation of a {@link RectangleWrapper}
 */
public abstract class PGUIObject extends RectangleWrapper
        implements PListener, PDrawable, PLocatable, Focusable, PRectangle {

    private static FocusEffect focusEffect;

    //InputHandling
    protected boolean listeningMouse = true;
    protected boolean listeningKey = true;

    //Focus
    protected boolean focus = false;
    protected boolean focusable = true;

    public PGUIObject(float x, float y, float width, float height, PApplet context) {
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
    public final boolean listenForKeyPressed(KeyEvent keyEvent) {
        if (isListeningKey()) {
            return onKeyPressed(keyEvent);
        } else {
            return false;
        }
    }

    @Override
    public final boolean listenForMouseClicked(MouseEvent mouseEvent) {
        if (isListeningMouse()) {
            if (isThisOverMe(context.mouseX, context.mouseY)) {
                return onMouseClick(mouseEvent);
            }
        }
        return false;
    }

    @Override
    public boolean listeningForMouseWheel(MouseEvent mouseEvent) {
        if (isListeningMouse()) {
            if (isThisOverMe(context.mouseX, context.mouseY)) {
                return onMouseWheel(mouseEvent);
            }
        }
        return false;
    }

    @Override
    public boolean isListeningMouse() {
        return listeningMouse;
    }

    @Override
    public void setListeningMouse(boolean listeningMouse) {
        this.listeningMouse = listeningMouse;
    }

    @Override
    public boolean isListeningKey() {
        return listeningKey;
    }

    @Override
    public void setListeningKey(boolean listeningKey) {
        this.listeningKey = listeningKey;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    @Override
    public void setFocus(boolean focus) {
        this.focus = focus;
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
        public void draw(PGUIObject pGuiObject) {
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

}
