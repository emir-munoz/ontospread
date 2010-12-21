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

package net.sourceforge.jpowergraph.swt;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.jpowergraph.swtswinginteraction.color.JPowerGraphColor;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


public class ColourManager {

    private Map <JPowerGraphColor, Color> colourMap = new HashMap <JPowerGraphColor, Color> ();
    private Display display;
    private boolean disposed = false;
    
    public ColourManager(Display theDisplay) {
        this.display = theDisplay;
    }

    public Color getSWTColor(JPowerGraphColor color) {
        if (!colourMap.containsKey(color)){
            colourMap.put(color, new Color(display, color.getRed(), color.getGreen(), color.getBlue()));
        }
        return colourMap.get(color);
    }

    public void dispose() {
        if (!disposed ){
            for(JPowerGraphColor jpgc : colourMap.keySet()){
                Color c = colourMap.get(jpgc);
                colourMap.remove(jpgc);
                if (!c.isDisposed()){
                    c.dispose();
                }
            }
            display = null;
            disposed = true;
        }
    }

    public boolean isDisposed() {
        return disposed;
    }
}
