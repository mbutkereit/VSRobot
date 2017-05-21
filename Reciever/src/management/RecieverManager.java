package management;

import java.net.InetAddress;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import consumer.FifoQueue;
import consumer.KommunikationsThread;
import implementation.IIDLCaDSEV3RMIMoveHorizontalImplementation;
import implementation.IIDLCaDSEV3RMIMoveVerticalImplementation;
import implementation.IIDLCaDSEV3RMIUltraSonicImplementation;
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

		// Anmeldung der Dienste beim Nameserver
		if (args.length == 1) {
			namespace = args[0];
			String ip = null;
			try {
				ip = "127.0.0.1";
				ipAdress = InetAddress.getByName(ip);
			} catch (Exception e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}

			// Logik
			FifoQueue sendQueue = new FifoQueue();
			FifoQueue recieveQueue = new FifoQueue();
			
			KommunikationsThread thread = new KommunikationsThread(sendQueue, recieveQueue);
			thread.start();
		


			InterfaceIDLCaDSEV3RMINameserverRegistration stub = new IDLCaDSEV3RMINameserverRegistrationStub(
					sendQueue);
			stub.registerService(
					namespace + "."
							+ "InterfaceIDLCaDSEV3RMIMoveGripperSkeleton",
					ip, 3232);
			stub.registerService(
					namespace + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveHorizontalSkeleton",
					ip, 3233);
			stub.registerService(
					namespace + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveVerticalSkeleton",
					ip, 3234);
			stub.registerService(
					namespace + "." + "InterfaceIIDLCaDSEV3RMIUltraSonic", ip,
					3235);
			stub.registerService(
					"robi25" + "."
							+ "InterfaceIDLCaDSEV3RMIMoveGripperSkeleton",
					ip, 3232);
			stub.registerService(
					"robi25" + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveHorizontalSkeleton",
					ip, 3233);
			stub.registerService(
					"robi25" + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveVerticalSkeleton",
					ip, 3234);
			stub.registerService(
					"robi25" + "." + "InterfaceIIDLCaDSEV3RMIUltraSonic", ip,
					3235);
			stub.registerService(
					"robocop" + "."
							+ "InterfaceIDLCaDSEV3RMIMoveGripperSkeleton",
					ip, 3232);
			stub.registerService(
					"robocop" + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveHorizontalSkeleton",
					ip, 3233);
			stub.registerService(
					"robocop" + "."
							+ "InterfaceIIDLCaDSEV3RMIMoveVerticalSkeleton",
					ip, 3234);
			stub.registerService(
					"robocop" + "." + "InterfaceIIDLCaDSEV3RMIUltraSonic", ip,
					3235);
		}
		
		//Nur zum Stoppen Endlosschleife
		while("".equals("")){
			
		}

		// Get Roboter.
		RobotStatusManager manager = new RobotStatusManager();
		CaDSEV3RobotStudentImplementation robot = CaDSEV3RobotStudentImplementation
				.createInstance(CaDSEV3RobotType.SIMULATION, manager, manager);

		RobotImplementationFactory factory = new RobotImplementationFactory(
				robot);

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
				new IDLCaDSEV3RMIMoveGripperSkeleton(gripper), 3232));

		Thread moveHorizontalProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIMoveHorizontalSkeleton(moveHorizontal),
				3233));

		Thread moveVerticalProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIMoveVerticalSkeleton(moveVertical), 3234));

		Thread ultraSonicProviderThread = new Thread(new Revciever(
				new IIDLCaDSEV3RMIUltraSonicSkeleton(ultraSonic), 3235));

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
