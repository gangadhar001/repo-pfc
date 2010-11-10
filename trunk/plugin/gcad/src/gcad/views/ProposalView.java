package gcad.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import gcad.domain.Proposal;
import gcad.proposal.models.ProposalManager;
import gcad.ui.treeviewer.ProposalContentProvider;
import gcad.ui.treeviewer.ProposalLabelProvider;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;


public class ProposalView extends ViewPart {
	
	protected TreeViewer treeViewer;
	protected Text text;
	
	protected Action onlyBoardGamesAction, atLeatThreeItems;
	protected Action booksBoxesGamesAction, noArticleAction;
	protected Action addBookAction, removeAction;
	protected ViewerFilter onlyBoardGamesFilter, atLeastThreeFilter;
	protected ViewerSorter booksBoxesGamesSorter, noArticleSorter;
	

	protected Proposal root;
	ProposalManager manager = ProposalManager.getManager();
	


	@Override
	public void createPartControl(Composite parent) {	
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
					| SWT.V_SCROLL);
		ProposalContentProvider a = new ProposalContentProvider();
		treeViewer.setContentProvider(a);
		treeViewer.setLabelProvider(new ProposalLabelProvider());
			// Expand the tree 
		treeViewer.setAutoExpandLevel(2);
			// Provide the input to the ContentProvider
		treeViewer.setInput(a.getElements(null));
	}

	
	
	

	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();


	}
                                                                                                                                   
}
