package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveHorizontal;
import others.RobotStatusManager;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIMoveHorizontalImplementation
		implements InterfaceIIDLCaDSEV3RMIMoveHorizontal {

	// private CaDSEV3RobotStudentImplementation call = null;
	private BewegungsSteuerung steuerung = null;

	/**
	 * Statusmanager
	 */
	private RobotStatusManager manager = null;

	public IIDLCaDSEV3RMIMoveHorizontalImplementation(
			CaDSEV3RobotStudentImplementation roboter,
			BewegungsSteuerung steuerung, RobotStatusManager manager) {
		this.steuerung = steuerung;
		this.manager=manager;
		// this.call = roboter;
	}

	public int moveHorizontalToPercent(int paramInt1, int paramInt2) {
		steuerung.setWertH(paramInt2);
		steuerung.setBew(Bewegung.HORIZONTAL);
		System.out.println("Bewege im Horizontalen zu " + paramInt2 + ".");
		return 0;
	}

	public int stop(int paramInt) {
		steuerung.setBew(Bewegung.STOP);
		return 0;
	}

	public int getCurrentHorizontalPercent() {
		return manager.getCurrentPositionH();
	}
}
