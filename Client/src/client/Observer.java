package client;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;

public class Observer implements ICaDSRobotGUIUpdater {
	
	/**
	 * Instanz der Gui
	 */
	private CaDSRobotGUISwing gui = null;

	/**
	 * Setter
	 * @param gui
	 */
	public void setGui(CaDSRobotGUISwing gui) {
		this.gui = gui;
	}

	@Override
	public void addService(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeService(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChoosenService(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChoosenService(String arg0, int arg1, int arg2, boolean arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGripperClosed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGripperOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHorizontalProgressbar(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUltraSonicFree() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUltraSonicNotDefined() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUltraSonicOccupied() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVerticalProgressbar(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}

}
