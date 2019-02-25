package GObjects;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * A GObject is a class that handle a group of processing graphics methods (line, image,
 * rect, ellipse, etc) to form a coherent draw object...
 */
public abstract class GObject {

    //Static Fields
    private static ArrayList<String> ids = new ArrayList<>();
    private static int objectLabel = 0;

    protected PApplet context;
    protected String id;
    protected int x;
    protected int y;

    /**
     * A GObject is a class that handle a group of processing graphics methods (line, image,
     * rect, ellipse, etc) to form a coherent draw object...
     * This constructor will create a default id for the objects.
     *
     * @param x
     * @param y
     * @param context
     */
    public GObject(int x, int y, PApplet context) {

        //Id
        String newId;
        String className = this.getClass().getSimpleName();
        String firstChar = String.valueOf(className.charAt(0));
        className = className.replaceFirst(firstChar, firstChar.toLowerCase());
        do {
            newId = className + objectLabel;
            objectLabel++;
        } while (ids.contains(newId));
        id = newId;
        ids.add(id);

        this.context = context;
        this.x = x;
        this.y = y;
    }

    public GObject(int x, int y, String id, PApplet context) throws UsedIdException {
        if (ids.contains(id)) throw new UsedIdException("This id was already used!!!");
        this.id = id;
        ids.add(id);
        this.y = y;
        this.x = x;
        this.context = context;
    }

    /**
     * Will draw the GObject into the context
     */
    public abstract void draw();

    /**
     * Return true if the given point belong to the GObject
     *
     * @param x A global x coordinate
     * @param y A global y coordinate
     * @return
     */
    public abstract boolean belong(int x, int y);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void setX(int x);

    public abstract void setY(int y);
}