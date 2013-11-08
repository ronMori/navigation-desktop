package olivsoftdesktop.param;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.EventObject;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import olivsoftdesktop.ctx.DesktopContext;
import olivsoftdesktop.ctx.JTableFocusChangeListener;

import olivsoftdesktop.param.widget.ColorPickerCellEditor;
import olivsoftdesktop.param.widget.FieldAndButtonCellEditor;
import olivsoftdesktop.param.widget.FieldPlusFinder;

import olivsoftdesktop.param.widget.FieldPlusFontPicker;

import olivsoftdesktop.utils.DesktopUtilities;
import olivsoftdesktop.utils.SerialPortList;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public final class ParamPanel 
           extends JPanel 
{
  @SuppressWarnings("compatibility:-7630492256577044663")
  private final static long serialVersionUID = 1L;
  
  private static boolean verbose = "true".equals(System.getProperty("verbose", "false"));
  
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel topPanel = new JPanel();
  private JPanel bottomPanel = new JPanel();
  private JPanel centerPane = new JPanel();
  private JLabel fileNameLabel = new JLabel();
  
  private JEditorPane helpTextArea = new JEditorPane();
  private JScrollPane textAreaScrollPane = new JScrollPane(helpTextArea);

  private final static String KEY      = "Param";
  private final static String VALUE    = "Value";
  
  public final static int PRM_NAME  = 0;
  public final static int PRM_VALUE = 1;

  final static String[] names = {KEY, VALUE};
  
  private transient TableModel dataModel;

  private static Object[][] data = null; // new Object[ParamData.labels.length][names.length];
  private transient Object[][] localData = new Object[0][0];
  
  private JTable table;
  private JScrollPane scrollPane;
  private BorderLayout borderLayout2 = new BorderLayout();
  private JLabel titleLabel = new JLabel();

  public ParamPanel()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public static Object[][] getData()
  {
    return data;
  }
  
  /**
   * Factory settings
   */
  private static void initTableValues()
  {    
    for (int i=0; i<ParamData.NB_PARAMETERS; i++)
    {
      data[i][PRM_NAME]   = new ParamDisplayLabel(ParamData.getLabels()[i], ParamData.getHelpText()[i]);
      data[i][PRM_VALUE]   = oneFactorySetting(i);
    }
  }

  private static Object oneFactorySetting(int settingID)
  {
    Object it = null;
    switch (settingID)
    {
      case ParamData.NMEA_CHANNEL:
        it = new ListOfChannels("Serial");
        break;
      case ParamData.NMEA_SERIAL_PORT:
        it = new ListOfSerialPorts("COM1");
        break;
      case ParamData.NMEA_TCP_PORT:
        it = "3001";
        break;
      case ParamData.NMEA_UDP_PORT:
        it = "8001";
        break;
      case ParamData.NMEA_HOST_NAME:
        it = "localhost";
        break;
      case ParamData.NMEA_HTTP_PORT:
        it = "9999"; // Chrome does not like 6666
        break;
      case ParamData.NMEA_BAUD_RATE:
        it = "4800";
        break;
      case ParamData.FOREGROUND_FONT_COLOR:
        it = new ParamColor(Color.red, "");
        break;
      case ParamData.NMEA_CONFIG_FILE:
        it = new PropertiesFile("nmea.properties");
        break;
      case ParamData.SAILFAX_CATALOG:
        it = new XMLFile("pac-fax-data.xml");
        break;
      case ParamData.DB_LOCATION:
        it = new DataDirectory("DB Files Location", ".." + File.separator + "all-db");
        break;
      case ParamData.BACKGROUND_IMAGE:
        it = new ImageFile("");
        break;
      case ParamData.DELTA_T:
        it = new Double(65.984);
        break;
      case ParamData.NMEA_DATA_STREAM:
        it = new ListOfDataStreams("NMEA Port");
        break;
      case ParamData.AIRMAIL_LOCATION:
        it = new DataDirectory("Airmail Location", ".");
        break;
      case ParamData.PLAY_SOUNDS:
        it = Boolean.valueOf(true);
        break;
      case ParamData.TIDE_FLAVOR:
        it = new ListOfTideFlavors("XML");
        break;
      case ParamData.DESKTOP_MESSAGE:
        it = "Navigation Console";
        break;
      case ParamData.INTERNAL_FRAMES_TRANSPARENCY:
        it = new Float(0.75);
        break;
      case ParamData.COMPUTE_SUN_MOON_DATA:
        it = Boolean.valueOf(true);
        break;
      case ParamData.USE_CHART_APP:
      case ParamData.USE_NMEA_APP:
      case ParamData.USE_STAR_FINDER_APP:
      case ParamData.USE_SAILFAX_APP:
      case ParamData.USE_LUNAR_APP:
      case ParamData.USE_SIGHT_RED_APP:
      case ParamData.USE_PUBLISHER_APP:
      case ParamData.USE_GOOGLE_APP:
      case ParamData.USE_TIDES_APP:
        it = Boolean.valueOf(true);
        break;
      case ParamData.NMEA_RMI_PORT:
        it = "1099";
        break;
      case ParamData.BG_WIN_FONT_COLOR:
        it = new ParamColor(Color.blue, "");
        break;
      case ParamData.MAX_TIDE_RECENT_STATIONS:
        it = new Integer(5);
        break;
      case ParamData.BOAT_ID:
        it = "DP";
        break;
      case ParamData.MAX_ANALOG_BSP:
        it = new Double(10);
        break;
      case ParamData.MAX_ANALOG_TWS:
        it = new Double(50);
      case ParamData.DEFAULT_FONT:
        it = new Font("Arial", Font.PLAIN, 12)
          {
            @Override
            public String toString()
            {
              return FontPanel.fontToString(this);
            }
          }; 
        break;
      default:
        break;
    }
    if ("true".equals(System.getProperty("verbose", "false")))
      System.out.println("... oneFactorySetting: [" + it.toString() + "], as a [" + it.getClass().getName() + "]");
    return it;
  }
  
  /**
   * From Config file
   */
  public static void setUserValues()
  { 
    if (data == null)
    {
      data = new Object[ParamData.getLabels().length][names.length];
      try
      {
        FileInputStream fis = new FileInputStream(ParamData.PARAM_FILE_NAME);
        DOMParser parser = DesktopContext.getInstance().getParser();
        XMLDocument doc = null;
        synchronized (parser)
        {
          parser.parse(fis);
          doc = parser.getDocument();
        }
        for (int i=0; i < ParamData.getLabels().length; i++)
        {
          NodeList nl = doc.selectNodes("/desktop-parameters/param[@id='" + Integer.toString(i) + "']");
          try
          {
            data[i][PRM_NAME] = new ParamDisplayLabel(ParamData.getLabels()[i], ParamData.getHelpText()[i]);
            String s = nl.item(0).getFirstChild().getNodeValue();        
            if (i == ParamData.NMEA_CHANNEL)
              data[i][PRM_VALUE] = new ListOfChannels(s);
            else if (i == ParamData.NMEA_CONFIG_FILE)
              data[i][PRM_VALUE] = new PropertiesFile(s);                 
            else if (i == ParamData.SAILFAX_CATALOG)
              data[i][PRM_VALUE] = new XMLFile(s);                 
            else if (i == ParamData.BACKGROUND_IMAGE)
              data[i][PRM_VALUE] = new ImageFile(s);                 
            else if (i == ParamData.DB_LOCATION)
              data[i][PRM_VALUE] = new DataDirectory("DB Files Location", s);                 
            else if (i == ParamData.DELTA_T ||
                     i == ParamData.MAX_ANALOG_BSP ||
                     i == ParamData.MAX_ANALOG_TWS) 
              data[i][PRM_VALUE] = new Double(s);
            else if (i == ParamData.NMEA_DATA_STREAM)
              data[i][PRM_VALUE] = new ListOfDataStreams(s);              
            else if (i == ParamData.AIRMAIL_LOCATION)
              data[i][PRM_VALUE] = new DataDirectory("Airmail Location", s);                 
            else if (i == ParamData.NMEA_SERIAL_PORT)
              data[i][PRM_VALUE] = new ListOfSerialPorts(s);
            else if (i == ParamData.TIDE_FLAVOR)
              data[i][PRM_VALUE] = new ListOfTideFlavors(s);
            else if (i == ParamData.PLAY_SOUNDS || i == ParamData.COMPUTE_SUN_MOON_DATA)
              data[i][PRM_VALUE] = new Boolean(s);
            else if (i == ParamData.INTERNAL_FRAMES_TRANSPARENCY) 
              data[i][PRM_VALUE] = new Float(s);
            else if (i == ParamData.USE_CHART_APP || 
                     i == ParamData.USE_NMEA_APP || 
                     i == ParamData.USE_STAR_FINDER_APP || 
                     i == ParamData.USE_SAILFAX_APP || 
                     i == ParamData.USE_LUNAR_APP || 
                     i == ParamData.USE_SIGHT_RED_APP || 
                     i == ParamData.USE_PUBLISHER_APP || 
                     i == ParamData.USE_GOOGLE_APP || 
                     i == ParamData.USE_TIDES_APP)
              data[i][PRM_VALUE] = new Boolean(s);
            else if (i == ParamData.BG_WIN_FONT_COLOR ||
                     i == ParamData.FOREGROUND_FONT_COLOR)
              data[i][PRM_VALUE] = new ParamColor(DesktopUtilities.buildColor(s), "");
            else if (i == ParamData.MAX_TIDE_RECENT_STATIONS) 
              data[i][PRM_VALUE] = new Integer(s);
            else if (i == ParamData.DEFAULT_FONT) 
              data[i][PRM_VALUE] = FontPanel.stringToFont(s);
            else 
              data[i][PRM_VALUE] = s; // All the string fall in this bucket
          }
          catch (Exception ex)
          {
            if (verbose)
              System.out.println("ParamPanel:" + ex.toString() + " for [" + ParamData.getLabels()[i] + "]");
            data[i][PRM_VALUE] = oneFactorySetting(i);
          }
//        System.out.println("** setUserValue: i=" + Integer.toString(i) + " : [" + data[i][PRM_VALUE].toString() + "], a [" + data[i][PRM_VALUE].getClass().getName() + "] for [" + ParamData.labels[i] + "].");
        }
        fis.close();
      }
      catch (Exception e)
      {
        initTableValues();
        if (verbose) 
          System.out.println("Defaulting values");
  //    e.printStackTrace();
      }
    }
  }

  // By Category    
  private int currentCategoryIndex     = -1;
  
  // Must match CategoryPanel.CATEGORIES
  private static Object[] categoryIndexes = new Object[]
  {
    new int[] // General
    {
      ParamData.DESKTOP_MESSAGE,
      ParamData.INTERNAL_FRAMES_TRANSPARENCY,
      ParamData.BACKGROUND_IMAGE,
      ParamData.NMEA_DATA_STREAM,
      ParamData.PLAY_SOUNDS,
      ParamData.BG_WIN_FONT_COLOR,
      ParamData.FOREGROUND_FONT_COLOR,
      ParamData.DEFAULT_FONT
    },
    new int[] // NMEA
    { 
      ParamData.NMEA_CHANNEL, 
      ParamData.NMEA_SERIAL_PORT,
      ParamData.NMEA_TCP_PORT,
      ParamData.NMEA_UDP_PORT,
      ParamData.NMEA_RMI_PORT,
      ParamData.NMEA_BAUD_RATE,      
      ParamData.NMEA_HOST_NAME,
      ParamData.NMEA_HTTP_PORT,
      ParamData.MAX_ANALOG_BSP,
      ParamData.MAX_ANALOG_TWS /*,
      ParamData.NMEA_CONFIG_FILE */ /* Unused */ /*,
      ParamData.NMEA_SIMULATION */  /* Unused */
    }, 
    new int[] // SAILFAX
    { 
      ParamData.SAILFAX_CATALOG,
      ParamData.AIRMAIL_LOCATION
    },
    new int[] // Databases
    {
      ParamData.DB_LOCATION // Charts, NMEA Logs, Tides
    },
    new int[] // ALMANAC
    {
      ParamData.DELTA_T
    },
    new int[] // Locator
    {
      ParamData.BOAT_ID
    },
    new int[] // TIDES
    {
      ParamData.TIDE_FLAVOR,
      ParamData.COMPUTE_SUN_MOON_DATA,
      ParamData.MAX_TIDE_RECENT_STATIONS
    },
    new int[] // APPLICATIONS
    {
      ParamData.USE_CHART_APP,
      ParamData.USE_NMEA_APP,
      ParamData.USE_STAR_FINDER_APP,
      ParamData.USE_SAILFAX_APP,
      ParamData.USE_LUNAR_APP,
      ParamData.USE_SIGHT_RED_APP,
      ParamData.USE_PUBLISHER_APP,
      ParamData.USE_GOOGLE_APP,
      ParamData.USE_TIDES_APP      
    }
  };
  
  private Object[][] mkDataArray(int idx)
  {
    int len = ((int[])categoryIndexes[idx]).length;
    Object[][] oa = new Object[len][2];
    for (int i=0; i<len; i++)
    {
      int index = ((int[])categoryIndexes[idx])[i];
      oa[i] = new Object[] {data[index][PRM_NAME], data[index][PRM_VALUE]};
    }    
    return oa;
  }
  
  
  public void  setGnlPrm(String help)
  {
    helpTextArea.setText(help);
    setGnlPrm();
  }
  
  public void  setGnlPrm()
  {
    setObject(mkDataArray(CategoryPanel.GNL_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.GNL_CATEGORY_INDEX;
  }
  
  public void  setNMEAPrm(String help)
  {
    helpTextArea.setText(help);
    setNMEAPrm();
  }
  public void  setNMEAPrm()
  {
    setObject(mkDataArray(CategoryPanel.NMEA_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.NMEA_CATEGORY_INDEX;
  }
  
  public void setSailFaxPrm(String help)
  {
    helpTextArea.setText(help);
    setSailFaxPrm();
  }
  public void setSailFaxPrm()
  {
    setObject(mkDataArray(CategoryPanel.SAILFAX_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.SAILFAX_CATEGORY_INDEX;
  }
  
  public void  setChartLibPrm(String help)
  {
    helpTextArea.setText(help);
    setChartLibPrm();
  }
  public void  setChartLibPrm()
  {
    setObject(mkDataArray(CategoryPanel.DATABASES_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.DATABASES_CATEGORY_INDEX;
  }
  
  public void  setLocatorPrm(String help)
  {
    helpTextArea.setText(help);
    setLocatorPrm();
  }
  public void  setAlmanacPrm(String help)
  {
    helpTextArea.setText(help);
    setAlmanacPrm();
  }
  public void  setAlmanacPrm()
  {
    setObject(mkDataArray(CategoryPanel.ALMANAC_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.ALMANAC_CATEGORY_INDEX;
  }
  
  public void  setLocatorPrm()
  {
    setObject(mkDataArray(CategoryPanel.LOCATOR_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.LOCATOR_CATEGORY_INDEX;
  }

  public void setTidePrm(String help)
  {
    helpTextArea.setText(help);
    setTidePrm();
  }
  public void setTidePrm()
  {
    setObject(mkDataArray(CategoryPanel.TIDE_CATEGORY_INDEX));
    currentCategoryIndex = CategoryPanel.TIDE_CATEGORY_INDEX;
  }
  
  public void setApplicationPrm(String help)
  {
    helpTextArea.setText(help);
    setApplicationPrm();
  }
  public void setApplicationPrm()
  {
    setObject(mkDataArray(CategoryPanel.TIDE_APPLICATIONS_INDEX));
    currentCategoryIndex = CategoryPanel.TIDE_APPLICATIONS_INDEX;
  }
  
  public void updateData()
  {
//  List<String> reloadDataRequired = new ArrayList<String>();
    // Update data
    if (currentCategoryIndex > -1)
    {
      for (int i=0; i<localData.length; i++)
      {
        String before = (data[((int[])categoryIndexes[currentCategoryIndex])[i]][PRM_VALUE]).toString();
        String after = localData[i][PRM_VALUE].toString();
        if (i == ParamData.DEFAULT_FONT)
          after = FontPanel.fontToString((Font)localData[i][PRM_VALUE]);
        if (verbose) 
          System.out.println("Comparing [" + after + "] to [" + before + "]");
        if (!before.equals(after))
        {
          boolean ok2go = true;
          // Validation
          int currentIndex = ((int[])categoryIndexes[currentCategoryIndex])[i];          
          if (currentIndex == ParamData.NMEA_HTTP_PORT  ||
              currentIndex == ParamData.NMEA_TCP_PORT  ||
              currentIndex == ParamData.NMEA_UDP_PORT  ||
              currentIndex == ParamData.NMEA_RMI_PORT  ||
              currentIndex == ParamData.NMEA_BAUD_RATE ||
              currentIndex == ParamData.MAX_TIDE_RECENT_STATIONS) // The int values
          {
            try 
            { 
              int x = Integer.parseInt(after); 
              localData[i][PRM_VALUE] = new Integer(x);
              System.setProperty("max.recent.stations", Integer.toString(x));
            }
            catch (Exception e) 
            { 
              JOptionPane.showMessageDialog(this, 
                                            "Bad integer value " + after + ", " + before, 
                                            "Parameters modified", 
                                            JOptionPane.ERROR_MESSAGE);
              ok2go = false; 
            }           
          }
          else if (currentIndex == ParamData.DELTA_T ||
                   currentIndex == ParamData.MAX_ANALOG_BSP ||
                   currentIndex == ParamData.MAX_ANALOG_TWS)
          {
            try 
            { 
              double x = Double.parseDouble(after); 
              localData[i][PRM_VALUE] = new Double(x);
            }
            catch (Exception e) 
            { 
              JOptionPane.showMessageDialog(this, 
                                            "Bad double value " + after + ", " + before, 
                                            "Parameters modified", 
                                            JOptionPane.ERROR_MESSAGE);
              ok2go = false; 
            }           
          }
          else if (currentIndex == ParamData.INTERNAL_FRAMES_TRANSPARENCY)
          {
            try 
            { 
              float x = Float.parseFloat(after); 
              if (x < 0 || x > 1)
              {
                JOptionPane.showMessageDialog(this, 
                                              "Bad value for [" + after + "], [" + before + "]\n" +
                                              "for [" + ParamData.getLabels()[ParamData.INTERNAL_FRAMES_TRANSPARENCY] + "]\n" +
                                              "Must be between 0 and 1.", 
                                              "Parameters modified", 
                                              JOptionPane.ERROR_MESSAGE);
                ok2go = false; 
              }
              else
                localData[i][PRM_VALUE] = new Float(x);
            }
            catch (Exception e) 
            { 
              JOptionPane.showMessageDialog(this, 
                                            "Bad float value for " + after + ", " + before, 
                                            "Parameters modified", 
                                            JOptionPane.ERROR_MESSAGE);
              ok2go = false; 
            }           
          }
          // Ok to go
          if (ok2go)
          {
            if (verbose) 
              System.out.println("Updating " + before + " with " + after + " (i=" + currentIndex + ")");
            data[((int[])categoryIndexes[currentCategoryIndex])[i]][PRM_NAME] = localData[i][PRM_NAME];
            data[((int[])categoryIndexes[currentCategoryIndex])[i]][PRM_VALUE] = localData[i][PRM_VALUE];

            // Component restart required?
            if (currentIndex == ParamData.NMEA_HOST_NAME ||
                currentIndex == ParamData.NMEA_HTTP_PORT)
            {              
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isLocatorRunning())
                mess += "Locator ";
              if (DesktopContext.getInstance().getTopFrame().isStarFinderRunning())
                mess += ((mess.length() > 0?"and ":"") + "Star Finder");
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else if (currentIndex == ParamData.NMEA_CONFIG_FILE ||
                     currentIndex == ParamData.NMEA_CHANNEL ||
                     currentIndex == ParamData.NMEA_SERIAL_PORT ||
                     currentIndex == ParamData.NMEA_TCP_PORT ||
                     currentIndex == ParamData.NMEA_UDP_PORT ||
                     currentIndex == ParamData.NMEA_RMI_PORT ||
                     currentIndex == ParamData.NMEA_BAUD_RATE ||
                     currentIndex == ParamData.MAX_ANALOG_BSP ||
                     currentIndex == ParamData.MAX_ANALOG_TWS)
            {              
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isNMEAConsoleRunning())
                mess += "NMEA Console ";
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else if (currentIndex == ParamData.TIDE_FLAVOR)
            {              
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isTideRunning())
                mess += "Tides application ";
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else if (currentIndex == ParamData.BACKGROUND_IMAGE)
            {
              DesktopContext.getInstance().fireBackgroundImageChanged();
            }
            else if (currentIndex == ParamData.BG_WIN_FONT_COLOR)
            {
              DesktopContext.getInstance().fireBGWinColorChanged();
            }
            else if (currentIndex == ParamData.SAILFAX_CATALOG)
            {
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isSailFaxRunning())
                mess += "SailFax ";
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else if (currentIndex == ParamData.DB_LOCATION)
            {
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isChartLibRunning())
                mess += "Chart Library ";
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else if (currentIndex == ParamData.DELTA_T)
            {
              String mess = "";
              if (DesktopContext.getInstance().getTopFrame().isAPRunning())
                mess += "Almanac Publisher ";
              if (mess.trim().length() > 0)
              {
                mess += " must be restarted for your changes to take effect.";
                JOptionPane.showMessageDialog(this, mess, "Parameters modified", JOptionPane.INFORMATION_MESSAGE);
              }
            }
          }              
        }
      }
    }
  }

  private void setObject(Object oaa)
  {
    updateData();
    localData = (Object[][])oaa;
    ((AbstractTableModel)dataModel).fireTableDataChanged();
    table.repaint();
  }
  
  private void jbInit() throws Exception
  {
    // Init Look and Feel list
//  UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
    
    this.setLayout(borderLayout1);
    this.setSize(new Dimension(156, 300));
//  parent.setSize(new Dimension(400, 378));
    bottomPanel.setLayout(new BorderLayout());
    centerPane.setLayout(borderLayout2);
    fileNameLabel.setText(" ");
    helpTextArea.setPreferredSize(new Dimension(20, 60));
    helpTextArea.setEditable(false);
    helpTextArea.setText("...");
    helpTextArea.setBackground(new Color(247, 255, 196));
    helpTextArea.setFont(new Font("Tahoma", 0, 10));
    titleLabel.setText("Parameters for Applications");
    topPanel.add(fileNameLabel, null);
    topPanel.add(titleLabel, null);
    this.add(topPanel, BorderLayout.NORTH);
//  bottomPanel.add(saveButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomPanel.add(textAreaScrollPane, BorderLayout.CENTER);

    this.add(bottomPanel, BorderLayout.SOUTH);
    this.add(centerPane, BorderLayout.CENTER);
    //  centerPane.add(scrollPane, BorderLayout.CENTER);
    initTable();
    SelectionListener listener = new SelectionListener(table);
    table.getSelectionModel().addListSelectionListener(listener);
    table.getColumnModel().getSelectionModel().addListSelectionListener(listener);

    setUserValues();
  }

  private void initTable()
  {
    dataModel = new AbstractTableModel()
    {
        @SuppressWarnings("compatibility:9070027359489543434")
        private final static long serialVersionUID = 1L;

      public int getColumnCount()
      { return names.length; }
      public int getRowCount()
      { return localData.length; }
      public Object getValueAt(int row, int col)
      { return localData[row][col]; }
      public String getColumnName(int column)
      { return names[column]; }
      public Class getColumnClass(int c)
      {
//      System.out.println("Class requested column " + c + ", type:" + getValueAt(0, c).getClass());
        return getValueAt(0, c).getClass();
      }
      public boolean isCellEditable(int row, int col)
      { return (col == 1); } // Second column only
      public void setValueAt(Object aValue, int row, int column)
      { localData[row][column] = aValue; }
    };
    table = new JTable(dataModel)
    {
        @SuppressWarnings("compatibility:-6277413828840729633")
        private final static long serialVersionUID = 1L;
      /* For the tooltip text */
      public Component prepareRenderer(TableCellRenderer renderer,
                                       int rowIndex, 
                                       int vColIndex) 
      {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (c instanceof JComponent) 
        {
          JComponent jc = (JComponent)c;
          try { jc.setToolTipText(getValueAt(rowIndex, vColIndex).toString()); } catch (Exception e) { e.printStackTrace(); }
        }
        return c;
      }
    };
    // Set a specific #Editor for a special column/line cell
    TableColumn secondColumn = table.getColumn(VALUE);
    secondColumn.setCellEditor(new ParamEditor());
    secondColumn.setCellRenderer(new ColorTableCellRenderer());

    scrollPane = new JScrollPane(table);  
    centerPane.add(scrollPane, BorderLayout.CENTER);
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new JTableFocusChangeListener(table));
  }

  public static class ColorTableCellRenderer
       extends JLabel
    implements TableCellRenderer
  {
    @SuppressWarnings("compatibility:-7810679012468272669")
    private final static long serialVersionUID = 1L;

    transient Object curValue = null;

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column)
    {
      curValue = value;
      return this;
    }
     
    public void paintComponent(Graphics g)
    {
      if (verbose) 
        System.out.println("- Rendering [" + curValue.toString() + "], a " + curValue.getClass().getName());
      if (curValue instanceof ParamColor)
      {
        if (curValue != null)
          g.setColor(((ParamColor)curValue).getColor());
//      g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.fillRect(2, 2, Math.min(getWidth() - 5, 20), getHeight() - 5);
        g.setColor(Color.black);
        g.drawRect(2, 2, Math.min(getWidth() - 5, 20), getHeight() - 5);
      }
      else if (curValue instanceof ListOfValues)
        g.drawString(((ListOfValues)curValue).toString(), 1, getHeight() - 1);
      else
      {
        if (curValue != null)
        {
//        System.out.println("curValue is a " + curValue.getClass().getName());
//        g.drawString((String)curValue, 1, getHeight() - 1);
          g.drawString(curValue.toString(), 1, getHeight() - 1);
        }
      }
    }
  }

  public static class DataFile
  {
    String fileName = "";
    public DataFile(String s)
    { fileName = s; }
    public void setFileName(String str)
    { fileName = str; }
    public String getFileName()
    { return fileName; }
    public String toString()
    { return fileName; }
  }  
  public static class XMLFile extends DataFile 
  {
    public XMLFile(String s)
    {
      super(s);
    }
  }
  public static class ImageFile extends DataFile 
  {
    public ImageFile(String s)
    {
      super(s);
    }
  }
  public static class PropertiesFile extends DataFile 
  {
    public PropertiesFile(String s)
    {
      super(s);
    }
  }
  public static class NMEAFile extends DataFile 
  {
    public NMEAFile(String s)
    {
      super(s);
    }
  }
  public static class LoggedDataDirectory extends DataFile
  { 
    public LoggedDataDirectory(String s)
    {
      super(s);
    }
  }
    
  public static class DataDirectory
  {
    private String desc;
    private String value;
    
    public DataDirectory(String description, String dirValue)
    {
      desc = description;
      value = dirValue;
    }
    
    public String getDesc() { return desc; }
    public String toString() { return value; }
    public void setValue(String s) { value = s; }
  }
  public static class DataPath
  {    
    private DataDirectory[] path = null;
    
    public DataPath(String s)
    {
      this.setValue(s);
    }
    
    public DataPath(DataDirectory[] dd)
    {
      this.path = dd;
    }
    
    public DataDirectory[] getPath()
    {
      return path;
    }
    
    public void setValue(String s) 
    {
      // Parse the String, build the array
      String[] data = s.split(File.pathSeparator);
      if (data != null)
      {
        path = new DataDirectory[data.length];
        for (int i=0; i<data.length; i++)
          path[i] = new DataDirectory("DataPath", data[i]);
      }
    }
    public String toString()
    {
      String str = "";
      for (int i=0; path != null && i<path.length; i++)
      {
        str += ((str.length() > 0?File.pathSeparator:"") + path[i].toString());
      }
      return str;
    }
  }
    
  private final static String[] channelValues = {"Serial",
                                                 "TCP",
                                                 "UDP",
                                                 "RMI",
                                                 "XML over HTTP"};
  private final static String[] streamValues  = {"NMEA Port",
                                                 "HTTP"};
  private final static String[] tideFlavorValues  = {"XML" /*, // Reduced to only one element
                                                     "SQL",
                                                     "SQLITE" ,
                                                     "SERIALIZED",
                                                     "JSON" */};
  public static class ParamColor
  {
    Color color;
    String colorName;
    public ParamColor(Color c, String name)
    {
      this.color     = c;
      this.colorName = name;
    }

    public boolean equals(ParamColor pc)
    {
      if (pc.getColor().equals(this.color) && pc.toString().equals(this.colorName))
        return true;
      else
        return false;
    }

    public Color getColor()
    { return this.color; }

    public String toString_1()
    { return this.colorName; }

    public String toString()
    { return DesktopUtilities.colorToString(this.color); }
  }
  
  public static class ParamEditor 
       extends JComponent 
    implements TableCellEditor
  {
    @SuppressWarnings("compatibility:68475031554043737")
    private final static long serialVersionUID = 1L;

    JComponent componentToApply;
    protected transient Vector<CellEditorListener> listeners;
    protected transient Object originalValue;
    
    JTextField tf                   = new JTextField();
    JComboBox channelList           = new JComboBox(channelValues);
    JComboBox streamList            = new JComboBox(streamValues);
    JComboBox tideFlavorList        = new JComboBox(tideFlavorValues);
    JComboBox serialPortList        = new JComboBox(SerialPortList.listSerialPorts());
    FieldAndButtonCellEditor xml    = new FieldAndButtonCellEditor(FieldPlusFinder.XML_TYPE);
    FieldAndButtonCellEditor nmea   = new FieldAndButtonCellEditor(FieldPlusFinder.NMEA_TYPE);
    FieldAndButtonCellEditor img    = new FieldAndButtonCellEditor(FieldPlusFinder.IMG_TYPE);
    FieldAndButtonCellEditor prop   = new FieldAndButtonCellEditor(FieldPlusFinder.PROPERTIES_TYPE);
    FieldAndButtonCellEditor ldd    = new FieldAndButtonCellEditor(FieldPlusFinder.DIRECTORY_TYPE);
    FieldPlusFontPicker      ffp    = null; // new FieldPlusFontPicker(null);

    public ParamEditor()
    {
      super();
      listeners = new Vector<CellEditorListener>();
    }
    
    public Component getTableCellEditorComponent(JTable table, 
                                                 Object value, 
                                                 boolean isSelected, 
                                                 int row, 
                                                 int column)
    {
      originalValue = value;
      if (verbose) 
        System.out.println("Value at row " + row + ", col " + column + " is a [" + value.getClass().getName() + "] : [" + value.toString() + "]");
      if (column == 1 && value instanceof ListOfChannels)
      {
        componentToApply = channelList;
        channelList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ListOfDataStreams)
      {
        componentToApply = streamList;
        streamList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ListOfTideFlavors)
      {
        componentToApply = tideFlavorList;
        tideFlavorList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ListOfSerialPorts)
      {
        componentToApply = serialPortList;
        serialPortList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ParamColor)
      {
        componentToApply = new ColorPickerCellEditor(((ParamColor)value).getColor()); // colorList;
      }
      else if (column == 1 && value instanceof XMLFile)
      {
        componentToApply = xml;
        xml.setText(value.toString());
      }
      else if (column == 1 && value instanceof ImageFile)
      {
        componentToApply = img;
        img.setText(value.toString());
      }
      else if (column == 1 && value instanceof PropertiesFile)
      {
        componentToApply = prop;
        prop.setText(value.toString());
      }
      else if (column == 1 && value instanceof NMEAFile)
      {
        componentToApply = nmea;
        nmea.setText(value.toString());
      }
      else if (column == 1 && value instanceof DataDirectory)
      {
        componentToApply = ldd;
        ldd.setText(value.toString());
      }
      else if (column == 1 && value instanceof Boolean)
      {
        componentToApply = new JCheckBox();
        componentToApply.addFocusListener(new FocusAdapter()
          {
            public void focusLost(FocusEvent e)
            {
              stopCellEditing(); // Will validate the cell content!
            }                      
          });        
      }
      else if (column == 1 && value instanceof ListOfChannels)
      {
        componentToApply = channelList;
        channelList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ListOfDataStreams)
      {
        componentToApply = channelList;
        streamList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof ListOfSerialPorts)
      {
        componentToApply = channelList;
        serialPortList.setSelectedItem(value);
      }
      else if (column == 1 && value instanceof Double)
      {
//      System.out.println("Colunm:" + column  + ", Value is a " + value.getClass().getName());
        componentToApply = tf;
        tf.setFont(new Font("Dialog", 0, 9));
        tf.setText(((Double)value).toString());
      }
      else if (column == 1 && value instanceof Float)
      {
      //      System.out.println("Colunm:" + column  + ", Value is a " + value.getClass().getName());
        componentToApply = tf;
        tf.setFont(new Font("Dialog", 0, 9));
        tf.setText(((Float)value).toString());
      }
      else if (column == 1 && value instanceof Integer)
      {
      //      System.out.println("Colunm:" + column  + ", Value is a " + value.getClass().getName());
        componentToApply = tf;
        tf.setFont(new Font("Dialog", 0, 9));
        tf.setText(((Integer)value).toString());
      }
      else if (column == 1 && value instanceof LoggedDataDirectory)
      {
        componentToApply = ldd;
        ldd.setText(((LoggedDataDirectory)value).toString());
      }
      else if (column == 1 && value instanceof Font)
      {
        ffp = new FieldPlusFontPicker((Font)value);
        componentToApply = ffp;
        ffp.setText(FontPanel.fontToString((Font)value));
      }
      else if (column == 1)
      {
        System.out.println(">> Default renderer for Colunm:" + column  + ", row:" + row + ", value is a " + value.getClass().getName() + ":" + value.toString());
        componentToApply = tf;
        tf.setFont(new Font("Dialog", 0, 9));
        tf.setText((String)value);
      }
//    System.out.println("getCellEditorComponent invoked line " + row);
      return componentToApply;
    }

    public Object getCellEditorValue()
    {
//    System.out.println("getCellEditorValue invoked");
      if (componentToApply instanceof JTextField)
        return ((JTextField)componentToApply).getText();
      else if (componentToApply instanceof JComboBox)      
      {
//      System.out.println("OriginalValue:" + originalValue.getClass().getName());
        if (originalValue instanceof ListOfChannels) 
          return new ListOfChannels((String)((JComboBox)componentToApply).getSelectedItem());
        else if (originalValue instanceof ListOfDataStreams) 
          return new ListOfDataStreams((String)((JComboBox)componentToApply).getSelectedItem());
        else if (originalValue instanceof ListOfSerialPorts) 
          return new ListOfSerialPorts((String)((JComboBox)componentToApply).getSelectedItem());
        else if (originalValue instanceof ListOfTideFlavors) 
          return new ListOfTideFlavors((String)((JComboBox)componentToApply).getSelectedItem());
      }
      else if (componentToApply instanceof FieldAndButtonCellEditor)
      { 
        if (componentToApply.equals(xml))
          return new XMLFile((String)xml.getCellEditorValue());
        else if (componentToApply.equals(img))
          return new ImageFile((String)img.getCellEditorValue());
        else if (componentToApply.equals(prop))
          return new PropertiesFile((String)prop.getCellEditorValue());
        else if (componentToApply.equals(nmea))
          return new PropertiesFile((String)nmea.getCellEditorValue());
        else if (componentToApply.equals(ldd))
          return new LoggedDataDirectory((String)ldd.getCellEditorValue());
      }
      else if (componentToApply instanceof FieldPlusFontPicker)
      {
        Object obj = ((FieldPlusFontPicker)componentToApply).getCellEditorValue();        
        if (obj instanceof Font) // FIXME Fix that shit...
          return (Font)obj;
        else if (obj instanceof String)
        {
          String font = (String)((FieldPlusFontPicker)componentToApply).getCellEditorValue();
          if (font != null)
            return FontPanel.stringToFont(font);
          else
            return null;
        }
        else 
        {
      //  System.out.println("Original Value is a " + originalValue.getClass().getName());
          return (Font)originalValue;
        }
      }
      else if (componentToApply instanceof ColorPickerCellEditor)
      {
        Color c = (Color)((ColorPickerCellEditor)componentToApply).getCellEditorValue();
        if (c != null)
        {
          ParamColor pc = new ParamColor(c, ""); //GnlUtilities.colorToString(c));
          return pc;
        }
        else 
        {
//        System.out.println("Original Value is a " + originalValue.getClass().getName());
          ParamColor pc = (ParamColor)originalValue;
          return pc;
        }
      }
      else if (componentToApply instanceof JCheckBox)
      {
        return new Boolean(((JCheckBox)componentToApply).isSelected()?"true":"false");
      }
      else
      {
        System.out.println("Null!! " + (componentToApply!=null?componentToApply.getClass().getName():" null"));
        return null;
      }
      return null;
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
    { fireEditingCanceled(); }

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
      ChangeEvent ce = new ChangeEvent(this);
      for (int i=listeners.size(); i>=0; i--)
        (listeners.elementAt(i)).editingCanceled(ce);
    }
    protected void fireEditingStopped()
    {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i=(listeners.size() -1); i>=0; i--)
        (listeners.elementAt(i)).editingStopped(ce);
    }
  }
  
  public int[] getSelectRows()
  {
    return table.getSelectedRows();
  }
  
  private Object[][] addLineInTable(String k,
                                    String v)
  {
    return addLineInTable(k, v, data);
  }
  private Object[][] addLineInTable(String k,
                                    String v,
                                    Object[][] d)
  {
    int len = 0;
    if (d != null)
      len = d.length;
    Object[][] newData = new Object[len + 1][names.length];
    for (int i=0; i<len; i++)
    {
      for (int j=0; j<names.length; j++)
        newData[i][j] = d[i][j];
    }
    newData[len][0] = k;
    newData[len][1] = v;
//  System.out.println("Adding " + k + ":" + v);
    return newData;
  }
  
//  public static void saveValues_actionPerformed(ActionEvent e)
//  {
//    saveParameters();
//  }

  public static void saveParameters()
  {
    XMLDocument doc = new XMLDocument();
    Element elem = doc.createElement("desktop-parameters");
    doc.appendChild(elem);
    for (int i=0; i<data.length; i++)
    {
      Element param = doc.createElement("param");
      elem.appendChild(param);
      param.setAttribute("id", Integer.toString(i));
      Text val = doc.createTextNode("text#");
      Object valueObject = data[i][PRM_VALUE];
      if (valueObject != null)
      {
        if (valueObject instanceof Font)
          val.setNodeValue(FontPanel.fontToString((Font)valueObject));
        else
          val.setNodeValue(valueObject.toString());
      }
      else
        JOptionPane.showMessageDialog(null, "Null value in line " + i);
      param.appendChild(val);
    }
    OutputStream os = null;
    try
    {
      os = new FileOutputStream(ParamData.PARAM_FILE_NAME);
      doc.print(os);
      os.flush();
      os.close();
//    parent.setParameterChanged(false); // reset
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, ex.toString(), "Writing Parameters", JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
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
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0)
      {
        Object o = localData[selectedRow][PRM_NAME];
        helpTextArea.setText(((ParamDisplayLabel)o).getHelp());
      }
      else
      {
        helpTextArea.setText("");
      }
    }
  }

  static class ParamDisplayLabel
  {
    private String label = "";
    private String help  = "";
    
    public ParamDisplayLabel() {}
    public ParamDisplayLabel(String label) 
    {
      this.label = label;
      this.help  = label;
    }
    public ParamDisplayLabel(String label, String help) 
    {
      this.label = label;
      this.help  = help;
    }

    public String getLabel()
    {
      return label;
    }

    public String getHelp()
    {
      return help;
    }
    
    public String toString()
    { return getLabel(); }
  }
}