package GObjectTests;

import PGUIObject.MovingTextBox;
import processing.core.PApplet;

public class _MovingTextBoxTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._MovingTextBoxTest");
    }

    private static final int DW = 640;
    private static final int DH = 100;

    @Override
    public void settings() {
        size(DW, DH);
    }

    MovingTextBox movingTextBox;

    @Override
    public void setup() {
        movingTextBox = new MovingTextBox("This is a test... This is a test... This is a test... This is a test... This is a test... This is a test...             Of MovingTextBox...",
                0, 0, width, height, this);
        movingTextBox.addHead(true);
    }

    @Override
    public void draw() {
        background(255);
        movingTextBox.setBackgroundColor((int)random(255));
        movingTextBox.draw();
    }
}