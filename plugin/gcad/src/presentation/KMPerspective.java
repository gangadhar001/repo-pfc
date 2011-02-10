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
	private static final String USER_INF_VIEW_ID = "gcad.view.UserInformation";
		
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area identifier
        String idEditorArea = layout.getEditorArea();
                
        layout.setEditorAreaVisible(false);
        
        // Put the "Outline" View on the left 
        //layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, idEditorArea);
        
        layout.addView(PROPOSAL_GRAPH_VIEW_ID, IPageLayout.LEFT, 0.65f, idEditorArea);
        layout.addView(PROPOSAL_VIEW_ID, IPageLayout.RIGHT, 0.35f, idEditorArea);
        
        IFolderLayout folderBottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.6f, PROPOSAL_GRAPH_VIEW_ID);
        // Add standard "Problems" and "Task List" Views on the bottom
        //folderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
        folderBottom.addPlaceholder(IPageLayout.ID_TASK_LIST);
        folderBottom.addPlaceholder(USER_INF_VIEW_ID);

		// TODO: poner otras vistas
	}

}
