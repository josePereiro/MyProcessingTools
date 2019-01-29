package WrapperPainter;

import P2DPrimitiveWrappers.*;
import PGUIObject.GuidedBoard;
import PGUIObject.PFocusable;

public abstract class DrawComponent<T extends P2DPrimitiveWrapper>
        implements PFocusable, PDrawable, PLocatable {

    protected final T wrapper;
    protected boolean focus, focusable;
    protected EllipseWrapper[] constructionPoints;
    protected String name;
    protected int type;

    private static final float CONSTRUCTION_POINT_SIZE = 25;

    //Types
    public static class Types {
        public static final int LINE = 944;
        public static final int ELLIPSE = 901;
        public static final int RECTANGLE = 922;
        public static final int POINT = 884;
        public static final int IMAGE = 303;
        public static final int POLYGON = 764;
    }

    public DrawComponent(T wrapper, String name, int type) {
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
                if (i == 0) {
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

    @Override
    public float getX() {
        return wrapper.getX();
    }

    @Override
    public float getY() {
        return wrapper.getY();
    }

    @Override
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

    public abstract void guideComponent(GuidedBoard guidedBoard);

    public int getType() {
        return type;
    }

    public abstract void move(float dx, float dy);

    public abstract String getDrawContentAsString();

    public abstract String getFieldsAsString(String accessModifier);

    public boolean isALine() {
        return getType() == Types.LINE;
    }

    public boolean isAPoint() {
        return getType() == Types.POINT;
    }

    public boolean isAnEllipse() {
        return getType() == Types.ELLIPSE;
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

    public static final class LineDrawComponent extends DrawComponent<LineWrapper> {


        public LineDrawComponent(LineWrapper wrapper, String name) {
            super(wrapper, name, Types.LINE);
            constructionPoints = new EllipseWrapper[2];
            constructionPoints[0] = new EllipseWrapper(wrapper.getX(), wrapper.getY(),
                    CONSTRUCTION_POINT_SIZE, CONSTRUCTION_POINT_SIZE, wrapper.getContext());
            constructionPoints[1] = new EllipseWrapper(wrapper.getX1(), wrapper.getY1(),
                    CONSTRUCTION_POINT_SIZE, CONSTRUCTION_POINT_SIZE, wrapper.getContext());
        }


        @Override
        public void rebuild() {
            wrapper.setX(constructionPoints[0].getX());
            wrapper.setX1(constructionPoints[1].getX());
            wrapper.setY(constructionPoints[0].getY());
            wrapper.setY1(constructionPoints[1].getY());
        }

        @Override
        public void guideComponent(GuidedBoard guidedBoard) {

            //Construction Points
            for (EllipseWrapper constructionPoint : constructionPoints) {
                constructionPoint.setX(guidedBoard.getCloserGuideX(constructionPoint.getX()));
                constructionPoint.setY(guidedBoard.getCloserGuideY(constructionPoint.getY()));
            }

            rebuild();

        }

        @Override
        public void move(float dx, float dy) {
            constructionPoints[0].setX(constructionPoints[0].getX() + dx);
            constructionPoints[0].setY(constructionPoints[0].getY() + dy);
            constructionPoints[1].setX(constructionPoints[1].getX() + dx);
            constructionPoints[1].setY(constructionPoints[1].getY() + dy);
            rebuild();

        }

        @Override
        public String getDrawContentAsString() {

            return null;
        }

        @Override
        public String getFieldsAsString(String accessModifier) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getName() + "\n");
            stringBuilder.append("x = " + getX() + "\n");
            stringBuilder.append("y = " + getY() + "\n");
            stringBuilder.append("x1 = " + getX1() + "\n");
            stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
            stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");

            return stringBuilder.toString();
        }


        public float getX1() {
            return constructionPoints[1].getX();
        }

        public float getY1() {
            return constructionPoints[1].getY();
        }

        public void setX1(float x1) {
            constructionPoints[1].setX(x1);
            rebuild();
        }

        public void setY1(float y1) {
            constructionPoints[1].setY(y1);
            rebuild();
        }

        @Override
        public void setX(float x) {
            constructionPoints[0].setX(x);
            rebuild();
        }

        @Override
        public void setY(float y) {
            constructionPoints[0].setY(y);
            rebuild();
        }

    }


}
