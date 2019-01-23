//package GObjectTests;
//
//import GObjects.Rectangle;
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
//    GObjects.Rectangle rectangle;
//
//    @Override
//    public void setup() {
//        rectangle = new Rectangle((int) (random(width)), (int) (random(height)),
//                (int) (random(50)) + 10, (int) (random(50)) + 10, this);
//        rectangle.setStrokeWeight(random(5) + 1);
//        noCursor();
//    }
//
//    @Override
//    public void draw() {
//        background(255);
//
//        if (rectangle.isThisOverMe(mouseX, mouseY)) {
//            rectangle.setFillColor(Color.RED.getRGB());
//        } else rectangle.setFillColor(Color.BLUE.getRGB());
//
//        rectangle.draw();
//
//        drawBackground(0);
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