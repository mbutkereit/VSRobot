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
 * Queue. Außerdem wird für den Sender die Anzahl der erhaltenen Nachrichten erhöht.
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
			recieveSocket = new DatagramSocket(9092);
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
		byte[] receiveData = new byte[2048];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);
		while (true) {
			try {
				recieveSocket.receive(receivePacket);
			} catch (IOException e) {
				System.err.println("Fehler beim Empfangen der Nachricht");
				e.printStackTrace();
			}
			try (InputStream is = new ByteArrayInputStream(receiveData, 0,
					receivePacket.getLength());
					JsonReader rdr = Json.createReader(is)) {

				JsonObject obj = rdr.readObject();
				antworten++;
				queue.enque(obj);
			} catch (IOException e) {
				System.err.println("Fehler beim Lesen des JsonObjectes");
				e.printStackTrace();
			}
		}
	}
}
