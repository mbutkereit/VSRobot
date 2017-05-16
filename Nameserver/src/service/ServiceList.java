package service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import fi.iki.elonen.NanoWSD.WebSocketFrame;
import nameserver.service42.UserList;

public class ServiceList {
	private Map<String, ServiceDefinition> services;

	private static ServiceList instance = null;

	protected ServiceList() {
		this.services = new HashMap<String, ServiceDefinition>();
	}

	public static synchronized ServiceList getInstance() {
		if (instance == null) {
			instance = new ServiceList();
		}
		return instance;
	}

	public InetAddress getIpFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getIp();
		}
		return null;
	}

	public int getPortFromService(String name) {
		ServiceDefinition service = this.services.get(name);
		if (service != null) {
			return service.getPort();
		}
		return -1;
	}
	
	public void registerService(String serviceName, InetAddress ip, int port) {
		this.services.put(serviceName, new ServiceDefinition(ip, port));
		
		// add in observer
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "ServiceList");
		response.add("values", this.createServiceJsonArray());
		UserList.getInstance().sendToAllMessage(response.build().toString());
	}

	public void unregisterService(String name) {
		this.services.remove(name);
		
		// add in observer
		JsonObjectBuilder response = Json.createObjectBuilder();
		response.add("Type", "ServiceList");
		response.add("values", this.createServiceJsonArray());
		UserList.getInstance().sendToAllMessage(response.build().toString());
	}
	
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
