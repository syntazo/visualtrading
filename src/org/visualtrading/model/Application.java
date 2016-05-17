/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.model;

import org.zaval.lw.LwContainer;


public interface Application {

// -------------------------- OTHER METHODS --------------------------

    ModelManager getModelManager();

    LwContainer getRoot();


    User getUser();

}
