package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

import dienste.IIDLCaDSEV3RMIMoveGripper;
import dienste.IIDLCaDSEV3RMIMoveHorizontal;
import dienste.IIDLCaDSEV3RMIMoveVertical;


public class CaDSRobotControlGUISwing
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  static int transactionID = Integer.MIN_VALUE;
  private static IIDLCaDSEV3RMIMoveGripper gripper = null;
  private static IIDLCaDSEV3RMIMoveVertical vertical = null;
  private static IIDLCaDSEV3RMIMoveHorizontal horizontal = null;
  private ICaDSRMIConsumer consumer = null;
  private boolean closed = true;
  private final JSlider verticalSlider;
  private final JSlider horizontalSlider;
  private final JProgressBar verticalProgressbar;
  private final JProgressBar horizontalProgressbar;
  private final JLabel gripperClosedLabel;
  private final JComboBox<String> robotComboBox;
  private final JLabel ultraSonicOccupied;
  
  public static void main(String[] paramArrayOfString)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotGUISwing localCaDSRobotGUISwing = new CaDSRobotGUISwing(null, null, null, null, null);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  public void setConsumer(ICaDSRMIConsumer paramICaDSRMIConsumer)
  {
    this.consumer = paramICaDSRMIConsumer;
  }
  
  public CaDSRobotControlGUISwing(ICaDSRMIConsumer paramICaDSRMIConsumer, IIDLCaDSEV3RMIMoveGripper paramIIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveVertical paramIIDLCaDSEV3RMIMoveVertical, IIDLCaDSEV3RMIMoveHorizontal paramIIDLCaDSEV3RMIMoveHorizontal)
  {
    gripper = paramIIDLCaDSEV3RMIMoveGripper;
    horizontal = paramIIDLCaDSEV3RMIMoveHorizontal;
    vertical = paramIIDLCaDSEV3RMIMoveVertical;
    this.consumer = paramICaDSRMIConsumer;
    setTitle("CaDS Swing Remote GUI");
    setDefaultCloseOperation(3);
    setBounds(300, 300, 600, 600);
    GridBagLayout localGridBagLayout = new GridBagLayout();
    localGridBagLayout.columnWidths = new int[] { 102, 78, 374, 0 };
    localGridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 282, 0, 0, 0, 0, 0, 0, 0 };
    localGridBagLayout.columnWeights = new double[] { 0.0D, 0.0D, 0.0D, Double.MIN_VALUE };
    localGridBagLayout.rowWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, Double.MIN_VALUE };
    getContentPane().setLayout(localGridBagLayout);
    String str = "pictures/CADS_Logo_Quadrat_300x300_RGB_72dpi.jpg";
    JLabel localJLabel1 = new JLabel(new ImageIcon(str));
    GridBagConstraints localGridBagConstraints1 = new GridBagConstraints();
    localGridBagConstraints1.insets = new Insets(0, 0, 5, 0);
    localGridBagConstraints1.gridx = 2;
    localGridBagConstraints1.gridy = 4;
    getContentPane().add(localJLabel1, localGridBagConstraints1);
    JLabel localJLabel2 = new JLabel("Robot ID:");
    GridBagConstraints localGridBagConstraints2 = new GridBagConstraints();
    localGridBagConstraints2.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints2.gridx = 0;
    localGridBagConstraints2.gridy = 0;
    getContentPane().add(localJLabel2, localGridBagConstraints2);
    JComboBox localJComboBox = new JComboBox();
    this.robotComboBox = localJComboBox;
    localJComboBox.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent paramAnonymousItemEvent)
      {
        String str = null;
        int i = paramAnonymousItemEvent.getStateChange();
        System.out.println(i == 1 ? "Selected" : "Deselected");
        if (i != 1) {
          return;
        }
        System.out.println("Item: " + paramAnonymousItemEvent.getItem());
        ItemSelectable localItemSelectable = paramAnonymousItemEvent.getItemSelectable();
        Object[] arrayOfObject = localItemSelectable.getSelectedObjects();
        if (arrayOfObject.length == 0) {
          str = "null";
        } else {
          str = (String)arrayOfObject[0];
        }
        if (CaDSRobotControlGUISwing.this.consumer != null) {
          CaDSRobotControlGUISwing.this.consumer.update(str);
        }
      }
    });
    GridBagConstraints localGridBagConstraints3 = new GridBagConstraints();
    localGridBagConstraints3.gridwidth = 2;
    localGridBagConstraints3.insets = new Insets(0, 0, 5, 0);
    localGridBagConstraints3.fill = 1;
    localGridBagConstraints3.gridx = 1;
    localGridBagConstraints3.gridy = 0;
    getContentPane().add(localJComboBox, localGridBagConstraints3);
    JLabel localJLabel3 = new JLabel("Vertical:");
    GridBagConstraints localGridBagConstraints4 = new GridBagConstraints();
    localGridBagConstraints4.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints4.gridx = 0;
    localGridBagConstraints4.gridy = 2;
    getContentPane().add(localJLabel3, localGridBagConstraints4);
    JLabel localJLabel4 = new JLabel("Current");
    GridBagConstraints localGridBagConstraints5 = new GridBagConstraints();
    localGridBagConstraints5.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints5.gridx = 0;
    localGridBagConstraints5.gridy = 3;
    getContentPane().add(localJLabel4, localGridBagConstraints5);
    JLabel localJLabel5 = new JLabel("New");
    GridBagConstraints localGridBagConstraints6 = new GridBagConstraints();
    localGridBagConstraints6.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints6.gridx = 1;
    localGridBagConstraints6.gridy = 3;
    getContentPane().add(localJLabel5, localGridBagConstraints6);
    JProgressBar localJProgressBar1 = new JProgressBar();
    this.verticalProgressbar = localJProgressBar1;
    localJProgressBar1.setStringPainted(true);
    localJProgressBar1.setOrientation(1);
    GridBagConstraints localGridBagConstraints7 = new GridBagConstraints();
    localGridBagConstraints7.fill = 3;
    localGridBagConstraints7.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints7.gridx = 0;
    localGridBagConstraints7.gridy = 4;
    getContentPane().add(localJProgressBar1, localGridBagConstraints7);
    JSlider localJSlider1 = new JSlider();
    this.verticalSlider = localJSlider1;
    localJSlider1.setValue(0);
    localJSlider1.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        JSlider localJSlider = (JSlider)paramAnonymousMouseEvent.getSource();
        try
        {
          CaDSRobotControlGUISwing.vertical.moveVerticalToPercent(CaDSRobotControlGUISwing.this.getTransactionID(), localJSlider.getValue());
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    localJSlider1.setMinorTickSpacing(25);
    localJSlider1.setPaintTicks(true);
    localJSlider1.setPaintLabels(true);
    localJSlider1.setMajorTickSpacing(50);
    localJSlider1.setOrientation(1);
    GridBagConstraints localGridBagConstraints8 = new GridBagConstraints();
    localGridBagConstraints8.fill = 3;
    localGridBagConstraints8.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints8.gridx = 1;
    localGridBagConstraints8.gridy = 4;
    getContentPane().add(localJSlider1, localGridBagConstraints8);
    JLabel localJLabel6 = new JLabel("Horizontal");
    GridBagConstraints localGridBagConstraints9 = new GridBagConstraints();
    localGridBagConstraints9.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints9.gridx = 0;
    localGridBagConstraints9.gridy = 6;
    getContentPane().add(localJLabel6, localGridBagConstraints9);
    JLabel localJLabel7 = new JLabel("New");
    GridBagConstraints localGridBagConstraints10 = new GridBagConstraints();
    localGridBagConstraints10.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints10.gridx = 1;
    localGridBagConstraints10.gridy = 6;
    getContentPane().add(localJLabel7, localGridBagConstraints10);
    JSlider localJSlider2 = new JSlider();
    this.horizontalSlider = localJSlider2;
    localJSlider2.setValue(0);
    localJSlider2.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        JSlider localJSlider = (JSlider)paramAnonymousMouseEvent.getSource();
        try
        {
          CaDSRobotControlGUISwing.horizontal.moveHorizontalToPercent(CaDSRobotControlGUISwing.this.getTransactionID(), localJSlider.getValue());
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    localJSlider2.setMinorTickSpacing(25);
    localJSlider2.setPaintTicks(true);
    localJSlider2.setPaintLabels(true);
    localJSlider2.setMajorTickSpacing(50);
    localJSlider2.setOrientation(0);
    GridBagConstraints localGridBagConstraints11 = new GridBagConstraints();
    localGridBagConstraints11.fill = 2;
    localGridBagConstraints11.insets = new Insets(0, 0, 5, 0);
    localGridBagConstraints11.gridx = 2;
    localGridBagConstraints11.gridy = 6;
    getContentPane().add(localJSlider2, localGridBagConstraints11);
    JLabel localJLabel8 = new JLabel("Current");
    GridBagConstraints localGridBagConstraints12 = new GridBagConstraints();
    localGridBagConstraints12.insets = new Insets(0, 0, 5, 5);
    localGridBagConstraints12.gridx = 1;
    localGridBagConstraints12.gridy = 7;
    getContentPane().add(localJLabel8, localGridBagConstraints12);
    JProgressBar localJProgressBar2 = new JProgressBar();
    this.horizontalProgressbar = localJProgressBar2;
    localJProgressBar2.setStringPainted(true);
    GridBagConstraints localGridBagConstraints13 = new GridBagConstraints();
    localGridBagConstraints13.fill = 2;
    localGridBagConstraints13.insets = new Insets(0, 0, 5, 0);
    localGridBagConstraints13.gridx = 2;
    localGridBagConstraints13.gridy = 7;
    getContentPane().add(localJProgressBar2, localGridBagConstraints13);
    JLabel localJLabel9 = new JLabel(" Gripper is:");
    GridBagConstraints localGridBagConstraints14 = new GridBagConstraints();
    localGridBagConstraints14.insets = new Insets(0, 0, 0, 5);
    localGridBagConstraints14.gridx = 0;
    localGridBagConstraints14.gridy = 9;
    getContentPane().add(localJLabel9, localGridBagConstraints14);
    JLabel localJLabel10 = new JLabel("Closed");
    this.gripperClosedLabel = localJLabel10;
    GridBagConstraints localGridBagConstraints15 = new GridBagConstraints();
    localGridBagConstraints15.insets = new Insets(0, 0, 0, 5);
    localGridBagConstraints15.gridx = 1;
    localGridBagConstraints15.gridy = 9;
    getContentPane().add(localJLabel10, localGridBagConstraints15);
    JButton localJButton = new JButton("Open/Close");
    localJButton.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        if (CaDSRobotControlGUISwing.this.closed)
        {
          if (CaDSRobotControlGUISwing.gripper != null) {
            try
            {
              CaDSRobotControlGUISwing.gripper.openGripper(CaDSRobotControlGUISwing.this.getTransactionID());
            }
            catch (Exception localException1)
            {
              localException1.printStackTrace();
            }
          }
        }
        else if (CaDSRobotControlGUISwing.gripper != null) {
          try
          {
            CaDSRobotControlGUISwing.gripper.closeGripper(CaDSRobotControlGUISwing.this.getTransactionID());
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
        CaDSRobotControlGUISwing.this.closed = (!CaDSRobotControlGUISwing.this.closed);
      }
    });
    GridBagConstraints localGridBagConstraints16 = new GridBagConstraints();
    localGridBagConstraints16.gridx = 2;
    localGridBagConstraints16.gridy = 9;
    getContentPane().add(localJButton, localGridBagConstraints16);
    JLabel localJLabel11 = new JLabel(" Ultra Sonic is:");
    GridBagConstraints localGridBagConstraints17 = new GridBagConstraints();
    localGridBagConstraints17.insets = new Insets(0, 0, 0, 5);
    localGridBagConstraints17.gridx = 0;
    localGridBagConstraints17.gridy = 10;
    getContentPane().add(localJLabel11, localGridBagConstraints17);
    JLabel localJLabel12 = new JLabel("Free!");
    this.ultraSonicOccupied = localJLabel12;
    GridBagConstraints localGridBagConstraints18 = new GridBagConstraints();
    localGridBagConstraints18.insets = new Insets(0, 0, 0, 5);
    localGridBagConstraints18.gridx = 1;
    localGridBagConstraints18.gridy = 10;
    getContentPane().add(localJLabel12, localGridBagConstraints18);
  }
  
  void setGripperClosed()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.gripperClosedLabel.setText("Closed!");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setGripperOpen()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.gripperClosedLabel.setText("Open!");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setUltraSonicSensorOccupied()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.ultraSonicOccupied.setText("Occupied!");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setUltraSonicSensorFree()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.ultraSonicOccupied.setText("Free!");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setUltraSonicSensorNotDefined()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.ultraSonicOccupied.setText("Not Defined!");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setVerticalProgressbar(final int paramInt)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.verticalProgressbar.setValue(paramInt);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void setHorizontalProgressbar(final int paramInt)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          CaDSRobotControlGUISwing.this.horizontalProgressbar.setValue(paramInt);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void teardown()
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.currentThread().interrupt();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  void addService(String paramString)
  {
    this.robotComboBox.addItem(paramString);
  }
  
  void removeService(String paramString)
  {
    for (int i = 0; i < this.robotComboBox.getItemCount(); i++) {
      if (((String)this.robotComboBox.getItemAt(i)).equals(paramString))
      {
        this.robotComboBox.remove(i);
        break;
      }
    }
  }
  
  void setChoosenService(String paramString)
  {
    setChoosenService(paramString, this.horizontalSlider.getValue(), this.verticalSlider.getValue(), this.closed);
  }
  
  void setChoosenService(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    for (int i = 0; i < this.robotComboBox.getItemCount(); i++) {
      if (((String)this.robotComboBox.getItemAt(i)).equals(paramString))
      {
        this.robotComboBox.setSelectedIndex(i);
        return;
      }
    }
    try
    {
      this.robotComboBox.setSelectedIndex(0);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private int getTransactionID()
  {
    return transactionID++;
  }
  
  private void setGUIValues(final int paramInt1, final int paramInt2, final boolean paramBoolean)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        CaDSRobotControlGUISwing.this.verticalSlider.setValue(paramInt2);
        CaDSRobotControlGUISwing.this.horizontalSlider.setValue(paramInt1);
        if (paramBoolean) {
          CaDSRobotControlGUISwing.this.gripperClosedLabel.setText("Closed!");
        } else {
          CaDSRobotControlGUISwing.this.gripperClosedLabel.setText("Open!");
        }
      }
    });
  }
}


