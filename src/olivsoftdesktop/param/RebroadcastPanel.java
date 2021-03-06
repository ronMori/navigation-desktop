package olivsoftdesktop.param;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import olivsoftdesktop.ctx.DesktopContext;

public class RebroadcastPanel
  extends JPanel
{
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JCheckBox HTTPCheckBox = new JCheckBox();
  private JComboBox xmlJsonComboBox = new JComboBox();
  private JLabel overHttpLabel = new JLabel(" / HTTP");
  private JPanel httpPanel = new JPanel();
  private JCheckBox UDPCheckBox = new JCheckBox();
  private JCheckBox TCPCheckBox = new JCheckBox();
  private JLabel httpPortLabel = new JLabel();
  private JLabel udpPortLabel = new JLabel();
  private JLabel tcpPortLabel = new JLabel();
  private JFormattedTextField httpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
  private JFormattedTextField udpPortFormattedTextField  = new JFormattedTextField(new DecimalFormat("#####0"));
  private JFormattedTextField tcpPortFormattedTextField  = new JFormattedTextField(new DecimalFormat("#####0"));
  private JCheckBox verboseCheckBox = new JCheckBox();
  private JTextField udpHostNameTextField = new JTextField();
  private JCheckBox RMICheckBox = new JCheckBox();
  private JLabel rmiPortLabel = new JLabel();
  private JFormattedTextField rmiPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
//private JCheckBox GPSDCheckBox = new JCheckBox();
//private JLabel gpsdPortLabel = new JLabel();
//private JFormattedTextField gpsdPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JCheckBox logDataCheckBox = new JCheckBox();
  private JTextField logFileTextField = new JTextField();
  private JButton logFileButton = new JButton();
  
  private boolean withLogFile = false;
  private boolean withRMI     = true;
  private JButton excludedStringButton = new JButton();
  private JSeparator jSeparator1 = new JSeparator();

  public RebroadcastPanel()
  {
    this(true, false);
  }
  public RebroadcastPanel(boolean withRMI, boolean withLogFile)
  {
    this.withRMI = withRMI;
    this.withLogFile = withLogFile;
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
    this.setSize(new Dimension(263, 126));
    xmlJsonComboBox.removeAllItems();
    xmlJsonComboBox.addItem("XML");
    xmlJsonComboBox.addItem("json");
    HTTPCheckBox.setText("");
    HTTPCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          HTTPCheckBox_actionPerformed(e);
        }
      });
    overHttpLabel.setText("/ HTTP");
    httpPanel.setLayout(gridBagLayout2);
    httpPanel.add(HTTPCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    httpPanel.add(xmlJsonComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 1, 0, 0), 0, 0));
    httpPanel.add(overHttpLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 0), 0, 0));
    UDPCheckBox.setText("UDP");
    UDPCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          UDPCheckBox_actionPerformed(e);
        }
      });
    TCPCheckBox.setText("TCP");
    httpPortLabel.setText("port");
    udpPortLabel.setText("port");
    tcpPortLabel.setText("port");
    httpPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    httpPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    httpPortFormattedTextField.setText("9999");
    httpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    udpPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    udpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpPortFormattedTextField.setText("8001");
    tcpPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    tcpPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    tcpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    tcpPortFormattedTextField.setText("7001");
    verboseCheckBox.setText("verbose");
    udpHostNameTextField.setText("230.0.0.1");
    udpHostNameTextField.setHorizontalAlignment(JTextField.LEFT);
    udpHostNameTextField.setPreferredSize(new Dimension(70, 19));
    udpHostNameTextField.setMinimumSize(new Dimension(50, 19));

//    gpsdPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
//    gpsdPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
//    gpsdPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
//    gpsdPortFormattedTextField.setText("2947");

    this.add(httpPanel,
             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 5), 0, 0));
    this.add(UDPCheckBox,
             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 5), 0, 0));
    this.add(TCPCheckBox,
             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 5), 0, 0));
    this.add(httpPortLabel,
             new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 0), 0, 0));
    this.add(udpPortLabel,
             new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 0), 0, 0));
    this.add(tcpPortLabel,
             new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 0, 0, 0), 0, 0));
    this.add(httpPortFormattedTextField,
             new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 5, 0, 5), 0, 0));
    this.add(udpPortFormattedTextField,
             new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 5, 0, 5), 0, 0));
    this.add(tcpPortFormattedTextField,
             new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(0, 5, 0, 5), 0, 0));
    this.add(verboseCheckBox,
             new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                                    new Insets(0, 10, 0, 0), 0, 0));
    this.add(udpHostNameTextField,
             new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                                    new Insets(0, 10, 0, 0), 0, 0));

    this.add(excludedStringButton,
             new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                    new Insets(5, 0, 0, 0), 0, 0));
    this.add(jSeparator1,
             new GridBagConstraints(0, 5, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                                    new Insets(5, 0, 0, 0), 0, 0));
    if (withRMI)
    {
      this.add(RMICheckBox, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
      this.add(rmiPortLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
      this.add(rmiPortFormattedTextField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
    }
//    this.add(GPSDCheckBox, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//          new Insets(0, 0, 0, 0), 0, 0));
//    this.add(gpsdPortLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
//          new Insets(0, 0, 0, 0), 0, 0));
//    this.add(gpsdPortFormattedTextField, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
//          new Insets(0, 0, 0, 0), 0, 0));
    if (withLogFile)
    {
      this.add(logDataCheckBox, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 0, 0, 0), 0, 0));
      this.add(logFileTextField, new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 0, 0), 0, 0));
      this.add(logFileButton, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 2, 0, 0), 0, 0));
      logFileButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            String df = coreutilities.Utilities.chooseFile(JFileChooser.FILES_ONLY,
                                                           "nmea",
                                                           "NMEA Data",
                                                           "Log NMEA Data",
                                                           "Log");
            if (df != null && df.trim().length() > 0)
              logFileTextField.setText(df);
          }
        });
      
    }
    httpPortLabel.setEnabled(false);
    httpPortFormattedTextField.setEnabled(false);
    verboseCheckBox.setEnabled(false);
    
    udpPortLabel.setEnabled(false);
    udpPortFormattedTextField.setEnabled(false);
    udpHostNameTextField.setEnabled(false);

    rmiPortLabel.setEnabled(false);
    rmiPortFormattedTextField.setEnabled(false);
    
    logFileTextField.setEnabled(false);
    logFileButton.setEnabled(false);
    logFileButton.setPreferredSize(new Dimension(21, 21));

    RMICheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          RMICheckBox_actionPerformed(e);
        }
      });

    RMICheckBox.setText("RMI");
    rmiPortLabel.setText("port");
    rmiPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    rmiPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    rmiPortFormattedTextField.setText("1099");
    rmiPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);

//    GPSDCheckBox.setText("GPSd");
//    gpsdPortLabel.setText("port");
//    gpsdPortLabel.setEnabled(false);
//    
//    gpsdPortFormattedTextField.setEnabled(false);
//    GPSDCheckBox.setEnabled(true);
//    GPSDCheckBox.addActionListener(new ActionListener()
//      {
//        public void actionPerformed(ActionEvent e)
//        {
//          GPSDCheckBox_actionPerformed(e);
//        }
//      });

    logDataCheckBox.setText("Log Data in");
    logDataCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          logDataCheckBox_actionPerformed(e);
        }
      });
    logFileTextField.setHorizontalAlignment(JTextField.RIGHT);
    logFileButton.setText("...");
    excludedStringButton.setText("Excluded Strings");
    excludedStringButton.setToolTipText("From the file no.rebroadcast.list");
    excludedStringButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        excludedStringsButton_actionPerformed(e);
      }
    });
    tcpPortLabel.setEnabled(false);
    
    tcpPortFormattedTextField.setEnabled(false);
    TCPCheckBox.setEnabled(true);
    TCPCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          TCPCheckBox_actionPerformed(e);
        }
      });
  }

  private void HTTPCheckBox_actionPerformed(ActionEvent e)
  {
    httpPortLabel.setEnabled(HTTPCheckBox.isSelected());
    httpPortFormattedTextField.setEnabled(HTTPCheckBox.isSelected());
    verboseCheckBox.setEnabled(HTTPCheckBox.isSelected());
  }

  private void RMICheckBox_actionPerformed(ActionEvent e)
  {
    rmiPortLabel.setEnabled(RMICheckBox.isSelected());
    rmiPortFormattedTextField.setEnabled(RMICheckBox.isSelected());
  }

  private void TCPCheckBox_actionPerformed(ActionEvent e)
  {
    tcpPortLabel.setEnabled(TCPCheckBox.isSelected());
    tcpPortFormattedTextField.setEnabled(TCPCheckBox.isSelected());
  }

  private void UDPCheckBox_actionPerformed(ActionEvent e)
  {
    udpPortLabel.setEnabled(UDPCheckBox.isSelected());
    udpPortFormattedTextField.setEnabled(UDPCheckBox.isSelected());
    udpHostNameTextField.setEnabled(UDPCheckBox.isSelected());
  }
    
//  private void GPSDCheckBox_actionPerformed(ActionEvent e)
//  {
//    gpsdPortLabel.setEnabled(GPSDCheckBox.isSelected());
//    gpsdPortFormattedTextField.setEnabled(GPSDCheckBox.isSelected());
//  }
  
  public boolean isHTTPSelected()
  {
    return HTTPCheckBox.isSelected();
  }
  
  public String getHttpFlavor()
  {
    return xmlJsonComboBox.getSelectedItem().toString();
  }
  
  public boolean isUDPSelected()
  {
    return UDPCheckBox.isSelected();
  }
  
  public boolean isTCPSelected()
  {
    return TCPCheckBox.isSelected();
  }
  
//  public boolean isGPSDSelected()
//  {
//    return GPSDCheckBox.isSelected();
//  }
  
  public boolean isRMISelected()
  {
    return RMICheckBox.isSelected();
  }
  
  public int getHTTPPort()
  {
    int port = -1;
    try { port = Integer.parseInt(httpPortFormattedTextField.getText()); } catch (Exception nfe) { nfe.printStackTrace(); }
    return port;                                                                                                     
  }
  
  public int getUDPPort()
  {
    int port = -1;
    try { port = Integer.parseInt(udpPortFormattedTextField.getText()); } catch (Exception nfe) { nfe.printStackTrace(); }
    return port;                                                                                                     
  }
  
  public int getTCPPort()
  {
    int port = -1;
    try { port = Integer.parseInt(tcpPortFormattedTextField.getText()); } catch (Exception nfe) { nfe.printStackTrace(); }
    return port;                                                                                                     
  }
  
//  public int getGPSDPort()
//  {
//    int port = -1;
//    try { port = Integer.parseInt(gpsdPortFormattedTextField.getText()); } catch (Exception nfe) { nfe.printStackTrace(); }
//    return port;                                                                                                     
//  }
  
  public int getRMIPort()
  {
    int port = -1;
    try { port = Integer.parseInt(rmiPortFormattedTextField.getText()); } catch (Exception nfe) { nfe.printStackTrace(); }
    return port;                                                                                                     
  }
  
  public boolean httpVerbose()
  {
    return verboseCheckBox.isSelected();
  }
  
  public String udpHost()
  {
    return udpHostNameTextField.getText();
  }

  private void logDataCheckBox_actionPerformed(ActionEvent e)
  {
    logFileTextField.setEnabled(logDataCheckBox.isSelected());
    logFileButton.setEnabled(logDataCheckBox.isSelected());
  }
  
  public boolean isLogSelected()
  {
    return logDataCheckBox.isSelected();
  }
  
  public String getLogFileName()
  {
    return logFileTextField.getText();
  }

  private void excludedStringsButton_actionPerformed(ActionEvent e)
  {
    String mess = "NMEA Sentences excluded from re-broadcasting:\n";
    List<String> nrbl = DesktopContext.getInstance().getNoRebroadcastList();
    if (nrbl == null || nrbl.size() == 0)
      mess += "none";
    else
    {
      for (String s : nrbl)
        mess += (s + "\n");
    }
    JOptionPane.showMessageDialog(this, mess, "Not Re-broadcasted", JOptionPane.INFORMATION_MESSAGE);
  }
}
