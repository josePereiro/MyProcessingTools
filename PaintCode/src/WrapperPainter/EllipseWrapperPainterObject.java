package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import PGUIObject.GuidedBoard;

import java.awt.*;

public class EllipseWrapperPainterObject extends WrapperPainterObject<EllipseWrapper> {

    public EllipseWrapperPainterObject(EllipseWrapper wrapper, String name) {
        super(wrapper, name, Types.ELLIPSE);
        Point.Float centerPosition = new Point.Float(wrapper.getX(), wrapper.getY());
        constructionPoints = new EllipseWrapper[2];
        constructionPoints[0] = new EllipseWrapper(centerPosition.x,
                centerPosition.y - wrapper.getVr() / 2, constructionPointSize, constructionPointSize,
                wrapper.getContext());
        constructionPoints[1] = new EllipseWrapper(centerPosition.x + wrapper.getHr() / 2,
                centerPosition.y, constructionPointSize, constructionPointSize,
                wrapper.getContext());
        focusedConstructionPoints = constructionPoints[0];
    }

    @Override
    public void rebuild() {
        wrapper.setX(getX());
        wrapper.setY(getY());
        wrapper.setVr(Math.abs(getY() - constructionPoints[0].getY()) * 2);
        wrapper.setHr(Math.abs(getX() - constructionPoints[1].getX()) * 2);
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
        setX(x);
        setY(y);
    }

    @Override
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName() + "\n");
        stringBuilder.append("x = " + getWrapper().getX() + "\n");
        stringBuilder.append("y = " + getWrapper().getY() + "\n");
        stringBuilder.append("vr = " + getWrapper().getVr() + "\n");
        stringBuilder.append("vh = " + getWrapper().getHr() + "\n");
        stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
        stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");

        return stringBuilder.toString();
    }

    @Override
    public float getX() {
        return constructionPoints[0].getX();
    }

    @Override
    public float getY() {
        return constructionPoints[1].getY();
    }

    @Override
    public void setX(float x) {
        if (constructionPoints[1].getX() > getX()) {
            constructionPoints[1].setX(x + getWrapper().getHr() / 2);
        } else {
            constructionPoints[1].setX(x - getWrapper().getHr() / 2);
        }
        constructionPoints[0].setX(x);
        wrapper.setX(getX());
    }

    @Override
    public void setY(float y) {
        if (constructionPoints[0].getY() > getY()) {
            constructionPoints[0].setY(y + getWrapper().getVr() / 2);
        } else {
            constructionPoints[0].setY(y - getWrapper().getVr() / 2);
        }
        constructionPoints[1].setY(y);
        wrapper.setY(getY());

    }
}

