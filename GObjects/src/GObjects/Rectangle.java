package GObjects;

import processing.core.PApplet;

public class Rectangle extends GObject {


    int width;
    int height;

    public Rectangle(int x, int y, int width, int height, PApplet context) {
        super(x, y, context);
        this.width = width;
        this.height = height;
    }

    public Rectangle(int x, int y, int width, int height, String id, PApplet context) throws UsedIdException {
        super(x, y, id, context);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean belong(int x, int y) {
        return false;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }
}
