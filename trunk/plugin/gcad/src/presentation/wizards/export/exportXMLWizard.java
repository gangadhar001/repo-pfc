package presentation.wizards.export;

import model.business.control.Controller;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class exportXMLWizard extends WizardPage {

	private FileDialog fileDialog;
	private Text fileText;
	private Button selectFileButton;
	private String filePath = "";
	
	public exportXMLWizard() {
		super("nombrwe");
		setTitle("title");
		setDescription("a");
	}

	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayout(layout);	
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);		
		Label destinationLabel = new Label(container, SWT.NONE);
		destinationLabel.setText("titulo");
		
		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		fileText.setEditable(false);
		fileText.setLayoutData(gd);
		
		selectFileButton = new Button(container, SWT.PUSH);
		selectFileButton.setText("Browse ...");
		selectFileButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				fileDialog = new FileDialog(getShell(), SWT.SAVE);
				fileDialog.setFilterNames(new String[] { "XML Files"});
				fileDialog.setFilterExtensions(new String[] { "*.xml" });	
				filePath = fileDialog.open();	
				fileText.setText(filePath);
				wizardChanged();
			}
		});  
			
		wizardChanged();
		setControl(container);
	}

	
	protected void wizardChanged() {		
		// The file path can't be empty
		if (filePath.equals("")) {
			updateStatus("Debes seleccionar un archivo");			
		}
		
		else  
			updateStatus(null);
	}

	 public boolean finish() {
		 boolean result = false;
		 if (Controller.getInstance().isLogged()) {
			 if (!filePath.equals(""))
				 result = true;
			 else
				 updateStatus("Debes seleccionar un archivo");
		 } else
			 MessageDialog.openError(getShell(), "Error", "Debes estar logueado para realizar esta acción");
		 return result;
		 
	}
	 
	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);		
	}
	
	
	public String getFilePath() {
		return filePath;
	}
	 
	

}
