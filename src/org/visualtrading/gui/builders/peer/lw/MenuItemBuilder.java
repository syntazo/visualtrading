/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.MenuItem;
import org.visualtrading.gui.widgets.SimpleMenuItem;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;


public class MenuItemBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * 
     */
    public MenuItemBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object configure(Application application, Object obj, XMLElement xml) {
        MenuItem menu = (MenuItem) obj;
        menu.setTitle(xml.getStringAttribute("label"));
        return menu;
    }

    public Class getClass(String className) {
        return SimpleMenuItem.class;
    }

}
