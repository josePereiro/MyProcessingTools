package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.LineWrapper;
import PGUIObject.GuidedBoard;

public class LineWrapperPainterObject extends WrapperPainterObject<LineWrapper> {


    public LineWrapperPainterObject(LineWrapper wrapper, String name) {
        super(wrapper, name, Types.LINE);
        constructionPoints = new EllipseWrapper[2];
        constructionPoints[0] = new EllipseWrapper(wrapper.getX(),
                wrapper.getY(), constructionPointSizes,
                constructionPointSizes, getWrapper().getContext());
        constructionPoints[1] = new EllipseWrapper(wrapper.getX1(),
                wrapper.getY1(), constructionPointSizes,
                constructionPointSizes, getWrapper().getContext());
        focusedConstructionPoints = constructionPoints[0];
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
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName() + "\n");
        stringBuilder.append("x = " + getX() + "\n");
        stringBuilder.append("y = " + getY() + "\n");
        stringBuilder.append("x1 = " + getX1() + "\n");
        stringBuilder.append("y1 = " + getY1() + "\n");
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

