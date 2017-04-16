package Client;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class GripperStub {

	/**
	 * enhält die JsonObjekte
	 */
	private FifoQueue queue = null;

	/**
	 * Konstruktor
	 * 
	 * @param queue
	 */
	public GripperStub(FifoQueue queue) {
		this.queue = queue;
	}

	/**
	 * Die Methode ereugt aus einem Methodenaufruf ein JsonObejct und legt
	 * dieses in die Queue.
	 * 
	 * @param methode
	 * @return
	 */
	public void handle(Method methode, int value) {
		// Methodenname
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObjectBuilder builder = factory.createObjectBuilder()
				.add("FunctionName", methode.getName()).add("Type", "Request");

		// Parameter

		Parameter[] param = methode.getParameters();

		JsonObject object = builder
				.add("Parameter",
						Json.createArrayBuilder().add(
								factory.createObjectBuilder()
										.add("position", 0)
										.add("type",
												param[0].getType().getName())
										.add("value", value)))
				.add("ObjectName", "IDLCaDSEV3RMIMoveGripper").build();

		System.out.println("Objekt in die Queue gelegt: \n"+ object.toString());
		queue.push(object);
	}
}
