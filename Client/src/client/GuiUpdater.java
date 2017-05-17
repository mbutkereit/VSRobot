package client;

import javax.json.JsonObject;

import gui.CaDSRobotGUISwing;

/**
 * Die Klasse aktualisiert die Werte für die GUI.
 * 
 * @author Wilhelm und Marvin
 *
 */
public class GuiUpdater extends Thread {

	/**
	 * Eine Queue für die JsonObjekte.
	 * 
	 * @param fifo
	 */
	private FifoQueue fifo = null;

	/**
	 * Instanz der Gui
	 */
	private CaDSRobotGUISwing gui = null;

	/**
	 * Konstruktor
	 * 
	 * @param fifo
	 */
	public GuiUpdater(FifoQueue fifo, CaDSRobotGUISwing gui) {
		this.fifo = fifo;
		this.gui = gui;
	}

	@Override
	public void run() {
		JsonObject object = fifo.deque();
		String functionname = object.getString("FunctionName");
		String robotname = object.getString("ObjectName").split(".")[0];
		gui.setChoosenService(robotname);
		int returnvalue = 0;

		switch (functionname) {
		case "isGripperClosed":
			returnvalue = object.getInt("ReturnValue");
			if (returnvalue == 1) {
				gui.setGripperClosed();
			} else {
				gui.setGripperOpen();
			}
			Controller.gripperstate = returnvalue;
			break;
		case "getCurrentHorizontalPercent":
			returnvalue = object.getInt("ReturnValue");
			gui.setHorizontalProgressbar(returnvalue);
			Controller.horizontalPercent = returnvalue;
			break;
		case "getCurrentVerticalPercent":
			returnvalue = object.getInt("ReturnValue");
			gui.setVerticalProgressbar(returnvalue);
			Controller.verticalPercent = returnvalue;
			break;
		case "isUltraSonicOccupied":
			returnvalue = object.getInt("ReturnValue");
			if (returnvalue == 1) {
				gui.setUltraSonicOccupied();
			} else {
				gui.setUltraSonicFree();
			}
			Controller.ultrasonicState = returnvalue;
			break;
		}

	}

}
