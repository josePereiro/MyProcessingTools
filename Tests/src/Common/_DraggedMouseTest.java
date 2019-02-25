package Common;

import P2DPrimitiveWrappers.EllipseWrapper;
import processing.core.PApplet;

public class _DraggedMouseTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Common._DraggedMouseTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    EllipseWrapper ellipseWrapper;

    @Override
    public void settings() {
        size(DW, DH);
    }

    @Override
    public void setup() {
        ellipseWrapper = new EllipseWrapper(width / 2, height / 2,
                width / 4, height / 4, this);
    }

    @Override
    public void draw() {
        background(255);
        ellipseWrapper.draw();
    }

    @Override
    public void keyPressed() {

        System.out.println(keyCode);

    }

    @Override
    public void mouseDragged() {
        if (ellipseWrapper.isThisOverMe(mouseX, mouseY)) {
            ellipseWrapper.setX(mouseX);
            ellipseWrapper.setY(mouseY);
        }
    }
}