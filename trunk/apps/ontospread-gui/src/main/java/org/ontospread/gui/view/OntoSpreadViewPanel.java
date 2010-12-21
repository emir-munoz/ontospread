package org.ontospread.gui.view;

import net.sourceforge.jpowergraph.lens.CursorLens;
import net.sourceforge.jpowergraph.lens.LensSet;
import net.sourceforge.jpowergraph.lens.NodeSizeLens;
import net.sourceforge.jpowergraph.lens.RotateLens;
import net.sourceforge.jpowergraph.lens.ZoomLens;
import net.sourceforge.jpowergraph.swt.viewcontrols.CursorControlPanel;
import net.sourceforge.jpowergraph.swt.viewcontrols.NodeSizeControlPanel;
import net.sourceforge.jpowergraph.swt.viewcontrols.RotateControlPanel;
import net.sourceforge.jpowergraph.swt.viewcontrols.ZoomControlPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OntoSpreadViewPanel extends Composite{


	private Composite startComposite;
    private Composite endComposite;
    
    /**
     * 
     */
    public OntoSpreadViewPanel(Composite theParent, Composite theComposite, LensSet theLensSet) {
        super(theParent, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 9;
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 4;
        setLayout(gridLayout);
        
        GridData sepData = new GridData();
        sepData.grabExcessVerticalSpace = false;
        sepData.heightHint = 20;
        
        GridLayout startContributeLayout = new GridLayout();
        startContributeLayout.numColumns = 1;
        startContributeLayout.marginHeight = 0;
        startContributeLayout.marginWidth = 0;
        
        GridData startContributeData = new GridData();
        startContributeData.minimumWidth = 0;
        startContributeData.widthHint = 0;
        startContributeData.heightHint = 0;
        
        GridLayout endContributeLayout = new GridLayout();
        endContributeLayout.numColumns = 1;
        endContributeLayout.marginHeight = 0;
        endContributeLayout.marginWidth = 0;
        
        GridData endContributeData = new GridData(GridData.FILL_HORIZONTAL);
        endContributeData.grabExcessHorizontalSpace= true;
        
        this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
        
        startComposite = new Composite(this, SWT.NONE);
        startComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
        startComposite.setLayout(startContributeLayout);
        startComposite.setLayoutData(startContributeData);
        new CursorControlPanel(this, (CursorLens) theLensSet.getFirstLensOfType(CursorLens.class));
        Label l1 = new Label(this, SWT.SEPARATOR);
        l1.setLayoutData(sepData);
        new ZoomControlPanel(this, (ZoomLens) theLensSet.getFirstLensOfType(ZoomLens.class));
        new RotateControlPanel(this, (RotateLens) theLensSet.getFirstLensOfType(RotateLens.class));
        Label l2 = new Label(this, SWT.SEPARATOR);
        l2.setLayoutData(sepData);
        new NodeSizeControlPanel(this, (NodeSizeLens) theLensSet.getFirstLensOfType(NodeSizeLens.class));
        Label l3 = new Label(this, SWT.SEPARATOR);
        l3.setLayoutData(sepData);
        endComposite = new Composite(this, SWT.NONE);
       // endComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
        endComposite.setLayout(endContributeLayout);
        endComposite.setLayoutData(endContributeData);
        
        theComposite.setParent(this);
        GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
        gridData2.horizontalAlignment = GridData.FILL;
        gridData2.horizontalSpan = gridLayout.numColumns;
        theComposite.setLayoutData(gridData2);
    }

    public Composite getStartContributeComposite() {
        return startComposite;
    }
    
    public Composite getEndContributeComposite() {
        return endComposite;
    }
}
