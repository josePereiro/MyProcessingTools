package Common;

import P2DPrimitiveWrappers.TextWrapper;
import processing.core.PApplet;

public class _TextWrapperTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Common._TextWrapperTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    TextWrapper textWrapper;

    @Override
    public void settings() {
        size(DW, DH);
    }

    @Override
    public void setup() {
        textWrapper = new TextWrapper("Jose",
                width / 2, height / 2, this);
        textWrapper.setTextSize(50);
    }

    @Override
    public void draw() {
        background(255);
        textWrapper.draw();
        if (textWrapper.isThisOverMe(mouseX,mouseY)){
            textWrapper.setFillAlpha(155);
        }else {
            textWrapper.setFillAlpha(255);

        }
    }
}