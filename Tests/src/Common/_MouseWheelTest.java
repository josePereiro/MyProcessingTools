//package Common;
//
//import processing.core.PApplet;
//import processing.event.MouseEvent;
//
//public class _MouseWheelTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("Common._MouseWheelTest");
//    }
//
//    private static final int DW = 640;
//    private static final int DH = 480;
//
//    int wheelValue = 0;
//
//    @Override
//    public void settings() {
//        size(DW, DH);
//    }
//
//    @Override
//    public void setup() {
//    }
//
//    @Override
//    public void mouseWheel(MouseEvent event){
//        wheelValue += event.getCount();
//    }
//
//    @Override
//    public void draw() {
//        background(255);
//        drawBackground(0);
//        text("WheelValue: " + wheelValue, 20,20);
//    }
//}