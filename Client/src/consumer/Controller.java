package consumer;

import java.util.List;

import dienste.IIDLCaDSEV3RMIMoveGripper;
import dienste.IIDLCaDSEV3RMIMoveHorizontal;
import dienste.IIDLCaDSEV3RMIMoveVertical;
import dienste.IIDLCaDSEV3RMIUltraSonic;
import gui.ICaDSRMIConsumer;
import gui.ICaDSRobotGUIUpdater;
import stubInterfaces.InterfaceIDLCaDSEV3RMIMoveGripper;
import stubInterfaces.InterfaceIDLCaDSEV3RMIMoveHorizontal;
import stubInterfaces.InterfaceIDLCaDSEV3RMIMoveVertical;
import stubInterfaces.InterfaceIDLCaDSEV3RMINameserverRegistration;
import stubInterfaces.InterfaceIDLCaDSEV3RMIUltraSonic;

/**
 * Die Klasse wird von der Gui benachrichtigt, wenn sich etwas ver�ndert.
 * 
 * @author wilhelm
 *
 */
public class Controller
		implements IIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveHorizontal,
		IIDLCaDSEV3RMIMoveVertical, IIDLCaDSEV3RMIUltraSonic, ICaDSRMIConsumer {

	/**
	 * Factory
	 */
	private StubFactory factory = null;

	/**
	 * Der Gripperstub ist verantwortlich für das Mahrshalling.
	 */
	private InterfaceIDLCaDSEV3RMIMoveGripper gripperstub = null;

	/**
	 * Stub für die vertikale Bewegung.
	 */
	private InterfaceIDLCaDSEV3RMIMoveVertical verticalstub = null;

	/**
	 * Stub für die horizontale Bewegung.
	 */
	private InterfaceIDLCaDSEV3RMIMoveHorizontal horizontalstub = null;

	/**
	 * Stub für den Ultraschall.
	 */
	private InterfaceIDLCaDSEV3RMIUltraSonic ultrasonicstub = null;

	/**
	 * 
	 */
	private InterfaceIDLCaDSEV3RMINameserverRegistration nameserverRegStub = null;

	/**
	 * 
	 */
	public static int horizontalPercent = 50;

	/**
	 * 
	 */
	public static int verticalPercent = 50;

	/**
	 * 
	 */
	public static int gripperstate = 0;

	/**
	 * 
	 */
	public static int ultrasonicState = 0;

	/**
	 * Der aktuell ausgewählte Roboter
	 */
	public static String currentRoboter = null;

	/**
	 * Alle auswählbaren Roboter
	 */
	public static List<String> roboternamenslisten = null;

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
		currentRoboter = arg0;

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
		nameserverRegStub = factory.getNameserverRegStub();
	}

	/**
	 * Führt einen Lookup beim Nameserver durch.
	 * 
	 * @throws Exception
	 */
	public void lookup()  {
		nameserverRegStub.lookup("ALL");
	}

}
