package GObjectTests;

import P2DPrimitiveWrappers.ImageWrapper;
import processing.core.PApplet;
import processing.core.PImage;

public class _ImageBoxTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("GObjectTests._ImageBoxTest");
    }

    private static final int DW = 640;
    private static final int DH = 480;

    @Override
    public void settings() {
        size(DW, DH);
    }

    ImageWrapper imageWrapper;

    @Override
    public void setup() {
        imageWrapper = new ImageWrapper((int) (random(width)), (int) (random(height)),
                (int) (random(200)) + 10, (int) (random(200)) + 10, this);
        PImage image = loadImage("/Users/Pereiro/Pictures/Photo on 1-7-19 at 4.13 PM.jpg");
        imageWrapper.setImage(image);
    }

    @Override
    public void mousePressed() {

        setup();

    }

    @Override
    public void draw() {
        background(255);
        if (imageWrapper.isThisOverMe(mouseX, mouseY)) {
            PImage backgound = new PImage(imageWrapper.getPImage().getImage());
            backgound.resize(width, height);
            image(backgound, 0, 0);
        }
        imageWrapper.draw();

    }
}