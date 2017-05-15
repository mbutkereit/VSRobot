package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

/**
 * Die Klasse ist verantwortlich für das Unmarshalling.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMIMoveGripperSkeleton {

	private final static String ServiceClass = "InterfaceIDLCaDSEV3RMIMoveGripper";
	
	/**
	 * Konstruktor
	 * 
	 * @param fifo
	 */
	public IDLCaDSEV3RMIMoveGripperSkeleton(FifoQueue fifo) {
		this.fifo = fifo;
		factory = Json.createBuilderFactory(null);
		builder = factory.createObjectBuilder();

		stubinterface = null;
		try {
			stubinterface = Class.forName("client.InterfaceIDLCaDSEV3RMIMoveGripper");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

					param1 = ((Integer) parameterList.get(Long.valueOf(0L)))
							.intValue();

					result = this.getImplementation().closeGripper(param1);

					break;
				case "openGripper":
					
					param1 = ((Integer) parameterList.get(Long.valueOf(0L)))
							.intValue();
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
	
	
	    public int openGripper(int Transaction_ID) {
		openGripper
    }
    
    public int closeGripper(int Transaction_ID) {
		closeGripper
    }
    
    public int isGripperClosed() {
		isGripperClosed
    }
    

}
