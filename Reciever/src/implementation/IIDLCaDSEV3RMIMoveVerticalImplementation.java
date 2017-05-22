package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveVertical;

/**
 * Die Klasse ist verantwortlich für die Implementierung.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIMoveVerticalImplementation implements InterfaceIIDLCaDSEV3RMIMoveVertical {
	
	private CaDSEV3RobotStudentImplementation call = null;
	private static int percent = 50;
	
	public IIDLCaDSEV3RMIMoveVerticalImplementation(
			CaDSEV3RobotStudentImplementation roboter) {
		this.call = roboter;
	}

    public int moveVerticalToPercent(int paramInt1, int paramInt2) {
    	if(percent==50){
    	 	call.moveUp();
    	 	percent=51;
    	}else{
    		call.moveDown();
    		percent=50;
    	}
   
		return 0;
    }
    
    public int stop(int paramInt) {
		call.stop_v();
		return 0;
    }
    
    public int getCurrentVerticalPercent() {
		return percent;
    }
    

}
