package Client;

import java.lang.reflect.Method;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

/**
 * Die Klasse wird von der Gui benachrichtigt, wenn sich etwas verändert.
 * 
 * @author wilhelm
 *
 */
public class Controller implements IIDLCaDSEV3RMIMoveGripper,
		IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical,
		IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer {

	/**
	 * Reflection: Alle Methoden von der Klasse Controller
	 */
	private Method[] methoden = null;

	/**
	 * Factory
	 */
	private StubFactory factory = null;

	/**
	 * Der Gripperstub ist verantwortlich für das Mahrshalling.
	 */
	private IGripperStub gripperstub = null;

	/**
	 * Konstruktor
	 */
	public Controller(FifoQueue fifo) {
		init(fifo);
	}

	@Override
	public void register(ICaDSRobotGUIUpdater arg0) {
		System.out.println("Register Methode");

	}

	@Override
	public void update(String arg0) {
		System.out.println("Update Methode");

	}

	@Override
	public int isUltraSonicOccupied() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentVerticalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception {
		Method passendeMethode = findeMethodenObject("closeGripper");
		gripperstub.handle(passendeMethode, arg0);
		return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		Method passendeMethode = findeMethodenObject("openGripper");
		gripperstub.handle(passendeMethode, arg0);
		return 0;
	}

	/**
	 * Initialisierung
	 * 
	 * @param fifo
	 *            eine Queue für die JsonDokumente
	 */
	private void init(FifoQueue fifo) {
		factory = new StubFactory(fifo);
		gripperstub = factory.getGripperStub();
	
		try {
			methoden = Class.forName("Client.Controller").getMethods();
			// Method[] methoden = new Controller().getClass().getMethods();
	
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sucht die passende Methode heraus.
	 * 
	 * @param methodenname
	 *            Name der Methode
	 * @return ein Method Objekt
	 */
	private Method findeMethodenObject(String methodenname) {
		for (Method methode : methoden) {

			if (methode.getName().equals(methodenname)) {
				System.out.println(methode.getName());
				return methode;
			}
		}
		System.err
				.println("Es konnte keine passender entfernter Methodenaufruf gefunden werden.");
		return null;
	}

	/**
	 * Programmstart
	 * 
	 * @param args
	 *            Kommandozeilenparameter
	 */
	public static void main(String[] args) {

		// Logik
		FifoQueue fifo = new FifoQueue();
		Controller c = new Controller(fifo);

		// Gui
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(c, c, c, c, c);
		gui.addService("TestService1");
		gui.addService("TestService2");

		// Startet den Sender um Nachrichten an den Server zu schicken
		Sender sender = new Sender(fifo);
		sender.start();
	}

}
