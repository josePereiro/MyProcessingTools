package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.P2DPrimitiveWrapper;
import processing.core.PApplet;

import java.util.ArrayList;

public abstract class EditableWrapper extends P2DPrimitiveWrapper {

    private final ArrayList<EllipseWrapper> constrictionPoints;

    public EditableWrapper(float x, float y, PApplet context) {
        super(x, y, context);
        constrictionPoints = new ArrayList<>();
    }

    @Override
    public void draw() {

    }

    @Override
    public void draw(float x, float y) {

    }

    @Override
    public boolean isThisOverMe(float x, float y) {
        return false;
    }

    public

    public ArrayList<EllipseWrapper> getConstrictionPoints() {
        return constrictionPoints;
    }
}
