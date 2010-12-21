package org.ontospread.gui.view;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.jpowergraph.Graph;
import net.sourceforge.jpowergraph.layout.Layouter;
import net.sourceforge.jpowergraph.layout.spring.SpringLayoutStrategy;
import net.sourceforge.jpowergraph.swt.SWTJGraphPane;
import net.sourceforge.jpowergraph.swt.SWTJGraphScrollPane;
import net.sourceforge.jpowergraph.swt.manipulator.DefaultSWTContextMenuListener;
import net.sourceforge.jpowergraph.swt.manipulator.DefaultSWTToolTipListener;
import net.sourceforge.jpowergraph.swt.manipulator.SWTPopupDisplayer;
import net.sourceforge.jpowergraph.swt.viewcontrols.RotateControlPanel;
import net.sourceforge.jpowergraph.swt.viewcontrols.ZoomControlPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.ontospread.exceptions.ConceptNotFoundException;
import org.ontospread.gui.controller.OntoSpreadController;
import org.ontospread.gui.to.PairRelationActiveTO;
import org.ontospread.gui.view.dialogs.EndDialog;
import org.ontospread.gui.view.dialogs.ErrorDialog;
import org.ontospread.gui.view.dialogs.HelpDialog;
import org.ontospread.gui.view.dialogs.LoadConfiguration;
import org.ontospread.gui.view.dialogs.SetInitialConcepts;
import org.ontospread.gui.view.dialogs.ShowConcept;
import org.ontospread.gui.view.dialogs.ShowConceptPath;
import org.ontospread.gui.view.dialogs.ShowSpreadingPath;
import org.ontospread.gui.view.dialogs.SpreadInspector;
import org.ontospread.gui.view.forms.LoadConfigurationForm;
import org.ontospread.state.UriDepthPair;
import org.ontospread.to.ConceptTO;
import org.ontospread.to.ScoredConceptTO;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class OntoSpreadViewerMain {
	SWTJGraphPane viewer = null;
	Display display = new Display();
	Shell shell = new Shell(display);
	ViewState state;
	TimeToWait timeToWait = TimeToWait.FAST;

	OntoSpreadController controller;
	private boolean hasEnd = false;
	private boolean update;;


	public OntoSpreadViewerMain (){
		state = ViewState.NEW;
	}

	private void createContents() throws Exception{
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.0"))); //$NON-NLS-1$
		shell.setText(ApplicationResources.getString("OntoSpreadViewerMain.1"));	 //$NON-NLS-1$
		final Group group = new Group(shell, SWT.NONE);
		group.setLayout(new FillLayout(SWT.VERTICAL));
		//ACTIVE VIEWER
		viewer = createViewer(group);
		group.setParent(shell);

		Thread updateGraph = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try { Thread.sleep(1000); } catch (Exception e) { }
					if(isUpdate()){
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {									
								//update graph
								Graph graph = viewer.getGraph();
								graph.setNodeFilter(controller.getNodeFilter());

								Layouter m_layouter = new Layouter(new SpringLayoutStrategy(graph));
								m_layouter.start();

								controller.addManipulatorsAndSelectionModel(viewer);
								viewer.setPopupDisplayer(new SWTPopupDisplayer(display, new DefaultSWTToolTipListener(), new DefaultSWTContextMenuListener(graph, controller.getLensSet(), ZoomControlPanel.DEFAULT_ZOOM_LEVELS, RotateControlPanel.DEFAULT_ROTATE_ANGLES)));
								controller.setPainters(viewer);
								viewer.setAntialias(true);
								setUpdate(false);
							}
						});
					}
				}    
			}					      
		});
		updateGraph.setDaemon(true);
		updateGraph.start();


		final Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		final MenuItem fileMenuItem = new MenuItem(menu, SWT.CASCADE);
		fileMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.2")); //$NON-NLS-1$
		
		
		final Menu menu_6 = new Menu(fileMenuItem);
		fileMenuItem.setMenu(menu_6);

		final MenuItem nuevoMenuItem = new MenuItem(menu_6, SWT.NONE);

		nuevoMenuItem.setAccelerator(SWT.CTRL+'n');
		nuevoMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.3"))); //$NON-NLS-1$
		nuevoMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.4")+"\tCtrl+n"); //$NON-NLS-1$

		final MenuItem quitMenuItem = new MenuItem(menu_6, SWT.NONE);
		quitMenuItem.setAccelerator(SWT.CTRL+'q');
		quitMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.5"))); //$NON-NLS-1$

		quitMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.6")+"\tCtrl+q"); //$NON-NLS-1$
		final MenuItem configMenuItem = new MenuItem(menu, SWT.CASCADE);
		configMenuItem.setEnabled(false);
		configMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.7")); //$NON-NLS-1$

		final Menu menu_3 = new Menu(configMenuItem);
		configMenuItem.setMenu(menu_3);

		final MenuItem loadMenuItem = new MenuItem(menu_3, SWT.NONE);
		loadMenuItem.setEnabled(false);
		loadMenuItem.setAccelerator(SWT.CTRL+'l');
		loadMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.8"))); //$NON-NLS-1$

		loadMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.9")+"\tCtrl+l"); //$NON-NLS-1$


		final MenuItem setInitialConceptMenuItem = new MenuItem(menu_3, SWT.NONE);
		setInitialConceptMenuItem.setEnabled(false);
		setInitialConceptMenuItem.setAccelerator(SWT.CTRL+'i');
		setInitialConceptMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.10"))); //$NON-NLS-1$

		setInitialConceptMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.11")+"\tCtrl+i"); //$NON-NLS-1$

		final MenuItem showMenuItem = new MenuItem(menu, SWT.CASCADE);
		showMenuItem.setEnabled(false);
		showMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.12")); //$NON-NLS-1$


		final Menu menu_1 = new Menu(showMenuItem);
		showMenuItem.setMenu(menu_1);

		final MenuItem inspectorMenuItem = new MenuItem(menu_1, SWT.NONE);
		inspectorMenuItem.setEnabled(false);
		inspectorMenuItem.setAccelerator(SWT.CTRL+'d');
		inspectorMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.13"))); //$NON-NLS-1$

		inspectorMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.14")+"\tCtrl+d"); //$NON-NLS-1$

		final MenuItem showConceptMenuItem = new MenuItem(menu_1, SWT.NONE);
		showConceptMenuItem.setEnabled(false);
		showConceptMenuItem.setAccelerator(SWT.ALT+'c');
		showConceptMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.15"))); //$NON-NLS-1$
		showConceptMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.16")+"\tAlt+c"); //$NON-NLS-1$


		final MenuItem conceptPathMenuItem = new MenuItem(menu_1, SWT.NONE);
		conceptPathMenuItem.setEnabled(false);
		conceptPathMenuItem.setAccelerator(SWT.ALT+'a');
		conceptPathMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.17"))); //$NON-NLS-1$

		conceptPathMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.18")+"\tAlt+a"); //$NON-NLS-1$


		final MenuItem showPathMenuItem = new MenuItem(menu_1, SWT.NONE);
		showPathMenuItem.setEnabled(false);
		showPathMenuItem.setAccelerator(SWT.ALT+'p');
		showPathMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.19"))); //$NON-NLS-1$
		showPathMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.20")+"\tAlt+p"); //$NON-NLS-1$


		final MenuItem runMenuItem = new MenuItem(menu, SWT.CASCADE);
		runMenuItem.setEnabled(false);
		runMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.21")); //$NON-NLS-1$

		final Menu menu_5 = new Menu(runMenuItem);
		runMenuItem.setMenu(menu_5);

		final MenuItem firstMenuItem = new MenuItem(menu_5, SWT.NONE);	
		firstMenuItem.setEnabled(false);
		firstMenuItem.setAccelerator(SWT.F4);
		firstMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.22"))); //$NON-NLS-1$

		firstMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.23")+"\tF4"); //$NON-NLS-1$

		final MenuItem nextMenuItem = new MenuItem(menu_5, SWT.NONE);
		nextMenuItem.setEnabled(false);
		nextMenuItem.setAccelerator(SWT.F5);
		nextMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.24"))); //$NON-NLS-1$
		nextMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.25")+"\tF5"); //$NON-NLS-1$

		final MenuItem previousMenuItem = new MenuItem(menu_5, SWT.NONE);
		previousMenuItem.setEnabled(false);
		previousMenuItem.setAccelerator(SWT.F6);
		previousMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.26"))); //$NON-NLS-1$
		previousMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.27")+"\tF6"); //$NON-NLS-1$

		final MenuItem lastMenuItem = new MenuItem(menu_5, SWT.NONE);
		lastMenuItem.setEnabled(false);
		lastMenuItem.setAccelerator(SWT.F7);
		lastMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.28"))); //$NON-NLS-1$
		lastMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.29")+"\tF7"); //$NON-NLS-1$

		final MenuItem currentMenuItem = new MenuItem(menu_5, SWT.NONE);
		currentMenuItem.setEnabled(false);
		currentMenuItem.setAccelerator(SWT.CTRL+SWT.F3);
		currentMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.30"))); //$NON-NLS-1$
		currentMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.31")+"\tCtrl+F3"); //$NON-NLS-1$

		final MenuItem playMenuItem = new MenuItem(menu_5, SWT.NONE);
		playMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		playMenuItem.setEnabled(false);
		playMenuItem.setAccelerator(SWT.F8);
		playMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.32"))); //$NON-NLS-1$
		playMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.33")+"\tF8"); //$NON-NLS-1$

		final MenuItem stopMenuItem = new MenuItem(menu_5, SWT.NONE);
		stopMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		stopMenuItem.setEnabled(false);
		stopMenuItem.setAccelerator(SWT.F9);
		stopMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.34"))); //$NON-NLS-1$
		stopMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.35")+"\tF9"); //$NON-NLS-1$

		final MenuItem helpMenuItem = new MenuItem(menu, SWT.CASCADE);
		helpMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.36")); //$NON-NLS-1$

		final Menu menu_4 = new Menu(helpMenuItem);
		helpMenuItem.setMenu(menu_4);

		final MenuItem aboutOntospreadMenuItem = new MenuItem(menu_4, SWT.NONE);
		aboutOntospreadMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				HelpDialog hp = new HelpDialog(shell);
				hp.open();
			}
		});
		aboutOntospreadMenuItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, "/app-icons/help.png"));
		aboutOntospreadMenuItem.setText(ApplicationResources.getString("OntoSpreadViewerMain.37")+"\tF1"); //$NON-NLS-1$
		aboutOntospreadMenuItem.setAccelerator(SWT.F1);
		ToolBar toolBar;
		toolBar = new ToolBar(shell, SWT.NONE);

		final ToolItem newItemToolItem = new ToolItem(toolBar, SWT.PUSH);

		newItemToolItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.38"))); //$NON-NLS-1$
		newItemToolItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.4")+" Ctrl+n");
		
		final ToolItem quitToolBarItem = new ToolItem(toolBar, SWT.PUSH);
		quitToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				quitMenuItem.notifyListeners(SWT.Selection, createEvent(arg0));
			}
		});
		quitToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.39"))); //$NON-NLS-1$
		quitToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.6")+" Ctrl+q");
		
		ToolBar runToolBar;
		runToolBar = new ToolBar(shell, SWT.NONE);

		final ToolItem firstToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		firstToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				firstMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		firstToolBarItem.setEnabled(false);
		firstToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.40"))); //$NON-NLS-1$
		firstToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.23")+" F4");
		
		final ToolItem nextToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		nextToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				nextMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		nextToolBarItem.setEnabled(false);
		nextToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.41"))); //$NON-NLS-1$
		nextToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.25")+" F5");
		
		final ToolItem previousToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		previousToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				previousMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		previousToolBarItem.setEnabled(false);
		previousToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.42"))); //$NON-NLS-1$
		previousToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.27")+" F6");
		
		final ToolItem lastToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		lastToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				lastMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		lastToolBarItem.setEnabled(false);
		lastToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.43"))); //$NON-NLS-1$
		lastToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.29")+" F7");
		
		final ToolItem currentToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		currentToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				currentMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		currentToolBarItem.setEnabled(false);
		currentToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.44"))); //$NON-NLS-1$
		currentToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.31")+" Ctrl+F3");
		
		final ToolItem playToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		playToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				playMenuItem.notifyListeners(SWT.Selection, createEvent(arg0));
			}
		});
		playToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.45"))); //$NON-NLS-1$
		playToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.33")+" F8");
		
		final ToolItem stopToolBarItem = new ToolItem(runToolBar, SWT.PUSH);
		stopToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				stopMenuItem.notifyListeners(SWT.Selection, createEvent(arg0));
			}
		});
		stopToolBarItem.setEnabled(false);
		stopToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.46"))); //$NON-NLS-1$
		stopToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.35")+" F9");
		
		ToolBar showToolBar;
		showToolBar = new ToolBar(shell, SWT.NONE);

		final ToolItem inspectorToolBarItem = new ToolItem(showToolBar, SWT.PUSH);
		inspectorToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				inspectorMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		inspectorToolBarItem.setEnabled(false);
		inspectorToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.47"))); //$NON-NLS-1$
		inspectorToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.14")+" Ctrl+d");
		
		final ToolItem conceptToolBarItem = new ToolItem(showToolBar, SWT.PUSH);
		conceptToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				showConceptMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		conceptToolBarItem.setEnabled(false);
		conceptToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.48"))); //$NON-NLS-1$
		conceptToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.16")+" Alt+c");
		
		final ToolItem activeToolBarItem = new ToolItem(showToolBar, SWT.PUSH);
		activeToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				conceptPathMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		activeToolBarItem.setEnabled(false);
		activeToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.49"))); //$NON-NLS-1$
		activeToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.18")+" Alt+a");
		
		final ToolItem spreadToolBarItem = new ToolItem(showToolBar, SWT.PUSH);
		spreadToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				showPathMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		spreadToolBarItem.setEnabled(false);
		spreadToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.50"))); //$NON-NLS-1$
		spreadToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.20")+" Alt+p");
		
		final ToolItem newItemToolItem_1 = new ToolItem(showToolBar, SWT.PUSH);
		newItemToolItem_1.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.51"))); //$NON-NLS-1$

		ToolBar setToolbar;
		setToolbar = new ToolBar(shell, SWT.NONE);

		final ToolItem loadToolBarItem = new ToolItem(setToolbar, SWT.PUSH);
		loadToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {			
				loadMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		loadToolBarItem.setEnabled(false);
		loadToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.52"))); //$NON-NLS-1$
		loadToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.9")+" Ctrl+l");
		
		final ToolItem initialToolBarItem = new ToolItem(setToolbar, SWT.PUSH);
		initialToolBarItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				setInitialConceptMenuItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		initialToolBarItem.setEnabled(false);
		initialToolBarItem.setImage(SWTResourceManager.getImage(OntoSpreadViewerMain.class, ApplicationResources.getString("OntoSpreadViewerMain.53"))); //$NON-NLS-1$
		initialToolBarItem.setToolTipText(ApplicationResources.getString("OntoSpreadViewerMain.11")+" Ctrl+i");
		final GroupLayout groupLayout = new GroupLayout(shell);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
						.addContainerGap()
						.add(toolBar, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(setToolbar, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(showToolBar, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
						.add(27, 27, 27)
						.add(runToolBar, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(256, Short.MAX_VALUE))
						.add(group, GroupLayout.PREFERRED_SIZE, 744, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
						.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.add(setToolbar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.add(runToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.add(showToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.RELATED)
								.add(group, GroupLayout.PREFERRED_SIZE, 441, Short.MAX_VALUE))
		);
		//Listeners
		quitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.dispose();
			}
		});
		nuevoMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				newItemToolItem.notifyListeners(SWT.Selection,createEvent(arg0));
			}
		});
		newItemToolItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				controller.clean();
				viewer.setGraph(controller.createEmptyGraph());
				updateGraph();
				state = ViewState.CONFIGURATION;
				configMenuItem.setEnabled(true);
				loadMenuItem.setEnabled(true);
				loadToolBarItem.setEnabled(true);
				setInitialConceptMenuItem.setEnabled(false);
				initialToolBarItem.setEnabled(false);



				//TOOLBAR
				inspectorToolBarItem.setEnabled(false);
				conceptToolBarItem.setEnabled(false);
				activeToolBarItem.setEnabled(false);
				spreadToolBarItem.setEnabled(false);
				firstToolBarItem.setEnabled(false);
				nextToolBarItem.setEnabled(false);
				previousToolBarItem.setEnabled(false);
				lastToolBarItem.setEnabled(false);
				currentToolBarItem.setEnabled(false);
				playToolBarItem.setEnabled(false);
				stopToolBarItem.setEnabled(false);
				//MENU
				showMenuItem.setEnabled(false);
				runMenuItem.setEnabled(false);
				inspectorMenuItem.setEnabled(false);
				showConceptMenuItem.setEnabled(false);
				conceptPathMenuItem.setEnabled(false);
				showPathMenuItem.setEnabled(false);
				firstMenuItem.setEnabled(false);
				nextMenuItem.setEnabled(false);
				previousMenuItem.setEnabled(false);
				lastMenuItem.setEnabled(false);
				currentMenuItem.setEnabled(false);
				playMenuItem.setEnabled(false);
				stopMenuItem.setEnabled(false);

			}


		});
		loadMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {	
				try {
					LoadConfiguration lc = new LoadConfiguration(shell);
					LoadConfigurationForm lcform = (LoadConfigurationForm) lc.open();
					if(lcform != null){
						state = ViewState.INITIAL_CONCEPTS;
						controller.createProcess(lcform);
						setInitialConceptMenuItem.setEnabled(true);						
						conceptToolBarItem.setEnabled(true);
						showMenuItem.setEnabled(true);
						showConceptMenuItem.setEnabled(true);
						initialToolBarItem.setEnabled(true);
					}					
				} catch (ConceptNotFoundException e) {
					createErrorDialog(e);
				}
			}
		});

		setInitialConceptMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					SetInitialConcepts stp = new SetInitialConcepts(shell);
					ConceptTO[] allConcepts = controller.getAllConcepts();
					String []concepts = new String[allConcepts.length];
					for(int i = 0; i<allConcepts.length;i++){
						concepts[i] = allConcepts[i].getUri();
					}
					Arrays.sort(concepts);
					stp.setInitialConceptsUris(concepts);
					ScoredConceptTO []initialConcepts =  (ScoredConceptTO[]) stp.open();
					if(initialConcepts != null){
						state = ViewState.RUN;
						controller.createPlayer(initialConcepts);
						//TOOLBAR
						inspectorToolBarItem.setEnabled(true);
						conceptToolBarItem.setEnabled(true);
						activeToolBarItem.setEnabled(true);
						spreadToolBarItem.setEnabled(true);
						firstToolBarItem.setEnabled(true);
						nextToolBarItem.setEnabled(true);
						previousToolBarItem.setEnabled(true);
						lastToolBarItem.setEnabled(true);
						currentToolBarItem.setEnabled(true);
						playToolBarItem.setEnabled(true);
						//stopToolBarItem.setEnabled(true);
						//MENU
						showMenuItem.setEnabled(true);
						runMenuItem.setEnabled(true);
						inspectorMenuItem.setEnabled(true);
						showConceptMenuItem.setEnabled(true);
						conceptPathMenuItem.setEnabled(true);
						showPathMenuItem.setEnabled(true);
						firstMenuItem.setEnabled(true);
						nextMenuItem.setEnabled(true);
						previousMenuItem.setEnabled(true);
						lastMenuItem.setEnabled(true);
						currentMenuItem.setEnabled(true);
						playMenuItem.setEnabled(true);
						//stopMenuItem.setEnabled(true);

					}
				} catch (ConceptNotFoundException e) {					
					createErrorDialog(e);
				}
			}
		});

		inspectorMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					SpreadInspector sp = new SpreadInspector(shell);
					ConceptTO[] allConcepts = controller.getAllConcepts();
					String []concepts = new String[allConcepts.length];
					for(int i = 0; i<allConcepts.length;i++){
						concepts[i] = allConcepts[i].getUri();
					}
					Arrays.sort(concepts);
					sp.setInitialConceptsTOs(controller.getInitialConcepts());
					sp.setRelationsWeight(controller.getOntoSpreadProcess().getRelationWeight());
					sp.setActiveConcepts(createActiveConcepts());
					PairRelationActiveTO result = (PairRelationActiveTO) sp.open();
					if(result != null) updateInspector(result);
				} catch (ConceptNotFoundException e) {
					createErrorDialog(e);
				}

			}

			private void updateInspector(PairRelationActiveTO result) throws ConceptNotFoundException {
				controller.updateActiveConcepts(result.getActiveConceptsTOs());
				controller.updateRelationsWeight(result.getRelationConceptsTOs());
			}

			private List<ScoredConceptTO> createActiveConcepts() throws ConceptNotFoundException {
				List<ScoredConceptTO> actived = new LinkedList<ScoredConceptTO>();
				Map<String, Double> concepts = controller.current().getConcepts();
				for (UriDepthPair uriDepthPair : controller.current().getSortedList()) {
					actived.add(new ScoredConceptTO(uriDepthPair.getUri(),concepts.get(uriDepthPair.getUri())));					
				}
				return actived;
			}
		});


		showConceptMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {	
				ShowConcept showConcept = new ShowConcept(shell);
				ConceptTO[] allConcepts = controller.getAllConcepts();
				String []concepts = new String[allConcepts.length];
				for(int i = 0; i<allConcepts.length;i++){
					concepts[i] = allConcepts[i].getUri();
				}
				Arrays.sort(concepts);
				showConcept.setConcepts(concepts);
				String uri = (String) showConcept.open();
				if(uri != null){
					try {
						viewer.setGraph(controller.createGraphFromConcept(uri));
						updateGraph();
					} catch (ConceptNotFoundException e) {
						createErrorDialog(e);
					}
				}
			}
		});

		conceptPathMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					ShowConceptPath sp = new ShowConceptPath(shell);
					String[] concepts;
					concepts = controller.getAvailablePaths();
					Arrays.sort(concepts);
					sp.setConcepts(concepts);
					String uri = (String) sp.open();
					if(uri != null){
						viewer.setGraph(controller.createGraphForSpreading(uri));
						updateGraph();
					}
				} catch (ConceptNotFoundException e) {
					createErrorDialog(e);
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});

		showPathMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {	
				ShowSpreadingPath showConcept = new ShowSpreadingPath(shell);
				String[] concepts;
				try {
					concepts = controller.getAvailableSpreading();
					Arrays.sort(concepts);
					showConcept.setConcepts(concepts);
					String uri = (String) showConcept.open();
					if(uri != null){
						viewer.setGraph(controller.createGraphForSpreading(uri));
						updateGraph();
					}
				} catch (ConceptNotFoundException e) {
					createErrorDialog(e);
				} catch (Exception e) {
					createErrorDialog(e);
				}

			}
		});
		firstMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.first()));
					updateGraph();
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});

		nextMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.next()));
					updateGraph();
					if(!controller.hasNext()){
						EndDialog end = new EndDialog(shell);
						end.open();
					}
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});

		previousMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.previous()));
					updateGraph();					
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});

		lastMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.last()));
					updateGraph();
					EndDialog end = new EndDialog(shell);
					end.open();
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});

		currentMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.current()));
					updateGraph();
				} catch (Exception e) {
					createErrorDialog(e);
				}
			}
		});
		playMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				stopMenuItem.setEnabled(true);
				stopToolBarItem.setEnabled(true);

				showMenuItem.setEnabled(false);
				newItemToolItem.setEnabled(false);
				newItemToolItem_1.setEnabled(false);

				//Deactivate all buttons
				configMenuItem.setEnabled(false);
				loadMenuItem.setEnabled(false);
				loadToolBarItem.setEnabled(false);
				setInitialConceptMenuItem.setEnabled(false);
				initialToolBarItem.setEnabled(false);

				inspectorToolBarItem.setEnabled(false);
				conceptToolBarItem.setEnabled(false);
				activeToolBarItem.setEnabled(false);
				spreadToolBarItem.setEnabled(false);
				firstToolBarItem.setEnabled(false);
				nextToolBarItem.setEnabled(false);
				previousToolBarItem.setEnabled(false);
				lastToolBarItem.setEnabled(false);
				currentToolBarItem.setEnabled(false);
				//MENU
				showMenuItem.setEnabled(false);
				runMenuItem.setEnabled(false);
				inspectorMenuItem.setEnabled(false);
				showConceptMenuItem.setEnabled(false);
				conceptPathMenuItem.setEnabled(false);
				showPathMenuItem.setEnabled(false);
				firstMenuItem.setEnabled(false);
				nextMenuItem.setEnabled(false);
				previousMenuItem.setEnabled(false);
				lastMenuItem.setEnabled(false);
				currentMenuItem.setEnabled(false);
				//End deactivate buttons
				try{
				hasEnd = false;
				controller.first();
				while(!isEnd() && controller.hasNext()){
					viewer.setGraph(controller.createGraphFromOntoSpreadState(controller.next()));
					updateGraph();				
					Thread.sleep(timeToWait.timeToWait());					
				}

				EndDialog end = new EndDialog(shell);
				end.open();
				
				} catch (Exception e) {
					createErrorDialog(e);
				}finally{
					stopMenuItem.notifyListeners(SWT.Selection, createEvent(arg0));
				}

			}
		});
		stopMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				hasEnd = true;
				//activate all buttons
				showMenuItem.setEnabled(true);
				newItemToolItem.setEnabled(true);
				newItemToolItem_1.setEnabled(true);

				configMenuItem.setEnabled(true);
				loadMenuItem.setEnabled(true);
				loadToolBarItem.setEnabled(true);
				setInitialConceptMenuItem.setEnabled(true);
				initialToolBarItem.setEnabled(true);

				inspectorToolBarItem.setEnabled(true);
				conceptToolBarItem.setEnabled(true);
				activeToolBarItem.setEnabled(true);
				spreadToolBarItem.setEnabled(true);
				firstToolBarItem.setEnabled(true);
				nextToolBarItem.setEnabled(true);
				previousToolBarItem.setEnabled(true);
				lastToolBarItem.setEnabled(true);
				currentToolBarItem.setEnabled(true);
				playToolBarItem.setEnabled(true);
				//MENU
				showMenuItem.setEnabled(true);
				runMenuItem.setEnabled(true);
				inspectorMenuItem.setEnabled(true);
				showConceptMenuItem.setEnabled(true);
				conceptPathMenuItem.setEnabled(true);
				showPathMenuItem.setEnabled(true);
				firstMenuItem.setEnabled(true);
				nextMenuItem.setEnabled(true);
				previousMenuItem.setEnabled(true);
				lastMenuItem.setEnabled(true);
				currentMenuItem.setEnabled(true);
				playMenuItem.setEnabled(true);
				//End activate buttons
				stopMenuItem.setEnabled(false);
				stopToolBarItem.setEnabled(false);
			}

		});

		shell.setLayout(groupLayout);
		shell.pack();
	}


	protected boolean isEnd() {
		return hasEnd ;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void updateGraph(){
		/*
		Graph graph = this.viewer.getGraph();
		graph.setNodeFilter(controller.getNodeFilter());

		Layouter m_layouter = new Layouter(new SpringLayoutStrategy(graph));
		m_layouter.start();

		controller.addManipulatorsAndSelectionModel(viewer);
		viewer.setPopupDisplayer(new SWTPopupDisplayer(display, new DefaultSWTToolTipListener(), new DefaultSWTContextMenuListener(graph, controller.getLensSet(), ZoomControlPanel.DEFAULT_ZOOM_LEVELS, RotateControlPanel.DEFAULT_ROTATE_ANGLES)));
		controller.setPainters(viewer);
		viewer.setAntialias(true);
		*/
		setUpdate(true);
	}

//	public SWTJGraphPane addViewerTo(Composite group) throws ConceptNotFoundException, Exception{
//	JGraphPane jGraphPane;
//	SWTJGraphScrollPane scroll;
//	SWTJGraphViewPane view;
//	//	String conceptUri = "http://websemantica.fundacionctic.org/ontologias/bopa/piscina.owl#Piscina";
//	DefaultGraph graph = controller.createGraphFromOntoSpreadState(controller.next());
//	//DefaultGraph graph = controller.createGraphFromConcept(conceptUri);
//	//DefaultGraph graph = controller.createGraphForSpreading(controller.next());
//	graph.setNodeFilter(controller.getNodeFilter());

//	Layouter m_layouter = new Layouter(new SpringLayoutStrategy(graph));
//	m_layouter.start();

//	jGraphPane = new SWTJGraphPane(group, graph, null);
//	jGraphPane.setLens(controller.getLensSet());
//	controller.addManipulatorsAndSelectionModel(jGraphPane);
//	jGraphPane.setPopupDisplayer(new SWTPopupDisplayer(display, new DefaultSWTToolTipListener(), new DefaultSWTContextMenuListener(graph, controller.getLensSet(), ZoomControlPanel.DEFAULT_ZOOM_LEVELS, RotateControlPanel.DEFAULT_ROTATE_ANGLES)));
//	controller.setPainters(jGraphPane);
//	jGraphPane.setAntialias(true);

//	scroll = new SWTJGraphScrollPane(group, (SWTJGraphPane) jGraphPane, controller.getLensSet());
//	scroll.setParent(group);

//	view = new SWTJGraphViewPane(group, scroll, controller.getLensSet());
//	view.setParent(group);	
//	return (SWTJGraphPane) jGraphPane;
//	}

	public SWTJGraphPane createViewer(Composite group){
		SWTJGraphPane jGraphPane;
		SWTJGraphScrollPane scroll;
		OntoSpreadViewPanel view;
		jGraphPane = new SWTJGraphPane(group, controller.createEmptyGraph());
		jGraphPane.setLens(controller.getLensSet());
		scroll = new SWTJGraphScrollPane(group, (SWTJGraphPane) jGraphPane, controller.getLensSet());
		scroll.setParent(group);
		view = new OntoSpreadViewPanel(group, scroll, controller.getLensSet());
		view.setParent(group);
		return (SWTJGraphPane) jGraphPane;
	}

	public void run() throws Exception{
		controller = new OntoSpreadController();
		createContents();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
	}


	private Event createEvent(SelectionEvent arg0) {
		Event event = new Event();
		event.data = arg0.data;
		event.x = arg0.x;
		event.y = arg0.y;
		return event;
	}

	protected void createErrorDialog(Exception e){
		ErrorDialog error = new ErrorDialog(shell);
		error.setErrorMessage(e.getMessage());
		error.open();
	}


	
	public static void main(String[] args) throws Exception {
		new OntoSpreadViewerMain().run();
	}

}
