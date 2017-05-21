package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveHorizontal;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIMoveHorizontalImplementation implements InterfaceIIDLCaDSEV3RMIMoveHorizontal {
	
	private CaDSEV3RobotStudentImplementation call = null;
	private static int percent = 50;
	
	public IIDLCaDSEV3RMIMoveHorizontalImplementation(
			CaDSEV3RobotStudentImplementation roboter) {
		this.call = roboter;
	}

    public int moveHorizontalToPercent(int paramInt1, int paramInt2) {
    	this.call.moveRight();
		return 0;
    }
    
    public int stop(int paramInt) {
		this.call.stop_h();
		return 0;
    }
    
    public int getCurrentHorizontalPercent() {
		return percent;
    }
    

}
