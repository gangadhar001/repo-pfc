package presentation.customComponents;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jdesktop.swingx.graphics.ShadowRenderer;



import org.jdesktop.swingx.JXPanel;

import presentation.utils.GraphicsUtilities;

public class DetailPanel extends JXPanel {
	
	private BufferedImage shadow = null;
	private NewJFrame parent;
	private JLabel lbl;
	private JLabel lbl1;

    public DetailPanel(NewJFrame newJFrame) {
        setOpaque(false);
       this.parent = newJFrame;
        
         lbl1 = new JLabel("HOLA");
//         lbl2 = new JLabel("HOLA");
        JButton bt = new JButton("OK");
        bt.setSize(20,20);
//        
////        this.setBackground(Color.CYAN);
        bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.fadeOut();
				
			}
		});
     
        this.add(lbl1);
//        this.add(lbl2);        
        this.add(bt);
    }

    

@Override
public void paintComponent(Graphics g) {
    int x = 34;
    int y = 34;
    int w = getWidth() - 68;
    int h = getHeight() - 68;
    int arc = 30;  
        
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

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