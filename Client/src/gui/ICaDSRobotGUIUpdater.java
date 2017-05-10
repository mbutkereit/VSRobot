package gui;

public abstract interface ICaDSRobotGUIUpdater
{
  public abstract void addService(String paramString);
  
  public abstract void removeService(String paramString);
  
  public abstract void setChoosenService(String paramString);
  
  public abstract void setChoosenService(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void setGripperClosed();
  
  public abstract void setGripperOpen();
  
  public abstract void setUltraSonicOccupied();
  
  public abstract void setUltraSonicFree();
  
  public abstract void setUltraSonicNotDefined();
  
  public abstract void setVerticalProgressbar(int paramInt);
  
  public abstract void setHorizontalProgressbar(int paramInt);
  
  public abstract void teardown();
}
