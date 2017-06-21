package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import interfaces.InterfaceIIDLCaDSEV3RMIMoveVertical;
import management.RecieverManager;
import utilities.ParameterParser;

/**
 * Die Klasse ist verantwortlich für das Unmarshalling.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIMoveVerticalSkeleton implements InterfaceSkeleton {

	public final static String ServiceClass = "InterfaceIDLCaDSEV3RMIMoveVertical";

	private InterfaceIIDLCaDSEV3RMIMoveVertical imp;

	/**
	 * Konstruktor
	 * 
	 */
	public IIDLCaDSEV3RMIMoveVerticalSkeleton(
			InterfaceIIDLCaDSEV3RMIMoveVertical imp) {
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
			String className = objektname.split("\\.")[1];
			String methodName = obj.getString("FunctionName");
			response.add("ObjectName",objektname);
			response.add("FunctionName",methodName);
			
			String robotername = objektname.split("\\.")[0];
			if(robotername.equals(RecieverManager.namespace) == false ){
				//		throw new Exception("Wrong Robotname.");
			}

			if (!(className
					.equals(IIDLCaDSEV3RMIMoveVerticalSkeleton.ServiceClass))) {
				throw new Exception("Class not found.");
			} else {

				Map<Long, Object> parameterList = parser.parse(obj);

				switch (methodName) {
				case "moveVerticalToPercent":
					int paramInt1_moveVerticalToPercent = (Integer) parameterList
							.get(1L);
					int paramInt2_moveVerticalToPercent = (Integer) parameterList
							.get(2L);

					int result_moveVerticalToPercent = this.imp
							.moveVerticalToPercent(
									paramInt1_moveVerticalToPercent,
									paramInt2_moveVerticalToPercent);
					response.add("ReturnValue", result_moveVerticalToPercent);
					break;
				case "stop":
					int paramInt_stop = (Integer) parameterList.get(1L);

					int result_stop = this.imp.stop(paramInt_stop);
					response.add("ReturnValue", result_stop);
					break;
				case "getCurrentVerticalPercent":

					int result_getCurrentVerticalPercent = this.imp
							.getCurrentVerticalPercent();
					response.add("ReturnValue",
							result_getCurrentVerticalPercent);
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
