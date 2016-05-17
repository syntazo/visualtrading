/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;


public interface KeyUser extends Mappable {

// -------------------------- OTHER METHODS --------------------------

    Mapper getKeyMapper();

    void keyTyped(Object keyCode, Object key);
}
