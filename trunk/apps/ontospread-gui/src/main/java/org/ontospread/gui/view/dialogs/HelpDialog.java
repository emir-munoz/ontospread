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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class HelpDialog extends Dialog {

	protected Object result;

	protected Shell shell;
	protected String errorMessage;
	
	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public HelpDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public HelpDialog(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(HelpDialog.class, ApplicationResources.getString("HelpDialog.0"))); //$NON-NLS-1$
		shell.setImage(SWTResourceManager.getImage(HelpDialog.class, ApplicationResources.getString("ErrorDialog.0"))); //$NON-NLS-1$
		
		shell.setSize(310, 194);
		shell.setText("Sobre OntoSpread"); //$NON-NLS-1$

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				shell.dispose();
			}
		});
		okButton.setImage(SWTResourceManager.getImage(HelpDialog.class, ApplicationResources.getString("HelpDialog.1"))); //$NON-NLS-1$
		okButton.setBounds(178, 137, 98, 22);
		okButton.setText(ApplicationResources.getString("HelpDialog.2")); //$NON-NLS-1$

		final Label proyectoFinDeLabel = new Label(shell, SWT.NONE);
		proyectoFinDeLabel.setText(ApplicationResources.getString("HelpDialog.3")); //$NON-NLS-1$
		proyectoFinDeLabel.setBounds(10, 10, 207, 13);

		final Label proyectoFinDeLabel_1 = new Label(shell, SWT.NONE);
		proyectoFinDeLabel_1.setBounds(10, 29, 264, 13);
		proyectoFinDeLabel_1.setText(ApplicationResources.getString("HelpDialog.4")); //$NON-NLS-1$

		final Label proyectoFinDeLabel_1_1 = new Label(shell, SWT.NONE);
		proyectoFinDeLabel_1_1.setBounds(10, 50, 264, 13);
		proyectoFinDeLabel_1_1.setText(ApplicationResources.getString("HelpDialog.5")); //$NON-NLS-1$

		final Label proyectoFinDeLabel_1_1_1 = new Label(shell, SWT.NONE);
		proyectoFinDeLabel_1_1_1.setBounds(10, 70, 264, 13);
		proyectoFinDeLabel_1_1_1.setText(ApplicationResources.getString("HelpDialog.6")); //$NON-NLS-1$

		final Label proyectoFinDeLabel_1_1_1_1 = new Label(shell, SWT.NONE);
		proyectoFinDeLabel_1_1_1_1.setBounds(10, 89, 264, 13);
		proyectoFinDeLabel_1_1_1_1.setText(ApplicationResources.getString("HelpDialog.7")); //$NON-NLS-1$

		final Label proyectoFinDeLabel_1_1_1_1_1 = new Label(shell, SWT.NONE);
		proyectoFinDeLabel_1_1_1_1_1.setBounds(10, 108, 264, 13);
		proyectoFinDeLabel_1_1_1_1_1.setText(ApplicationResources.getString("HelpDialog.8")); //$NON-NLS-1$

		final Link eclipseorgLink = new Link(shell, SWT.NONE);
		eclipseorgLink.setText(ApplicationResources.getString("HelpDialog.9")); //$NON-NLS-1$
		eclipseorgLink.setBounds(10, 137, 71, 13);
		//
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



}
