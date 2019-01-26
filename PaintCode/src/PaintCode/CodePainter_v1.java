//package PaintCode;
//
//import BuiltIn.LayoutTools;
//import P2DPrimitiveWrappers.RectangleWrapper;
//import PGUIObject.*;
//import processing.core.PApplet;
//import processing.event.MouseEvent;
//
//import java.awt.*;
//import java.util.ArrayList;
//
//public class CodePainter_v1 extends PApplet {
//
//    // 53871722 Yenier, Hermano de Miguel 80CUC 3*
//    public static void main(String[] args) {
//        PApplet.main("PaintCode.CodePainter_v1");
//    }
//
//    //Main Window
//    private static final float f = 1.23F;
//    private static final int DW = (int) (640 * f);
//    private static final int DH = (int) (480 * f);
//    private int margin = (int) (Math.min(DW, DH) * 0.01);
//
//    //PGuiObjects
//    private static PEventGuiManager pGuiManager;
//    private static RectangleWrapper toolsBox;
//    private static ColorSelector colorSelector;
//    private static RectangleWrapper bottomsGridBox;
//    private static PGuiObject newRectBtm, newEllipseBtm, newLineBtm, newImageBtm, newTextBtn;
//    private static SingleLineTextBox[] objectPropertiesBoxes;
//    private static int propertiesCount = 10;
//    private static ZoomBox zoom;
//    private static Console console;
//    private static GuidedBoard drawBoard;
//
//
//    //App flow
//    //Focus
//    private boolean focusChanged = false;
//    PGuiObject focusedPPComponent;
//    ArrayList<PGuiObject> PGuiObjects;
//
//    @Override
//    public void settings() {
//        size(DW, DH);
//    }
//
//    @Override
//    public void setup() {
//        initializePGUIObjects();
//    }
//
//    @Override
//    public void draw() {
//        background(155);
//        drawGUIComponents();
//        updateProperties();
//        zoom.magnifyCenteredArea(mouseX, mouseY);
//    }
//
//    @Override
//    public void keyPressed() {
//        //        console.listenForKeyPressed();
//        //        if (console.isForcingUserForInput() || drawBoard.isForcingUserForInput()) {
//        //            return;
//        //        }
//        //        handleFunctionsKeyPressed();
//        //        if (focusedPPComponent.equals(console)) {
//        //            return;
//        //        }
//        //        zoom.listenForKeyPressed();
//        //        drawBoard.listenForKeyPressed();
//    }
//
//    @Override
//    public void mouseClicked() {
//    }
//
//    @Override
//    public void mouseWheel(MouseEvent event) {
//        if (console.isThisOverMe(mouseX, mouseY) && console.listeningForMouseWheel(event)) {
//            return;
//        } else if (drawBoard.isThisOverMe(mouseX, mouseY) || zoom.isThisOverMe(mouseX, mouseY)) {
//            zoom.listeningForMouseWheel(event);
//            return;
//        }
//    }
//
//    private void initializePGUIObjects() {
//
//        //Common Margin
//        drawBoard = new GuidedBoard(margin, margin,
//                (int) (width * 0.65), (int) (height * 0.7), margin, margin, this);
//
//        //PUIConsole
//        console = new Console(10, margin, drawBoard.getHeight() + 2 * margin,
//                drawBoard.getWidth(), height - (drawBoard.getHeight() + 3 * margin), this);
//        console.println("This is the console. It will show you the program flow!!!");
//
//
//        toolsBox = new RectangleWrapper(drawBoard.getWidth() + 2 * margin, margin,
//                width - (drawBoard.getWidth() + 3 * margin), height - 2 * margin, this);
//
//        //ZoomBox
//        zoom = new ZoomBox(toolsBox.getX(), toolsBox.getY(),
//                toolsBox.getWidth(), toolsBox.getWidth(), this);
//
//        //ColorSelector
//        int colorSelectorHeight = Math.min((int) (toolsBox.getHeight() * 0.1F), 20);
//        colorSelector = new ColorSelector(toolsBox.getX() + margin,
//                zoom.getY() + zoom.getHeight() + margin,
//                toolsBox.getWidth() - 2 * margin,
//                colorSelectorHeight, this);
//
//        //Bottoms
//        createBottoms();
//
//        //Properties
//        float firstPropertiesY = bottomsGridBox.getY() + bottomsGridBox.getHeight();
//        float totalPropertiesHeight = toolsBox.getY() +
//                toolsBox.getHeight() - bottomsGridBox.getY() - bottomsGridBox.getHeight();
//        float propertyHeight = totalPropertiesHeight / propertiesCount;
//        SingleLineTextBox propertyBox;
//        objectPropertiesBoxes = new SingleLineTextBox[propertiesCount];
//        for (int pi = 0; pi < propertiesCount; pi++) {
//            propertyBox = new SingleLineTextBox("Test", toolsBox.getX(),
//                    firstPropertiesY + pi * propertyHeight, toolsBox.getWidth(),
//                    propertyHeight, this);
//            propertyBox.setFocusable(false);
//            objectPropertiesBoxes[pi] = propertyBox;
//        }
//
//
//        pGuiManager = new PEventGuiManager(this);
//        pGuiManager.addPGUIObject(colorSelector);
//        pGuiManager.addPGUIObject(newImageBtm);
//        pGuiManager.addPGUIObject(newEllipseBtm);
//        pGuiManager.addPGUIObject(newLineBtm);
//        pGuiManager.addPGUIObject(newRectBtm);
//        pGuiManager.addPGUIObject(newTextBtn);
//        pGuiManager.addPGUIObject(console);
//        pGuiManager.addPGUIObject(zoom);
//        pGuiManager.addPGUIObject(drawBoard);
//        for (SingleLineTextBox objectPropertiesBox : objectPropertiesBoxes) {
//            pGuiManager.addPGUIObject(objectPropertiesBox);
//        }
//
//    }
//
//    private void createBottoms() {
//        //BottomGrid
//        int bottomsGridColumnsCount = 5;
//        int bottomsGridRowsCount = 1;
//        bottomsGridBox = new RectangleWrapper(toolsBox.getX(),
//                colorSelector.getY() + colorSelector.getHeight(),
//                toolsBox.getWidth(),
//                (colorSelector.getHeight() + 4 * margin) * bottomsGridRowsCount,
//                this);
//        Point bottomsDimension = LayoutTools.getGridItemDimention(bottomsGridRowsCount, bottomsGridColumnsCount,
//                margin, margin, (int) toolsBox.getWidth(), (int) bottomsGridBox.getHeight());
//        Point.Float[] bottomsPositions = LayoutTools.getGridItemPositions(bottomsGridBox.getX(), bottomsGridBox.getY(),
//                bottomsGridRowsCount, bottomsGridColumnsCount, margin, margin, bottomsDimension.x,
//                bottomsDimension.y);
//
//        //New Rect
//        newRectBtm = new PGuiObject(bottomsPositions[0].x, bottomsPositions[0].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = context.random(500);
//            float r1 = this.getWidth() / 2;
//            float r2 = this.getHeight() / 2;
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.stroke(0);
//                context.strokeWeight(1.0F);
//                context.fill(colorSelector.getSelectedColor());
//                context.rect(this.getX() + this.getWidth() / 2 - r1 / 2,
//                        y + this.getHeight() / 2 - r2 / 2, r1, r2);
//                randomizeLook();
//
//            }
//
//            private void randomizeLook() {
//                r1 = (int) Math.min(r1 * noise(n) + this.getWidth() / 4, this.getWidth() * 0.8F);
//                r2 = (int) Math.min(r2 * noise(n + 100) + this.getHeight() / 4, this.getHeight() * 0.8F);
//                n += 0.005F;
//            }
//
//        };
//
//        //newEllipseBtm
//        newEllipseBtm = new PGuiObject(bottomsPositions[1].x, bottomsPositions[1].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = context.random(500);
//            float r1 = this.getWidth() / 2;
//            float r2 = this.getHeight() / 2;
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.stroke(0);
//                context.strokeWeight(1.0F);
//                context.fill(colorSelector.getSelectedColor());
//                context.ellipse(this.getX() + this.getWidth() / 2,
//                        y + this.getHeight() / 2, r1, r2);
//                randomizeLook();
//
//            }
//
//            private void randomizeLook() {
//                r1 = (int) Math.min(r1 * noise(n) + this.getWidth() / 4, this.getWidth() * 0.8F);
//                r2 = (int) Math.min(r2 * noise(n + 100) + this.getHeight() / 4, this.getHeight() * 0.8F);
//                n += 0.005F;
//            }
//
//        };
//
//        //newLineBtm
//        newLineBtm = new PGuiObject(bottomsPositions[2].x, bottomsPositions[2].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = context.random(500);
//            float rx1, rx2, ry1, ry2;
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.stroke(colorSelector.getSelectedColor());
//                randomizeLook();
//                context.line(rx1, ry1, rx2, ry2);
//
//            }
//
//            private void randomizeLook() {
//                strokeWeight(Math.max(noise(n) + 2, 1));
//                rx1 = x + width / 2 - width / 3 * noise(n) + 1;
//                ry1 = y + this.getHeight() / 2 - this.getHeight() / 3 * noise(n) + 1;
//                rx2 = x + width / 2 + width / 3 * noise(n) - 1;
//                ry2 = y + this.getHeight() / 2 + this.getHeight() / 3 * noise(n) - 1;
//                n += 0.005F;
//            }
//
//        };
//
//        //newImageBtm
//        newImageBtm = new PGuiObject(bottomsPositions[3].x, bottomsPositions[3].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = random(345);
//            int rectCount = 5;
//            float rectW = getWidth() / (float) rectCount;
//            float rectH = getHeight() / (float) rectCount;
//            Point.Float[] rectPositions = LayoutTools.getGridItemPositions(getX() + 1, getY() + 1, rectCount, rectCount,
//                    0, 0, getWidth() - 1.0F, getHeight() - 1.0F);
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.noStroke();
//                for (int i = 0; i < rectPositions.length; i++) {
//                    context.fill(colorSelector.getSelectedColor(), noise(n + i) * 255);
//                    context.rect(rectPositions[i].x, rectPositions[i].y, rectW, rectH);
//                    n += 0.001;
//                }
//            }
//
//        };
//
//        //newTextBtn
//        newTextBtn = new PGuiObject(bottomsPositions[4].x, bottomsPositions[4].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = 0F;
//            String text = "Tools";
//            SingleLineTextBox textBox = new SingleLineTextBox("Tools", getX() + 1, getY() + 1,
//                    getWidth() - 2, getHeight() - 2, context);
//
//            @Override
//            public void draw() {
//                super.draw();
//                textBox.drawStroke(false);
//                textBox.setStrokeColor(colorSelector.getSelectedColor());
//                textBox.setText(text);
//                textBox.setTextSize((int) (getHeight() * 0.3 + context.noise(n) * getHeight() * 0.5));
//                textBox.fixTextLength();
//                textBox.draw();
//                n += 0.01;
//
//            }
//
//        };
//    }
//
//    private void updateProperties() {
//
//        //Clean
//        for (SingleLineTextBox objectPropertiesBox : objectPropertiesBoxes) {
//            objectPropertiesBox.setText("");
//        }
//
//        //Frame Rate
//        objectPropertiesBoxes[propertiesCount - 1].setText("FR: " + frameRate);
//
//        //Set Properties
//        //focusedPPComponent.loadProperties();
//    }
//
//    private void drawGUIComponents() {
//        drawBoard.draw();
//        console.draw();
//        toolsBox.draw();
//        newRectBtm.draw();
//        newEllipseBtm.draw();
//        newLineBtm.draw();
//        newImageBtm.draw();
//        newTextBtn.draw();
//        colorSelector.draw();
//        zoom.draw();
//        for (SingleLineTextBox objectPropertiesBox : objectPropertiesBoxes) {
//            objectPropertiesBox.draw();
//        }
//    }
//
//
//}