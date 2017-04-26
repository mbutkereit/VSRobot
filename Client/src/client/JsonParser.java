package client;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

/**
 * Nicht in Benutzung
 * @author wilhelm
 *
 */
public class JsonParser {

	public JsonObject getJsonMessageOpenGripper() {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);

		return factory
				.createObjectBuilder()
				.add("Functionname", "openGripper")
				.add("Type", "Request")
				.add("Parameter",
						Json.createArrayBuilder().add(
								factory.createObjectBuilder()
										.add("position", 0).add("type", "int")
										.add("value", 0)).add(factory.createObjectBuilder()
										.add("position", 0).add("type", "int")
										.add("value", 0)))
				.add("ObjectName", "IDLCaDSEV�RMIMoveGripper").build();
	}

	//Nur zum Testen
	public static void main(String[] arsg) {
		JsonParser parser = new JsonParser();
		
		Controller ck = new Controller(new FifoQueue());
		
		System.out.println(ck.getClass().getName());

		JsonObject value =	parser.getJsonMessageOpenGripper();		
		System.out.println(value.toString());

	}
}
