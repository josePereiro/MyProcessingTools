package P2DPrimitiveWrappers;

import processing.core.PApplet;

public class EllipseWrapper extends P2DPrimitiveWrapper {

    private float vr;
    private float hr;

    public EllipseWrapper(float x, float y, float vr, float hr, PApplet context) {
        super(x, y, context);
        this.vr = vr;
        this.hr = hr;
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
            context.ellipse(x, y, hr, vr);
        }
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return Math.pow(this.x - x, 2) / Math.pow(getHr() / 2, 2) +
                Math.pow(this.y - y, 2) / Math.pow(getVr() / 2, 2) <= 1;
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

}
