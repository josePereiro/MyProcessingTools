package GObjectTests;

import P2DPrimitiveWrappers.PointWrapper;
import processing.core.PApplet;

import java.awt.*;

public class _PointWrapperTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._PointWrapperTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    PointWrapper pointWrapper;

    @Override
    public void setup() {

        pointWrapper = new PointWrapper(width / 2, height / 2, this);
        pointWrapper.setStrokeWeight(150);

    }

    @Override
    public void draw() {
        background(255);
        pointWrapper.draw();

        if (pointWrapper.isThisOverMe(mouseX, mouseY)) {
            pointWrapper.setStrokeColor(Color.BLUE.getRGB());
        } else {
            pointWrapper.setStrokeColor(Color.BLACK.getRGB());
        }
    }
}