package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Die Klasse empfängt alle Nachrichten vom Broker und schreibt sie in eine
 * Queue. Außerdem wird für den Sender die Anzahl der erhaltenen Nachrichten
 * erhöht.
 * 
 * @author wilhelm
 *
 */
public class Reciever extends Thread {

	/**
	 * Ein Socket zum Recieven von Nachrichten.
	 */
	private DatagramSocket recieveSocket = null;

	/**
	 * Eine Queue für die eingehenden Nachrichten.
	 */
	private FifoQueue queue = null;

	/**
	 * erhaltene Antworten
	 */
	private int antworten = 0;

	/**
	 * Portnummer für die Nachrichten vom Server.
	 */
	public static final int PORTNUMMERRECIEVER = 9092;

	/**
	 * Getter
	 * 
	 * @return
	 */
	public int getAntworten() {
		return antworten;
	}

	/**
	 * Setter
	 * 
	 * @param antworten
	 */
	public void setAntworten(int antworten) {
		this.antworten = antworten;
	}

	/**
	 * Konstruktor
	 * 
	 * @param queue
	 */
	public Reciever(FifoQueue queue) {
		this.queue = queue;
		try {
			recieveSocket = new DatagramSocket(Reciever.PORTNUMMERRECIEVER);
		} catch (SocketException e) {
			System.err.println(
					"Fehler beim Erstellen eines Sockets beim Reciever.");
			e.printStackTrace();
		}
	}

	/**
	 * Empfängt alle Nachrichten vom Broker und schreibt diese in eine Queue.
	 */
	@Override
	public void run() {
		byte[] receiveData = null;
		DatagramPacket receivePacket = null;
		while (true) {
			try {
				receiveData = new byte[2048];
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				recieveSocket.receive(receivePacket);
			} catch (IOException e) {
				System.err.println(
						"Fehler beim Empfangen der Nachricht im Receiver.");
				e.printStackTrace();
			}
			try (InputStream is = new ByteArrayInputStream(receiveData, 0,
					receivePacket.getLength());
					JsonReader rdr = Json.createReader(is)) {

				JsonObject obj = rdr.readObject();
				antworten++;
				queue.enque(obj);
			} catch (IOException e) {
				System.err.println(
						"Fehler beim Lesen des JsonObjectes im Receiver");
				e.printStackTrace();
			}
		}
	}
}
