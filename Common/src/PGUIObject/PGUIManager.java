package PGUIObject;

import Common.PObject;
import processing.core.PApplet;

import java.util.ArrayList;

public class PGUIManager extends PObject implements PListener {

    private final ArrayList<PGuiObject> pGuiObjects;

    public PGUIManager(PApplet context) {
        super(context);
        pGuiObjects = new ArrayList<>();
    }

    public void addPGUIObject(PGuiObject pGuiObject) {
        pGuiObjects.add(pGuiObject);
    }


}
