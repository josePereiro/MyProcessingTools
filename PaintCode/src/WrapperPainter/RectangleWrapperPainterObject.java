package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.RectangleWrapper;
import PGUIObject.GuidedBoard;

public class RectangleWrapperPainterObject extends WrapperPainterObject<RectangleWrapper> {


    public RectangleWrapperPainterObject(RectangleWrapper wrapper, String name) {
        super(wrapper, name, Types.RECTANGLE);
        constructionPoints = new EllipseWrapper[2];
        constructionPoints[0] = new EllipseWrapper(wrapper.getX(),
                wrapper.getY(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
        constructionPoints[1] = new EllipseWrapper(wrapper.getX() + wrapper.getWidth(),
                wrapper.getY() + wrapper.getHeight(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
        focusedConstructionPoints = constructionPoints[0];
    }


    @Override
    public void rebuild() {
        if (constructionPoints[0].getX() < constructionPoints[1].getX()) {
            if (constructionPoints[0].getY() < constructionPoints[1].getY()) {
                wrapper.setX(constructionPoints[0].getX());
                wrapper.setY(constructionPoints[0].getY());
            } else {
                wrapper.setX(constructionPoints[0].getX());
                wrapper.setY(constructionPoints[1].getY());
            }
        } else {
            if (constructionPoints[0].getY() < constructionPoints[1].getY()) {
                wrapper.setX(constructionPoints[1].getX());
                wrapper.setY(constructionPoints[0].getY());
            } else {
                wrapper.setX(constructionPoints[1].getX());
                wrapper.setY(constructionPoints[1].getY());
            }
        }
        wrapper.setWidth(Math.abs(constructionPoints[1].getX() - constructionPoints[0].getX()));
        wrapper.setHeight(Math.abs(constructionPoints[1].getY() - constructionPoints[0].getY()));
    }

    @Override
    public void guide(GuidedBoard guidedBoard) {

        //Construction Points
        for (EllipseWrapper constructionPoint : constructionPoints) {
            constructionPoint.setX(guidedBoard.getCloserGuideX(constructionPoint.getX()));
            constructionPoint.setY(guidedBoard.getCloserGuideY(constructionPoint.getY()));
        }

        rebuild();

    }

    @Override
    public void moveTo(float x, float y) {
        float ccx = getX();
        float ccy = getY();
        float dx = x - ccx;
        float dy = y - ccy;
        move(dx, dy);
    }

    @Override
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName() + "\n");
        stringBuilder.append("x = " + getWrapper().getX() + "\n");
        stringBuilder.append("y = " + getWrapper().getY() + "\n");
        stringBuilder.append("width = " + getWrapper().getWidth() + "\n");
        stringBuilder.append("height = " + getWrapper().getHeight() + "\n");
        stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
        stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");

        return stringBuilder.toString();
    }

    @Override
    public float getX() {
        return Math.min(constructionPoints[0].getX(), constructionPoints[1].getX()) +
                Math.abs(constructionPoints[0].getX() - constructionPoints[1].getX()) / 2;
    }

    @Override
    public float getY() {
        return Math.min(constructionPoints[0].getY(), constructionPoints[1].getY()) +
                Math.abs(constructionPoints[0].getY() - constructionPoints[1].getY()) / 2;
    }

    @Override
    public void setX(float x) {

    }

    @Override
    public void setY(float y) {

    }
}

