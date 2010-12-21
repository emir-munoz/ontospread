package net.sourceforge.jpowergraph.swt.viewcontrols;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;
import net.sourceforge.jpowergraph.lens.ZoomLens;

/**
 * @author Mick Kerrigan
 *
 * Created on 05-Aug-2005
 * Committed by $Author: morcen $
 *
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/ZoomControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class ZoomControlPanel extends Composite {

    public static final Integer[] DEFAULT_ZOOM_LEVELS = new Integer[]{25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300};
    
    private Combo zoomFactor;
    private ZoomLens zoomLens;
    
    /**
     * 
     */
    public ZoomControlPanel(Composite theParent, ZoomLens theZoomLens) {
        this(theParent, theZoomLens, DEFAULT_ZOOM_LEVELS, 100);
    }
    
    public ZoomControlPanel(Composite theParent, ZoomLens theZoomLens, Integer[] zoomLevels, Integer zoomValue) {
        super(theParent, SWT.NONE);
        zoomLens = theZoomLens;
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        Label l = new Label(this, SWT.NONE);
        l.setText("Zoom: ");
        
        zoomFactor = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
        for (int i = 0; i < zoomLevels.length; i++){
            zoomFactor.add(getComboTextFromInt(zoomLevels[i]));
        }
        zoomFactor.setEnabled(zoomLens != null);
        zoomFactor.setText(getComboTextFromInt(zoomValue));
        setSelectedItemFromLens();
        this.addActionListeners();
    }

    private void addActionListeners() {
        if (zoomLens != null && zoomFactor != null){
            zoomFactor.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    int i = getIntFromComboText(zoomFactor.getText());
                    double d = i / 100d;
                    if (zoomLens.getZoomFactor() != d){
                        zoomLens.setZoomFactor(d);
                    }
                }
            });
        }
        
        if (zoomLens != null){
            zoomLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }
    
    public String getComboTextFromInt(int i){
        return i + "%   ";
    }
    
    public int getIntFromComboText(String s){
        return Integer.parseInt(s.replaceAll("%   ", ""));
    }

    protected void setSelectedItemFromLens() {
        if (zoomLens != null && zoomFactor != null){
            zoomFactor.setText(getComboTextFromInt((int) (zoomLens.getZoomFactor() * 100d)));
        }
    }
}
