package client;

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
	 * Portnummer für die Verbindung zum Server
	 */
	public static final int PORTNUMMERLOOKUP = 9091;
	
	/**
	 * Portnummer für den Receive vom Lookup
	 */
	public static final int PORTNUMMERLOOKUPRECEIVE = 9093;

	/**
	 * Die Adresse vom Server.
	 */
	public static final String IP_ADRESSE = "127.0.0.1";

	/**
	 * Instanz des Recievers
	 */
	private Reciever reciever = null;

	/**
	 * Enthält die JsonObjekte, die verschickt werden sollen.
	 */
	private FifoQueue sendQueue = null;

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
	public Sender(FifoQueue queue, Reciever reciever) {
		this.sendQueue = queue;
		this.reciever = reciever;
		init();
		createSocket();
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
			System.err
					.println("Error creating or accessing a Socket im Sender.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		DatagramPacket packet = null;
		byte[] data = null;
		int portnummer = PORTNUMMER;

		while (!isInterrupted()) {
			portnummer = PORTNUMMER;
			JsonObject object = sendQueue.deque();
			if(object.getString("ObjectName")=="InterfaceIDLCaDSEV3RMINameserverRegistration"){
				 portnummer = PORTNUMMERLOOKUP;
			}

			System.out.println("Senden:::::: " + object.toString());

			// get the byte array of the object
			data = object.toString().getBytes();
			packet = new DatagramPacket(data, data.length, ia, portnummer);
			try {
				socket.send(packet);
				System.out.println("Paket wurde gesendet");
				int versuche = 0;

				while (reciever.getAntworten() == 0 && (versuche < 5)) {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.err.println(
							"Timeout: Versuche die Nachricht noch einmal zu �bertragen");
					versuche++;
					socket.send(packet);
				}
				reciever.setAntworten(reciever.getAntworten() - 1);
			} catch (SocketException e) {
				System.err.println("Socket has been closed .");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("An I/O Error is occured.");
				e.printStackTrace();
			}
		}
		socket.close();
	}
}
