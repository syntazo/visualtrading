/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwNotebook;


public class TabPane extends LwNotebook implements Widget {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public TabPane() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public TabPane(int arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Configurable ---------------------


    public void loadConfig(XMLElement xml) {
    }

    public XMLElement saveConfig() {
        return null;
    }

// --------------------- Interface Widget ---------------------

    public void cleanup() {
    }
}
