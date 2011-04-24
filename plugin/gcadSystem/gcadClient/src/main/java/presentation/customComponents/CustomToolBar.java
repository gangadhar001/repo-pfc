package presentation.customComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JToolBar;
import javax.swing.border.Border;

import org.jdesktop.swingx.border.DropShadowBorder;

public class CustomToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6298963646291672373L;

	public CustomToolBar() {
		Border border = new DropShadowBorder(Color.BLACK, 5, 0.5f, 12, false, false, true, false);
		setBorder(border);		
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int bottom = getHeight() - getInsets().bottom;
		GradientPaint gp = new GradientPaint(0, 0, new Color(236, 236, 216), 0, bottom, Color.WHITE, true);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, getWidth(), bottom);
		g2d.setColor(new Color(127, 157, 185));
		g2d.drawLine(0, bottom-1, getWidth(), bottom-1);
		g2d.dispose();
	}
}
