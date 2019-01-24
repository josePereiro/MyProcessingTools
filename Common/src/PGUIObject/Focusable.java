package PGUIObject;

public interface Focusable {

    boolean isFocused();

    void setFocus(boolean focus);

    void drawFocus();

    boolean isFocusable();

    void setFocusable(boolean focusable);

}
