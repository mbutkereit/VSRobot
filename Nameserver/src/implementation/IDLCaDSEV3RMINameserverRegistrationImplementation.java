package implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import interfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;
import nameserver.service42.UserList;
import service.ServiceDefinition;
import service.ServiceList;

/**
 * Die Klasse enthält die Methoden, die Nameserver zur Verfügung stellt und
 * diese Methoden können über Rpc aufgerufen werden.
 * 
 * @author Marvin und Wilhelm
 *
 */
public class IDLCaDSEV3RMINameserverRegistrationImplementation implements InterfaceIDLCaDSEV3RMINameserverRegistration{

	/**
	 * Fügt einen Dienst zur Serviceliste hinzu.
	 * @param serviceName Name des Dienstes
	 * @param ip IP-Adresse des Dienstes
	 * @param port Portnummer des Dientes
	 */
	public int registerService(String serviceName, String ip, int port) {
		ServiceList list = ServiceList.getInstance();
		InetAddress ia = null;
		try {
			ia = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host.");
			e.printStackTrace();
		}
		list.registerService(serviceName, ia, port);
		return 0;
	}

	/**
	 * Entfernt einen Dienst aus der Serviceliste.
	 * @param name Name des Dienstes
	 */
	public int unregisterService(int name) {
		ServiceList list = ServiceList.getInstance();
		//list.unregisterService(name);
		
		return 0;
	}

	/**
	 * Liefert alle Elemente des Namespaces.
	 * 
	 * @return ein Stringarray mit allen Namen aus dem Namespace.
	 */
	public String lookup(String name) {
		StringBuffer json = new StringBuffer();
		ServiceList list = ServiceList.getInstance();
		String[] namespaces = list.getAllNamespaces();

		for (String name_service : namespaces) {
			json.append(name_service+',');
		}
		return json.toString();
	}


}
