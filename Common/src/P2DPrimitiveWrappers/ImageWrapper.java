package P2DPrimitiveWrappers;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageWrapper extends RectangleWrapper {

    PImage image;

    public ImageWrapper(float x, float y, float width, float height, PApplet context) {
        super(x, y, width, height, context);
    }

    @Override
    public void draw() {

        //image
        if (image != null) {
            fillEnable = false;
            context.image(image, x, y);
        } else {
            fillEnable = true;
        }
        super.draw();

    }

    public void setImage(PImage image) {
        if (image == null) {
            return;
        }
        this.image = image;
        image.resize((int) width, (int) height);
    }

    public void copyAndSetImage(PImage image) {
        this.image = new PImage(image.getImage());
        image.resize((int) width, (int) height);
    }

    public PImage getPImage() {
        return image;
    }
}
