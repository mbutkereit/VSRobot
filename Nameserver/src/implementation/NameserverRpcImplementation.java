package implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import service.ServiceList;

/**
 * Die Klasse enthält die Methoden, die Nameserver zur Verfügung stellt und
 * diese Methoden können über Rpc aufgerufen werden.
 * 
 * @author Marvin und Wilhelm
 *
 */
public class NameserverRpcImplementation {

	/**
	 * Fügt einen Dienst zur Serviceliste hinzu.
	 * @param serviceName Name des Dienstes
	 * @param ip IP-Adresse des Dienstes
	 * @param port Portnummer des Dientes
	 */
	public void registerService(String serviceName, String ip, int port) {
		ServiceList list = ServiceList.getInstance();
		InetAddress ia = null;
		try {
			ia = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host.");
			e.printStackTrace();
		}
		list.registerService(serviceName, ia, port);
	}

	/**
	 * Entfernt einen Dienst aus der Serviceliste.
	 * @param name Name des Dienstes
	 */
	public void unregisterService(String name) {
		ServiceList list = ServiceList.getInstance();
		list.unregisterService(name);
	}

	/**
	 * Liefert alle Elemente des Namespaces.
	 * @return ein Stringarray  mit allen Namen aus dem Namespace.
	 */
	public String[] getAllNamespaces() {
		ServiceList list = ServiceList.getInstance();
		return list.getAllNamespaces();
	}

}
