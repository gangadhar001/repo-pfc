package gcad.views;

import gcad.domain.AbstractProposal;
import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.internationalization.BundleInternationalization;
import gcad.proposals.models.ProposalManager;
import gcad.ui.treeviewer.ProposalContentProvider;
import gcad.ui.treeviewer.ProposalLabelProvider;
import gcad.wizards.AbstractNewProposalWizard;
import gcad.wizards.NewProposalViewWizard;
import gcad.wizards.NewProposalViewWizardPage;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;


public class ProposalView extends ViewPart {
	
	protected TreeViewer treeViewer;
	private Label errorLabel;
	private ProposalManager manager;
	// TODO: se usa para poner los iconos de las acciones en una barra de menus dentro de la vista
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	private Proposal root;
	private Composite parent;
	private Proposal proposalSelected;

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		manager = ProposalManager.getManager();		
		root = new Proposal();
		// Expand the tree 
		//treeViewer.setAutoExpandLevel(2);			
		// TODO: si no hay error en el acceso a la base de datos, se muestra el arbol.
		// Si no, se muestra el mensaje de error
		makeTree();		
	}
	
	// Metodo para manejar el doble clic
	// En este caso, se añade nueva informacion a una propuesta
	private void makeActions() {
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = treeViewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				// TODO: si es una propuesta, mostrar el wizard para registrar una nueva propuesta/answer
				if (obj instanceof Proposal) {
					proposalSelected = (Proposal) obj;
					// Mostrar un dialogo para elegir si se desea añadir una nueva propuesta o una nueva respuesta
					MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "New knowledge", null,
							"Choose the type of knowledge to add to the proposal titled: " + proposalSelected.getTitle() , MessageDialog.INFORMATION, new String[] {
									"Proposal", "Answer" }, 0);
					dialog.create();
					int result = dialog.open();
					// Proposal
					if (result == 0) {
						AbstractNewProposalWizard wizard = new NewProposalViewWizard(BundleInternationalization.getString("NewProposalWizard"));
						wizard.addPages(new NewProposalViewWizardPage(BundleInternationalization.getString("NewProposalWizardPageTitle")));
						WizardDialog proposalDialog = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), wizard);
						proposalDialog.create();
						proposalDialog.open();
					}
				}
			}
		};
	}
	
	// Listener for Double Click Action
	private void hookDoubleClickAction() {
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		if (treeViewer!=null)
			treeViewer.getControl().setFocus();


	}
	
	// Al conectar a la base de datos, se muestra el arbol
	public void showContentConnected() {
		makeTree();
	}
	
	private void makeTree() {
		try {
			ArrayList<AbstractProposal> proposals = manager.getProposalsTree();
			for (AbstractProposal p: proposals)
				root.add(p);
			establishTree();
		// TODO: no se puede conectar con la base de datos
		} catch (SQLException e) {			
			errorLabel = new Label(parent, SWT.NULL);
			errorLabel.setText(e.getLocalizedMessage());
		} catch (NoProjectProposalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void establishTree() {
		if (errorLabel != null)
			errorLabel.dispose();
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(treeViewer);
		treeViewer.setContentProvider(new ProposalContentProvider());
		treeViewer.setLabelProvider(new ProposalLabelProvider());
		treeViewer.setInput(root);
		//treeViewer.refresh();
		//parent.redraw(0, 0, parent.getBounds().width, parent.getBounds().height, true);
		parent.layout();
		setFocus();
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(treeViewer.getControl(), "Proposals Tree");
		makeActions();
		hookDoubleClickAction();
	}

	public void refresh(Proposal p) {
		//treeViewer.setInput(root);
		treeViewer.refresh(p);
		//makeTree();
	}

	public Proposal getProposalSelected() {
		return proposalSelected;
	}
	
                                                                                                                                   
}
