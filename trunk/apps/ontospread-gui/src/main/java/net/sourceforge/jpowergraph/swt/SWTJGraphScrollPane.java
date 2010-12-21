package net.sourceforge.jpowergraph.swt;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;

import net.sourceforge.jpowergraph.Graph;
import net.sourceforge.jpowergraph.GraphListener;
import net.sourceforge.jpowergraph.Node;
import net.sourceforge.jpowergraph.lens.Lens;
import net.sourceforge.jpowergraph.lens.LensListener;
import net.sourceforge.jpowergraph.lens.LensSet;
import net.sourceforge.jpowergraph.lens.TranslateLens;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;


/**
 * A scroll pane for the graph.
 */
public class SWTJGraphScrollPane extends Composite {
    /** The graph pane being visualized. */
    private SWTJGraphPane m_graphPane;

    /** The horizontal scroll bar. */
    private ScrollBar m_horizontalScrollBar;

    /** The vertical scroll bar. */
    private ScrollBar m_verticalScrollBar;

    /** The total area of the graph in graph coordinates. */
    private Rectangle2D m_graphArea;
    private Rectangle2D lastGraphArea; 

    /** The translation lens. */
    private TranslateLens m_translateLens;

    /**
     * Set to <code>true</code> if scroll bars are being programmatically
     * updated.
     */
    protected boolean m_updatingScrollBars;
    
    private Composite parent;
    
    /**
     * Creates an instance of this class.
     * 
     * @param graphPane
     *            the graph pane
     * @param translateLens
     *            the lens for translation
     */
    public SWTJGraphScrollPane(Composite theParent, SWTJGraphPane graphPane, LensSet theLensSet) {
        super(theParent, SWT.H_SCROLL | SWT.V_SCROLL);
        
        this.parent = theParent;
        
        setLayout(new FillLayout());
        m_graphPane = graphPane;
        m_graphPane.setParent(this);
        m_graphPane.getLens().addLensListener(new LensListener() {
            public void lensUpdated(Lens lens) {
                updateScrollBars();
            }
        });
         m_graphPane.getGraph().addGraphListener(new GraphHandler());
        m_translateLens = (TranslateLens) theLensSet.getFirstLensOfType(TranslateLens.class);
        m_graphArea = new Rectangle2D.Double();
        m_horizontalScrollBar = getHorizontalBar();
        m_horizontalScrollBar.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                if (!m_updatingScrollBars) {
                    scrollTo(m_horizontalScrollBar.getSelection(), m_verticalScrollBar.getSelection());
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                if (!m_updatingScrollBars) {
                    scrollTo(m_horizontalScrollBar.getSelection(), m_verticalScrollBar.getSelection());
                }
            }
        });
        m_verticalScrollBar = getVerticalBar();
        m_verticalScrollBar.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                if (!m_updatingScrollBars) {
                    scrollTo(m_horizontalScrollBar.getSelection(), m_verticalScrollBar.getSelection());
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                if (!m_updatingScrollBars) {
                    scrollTo(m_horizontalScrollBar.getSelection(), m_verticalScrollBar.getSelection());
                }
            }
        });
        
        setGraphArea(-500.0, -500.0, 1500.0, 1500.0);
        m_horizontalScrollBar.setSelection(500);
        m_verticalScrollBar.setSelection(500);
    }

    public void scrollTo(int hVal, int vVal) {
      int xDiff = (int) (m_graphArea.getX() * -1);
      int yDiff = (int) (m_graphArea.getY() * -1);

      Point2D point = new Point2D.Double(hVal - xDiff, vVal - yDiff);
      m_graphPane.getLens().undoLens(m_graphPane, new Point2D.Double(0, 0));
      m_translateLens.setTranslate(-point.getX(), -point.getY());
  }

    /**
     * Sets the area of the graph.
     * 
     * @param left
     *            the left boundary
     * @param top
     *            the top boundary
     * @param width
     *            the width of the area
     * @param height
     *            the height of the area
     */
    public void setGraphArea(double left, double top, double width, double height) {
        m_graphArea.setFrame(left, top, width, height);
        updateScrollBars();
    }

    /**
     * Updates the position and size of the scroll bars.
     */
    protected void updateScrollBars() {
        m_updatingScrollBars = true;
        
        if (m_horizontalScrollBar.isDisposed() || m_verticalScrollBar.isDisposed()){
            return;
        }
        
        int currentX = m_horizontalScrollBar.getSelection();
        int currentY = m_verticalScrollBar.getSelection();
        if (lastGraphArea != null){
            int deltax = (int) (lastGraphArea.getX() - m_graphArea.getX());
            int deltay = (int) (lastGraphArea.getY() - m_graphArea.getY());
            currentX += deltax;
            currentY += deltay;
        }
        
        int xDiff = (int) (m_graphArea.getX() * -1);
        int yDiff = (int) (m_graphArea.getY() * -1);
        
        int width = (int) m_graphArea.getWidth();
        int height = (int) m_graphArea.getHeight();
        int hMin = (int) (m_graphArea.getX() + xDiff);
        int hMax = (int) (m_graphArea.getX() + width + xDiff);
        int vMin = (int) (m_graphArea.getY() + yDiff);
        int vMax = (int) (m_graphArea.getY() + height + yDiff);
        
        m_horizontalScrollBar.setMinimum(hMin);
        m_horizontalScrollBar.setMaximum(hMax);
        m_horizontalScrollBar.setThumb(m_horizontalScrollBar.getMaximum()/10);
        m_horizontalScrollBar.setPageIncrement(200);
        m_horizontalScrollBar.setIncrement(20);
        m_horizontalScrollBar.setSelection(currentX);
        m_verticalScrollBar.setMinimum(vMin);
        m_verticalScrollBar.setMaximum(vMax);
        m_verticalScrollBar.setThumb(m_verticalScrollBar.getMaximum()/10);
        m_verticalScrollBar.setPageIncrement(100);
        m_verticalScrollBar.setIncrement(20);
        m_verticalScrollBar.setSelection(currentY);
        m_updatingScrollBars = false;
        
        lastGraphArea = new Rectangle2D.Double(m_graphArea.getX(), m_graphArea.getY(), m_graphArea.getWidth(), m_graphArea.getHeight());
    }
    
    public void scrollRectToVisible(Rectangle contentRect) {
        int deltaX = positionAdjustment(m_graphPane.getSize().x, contentRect.width, contentRect.x);
        int deltaY = positionAdjustment(m_graphPane.getSize().y, contentRect.height, contentRect.y);
        if (deltaX != 0 || deltaY != 0) {
            scrollTo(m_horizontalScrollBar.getSelection() + deltaX, m_verticalScrollBar.getSelection() + deltaY);
        }
    }

    /*
     * This method is used by the scrollToRect method to determine the proper
     * direction and amount to move by. The integer variables are named width,
     * but this method is applicable to height also. The code assumes that
     * parentWidth/childWidth are positive and childAt can be negative. This
     * method is copied from JViewport.
     */
    protected int positionAdjustment(int parentWidth, int childWidth, int childAt) {
        if (childAt >= 0 && childWidth + childAt <= parentWidth)
            return 0;
        if (childAt <= 0 && childWidth + childAt >= parentWidth)
            return 0;
        if (childAt > 0 && childWidth <= parentWidth)
            return childAt - parentWidth + childWidth;
        if (childAt >= 0 && childWidth >= parentWidth)
            return childAt;
        if (childAt <= 0 && childWidth <= parentWidth)
            return childAt;
        if (childAt < 0 && childWidth >= parentWidth)
            return childAt - parentWidth + childWidth;
        return 0;
    }

    /**
     * The handler for the graph events.
     */
    protected class GraphHandler implements GraphListener {
        public void graphLayoutUpdated(final Graph graph) {
            if (!parent.isDisposed() && !parent.getDisplay().isDisposed()){
                parent.getDisplay().asyncExec(new Runnable() {
                    public void run() {
                        updateGraphBoundsMainThread(graph);
                    }
                });
            }
        }

        public void graphUpdated(Graph graph) {
        }

        public void graphContentsChanged(final Graph graph) {
            if (!parent.isDisposed() && !parent.getDisplay().isDisposed()){
                parent.getDisplay().asyncExec(new Runnable() {
                    public void run() {
                        graphContentsChangedMainThread(graph);
                    }
                });
            }
        }

        public void elementsAdded(Graph graph, Collection nodes, Collection edges) {
        }

        public void elementsRemoved(Graph graph, Collection nodes, Collection edges) {
        }

        protected void graphContentsChangedMainThread(Graph graph) {
            m_translateLens.setTranslate(0, 0);
            setGraphArea(-500.0, -500.0, 1000.0, 1000.0);
            updateGraphBoundsMainThread(graph);
        }

        protected void updateGraphBoundsMainThread(Graph graph) {
            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            synchronized (graph) {
                Iterator iterator = graph.getVisibleNodes().iterator();
                while (iterator.hasNext()) {
                    Node node = (Node) iterator.next();
                    if (node.getX() < minX)
                        minX = node.getX();
                    if (node.getX() > maxX)
                        maxX = node.getX();
                    if (node.getY() < minY)
                        minY = node.getY();
                    if (node.getY() > maxY)
                        maxY = node.getY();
                }
            }
            minX -= 100.0;
            maxX += 100.0;
            minY -= 100.0;
            maxY += 100.0;
            if (minX < m_graphArea.getMinX() || maxX > m_graphArea.getMaxX() || minY < m_graphArea.getMinY() || maxY > m_graphArea.getMaxY()) {
                minX = Math.min(minX, m_graphArea.getMinX());
                maxX = Math.max(maxX, m_graphArea.getMaxX());
                minY = Math.min(minY, m_graphArea.getMinY());
                maxY = Math.max(maxY, m_graphArea.getMaxY());
                setGraphArea(minX, minY, maxX - minX, maxY - minY);
            }
        }
    }
    
    public void dispose() {
        super.dispose();
        if (!m_graphPane.isDisposed()){
            m_graphPane.dispose();
        }
        if (!m_horizontalScrollBar.isDisposed()){
            m_horizontalScrollBar.dispose();
        }
        if (!m_verticalScrollBar.isDisposed()){
            m_verticalScrollBar.dispose();
        }
    }
}