package service;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ServiceList {
	private Map<String, ServiceDefinition> services;

	private static ServiceList instance = null;

	protected ServiceList() {
		this.services = new HashMap<String, ServiceDefinition>();
	}

	public static synchronized ServiceList getInstance() {
		if (instance == null) {
			instance = new ServiceList();
		}
		return instance;
	}

	public InetAddress getIpFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getIp();
		}
		return null;
	}

	public int getPortFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getPort();
		}
		return -1;
	}
	
	public void registerService(String serviceName, InetAddress ip, int port) {
		this.services.put(serviceName, new ServiceDefinition(ip, port));
	}

	public void unregisterService(String name) {
		this.services.remove(name);
	}
	
	public String[] getAllNamespaces(){
		return null;
	}
	
	}
