/**
 * Copyright (c) 2000,1,2,3,4,5 syntazo
 *
 * @author thanos vassilakis
 *
 */
package org.visualtrading.gui.widgets;

import org.zaval.lw.LayoutContainer;
import org.zaval.lw.Layoutable;
import org.zaval.lw.LwComponent;
import org.zaval.lw.LwLayout;
import org.zaval.lw.LwPanel;
import org.zaval.lw.LwRasterLayout;
import org.zaval.lw.LwToolkit;
import org.zaval.misc.ScrollMan;
import org.zaval.misc.ScrollObj;
import org.zaval.misc.event.PosListener;

import java.awt.*;

public class ScrollPane
        extends Panel
        implements PosListener, ScrollObj, ScrollMan, LwLayout {

// ------------------------------ FIELDS ------------------------------

    private ScrollBar hBar, vBar;
    private LwComponent scrollObj, scrollArea;
    private ScrollObj sobj;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructs a scroll panel component with the specified child component to be scrolled. This constructor allows
     * vertical and horizontal scroll bars.
     *
     * @param c the specified child component.
     */
    public ScrollPane(LwComponent c) {
        this(c, LwToolkit.HORIZONTAL | LwToolkit.VERTICAL);
    }

    /**
     * Constructs a scroll panel component with the specified child component to be scrolled and the given scroll bar
     * mask. The mask defines if the horizontal or vertical scroll bar or both scrollbars should be shown. Use following
     * values for the mask: <ul> <li> LwToolkit.HORIZONTAL - to enable horizontal scroll bar. </li> <li>
     * LwToolkit.VERTICAL - to enable vertical scroll bar. </li> <li> LwToolkit.VERTICAL | LwToolkit.HORIZONTAL - to
     * enable both vertical and horizontal scroll bars. </li> </ul>
     *
     * @param c       the specified child component.
     * @param barMask the specified scroll bar mask.
     */
    public ScrollPane(LwComponent c, int barMask) {
        scrollObj = c;
        sobj = (c instanceof ScrollObj) ? (ScrollObj) c : this;
        sobj.setScrollMan(this);

        if ((LwToolkit.HORIZONTAL & barMask) > 0) {
            hBar = new ScrollBar(LwToolkit.HORIZONTAL);
            hBar.getPosController().addPosListener(this);
            add(hBar);
        }

        if ((LwToolkit.VERTICAL & barMask) > 0) {
            vBar = new ScrollBar(LwToolkit.VERTICAL);
            vBar.getPosController().addPosListener(this);
            add(vBar);
        }

        if (sobj.moveContent()) {
            scrollArea = scrollObj;
            add(scrollObj);
        } else {
            scrollArea = new Panel();
            ((Panel) scrollArea).setLwLayout(new LwRasterLayout(LwRasterLayout.USE_PS_SIZE |
                                                                ((hBar !=
                                                                  null) ?
                                                                 0 :
                                                                 LwRasterLayout.W_BY_PARENT) |
                                                                ((vBar !=
                                                                  null) ?
                                                                 0 :
                                                                 LwRasterLayout.H_BY_PARENT)));
            ((LwPanel) scrollArea).add(scrollObj);
            add(scrollArea);
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public LwComponent getScrollObj() {
        return scrollObj;
    }

    public void setScrollObj(LwComponent scrollObj) {
        this.scrollObj = scrollObj;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LwLayout ---------------------


    public void componentAdded(Object id, Layoutable comp, int index) {
    }

    public void componentRemoved(Layoutable comp, int index) {
    }

    /**
     * Calculates the preferred size dimension of the layout container. The method calculates "pure" preferred size, it
     * means that an insets of the container is not considered.
     *
     * @param target the layout container.
     */
    public Dimension calcPreferredSize(LayoutContainer target) {
        return getPS(scrollArea);
    }

    /**
     * Lays out the child layoutable components inside the layout container.
     *
     * @param target the layout container that needs to be laid out.
     */
    public void layout(LayoutContainer target) {
        //
        // Calculate maximal values for vertical and horizontal scroll bars
        //
        Dimension so = sobj.getSOSize();
        Insets insets = getInsets();

        int ww = width - insets.left - insets.right, maxH = ww;
        int hh = height - insets.top - insets.bottom, maxV = hh;

        // Vertical scroll bar max value calculation
        if (hBar != null) {
            if (so.width > ww ||
                ((so.height > hh) &&
                 so.width >
                 (ww - (vBar == null ? 0 : vBar.getPreferredSize().width)))) {
                maxV -= hBar.getPreferredSize().height;
            }
        }
        maxV = so.height > maxV ? (so.height - maxV) : -1;

        // Horizontal scroll bar max value calculation
        if (vBar != null) {
            if (so.height > hh ||
                ((so.width > ww) &&
                 so.height >
                 (hh - (hBar == null ? 0 : hBar.getPreferredSize().height)))) {
                maxH -= vBar.getPreferredSize().width;
            }
        }
        maxH = so.width > maxH ? (so.width - maxH) : -1;

        //
        // Sets scroll bars visibility
        //
        Point p = sobj.getSOLocation();
        int sy = p.y;
        if (vBar != null) {
            if (maxV < 0) {
                if (vBar.isVisible()) {
                    vBar.setVisible(false);
                    sobj.setSOLocation(p.x, 0);
                    vBar.getPosController().setOffset(0);
                }
                sy = 0;
            } else {
                vBar.setVisible(true);
                sy = sobj.getSOLocation().y;
            }
        }

        if (hBar != null) {
            if (maxH < 0) {
                if (hBar.isVisible()) {
                    hBar.setVisible(false);
                    sobj.setSOLocation(0, sy);
                    hBar.getPosController().setOffset(0);
                }
            } else {
                hBar.setVisible(true);
            }
        }

        //
        // Layout scroll panel
        //
        Dimension sa = getPS(scrollArea), vs = getPS(vBar), hs = getPS(hBar);

        if (sa.width > 0) {
            scrollArea.setLocation(insets.left, insets.top);
            scrollArea.setSize(ww - vs.width,
                               hh - hs.height);
        }

        if (hBar != null && hs.height > 0) {
            hBar.setLocation(insets.left, height - insets.bottom - hs.height);
            hBar.setSize(ww - vs.width, hs.height);
            hBar.setMaximum(maxH);
        }

        if (vBar != null && vs.width > 0) {
            vBar.setLocation(width - insets.right - vs.width, insets.top);
            vBar.setSize(vs.width, hh - hs.height);
            vBar.setMaximum(maxV);
        }
    }

// --------------------- Interface PosListener ---------------------

    public void posChanged(Object target,
                           int prevOffset,
                           int prevLine,
                           int prevCol) {
        Point p = sobj.getSOLocation();
        if (hBar != null && hBar.getPosController() == target) {
            sobj.setSOLocation(-hBar.getPosController().getOffset(), p.y);
        } else {
            if (vBar != null) {
                sobj.setSOLocation(p.x, -vBar.getPosController().getOffset());
            }
        }
    }

// --------------------- Interface ScrollMan ---------------------


    public void moveScrolledObj(int x, int y) {
        if (hBar != null) {
            hBar.getPosController().setOffset(-x);
        }
        if (vBar != null) {
            vBar.getPosController().setOffset(-y);
        }
    }

    /**
     * Makes visible the specified area of the scrolled component.
     *
     * @param x the "x" coordinate of the scrolled component area.
     * @param y the "y" coordinate of the scrolled component area.
     * @param w the width of the scrolled component area.
     * @param h the height of the scrolled component area.
     */
    public void makeVisible(int x, int y, int w, int h) {
        validate();
        Point p = LwToolkit.calcOrigin(x, y, w, h, scrollObj);
        if (hBar != null && hBar.isVisible()) {
            hBar.getPosController()
                    .setOffset(-p.x);
        }
        if (vBar != null && vBar.isVisible()) {
            vBar.getPosController()
                    .setOffset(-p.y);
        }
    }

// --------------------- Interface ScrollObj ---------------------


    public Point getSOLocation() {
        return scrollObj.getLocation();
    }

    public void setSOLocation(int x, int y) {
        scrollObj.setLocation(x, y);
    }

    public Dimension getSOSize() {
        return scrollObj.getPreferredSize();
    }

    public void setScrollMan(ScrollMan m) {
    }

    public boolean moveContent() {
        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Gets the default layout manager that is set with the container during initialization. Pay attention that the
     * panel implements own layout manager and returns it by the method. You should not try to set other layout, since
     * it brings the component to fail.
     *
     * @return a layout manager.
     */
    protected /*C#override*/ LwLayout getDefaultLayout() {
        return this;
    }

    private Dimension getPS(Layoutable l) {
        return (l != null && l.isVisible()) ?
               l.getPreferredSize() :
               new Dimension();
    }

    /**
     * Gets the horizontal or vertical scroller component.
     *
     * @param type the specified scroller type. Use the LwToolkit.VERTICAL constant to get vertical scroller component
     *             or the LwToolkit.HORIZONTAL constant to get horizontal scroller component.
     *
     * @return a scroller component.
     */
    public ScrollBar getScrollbar(int type) {
        return type == LwToolkit.HORIZONTAL ? hBar : vBar;
    }

    /**
     * Sets the horizontal, vertical unit and page increments.
     *
     * @param hUnit the horizontal unit increment (use -1 to not change the property)
     * @param hPage the horizontal page increment (use -1 to not change the property)
     * @param vUnit the vertical unit increment (use -1 to not change the property)
     * @param vPage the vertical page increment (use -1 to not change the property)
     */
    public void setIncrements(int hUnit, int hPage, int vUnit, int vPage) {
        if (hBar != null) {
            if (hUnit != -1) {
                hBar.setUnitIncrement(hUnit);
            }
            if (hPage != -1) {
                hBar.setPageIncrement(hPage);
            }
        }

        if (vBar != null) {
            if (vUnit != -1) {
                vBar.setUnitIncrement(vUnit);
            }
            if (vPage != -1) {
                vBar.setPageIncrement(vPage);
            }
        }
    }
}


