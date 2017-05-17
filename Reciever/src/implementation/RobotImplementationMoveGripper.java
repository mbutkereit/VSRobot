package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;

public class RobotImplementationMoveGripper implements
InterfaceIDLCaDSEV3RMIMoveGripper{

	private CaDSEV3RobotStudentImplementation call = null;
	private static boolean gripperClosed = false;

	public RobotImplementationMoveGripper(
			CaDSEV3RobotStudentImplementation roboter) {
		this.call = roboter;
	}

	@Override
	public int closeGripper(int arg0) {
		this.call.doClose();
		RobotImplementationMoveGripper.gripperClosed = true;
		return 0;
	}

	@Override
	public int isGripperClosed() {
		if (RobotImplementationMoveGripper.gripperClosed == false) {
			return 1;
		}
		return 0;
	}

	@Override
	public int openGripper(int arg0) {
		this.call.doOpen();
		RobotImplementationMoveGripper.gripperClosed = false;
		return 0;
	}

}
