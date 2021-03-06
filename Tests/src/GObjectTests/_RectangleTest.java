//package GObjectTests;
//
//import P2DPrimitiveWrappers.RectangleWrapper;
//import processing.core.PApplet;
//
//import java.awt.*;
//
//public class _RectangleTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("GObjectTests._RectangleTest");
//    }
//
//    private static final int DW = 640;
//    private static final int DH = 480;
//
//    @Override
//    public void settings() {
//        size(DW, DH);
//    }
//
//    RectangleWrapper rectangle;
//
//    @Override
//    public void setup() {
//        rectangle = new RectangleWrapper((int) (random(width)), (int) (random(height)),
//                (int) (random(50)) + 10, (int) (random(50)) + 10, this);
//        rectangle.setStrokeWeight(random(5) + 1);
//        noCursor();
//    }
//
//    @Override
//    public void drawObjects() {
//        background(255);
//
//        if (rectangle.isThisOverMe(mouseX, mouseY)) {
//            rectangle.setBackgroundColor(Color.RED.getRGB());
//        } else rectangle.setBackgroundColor(Color.BLUE.getRGB());
//
//        rectangle.drawObjects();
//
//        stroke(0);
//        ellipse(mouseX, mouseY, 3, 3);
//
//    }
//
//    @Override
//    public void mousePressed() {
//
//        setup();
//    }
//}