package net.sourceforge.jpowergraph.swt.manipulator;

import java.util.ArrayList;

import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.manipulator.popup.ToolTipListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Mick Kerrigan
 *
 * Created on 03-Aug-2005
 * Committed by $Author: morcen $
 *
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/manipulator/DefaultSWTToolTipListener.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */

public class DefaultSWTToolTipListener implements ToolTipListener <Composite, Color> {
    private ArrayList <Control> controls = new ArrayList <Control> ();
    
    public boolean addNodeToolTipItems(Node theNode, Composite theComposite, Color backgroundColor) {
        theComposite.setLayout(new GridLayout(1, true));
        
        Label l1 = new Label(theComposite, SWT.NONE);
        l1.setText(theNode.getLabel());
        l1.setBackground(backgroundColor);
        
        FontData[] fds1 = l1.getFont().getFontData();
        for (FontData fd : fds1){
            fd.setStyle(SWT.BOLD);
        }
        l1.setFont(new Font(theComposite.getDisplay(), fds1));
        
        Label l2 = new Label(theComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
        l2.setBackground(backgroundColor);
        
        Composite c = new Composite(theComposite, SWT.NONE);
        c.setLayout(new RowLayout());
        c.setBackground(backgroundColor);
        
        Label l3 = new Label(c, SWT.NONE);
        l3.setText("Node Type: ");
        l3.setBackground(backgroundColor);
        
        FontData[] fds3 = l1.getFont().getFontData();
        for (FontData fd : fds3){
            fd.setStyle(SWT.BOLD);
        }
        l3.setFont(new Font(theComposite.getDisplay(), fds3));
        
        Label l4 = new Label(c, SWT.NONE);
        l4.setText(theNode.getNodeType());
        l4.setBackground(backgroundColor);
        c.pack();
        
        controls.add(l1);
        controls.add(l2);
        controls.add(l3);
        controls.add(l4);
        
        return true;
    }
    
    public void removeNodeToolTipItems(Node theNode, Composite theComposite) {
        for (Control c : controls){
            if (c != null && !c.isDisposed()){
                c.dispose();
            }
        }
        controls.clear();
    }
}
