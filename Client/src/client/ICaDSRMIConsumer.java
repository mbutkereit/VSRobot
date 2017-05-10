package client;

import gui.ICaDSRobotGUIUpdater;

public abstract interface ICaDSRMIConsumer
{
  public abstract void register(ICaDSRobotGUIUpdater paramICaDSRobotGUIUpdater);
  
  public abstract void update(String paramString);
}
