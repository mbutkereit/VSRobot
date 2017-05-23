package implementation;

import org.cads.ev3.middleware.CaDSEV3RobotStudentImplementation;

/**
 * Die Klasse ist nur für die Ausführungen der Bewegung des Roboters
 * verantwortlich.
 * 
 * @author wilhelm
 *
 */
public class RoboterBewegung extends Thread {

	/**
	 * Instanz des Roboters
	 */
	CaDSEV3RobotStudentImplementation roboter = null;

	/**
	 * Gibt an welche Bewegung vom Roboter durchgeführt werden soll.
	 */
	Bewegung status ;

	/**
	 * Konstruktor
	 * 
	 * @param roboter
	 *            Instanz des Roboters
	 */
	public RoboterBewegung(CaDSEV3RobotStudentImplementation roboter) {
		this.roboter = roboter;
		this.status=Bewegung.STOPH;
	}

	/**
	 * Getter
	 * @return
	 */
	public synchronized Bewegung getStatus() {
		return status;
	}

	/**
	 * Setter
	 * @param status
	 */
	public synchronized void setStatus(Bewegung status) {
		this.status = status;
	}
	
	/**
	 * Stoppt die aktuelle vertikale Bewegung.
	 */
	public synchronized void stoppeAlleBwegungen() {
		setStatus(Bewegung.STOPV);
		roboter.stop_v();
		roboter.stop_h();
	}

	/**
	 * Stoppt die aktuelle vertikale Bewegung.
	 */
	public synchronized void stopV() {
		roboter.stop_v();
	}

	/**
	 * Stoppt die aktuelle horizontale Bewegung.
	 */
	public synchronized void stopH() {
		roboter.stop_h();
	}

	/**
	 * Führt eine Bewegung abhängig vom Status aus.
	 */
	public void run() {
		while (!isInterrupted()) {
			switch (status) {
			case LINKS:
				roboter.moveLeft();
				break;
			case RECHTS:
				roboter.moveRight();
				break;
			case HOCH:
				roboter.moveUp();
				break;
			case RUNTER:
				roboter.moveDown();
				break;
			case STOPH:
				roboter.stop_h();
				break;
			case STOPV:
				roboter.stop_v();
				break;
			default:
				break;
			}

		}
	}
}
