package presentation.utils;

import gcad.Activator;
import internationalization.BundleInternationalization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import model.business.control.Controller;
import model.business.knowledge.Language;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * This class is used to show a dialog in order to choose an available language
 */
public class LanguageDialog extends Dialog {

	private Shell shell;
	private String languageCode;
	private String currentLanguage;
	private String path;
	
	public LanguageDialog(Shell parentShell) {
		super(parentShell);
		currentLanguage = System.getProperty("osgi.nl");
	}
	
	 protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      this.shell = shell;
	      shell.setText(BundleInternationalization.getString("Choose_LanguageDialog_Title"));
	 }
	
	protected Control createContents(Composite parent) {	
		ArrayList<Language> languages = new ArrayList<Language>();
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData gd = new GridData(GridData.FILL_BOTH);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		
		// Retrieve available languages
		try {
			languages = Controller.getInstance().getLanguages();
			for (Language l: languages) {
				Button radioButton = new Button(composite, SWT.RADIO);
				radioButton.setText(l.getName());
				radioButton.setLayoutData(new GridData(200,20));
				if (l.getCode().equals(currentLanguage))
					radioButton.setSelection(true);
				radioButton.setData("code", l.getCode());
				radioButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						languageCode = ((Button)e.widget).getData("code").toString();
					}					
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {}
					
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		Composite comp = new Composite(composite, SWT.NONE);
		comp.setLayout(new GridLayout(2, true));
		comp.setLayoutData(gd);
		
		Button btnOk = new Button(comp, SWT.PUSH);
		btnOk.setLayoutData(gd);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Show a dialog to confirm the new language
				if (!currentLanguage.equals(languageCode)){
					if (Dialogs.showConfirmationMessage(BundleInternationalization.getString("Confirm_title"), BundleInternationalization.getString("Confirm_Language"))) {
						path = Platform.getInstallLocation().getURL().getFile() + "configuration" + System.getProperty("file.separator") + "config.ini"; //$NON-NLS-1$
						Properties props = new Properties();
						try {
							props.load(new FileInputStream(path));
							props.setProperty("osgi.nl", languageCode);
							props.store(new FileOutputStream(path), "Edited by plug-in " + Activator.PLUGIN_ID);
							PlatformUI.getWorkbench().restart();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				}	
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
		
		Button btnCancel = new Button(comp, SWT.PUSH);
		btnCancel.setLayoutData(gd);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
		
		return composite;
	}
}
