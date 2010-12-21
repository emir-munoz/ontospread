package org.ontospread.gui.view.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.ontospread.gui.controller.OntoSpreadController;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class ShowConceptPath extends Dialog {

	private Combo combo;
	protected Object result;

	protected Shell shell;
	private String[] concepts;	

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public ShowConceptPath(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public ShowConceptPath(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(ShowConceptPath.class, ApplicationResources.getString("ShowConceptPath.0"))); //$NON-NLS-1$
		shell.addShellListener(new ShellAdapter() {
			public void shellActivated(ShellEvent arg0) {
				combo.setItems(concepts);
				if(combo.getItemCount()==0){
					//Deactivate button
				}
			}
		});
		shell.setSize(566, 102);
		shell.setText(ApplicationResources.getString("ShowConceptPath.1")); //$NON-NLS-1$

		final Button showButton = new Button(shell, SWT.NONE);
		showButton.setImage(SWTResourceManager.getImage(ShowConceptPath.class, ApplicationResources.getString("ShowConceptPath.2"))); //$NON-NLS-1$
		showButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				try {
					result = combo.getText();
					shell.close();
					shell.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		showButton.setText(ApplicationResources.getString("ShowConceptPath.3")); //$NON-NLS-1$
		showButton.setBounds(442, 22, 88, 23);

		final Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setImage(SWTResourceManager.getImage(ShowConceptPath.class, ApplicationResources.getString("ShowConceptPath.4"))); //$NON-NLS-1$
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				shell.dispose();
			}
		});
		cancelButton.setBounds(465, 51, 88, 22);
		cancelButton.setText(ApplicationResources.getString("ShowConceptPath.5")); //$NON-NLS-1$

		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(10, 24, 426, 21);
		//
	}

	public void setConcepts(String[] allConcepts) {
		this.concepts = allConcepts;	
	}


}
