package WrapperPainter;

import P2DPrimitiveWrappers.*;
import PGUIObject.GuidedBoard;
import WrapperPainter.WPObject.Types;

import java.util.ArrayList;

class WPStoreData {


    //StoreData Format
    static final String LINE_SEPARATOR = "\n";
    private static final String DATA_SEPARATOR = ",";

    //Wrapper Painter Preferences
    private static final int PREFERENCES_DATA_INDEX = 0;
    private static final int DX_INDEX = 0;
    private static final int DY_INDEX = 1;
    private static final int BACKGROUND_IMAGE_PATH_INDEX = 2;


    //Wrappers
    private static final int WRAPPERS_DATA_START_INDEX = 1;
    private static final int TYPE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int STROKE_COLOR_INDEX = 2;
    private static final int STROKE_WEIGHT_INDEX = 3;
    private static final int FILL_COLOR_INDEX = 4;
    private static final int STROKE_COLOR_ENABLE_INDEX = 5;
    private static final int FILL_COLOR_ENABLE_INDEX = 6;
    private static final int STROKE_COLOR_ALPHA_INDEX = 7;
    private static final int FILL_COLOR_ALPHA_INDEX = 8;
    private static final int X_INDEX = 9;
    private static final int Y_INDEX = 10;
    private static final int CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX = 11;


    static String[] getStoreData() {
        ArrayList<String> storeData = new ArrayList<>();
        addPreferencesStoreData(storeData);
        addWrappersStoreData(storeData);
        return storeData.toArray(new String[]{""});
    }

    private static void addPreferencesStoreData(ArrayList<String> storeData) {
        storeData.add(getPreferencesStoreData());
    }

    private static String getPreferencesStoreData() {
        GuidedBoard drawBoard = WrapperPainter.drawBoard;
        return drawBoard.getDx() + DATA_SEPARATOR + drawBoard.getDy() + DATA_SEPARATOR +
                drawBoard.getBackgroundImagePath();
    }

    private static void addWrappersStoreData(ArrayList<String> storeData) {
        for (WPObject WPObject :
                WrapperPainter.WPObjectsManager.getWPObjects()) {
            storeData.add(getWrapperStoreData(WPObject));
        }
    }

    static String getWrapperStoreData(WPObject WPObject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WPObject.getType());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getName());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().getStrokeColor());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().getStrokeWeight());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().getFillColor());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().isStrokeEnable());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().isFillEnable());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().getStrokeAlpha());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(WPObject.getWrapper().getFillAlpha());
        stringBuilder.append(DATA_SEPARATOR);

        if (WPObject.isALine()) {
            stringBuilder.append(WPObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(WPObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWPObject) WPObject).getWrapper().getX1());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWPObject) WPObject).getWrapper().getY1());
        } else if (WPObject.isARectangle()) {
            stringBuilder.append(WPObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(WPObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((RectangleWPObject) WPObject).getWrapper().getWidth());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((RectangleWPObject) WPObject).getWrapper().getHeight());
        } else if (WPObject.isAnEllipse()) {
            stringBuilder.append(WPObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(WPObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((EllipseWPObject) WPObject).getWrapper().getVr());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((EllipseWPObject) WPObject).getWrapper().getHr());
        } else if (WPObject.isAText()) {
            stringBuilder.append(WPObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(WPObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((TextWPObject) WPObject).getWrapper().getText());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((TextWPObject) WPObject).getWrapper().getTextSize());
        } else if (WPObject.isAnImage()) {
            stringBuilder.append(WPObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(WPObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWPObject) WPObject).getWrapper().getWidth());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWPObject) WPObject).getWrapper().getHeight());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWPObject) WPObject).getImagePath());
        }


        return stringBuilder.toString();
    }

    static void loadStoreData(String[] dataLines, WrapperPainter context) {
        setWrapperPainterPreferences(dataLines);
        setWrappersFromStoreData(dataLines, context);
    }

    static void loadStoreData(String data, WrapperPainter context) {
        loadStoreData(data.split(LINE_SEPARATOR, -1), context);
    }

    private static void setWrapperPainterPreferences(String[] dataLines) {
        String[] preferences = dataLines[PREFERENCES_DATA_INDEX].split(DATA_SEPARATOR, -1);
        WrapperPainter.drawBoard.setDx(Float.parseFloat(preferences[DX_INDEX]));
        WrapperPainter.drawBoard.setDy(Float.parseFloat(preferences[DY_INDEX]));
        WrapperPainter.drawBoard.setBackgroundImagePath(preferences[BACKGROUND_IMAGE_PATH_INDEX]);
    }

    private static void setWrappersFromStoreData(String[] dataLines, WrapperPainter context) {
        ArrayList<WPObject> wPObjects = new ArrayList<>();
        for (int i = 0; i < dataLines.length; i++) {
            if (i < WRAPPERS_DATA_START_INDEX) continue;
            wPObjects.add(getWPObjectFromStoreDataLine(dataLines[i], context));
        }

        WrapperPainter.WPObjectsManager.setWPObjects(wPObjects);
    }

    static WPObject getWPObjectFromStoreDataLine(String dataLine, WrapperPainter context) {
        WPObject WPObject = null;
        String[] wrapperData = dataLine.split(DATA_SEPARATOR, -1);
        float x = Float.parseFloat(wrapperData[X_INDEX]);
        float y = Float.parseFloat(wrapperData[Y_INDEX]);
        if (wrapperData[TYPE_INDEX].equals(String.valueOf(Types.LINE))) {
            float x1 = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX]);
            float y1 = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 1]);
            WPObject = new LineWPObject(new LineWrapper(x, y, x1, y1, context),
                    wrapperData[NAME_INDEX]);
        } else if (wrapperData[TYPE_INDEX].equals(String.valueOf(Types.RECTANGLE))) {
            float w = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX]);
            float h = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 1]);
            WPObject = new RectangleWPObject(new RectangleWrapper(x, y, w, h, context),
                    wrapperData[NAME_INDEX]);
        } else if (wrapperData[TYPE_INDEX].equals(String.valueOf(Types.ELLIPSE))) {
            float vr = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX]);
            float hr = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 1]);
            WPObject = new EllipseWPObject(new EllipseWrapper(x, y, vr, hr, context),
                    wrapperData[NAME_INDEX]);
        } else if (wrapperData[TYPE_INDEX].equals(String.valueOf(Types.TEXT))) {
            String text = wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX];
            float textSize = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 1]);
            WPObject = new TextWPObject(
                    new TextWrapper(text, x, y, context),
                    wrapperData[NAME_INDEX]);
            ((TextWPObject) WPObject).getWrapper().setTextSize(textSize);
        } else if (wrapperData[TYPE_INDEX].equals(String.valueOf(Types.IMAGE))) {
            float w = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX]);
            float h = Float.parseFloat(wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 1]);
            String ip = wrapperData[CONSTRUCTOR_EXTRA_DATA_FIRST_INDEX + 2];
            WPObject = new ImageWPObject(new ImageWrapper(x, y, w, h, context),
                    wrapperData[NAME_INDEX], ip);
        }
        if (WPObject != null) {
            WPObject.getWrapper().setFillColor(Integer.parseInt(wrapperData[FILL_COLOR_INDEX]));
            WPObject.getWrapper().setStrokeColor(Integer.parseInt(wrapperData[STROKE_COLOR_INDEX]));
            WPObject.getWrapper().setStrokeWeight(Float.parseFloat(wrapperData[STROKE_WEIGHT_INDEX]));
            WPObject.getWrapper().setStrokeAlpha(Integer.parseInt(wrapperData[STROKE_COLOR_ALPHA_INDEX]));
            WPObject.getWrapper().setStrokeEnable(Boolean.parseBoolean(wrapperData[STROKE_COLOR_ENABLE_INDEX]));
            WPObject.getWrapper().setFillAlpha(Integer.parseInt(wrapperData[FILL_COLOR_ALPHA_INDEX]));
            WPObject.getWrapper().setFillEnable(Boolean.parseBoolean(wrapperData[FILL_COLOR_ENABLE_INDEX]));
        }
        return WPObject;
    }

}
