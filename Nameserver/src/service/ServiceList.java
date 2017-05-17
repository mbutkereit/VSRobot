package service;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import nameserver.service42.UserList;

/**
 * Die Klasse speichert Dienste mit den zugehörigen Ports und IP-Adressen.
 * @author Marvin und Wilhelm
 *
 */
public class ServiceList {
	
	/**
	 * Hashmap für die Beziehung Dienstname und Adresse
	 */
	private Map<String, ServiceDefinition> services;

	/**
	 * statische Instanz
	 */
	private static ServiceList instance = null;

	/**
	 * Konstruktor
	 */
	protected ServiceList() {
		this.services = new HashMap<String, ServiceDefinition>();
	}

	/**
	 * Singleteon
	 * @return die einzige Instanz von der Serviceliste
	 */
	public static synchronized ServiceList getInstance() {
		if (instance == null) {
			instance = new ServiceList();
		}
		return instance;
	}

	/**
	 * Liefert die zugehörige IP-Adresse für einen Dienst.
	 * @param name Name des Dienstes
	 * @return IP-Adresse
	 */
	public InetAddress getIpFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getIp();
		}
		return null;
	}

	/**
	 * Liefert den zugehörigen Port für einen Dienst.
	 * @param name Name des Dienstes
	 * @return Portnummer
	 */
	public int getPortFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getPort();
		}
		return -1;
	}
	
	/**
	 * Fügt einen neuen Dienst zur Map hinzu.
	 * @param serviceName Name des Dientes
	 * @param ip IP des Dienstes
	 * @param port Portnummer 
	 */
	public void registerService(String serviceName, InetAddress ip, int port) {
		this.services.put(serviceName, new ServiceDefinition(ip, port));
		
		// add in observer
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "ServiceList");
		response.add("values", this.createServiceJsonArray());
		UserList.getInstance().sendToAllMessage(response.build().toString());
	}

	/**
	 * Enfernt einen Dienst aus der Map.
	 * @param name Name des Dienstes
	 */
	public void unregisterService(String name) {
		this.services.remove(name);
		
		// add in observer
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "ServiceList");
		response.add("values", this.createServiceJsonArray());
		UserList.getInstance().sendToAllMessage(response.build().toString());
	}
	
	/**
	 * Gibt alle Elemente aus dem Namespace zurück.
	 * @return Stringarray enhält die verschiedenen Namespaces
	 */
	public String[] getAllNamespaces(){
		Set<String> listOfNames = new HashSet<String>();
		for(Map.Entry<String, ServiceDefinition> entry : services.entrySet()) {
		    String key = entry.getKey();
		    if(key.contains(".")){
		    String[] serviceName = key.split("\\.");
		    if(serviceName.length > 0){
		    	listOfNames.add(serviceName[0]);
		    }
		    }
		}
		if(listOfNames.isEmpty()){
			return null;
		}
		String[] string = listOfNames.toArray(new String[listOfNames.size()]);
		return string;
	}
	
	/**
	 * Erstellt ein JsonArray, das alle Elemente der Map enthält.
	 * @return
	 */
	public JsonArrayBuilder createServiceJsonArray(){
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for(Map.Entry<String, ServiceDefinition> entry : services.entrySet()) {
		    String key = entry.getKey();
		    ServiceDefinition value = entry.getValue();
		    
		    JsonObjectBuilder response = Json.createObjectBuilder();
		    response.add("name", key);
		    response.add("ip", value.getIp().getHostAddress());
		    response.add("port", value.getPort());
		    
		    builder.add(response);
		 }
		return builder;
	}
	}
