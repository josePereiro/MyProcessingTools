package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.LineWrapper;
import PGUIObject.GuidedBoard;

public class LineWPObject extends WPObject<LineWrapper> {


    public LineWPObject(LineWrapper wrapper, String name) {
        super(wrapper, name, Types.LINE);
        constructionPoints = new EllipseWrapper[2];
        constructionPoints[0] = new EllipseWrapper(wrapper.getX(),
                wrapper.getY(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
        constructionPoints[1] = new EllipseWrapper(wrapper.getX1(),
                wrapper.getY1(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
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
        float ccx = Math.min(constructionPoints[0].getX(), constructionPoints[1].getX()) +
                Math.abs(constructionPoints[0].getX() - constructionPoints[1].getX()) / 2;
        float ccy = Math.min(constructionPoints[0].getY(), constructionPoints[1].getY()) +
                Math.abs(constructionPoints[0].getY() - constructionPoints[1].getY()) / 2;
        float dx = x - ccx;
        float dy = y - ccy;
        move(dx, dy);
    }

    @Override
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder(super.getDescription());
        stringBuilder.append("x1 = " + getWrapper().getX1() + "\n");
        stringBuilder.append("y1 = " + getWrapper().getY1() + "\n");
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

