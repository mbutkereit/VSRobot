package gui;

import client.ICaDSRMIConsumer;
import dienste.IIDLCaDSEV3RMIMoveGripper;
import dienste.IIDLCaDSEV3RMIMoveHorizontal;
import dienste.IIDLCaDSEV3RMIMoveVertical;
import dienste.IIDLCaDSEV3RMIUltraSonic;

public class CaDSRobotGUISwing
implements ICaDSRobotGUIUpdater
{
private CaDSRobotControlGUISwing controller;
private Thread refreshThread;
private final ICaDSRMIConsumer consumer;

public CaDSRobotGUISwing(ICaDSRMIConsumer paramICaDSRMIConsumer, IIDLCaDSEV3RMIMoveGripper paramIIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveVertical paramIIDLCaDSEV3RMIMoveVertical, IIDLCaDSEV3RMIMoveHorizontal paramIIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIUltraSonic paramIIDLCaDSEV3RMIUltraSonic)
{
  this.consumer = paramICaDSRMIConsumer;
  this.refreshThread = null;
  this.controller = new CaDSRobotControlGUISwing(paramICaDSRMIConsumer, paramIIDLCaDSEV3RMIMoveGripper, paramIIDLCaDSEV3RMIMoveVertical, paramIIDLCaDSEV3RMIMoveHorizontal);
  this.controller.setDefaultCloseOperation(3);
  this.controller.setVisible(true);
}

public synchronized void startGUIRefresh(int paramInt)
{
  if ((paramInt > 99) && (this.refreshThread == null))
  {
    this.refreshThread = new CaDSRobotGUIRefresh(paramInt, this, this.consumer);
    this.refreshThread.setDaemon(true);
    this.refreshThread.setName("CaDSRobotGUISwing_RefreshThread");
    this.refreshThread.start();
  }
  else
  {
    System.out.println("GUI refresh time is not provided! Must be 100 or higher. Is= " + paramInt);
  }
}

public synchronized void stopGUIRefresh()
{
  if (this.refreshThread != null)
  {
    this.refreshThread.interrupt();
    this.refreshThread = null;
  }
}

public void addService(String paramString)
{
  this.controller.addService(paramString);
}

public void removeService(String paramString)
{
  this.controller.removeService(paramString);
}

public void setChoosenService(String paramString)
{
  this.controller.setChoosenService(paramString);
}

public void setChoosenService(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
{
  this.controller.setChoosenService(paramString, paramInt1, paramInt2, paramBoolean);
}

public void setGripperClosed()
{
  this.controller.setGripperClosed();
}

public void setGripperOpen()
{
  this.controller.setGripperOpen();
}

public void setUltraSonicOccupied()
{
  this.controller.setUltraSonicSensorOccupied();
}

public void setUltraSonicFree()
{
  this.controller.setUltraSonicSensorFree();
}

public void setUltraSonicNotDefined()
{
  this.controller.setUltraSonicSensorNotDefined();
}

public void setVerticalProgressbar(int paramInt)
{
  this.controller.setVerticalProgressbar(paramInt);
}

public void setHorizontalProgressbar(int paramInt)
{
  this.controller.setHorizontalProgressbar(paramInt);
}

public void teardown()
{
  this.controller.teardown();
}
}
