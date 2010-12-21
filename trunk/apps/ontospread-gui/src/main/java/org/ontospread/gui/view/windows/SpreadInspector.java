package org.ontospread.gui.view.windows;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import com.swtdesigner.SWTResourceManager;

public class SpreadInspector extends Shell {

	private Table relationsWeight;
	private Table activatedConcepts;
	private Table initialConcepts;
	private Combo combo_1;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			SpreadInspector shell = new SpreadInspector(display,
					SWT.SHELL_TRIM);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell
	 * @param display
	 * @param style
	 */
	public SpreadInspector(Display display, int style) {
		super(display, style);
		createContents();
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		setText("Spread Inspector");
		setSize(523, 514);

		final Button cancelButton = new Button(this, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.setBounds(429, 458, 63, 23);

		final Button okButton = new Button(this, SWT.NONE);
		okButton.setText("Update");
		okButton.setBounds(334, 458, 86, 23);

		final Group group = new Group(this, SWT.NONE);
		group.setBounds(10, 34, 476, 113);

		combo_1 = new Combo(group, SWT.NONE);
		combo_1.setBounds(10, 15, 396, 21);

		final Button addButton = new Button(group, SWT.NONE);
		addButton.setBounds(412, 13, 44, 23);
		addButton.setText("Add");

		final Button deleteButton_1 = new Button(group, SWT.NONE);
		deleteButton_1.setBounds(412, 55, 44, 23);
		deleteButton_1.setText("Delete");
		
		initialConcepts = new Table(group, SWT.FULL_SELECTION | SWT.BORDER);

		TableColumn uriInitial  = new TableColumn(initialConcepts,SWT.LEFT);
			  uriInitial.setText("Uri");
			  uriInitial.setWidth(300);
		TableColumn initialValue  =
			    new TableColumn(initialConcepts,SWT.LEFT);
			  initialValue.setText("Initial Value");
			  initialValue.setWidth(100);


		initialConcepts.setLinesVisible(true);
		initialConcepts.setHeaderVisible(true);
		initialConcepts.setBounds(10, 42, 396, 49);

		final Label initialConceptsLabel = new Label(this, SWT.NONE);
		initialConceptsLabel.setBounds(10, 10, 152, 20);
		initialConceptsLabel.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		initialConceptsLabel.setText("Initial Concepts");

		final Group group_1 = new Group(this, SWT.NONE);
		group_1.setBounds(10, 183, 476, 113);

		activatedConcepts = new Table(group_1, SWT.BORDER);
		activatedConcepts.setBounds(10, 10, 443, 81);
		activatedConcepts.setLinesVisible(true);
		activatedConcepts.setHeaderVisible(true);

		final Label initialConceptsLabel_1 = new Label(this, SWT.NONE);
		initialConceptsLabel_1.setBounds(10, 159, 152, 20);
		initialConceptsLabel_1.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		initialConceptsLabel_1.setText("Actived Concepts");

		final Group group_1_1 = new Group(this, SWT.NONE);
		group_1_1.setBounds(10, 323, 476, 113);

		relationsWeight = new Table(group_1_1, SWT.BORDER);
		relationsWeight.setBounds(10, 10, 443, 93);
		relationsWeight.setLinesVisible(true);
		relationsWeight.setHeaderVisible(true);

		final Label initialConceptsLabel_1_1 = new Label(this, SWT.NONE);
		initialConceptsLabel_1_1.setBounds(10, 302, 152, 20);
		initialConceptsLabel_1_1.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		initialConceptsLabel_1_1.setText("Relations Weights");
		//
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
