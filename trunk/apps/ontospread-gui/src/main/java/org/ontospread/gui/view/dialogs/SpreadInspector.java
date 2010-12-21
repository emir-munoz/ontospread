package org.ontospread.gui.view.dialogs;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.ontospread.constraints.OntoSpreadRelationWeight;
import org.ontospread.gui.to.PairRelationActiveTO;
import org.ontospread.to.ScoredConceptTO;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class SpreadInspector extends Dialog {

	private Table relationsWeight;
	private Table activatedConcepts;
	private Table initialConcepts;
	protected Object result;
	private ScoredConceptTO[] initialConceptsTOs;
	TableEditor valueEditorActive = null;
	TableEditor valueEditorRelations = null;

	protected Shell shell;
	private OntoSpreadRelationWeight relationWeight;
	private List<ScoredConceptTO> activateConcepts;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public SpreadInspector(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public SpreadInspector(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(SpreadInspector.class, ApplicationResources.getString("SpreadInspector.0"))); //$NON-NLS-1$
		shell.addShellListener(new ShellAdapter() {
		
			public void shellActivated(ShellEvent arg0) {
				loadInitialConcepts();	
				loadRelationsWeight();
				loadActiveConcepts();
			}
		});
		shell.setSize(510, 544);
		shell.setText(ApplicationResources.getString("SpreadInspector.1")); //$NON-NLS-1$

		final Label initialConceptsLabel = new Label(shell, SWT.NONE);
		initialConceptsLabel.setBounds(10, 10, 152, 20);
		initialConceptsLabel.setFont(SWTResourceManager.getFont(ApplicationResources.getString("SpreadInspector.2"), 10, SWT.NONE)); //$NON-NLS-1$
		initialConceptsLabel.setText(ApplicationResources.getString("SpreadInspector.3")); //$NON-NLS-1$


		final Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 34, 476, 137);



		initialConcepts = new Table(group, SWT.BORDER);
		initialConcepts.setBounds(10, 24, 446, 103);
		initialConcepts.setLinesVisible(true);
		initialConcepts.setHeaderVisible(true);
		TableColumn uriInitial  = new TableColumn(initialConcepts,SWT.LEFT);
		  uriInitial.setText(ApplicationResources.getString("SpreadInspector.4")); //$NON-NLS-1$
		  uriInitial.setWidth(300);
		 TableColumn initialValue  =
		    new TableColumn(initialConcepts,SWT.LEFT);
		  initialValue.setText(ApplicationResources.getString("SpreadInspector.5")); //$NON-NLS-1$
		  initialValue.setWidth(142);
	

		final Label initialConceptsLabel_1 = new Label(shell, SWT.NONE);
		initialConceptsLabel_1.setBounds(10, 182, 152, 20);
		initialConceptsLabel_1.setFont(SWTResourceManager.getFont(ApplicationResources.getString("SpreadInspector.6"), 10, SWT.NONE)); //$NON-NLS-1$
		initialConceptsLabel_1.setText(ApplicationResources.getString("SpreadInspector.7")); //$NON-NLS-1$

		final Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(10, 208, 476, 113);

		activatedConcepts = new Table(group_1, SWT.BORDER);
		activatedConcepts.setBounds(10, 10, 443, 93);
		activatedConcepts.setLinesVisible(true);
		activatedConcepts.setHeaderVisible(true);

		TableColumn uriActive  = new TableColumn(activatedConcepts,SWT.LEFT);
		uriActive.setText(ApplicationResources.getString("SpreadInspector.8")); //$NON-NLS-1$
		uriActive.setWidth(282);
		 TableColumn activeValue  =
		    new TableColumn(activatedConcepts,SWT.LEFT);
		 	activeValue.setText(ApplicationResources.getString("SpreadInspector.9")); //$NON-NLS-1$
		  activeValue.setWidth(156);
		  
		   
		  
		final Label initialConceptsLabel_1_1 = new Label(shell, SWT.NONE);
		initialConceptsLabel_1_1.setBounds(10, 341, 152, 20);
		initialConceptsLabel_1_1.setFont(SWTResourceManager.getFont(ApplicationResources.getString("SpreadInspector.10"), 10, SWT.NONE)); //$NON-NLS-1$
		initialConceptsLabel_1_1.setText(ApplicationResources.getString("SpreadInspector.11")); //$NON-NLS-1$

		final Group group_1_1 = new Group(shell, SWT.NONE);
		group_1_1.setBounds(10, 367, 476, 113);

		relationsWeight = new Table(group_1_1, SWT.BORDER);
		relationsWeight.setBounds(10, 10, 443, 93);
		relationsWeight.setLinesVisible(true);
		relationsWeight.setHeaderVisible(true);
		TableColumn uriRelation  = new TableColumn(relationsWeight,SWT.LEFT);
		uriRelation.setText(ApplicationResources.getString("SpreadInspector.12")); //$NON-NLS-1$
		uriRelation.setWidth(282);
		 TableColumn relationValue  =
		    new TableColumn(relationsWeight,SWT.LEFT);
		 relationValue.setText(ApplicationResources.getString("SpreadInspector.13")); //$NON-NLS-1$
		  relationValue.setWidth(156);

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.setImage(SWTResourceManager.getImage(SpreadInspector.class, ApplicationResources.getString("SpreadInspector.14"))); //$NON-NLS-1$
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				ScoredConceptTO[] activeConceptsTOs = new ScoredConceptTO[activatedConcepts.getItems().length];
				ScoredConceptTO[] relationConceptsTOs = new ScoredConceptTO[relationsWeight.getItems().length];
				int i = 0;
				for (TableItem entry : activatedConcepts.getItems()) {
					activeConceptsTOs[i++]=new ScoredConceptTO(entry.getText(0),Double.parseDouble(entry.getText(1)));
				}
				i=0;
				for (TableItem entry : relationsWeight.getItems()) {
					relationConceptsTOs[i++] = (new ScoredConceptTO(entry.getText(0),Double.parseDouble(entry.getText(1))));
				}
				result = new PairRelationActiveTO(activeConceptsTOs,relationConceptsTOs);
				shell.close();
				shell.dispose();
			}
		});
		okButton.setBounds(324, 486, 86, 23);
		okButton.setText(ApplicationResources.getString("SpreadInspector.15")); //$NON-NLS-1$

		final Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setImage(SWTResourceManager.getImage(SpreadInspector.class, ApplicationResources.getString("SpreadInspector.16"))); //$NON-NLS-1$
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				shell.dispose();
			}
		});
		cancelButton.setBounds(416, 486, 78, 23);
		cancelButton.setText(ApplicationResources.getString("SpreadInspector.17")); //$NON-NLS-1$
		//
		valueEditorActive = new TableEditor(this.activatedConcepts);
		valueEditorRelations = new TableEditor(this.relationsWeight);
		
		activatedConcepts.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// Clean up any previous editor control
				Control oldEditor = valueEditorActive.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();	

				// Identify the selected row
				int index = activatedConcepts.getSelectionIndex ();
				if (index == -1) return;
				final TableItem item = activatedConcepts.getItem (index);

				// The control that will be the editor must be a child of the Table
				final Text valueTxt = new Text(activatedConcepts, SWT.NONE);

				//The text editor must have the same size as the cell and must
				//not be any smaller than 50 pixels.
				valueEditorActive.horizontalAlignment = SWT.LEFT;
				valueEditorActive.grabHorizontal = true;
				valueEditorActive.minimumWidth = 50;

				
				Listener textListener = new Listener() {
	                public void handleEvent(final Event e) {
	                  switch (e.type) {
	                  case SWT.FocusOut:
	                	item.setText(1, valueTxt.getText());	                	
	                    valueTxt.dispose();
	                    break;
	                  case SWT.Traverse:
	                    switch (e.detail) {
	                    case SWT.TRAVERSE_RETURN:
	                    	item.setText(1, valueTxt.getText());	  	                  
	                    // FALL THROUGH
	                    case SWT.TRAVERSE_ESCAPE:
	                      valueTxt.dispose();
	                      e.doit = false;
	                    }
	                    break;
	                  }
	                }
	              };
	              valueTxt.addListener(SWT.FocusOut, textListener);
	              valueTxt.addListener(SWT.Traverse, textListener);
	              
				// Open the text editor in the second column of the selected row.
				valueEditorActive.setEditor (valueTxt, item, 1);

				// Assign focus to the text control
				valueTxt.setFocus ();
			}
		 });

		relationsWeight.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// Clean up any previous editor control
				Control oldEditor = valueEditorRelations.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();	

				// Identify the selected row
				int index = relationsWeight.getSelectionIndex ();
				if (index == -1) return;
				final TableItem item = relationsWeight.getItem (index);

				// The control that will be the editor must be a child of the Table
				final Text valueTxt = new Text(relationsWeight, SWT.NONE);

				//The text editor must have the same size as the cell and must
				//not be any smaller than 50 pixels.
				valueEditorRelations.horizontalAlignment = SWT.LEFT;
				valueEditorRelations.grabHorizontal = true;
				valueEditorRelations.minimumWidth = 50;

				
				Listener textListener = new Listener() {
	                public void handleEvent(final Event e) {
	                  switch (e.type) {
	                  case SWT.FocusOut:
	                	item.setText(1, valueTxt.getText());	                	
	                    valueTxt.dispose();
	                    break;
	                  case SWT.Traverse:
	                    switch (e.detail) {
	                    case SWT.TRAVERSE_RETURN:
	                    	item.setText(1, valueTxt.getText());	  	                  
	                    // FALL THROUGH
	                    case SWT.TRAVERSE_ESCAPE:
	                      valueTxt.dispose();
	                      e.doit = false;
	                    }
	                    break;
	                  }
	                }
	              };
	              valueTxt.addListener(SWT.FocusOut, textListener);
	              valueTxt.addListener(SWT.Traverse, textListener);
	              
				// Open the text editor in the second column of the selected row.
				valueEditorRelations.setEditor (valueTxt, item, 1);

				// Assign focus to the text control
				valueTxt.setFocus ();
			}
		 });

		
	}

	protected void loadInitialConcepts() {
		
		for (ScoredConceptTO scoredConcept : this.initialConceptsTOs) {
			TableItem ti = new TableItem(this.initialConcepts,SWT.NONE);			
			ti.setText(new String []{scoredConcept.getConceptUri(),String.valueOf(scoredConcept.getScore())});			
		}
	}


	public void setInitialConceptsTOs(ScoredConceptTO[] conceptTOs) {
		this.initialConceptsTOs = conceptTOs;
	}
	
	public void loadActiveConcepts(){
		for (final ScoredConceptTO activeConceptTO : this.activateConcepts) {
			TableItem ti = new TableItem(this.activatedConcepts,SWT.NONE);			
			ti.setText(new String []{activeConceptTO.getConceptUri(),String.valueOf(activeConceptTO.getScore())});						            
		}
		
	}

	
	public void loadRelationsWeight(){
		List<String> uris = new LinkedList<String>(this.relationWeight.getRelationsUris());
		Collections.sort(uris);
		for (final String string : uris) {
			TableItem ti = new TableItem(this.relationsWeight,SWT.NONE);			
			ti.setText(new String []{string,String.valueOf(this.relationWeight.getWeight(string))});						            
		}

		
	}
	public void setRelationsWeight(OntoSpreadRelationWeight relationWeight) {
		this.relationWeight = relationWeight;
	}

	public void setActiveConcepts(List<ScoredConceptTO> activeConcepts) {
		
		this.activateConcepts = activeConcepts;
	}




}
