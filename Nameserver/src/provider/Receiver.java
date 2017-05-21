package provider;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.json.JsonObject;


/**
 * Dieser Thread empfängt die Nachrichten von den Clienten.
 * @author Wilhelm und Marvin
 *
 */
public class Receiver implements Runnable {
	
	/**
	 * Datagrammsocket
	 */
	private DatagramSocket dsocket;
	
	/**
	 * Eine Forwardstruktur, die verantwortlich für das weiterleiten ist.
	 */
	private InterfaceForwarder forwarder;
	
	/**
	 * Portnummer des Clienten.
	 */
	private int port;
	
	/**
	 * Maximale Buffergröße.
	 */
	private int bufferSize;
	
	/**
	 * Konstruktor
	 * @param forwarder Instanz zum Verarbeiten der Nachricht.
	 * @param port Port des Clienten.
	 */
	public Receiver(InterfaceForwarder forwarder,int port){
		this.forwarder = forwarder;
		this.port = port;
		this.bufferSize = 2048;
	}
	
	/**
	 * siehe anderer Konstruktor
	 * @param skeleton
	 * @param port
	 * @param bufferSize
	 */
	public Receiver(InterfaceForwarder skeleton,int port, int bufferSize){
		this.forwarder = skeleton;
		this.port = port;
		this.bufferSize = bufferSize;
	}
	
	
	/**
	 * Empfängt Nachrichten vom Client und verarbeitet diese.
	 */
	@Override
	public void run() {
		
		try {

			dsocket = new DatagramSocket(this.port);
			byte[] buffer = new byte[this.bufferSize];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			while (true) {
				dsocket.receive(packet);
				
				System.out.println("New Packet:"+port);
				JsonObject response = this.forwarder.handle(buffer, packet.getLength());
				System.out.println(response.toString());

				packet.setLength(buffer.length);
				
				byte[] data = response.toString().getBytes();
				DatagramPacket send_packet = new DatagramPacket(data, data.length, packet.getAddress(),packet.getPort());
				System.out.println("ich sende jetzt ein Paket.");
				dsocket.send(send_packet);
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
}