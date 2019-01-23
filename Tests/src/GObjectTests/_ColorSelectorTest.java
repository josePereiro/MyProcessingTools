package GObjectTests;

import P2DPrimitiveWrappers.ColorSelector;
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
        if (vertical)
            colorSelector = new ColorSelector(0, (int) (height * 0.8), width,
                    (int) (height * 0.2), this){
                @Override
                public boolean listenForKeyEvent() {
                    if (keyCode == 39)
                        colorSelector.moveRight();
                    else if (keyCode == 37)
                        colorSelector.moveLeft();
                    else if (key == 'v') {
                        vertical = true;
                        setup();
                    } else if (key == 'h') {
                        vertical = false;
                        setup();
                    }
                    return true;
                }

                @Override
                public boolean listenForMouseEvent() {
                    colorSelector.setSelectedColor(mouseX, mouseY);
                    colorSelector.setSelectedColor(colorSelector.getSelectedColor());
                    return true;
                }
            };
        else {
            colorSelector = new ColorSelector(0, 0, (int) (width * 0.2), height, this){
                @Override
                public boolean listenForKeyEvent() {
                    if (keyCode == 39)
                        colorSelector.moveRight();
                    else if (keyCode == 37)
                        colorSelector.moveLeft();
                    else if (key == 'v') {
                        vertical = true;
                        setup();
                    } else if (key == 'h') {
                        vertical = false;
                        setup();
                    }
                    return true;
                }

                @Override
                public boolean listenForMouseEvent() {
                    colorSelector.setSelectedColor(mouseX, mouseY);
                    colorSelector.setSelectedColor(colorSelector.getSelectedColor());
                    return true;
                }
            };
            colorSelector.vertical(false);
        }
    }

    @Override
    public void draw() {

        background(colorSelector.getSelectedColor());

        colorSelector.draw();

    }

    @Override
    public void keyPressed() {
        colorSelector.listenForKeyEvent();

    }

    @Override
    public void mousePressed() {
        colorSelector.listenForMouseEvent();
    }
}