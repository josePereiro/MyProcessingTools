package PGUIObject;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface PListener {

    /**
     * Put this in the key handling methods of the PApple to handle
     * key events of this object.
     *
     * @param keyEvent the key event involved.
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    boolean listenForKeyPressed(KeyEvent keyEvent);

    boolean onKeyPressed(KeyEvent keyEvent);

    /**
     * Put this in the mouse handling methods of the PApple to handle
     * mouse events of this object.
     *
     * @param mouseEvent the mouse event involved.
     * @return it must return true if the implementation considered that
     * an event was handled
     */
    boolean listenForMouseClicked(MouseEvent mouseEvent);

    boolean onMouseClick(MouseEvent mouseEvent);

    boolean listeningForMouseWheel(MouseEvent mouseEvent);

    boolean onMouseWheel(MouseEvent mouseEvent);

    boolean isListeningMouse();

    void setListeningMouse(boolean listeningMouse);

    boolean isListeningKey();

    void setListeningKey(boolean listeningKey);

}
