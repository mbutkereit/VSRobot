package management;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import others.RobotStatusManager;
import provider.MoveGripperSkeleton;
import provider.Revciever;

public class RecieverManager {

	public static void main(String[] args) throws InterruptedException {

		if (args.length == 3) {
			String namespace = args[1];
			try {
				InetAddress ia = InetAddress.getByName(args[2]);
			} catch (UnknownHostException e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}
			try {
				int port = Integer.parseInt(args[2]);
			} catch (Exception e) {
				System.err.println("Unknown Host.");
				e.printStackTrace();
			}
		}
		
		// Get Roboter.
		RobotStatusManager manager = new RobotStatusManager();
		CaDSEV3RobotStudentImplementation robot = CaDSEV3RobotStudentImplementation
				.createInstance(CaDSEV3RobotType.SIMULATION, manager, manager);
		
		Thread moveGripperProviderThread = new Thread(new Revciever(new MoveGripperSkeleton(robot),9090));
		moveGripperProviderThread.start();
		moveGripperProviderThread.join();
	}
}
