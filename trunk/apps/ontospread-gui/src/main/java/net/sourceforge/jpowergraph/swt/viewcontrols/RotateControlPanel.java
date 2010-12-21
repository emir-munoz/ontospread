package net.sourceforge.jpowergraph.swt.viewcontrols;

import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;
import net.sourceforge.jpowergraph.lens.RotateLens;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import resources.ApplicationResources;

/**
 * @author Mick Kerrigan
 *
 * Created on 05-Aug-2005
 * Committed by $Author: morcen $
 *
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/viewcontrols/RotateControlPanel.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */
public class RotateControlPanel extends Composite {
    
    public static final Integer[] DEFAULT_ROTATE_ANGLES = new Integer[]{0, 90, 180, 270};

    private Combo rotateFactor;
    private RotateLens rotateLens;
    
    /**
     * 
     */
    public RotateControlPanel(Composite theParent, RotateLens theRotateLens) {
        this(theParent, theRotateLens, DEFAULT_ROTATE_ANGLES, 0);
    }
    
    public RotateControlPanel(Composite theParent, RotateLens theRotateLens, Integer[] rotateAngles, Integer rotateValue) {
        super(theParent, SWT.NONE);
        rotateLens = theRotateLens;
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 2;
        gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        
        Label l = new Label(this, SWT.NONE);
        l.setText(ApplicationResources.getString("RotateControlPanel.0")+": "); //$NON-NLS-1$
        
        rotateFactor = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
        for (int i = 0; i < rotateAngles.length; i++){
            rotateFactor.add(getComboTextFromInt(rotateAngles[i]));
        }
        rotateFactor.setEnabled(rotateLens != null);
        rotateFactor.setText(getComboTextFromInt(rotateValue));
        setSelectedItemFromLens();
        this.addActionListeners();
    }

    private void addActionListeners() {
        if (rotateLens != null && rotateFactor != null){
            rotateFactor.addSelectionListener(new SelectionAdapter(){
                public void widgetSelected(SelectionEvent e){
                    int i = 360 - getIntFromComboText(rotateFactor.getText());
                    if (rotateLens.getRotationAngle() != i){
                        rotateLens.setRotationAngle(i);
                    }
                }
            });
        }
        
        if (rotateLens != null){
            rotateLens.addLensListener(new LensListener() {
                public void lensUpdated(Lens lens) {
                    setSelectedItemFromLens();
                }
            });
        }
    }
    
    public String getComboTextFromInt(int i){
        return i + "º   "; //$NON-NLS-1$
    }
    
    public int getIntFromComboText(String s){
        return Integer.parseInt(s.replaceAll("º   ", "")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    protected void setSelectedItemFromLens() {
        if (rotateLens != null && rotateFactor != null){
            int currentValue = (int) (360 - rotateLens.getRotationAngle());
            while (currentValue == 360){
                currentValue = 0;
            }
            rotateFactor.setText(getComboTextFromInt(currentValue));
        }
    }
}
