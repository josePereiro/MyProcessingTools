package P2DPrimitiveWrappers;

import processing.core.PApplet;

import java.awt.*;

public class PointWrapper extends P2DPrimitiveWrapper {

    /**
     * @param x       the x value of the position of the wrapper.
     * @param y       the y value of the position of the wrapper.
     * @param context an instance of the PApplet where this wrapper will be drawn.
     */
    public PointWrapper(float x, float y, PApplet context) {
        super(x, y, context);
    }

    @Override
    public void draw() {
        draw(x, y);
    }

    @Override
    public void draw(float x, float y) {
        if (strokeEnable) {
            context.stroke(strokeColor, strokeAlpha);
            context.strokeWeight(strokeWeight);
            context.point(x, y);
        }
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return Point.distance(this.x, this.y, x, y) <= strokeWeight / 2;
    }

}
