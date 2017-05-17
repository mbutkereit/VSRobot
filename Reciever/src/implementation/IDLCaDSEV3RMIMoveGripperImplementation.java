package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMIMoveGripperImplementation implements InterfaceIDLCaDSEV3RMIMoveGripper {

	private CaDSEV3RobotStudentImplementation call = null;
	private static boolean gripperClosed = false;

	public IDLCaDSEV3RMIMoveGripperImplementation(
			CaDSEV3RobotStudentImplementation roboter) {
		this.call = roboter;
	}

	@Override
	public int closeGripper(int arg0) {
		this.call.doClose();
		IDLCaDSEV3RMIMoveGripperImplementation.gripperClosed = true;
		return 0;
	}

	@Override
	public int isGripperClosed() {
		if (IDLCaDSEV3RMIMoveGripperImplementation.gripperClosed == false) {
			return 1;
		}
		return 0;
	}

	@Override
	public int openGripper(int arg0) {
		this.call.doOpen();
		IDLCaDSEV3RMIMoveGripperImplementation.gripperClosed = false;
		return 0;
	}
    

}
