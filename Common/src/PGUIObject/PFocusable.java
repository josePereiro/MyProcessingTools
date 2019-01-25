package PGUIObject;

public interface PFocusable {

    boolean isFocused();

    void setFocus(boolean focus);

    void drawFocus();

    boolean isFocusable();

    void setFocusable(boolean focusable);

}
