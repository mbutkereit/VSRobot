package client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
	 * Factory
	 */
	private StubFactory factory = null;

	/**
	 * Der Gripperstub ist verantwortlich für das Mahrshalling.
	 */
	private InterfaceIDLCaDSEV3RMIMoveGripper gripperstub = null;

	/**
	 * Stub für die vertikale Bewegung.
	 */
	private InterfaceIDLCaDSEV3RMIMoveVertical verticalstub = null;

	/**
	 * Stub für die horizontale Bewegung.
	 */
	private InterfaceIDLCaDSEV3RMIMoveHorizontal horizontalstub = null;

	/**
	 * Stub für den Ultraschall.
	 */
	private InterfaceIDLCaDSEV3RMIUltraSonic ultrasonicstub = null;

	/**
	 * 
	 */
	private InterfaceIDLCaDSEV3RMINameserverRegistration nameserverRegStub = null;

	/**
	 * 
	 */
	public static int horizontalPercent = 50;

	/**
	 * 
	 */
	public static int verticalPercent = 50;

	/**
	 * 
	 */
	public static int gripperstate = 0;

	/**
	 * 
	 */
	public static int ultrasonicState = 0;

	/**
	 * Der aktuell ausgewählte Roboter
	 */
	public static String currentRoboter = null;

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
	public int closeGripper(int arg0) throws Exception {
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
		factory = new StubFactory(fifo);
		gripperstub = factory.getGripperStub();
		verticalstub = factory.getVerticalStub();
		horizontalstub = factory.getHorizontalStub();
		ultrasonicstub = factory.getUltraSonicStub();
		nameserverRegStub = factory.getNameserverRegStub();
	}

	private String[] lookup() throws Exception  {
		nameserverRegStub.lookup("ALL");
		DatagramSocket socket = new DatagramSocket(Sender.PORTNUMMERLOOKUPRECEIVE);
		byte[] receiveData = new byte[2048];
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		System.out.println("Gehe jetzt in das blockierende Receive");
		socket.receive(packet);
		
		System.out.println("LookUp Paket erhalten");
		try (InputStream is = new ByteArrayInputStream(receiveData, 0,
				packet.getLength()); JsonReader rdr = Json.createReader(is)) {

			JsonObject obj = rdr.readObject();
			System.out.println("Recieve::::::::"+obj.toString());
			String namespace = obj.getString("ReturnValue");
			roboternamearray = namespace.split(",");
			if(roboternamearray[0]==null){
				throw new Exception();
			}
			
			for(String text:roboternamearray){
				System.out.print(text+" , ");
			}
			currentRoboter = roboternamearray[0];
		}
		socket.close();
		return roboternamearray;
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
		
		// Startet Sender und Reciever
		Reciever reciever = new Reciever(recieveQueue);
		Sender sender = new Sender(sendQueue, reciever);
		reciever.start();
		sender.start();
		
		//Controller
		Controller c = new Controller(sendQueue);
		String [] namespace = null;
		try {
			namespace = c.lookup();
		} catch (Exception e1) {
			System.err.println("Fehler beim LookUp");
			e1.printStackTrace();
		}
		
		// Gui
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(c, c, c, c, c,namespace);
		gui.startGUIRefresh(5000);
		GuiUpdater updater = new GuiUpdater(recieveQueue, gui);
		updater.start();

		//Wartet auf Termination
		try {
			sender.join();
			reciever.join();
		} catch (InterruptedException e) {
			System.err.println("Main Thread interrupted while waiting");
			e.printStackTrace();
		}

	}
}
