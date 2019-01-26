package GObjectTests;

import PGUIObject.Console;
import PGUIObject.PGuiObject;
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
        console.setOnMouseClickedHandler(new PGuiObject.OnMouseClickedHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                console.setFocus(true);
                return true;
            }
        });

    }

    @Override
    public void draw() {
        background(255);
        console.draw();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        console.listeningForMouseWheel(event);
    }

    @Override
    public void mouseClicked() {
        console.listeningForMouseClicked(null);
    }

    @Override
    public void keyTyped() {
        console.listeningForKeyTyped(null);
    }
}