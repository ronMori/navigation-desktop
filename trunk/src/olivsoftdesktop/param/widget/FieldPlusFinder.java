package olivsoftdesktop.param.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import olivsoftdesktop.utils.DesktopUtilities;

public class FieldPlusFinder 
     extends JPanel 
{
  public final static int XML_TYPE        = 0;
  public final static int DIRECTORY_TYPE  = 1;
  public final static int IMG_TYPE        = 2;
  public final static int PROPERTIES_TYPE = 3;
  public final static int NMEA_TYPE       = 4;
  
  BorderLayout borderLayout1 = new BorderLayout();
  JButton finderButton = new JButton();
  JTextField textField = new JTextField();

  private int type;

  public FieldPlusFinder(int t)
  {
    type = t;
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.setSize(new Dimension(181, 29));
    this.setLayout(borderLayout1);
    finderButton.setFocusable(false);
    finderButton.setText("...");
    finderButton.setPreferredSize(new Dimension(30, 20));
    finderButton.setMinimumSize(new Dimension(30, 20));
    finderButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          finderButton_actionPerformed(e);
        }
      });
    textField.setHorizontalAlignment(JTextField.RIGHT);
    this.add(finderButton, BorderLayout.EAST);
    this.add(textField, BorderLayout.CENTER);
  }

  public JTextField getTextField()
  { return this.textField; }
  public JButton getButton()
  { return this.finderButton; }

  private String invokeEditor()
  {
    String[] fileType = null;
    String desc = "";
    int option = 0;
    
    if (this.type == XML_TYPE)
    {
      fileType = new String[] {"xml"};
      desc = "XML Files";
      option = JFileChooser.FILES_ONLY;
    }
    else if (this.type == IMG_TYPE)
    {
      fileType = new String[] {"png", "jpg", "gif"};
      desc = "Image Files";
      option = JFileChooser.FILES_ONLY;
    }
    else if (this.type == PROPERTIES_TYPE)
    {
      fileType = new String[] {"properties"};
      desc = "Properties Files";
      option = JFileChooser.FILES_ONLY;
    }
    else if (this.type == NMEA_TYPE)
    {
      fileType = new String[] {"nmea"};
      desc = "NMEA Data Files";
      option = JFileChooser.FILES_ONLY;
    }
    else if (this.type == DIRECTORY_TYPE)
    {
      fileType = new String[] {"data"};
      desc = "directory";
      option = JFileChooser.DIRECTORIES_ONLY;
    }
    String val = this.textField.getText();
    System.out.println("Editing :" + val);
    String chooserDir = null;
    if (val.indexOf(File.separator) > -1)
    {
      chooserDir = val.substring(0, val.lastIndexOf(File.separator));
      System.out.println("ChooserDir:" + chooserDir);
    }
    String str = DesktopUtilities.chooseFile(option, 
                                         fileType, 
                                         desc,
                                         chooserDir);
    if (str.startsWith(System.getProperty("user.dir")))        
      str = DesktopUtilities.replaceString(str, System.getProperty("user.dir"), ".");
    return str;
  }

  void finderButton_actionPerformed(ActionEvent e)
  {
//  String origValue = this.textField.getText();
    String str = invokeEditor();
    if (str != null && str.trim().length() > 0)
    {
      this.textField.setText(str);
    }
  }
}