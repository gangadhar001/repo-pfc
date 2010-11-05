package gcad.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class KMPerspective implements IPerspectiveFactory {

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
		
		// TODO: poner otras vistas
		

	}

}
