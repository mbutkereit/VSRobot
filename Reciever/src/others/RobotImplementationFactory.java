package others;

import implementation.BewegungsSteuerung;
import implementation.IDLCaDSEV3RMIMoveGripperImplementation;
import implementation.IIDLCaDSEV3RMIMoveHorizontalImplementation;
import implementation.IIDLCaDSEV3RMIMoveVerticalImplementation;
import implementation.IIDLCaDSEV3RMIUltraSonicImplementation;
import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import interfaces.InterfaceIIDLCaDSEV3RMIMoveHorizontal;
import interfaces.InterfaceIIDLCaDSEV3RMIMoveVertical;
import interfaces.InterfaceIIDLCaDSEV3RMIUltraSonic;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

/**
 * Eine Klasse, die Getter für die verschiedenen Implementationen der Dienste
 * enthält.
 * 
 * @author wilhelm
 *
 */
public class RobotImplementationFactory {
	/**
	 * Instanz des Roboters
	 */
	private CaDSEV3RobotStudentImplementation robot;
	
	/**
	 * Instanz der Bewegungssteuerung
	 */
	private BewegungsSteuerung steuerung;

	/**
	 * Konstruktor
	 * 
	 * @param robot
	 */
	public RobotImplementationFactory(CaDSEV3RobotStudentImplementation robot,BewegungsSteuerung steuerung) {
		this.robot = robot;
		this.steuerung=steuerung;
	}

	/**
	 * 
	 * @param ClassName
	 * @return
	 */
	public InterfaceIDLCaDSEV3RMIMoveGripper getMoveGripperImplementation() {
		return new IDLCaDSEV3RMIMoveGripperImplementation(this.robot);
	}

	/**
	 * 
	 * @param ClassName
	 * @return
	 */
	public InterfaceIIDLCaDSEV3RMIMoveHorizontal getMoveHorizontalImplementation() {
		return new IIDLCaDSEV3RMIMoveHorizontalImplementation(this.robot,steuerung);
	}

	/**
	 * 
	 * @param ClassName
	 * @return
	 */
	public InterfaceIIDLCaDSEV3RMIMoveVertical getMoveVerticalImplementation() {
		return new IIDLCaDSEV3RMIMoveVerticalImplementation(this.robot,steuerung);
	}

	/**
	 * 
	 * @param ClassName
	 * @return
	 */
	public InterfaceIIDLCaDSEV3RMIUltraSonic getUltraSonicImplementation() {
		return new IIDLCaDSEV3RMIUltraSonicImplementation(this.robot);
	}
}
