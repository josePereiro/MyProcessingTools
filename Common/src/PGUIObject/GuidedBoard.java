package PGUIObject;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class GuidedBoard extends PGuiObject {

    //Fields
    private float dx;
    private float dy;
    private float[] guidesXs, guidesYs;

    //GUI
    private float guidesWeight = 0.1F;
    private int guidesAlpha = 120;
    private boolean drawVGuides = false;
    private boolean drawHGuides = false;
    private boolean drawBackgroundImage = false;
    private int backgroundImageAlpha = 120;
    private PImage backgroundImage;


    public GuidedBoard(float x, float y, float width, float height, float dx, float dy, PApplet context) {
        super(x, y, width, height, context);
        this.dx = dx;
        this.dy = dy;
        updateGuidesXs();
        updateGuidesYs();
    }

    @Override
    public void draw() {

        //BackgroundImage
        if (drawBackgroundImage && backgroundImage != null) {
            context.image(backgroundImage, x, y);
            context.fill(255, backgroundImageAlpha);
            context.rect(x, y, width, height);
        } else {
            super.draw();
        }

        //Guides
        if (drawVGuides) {
            context.stroke(strokeColor, guidesAlpha);
            context.strokeWeight(guidesWeight);
            float lastY = y + height;
            for (float guidesX : guidesXs) {
                context.line(guidesX, y, guidesX, lastY);
            }
        }
        if (drawHGuides) {
            context.stroke(strokeColor, guidesAlpha);
            context.strokeWeight(guidesWeight);
            float lastX = x + width;
            for (float guidesY : guidesYs) {
                context.line(x, guidesY, lastX, guidesY);
            }
        }


    }

    public float getCloserGuideX(float x) {
        int xi = Math.round((x - this.x) / dx);
        if (xi <= 0) {
            return guidesXs[0];
        }

        if (xi < guidesXs.length - 1) {
            return x - guidesXs[xi] < guidesXs[xi + 1] - x ? guidesXs[xi] : guidesXs[xi + 1];
        }
        return guidesXs[guidesXs.length - 1];
    }

    public float getCloserGuideY(float y) {
        int yi = Math.round((y - this.y) / dy);
        if (yi <= 0) {
            return guidesYs[0];
        }
        if (yi < guidesYs.length - 1) {
            return y - guidesYs[yi] < guidesYs[yi + 1] - y ? guidesYs[yi] : guidesYs[yi + 1];
        }
        return guidesYs[guidesYs.length - 1];
    }

    public Point.Float getCloserGuidedPoint(float x, float y) {
        return new Point.Float(getCloserGuideX(x), getCloserGuideY(y));
    }

    public void setDx(float dx) {
        if (dx < 1) {
            dx = 1;
        }
        this.dx = dx;
        updateGuidesXs();
    }

    public void setDy(float dy) {
        if (dy < 1) {
            dy = 1;
        }
        this.dy = dy;
        updateGuidesYs();
    }

    public void drawVGuides(boolean b) {
        drawVGuides = b;
    }

    public void drawHGuides(boolean b) {
        drawHGuides = b;
    }

    public void drawBackgroundImage(boolean b) {
        drawBackgroundImage = b;
    }

    public void setBackgroundImageAlpha(int backgroundImageAlpha) {
        this.backgroundImageAlpha = backgroundImageAlpha;
    }

    public boolean isDrawVGuides() {
        return drawVGuides;
    }

    public boolean isDrawHGuides() {
        return drawHGuides;
    }

    public boolean isDrawBackgroundImage() {
        return drawBackgroundImage;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public PImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(PImage backgroundImage) {
        this.backgroundImage = backgroundImage;
        backgroundImage.resize((int) width, (int) height);
    }

    private void updateGuidesYs() {
        //Ys
        guidesYs = new float[(int) (height / dy) + 1];
        for (int yi = 0; yi < guidesYs.length; yi++) {
            guidesYs[yi] = y + dy * yi;
        }
    }

    private void updateGuidesXs() {
        //Xs
        guidesXs = new float[(int) (width / dx + 1)];
        for (int xi = 0; xi < guidesXs.length; xi++) {
            guidesXs[xi] = x + dx * xi;
        }

    }

    private float getRelativeX(int contextX) {
        return (contextX - x) / width;
    }

    private float getRelativeY(int contextY) {
        return (contextY - y) / height;
    }

    private float getRelativeCloserGuideY(int contextY) {
        return getCloserGuideY(contextY) / height;
    }

    private float getRelativeCloserGuideX(int contextX) {
        return getCloserGuideX(contextX) / width;
    }

    public float getGuidesWeight() {
        return guidesWeight;
    }

    public void setGuidesWeight(float guidesWeight) {
        this.guidesWeight = guidesWeight;
    }

    public int getGuidesAlpha() {
        return guidesAlpha;
    }

    public void setGuidesAlpha(int guidesAlpha) {
        this.guidesAlpha = guidesAlpha;
    }

    public int getBackgroundImageAlpha() {
        return backgroundImageAlpha;
    }

}
