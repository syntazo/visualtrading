/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.gui.widgets.Button;
import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.data.Text;
import org.zaval.lw.LwLabel;

import java.awt.*;


public class ButtonBuilder extends CompositeBuilder {

// -------------------------- OTHER METHODS --------------------------

    public Object addChildren(Application application, Object obj, XMLElement xml) {
        return obj;
    }

    public Object configure(Application application, Object obj, XMLElement xml) {
        obj = super.configure(application, obj, xml);
        Button button = (Button) obj;
        String text = xml.getStringAttribute("LABEL");
        LwLabel lab = new LwLabel(new Text(text));
        Font font = getFont(xml, Button.FONT);
        lab.getTextRender().setFont(font);
        Color color = getColor(xml, "foreground");
        if (color != null) {
            lab.getTextRender().setForeground(color);
        }
        setComponent(button, lab, 0);
        //System.out.println(button);
        return button;
    }

    public Class getClass(String className) {
        return Button.class;
    }
}
