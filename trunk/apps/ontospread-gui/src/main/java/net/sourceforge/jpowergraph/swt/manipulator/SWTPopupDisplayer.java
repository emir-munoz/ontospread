/*
 * Copyright (C) 2006 Digital Enterprise Research Insitute (DERI) Innsbruck
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sourceforge.jpowergraph.swt.manipulator;

import net.sourceforge.jpowergraph.Edge;
import net.sourceforge.jpowergraph.Legend;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.manipulator.popup.ContextMenuListener;
import net.sourceforge.jpowergraph.manipulator.popup.ToolTipListener;
import net.sourceforge.jpowergraph.pane.JGraphPane;
import net.sourceforge.jpowergraph.pane.PopupDisplayer;
import net.sourceforge.jpowergraph.swt.SWTJGraphPane;
import net.sourceforge.jpowergraph.swtswinginteraction.geometry.JPowerGraphPoint;
import net.sourceforge.jpowergraph.swtswinginteraction.listeners.JPowerGraphMouseEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class SWTPopupDisplayer implements PopupDisplayer {

    private Menu rightClick;
    private Shell tipShell;

    private ToolTipListener<Composite, Color> toolTipListener;
    private ContextMenuListener<Menu> contextMenuListener;

    private Color background;
    private Node lastNode = null;
    private boolean disposed;

    public SWTPopupDisplayer(Display theDisplay, ToolTipListener<Composite, Color> theToolTipListener, ContextMenuListener<Menu> theContextMenuListener) {
        this.background = theDisplay.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
        this.toolTipListener = theToolTipListener;
        this.contextMenuListener = theContextMenuListener;
    }

    public void doRightClickPopup(JGraphPane theGraphPane, JPowerGraphMouseEvent e) {
        final SWTJGraphPane graphPane = (SWTJGraphPane) theGraphPane;

        JPowerGraphPoint point = e.getPoint();
        Legend legend = graphPane.getLegendAtPoint(point);
        Node node = graphPane.getNodeAtPoint(point);
        Edge edge = graphPane.getNearestEdge(point);

        closeRightClickIfNeeded(graphPane);
        rightClick = new Menu(graphPane);
        graphPane.setMenu(rightClick);
        rightClick.addMenuListener(new MenuListener() {
            public void menuHidden(MenuEvent arg0) {
                synchronized (graphPane) {
                    graphPane.redraw();
                }
            }

            public void menuShown(MenuEvent arg0) {
            }
        });
        if (legend != null) {
            if (contextMenuListener != null) {
                contextMenuListener.fillLegendContextMenu(legend, rightClick);
            }
        }
        else if (node != null) {
            if (contextMenuListener != null) {
                contextMenuListener.fillNodeContextMenu(node, rightClick);
            }
        }
        else if (edge != null) {
            if (contextMenuListener != null) {
                contextMenuListener.fillEdgeContextMenu(edge, rightClick);
            }
        }
        else {
            if (contextMenuListener != null) {
                contextMenuListener.fillBackgroundContextMenu(rightClick);
            }
        }

        if (rightClick.getItemCount() > 0) {
            closeToolTipIfNeeded(graphPane);
            rightClick.setVisible(true);
        }
    }

    public void doToolTipPopup(JGraphPane theGraphPane, JPowerGraphMouseEvent e) {
        final SWTJGraphPane graphPane = (SWTJGraphPane) theGraphPane;
        
        JPowerGraphPoint point = e.getPoint();
        Node node = graphPane.getNodeAtPoint(point);

        if (node != null && node != lastNode) {
            closeToolTipIfNeeded(graphPane);
            if (toolTipListener != null) {
                tipShell = new Shell(graphPane.getShell(), SWT.ON_TOP | SWT.TOOL);
                tipShell.setBackground(background);
                boolean okay = toolTipListener.addNodeToolTipItems(node, tipShell, background);
                if (okay) {
                    tipShell.pack();
                    setTooltipLocation(tipShell, graphPane.toDisplay(point.x, point.y));
                    tipShell.setVisible(true);
                }
            }
        }
        else if (node == null) {
            closeToolTipIfNeeded(graphPane);
        }
        lastNode = node;
    }

    private void setTooltipLocation(Shell shell, Point position) {
        Rectangle displayBounds = shell.getDisplay().getBounds();
        Rectangle shellBounds = shell.getBounds();
        shellBounds.x = Math.max(Math.min(position.x, displayBounds.width - shellBounds.width), 0);
        shellBounds.y = Math.max(Math.min(position.y + 16, displayBounds.height - shellBounds.height), 0);
        shell.setBounds(shellBounds);
    }

    public boolean isRightClickShowing() {
        return rightClick != null && !rightClick.isDisposed() && rightClick.isVisible();
    }

    public boolean isToolTipShowing() {
        return tipShell != null && !tipShell.isDisposed() && tipShell.isVisible();
    }

    public void closeRightClickIfNeeded(JGraphPane theGraphPane) {
        if (isRightClickShowing()) {
            rightClick.setVisible(false);
            rightClick.dispose();
            rightClick = null;
            synchronized (theGraphPane) {
                theGraphPane.redraw();
            }
        }
    }

    public void closeToolTipIfNeeded(JGraphPane theGraphPane) {
        if (isToolTipShowing()) {
            tipShell.setVisible(false);
            toolTipListener.removeNodeToolTipItems(lastNode, tipShell);
            tipShell.dispose();
            tipShell = null;
            synchronized (theGraphPane) {
                theGraphPane.redraw();
            }
            lastNode = null;
        }
    }

    public void dispose() {
        if (rightClick != null && !rightClick.isDisposed()) {
            rightClick.dispose();
        }
        if (tipShell != null && !tipShell.isDisposed()) {
            tipShell.dispose();
        }
        if (background != null && !background.isDisposed()) {
            background.dispose();
        }
        disposed = true;
    }

    public boolean isDisposed() {
        return disposed;
    }
}
