package GObjectTests;

import PGUIObject.ColorSelector;
import PGUIObject.PGuiManager;
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

    private PGuiManager pGuiManager;
    private ColorSelector colorSelector;
    private ColorSelector colorSelector2;

    @Override
    public void setup() {
        colorSelector = new ColorSelector(0, (int) (height * 0.8), width,
                (int) (height * 0.2), this);
        colorSelector2 = new ColorSelector(0, 0, width, colorSelector.getHeight(), this );
        pGuiManager = PGuiManager.createPGuiManager(this);
        pGuiManager.addPGUIObject(colorSelector);
        colorSelector.setOnSelectedColorChanged(new ColorSelector.OnSelectedColorChanged() {
            @Override
            public boolean handleEvent(ColorSelector colorSelector, int newColor) {
                System.out.println("Color Selected changed!!");
                colorSelector2.setSelectedColor(colorSelector.getSelectedColor());
                return false;
            }
        });
    }


    @Override
    public void draw() {

        background(colorSelector.getSelectedColor());
        colorSelector.draw();
        colorSelector2.draw();

    }

    @Override
    public void mouseClicked() {
        pGuiManager.listeningForMouseClicked(null);
    }
}