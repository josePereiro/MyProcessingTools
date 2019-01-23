package P2DPrimitiveWrappers;

import Common.PObject;
import PGUIObject.PGUIObject;
import processing.core.PApplet;

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
public abstract class P2DPrimitiveWrapper extends PObject implements PGUIObject {


    protected float x;
    protected float y;

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
     *<p>
     *<p>
     * The objective of each wrapper is to be drawn in a processing sketch. So it will have at least a
     * position and a PApplet context.
     * @param x the x value of the position of the wrapper.
     * @param y the y value of the position of the wrapper.
     * @param context an instance of the PApplet where this wrapper will be drawn.
     */
    public P2DPrimitiveWrapper(float x, float y, PApplet context) {
        super(context);
        this.x = x;
        this.y = y;
    }

    /**
     * Will draw the P2DPrimitiveWrapper into the context
     */
    public abstract void draw();

    /**
     * Return true if the given point is over the P2DPrimitiveWrapper area.
     *
     * @param x A global x coordinate
     * @param y A global y coordinate
     * @return
     */
    public abstract boolean isThisOverMe(float x, float y);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}