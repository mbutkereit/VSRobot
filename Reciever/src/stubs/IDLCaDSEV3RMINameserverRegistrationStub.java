package stubs;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import interfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;

import javax.json.JsonArrayBuilder;

/**
 * Die Klasse ist verantwortlich für das Marshalling.
 * 
 * @author wilhelm
 *
 */
public class IDLCaDSEV3RMINameserverRegistrationStub implements InterfaceIDLCaDSEV3RMINameserverRegistration {

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
	public IDLCaDSEV3RMINameserverRegistrationStub(FifoQueue fifo) {
		this.fifo = fifo;
		factory = Json.createBuilderFactory(null);
		builder = factory.createObjectBuilder();

		stubinterface = null;
		try {
			stubinterface = Class.forName("client.InterfaceIDLCaDSEV3RMINameserverRegistration");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	    public int registerService(String serviceName, String ip, int port) {
    
		builder.add("FunctionName", "registerService").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","String").add("value", serviceName));abuilder.add(factory.createObjectBuilder().add("position",2).add("type","String").add("value", ip));abuilder.add(factory.createObjectBuilder().add("position",3).add("type","int").add("value", port));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public int unregisterService(int serviceName) {
    
		builder.add("FunctionName", "unregisterService").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","int").add("value", serviceName));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return 0;
    }
    
    
    
    public String lookup(String serviceName) {
    
		builder.add("FunctionName", "lookup").add("Type", "Request");
		JsonArrayBuilder abuilder = Json.createArrayBuilder();
		abuilder.add(factory.createObjectBuilder().add("position",1).add("type","String").add("value", serviceName));
		builder.add("Parameter", abuilder);
		builder.add("ObjectName", stubinterface.getSimpleName());
		JsonObject object = builder.build();
		System.out
				.println("Objekt in die Queue gelegt: \n" + object.toString());
		fifo.enque(object);
		return null;
    }
    
    
    

}
