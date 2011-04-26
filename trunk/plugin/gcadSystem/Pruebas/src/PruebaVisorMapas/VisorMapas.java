package PruebaVisorMapas;

import geoposition.PruebaMaps;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;
import org.jdesktop.swingx.JXMapViewer;


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
public class VisorMapas extends javax.swing.JFrame {

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VisorMapas inst = new VisorMapas();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	private GeoPosition position;
	
	public VisorMapas() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			GridBagLayout thisLayout = new GridBagLayout();
			getContentPane().setLayout(thisLayout);
			thisLayout.rowWeights = new double[] {0.1};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.1};
			thisLayout.columnWidths = new int[] {7};

			 jXMapKit1 = new org.jdesktop.swingx.JXMapKit();
		        
			 mainPanel = new JPanel();
			 GridBagLayout mainPanelLayout = new GridBagLayout();
			 mainPanel.setLayout(mainPanelLayout);
		        mainPanel.setName("mainPanel"); // NOI18N

		        jXMapKit1.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
		        jXMapKit1.setDataProviderCreditShown(true);
		        jXMapKit1.setAutoscrolls(true);
		        jXMapKit1.setZoomButtonsVisible(false);
		        
		        
		       
		      

		        // put your action code here
		        //jXMapKit1.setCenterPosition(new GeoPosition(41.881944,-87.627778));
		        
		        ArrayList<String> coordinates = PruebaMaps.getCoordinates();
		        double latitude = Double.parseDouble(coordinates.get(0));
		        double longitude = Double.parseDouble(coordinates.get(1));
		        
		        position = new GeoPosition(latitude, longitude);
		        jXMapKit1.setAddressLocation(position);    


 
		        Set<Waypoint> waypoints = new HashSet<Waypoint>();
		        waypoints.add(new Waypoint(latitude, longitude));
		        
		        
		        WaypointPainter painter = new WaypointPainter();
		        painter.setWaypoints(waypoints);
//		        painter.setRenderer(new WaypointRenderer() {
//		            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
//		                g.setColor(Color.RED);
//		                g.drawLine(-5,-5,+5,+5);
//		                g.drawLine(-5,+5,+5,-5);
//		                return true;
//		            }
//		        });
		        
		        jXMapKit1.getMainMap().setOverlayPainter(painter);
		        jXMapKit1.getMainMap().setZoom(2);
		        jXMapKit1.setAddressLocationShown(true);
		        jXMapKit1.setCenterPosition(position);
		        jXMapKit1.setDataProviderCreditShown(true);
//		        jXMapKit1.setMiniMapVisible(false);
		        
		       
		   mainPanel.add(jXMapKit1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		   getContentPane().add(mainPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		   mainPanelLayout.rowWeights = new double[] {0.1};
		   mainPanelLayout.rowHeights = new int[] {7};
		   mainPanelLayout.columnWeights = new double[] {0.1};
		   mainPanelLayout.columnWidths = new int[] {7};

			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

// Variables declaration - do not modify//GEN-BEGIN:variables

private org.jdesktop.swingx.JXMapKit jXMapKit1;
private javax.swing.JPanel mainPanel;


// End of variables declaration//GEN-END:variables


}
