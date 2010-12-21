package org.ontospread.gui.view.dialogs;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.ontospread.gui.view.forms.LoadConfigurationForm;

import resources.ApplicationResources;

import com.swtdesigner.SWTResourceManager;

public class LoadConfiguration extends Dialog {

	private Text retries;
	private Text defaultValue;
	private List resources;
	private Text context;
	private Text time;
	private Text minConcepts;
	private Text maxConcepts;
	private Text activationValue;
	protected Object result;

	protected Shell shell;
	private String relationsFile;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public LoadConfiguration(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public LoadConfiguration(Shell parent) {
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
		shell.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.0"))); //$NON-NLS-1$
		shell.setSize(549, 493);
		shell.setText(ApplicationResources.getString("LoadConfiguration.1")); //$NON-NLS-1$

		final Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(10, 136, 523, 113);

		final Button activationValueButton = new Button(group_1, SWT.CHECK);
		activationValueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				activationValue.setEnabled(!activationValue.getEnabled());
				if(!activationValue.getEnabled()){
					activationValue.setText(ApplicationResources.getString("LoadConfiguration.2")); //$NON-NLS-1$
				}
			}
		});
		activationValueButton.setBounds(10, 19, 98, 16);
		activationValueButton.setText(ApplicationResources.getString("LoadConfiguration.3")); //$NON-NLS-1$

		final Button maxConceptsButton = new Button(group_1, SWT.CHECK);
		maxConceptsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				maxConcepts.setEnabled(!maxConcepts.getEnabled());
				if(!maxConcepts.getEnabled()){
					maxConcepts.setText(ApplicationResources.getString("LoadConfiguration.4")); //$NON-NLS-1$
				}
			}
		});
		maxConceptsButton.setBounds(312, 19, 85, 16);
		maxConceptsButton.setText(ApplicationResources.getString("LoadConfiguration.5")); //$NON-NLS-1$

		final Button minConceptsButton = new Button(group_1, SWT.CHECK);
		minConceptsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				minConcepts.setEnabled(!minConcepts.getEnabled());
				if(!minConcepts.getEnabled()){
					minConcepts.setText(ApplicationResources.getString("LoadConfiguration.6")); //$NON-NLS-1$
				}
			}
		});
		minConceptsButton.setBounds(312, 52, 85, 16);
		minConceptsButton.setText(ApplicationResources.getString("LoadConfiguration.7")); //$NON-NLS-1$

		final Button contextButton = new Button(group_1, SWT.CHECK);
		contextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				context.setEnabled(!context.getEnabled());
				retries.setEnabled(!retries.getEnabled());
				if(!context.getEnabled()){
					context.setText(ApplicationResources.getString("LoadConfiguration.8")); //$NON-NLS-1$
					retries.setText(ApplicationResources.getString("LoadConfiguration.9")); //$NON-NLS-1$
				}
			}
		});
		contextButton.setBounds(10, 85, 85, 16);
		contextButton.setText(ApplicationResources.getString("LoadConfiguration.10")); //$NON-NLS-1$

		final Button timeButton = new Button(group_1, SWT.CHECK);
		timeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				time.setEnabled(!time.getEnabled());
				if(!time.getEnabled()){
					time.setText(ApplicationResources.getString("LoadConfiguration.11")); //$NON-NLS-1$
				}
			}
		});
		timeButton.setBounds(10, 52, 85, 16);
		timeButton.setText(ApplicationResources.getString("LoadConfiguration.12")); //$NON-NLS-1$

		activationValue = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		activationValue.setText(ApplicationResources.getString("LoadConfiguration.13")); //$NON-NLS-1$
		activationValue.setEnabled(false);
		activationValue.setBounds(195, 18, 76, 16);

		maxConcepts = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		maxConcepts.setText(ApplicationResources.getString("LoadConfiguration.14")); //$NON-NLS-1$
		maxConcepts.setEnabled(false);
		maxConcepts.setBounds(424, 18, 76, 16);

		minConcepts = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		minConcepts.setText(ApplicationResources.getString("LoadConfiguration.15")); //$NON-NLS-1$
		minConcepts.setEnabled(false);
		minConcepts.setBounds(424, 51, 76, 16);

		time = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		time.setText(ApplicationResources.getString("LoadConfiguration.16")); //$NON-NLS-1$
		time.setEnabled(false);
		time.setBounds(195, 51, 76, 16);

		context = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		context.setEnabled(false);
		context.setBounds(121, 84, 150, 16);

		retries = new Text(group_1, SWT.RIGHT | SWT.BORDER);
		retries.setText(ApplicationResources.getString("LoadConfiguration.17")); //$NON-NLS-1$
		retries.setBounds(424, 84, 76, 16);
		retries.setEnabled(false);

		final Label retriesLabel = new Label(group_1, SWT.NONE);
		retriesLabel.setText(ApplicationResources.getString("LoadConfiguration.18")); //$NON-NLS-1$
		retriesLabel.setBounds(363, 87, 34, 13);

		final Label relationsWeighLabel_1 = new Label(shell, SWT.NONE);
		relationsWeighLabel_1.setBounds(10, 116, 199, 20);
		relationsWeighLabel_1.setFont(SWTResourceManager.getFont(ApplicationResources.getString("LoadConfiguration.19"), 10, SWT.NONE)); //$NON-NLS-1$
		relationsWeighLabel_1.setText(ApplicationResources.getString("LoadConfiguration.20")); //$NON-NLS-1$

		final Group resourcesGroup = new Group(shell, SWT.NONE);
		resourcesGroup.setBounds(10, 30, 523, 80);

		resources = new List(resourcesGroup, SWT.BORDER);
		resources.setBounds(10, 15, 406, 54);

		final Button deleteButton = new Button(resourcesGroup, SWT.NONE);
		deleteButton.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.21"))); //$NON-NLS-1$
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				resources.remove(resources.getSelectionIndex());
			}
		});
		deleteButton.setBounds(422, 47, 80, 23);
		deleteButton.setText(ApplicationResources.getString("LoadConfiguration.22")); //$NON-NLS-1$

		final Label label = new Label(resourcesGroup, SWT.HORIZONTAL | SWT.SEPARATOR);
		label.setBounds(158, 70, 0, 30);
		label.setText(ApplicationResources.getString("LoadConfiguration.23")); //$NON-NLS-1$

		final Button newButton = new Button(resourcesGroup, SWT.NONE);
		newButton.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.24"))); //$NON-NLS-1$
		newButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				selectFile();
			}
		});
		newButton.setBounds(422, 14, 80, 23);
		newButton.setText(ApplicationResources.getString("LoadConfiguration.25")); //$NON-NLS-1$

		final Label loadOntologiesLabel = new Label(shell, SWT.NONE);
		loadOntologiesLabel.setBounds(10, 10, 164, 20);
		loadOntologiesLabel.setFont(SWTResourceManager.getFont(ApplicationResources.getString("LoadConfiguration.26"), 10, SWT.NONE)); //$NON-NLS-1$
		loadOntologiesLabel.setText(ApplicationResources.getString("LoadConfiguration.27")); //$NON-NLS-1$

		final Label relationsWeighLabel = new Label(shell, SWT.NONE);
		relationsWeighLabel.setBounds(10, 255, 199, 20);
		relationsWeighLabel.setFont(SWTResourceManager.getFont(ApplicationResources.getString("LoadConfiguration.28"), 10, SWT.NONE)); //$NON-NLS-1$
		relationsWeighLabel.setText(ApplicationResources.getString("LoadConfiguration.29")); //$NON-NLS-1$

		final Label othersLabel = new Label(shell, SWT.NONE);
		othersLabel.setBounds(10, 341, 113, 20);
		othersLabel.setFont(SWTResourceManager.getFont(ApplicationResources.getString("LoadConfiguration.30"), 10, SWT.NONE)); //$NON-NLS-1$
		othersLabel.setText(ApplicationResources.getString("LoadConfiguration.31")); //$NON-NLS-1$

		final Group group_3 = new Group(shell, SWT.NONE);
		group_3.setBounds(10, 363, 523, 60);

		final Button prizePaths = new Button(group_3, SWT.CHECK);
		prizePaths.setBounds(32, 14, 85, 16);
		prizePaths.setText(ApplicationResources.getString("LoadConfiguration.32")); //$NON-NLS-1$

		final Group group = new Group(group_3, SWT.NONE);
		group.setBounds(280, 14, 233, 36);

		final Button h1Button = new Button(group, SWT.RADIO);
		h1Button.setText(ApplicationResources.getString("LoadConfiguration.33")); //$NON-NLS-1$
		h1Button.setBounds(10, 10, 83, 16);

		final Button h2Button = new Button(group, SWT.RADIO);
		h2Button.setBounds(93, 10, 83, 16);
		h2Button.setText(ApplicationResources.getString("LoadConfiguration.34")); //$NON-NLS-1$

		final Label retriesLabel_1_1 = new Label(group_3, SWT.NONE);
		retriesLabel_1_1.setBounds(198, 16, 76, 13);
		retriesLabel_1_1.setText(ApplicationResources.getString("LoadConfiguration.35")); //$NON-NLS-1$

		final Button okButton = new Button(shell, SWT.NONE);
		okButton.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.36"))); //$NON-NLS-1$
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				//
				LoadConfigurationForm form = new LoadConfigurationForm();
				form.setResources(resources.getItems());
				form.setActivationValue(Double.parseDouble(activationValue.getText()));
				form.setContext(context.getText());
				form.setRetries(Integer.parseInt(retries.getText()));
				form.setDefaultValue(Double.parseDouble(defaultValue.getText()));
				form.setMaxConcepts(Integer.parseInt(maxConcepts.getText()));
				form.setMinConcepts(Integer.parseInt(minConcepts.getText()));
				form.setPrizePaths(prizePaths.getSelection());
				form.setRelationsFile(relationsFile);		
				result = form;
				shell.close();
				shell.dispose();
			}
		});
		okButton.setBounds(367, 429, 80, 23);
		okButton.setText(ApplicationResources.getString("LoadConfiguration.37")); //$NON-NLS-1$

		final Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.38"))); //$NON-NLS-1$
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				result = null;
				shell.close();
				shell.dispose();
			}
		});
		cancelButton.setBounds(453, 429, 80, 23);
		cancelButton.setText(ApplicationResources.getString("LoadConfiguration.39")); //$NON-NLS-1$

		final Group group_2 = new Group(shell, SWT.NONE);
		group_2.setBounds(10, 275, 523, 60);

		defaultValue = new Text(group_2, SWT.RIGHT | SWT.BORDER);
		defaultValue.setText(ApplicationResources.getString("LoadConfiguration.40")); //$NON-NLS-1$
		defaultValue.setBounds(121, 16, 76, 16);

		final Label retriesLabel_1 = new Label(group_2, SWT.NONE);
		retriesLabel_1.setBounds(10, 19, 76, 13);
		retriesLabel_1.setText(ApplicationResources.getString("LoadConfiguration.41")); //$NON-NLS-1$

		final Label loadRelationsLabel = new Label(group_2, SWT.NONE);
		loadRelationsLabel.setText(ApplicationResources.getString("LoadConfiguration.42")); //$NON-NLS-1$
		loadRelationsLabel.setBounds(10, 38, 490, 13);

		final Button loadRelationsBtn = new Button(group_2, SWT.NONE);
		loadRelationsBtn.setImage(SWTResourceManager.getImage(LoadConfiguration.class, ApplicationResources.getString("LoadConfiguration.43"))); //$NON-NLS-1$
		loadRelationsBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				selectRelationsFile();
				loadRelationsLabel.setText(relationsFile);
			}
		});
		loadRelationsBtn.setText(ApplicationResources.getString("LoadConfiguration.44")); //$NON-NLS-1$
		loadRelationsBtn.setBounds(227, 10, 76, 25);
		//
	}

	protected void selectFile() {
		FileDialog fd = new FileDialog(shell);
		fd.setText(ApplicationResources.getString("LoadConfiguration.45"));		 //$NON-NLS-1$
		fd.open();
		resources.add(fd.getFilterPath()+File.separator+fd.getFileName());		
	}
	
	protected void selectRelationsFile() {
		FileDialog fd = new FileDialog(shell);
		fd.setText(ApplicationResources.getString("LoadConfiguration.46"));		 //$NON-NLS-1$
		fd.open();
		this.relationsFile = fd.getFilterPath()+File.separator+fd.getFileName();		
	}

}
