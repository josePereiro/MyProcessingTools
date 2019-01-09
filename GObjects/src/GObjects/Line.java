package GObjects;

import processing.core.PApplet;

import java.awt.*;

public class Line extends GObject {

    protected int x1;
    protected int y1;
    protected float weight = 1.0F;
    protected int color = Color.BLACK.getRGB();

    public Line(int x, int y, int x1, int y1, PApplet context) {
        super(x, y, context);
        this.x1 = x1;
        this.y1 = y1;
    }

    public Line(int x, int y, int x1, int y1, String id, PApplet context) throws UsedIdException {
        super(x, y, id, context);
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public void draw() {
        context.stroke(color);
        context.strokeWeight(weight);
        context.line(x, y, x1, y1);
    }

    @Override
    public boolean belong(int x, int y) {

        return false;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
