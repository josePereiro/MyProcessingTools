package GObjectTests;

import PGUIObject.Console;
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

    Console console;

    @Override
    public void setup() {

        console = new Console(height / 20, 0, 0, width, height, this);
    }

    @Override
    public void draw() {
        background(255);
        console.draw();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        if (console.listeningForMouseWheel(event)) {
            return;
        }
    }

    @Override
    public void keyPressed() {
        console.setFocus(true);
        console.listenForKeyPressed(null);
    }

}