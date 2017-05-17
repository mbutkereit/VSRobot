package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import dienste.IIDLCaDSEV3RMIMoveGripper;
import dienste.IIDLCaDSEV3RMIMoveHorizontal;
import dienste.IIDLCaDSEV3RMIMoveVertical;
import dienste.IIDLCaDSEV3RMIUltraSonic;
import gui.CaDSRobotGUISwing;
import gui.ICaDSRobotGUIUpdater;

/**
 * Die Klasse wird von der Gui benachrichtigt, wenn sich etwas ver�ndert.
 * 
 * @author wilhelm
 *
 */
public class Controller
		implements IIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveHorizontal,
		IIDLCaDSEV3RMIMoveVertical, IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer {
	/**
	 * Instanz der Gui
	 */
	private CaDSRobotGUISwing gui = null;

	/**
	 * Factory
	 */
	private StubFactory factory = null;

	/**
	 * Der Gripperstub ist verantwortlich f�r das Mahrshalling.
	 */
	private InterfaceIDLCaDSEV3RMIMoveGripper gripperstub = null;

	/**
	 * Stub für die vertikale Bewegung.
	 */
	private InterfaceIIDLCaDSEV3RMIMoveVertical verticalstub = null;

	/**
	 * Stub für die horizontale Bewegung.
	 */
	private InterfaceIIDLCaDSEV3RMIMoveHorizontal horizontalstub = null;

	/**
	 * Stub für den Ultraschall.
	 */
	private InterfaceIIDLCaDSEV3RMIUltraSonic ultrasonicstub = null;

	/**
	 * 
	 */
	private InterfaceIDLCaDSEV3RMINameserverRegistration nameserverRegStub = null;

	/**
	 * 
	 */
	private int horizontalPercent = 50;

	/**
	 * 
	 */
	private int verticalPercent = 50;

	/**
	 * 
	 */
	private int gripperstate = 0;

	/**
	 * 
	 */
	private int ultrasonicState = 0;

	/**
	 * Der aktuell ausgewählte Roboter
	 */
	private String currentRoboter = null;

	/**
	 * Alle auswählbaren Roboter
	 */
	private String[] roboternamearray = null;

	/**
	 * Konstruktor
	 */
	public Controller(FifoQueue fifo) {
		init(fifo);
	}

	@Override
	public void register(ICaDSRobotGUIUpdater observer) {
		System.out.println("New Observer");
	}

	@Override
	public void update(String arg0) {
		currentRoboter=arg0;

	}

	@Override
	public int isUltraSonicOccupied() throws Exception {
		ultrasonicstub.isUltraSonicOccupied();
		return ultrasonicState;
	}

	@Override
	public int getCurrentVerticalPercent() throws Exception {
		verticalstub.getCurrentVerticalPercent();
		return verticalPercent;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		verticalstub.moveVerticalToPercent(arg0, arg1);
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		horizontalstub.getCurrentHorizontalPercent();
		return horizontalPercent;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		horizontalstub.moveHorizontalToPercent(arg0, arg1);
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		verticalstub.stop(arg0);
		horizontalstub.stop(arg0);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return gripperstate;
	}

	@Override
	public int closeGripper(int arg0,) throws Exception {
		gripperstub.closeGripper(arg0);
		return 10;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		gripperstub.openGripper(arg0);
		return 0;
	}

	/**
	 * Initialisierung
	 * 
	 * @param fifo
	 *            eine Queue f�r die JsonDokumente
	 */
	private void init(FifoQueue fifo) {
		FifoQueue fifoNamespace = new FifoQueue();
		factory = new StubFactory(fifo, fifoNamespace);
		gripperstub = factory.getGripperStub();
		verticalstub = factory.getVerticalStub();
		horizontalstub = factory.getHorizontalStub();
		ultrasonicstub = factory.getUltraSonicStub();
		nameserverRegStub = factory.getNameserverRegStub();
		// Gui
		gui = new CaDSRobotGUISwing(this, this, this, this, this);
		gui.setGripperClosed();
		gui.setVerticalProgressbar(20);
		gui.setHorizontalProgressbar(20);
		gui.startGUIRefresh(5000);

		String[] name = lookup(fifoNamespace);
	}

	private String[] lookup(FifoQueue queue) {

		InetAddress ia = null;
		try {
			ia = InetAddress.getByName(Sender.IP_ADRESSE);
		} catch (UnknownHostException e) {
			System.err.println("Unknown Host.");
			e.printStackTrace();
		}
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.err.println("Error creating or accessing a Socket.");
			e.printStackTrace();
		}

		DatagramPacket packet = null;
		nameserverRegStub.lookup("ALL");
		JsonObject obj = queue.deque();
		byte[] receiveData = new byte[2048];

		System.out.println("Senden:::::: " + obj.toString());
		// get the byte array of the object
		byte[] data = obj.toString().getBytes();
		packet = new DatagramPacket(data, data.length, ia,
				Sender.PORTNUMMERLOOKUP);
		try {
			socket.send(packet);
			System.out.println("Paket gesendet");
			packet = new DatagramPacket(receiveData, receiveData.length, ia,
					Sender.PORTNUMMERLOOKUP);
			System.out.println(new String(packet.getData()));
			socket.receive(packet);
			System.out.println(new String(packet.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Paket erhalten");

		try (InputStream is = new ByteArrayInputStream(receiveData, 0,
				packet.getLength()); JsonReader rdr = Json.createReader(is)) {

			JsonObject obj2 = rdr.readObject();
			System.out.println("Recieve::::::::"+obj2.toString());
			String namespace = obj2.getString("ReturnValue");
			String [] array = namespace.split(",");
			for(String text:array){
				System.out.println(text);
			}
			
			roboternamearray = array;
			currentRoboter= roboternamearray[0];
			
			for(String roboternamen:roboternamearray){
				gui.addService(roboternamen);
			}
			
			

			
		} catch (IOException e) {
			System.err.println("Fehler beim Lesen des JsonObjectes");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Programmstart
	 * 
	 * @param args
	 *            Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		// Logik
		FifoQueue sendQueue = new FifoQueue();
		FifoQueue recieveQueue = new FifoQueue();
		Controller c = new Controller(sendQueue);

		// Reciever
		Reciever reciever = new Reciever(recieveQueue);

		// Startet den Sender um Nachrichten an den Server zu schicken
		Sender sender = new Sender(sendQueue, reciever);

		reciever.start();
		sender.start();

		try {
			sender.join();
			reciever.join();
		} catch (InterruptedException e) {
			System.err.println("Main Thread interrupted while waiting");
			e.printStackTrace();
		}

	}
}
