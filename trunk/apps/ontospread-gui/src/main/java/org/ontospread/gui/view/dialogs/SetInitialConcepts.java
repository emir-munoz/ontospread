package org.ontospread.gui.view.dialogs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.ontospread.to.ScoredConceptTO;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class SetInitialConcepts extends Dialog {

	private Table initialConcepts;
	private Combo initialConceptsCombo;
	protected Object result;

	protected Shell shell;
	protected String[] initialConceptsUris;
	protected List<ScoredConceptTO> initials;
	TableEditor valueEditor = null;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public SetInitialConcepts(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public SetInitialConcepts(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(SetInitialConcepts.class, ApplicationResources.getString("SetInitialConcepts.0"))); //$NON-NLS-1$
		shell.addShellListener(new ShellAdapter() {
			public void shellActivated(ShellEvent arg0) {
				initialConceptsCombo.setItems(initialConceptsUris);	
			}
		});
		shell.setSize(560, 238);
		shell.setText(ApplicationResources.getString("SetInitialConcepts.1")); //$NON-NLS-1$

		final Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 30, 534, 137);

		initialConceptsCombo = new Combo(group, SWT.NONE);
		initialConceptsCombo.setBounds(10, 15, 396, 21);

		final Button addButton = new Button(group, SWT.NONE);
		addButton.setImage(SWTResourceManager.getImage(SetInitialConcepts.class, ApplicationResources.getString("SetInitialConcepts.2"))); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if(initialConceptsCombo.getText()!=null && !initialConceptsCombo.getText().equals("")){
				final TableItem ti = new TableItem(initialConcepts,SWT.NONE);
				final Text valueTxt = new Text(initialConcepts,SWT.NONE);
				valueTxt.setText(ApplicationResources.getString("SetInitialConcepts.3")); //$NON-NLS-1$
				valueTxt.computeSize(SWT.DEFAULT, initialConcepts.getItemHeight());
				// Set attributes of the editor
				ti.setText(new String []{initialConceptsCombo.getText(),valueTxt.getText()});				             
				}

			}
		});
		addButton.setBounds(412, 13, 82, 23);
		addButton.setText(ApplicationResources.getString("SetInitialConcepts.4")); //$NON-NLS-1$

		initialConcepts = new Table(group, SWT.BORDER);
		initialConcepts.setBounds(10, 42, 484, 85);
		initialConcepts.setLinesVisible(true);
		initialConcepts.setHeaderVisible(true);
		
		initialConcepts.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// Clean up any previous editor control
				Control oldEditor = valueEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();	

				// Identify the selected row
				int index = initialConcepts.getSelectionIndex ();
				if (index == -1) return;
				final TableItem item = initialConcepts.getItem (index);

				// The control that will be the editor must be a child of the Table
				final Text valueTxt = new Text(initialConcepts, SWT.NONE);

				//The text editor must have the same size as the cell and must
				//not be any smaller than 50 pixels.
				valueEditor.horizontalAlignment = SWT.LEFT;
				valueEditor.grabHorizontal = true;
				valueEditor.minimumWidth = 50;

				
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
				valueEditor.setEditor (valueTxt, item, 1);

				// Assign focus to the text control
				valueTxt.setFocus ();
			}
		 });
		
		
		
		  Menu deleteInitialConcepts = new Menu(shell, SWT.POP_UP);
		   initialConcepts.setMenu(deleteInitialConcepts);
		    MenuItem item = new MenuItem(deleteInitialConcepts, SWT.PUSH);
		    item.setText(ApplicationResources.getString("SetInitialConcepts.5")); //$NON-NLS-1$
		    item.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  initialConcepts.remove(initialConcepts.getSelectionIndices());
		      }
		    });

		final TableColumn uriInitial = new TableColumn(initialConcepts, SWT.NONE);
		uriInitial.setWidth(300);
		uriInitial.setText(ApplicationResources.getString("SetInitialConcepts.6")); //$NON-NLS-1$

		final TableColumn initialValue = new TableColumn(initialConcepts, SWT.NONE);
		initialValue.setWidth(180);
		initialValue.setText(ApplicationResources.getString("SetInitialConcepts.7")); //$NON-NLS-1$

		final Label initialConceptsLabel = new Label(shell, SWT.NONE);
		initialConceptsLabel.setBounds(10, 6, 152, 20);
		initialConceptsLabel.setFont(SWTResourceManager.getFont(ApplicationResources.getString("SetInitialConcepts.8"), 10, SWT.NONE)); //$NON-NLS-1$
		initialConceptsLabel.setText(ApplicationResources.getString("SetInitialConcepts.9")); //$NON-NLS-1$

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.setImage(SWTResourceManager.getImage(SetInitialConcepts.class, ApplicationResources.getString("SetInitialConcepts.10"))); //$NON-NLS-1$
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				result = createActiveConceptsTO();
				shell.close();
				shell.dispose();
			}
		});
		okButton.setBounds(360, 173, 86, 23);
		okButton.setText(ApplicationResources.getString("SetInitialConcepts.11")); //$NON-NLS-1$

		final Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setImage(SWTResourceManager.getImage(SetInitialConcepts.class, ApplicationResources.getString("SetInitialConcepts.12"))); //$NON-NLS-1$
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				result = null;
				shell.close();
				shell.dispose();
			}
		});
		cancelButton.setBounds(458, 173, 86, 23);
		cancelButton.setText(ApplicationResources.getString("SetInitialConcepts.13")); //$NON-NLS-1$
		//
		valueEditor = new TableEditor(initialConcepts);
	}
	
	private ScoredConceptTO[] createActiveConceptsTO(){
		List<ScoredConceptTO> initials = new LinkedList<ScoredConceptTO>();	
		for (TableItem entry : initialConcepts.getItems()) {
			initials.add(new ScoredConceptTO(entry.getText(0),Double.parseDouble(entry.getText(1))));
		}
		return initials.toArray(new ScoredConceptTO[initials.size()]);		
	}

	public String[] getInitialConceptsUris() {
		return initialConceptsUris;
	}

	public void setInitialConceptsUris(String[] initialConceptsUris) {
		this.initialConceptsUris = initialConceptsUris;
	}
}
