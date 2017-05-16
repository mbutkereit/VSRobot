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
public class IIDLCaDSEV3RMIMoveVerticalStub implements InterfaceIIDLCaDSEV3RMIMoveVertical {

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
	public IIDLCaDSEV3RMIMoveVerticalStub(FifoQueue fifo) {
		this.fifo = fifo;
		factory = Json.createBuilderFactory(null);
		builder = factory.createObjectBuilder();

		stubinterface = null;
		try {
			stubinterface = Class.forName("client.InterfaceIIDLCaDSEV3RMIMoveVertical");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	    public int moveVerticalToPercent(int paramInt1, int paramInt2) {
    
		builder.add("FunctionName", "moveVerticalToPercent").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","int").add("value", paramInt1));abuilder.add(factory.createObjectBuilder().add("position",2).add("type","int").add("value", paramInt2));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
//		System.out
//				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public int stop(int paramInt) {
    
		builder.add("FunctionName", "stop").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","int").add("value", paramInt));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
//		System.out
//				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public int getCurrentVerticalPercent() {
    
		builder.add("FunctionName", "getCurrentVerticalPercent").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
//		System.out
//				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    

}
