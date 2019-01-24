//package PaintCode;
//
//import BuiltIn.LayoutTools;
//import Common.Tools;
//import P2DPrimitiveWrappers.*;
//import processing.core.PApplet;
//import processing.event.MouseEvent;
//
//import java.awt.*;
//import java.awt.event.KeyEvent;
//
//public class CodePainter_v1 extends GUIHandler {
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
//    //ToolsBox
//    private static RectangleWrapper toolsBox;
//    private static GUIComponent<ColorSelector> colorSelector;
//    private static RectangleWrapper bottomsGrid;
//    private static GUIComponent<RectangleWrapper> newRectBtm, newEllipseBtm, newLineBtm, newImageBtm, newTextBtn;
//    private static SingleLineTextBox[] objectPropertiesBoxes;
//    private static int propertiesCount = 10;
//    private static ZoomComponent zoom;
//
//    //PUIConsole
//    private static Console console;
//    private boolean focusChanged = false;
//
//    //DrawBoard
//    private static DrawBoard drawBoard;
//
//
//    //App flow
//    //Focus
//    GUIComponent focusedPPComponent;
//
//    //DrawComponents
//    DrawComponent drawComponentBuffer;
//
//    @Override
//    public void settings() {
//        size(DW, DH);
//    }
//
//    @Override
//    public void setup() {
//        initializePPGuiComponents();
//        focusedPPComponent = drawBoard;
//    }
//
//    @Override
//    public void draw() {
//        background(155);
//        drawGUIComponents();
//        drawFocus();
//        updateProperties();
//        handleZoomMagnify();
//    }
//
//    @Override
//    public void keyPressed() {
//        console.listenForKeyPressed();
//        if (console.isForcingUserForInput() || drawBoard.isForcingUserForInput()) {
//            return;
//        }
//        handleFunctionsKeyPressed();
//        if (focusedPPComponent.equals(console)) {
//            return;
//        }
//        zoom.listenForKeyPressed();
//        drawBoard.listenForKeyPressed();
//    }
//
//    @Override
//    public void mouseClicked() {
//        drawBoard.listeningForUserInput();
//        if (console.isForcingUserForInput()) {
//            return;
//        }
//        if (drawBoard.isForcingUserForInput()) {
//            return;
//        }
//        drawBoard.listeningForUserInput();
//        drawBoard.listenForMouseClick();
//        console.listenForMouseClick();
//        newRectBtm.listenForMouseClick();
//        newTextBtn.listenForMouseClick();
//        newLineBtm.listenForMouseClick();
//        newEllipseBtm.listenForMouseClick();
//        newImageBtm.listenForMouseClick();
//        zoom.listenForMouseClick();
//    }
//
//    @Override
//    public void mouseWheel(MouseEvent event) {
//
//        if (console.isForcingUserForInput()) {
//            return;
//        }
//        int wheelCount = event.getCount();
//
//        if (console.isThisOverMe(mouseX, mouseY)) {
//            if (wheelCount > 0) {
//                for (int t = 0; t < wheelCount && t < console.getGComponent().getMaxVisibleLinesCount(); t++) {
//                    console.getGComponent().scrollDown();
//                }
//            } else {
//                wheelCount *= -1;
//                for (int t = 0; t < wheelCount && t < console.getGComponent().getMaxVisibleLinesCount(); t++) {
//                    console.getGComponent().scrollUp();
//                }
//            }
//        } else if (drawBoard.isThisOverMe(mouseX, mouseY) || zoom.isThisOverMe(mouseX, mouseY)) {
//            if (wheelCount > 0) {
//                for (int t = 0; t < wheelCount && t < 10; t++) {
//                    zoom.getGComponent().setFactor(zoom.getGComponent().getFactor() - 0.01F);
//                }
//            } else {
//                wheelCount *= -1;
//                for (int t = 0; t < wheelCount && t < 10; t++) {
//                    zoom.getGComponent().setFactor(zoom.getGComponent().getFactor() + 0.01F);
//                }
//            }
//        }
//
//    }
//
//    private void initializePPGuiComponents() {
//        //Common Margin
//
//        drawBoard = new DrawBoard(new GuidedBoard(margin, margin,
//                (int) (width * 0.65), (int) (height * 0.7), margin, margin, this));
//
//        //PUIConsole
//        console = new Console(
//                new P2DPrimitiveWrappers.Console(10, margin, drawBoard.getGComponent().getHeight() + 2 * margin,
//                        drawBoard.getGComponent().getWidth(), height - (drawBoard.getGComponent().getHeight() + 3 * margin), this));
//        console.setLastLine("This is the console. It will show you the program flow!!!");
//        console.println();
//
//
//        toolsBox = new RectangleWrapper(drawBoard.getGComponent().getWidth() + 2 * margin, margin,
//                width - (drawBoard.getGComponent().getWidth() + 3 * margin), height - 2 * margin, this);
//
//        //ZoomBox
//        zoom = new ZoomComponent(new ZoomBox(toolsBox.getX(), toolsBox.getY(),
//                toolsBox.getWidth(), toolsBox.getWidth(), this));
//
//        //ColorSelector
//        int colorSelectorHeight = Math.min((int) (toolsBox.getHeight() * 0.1F), 20);
//        colorSelector = new GUIComponent<ColorSelector>(new ColorSelector(toolsBox.getX() + margin,
//                zoom.getY() + zoom.getGComponent().getHeight() + margin,
//                toolsBox.getWidth() - 2 * margin,
//                colorSelectorHeight, this)) {
//
//
//            @Override
//            public void listenForMouseClick() {
//                colorSelector.getGComponent().setSelectedColor(mouseX, mouseY);
//            }
//        };
//
//        //Bottoms
//        createBottoms();
//
//        //Properties
//        float firstPropertiesY = bottomsGrid.getY() + bottomsGrid.getHeight();
//        float totalPropertiesHeight = toolsBox.getY() +
//                toolsBox.getHeight() - bottomsGrid.getY() - bottomsGrid.getHeight();
//        float propertyHeight = totalPropertiesHeight / propertiesCount;
//        SingleLineTextBox propertyBox;
//        objectPropertiesBoxes = new SingleLineTextBox[propertiesCount];
//        for (int pi = 0; pi < propertiesCount; pi++) {
//            propertyBox = new SingleLineTextBox("Test", toolsBox.getX(),
//                    firstPropertiesY + pi * propertyHeight, toolsBox.getWidth(),
//                    propertyHeight, this);
//            propertyBox.setText("Tools: " + pi);
//            objectPropertiesBoxes[pi] = propertyBox;
//        }
//
//
//    }
//
//    private void createBottoms() {
//        //BottomGrid
//        int bottomsGridColumnsCount = 5;
//        int bottomsGridRowsCount = 1;
//        bottomsGrid = new RectangleWrapper(toolsBox.getX(),
//                colorSelector.getY() + colorSelector.getGComponent().getHeight(),
//                toolsBox.getWidth(),
//                (colorSelector.getGComponent().getHeight() + 4 * margin) * bottomsGridRowsCount,
//                this);
//        Point bottomsDimension = LayoutTools.getGridItemDimention(bottomsGridRowsCount, bottomsGridColumnsCount,
//                margin, margin, (int) toolsBox.getWidth(), (int) bottomsGrid.getHeight());
//        Point.Float[] bottomsPositions = LayoutTools.getGridItemPositions(bottomsGrid.getX(), bottomsGrid.getY(),
//                bottomsGridRowsCount, bottomsGridColumnsCount, margin, margin, bottomsDimension.x,
//                bottomsDimension.y);
//
//        //New Rect
//        newRectBtm = new GUIComponent<RectangleWrapper>(
//                new RectangleWrapper(bottomsPositions[0].x, bottomsPositions[0].y,
//                        bottomsDimension.x, bottomsDimension.y, this) {
//
//                    float n = context.random(500);
//                    float r1 = this.getWidth() / 2;
//                    float r2 = this.getHeight() / 2;
//
//                    @Override
//                    public void draw() {
//                        super.draw();
//                        context.drawStroke(0);
//                        context.strokeWeight(1.0F);
//                        context.fill(colorSelector.getGComponent().getSelectedColor());
//                        context.rect(this.getX() + this.getWidth() / 2 - r1 / 2,
//                                this.getY() + this.getHeight() / 2 - r2 / 2, r1, r2);
//                        randomizeLook();
//
//                    }
//
//                    private void randomizeLook() {
//                        r1 = (int) Math.min(r1 * noise(n) + this.getWidth() / 4, this.getWidth() * 0.8F);
//                        r2 = (int) Math.min(r2 * noise(n + 100) + this.getHeight() / 4, this.getHeight() * 0.8F);
//                        n += 0.005F;
//                    }
//
//                }) {
//            @Override
//            public void listenForMouseClick() {
//                DrawComponent<RectangleWrapper> newRect = null;
//                DrawComponent.drawComponentCreationRoutine(newRect, context);
//            }
//        };
//
//        //newEllipseBtm
//        newEllipseBtm = new GUIComponent<RectangleWrapper>(new RectangleWrapper(bottomsPositions[1].x, bottomsPositions[1].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = context.random(500);
//            float r1 = this.getWidth() / 2;
//            float r2 = this.getHeight() / 2;
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.drawStroke(0);
//                context.strokeWeight(1.0F);
//                context.fill(colorSelector.getGComponent().getSelectedColor());
//                context.ellipse(this.getX() + this.getWidth() / 2,
//                        this.getY() + this.getHeight() / 2, r1, r2);
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
//        });
//
//        //newLineBtm
//        newLineBtm = new GUIComponent<RectangleWrapper>(new RectangleWrapper(bottomsPositions[2].x, bottomsPositions[2].y,
//                bottomsDimension.x, bottomsDimension.y, this) {
//
//            float n = context.random(500);
//            float rx1, rx2, ry1, ry2;
//
//            @Override
//            public void draw() {
//                super.draw();
//                context.drawStroke(colorSelector.getGComponent().getSelectedColor());
//                randomizeLook();
//                context.line(rx1, ry1, rx2, ry2);
//
//            }
//
//            private void randomizeLook() {
//                strokeWeight(Math.max(noise(n) + 2, 1));
//                rx1 = this.getX() + this.getWidth() / 2 - this.getWidth() / 3 * noise(n) + 1;
//                ry1 = this.getY() + this.getHeight() / 2 - this.getHeight() / 3 * noise(n) + 1;
//                rx2 = this.getX() + this.getWidth() / 2 + this.getWidth() / 3 * noise(n) - 1;
//                ry2 = this.getY() + this.getHeight() / 2 + this.getHeight() / 3 * noise(n) - 1;
//                n += 0.005F;
//            }
//
//        });
//
//        //newImageBtm
//        newImageBtm = new GUIComponent<RectangleWrapper>(new RectangleWrapper(bottomsPositions[3].x, bottomsPositions[3].y,
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
//                    context.fill(colorSelector.getGComponent().getSelectedColor(), noise(n + i) * 255);
//                    context.rect(rectPositions[i].x, rectPositions[i].y, rectW, rectH);
//                    n += 0.001;
//                }
//            }
//
//        });
//
//        //newTextBtn
//        newTextBtn = new GUIComponent<RectangleWrapper>(new RectangleWrapper(bottomsPositions[4].x, bottomsPositions[4].y,
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
//                textBox.setStrokeColor(colorSelector.getGComponent().getSelectedColor());
//                textBox.setText(text);
//                textBox.setTextSize((int) (getHeight() * 0.3 + context.noise(n) * getHeight() * 0.5));
//                textBox.fixTextLength();
//                textBox.draw();
//                n += 0.01;
//
//            }
//
//        });
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
//    private void handleZoomMagnify() {
//        zoom.getGComponent().magnifyCenteredArea(zoom.getMx(), zoom.getMy());
//    }
//
//    private void drawFocus() {
//
//        noFill();
//        drawStroke(0);
//        strokeWeight(6);
//        rect(focusedPPComponent.getX(), focusedPPComponent.getY(),
//                focusedPPComponent.getWidth(), focusedPPComponent.getHeight());
//        drawStroke(255);
//        strokeWeight(3);
//        rect(focusedPPComponent.getX(), focusedPPComponent.getY(),
//                focusedPPComponent.getWidth(), focusedPPComponent.getHeight());
//
//    }
//
//    private void drawGUIComponents() {
//        drawBoard.draw();
//        console.draw();
//        toolsBox.draw();
//        newRectBtm.draw();
//        newEllipseBtm.draw();
//        newLineBtm.draw();
//        colorSelector.draw();
//        newImageBtm.draw();
//        newTextBtn.draw();
//        zoom.draw();
//        for (SingleLineTextBox objectPropertiesBox : objectPropertiesBoxes) {
//            objectPropertiesBox.draw();
//        }
//    }
//
//    private void handleFunctionsKeyPressed() {
//        if (keyCode == KeyEvent.VK_F1) {
//            console.waitForUserCommand();
//        } else if (keyCode == KeyEvent.VK_F2) {
//            setFocusTo(drawBoard);
//        } else if (keyCode == KeyEvent.VK_F3) {
//            setFocusTo(newRectBtm);
//        }
//    }
//
//    private void setFocusTo(GUIComponent component) {
//        focusChanged = !component.equals(focusedPPComponent);
//        focusedPPComponent = component;
//    }
//
//    //    private class PPComponent<T extends Rectangle> extends Rectangle {
//    //
//    //        private T component;
//    //
//    //        public PPComponent(T rectangle) {
//    //            super(rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
//    //                    rectangle.getHeight(), rectangle.getContext());
//    //            this.component = rectangle;
//    //        }
//    //
//    //        public final T getGObject() {
//    //            return component;
//    //        }
//    //
//    //        public void loadProperties(){}
//    //
//    //        public final void listenForClick() {
//    //            if (isThisOverMe(mouseX, mouseY)) {
//    //                onClick();
//    //            }
//    //        }
//    //
//    //        @Override
//    //        public void listenForKeyPressed() {
//    //        }
//    //
//    //        public void onClick() {
//    //            setFocusTo(this);
//    //        }
//    //
//    //        @Override
//    //        public void draw() {
//    //            getGObject().draw();
//    //        }
//    //    }
//
//    private class ZoomComponent extends GUIComponent<ZoomBox> {
//
//        int mx;
//        int my;
//
//        ZoomComponent(ZoomBox component) {
//            super(component);
//            mx = (int)((component.getWidth() / 2) + component.getX());
//            my = (int)((component.getHeight() / 2) + component.getY());
//            component.setFactor(0.81F);
//        }
//
//        public int getMx() {
//            return mx;
//        }
//
//        public void setMx(int mx) {
//            this.mx = mx;
//        }
//
//        public int getMy() {
//            return my;
//        }
//
//        public void setMy(int my) {
//            this.my = my;
//        }
//
//        @Override
//        public void listenForKeyPressed() {
//            if (key == 'z' || key == 'Z') {
//                zoom.setMx(mouseX);
//                zoom.setMy(mouseY);
//            } else if (key == 'x' || key == 'X') {
//                zoom.getGComponent().setFactor(zoom.getGComponent().getFactor() / 2);
//            } else if (key == 'c' || key == 'C') {
//                zoom.getGComponent().setFactor(zoom.getGComponent().getFactor() * 2);
//            }
//        }
//    }
//
//    private class Console extends GUIComponent<P2DPrimitiveWrappers.Console> {
//
//
//        private boolean forcingUserForInput = false;
//        private String PROMPT = "$: ";
//        private Tools.KeyScanner scanner;
//        private UserInputHandler.Console inputHandler;
//        private UserInputHandler.Console consoleCommandDecoder = new UserInputHandler.Console() {
//            @Override
//            public void handleUserInput(String userInput) {
//                if (userInput.equals("")) {
//                    return;
//                }
//                if (userInput.toLowerCase().equals("clear")) {
//                    getGComponent().clear();
//                } else {
//                    println(userInput + " is not a valid command!!!");
//                }
//            }
//        };
//
//        public Console(P2DPrimitiveWrappers.Console component) {
//            super(component);
//            scanner = new Tools.KeyScanner();
//        }
//
//        @Override
//        public void listenForMouseClick() {
//            inputHandler = consoleCommandDecoder;
//            //Scroll control
//            if (mouseY > console.getY() + console.getHeight() / 2) {
//                console.getGComponent().scrollDown();
//            } else {
//                console.getGComponent().scrollUp();
//            }
//        }
//
//        public final boolean isForcingUserForInput() {
//            return forcingUserForInput;
//        }
//
//        public final void askAndForceUserInput(String question, UserInputHandler.Console userInputHandler) {
//            setFocusTo(this);
//            forcingUserForInput = true;
//            inputHandler = userInputHandler;
//            scanner.clear();
//            console.setLastLine("");
//            console.println(question);
//        }
//
//        public final void forceUserForInput(boolean force) {
//            forcingUserForInput = force;
//        }
//
//        @Override
//        public void listenForKeyPressed() {
//            if (focusedPPComponent.equals(console)) {
//                scanner.scan(key);
//                if (scanner.newLineJustHappen()) {
//                    console.println();
//                    inputHandler.handleUserInput(scanner.getLastLine());
//                    scanner.clear();
//                    inputHandler = consoleCommandDecoder;
//                } else {
//                    console.setLastLine(scanner.getBufferedLine());
//                }
//            }
//        }
//
//        public final void waitForUserCommand() {
//            setFocusTo(this);
//            inputHandler = consoleCommandDecoder;
//        }
//
//        public final void println() {
//            getGComponent().println();
//        }
//
//        public final void println(String s) {
//            getGComponent().println(s);
//        }
//
//        public final void setLastLine(String s) {
//            getGComponent().setLastLine(s);
//        }
//
//
//    }
//
//    private class DrawBoard extends GUIComponent<GuidedBoard> {
//
//        private UserInputHandler.DrawBoard customUserInputHandler;
//        private boolean forcingUserForInput = false;
//
//        public DrawBoard(GuidedBoard component) {
//            super(component);
//        }
//
//        public void askAndForceUserInput(String question, UserInputHandler.DrawBoard customUserInputHandler) {
//            this.customUserInputHandler = customUserInputHandler;
//            forcingUserForInput = true;
//            console.setLastLine("");
//            console.println(question);
//        }
//
//        public void listeningForUserInput() {
//            if (forcingUserForInput) {
//                customUserInputHandler.handleUserInput(mouseX, mouseY);
//            }
//        }
//
//        public boolean isForcingUserForInput() {
//            return forcingUserForInput;
//        }
//
//        public void forceUserForInput(boolean forcingUserForInput) {
//            this.forcingUserForInput = forcingUserForInput;
//        }
//    }
//
//    private abstract static class UserInputHandler {
//
//        public static abstract class Console {
//            public abstract void handleUserInput(String userInput);
//        }
//
//        public static abstract class DrawBoard {
//            public abstract void handleUserInput(int x, int y);
//        }
//    }
//
//    private static abstract class DrawComponent<T extends P2DPrimitiveWrapper> extends P2DPrimitiveWrapper {
//
//        private T gObject;
//
//        public DrawComponent(T gObject) {
//            super(gObject.getX(), gObject.getY(), gObject.getContext());
//            this.gObject = gObject;
//        }
//
//        public static void drawComponentCreationRoutine(DrawComponent<RectangleWrapper> rect, PApplet context) {
//            drawBoard.askAndForceUserInput("Crating a new rectangle!!! Click in the drawBoard to set the left" +
//                    "-top corner of the rectangle!!!", new UserInputHandler.DrawBoard() {
//                @Override
//                public void handleUserInput(int x, int y) {
//                    if (drawBoard.isThisOverMe(context.mouseX, context.mouseY)) {
//                        console.println("Top-left corner set at: " +
//                                x + "," + y);
//                        drawBoard.askAndForceUserInput("Click again to set the bottom-right corner of the rectangle!!!",
//                                new UserInputHandler.DrawBoard() {
//                                    @Override
//                                    public void handleUserInput(int x, int y) {
//                                        if (drawBoard.isThisOverMe(x, y)) {
//                                            console.println("Rectangle crated!!! Bottom-Right corner at: " +
//                                                    x + "," + y);
//                                            drawBoard.forceUserForInput(false);
//                                            console.askAndForceUserInput("Put a name for this item: ", itemRenameHandler);
//                                            console.println("Item name set to: " + renameHandlerOutputName);
//                                        }
//                                    }
//                                });
//                    }
//                }
//            });
//
//        }
//    }
//
//    private static String renameHandlerOutputName;
//    private static final UserInputHandler.Console itemRenameHandler = new UserInputHandler.Console() {
//
//        @Override
//        public void handleUserInput(String userInput) {
//            renameHandlerOutputName = userInput;
//            console.forceUserForInput(false);
//        }
//
//    };
//
//}