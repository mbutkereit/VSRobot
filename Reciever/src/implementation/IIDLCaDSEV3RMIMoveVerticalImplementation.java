package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveVertical;
import others.RobotStatusManager;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIMoveVerticalImplementation implements InterfaceIIDLCaDSEV3RMIMoveVertical {
	
//	private CaDSEV3RobotStudentImplementation call = null;
	private BewegungsSteuerung steuerung = null;
	
	/**
	 * Statusmanager
	 */
	private RobotStatusManager manager = null;
	
	
	public IIDLCaDSEV3RMIMoveVerticalImplementation(
			CaDSEV3RobotStudentImplementation roboter,BewegungsSteuerung steuerung,RobotStatusManager manager ) {
		this.steuerung=steuerung;
		this.manager=manager;
//		this.call = roboter;
	}

    public int moveVerticalToPercent(int paramInt1, int paramInt2) {
		steuerung.setWertV(paramInt2);
		steuerung.setBew(Bewegung.VERTIKAL);
		System.out.println("Bewege im Vertikalen zu"+paramInt2);
		return 0;
    }
    
    public int stop(int paramInt) {
		steuerung.setBew(Bewegung.STOP);
		return 0;
    }
    
    public int getCurrentVerticalPercent() {
		return manager.getCurrentPositionV();
    }
    

}
