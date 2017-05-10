package dienste;

public abstract interface IIDLCaDSEV3RMIMoveGripper
{
  public abstract int closeGripper(int paramInt)
    throws Exception;
  
  public abstract int openGripper(int paramInt)
    throws Exception;
  
  public abstract int isGripperClosed()
    throws Exception;
}
