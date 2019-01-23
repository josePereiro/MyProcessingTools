package GObjectTests;

import P2DPrimitiveWrappers.GuidedBoard;
import processing.core.PApplet;

public class _DrawBoardTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._DrawBoardTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    GuidedBoard guidedBoard;

    @Override
    public void setup() {

        guidedBoard = new GuidedBoard(5, 5, width - 10, height - 10,
                15, 15, this);
        //(int) (width * 0.1), (int) (height * 0.1), this);
        try {
            guidedBoard.setBackgroundImage(loadImage("/Users/Pereiro/Pictures/Screenshots/Friends - 901 - The one where no one proposes-0001.png"));
        } catch (Exception ignored) {
        }


    }

    @Override
    public void keyPressed() {
        if (key == 'i')
            guidedBoard.drawBackgroundImage(!guidedBoard.isDrawBackgroundImage());
        else if (key == 'v')
            guidedBoard.drawVGuides(!guidedBoard.isDrawVGuides());
        else if (key == 'h')
            guidedBoard.drawHGuides(!guidedBoard.isDrawHGuides());
        else if (key == 'x')
            guidedBoard.setDx(guidedBoard.getDx() + 1);
        else if (key == 'z')
            guidedBoard.setDx(guidedBoard.getDx() - 1);
        else if (key == 'y')
            guidedBoard.setDy(guidedBoard.getDy() + 1);
        else if (key == 't')
            guidedBoard.setDy(guidedBoard.getDy() - 1);


    }

    @Override
    public void draw() {

        background(255);
        guidedBoard.draw();

        //Closer Point
        fill(0);
        noStroke();
        ellipse(guidedBoard.getCloserGuideX(mouseX), guidedBoard.getCloserGuideY(mouseY),
                3, 2);
    }
}