package Common;


import processing.core.PApplet;

/**
 * This is the base class of any class in this package...
 */
public class PObject {

    protected final PApplet context;

    public PObject(PApplet context){

        this.context = context;
    }

    public PApplet getContext() {
        return context;
    }
}
