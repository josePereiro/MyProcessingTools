package PGUIObject;

import Common.Tools;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.MouseEvent;

/**
 * Implement a Graphics in a Processing Applet.
 */
public class ZoomBox extends PGuiObject {

    private final Tools.Zoom zoom;

    public ZoomBox(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);
        drawBackground(false);
        zoom = new Tools.Zoom((int) width, (int) height, context);
        setOnMouseWheelHandler(DEFAULT_ON_MOUSE_WHEEL_HANDLER);
    }

    /**
     * Amplify a area with the center in the given coordinates.
     * The magnified area is a rectangle with dimensions {@code width / factor} x {@code height / factor}.
     *
     * @param centerX the x coordinate of the center
     * @param centerY the y coordinate of the center
     */
    public void magnifyCenteredArea(int centerX, int centerY) {
        zoom.magnifyCenteredArea(centerX, centerY);
    }

    /**
     * Amplify a area with the top left corner in the given coordinates.
     * The magnified area is a rectangle with dimensions {@code width / factor} x {@code height / factor}.
     *
     * @param x the x coordinate of the top left corner
     * @param y the y coordinate of the top left corner
     */
    public void magnifyArea(int x, int y) {
        zoom.magnifyArea(x, y);
    }

    /**
     * Draw the amplified area in the given coordinates.
     * The size of the area is defined by {@code size}.
     */
    @Override
    public void draw() {
        if (zoom.getAmplifiedImage() != null) {
            drawBackground(false);
            context.imageMode(PConstants.CORNER);
            context.image(zoom.getAmplifiedImage(), x, y);
        } else {
            drawBackground(true);
        }
        super.draw();
    }

    /**
     * Get the current factor of magnification of the zoom
     *
     * @return
     */
    public float getFactor() {
        return zoom.getFactor();
    }

    /**
     * Set the magnification factor of the zoom
     *
     * @param factor
     */
    public void setFactor(float factor) {
        zoom.setFactor(factor);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        zoom.setWidth((int) width);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        zoom.setHeight((int) height);
    }

    public int getMagnifiedAreaWidth() {
        return zoom.getMagnifiedAreaWidth();
    }

    public int getMagnifiedAreaHeight() {
        return zoom.getMagnifiedAreaHeight();
    }

    public float getMaxFactor() {
        return zoom.getMaxFactor();
    }

    public void setMaxFactor(float maxFactor) {
        zoom.setMaxFactor(maxFactor);
    }

    public float getMinFactor() {
        return zoom.getMinFactor();
    }

    public void setMinFactor(float minFactor) {
        zoom.setMinFactor(minFactor);
    }

    private final static OnMouseWheelHandler DEFAULT_ON_MOUSE_WHEEL_HANDLER = new OnMouseWheelHandler() {

        int lastDir = 1;

        @Override
        public boolean handlePEvent(MouseEvent event, PGuiObject pGuiObject) {
            int wheelCount = event.getCount();
            ZoomBox zoom = (ZoomBox) pGuiObject;
            float zoomFactor = zoom.getFactor();
            if (wheelCount > 0) {
                for (int t = 0; lastDir > 0 &&
                        t < wheelCount && t < 10; t++) {
                    zoomFactor += zoomFactor * 0.05F;
                    zoom.setFactor(zoomFactor);
                }
                lastDir = 1;
            } else if (wheelCount < 0) {
                wheelCount *= -1;
                for (int t = 0; lastDir < 0 &&
                        t < wheelCount && t < 10; t++) {
                    zoomFactor -= zoomFactor * 0.05F;
                    zoom.setFactor(zoomFactor);
                }
                lastDir = -1;
            }
            return true;
        }

    };

}
