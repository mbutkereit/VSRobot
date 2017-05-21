package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIUltraSonic;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIUltraSonicImplementation implements InterfaceIIDLCaDSEV3RMIUltraSonic {
	
	private static int isoccupied = 0;
	private CaDSEV3RobotStudentImplementation call = null;
	
	public IIDLCaDSEV3RMIUltraSonicImplementation(
			CaDSEV3RobotStudentImplementation roboter) {
		this.call = roboter;
	}

    public int isUltraSonicOccupied() {
		return isoccupied;
    }
    

}
