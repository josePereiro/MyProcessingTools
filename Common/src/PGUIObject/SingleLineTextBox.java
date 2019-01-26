package PGUIObject;

import Common.Tools;
import processing.core.PApplet;

public class SingleLineTextBox extends PGuiObject {

    private String text;
    private float textSize;
    private float margin;
    private float textY, textX;

    /**
     * Is a simple rectangle with a text inside of it. It was tuned with the default Processing font.
     * So, if it is ugly for you, FIXED!!!
     * It do not allow multiples textBoxes so it will delete the {@code "\n"} chars...
     *
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     * @param context
     */
    public SingleLineTextBox(String text, float x, float y,
                             float width, float height, PApplet context) {
        super(x, y, width, height, context);
        this.text = text.replaceAll("\n", "");
        margin = height * 0.25F;
        if (margin < 1) {
            margin = 1;
        }
        textY = y + height - margin;
        textSize = height * 0.70F;
        textX = x + margin;
        setFocusable(false);
    }

    @Override
    public void draw() {
        super.draw();

        //Tools
        context.fill(strokeColor);
        context.textSize(textSize);
        context.text(text, textX, textY);

    }

    //region IO methods

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text.replaceAll("\n", "");
    }
    //endregion

    //region Layout Methods ....

    /**
     * Cut {@code text}, if necessary, to make sure it fit in the {@code SingleLineTextBox} box.
     * It can even set {@code text} to empty!!!
     */
    public void fixTextLength() {
        context.textSize(textSize);
        text = Tools.fixTextLength(text, width - 2 * margin, context);
    }

    /**
     * Change the textSize to make sure it fit in the box height
     */
    public void fixTextSize() {
        textSize = height * 0.70F;
    }

    /**
     * Change {@code width} and {@code height} to make the box
     * perfect to the text!!!
     */
    public void fixBox() {
        context.textSize(textSize);
        height = textSize * 1.30F;
        margin = height * 0.25F;
        width = context.textWidth(text) + 2 * margin;
        textX = x + margin;
        textY = y + height - margin;
    }
    //endregion

    //region Getters and Setters
    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getTextY() {
        return textY;
    }

    public void setTextY(float textY) {
        this.textY = textY;
    }

    public float getTextX() {
        return textX;
    }

    public void setTextX(float textX) {
        this.textX = textX;
    }
    //endregion


}
