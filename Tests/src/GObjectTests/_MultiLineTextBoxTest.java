package GObjectTests;

import PGUIObject.MultiLineTextBox;
import PGUIObject.PGuiManager;
import processing.core.PApplet;
import processing.event.MouseEvent;

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

    PGuiManager pGuiManager;
    MultiLineTextBox multiLineTextBox;
    String longText = "jose jose jose jowih wec oc ouirhc oiuc oiauhoiuhvoie oviv oivrsoiu gbvru yvg iueyvct wuycv uwygvf iewhbgsekbcvkajshbc iugf wehbvcsdiuyg wiehcv bekjbhvmdbv isu veshvb ieuwv we";

    @Override
    public void setup() {

        pGuiManager = PGuiManager.createPGuiManager(this);
        multiLineTextBox = new MultiLineTextBox(5, 0, 0, width, height, this);
        multiLineTextBox.setText(longText);
        pGuiManager.addPGUIObject(multiLineTextBox);

    }

    @Override
    public void draw() {
        background(255);
        multiLineTextBox.draw();
        if (mouseX != pmouseX) {
            multiLineTextBox.setMaxVisibleLinesCount((int) map(mouseX, 0, width, 1, 20));
        }
    }

    @Override
    public void mousePressed() {
        multiLineTextBox.setText("blo");
    }

    @Override
    public void mouseWheel(MouseEvent mouseEvent) {
        pGuiManager.listeningForMouseWheel(mouseEvent);
    }
}