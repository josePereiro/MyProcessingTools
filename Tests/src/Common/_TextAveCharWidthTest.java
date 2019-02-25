//package Common;
//
//import processing.core.PApplet;
//
//public class _TextAveCharWidthTest extends PApplet {
//
//    public static void main(String[] args) {
//        PApplet.main("Common._TextAveCharWidthTest");
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
//
//
//    }
//
//    @Override
//    public void drawObjects() {
//        background(255);
//        String longText = "Given the set of allowed metabolic states (Ps), the dependency of the " +
//                "cellular growth rate with metabolic fluxes (λs(v)), and the average growth... ";
//        String text = longText.substring(0,(int)map(mouseX,0,width,1,longText.length()));
//        int textX = 20;
//        int textY = 50;
//        drawBackground(0);
//        drawStroke(0);
//        text(text, textX, textY);
//        line(textX, textY + 20, textX + textWidth(text), textY + 20);
//        line(textX, textY + 40, textX + Tools.getAverageCommonCharsWidth(this) * text.length(), textY + 40);
//        line(textX, textY + 60, textX + Tools.getAverageCharsWidth(longText,this) * text.length(), textY + 60);
//
//    }
//}