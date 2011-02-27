package model.tableviewer;

import gcad.Activator;
import model.business.knowledge.Knowledge;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

/**
 * This class represents a label provider for the table. It allows to get the text or set 
 * an image for a cell
 */
public class UserInfCompanyLabelProvider extends CellLabelProvider {
	
	public static final int BUTTON_WIDTH = 27;
	public static final int BUTTON_HEIGHT = 25;
	private Image image = Activator.getImageDescriptor("resources/images/lupa.png").createImage();
	private TableViewer viewer;
	
	public UserInfCompanyLabelProvider (TableViewer viewer) {
		this.viewer = viewer;
	}

	public String getText(Object element) {
		Knowledge r = (Knowledge) element;				
		return r.getUser().getCompany().getName() + ", " + r.getUser().getCompany().getAddress().getCountry();
	}
		
	/**
	 * This method embed a button in a table cell
	 */
	public Image getImage(Object element) {
		Shell shell = new Shell(viewer.getControl().getShell(),SWT.NO_TRIM);
	    Button button = new Button(shell,SWT.PUSH);						
	    button.setImage(image);
	    button.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT); 
	    
	    Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	    button.setSize(bsize);
	    
	    shell.setSize(bsize);
	    shell.open();
	    
	    GC gc = new GC(button);
	    Image i = new Image(viewer.getControl().getShell().getDisplay(), bsize.x,  bsize.y);				    
	    gc.copyArea(i, 0, 0);
	    gc.dispose();
	    
		shell.close();
	    
	    return i;
	}

	@Override
	public void update(ViewerCell cell) {	
		Object element = cell.getElement();
		cell.setText(getText(element));
		Image image = getImage(element);
		cell.setImage(image);
	}
}
