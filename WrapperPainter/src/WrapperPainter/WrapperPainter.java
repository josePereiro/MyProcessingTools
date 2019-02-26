package WrapperPainter;

import BuiltIn.FileTools;
import BuiltIn.LayoutTools;
import BuiltIn.StringTools;
import Common.Tools;
import P2DPrimitiveWrappers.*;
import PGUIObject.*;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static WrapperPainter.WPObject.Types;

public class WrapperPainter extends PApplet {

    /*
     * TODO
     * 1. Returns relative coordinates!!
     * 2. Add settings to the save file. Ex: drawBoard dx, dy, backgroundImage Path!!!
     *
     */

    public static void main(String[] args) {
        PApplet.main("WrapperPainter.WrapperPainter");
    }

    //Main Window
    private static final int DW = 1100;
    private static final int DH = 650;
    private int margin = (int) (Math.min(DW, DH) * 0.01);

    //PGuiObjects
    static PGuiManager pGuiManager;
    static RectangleWrapper toolsBox;
    static ColorSelector colorSelector;
    static RectangleWrapper bottomsGridBox;
    static PGuiObject newRectBtm, newEllipseBtm, newLineBtm, newImageBtm, newTextBtn;
    static MultiLineTextBox objectProperties;
    static SingleLineTextBox guiProperties;
    static ZoomBox zoom;
    static Console console;
    static GuidedBoard drawBoard;

    //DrawComponents
    private static Point.Float pointer;
    static WPObjectsManager WPObjectsManager;
    private static ObjectConstructor.ComponentInConstruction objectInConstruction;
    private static RectangleWrapper constructionIcon;
    private static RectangleWrapper lineIcon;
    private static RectangleWrapper ellipseIcon;
    private static RectangleWrapper rectIcon;
    private static RectangleWrapper imageIcon;
    private static RectangleWrapper textIcon;

    //Zoom
    private int mx, my;

    //Files
    private File saveFile;
    private File openedFile;

    @Override
    public void settings() {
        size(DW, DH);
    }

    @Override
    public void setup() {
        initializeDrawBoard();
        initializeConsole();
        initializeTools();
        initializeDrawComponentsBtms();
        initializeProperties();
        initializePGuiManager();
        initializeIcons();

        //Paths hard coded
        saveFile = new File("/Users/Pereiro/Code/Java/" +
                "IntelliJIDEA/save8BitsComputer.txt");
        openedFile = new File("/Users/Pereiro/Code/Java/" +
                "IntelliJIDEA/save8BitsComputer.txt");
        drawBoard.setBackgroundImagePath("/Users/Pereiro/" +
                "Code/Java/IntelliJIDEA/MyProcessingTools/background.png");

        //Opening File
        WPStoreData.loadStoreData(loadStrings(openedFile), this);
        console.println("File loaded: " + openedFile.getAbsolutePath());

    }

    @Override
    public void draw() {
        background(155);
        drawGUIComponents();
        WPObjectsManager.drawObjects();
        drawComponentInConstruction();

        drawPointer();
        zoom.magnifyCenteredArea(mx, my);
        drawFloatingIcon();

        if (frameCount % 20 == 0) {
            WPObjectsManager.saveInHistory();
        }


    }

    private void drawPointer() {
        if (drawBoard.isThisOverMe(mouseX, mouseY)) {
            pointer.x = drawBoard.getCloserGuideX(mouseX);
            pointer.y = drawBoard.getCloserGuideY(mouseY);
        }
        strokeWeight(0.5F);
        stroke(0, 120);
        fill(255, 100);
        ellipse(pointer.x, pointer.y, 15, 15);
        noStroke();
        fill(0);
        ellipse(pointer.x, pointer.y, 1, 1);
        ellipse(mouseX, mouseY, 1, 1);
    }

    private void drawComponentInConstruction() {
        if (objectInConstruction != null) {

            int pointSize = 15;

            if (objectInConstruction.isALine()) {
                drawLineInConstruction(pointSize);
            } else if (objectInConstruction.isARectangle() || objectInConstruction.isAImage()) {
                drawRectangleInConstruction(pointSize);
            } else if (objectInConstruction.isAEllipse()) {
                drawEllipseInConstruction(pointSize);
            } else if (objectInConstruction.isAText()) {
                drawATextInConstruction(pointSize);
            }
        }
    }

    private void drawATextInConstruction(int pointSize) {
        if (objectInConstruction.getConstructionPoints().size() > 0) {
            //Point
            Point.Float point = objectInConstruction.getConstructionPoints().get(0);
            stroke(0);
            strokeWeight(1);
            fill(255, 100);
            ellipse(point.x, point.y, pointSize, pointSize);
            textSize(15);
            fill(0);
            text("Text", point.x, point.y);
        }
    }

    private void drawEllipseInConstruction(int pointSize) {
        if (objectInConstruction.getConstructionPoints().size() > 0) {
            //Firs Point
            Point.Float firstPoint = objectInConstruction.getConstructionPoints().get(0);
            stroke(0);
            strokeWeight(1);
            fill(255, 100);
            ellipse(firstPoint.x, firstPoint.y, pointSize, pointSize);

            if (objectInConstruction.getConstructionPoints().size() > 1) {
                //Second Point
                Point.Float secondPoint = objectInConstruction.getConstructionPoints().get(1);
                stroke(0);
                strokeWeight(1);
                fill(255, 100);
                float r = (float) Point.Float.
                        distance(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y) * 2;
                ellipse(firstPoint.x, firstPoint.y, r, r);
                ellipse(secondPoint.x, secondPoint.y, pointSize, pointSize);

            } else if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                float r = (float) Point.Float.
                        distance(firstPoint.x, firstPoint.y, mouseX, mouseY) * 2;
                ellipse(firstPoint.x, firstPoint.y, r, r);
            }
        }
    }

    private void drawRectangleInConstruction(int pointSize) {
        if (objectInConstruction.getConstructionPoints().size() > 0) {
            //Firs Point
            Point.Float firstPoint = objectInConstruction.getConstructionPoints().get(0);
            stroke(0);
            strokeWeight(1);
            fill(255, 100);
            ellipse(firstPoint.x, firstPoint.y, pointSize, pointSize);

            if (objectInConstruction.getConstructionPoints().size() > 1) {
                //Second Point
                Point.Float secondPoint = objectInConstruction.getConstructionPoints().get(1);
                stroke(0);
                strokeWeight(1);
                fill(255, 100);
                float w = Math.abs(firstPoint.x - secondPoint.x);
                float h = Math.abs(firstPoint.y - secondPoint.y);
                if (firstPoint.x < secondPoint.x) {
                    if (firstPoint.y < secondPoint.y) {
                        rect(firstPoint.x, firstPoint.y, w, h);
                    } else {
                        rect(firstPoint.x, secondPoint.y, w, h);
                    }
                } else {
                    if (firstPoint.y < secondPoint.y) {
                        rect(secondPoint.x, firstPoint.y, w, h);
                    } else {
                        rect(secondPoint.x, secondPoint.y, w, h);
                    }
                }
                ellipse(secondPoint.x, secondPoint.y, pointSize, pointSize);

            } else if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                float w = Math.abs(firstPoint.x - mouseX);
                float h = Math.abs(firstPoint.y - mouseY);
                if (firstPoint.x < mouseX) {
                    if (firstPoint.y < mouseY) {
                        rect(firstPoint.x, firstPoint.y, w, h);
                    } else {
                        rect(firstPoint.x, mouseY, w, h);
                    }
                } else {
                    if (firstPoint.y < mouseY) {
                        rect(mouseX, firstPoint.y, w, h);
                    } else {
                        rect(mouseX, mouseY, w, h);
                    }
                }
                ellipse(mouseX, mouseY, pointSize, pointSize);
            }
        }
    }

    private void drawLineInConstruction(int pointSize) {
        if (objectInConstruction.getConstructionPoints().size() > 0) {
            //Firs Point
            Point.Float firstPoint = objectInConstruction.getConstructionPoints().get(0);
            stroke(0);
            strokeWeight(1);
            fill(255, 100);
            ellipse(firstPoint.x, firstPoint.y, pointSize, pointSize);

            if (objectInConstruction.getConstructionPoints().size() > 1) {
                //Second Point
                Point.Float secondPoint = objectInConstruction.getConstructionPoints().get(1);
                stroke(0);
                strokeWeight(1);
                fill(255, 100);
                ellipse(secondPoint.x, secondPoint.y, pointSize, pointSize);
                line(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
            } else if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                line(firstPoint.x, firstPoint.y, pointer.x, pointer.y);
            }
        }
    }

    private void drawFloatingIcon() {
        if (constructionIcon != null && drawBoard.isThisOverMe(mouseX, mouseY)) {
            constructionIcon.setX(pointer.x + 2 * margin);
            constructionIcon.setY(pointer.y + 2 * margin);
            constructionIcon.draw();
        }
    }

    private void drawGUIComponents() {
        drawBoard.draw();
        console.draw();
        toolsBox.draw();
        newRectBtm.draw();
        newEllipseBtm.draw();
        newLineBtm.draw();
        newImageBtm.draw();
        newTextBtn.draw();
        colorSelector.draw();
        objectProperties.draw();
        guiProperties.draw();
        zoom.draw();

    }

    @Override
    public void mouseClicked() {
        pGuiManager.listeningForMouseClicked(null);
    }

    @Override
    public void mouseDragged() {
        pGuiManager.listeningForMouseDragged(null);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        pGuiManager.listeningForKeyPressed(event);
    }

    @Override
    public void keyReleased() {
        pGuiManager.listeningForKeyReleased(null);
    }

    @Override
    public void keyTyped(KeyEvent event) {
        pGuiManager.listeningForKeyTyped(event);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        pGuiManager.listeningForMouseWheel(event);
        if (drawBoard.isThisOverMe(mouseX, mouseY)) {
            mx = mouseX;
            my = mouseY;
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        pGuiManager.listeningForMouseMoved(event);
    }

    private void initializePGuiManager() {
        pGuiManager = PGuiManager.createPGuiManager(this);
        pGuiManager.addPGUIObject(colorSelector);
        pGuiManager.addPGUIObject(newImageBtm);
        pGuiManager.addPGUIObject(newEllipseBtm);
        pGuiManager.addPGUIObject(newLineBtm);
        pGuiManager.addPGUIObject(newRectBtm);
        pGuiManager.addPGUIObject(newTextBtn);
        pGuiManager.addPGUIObject(console);
        pGuiManager.addPGUIObject(zoom);
        pGuiManager.addPGUIObject(drawBoard);
        pGuiManager.addPGUIObject(objectProperties);

        pGuiManager.setOnMouseWheelHandler(new PGuiObject.OnMouseWheelHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                if (drawBoard.isThisOverMe(mouseX, mouseY) ||
                        zoom.isThisOverMe(mouseX, mouseY)) {
                    return zoom.getOnMouseWheelHandler().handlePEvent(event, zoom);
                }
                return false;
            }
        });

        pGuiManager.setOnKeyPressedHandler(new PGuiObject.OnKeyPressedHandler() {
            @Override
            public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {

                if (console.isFocused()) {
                    return false;
                }

                if (keyCode == KeyMap.FOCUS_NEXT) {
                    WPObjectsManager.focusNext();
                    return true;
                } else if (keyCode == KeyMap.FOCUS_POINTED) {
                    focusPointed();
                    return true;
                } else if (keyCode == KeyMap.AMPLIFIED_AREA) {
                    mx = mouseX;
                    my = mouseY;
                    return true;
                } else if (keyCode == KeyMap.FOCUS_INNER_OBJECTS) {
                    focusInnerObjects();
                    return true;
                } else if (keyCode == KeyMap.GENERATE_CODE) {
                    String containerClass = CodeGenerator.
                            getCode(WPObjectsManager.getFocusedObjects(), drawBoard);
                    console.println(StringTools.ensureStringLength(containerClass, 100, ' '));
                    console.println("Code copied to clipboard!!!");
                    StringTools.copyToClipboard(containerClass);
                    return true;
                } else if (keyCode == KeyMap.REDUCE_GUIDES_WIDTH) {
                    drawBoard.setDy(drawBoard.getDy() - 1);
                    return true;
                } else if (keyCode == KeyMap.INCREASE_GUIDES_WIDTH) {
                    drawBoard.setDy(drawBoard.getDy() + 1);
                    return true;
                } else if (keyCode == KeyMap.REDUCE_GUIDES_HEIGHT) {
                    drawBoard.setDx(drawBoard.getDx() - 1);
                    return true;
                } else if (keyCode == KeyMap.INCREASE_GUIDES_HEIGHT) {
                    drawBoard.setDx(drawBoard.getDx() + 1);
                    return true;
                }

                ArrayList<WPObject> focusedObjects =
                        WPObjectsManager.getFocusedObjects();
                if (focusedObjects != null && focusedObjects.size() != 0) {
                    float dx = drawBoard.getDx();
                    float dy = drawBoard.getDy();
                    if (keyCode == KeyMap.MOVE_FOCUSED_TO_LEFT) {
                        WPObjectsManager.moveObjects(focusedObjects, -dx, 0);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_TOP) {
                        WPObjectsManager.moveObjects(focusedObjects, 0, -dy);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_RIGHT) {
                        WPObjectsManager.moveObjects(focusedObjects, dx, 0);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_BOTTOM) {
                        WPObjectsManager.moveObjects(focusedObjects, 0, dy);
                    } else if (keyCode == KeyMap.DELETE_FOCUSED) {
                        WPObjectsManager.remove(focusedObjects);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_MOUSE_POS) {
                        WPObjectsManager.moveObjectsToSavingRelation(focusedObjects, mouseX, mouseY);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_BACK) {
                        WPObjectsManager.moveToBack(focusedObjects);
                    } else if (keyCode == KeyMap.MOVE_FOCUSED_TO_FRONT) {
                        WPObjectsManager.moveToFront(focusedObjects);
                    }

                    return true;
                }
                return false;
            }


        });

        pGuiManager.setOnKeyReleasedHandler(new PGuiObject.OnKeyReleasedHandler() {
            @Override
            public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {

                if (console.isFocused()) {
                    return false;
                }

                if (keyCode == KeyMap.GUIDE_FOCUSED) {
                    WPObjectsManager.guideFocusedObjects(drawBoard);
                    return true;
                } else if (keyCode == KeyMap.SELECT_BACKGROUND_IMAGE) {
                    File imageFile = FileTools.openFileDialog("Select a background photo!!!");
                    if (imageFile != null) {
                            drawBoard.setBackgroundImagePath(imageFile.getAbsolutePath());
                            console.println("Background image set!!!");
                    }
                } else if (keyCode == KeyMap.SHOW_BACKGROUND_IMAGE) {
                    drawBoard.drawBackgroundImage(!drawBoard.isDrawBackgroundImage());
                    return true;
                } else if (keyCode == KeyMap.PRINT_HELP) {
                    printHelp();
                    return true;
                } else if (keyCode == KeyMap.SHOW_V_GUIDES) {
                    drawBoard.setVGuidesVisible(!drawBoard.isVGuidesVisible());
                    return true;
                } else if (keyCode == KeyMap.SHOW_H_GUIDES) {
                    drawBoard.setHGuidesVisible(!drawBoard.ishGuidesVisible());
                    return true;
                } else if (keyCode == KeyMap.NORMALIZE_GUIDES) {
                    float d = Math.max(drawBoard.getDx(), drawBoard.getDy());
                    drawBoard.setDx(d);
                    drawBoard.setDy(d);
                    return true;
                } else if (keyCode == KeyMap.START_LINE_CONSTRUCTION) {
                    ObjectConstructor.getTwoPointsObjectStartConstructionHandler(Types.LINE).
                            handlePEvent(null, newLineBtm);
                    return true;
                } else if (keyCode == KeyMap.START_RECT_CONSTRUCTION) {
                    ObjectConstructor.getTwoPointsObjectStartConstructionHandler(Types.RECTANGLE).
                            handlePEvent(null, newRectBtm);
                    return true;
                } else if (keyCode == KeyMap.START_ELLIPSE_CONSTRUCTION) {
                    ObjectConstructor.getTwoPointsObjectStartConstructionHandler(Types.ELLIPSE).
                            handlePEvent(null, newEllipseBtm);
                    return true;
                } else if (keyCode == KeyMap.START_IMAGE_CONSTRUCTION) {
                    ObjectConstructor.getTwoPointsObjectStartConstructionHandler(Types.IMAGE).
                            handlePEvent(null, newImageBtm);
                    return true;
                } else if (keyCode == KeyMap.START_TEXT_CONSTRUCTION) {
                    ObjectConstructor.getOnePointObjectStartConstructionHandler(Types.TEXT).
                            handlePEvent(null, newTextBtn);
                    return true;
                } else if (keyCode == KeyMap.SAVE_INTO_FILE) {
                    saveIntoFile();
                    return true;
                } else if (keyCode == KeyMap.OPEN_FILE) {
                    openFile();
                    return true;
                } else if (keyCode == KeyMap.UNDO) {
                    WPObjectsManager.toThePass();
                    return true;
                } else if (keyCode == KeyMap.REDO) {
                    WPObjectsManager.toTheFuture();
                    return true;
                } else if (keyCode == KeyMap.FIND_BY_NAME) {
                    findByName();
                    return true;
                }

                ArrayList<WPObject> focusedComponents =
                        WPObjectsManager.getFocusedObjects();
                if (focusedComponents.size() > 0) {
                    if (keyCode == KeyMap.RENAME_FOCUSED) {
                        rename(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_FOCUSED_STROKE_WEIGHT) {
                        changeStrokeWeight(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_FOCUSED_STROKE_ALPHA) {
                        changeStrokeAlpha(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_FOCUSED_STROKE_ENABLE) {
                        changeStrokeEnable(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_FOCUSED_FILL_ENABLE) {
                        changeFillEnable(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_FOCUSED_FILL_ALPHA) {
                        changeFillAlpha(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.SET_FOCUS_TO_NULL) {
                        WPObjectsManager.setFocusTo(null);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_TEXT_VALUE) {
                        changeTextValue(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.CHANGE_TEXT_SIZE) {
                        changeTexSizeValue(focusedComponents);
                        return true;
                    } else if (keyCode == KeyMap.DUPLICATE_OBJECT) {
                        WPObjectsManager.duplicateFocused();
                        return true;
                    }
                }
                return false;
            }
        });

        pGuiManager.setOnMouseClickedHandler(new PGuiObject.OnMouseClickedHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                    return focusObject();
                }
                return false;
            }
        });

        pGuiManager.setOnMouseDraggedHandler(new PGuiObject.OnMouseDraggedHandler() {

            long lastUpdate = -1;

            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                if (WPObjectsManager.getFocusedObjects().size() == 0) {
                    return focusObject();
                } else if (WPObjectsManager.getFocusedObjects().size() == 1) {
                    return moveConstructionPoint();
                }
                return false;
            }

            private boolean moveConstructionPoint() {
                WPObject focusedComponent =
                        WPObjectsManager.getFocusedObjects().get(0);
                if (focusedComponent != null) {

                    EllipseWrapper constructionPoint =
                            focusedComponent.getFocusedConstructionPoints();

                    if (constructionPoint.isThisOverMe(mouseX, mouseY)) {
                        lastUpdate = System.currentTimeMillis();
                    }

                    if (System.currentTimeMillis() - lastUpdate < 300) {
                        constructionPoint.setX(mouseX);
                        constructionPoint.setY(mouseY);
                        focusedComponent.rebuild();
                        focusedComponent.guide(drawBoard);
                    } else {
                        constructionPoint = focusedComponent.getConstructionPointAtPosition(mouseX, mouseY);
                        if (constructionPoint != null) {
                            focusedComponent.
                                    setFocusedConstructionPoints(constructionPoint);
                        }
                    }

                }
                return true;
            }
        });

    }

    private void focusPointed() {
        for (WPObject WPObject : WPObjectsManager.getWPObjects()) {
            if (WPObject.isThisOverMe(mouseX, mouseY)) {
                WPObjectsManager.addFocusTo(WPObject);
            }
        }
    }

    private void focusInnerObjects() {

        ArrayList<WPObject> innerObjects =
                WPObjectsManager.getInnerObjects(WPObjectsManager.getFocusedObjects());
        if (innerObjects.size() > 0) {
            WPObjectsManager.setFocusTo(null);
            for (WPObject innerObject : innerObjects) {
                WPObjectsManager.addFocusTo(innerObject);
            }
        }
    }

    private void changeFillEnable(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() > 0) {
            console.println("Change fill alpha: " + focusedComponents.size() + " objects");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        try {
                            int fa = Integer.parseInt(input);
                            for (WPObject focusedComponent : focusedComponents) {
                                focusedComponent.getWrapper().setFillAlpha(fa);
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void changeFillAlpha(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() > 0) {
            console.println("Change fill enable: " + focusedComponents.size() + " objects. Press \'y\' " +
                    "to enable or anything else to disable!");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        try {
                            boolean fe = input.equals("y");
                            for (WPObject focusedComponent : focusedComponents) {
                                focusedComponent.getWrapper().setFillEnable(fe);
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void changeStrokeEnable(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() > 0) {
            console.println("Change stroke enable: " + focusedComponents.size() + " objects. Press \'y\' " +
                    "to enable or anything else to disable!");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        try {
                            boolean se = input.equals("y");
                            for (WPObject focusedComponent : focusedComponents) {
                                focusedComponent.getWrapper().setStrokeEnable(se);
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private boolean focusObject() {

        if (objectInConstruction != null) {
            return false;
        }

        //If it is over the object
        for (WPObject WPObject :
                WPObjectsManager.getWPObjects()) {

            if (WPObject.isThisOverMe(mouseX, mouseY)) {
                WPObjectsManager.addFocusTo(WPObject);
                return true;
            }

        }

        //If it is over a constructor point
        for (WPObject WPObject :
                WPObjectsManager.getWPObjects()) {
            EllipseWrapper constructionPoint =
                    WPObject.getConstructionPointAtPosition(mouseX, mouseY);

            if (constructionPoint != null) {
                WPObjectsManager.addFocusTo(WPObject);
                WPObject.setFocusedConstructionPoints(constructionPoint);
                return true;
            }
        }

        WPObjectsManager.setFocusTo(null);
        return false;
    }

    private void changeStrokeWeight(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() > 0) {
            console.println("Change stroke weight: " + focusedComponents.size() + " objects");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        try {
                            float sw = Float.parseFloat(input);
                            for (WPObject focusedComponent : focusedComponents) {
                                focusedComponent.getWrapper().setStrokeWeight(sw);
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void changeStrokeAlpha(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() > 0) {
            console.println("Change stroke alpha: " + focusedComponents.size() + " objects");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        try {
                            int sa = Integer.parseInt(input);
                            for (WPObject focusedComponent : focusedComponents) {
                                focusedComponent.getWrapper().setStrokeAlpha(sa);
                            }
                        } catch (Exception ignored) {
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void changeTextValue(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() == 1 && focusedComponents.get(0).isAText()) {
            console.println("Change " + focusedComponents.get(0).getName() + " text!");

            TextWPObject textWrapperPainterObject = (TextWPObject) focusedComponents.get(0);
            console.read(textWrapperPainterObject.getWrapper().getText());

            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {
                        textWrapperPainterObject.getWrapper().setText(input);
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void changeTexSizeValue(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() == 1 && focusedComponents.get(0).isAText()) {
            console.println("Change " + focusedComponents.get(0).getName() + " textSize!");
            TextWPObject textWrapperPainterObject = (TextWPObject) focusedComponents.get(0);
            console.read(textWrapperPainterObject.getWrapper().getTextSize() + "");
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    float ts = textWrapperPainterObject.getWrapper().getTextSize();
                    try {
                        ts = Float.parseFloat(input);
                    } catch (Exception ignored) {
                    }
                    textWrapperPainterObject.getWrapper().setTextSize(ts);
                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void rename(ArrayList<WPObject> focusedComponents) {
        if (focusedComponents.size() == 1) {
            console.println("Rename " + focusedComponents.get(0).getName());
            console.read(focusedComponents.get(0).getName());
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);
            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!focusedComponents.get(0).getName().equals(input)) {
                        focusedComponents.get(0).setName(
                                WPObjectsManager.formatNewName(input));
                    }
                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        } else {
            console.println("Renaming " + focusedComponents.size() + " objects ");
            console.clearInput();
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);
            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {
                    if (!input.equals("")) {
                        for (WPObject focusedComponent :
                                focusedComponents) {
                            focusedComponent.setName(
                                    WPObjectsManager.formatNewName(input));
                        }
                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        }
    }

    private void findByName() {
        console.println("Search objects by name:");
        if (WPObjectsManager.getFocusedObjects().size() > 0) {
            console.read(WPObjectsManager.getFocusedObjects().get(0).getName());
        } else {
            console.clearInput();
        }
        console.setFocusable(true);
        pGuiManager.setFocusTo(console);

        console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
            @Override
            public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                ArrayList<String> selectedNames = new ArrayList<>();

                if (input.equals(".")) {
                    WPObjectsManager.setFocusTo(null);
                    for (WPObject WPObject :
                            WPObjectsManager.getWPObjects()) {
                        WPObjectsManager.addFocusTo(WPObject);
                        selectedNames.add("\'" + WPObject.getName() + "\'");
                    }
                } else if (!input.equals("")) {
                    WPObjectsManager.setFocusTo(null);
                    for (WPObject WPObject :
                            WPObjectsManager.getWPObjects()) {
                        if (WPObject.getName().contains(input)) {
                            WPObjectsManager.addFocusTo(WPObject);
                            selectedNames.add(WPObject.getName().replaceAll(input, "\'" +
                                    input + "\'"));
                        }
                    }
                }
                console.println("Selected " +
                        WPObjectsManager.getFocusedObjects().size() +
                        " objects!!");
                if (selectedNames.size() > 0) {
                    console.println(selectedNames.toString());
                }

                pGuiManager.setFocusTo(drawBoard);
                console.setFocusable(false);
                console.setOnInputEnteredHandler(null);
                return true;
            }
        });
    }

    private void openFile() {
        openedFile = FileTools.openFileDialog("Select a file!!!");
        if (openedFile != null) {

            WPStoreData.loadStoreData(loadStrings(openedFile), this);
            console.println("File loaded: " + openedFile.getAbsolutePath());

        }
    }

    private void saveIntoFile() {
        if (saveFile == null) {
            console.println("Write the name for the saveIntoFile file!");

            if (openedFile != null) {
                console.read(openedFile.getName());
            } else {
                console.read("save_" + System.currentTimeMillis() + ".txt");
            }
            console.setFocusable(true);
            pGuiManager.setFocusTo(console);

            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                @Override
                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                    if (!input.equals("")) {

                        if (!input.endsWith(".txt")) {
                            input += ".txt";
                        }
                        saveFile = FileTools.openDirDialog("Select a directory!!");
                        if (saveFile == null) {
                            return false;
                        }
                        saveFile = new File(saveFile, input);
                        String[] toStore = WPStoreData.getStoreData();
                        saveStrings(saveFile.getAbsolutePath(), toStore);
                        console.println("File saved: " + saveFile.getAbsolutePath());

                    }

                    pGuiManager.setFocusTo(drawBoard);
                    console.setFocusable(false);
                    console.setOnInputEnteredHandler(null);
                    return true;
                }
            });
        } else {
            String[] toStore = WPStoreData.getStoreData();
            saveStrings(saveFile.getAbsolutePath(), toStore);
            console.println("File saved: " + saveFile.getAbsolutePath());

        }
    }

    private void initializeConsole() {
        console = new Console(5, margin, drawBoard.getHeight() + 2 * margin,
                drawBoard.getWidth(), height - (drawBoard.getHeight() + 3 * margin), this);
        console.setFocusable(false);
        console.println("This is the console. It will show you the program flow!!!");
    }

    private void initializeDrawBoard() {
        drawBoard = new GuidedBoard(margin, margin,
                (int) (width * 0.80), (int) (height * 0.8), margin, margin, this);

        pointer = new Point.Float(drawBoard.getCloserGuideX(drawBoard.getX() + drawBoard.getWidth() / 2),
                drawBoard.getCloserGuideY(drawBoard.getY() + drawBoard.getHeight()));

        WPObjectsManager = new WPObjectsManager(this);

    }

    private void initializeTools() {
        toolsBox = new RectangleWrapper(drawBoard.getWidth() + 2 * margin, margin,
                width - (drawBoard.getWidth() + 3 * margin), height - 2 * margin, this);

        zoom = new ZoomBox(toolsBox.getX(), toolsBox.getY(),
                toolsBox.getWidth(), toolsBox.getWidth(), this);
        mx = (int) (zoom.getX() + zoom.getWidth() / 2);
        my = (int) (zoom.getY() + zoom.getHeight() / 2);
        zoom.setFactor(0.93F);

        //ColorSelector
        int colorSelectorHeight = Math.min((int) (toolsBox.getHeight() * 0.1F), 20);
        colorSelector = new ColorSelector(toolsBox.getX() + margin,
                zoom.getY() + zoom.getHeight() + margin,
                toolsBox.getWidth() - 2 * margin,
                colorSelectorHeight, this);

        colorSelector.setOnSelectedColorChanged(new ColorSelector.OnSelectedColorChanged() {
            @Override
            public boolean handleEvent(ColorSelector colorSelector, int newColor) {
                ArrayList<WPObject> WPObjects =
                        WPObjectsManager.getFocusedObjects();
                if (WPObjects == null || WPObjects.size() == 0) {
                    return false;
                }
                for (WPObject WPObject : WPObjects) {
                    if (WPObject.isALine()) {
                        LineWrapper lineWrapper = (LineWrapper) WPObject.getWrapper();
                        lineWrapper.setStrokeColor(newColor);
                    } else if (WPObject.isAPoint()) {
                        PointWrapper pointWrapper = (PointWrapper) WPObject.getWrapper();
                        pointWrapper.setStrokeColor(newColor);
                    } else if (WPObject.isAnEllipse()) {
                        EllipseWrapper ellipseWrapper = (EllipseWrapper) WPObject.getWrapper();
                        ellipseWrapper.setFillColor(newColor);
                    } else if (WPObject.isARectangle()) {
                        RectangleWrapper rectangleWrapper = (RectangleWrapper) WPObject.getWrapper();
                        rectangleWrapper.setFillColor(newColor);
                    }
                }
                return true;
            }

        });
    }

    private void initializeProperties() {
        //Properties
        float totalPropertiesHeight = toolsBox.getY() +
                toolsBox.getHeight() - bottomsGridBox.getY() - bottomsGridBox.getHeight();
        totalPropertiesHeight *= 0.9F;
        objectProperties = new MultiLineTextBox(15, toolsBox.getX(),
                bottomsGridBox.getY() + bottomsGridBox.getHeight(),
                toolsBox.getWidth(), totalPropertiesHeight, this) {
            @Override
            public void draw() {
                if (WPObjectsManager.getFocusedObjects().size() > 0) {
                    setText(WPObjectsManager.getFocusedObjects().get(0).
                            getDescription());
                }
                super.draw();
            }
        };


        guiProperties = new SingleLineTextBox("", objectProperties.getX(),
                objectProperties.getY() + objectProperties.getHeight(),
                objectProperties.getWidth(), objectProperties.getHeight() * 0.1F,
                this) {

            long lastMillis = -1;

            @Override
            public void draw() {
                if (drawBoard.isThisOverMe(mouseX, mouseY) &&
                        (pmouseY != mouseY || pmouseX != mouseX)) {

                    lastMillis = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - lastMillis > 2000) {
                    guiProperties.setText("FR: " + (int) frameRate);
                    guiProperties.fixTextLength();

                    if (System.currentTimeMillis() - lastMillis > 4000) {
                        lastMillis = System.currentTimeMillis();
                    }
                } else {
                    int x = (int) ((pointer.x - drawBoard.getX()) / drawBoard.getWidth() * 1000);
                    int y = (int) ((pointer.y - drawBoard.getY()) / drawBoard.getHeight() * 1000);
                    guiProperties.setText("(" + x + "," + y + ")");
                    guiProperties.fixTextLength();
                }
                super.draw();
            }
        };
    }

    private void initializeDrawComponentsBtms() {
        //BottomGrid
        int bottomsGridColumnsCount = 5;
        int bottomsGridRowsCount = 1;
        bottomsGridBox = new RectangleWrapper(toolsBox.getX(),
                colorSelector.getY() + colorSelector.getHeight(),
                toolsBox.getWidth(),
                (colorSelector.getHeight() + 4 * margin) * bottomsGridRowsCount,
                this);
        Point bottomsDimension = LayoutTools.getGridItemDimention(bottomsGridRowsCount, bottomsGridColumnsCount,
                margin, margin, (int) toolsBox.getWidth(), (int) bottomsGridBox.getHeight());
        Point.Float[] bottomsPositions = LayoutTools.getGridItemPositions(bottomsGridBox.getX(), bottomsGridBox.getY(),
                bottomsGridRowsCount, bottomsGridColumnsCount, margin, margin, bottomsGridBox.getWidth(),
                bottomsGridBox.getHeight());

        //New Rect
        newRectBtm = new PGuiObject(bottomsPositions[0].x, bottomsPositions[0].y,
                bottomsDimension.x, bottomsDimension.y, this) {

            @Override
            public void draw() {
                super.draw();
                rectIcon.setX(x);
                rectIcon.setY(y);
                rectIcon.draw();
            }
        };
        newRectBtm.setOnMouseClickedHandler(ObjectConstructor.
                getTwoPointsObjectStartConstructionHandler(Types.RECTANGLE));

        //newEllipseBtm
        newEllipseBtm = new PGuiObject(bottomsPositions[1].x, bottomsPositions[1].y,
                bottomsDimension.x, bottomsDimension.y, this) {
            @Override
            public void draw() {
                super.draw();
                ellipseIcon.setX(x);
                ellipseIcon.setY(y);
                ellipseIcon.draw();
            }
        };

        //newLineBtm
        newLineBtm = new PGuiObject(bottomsPositions[2].x, bottomsPositions[2].y,
                bottomsDimension.x, bottomsDimension.y, this) {
            @Override
            public void draw() {
                super.draw();
                lineIcon.setX(x);
                lineIcon.setY(y);
                lineIcon.draw();
            }
        };
        newLineBtm.setOnMouseClickedHandler(ObjectConstructor.
                getTwoPointsObjectStartConstructionHandler(Types.LINE));

        //newImageBtm
        newImageBtm = new PGuiObject(bottomsPositions[3].x, bottomsPositions[3].y,
                bottomsDimension.x, bottomsDimension.y, this) {
            @Override
            public void draw() {
                super.draw();
                imageIcon.setX(x);
                imageIcon.setY(y);
                imageIcon.draw();
            }
        };

        //newTextBtn
        newTextBtn = new PGuiObject(bottomsPositions[4].x, bottomsPositions[4].y,
                bottomsDimension.x, bottomsDimension.y, this) {
            @Override
            public void draw() {
                super.draw();
                textIcon.setX(x);
                textIcon.setY(y);
                textIcon.draw();
            }
        };
    }

    private void initializeIcons() {


        lineIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = context.random(500);
            float rx1, rx2, ry1, ry2;

            @Override
            public void draw() {
                super.draw();
                context.stroke(0);
                randomizeLook();
                context.line(rx1, ry1, rx2, ry2);

            }

            private void randomizeLook() {
                strokeWeight(Math.max(noise(n) + 2, 1));
                rx1 = x + width / 2 - width / 3 * noise(n) + 1;
                ry1 = y + this.getHeight() / 2 - this.getHeight() / 3 * noise(n) + 1;
                rx2 = x + width / 2 + width / 3 * noise(n) - 1;
                ry2 = y + this.getHeight() / 2 + this.getHeight() / 3 * noise(n) - 1;
                n += 0.005F;
            }

        };

        ellipseIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = context.random(500);
            float r1 = this.getWidth() / 2;
            float r2 = this.getHeight() / 2;

            @Override
            public void draw() {
                super.draw();
                context.stroke(0);
                context.strokeWeight(1.0F);
                context.fill(0);
                context.ellipse(this.getX() + this.getWidth() / 2,
                        y + this.getHeight() / 2, r1, r2);
                randomizeLook();

            }

            private void randomizeLook() {
                r1 = (int) Math.min(r1 * noise(n) + this.getWidth() / 4, this.getWidth() * 0.8F);
                r2 = (int) Math.min(r2 * noise(n + 100) + this.getHeight() / 4, this.getHeight() * 0.8F);
                n += 0.005F;
            }

        };


        rectIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = context.random(500);
            float r1 = this.getWidth() / 2;
            float r2 = this.getHeight() / 2;

            @Override
            public void draw() {
                super.draw();
                context.stroke(0);
                context.strokeWeight(1.0F);
                context.fill(0);
                context.rect(this.getX() + this.getWidth() / 2 - r1 / 2,
                        y + this.getHeight() / 2 - r2 / 2, r1, r2);
                randomizeLook();

            }

            private void randomizeLook() {
                r1 = (int) Math.min(r1 * noise(n) + this.getWidth() / 4, this.getWidth() * 0.8F);
                r2 = (int) Math.min(r2 * noise(n + 100) + this.getHeight() / 4, this.getHeight() * 0.8F);
                n += 0.005F;
            }

        };


        imageIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = random(345);
            int rectCount = 5;
            float rectW = getWidth() / (float) rectCount;
            float rectH = getHeight() / (float) rectCount;


            @Override
            public void draw() {
                super.draw();
                context.noStroke();
                Point.Float[] rectPositions = LayoutTools.getGridItemPositions(getX() + 1, getY() + 1, rectCount, rectCount,
                        0, 0, getWidth() - 1.0F, getHeight() - 1.0F);
                for (int i = 0; i < rectPositions.length; i++) {
                    context.fill(0, noise(n + i) * 255);
                    context.rect(rectPositions[i].x, rectPositions[i].y, rectW, rectH);
                    n += 0.001;
                }
            }

        };

        textIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = 0F;
            String text = "Tools";
            SingleLineTextBox textBox = new SingleLineTextBox("Text", getX() + 1, getY() + 1,
                    getWidth() - 2, getHeight() - 2, context);

            @Override
            public void draw() {
                super.draw();
                textBox.setFillEnable(false);
                textBox.setStrokeEnable(true);
                textBox.setStrokeEnable(false);
                textBox.setStrokeColor(0);
                textBox.setText(text);
                textBox.setTextSize((int) (getHeight() * 0.3 + context.noise(n) * getHeight() * 0.5));
                textBox.fixTextLength();
                textBox.setX(x);
                textBox.setY(y);
                textBox.draw();
                n += 0.01;

            }

        };

    }

    private static class ObjectConstructor {

        private static PGuiObject.OnMouseClickedHandler getOnePointObjectStartConstructionHandler(int type) {
            return new PGuiObject.OnMouseClickedHandler() {
                @Override
                public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                    if (objectInConstruction != null) {
                        if (objectInConstruction.isAText()) {
                            cancelConstruction();
                            return true;
                        }
                        cancelConstruction();
                        console.println();
                    }

                    if (type == Types.TEXT) {
                        console.println("Building text?");
                        constructionIcon = textIcon;
                    }

                    console.println("Click the drawBoard to set the anchor point!!!");

                    //New objectInConstruction
                    objectInConstruction = new ComponentInConstruction(type);
                    WPObjectsManager.setFocusTo(null);

                    //NextStep
                    pGuiManager.setFocusTo(drawBoard);
                    drawBoard.setOnMouseClickedHandler(getOnePointObjectPointHandler(type));

                    return true;
                }
            };
        }

        private static PGuiObject.OnMouseClickedHandler getOnePointObjectPointHandler(int type) {
            return new PGuiObject.OnMouseClickedHandler() {
                @Override
                public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                    //SecondPoint
                    Point.Float point = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                            drawBoard.getContext().mouseY);

                    objectInConstruction.addConstructionPoint(point);

                    //Info
                    console.println("Anchor Point set at " + point.toString());
                    console.println("Now, type a new name in the console or accept the suggested by pressing enter!!!");

                    //Event Handler
                    drawBoard.setOnMouseClickedHandler(null);
                    console.read(WPObjectsManager.suggestNewName(type));
                    console.setFocusable(true);
                    pGuiManager.setFocusTo(console);
                    console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                        @Override
                        public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                            //name
                            String checkedName = WPObjectsManager.formatNewName(input);
                            objectInConstruction.setName(checkedName);

                            //Finishing
                            finishConstruction();

                            //Event Handler
                            console.setOnInputEnteredHandler(null);
                            console.setFocusable(false);

                            return true;
                        }
                    });

                    return true;
                }
            };
        }


        private static PGuiObject.OnMouseClickedHandler getTwoPointsObjectStartConstructionHandler(int type) {
            return new PGuiObject.OnMouseClickedHandler() {
                @Override
                public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                    if (objectInConstruction != null) {
                        if (objectInConstruction.isALine()) {
                            cancelConstruction();
                            return true;
                        }
                        cancelConstruction();
                        console.println();
                    }

                    if (type == Types.LINE) {
                        console.println("Building Line?");
                        constructionIcon = lineIcon;
                    } else if (type == Types.RECTANGLE) {
                        console.println("Building Rectangle?");
                        constructionIcon = rectIcon;
                    } else if (type == Types.ELLIPSE) {
                        console.println("Building Ellipse?");
                        constructionIcon = ellipseIcon;
                    } else if (type == Types.IMAGE) {
                        console.println("Building ImageBox?");
                        constructionIcon = imageIcon;
                    }

                    console.println("Click the drawBoard to set the first point!!!");

                    //New objectInConstruction
                    objectInConstruction = new ComponentInConstruction(type);
                    WPObjectsManager.setFocusTo(null);

                    //NextStep
                    pGuiManager.setFocusTo(drawBoard);
                    drawBoard.setOnMouseClickedHandler(getTwoPointsObjectFirstPointHandler(type));

                    return true;
                }
            };
        }

        private static PGuiObject.OnMouseClickedHandler getTwoPointsObjectFirstPointHandler(int type) {
            return new PGuiObject.OnMouseClickedHandler() {
                @Override
                public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                    //FirstPoint
                    Point.Float firstPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                            drawBoard.getContext().mouseY);
                    objectInConstruction.addConstructionPoint(firstPoint);

                    //Info
                    console.println("First Point set at " + firstPoint.toString());
                    console.println("Click the drawBoard again to set the second point!!!");

                    //NextStep
                    drawBoard.setOnMouseClickedHandler(getTwoPointsObjectSecondPointHandler(type));
                    return true;
                }
            };

        }

        private static PGuiObject.OnMouseClickedHandler getTwoPointsObjectSecondPointHandler(int type) {
            return new PGuiObject.OnMouseClickedHandler() {
                @Override
                public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                    //SecondPoint
                    Point.Float secondPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                            drawBoard.getContext().mouseY);

                    objectInConstruction.addConstructionPoint(secondPoint);

                    //Info
                    console.println("Second Point set at " + secondPoint.toString());
                    console.println("Now, type a new name in the console or accept the suggested by pressing enter!!!");

                    //Event Handler
                    drawBoard.setOnMouseClickedHandler(null);
                    console.read(WPObjectsManager.suggestNewName(type));
                    console.setFocusable(true);
                    pGuiManager.setFocusTo(console);
                    console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                        @Override
                        public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                            //name
                            String checkedName = WPObjectsManager.formatNewName(input);
                            objectInConstruction.setName(checkedName);

                            //Finishing
                            finishConstruction();

                            //Event Handler
                            console.setOnInputEnteredHandler(null);
                            console.setFocusable(false);

                            return true;
                        }
                    });

                    return true;
                }
            };
        }


        private static class ComponentInConstruction {

            private int type;
            private String name;
            private final ArrayList<Point.Float> constructionPoints;

            ComponentInConstruction(int type) {
                this.type = type;
                constructionPoints = new ArrayList<>();
            }

            int getType() {
                return type;
            }

            void setName(String name) {
                this.name = name;
            }

            ArrayList<Point.Float> getConstructionPoints() {
                return constructionPoints;
            }

            void addConstructionPoint(Point.Float point) {
                constructionPoints.add(point);
            }

            boolean isALine() {
                return getType() == Types.LINE;
            }

            boolean isARectangle() {
                return getType() == Types.RECTANGLE;
            }

            boolean isAEllipse() {
                return getType() == Types.ELLIPSE;
            }

            boolean isAText() {
                return getType() == Types.TEXT;
            }

            boolean isAImage() {
                return getType() == Types.IMAGE;
            }

        }


        private static void cancelConstruction() {
            pGuiManager.setFocusTo(drawBoard);
            console.setOnInputEnteredHandler(null);
            console.setFocusable(false);
            drawBoard.setOnMouseClickedHandler(null);
            objectInConstruction = null;
            constructionIcon = null;
            console.println("Construction Cancelled!!!");
            console.println();
        }

        private static void finishConstruction() {
            pGuiManager.setFocusTo(drawBoard);
            WPObject brantNew = constructDrawComponent();
            console.println("New component created: " + brantNew);
            console.println("Construction finished!!! Enjoy");
            console.println();
            WPObjectsManager.addDrawComponent(brantNew);
            WPObjectsManager.setFocusTo(brantNew);
            objectInConstruction = null;
            constructionIcon = null;

        }

        private static WPObject constructDrawComponent() {

            if (objectInConstruction == null) {
                return null;
            }

            if (objectInConstruction.isALine()) {
                Point.Float p0 = objectInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = objectInConstruction.getConstructionPoints().get(1);
                LineWrapper lineWrapper = new LineWrapper(p0.x, p0.y, p1.x, p1.y, pGuiManager.getContext());
                return new LineWPObject(lineWrapper,
                        objectInConstruction.name);

            } else if (objectInConstruction.isARectangle()) {
                Point.Float p0 = objectInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = objectInConstruction.getConstructionPoints().get(1);
                float w = Math.abs(p1.x - p0.x);
                float h = Math.abs(p1.y - p0.y);
                RectangleWrapper rectWrapper;
                if (p0.getX() < p1.getX()) {
                    if (p0.getY() < p1.getY()) {
                        rectWrapper = new RectangleWrapper(p0.x, p0.y,
                                w, h, pGuiManager.getContext());
                    } else {
                        rectWrapper = new RectangleWrapper(p0.x, p1.y,
                                w, h, pGuiManager.getContext());
                    }
                } else {
                    if (p0.getY() < p1.getY()) {
                        rectWrapper = new RectangleWrapper(p1.x, p0.y,
                                w, h, pGuiManager.getContext());
                    } else {
                        rectWrapper = new RectangleWrapper(p1.x, p1.y,
                                w, h, pGuiManager.getContext());
                    }
                }
                return new RectangleWPObject(rectWrapper,
                        objectInConstruction.name);
            } else if (objectInConstruction.isAEllipse()) {
                Point.Float p0 = objectInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = objectInConstruction.getConstructionPoints().get(1);
                float r = (float) Point.distance(p0.x, p0.y, p1.x, p1.y) * 2;
                EllipseWrapper ellipseWrapper =
                        new EllipseWrapper(p0.x, p0.y, r, r, pGuiManager.getContext());
                return new EllipseWPObject(ellipseWrapper,
                        objectInConstruction.name);
            } else if (objectInConstruction.isAImage()) {
                Point.Float p0 = objectInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = objectInConstruction.getConstructionPoints().get(1);
                float w = Math.abs(p1.x - p0.x);
                float h = Math.abs(p1.y - p0.y);
                ImageWrapper rectWrapper;
                if (p0.getX() < p1.getX()) {
                    if (p0.getY() < p1.getY()) {
                        rectWrapper = new ImageWrapper(p0.x, p0.y,
                                w, h, pGuiManager.getContext());
                    } else {
                        rectWrapper = new ImageWrapper(p0.x, p1.y,
                                w, h, pGuiManager.getContext());
                    }
                } else {
                    if (p0.getY() < p1.getY()) {
                        rectWrapper = new ImageWrapper(p1.x, p0.y,
                                w, h, pGuiManager.getContext());
                    } else {
                        rectWrapper = new ImageWrapper(p1.x, p1.y,
                                w, h, pGuiManager.getContext());
                    }
                }
                return new ImageWPObject(rectWrapper,
                        objectInConstruction.name, "");
            } else if (objectInConstruction.isAText()) {
                Point.Float p0 = objectInConstruction.getConstructionPoints().get(0);
                TextWrapper textWrapper = new TextWrapper("Text", p0.x, p0.y, pGuiManager.getContext());
                return new TextWPObject(textWrapper,
                        objectInConstruction.name);
            }

            return null;
        }


    }

    private void printHelp() {

        StringBuilder helpMs = new StringBuilder("--------------- WRAPPER PAINTER HELP ------------");
        helpMs.append("\n");
        helpMs.append("Press any of this keys to:");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.FOCUS_NEXT));
        helpMs.append(": ");
        helpMs.append("Pass the focus to the next object!!!");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.DELETE_FOCUSED));
        helpMs.append(": ");
        helpMs.append("Delete the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_TOP));
        helpMs.append(": ");
        helpMs.append("Move the focused objects to the top of the screen");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_LEFT));
        helpMs.append(": ");
        helpMs.append("Move the focused objects to the left of the screen");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_RIGHT));
        helpMs.append(": ");
        helpMs.append("Move the focused objects to the right of the screen");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_BOTTOM));
        helpMs.append(": ");
        helpMs.append("Move the focused objects to the bottom of the screen");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.START_RECT_CONSTRUCTION));
        helpMs.append(": ");
        helpMs.append("Start/Cancel the construction of a rectangle");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.START_ELLIPSE_CONSTRUCTION));
        helpMs.append(": ");
        helpMs.append("Start/Cancel the construction of an ellipse");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.START_LINE_CONSTRUCTION));
        helpMs.append(": ");
        helpMs.append("Start/Cancel the construction of a line");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SAVE_INTO_FILE));
        helpMs.append(": ");
        helpMs.append("Start/Cancel the construction of an image");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.START_TEXT_CONSTRUCTION));
        helpMs.append(": ");
        helpMs.append("Start/Cancel the construction of a text");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_FOCUSED_STROKE_WEIGHT));
        helpMs.append(": ");
        helpMs.append("Change the stroke weight value of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_FOCUSED_STROKE_ALPHA));
        helpMs.append(": ");
        helpMs.append("Change the stroke alpha value of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_FOCUSED_STROKE_ENABLE));
        helpMs.append(": ");
        helpMs.append("Change the stroke enable value of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_FOCUSED_FILL_ENABLE));
        helpMs.append(": ");
        helpMs.append("Change the fill enable value of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_FOCUSED_FILL_ALPHA));
        helpMs.append(": ");
        helpMs.append("Change the fill alpha value of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.RENAME_FOCUSED));
        helpMs.append(": ");
        helpMs.append("Change the name of the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.PRINT_HELP));
        helpMs.append(": ");
        helpMs.append("Print help");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.UNDO));
        helpMs.append(": ");
        helpMs.append("Undo the last action");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.REDO));
        helpMs.append(": ");
        helpMs.append("Undo the undo :)");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.OPEN_FILE));
        helpMs.append(": ");
        helpMs.append("Open a dialog to select a file to open");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.FIND_BY_NAME));
        helpMs.append(": ");
        helpMs.append("Focus all the objects that match, at least partially with a given expression");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SHOW_V_GUIDES));
        helpMs.append(": ");
        helpMs.append("Show/Hide the vertical guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SHOW_H_GUIDES));
        helpMs.append(": ");
        helpMs.append("Show/Hide the horizontal guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.GUIDE_FOCUSED));
        helpMs.append(": ");
        helpMs.append("Guide the focused objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.AMPLIFIED_AREA));
        helpMs.append(": ");
        helpMs.append("Amplified area pointed by the mouse and show it in the zoom box");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SAVE_INTO_FILE));
        helpMs.append(": ");
        helpMs.append("Save");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.NORMALIZE_GUIDES));
        helpMs.append(": ");
        helpMs.append("Make both guides of the same size");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_TEXT_VALUE));
        helpMs.append(": ");
        helpMs.append("Change the text value of the focused text objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.CHANGE_TEXT_SIZE));
        helpMs.append(": ");
        helpMs.append("Change the text size of the focused text objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.REDUCE_GUIDES_WIDTH));
        helpMs.append(": ");
        helpMs.append("Reduce the distance between horizontal guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.INCREASE_GUIDES_WIDTH));
        helpMs.append(": ");
        helpMs.append("Increase the distance between horizontal guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.REDUCE_GUIDES_HEIGHT));
        helpMs.append(": ");
        helpMs.append("Reduce the distance between vertical guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.INCREASE_GUIDES_HEIGHT));
        helpMs.append(": ");
        helpMs.append("Increase the distance between vertical guides");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_BACK));
        helpMs.append(": ");
        helpMs.append("Move the draw plain of the selected object to the back");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_FRONT));
        helpMs.append(": ");
        helpMs.append("Move the draw plain of the selected object to the front");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.MOVE_FOCUSED_TO_MOUSE_POS));
        helpMs.append(": ");
        helpMs.append("Move the selected objects to the position of the mouse");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SHOW_BACKGROUND_IMAGE));
        helpMs.append(": ");
        helpMs.append("Show/Hide the image set as background");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SELECT_BACKGROUND_IMAGE));
        helpMs.append(": ");
        helpMs.append("Open a browser dialog to select the background image");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.SET_FOCUS_TO_NULL));
        helpMs.append(": ");
        helpMs.append("Unfocus everybody");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.DUPLICATE_OBJECT));
        helpMs.append(": ");
        helpMs.append("Duplicate the selected objects");
        helpMs.append("\n");

        helpMs.append(Tools.KeyCodes.toString(KeyMap.FOCUS_INNER_OBJECTS));
        helpMs.append(": ");
        helpMs.append("Focus the objects that are entirely inside the previews one");
        helpMs.append("\n");

        console.println(helpMs.toString());
    }

    private static class KeyMap {

        private static final int MOVE_FOCUSED_TO_BACK = Tools.KeyCodes.LETTER_N;
        private static final int MOVE_FOCUSED_TO_FRONT = Tools.KeyCodes.LETTER_M;
        private static final int MOVE_FOCUSED_TO_MOUSE_POS = Tools.KeyCodes.LETTER_Q;
        private static final int SHOW_BACKGROUND_IMAGE = Tools.KeyCodes.LETTER_U;
        private static final int SELECT_BACKGROUND_IMAGE = Tools.KeyCodes.LETTER_I;
        private static final int SET_FOCUS_TO_NULL = Tools.KeyCodes.SPACE;


        private static final int REDO = Tools.KeyCodes.LETTER_X;
        private static final int UNDO = Tools.KeyCodes.LETTER_Z;
        private static final int OPEN_FILE = Tools.KeyCodes.LETTER_O;
        private static final int FIND_BY_NAME = Tools.KeyCodes.LETTER_F;
        private static final int SHOW_H_GUIDES = Tools.KeyCodes.LETTER_H;
        private static final int SHOW_V_GUIDES = Tools.KeyCodes.LETTER_V;
        private static final int GUIDE_FOCUSED = Tools.KeyCodes.F12;
        private static final int AMPLIFIED_AREA = Tools.KeyCodes.LETTER_A;
        private static final int SAVE_INTO_FILE = Tools.KeyCodes.LETTER_S;
        private static final int NORMALIZE_GUIDES = Tools.KeyCodes.LETTER_K;
        private static final int CHANGE_TEXT_SIZE = Tools.KeyCodes.LETTER_Y;
        private static final int CHANGE_TEXT_VALUE = Tools.KeyCodes.LETTER_T;
        private static final int REDUCE_GUIDES_WIDTH = Tools.KeyCodes.LETTER_G;
        private static final int REDUCE_GUIDES_HEIGHT = Tools.KeyCodes.LETTER_C;
        private static final int INCREASE_GUIDES_WIDTH = Tools.KeyCodes.LETTER_J;
        private static final int INCREASE_GUIDES_HEIGHT = Tools.KeyCodes.LETTER_B;
        private static final int DUPLICATE_OBJECT = Tools.KeyCodes.LETTER_D;

        private static final int RENAME_FOCUSED = Tools.KeyCodes.F2;
        private static final int GENERATE_CODE = Tools.KeyCodes.F3;
        private static final int FOCUS_POINTED = Tools.KeyCodes.SHIFT;
        private static final int PRINT_HELP = Tools.KeyCodes.F1;
        private static final int FOCUS_INNER_OBJECTS = Tools.KeyCodes.RETURN;

        private static final int START_RECT_CONSTRUCTION = Tools.KeyCodes.NUMBER_1;
        private static final int START_ELLIPSE_CONSTRUCTION = Tools.KeyCodes.NUMBER_2;
        private static final int START_LINE_CONSTRUCTION = Tools.KeyCodes.NUMBER_3;
        private static final int START_IMAGE_CONSTRUCTION = Tools.KeyCodes.NUMBER_4;
        private static final int START_TEXT_CONSTRUCTION = Tools.KeyCodes.NUMBER_5;
        private static final int CHANGE_FOCUSED_STROKE_WEIGHT = Tools.KeyCodes.NUMBER_6;
        private static final int CHANGE_FOCUSED_STROKE_ALPHA = Tools.KeyCodes.NUMBER_7;
        private static final int CHANGE_FOCUSED_STROKE_ENABLE = Tools.KeyCodes.NUMBER_8;
        private static final int CHANGE_FOCUSED_FILL_ENABLE = Tools.KeyCodes.NUMBER_9;
        private static final int CHANGE_FOCUSED_FILL_ALPHA = Tools.KeyCodes.NUMBER_0;

        private static final int FOCUS_NEXT = Tools.KeyCodes.TAB;
        private static final int DELETE_FOCUSED = Tools.KeyCodes.DELETE;
        private static final int MOVE_FOCUSED_TO_TOP = Tools.KeyCodes.UP_ARROW;
        private static final int MOVE_FOCUSED_TO_LEFT = Tools.KeyCodes.LEFT_ARROW;
        private static final int MOVE_FOCUSED_TO_RIGHT = Tools.KeyCodes.RIGHT_ARROW;
        private static final int MOVE_FOCUSED_TO_BOTTOM = Tools.KeyCodes.DOWN_ARROW;


    }

}