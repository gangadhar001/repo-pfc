package presentation.customComponents;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import model.business.knowledge.Company;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.image.GaussianBlurFilter;

import presentation.utils.ImagesUtilities;

public class JPDetailsCompanyGlassPanel extends JXPanel  implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6048620356203327658L;
	private JPDetailsCompany detailPanel;
    private BufferedImage blurBuffer;
    private BufferedImage backBuffer;
    private float alpha = 0.0f;
	private Timer timer;
	private int value;

    public JPDetailsCompanyGlassPanel(JPDetailsCompany panel) {
        this.detailPanel = panel;
        this.detailPanel.setAlpha(0.0f);
        add(panel);
        
        // Disable key events
        addMouseListener(new MouseAdapter() { });
    }

	private void createBlur() {
	    JRootPane root = SwingUtilities.getRootPane(this);
	    blurBuffer = ImagesUtilities.createCompatibleImage(
	        getWidth(), getHeight());
	    Graphics2D g2 = blurBuffer.createGraphics();
	    root.paint(g2);
	    g2.dispose();
	
	    backBuffer = blurBuffer;
	
	    blurBuffer = ImagesUtilities.createThumbnailFast(
	        blurBuffer, getWidth() / 2);
	    blurBuffer = new GaussianBlurFilter(5).filter(blurBuffer, null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    if (isVisible() && blurBuffer != null) {
	    	Graphics2D g2 = (Graphics2D) g.create();
	
	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(backBuffer, 0, 0, null);
	
	        g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
	        g2.drawImage(blurBuffer, 0, 0, getWidth(), getHeight(), null);
	        g2.dispose();
	    }
	}

	
	public float getAlpha() {
	    return alpha;
	}
	
	public void setAlpha(float alpha) {
	    this.alpha = alpha;
	    repaint();
	}
	
	public void fadeIn(Company c) {
		// Set the company details in the dialog
		this.detailPanel.setCompanyDetails(c);
		
	    createBlur();
	
	    setVisible(true);
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            Animator animator = PropertySetter.createAnimator(
	                1000, detailPanel, "alpha", 1.0f);
	            animator.setAcceleration(0.2f);
	            animator.setDeceleration(0.3f);
	            animator.addTarget(
	                new PropertySetter(JPDetailsCompanyGlassPanel.this, "alpha", 1.0f));
	            animator.start();
	        }
	    });
	}	
	
	public void fadeOut() {			
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            Animator animator = PropertySetter.createAnimator(
	                1000, detailPanel, "alpha", 0.0f);
	            animator.setAcceleration(0.2f);
	            animator.setDeceleration(0.3f);
	            animator.addTarget(
	                new PropertySetter(JPDetailsCompanyGlassPanel.this, "alpha", 0.0f));
	            animator.start();
	        }
	    });
		
		timer = new Timer(1, this);	   
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
	    ++value;
	    if (value == 80) {
	    	setVisible(false);
	    	timer.stop();
	    	value=0;
	    }
	}
}