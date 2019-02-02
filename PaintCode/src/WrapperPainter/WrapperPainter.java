package WrapperPainter;

import BuiltIn.FileTools;
import BuiltIn.LayoutTools;
import BuiltIn.StringTools;
import Common.Tools;
import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.LineWrapper;
import P2DPrimitiveWrappers.PointWrapper;
import P2DPrimitiveWrappers.RectangleWrapper;
import PGUIObject.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WrapperPainter extends PApplet {

    // 53871722 Yenier, Hermano de Miguel 80CUC 3*
    public static void main(String[] args) {
        PApplet.main("WrapperPainter.WrapperPainter");
    }

    //Main Window
    private static final int DW = 1100;
    private static final int DH = 650;
    private int margin = (int) (Math.min(DW, DH) * 0.01);

    //PGuiObjects
    private static PGuiManager pGuiManager;
    private static RectangleWrapper toolsBox;
    private static ColorSelector colorSelector;
    private static RectangleWrapper bottomsGridBox;
    private static PGuiObject newRectBtm, newEllipseBtm, newLineBtm, newImageBtm, newTextBtn;
    private static MultiLineTextBox objectProperties;
    private static SingleLineTextBox guiProperties;
    private static ZoomBox zoom;
    private static Console console;
    private static GuidedBoard drawBoard;

    //DrawComponents
    private static Point.Float pointer;
    private static WrapperPainterObjectsManager wrapperPainterObjectsManager;
    private static ComponentConstructor.ComponentInConstruction componentInConstruction;
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
    }

    @Override
    public void draw() {
        background(155);
        drawGUIComponents();
        wrapperPainterObjectsManager.drawObjects();
        drawComponentInConstruction();

        drawPointer();
        zoom.magnifyCenteredArea(mx, my);
        drawFloatingIcon();

        if (frameCount % 20 == 0) {
            wrapperPainterObjectsManager.saveInHistory();
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
        if (componentInConstruction != null) {
            if (componentInConstruction.isALine()) {
                if (componentInConstruction.getConstructionPoints().size() > 0) {
                    //Firs Point
                    Point.Float firstPoint = componentInConstruction.getConstructionPoints().get(0);
                    stroke(0);
                    strokeWeight(1);
                    fill(255, 100);
                    ellipse(firstPoint.x, firstPoint.y, 15, 15);

                    if (componentInConstruction.getConstructionPoints().size() > 1) {
                        //Second Point
                        Point.Float secondPoint = componentInConstruction.getConstructionPoints().get(1);
                        stroke(0);
                        strokeWeight(1);
                        fill(255, 100);
                        ellipse(secondPoint.x, secondPoint.y, 15, 15);
                        line(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
                    } else if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                        line(firstPoint.x, firstPoint.y, pointer.x, pointer.y);
                    }
                }
            } else if (componentInConstruction.isARectangle()) {
                if (componentInConstruction.getConstructionPoints().size() > 0) {
                    //Firs Point
                    Point.Float firstPoint = componentInConstruction.getConstructionPoints().get(0);
                    stroke(0);
                    strokeWeight(1);
                    fill(255, 100);
                    ellipse(firstPoint.x, firstPoint.y, 15, 15);

                    if (componentInConstruction.getConstructionPoints().size() > 1) {
                        //Second Point
                        Point.Float secondPoint = componentInConstruction.getConstructionPoints().get(1);
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
                    }
                }
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
                if (keyCode == 'q') {
                    wrapperPainterObjectsManager.guideComponents(drawBoard);
                    return true;
                } else if (keyCode == Tools.KeyCodes.TAB) {
                    wrapperPainterObjectsManager.focusNext();
                    return true;
                }

                ArrayList<WrapperPainterObject> focusedComponents =
                        wrapperPainterObjectsManager.getFocusedObjects();
                if (focusedComponents != null && focusedComponents.size() != 0) {

                    float dx = drawBoard.getDx();
                    float dy = drawBoard.getDy();
                    if (keyCode == 37) {
                        moveComponent(focusedComponents, -dx, 0);
                    } else if (keyCode == 38) {
                        moveComponent(focusedComponents, 0, -dy);
                    } else if (keyCode == 39) {
                        moveComponent(focusedComponents, dx, 0);
                    } else if (keyCode == 40) {
                        moveComponent(focusedComponents, 0, dy);
                    }

                    return true;
                }
                return false;
            }

            private boolean isInsideDrawBoard(WrapperPainterObject wrapperPainterObject, float dx, float dy) {
                for (EllipseWrapper constructionPoint : wrapperPainterObject.constructionPoints) {
                    if (!drawBoard.isThisOverMe(constructionPoint.getX() + dx,
                            constructionPoint.getY() + dy)) {
                        return false;
                    }
                }
                return true;
            }

            private void moveComponent(ArrayList<WrapperPainterObject> wrapperPainterObjects, float dx, float dy) {
                for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
                    if (!isInsideDrawBoard(wrapperPainterObject, dx, dy)) {
                        return;
                    }
                }

                for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
                    wrapperPainterObject.move(dx, dy);
                }
            }

        });

        pGuiManager.setOnKeyTypedHandler(new PGuiObject.OnKeyTypedHandler() {
            @Override
            public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {

                if (key == 'v' || key == 'V') {
                    drawBoard.drawVGuides(!drawBoard.isDrawVGuides());
                    return true;
                } else if (key == 'h' || key == 'H') {
                    drawBoard.drawHGuides(!drawBoard.isDrawHGuides());
                    return true;
                } else if (key == 'c' || key == 'C') {
                    drawBoard.setDx(drawBoard.getDx() - 1);
                    return true;
                } else if (key == 'b' || key == 'B') {
                    drawBoard.setDx(drawBoard.getDx() + 1);
                    return true;
                } else if (key == 'g' || key == 'G') {
                    drawBoard.setDy(drawBoard.getDy() - 1);
                    return true;
                } else if (key == 'j' || key == 'J') {
                    drawBoard.setDy(drawBoard.getDy() + 1);
                    return true;
                } else if (key == 'k' || key == 'K') {
                    float d = Math.max(drawBoard.getDx(), drawBoard.getDy());
                    drawBoard.setDx(d);
                    drawBoard.setDy(d);
                    return true;
                } else if (key == 'a' || key == 'A') {
                    mx = mouseX;
                    my = mouseY;
                    return true;
                } else if (key == '1') {
                    ComponentConstructor.Line.startConstructionHandler.handlePEvent(null, newLineBtm);
                    return true;
                } else if (key == '2') {
                    ComponentConstructor.Rectangle.startConstructionHandler.handlePEvent(null, newRectBtm);
                    return true;
                } else if (key == 's' || key == 'S') {

                    if (saveFile == null) {
                        console.println("Write the name for the save file!");

                        if (openedFile != null) {
                            console.setInputText(openedFile.getName());
                        } else {
                            console.setInputText("save_" + System.currentTimeMillis() + ".txt");
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
                                    try {
                                        FileTools.writeTextFile(saveFile, DataStoreFile.generateDataStore(
                                                wrapperPainterObjectsManager.getWrapperPainterObjects()));
                                        console.println("File created and saved at: " + saveFile.getCanonicalPath());
                                    } catch (IOException ignored) {

                                    }

                                }

                                pGuiManager.setFocusTo(drawBoard);
                                console.setFocusable(false);
                                console.setOnInputEnteredHandler(null);
                                return true;
                            }
                        });
                    } else {
                        try {
                            FileTools.writeTextFile(saveFile, DataStoreFile.generateDataStore(
                                    wrapperPainterObjectsManager.getWrapperPainterObjects()));
                            console.println("File saved: " + saveFile.getCanonicalPath());
                        } catch (IOException ignored) {

                        }
                    }
                    return true;
                } else if (key == 'o' || key == 'O') {
                    openedFile = FileTools.openFileDialog("Select a file!!!");
                    if (openedFile != null) {

                        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(loadStrings(openedFile)));
                        String s = StringTools.stringTheList(arrayList,
                                "\n");
                        wrapperPainterObjectsManager.setWrapperPainterObjects(
                                DataStoreFile.readDataStore(s, drawBoard.getContext()));
                        console.println("File loaded: " + openedFile.getAbsolutePath());

                    }
                    return true;
                }


                if (key == 'z' || key == 'Z') {
                    wrapperPainterObjectsManager.toThePass();
                    console.println("Undo");
                } else if (key == 'x' || key == 'X') {
                    wrapperPainterObjectsManager.toTheFuture();
                    console.println("Redo");
                } else if (key == 'f' || key == 'F') {
                    console.println("Search components by name:");
                    console.setInputText("");
                    console.setFocusable(true);
                    pGuiManager.setFocusTo(console);

                    console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                        @Override
                        public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                            if (!input.equals("")) {

                                wrapperPainterObjectsManager.setFocusTo(null);
                                for (WrapperPainterObject wrapperPainterObject :
                                        wrapperPainterObjectsManager.getWrapperPainterObjects()) {
                                    if (wrapperPainterObject.getName().contains(input)) {
                                        wrapperPainterObjectsManager.addFocusTo(wrapperPainterObject);
                                    }
                                }
                            }

                            pGuiManager.setFocusTo(drawBoard);
                            console.setFocusable(false);
                            console.setOnInputEnteredHandler(null);
                            return true;
                        }
                    });
                }

                ArrayList<WrapperPainterObject> focusedComponents =
                        wrapperPainterObjectsManager.getFocusedObjects();
                if (focusedComponents.size() > 0) {
                    if (key == 'r') {

                        if (focusedComponents.size() == 1) {
                            console.println("Rename " + focusedComponents.get(0).getName());
                            console.setInputText(focusedComponents.get(0).getName());
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);
                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    if (!focusedComponents.get(0).getName().equals(input)) {
                                        focusedComponents.get(0).setName(
                                                wrapperPainterObjectsManager.formatNewName(input));
                                        wrapperPainterObjectsManager.updateNames();
                                    }
                                    pGuiManager.setFocusTo(drawBoard);
                                    console.setFocusable(false);
                                    console.setOnInputEnteredHandler(null);
                                    return true;
                                }
                            });
                        } else {
                            console.println("Renaming " + focusedComponents.size() + " components ");
                            //TODO finish this

                            console.setInputText("");
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);
                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {
                                    if (!input.equals("")) {
                                        for (WrapperPainterObject focusedComponent :
                                                focusedComponents) {
                                            focusedComponent.setName(
                                                    wrapperPainterObjectsManager.formatNewName(input));
                                            wrapperPainterObjectsManager.updateNames();
                                        }
                                    }

                                    pGuiManager.setFocusTo(drawBoard);
                                    console.setFocusable(false);
                                    console.setOnInputEnteredHandler(null);
                                    return true;
                                }
                            });
                        }


                    } else if (key == 'w' || key == 'W') {
                        if (focusedComponents.size() == 1) {
                            console.println("Change " + focusedComponents.get(0).getName() + " strokeWeight!");
                            console.setInputText(focusedComponents.get(0).getWrapper().getStrokeWeight() + "");
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);

                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    float sw = focusedComponents.get(0).getWrapper().getStrokeWeight();
                                    try {
                                        sw = Float.parseFloat(input);
                                    } catch (Exception ignored) {
                                    }
                                    focusedComponents.get(0).getWrapper().setStrokeWeight(sw);
                                    pGuiManager.setFocusTo(drawBoard);
                                    console.setFocusable(false);
                                    console.setOnInputEnteredHandler(null);
                                    return true;
                                }
                            });
                        } else {
                            console.println("Change " + focusedComponents.size() + " components strokeWeight!");
                            console.setInputText("");
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);

                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    if (input.equals("")) {
                                        float sw;
                                        try {
                                            sw = Float.parseFloat(input);
                                        } catch (Exception e) {
                                            return false;
                                        }

                                        for (WrapperPainterObject focusedComponent : focusedComponents) {
                                            focusedComponent.getWrapper().setStrokeWeight(sw);
                                        }
                                    }

                                    console.setOnInputEnteredHandler(null);
                                    console.setFocusable(false);
                                    pGuiManager.setFocusTo(drawBoard);
                                    return true;
                                }
                            });
                        }
                    }
                }
                return false;
            }
        });

        pGuiManager.setOnMouseClickedHandler(new PGuiObject.OnMouseClickedHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                if (drawBoard.isThisOverMe(mouseX, mouseY)) {
                    if (componentInConstruction != null) {
                        return false;
                    }

                    //Focus
                    for (WrapperPainterObject wrapperPainterObject :
                            wrapperPainterObjectsManager.getWrapperPainterObjects()) {
                        EllipseWrapper constructionPoint =
                                wrapperPainterObject.getConstructionPointAtPosition(mouseX, mouseY);
                        if (wrapperPainterObject.isThisOverMe(mouseX, mouseY)) {
                            wrapperPainterObjectsManager.addFocusTo(wrapperPainterObject);
                            return true;
                        }
                        if (constructionPoint != null) {
                            wrapperPainterObjectsManager.addFocusTo(wrapperPainterObject);
                            wrapperPainterObject.setFocusedConstructionPoints(constructionPoint);
                            return true;
                        }
                    }
                    wrapperPainterObjectsManager.setFocusTo(null);
                }
                return false;
            }
        });

        pGuiManager.setOnMouseDraggedHandler(new PGuiObject.OnMouseDraggedHandler() {

            long lastUpdate = -1;

            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                if (wrapperPainterObjectsManager.getFocusedObjects().size() == 1) {
                    WrapperPainterObject focusedComponent =
                            wrapperPainterObjectsManager.getFocusedObjects().get(0);
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
                            focusedComponent.guideComponent(drawBoard);
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
                return false;
            }
        });

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

        drawBoard.setOnKeyTypedHandler(new PGuiObject.OnKeyTypedHandler() {
            @Override
            public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {
                if (pGuiManager.getFocusedPGuiObject() != null) {
                    if (pGuiManager.getFocusedPGuiObject().equals(console)) {
                        return false;
                    }
                }

                if (key == 'i' || key == 'I') {
                    File imageFile = FileTools.openFileDialog("Select a backgroud photo!!!");
                    if (imageFile != null) {
                        PImage image = loadImage(imageFile.getAbsolutePath());
                        if (image != null) {
                            drawBoard.setBackgroundImage(image);
                            console.println("Background image setted!!!");
                        }
                    }
                } else if (key == 'u' || key == 'U') {
                    drawBoard.drawBackgroundImage(!drawBoard.isDrawBackgroundImage());
                } else if (key == ',') {
                    wrapperPainterObjectsManager.moveToBack(wrapperPainterObjectsManager.getFocusedObjects());
                } else if (key == '.') {
                    wrapperPainterObjectsManager.moveToFront(wrapperPainterObjectsManager.getFocusedObjects());
                }
                return false;

            }
        });

        pointer = new Point.Float(drawBoard.getCloserGuideX(drawBoard.getX() + drawBoard.getWidth() / 2),
                drawBoard.getCloserGuideY(drawBoard.getY() + drawBoard.getHeight()));

        wrapperPainterObjectsManager = new WrapperPainterObjectsManager(this);

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
                ArrayList<WrapperPainterObject> wrapperPainterObjects =
                        wrapperPainterObjectsManager.getFocusedObjects();
                if (wrapperPainterObjects == null || wrapperPainterObjects.size() == 0) {
                    return false;
                }
                for (WrapperPainterObject wrapperPainterObject : wrapperPainterObjects) {
                    if (wrapperPainterObject.isALine()) {
                        LineWrapper lineWrapper = (LineWrapper) wrapperPainterObject.getWrapper();
                        lineWrapper.setStrokeColor(newColor);
                    } else if (wrapperPainterObject.isAPoint()) {
                        PointWrapper pointWrapper = (PointWrapper) wrapperPainterObject.getWrapper();
                        pointWrapper.setStrokeColor(newColor);
                    } else if (wrapperPainterObject.isAnEllipse()) {
                        EllipseWrapper ellipseWrapper = (EllipseWrapper) wrapperPainterObject.getWrapper();
                        ellipseWrapper.setFillColor(newColor);
                    } else if (!wrapperPainterObject.isAnImage()) {
                        RectangleWrapper rectangleWrapper = (RectangleWrapper) wrapperPainterObject.getWrapper();
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
        objectProperties = new MultiLineTextBox(10, toolsBox.getX(),
                bottomsGridBox.getY() + bottomsGridBox.getHeight(),
                toolsBox.getWidth(), totalPropertiesHeight, this) {
            @Override
            public void draw() {
                if (wrapperPainterObjectsManager.getFocusedObjects().size() > 0) {
                    setText(wrapperPainterObjectsManager.getFocusedObjects().get(0).
                            getDescription());
                }
                super.draw();
            }
        };


        guiProperties = new SingleLineTextBox("", objectProperties.getX(),
                objectProperties.getY() + objectProperties.getHeight(),
                objectProperties.getWidth(), objectProperties.getHeight() * 0.1F,
                this) {
            @Override
            public void draw() {
                guiProperties.setText("FR: " + frameRate);
                guiProperties.fixTextLength();
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
        newRectBtm.setOnMouseClickedHandler(ComponentConstructor.Rectangle.startConstructionHandler);

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
        newLineBtm.setOnMouseClickedHandler(ComponentConstructor.Line.startConstructionHandler);

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

    private static class ComponentConstructor {

        private static class Line {

            private static PGuiObject.OnMouseClickedHandler startConstructionHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            if (componentInConstruction != null) {
                                if (componentInConstruction.isALine()) {
                                    cancelConstruction();
                                    return true;
                                }
                                cancelConstruction();
                                console.println();
                            }

                            //Info
                            console.println("Building a Line?");
                            console.println("Click the drawBoard to set the first point!!!");

                            //New componentInConstruction
                            componentInConstruction = new ComponentInConstruction(WrapperPainterObject.Types.LINE);
                            constructionIcon = lineIcon;
                            wrapperPainterObjectsManager.setFocusTo(null);

                            //NextStep
                            pGuiManager.setFocusTo(drawBoard);
                            drawBoard.setOnMouseClickedHandler(firstPointHandler);

                            return true;
                        }
                    };

            private static PGuiObject.OnMouseClickedHandler firstPointHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            //FirstPoint
                            Point.Float firstPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                                    drawBoard.getContext().mouseY);
                            componentInConstruction.addConstructionPoint(firstPoint);

                            //Info
                            console.println("First Point set at " + firstPoint.toString());
                            console.println("Click the drawBoard again to set the second point!!!");

                            //NextStep
                            drawBoard.setOnMouseClickedHandler(secondPointHandler);
                            return true;
                        }
                    };

            private static PGuiObject.OnMouseClickedHandler secondPointHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            //SecondPoint
                            Point.Float secondPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                                    drawBoard.getContext().mouseY);

                            componentInConstruction.addConstructionPoint(secondPoint);

                            //Info
                            console.println("Second Point set at " + secondPoint.toString());
                            console.println("Now, type a new name in the console or accept the suggested by pressing enter!!!");

                            //Event Handler
                            drawBoard.setOnMouseClickedHandler(null);
                            console.setInputText(wrapperPainterObjectsManager.suggestNewName(WrapperPainterObject.Types.LINE));
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);
                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    //name
                                    String checkedName = wrapperPainterObjectsManager.formatNewName(input);
                                    wrapperPainterObjectsManager.addComponentName(checkedName);
                                    componentInConstruction.setName(checkedName);

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

        private static class Rectangle {

            private static PGuiObject.OnMouseClickedHandler startConstructionHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            if (componentInConstruction != null) {
                                if (componentInConstruction.isALine()) {
                                    cancelConstruction();
                                    return true;
                                }
                                cancelConstruction();
                                console.println();
                            }

                            //Info
                            console.println("Building a Rectangle?");
                            console.println("Click the drawBoard to set the first point!!!");

                            //New componentInConstruction
                            componentInConstruction = new ComponentInConstruction(WrapperPainterObject.Types.RECTANGLE);
                            constructionIcon = rectIcon;
                            wrapperPainterObjectsManager.setFocusTo(null);

                            //NextStep
                            pGuiManager.setFocusTo(drawBoard);
                            drawBoard.setOnMouseClickedHandler(firstPointHandler);

                            return true;
                        }
                    };

            private static PGuiObject.OnMouseClickedHandler firstPointHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            //FirstPoint
                            Point.Float firstPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                                    drawBoard.getContext().mouseY);
                            componentInConstruction.addConstructionPoint(firstPoint);

                            //Info
                            console.println("First Point set at " + firstPoint.toString());
                            console.println("Click the drawBoard again to set the second point!!!");

                            //NextStep
                            drawBoard.setOnMouseClickedHandler(secondPointHandler);
                            return true;
                        }
                    };

            private static PGuiObject.OnMouseClickedHandler secondPointHandler =
                    new PGuiObject.OnMouseClickedHandler() {
                        @Override
                        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {

                            //SecondPoint
                            Point.Float secondPoint = drawBoard.getCloserGuidedPoint(drawBoard.getContext().mouseX,
                                    drawBoard.getContext().mouseY);

                            componentInConstruction.addConstructionPoint(secondPoint);

                            //Info
                            console.println("Second Point set at " + secondPoint.toString());
                            console.println("Now, type a new name in the console or accept the suggested by pressing enter!!!");

                            //Event Handler
                            drawBoard.setOnMouseClickedHandler(null);
                            console.setInputText(wrapperPainterObjectsManager.suggestNewName(WrapperPainterObject.Types.RECTANGLE));
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);
                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    //name
                                    String checkedName = wrapperPainterObjectsManager.formatNewName(input);
                                    wrapperPainterObjectsManager.addComponentName(checkedName);
                                    componentInConstruction.setName(checkedName);

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

            public ComponentInConstruction(int type) {
                this.type = type;
                constructionPoints = new ArrayList<>();
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<Point.Float> getConstructionPoints() {
                return constructionPoints;
            }

            public void addConstructionPoint(Point.Float point) {
                constructionPoints.add(point);
            }

            public boolean isALine() {
                return getType() == WrapperPainterObject.Types.LINE;
            }

            public boolean isARectangle() {
                return getType() == WrapperPainterObject.Types.RECTANGLE;
            }

        }


        private static void cancelConstruction() {
            pGuiManager.setFocusTo(drawBoard);
            console.setOnInputEnteredHandler(null);
            console.setFocusable(false);
            drawBoard.setOnMouseClickedHandler(null);
            componentInConstruction = null;
            constructionIcon = null;
            console.println("Construction Cancelled!!!");
            console.println();
        }

        private static void finishConstruction() {
            pGuiManager.setFocusTo(drawBoard);
            WrapperPainterObject brantNew = constructDrawComponent();
            console.println("New component created: " + brantNew);
            console.println("Construction finished!!! Enjoy");
            console.println();
            wrapperPainterObjectsManager.addDrawComponent(brantNew);
            componentInConstruction = null;
            constructionIcon = null;

        }

        private static WrapperPainterObject constructDrawComponent() {

            if (componentInConstruction == null) {
                return null;
            }

            if (componentInConstruction.isALine()) {
                Point.Float p0 = componentInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = componentInConstruction.getConstructionPoints().get(1);
                LineWrapper lineWrapper = new LineWrapper(p0.x, p0.y, p1.x, p1.y, pGuiManager.getContext());
                return new LineWrapperPainterObject(lineWrapper,
                        componentInConstruction.name);
            } else if (componentInConstruction.isARectangle()) {
                Point.Float p0 = componentInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = componentInConstruction.getConstructionPoints().get(1);
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
                return new RectangleWrapperPainterObject(rectWrapper,
                        componentInConstruction.name);
            }

            return null;
        }


    }


}