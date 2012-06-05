package olivsoftdesktop.param.widget;

import java.awt.Component;
import javax.swing.JTable;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.util.Vector;
import javax.swing.event.ChangeEvent;

public class FieldAndButtonCellEditor 
     extends FieldPlusFinder 
  implements TableCellEditor 
{
  protected transient Vector listeners;
  protected transient String origValue;

  public FieldAndButtonCellEditor(int t)
  {
    super(t);
    listeners = new Vector();
  }

  public void setText(String str)
  {
    this.getTextField().setText(str);
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
    }
    if (value instanceof String)
      this.getTextField().setText((String)value);
    else
      this.getTextField().setText(value.toString());
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    origValue = this.getTextField().getText();
    return this;
  }

  public Object getCellEditorValue()
  {
//  return new ParamPanel.DataFile(this.getTextField().getText());
    return this.getTextField().getText();
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
    this.getTextField().setText(origValue);
    ChangeEvent ce = new ChangeEvent(this);
    for (int i=listeners.size(); i>=0; i--)
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
  }

  protected void fireEditingStopped()
  {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i=listeners.size() - 1; i>=0; i--)
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
  }
}