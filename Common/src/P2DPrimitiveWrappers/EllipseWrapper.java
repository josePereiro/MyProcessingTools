package P2DPrimitiveWrappers;

import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.*;

public class EllipseWrapper extends P2DPrimitiveWrapper {

    private float vr;
    private float hr;
    private float strokeWeight = 1.0F;
    private int backgroundColor = Color.WHITE.getRGB();
    private int strokeColor = Color.BLACK.getRGB();
    private boolean fillBackground = true;
    private boolean drawStroke = true;

    public EllipseWrapper(float x, float y, float vr, float hr, PApplet context) {
        super(x, y, context);
        this.vr = vr;
        this.hr = hr;
    }

    @Override
    public void draw() {
        context.ellipseMode(PConstants.CORNER);
        if (fillBackground) {
            context.fill(backgroundColor);
        } else {
            context.noFill();
        }
        if (drawStroke) {
            context.strokeWeight(strokeWeight);
            context.stroke(strokeColor);
        } else {
            context.noStroke();
        }
        if (drawStroke || fillBackground) {
            context.ellipse(x, y, hr, vr);
        }
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        if (hr <= 0.0 || vr <= 0.0) {
            return false;
        }
        float nx = (x - getX()) / hr - 0.5F;
        float ny = (y - getY()) / vr - 0.5F;
        return (nx * nx + ny * ny) < 0.25;
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

    public void fill(boolean fill) {
        this.fillBackground = fill;
    }

    public void stroke(boolean stroke) {
        this.drawStroke = stroke;
    }

    public float getVr() {
        return vr;
    }

    public void setVr(float vr) {
        this.vr = vr;
    }

    public float getHr() {
        return hr;
    }

    public void setHr(float hr) {
        this.hr = hr;
    }

    public boolean isFillBackground() {
        return fillBackground;
    }

    public void setFillBackground(boolean fillBackground) {
        this.fillBackground = fillBackground;
    }

    public boolean isDrawStroke() {
        return drawStroke;
    }

    public void setDrawStroke(boolean drawStroke) {
        this.drawStroke = drawStroke;
    }



}
