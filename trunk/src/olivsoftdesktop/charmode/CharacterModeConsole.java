package olivsoftdesktop.charmode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.PrintStream;

import java.text.DecimalFormat;
import java.text.Format;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.util.Set;
import java.util.TreeMap;

import nmea.event.NMEAReaderListener;

import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;

import ocss.nmea.parser.Angle180;
import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Depth;
import ocss.nmea.parser.Distance;
import ocss.nmea.parser.GeoPos;
import ocss.nmea.parser.Pressure;
import ocss.nmea.parser.SolarDate;
import ocss.nmea.parser.Speed;

import ocss.nmea.parser.Temperature;
import ocss.nmea.parser.TrueWindDirection;
import ocss.nmea.parser.TrueWindSpeed;

import ocss.nmea.parser.UTCDate;

import olivsoftdesktop.utils.DesktopUtilities;
import olivsoftdesktop.utils.EscapeSeq;

import org.fusesource.jansi.AnsiConsole;

import user.util.GeomUtil;

public class CharacterModeConsole
{
  private final static boolean DEBUG = false;
  
  private static int cellSize   = 13;
  private static int dataSize   =  5;
  private static int keySize    =  3;
  private static int suffixSize =  3;
  
  private final static Format DF_22 = new DecimalFormat("#0.00");
  private final static Format DF_31 = new DecimalFormat("#00.0");
  private final static Format DF_3  = new DecimalFormat("##0");
  private final static Format DF_4  = new DecimalFormat("###0");
  private final static SimpleDateFormat SDF               = new SimpleDateFormat("dd MMM yyyy HH:mm:ss 'UTC'");
  private final static SimpleDateFormat SOLAR_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss 'Solar'");
  
  private static Map<String, AssociatedData> suffixes = new HashMap<String, AssociatedData>();
  static
  {
    suffixes.put("BSP", new AssociatedData("kt", DF_22));
    suffixes.put("HDG", new AssociatedData("t",  DF_3));
    suffixes.put("AWS", new AssociatedData("kt", DF_22));
    suffixes.put("SOG", new AssociatedData("kt", DF_22));
    suffixes.put("TWS", new AssociatedData("kt", DF_22));
    suffixes.put("CSP", new AssociatedData("kt", DF_22)); // Current Speed
    suffixes.put("AWA", new AssociatedData("",   DF_3));
    suffixes.put("TWA", new AssociatedData("",   DF_3));
    suffixes.put("HDG", new AssociatedData("t",  DF_3));
    suffixes.put("COG", new AssociatedData("t",  DF_3));
    suffixes.put("CDR", new AssociatedData("t",  DF_3)); // Current Direction
    suffixes.put("TWD", new AssociatedData("t",  DF_3));
//  suffixes.put("POS", new AssociatedData("",   null)); // Special Display (String). TODO Same for times and dates, and lat & long.
    suffixes.put("BAT", new AssociatedData("V",  DF_22));
    suffixes.put("MWT", new AssociatedData("C",  DF_31)); // Water Temp
    suffixes.put("MTA", new AssociatedData("C",  DF_31)); // Air Temp
    suffixes.put("MMB", new AssociatedData("mb", DF_4));  // Pressure at Sea Level
    suffixes.put("DBT", new AssociatedData("m",  DF_31)); // Depth
    suffixes.put("LOG", new AssociatedData("nm", DF_4));  // Log
    suffixes.put("CCS", new AssociatedData("kt", DF_22)); // Current Speed
    suffixes.put("CCD", new AssociatedData("t",  DF_3));  // Current Direction
    suffixes.put("TBF", new AssociatedData("m",  DF_4));  // Time buffer (in minutes) for current calculation
    suffixes.put("XTE", new AssociatedData("nm", DF_22)); 
  }

  private static Map<String, Integer> nonNumericData = new HashMap<String, Integer>();
  static
  {
    nonNumericData.put("POS", 24);
    nonNumericData.put("GDT", 40);
    nonNumericData.put("SLT", 40);
    nonNumericData.put("NWP", 10);
  }
  
  private static Map<String, String> colorMap = new HashMap<String, String>();
  static
  {
    colorMap.put("RED",     EscapeSeq.ANSI_RED);
    colorMap.put("BLACK",   EscapeSeq.ANSI_BLACK);
    colorMap.put("CYAN",    EscapeSeq.ANSI_CYAN);
    colorMap.put("GREEN",   EscapeSeq.ANSI_GREEN);
    colorMap.put("BLUE",    EscapeSeq.ANSI_BLUE);
    colorMap.put("WHITE",   EscapeSeq.ANSI_WHITE);
    colorMap.put("YELLOW",  EscapeSeq.ANSI_YELLOW);
    colorMap.put("MAGENTA", EscapeSeq.ANSI_MAGENTA);
  }

  private Map<String, ConsoleData> consoleData = null;

  public CharacterModeConsole()
  {
    super();
    try
    {
//    System.setOut(new PrintStream(new FileOutputStream("out.txt", true)));
//    System.setErr(new PrintStream(new FileOutputStream("err.txt", true)));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void displayConsole()
  {
//  System.out.println("Displaying Character mode console");
    // Init console here
    AnsiConsole.systemInstall();
    AnsiConsole.out.println(EscapeSeq.ANSI_CLS);
    
    try { initConsole(); }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    
    try { Thread.sleep(1000L); } catch (Exception ex) {} // Not nice, I know...
    
    final SuperBool first = new SuperBool(true);        
    // The small touchscreen (320x240) in 8x8 resolution has 40 columns per line of text, in 6x12, 54 columns.
    synchronized (NMEAContext.getInstance())
    {
      synchronized (NMEAContext.getInstance().getNMEAListeners())
      {
        NMEAContext.getInstance().addNMEAReaderListener(new NMEAReaderListener()
          {
            @Override
            public void manageNMEAString(String nmeaString) // TODO ? Will need an event like CacheHasBeenHit, for GPSd
            {
    //        super.manageNMEAString(nmeaString);
              try
              {
    //            plotMessage(DesktopUtilities.rpad(nmeaString, " ", 100));
                if (first.isTrue())
                {
                  AnsiConsole.out.println(EscapeSeq.ANSI_CLS);
                  first.setValue(false);
                  try { initConsole(); }
                  catch (Exception ex)
                  {
                    ex.printStackTrace();
                  }
                }
                Set<String> keys = consoleData.keySet();
                for (String s : keys)
                {
                  ConsoleData cd = consoleData.get(s);
                  int col = cd.getX();
                  int row = cd.getY();
                  String value = "";
                  if (nonNumericData.containsKey(s))
                  {
                    if ("POS".equals(s))                  
                      value = DesktopUtilities.lpad(GeomUtil.decToSex(((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lat, GeomUtil.NO_DEG, GeomUtil.NS), " ", 12) + 
                              DesktopUtilities.lpad(GeomUtil.decToSex(((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lng, GeomUtil.NO_DEG, GeomUtil.EW), " ", 12);
                    else if ("GDT".equals(s))  
                    {
                      UTCDate utcDate = (UTCDate)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_DATE_TIME, true);
                      value = DesktopUtilities.lpad(SDF.format(utcDate.getValue()), " ", 24);
                    }
                    else if ("SLT".equals(s))  
                    {
                      SolarDate solarDate = (SolarDate)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_SOLAR_TIME, true);
                      value = DesktopUtilities.lpad(SOLAR_DATE_FORMAT.format(solarDate.getValue()), " ", 24);
                    }
                    else if ("NWP".equals(s))  
                    {
                      value = (String)NMEAContext.getInstance().getCache().get(NMEADataCache.TO_WP, true);
                    }
                  }
                  else
                    value = DesktopUtilities.lpad(suffixes.get(s).getFmt().format(getValueFromCache(s)), " ", dataSize); // + " ";
                  String plot = plotOneValue(1 + ((col - 1) * cellSize), row + 1, value, colorMap.get(cd.getFgData()), colorMap.get(cd.getBgData()));
                  AnsiConsole.out.println(plot);          
    //            try { Thread.sleep(10); } catch (Exception ex) {}
                }
    //          plotMessage(DesktopUtilities.rpad(nmeaString, " ", 100));
              }
              catch (Exception ex)
              {
                System.err.println(nmeaString);
              }
            }
          });
      }
    }        
  }
  
  private double getValueFromCache(String key)
  {
    double value = 0;
    if ("BSP".equals(key))  // TODO Add times and dates
    {
      try { value = ((Speed) NMEAContext.getInstance().getCache().get(NMEADataCache.BSP)).getValue(); } catch (Exception ignore) {}
    }
    else if ("HDG".equals(key))
    {
      try { value = (int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.HDG_TRUE, true)).getDoubleValue(); } catch (Exception ex) {}
    }
    else if ("TWD".equals(key))
    {
      try { value = (int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.TWD, true)).getDoubleValue(); } catch (Exception ex) {}
    }
    else if ("AWS".equals(key))
    {
      try { value = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.AWS, true)).getDoubleValue(); } catch (Exception ex) {}
    }    
    else if ("AWA".equals(key))
    {
      try { value = (int)((Angle180)NMEAContext.getInstance().getCache().get(NMEADataCache.AWA, true)).getDoubleValue(); } catch (Exception ex) {}
    }    
    else if ("TWS".equals(key))
    {
      try { value = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.TWS, true)).getDoubleValue(); } catch (Exception ex) {}
    }    
    else if ("COG".equals(key))
    {
      try { value = ((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.COG)).getValue(); } catch (Exception ex) {}
    }    
    else if ("SOG".equals(key))
    {
      try { value = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.SOG)).getValue(); } catch (Exception ex) {}
    }    
    else if ("TWA".equals(key))
    {
      try { value = ((Angle180)NMEAContext.getInstance().getCache().get(NMEADataCache.TWA)).getValue(); } catch (Exception ignore) {}
    }    
    else if ("CDR".equals(key))
    {
      try { value = ((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.CDR)).getValue(); } catch (Exception ignore) {}
    }    
    else if ("CSP".equals(key))
    {
      try { value = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.CSP)).getValue(); } catch (Exception ignore) {}
    }
    else if ("LAT".equals(key))
    {
      try { value = ((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION)).lat; } catch (Exception ignore) {}
    }
    else if ("LNG".equals(key))
    {
      try { value = ((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION)).lng; } catch (Exception ignore) {}
    }
    else if ("LOG".equals(key))
    {
      try { value = ((Distance)NMEAContext.getInstance().getCache().get(NMEADataCache.LOG)).getValue(); } catch (Exception ignore) {}
    }
    else if ("MWT".equals(key))
    {
      try { value = ((Temperature)NMEAContext.getInstance().getCache().get(NMEADataCache.WATER_TEMP)).getValue(); } catch (Exception ignore) {}
    }
    else if ("MTA".equals(key))
    {
      try { value = ((Temperature)NMEAContext.getInstance().getCache().get(NMEADataCache.AIR_TEMP)).getValue(); } catch (Exception ignore) {}
    }
    else if ("MMB".equals(key))
    {
      try { value = ((Pressure)NMEAContext.getInstance().getCache().get(NMEADataCache.BARO_PRESS)).getValue(); } catch (Exception ignore) {}
    }
    else if ("DBT".equals(key))
    {
      try { value = ((Depth)NMEAContext.getInstance().getCache().get(NMEADataCache.DBT)).getValue(); } catch (Exception ignore) {}
    }    
    else if ("BAT".equals(key))
    {
      try { value = ((Float)NMEAContext.getInstance().getCache().get(NMEADataCache.BATTERY)).floatValue(); } catch (Exception ignore) {}
    }    
    else if ("CCS".equals(key))
    {
      try
      {
        Map<Long, NMEADataCache.CurrentDefinition> currentMap = 
                            ((Map<Long, NMEADataCache.CurrentDefinition>)NMEAContext.getInstance().getCache().get(NMEADataCache.CALCULATED_CURRENT));  //.put(bufferLength, new NMEADataCache.CurrentDefinition(bufferLength, new Speed(speed), new Angle360(dir)));
        Set<Long> keys = currentMap.keySet();
        if (keys.size() != 1 && DEBUG)
          plotMessage("Nb entry(ies) in Calculated Current Map:" + keys.size());
        for (Long l : keys)
          value = currentMap.get(l).getSpeed().getValue();
      }
      catch (Exception ignore) {}
    }
    else if ("CCD".equals(key))
    {
      try
      {
        Map<Long, NMEADataCache.CurrentDefinition> currentMap = 
                            ((Map<Long, NMEADataCache.CurrentDefinition>)NMEAContext.getInstance().getCache().get(NMEADataCache.CALCULATED_CURRENT));  //.put(bufferLength, new NMEADataCache.CurrentDefinition(bufferLength, new Speed(speed), new Angle360(dir)));
        Set<Long> keys = currentMap.keySet();
        if (keys.size() != 1 && DEBUG)
          plotMessage("Nb entry(ies) in Calculated Current Map:" + keys.size());
        for (Long l : keys)
          value = currentMap.get(l).getDirection().getValue();
      }
      catch (Exception ignore) {}
    }
    else if ("TBF".equals(key))
    {
      try
      {
        Map<Long, NMEADataCache.CurrentDefinition> currentMap = 
                            ((Map<Long, NMEADataCache.CurrentDefinition>)NMEAContext.getInstance().getCache().get(NMEADataCache.CALCULATED_CURRENT));  //.put(bufferLength, new NMEADataCache.CurrentDefinition(bufferLength, new Speed(speed), new Angle360(dir)));
        Set<Long> keys = currentMap.keySet();
        if (keys.size() != 1 && DEBUG)
          plotMessage("Nb entry(ies) in Calculated Current Map:" + keys.size());
        for (Long l : keys)
          value = l / (60 * 1000);
      }
      catch (Exception ignore) {}
    }
    else if ("XTE".equals(key))
    {
      try { value = ((Distance)NMEAContext.getInstance().getCache().get(NMEADataCache.XTE)).getValue(); } catch (Exception ignore) {}
    }    
    return value;
  }
  
  private void initConsole() throws Exception
  {
    String propFileName = System.getProperty("char.console.properties", "char.console.properties"); // "D:\\_mywork\\dev-corner\\olivsoft\\OlivSoftDesktop\\char.console.properties"
    consoleData = new HashMap<String, ConsoleData>();
    Properties consoleProps = new Properties();
    consoleProps.load(new FileReader(new File(propFileName)));
    Enumeration<String> props = (Enumeration<String>)consoleProps.propertyNames();
    while (props.hasMoreElements())
    {
      String prop = props.nextElement();
//    System.out.println("Prop:" + prop);
      String value = consoleProps.getProperty(prop);
      String[] elem = value.split(",");
      consoleData.put(prop.trim(),
                      new ConsoleData(prop.trim(), 
                                      Integer.parseInt(elem[0].trim()), 
                                      Integer.parseInt(elem[1].trim()), 
                                      elem[2].trim(), 
                                      elem[3].trim(), 
                                      elem[4].trim(), 
                                      elem[5].trim()));
    }
    // First display
    AnsiConsole.out.println(EscapeSeq.ANSI_CLS);
    String screenTitle = " - Character-mode NMEA console -";
    AnsiConsole.out.println(EscapeSeq.ansiLocate(1, 1) + EscapeSeq.ansiSetTextAndBackgroundColor(EscapeSeq.ANSI_WHITE, EscapeSeq.ANSI_BLACK) + EscapeSeq.ANSI_BOLD + screenTitle + EscapeSeq.ANSI_NORMAL + EscapeSeq.ANSI_DEFAULT_BACKGROUND + EscapeSeq.ANSI_DEFAULT_TEXT);
    
    // Ordered lists
    Map<Integer, Map<Integer, String>> table = new TreeMap<Integer, Map<Integer, String>>();
    Set<String> keys = consoleData.keySet();
    for (String s : keys)
    {
      ConsoleData cd = consoleData.get(s);
      int col = cd.getX();
      int row = cd.getY();
      Map<Integer, String> rowMap = table.get(row);
      if (rowMap == null)
      {
        rowMap = new TreeMap<Integer, String>();
        table.put(row, rowMap);
      }
      rowMap.put(col, cd.getKey());
    }
    
    Set<Integer> rows = table.keySet();
    for (Integer i : rows)
    {
      Map<Integer, String> cols = table.get(i); 
      Set<Integer> values = cols.keySet();
      List<CharData> consoleLine = new ArrayList<CharData>();
      for (Integer j : values)
      {
        String k = cols.get(j);
//      System.err.print(k + " ");
        if (nonNumericData.containsKey(k))
        {
//      if ("POS".equals(k))
          consoleLine.add(new CharData(k, 
                                       DesktopUtilities.lpad(" ", " ", nonNumericData.get(k)), // "  ** **.**'N *** **.**'E",  //    "                        ", 
                                       "", // suffixes.get(k).getSuffix(), 
                                       cellSize, 
                                       colorMap.get(consoleData.get(k).getFgData()),
                                       colorMap.get(consoleData.get(k).getBgData()),
                                       colorMap.get(consoleData.get(k).getFgTitle()),
                                       colorMap.get(consoleData.get(k).getBgTitle())));
        }
        else
          consoleLine.add(new CharData(k, 
                                       0, 
                                       dataSize, 
                                       suffixes.get(k).getFmt(), 
                                       suffixes.get(k).getSuffix(), 
                                       cellSize, 
                                       colorMap.get(consoleData.get(k).getFgData()),
                                       colorMap.get(consoleData.get(k).getBgData()),
                                       colorMap.get(consoleData.get(k).getFgTitle()),
                                       colorMap.get(consoleData.get(k).getBgTitle())));
      }
//    System.out.println();
      CharData[] cda = new CharData[consoleLine.size()];
      cda = consoleLine.toArray(cda);
      String dataLine = formatCharacterLine(1, 1 + i.intValue(), cda);
      AnsiConsole.out.println(dataLine);          
//    System.out.println(dataLine);
    }   
//  plotMessage("Console Ready...");
  }
  
  private static String formatCharacterLine(int x, int y, CharData[] lineData)
  {
    String line = "";
    line += EscapeSeq.ansiLocate(x, y);
    for (CharData cd : lineData)
    {
  //  int rpad = cd.getCellLen() - (cd.getKey().length() + cd.formattedValue().length() + cd.getSuffix().length() + 3);
      String s = EscapeSeq.ansiSetTextAndBackgroundColor(cd.getTitleColor(), cd.getTitleBackground()) + 
      EscapeSeq.ANSI_BOLD + 
      cd.getKey() + 
      EscapeSeq.ansiSetTextAndBackgroundColor(cd.getValueColor(), cd.getValueBackground()) + 
      EscapeSeq.ANSI_BOLD + 
      " " + cd.formattedValue() + " " + 
      EscapeSeq.ansiSetTextAndBackgroundColor(cd.getTitleColor(), cd.getTitleBackground()) + 
      EscapeSeq.ANSI_BOLD + 
      DesktopUtilities.rpad(cd.getSuffix(), " ", suffixSize) +  // "(" + Integer.toString(rpad) + ")" +
   // DesktopUtilities.rpad("", " ", rpad) +                  
      EscapeSeq.ANSI_NORMAL + 
      EscapeSeq.ANSI_DEFAULT_BACKGROUND + EscapeSeq.ANSI_DEFAULT_TEXT;
      line += s;
    }
    
    return line + "          ";
  }
  
  private static String plotOneValue(int x, int y, String value, String valueColor, String bgColor)
  {
    String line = "";
    line += EscapeSeq.ansiLocate(x + keySize, y);
    String s = EscapeSeq.ansiSetTextAndBackgroundColor(valueColor, bgColor) + 
               EscapeSeq.ANSI_BOLD + 
               " " + value + " ";
    line += s;
    return line;
  }

  private static void plotMessage(String mess)
  {
    String line = "";
    int x = 0, y = 10;
    line = EscapeSeq.ansiLocate(x, y) +
           EscapeSeq.ansiSetTextAndBackgroundColor(EscapeSeq.ANSI_WHITE, EscapeSeq.ANSI_BLUE) + 
           EscapeSeq.ANSI_BOLD + 
           mess +       
           EscapeSeq.ANSI_NORMAL + 
           EscapeSeq.ANSI_DEFAULT_BACKGROUND + 
           EscapeSeq.ANSI_DEFAULT_TEXT;;
    
    AnsiConsole.out.println(line);          
  }
  
  private static class AssociatedData
  {
    private String suffix;
    private Format fmt;

    public String getSuffix()
    {
      return suffix;
    }

    public Format getFmt()
    {
      return fmt;
    }

    public AssociatedData(String suffix, Format fmt)
    {
      this.suffix = suffix;
      this.fmt = fmt;
    }
  }
  
  private static class CharData
  {
    private String key = "XXX";
    private double value = 0d;
    private String charValue = "";
    private Format fmt = null;
    private String suffix = "";
    private int valueLen = 5;
    private int cellLen = 15;
    
    private String valueColor = EscapeSeq.ANSI_RED;
    private String valueBackground = EscapeSeq.ANSI_BLACK;
    private String titleColor = EscapeSeq.ANSI_RED;
    private String titleBackground = EscapeSeq.ANSI_BLACK;

    public CharData(String key, String value, String suffix, int cellLen, 
                    String valueColor, String valueBackground, String titleColor, String titleBackground)
    {
      this.key = key;
      this.charValue = value;
      this.suffix = suffix;
      this.cellLen = cellLen;
      this.valueColor = valueColor;
      this.valueBackground = valueBackground;
      this.titleColor = titleColor;
      this.titleBackground = titleBackground;
    }

    public CharData(String key, double value, int valueLen, Format fmt, String suffix, int cellLen, 
                    String valueColor, String valueBackground, String titleColor, String titleBackground)
    {
      this.key = key;
      this.value = value;
      this.valueLen = valueLen;
      this.fmt = fmt;
      this.suffix = suffix;
      this.cellLen = cellLen;
      this.valueColor = valueColor;
      this.valueBackground = valueBackground;
      this.titleColor = titleColor;
      this.titleBackground = titleBackground;
    }
    
    public String formattedValue()
    {
      String str = "";
      if (this.fmt != null)
        str = DesktopUtilities.lpad(this.fmt.format(this.value), " ", this.valueLen);
      else
        str = this.charValue;
      return str;
    }
    
    public String getKey()
    {
      return key;
    }

    public double getValue()
    {
      return value;
    }

    public Format getFmt()
    {
      return fmt;
    }

    public String getSuffix()
    {
      return suffix;
    }

    public int getCellLen()
    {
      return cellLen;
    }

    public String getValueColor()
    {
      return valueColor;
    }

    public String getValueBackground()
    {
      return valueBackground;
    }

    public String getTitleColor()
    {
      return titleColor;
    }

    public String getTitleBackground()
    {
      return titleBackground;
    }
  }
  
  private static class SuperBool
  {
    private boolean b;
    public SuperBool(boolean b)
    {
      this.b = b;
    }
    public void setValue(boolean b)
    {
      this.b = b;
    }
    public boolean isTrue()
    {
      return this.b;
    }
  }  
 
  private static class ConsoleData
  {
    private String key;
    private int x;
    private int y;
    private String fgData;
    private String bgData;
    private String fgTitle;

    public String getKey()
    {
      return key;
    }

    public int getX()
    {
      return x;
    }

    public int getY()
    {
      return y;
    }

    public String getFgData()
    {
      return fgData;
    }

    public String getBgData()
    {
      return bgData;
    }

    public String getFgTitle()
    {
      return fgTitle;
    }

    public String getBgTitle()
    {
      return bgTitle;
    }
    private String bgTitle;
    public ConsoleData(String key, int x, int y, String fgData, String bgData, String fgTitle, String bgTitle)
    {
      this.key = key;
      this.x = x;
      this.y = y;
      this.fgData = fgData;
      this.bgData = bgData;
      this.fgTitle = fgTitle;
      this.bgTitle = bgTitle;
    }
  }
  
  // Propeties test 
  public static void main(String[] args) throws Exception
  {
    String propFileName = System.getProperty("console.definition", "char.console.properties"); // "D:\\_mywork\\dev-corner\\olivsoft\\OlivSoftDesktop\\char.console.properties"
    Map<String, ConsoleData> consoleData = new HashMap<String, ConsoleData>();
    Properties consoleProps = new Properties();
    consoleProps.load(new FileReader(new File(propFileName)));
    Enumeration<String> props = (Enumeration<String>)consoleProps.propertyNames();
    while (props.hasMoreElements())
    {
      String prop = props.nextElement();
//    System.out.println("Prop:" + prop);
      String value = consoleProps.getProperty(prop);
      String[] elem = value.split(",");
      consoleData.put(prop.trim(),
                      new ConsoleData(prop.trim(), 
                                      Integer.parseInt(elem[0].trim()), 
                                      Integer.parseInt(elem[1].trim()), 
                                      elem[2].trim(), 
                                      elem[3].trim(), 
                                      elem[4].trim(), 
                                      elem[5].trim()));
    }
    // First display
    AnsiConsole.out.println(EscapeSeq.ANSI_CLS);
    String screenTitle = "Character-mode NMEA console";
    AnsiConsole.out.println(EscapeSeq.ansiLocate(1, 1) + EscapeSeq.ansiSetTextAndBackgroundColor(EscapeSeq.ANSI_WHITE, EscapeSeq.ANSI_BLACK) + EscapeSeq.ANSI_BOLD + screenTitle + EscapeSeq.ANSI_NORMAL + EscapeSeq.ANSI_DEFAULT_BACKGROUND + EscapeSeq.ANSI_DEFAULT_TEXT);
    
    // Ordered lists
    Map<Integer, Map<Integer, String>> table = new TreeMap<Integer, Map<Integer, String>>();
    Set<String> keys = consoleData.keySet();
    for (String s : keys)
    {
      ConsoleData cd = consoleData.get(s);
      int col = cd.getX();
      int row = cd.getY();
      Map<Integer, String> rowMap = table.get(row);
      if (rowMap == null)
      {
        rowMap = new TreeMap<Integer, String>();
        table.put(row, rowMap);
      }
      rowMap.put(col, cd.getKey());
    }
    
    Set<Integer> rows = table.keySet();
    for (Integer i : rows)
    {
      Map<Integer, String> cols = table.get(i); 
      Set<Integer> values = cols.keySet();
      List<CharData> consoleLine = new ArrayList<CharData>();
      for (Integer j : values)
      {
        String k = cols.get(j);
//      System.out.print(k + " "); 
        if ("POS".equals(k))
          consoleLine.add(new CharData(k, // POS!
                                       "  ** **.**'N *** **.**'E", 
                                 //    "                        ", 
                                       suffixes.get(k).getSuffix(), 
                                       cellSize, 
                                       colorMap.get(consoleData.get(k).getFgData()),
                                       colorMap.get(consoleData.get(k).getBgData()),
                                       colorMap.get(consoleData.get(k).getFgTitle()),
                                       colorMap.get(consoleData.get(k).getBgTitle())));
        else
          consoleLine.add(new CharData(k, 
                                       0, 
                                       dataSize, 
                                       suffixes.get(k).getFmt(), 
                                       suffixes.get(k).getSuffix(), 
                                       cellSize, 
                                       colorMap.get(consoleData.get(k).getFgData()),
                                       colorMap.get(consoleData.get(k).getBgData()),
                                       colorMap.get(consoleData.get(k).getFgTitle()),
                                       colorMap.get(consoleData.get(k).getBgTitle())));
      }
//    System.out.println();
      CharData[] cda = new CharData[consoleLine.size()];
      cda = consoleLine.toArray(cda);
      String dataLine = formatCharacterLine(1, 1 + i.intValue(), cda);
      AnsiConsole.out.println(dataLine);          
//    System.out.println(dataLine);          
    }
    try { Thread.sleep(2000); } catch (Exception ex) {}

    // Subsequent displays
    int i = 0;
    while (i < 10)
    {
      keys = consoleData.keySet();
      for (String s : keys)
      {
        ConsoleData cd = consoleData.get(s);
        int col = cd.getX();
        int row = cd.getY();
        String value = "";
        if ("POS".equals(s))
          value = "  12 34.56'N 123 45.67'W";
        else
          value = DesktopUtilities.lpad(suffixes.get(s).getFmt().format(Math.random() * 100), " ", dataSize); // + " ";
        String plot = plotOneValue(1 + ((col - 1) * cellSize), row + 1, value, colorMap.get(cd.getFgData()), colorMap.get(cd.getBgData()));
        AnsiConsole.out.println(plot);          
  //    try { Thread.sleep(100); } catch (Exception ex) {}
      }
      try { Thread.sleep(1000); } catch (Exception ex) {}
      i++;
    }
  }
}
