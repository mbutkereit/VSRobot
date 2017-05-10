package dienste;

public abstract interface IIDLCaDSEV3RMIMoveVertical
{
  public abstract int moveVerticalToPercent(int paramInt1, int paramInt2)
    throws Exception;
  
  public abstract int stop(int paramInt)
    throws Exception;
  
  public abstract int getCurrentVerticalPercent()
    throws Exception;
}
