package presentation.customComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Custom panel with rounded corners
 *
 */
public class RoundedPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4517413560676967760L;
		
	/** Stroke size. it is recommended to set it to 1 for better view */
    protected int strokeSize = 1;
    /** Color of shadow */
    protected Color shadowColor = Color.black;
    /** Sets if it drops shadow */
    protected boolean shady;
    /** Double values for Horizontal and Vertical radius of corner arcs */
    protected Dimension arcs = new Dimension(20, 20);
    /** Distance between shadow border and opaque panel border */
    protected int shadowGap = 5;
    /** The offset of shadow.  */
    protected int shadowOffset = 4;
    /** The transparency value of shadow. ( 0 - 255) */
    protected int shadowAlpha = 150;

	private BufferedImage img;
    
	public RoundedPanel() {
		super();	
		this.shady = true;
	}
	
	 public RoundedPanel(boolean enableShadow) {
		this.shady = enableShadow;
	}
	 
	public RoundedPanel(boolean enableShadow, BufferedImage image) {
			this.img = image;
			this.shady = enableShadow;
	 }

	protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        int width = getWidth();
	        int height = getHeight();
	        int shadowGap = this.shadowGap;
	        Color shadowColorA = new Color(shadowColor.getRed(), 
	        shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
	        Graphics2D graphics = (Graphics2D) g;

	        //Sets antialiasing
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);        

	        //Draws shadow borders if any.
	        if (shady) {
	            graphics.setColor(shadowColorA);
	            graphics.fillRoundRect(
	                shadowOffset,// X position
	                shadowOffset,// Y position
	                width - strokeSize - shadowOffset, // width
	                height - strokeSize - shadowOffset, // height
	                arcs.width, arcs.height);// arc Dimension
	        } else {
	            shadowGap = 1;
	        }

	        //Draws the rounded opaque panel with borders.
	        graphics.setColor(getBackground());
	        graphics.fillRoundRect(0, 0, width - shadowGap, 
			height - shadowGap, arcs.width, arcs.height);
	        graphics.setColor(getForeground());
	        graphics.setStroke(new BasicStroke(strokeSize));
	        graphics.drawRoundRect(0, 0, width - shadowGap, 
			height - shadowGap, arcs.width, arcs.height);

	        //Sets strokes to default, is better.
	        graphics.setStroke(new BasicStroke());
	        
	        if (img != null) {
	        	graphics.drawImage(img, 0,0, img.getWidth(), img.getHeight(),this);
	        }
	    }

}
