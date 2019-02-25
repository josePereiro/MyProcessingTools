package WrapperPainter;

import P2DPrimitiveWrappers.*;
import PGUIObject.GuidedBoard;

import java.util.ArrayList;

public class CodeGenerator {

    private static String tab = "   ";

    public static void main(String[] args) {

    }

    private static String initializeWrapper(WPObject WPObject,
                                            float ix, float iy, float iw, float ih) {
        if (WPObject.isALine()) {
            return initializeLineWrapper(WPObject, ix, iy, iw, ih);
        } else if (WPObject.isAnEllipse()) {
            return initializeEllipseWrapper(WPObject, ix, iy, iw, ih);
        } else if (WPObject.isAText()) {
            return initializeTextWrapper(WPObject, ix, iy, iw, ih);
        } else if (WPObject.isARectangle()) {
            return initializeRectWrapper(WPObject, ix, iy, iw, ih);
        } else if (WPObject.isAnImage()) {
            return initializeTextWrapper(WPObject, ix, iy, iw, ih);
        }

        return "";
    }

    private static String initializeLineWrapper(WPObject WPObject,
                                                float ix, float iy, float iw, float ih) {
        LineWrapper wrapper = (LineWrapper) WPObject.getWrapper();
        return tab + tab + WPObject.getName() + " = " + " new " + wrapper.getClass().getCanonicalName() + "(" +
                (wrapper.getX() - ix) / iw + "F * cw + cx," + (wrapper.getY() - iy) / ih + "F * ch + cy," +
                (wrapper.getX1() - ix) / iw + "F * cw + cx," + (wrapper.getY1() - iy) / ih + "F * ch + cy," +
                "context" + ")" + ";" + "\n" +
                initializeWrapperLayout(WPObject, iw, ih);
    }

    private static String initializeTextWrapper(WPObject WPObject,
                                                float ix, float iy, float iw, float ih) {
        TextWrapper wrapper = (TextWrapper) WPObject.getWrapper();
        return tab + tab + WPObject.getName() + " = " + " new " +
                wrapper.getClass().getCanonicalName() + "(\"" +
                wrapper.getText() + "\"," + (wrapper.getX() - ix) / iw + "F * cw + cx," +
                (wrapper.getY() - iy) / ih + "F * ch + cy," +
                "context" + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setTextSize(" + wrapper.getTextSize() / ih + "F * ch)" + ";" + "\n" +
                initializeWrapperLayout(WPObject, iw, ih);
    }

    private static String initializeRectWrapper(WPObject WPObject,
                                                float ix, float iy, float iw, float ih) {
        RectangleWrapper wrapper = (RectangleWrapper) WPObject.getWrapper();
        return tab + tab + WPObject.getName() + " = " + " new " + wrapper.getClass().getCanonicalName() + "(" +
                (wrapper.getX() - ix) / iw + "F * cw + cx," + (wrapper.getY() - iy) / ih + "F * ch + cy," +
                wrapper.getWidth() / iw + "F * cw," + wrapper.getHeight() / ih + "F * ch," +
                "context" + ")" + ";" + "\n" +
                initializeWrapperLayout(WPObject, iw, ih);
    }

    private static String initializeImageWrapper(WPObject WPObject,
                                                 float ix, float iy, float iw, float ih) {
        ImageWrapper wrapper = (ImageWrapper) WPObject.getWrapper();
        return tab + tab + WPObject.getName() + " = " + " new " + wrapper.getClass().getCanonicalName() + "(" +
                (wrapper.getX() - ix) / iw + "F * cw + cx," + (wrapper.getY() - iy) / ih + "F * ch + cy," +
                wrapper.getWidth() / iw + "F * cw," + wrapper.getHeight() / ih + "F * ch," +
                "context" + ")" + ";" + "\n" +
                initializeWrapperLayout(WPObject, iw, ih);
    }

    private static String initializeEllipseWrapper(WPObject WPObject,
                                                   float ix, float iy, float iw, float ih) {
        EllipseWrapper wrapper = (EllipseWrapper) WPObject.getWrapper();
        return tab + tab + WPObject.getName() + " = " + " new " + wrapper.getClass().getCanonicalName() + "(" +
                (wrapper.getX() - ix) / iw + "F * cw + cx," + (wrapper.getY() - iy) / ih + "F * ch + cy," +
                wrapper.getVr() / iw + "F * cw," + wrapper.getHr() / ih + "F * ch," +
                "context" + ")" + ";" + "\n" +
                initializeWrapperLayout(WPObject, iw, ih);
    }

    private static String initializeWrapperLayout(WPObject WPObject, float iw, float ih) {
        return tab + tab + WPObject.getName() + "." +
                "setFillColor(" + WPObject.getWrapper().getFillColor() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setStrokeColor(" + WPObject.getWrapper().getStrokeColor() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setStrokeAlpha(" + WPObject.getWrapper().getStrokeAlpha() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setFillEnable(" + WPObject.getWrapper().isFillEnable() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setStrokeEnable(" + WPObject.getWrapper().isStrokeEnable() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setFillAlpha(" + WPObject.getWrapper().getFillAlpha() + ")" + ";" + "\n" +
                tab + tab + WPObject.getName() + "." +
                "setStrokeWeight(" + WPObject.getWrapper().getStrokeWeight() / (ih + iw) + "F * (ch + cw))" + ";";

    }

    private static String createWrapperField(WPObject WPObject) {
        if (WPObject.isALine()) {
            return "public static " + LineWrapper.class.getCanonicalName() +
                    " " + WPObject.getName() + ";";
        } else if (WPObject.isARectangle()) {
            return "public static " + RectangleWrapper.class.getCanonicalName() +
                    " " + WPObject.getName() + ";";
        } else if (WPObject.isAnImage()) {
            return "public static " + ImageWrapper.class.getCanonicalName() +
                    " " + WPObject.getName() + ";";
        } else if (WPObject.isAText()) {
            return "public static " + TextWrapper.class.getCanonicalName() +
                    " " + WPObject.getName() + ";";
        } else if (WPObject.isAnEllipse()) {
            return "public static " + EllipseWrapper.class.getCanonicalName() +
                    " " + WPObject.getName() + ";";
        }
        return "";
    }

    static String getCode(ArrayList<WPObject> WPObjects,
                                 GuidedBoard guidedBoard) {

        StringBuilder stringBuilder = new StringBuilder();
        //Fields
        stringBuilder.append("public static " + P2DPrimitiveWrapper.class.getName() + "[] wrappers;");
        stringBuilder.append("\n");
        for (WPObject WPObject : WPObjects) {
            stringBuilder.append(createWrapperField(WPObject));
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");

        stringBuilder.append("public static void initializeWrappers(PApplet context," +
                "float cx, float cy, float cw, float ch){\n");


        //Initialization
        for (WPObject WPObject : WPObjects) {
            stringBuilder.append(initializeWrapper(WPObject, guidedBoard.getX(),
                    guidedBoard.getY(), guidedBoard.getWidth(), guidedBoard.getHeight()));
            stringBuilder.append("\n");
        }

        boolean firstItem = true;
        stringBuilder.append(tab + tab + "wrappers = new " + P2DPrimitiveWrapper.class.getName() + "[]{");
        for (WPObject WPObject : WPObjects) {
            if (firstItem) {
                firstItem = false;
            } else {
                stringBuilder.append(",");
            }

            stringBuilder.append(WPObject.getName());
        }
        stringBuilder.append("};\n");

        stringBuilder.append("\n");
        stringBuilder.append(tab);
        stringBuilder.append("}");
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

}
