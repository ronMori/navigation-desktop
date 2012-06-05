package olivsoftdesktop.param.widget;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JColorChooser;

public class FieldPlusColorPicker
  extends FieldPlusFinderButton
{
  public FieldPlusColorPicker(Object o)
  {
    super(o);
  }
  
  protected Object invokeEditor()
  {
    Color c = JColorChooser.showDialog(this, "Color", this.textField.getBackground());
    this.value = c;
    return c;
  }
  
  protected void finderButton_actionPerformed(ActionEvent e)
  {
    Object o = invokeEditor();
    if (o instanceof Color)
    {
      Color c = (Color)o;
      if (c != null)
      {
        this.value = o;
        this.textField.setBackground(c);
      }
    }
  }  
}
