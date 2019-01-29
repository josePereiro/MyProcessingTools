package WrapperPainter;

import P2DPrimitiveWrappers.LineWrapper;

public class CodeGenerator {

    public static void main(String[] args) {
        DrawComponent.LineDrawComponent lineDrawComponent = new DrawComponent.LineDrawComponent(new LineWrapper(0, 1,
                2, 3, null), "myLine");

        System.out.println(generateCode(lineDrawComponent));
    }

    public static String generateCode(DrawComponent drawComponent) {
        StringBuilder stringBuilder = new StringBuilder();
        if (drawComponent.isALine()) {
            DrawComponent.LineDrawComponent line = (DrawComponent.LineDrawComponent) drawComponent;
            stringBuilder.append("private static class " + line.getName() + "{\n");
            stringBuilder.append("public float x = " + line.getX() + ";");
            stringBuilder.append("public float y = " + line.getY() + ";");
            stringBuilder.append("public int fillColor = " + line.getWrapper().getFillColor() + ";" );
            stringBuilder.append("public int strokeColor = " + line.getWrapper().getStrokeColor() + ";" );


            stringBuilder.append("}");
        }

        return stringBuilder.toString();
    }

}
