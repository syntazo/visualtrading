/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import java.awt.*;


public class FontMapper extends Mapper {

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     *
     */
    public FontMapper() {
        super("fonts");

    }

    /**
     * @param map
     */
    public FontMapper(Object[] map) {
        super("fonts", map);

    }

// -------------------------- OTHER METHODS --------------------------

    public void updateValue(Mappable mappable, Object group, Object value) {
        FontUser fontUser = (FontUser) mappable;
        fontUser.setFont(group, (Font) value);
    }

}
