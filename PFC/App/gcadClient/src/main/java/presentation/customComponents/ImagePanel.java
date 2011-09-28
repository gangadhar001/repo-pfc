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

	public ImagePanel(BufferedImage image) {
		this.img = image;
//		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 if (img != null)
		      g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
	}

}
