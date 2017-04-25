package client;

import java.util.LinkedList;
import java.util.Queue;

import javax.json.JsonObject;

/**
 * Wrapper für eine Queue von JsonObjekten.
 * @author wilhelm
 *
 */
public class FifoQueue {
	
	/**
	 * einfache Java Util Queue
	 */
	private Queue<JsonObject> queue = null;
	
	/**
	 * Konstruktor init
	 */
	public FifoQueue(){
		queue= new LinkedList<JsonObject>();
	}
	
	/**
	 * Fügt ein Element der Queue hinzu.
	 */
	public synchronized void push(JsonObject objekt){
		queue.add(objekt);
	}
	
	/**
	 * Nimmt das zuersthinzugefügte Element aus der Queue.
	 * @return das zuersthinzugefügte Element
	 */
	public synchronized JsonObject pull(){
		while(isEmpty()){
			try {
				this.wait(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return queue.poll();
	}
	
	/**
	 * Gibt ob ein Element in der Queue vorhanden ist
	 * @return true or false
	 */
	private boolean isEmpty(){
		return queue.isEmpty();
	}

}
