package org.ontospread.gui.view.windows;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import com.swtdesigner.SWTResourceManager;

public class LoadConfiguration extends Shell {

	private Text defaultValue;
	private Text context;
	private Text time;
	private Text minConcepts;
	private Text maxConcepts;
	private Text activationValue;
	private List list;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			LoadConfiguration shell = new LoadConfiguration(display,
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
	public LoadConfiguration(Display display, int style) {
		super(display, style);
		createContents();
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		setText("Load Configuration");
		setSize(514, 494);

		final Button cancelButton = new Button(this, SWT.NONE);
		cancelButton.setText("Cancel");
		cancelButton.setBounds(428, 433, 58, 23);

		final Button okButton = new Button(this, SWT.NONE);
		okButton.setText("Ok");
		okButton.setBounds(350, 433, 72, 23);

		final Group resourcesGroup = new Group(this, SWT.NONE);
		resourcesGroup.setBounds(10, 34, 476, 80);

		list = new List(resourcesGroup, SWT.BORDER);
		list.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(list.getItem(list.getSelectionIndex()));
			}
		});
		list.setItems(new String[] {"linea0", "linea1"});
		list.setBounds(10, 15,406, 54);

		final Button deleteButton = new Button(resourcesGroup, SWT.NONE);
		deleteButton.setText("Delete");
		deleteButton.setBounds(422, 47, 44, 23);

		final Label label = new Label(resourcesGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(158, 70, -28, 30);

		final Button newButton = new Button(resourcesGroup, SWT.NONE);
		newButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				selectFile();
			}
		});

		newButton.setText("Add");
		newButton.setBounds(422, 14, 44, 23);

		final Group group_1 = new Group(this, SWT.NONE);
		group_1.setBounds(10, 140, 476, 113);

		final Button activationValueButton = new Button(group_1, SWT.CHECK);
		activationValueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				activationValue.setEnabled(!activationValue.getEnabled());
			}
		
		});
		activationValueButton.setText("Activation Value");
		activationValueButton.setBounds(10, 19, 98, 16);

		final Button maxConceptsButton = new Button(group_1, SWT.CHECK);
		maxConceptsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				maxConcepts.setEnabled(true);
			}
		});
		maxConceptsButton.setText("Max Concepts");
		maxConceptsButton.setBounds(238, 19, 85, 16);

		final Button minConceptsButton = new Button(group_1, SWT.CHECK);
		minConceptsButton.setText("Min Concepts");
		minConceptsButton.setBounds(238, 52, 85, 16);

		final Button contextButton = new Button(group_1, SWT.CHECK);
		contextButton.setText("Context");
		contextButton.setBounds(10, 85, 85, 16);

		final Button timeButton = new Button(group_1, SWT.CHECK);
		timeButton.setText("Time");
		timeButton.setBounds(10, 52, 85, 16);

		activationValue = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		activationValue.setEnabled(false);
		activationValue.setText("0");
		activationValue.setBounds(121, 18, 76, 16);

		maxConcepts = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		maxConcepts.setEnabled(false);
		maxConcepts.setText("0");
		maxConcepts.setBounds(329, 18, 76, 16);

		minConcepts = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		minConcepts.setEnabled(false);
		minConcepts.setText("0");
		minConcepts.setBounds(329, 51, 76, 16);

		time = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		time.setEnabled(false);
		time.setText("0");
		time.setBounds(121, 52, 76, 16);

		context = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		context.setEnabled(false);
		context.setBounds(121, 85, 284, 16);

		final Group group_2 = new Group(this, SWT.NONE);
		group_2.setBounds(10, 279, 476, 47);

		final Button defaultButton = new Button(group_2, SWT.CHECK);
		defaultButton.setText("Default");
		defaultButton.setBounds(10, 19, 85, 16);

		defaultValue = new Text(group_2, SWT.RIGHT | SWT.BORDER);
		defaultValue.setText("0");
		defaultValue.setBounds(123, 19, 76, 16);

		final Group group_3 = new Group(this, SWT.NONE);
		group_3.setBounds(10, 367, 476, 60);

		final Button prizePaths = new Button(group_3, SWT.CHECK);
		prizePaths.setText("Prize Paths");
		prizePaths.setBounds(10, 14, 85, 16);

		final Label relationsWeighLabel = new Label(this, SWT.NONE);
		relationsWeighLabel.setBounds(10, 259,199, 20);
		relationsWeighLabel.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		relationsWeighLabel.setText("Relations Weight");

		final Label loadOntologiesLabel = new Label(this, SWT.NONE);
		loadOntologiesLabel.setBounds(10, 14,164, 20);
		loadOntologiesLabel.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		loadOntologiesLabel.setText("Load Ontologies");

		final Label othersLabel = new Label(this, SWT.NONE);
		othersLabel.setBounds(10, 345,113, 20);
		othersLabel.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		othersLabel.setText("Others");

		final Label relationsWeighLabel_1 = new Label(this, SWT.NONE);
		relationsWeighLabel_1.setBounds(10, 120, 199, 20);
		relationsWeighLabel_1.setFont(SWTResourceManager.getFont("", 10, SWT.NONE));
		relationsWeighLabel_1.setText("Restrictions");
		//
	}

	protected void selectFile() {
		FileDialog fd = new FileDialog(this);
		fd.setText("Load resources");		
		fd.open();
		list.add(fd.getFilterPath()+File.separator+fd.getFileName());		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
