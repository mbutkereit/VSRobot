package alteFiles;

public interface InterfaceIIDLCaDSEV3RMIMoveGripper {

	/**
	 * �ffnet den Gripper
	 * 
	 * @param id
	 * @return
	 */
	public int openGripper(int id);

	/**
	 * Schlie�t den Gripper
	 * 
	 * @param id
	 * @return
	 */
	public int closeGripper(int id);

	/**
	 * Pr�ft ob der Gripper geschlossen ist.
	 * 
	 * @return
	 */
	public int isGripperClosed();

}
