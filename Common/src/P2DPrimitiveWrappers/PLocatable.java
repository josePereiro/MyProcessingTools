package P2DPrimitiveWrappers;

public interface PLocatable {

    float getX();

    float getY();

    void setX(float x);

    void setY(float y);

    /**
     * @param x A global x coordinate
     * @param y A global y coordinate
     * @return Return true if the given point is over the P2DPrimitiveWrapper area.
     */
    boolean isThisOverMe(float x, float y);

}
