package client;



import dienste.IIDLCaDSEV3RMIMoveGripper;
import dienste.IIDLCaDSEV3RMIMoveHorizontal;
import dienste.IIDLCaDSEV3RMIMoveVertical;
import dienste.IIDLCaDSEV3RMIUltraSonic;
import gui.CaDSRobotGUISwing;
import gui.ICaDSRobotGUIUpdater;


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
	private InterfaceIDLCaDSEV3RMIMoveGripper gripperstub = null;
	
	/**
	 * Stub für die vertikale Bewegung.
	 */
	private InterfaceIIDLCaDSEV3RMIMoveVertical verticalstub = null;
	
	/**
	 * Stub für die horizontale Bewegung.
	 */
	private InterfaceIIDLCaDSEV3RMIMoveHorizontal horizontalstub = null;
	
	/**
	 * Stub für den Ultraschall.
	 */
	private InterfaceIIDLCaDSEV3RMIUltraSonic ultrasonicstub = null;
	
	/**
	 * 
	 */
	private int horizontalPercent = 50;
	
	/**
	 * 
	 */
	private int verticalPercent = 50;
	
	/**
	 * 
	 */
	private int gripperstate = 0;
	
	/**
	 * 
	 */
	private int ultrasonicState = 0;

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
		ultrasonicstub.isUltraSonicOccupied();
		return ultrasonicState;
	}

	@Override
	public int getCurrentVerticalPercent() throws Exception {
		verticalstub.getCurrentVerticalPercent();
		return verticalPercent;
	}

	@Override
	public int moveVerticalToPercent(int arg0, int arg1) throws Exception {
		verticalstub.moveVerticalToPercent(arg0, arg1);
		return 0;
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		horizontalstub.getCurrentHorizontalPercent();
		return horizontalPercent;
	}

	@Override
	public int moveHorizontalToPercent(int arg0, int arg1) throws Exception {
		horizontalstub.moveHorizontalToPercent(arg0, arg1);
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		verticalstub.stop(arg0);
		horizontalstub.stop(arg0);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return gripperstate;
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
		verticalstub = factory.getVerticalStub();
		horizontalstub = factory.getHorizontalStub();
		ultrasonicstub = factory.getUltraSonicStub();
		// Gui
		gui = new CaDSRobotGUISwing(this, this, this, this, this);
		gui.setGripperClosed();
		gui.setVerticalProgressbar(20);
		gui.setHorizontalProgressbar(20);
		gui.addService("TestService1");
		gui.addService("TestService2");
		gui.addService("TestServiece3");
		gui.removeService("TestService2");
		gui.startGUIRefresh(5000);
	}

	/**
	 * Programmstart
	 * 
	 * @param args
	 *            Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		// Logik
		FifoQueue sendQueue = new FifoQueue();
		FifoQueue recieveQueue = new FifoQueue();
		Controller c = new Controller(sendQueue);
		
		// Reciever
		Reciever reciever = new Reciever(recieveQueue);

		// Startet den Sender um Nachrichten an den Server zu schicken
		Sender sender = new Sender(sendQueue,reciever);
		
		reciever.start();
		sender.start();

		try {
			sender.join();
			reciever.join();
		} catch (InterruptedException e) {
			System.err.println("Main Thread interrupted while waiting");
			e.printStackTrace();
		}

	}
}
