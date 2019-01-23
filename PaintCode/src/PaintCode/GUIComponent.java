//package PaintCode;
//
//import GObjects.Rectangle;
//import processing.core.PApplet;
//
//public class GUIComponent<T extends Rectangle> extends Rectangle {
//
//    private final T gComponent;
//
//    public GUIComponent(T gComponent) {
//        super(gComponent.getX(), gComponent.getY(),
//                gComponent.getWidth(), gComponent.getHeight(), gComponent.getContext());
//        this.gComponent = gComponent;
//    }
//
//    public T getGComponent() {
//        return gComponent;
//    }
//
//    @Override
//    public void draw() {
//        gComponent.draw();
//    }
//
//    public void listenForKeyPressed() {
//
//    }
//
//    public <T> void listenForKeyPressed(T extra) {
//
//    }
//
//    public void listenForMouseClick() {
//
//    }
//
//    public void listenForMouseWheel() {
//
//    }
//}
