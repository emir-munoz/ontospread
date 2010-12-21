package net.sourceforge.jpowergraph.swt.viewcontrols;

import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;
import net.sourceforge.jpowergraph.lens.TooltipLens;

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
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/TooltipControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class TooltipControlPanel extends Composite {

    private Button showToolTips;
    private TooltipLens tooltipLens;
    
    /**
     * 
     */
    public TooltipControlPanel(Composite theParent, TooltipLens theTooltipLens) {
        this(theParent, theTooltipLens, true);
    }
    
    public TooltipControlPanel(Composite theParent, TooltipLens theTooltipLens, boolean showToolTipsValue) {
        super(theParent, SWT.NONE);
        tooltipLens = theTooltipLens;
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        showToolTips = new Button(this, SWT.CHECK);
        showToolTips.setText("Show Tooltips");
        showToolTips.setSelection(showToolTipsValue);
        showToolTips.setEnabled(tooltipLens != null);
        setSelectedItemFromLens();
        this.addActionListeners();
    }

    private void addActionListeners() {
        if (tooltipLens != null && showToolTips != null){
            showToolTips.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    if (tooltipLens.isShowToolTips() != showToolTips.getSelection()){
                        tooltipLens.setShowToolTips(showToolTips.getSelection());
                    }
                }
            });
        }
        
        if (tooltipLens != null){
            tooltipLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }

    protected void setSelectedItemFromLens() {
        if (tooltipLens != null && showToolTips != null){
            showToolTips.setSelection(tooltipLens.isShowToolTips());
        }
    }
}
