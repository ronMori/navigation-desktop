package olivsoftdesktop.param;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class HeadlessGUIPanel
  extends JPanel
{
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private InputChannelPanel inputPanel = new InputChannelPanel();
  private RebroadcastPanel outputPanel = new RebroadcastPanel(false, true);

  public HeadlessGUIPanel()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit()
    throws Exception
  {
    this.setLayout(gridBagLayout1);
    inputPanel.setBorder(BorderFactory.createTitledBorder("Input Channel"));
    outputPanel.setBorder(BorderFactory.createTitledBorder("Output Channel(s)"));
    this.add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(outputPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
          new Insets(10, 0, 0, 0), 0, 0));
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
