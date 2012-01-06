package presentation.customComponents;

import internationalization.ApplicationInternationalization;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.business.control.GeoCoder;
import model.business.knowledge.Company;
import model.business.knowledge.Coordinates;

import org.japura.gui.WrapLabel;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.ShadowRenderer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdom.JDOMException;

import presentation.JFMain;
import resources.ImagesUtilities;

import com.cloudgarden.layout.AnchorConstraint;

import exceptions.NonExistentAddressException;
import exceptions.WSResponseException;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/

/**
 * This class represents a custom panel to show the details of a company
 *
 */
public class JPDetailsCompany extends JXPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1590226883314607393L;
	private BufferedImage shadow = null;
	private JFMain parent;
	private JLabel lblTitle;
	private JButton btnOK;
	private JLabel lblCountry;
	private JLabel lblZip;
	private JLabel lblAddress;
	private JLabel lblCif;
	private JLabel lblNameCompany;
	private JPanel panelImageCompany;
	private JPanel panelDetailsCompany;
	private JPanel panelInfo;
	private JXMapKit jXMapKit;
	private Company company;
	private GeoPosition position;	
		
	public JPDetailsCompany(JFMain frame) {
        setOpaque(false);
        this.parent = frame;
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        
		try {
			this.setPreferredSize(new java.awt.Dimension(850, 388));
			this.setLayout(null);
			{
				lblTitle = new JLabel();
				this.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				lblTitle.setName("lblTitle");
				lblTitle.setText(ApplicationInternationalization.getString("companyTitle"));
				lblTitle.setBounds(85, 47, 282, 40);
				Font newFont = new Font("Times New Roman", Font.BOLD, 20);  
				lblTitle.setFont(newFont);  
				
				
			}
			{
				panelInfo = new JPanel();
				this.add(panelInfo);
				panelInfo.setLayout(null);
				panelInfo.setBounds(40, 73, 468, 195);
				panelInfo.setName("panelInfo");
				{
					panelDetailsCompany = new JPanel();
					panelDetailsCompany.setLayout(null);
					panelInfo.add(panelDetailsCompany, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelDetailsCompany.setName("panelDetailsCompany");
					{
						lblCountry = new JLabel();
						panelDetailsCompany.add(lblCountry, new AnchorConstraint(718, 708, 815, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblCountry.setName("lblCountry");
						lblCountry.setText(ApplicationInternationalization.getString("companyCountryPanel"));
						lblCountry.setBounds(21, 132, 269, 17);
						lblCountry.setFont(font); 
					}
					{
						lblZip = new JLabel();
						panelDetailsCompany.add(lblZip, new AnchorConstraint(593, 694, 684, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblZip.setName("lblZip");
						lblZip.setText(ApplicationInternationalization.getString("companyZip"));
						lblZip.setBounds(21, 108, 268, 18);
						lblZip.setFont(font); 
					}
					{
						lblAddress = new JLabel();
						panelDetailsCompany.add(lblAddress, new AnchorConstraint(474, 672, 559, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblAddress.setName("lblAddress");
						lblAddress.setText(ApplicationInternationalization.getString("companyAddress"));
						lblAddress.setBounds(21, 84, 268, 26);
						lblAddress.setFont(font); 
					}
					{
						lblCif = new JLabel();
						panelDetailsCompany.add(lblCif, new AnchorConstraint(281, 667, 372, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblCif.setName("lblCif");
						lblCif.setText(ApplicationInternationalization.getString("companyCif"));
						lblCif.setBounds(21, 48, 274, 20);
						lblCif.setFont(font); 
					}
					{
						lblNameCompany = new WrapLabel();
						panelDetailsCompany.add(lblNameCompany, new AnchorConstraint(150, 663, 247, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblNameCompany.setName("lblNameCompany");
						lblNameCompany.setText(ApplicationInternationalization.getString("companyName"));
						lblNameCompany.setBounds(21, 29, 274, 19);
						lblNameCompany.setFont(font); 
					}
				}
				{
					panelImageCompany = new JPanel();
					panelImageCompany.setLayout(null);
					panelInfo.add(panelImageCompany, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelImageCompany.setName("panelImageCompany");
					
					// Show the map
					jXMapKit = new JXMapKit();
					jXMapKit.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
			        jXMapKit.setDataProviderCreditShown(true);
			        jXMapKit.setAutoscrolls(true);
			        jXMapKit.setZoomButtonsVisible(false);
				       
			        panelImageCompany.add(jXMapKit, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			        jXMapKit.setBounds(0, 0, 385, 231);
				}
			}
			{
				btnOK = new JButton();
				this.add(btnOK, new GridBagConstraints(-1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 330, 0, 10), 0, 0));
				btnOK.setName("btnOK");
				btnOK.setBounds(442, 292, 66, 23);
				btnOK.setText(ApplicationInternationalization.getString("btnOK"));
				btnOK.setDoubleBuffered(true);
				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						parent.fadeOut();
						btnOK.setEnabled(false);
					}
				});
			}
			
			panelInfo.setOpaque(false);
			panelInfo.setBounds(12, 44, 815, 268);
			panelDetailsCompany.setOpaque(false);
			panelDetailsCompany.setBounds(55, 34, 307, 233);
			panelImageCompany.setOpaque(false);
			panelImageCompany.setBounds(368, 18, 385, 235);
			btnOK.setBounds(702, 324, 66, 23);

			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
		}
	}
	
	// Custom paint method used to paint a rounded border and change background color of the panel
	@Override
	public void paintComponent(Graphics g) {
	    int x = 34;
	    int y = 34;
	    int w = getWidth() - 68;
	    int h = getHeight() - 68;
	    int arc = 30;  
	    
	    super.paintComponent(g);
	        
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    // Color for background
	    g2.setColor(new Color(160, 208, 222, 140));
	    g2.fillRoundRect(x, y, w, h, arc, arc);

	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.DARK_GRAY);
	    g2.drawRoundRect(x, y, w, h, arc, arc); 

	    g2.dispose();
	}

	// Method used to paint shadow around the panel 
	@Override
	public void setBounds(int x, int y, int width, int height) {
	    super.setBounds(x, y, width, height);

	    int w = getWidth() - 68;
	    int h = getHeight() - 68;
	    int arc = 30;
	    int shadowSize = 20;
	    
	    shadow = ImagesUtilities.createCompatibleTranslucentImage(w, h);
	    Graphics2D g2 = shadow.createGraphics();
	    g2.setColor(Color.WHITE);
	    g2.fillRoundRect(0, 0, w, h, arc, arc);
	    g2.dispose();
		 
	    ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f, Color.BLACK);
	    shadow = renderer.createShadow(shadow);
	    
	    g2 = shadow.createGraphics();
	    // The color does not matter, red is used for debugging
	    g2.setColor(Color.RED);
	    g2.setComposite(AlphaComposite.Clear);
	    g2.fillRoundRect(shadowSize, shadowSize, w, h, arc, arc);
	    g2.dispose();

		if (shadow != null) {
		    int xOffset = (shadow.getWidth()  - w) / 2;
		    int yOffset = (shadow.getHeight() - h) / 2;
		    g2.drawImage(shadow, x - xOffset, y - yOffset, null);
		}
	}

	public void setCompanyDetails(Company c) {
		btnOK.setEnabled(true);		
		this.company = c;
		setDetailsCompany();
		setPosition();
	}

	private void setDetailsCompany() {
		lblTitle.setText(company.getName() + ", " + company.getAddress().getCountry());
		lblCif.setText(ApplicationInternationalization.getString("companyCif") + ": " + company.getCif());
		lblNameCompany.setText(ApplicationInternationalization.getString("companyName") + ": " + company.getName());
		lblCountry.setText(ApplicationInternationalization.getString("companyCountry") + ": " + company.getAddress().getCountry());
		lblZip.setText(ApplicationInternationalization.getString("companyZip") + ": " + company.getAddress().getZip());
		lblAddress.setText(ApplicationInternationalization.getString("companyAddress") + ": " + company.getAddress().getStreet() + ", " + company.getAddress().getCity());		
	}

	@SuppressWarnings("rawtypes")
	private void setPosition() {
		Coordinates coor;
		try {
			coor = GeoCoder.getGeoCoordinates(company.getAddress());
		
        double latitude = Double.parseDouble(coor.getLatitude());
        double longitude = Double.parseDouble(coor.getLongitude());			        
        position = new GeoPosition(latitude, longitude);
        jXMapKit.setAddressLocation(position);    
        
        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        waypoints.add(new Waypoint(latitude, longitude));			        

        WaypointPainter<?> painter = new WaypointPainter();
        painter.setWaypoints(waypoints);
//	        painter.setRenderer(new WaypointRenderer() {
//	            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
//	                g.setColor(Color.RED);
//	                g.drawLine(-5,-5,+5,+5);
//	                g.drawLine(-5,+5,+5,-5);
//	                return true;
//	            }
//	        });
        
        jXMapKit.getMainMap().setOverlayPainter(painter);
        jXMapKit.getMainMap().setZoom(2);
        jXMapKit.setAddressLocationShown(true);
        jXMapKit.setCenterPosition(position);
        jXMapKit.setDataProviderCreditShown(true);
		} catch (NonExistentAddressException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (WSResponseException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (JDOMException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), ApplicationInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
