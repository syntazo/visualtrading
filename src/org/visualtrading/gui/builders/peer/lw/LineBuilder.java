/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.builders.peer.lw;

import org.visualtrading.model.Application;
import org.visualtrading.xml.nanoxml.XMLElement;
import org.zaval.lw.LwBorder;
import org.zaval.lw.LwLine;
import org.zaval.lw.LwToolkit;

import java.lang.reflect.InvocationTargetException;


public class LineBuilder extends ComponentBuilder {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public LineBuilder() {
        super();

    }

// -------------------------- OTHER METHODS --------------------------

    public Object construct(Application application, Class classObj, XMLElement xml) throws SecurityException,
                                                                                            NoSuchMethodException,
                                                                                            IllegalArgumentException,
                                                                                            InstantiationException,
                                                                                            IllegalAccessException,
                                                                                            InvocationTargetException {
        return new LwLine(getIntConstant(xml, "type", LwBorder.PLAIN),
                          getIntConstant(xml, "orient", LwToolkit.HORIZONTAL));

    }

    public Class getClass(String className) {
        return LwLine.class;
    }

}
