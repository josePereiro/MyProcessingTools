package P2DPrimitiveWrappers;

import processing.core.PApplet;

import java.awt.*;
import java.awt.geom.Line2D;

public class LineWrapper extends P2DPrimitiveWrapper {

    private float x1;
    private float y1;
    private float strokeWeight = 1.0F;
    private int strokeColor = Color.BLACK.getRGB();

    public LineWrapper(float x, float y, float x1, float y1, PApplet context) {
        super(x, y, context);
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public void draw() {
        context.stroke(strokeColor);
        context.strokeWeight(strokeWeight);
        context.line(x, y, x1, y1);
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return Line2D.ptSegDistSq(this.x, this.y, x1, y1, x, y) <= strokeWeight * strokeWeight / 4;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
