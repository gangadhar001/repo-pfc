package presentation.customComponents;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.ShadowRenderer;

import presentation.JFMain;
import presentation.utils.GraphicsUtilities;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

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

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public JPDetailsCompany(JFMain frame) {
        setOpaque(false);
        this.parent = frame;

		try {
			this.setPreferredSize(new java.awt.Dimension(468, 343));
			this.setLayout(null);
			{
				lblTitle = new JLabel();
				this.add(lblTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				lblTitle.setName("lblTitle");
				lblTitle.setText("Title");
				lblTitle.setBounds(208, 51, 23, 16);
			}
			{
				panelInfo = new JPanel();
				GridBagLayout panelInfoLayout = new GridBagLayout();
				this.add(panelInfo);
				panelInfoLayout.rowWeights = new double[] {0.1};
				panelInfoLayout.rowHeights = new int[] {7};
				panelInfoLayout.columnWeights = new double[] {0.3, 0.5};
				panelInfoLayout.columnWidths = new int[] {7, 7};
				panelInfo.setLayout(panelInfoLayout);
				panelInfo.setBounds(40, 73, 468, 195);
				panelInfo.setName("panelInfo");
				{
					panelDetailsCompany = new JPanel();
					panelDetailsCompany.setLayout(null);
					panelInfo.add(panelDetailsCompany, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelDetailsCompany.setName("panelDetailsCompany");
					{
						lblCountry = new JLabel();
						panelDetailsCompany.add(lblCountry, new AnchorConstraint(718, 708, 815, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblCountry.setPreferredSize(new java.awt.Dimension(176, 17));
						lblCountry.setName("lblCountry");
						lblCountry.setText("Country");
						lblCountry.setBounds(21, 140, 172, 18);
					}
					{
						lblZip = new JLabel();
						panelDetailsCompany.add(lblZip, new AnchorConstraint(593, 694, 684, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblZip.setPreferredSize(new java.awt.Dimension(173, 16));
						lblZip.setName("lblZip");
						lblZip.setText("ZIP");
						lblZip.setBounds(21, 115, 168, 18);
					}
					{
						lblAddress = new JLabel();
						panelDetailsCompany.add(lblAddress, new AnchorConstraint(474, 672, 559, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblAddress.setPreferredSize(new java.awt.Dimension(167, 15));
						lblAddress.setName("lblAddress");
						lblAddress.setText("Address");
						lblAddress.setBounds(21, 92, 163, 17);
					}
					{
						lblCif = new JLabel();
						panelDetailsCompany.add(lblCif, new AnchorConstraint(281, 667, 372, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblCif.setPreferredSize(new java.awt.Dimension(165, 16));
						lblCif.setName("lblCif");
						lblCif.setText("CIF");
						lblCif.setBounds(21, 54, 161, 18);
					}
					{
						lblNameCompany = new JLabel();
						panelDetailsCompany.add(lblNameCompany, new AnchorConstraint(150, 663, 247, 46, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
						lblNameCompany.setPreferredSize(new java.awt.Dimension(164, 17));
						lblNameCompany.setName("lblNameCompany");
						lblNameCompany.setText("Company");
						lblNameCompany.setBounds(21, 29, 160, 19);
					}
				}
				{
					panelImageCompany = new JPanel();
					panelInfo.add(panelImageCompany, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					panelImageCompany.setName("panelImageCompany");
				}
			}
			{
				btnOK = new JButton();
				this.add(btnOK, new GridBagConstraints(-1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 330, 0, 10), 0, 0));
				btnOK.setName("btnOK");
				btnOK.setPreferredSize(new java.awt.Dimension(40, 23));
				btnOK.setBounds(285, 270, 128, 23);
				btnOK.setText("OK");
				btnOK.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						parent.fadeOut();
						
					}
				});
			}
			
			panelInfo.setOpaque(false);
			panelDetailsCompany.setOpaque(false);
			panelImageCompany.setOpaque(false);
			panelImageCompany.setLayout(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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

	    g2.setColor(new Color(0, 0, 0, 100));
	    g2.fillRoundRect(x, y, w, h, arc, arc);

	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.WHITE);
	    g2.drawRoundRect(x, y, w, h, arc, arc); 

	    g2.dispose();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
	    super.setBounds(x, y, width, height);

	    int w = getWidth() - 68;
	    int h = getHeight() - 68;
	    int arc = 30;
	    int shadowSize = 20;
	    
	    shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
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
}
