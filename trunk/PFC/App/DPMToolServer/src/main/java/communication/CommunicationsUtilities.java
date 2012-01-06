package communication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Auxiliary class with static methods related to communication between systems.
 */
public class CommunicationsUtilities {

	private final static String IP_LOCALHOST = "127.0.0.1";
	
	// Get public IP, private IP or local IP
	public static String getHostIP() {
		Enumeration<NetworkInterface> interfaces;
		Enumeration<InetAddress> IPAdresses;
		Vector<String> ips;
		NetworkInterface inter;
		InetAddress IPAddress;
		Pattern patternIP;
		String ip;
		boolean found;
		int i;
		
		patternIP = Pattern.compile("\\b" +
				// 10.0.0.0 - 10.255.255.255
				"((10)\\." +
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|" +
                // 172.16.0.0 - 172.31.255.255
				"((172)\\." +
				"(1[6-9]|2[0-9]|3[0-1])\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|" +
                // 192.168.0.0 - 192.168.255.255
				"((192)\\." +
				"(168)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))" +
                "\\b");
		
		ip = "";
		try {
			// Go over all network interfaces
			ips = new Vector<String>();
			interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()) {
				inter = interfaces.nextElement();
				// Go over all IP addresses from interface
				IPAdresses = inter.getInetAddresses();
				while(IPAdresses.hasMoreElements()) {
					IPAddress = IPAdresses.nextElement();
					if(IPAddress instanceof Inet4Address) {
						ips.add(IPAddress.getHostAddress());
					}
				}
			}
			// Take first public IP
			found = false;
			for(i = 0; !found && i < ips.size(); i++) {
				if(!ips.get(i).equals(IP_LOCALHOST) && !patternIP.matcher(ips.get(i)).matches()) {
					found = true;
					ip = ips.get(i);
				}
			}
			// It there isn't public IP, take the first private IP
			if(!found) {
				for(i = 0; !found && i < ips.size(); i++) {
					if(!ips.get(i).equals(IP_LOCALHOST)) {
						found = true;
						ip = ips.get(i);
					}
				}
			}
			if(!found) {
				ip = IP_LOCALHOST;
			}
		} catch(SocketException e) {
			ip = IP_LOCALHOST;
		}
		
		return ip;
	}
	
}
