package GObjectTests;

import PGUIObject.Console;
import PGUIObject.PGuiManager;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class _ConsoleTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._ConsoleTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    PGuiManager pGuiManager;
    Console console;

    @Override
    public void setup() {

        console = new Console(height / 20, 0, 0, width, height, this);
        pGuiManager = PGuiManager.createPGuiManager(this);
        pGuiManager.addPGUIObject(console);

        for (int li = 0; li < 500; li++) {
            console.println(li + "");
        }


    }

    @Override
    public void draw() {
        background(255);
        console.draw();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        pGuiManager.listeningForMouseWheel(event);
    }

    @Override
    public void mouseClicked() {
        pGuiManager.listeningForMouseClicked(null);
    }

    @Override
    public void keyTyped() {
        pGuiManager.listeningForKeyTyped(null);
    }
}