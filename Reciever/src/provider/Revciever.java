package provider;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.json.JsonObject;


public class Revciever implements Runnable {
	
	private DatagramSocket dsocket;
	
	private InterfaceSkeleton skeleton;
	private int port;
	private int bufferSize;
	
	public Revciever(InterfaceSkeleton skeleton,int port){
		this.skeleton = skeleton;
		this.port = port;
		this.bufferSize = 2048;
	}
	
	public Revciever(InterfaceSkeleton skeleton,int port, int bufferSize){
		this.skeleton = skeleton;
		this.port = port;
		this.bufferSize = bufferSize;
	}
	
	
	@Override
	public void run() {
		
		try {

			dsocket = new DatagramSocket(this.port);
			byte[] buffer = new byte[this.bufferSize];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			while (true) {
				dsocket.receive(packet);
				
				System.out.println("New Packet:");
				JsonObject response = this.skeleton.handle(buffer, packet.getLength());
				System.out.println(response.toString());

				packet.setLength(buffer.length);
				
				byte[] data = response.toString().getBytes();
				DatagramPacket send_packet = new DatagramPacket(data, data.length, packet.getAddress(),packet.getPort());
				dsocket.send(send_packet);
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
}