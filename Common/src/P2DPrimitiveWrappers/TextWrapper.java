package P2DPrimitiveWrappers;

import processing.core.PApplet;

public class TextWrapper extends P2DPrimitiveWrapper {

    private String text;
    private float textSize;
    private float textWidth;

    public TextWrapper(String text, float x, float y, PApplet context) {
        super(x, y, context);
        this.text = text;
        textSize = 15;
        updateTextWidth();
        fillColor = 0;
    }

    @Override
    public void draw() {
        draw(x, y);
    }

    @Override
    public void draw(float x, float y) {
        if (strokeEnable) {
            context.fill(fillColor,fillAlpha);
            context.textSize(textSize);
            context.text(text, x, y);
        }
    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return x >= this.x && x <= this.x + textWidth &&
                y <= this.y && y >= this.y - textSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateTextWidth();
    }

    private void updateTextWidth() {
        context.textSize(textSize);
        textWidth = context.textWidth(text);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        updateTextWidth();
    }

    public float getTextWidth() {
        return textWidth;
    }
}
