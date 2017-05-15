package management;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import others.RobotStatusManager;
import provider.MoveGripperSkeleton;
import provider.Revciever;

public class RecieverManager {
	
	
	public static void main(String[] args) throws InterruptedException {

		// Get Roboter.
		RobotStatusManager manager = new RobotStatusManager();
		CaDSEV3RobotStudentImplementation robot = CaDSEV3RobotStudentImplementation
				.createInstance(CaDSEV3RobotType.SIMULATION, manager, manager);
		
		Thread moveGripperProviderThread = new Thread(new Revciever(new MoveGripperSkeleton(robot),9090));
		moveGripperProviderThread.start();
		moveGripperProviderThread.join();
	}
}
