package implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import service.ServiceList;

public class NameserverRpcImplementation {

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

	public void unregisterService(String name) {
		ServiceList list = ServiceList.getInstance();
		list.unregisterService(name);
	}
	
	public String[] getAllNamespaces(){
		ServiceList list = ServiceList.getInstance();
		return  list.getAllNamespaces();
	}

}
