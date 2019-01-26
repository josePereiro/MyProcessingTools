package PGUIObject;

interface PEventHandler{

    //////////////////////////////////////////////////////////////////////////////////////

    PGuiObject.OnMouseReleasedHandler getOnMouseReleasedHandler();

    void setOnMouseReleasedHandler(PGuiObject.OnMouseReleasedHandler onMouseReleasedHandler);

    //////////////////////////////////////////////////////////////////////////////////////

    PGuiObject.OnMouseWheelHandler getOnMouseWheelHandler();

    void setOnMouseWheelHandler(PGuiObject.OnMouseWheelHandler onMouseWheelHandler);

    //////////////////////////////////////////////////////////////////////////////////////

    PGuiObject.OnMousePressedHandler getOnMousePressedHandler();

    void setOnMousePressedHandler(PGuiObject.OnMousePressedHandler onMousePressedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnMouseClickedHandler getOnMouseClickedHandler();

    void setOnMouseClickedHandler(PGuiObject.OnMouseClickedHandler onMouseClickedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnMouseDraggedHandler getOnMouseDraggedHandler();

    void setOnMouseDraggedHandler(PGuiObject.OnMouseDraggedHandler onMouseDraggedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnMouseMovedHandler getOnMouseMovedHandler();

    void setOnMouseMovedHandler(PGuiObject.OnMouseMovedHandler onMouseMovedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnKeyPressedHandler getOnKeyPressedHandler();

    void setOnKeyPressedHandler(PGuiObject.OnKeyPressedHandler onKeyPressedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnKeyReleasedHandler getOnKeyReleasedHandler();

    void setOnKeyReleasedHandler(PGuiObject.OnKeyReleasedHandler onKeyReleasedHandler);

    //////////////////////////////////////////////////////////////////////////////////////


    PGuiObject.OnKeyTypedHandler getOnKeyTypedHandler();

    void setOnKeyTypedHandler(PGuiObject.OnKeyTypedHandler onKeyTypedHandler);

}