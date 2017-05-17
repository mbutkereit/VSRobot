package others;
import implementation.IDLCaDSEV3RMIMoveGripperImplementation;
import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

public class RobotMoveGripperFactory {
	CaDSEV3RobotStudentImplementation robot;
	
	public RobotMoveGripperFactory(CaDSEV3RobotStudentImplementation robot){
		this.robot = robot;
	}
  public InterfaceIDLCaDSEV3RMIMoveGripper get(String ClassName){
	  return new IDLCaDSEV3RMIMoveGripperImplementation(this.robot);
  }
}
