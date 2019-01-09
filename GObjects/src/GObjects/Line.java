package GObjects;

import processing.core.PApplet;

public class Line extends GObject {



    public Line(int x, int y, PApplet context) {
        super(x, y, context);
    }

    public Line(int x, int y, String id, PApplet context) throws IdInUseException {
        super(x, y, id, context);
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean belong(int x, int y) {
        return false;
    }
}
