package PGUIObject;

import P2DPrimitiveWrappers.RectangleWrapper;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.awt.*;

public class ColorSelector extends PGuiObject {

    private static final OnMouseClickedHandler DEFAULT_ON_MOUSE_CLICKED_HANDLER = new OnMouseClickedHandler() {
        @Override
        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
            ColorSelector colorSelector = (ColorSelector) pGuiObject;
            for (int sc = 0; sc < colorSelector.colorsRectangles.length; sc++) {
                if (colorSelector.colorsRectangles[sc].isThisOverMe(colorSelector.context.mouseX,
                        colorSelector.context.mouseY)) {
                    colorSelector.setSelectedColor(colorSelector.colors[sc]);
                    return true;
                }
            }
            return false;
        }
    };
    private int[] colors;
    private RectangleWrapper[] colorsRectangles;
    private int selectedColor, selectedColorIndex;
    private boolean vertical;
    private OnSelectedColorChanged onSelectedColorChanged;
    private boolean selectedColorChanged;


    public ColorSelector(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);

        //Defaults
        setStrokeEnable(false);
        setFillEnable(false);

        //Colors
        setColors();

        selectedColorChanged = false;
        vertical = true;

        //colorRectangles
        setColorsRectangles();

        //selectedColor
        selectedColorIndex = 0;
        selectedColor = colors[selectedColorIndex];

        setOnMouseClickedHandler(DEFAULT_ON_MOUSE_CLICKED_HANDLER);
        setFocusable(false);

    }

    private void setColors() {
        colors = new int[10];
        colors[0] = new Color(255, 0, 0).getRGB();//Red
        colors[1] = new Color(244, 141, 52).getRGB();//Orange
        colors[2] = new Color(244, 244, 0).getRGB();//Yellow
        colors[3] = new Color(105, 202, 60).getRGB();//Green
        colors[4] = new Color(24, 210, 233).getRGB();//Blue
        colors[5] = new Color(0, 102, 203).getRGB();//Indigo
        colors[6] = new Color(151, 53, 152).getRGB();//Violet
        colors[7] = new Color(255, 255, 255).getRGB();//White
        colors[8] = new Color(122, 122, 122).getRGB();//Grey
        colors[9] = new Color(0, 0, 0).getRGB();//Black
    }

    private void setColorsRectangles() {
        colorsRectangles = new RectangleWrapper[colors.length];
        RectangleWrapper colorRectangle;
        if (vertical) {
            float colorRectangleW = width / colors.length;
            for (int c = 0; c < colorsRectangles.length; c++) {
                colorRectangle = new RectangleWrapper(x + c * colorRectangleW, y,
                        colorRectangleW, height, context);
                colorRectangle.setStrokeWeight(1);
                colorRectangle.setFillColor(colors[c]);
                colorsRectangles[c] = colorRectangle;
            }
        } else {
            float colorRectangleH = height / colors.length;
            for (int c = 0; c < colorsRectangles.length; c++) {
                colorRectangle = new RectangleWrapper(x, y + c * colorRectangleH,
                        width, colorRectangleH, context);
                colorRectangle.setStrokeWeight(1);
                colorRectangle.setFillColor(colors[c]);
                colorsRectangles[c] = colorRectangle;
            }
        }
    }

    @Override
    public void draw() {

        //background
        super.draw();

        //colors
        for (int ci = 0; ci < colorsRectangles.length; ci++) {
            colorsRectangles[ci].draw();
        }

        //selectedColor
        RectangleWrapper selectedColorRectangle = colorsRectangles[selectedColorIndex];
        context.noFill();
        context.strokeWeight(selectedColorRectangle.getStrokeWeight() * 3);
        context.stroke(0);
        context.rect(selectedColorRectangle.getX(), selectedColorRectangle.getY(),
                selectedColorRectangle.getWidth(), selectedColorRectangle.getHeight());
        context.strokeWeight(selectedColorRectangle.getStrokeWeight());
        context.stroke(255);
        context.rect(selectedColorRectangle.getX(), selectedColorRectangle.getY(),
                selectedColorRectangle.getWidth(), selectedColorRectangle.getHeight());

    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int color) {
        selectedColorChanged = false;
        if (color != selectedColor) {
            for (int ci = 0; ci < colors.length; ci++) {
                if (color == colors[ci]) {
                    selectedColorIndex = ci;
                    selectedColor = color;
                    selectedColorChanged = true;
                    if (onSelectedColorChanged != null) {
                        onSelectedColorChanged.handleEvent(this, selectedColor);
                    }
                }
            }
        }
    }

    public void vertical(boolean vertical) {
        this.vertical = vertical;
        setColorsRectangles();
    }

    public void moveRight() {
        if (selectedColorIndex < colors.length - 1) {
            selectedColorIndex++;
        } else {
            selectedColorIndex = 0;
        }
        selectedColor = colors[selectedColorIndex];
    }

    public void moveLeft() {
        if (selectedColorIndex > 1) {
            selectedColorIndex--;
        } else {
            selectedColorIndex = colors.length - 1;
        }
        selectedColor = colors[selectedColorIndex];
    }

    public boolean isSelectedColorChanged() {
        return selectedColorChanged;
    }

    public void setSelectedColorChanged(boolean selectedColorChanged) {
        this.selectedColorChanged = selectedColorChanged;
    }

    public OnSelectedColorChanged getOnSelectedColorChanged() {
        return onSelectedColorChanged;
    }

    public void setOnSelectedColorChanged(OnSelectedColorChanged onSelectedColorChanged) {
        this.onSelectedColorChanged = onSelectedColorChanged;
    }

    public abstract static class OnSelectedColorChanged {

        public abstract boolean handleEvent(ColorSelector colorSelector, int newColor);
    }

}
