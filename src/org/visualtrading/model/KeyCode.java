/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.visualtrading.xml.XMLObj;
import org.visualtrading.xml.nanoxml.XMLElement;

import java.awt.event.KeyEvent;


public class KeyCode implements XMLObj {

// ------------------------------ FIELDS ------------------------------

    public int code = 0, mask = 0;
    public char ch = 0;

// --------------------------- CONSTRUCTORS ---------------------------

    public KeyCode() {
        super();

    }

    public KeyCode(char ch, int code, int mask) {
        super();
        set(ch, code, mask);
    }

    public void set(char ch, int code, int mask) {
        this.code = code;
        this.mask = mask;
        this.ch = ch;

    }

// ------------------------ CANONICAL METHODS ------------------------

    /**
     * Checks whether two KeyCodes objects have equal values.
     */
    public boolean equals(Object obj) {
        if (obj instanceof KeyCode) {
            KeyCode d = (KeyCode) obj;
            return (code == d.code) && (mask == d.mask);
        }
        return false;
    }

    /**
     * Returns the hash code for this <code>KeyCodes</code>.
     *
     * @return a hash code for this <code>KeyCodes</code>
     */
    public int hashCode() {
        int sum = code + mask;
        return sum * (sum + 1) / 2 + mask;
    }

    public String toString() {
        if (code == 0) {
            return "";
        }
        String modifier = KeyEvent.getKeyModifiersText(mask);
        String codeStr = KeyEvent.getKeyText(code);
        if (mask == 0) {
            if (ch > 0x1F && ch < 0x7F) {
                return "" + ch;
            } else {
                return codeStr;
            }
        }
        if (mask != 0 && !modifier.equals(codeStr)) {
            if (ch > 0x1F && ch < 0x7F) {
                return modifier + "+" + ch;
            } else {
                return modifier + "+" + codeStr;
            }
        } else if (modifier.equals(codeStr)) {
            return modifier;
        }
        return "";

    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface XMLObj ---------------------

    /* (non-Javadoc)
     * @see org.visualtrading.xml.XMLObj#toXML()
     */
    public XMLElement toXML() {
        if (code != 0 || mask != 0 || ch != 0) {
            XMLElement xml = new XMLElement();
            xml.setName(getTag());
            xml.setIntAttribute("code", code);
            xml.setIntAttribute("ch", 0);
            xml.setIntAttribute("mask", mask);
            return xml;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.visualtrading.xml.XMLObj#fromXML(org.visualtrading.xml.nanoxml.XMLElement)
     */
    public void fromXML(XMLElement xml) {
        code = xml.getIntAttribute("code");
        ch = (char) xml.getIntAttribute("ch");
        mask = xml.getIntAttribute("mask");

    }

// -------------------------- OTHER METHODS --------------------------

    public String getTag() {
        return "KeyCode";
    }


    public void process(KeyMapper mapper, Object group) {
        mapper.updateDependants(group);

    }

    public void set(KeyCode keyCode) {
        set(keyCode.ch, keyCode.code, keyCode.mask);
    }
}
