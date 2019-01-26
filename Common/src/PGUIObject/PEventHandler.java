package PGUIObject;

import processing.event.Event;

public abstract class PEventHandler<T extends Event> {

    /**
     * The implementation of the method will be called in any
     * listening event methods of the {@link PGuiObject} objects.
     * It will be passed the implicated {@link Event} and {@link PGuiObject}
     * to be used inside this method. You are welcome!!!
     *
     * @param event
     * @param pGuiObject
     * @return
     */
    public abstract boolean handlePEvent(T event, PGuiObject pGuiObject);

}
