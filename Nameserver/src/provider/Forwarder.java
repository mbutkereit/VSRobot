package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import service.ServiceList;

public class Forwarder implements InterfaceForwarder {

	public JsonObject handle(byte[] buffer, int length) {

		ServiceList list = ServiceList.getInstance();
		
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "Response");

		try (InputStream is = new ByteArrayInputStream(buffer, 0, length); JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();
			String className = obj.getString("ObjectName");
			String methodName = obj.getString("FunctionName");
			
			// Initialisieren
			DatagramSocket dsocket = new DatagramSocket();
			boolean answerRecieved = false;
			DatagramPacket recieve_packet = null;
			
			// Laden des Services
			int port = list.getPortFromService(className);
			InetAddress ip = list.getIpFromService(className);
			if(port == -1 || ip == null){
				throw new Exception();
			}
			
			byte[] data = obj.toString().getBytes();
			DatagramPacket send_packet = new DatagramPacket(data, data.length, ip, port);
			dsocket.send(send_packet);
			dsocket.setSoTimeout(2000);

			answerRecieved = false;
			int versuche = 0;
			while (!(answerRecieved) && versuche < 5) {
				try {
					dsocket.receive(recieve_packet);
					answerRecieved = true;
					byte[] data_response = response.toString().getBytes();
					DatagramPacket response_packet = new DatagramPacket(data_response, data_response.length, ip, port);
					dsocket.send(send_packet);
				} catch (SocketTimeoutException e) {
					System.err.println("Timeout: Versuche die Nachricht noch einmal zu �bertragen");
					versuche++;
					dsocket.send(recieve_packet);
					System.out.println("Paket erneut gesendet");
				}
			}

		} catch (Exception e) {
			response.add("Type", "Response");
			response.add("Exception", "ClassNotFoundException");
		}

		return response.build();
	}

}
