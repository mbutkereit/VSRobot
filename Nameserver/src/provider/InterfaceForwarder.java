package provider;


import javax.json.JsonObject;
/**
 * Interface
 * @author wilhelm und Marvin
 *
 */
public interface InterfaceForwarder {
	/**
	 * Verarbeiten einer Nachricht.
	 * @param buffer Inhalt
	 * @param length Länge
	 * @return Antwort für den Client
	 */
	public JsonObject handle(byte[] buffer, int length);
}
