package Tests;

import GObjects.Line;
import processing.core.PApplet;

public class _LineDrawTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Tests._LineDrawTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    Line line;
    float nc = 0.0F;

    @Override
    public void setup() {
        line = new Line(width / 4, height / 4, 3*width / 4, 3*height / 4, this);
    }

    @Override
    public void draw() {
        background(255);
        line.setX(line.getX() + (int) (PApplet.map(noise(nc + 10), 0, 1, -3, 3)));
        line.setX1(line.getX1() + (int) (PApplet.map(noise(nc + 20), 0, 1, -3, 3)));
        line.setY(line.getY() + (int) (PApplet.map(noise(nc + 30), 0, 1, -3, 3)));
        line.setY1(line.getY1() + (int) (PApplet.map(noise(nc + 40), 0, 1, -3, 3)));
        line.setWeight(noise(nc) * 10);
        line.setColor((int) (noise(nc + 100) * 255));
        line.draw();

        nc += 0.05F;
    }
}
