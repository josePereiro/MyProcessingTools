//package Common;
//
//import processing.core.PApplet;
//
//public class _FixTextLenghtTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("Common._FixTextLenghtTest");
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
//    @Override
//    public void setup() {
//    }
//
//    @Override
//    public void draw() {
//        background(255);
//
//        String fullText = "qiuxhiquwtxfuatcx qwiuy9834rh34 98347fgew67cg a87wtcgq3fk3nfbe9dycgq78wtdf q3fb q39ycgq9 w7ecg q3yfw49g weuv a087eq9723f q38fgh -i9 fa-9hdc a97sydgc75rsdcfq35efg 3f94ugh 08e 8awyc 0wyf 0q8f qw0e ca9wgf q3ufhq oweygc 9qf";
//        int desiredWidth = 928;
//        textSize(27);
//        String line = Tools.fixTextLength(fullText, desiredWidth, this);
//        System.out.println("Buffer: " + fullText);
//        System.out.println("line: " + line);
//        System.out.println("Tools real w: " + textWidth(line));
//        System.out.println("Desired w: " + desiredWidth);
//
//
//    }
//}