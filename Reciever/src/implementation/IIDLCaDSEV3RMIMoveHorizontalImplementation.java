package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveHorizontal;

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

	public IIDLCaDSEV3RMIMoveHorizontalImplementation(
			CaDSEV3RobotStudentImplementation roboter,BewegungsSteuerung steuerung ) {
		this.steuerung=steuerung;
		// this.call = roboter;
	}

	public int moveHorizontalToPercent(int paramInt1, int paramInt2) {
		steuerung.setWertH(paramInt2);
		steuerung.setBew(Bewegung.HORIZONTAL);
		System.out.println("Bewege im Horizontalen zu "+paramInt2+".");
		return 0;
	}

	public int stop(int paramInt) {
		steuerung.setBew(Bewegung.STOP);
		return 0;
	}

	public int getCurrentHorizontalPercent() {
		return 0;
	}
}
