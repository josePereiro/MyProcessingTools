package WrapperPainter;

import BuiltIn.LayoutTools;
import Common.Tools;
import P2DPrimitiveWrappers.EllipseWrapper;
import P2DPrimitiveWrappers.LineWrapper;
import P2DPrimitiveWrappers.PointWrapper;
import P2DPrimitiveWrappers.RectangleWrapper;
import PGUIObject.*;
import WrapperPainter.DrawComponent.Types;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.util.ArrayList;

public class WrapperPainter extends PApplet {

    // 53871722 Yenier, Hermano de Miguel 80CUC 3*
    public static void main(String[] args) {
        PApplet.main("WrapperPainter.CodePainter_v1");
    }

    //Main Window
    private static final float f = 1.23F;
    private static final int DW = (int) (640 * f);
    private static final int DH = (int) (480 * f);
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
    private static DrawComponentManager drawComponentManager;
    private static ComponentConstructor.ComponentInConstruction componentInConstruction;
    private static RectangleWrapper constructionIcon;

    //Zoom
    private int mx, my;

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
        drawComponentManager.draw();
        drawComponentInConstruction();

        drawPointer();
        zoom.magnifyCenteredArea(mx, my);
        drawFloatingIcon();


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
        drawComponentManager.getOnMouseClickedHandler().handlePEvent(null, null);
    }

    @Override
    public void mouseDragged() {
        drawComponentManager.getOnMouseDraggedHandler().handlePEvent(null, null);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        pGuiManager.listeningForKeyPressed(event);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        drawComponentManager.getOnKeyReleasedHandler().handlePEvent(null, null);
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
                if (keyCode == java.awt.event.KeyEvent.VK_F5) {
                    drawComponentManager.guideComponents(drawBoard);
                    return true;
                }


                return false;
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
                } else if (key == 'f' || key == 'F') {
                    float d = Math.max(drawBoard.getDx(), drawBoard.getDy());
                    drawBoard.setDx(d);
                    drawBoard.setDy(d);
                    return true;
                } else if (key == 'z' || key == 'Z') {
                    mx = mouseX;
                    my = mouseY;
                    return true;
                } else if (key == '1') {
                    ComponentConstructor.Line.startConstructionHandler.handlePEvent(null, newLineBtm);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void initializeConsole() {
        console = new Console(10, margin, drawBoard.getHeight() + 2 * margin,
                drawBoard.getWidth(), height - (drawBoard.getHeight() + 3 * margin), this);
        console.setFocusable(false);
        console.println("This is the console. It will show you the program flow!!!");
    }

    private void initializeDrawBoard() {
        drawBoard = new GuidedBoard(margin, margin,
                (int) (width * 0.65), (int) (height * 0.7), margin, margin, this);

        pointer = new Point.Float(drawBoard.getCloserGuideX(drawBoard.getX() + drawBoard.getWidth() / 2),
                drawBoard.getCloserGuideY(drawBoard.getY() + drawBoard.getHeight()));

        drawComponentManager = new DrawComponentManager(drawBoard.getX(), drawBoard.getY(), drawBoard.getWidth(),
                drawBoard.getHeight(), this);

        drawComponentManager.setOnMouseClickedHandler(new PGuiObject.OnMouseClickedHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                if (drawComponentManager.isThisOverMe(mouseX, mouseY)) {
                    //Focus
                    for (DrawComponent drawComponent : drawComponentManager.getDrawComponents()) {
                        if (drawComponent.isThisOverMe(mouseX, mouseY) ||
                                drawComponent.getConstructionPointAtPosition(mouseX, mouseY) != null) {
                            drawComponentManager.setFocusTo(drawComponent);
                            return true;
                        }
                    }
                    drawComponentManager.setFocusTo(null);
                }
                return false;
            }
        });

        drawComponentManager.setOnMouseDraggedHandler(new PGuiObject.OnMouseDraggedHandler() {
            @Override
            public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
                DrawComponent focusedComponent = drawComponentManager.getFocusedComponent();
                if (focusedComponent != null) {

                    EllipseWrapper constructionPoint =
                            focusedComponent.getConstructionPointAtPosition(mouseX, mouseY);

                    if (constructionPoint != null) {
                        constructionPoint.setX(mouseX);
                        constructionPoint.setY(mouseY);
                        focusedComponent.rebuild();
                        focusedComponent.guideComponent(drawBoard);
                    }

                }
                mx = mouseX;
                my = mouseY;
                return true;

            }
        });

        drawComponentManager.setOnFocusChangedHandler(new DrawComponentManager.OnFocusChangedHandler() {
            @Override
            public void handlePEvent(DrawComponent drawComponent) {
                if (drawComponent == null) {
                    return;
                }
                if (drawComponent.isALine()) {
                    LineWrapper lineWrapper = (LineWrapper) drawComponent.getWrapper();
                    colorSelector.setSelectedColor(lineWrapper.getStrokeColor());
                } else if (drawComponent.isAPoint()) {
                    PointWrapper pointWrapper = (PointWrapper) drawComponent.getWrapper();
                    colorSelector.setSelectedColor(pointWrapper.getStrokeColor());
                } else if (drawComponent.isAnEllipse()) {
                    EllipseWrapper ellipseWrapper = (EllipseWrapper) drawComponent.getWrapper();
                    colorSelector.setSelectedColor(ellipseWrapper.getFillColor());
                } else if (!drawComponent.isAnImage()) {
                    RectangleWrapper rectangleWrapper = (RectangleWrapper) drawComponent.getWrapper();
                    colorSelector.setSelectedColor(rectangleWrapper.getFillColor());
                }


            }
        });

        drawComponentManager.setOnKeyReleasedHandler(new PGuiObject.OnKeyReleasedHandler() {
            @Override
            public boolean handlePEvent(KeyEvent event, PGuiObject pGuiObject) {

                if (pGuiManager.getFocusedPGuiObject().equals(console)) {
                    return false;
                }
                if (keyCode == 9) {
                    drawComponentManager.focusNext();
                    return true;
                }

                DrawComponent focusedComponent =
                        drawComponentManager.getFocusedComponent();
                if (focusedComponent == null) {
                    return true;
                }

                float dx = drawBoard.getDx();
                float dy = drawBoard.getDy();
                if (keyCode == 37) {
                    moveComponent(focusedComponent, -dx, 0);
                } else if (keyCode == 38) {
                    moveComponent(focusedComponent, 0, -dy);
                } else if (keyCode == 39) {
                    moveComponent(focusedComponent, dx, 0);
                } else if (keyCode == 40) {
                    moveComponent(focusedComponent, 0, dy);
                } else if (keyCode == Tools.KeyCodes.F1) {
                    console.println("Rename " + focusedComponent.getName());
                    console.setInputText(focusedComponent.getName());
                    console.setFocusable(true);
                    pGuiManager.setFocusTo(console);

                    console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                        @Override
                        public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                            focusedComponent.setName(
                                    drawComponentManager.formatNewName(input));
                            pGuiManager.setFocusTo(drawBoard);
                            console.setFocusable(false);
                            console.setOnInputEnteredHandler(null);
                            return true;
                        }
                    });
                } else if (key == 'w' || key == 'W') {
                    console.println("Change " + focusedComponent.getName() + " strokeWeight!");
                    console.setInputText(focusedComponent.getWrapper().getStrokeWeight() + "");
                    console.setFocusable(true);
                    pGuiManager.setFocusTo(console);

                    console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                        @Override
                        public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                            focusedComponent.setName(
                                    drawComponentManager.formatNewName(input));
                            pGuiManager.setFocusTo(drawBoard);
                            console.setFocusable(false);
                            console.setOnInputEnteredHandler(null);
                            return true;
                        }
                    });
                }

                return true;
            }

            private boolean isInsideDrawBoard(DrawComponent drawComponent, float dx, float dy) {
                for (EllipseWrapper constructionPoint : drawComponent.constructionPoints) {
                    if (!drawBoard.isThisOverMe(constructionPoint.getX() + dx,
                            constructionPoint.getY() + dy)) {
                        return false;
                    }
                }
                return true;
            }

            private void moveComponent(DrawComponent drawComponent, float dx, float dy) {
                if (isInsideDrawBoard(drawComponent, dx, dy)) {
                    drawComponent.move(dx, dy);
                }
            }

        });


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
                DrawComponent drawComponent = drawComponentManager.getFocusedComponent();
                if (drawComponent == null) {
                    return false;
                }
                if (drawComponent.isALine()) {
                    LineWrapper lineWrapper = (LineWrapper) drawComponent.getWrapper();
                    lineWrapper.setStrokeColor(newColor);
                } else if (drawComponent.isAPoint()) {
                    PointWrapper pointWrapper = (PointWrapper) drawComponent.getWrapper();
                    pointWrapper.setStrokeColor(newColor);
                } else if (drawComponent.isAnEllipse()) {
                    EllipseWrapper ellipseWrapper = (EllipseWrapper) drawComponent.getWrapper();
                    ellipseWrapper.setFillColor(newColor);
                } else if (!drawComponent.isAnImage()) {
                    RectangleWrapper rectangleWrapper = (RectangleWrapper) drawComponent.getWrapper();
                    rectangleWrapper.setFillColor(newColor);
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
                if (drawComponentManager.getFocusedComponent() != null) {
                    setText("DrawObject description!!");
                    addNewLine(drawComponentManager.getFocusedComponent().
                            getFieldsAsString(""));
                    addNewLine("");
                    addNewLine("Draw Method");
                    addNewLine(drawComponentManager.
                            getFocusedComponent().getDrawContentAsString());
                }
                super.draw();
            }
        };

        objectProperties.setText("ajcv acvksjch kasjcv ksajhdcv kasjcv aksjcv aksjcv kasjdcv kasjdvc aksjcv kajscv kasjdvck jsvksajvb dsjkvhb ldhvb lav dbjvklsdbv lsdiv baas");

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

    private static RectangleWrapper lineIcon;

    private static RectangleWrapper ellipseIcon;

    private static RectangleWrapper rectIcon;

    private static RectangleWrapper imageIcon;

    private static RectangleWrapper textIcon;

    private void initializeIcons() {


        lineIcon = new RectangleWrapper(0, 0,
                newLineBtm.getWidth(), newLineBtm.getHeight(), this) {

            float n = context.random(500);
            float rx1, rx2, ry1, ry2;

            @Override
            public void draw() {
                super.draw();
                context.stroke(colorSelector.getSelectedColor());
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
                context.fill(colorSelector.getSelectedColor());
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
                context.fill(colorSelector.getSelectedColor());
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
                    context.fill(colorSelector.getSelectedColor(), noise(n + i) * 255);
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
                textBox.setStrokeColor(colorSelector.getSelectedColor());
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
                            componentInConstruction = new ComponentInConstruction(Types.LINE);
                            constructionIcon = lineIcon;

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
                            console.setInputText(drawComponentManager.suggestNewName(Types.LINE));
                            console.setFocusable(true);
                            pGuiManager.setFocusTo(console);
                            console.setOnInputEnteredHandler(new Console.OnInputEnteredHandler() {
                                @Override
                                public boolean handlePEvent(PGuiObject pGuiObject, String input) {

                                    //name
                                    String checkedName = drawComponentManager.formatNewName(input);
                                    drawComponentManager.addComponentName(checkedName);
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
                return getType() == Types.LINE;
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
            DrawComponent brantNew = constructDrawComponent();
            console.println("New component created: " + brantNew);
            console.println("Construction finished!!! Enjoy");
            console.println();
            drawComponentManager.addDrawComponent(brantNew);
            componentInConstruction = null;
            constructionIcon = null;

        }

        private static DrawComponent constructDrawComponent() {

            if (componentInConstruction == null) {
                return null;
            }

            if (componentInConstruction.isALine()) {
                Point.Float p0 = componentInConstruction.getConstructionPoints().get(0);
                Point.Float p1 = componentInConstruction.getConstructionPoints().get(1);
                LineWrapper lineWrapper = new LineWrapper(p0.x, p0.y, p1.x, p1.y, pGuiManager.getContext());
                return new DrawComponent.LineDrawComponent(lineWrapper,
                        componentInConstruction.name);
            }

            return null;
        }


    }

}