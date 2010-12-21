package org.ontospread.gui.view.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class EndDialog extends Dialog {

	protected Object result;

	protected Shell shell;

	
	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public EndDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public EndDialog(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(EndDialog.class, ApplicationResources.getString("EndDialog.0"))); //$NON-NLS-1$
		
		shell.setSize(342, 121);
		shell.setText(ApplicationResources.getString("EndDialog.2")); //$NON-NLS-1$

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.setImage(SWTResourceManager.getImage(EndDialog.class, ApplicationResources.getString("EndDialog.1"))); //$NON-NLS-1$

		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				shell.dispose();
			}
		});
		okButton.setBounds(238, 64, 88, 22);
		okButton.setText(ApplicationResources.getString("EndDialog.5")); //$NON-NLS-1$

		final Label endLabel = new Label(shell, SWT.NONE);
		endLabel.setText(ApplicationResources.getString("EndDialog.3")); //$NON-NLS-1$
		endLabel.setBounds(65, 24, 247, 13);

		final Label image = new Label(shell, SWT.NONE);
		image.setImage(SWTResourceManager.getImage(EndDialog.class, ApplicationResources.getString("EndDialog.4")));
		image.setBounds(10, 10, 38, 48);
		//
	}


}
