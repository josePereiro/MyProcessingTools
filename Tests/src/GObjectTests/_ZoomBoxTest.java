package GObjectTests;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.P2DPrimitiveWrapper;
import P2DPrimitiveWrappers.RectangleWrapper;
import PGUIObject.ZoomBox;
import processing.core.PApplet;

import java.util.ArrayList;

public class _ZoomBoxTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._ZoomBoxTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    ZoomBox zoomBox;
    ArrayList<P2DPrimitiveWrapper> p2DPrimitiveWrappers;

    @Override
    public void setup() {
        zoomBox = new ZoomBox(10, 10, 50, 50, this);
        //noCursor();
        zoomBox.setFactor(4.5F);
        p2DPrimitiveWrappers = new ArrayList<>();
        int rx, ry, rs;
        P2DPrimitiveWrapper p2DPrimitiveWrapper;
        for (int o = 0; o < 50; o++) {
            rx = (int) random(width);
            ry = (int) random(height);
            rs = (int) random(50);
            if (random(5) > 2.5) {
                p2DPrimitiveWrapper = new RectangleWrapper(rx, ry, rs, rs, this);
            } else {
                p2DPrimitiveWrapper = new EllipseWrapper(rx, ry, rs, rs, this);
            }
            p2DPrimitiveWrappers.add(p2DPrimitiveWrapper);
        }
    }

    @Override
    public void draw() {
        background(255);
        for (P2DPrimitiveWrapper p2DPrimitiveWrapper : p2DPrimitiveWrappers) {
            p2DPrimitiveWrapper.draw();
        }
        zoomBox.magnifyCenteredArea(mouseX, mouseY);
        zoomBox.draw();
        zoomBox.setFactor(map(mouseX, 0, width, 0.5F, 5));
        zoomBox.setWidth((int) map(mouseY, 0, height, 50, 200));
        zoomBox.setHeight((int) map(mouseY, 0, height, 50, 200));
    }
}