package olivsoftdesktop.param;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RebroadcastPanel
  extends JPanel
{
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JCheckBox HTTPCheckBox = new JCheckBox();
  private JCheckBox UDPCheckBox = new JCheckBox();
  private JCheckBox TCPCheckBox = new JCheckBox();
  private JLabel httpPortLabel = new JLabel();
  private JLabel udpPortLabel = new JLabel();
  private JLabel tcpPortLabel = new JLabel();
  private JFormattedTextField httpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
  private JFormattedTextField udpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
  private JFormattedTextField tcpPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));
  private JCheckBox verboseCheckBox = new JCheckBox();
  private JTextField hostNameTextField = new JTextField();
  private JCheckBox RMICheckBox = new JCheckBox();
  private JLabel rmiPortLabel = new JLabel();
  private JFormattedTextField rmiPortFormattedTextField = new JFormattedTextField(new DecimalFormat("#####0"));

  public RebroadcastPanel()
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
    this.setSize(new Dimension(376, 187));
    HTTPCheckBox.setText("XML over HTTP");
    HTTPCheckBox.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          HTTPCheckBox_actionPerformed(e);
        }
      });
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
    httpPortFormattedTextField.setText("6666");
    httpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    udpPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    udpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    udpPortFormattedTextField.setText("8001");
    tcpPortFormattedTextField.setMinimumSize(new Dimension(50, 19));
    tcpPortFormattedTextField.setPreferredSize(new Dimension(50, 19));
    tcpPortFormattedTextField.setHorizontalAlignment(JTextField.CENTER);
    tcpPortFormattedTextField.setText("3001");
    verboseCheckBox.setText("verbose");
    hostNameTextField.setText("230.0.0.1");
    hostNameTextField.setHorizontalAlignment(JTextField.LEFT);
    hostNameTextField.setPreferredSize(new Dimension(70, 19));
    hostNameTextField.setMinimumSize(new Dimension(50, 19));
    this.add(HTTPCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(UDPCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(TCPCheckBox, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 5), 0, 0));
    this.add(httpPortLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(udpPortLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(tcpPortLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(httpPortFormattedTextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 5), 0, 0));
    this.add(udpPortFormattedTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 5), 0, 0));
    this.add(tcpPortFormattedTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 5, 0, 5), 0, 0));
    this.add(verboseCheckBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 10, 0, 0), 0, 0));
    this.add(hostNameTextField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
          new Insets(0, 10, 0, 0), 0, 0));

    this.add(RMICheckBox, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(rmiPortLabel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    this.add(rmiPortFormattedTextField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
    httpPortLabel.setEnabled(false);
    httpPortFormattedTextField.setEnabled(false);
    verboseCheckBox.setEnabled(false);
    
    udpPortLabel.setEnabled(false);
    udpPortFormattedTextField.setEnabled(false);
    hostNameTextField.setEnabled(false);

    rmiPortLabel.setEnabled(false);
    rmiPortFormattedTextField.setEnabled(false);
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
    tcpPortLabel.setEnabled(false);
    tcpPortFormattedTextField.setEnabled(false);
    TCPCheckBox.setEnabled(false); // For now...
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

  private void UDPCheckBox_actionPerformed(ActionEvent e)
  {
    udpPortLabel.setEnabled(UDPCheckBox.isSelected());
    udpPortFormattedTextField.setEnabled(UDPCheckBox.isSelected());
    hostNameTextField.setEnabled(UDPCheckBox.isSelected());
  }
  
  public boolean isHTTPSelected()
  {
    return HTTPCheckBox.isSelected();
  }
  
  public boolean isUDPSelected()
  {
    return UDPCheckBox.isSelected();
  }
  
  public boolean isTCPSelected()
  {
    return TCPCheckBox.isSelected();
  }
  
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
  
  public int getUDTPPort()
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
    return hostNameTextField.getText();
  }
}
