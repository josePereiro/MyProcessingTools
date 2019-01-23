package GObjectTests;

import P2DPrimitiveWrappers.EllipseWrapper;
import processing.core.PApplet;

import java.awt.*;

public class _EllipseTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._EllipseTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    EllipseWrapper ellipseWrapper;

    @Override
    public void setup() {
        ellipseWrapper = new EllipseWrapper((int) (random(width)), (int) (random(height)),
                (int) (random(50)) + 10, (int) (random(50)) + 10, this);
        ellipseWrapper.setStrokeWeight(random(5) + 1);
        noCursor();
    }

    @Override
    public void draw() {
        background(255);

        if (ellipseWrapper.isThisOverMe(mouseX, mouseY)) {
            ellipseWrapper.setBackgroundColor(Color.RED.getRGB());
        } else ellipseWrapper.setBackgroundColor(Color.BLUE.getRGB());

        ellipseWrapper.draw();

        fill(0);
        ellipse(mouseX, mouseY, 3, 3);

    }

    @Override
    public void mousePressed() {
        setup();
    }
}