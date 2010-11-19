package gcad.perspective;

import gcad.wizards.DBConnectionWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PlatformUI;

public class KMPerspective implements IPerspectiveFactory {
	
	private static final String PROPOSAL_VIEW_ID = "gcad.view.issues";
		
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area identifier
        String idEditorArea = layout.getEditorArea();
        
        // Put the "Outline" View on the left 
        //layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, idEditorArea);
        
        IFolderLayout folderBottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.66f, idEditorArea);
        
        // Add standard "Problems" and "Task List" Views on the bottom
        folderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
        folderBottom.addPlaceholder(IPageLayout.ID_TASK_LIST);
        
        // TODO: poner otras vistas
        // Put the "Issues" view on the left, with the "Outline" view
        IFolderLayout folderLeft = layout.createFolder("left", IPageLayout.LEFT, 0.20f, idEditorArea);
        folderLeft.addView(PROPOSAL_VIEW_ID);
        folderLeft.addView(IPageLayout.ID_OUTLINE);
		
		// TODO: poner otras vistas
		
		
		

	}

}
