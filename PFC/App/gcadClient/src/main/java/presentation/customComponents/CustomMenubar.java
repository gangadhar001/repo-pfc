package presentation.customComponents;

import internationalization.ApplicationInternationalization;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import model.business.knowledge.Groups;
import model.business.knowledge.Operation;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import presentation.JDAbout;
import presentation.JDChooseProject;
import presentation.JDLanguages;
import presentation.JFMain;
import resources.ImagesUtilities;
import resources.MenuMnemonicsRuntime;
import bussiness.control.OperationsUtilities;

/**
 * Custom class that represents a custom menu bar. It manages the menus, the custom toolbar and its events
 * 
 */
public class CustomMenubar extends JMenuBar {

	// REFERENCE: http://blogs.oracle.com/geertjan/entry/customizable_corporate_menu_bar_for
	// REFERENCE: http://www.miglayout.com/

	private static final long serialVersionUID = 7960099104264885051L;

	private static final int MENU_HEIGHT = 60;

	private JPanel viewsPanel;
	private JMenu menuTools;
	private JFMain mainFrame;

	protected Object viewButtonName;

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}
	
	public CustomMenubar() {
		super();
	}

	public CustomMenubar(JFMain mainFrame, List<Operation> operations) throws MalformedURLException, IOException {
		super();
		
		this.mainFrame = mainFrame;
		
		// Set layout to menu bar
		setLayout(new MigLayout("align left"));
		setPreferredSize(new Dimension(1024, MENU_HEIGHT));
		 
		// Add common menus
		JMenu menuFile = new JMenu(ApplicationInternationalization.getString("menuFile"));
		menuFile.setName("menuFile");
		
		JMenuItem menuFileCloseSession = new JMenuItem();
		menuFileCloseSession.setName("menuFileCloseSession");
		menuFileCloseSession.setAction(getAppActionMap().get("CloseSession"));
		menuFileCloseSession.setText(ApplicationInternationalization.getString("menuFileCloseSession"));
		menuFileCloseSession.setIcon(ImagesUtilities.loadIcon("menus/session.png"));
		menuFile.add(menuFileCloseSession);
		menuFile.addSeparator();
		
		JMenuItem menuFileExit = new JMenuItem();
		menuFile.add(menuFileExit);
		menuFileExit.setName("menuFileExit");
		menuFileExit.setAction(getAppActionMap().get("Exit"));
		menuFileExit.setText(ApplicationInternationalization.getString("menuItemExit"));
		menuFileExit.setIcon(ImagesUtilities.loadIcon("menus/exit.png"));

		menuTools = new JMenu(ApplicationInternationalization.getString("menuTools"));
		menuTools.setName("menuTools");

		// Add menu items to "Tools" menu. Each menu item is a group of
		// operations
		List<String> groups = OperationsUtilities.getAllGroupsMenu(operations);
		for (String group : groups) {
			createToolMenuItem(group);
		}

		// Add "Options" menu
		JMenu menuOptions = new JMenu();
		menuOptions.setName("menuOptions");
		menuOptions.setText(ApplicationInternationalization.getString("menuOptions"));
		
		// Add menu item for switch active project
		JMenuItem menuItemSwitch = new JMenuItem();
		menuOptions.add(menuItemSwitch);
		menuItemSwitch.setName("menuItemSwitch");
		menuItemSwitch.setAction(getAppActionMap().get("SwitchProject"));
		menuItemSwitch.setText(ApplicationInternationalization.getString("menuItemSwitch"));
		menuItemSwitch.setIcon(ImagesUtilities.loadIcon("menus/changeProject.png"));
		menuOptions.addSeparator();
		
		JMenuItem menuChange = new JMenuItem();
		menuOptions.add(menuChange);
		menuChange.setName("menuChangeLanguage");
		menuChange.setAction(getAppActionMap().get("ChangeLanguage"));
		menuChange.setText(ApplicationInternationalization.getString("menuItemLanguage"));
		menuChange.setIcon(ImagesUtilities.loadIcon("menus/languages.png"));
		
		JMenu menuHelp = new JMenu(ApplicationInternationalization.getString("menuHelp"));
		JMenuItem menuAbout = new JMenuItem(ApplicationInternationalization.getString("menuItemAbout"));
		menuAbout.setAction(getAppActionMap().get("About"));
		menuAbout.setIcon(ImagesUtilities.loadIcon("menus/about.png"));
		menuHelp.add(menuAbout);		
		
		add(menuFile);
		add(menuTools);
		add(menuOptions);
		add(menuHelp);	

		// Set mnemonics
		MenuMnemonicsRuntime.setMnemonics(this);
		
		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		separator.setSize(new Dimension(separator.getWidth(), MENU_HEIGHT - 5));
		separator.setPreferredSize(new Dimension(separator.getWidth(), MENU_HEIGHT - 5));
		add(separator);

		JLabel label = new JLabel();
		label.setSize(new Dimension(10, MENU_HEIGHT - 5));
		label.setPreferredSize(new Dimension(10, MENU_HEIGHT - 5));
		label.setFocusable(false);
		add(label);

		// Add the panel used to contain the view icons
		viewsPanel = new JPanel();
		GridLayout gl = new GridLayout(1, groups.size());
		gl.setHgap(20);
		viewsPanel.setLayout(gl);
		// Create panel with view icons		
		createViews(operations);	
		
		viewsPanel.setOpaque(false);
		add(viewsPanel, "span, align left");
		
	}
	
	// Create all submenus for one menu (one group of operations)
	private void createToolMenuItem(String groupName) {
        if (!groupName.equals(Groups.Notifications.name())) {
	        JMenuItem item = new JMenuItem();
            item.setName("Manage_"+groupName);
            item.setAction(getAppActionMap().get(item.getName()));
            item.setText(ApplicationInternationalization.getString("manage"+groupName));
            ImageIcon icon = null;
			try {
				icon = ImagesUtilities.loadIcon("menus/" + item.getName() + ".png");
			} catch (Exception e) { }
            if (icon != null)
            	item.setIcon(icon);
            menuTools.add(item);
        }
    }

	// Method used to create the different button with icons on toolbar, depends on the
	// allowed operations
	public void createViews(List<Operation> operations) throws MalformedURLException, IOException {
		List<String> shownGroups = new ArrayList<String>();
		for (Operation op : operations) {
			if (op.isShowGroupInView() && !shownGroups.contains(op.getGroup())) {
				JButton button = new JButton();
				button.setName("View_" + op.getGroup());
				BufferedImage icon = ImagesUtilities.loadCompatibleImage("Toolbars/" + op.getGroup()+".png");
			    button.setAction(getAppActionMap().get(button.getName()));
			    button.setIcon(new ImageIcon(icon));
				button.setSize(new Dimension(80, 100));	
				button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
				button.setBorderPainted(false);
		    	button.setFocusPainted(false);
			    button.setContentAreaFilled(false);
			    button.setText(ApplicationInternationalization.getString("ButtonView_" + op.getGroup()));
			    button.setVerticalTextPosition(SwingConstants.BOTTOM);
			    button.setHorizontalTextPosition(SwingConstants.CENTER);
			    button.setToolTipText(ApplicationInternationalization.getString("TooltipView_" + op.getGroup()));
			    button.setBorderPainted(false);
			    // Save button icon
				ImagesUtilities.addImageButton(button.getName(), icon);
			    button.addMouseListener(new MouseListener() {	
					@Override
					public void mouseReleased(MouseEvent e) {						
					}
					
					@Override
					public void mousePressed(MouseEvent e)
					{
					}
					
					@Override
			        public void mouseEntered(MouseEvent e) {
			            JButton button = (JButton) e.getComponent();
			            button.setBorderPainted(true);
			            setCursor(new Cursor(Cursor.HAND_CURSOR));
						ImagesUtilities.increaseImageBrightness(button);
						button.setBackground(Color.LIGHT_GRAY);
			        }

			        @Override
			        public void mouseExited(MouseEvent e) {
			            JButton button = (JButton) e.getComponent();
			            if (!button.getName().equals(viewButtonName)) {
			            	button.setBorderPainted(false);
			            	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							ImagesUtilities.decreaseImageBrightness(button);
			            }
			        }

					
					@Override
					public void mouseClicked(MouseEvent e) {
						// Deactivate other buttons
						for(Component component: viewsPanel.getComponents()) {
							if (component instanceof JButton) {
								JButton button = (JButton) component;
								button.setBorderPainted(false);
								ImagesUtilities.decreaseImageBrightness(button);
							}
						}
						
						// Activate current button
						JButton button = (JButton) e.getComponent();
			            button.setBorderPainted(true);
			            ImagesUtilities.decreaseImageBrightness(button);
						
			            viewButtonName = button.getName();
					}
				});
			   
				viewsPanel.add(button);
				shownGroups.add(op.getGroup());
			}				
		}		
	}
	
	/*** Actions used to manage actions for menu items ***/
	@Action
	public void Exit() {
		if (JOptionPane.showConfirmDialog(mainFrame.getMainFrame(), ApplicationInternationalization.getString("Dialog_CloseFrame_Message"),
				ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			mainFrame.quit();
	}

	@Action
	public void CloseSession() {		
		// TODO: cerrar sesion
//		if (JOptionPane.showConfirmDialog(getMainFrame(), ApplicationInternationalization.getString("Dialog_CloseSession_Message"),
//					ApplicationInternationalization.getString("Dialog_CloseFrame_Message"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
//					JOptionPane.YES_OPTION)
//			closeSessionConfirm();

		// JDConfigSimil j = new JDConfigSimil(null);
		// j.setVisible(true);/	
	}

	@Action
	public void Manage_Knowledge() {
		mainFrame.manageKnowledgeFromMenu();
	}

	@Action
	public void Manage_Project() {
		mainFrame.manageProjectFromMenu();
	}

	@Action
	public void About() {
		JDAbout about = new JDAbout(mainFrame.getMainFrame());
		about.setLocationRelativeTo(mainFrame.getMainFrame());
		about.setModal(true);
		about.setVisible(true);
	}

	@Action
	public void SwitchProject() {
		JDChooseProject jdc = new JDChooseProject(mainFrame.getMainFrame());
		jdc.setModal(true);
		jdc.setLocationRelativeTo(mainFrame.getMainFrame());
		jdc.setVisible(true);
	}
	
	@Action
	public void ChangeLanguage() {
		JDLanguages jdl = new JDLanguages(mainFrame);
		jdl.setModal(true);
		jdl.setLocationRelativeTo(mainFrame.getMainFrame());
		jdl.setVisible(true);
	}

	/*** Actions used to manage actions for menu items ***/
	@Action
	// Show Knowledge view
	public void View_Knowledge() {
		mainFrame.showKnowledgeView();		
	}
	
	@Action
	// Show Notifications view
	public void View_Notifications() {
		mainFrame.showNotificationsView();		
	}
	
	@Action
	// Show Notifications view
	public void View_Statistics() {
		mainFrame.showStatisticsView();		
	}
    
	@Action
	// Show Notifications view
	public void View_PDFGeneration() {
		mainFrame.showPDFView();		
	}

}
