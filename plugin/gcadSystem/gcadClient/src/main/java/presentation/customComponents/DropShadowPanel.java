/*
 * $Id: DropShadowPanel.java,v 1.2 2007/03/12 22:58:41 rbair Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package presentation.customComponents;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import org.jdesktop.swingx.graphics.ShadowRenderer;
 
/**
 * <p><code>DropShadowPanel</code> is a generic lightweight container casting
 * the shadow of its children. The shadow itself is generated by a
 * {@link org.jdesktop.swingx.util.ShadowFactory} you can provide or modify.</p>
 * <p>Besides the properties provided by the <code>ShadowFactory</code>, the
 * drop shadow panel gives you control over the angle and the distance of the
 * shadow. Any change of these properties, be the properties of the
 * <code>ShadowFactory</code> or the angle or the distance, will triger an update
 * of the panel to repaint it.</p>
 * <p>The default shadow factory provided by this panel is suited for good
 * looking results with regular Swing components. You can also combine this panel
 * with a {@link org.jdesktop.swingx.StackLayout} and a
 * {@link org.jdesktop.swingx.CheckboardPanel} to create interesting effects.</p>
 * <p><b>IMPORTANT NOTICE:</b> this component has not yet been fully tested.</p>
 * 
 * @author Romain Guy <romain.guy@mac.com>
 */

// REFERENCE: http://www.koders.com/java/fidD5C92639A19E0C4C749E1B18928051F0DBAB4FA1.aspx?s=mdef%3acompute
public class DropShadowPanel extends JPanel implements PropertyChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3275883383164077180L;
	// angle and distance of the shadow from the original subjects
    private float angle = 45.0f;
    private int distance = 2;

    // cached values for fast painting
    private int distance_x = 0;
    private int distance_y = 0;
    
    // when shadow member is equaled to null, the factory is asked to
    // re-generated it
    private BufferedImage shadow = null;
    private ShadowRenderer factory = null;
    
    /**
     * <p>Creates a new checkboard panel with a flow layout. The drop shadow has
     * the following default properties:
     * <ul>
     *   <li><i>angle</i>: 45 deg</li>
     *   <li><i>distance</i>: 2 pixels</li>
     *   <li><i>size</i>: 5 pixels (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>opacity</i>: 50% (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>color</i>: Black (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>rendering quality</i>: VALUE_BLUR_QUALITY_FAST (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     * </ul>
     * This configuration provides good looking results for use with Swing
     * components. To add a drop shadow to a picture, the distance should be
     * increased.</p>
     */
    public DropShadowPanel() {
        this(new ShadowRenderer(), new FlowLayout());
    }

    /**
     * <p>Creates a new checkboard panel the specified layout. The drop shadow 
     * has the following default properties:
     * <ul>
     *   <li><i>angle</i>: 45deg</li>
     *   <li><i>distance</i>: 2 pixels</li>
     *   <li><i>size</i>: 5 pixels (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>opacity</i>: 50% (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>color</i>: Black (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     *   <li><i>rendering quality</i>: VALUE_BLUR_QUALITY_FAST (see
     *   {@link org.jdesktop.swingx.util.ShadowFactory}</li>
     * </ul>
     * This configuration provides good looking results for use with Swing
     * components. To add a drop shadow to a picture, the distance should be
     * increased.</p>
     */
    public DropShadowPanel(final LayoutManager layout) {
        this(new ShadowRenderer(), layout);
    }
    
    /**
     * <p>Creates a new checkboard panel the specified layout and shadow factory.
     * The drop shadow has the following default properties:
     * <ul>
     *   <li><i>angle</i>: 45deg</li>
     *   <li><i>distance</i>: 2 pixels</li>
     * </ul>
     * This configuration provides good looking results for use with Swing
     * components. To add a drop shadow to a picture, the distance should be
     * increased.</p>
     */
    public DropShadowPanel(final ShadowRenderer factory,
                           final LayoutManager layout) {
        super(layout);
        computeShadowPosition();
        setShadowRenderer(factory);
    }

    /**
     * <p>Gets the shadow factory used to generate the shadow.</p>
     * 
     * @return this panel's shadow factory
     */
    public ShadowRenderer getShadowRenderer() {
        return factory;
    }
    
    /**
     * <p>Sets the shadow factory used by this panel to generate the shadows.</p>
     * <p>If the specified factory is null, the default shadow factory is used
     * instead (see
     * {@link org.jdesktop.swingx.util.ShadowFactory#ShadowFactory()}.</p>
     * 
     * @param factory the factory used to generate the shadows for this
     * panel's content
     */
    public void setShadowRenderer(ShadowRenderer factory) {
        if (factory == null) {
            factory = new ShadowRenderer();
        }
        
        if (factory != this.factory){
            if (this.factory != null) {
                this.factory.removePropertyChangeListener(this);
            }

            this.factory = factory;
            this.factory.addPropertyChangeListener(this);
            
            shadow = null;
            repaint();
        }
    }

    /**
     * <p>Gets the angle, in degrees, of the shadow relatively to the panel's
     * content.</p>
     * 
     * @return this panel's shadow angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * <p>Sets the angle, in degrees, at which the drop shadow is drawn
     * relatively to this panel's content.</p>
     * <p>The angle should be comprised between 0 and 360 but negative values
     * and values greater than 360 are accepted.</p>
     * 
     * @param angle the angle of the shadow relatively to the panel's content
     */
    public void setAngle(final float angle) {
        this.angle = angle;
        computeShadowPosition();
        repaint();
    }

    /**
     * <p>Gets the distance, in pixels, of the shadow relatively to the panel's
     * content.</p>
     * 
     * @return this panel's shadow distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * <p>Sets the distance, in pixels, at which the drop shadow is drawn
     * relatively to this panel's content.</p>
     * <p>The distance can be either positive or negative.</p>
     * 
     * @param distance the distance of the shadow relatively to the panel's content
     */
    public void setDistance(final int distance) {
        this.distance = distance;
        computeShadowPosition();
        repaint();
    }

    // computes the shadow position, in pixels, according to the current
    // angle and distance properties
    private void computeShadowPosition() {
        double angleRadians = Math.toRadians(angle);
        distance_x = (int) (Math.cos(angleRadians) * distance);
        distance_y = (int) (Math.sin(angleRadians) * distance);
    }

    /**
     * <p>Due to the nature of this panel, it is assumed it is non opaque.
     * This allows to handle the drop shadow drawing properly.</p>
     * 
     * @return always true
     */
    @Override
    public boolean isOpaque() {
        return false;
    }

    /** 
     * <p>When this panel is asked to lay out its children components,
     * the shadow is invalidated for redrawing.</p>
     */
    @Override
    public void doLayout() {
        super.doLayout();
        shadow = null;
    }
    
    /**
     * <p>When the drop shadow is invalidated (see {@link #doLayout}), the
     * content of this panel is drawn in an offscreen picture and the shadow
     * factory is asked to generate the shadow. Otherwise, the shadow is drawn,
     * then the panel is painted as usual.</p>
     * 
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        if (shadow == null) {
            BufferedImage buffer = new BufferedImage(getWidth(),
                                                     getHeight(),
                                                     BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffer.createGraphics();
            super.paint(g2);
            factory.setSize(5);
            factory.setOpacity(0.3f);
            shadow = factory.createShadow(buffer);
            g2.dispose();
            g.drawImage(shadow, distance_x, distance_y, null);
            g.drawImage(buffer, 0, 0, null);
        } else if (isVisible()) {
            g.drawImage(shadow, distance_x, distance_y, null);
            super.paint(g);
        }
    }

    /**
     * <p>Whenever a property of the shadow factory is changed, the shadow
     * is invalidated and the panel is updated for repainting.</p>
     * 
     * @param evt the property change event generating the update
     */
    public void propertyChange(PropertyChangeEvent evt) {
        shadow = null;
        repaint();
    }
}
