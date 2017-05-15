package others;
import implementation.RobotImplementationMoveGripper;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

public class RobotMoveGripperFactory {
	CaDSEV3RobotStudentImplementation robot;
	
	public RobotMoveGripperFactory(CaDSEV3RobotStudentImplementation robot){
		this.robot = robot;
	}
  public IIDLCaDSEV3RMIMoveGripper get(String ClassName){
	  return new RobotImplementationMoveGripper(this.robot);
  }
}
