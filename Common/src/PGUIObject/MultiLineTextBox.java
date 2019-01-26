package PGUIObject;

import Common.Tools;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MultiLineTextBox extends PGuiObject {

    private static final OnMouseWheelHandler DEFAULT_ON_MOUSE_WHEEL_HANDLER = new OnMouseWheelHandler() {

        int lastSign = 1;

        @Override
        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

            int wheelCount = event.getCount();
            MultiLineTextBox multiLineTextBox = (MultiLineTextBox) pGuiObject;
            if (wheelCount > 0) {
                for (int t = 0; lastSign > 0 && t < wheelCount &&
                        t < multiLineTextBox.getMaxVisibleLinesCount(); t++) {
                    multiLineTextBox.scrollDown();
                }
                lastSign = 1;
            } else if (wheelCount < 0) {
                wheelCount *= -1;
                for (int t = 0; lastSign < 0 && t < wheelCount &&
                        t < multiLineTextBox.getMaxVisibleLinesCount(); t++) {
                    multiLineTextBox.scrollUp();
                }
                lastSign = -1;
            }
            return true;
        }
    };
    private int maxVisibleLinesCount;
    private final ArrayList<SingleLineTextBox> textBoxes;
    private final ArrayList<String> textAsLines;
    private final StringBuilder textSB;
    private int firstVisibleLine;
    private final char forceNewLineChar = '\n';


    public MultiLineTextBox(int MaxVisibleLinesCount,
                            float x, float y, float width,
                            float height, PApplet context) {
        super(x, y, width, height, context);
        this.maxVisibleLinesCount = MaxVisibleLinesCount;
        firstVisibleLine = 0;
        textSB = new StringBuilder();
        textBoxes = new ArrayList<>();
        textAsLines = new ArrayList<>();
        setUpTextBoxes();
        setOnMouseWheelHandler(DEFAULT_ON_MOUSE_WHEEL_HANDLER);
    }

    private void setUpTextBoxes() {
        float textBoxH = height / maxVisibleLinesCount;
        textBoxes.clear();
        SingleLineTextBox textBox;
        for (int l = 0; l < maxVisibleLinesCount; l++) {
            textBox = new SingleLineTextBox("", x, y + l * textBoxH, width, textBoxH, context);
            textBox.drawStroke(false);
            textBox.drawBackground(false);
            textBoxes.add(textBox);
        }
    }

    private void setUpTextLines() {

        //Setting context
        context.textSize(textBoxes.get(0).getTextSize());

        //desiredTextWidth
        float desiredTextWidth = width - 2 * textBoxes.get(0).getMargin();

        //TextLines
        textAsLines.clear();

        //Paragraphs
        ArrayList<String> textParagraphs = splitText();
        StringBuilder paragraphBuffer;
        String line;
        int lastSpaceIndex;
        for (String paragraph : textParagraphs) {

            if (paragraph.equals("")) {
                textAsLines.add(paragraph);
            }

            //Current paragraph textSB
            paragraphBuffer = new StringBuilder(paragraph);
            while (paragraphBuffer.length() > 0) {

                //fix line
                line = Tools.fixTextLength(paragraphBuffer.toString(), desiredTextWidth, context);

                //check end line
                if (line.length() != paragraphBuffer.length()) {

                    //if it brakes a world
                    if (paragraphBuffer.charAt(line.length()) != ' ') {

                        //Reject last peace of world
                        lastSpaceIndex = line.lastIndexOf(' ');
                        if (lastSpaceIndex > 0) {
                            line = line.substring(0, lastSpaceIndex + 1);
                        }

                    } else
                    //Delete last space char
                    {
                        paragraphBuffer.deleteCharAt(line.length());
                    }
                }

                //Adding line
                textAsLines.add(line);

                //deleting added line
                paragraphBuffer.delete(0, line.length());
            }
        }
    }

    private ArrayList<String> splitText() {
        ArrayList<String> splitText = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        char currentChar;
        for (int ci = 0; ci < textSB.length(); ci++) {
            currentChar = textSB.charAt(ci);
            if (currentChar != forceNewLineChar) {
                buffer.append(currentChar);
            } else {
                splitText.add(buffer.toString());
                buffer.delete(0, buffer.length());
            }
        }
        splitText.add(buffer.toString());

        return splitText;
    }

    private void putLinesInTextBoxes() {

        //Cleaning
        for (SingleLineTextBox textBox : textBoxes) {
            textBox.setText("");
        }

        for (int c = firstVisibleLine; c < textAsLines.size() && c - firstVisibleLine < textBoxes.size(); c++) {
            textBoxes.get(c - firstVisibleLine).setText(textAsLines.get(c));
        }
    }

    public void scrollDown() {
        if (firstVisibleLine < textAsLines.size() - maxVisibleLinesCount) {
            firstVisibleLine++;
            putLinesInTextBoxes();
        }
    }

    public void scrollUp() {
        if (firstVisibleLine > 0) {
            firstVisibleLine--;
            putLinesInTextBoxes();
        }
    }

    public void setText(String text) {
        textSB.delete(0, textSB.length());
        textSB.append(text);
        setUpTextLines();
        firstVisibleLine = 0;
        putLinesInTextBoxes();
    }

    @Override
    public void draw() {
        super.draw();

        for (SingleLineTextBox textBox : textBoxes)
            textBox.draw();

        //scroll
        if (textAsLines.size() > maxVisibleLinesCount) {
            float margin = textBoxes.get(0).getMargin();
            context.ellipse(x + width,
                    y + height * firstVisibleLine / (textAsLines.size() - maxVisibleLinesCount),
                    margin, margin);
        }

    }

    @Override
    public void drawFocus() {

    }

    public int getMaxVisibleLinesCount() {
        return maxVisibleLinesCount;
    }

    public void setMaxVisibleLinesCount(int MaxVisibleLinesCount) {
        this.maxVisibleLinesCount = MaxVisibleLinesCount;
        setUpTextBoxes();
        setUpTextLines();
        firstVisibleLine = 0;
        putLinesInTextBoxes();
    }

    public String getText() {
        return textSB.toString();
    }

    public void addText(String newText) {
        textSB.append(newText);
        setUpTextLines();
        putLinesInTextBoxes();
    }

    public void addNewLine(String newLine) {
        addText("\n" + newLine);
    }

    public int getFirstVisibleLine() {
        return firstVisibleLine;
    }

    public void scrollTillBottom() {
        firstVisibleLine = textAsLines.size() - maxVisibleLinesCount;
        if (firstVisibleLine < 0) {
            firstVisibleLine = 0;
        }
        putLinesInTextBoxes();
    }

    public void scrollTillTop() {
        firstVisibleLine = 0;
        putLinesInTextBoxes();
    }

    public int getLineCount() {
        return textAsLines.size();
    }

    public String getLineText(int lineIndex) {
        return textAsLines.get(lineIndex);
    }

    public void setLastLine(String newLastLine) {
        if (textAsLines.size() <= 1) {
            textSB.delete(0, textSB.length());
            textSB.append(newLastLine);
        } else {
            String lastLine = textAsLines.get(textAsLines.size() - 1);
            if (!lastLine.equals("")) {
                textSB.delete(textSB.lastIndexOf(lastLine), textSB.length());
            }
            textSB.append(newLastLine);
        }
        setUpTextLines();
        putLinesInTextBoxes();
    }

    public char getForceNewLineChar() {
        return forceNewLineChar;
    }


}
