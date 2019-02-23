package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.TextWrapper;
import PGUIObject.GuidedBoard;

public class TextWPObject extends WPObject<TextWrapper> {


    TextWPObject(TextWrapper wrapper, String name) {
        super(wrapper, name, Types.TEXT);
        constructionPoints = new EllipseWrapper[1];
        constructionPoints[0] = new EllipseWrapper(wrapper.getX(),
                wrapper.getY(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
        focusedConstructionPoints = constructionPoints[0];
    }


    @Override
    public void rebuild() {
        wrapper.setX(getX());
        wrapper.setY(getY());
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
        constructionPoints[0].setY(y);
        constructionPoints[0].setX(x);
        rebuild();
    }

    @Override
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder(super.getDescription());
        stringBuilder.append("text = " + getWrapper().getText() + "\n");

        return stringBuilder.toString();
    }

    @Override
    public float getX() {
        return constructionPoints[0].getX();
    }

    @Override
    public float getY() {
        return constructionPoints[0].getY();
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

