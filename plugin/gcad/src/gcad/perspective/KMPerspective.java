package gcad.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * TODO: Esta clase representa la perspectiva utilizada para la gestion del conocimiento
 */
public class KMPerspective implements IPerspectiveFactory {
	
	private static final String PROPOSAL_VIEW_ID = "gcad.category.view.proposal";
		
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area identifier
        String idEditorArea = layout.getEditorArea();
        
        // Put the "Outline" View on the left 
        layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, idEditorArea);
        
        IFolderLayout folderBottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.66f, idEditorArea);
        
        // Add standard "Problems" and "Task List" Views on the bottom
        folderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
        folderBottom.addPlaceholder(IPageLayout.ID_TASK_LIST);
        // Add "Proposals View" on the bottom, too
        folderBottom.addPlaceholder(PROPOSAL_VIEW_ID);

		// TODO: poner otras vistas
		
		
		

	}

}
