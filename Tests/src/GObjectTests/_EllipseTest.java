package GObjectTests;

import P2DPrimitiveWrappers.EllipseWrapper;
import processing.core.PApplet;

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
        ellipseWrapper.draw();
    }

    @Override
    public void draw() {
        if (ellipseWrapper.isThisOverMe(mouseX, mouseY)) {
            ellipse(mouseX,mouseY,1,1);
        }

//
//
//        fill(0);
//        ellipse(mouseX, mouseY, 3, 3);

    }

    @Override
    public void mousePressed() {
        setup();
    }
}