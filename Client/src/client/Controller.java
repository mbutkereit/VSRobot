package client;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

/**
 * Die Klasse wird von der Gui benachrichtigt, wenn sich etwas ver�ndert.
 * 
 * @author wilhelm
 *
 */
public class Controller implements IIDLCaDSEV3RMIMoveGripper,
		IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical,
		IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer {
	/**
	 * Instanz der Gui
	 */
	private CaDSRobotGUISwing gui = null;

	/**
	 * Factory
	 */
	private StubFactory factory = null;

	/**
	 * Der Gripperstub ist verantwortlich f�r das Mahrshalling.
	 */
	private IMoveGripperStub gripperstub = null;

	/**
	 * Konstruktor
	 */
	public Controller(FifoQueue fifo) {
		init(fifo);
	}

	@Override
	public void register(ICaDSRobotGUIUpdater observer) {
		System.out.println("New Observer");
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
		System.out.println("Diese Methode wird wirklich acu benutzt!");
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
		System.out.println("Diese Methode wird nie von der Gui aufgerufen!");
		return 0;
	}

	@Override
	public int closeGripper(int arg0) throws Exception {
		gripperstub.closeGripper(arg0);
		return 10;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		gripperstub.openGripper(arg0);
		return 0;
	}

	/**
	 * Initialisierung
	 * 
	 * @param fifo
	 *            eine Queue f�r die JsonDokumente
	 */
	private void init(FifoQueue fifo) {
		factory = new StubFactory(fifo);
		gripperstub = factory.getGripperStub();
		// Gui
		gui = new CaDSRobotGUISwing(this, this, this, this, this);
		gui.setGripperClosed();
		gui.setVerticalProgressbar(50);
		gui.setHorizontalProgressbar(50);
		gui.addService("TestService1");
		gui.addService("TestService2");
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

		// Startet den Sender um Nachrichten an den Server zu schicken
		Sender sender = new Sender(fifo);
		sender.start();

		// Reciever
		Reciever reciever = new Reciever();
		reciever.start();

		try {
			sender.join();
			reciever.join();
		} catch (InterruptedException e) {
			System.err.println("Main Thread interrupted while waiting");
			e.printStackTrace();
		}

	}

}
