package PaintCode;

import processing.core.PApplet;

public class GUIHandler extends PApplet {

    public static void main(String[] args) {
        PApplet.main("PaintCode.GUIHandler");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
        background(255);
    }
}