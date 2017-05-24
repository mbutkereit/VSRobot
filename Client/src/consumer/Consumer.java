package consumer;

import java.util.LinkedList;

import gui.CaDSRobotGUISwing;

/**
 * Die Klasse stellt den Programmeinstieg zur Verfügung.
 * 
 * @author Wilhelm und Marvin
 *
 */
public class Consumer {

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length == 1) {
			KommunikationsThread.IP_ADRESSE = args[0];
		} else {
			KommunikationsThread.IP_ADRESSE = "127.0.0.1";
		}
		// Logik
		FifoQueue sendQueue = new FifoQueue();
		FifoQueue recieveQueue = new FifoQueue();
		Controller.roboternamenslisten = new LinkedList<String>();

		// Starte 5 Sender zum Kommunizieren als Threads
		KommunikationsThread[] senderThreadArray = new KommunikationsThread[5];
		for (int i = 0; i < senderThreadArray.length; i++) {
			senderThreadArray[i] = new KommunikationsThread(sendQueue,
					recieveQueue);
			senderThreadArray[i].start();
		}

		// Controller
		Controller c = new Controller(sendQueue);
		c.lookup();

		// Gui
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(c, c, c, c, c);
		gui.startGUIRefresh(1000);
		GuiUpdater updater = new GuiUpdater(recieveQueue, gui);
		updater.start();

		// Wartet auf Termination
		for (int i = 0; i < senderThreadArray.length; i++) {
			try {
				senderThreadArray[i].join();
			} catch (InterruptedException e) {
				System.err.println("Fehler beim Join");
				e.printStackTrace();
			}
		}

	}

}
