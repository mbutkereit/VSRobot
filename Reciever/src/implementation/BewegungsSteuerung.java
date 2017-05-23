package implementation;

import others.RobotStatusManager;

/**
 * Steuert die Bewegungen des Armes vom Roboter.
 * @author Wilhelm und Marvin
 *
 */
public class BewegungsSteuerung extends Thread {

	/**
	 * weiß über die Position des Armes Bescheid
	 */
	private RobotStatusManager manager;

	/**
	 * kann die Bewegungen durchführen
	 */
	private RoboterBewegung bewegung;

	/**
	 * Dieser Wert soll erreicht werden.
	 */
	private int wertV=0;

	/**
	 * Dieser Wert soll erreicht werden.
	 */
	private int wertH=0;

	/**
	 * Die aktuell auszuführende Bewegung.
	 */
	private Bewegung bew;

	/**
	 * innerer Zustand der Bwegungssteuerung
	 */
	private Zustand zustand;

	/**
	 * Gibt an, ob sich die auszuführende Aktion geändert hat.
	 */
	private boolean changed = false;

	/**
	 * Konstruktor
	 * 
	 * @param manager
	 * @param bewegung
	 */
	public BewegungsSteuerung(RobotStatusManager manager,
			RoboterBewegung bewegung) {
		this.manager = manager;
		this.bewegung = bewegung;
		this.bew=null;
		this.zustand=Zustand.ABGESCHLOSSEN;
	};

	public synchronized int getWertV() {
		return wertV;
	}

	public synchronized void setWertV(int wertV) {
		this.wertV = wertV;
	}

	public synchronized int getWertH() {
		return wertH;
	}

	public synchronized void setWertH(int wertH) {
		this.wertH = wertH;
	}

	/**
	 * Liefert den neuen auszuführenden Befehl
	 * 
	 * @return
	 */
	public synchronized Zustand getNeuerZustand() {
		Zustand z = Zustand.ABGESCHLOSSEN;
		switch (bew) {
		case STOP:
			z = Zustand.ABGESCHLOSSEN;
			break;
		case HORIZONTAL:
			z = Zustand.HORIZONTAL;
			break;
		case VERTIKAL:
			z = Zustand.VERTIKAL;
			break;
		default:
			break;
		}
		changed = false;
		return z;
	}

	public synchronized void setBew(Bewegung bew) {
		this.bew = bew;
		changed = true;
	}

	public void run() {
		while (!isInterrupted()) {
			switch (zustand) {
			case ABGESCHLOSSEN:
				bewegung.stoppeAlleBwegungen();
				if (changed) {
					zustand = getNeuerZustand();
				}
				break;
			case HORIZONTAL:
				int wert = manager.getCurrentPositionH();
				int werth= getWertH();
				if (wert == werth) {
					zustand = Zustand.ABGESCHLOSSEN;
				} else {
					if (wert < werth) {
						if (bewegung.getStatus() != Bewegung.LINKS) {
							System.out.println("Führe eine Bewegung nach Links aus.");
							bewegung.setStatus(Bewegung.LINKS);
							bewegung.stopH();
							bewegung.stopV();
						}
					} else {
						if (bewegung.getStatus() != Bewegung.RECHTS) {
							bewegung.setStatus(Bewegung.RECHTS);
							bewegung.stopH();
							bewegung.stopV();
						}
					}
					if (changed) {
						zustand = getNeuerZustand();
					}
				}
				break;
			case VERTIKAL:
				int wert2 = manager.getCurrentPositionV();
				int wertv = getWertV();
				if (wert2 < wertv+2 && wert2 > wertv-2) {
					zustand = Zustand.ABGESCHLOSSEN;
				} else {
					if (wert2 < wertv) {
						if (bewegung.getStatus() != Bewegung.HOCH) {
							bewegung.setStatus(Bewegung.HOCH);
							bewegung.stopH();
							bewegung.stopV();
						}
					} else {
						if (bewegung.getStatus() != Bewegung.RUNTER) {
							bewegung.setStatus(Bewegung.RUNTER);
							bewegung.stopH();
							bewegung.stopV();
						}
					}
					if (changed) {
						zustand = getNeuerZustand();
					}
				}
				break;
			default:
				break;
			}

		}
	}

}
