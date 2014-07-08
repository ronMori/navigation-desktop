package olivsoftdesktop.param.widget;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.util.EventObject;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import olivsoftdesktop.param.FontPanel;
import olivsoftdesktop.param.MarqueeDataPanel;

@SuppressWarnings("serial")
public class FieldPlusMarqueeDataPicker
     extends FieldPlusFinderButton
  implements TableCellEditor
{
  protected transient Vector<CellEditorListener> listeners;
  protected transient String origValue;

  public FieldPlusMarqueeDataPicker(Object o)
  {
    super(o);
    if (o != null)
    {
      if (o instanceof String)
        setText((String)o);
      else
        setText(o.toString());
    }
    listeners = new Vector<CellEditorListener>();
  }

  public void setText(String str)
  {
    this.getTextField().setText(str);
  }

  protected Object invokeEditor()
  {
    MarqueeDataPanel mdp = new MarqueeDataPanel(this.value.toString());
    int resp = JOptionPane.showConfirmDialog(this, mdp, "Marquee Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (resp == JOptionPane.OK_OPTION)
      this.value = mdp.getStringData(); 
    return this.value;
  }

  protected void finderButton_actionPerformed(ActionEvent e)
  {
    Object o = invokeEditor();
    if (o instanceof String)
    {
      String df = (String) o;
      if (df != null)
      {
        this.value = o;
      }
    }
  }

  public Component getTableCellEditorComponent(JTable table, 
                                               Object value, 
                                               boolean isSelected, 
                                               int row, 
                                               int column)
  {
    if (value == null)
    {
      this.getTextField().setText("");
      return this;
//    System.out.println("Value is null, reseting");
//    value = origValue;
    }
//  this.getTextField().setText(value.toString());
    this.getTextField().setText(FontPanel.fontToString((Font)value));
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    origValue = (String)value;
    return this;
  }

  public Object getCellEditorValue()
  {
    return this.getValue();
  }

  public boolean isCellEditable(EventObject anEvent)
  {
    return true;
  }

  public boolean shouldSelectCell(EventObject anEvent)
  {
    return true;
  }

  public boolean stopCellEditing()
  {
    fireEditingStopped();
    return true;
  }

  public void cancelCellEditing()
  {
    fireEditingCanceled();
  }

  public void addCellEditorListener(CellEditorListener l)
  {
    listeners.addElement(l);
  }

  public void removeCellEditorListener(CellEditorListener l)
  {
    listeners.removeElement(l);
  }

  protected void fireEditingCanceled()
  {
  //  this.getTextField().setText(origValue);
    value = origValue;
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size(); i >= 0; i--)
      listeners.elementAt(i).editingCanceled(ce);
  }

  protected void fireEditingStopped()
  {
    value = origValue;
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--)
      listeners.elementAt(i).editingStopped(ce);
  }
}
