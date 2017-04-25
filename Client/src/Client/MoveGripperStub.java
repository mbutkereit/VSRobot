package client;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

/**
 * Die Klasse ist verantwortlich für das Marshalling.
 * 
 * @author wilhelm
 *
 */
public class MoveGripperStub implements IIDLCaDSEV3RMIMoveGripper {

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
	private JsonObject buildMessage(String methodenname, int parameter) {
		// Methodenname
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObjectBuilder builder = factory.createObjectBuilder()
				.add("FunctionName", methodenname).add("Type", "Request");

		JsonObject object = builder
				.add("Parameter",
						Json.createArrayBuilder().add(
								factory.createObjectBuilder()
										.add("position", 0).add("type", "int")
										.add("value", parameter)))
				.add("ObjectName", "IDLCaDSEV3RMIMoveGripper").build();

		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		return object;
	}

	@Override
	public int closeGripper(int parameter) throws Exception {
		JsonObject object = buildMessage("closeGripper", parameter);
		fifo.push(object);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int openGripper(int parameter) throws Exception {
		JsonObject object = buildMessage("openGripper", parameter);
		fifo.push(object);
		return 0;
	}

}
