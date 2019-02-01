package WrapperPainter;

import P2DPrimitiveWrappers.LineWrapper;
import processing.core.PApplet;

import java.util.ArrayList;

public class DataStoreFile {

    private static final String OBJECT_SEPARATOR = "\n";
    private static final String DATA_SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int STROKE_COLOR_INDEX = 2;
    private static final int STROKE_WEIGHT_INDEX = 3;
    private static final int FILL_COLOR_INDEX = 4;
    private static final int CONSTRUCTOR_DATA_FIRST_INDEX = 5;

    public static String generateDataStore(ArrayList<WrapperPainterObject> wrapperPainterObjects) {
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

    public static String generateDataStore(WrapperPainterObject wrapperPainterObject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WrapperPainterObject.Types.LINE);
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
            stringBuilder.append(wrapperPainterObject.getX());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(wrapperPainterObject.getY());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWrapperPainterObject) wrapperPainterObject).getX1());
            stringBuilder.append(DATA_SEPARATOR);
            stringBuilder.append(((LineWrapperPainterObject) wrapperPainterObject).getY1());
        }


        return stringBuilder.toString();
    }

    public static ArrayList<WrapperPainterObject> readDataStore(String data, PApplet context) {
        ArrayList<WrapperPainterObject> arrayList = new ArrayList<>();
        String[] objectsLine = data.split(OBJECT_SEPARATOR, -1);
        String[] objectData;
        WrapperPainterObject wrapperPainterObject;
        for (String line : objectsLine) {
            objectData = line.split(DATA_SEPARATOR, -1);
            if (objectData[TYPE_INDEX].equals(String.valueOf(WrapperPainterObject.Types.LINE))) {
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
            }

        }


        return arrayList;

    }


}
