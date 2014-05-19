package olivsoftdesktop.param;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HeadlessGUIPanel
  extends JPanel
{
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private InputChannelPanel inputPanel = new InputChannelPanel();
  private RebroadcastPanel outputPanel = new RebroadcastPanel(false, true);

  public HeadlessGUIPanel()
  {
    this(false);
  }
  public HeadlessGUIPanel(boolean small)
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (small)
    {
      drillDownComponents(this.getComponents(), 0);
      repaint();
    }
  }
  
  private void drillDownComponents(Component[] comp, int level)
  {
//  System.out.println(indent(2 * level) + "Level " + level + ":" + comp.length + " sub-components.");
    // TODO Make the font size a preference?
    Font newF = new Font("Arial", Font.PLAIN, 8); // f.deriveFont((int)Math.floor(f.getSize() * (factor * 0.75)));

    for (int i=0; i<comp.length; i++)
    {
//    System.out.println(indent(2 * level) + comp[i].getClass().getName());
      if (comp[i] instanceof JPanel)
      {
        JPanel jp = (JPanel)comp[i];
        try { ((javax.swing.border.TitledBorder)jp.getBorder()).setTitleFont(newF); } catch (NullPointerException npe) {}
        if (jp.getComponents() != null)
          drillDownComponents(jp.getComponents(), level + 1);
      }
      else if (true)
      {
        if (comp[i] instanceof JTextField || comp[i] instanceof JButton)
        {
          double factor = 0.65; // TODO System variable
          Dimension dim = comp[i].getPreferredSize();
          Dimension newDim = new Dimension((int)(dim.getWidth() * factor), (int)(dim.getHeight() * factor));
//        System.out.println(indent(2 * level) + " Changing size of a " + comp[i].getClass().getName() + ", from " + dim + " to " + newDim);
          comp[i].setPreferredSize(newDim);
//        Font f = comp[i].getFont();
//        Font newF = new Font("Arial", Font.PLAIN, 8) ; // f.deriveFont((int)Math.floor(f.getSize() * (factor * 0.75)));
        }
        comp[i].setFont(newF);
      }
    }
  }

  private static String indent(int x)
  {
    String s = "";
    for (int i=0; i<x; i++)
      s += " ";
    return s;
  }
  
  private void jbInit()
    throws Exception
  {
    this.setLayout(gridBagLayout1);
  //this.setPreferredSize(new Dimension(306, 203));
  //this.setBounds(new Rectangle(10, 10, 400, 210));
    this.setSize(new Dimension(310, 205));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Input Channel"));
    outputPanel.setBorder(BorderFactory.createTitledBorder("Output Channel(s)"));
    this.add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(outputPanel,
             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                    new Insets(0, 0, 0, 0), 0, 0));
  }
  
  public boolean isVerbose()
  {
    return outputPanel.httpVerbose();
  }
  
  public int getChannel()
  {
    return inputPanel.getInputChannel();
  }
  
  public String getSerialport()
  {
    return inputPanel.getSerialport();
  }
  
  public String getTcpPort()
  {
    return inputPanel.getTcpPort();
  }

  public String getUdpPort()
  {
    return inputPanel.getUdpPort();
  }
  
  public String getUdpMachine()
  { 
    return inputPanel.getUdpMachine();
  }
  
  public String getDataFileName()
  {
    return inputPanel.getDataFileName();
  }
  
  public boolean isHTTPSelected()
  {
    return outputPanel.isHTTPSelected();
  }
  
  public boolean isTCPSelected()
  {
    return outputPanel.isTCPSelected();
  }

  public boolean isUDPSelected()
  {
    return outputPanel.isUDPSelected();
  }

  public boolean isRMISelected()
  {
    return outputPanel.isRMISelected();
  }
  
  public String getHTTPPort()
  {
    return Integer.toString(outputPanel.getHTTPPort());
  }
  
  public String getTCPPort()
  {
    return Integer.toString(outputPanel.getTCPPort());
  }
  
  public String getUDPPort()
  {
    return Integer.toString(outputPanel.getUDPPort());
  }
  
  public String getUDPMachine()
  {
    return outputPanel.udpHost();
  }

  public String getRMIPort()
  {
    return Integer.toString(outputPanel.getRMIPort());
  }

  public boolean isLogFileSelected()
  {
    return outputPanel.isLogSelected();
  }
  
  public String getLogFileName()
  {
    return outputPanel.getLogFileName();
  }
}
