package client;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

/**
 * Die Klasse ist verantwortlich für das Marshalling.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMIMoveGripperStub implements InterfaceIDLCaDSEV3RMIMoveGripper {

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
	public IDLCaDSEV3RMIMoveGripperStub(FifoQueue fifo) {
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
	
	    public int openGripper(int Transaction_ID) {
    
		builder.add("FunctionName", "openGripper").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","int").add("value", Transaction_ID));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName",Controller.currentRoboter+"."+ stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public int closeGripper(int Transaction_ID) {
    
		builder.add("FunctionName", "closeGripper").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","int").add("value", Transaction_ID));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName",Controller.currentRoboter+"."+ stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public int isGripperClosed() {
    
		builder.add("FunctionName", "isGripperClosed").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		
		builder.add("Parameter", abuilder);
		builder.add("ObjectName",Controller.currentRoboter+"."+ stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    

}
