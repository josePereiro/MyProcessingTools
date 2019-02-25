package GObjectTests;

import P2DPrimitiveWrappers.LineWrapper;
import processing.core.PApplet;

import java.awt.*;

public class _LineDrawTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._LineDrawTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    LineWrapper line;

    @Override
    public void setup() {
        line = new LineWrapper((int) (random(width)), (int) (random(height)), (int) (random(width)), (int) (random(height)), this);
        line.setStrokeWeight(random(50) + 5);
        noCursor();
    }

    @Override
    public void mousePressed() {
        setup();
    }

    @Override
    public void draw() {
        background(255);
        line.draw();
        if (line.isThisOverMe(mouseX, mouseY)) {
            line.setStrokeColor(Color.BLUE.getRGB());
        }else
            line.setStrokeColor(Color.BLACK.getRGB());

        //Cursor
        noStroke();
        fill(Color.RED.getRGB());
        ellipse(mouseX,mouseY,2,2);
    }
}
