package net.sourceforge.jpowergraph.swt.viewcontrols;

import net.sourceforge.jpowergraph.lens.LegendLens;
import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Mick Kerrigan
 *
 * Created on 05-Aug-2005
 * Committed by $Author: morcen $
 *
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/LegendControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class LegendControlPanel extends Composite {

    private Button showLegend;
    private LegendLens legendLens;
    
    /**
     * 
     */
    public LegendControlPanel(Composite theParent, LegendLens theLegendLens) {
        this(theParent, theLegendLens, true);
    }
    
    public LegendControlPanel(Composite theParent, LegendLens theLegendLens, boolean showLegendValue) {
        super(theParent, SWT.NONE);
        legendLens = theLegendLens;
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        showLegend = new Button(this, SWT.CHECK);
        showLegend.setText("Show Legend");
        showLegend.setSelection(showLegendValue);
        showLegend.setEnabled(legendLens != null);
        setSelectedItemFromLens();
        this.addActionListeners();
    }

    private void addActionListeners() {
        if (legendLens != null && showLegend != null){
            showLegend.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    if (legendLens.isShowLegend() != showLegend.getSelection()){
                        legendLens.setShowLegend(showLegend.getSelection());
                    }
                }
            });
        }
        
        if (legendLens != null){
            legendLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }

    protected void setSelectedItemFromLens() {
        if (legendLens != null && showLegend != null){
            showLegend.setSelection(legendLens.isShowLegend());
        }
    }
}
