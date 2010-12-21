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

import java.util.ArrayList;

import net.sourceforge.jpowergraph.manipulator.Manipulator;
import net.sourceforge.jpowergraph.swtswinginteraction.geometry.JPowerGraphPoint;
import net.sourceforge.jpowergraph.swtswinginteraction.listeners.JPowerGraphFocusEvent;
import net.sourceforge.jpowergraph.swtswinginteraction.listeners.JPowerGraphKeyEvent;
import net.sourceforge.jpowergraph.swtswinginteraction.listeners.JPowerGraphMouseEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;


public class SWTManipulatorListener implements MouseListener, MouseMoveListener, MouseTrackListener, KeyListener, FocusListener{

    private Manipulator manipulator;

    public SWTManipulatorListener(Manipulator theManipulator) {
        this.manipulator = theManipulator;
    }

    public void mouseDoubleClick(MouseEvent e) {
        manipulator.mouseDoubleClick(getJPowerGraphMouseEvent(e));
    }

    public void mouseDown(MouseEvent e) {
        manipulator.mouseDown(getJPowerGraphMouseEvent(e));
    }

    public void mouseUp(MouseEvent e) {
        manipulator.mouseUp(getJPowerGraphMouseEvent(e));
    }

    public void mouseMove(MouseEvent e) {
        manipulator.mouseMove(getJPowerGraphMouseEvent(e));
    }

    public void mouseEnter(MouseEvent e) {
        manipulator.mouseEnter(getJPowerGraphMouseEvent(e));
    }

    public void mouseExit(MouseEvent e) {
        manipulator.mouseExit(getJPowerGraphMouseEvent(e));
    }

    public void mouseHover(MouseEvent e) {
        manipulator.mouseHover(getJPowerGraphMouseEvent(e));
    }

    public void keyPressed(KeyEvent e) {
        manipulator.keyPressed(new JPowerGraphKeyEvent());
    }

    public void keyReleased(KeyEvent e) {
        manipulator.keyReleased(new JPowerGraphKeyEvent());
    }

    public void focusGained(FocusEvent e) {
        manipulator.focusGained(new JPowerGraphFocusEvent());
    }

    public void focusLost(FocusEvent e) {
        manipulator.focusLost(new JPowerGraphFocusEvent());
    }
    
    private JPowerGraphMouseEvent getJPowerGraphMouseEvent(MouseEvent e) {
        int button = getButton(e);
        ArrayList <Integer> modifiers = getModifiers(e);
        return new JPowerGraphMouseEvent(new JPowerGraphPoint(e.x, e.y), button, modifiers);
    }

    private int getButton(MouseEvent e) {
        switch (e.button) {
            case 1: return JPowerGraphMouseEvent.LEFT;
            case 2: return JPowerGraphMouseEvent.CENTRE;
            case 3: return JPowerGraphMouseEvent.RIGHT;
        default:
            return -1;
        }
    }
    
    private ArrayList<Integer> getModifiers(MouseEvent e) {
        ArrayList <Integer> result = new ArrayList <Integer> ();
        if ((e.stateMask & SWT.SHIFT) != 0){
            result.add(JPowerGraphMouseEvent.SHIFT_MODIFIER);
        }
        if ((e.stateMask & SWT.CTRL) != 0){
            result.add(JPowerGraphMouseEvent.CTRL_MODIFIER);
        }
        return result;
    }
}
