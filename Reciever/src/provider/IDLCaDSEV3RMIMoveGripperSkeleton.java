package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import interfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import management.RecieverManager;
import utilities.ParameterParser;

/**
 * Die Klasse ist verantwortlich für das Unmarshalling.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMIMoveGripperSkeleton implements InterfaceSkeleton {

	public final static String ServiceClass = "InterfaceIDLCaDSEV3RMIMoveGripper";

	private InterfaceIDLCaDSEV3RMIMoveGripper imp;

	/**
	 * Konstruktor
	 * 
	 */
	public IDLCaDSEV3RMIMoveGripperSkeleton(
			InterfaceIDLCaDSEV3RMIMoveGripper imp) {
		this.imp = imp;
	}

	public JsonObject handle(byte[] buffer, int length) {
		ParameterParser parser = new ParameterParser();

		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "Response");


		try (InputStream is = new ByteArrayInputStream(buffer, 0, length);
				JsonReader rdr = Json.createReader(is)) {

			JsonObject obj = rdr.readObject();
			String objektname = obj.getString("ObjectName");
			String robotername = objektname.split("\\.")[0];
			String className = objektname.split("\\.")[1];
			String methodName = obj.getString("FunctionName");
			response.add("ObjectName",objektname);
			response.add("FunctionName",methodName);
			
			if(robotername.equals(RecieverManager.namespace) ==false){
		//		throw new Exception("Wrong Robotname.");
			}

			if (!(className
					.equals(IDLCaDSEV3RMIMoveGripperSkeleton.ServiceClass))) {
				throw new Exception("Class not found.");
			} else {

				Map<Long, Object> parameterList = parser.parse(obj);

				switch (methodName) {
				case "openGripper":
					int Transaction_ID_openGripper = (Integer) parameterList
							.get(1L);

					int result_openGripper = this.imp
							.openGripper(Transaction_ID_openGripper);
					response.add("ReturnValue", result_openGripper);
					break;
				case "closeGripper":
					int Transaction_ID_closeGripper = (Integer) parameterList
							.get(1L);

					int result_closeGripper = this.imp
							.closeGripper(Transaction_ID_closeGripper);
					response.add("ReturnValue", result_closeGripper);
					break;
				case "isGripperClosed":

					int result_isGripperClosed = this.imp.isGripperClosed();
					response.add("ReturnValue", result_isGripperClosed);
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

}
