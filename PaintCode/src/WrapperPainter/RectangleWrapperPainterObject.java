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
        }else {
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
        stringBuilder.append("width = " + getWidth() + "\n");
        stringBuilder.append("height = " + getHeight() + "\n");
        stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
        stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");

        return stringBuilder.toString();
    }

    public float getWidth() {
        return wrapper.getWidth();
    }

    public float getHeight() {
        return wrapper.getHeight();
    }

}

