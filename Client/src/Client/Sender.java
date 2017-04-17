package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.json.JsonObject;

/**
 * Die Klasse Sender sendet Elemente aus der Queue an den Server.
 * 
 * @author wilhelm
 *
 */
public class Sender extends Thread {

	/**
	 * Portnummer für die Verbindung zum Server
	 */
	public static final int PORTNUMMER = 9090;

	/**
	 * Die Adresse vom Server.
	 */
	public static final String IP_ADRESSE = "127.0.0.1";

	/**
	 * enhält die JsonObjekte
	 */
	private FifoQueue queue = null;

	/**
	 * Verbindungsendpunkt um Nachrichten zum Server zu schicken.
	 */
	private static DatagramSocket socket = null;

	/**
	 * Representant der InetAdresse
	 */
	private InetAddress ia = null;

	/**
	 * Konstruktor
	 */
	public Sender(FifoQueue queue) {
		this.queue = queue;
	}

	/**
	 * Initialisierung
	 */
	private void init() {
		try {
			ia = InetAddress.getByName(IP_ADRESSE);
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host.");
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt einen Socket.
	 */
	private void createSocket() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Error creating or accessing a Socket.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		init();
		createSocket();
		DatagramPacket packet = null;

		while (!isInterrupted()) {
			JsonObject object = queue.pull();

			// get the byte array of the object
			byte[] data = object.toString().getBytes();
			packet = new DatagramPacket(data, data.length, ia, PORTNUMMER);
			try {
				socket.send(packet);
				System.out.println("Paket gesendet");
			} catch (IOException e) {
				System.err.println("An I/O Error is occured.");
				e.printStackTrace();
			}
		}
		socket.close();
	}

}
