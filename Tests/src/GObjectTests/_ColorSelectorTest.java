package GObjectTests;

import PGUIObject.ColorSelector;
import processing.core.PApplet;

public class _ColorSelectorTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._ColorSelectorTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    private ColorSelector colorSelector;
    boolean vertical = true;

    @Override
    public void setup() {
        colorSelector = new ColorSelector(0, (int) (height * 0.8), width,
                (int) (height * 0.2), this);
    }


    @Override
    public void draw() {

        background(colorSelector.getSelectedColor());
        colorSelector.draw();

    }

    @Override
    public void mouseClicked() {
        colorSelector.listeningForMouseClicked(null);
    }
}