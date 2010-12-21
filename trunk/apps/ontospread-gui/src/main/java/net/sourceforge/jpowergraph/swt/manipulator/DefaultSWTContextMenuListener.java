package net.sourceforge.jpowergraph.swt.manipulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import net.sourceforge.jpowergraph.Edge;
import net.sourceforge.jpowergraph.Graph;
import net.sourceforge.jpowergraph.Legend;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.lens.LegendLens;
import net.sourceforge.jpowergraph.lens.LensSet;
import net.sourceforge.jpowergraph.lens.RotateLens;
import net.sourceforge.jpowergraph.lens.TooltipLens;
import net.sourceforge.jpowergraph.lens.ZoomLens;
import net.sourceforge.jpowergraph.manipulator.popup.ContextMenuListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import resources.ApplicationResources;

/**
 * @author Mick Kerrigan
 * 
 * Created on 03-Aug-2005 Committed by $Author: morcen $
 * 
 * $Source: /cvsroot/jpowergraph/swt/src/net/sourceforge/jpowergraph/swt/manipulator/DefaultSWTContextMenuListener.java,v $,
 * @version $Revision: 1.1 $ $Date: 2006/10/26 18:37:00 $
 */

public class DefaultSWTContextMenuListener implements ContextMenuListener <Menu> {

    private Graph graph;
    private LegendLens legendLens;
    private ZoomLens zoomLens;
    private Integer[] zoomLevels;
    private RotateLens rotateLens;
    private Integer[] rotateAngles;
    private TooltipLens tooltipLens;
    
    public DefaultSWTContextMenuListener(Graph theGraph, LensSet theLensSet, Integer[] theZoomLevels, Integer[] theRotateAngles){
        this.graph = theGraph;
        this.legendLens = (LegendLens) theLensSet.getFirstLensOfType(LegendLens.class);
        this.zoomLens = (ZoomLens) theLensSet.getFirstLensOfType(ZoomLens.class);
        this.zoomLevels = theZoomLevels;
        this.rotateLens = (RotateLens) theLensSet.getFirstLensOfType(RotateLens.class);
        this.rotateAngles = theRotateAngles;
        this.tooltipLens = (TooltipLens) theLensSet.getFirstLensOfType(TooltipLens.class);
    }
    
    public void fillNodeContextMenu(final Node theNode, Menu theMenu) {
        MenuItem it1 = new MenuItem(theMenu, SWT.CASCADE);
        it1.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.0") + theNode.getLabel()); //$NON-NLS-1$
        it1.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent arg0) {
                doStuff();
            }

            public void widgetDefaultSelected(SelectionEvent arg0) {
                doStuff();
            }
            
            public void doStuff(){
                ArrayList <Node> nodes = new ArrayList <Node> ();
                nodes.add(theNode);
                ArrayList <Edge> edges = new ArrayList <Edge> ();
                for (Iterator i = graph.getVisibleEdges().iterator(); i.hasNext();) {
                    Edge edge = (Edge) i.next();
                    if (edge.getFrom().equals(theNode) || edge.getTo().equals(theNode)){
                        edges.add(edge);
                    }
                }
                graph.deleteElements(nodes, edges);
            }
        });
    }

    public void fillEdgeContextMenu(final Edge theEdge, Menu theMenu) {
        MenuItem it1 = new MenuItem(theMenu, SWT.CASCADE);
        it1.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.1")); //$NON-NLS-1$
        it1.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent arg0) {
                doStuff();
            }

            public void widgetDefaultSelected(SelectionEvent arg0) {
                doStuff();
            }
            
            public void doStuff(){
                ArrayList <Edge>  edges = new ArrayList <Edge> ();
                edges.add(theEdge);
                graph.deleteElements(new ArrayList <Node> (), edges);
            }
        });
    }
    
    public void fillLegendContextMenu(Legend theLegend, Menu theMenu) {
        if (legendLens != null){
            MenuItem it1 = new MenuItem(theMenu, SWT.CASCADE);
            it1.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.2")); //$NON-NLS-1$
            it1.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent arg0) {
                    doStuff();
                }

                public void widgetDefaultSelected(SelectionEvent arg0) {
                    doStuff();
                }
                
                public void doStuff(){
                    legendLens.setShowLegend(false);
                }
            });
        }
    }

    public void fillBackgroundContextMenu(Menu theMenu) {
        if (zoomLens != null){
            final Integer[] zoom = zoomLevels;
            final int index = Arrays.binarySearch(zoom, (int) (zoomLens.getZoomFactor() * 100d));
            
            MenuItem zoomIn = new MenuItem(theMenu, SWT.CASCADE);
            zoomIn.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.3")); //$NON-NLS-1$
            zoomIn.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent arg0) {
                    doStuff();
                }

                public void widgetDefaultSelected(SelectionEvent arg0) {
                    doStuff();
                }
                
                public void doStuff(){
                    zoomLens.setZoomFactor(zoom[index + 1]/ 100d);
                }
            });
            zoomIn.setEnabled(index != zoom.length - 1);
            
            MenuItem zoomOut = new MenuItem(theMenu, SWT.CASCADE);
            zoomOut.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.4")); //$NON-NLS-1$
            zoomOut.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent arg0) {
                    doStuff();
                }

                public void widgetDefaultSelected(SelectionEvent arg0) {
                    doStuff();
                }
                
                public void doStuff(){
                    zoomLens.setZoomFactor(zoom[index - 1]/ 100d);
                }
            });
            zoomOut.setEnabled(index != 0);
        }
        if (zoomLens != null && rotateLens != null){
            new MenuItem(theMenu, SWT.SEPARATOR);
        }
        if (rotateLens != null){
            final Integer[] rotate = rotateAngles;
            int currentValue = (int) (360 - rotateLens.getRotationAngle());
            while (currentValue == 360){
                currentValue = 0;
            }
            final int index = Arrays.binarySearch(rotate, currentValue);
            
            MenuItem rotateClockwise = new MenuItem(theMenu, SWT.CASCADE);
            rotateClockwise.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.5")); //$NON-NLS-1$
            rotateClockwise.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent arg0) {
                    doStuff();
                }

                public void widgetDefaultSelected(SelectionEvent arg0) {
                    doStuff();
                }
                
                public void doStuff(){
                    if (index == rotate.length - 1){
                        rotateLens.setRotationAngle(360 - rotate[0]);
                    }
                    else{
                        rotateLens.setRotationAngle(360 - rotate[index + 1]);
                    }
                }
            });
            
            MenuItem rotateCounterClockwise = new MenuItem(theMenu, SWT.CASCADE);
            rotateCounterClockwise.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.6")); //$NON-NLS-1$
            rotateCounterClockwise.addSelectionListener(new SelectionListener() {
                public void widgetSelected(SelectionEvent arg0) {
                    doStuff();
                }

                public void widgetDefaultSelected(SelectionEvent arg0) {
                    doStuff();
                }
                
                public void doStuff(){
                    if (index == 0){
                        rotateLens.setRotationAngle(360 - rotate[rotate.length - 1]);
                    }
                    else{
                        rotateLens.setRotationAngle(360 - rotate[index - 1]);
                    }
                }
            });
        }
        new MenuItem(theMenu, SWT.SEPARATOR);
        if (legendLens != null){
            if (legendLens.isShowLegend()){
                MenuItem hideLegend = new MenuItem(theMenu, SWT.CASCADE);
                hideLegend.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.7")); //$NON-NLS-1$
                hideLegend.addSelectionListener(new SelectionListener() {
                    public void widgetSelected(SelectionEvent arg0) {
                        doStuff();
                    }
    
                    public void widgetDefaultSelected(SelectionEvent arg0) {
                        doStuff();
                    }
                    
                    public void doStuff(){
                        legendLens.setShowLegend(false);
                    }
                });
            }
            else{
                MenuItem showLegend = new MenuItem(theMenu, SWT.CASCADE);
                showLegend.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.8")); //$NON-NLS-1$
                showLegend.addSelectionListener(new SelectionListener() {
                    public void widgetSelected(SelectionEvent arg0) {
                        doStuff();
                    }
    
                    public void widgetDefaultSelected(SelectionEvent arg0) {
                        doStuff();
                    }
                    
                    public void doStuff(){
                        legendLens.setShowLegend(true);
                    }
                });
            }
        }
        if (tooltipLens != null){
            if (tooltipLens.isShowToolTips()){
                MenuItem hideTooltips = new MenuItem(theMenu, SWT.CASCADE);
                hideTooltips.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.9")); //$NON-NLS-1$
                hideTooltips.addSelectionListener(new SelectionListener() {
                    public void widgetSelected(SelectionEvent arg0) {
                        doStuff();
                    }
    
                    public void widgetDefaultSelected(SelectionEvent arg0) {
                        doStuff();
                    }
                    
                    public void doStuff(){
                        tooltipLens.setShowToolTips(false);
                    }
                });
            }
            else{
                MenuItem showTooltips = new MenuItem(theMenu, SWT.CASCADE);
                showTooltips.setText(ApplicationResources.getString("DefaultSWTContextMenuListener.10")); //$NON-NLS-1$
                showTooltips.addSelectionListener(new SelectionListener() {
                    public void widgetSelected(SelectionEvent arg0) {
                        doStuff();
                    }
    
                    public void widgetDefaultSelected(SelectionEvent arg0) {
                        doStuff();
                    }
                    
                    public void doStuff(){
                        tooltipLens.setShowToolTips(true);
                    }
                });
            }
        }
    }
}
