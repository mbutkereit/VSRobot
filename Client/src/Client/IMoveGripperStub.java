package client;

public interface IMoveGripperStub {

	/**
	 * Öffnet den Gripper
	 * 
	 * @param id
	 * @return
	 */
	public int openGripper(int id);

	/**
	 * Schließt den Gripper
	 * 
	 * @param id
	 * @return
	 */
	public int closeGripper(int id);

	/**
	 * Prüft ob der Gripper geschlossen ist.
	 * 
	 * @return
	 */
	public int isGripperClosed();

}
