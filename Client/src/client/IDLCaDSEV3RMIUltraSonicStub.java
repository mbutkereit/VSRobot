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
public class IDLCaDSEV3RMIUltraSonicStub implements InterfaceIDLCaDSEV3RMIUltraSonic {

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
	public IDLCaDSEV3RMIUltraSonicStub(FifoQueue fifo) {
		this.fifo = fifo;
		factory = Json.createBuilderFactory(null);
		builder = factory.createObjectBuilder();

		stubinterface = null;
		try {
			stubinterface = Class.forName("client.InterfaceIDLCaDSEV3RMIUltraSonic");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	    public int isUltraSonicOccupied() {
    
		builder.add("FunctionName", "isUltraSonicOccupied").add("Type", "Request");
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
