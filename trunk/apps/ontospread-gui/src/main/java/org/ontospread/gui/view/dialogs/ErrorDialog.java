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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class ErrorDialog extends Dialog {

	private Text noSeHaText;
	protected Object result;

	protected Shell shell;
	protected String errorMessage;
	
	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public ErrorDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public ErrorDialog(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(ErrorDialog.class, ApplicationResources.getString("ErrorDialog.0"))); //$NON-NLS-1$
		
		shell.setSize(438, 135);
		shell.setText(ApplicationResources.getString("ErrorDialog.1")); //$NON-NLS-1$

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.setImage(SWTResourceManager.getImage(ErrorDialog.class, ApplicationResources.getString("ErrorDialog.2"))); //$NON-NLS-1$
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				shell.dispose();
			}
		});
		okButton.setBounds(331, 78, 88, 22);
		okButton.setText(ApplicationResources.getString("ErrorDialog.3")); //$NON-NLS-1$

		final Label seHaProducidoLabel = new Label(shell, SWT.NONE);
		seHaProducidoLabel.setText(ApplicationResources.getString("ErrorDialog.4")); //$NON-NLS-1$
		seHaProducidoLabel.setBounds(10, 10, 247, 13);

		noSeHaText = new Text(shell, SWT.BORDER);
		noSeHaText.setBounds(10, 29, 315, 71);
		noSeHaText.setText(getErrorMessage());
		//
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



}
