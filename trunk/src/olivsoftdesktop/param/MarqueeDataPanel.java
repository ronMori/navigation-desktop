package olivsoftdesktop.param;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Map;

import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import olivsoftdesktop.utils.JTableFocusChangeListener;

public final class MarqueeDataPanel
  extends JPanel
{
  public enum TableResizeValue { OFF, ON };
  
  private static Map<String, String> dataMap = new HashMap<String, String>();
  static
    {
      dataMap.put("BSP", "Boat Speed");
      dataMap.put("HDG", "Heading (true)");
      dataMap.put("TWD", "True Wind Direction (true)");
      dataMap.put("TWS", "True Wind Speed");
      dataMap.put("TWA", "True Wind Angle");
      dataMap.put("AWS", "Apparent Wind Speed");
      dataMap.put("AWA", "Apparent Wind Angle");
      dataMap.put("DBT", "Depth Below Transducer");
      dataMap.put("MWT", "Mean Water Temperature");
      dataMap.put("LOG", "Log Value");
      dataMap.put("MAT", "Mean Air Temperature");
      dataMap.put("PRS", "Atmospheric Pressure");
      dataMap.put("XTE", "Cross Track Error");
      dataMap.put("BAT", "Battery Voltage");
      dataMap.put("CDR", "Current Direction (true)");
      dataMap.put("CSP", "Current Speed"); 
      dataMap.put("NWP", "Next Waypoint");
    };
  
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel topPanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JPanel centerPane = new JPanel();

  static final String PREFIX = "Prefix";
  static final String DESCR  = "Description";
  static final String SELECT = "Select";

  static final String[] names =
  { PREFIX, DESCR, SELECT };

  private transient TableModel dataModel;

  protected static Object[][] data = new Object[0][0];

  private JTable table;
  private JScrollPane scrollPane;
  private BorderLayout borderLayout2 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton upButton = new JButton("Move Up");
  private JButton downButton = new JButton("Move Down");

  private int tableResize = JTable.AUTO_RESIZE_OFF;
  private String userString;

  public MarqueeDataPanel()
  {
    this(null);
  }
  public MarqueeDataPanel(String strData)
  {
    this.userString = strData;
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
    this.setLayout(borderLayout1);
    this.setSize(new Dimension(300, 350));
    this.setPreferredSize(new Dimension(300, 350));
    this.setMinimumSize(new Dimension(300, 350));
    bottomPanel.setLayout(gridBagLayout1);
    centerPane.setLayout(borderLayout2);
    this.add(topPanel, BorderLayout.NORTH);
    
    bottomPanel.add(upButton, null);
    bottomPanel.add(downButton, null);
    upButton.setEnabled(false);
    downButton.setEnabled(false);
    this.add(bottomPanel, BorderLayout.SOUTH);
    
    upButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
//      System.out.println("Moving lines up");                
        int[] sr = getSelectRows();
        
        if (sr.length > 0) // This test should not be necessary
        {
          if (sr[0] > 0)
          {
            int prev = sr[0] - 1;
            Object[][] newData = new Object[data.length][names.length];
            for (int i=0; i<prev; i++) // Before selected line
              newData[i] = data[i];
            
            for (int i=0; i<sr.length; i++) // Selected line(s)
              newData[prev + i] = data[sr[i]];
            
            newData[prev + sr.length] = data[prev]; // Move the previous behind
            
            for (int i=(prev + sr.length + 1); i<data.length; i++) // After last selected line
              newData[i] = data[i];
            
            data = newData;
            ((AbstractTableModel) dataModel).fireTableDataChanged();
          }
        }
      }
    });
    downButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent actionEvent)
      {
        System.out.println("Moving lines down");
        int[] sr = getSelectRows();
        
        if (sr.length > 0) // This test should not be necessary
        {
          if (sr[sr.length - 1] < data.length)
          {
            int next = sr[sr.length - 1] + 1;
            Object[][] newData = new Object[data.length][names.length];
            for (int i=0; i<sr[0]; i++) // Before first selected line
              newData[i] = data[i];
            
            newData[sr[0]] = data[next]; // Move the one behind in front

            for (int i=0; i<sr.length; i++) // Selected line(s)
              newData[sr[i] + 1] = data[sr[i]];          
            
            for (int i=next + 1; i<data.length; i++) // After last selected line
              newData[i] = data[i];
            
            data = newData;
            ((AbstractTableModel) dataModel).fireTableDataChanged();
          }
        }
      }
    });

    this.add(centerPane, BorderLayout.CENTER);
    initTable();
    MarqueeDataPanel.SelectionListener listener = new MarqueeDataPanel.SelectionListener(table);
    table.getSelectionModel().addListSelectionListener(listener);
    table.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    
    // Populate table
    String[] tableData = this.userString.split(",");
    for (String s : tableData)
    {
      data = addLineInTable(s, dataMap.get(s), Boolean.TRUE, data);
    }
    // What's not in the map:
    Set<String> keys = dataMap.keySet();
    for (String k : keys)
    {
      if (!contains(tableData, k)) 
        data = addLineInTable(k, dataMap.get(k), Boolean.FALSE, data);
    }
  }
  
  private boolean contains(String[] array, String k)
  {
    boolean b = false;
    for (String s : array)
    {
      if (k.equals(s))
      {
        b = true;
        break;
      }
    }    
    return b;
  }

  private void initTable()
  {
    dataModel = new AbstractTableModel()
        {
          public int getColumnCount()
          {
            return names.length;
          }

          public int getRowCount()
          {
            return data == null? 0: data.length;
          }

          public Object getValueAt(int row, int col)
          {
            return data[row][col];
          }

          public String getColumnName(int column)
          {
            return names[column];
          }

          public Class getColumnClass(int c)
          {
    //      System.out.println("Class requested column " + c + ", type:" + getValueAt(0, c).getClass());
            if (c == 2)
              return Boolean.class;
            else
              return String.class;
          }

          public boolean isCellEditable(int row, int col)
          {
            return col > 1;
          }

          public void setValueAt(Object aValue, int row, int column)
          {
            data[row][column] = aValue;
          }
        };
    table = new JTable(dataModel)
        {
          /* For the tooltip text */

          public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex)
          {
            Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
            if (c instanceof JComponent)
            {
              JComponent jc = (JComponent) c;
              try
              {
                jc.setToolTipText(getValueAt(rowIndex, vColIndex).toString());
              }
              catch (Exception ex)
              {
                System.err.println("ParamPanel:" + ex.getMessage());
              }
            }
            return c;
          }
        };
    
    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    TableColumn firstColumn = table.getColumn(PREFIX);
    firstColumn.setPreferredWidth(50);

    TableColumn secondColumn = table.getColumn(DESCR);
    secondColumn.setPreferredWidth(300);

    TableColumn thirdColumn = table.getColumn(SELECT);
//  thirdColumn.setCellEditor(new ParamEditor());
    thirdColumn.setPreferredWidth(50);
    
//  table.setAutoResizeMode(tableResize); // Allows horizontal scroll
    scrollPane = new JScrollPane(table);
    centerPane.add(scrollPane, BorderLayout.CENTER);
    
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new JTableFocusChangeListener(table));
  }

  public void setTableResize(TableResizeValue tr)
  {
    if (tr == TableResizeValue.OFF)
      this.tableResize = JTable.AUTO_RESIZE_OFF;
    else
      this.tableResize = JTable.AUTO_RESIZE_ALL_COLUMNS;
    table.setAutoResizeMode(tableResize);
  }

  public TableResizeValue getTableResize()
  {
    if (tableResize == JTable.AUTO_RESIZE_ALL_COLUMNS)
      return TableResizeValue.ON;
    else
      return TableResizeValue.OFF;
  }

  public int[] getSelectRows()
  {
    return table.getSelectedRows();
  }

  private Object[][] addLineInTable(String prefix, String descr, boolean select,Object[][] d)
  {
    int len = 0;
    if (d != null)
      len = d.length;
    Object[][] newData = new Object[len + 1][names.length];
    for (int i = 0; i < len; i++)
    {
      for (int j = 0; j < names.length; j++)
        newData[i][j] = d[i][j];
    }
    newData[len][0] = prefix;
    newData[len][1] = descr;
    newData[len][2] = select;
    data = newData;
    ((AbstractTableModel) dataModel).fireTableDataChanged();
    return newData;
  }

  public Object[][] getData()
  {
    return data;
  }
  
  public String getStringData()
  {
    String str = "";
    for (Object[] line : data)
    {
      if (((Boolean)line[2]).booleanValue())
      {
        String id = (String)line[0];
        str += ((str.trim().length() > 0 ? "," : "") + id);
      }
    }
    return str;
  }

  public void setData(Object[][] newData)
  {
    data = newData;
    ((AbstractTableModel) dataModel).fireTableDataChanged();
  }

  public class SelectionListener
    implements ListSelectionListener
  {
    JTable table;

    SelectionListener(JTable table)
    {
      this.table = table;
    }

    public void valueChanged(ListSelectionEvent e)
    {
      int[] selectedRow = table.getSelectedRows();
//    System.out.println(selectedRow.length + " row(s) selected");
      upButton.setEnabled(selectedRow.length > 0);
      downButton.setEnabled(selectedRow.length > 0);
    }
  }
  
  /** For tests */
  public static void main(String[] args)
  {
    MarqueeDataPanel mdp = new MarqueeDataPanel("BSP,HDG,TWD,TWS,TWA,AWS,AWA,DBT,MWT,LOG,MAT,PRS,XTE,BAT,CDR,CDS,NWP");

    JOptionPane.showMessageDialog(null, mdp, "Sample", JOptionPane.PLAIN_MESSAGE);
  }
}
