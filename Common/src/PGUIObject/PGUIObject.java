package PGUIObject;

import P2DPrimitiveWrappers.RectangleWrapper;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * This is the base interface for building processing graphic user interface objects.
 * A PGUIObject handle key and mouse event inputs. The graphic part
 * will be handle for a implementation of a {@link RectangleWrapper}
 */
public interface PGUIObject {


    /**
     * Put this in the key handling methods of the PApple to handle
     * key events of this object.
     *
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    default boolean listenForKeyEvent() {
        return false;
    }

    /**
     * Put this in the key handling methods of the PApple to handle
     * key events of this object.
     *
     * @param keyEvent the key event involved.
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    default boolean listenForKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    /**
     * Put this in the mouse handling methods of the PApple to handle
     * mouse events of this object.
     *
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    default boolean listenForMouseEvent() {
        return false;
    }

    /**
     * Put this in the mouse handling methods of the PApple to handle
     * mouse events of this object.
     *
     * @param mouseEvent the mouse event involved.
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    default boolean listenForMouseEvent(MouseEvent mouseEvent) {
        return false;
    }

}
