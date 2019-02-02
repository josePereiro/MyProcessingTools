package WrapperPainter;

import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.TextWrapper;
import PGUIObject.GuidedBoard;

public class TextWrapperPainterObject extends WrapperPainterObject<TextWrapper> {


    TextWrapperPainterObject(TextWrapper wrapper, String name) {
        super(wrapper, name, Types.TEXT);
        constructionPoints = new EllipseWrapper[1];
        constructionPoints[0] = new EllipseWrapper(wrapper.getX(),
                wrapper.getY(), constructionPointSize,
                constructionPointSize, getWrapper().getContext());
        focusedConstructionPoints = constructionPoints[0];
    }


    @Override
    public void rebuild() {
        wrapper.setX(constructionPoints[0].getX());
        wrapper.setY(constructionPoints[0].getY());
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
        rebuild();

    }

    @Override
    public String getDescription() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName() + "\n");
        stringBuilder.append("x = " + getX() + "\n");
        stringBuilder.append("y = " + getY() + "\n");
        stringBuilder.append("fillColor = " + getWrapper().getFillColor() + "\n");
        stringBuilder.append("strokeColor = " + getWrapper().getStrokeColor() + "\n");
        stringBuilder.append("text = " + getWrapper().getText() + "\n");

        return stringBuilder.toString();
    }

}

