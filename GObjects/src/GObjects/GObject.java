package GObjects;

import processing.core.PApplet;

import java.util.ArrayList;

public abstract class GObject {

    //Static Fields
    private static ArrayList<String> ids = new ArrayList<>();
    private static int objectLabel = 0;

    protected PApplet context;
    protected String id;

    public GObject(PApplet context) {
        this.context = context;

        //Id
        String newId;
        do {
            newId = "gObject" + objectLabel;
            objectLabel++;
        } while (ids.contains(newId));
        id = newId;
    }

    public GObject(String id, PApplet context) throws IdInUseException {
        if (ids.contains(id)) throw new IdInUseException("This id is already on use!!!");
        this.id = id;
        this.context = context;
    }

    /**
     * Will draw the GObject into the context
     */
    public abstract void draw();

    /**
     * Return true if the given point belong to the GObject
     * @param x A global x coordinate
     * @param y A global y coordinate
     * @return
     */
    public abstract boolean belong(int x, int y);

}