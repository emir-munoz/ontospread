package net.sourceforge.jpowergraph.swt.viewcontrols;

import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;
import net.sourceforge.jpowergraph.lens.NodeSizeLens;
import net.sourceforge.jpowergraph.painters.NodePainter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Mick Kerrigan
 *
 * Created on 05-Aug-2005
 * Committed by $Author: morcen $
 *
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/NodeSizeControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class NodeSizeControlPanel extends Composite {
    
    private Button smallNodes;
    private Button largeNodes;

    private NodeSizeLens nodeSizeLens;
    
    public NodeSizeControlPanel(Composite theParent, NodeSizeLens theNodeSizeLens) {
        super(theParent, SWT.NONE);
        nodeSizeLens = theNodeSizeLens;
        
        Image small = new Image(getDisplay(), CursorControlPanel.class.getResourceAsStream("small.gif"));
        Image large = new Image(getDisplay(), CursorControlPanel.class.getResourceAsStream("large.gif"));
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        largeNodes = new Button(this, SWT.TOGGLE);
        largeNodes.setImage(large);
        largeNodes.setSelection(!true);
        largeNodes.setEnabled(nodeSizeLens != null);
        smallNodes = new Button(this, SWT.TOGGLE);
        smallNodes.setImage(small);
        smallNodes.setSelection(false);
        smallNodes.setEnabled(nodeSizeLens != null);
        setSelectedItemFromLens();
        this.addActionListeners();
    }

    /**
     * @param theNodeSizeValue
     */
    private void select(Integer theNodeSizeValue) {
        smallNodes.setSelection(theNodeSizeValue == NodePainter.SMALL);
        largeNodes.setSelection(theNodeSizeValue == NodePainter.LARGE);
    }
    
    private void addActionListeners() {
        if (nodeSizeLens != null && smallNodes != null){
            smallNodes.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    int value = -1;
                    if (smallNodes.getSelection()){
                        value = NodePainter.SMALL;
                    }
                    else{
                        value = NodePainter.LARGE;
                    }
                    
                    if (nodeSizeLens.getNodeSize() != value){
                        nodeSizeLens.setNodeSize(value);
                    }
                }
            });
        }
        
        if (nodeSizeLens != null && largeNodes != null){
            largeNodes.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    int value = -1;
                    if (largeNodes.getSelection()){
                        value = NodePainter.LARGE;
                    }
                    else{
                        value = NodePainter.SMALL;
                    }
                    
                    if (nodeSizeLens.getNodeSize() != value){
                        nodeSizeLens.setNodeSize(value);
                    }
                }
            });
        }
        
        if (nodeSizeLens != null){
            nodeSizeLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }

    protected void setSelectedItemFromLens() {
        if (nodeSizeLens != null && largeNodes != null && smallNodes != null){
            select(nodeSizeLens.getNodeSize());
        }
    }
}
