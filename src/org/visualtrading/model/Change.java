/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.model;


public class Change {

// ------------------------------ FIELDS ------------------------------

    /**
     * 
     */
    Object mySource, myGroup, myValue;

// -------------------------- STATIC METHODS --------------------------

    public static void dispose(Change change) {
        change.dispose();
        change = null;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Change(Object source, Object group, Object value) {
        mySource = source;
        myGroup = group;
        myValue = value;
    }

// -------------------------- OTHER METHODS --------------------------

    public void dispose() {
        mySource = null;
        myGroup = null;
        myValue = null;
    }

}
