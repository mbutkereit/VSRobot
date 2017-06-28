package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import service.ServiceList;

public class Forwarder implements InterfaceForwarder {

	public JsonObject handle(byte[] buffer, int length) {

		ServiceList list = ServiceList.getInstance();
		DatagramSocket dsocket = null;
		try {
			dsocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JsonObjectBuilder response = null;
		JsonObject objektReturn = null;

		try (InputStream is = new ByteArrayInputStream(buffer, 0, length);
				JsonReader rdr = Json.createReader(is)) {
			JsonObject obj = rdr.readObject();

			System.err.println("Empfnagen:::::::"+obj.toString());
			String className = obj.getString("ObjectName");

			// Initialisieren
			boolean answerRecieved = false;
			DatagramPacket recieve_packet = null;
			byte[] receiveData = new byte[2048];

			// Laden des Services
			int port = list.getPortFromService(className);
			InetAddress ip = list.getIpFromService(className);
			if (port == -1 || ip == null) {
				System.err.println("Unbekannte Ip: "+ip+" oder Port: "+port);
				throw new Exception(className);
			}

			byte[] data = obj.toString().getBytes();
			DatagramPacket send_packet = new DatagramPacket(data, data.length,
					ip, port);
			dsocket.send(send_packet);
			dsocket.setSoTimeout(10000);

			recieve_packet = new DatagramPacket(receiveData,
					receiveData.length);
			answerRecieved = false;
			int versuche = 0;

			while (!(answerRecieved) && versuche < 5) {
				try {
					dsocket.receive(recieve_packet);
					answerRecieved = true;
					try (InputStream is2 = new ByteArrayInputStream(recieve_packet.getData(), 0,
							recieve_packet.getData().length); JsonReader rdr2 = Json.createReader(is2)) {
						objektReturn = rdr2.readObject();
						System.out.println("Antwort vom Provider::::::"
								+ objektReturn.toString());

					}
				} catch (SocketTimeoutException e) {
					System.err.println(
							"Timeout: Versuche die Nachricht noch einmal zu �bertragen");
					versuche++;
					dsocket.send(send_packet);
					System.out.println("Paket erneut gesendet");
				}
			}

		} catch (Exception e) {
			System.err.println("Unbekannter Robotername: "+e.getMessage());
			response = Json.createObjectBuilder();
			response.add("Type", "Response");
			response.add("Exception", "ClassNotFoundException");
			objektReturn = response.build();
		}
		dsocket.close();

		return objektReturn;
	}

}
