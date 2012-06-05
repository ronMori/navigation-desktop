package olivsoftdesktop.param.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class FieldPlusFinderButton 
              extends JPanel 
{
  BorderLayout borderLayout1 = new BorderLayout();
  JButton finderButton = new JButton();
  JTextField textField = new JTextField();
  
  Object value;

  public FieldPlusFinderButton(Object o)
  {
    value = o;
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
    finderButton.setText("...");
    finderButton.setFocusable(false);
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

  protected abstract Object invokeEditor();

  protected void finderButton_actionPerformed(ActionEvent e)
  {
//  String origValue = this.textField.getText();
    Object o = invokeEditor();
    if (o instanceof String)
    {
      String str = (String)o;
      if (str != null && str.length() > 0)
      {
        this.textField.setText(str);
//        if (value instanceof ParamPanel.DataFile)
//        {
//          ((ParamPanel.DataFile)value).setValue(str);
//        }
//        else if (value instanceof ParamPanel.DataDirectory)
//        {
//          ((ParamPanel.DataDirectory)value).setValue(str);
//        }
//        else if (value instanceof FaxType)
//        {
//          ((FaxType)value).setValue(str);
//        }
//        else
          value = o;
      }
    }
    else if (o instanceof Color)
    {
      this.value = (Color)o;
    }
//    else if (o instanceof FaxType)
//    {
//      this.textField.setText(((FaxType)o).getValue());
//      this.textField.setForeground(((FaxType)o).getColor());
//      this.value = (FaxType)o;
//    }
    else if (o != null)
      System.out.println("FieldPlusFinder, value, not managed, is a " + o.getClass().getName());
    else 
      System.out.println("FieldPlusFinder, value is null");
  }
  
  public Object getValue()
  { return value; }
}