package PGUIObject;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface PEventListener {


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMouseReleased();
    void  setListeningForMouseReleased(boolean enable);

    boolean listeningForMouseReleased(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMouseWheel();
    void  setListeningForMouseWheel(boolean enable);

    boolean listeningForMouseWheel(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMousePressed();
    void  setListeningForMousePressed(boolean enable);

    boolean listeningForMousePressed(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMouseClicked();
    void  setListeningForMouseClicked(boolean enable);

    boolean listeningForMouseClicked(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMouseDragged();
    void  setListeningForMouseDragged(boolean enable);

    boolean listeningForMouseDragged(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForMouseMoved();
    void  setListeningForMouseMoved(boolean enable);

    boolean listeningForMouseMoved(MouseEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForKeyPressed();
    void  setListeningForKeyPressed(boolean enable);

    boolean listeningForKeyPressed(KeyEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForKeyReleased();
    void  setListeningForKeyReleased(boolean enable);

    boolean listeningForKeyReleased(KeyEvent event);


    //////////////////////////////////////////////////////////////////////////////////////

    boolean isListeningForKeyTyped();
    void  setListeningForKeyTyped(boolean enable);

    boolean listeningForKeyTyped(KeyEvent event);

}
