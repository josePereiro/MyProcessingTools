package PGUIObject;

import Common.Tools;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Console extends PGUIObject {


    private final int maxVisibleLinesCount;

    private MultiLineTextBox outputBox;
    private SingleLineTextBox inputBox;
    private Tools.KeyScanner scanner;
    private String prompt = "$:";




    public Console(int maxVisibleLinesCount, float x, float y,
                   float width, float height, PApplet context) {
        super(x, y, width, height, context);


        if (maxVisibleLinesCount > 2) {
            this.maxVisibleLinesCount = maxVisibleLinesCount;
        } else {
            this.maxVisibleLinesCount = 2;
        }
        initializeLayout();
        scanner = new Tools.KeyScanner();
        drawBackground(false);
        drawStroke(false);
        outputBox.setFocusable(false);
        inputBox.setFocusable(false);
    }

    void initializeLayout() {
        float multiLineBoxHeight = (height * (maxVisibleLinesCount - 1)) / maxVisibleLinesCount;
        outputBox = new MultiLineTextBox(this.maxVisibleLinesCount - 1, x, y, width,
                multiLineBoxHeight, context);
        outputBox.setFocusable(false);
        inputBox = new SingleLineTextBox("", x, y + outputBox.getHeight(), width,
                height - outputBox.getHeight(), context);
        inputBox.setText(prompt);
        outputBox.setText(prompt);
    }

    @Override
    public void draw() {
        outputBox.draw();
        inputBox.draw();
        if (focus)
            inputBox.drawFocus();
    }

    @Override
    public boolean onKeyPressed(KeyEvent keyEvent) {
        if (focus) {
            scanner.scan(context.key);
            if (scanner.newLineJustHappen()) {
                println(scanner.getLastLine());
                setInputText("");
                onInputEntered(scanner.getLastLine());
            } else {
                setInputText(scanner.getBufferedLine());
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onMouseClick(MouseEvent mouseEvent) {
        return false;
    }

    @Override
    public boolean onMouseWheel(MouseEvent mouseEvent) {
        return outputBox.onMouseWheel(mouseEvent);
    }

    public int getMaxVisibleLinesCount() {
        return maxVisibleLinesCount;
    }

    public void print(String s) {
        outputBox.addText(s);
        outputBox.scrollTillBottom();
    }

    public void println(String s) {
        print(s + '\n' + prompt);
    }

    /**
     * Get the actual text
     * @return
     */
    public String getInputText() {
        return inputBox.getText().substring(prompt.length());
    }

    public void setInputText(String s) {
        inputBox.setText(prompt + s);
        inputBox.fixTextLength();
    }

    public void onInputEntered(String input){

    }

}
