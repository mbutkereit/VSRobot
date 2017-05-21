package alteFiles;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import consumer.FifoQueue;

/**
 * Die Klasse ist verantwortlich für das Marshalling.
 * 
 * @author wilhelm
 *
 */
public class MoveGripperStub implements InterfaceIIDLCaDSEV3RMIMoveGripper {

	/**
	 * Eine Queue für die JsonDokumente.
	 */
	private FifoQueue fifo = null;

	/**
	 * Interface ReflectionObjekt
	 */
	private Class<?> stubinterface = null;

	/**
	 * Builder zum Erstellen von Json-Nachrichten.
	 */
	private JsonObjectBuilder builder = null;

	/**
	 * Factory für Json
	 */
	private JsonBuilderFactory factory = null;

	/**
	 * Konstruktor
	 * 
	 * @param fifo
	 */
	public MoveGripperStub(FifoQueue fifo) {
		this.fifo = fifo;
		factory = Json.createBuilderFactory(null);
		builder = factory.createObjectBuilder();

		stubinterface = null;
		try {
			stubinterface = Class
					.forName("client.InterfaceIIDLCaDSEV3RMIMoveGripper");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int closeGripper(int parameter0) {
		builder.add("FunctionName", "closeGripper").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position", 0)
				.add("type", "int").add("value", parameter0));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
	}

	@Override
	public int isGripperClosed() {
		builder.add("FunctionName", "isGripperClosed").add("Type", "Request");
		builder.add("Parameter", Json.createArrayBuilder());
		builder.add("ObjectName", stubinterface.getName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
	}

	@Override
	public int openGripper(int parameter0) {
		builder.add("FunctionName", "openGripper").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position", 0)
				.add("type", "int").add("value", parameter0));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
	}

}
