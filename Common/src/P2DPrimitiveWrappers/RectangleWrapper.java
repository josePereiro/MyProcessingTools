package P2DPrimitiveWrappers;

import processing.core.PApplet;

import java.awt.*;

/**
 * This wrap a rectangle.
 */
public class RectangleWrapper extends P2DPrimitiveWrapper implements PRectangle{


    protected float strokeWeight = 1.0F;
    protected float height;
    protected float width;
    protected int backgroundColor = Color.WHITE.getRGB();
    protected int strokeColor = Color.BLACK.getRGB();
    protected boolean fillBackground = true;
    protected boolean drawStroke = true;

    public RectangleWrapper(float x, float y, float width, float height, PApplet context) {
        super(x, y, context);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {

        if (fillBackground) context.fill(backgroundColor);
        else context.noFill();

        if (drawStroke) {
            context.strokeWeight(strokeWeight);
            context.stroke(strokeColor);
        } else context.noStroke();

        if (drawStroke || fillBackground)
            context.rect(x, y, width, height);


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

    public float getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void drawBackground(boolean fill) {
        this.fillBackground = fill;
    }

    public void drawStroke(boolean stroke) {
        this.drawStroke = stroke;
    }

}
