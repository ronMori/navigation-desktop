package olivsoftdesktop.charmode;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.io.File;
import java.io.FileReader;

import java.text.DecimalFormat;
import java.text.Format;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import nmea.event.NMEAReaderListener;

import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;

import ocss.nmea.parser.Angle180;
import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Depth;
import ocss.nmea.parser.Distance;
import ocss.nmea.parser.GeoPos;
import ocss.nmea.parser.Pressure;
import ocss.nmea.parser.Speed;
import ocss.nmea.parser.Temperature;

import olivsoftdesktop.utils.DesktopUtilities;

import user.util.GeomUtil;

/*
 * Created for debugging purpose.
 * The parasite outputs (out & err) would be visible in the console, and not on the JPanel...
 */
public class CharModePanel
  extends javax.swing.JPanel
{
  private static int cellSize   = 13;
  private static int dataSize   =  5;
  private static int keySize    =  3;
  private static int suffixSize =  3;
  
  private final static Format DF_22 = new DecimalFormat("#0.00");
  private final static Format DF_31 = new DecimalFormat("#00.0");
  private final static Format DF_3  = new DecimalFormat("##0");
  private final static Format DF_4  = new DecimalFormat("###0");
  
  private transient SuperBool first = new SuperBool(true);        

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
    suffixes.put("POS", new AssociatedData("",   null)); // Special Display (String). TODO Same for times and dates, and lat & long.
    suffixes.put("BAT", new AssociatedData("V",  DF_22));
    suffixes.put("MWT", new AssociatedData("C",  DF_31)); // Water Temp
    suffixes.put("MTA", new AssociatedData("C",  DF_31)); // Air Temp
    suffixes.put("MMB", new AssociatedData("mb", DF_4));  // Pressure at Sea Level
    suffixes.put("DBT", new AssociatedData("m",  DF_31)); // Depth
    suffixes.put("LOG", new AssociatedData("nm", DF_4));  // Log
    suffixes.put("CCS", new AssociatedData("kt", DF_22)); // Current Speed
    suffixes.put("CCD", new AssociatedData("t",  DF_3));  // Current Direction
    suffixes.put("TBF", new AssociatedData("m",  DF_4));  // Time buffer (in minutes) for current calculation
  }
  
  private static Map<String, Color> colorMap = new HashMap<String, Color>();
  static
  {
    colorMap.put("RED",     Color.red);
    colorMap.put("BLACK",   Color.black);
    colorMap.put("CYAN",    Color.cyan);
    colorMap.put("GREEN",   Color.green);
    colorMap.put("BLUE",    Color.blue);
    colorMap.put("WHITE",   Color.white);
    colorMap.put("YELLOW",  Color.yellow);
    colorMap.put("MAGENTA", Color.magenta);
  }

  private transient Map<String, ConsoleData> consoleData = null;

  public CharModePanel()
  {
    Dimension dim = new Dimension(600, 400);
    this.setSize(dim);
    this.setPreferredSize(dim);
    try { initConsole(); }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }


    Thread readerThread = new Thread()
      {
        public void run()
        {
          synchronized (NMEAContext.getInstance().getNMEAListeners())
          {
            NMEAContext.getInstance().addNMEAReaderListener(new NMEAReaderListener()
              {
                @Override
                public void manageNMEAString(String nmeaString) // TODO ? Will need an event like CacheHasBeenHit, for GPSd
                {
                  super.manageNMEAString(nmeaString);
                  try
                  {
                    repaint();
                  }
                  catch (Exception ex)
                  {
                    System.out.println(nmeaString);
                  }
                }
              });
          }
        }
      };
    readerThread.start();
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
        if (keys.size() != 1)
          System.out.println("1 - Nb entry(ies) in Calculated Current Map:" + keys.size());
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
        if (keys.size() != 1)
          System.out.println("2 - Nb entry(ies) in Calculated Current Map:" + keys.size());
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
        if (keys.size() != 1)
          System.out.println("3 - Nb entry(ies) in Calculated Current Map:" + keys.size());
        for (Long l : keys)
          value = l / (60 * 1000);
      }
      catch (Exception ignore) {}
    }
    return value;
  }
  
  private void initConsole() throws Exception
  {
    // "D:\\_mywork\\dev-corner\\olivsoft\\OlivSoftDesktop\\char.console.properties"
    String propFileName = System.getProperty("char.console.properties", "char.console.properties"); 
    consoleData = new HashMap<String, ConsoleData>();
    Properties consoleProps = new Properties();
    consoleProps.load(new FileReader(new File(propFileName)));
    Enumeration<String> props = (Enumeration<String>)consoleProps.propertyNames();
    while (props.hasMoreElements())
    {
      String prop = props.nextElement();
  //  System.out.println("Prop:" + prop);
      String value = consoleProps.getProperty(prop);
      if (!"console.title".equals(prop))
      {
        String[] elem = value.split(",");
        try
        {
          ConsoleData cd = new ConsoleData(prop.trim(), 
                                           Integer.parseInt(elem[0].trim()), 
                                           Integer.parseInt(elem[1].trim()), 
                                           elem[2].trim(), 
                                           elem[3].trim(), 
                                           elem[4].trim(), 
                                           elem[5].trim());
          consoleData.put(prop.trim(), cd);
        }
        catch (NumberFormatException nfe)
        {
          System.err.println("Oops: " + nfe.getMessage());
  //      nfe.printStackTrace();
        }
      }
    }
  }
  
  private final static int FONT_SIZE = 18;
  private final static int FONT_WIDTH = 14;

  private void plotOneValue(Graphics gr, 
                            int x, 
                            int y, 
                            String value, 
                            Color valueColor, 
                            Color bgColor,
                            String key,
                            String unit, 
                            Color titleColor,
                            Color titleBgColor)
  {
    gr.setColor(titleColor);
    int abs = 10 + ((x - 1) * cellSize * FONT_WIDTH);
    int ord = y * FONT_SIZE;
    gr.drawString(key, abs, ord);
    abs =  10 + ((((x - 1) * cellSize) + keySize + dataSize) * FONT_WIDTH);
    gr.drawString(unit, abs, ord);
    gr.setColor(valueColor);
    abs = 10 + ((((x - 1) * cellSize) + keySize) * FONT_WIDTH);
    gr.drawString(value, abs, ord);
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
  
  @Override
  public void paintComponent(Graphics gr)
  {
    gr.setColor(Color.black);
    gr.fillRect(0, 0, this.getWidth(), this.getHeight());
    gr.setFont(new Font("courier new", Font.BOLD, FONT_SIZE));

    if (first.isTrue())
    {
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
      try
      {
        if ("POS".equals(s))                  
          value = DesktopUtilities.lpad(GeomUtil.decToSex(((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lat, GeomUtil.NO_DEG, GeomUtil.NS), " ", 12) + 
                  DesktopUtilities.lpad(GeomUtil.decToSex(((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lng, GeomUtil.NO_DEG, GeomUtil.EW), " ", 12);
        else
          value = DesktopUtilities.lpad(suffixes.get(s).getFmt().format(getValueFromCache(s)), " ", dataSize); // + " ";
      }
      catch (Exception ex)
      {
      }
//    System.out.println("   " + s + ":" + value);
      try
      {
        plotOneValue(gr, 
                     col, 
                     row, 
                     value, 
                     colorMap.get(cd.getFgData()), 
                     colorMap.get(cd.getBgData()),
                     s,
                     suffixes.get(s) == null ? "" : suffixes.get(s).getSuffix(), 
                     colorMap.get(cd.getFgTitle()),
                     colorMap.get(cd.getBgTitle()));
      }
      catch (Exception ex)
      {
        System.out.println("Col " + col + " row " + row);
        System.out.println("Value " + value);
        System.out.println("Key " + s);
        ex.printStackTrace();
      }
//    try { Thread.sleep(10); } catch (Exception ex) {}
    }    
  }
}
