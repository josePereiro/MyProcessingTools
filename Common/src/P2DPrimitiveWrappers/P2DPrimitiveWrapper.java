package P2DPrimitiveWrappers;

import Common.PObject;
import processing.core.PApplet;

import java.awt.*;

/**
 * This is the base object for building Processing 2d primitive graphic wrappers.
 * All wrapper will be a subclass of this class.
 * The processing 2d primitives (arc,ellipse,line,point, quad,rect,triangle) will be wrapped
 * to treat them as objects. Each wrapper most have a couple of useful, but basics, methods for
 * handling the draw of this primitives.
 * This implementations do not pursuit to improve Processing performance, but make easy to build
 * complex graphics with this primitive forms. So, do not use this wrappers as a substitution of the
 * Processing API in every context, only when performance is not an issue.
 * Regards
 * Pereiro
 */
public abstract class P2DPrimitiveWrapper extends PObject implements PDrawable, PLocatable {


    protected float x;
    protected float y;
    protected int fillColor;
    protected int fillAlpha;
    protected int strokeColor;
    protected int strokeAlpha;
    protected float strokeWeight;
    protected boolean strokeEnable;
    protected boolean fillEnable;

    /**
     * This is the base object for building Processing 2d primitive graphic wrappers.
     * All wrapper will be a subclass of this class.
     * The processing 2d primitives (arc,ellipse,line,point, quad,rect,triangle) will be wrapped
     * to treat them as objects. Each wrapper most have a couple of useful, but basics, methods for
     * handling the draw of this primitives.
     * This implementations do not pursuit to improve Processing performance, but make easy to build
     * complex graphics with this primitive forms. So, do not use this wrappers as a substitution of the
     * Processing API in every context, only when performance is not an issue.
     * Regards
     * Pereiro
     * <p>
     * <p>
     * The objective of each wrapper is to be drawn in a processing sketch. So it will have at least a
     * position and a PApplet context.
     *
     * @param x       the x value of the position of the wrapper.
     * @param y       the y value of the position of the wrapper.
     * @param context an instance of the PApplet where this wrapper will be drawn.
     */
    public P2DPrimitiveWrapper(float x, float y, PApplet context) {
        super(context);
        this.x = x;
        this.y = y;
        fillAlpha = 255;
        strokeAlpha = 255;
        strokeEnable = true;
        fillEnable = true;
        strokeColor = Color.BLACK.getRGB();
        fillColor = Color.WHITE.getRGB();
        strokeWeight = 1.0F;
    }

    /**
     * Will draw the P2DPrimitiveWrapper into the context
     */
    @Override
    public abstract void draw();

    /**
     * @param x A global x coordinate
     * @param y A global y coordinate
     * @return Return true if the given point is over the P2DPrimitiveWrapper area.
     */
    @Override
    public abstract boolean isThisOverMe(float x, float y);

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getFillAlpha() {
        return fillAlpha;
    }

    public void setFillAlpha(int fillAlpha) {
        this.fillAlpha = fillAlpha;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getStrokeAlpha() {
        return strokeAlpha;
    }

    public void setStrokeAlpha(int strokeAlpha) {
        this.strokeAlpha = strokeAlpha;
    }

    public float getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public boolean isStrokeEnable() {
        return strokeEnable;
    }

    public void setStrokeEnable(boolean strokeEnable) {
        this.strokeEnable = strokeEnable;
    }

    public boolean isFillEnable() {
        return fillEnable;
    }

    public void setFillEnable(boolean fillEnable) {
        this.fillEnable = fillEnable;
    }
}