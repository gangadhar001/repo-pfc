package presentation.customComponents;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5313649672422697765L;
	private Image img;

	public ImagePanel() {
		super();
	}
	
	public ImagePanel(BufferedImage image) {
		super();
		this.img = image;
	}

	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 if (img != null)
		      g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
	}

	public void setImage(BufferedImage image) {
		this.img = image;		
	}

}
