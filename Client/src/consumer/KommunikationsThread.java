package consumer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Die Klasse Sender sendet Elemente aus der Queue an den Server.
 * 
 * @author wilhelm
 *
 */
public class KommunikationsThread extends Thread {

	/**
	 * Portnummer für die Verbindung zum Server
	 */
	public static final int PORTNUMMERNAMESERVER = 9090;

	/**
	 * Portnummer für die Verbindung zum Server
	 */
	public static final int PORTNUMMERLOOKUP = 9091;

	/**
	 * Die Adresse vom Server.
	 */
	public static final String IP_ADRESSE = "127.0.0.1";

	/**
	 * Enthält die Nachrichten, die verschickt werden sollen.
	 */
	private FifoQueue sendQueue = null;

	/**
	 * Enthält die Nachrichten die Empfangen werden.
	 */
	private FifoQueue receiveQueue = null;

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
	public KommunikationsThread(FifoQueue sendQueue, FifoQueue receiveQueue) {
		this.sendQueue = sendQueue;
		this.receiveQueue = receiveQueue;
		init();
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
		createSocket();
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

	// Der Sender hat die Aufgabe Nachrichten an den Nameserver oder
	// Foward-Broker zuschicken und die anscließende Antwort wird in eine Queue
	// gelegt.
	@Override
	public void run() {
		DatagramPacket packet = null;
		DatagramPacket altesPacket = null;
		byte[] data = null;
		int portnummer = -1;
		boolean answerReceived = false;

		while (!isInterrupted()) {
			// Nachricht aus der Queue nehmen
			JsonObject object = sendQueue.deque();
			if (object.getString("ObjectName")
					.contains("InterfaceIDLCaDSEV3RMINameserverRegistration")) {
				portnummer = PORTNUMMERLOOKUP;
			} else {
				portnummer = PORTNUMMERNAMESERVER;
			}
			System.out.println("Senden:::::: " + object.toString());

			// get the byte array of the object
			data = object.toString().getBytes();
			packet = new DatagramPacket(data, data.length, ia, portnummer);
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int versuche = 0;
			answerReceived = false;
			altesPacket = packet;
			data = new byte[2048];
			packet = new DatagramPacket(data, data.length);

			try {
				socket.setSoTimeout(20000);
				while (versuche < 5 && answerReceived == false) {
					try {
						socket.receive(packet);
						System.out.println("Nachricht vom Broker erhalten");
						answerReceived = true;
					} catch (SocketTimeoutException e) {
						System.err.println(
								"Timeout: Versuche die Nachricht noch einmal zu übertragen");
						versuche++;
						socket.send(altesPacket);
					}
				}

			} catch (SocketException e) {
				System.err.println("Error with a socket in the Sender");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("IO-Exception im Sender.");
				e.printStackTrace();
			}

			if (answerReceived) {
				try (InputStream is = new ByteArrayInputStream(data, 0,
						packet.getLength());
						JsonReader rdr = Json.createReader(is)) {

					JsonObject obj = rdr.readObject();
					receiveQueue.enque(obj);
				} catch (IOException e) {
					System.err.println(
							"Fehler beim Lesen des JsonObjectes im Receiver");
					e.printStackTrace();
				}
			}
			if (versuche == 5) {
				System.err.println(
						"Übertragung war nicht möglich::: => Nachricht wird verworfen");
			}
		}
		socket.close();
	}
}
