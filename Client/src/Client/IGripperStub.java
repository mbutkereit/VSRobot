package Client;

import java.lang.reflect.Method;

/**
 * Interface für einen GripperStub
 * 
 * @author wilhelm
 *
 */
public interface IGripperStub {

	/**
	 * Erstellt aus einem Methodenaufruf eine JsonNachricht
	 * 
	 * @param method
	 * @param parameter
	 */
	public void handle(Method method, int parameter);

}
