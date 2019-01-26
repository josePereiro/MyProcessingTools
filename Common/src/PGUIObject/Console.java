package PGUIObject;

import Common.Tools;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Console extends PGuiObject {


    private static final OnKeyTypedHandler DEFAULT_ON_KEY_TYPE_HANDLER = new OnKeyTypedHandler() {
        @Override
        public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {
            Console console = (Console) pGuiObject;
            console.scanner.scan(console.context.key);
            if (console.scanner.newLineJustHappen()) {
                console.println(console.scanner.getLastLine());
                console.setInputText("");
                console.onInputEntered(console.scanner.getLastLine());
            } else {
                console.setInputText(console.scanner.getBufferedLine());
            }

            return true;

        }
    };
    private static final OnMouseWheelHandler DEFAULT_ON_MOUSE_WHEEL_HANDLER = new OnMouseWheelHandler() {


        @Override
        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
            MultiLineTextBox multiLineTextBox = ((Console) pGuiObject).outputBox;
            return multiLineTextBox.getOnMouseWheelHandler().handlePEvent(event,multiLineTextBox);
        }
    };
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

        setOnKeyTypedHandler(DEFAULT_ON_KEY_TYPE_HANDLER);
        setOnMouseWheelHandler(DEFAULT_ON_MOUSE_WHEEL_HANDLER);
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
        if (focus) {
            inputBox.drawFocus();
        }
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
     *
     * @return
     */
    public String getInputText() {
        return inputBox.getText().substring(prompt.length());
    }

    public void setInputText(String s) {
        inputBox.setText(prompt + s);
        inputBox.fixTextLength();
    }

    public void onInputEntered(String input) {

    }

}
