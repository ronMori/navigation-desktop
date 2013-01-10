package olivsoftdesktop.param;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class CategoryPanel
     extends JPanel
{
  public final static String GNL_CATEGORY            = "Desktop";
  public final static String NMEA_CATEGORY           = "NMEA Console";
  public final static String SAILFAX_CATEGORY        = "SailFax";
  public final static String DATABASES_CATEGORY      = "Databases (Charts, NMEA Journal)";
  public final static String ALMANAC_CATEGORY        = "Nautical Almanac";
  public final static String LOCATOR_CATEGORY        = "Google Locator";
  public final static String TIDE_CATEGORY           = "Tides";

  public final static String APPLICATIONS_CATEGORY   = "Applications";
  
  private final static String GNL_CATEGORY_HELP            = "Desktop Parameters";
  private final static String NMEA_CATEGORY_HELP           = "NMEA Console parameters";
  private final static String SAILFAX_CATEGORY_HELP        = "SailFax parameters";
  private final static String DATABASES_CATEGORY_HELP      = "Databases (Charts, NMEA Journal) location.";
  private final static String ALMANAC_CATEGORY_HELP        = "Nautical Almanac parameters";
  private final static String LOCATOR_CATEGORY_HELP        = "Google locator parameters";
  private final static String TIDE_CATEGORY_HELP           = "Tides parameters";
  private final static String APPLICATIONS_CATEGORY_HELP   = "Applications parameters, show or hide";

  public final static String[] CATEGORIES = { GNL_CATEGORY, 
                                              NMEA_CATEGORY, 
                                              SAILFAX_CATEGORY, 
                                              DATABASES_CATEGORY, 
                                              ALMANAC_CATEGORY,
                                              LOCATOR_CATEGORY,
                                              TIDE_CATEGORY,
                                              APPLICATIONS_CATEGORY};
  
  public final static int GNL_CATEGORY_INDEX       = 0;
  public final static int NMEA_CATEGORY_INDEX      = 1;
  public final static int SAILFAX_CATEGORY_INDEX   = 2;
  public final static int DATABASES_CATEGORY_INDEX = 3;
  public final static int ALMANAC_CATEGORY_INDEX   = 4;
  public final static int LOCATOR_CATEGORY_INDEX   = 5;
  public final static int TIDE_CATEGORY_INDEX      = 6;
  public final static int TIDE_APPLICATIONS_INDEX  = 7;
  
  private BorderLayout borderLayout1 = new BorderLayout();

  private JSplitPane jSplitPane = new JSplitPane();
  private CategoryJTreeHolder tree = new CategoryJTreeHolder(this);
  private ParamPanel table = new ParamPanel();

  public CategoryPanel()
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
    this.setLayout(borderLayout1);
    this.add(jSplitPane, BorderLayout.CENTER);
    jSplitPane.setDividerLocation(0.4D);
        
    jSplitPane.add(tree, JSplitPane.LEFT);
    jSplitPane.add(table, JSplitPane.RIGHT);
  }
  
  public void getEventFormTree(String str)
  {
    if (GNL_CATEGORY.equals(str))
      table.setGnlPrm(GNL_CATEGORY_HELP);
    else if (NMEA_CATEGORY.equals(str))
      table.setNMEAPrm(NMEA_CATEGORY_HELP);
    else if (SAILFAX_CATEGORY.equals(str))
      table.setSailFaxPrm(SAILFAX_CATEGORY_HELP);
    else if (DATABASES_CATEGORY.equals(str))
      table.setChartLibPrm(DATABASES_CATEGORY_HELP);
    else if (ALMANAC_CATEGORY.equals(str))
      table.setAlmanacPrm(ALMANAC_CATEGORY_HELP);
    else if (LOCATOR_CATEGORY.equals(str))
      table.setLocatorPrm(LOCATOR_CATEGORY_HELP);
    else if (TIDE_CATEGORY.equals(str))
      table.setTidePrm(TIDE_CATEGORY_HELP);
    else if (APPLICATIONS_CATEGORY.equals(str))
      table.setApplicationPrm(APPLICATIONS_CATEGORY_HELP);
  }
  
  public void finalPrmUpdate()
  {
    table.updateData();
  }
}
