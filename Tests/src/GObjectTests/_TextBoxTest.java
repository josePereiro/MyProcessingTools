package GObjectTests;

import P2DPrimitiveWrappers.SingleLineTextBox;
import processing.core.PApplet;

public class _TextBoxTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._TextBoxTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    SingleLineTextBox singleLineTextBox;
    String longText = "abcdefghijklmnñopqrstuvWxyz\nABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    @Override
    public void setup() {
        singleLineTextBox = new SingleLineTextBox(longText
                , 20, 120, 100, 20, this);
    }

    @Override
    public void draw() {
        background(255);
        singleLineTextBox.setText(longText);
        singleLineTextBox.setHeight((int) map(mouseY, 0, height, 10, 120));
        singleLineTextBox.setWidth((int) map(mouseX, 0, width, 0, width - singleLineTextBox.getX()));

        singleLineTextBox.fixTextLength();
        singleLineTextBox.fixTextSize();
        singleLineTextBox.fixBox();
        singleLineTextBox.draw();

    }

}