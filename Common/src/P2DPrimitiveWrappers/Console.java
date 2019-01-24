package P2DPrimitiveWrappers;

import Common.Tools;
import PGUIObject.MultiLineTextBox;
import processing.core.PApplet;

public class Console extends MultiLineTextBox {

    /* TODO
        Use multilineTextBox only for managing draw and
        store data in Console
     */

    private String prompt = "$: ";
    private final Tools.KeyScanner scanner;
    private boolean autoScroll = false;
    private boolean highlightSpaces = true;
    private String spacesHighlightString = "_";

    public Console(int maxVisibleLinesCount, float x, float y,
                   float width, float height, PApplet context) {
        super(maxVisibleLinesCount, x, y, width, height, context);
        scanner = new Tools.KeyScanner();
    }

    public final void println() {
        addText("\n" + prompt);
        if (autoScroll) {
            scrollTillBottom();
        }
    }

    public final void println(String s) {
        if (highlightSpaces) {
            s = s.replaceAll(" ", spacesHighlightString);
        }
        addText(s);
        addText("\n" + prompt);
        if (autoScroll) {
            scrollTillBottom();
        }
    }

    @Override
    public void draw() {
        super.draw();

    }

    public void clear() {
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

    public boolean isHighlightSpaces() {
        return highlightSpaces;
    }

    public void setHighlightSpaces(boolean highlightSpaces) {
        this.highlightSpaces = highlightSpaces;
    }

    public String getSpacesHighlightString() {
        return spacesHighlightString;
    }

    public void setSpacesHighlightString(String spacesHighlightString) {
        this.spacesHighlightString = spacesHighlightString;
    }
}
