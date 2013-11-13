package olivsoftdesktop.param;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import olivsoftdesktop.utils.SerialPortList;

public class InputChannelPanel
  extends JPanel
{
  public final static int SERIAL      = 0;
  public final static int TCP         = 1;
  public final static int UDP         = 2;
  public final static int LOGGED_DATA = 3;  
  
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JRadioButton serialRadioButton = new JRadioButton();
  private JRadioButton tcpRadioButton = new JRadioButton();
  private JRadioButton udpRadioButton = new JRadioButton();
  private JRadioButton fileRadioButton = new JRadioButton();
  private JLabel serialPortLabel = new JLabel();
  private JLabel tcpPortLabel = new JLabel();
  private JLabel udpPortLabel = new JLabel();
  private JLabel fileLabel = new JLabel();
  private JFormattedTextField tcpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("####0"));
  private JTextField udpMachineTextField = new JTextField();
  private JFormattedTextField udpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("####0"));
  private JTextField dataFileTextField = new JTextField();
  private JButton dataFileButton = new JButton();
  private JComboBox serialPortsComboBox = new JComboBox();
  private ButtonGroup buttonGroup = new ButtonGroup();

  public InputChannelPanel()
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
    serialRadioButton.setText("Serial");
    serialRadioButton.setToolTipText("Serial Port");
    serialRadioButton.setHorizontalTextPosition(SwingConstants.LEADING);
    tcpRadioButton.setText("TCP");
    tcpRadioButton.setToolTipText("TCP Port");
    tcpRadioButton.setHorizontalTextPosition(SwingConstants.LEADING);
    tcpRadioButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          tcpRadioButton_actionPerformed(e);
        }
      });
    udpRadioButton.setText("UDP");
    udpRadioButton.setToolTipText("UDP Address and Port");
    udpRadioButton.setHorizontalTextPosition(SwingConstants.LEADING);
    udpRadioButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          udpRadioButton_actionPerformed(e);
        }
      });
    fileRadioButton.setText("File");
    fileRadioButton.setToolTipText("From logged Data");
    fileRadioButton.setHorizontalTextPosition(SwingConstants.LEADING);
    fileRadioButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          fileRadioButton_actionPerformed(e);
        }
      });
    serialPortLabel.setText("Port");
    tcpPortLabel.setText("Port");
    udpPortLabel.setText("Port and Address");
    udpPortLabel.setToolTipText("Port and Address (or machine name)");
    fileLabel.setText("Data file");
    tcpPortFormattedTextField.setText("7001");
    udpMachineTextField.setText("localhost");
    udpPortFormattedTextField.setText("8001");
    dataFileButton.setText("...");

    dataFileButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          dataFileButton_actionPerformed(e);
        }
      });
    buttonGroup.add(serialRadioButton);
    buttonGroup.add(tcpRadioButton);
    buttonGroup.add(udpRadioButton);
    buttonGroup.add(fileRadioButton);
    serialRadioButton.setSelected(true);

    serialRadioButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          serialRadioButton_actionPerformed(e);
        }
      });
    serialPortsComboBox.setPreferredSize(new Dimension(100, 20));
    tcpPortFormattedTextField.setPreferredSize(new Dimension(50, 20));
    tcpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpPortFormattedTextField.setPreferredSize(new Dimension(50, 20));
    udpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpMachineTextField.setPreferredSize(new Dimension(100, 20));
    udpMachineTextField.setHorizontalAlignment(JTextField.CENTER);
    dataFileTextField.setPreferredSize(new Dimension(150, 20));
    dataFileTextField.setHorizontalAlignment(JTextField.RIGHT);
    
    enableRightOption();
    
    this.add(serialRadioButton,
             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(tcpRadioButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(udpRadioButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(fileRadioButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(serialPortLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(tcpPortLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(udpPortLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(fileLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(tcpPortFormattedTextField,
             new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 0), 0, 0));
    this.add(udpPortFormattedTextField,
             new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 0), 0, 0));
    this.add(udpMachineTextField,
             new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 2, 0, 0), 0, 0));
    this.add(dataFileTextField,
             new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 0), 0, 0));
    this.add(dataFileButton, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 2, 0, 0), 0, 0));
    this.add(serialPortsComboBox,
             new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 0), 0, 0));
    
    String[] serialPort = SerialPortList.listSerialPorts();
    serialPortsComboBox.removeAllItems();
    for (String port : serialPort)
      serialPortsComboBox.addItem(port);
  }

  private void dataFileButton_actionPerformed(ActionEvent e)
  {
    String df = coreutilities.Utilities.chooseFile(JFileChooser.FILES_ONLY,
                                                   "nmea",
                                                   "NMEA Data",
                                                   "Load NMEA Data",
                                                   "Load");
    if (df != null && df.trim().length() > 0)
      dataFileTextField.setText(df);
  }

  private void serialRadioButton_actionPerformed(ActionEvent e)
  {
    enableRightOption();
  }

  private void tcpRadioButton_actionPerformed(ActionEvent e)
  {
    enableRightOption();
  }

  private void udpRadioButton_actionPerformed(ActionEvent e)
  {
    enableRightOption();
  }

  private void fileRadioButton_actionPerformed(ActionEvent e)
  {
    enableRightOption();
  }

  private void enableRightOption()
  {
    enableOutputOption(serialRadioButton.isSelected(), tcpRadioButton.isSelected(), udpRadioButton.isSelected(), fileRadioButton.isSelected());    
  }
  
  private void enableOutputOption(boolean serial, boolean tcp, boolean udp, boolean file)
  {
    serialPortLabel.setEnabled(serial);
    serialPortsComboBox.setEnabled(serial);
    tcpPortLabel.setEnabled(tcp);
    tcpPortFormattedTextField.setEnabled(tcp);
    udpPortLabel.setEnabled(udp);
    udpPortFormattedTextField.setEnabled(udp);
    udpMachineTextField.setEnabled(udp);
    fileLabel.setEnabled(file);
    dataFileTextField.setEnabled(file);
    dataFileButton.setEnabled(file);    
  }
  
  public int getInputChannel()
  {
    int channel = SERIAL;
    if (tcpRadioButton.isSelected())
      channel = TCP;
    else if (udpRadioButton.isSelected())
      channel = UDP;
    else if (fileRadioButton.isSelected())
      channel = LOGGED_DATA;
    return channel;
  }
  
  public String getSerialport()
  {
    return serialPortsComboBox.getSelectedItem().toString();
  }
  
  public String getTcpPort()
  {
    return tcpPortFormattedTextField.getText();
  }

  public String getUdpPort()
  {
    return udpPortFormattedTextField.getText();
  }
  
  public String getUdpMachine()
  { 
    return udpMachineTextField.getText();
  }
  
  public String getDataFileName()
  {
    return dataFileTextField.getText();
  }
}
