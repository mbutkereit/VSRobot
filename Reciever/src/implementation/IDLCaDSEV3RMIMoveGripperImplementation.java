package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import others.RobotStatusManager;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMIMoveGripperImplementation implements InterfaceIDLCaDSEV3RMIMoveGripper {

	/**
	 * Roboter
	 */
	private CaDSEV3RobotStudentImplementation call = null;
	
	/**
	 * Statusmanager
	 */
	private RobotStatusManager manager = null;

	public IDLCaDSEV3RMIMoveGripperImplementation(
			CaDSEV3RobotStudentImplementation roboter,RobotStatusManager manager) {
		this.call = roboter;
		this.manager=manager;
	}

	@Override
	public int closeGripper(int arg0) {
		this.call.doClose();
		return 0;
	}

	@Override
	public int isGripperClosed() {
		return manager.getIsGripperClosed();
	}

	@Override
	public int openGripper(int arg0) {
		this.call.doOpen();
		return 0;
	}
    

}
