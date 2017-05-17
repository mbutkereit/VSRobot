package provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import interfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;
import utilities.ParameterParser;

/**
 * Die Klasse ist verantwortlich für das Unmarshalling.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMINameserverRegistrationSkeleton implements InterfaceForwarder {

	public final static String ServiceClass = "InterfaceIDLCaDSEV3RMINameserverRegistration";

	private InterfaceIDLCaDSEV3RMINameserverRegistration imp;

	/**
	 * Konstruktor
	 * 
	 */
	public IDLCaDSEV3RMINameserverRegistrationSkeleton(InterfaceIDLCaDSEV3RMINameserverRegistration imp) {
		this.imp = imp;
	}

	public JsonObject handle(byte[] buffer, int length) {
		ParameterParser parser = new ParameterParser();

		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "Response");

		try (InputStream is = new ByteArrayInputStream(buffer, 0, length); JsonReader rdr = Json.createReader(is)) {

			JsonObject obj = rdr.readObject();
			String className = obj.getString("ObjectName");
			String methodName = obj.getString("FunctionName");

			if (!(className.equals(IDLCaDSEV3RMINameserverRegistrationSkeleton.ServiceClass))) {
				throw new Exception("Class not found.");
			} else {

				Map<Long, Object> parameterList = parser.parse(obj);

				switch (methodName) {
				case "registerService":
					String serviceName_registerService = (String) parameterList.get(1L);
					String ip_registerService = (String) parameterList.get(2L);
					int port_registerService = (Integer) parameterList.get(3L);

					int result_registerService = this.imp.registerService(serviceName_registerService,
							ip_registerService, port_registerService);
					response.add("ReturnValue", result_registerService);
					break;
				case "unregisterService":
					int serviceName_unregisterService = (Integer) parameterList.get(1L);

					int result_unregisterService = this.imp.unregisterService(serviceName_unregisterService);
					response.add("ReturnValue", result_unregisterService);
					break;
				case "lookup":
					String serviceName_lookup = (String) parameterList.get(1L);

					String result_lookup = this.imp.lookup(serviceName_lookup);
					response.add("ReturnValue", result_lookup);
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
