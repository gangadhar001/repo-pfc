package presentation;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class represents the perspective used for the knowledge management
 */
public class KMPerspective implements IPerspectiveFactory {
	
	//TODO: leerlo del fichero de properties
	private static final String PROPOSAL_VIEW_ID = "gcad.view.proposals";
	private static final String PROPOSAL_GRAPH_VIEW_ID = "gcad.view.graph";
		
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area identifier
        String idEditorArea = layout.getEditorArea();
        
        // Put the "Outline" View on the left 
        layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, idEditorArea);
        
        layout.addView(PROPOSAL_GRAPH_VIEW_ID, IPageLayout.RIGHT, 0.25f, idEditorArea);
        
        IFolderLayout folderBottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.66f, idEditorArea);
        // Add standard "Problems" and "Task List" Views on the bottom
        folderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
        folderBottom.addPlaceholder(IPageLayout.ID_TASK_LIST);
        // Add "Proposals View" on the bottom, too
        folderBottom.addPlaceholder(PROPOSAL_VIEW_ID);

		// TODO: poner otras vistas
	}

}
