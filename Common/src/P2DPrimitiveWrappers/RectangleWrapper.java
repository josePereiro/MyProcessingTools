package P2DPrimitiveWrappers;

import processing.core.PApplet;

/**
 * This wrap a rectangle.
 */
public class RectangleWrapper extends P2DPrimitiveWrapper implements PRectangle {


    protected float height;
    protected float width;

    public RectangleWrapper(float x, float y, float width, float height, PApplet context) {
        super(x, y, context);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        draw(x, y);
    }

    @Override
    public void draw(float x, float y) {
        if (fillEnable) {
            context.fill(fillColor, fillAlpha);
        } else {
            context.noFill();
        }

        if (strokeEnable) {
            context.strokeWeight(strokeWeight);
            context.stroke(strokeColor, strokeAlpha);
        } else {
            context.noStroke();
        }

        if (strokeEnable || fillEnable) {
            context.rect(x, y, width, height);
        }
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

}
