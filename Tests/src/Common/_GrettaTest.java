//package Common;
//
//import processing.core.PApplet;
//import processing.core.PImage;
//import processing.core.PVector;
//import processing.event.MouseEvent;
//
//public class _GrettaTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("Common._GrettaTest");
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
//    PVector topLeftCorner;
//    int draggedDelay;
//    PImage backGround;
//
//    @Override
//    public void setup() {
//        background(255);
//        noFill();
//        drawStroke(0);
//    }
//
//    @Override
//    public void mouseDragged() {
//        if (mouseX != pmouseY || mouseY != pmouseY) {
//            draggedDelay = 25;
//            cursor(CROSS);
//            if (topLeftCorner == null) {
//                topLeftCorner = new PVector(mouseX, mouseY);
//                backGround = get();
//            } else {
//                background(backGround);
//                rect(topLeftCorner.x, topLeftCorner.y, mouseX - topLeftCorner.x, mouseY - topLeftCorner.y);
//            }
//        }
//
//    }
//
//    @Override
//    public void drawObjects() {
//        if (draggedDelay <= 0) {
//            cursor(ARROW);
//            draggedDelay = 0;
//            topLeftCorner = null;
//        } else {
//            draggedDelay--;
//        }
//    }
//}