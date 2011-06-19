package presentation;

import internationalization.AppInternationalization;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.EventListenerList;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.jdesktop.application.Application;

import presentation.auxiliary.CloseWindowListener;


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
 * About Window
 */
public class JDAbout extends javax.swing.JDialog {
	private static final long serialVersionUID = -4903915570854306815L;
	
	private EventListenerList listenerList;
	private JButton btnAceptar;
	private JPanel jPanel1;
	private JLabel lblTitulo2;
	private JLabel lblTitulo;
	private JTextPane txtAcercaDe;
	
	public JDAbout(JFrame frame) {
		super(frame);
		initGUI();
		listenerList = new EventListenerList();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle("Acerca del Servidor Front-End (SSCA)");
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setLayout(null);
				jPanel1.setBounds(0, 0, 414, 272);
				{
					lblTitulo2 = new JLabel();
					jPanel1.add(lblTitulo2);
					lblTitulo2.setText("Servidor Front-End");
					lblTitulo2.setPreferredSize(new java.awt.Dimension(389, 26));
					lblTitulo2.setFont(new java.awt.Font("Tahoma",1,20));
					lblTitulo2.setOpaque(false);
					lblTitulo2.setFocusable(false);
					lblTitulo2.setHorizontalAlignment(SwingConstants.CENTER);
					lblTitulo2.setBounds(12, 5, 389, 26);
				}
				{
					lblTitulo = new JLabel();
					jPanel1.add(lblTitulo);
					lblTitulo.setText("Sistema de Salud de Comunidades Autónomas");
					lblTitulo.setPreferredSize(new java.awt.Dimension(394, 19));
					lblTitulo.setOpaque(false);
					lblTitulo.setFont(new java.awt.Font("Tahoma",1,14));
					lblTitulo.setFocusable(false);
					lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
					lblTitulo.setBounds(10, 36, 394, 19);
				}
				{
					txtAcercaDe = new JTextPane();
					jPanel1.add(txtAcercaDe);
					txtAcercaDe.setPreferredSize(new java.awt.Dimension(370, 155));
					txtAcercaDe.setOpaque(false);
					txtAcercaDe.setContentType("text/html");
					txtAcercaDe.setText("Versión: 1.0<br>\nPágina web: <a href=\"http://www.inf-cr.uclm.es\">ESI@UCLM</a><br><br>\nDesarrolladores:\n<ul>Juan Andrada Romero (<a href=\"mailto:juan.andrada@alu.uclm.es\">juan.andrada@alu.uclm.es</a>)<br>\nJuan Gallardo Casero (<a href=\"mailto:juan.gallardo@alu.uclm.es\">juan.gallardo@alu.uclm.es</a>)<br>\nJose Domingo López López (<a href=\"mailto:josed.lopez1@alu.uclm.es\">josed.lopez1@alu.uclm.es</a>)<br>");
					txtAcercaDe.setEditable(false);
					txtAcercaDe.setFocusable(false);
					txtAcercaDe.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					txtAcercaDe.setBounds(22, 60, 370, 155);
					txtAcercaDe.addHyperlinkListener(new HyperlinkListener() {
						public void hyperlinkUpdate(HyperlinkEvent evt) {
							txtAcercaDeHyperlinkUpdate(evt);
						}
					});
				}
				{
					btnAceptar = new JButton();
					jPanel1.add(btnAceptar);
					btnAceptar.setText("Aceptar");
					btnAceptar.setName("btnAccept");
					btnAceptar.setBounds(309, 236, 83, 25);
					btnAceptar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							btnAceptarActionPerformed(evt);
						}
					});
				}
			}
			setResizable(false);
			this.setPreferredSize(new java.awt.Dimension(400, 300));
			this.setMaximumSize(new java.awt.Dimension(420, 300));
			this.setMinimumSize(new java.awt.Dimension(420, 300));
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					thisWindowClosing(evt);
				}
			});
			pack();
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(getContentPane());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//$hide>>$
	
	private void txtAcercaDeHyperlinkUpdate(HyperlinkEvent evt) {
		if(evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				Desktop.getDesktop().browse(evt.getURL().toURI());
			} catch(IOException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			} catch(URISyntaxException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), AppInternationalization.getString("Error"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void btnAceptarActionPerformed(ActionEvent evt) {
		cerrarVentana();
	}
	
	private void thisWindowClosing(WindowEvent evt) {
		cerrarVentana();
	}

	private void cerrarVentana() {
		Object[] listeners;
		int i;
		
		// Notificamos que la ventana se ha cerrado
		listeners = listenerList.getListenerList();
		for(i = 0; i < listeners.length; i += 2) {
			if(listeners[i] == CloseWindowListener.class) {
				((CloseWindowListener)listeners[i + 1]).closeWindow(new EventObject(this));
			}
		}
	}
	
	// Métodos públicos
	
	public void addVentanaCerradaListener(CloseWindowListener ventanaCerradaListener) {
		listenerList.add(CloseWindowListener.class, ventanaCerradaListener);
	}

	public void removeVentanaCerradaListener(CloseWindowListener listener) {
		listenerList.remove(CloseWindowListener.class, listener);
	}

	//$hide<<$

}
