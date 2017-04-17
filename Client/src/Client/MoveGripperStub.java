package Client;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Die Klasse ist verantwortlich für das Marshalling.
 * 
 * @author wilhelm
 *
 */
public class MoveGripperStub implements IGripperStub {

	/**
	 * Eine Queue für die JsonDokumente.
	 */
	private FifoQueue fifo = null;

	/**
	 * Konstruktor
	 * 
	 * @param fifo
	 */
	public MoveGripperStub(FifoQueue fifo) {
		this.fifo = fifo;
	}

	/**
	 * Die Methode ereugt aus einem Methodenaufruf ein JsonObejct und legt
	 * dieses in die Queue.
	 * 
	 * @param methode
	 *            Die Methode die in einem anderen Prozess aufgerufen werden
	 *            soll
	 */
	@Override
	public void handle(Method method, int parameter) {
		// Methodenname
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObjectBuilder builder = factory.createObjectBuilder()
				.add("FunctionName", method.getName()).add("Type", "Request");

		// Parameter

		Parameter[] param = method.getParameters();

		JsonObject object = builder
				.add("Parameter",
						Json.createArrayBuilder().add(
								factory.createObjectBuilder()
										.add("position", 0)
										.add("type",
												param[0].getType().getName())
										.add("value", parameter)))
				.add("ObjectName", "IDLCaDSEV3RMIMoveGripper").build();

		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());

		fifo.push(object);
	}

}
