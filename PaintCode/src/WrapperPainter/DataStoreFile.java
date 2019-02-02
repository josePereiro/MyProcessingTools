package WrapperPainter;

import P2DPrimitiveWrappers.*;
import WrapperPainter.WrapperPainterObject.Types;
import processing.core.PApplet;

import java.util.ArrayList;

class DataStoreFile {

    private static final String OBJECT_SEPARATOR = "\n";
    private static final String DATA_SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int STROKE_COLOR_INDEX = 2;
    private static final int STROKE_WEIGHT_INDEX = 3;
    private static final int FILL_COLOR_INDEX = 4;
    private static final int CONSTRUCTOR_DATA_FIRST_INDEX = 5;

    static String generateDataStore(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean notTheFirst = false;
        for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
            if (notTheFirst) {
                stringBuilder.append(OBJECT_SEPARATOR);
            }
            notTheFirst = true;
            stringBuilder.append(generateDataStore(wrapperPainterObject));

        }

        return stringBuilder.toString();
    }

    private static String generateDataStore(WrapperPainterObject wrapperPainterObject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(wrapperPainterObject.getType());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(wrapperPainterObject.getName());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(wrapperPainterObject.getWrapper().getStrokeColor());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(wrapperPainterObject.getWrapper().getStrokeWeight());
        stringBuilder.append(DATA_SEPARATOR);
        stringBuilder.append(wrapperPainterObject.getWrapper().getFillColor());
        stringBuilder.append(DATA_SEPARATOR);

        if (wrapperPainterObject.isALine()) {
            stringBuilder.append(wrapperPainterObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWrapperPainterObject) wrapperPainterObject).getWrapper().getX1());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWrapperPainterObject) wrapperPainterObject).getWrapper().getY1());
        } else if (wrapperPainterObject.isARectangle()) {
            stringBuilder.append(wrapperPainterObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((RectangleWrapperPainterObject) wrapperPainterObject).getWrapper().getWidth());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((RectangleWrapperPainterObject) wrapperPainterObject).getWrapper().getHeight());
        } else if (wrapperPainterObject.isAnEllipse()) {
            stringBuilder.append(wrapperPainterObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((EllipseWrapperPainterObject) wrapperPainterObject).getWrapper().getVr());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((EllipseWrapperPainterObject) wrapperPainterObject).getWrapper().getHr());
        } else if (wrapperPainterObject.isAText()) {
            stringBuilder.append(wrapperPainterObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((TextWrapperPainterObject) wrapperPainterObject).getWrapper().getText());
        } else if (wrapperPainterObject.isAnImage()) {
            stringBuilder.append(wrapperPainterObject.getWrapper().getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getWrapper().getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWrapperPainterObject) wrapperPainterObject).getWrapper().getWidth());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWrapperPainterObject) wrapperPainterObject).getWrapper().getHeight());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((ImageWrapperPainterObject) wrapperPainterObject).getImagePath());
        }


        return stringBuilder.toString();
    }

    static ArrayList<WrapperPainterObject> readDataStore(String data, PApplet context) {
        ArrayList<WrapperPainterObject> arrayList = new ArrayList<>();
        String[] objectsLine = data.split(OBJECT_SEPARATOR, -1);
        String[] objectData;
        WrapperPainterObject wrapperPainterObject;
        for (String line : objectsLine) {
            objectData = line.split(DATA_SEPARATOR, -1);
            if (objectData[TYPE_INDEX].equals(String.valueOf(Types.LINE))) {
                float x = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX]);
                float y = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 1]);
                float x1 = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 2]);
                float y1 = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 3]);
                wrapperPainterObject = new LineWrapperPainterObject(new LineWrapper(x, y, x1, y1, context),
                        objectData[NAME_INDEX]);
                wrapperPainterObject.getWrapper().setFillColor(Integer.parseInt(objectData[FILL_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeColor(Integer.parseInt(objectData[STROKE_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeWeight(Float.parseFloat(objectData[STROKE_WEIGHT_INDEX]));
                arrayList.add(wrapperPainterObject);
            } else if (objectData[TYPE_INDEX].equals(String.valueOf(Types.RECTANGLE))) {
                float x = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX]);
                float y = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 1]);
                float w = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 2]);
                float h = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 3]);
                wrapperPainterObject = new RectangleWrapperPainterObject(new RectangleWrapper(x, y, w, h, context),
                        objectData[NAME_INDEX]);
                wrapperPainterObject.getWrapper().setFillColor(Integer.parseInt(objectData[FILL_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeColor(Integer.parseInt(objectData[STROKE_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeWeight(Float.parseFloat(objectData[STROKE_WEIGHT_INDEX]));
                arrayList.add(wrapperPainterObject);
            } else if (objectData[TYPE_INDEX].equals(String.valueOf(Types.ELLIPSE))) {
                float x = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX]);
                float y = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 1]);
                float vr = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 2]);
                float hr = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 3]);
                wrapperPainterObject = new EllipseWrapperPainterObject(new EllipseWrapper(x, y, vr, hr, context),
                        objectData[NAME_INDEX]);
                wrapperPainterObject.getWrapper().setFillColor(Integer.parseInt(objectData[FILL_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeColor(Integer.parseInt(objectData[STROKE_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeWeight(Float.parseFloat(objectData[STROKE_WEIGHT_INDEX]));
                arrayList.add(wrapperPainterObject);
            } else if (objectData[TYPE_INDEX].equals(String.valueOf(Types.TEXT))) {
                float x = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX]);
                float y = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 1]);
                String text = objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 2];
                wrapperPainterObject = new TextWrapperPainterObject(
                        new TextWrapper(text, x, y, context),
                        objectData[NAME_INDEX]);
                wrapperPainterObject.getWrapper().setFillColor(Integer.parseInt(objectData[FILL_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeColor(Integer.parseInt(objectData[STROKE_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeWeight(Float.parseFloat(objectData[STROKE_WEIGHT_INDEX]));
                arrayList.add(wrapperPainterObject);
            } else if (objectData[TYPE_INDEX].equals(String.valueOf(Types.IMAGE))) {
                float x = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX]);
                float y = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 1]);
                float w = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 2]);
                float h = Float.parseFloat(objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 3]);
                String ip = objectData[CONSTRUCTOR_DATA_FIRST_INDEX + 4];
                wrapperPainterObject = new ImageWrapperPainterObject(new ImageWrapper(x, y, w, h, context),
                        objectData[NAME_INDEX], ip);
                wrapperPainterObject.getWrapper().setFillColor(Integer.parseInt(objectData[FILL_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeColor(Integer.parseInt(objectData[STROKE_COLOR_INDEX]));
                wrapperPainterObject.getWrapper().setStrokeWeight(Float.parseFloat(objectData[STROKE_WEIGHT_INDEX]));
                arrayList.add(wrapperPainterObject);
            }
        }


        return arrayList;

    }

}
