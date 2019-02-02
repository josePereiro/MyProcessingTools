package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import PGUIObject.GuidedBoard;

import java.awt.*;

public class EllipseWrapperPainterObject extends WrapperPainterObject<EllipseWrapper> {

    private Point.Float centerPosition;

    public EllipseWrapperPainterObject(EllipseWrapper wrapper, String name) {
        super(wrapper, name, Types.ELLIPSE);
        centerPosition = new Point.Float(wrapper.getX(), wrapper.getY());
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
        centerPosition = new Point.Float(constructionPoints[0].getX(),
                constructionPoints[1].getY());
        wrapper.setX(centerPosition.x);
        wrapper.setY(centerPosition.y);
        wrapper.setVr(Math.abs(centerPosition.y - constructionPoints[0].getY()) * 2);
        wrapper.setHr(Math.abs(centerPosition.x - constructionPoints[1].getX()) * 2);
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
        stringBuilder.append("vr = " + getVr() + "\n");
        stringBuilder.append("vh = " + getHr() + "\n");
        stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
        stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");

        return stringBuilder.toString();
    }

    public float getVr() {
        return wrapper.getVr();
    }

    public float getHr() {
        return wrapper.getHr();
    }

}

