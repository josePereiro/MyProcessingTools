package PGUIObject;

import Common.PObject;
import processing.core.PApplet;

public abstract class FocusEffect extends PObject {

    public FocusEffect(PApplet context) {
        super(context);
    }

    public abstract void draw(PGuiObject pGuiObject);
}
