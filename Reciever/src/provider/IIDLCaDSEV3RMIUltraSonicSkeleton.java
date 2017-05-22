package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import interfaces.InterfaceIIDLCaDSEV3RMIUltraSonic;

/**
 * Die Klasse ist verantwortlich für das Unmarshalling.
 * 
 * @author wilhelm
 *
 */
public class IIDLCaDSEV3RMIUltraSonicSkeleton implements InterfaceSkeleton {

	public final static String ServiceClass = "InterfaceIDLCaDSEV3RMIUltraSonic";

	private InterfaceIIDLCaDSEV3RMIUltraSonic imp;

	/**
	 * Konstruktor
	 * 
	 */
	public IIDLCaDSEV3RMIUltraSonicSkeleton(
			InterfaceIIDLCaDSEV3RMIUltraSonic imp) {
		this.imp = imp;
	}

	public JsonObject handle(byte[] buffer, int length) {

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

			if (!(className
					.equals(IIDLCaDSEV3RMIUltraSonicSkeleton.ServiceClass))) {
				throw new Exception("Class not found.");
			} else {

				switch (methodName) {
				case "isUltraSonicOccupied":

					int result_isUltraSonicOccupied = this.imp
							.isUltraSonicOccupied();
					response.add("ReturnValue", result_isUltraSonicOccupied);
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
