package net.sourceforge.jpowergraph.swt.viewcontrols;

import net.sourceforge.jpowergraph.lens.CursorLens;
import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;

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
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/CursorControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class CursorControlPanel extends Composite {

    private Button moveGraph;
    private Button createEdge;
    
    private CursorLens draggingLens;
    
    /**
     * 
     */
    public CursorControlPanel(Composite theParent, CursorLens theDraggingLens) {
        this(theParent, theDraggingLens, true);
    }
    
    public CursorControlPanel(Composite theParent, CursorLens theDraggingLens, boolean theMoveGraph) {
        super(theParent, SWT.NONE);
        draggingLens = theDraggingLens;

        Image arrow = new Image(getDisplay(), CursorControlPanel.class.getResourceAsStream("arrow.gif"));
        Image cross = new Image(getDisplay(), CursorControlPanel.class.getResourceAsStream("cross.gif"));
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        moveGraph = new Button(this, SWT.TOGGLE);
        moveGraph.setImage(arrow);
        moveGraph.setSelection(theMoveGraph);
        moveGraph.setEnabled(draggingLens != null);
        createEdge = new Button(this, SWT.TOGGLE);
        createEdge.setImage(cross);
        createEdge.setSelection(!theMoveGraph);
        createEdge.setEnabled(draggingLens != null);

        setSelectedItemFromLens();
        this.addActionListeners();
    }

    private void addActionListeners() {
        if (draggingLens != null && moveGraph != null){
            moveGraph.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    boolean b = moveGraph.getSelection();
                    if (draggingLens.isArrow() != b){
                        draggingLens.setArrow(b);
                    }
                }
            });
        }
        
        if (draggingLens != null && createEdge != null){
            createEdge.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    boolean b = createEdge.getSelection();
                    if (draggingLens.isCross() != b){
                        draggingLens.setCross(b);
                    }
                }
            });
        }
        
        if (draggingLens != null){
            draggingLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }
    
    protected void setSelectedItemFromLens() {
        if (draggingLens != null && moveGraph != null){
            moveGraph.setSelection(draggingLens.isArrow());
        }
        if (draggingLens != null && createEdge != null){
            createEdge.setSelection(draggingLens.isCross());
        }
    }
}
