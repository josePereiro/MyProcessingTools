//package Common;
//
//import PGUIObject.SingleLineTextBox;
//import processing.core.PApplet;
//
//public class _ScannerTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("Common._ScannerTest");
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
//    Scanner scanner;
//    SingleLineTextBox buffer;
//    SingleLineTextBox lastLine;
//
//    @Override
//    public void setup() {
//
//        scanner = new Scanner();
//        buffer = new SingleLineTextBox("", 0, 0, width, height/10, this);
//        lastLine = new SingleLineTextBox("", 0, buffer.getHeight(), width, height/10, this);
//
//    }
//
//    @Override
//    public void draw() {
//        background(255);
//        lastLine.draw();
//        buffer.draw();
//    }
//
//    @Override
//    public void keyPressed() {
//
//        scanner.scan(key);
//        buffer.setText(scanner.getBufferedLine());
//        lastLine.setText(scanner.getLastLine());
//
//    }
//}