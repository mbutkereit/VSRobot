package provider;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

import others.RobotMoveGripperFactory;
import utilities.ParameterParser;


public class MoveGripperSkeleton implements InterfaceSkeleton {
	private final static String ServiceClass = "InterfaceIDLCaDSEV3RMIMoveGripper";

	private CaDSEV3RobotStudentImplementation robot;
	
	public MoveGripperSkeleton(CaDSEV3RobotStudentImplementation robot){
		this.robot = robot;
	}
	
	public JsonObject handle(byte[] buffer, int length) {
		ParameterParser parser = new ParameterParser();
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "Response");

		try (InputStream is = new ByteArrayInputStream(buffer, 0, length);
				JsonReader rdr = Json.createReader(is)) {

			JsonObject obj = rdr.readObject();

			String className = obj.getString("ObjectName");
			
			String methodName = obj.getString("FunctionName");

			if (!(className.equals(MoveGripperSkeleton.ServiceClass))) {
				throw new Exception("Class not found.");
			} else {
				
				Map<Long, Integer> parameterList = parser.parse(obj);
				int param1 = 0;
				int result = 0;
				
				switch (methodName) {
				case "closeGripper":
					result = this.getImplementation().closeGripper(param1);
					break;
				case "openGripper":
					result = this.getImplementation().openGripper(param1);
					break;
				case "isGripperClosed":
					result = this.getImplementation().isGripperClosed();
					response.add("ReturnValue", "" + result);
					break;
				default:
					throw new Exception("[ERROR] Function not supported ");

				}
			}
		} catch (Exception e) {
			response.add("Type", "Response");
			response.add("Exception", "ClassNotFoundException");
		}

		return response.build();
	}

	/** todo better solution **/
	public IIDLCaDSEV3RMIMoveGripper getImplementation() {
		RobotMoveGripperFactory factory = new RobotMoveGripperFactory(robot);
		IIDLCaDSEV3RMIMoveGripper gripper = factory.get(MoveGripperSkeleton.ServiceClass);
		return gripper;
	}
	
}
