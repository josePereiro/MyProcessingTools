package P2DPrimitiveWrappers;

import Common.Tools;
import processing.core.PApplet;

public class Console extends MultiLineTextBox {

    private String prompt = "$: ";
    private final Tools.KeyScanner scanner;
    private boolean autoScroll = false;

    public Console(int MaxVisibleLinesCount, float x, float y,
                   float width, float height, PApplet context) {
        super(MaxVisibleLinesCount, x, y, width, height, context);
        scanner = new Tools.KeyScanner();
    }

    public final void println() {
        addText("\n" + prompt);
        if (autoScroll) {
            scrollTillBottom();
        }
    }

    public final void println(String s) {
        addText(s);
        addText("\n" + prompt);
        if (autoScroll) {
            scrollTillBottom();
        }
    }

    @Override
    public final void setLastLine(String s) {
        super.setLastLine(prompt + s);
        if (autoScroll) {
            scrollTillBottom();
        }
    }

    public void clear(){
        setText(prompt);
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Tools.KeyScanner getScanner() {
        return scanner;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }
}
