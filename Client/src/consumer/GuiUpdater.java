package consumer;

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

	/**
	 * Entnimmt Nachrichten aus der Queue und updatet entsprechend die GUI.
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {

			JsonObject object = fifo.deque();
			System.out.println(object.toString());
			String functionname = "";
			String robotname = null;

			if (object.containsKey("FunctionName")) {
				functionname = object.getString("FunctionName");
			}
			if (object.containsKey("ObjectName")) {
				robotname = object.getString("ObjectName").split("\\.")[0];
			}

			if (robotname != null
					&& robotname.equalsIgnoreCase("null") == false) {
				gui.setChoosenService(robotname);
			}

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
			case "lookup":
				String returnWert = object.getString("ReturnValue");
				String[] namespace = returnWert.split("\\,");
				for (String name : namespace) {
					if (name != null && !(name.equals(""))) {
						if (Controller.roboternamenslisten
								.contains(name) == false) {
							Controller.roboternamenslisten.add(name);
							System.out.println("Registrierung:::::" + name);
							gui.addService(name);
						}

					}
				}
				break;
			}

		}

	}

}
