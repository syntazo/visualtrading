/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LwActContainer;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLabel;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.event.LwFocusEvent;
import org.zaval.lw.event.LwKeyEvent;
import org.zaval.lw.event.LwKeyListener;
import org.zaval.lw.event.LwMouseEvent;
import org.zaval.lw.event.LwMouseListener;
import org.zaval.lw.event.LwMouseMotionListener;


public class FakeButton
        extends LwActContainer
        implements LwMouseListener, LwKeyListener, LwMouseMotionListener {

// ------------------------------ FIELDS ------------------------------

    private boolean pressed;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructs a button component with no label.
     */
    public FakeButton() {
        this((LwComponent) null);
    }

    /**
     * Constructs a button component with the specified component as a label.
     *
     * @param t the specified component to be used as the button label.
     */
    public FakeButton(LwComponent t) {
        super(t);
        setPressed(false);
    }

    /**
     * Sets the specified state of the button. The button can have "pressed" or "un-pressed" state.
     *
     * @param b the specified state. The <code>true</code> value is used to set the "pressed" state, the
     *          <code>false</code> value to set the "un-pressed" state.
     */
    protected /*C#virtual*/ void setPressed(boolean b) {
        if (pressed != b || getViewMan(true).getView() == null) {
            pressed = b;
            getViewMan(true).setView(b ? "button.on" : "button.off");
            repaint();
        }
    }

    /**
     * Constructs a button component with the specified label text. In this case the component creates LwLabel component
     * with the text and uses it as the button label.
     *
     * @param lab the specified label text.
     */
    public FakeButton(String lab) {
        this(new LwLabel(lab));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Gets the state of the button.
     *
     * @return <code>true</code> if the button is "pressed", <code>false</code> if the button is "un-pressed".
     */
    public boolean isPressed() {
        return pressed;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwKeyListener ---------------------


    public void keyPressed(LwKeyEvent e) {
        if (!isPressed() && LwToolkit.ACTION_KEY == LwToolkit.getKeyType(e.getKeyCode(), e.getMask())) {
            setPressed(true);
        }
    }

    public void keyReleased(LwKeyEvent e) {
        if (isPressed()) {
            setPressed(false);
        }
    }

    public void keyTyped(LwKeyEvent e) {
    }

// --------------------- Interface LwMouseListener ---------------------

    /**
     * Fires the action event for list of LwActionListener.
     */


    public void mouseClicked(LwMouseEvent e) {
    }

    public void mouseEntered(LwMouseEvent e) {
    }

    public void mouseExited(LwMouseEvent e) {
        setPressed(false);
    }

    public void mousePressed(LwMouseEvent e) {
        if (LwToolkit.isActionMask(e.getMask())) {
            setPressed(true);
        }
    }

    public void mouseReleased(LwMouseEvent e) {
        setPressed(false);
    }

// --------------------- Interface LwMouseMotionListener ---------------------


    public void startDragged(LwMouseEvent e) {
    }

    public void endDragged(LwMouseEvent e) {
    }

    public void mouseDragged(LwMouseEvent e) {
        if (LwToolkit.isActionMask(e.getMask())) {
            int mickyX = e.getX();
            int mickyY = e.getY();
            setPressed(mickyX >= 0 && mickyX < width && mickyY >= 0 && mickyY <= height);
        }
    }

    public void mouseMoved(LwMouseEvent e) {
    }

// -------------------------- OTHER METHODS --------------------------

    public void focusLost(LwFocusEvent e) {
        setPressed(false);
    }

    /**
     * Invoked whenever the view manager has been set. The method is overridden with the class to set appropriate
     * ("button.off" or "button.on") view by the view manager.
     */
    protected /*C#override*/ void viewManChanged() {
        getViewMan(true).setView(isPressed() ? "button.on" : "button.off");
    }
}



