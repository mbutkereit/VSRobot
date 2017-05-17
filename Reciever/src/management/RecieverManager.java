package management;


import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import client.FifoQueue;
import client.Reciever;
import client.Sender;
import implementation.IIDLCaDSEV3RMIMoveHorizontalImplementation;
import implementation.IIDLCaDSEV3RMIMoveVerticalImplementation;
import implementation.IIDLCaDSEV3RMIUltraSonicImplementation;
import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import interfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;
import others.RobotMoveGripperFactory;
import others.RobotStatusManager;
import provider.IDLCaDSEV3RMIMoveGripperSkeleton;
import provider.IIDLCaDSEV3RMIMoveHorizontalSkeleton;
import provider.IIDLCaDSEV3RMIMoveVerticalSkeleton;
import provider.IIDLCaDSEV3RMIUltraSonicSkeleton;
import provider.Revciever;
import stubs.IDLCaDSEV3RMINameserverRegistrationStub;

public class RecieverManager {

	public static void main(String[] args) throws InterruptedException {

		if (args.length == 3) {
			String namespace = args[0];
			String ip = null;
			int port = -1;
			try {
				ip = args[1];
			} catch (Exception e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}
			try {
				port = Integer.parseInt(args[2]);
			} catch (Exception e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}
			
			// Logik
			FifoQueue sendQueue = new FifoQueue();
			FifoQueue recieveQueue = new FifoQueue();

			// Reciever
			Reciever reciever = new Reciever(recieveQueue);

			// Startet den Sender um Nachrichten an den Server zu schicken
			Sender sender = new Sender(sendQueue, reciever);

			reciever.start();
			sender.start();
			
			InterfaceIDLCaDSEV3RMINameserverRegistration stub = new IDLCaDSEV3RMINameserverRegistrationStub(sendQueue);
			
			stub.registerService(namespace+"."+ "InterfaceIDLCaDSEV3RMIMoveGripperSkeleton", ip, 3232);
			stub.registerService(namespace+"."+ "InterfaceIIDLCaDSEV3RMIMoveHorizontalSkeleton", ip, 3233);
			stub.registerService(namespace+"."+ "InterfaceIIDLCaDSEV3RMIMoveVerticalSkeleton", ip, 3234);
			stub.registerService(namespace+"."+ "InterfaceIIDLCaDSEV3RMIUltraSonic", ip, 3235);
		}
		
		
		
		// Get Roboter.
		RobotStatusManager manager = new RobotStatusManager();
		CaDSEV3RobotStudentImplementation robot = CaDSEV3RobotStudentImplementation
				.createInstance(CaDSEV3RobotType.SIMULATION, manager, manager);
		
		RobotMoveGripperFactory factory = new RobotMoveGripperFactory(robot);
		InterfaceIDLCaDSEV3RMIMoveGripper gripper = factory
.get(IDLCaDSEV3RMIMoveGripperSkeleton.ServiceClass);
		
		Thread moveGripperProviderThread = new Thread(new Revciever(new IDLCaDSEV3RMIMoveGripperSkeleton(gripper),3232));
		moveGripperProviderThread.start();
		
		Thread moveHorizontalProviderThread = new Thread(new Revciever(new IIDLCaDSEV3RMIMoveHorizontalSkeleton(new IIDLCaDSEV3RMIMoveHorizontalImplementation()),3233));
		moveHorizontalProviderThread.start();
		
		Thread moveVerticalProviderThread = new Thread(new Revciever(new IIDLCaDSEV3RMIMoveVerticalSkeleton(new  IIDLCaDSEV3RMIMoveVerticalImplementation()),3234));
		moveVerticalProviderThread.start();
		
		Thread ultraSonicProviderThread = new Thread(new Revciever(new IIDLCaDSEV3RMIUltraSonicSkeleton(new IIDLCaDSEV3RMIUltraSonicImplementation()),3235));
		ultraSonicProviderThread.start();
		
		moveGripperProviderThread.join();
		moveHorizontalProviderThread.join();
		moveVerticalProviderThread.join();
		ultraSonicProviderThread.join();
	}
}
