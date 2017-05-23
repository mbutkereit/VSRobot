package management;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import consumer.FifoQueue;
import consumer.KommunikationsThread;
import implementation.BewegungsSteuerung;
import implementation.RoboterBewegung;
import others.RobotImplementationFactory;
import others.RobotStatusManager;
import provider.IDLCaDSEV3RMIMoveGripperSkeleton;
import provider.IIDLCaDSEV3RMIMoveHorizontalSkeleton;
import provider.IIDLCaDSEV3RMIMoveVerticalSkeleton;
import provider.IIDLCaDSEV3RMIUltraSonicSkeleton;
import provider.Revciever;
import stubs.IDLCaDSEV3RMINameserverRegistrationStub;
import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import interfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;
import interfaces.InterfaceIIDLCaDSEV3RMIMoveHorizontal;
import interfaces.InterfaceIIDLCaDSEV3RMIMoveVertical;
import interfaces.InterfaceIIDLCaDSEV3RMIUltraSonic;

public class RecieverManager {

	/**
	 * Die IP-Adresse von dem Nameserver
	 */
	public static InetAddress ipAdress = null;

	/**
	 * Der Port vom Nameserver
	 */
	public static int port = 8000;

	/**
	 * Namespace
	 */
	public static String namespace = null;

	/**
	 * Programmeinstieg
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// Auslesen der Kommandozeilenparameter und Registrierung beim
		// Nameserver.
		if (args.length == 3) {
			namespace = args[0];
			String ip = args[1];
			port = Integer.parseInt(args[2]);
			try {
				ipAdress = InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}
			
			// Logik
			FifoQueue sendQueue = new FifoQueue();
			FifoQueue recieveQueue = new FifoQueue();

			KommunikationsThread thread = new KommunikationsThread(sendQueue,
					recieveQueue);
			thread.start();

			InterfaceIDLCaDSEV3RMINameserverRegistration stub = new IDLCaDSEV3RMINameserverRegistrationStub(
					sendQueue);
			stub.registerService(
					namespace + "." + "InterfaceIDLCaDSEV3RMIMoveGripper", ip,
					port);
			stub.registerService(
					namespace + "." + "InterfaceIDLCaDSEV3RMIMoveHorizontal",
					ip, port + 1);
			stub.registerService(
					namespace + "." + "InterfaceIDLCaDSEV3RMIMoveVertical", ip,
					port + 2);
			stub.registerService(
					namespace + "." + "InterfaceIDLCaDSEV3RMIUltraSonic", ip,
					port + 3);
		} else {
			throw new IllegalArgumentException("Illegal Number of Arguments.");
		}

		// Get Roboter.
		RobotStatusManager manager = new RobotStatusManager();
		CaDSEV3RobotStudentImplementation robot = CaDSEV3RobotStudentImplementation
				.createInstance(CaDSEV3RobotType.SIMULATION, manager, manager);
		
		RoboterBewegung roboterbewegung = new RoboterBewegung(robot);
		roboterbewegung.start();
		BewegungsSteuerung steuerung = new BewegungsSteuerung(manager,roboterbewegung );
		steuerung.start();

		RobotImplementationFactory factory = new RobotImplementationFactory(
				robot,steuerung);

		// Get Implementations
		InterfaceIDLCaDSEV3RMIMoveGripper gripper = factory
				.getMoveGripperImplementation();
		InterfaceIIDLCaDSEV3RMIMoveHorizontal moveHorizontal = factory
				.getMoveHorizontalImplementation();
		InterfaceIIDLCaDSEV3RMIMoveVertical moveVertical = factory
				.getMoveVerticalImplementation();
		InterfaceIIDLCaDSEV3RMIUltraSonic ultraSonic = factory
				.getUltraSonicImplementation();

		// Threads für jeden Dienst
		Thread moveGripperProviderThread = new Thread(new Revciever(
				new IDLCaDSEV3RMIMoveGripperSkeleton(gripper), port));

		Thread moveHorizontalProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIMoveHorizontalSkeleton(moveHorizontal),
				port+1));

		Thread moveVerticalProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIMoveVerticalSkeleton(moveVertical), port+2));

		Thread ultraSonicProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIUltraSonicSkeleton(ultraSonic), port+3));

		moveGripperProviderThread.start();
		moveHorizontalProviderThread.start();
		ultraSonicProviderThread.start();
		moveVerticalProviderThread.start();

		moveGripperProviderThread.join();
		moveHorizontalProviderThread.join();
		moveVerticalProviderThread.join();
		ultraSonicProviderThread.join();
	}
}
