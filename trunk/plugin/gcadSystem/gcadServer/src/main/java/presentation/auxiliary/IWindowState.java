package presentation.auxiliary;

/**
 * Windows interface for displaying the status of the server
 */
public interface IWindowState {

	void putMessage(String string);

	void updateConnectedClients(int clients);

}
