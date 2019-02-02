package WrapperPainter;

import P2DPrimitiveWrappers.LineWrapper;

import java.util.ArrayList;

public class CodeGenerator {

    private static String className = "WrappersContainer";
    private static String tab = "   ";

    private static String initializeWrapper(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject.isALine()) {
            return initializeLineWrapper(wrapperPainterObject);
        }

        return "";
    }

    private static String initializeLineWrapper(WrapperPainterObject wrapperPainterObject) {
        LineWrapperPainterObject line = (LineWrapperPainterObject) wrapperPainterObject;
        return tab + tab + line.getName() + " = " + " new " + line.getWrapper().getClass().getCanonicalName() + "(" +
                line.getX() + "F," + line.getY() + "F," + line.getX1() + "F," + line.getY1() + "F," +
                "PApplet.this" + ")" + ";" + "\n" +
                tab + tab + line.getName() + "." + "setFillColor(" + line.getWrapper().getFillColor() + ")" + ";" + "\n" +
                tab + tab + line.getName() + "." + "setStrokeColor(" + line.getWrapper().getStrokeColor() + ")" + ";" + "\n" +
                tab + tab + line.getName() + "." + "setStrokeWeight(" + line.getWrapper().getStrokeWeight() + "F)" + ";";
    }

    private static String createWrapperField(WrapperPainterObject wrapperPainterObject) {
        if (wrapperPainterObject.isALine()) {
            return "public static " + LineWrapper.class.getCanonicalName() +
                    " " + wrapperPainterObject.getName() + ";";
        }
        return "";
    }

    public static String getContainerClassCode(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("private static class " + className + "{");
        stringBuilder.append("\n");
        stringBuilder.append("\n");

        //Fields
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            stringBuilder.append(createWrapperField(wrapperPainterObject));
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        //        stringBuilder.append("public ");
        //        stringBuilder.append(className);
//        stringBuilder.append("(){\n");
        stringBuilder.append("static{\n");


        //Initialization
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            stringBuilder.append(initializeWrapper(wrapperPainterObject));
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append(tab);
        stringBuilder.append("}");
        stringBuilder.append("\n");

        stringBuilder.append("}");


        return stringBuilder.toString();
    }

}
