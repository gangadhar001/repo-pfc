package presentation;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class represents the perspective used for the knowledge management
 */
public class KMPerspective implements IPerspectiveFactory {
	
	public static final String HIERARCHICAL_KNOWLEDGE_VIEW_ID = "gcad.view.HierarchicalKnowledge";
	public static final String GRAPH_KNOWLEDGE_VIEW_ID = "gcad.view.GraphKnowledge";
	public static final String USER_INF_VIEW_ID = "gcad.view.UserInformation";
	public static final String NOTIFICATION_VIEW_ID = "gcad.view.notifications";
		
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area identifier
        String idEditorArea = layout.getEditorArea();
                
        layout.setEditorAreaVisible(false);
        
        layout.setFixed(false);        
        
        layout.addView(GRAPH_KNOWLEDGE_VIEW_ID, IPageLayout.LEFT, 0.7f, idEditorArea);
        layout.addView(HIERARCHICAL_KNOWLEDGE_VIEW_ID, IPageLayout.RIGHT, 0.3f, idEditorArea);
        
        IFolderLayout folderBottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.6f, GRAPH_KNOWLEDGE_VIEW_ID);
        folderBottom.addPlaceholder(USER_INF_VIEW_ID);
        folderBottom.addPlaceholder(NOTIFICATION_VIEW_ID);

		// TODO: poner otras vistas
	}

}
