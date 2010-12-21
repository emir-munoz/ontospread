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

import net.sourceforge.jpowergraph.pane.JGraphPane;
import net.sourceforge.jpowergraph.swtswinginteraction.JPowerGraphGraphics;
import net.sourceforge.jpowergraph.swtswinginteraction.color.JPowerGraphColor;
import net.sourceforge.jpowergraph.swtswinginteraction.geometry.JPowerGraphDimension;
import net.sourceforge.jpowergraph.swtswinginteraction.geometry.JPowerGraphRectangle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;


public class SWTJPowerGraphGraphics implements JPowerGraphGraphics{

    private SWTJGraphPane jGraphPane;
    private GC gc;
    private Image image;
    private ColourManager colourManager;
    private Font fontbackup;
    private FontMetrics fontMetrics;
    
    private boolean disposed = false;

    public SWTJPowerGraphGraphics(SWTJGraphPane theJGraphPane, GC theGC, ColourManager theColourManager) {
        this.jGraphPane = theJGraphPane;
        this.gc = theGC;
        this.gc.setLineDash(new int[]{10});
        this.colourManager = theColourManager;
    }

    public SWTJPowerGraphGraphics(SWTJGraphPane theJGraphPane, Image theImage, ColourManager theColourManager) {
        this.jGraphPane = theJGraphPane;
        this.gc = new GC(theImage);
        this.image = theImage;
        this.colourManager = theColourManager;
    }

    public JPowerGraphColor getBackground() {
        Color c = gc.getBackground();
        return new JPowerGraphColor(c.getRed(), c.getGreen(), c.getBlue());
    }
    
    public JPowerGraphColor getForeground() {
        Color c = gc.getForeground();
        return new JPowerGraphColor(c.getRed(), c.getGreen(), c.getBlue());
    }

    public void setForeground(JPowerGraphColor theColor) {
        gc.setForeground(colourManager.getSWTColor(theColor));
    }

    public void setBackground(JPowerGraphColor theColor) {
        gc.setBackground(colourManager.getSWTColor(theColor));
    }
    
    public int getStringWidth(String theString){
        return gc.stringExtent(theString).x;
    }
    
    public int getAscent() {
        if (fontMetrics == null){
            fontMetrics = gc.getFontMetrics();
        }
        return fontMetrics.getAscent();
    }

    public int getDescent() {
        if (fontMetrics == null){
            fontMetrics = gc.getFontMetrics();
        }
        return fontMetrics.getDescent();
    }
    
    public void drawLine(int x1, int y1, int x2, int y2) {
        gc.drawLine(x1, y1, x2, y2);
    }
    
    public void drawString(String theString, int x, int y, int numlines){
        gc.drawString(theString, x, y, false);
    }

    public void drawRectangle(int x, int y, int width, int height) {
        gc.drawRectangle(x, y, width, height);
    }

    public void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        gc.drawRoundRectangle(x, y, width, height, arcWidth, arcHeight);
    }
    
    public void drawOval(int x, int y, int width, int height) {
        gc.drawOval(x, y, width, height);
    }

    public void drawPolygon(int[] theIntegers) {
        gc.drawPolygon(theIntegers);
    }

    public void fillRectangle(int x, int y, int width, int height) {
        gc.fillRectangle(x, y, width, height);
    }
    
    public void fillRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        gc.fillRoundRectangle(x, y, width, height, arcWidth, arcHeight);
    }

    public void fillOval(int x, int y, int width, int height) {
        gc.fillOval(x, y, width, height);
    }
    
    public void fillPolygon(int[] theIntegers) {
        gc.fillPolygon(theIntegers);
    }

    public void storeFont() {
        fontbackup = gc.getFont();
    }

    public void setFontFromJGraphPane(JGraphPane graphPane) {
        gc.setFont(((SWTJGraphPane) graphPane).getFont());
    }

    public void restoreFont() {
        if (fontbackup != null){
            gc.setFont(fontbackup);
            fontbackup = null;
        }
    }

    public JPowerGraphRectangle getClipping() {
        Rectangle r = gc.getClipping();
        return new JPowerGraphRectangle(r.x, r.y, r.width, r.height);
    }

    public boolean getAntialias() {
        return gc.getAntialias() == SWT.ON;
    }

    public void setAntialias(boolean antialias) {
        if (antialias) {
            gc.setAntialias(SWT.ON);
        }
        else{
            gc.setAntialias(SWT.OFF);
        }
    }

    public JPowerGraphGraphics getSubJPowerGraphGraphics(JPowerGraphDimension theDimension) {
        return new SWTJPowerGraphGraphics(jGraphPane, new Image(jGraphPane.getDisplay(), theDimension.width, theDimension.height), colourManager);
    }

    public void dispose() {
        if (!gc.isDisposed()){
            gc.dispose();
        }
        if (!image.isDisposed()){
            image.dispose();
        }
        fontbackup = null;
        fontMetrics = null;
        disposed = true;
    }
    
    public boolean isDisposed(){
        return disposed ;
    }

    public void drawSubJPowerGraph(JPowerGraphGraphics theGraphics, int i, int j) {
        SWTJPowerGraphGraphics subG = (SWTJPowerGraphGraphics) theGraphics;
        gc.drawImage(subG.getImage(), i, j);
    }

    public Image getImage() {
        return image;
    }

    public int getLineWidth() {
        return gc.getLineWidth();
    }

    public void setLineWidth(int lineWidth) {
        gc.setLineWidth(lineWidth);
    }

    public void reset() {
        setBackground(JPowerGraphColor.WHITE);
        setForeground(JPowerGraphColor.BLACK);
        gc.setBackgroundPattern(null);
        gc.setForegroundPattern(null);
    }

    public void setLineDashed(boolean lineDashed) {
        if (lineDashed){
            this.gc.setLineDash(new int[]{4});
        }
        else{
            this.gc.setLineDash(null);
        }
    }
}
