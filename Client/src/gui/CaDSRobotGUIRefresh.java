package gui;

import java.util.concurrent.TimeUnit;

import consumer.Controller;


public class CaDSRobotGUIRefresh
  extends Thread
{
  private final int refreshRateInMS;
  private final CaDSRobotGUISwing gui;
  private final Controller consumer;
  private int oldValueHorizontal;
  private int oldValueVertical;
  private int oldValueGripper;
  private int oldValueUltraSonic;
  
  CaDSRobotGUIRefresh(int paramInt, CaDSRobotGUISwing paramCaDSRobotGUISwing, ICaDSRMIConsumer paramICaDSRMIConsumer)
  {
    this.refreshRateInMS = paramInt;
    this.gui = paramCaDSRobotGUISwing;
    this.consumer = ((Controller)paramICaDSRMIConsumer);
    this.oldValueGripper = -2;
    this.oldValueHorizontal = -2;
    this.oldValueUltraSonic = -2;
    this.oldValueVertical = -2;
  }
  
  public void run()
  {
    long l1 = 0L;
    for (long l2 = 0L; !interrupted(); l2 = System.currentTimeMillis())
    {
      try
      {
        TimeUnit.MILLISECONDS.sleep(this.refreshRateInMS - (l2 - l1));
        l1 = System.currentTimeMillis();
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      try
      {
        int i = this.consumer.getCurrentVerticalPercent();
        if ((i > -1) && (i <= 100) && (i != this.oldValueVertical)) {
          this.gui.setVerticalProgressbar(i);
        } else {
        }
      }
      catch (Exception localException1)
      {
      }
      try
      {
        int j = this.consumer.getCurrentHorizontalPercent();
        if ((j > -1) && (j <= 100) && (j != this.oldValueHorizontal)) {
          this.gui.setHorizontalProgressbar(j);
        } else {
        }
      }
      catch (Exception localException2)
      {
      }
      try
      {
        int k = this.consumer.isGripperClosed();
        if (k != this.oldValueGripper) {
          switch (k)
          {
          case 0: 
            this.gui.setGripperOpen();
            break;
          case 1: 
            this.gui.setGripperClosed();
            break;
          default: 
          }
        }
      }
      catch (Exception localException3)
      {
      }
      try
      {
        int m = this.consumer.isUltraSonicOccupied();
        if (m != this.oldValueUltraSonic) {
          switch (m)
          {
          case 0: 
            this.gui.setUltraSonicFree();
            break;
          case 1: 
            this.gui.setUltraSonicOccupied();
            break;
          case -1: 
            this.gui.setUltraSonicNotDefined();
            break;
          default: 
          }
        }
      }
      catch (Exception localException4)
      {
      }
    }
  }
}
