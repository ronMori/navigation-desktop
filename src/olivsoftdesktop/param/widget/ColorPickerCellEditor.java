package olivsoftdesktop.param.widget;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

public class ColorPickerCellEditor
  extends FieldPlusColorPicker
  implements TableCellEditor
{
  protected transient Vector listeners;
  protected transient Color origValue;

  public ColorPickerCellEditor(Color o)
  {
    super(o);
    this.getTextField().setBackground(o);
    listeners = new Vector();
    this.addFocusListener(new FocusAdapter()
        {
          public void focusLost(FocusEvent e)
          {
            stopCellEditing(); // Will validate the cell content!
          }                      
        });
  }

  public void setText(String str)
  {
    this.getTextField().setText(str);
  }

  public Component getTableCellEditorComponent(JTable table, Object value, 
                                               boolean isSelected, int row, 
                                               int column)
  {
    if (value == null)
    {
      this.getTextField().setText("");
      return this;
    }
    if (value instanceof String)
      this.getTextField().setText((String) value);
    else
      this.getTextField().setText(value.toString());
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    origValue = (Color)value;
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
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size(); i >= 0; i--)
      ((CellEditorListener) listeners.elementAt(i)).editingCanceled(ce);
  }

  protected void fireEditingStopped()
  {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--)
      ((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
  }
}
