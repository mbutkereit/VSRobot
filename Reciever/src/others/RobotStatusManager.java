package others;

import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

/**
 * Die Klasse wird über den Status des Roboters benachrichtigt.
 * 
 * @author Wilhelm und Marvin
 *
 */
public class RobotStatusManager
		implements ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener {
	
	/**
	 * Konstante Gripper open
	 */
	public static final int GRIPPEROPEN=0;
	
	/**
	 * Konstante Gripperclosed
	 */
	public static final int GRIPPERCLOSED=1;

	/**
	 * Position vom Roboterarm horizontal.
	 */
	private int currentPositionH;

	/**
	 * Position vom Roboterarm vertikal.
	 */
	private int currentPositionV;

	/**
	 * Gibt an, ob der Gripper geschlossen ist.
	 */
	private int isGripperClosed;

	public RobotStatusManager() {
		super();
		this.currentPositionH = 0;
		this.currentPositionV = 0;
		this.isGripperClosed = 0;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public synchronized int getCurrentPositionH() {
		return currentPositionH;
	}

	/**
	 * Setter
	 * 
	 * @param currentPositionH
	 */
	public synchronized void setCurrentPositionH(int currentPositionH) {
		this.currentPositionH = currentPositionH;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public synchronized int getCurrentPositionV() {
		return currentPositionV;
	}

	/**
	 * Setter
	 * 
	 * @param currentPositionV
	 */
	public synchronized void setCurrentPositionV(int currentPositionV) {
		this.currentPositionV = currentPositionV;
	}

	@Override
	public void giveFeedbackByJSonTo(JSONObject feedback) {
		// wird nie aufgerufen ....glaub ich
	}

	@Override
	public void onStatusMessage(JSONObject status) {
		String state = (String) status.get("state");
		if (state.equals("horizontal")) {
			Long percent = (Long) status.get("percent");
			setCurrentPositionH(percent.intValue());
		} else {
			if (state.equals("vertical")) {
				Long percent = (Long) status.get("percent");
				setCurrentPositionV(percent.intValue());
			} else {
				String value = (String) status.get("value");
				if (value.equals("closed")) {
					setIsGripperClosed(GRIPPERCLOSED);
				} else {
					setIsGripperClosed(GRIPPEROPEN);
				}
			}

		}
	}

	public synchronized int getIsGripperClosed() {
		return isGripperClosed;
	}

	public synchronized void setIsGripperClosed(int isGripperClosed) {
		this.isGripperClosed = isGripperClosed;
	}

}
