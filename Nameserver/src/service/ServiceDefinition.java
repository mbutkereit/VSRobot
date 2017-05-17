package service;

import java.net.InetAddress;

/**
 * Eine Klasse die eine IP-Adresse und eine Portnummer kapselt.
 */
public class ServiceDefinition {

	/**
	 * Port
	 */
	private int port;

	/**
	 * Ip
	 */
	private InetAddress ip;

	/**
	 * Konstruktor
	 * 
	 * @param ip
	 *            eine IP-Adresse
	 * @param port
	 *            Portnummer
	 */
	public ServiceDefinition(InetAddress ip, int port) {
		this.port = port;
		this.ip = ip;
	}

	/**
	 * Getter
	 * 
	 * @return Portnummer
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Getter
	 * 
	 * @return IpAdresse
	 */
	public InetAddress getIp() {
		return ip;
	}
}
