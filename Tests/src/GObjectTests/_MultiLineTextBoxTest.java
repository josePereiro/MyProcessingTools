package GObjectTests;

import P2DPrimitiveWrappers.MultiLineTextBox;
import processing.core.PApplet;

public class _MultiLineTextBoxTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._MultiLineTextBoxTest");
    }

    private static final int DW = 960;
    private static final int DH = 720;

    @Override
    public void settings() {
        size(DW, DH);
    }

    MultiLineTextBox multiLineTextBox;
    String longText = "bla\nbla\nbla\nbla\nbla\nbla\nbla\nbla\nbla\n";

    @Override
    public void setup() {
        multiLineTextBox = new MultiLineTextBox(5, 0, 0, width, height, this);
        multiLineTextBox.setText(longText);

    }

    @Override
    public void draw() {
        background(255);
        multiLineTextBox.draw();
        if (mouseX != pmouseX)
            multiLineTextBox.setMaxVisibleLinesCount((int) map(mouseX, 0, width, 1, 20));
    }

    @Override
    public void mousePressed() {
        multiLineTextBox.addNewLine("blo");

    }
}