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

import net.sourceforge.jpowergraph.pane.CursorChanger;
import net.sourceforge.jpowergraph.pane.JGraphPane;
import net.sourceforge.jpowergraph.swt.SWTJGraphPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;

public class SWTCursorChanger implements CursorChanger {

    // Defaults
    private static Cursor arrow;
    private static Cursor cross;

    // Overrides
    private static Cursor hand;
    private static Cursor stop;

    private boolean disposed = false;
    private Display display;

    public SWTCursorChanger(Display theDisplay) {
        this.display = theDisplay;
        arrow = new Cursor(theDisplay, SWT.CURSOR_ARROW);
        cross = theDisplay.getSystemCursor(SWT.CURSOR_CROSS);
        hand = new Cursor(theDisplay, SWT.CURSOR_HAND);
        stop = theDisplay.getSystemCursor(SWT.CURSOR_NO);
    }

    public void setCursor(JGraphPane theJGraphPane, int theCursorType) {
        SWTJGraphPane graphPane = (SWTJGraphPane) theJGraphPane;
        if (!arrow.isDisposed() && theCursorType == CursorChanger.ARROW) {
            graphPane.setCursor(arrow);
        }
        else if (!cross.isDisposed() && theCursorType == CursorChanger.CROSS) {
            graphPane.setCursor(cross);
        }
        else if (!hand.isDisposed() && theCursorType == CursorChanger.HAND) {
            graphPane.setCursor(hand);
        }
        else if (!stop.isDisposed() && theCursorType == CursorChanger.STOP) {
            graphPane.setCursor(stop);
        }
        else if (display != null && !display.isDisposed()){
            graphPane.setCursor(new Cursor(display, SWT.CURSOR_ARROW));
        }
    }

    public void dispose(){
        if (!arrow.isDisposed()){
            arrow.dispose();
        }
        if (!cross.isDisposed()){
            cross.dispose();
        }
        if (!hand.isDisposed()){
            hand.dispose();
        }
        if (!stop.isDisposed()){
            stop.dispose();
        }
        disposed = true;
    }
    
    public boolean isDisposed(){
        return disposed ;
    }
}
