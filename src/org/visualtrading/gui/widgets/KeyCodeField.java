/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.model.KeyCode;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwToolkit;
import org.zaval.lw.event.LwKeyEvent;
import org.zaval.lw.event.LwKeyListener;

import java.awt.*;


public class KeyCodeField extends Label implements LwKeyListener {

// ------------------------------ FIELDS ------------------------------

    KeyCode keyCode;

// --------------------------- CONSTRUCTORS ---------------------------

    public KeyCodeField() {

        super("");
        setForeground(Color.black);
        LwToolkit.getEventManager().addKeyListener(this);

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

// --------------------- Interface LwKeyListener ---------------------

    public void keyPressed(LwKeyEvent e) {
        KeyCode keycode = new KeyCode(e.getKeyChar(), e.getKeyCode(), e.getMask());
        set(keycode);
//        System.out.println("Key pressed in keyCodeField " + getText());

    }

    public void keyReleased(LwKeyEvent e) {
    }

    public void keyTyped(LwKeyEvent e) {
    }

// -------------------------- OTHER METHODS --------------------------

    public Object getValue() {
        return keyCode;
    }

    /**
     * @param string
     * @param e
     */
    public void set(KeyCode keyEvent) {
        keyCode = keyEvent;
        setText(keyCode.toString());
        getLwParent().repaint();
    }

}