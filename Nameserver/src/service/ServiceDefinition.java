package service;

import java.net.InetAddress;

public class ServiceDefinition {

	private int port;
	private InetAddress ip;
	
	public ServiceDefinition(InetAddress ip, int port){
		this.port = port;
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}

	public InetAddress getIp() {
		return ip;
	}
}
