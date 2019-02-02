package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.P2DPrimitiveWrapper;
import P2DPrimitiveWrappers.PDrawable;
import P2DPrimitiveWrappers.PLocatable;
import PGUIObject.GuidedBoard;
import PGUIObject.PFocusable;

public abstract class WrapperPainterObject<T extends P2DPrimitiveWrapper>
        implements PFocusable, PDrawable, PLocatable {

    protected final T wrapper;
    protected boolean focus, focusable;
    protected EllipseWrapper[] constructionPoints;
    protected EllipseWrapper focusedConstructionPoints;
    public static float constructionPointSize = 25;
    protected String name;
    protected int type;


    //Types
    public static class Types {
        public static final int LINE = 944;
        public static final int TEXT = 456;
        public static final int ELLIPSE = 901;
        public static final int RECTANGLE = 121;
        public static final int POINT = 884;
        public static final int IMAGE = 303;
        public static final int POLYGON = 764;
    }

    public WrapperPainterObject(T wrapper, String name, int type) {
        this.wrapper = wrapper;
        this.name = name;
        this.type = type;
        this.focusable = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getWrapper() {
        return wrapper;
    }

    public void drawFocus() {
        if (focusable && focus) {
            for (int i = 0; i < constructionPoints.length; i++) {
                EllipseWrapper constructionPoint = constructionPoints[i];
                constructionPoint.setFillEnable(false);
                if (i == 0 || focusedConstructionPoints == constructionPoint) {
                    constructionPoint.setStrokeWeight(3);
                    constructionPoint.draw();
                } else {
                    constructionPoint.setStrokeWeight(1);
                    constructionPoint.draw();
                }
            }
        }

    }

    @Override
    public void draw() {
        wrapper.draw();
        drawFocus();
    }

    @Override
    public void draw(float x, float y) {
        draw();
    }

    public abstract void rebuild();

    public boolean isThisOverMe(float x, float y) {
        return wrapper.isThisOverMe(x, y);
    }

    public EllipseWrapper getConstructionPointAtPosition(float x, float y) {
        for (EllipseWrapper constructionPoint : constructionPoints) {
            if (constructionPoint.isThisOverMe(x, y)) {
                return constructionPoint;
            }
        }
        return null;
    }

    @Override
    public boolean isFocused() {
        return focus;
    }

    @Override
    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    @Override
    public boolean isFocusable() {
        return focusable;
    }

    @Override
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public abstract void guide(GuidedBoard guidedBoard);

    public int getType() {
        return type;
    }

    public void move(float dx, float dy) {
        for (EllipseWrapper constructionPoint : constructionPoints) {
            constructionPoint.setX(constructionPoint.getX() + dx);
            constructionPoint.setY(constructionPoint.getY() + dy);
        }
        rebuild();
    }

    public abstract void moveTo(float x, float y);


    public abstract String getDescription();

    public boolean isALine() {
        return getType() == Types.LINE;
    }

    public boolean isAPoint() {
        return getType() == Types.POINT;
    }

    public boolean isAnEllipse() {
        return getType() == Types.ELLIPSE;
    }

    public boolean isAText() {
        return getType() == Types.TEXT;
    }

    public boolean isARectangle() {
        return getType() == Types.RECTANGLE;
    }

    public boolean isAPolygon() {
        return getType() == Types.POLYGON;
    }

    public boolean isAnImage() {
        return getType() == Types.IMAGE;
    }

    public static void setConstructionPointSize(float constructionPointSize) {
        WrapperPainterObject.constructionPointSize = constructionPointSize;
    }

    public EllipseWrapper getFocusedConstructionPoints() {
        return focusedConstructionPoints;
    }

    public void setFocusedConstructionPoints(EllipseWrapper focusedConstructionPoints) {
        this.focusedConstructionPoints = focusedConstructionPoints;
    }

    public EllipseWrapper[] getConstructionPoints() {
        return constructionPoints;
    }
}
