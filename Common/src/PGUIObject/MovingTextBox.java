package PGUIObject;

import processing.core.PApplet;

public class MovingTextBox extends PGuiObject {

    private String text, textToDisplay;
    private SingleLineTextBox textBox;
    private int currentChar;
    private int stepInterval;
    private long lastStepTime;
    private boolean automaticStepping;
    private PApplet context;
    private boolean addHead;

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
    public MovingTextBox(String text, int x, int y, int width, int height, PApplet context) {
        super(x, y, width, height, context);
        this.context = context;
        textBox = new SingleLineTextBox("", x, y, width, height, context);
        this.text = text;
        initializeData();
        automaticStepping = true;
        textBox.setFillEnable(false);
        textBox.setStrokeEnable(false);
        setFocusable(false);
    }

    @Override
    public void draw() {
        super.draw();

        step();
        textBox.draw();

    }

    /**
     * Step the text. If {@code automaticStepping} is true it will only step if a time equal to {@code stepInterval} has
     * passed.  If {@code automaticStepping} is false it will step the text all the time this method is called.
     */
    public void step() {

        //Check
        if (automaticStepping) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastStepTime > stepInterval) {
                lastStepTime = currentTime;
            } else return;
        }

        //Step
        if (currentChar < textToDisplay.length())
            currentChar++;
        else currentChar = 0;
        setTextBoxText();

    }

    private void initializeData() {
        currentChar = 0;
        stepInterval = 100;
        lastStepTime = 0;
        if (addHead)
            addHead();
        else textToDisplay = text;
    }

    private void addHead() {
        context.textSize(textBox.getTextSize());
        float spaceWidth = context.textWidth(' ');
        int times = (int) (width / spaceWidth);
        StringBuilder head = new StringBuilder(" ");
        for (int t = 0; t < times; t++) {
            head.append(" ");
        }
        textToDisplay = head.toString() + text;
    }

    public void addHead(boolean addHead) {
        this.addHead = addHead;
        initializeData();
    }

    private void setTextBoxText() {
        textBox.setText(textToDisplay.substring(currentChar));
        textBox.fixTextLength();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        initializeData();
    }

}
