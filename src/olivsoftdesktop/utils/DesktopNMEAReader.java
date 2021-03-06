package olivsoftdesktop.utils;


import java.io.File;

import nmea.event.NMEAReaderListener;

import nmea.server.NMEAEventManager;
import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;
import nmea.server.datareader.CustomNMEAClient;
import nmea.server.utils.Utils;

import ocss.nmea.parser.StringParsers;

import olivsoftdesktop.ctx.DesktopContext;

import util.NMEACache;


public class DesktopNMEAReader
  implements NMEAEventManager
{
  private String pfile = "";
  private String serial = null;
  private int br = 0;
  private String port = "";
  private String host = "localhost";
  private String data = null;
  private int option = -1;
  
  private boolean connected = false, quit = false;
  
  CustomNMEAClient nmeaClient = null;

  public DesktopNMEAReader(boolean v,
                           String serial,
                           int br,
                           String port,
                           int option,
                           String host,
                           String fName, // simulation file
                           String propertiesFile)
  {
    DesktopContext.getInstance().setDesktopVerbose(v);
    this.serial = serial;
    this.br = br;
    this.port = port;
    this.option = option;
    this.host = host;
    this.data = fName;
    this.pfile = propertiesFile;
    
    this.quit = false;
    this.connected = false;
    while (!this.connected && !this.quit)
    {
      try
      {
        jbInit();
        this.connected = true;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      if (!this.connected)
      {
        System.out.println("... Will retry to connect.");
        try { Thread.sleep(1000L); } catch (InterruptedException ie) {}
      }
    }
    if (!this.connected)
    {
      System.err.println("Stop trying to connect...");
    }
  }
  
  private void jbInit()
    throws Exception
  {
    // Init
    try
    {
      if (port != null && port.trim().length() > 0)
      {
        int tcpport = Integer.parseInt(port);
        read(tcpport, this.host, option);
      } 
      else if (data != null && data.trim().length() > 0)
      {
        read(new File(data));
      }
      else if (serial != null && serial.trim().length() > 0)
      {
        read(serial, br);
      }
      else
      {
        System.out.println("Nothing to read, exiting.");
        System.exit(1);
      }
      // Instantiate the cache
      NMEADataCache cache = NMEAContext.getInstance().getCache();
//    System.out.println("NMEA Cache is " + (cache==null?"":"not ") + "null.");
      if (cache != null)
      {
//      System.out.println("Cache has " + cache.size() + " entry(ies).");
        NMEAContext.getInstance().addNMEAReaderListener(new NMEAReaderListener("Desktop", "NMEA Reader")
          {
            public void manageNMEAString(String str)
            {
//            System.out.println("DesktopNMEAReader - manageNMEAString [" + str + "]");
//            System.out.println("- Cache has " + NMEAContext.getInstance().getCache().size() + " entry(ies).");
              dispatchData(str);
            }
          });
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void dispatchData(String str)
  {
    if (!StringParsers.validCheckSum(str))
      return;
    if (str.trim().length() < 6 || !str.substring(1, 6).equals(str.substring(1, 6).toUpperCase()))
      return;
//  System.out.println("Substring:[" + payload.substring(1, 6) +"]");
    String key = "";
    try
    {
      key = str.substring(1);
      if (key.length() > 5)
        key = key.substring(0, 5);
      if (key.length() == 5) // Because it could be less
        Utils.parseAndCalculate(key, str, NMEAContext.getInstance().getCache());
    }
    catch (Exception ex)
    {
      System.err.println("Desktop Dispatch Data:");
      ex.printStackTrace();
    }
  }

  private void read()
  {
    read((String)null);
  }

  /* Serial */
  private void read(String port)
  {
    read(port, 4800);
  }

  /* Serial */
  private void read(String port, int br)
  {
    System.out.println("Reading...");
    nmeaClient = new CustomNMEAClient(this, port, br)
      {
        public void manageNMEAError(Throwable t)
        {
          throw new RuntimeException(t);
        }
      };
  }

  /* TCP/UDP/RMI */
  private void read(int port, String hostname, int opt)
  {
    if (opt == CustomNMEAClient.TCP_OPTION)
      System.out.println("Reading TCP...");
    else if (opt == CustomNMEAClient.UDP_OPTION)
      System.out.println("Reading UDP...");
    else if (opt == CustomNMEAClient.RMI_OPTION)
      System.out.println("Reading RMI...");
    else
      System.out.println("Reading... What?");
    nmeaClient = new CustomNMEAClient(this, opt, hostname, port)
      {
        public void manageNMEAError(Throwable t)
        {
          throw new RuntimeException(t);
        }
      };
  }

  /* File. Logged Data */
  private void read(File f)
  {
    System.out.println("Reading Data File...");
    nmeaClient = new CustomNMEAClient(this, f)
      {
        public void manageNMEAError(Throwable t)
        {
          throw new RuntimeException(t);
        }
      };
  }
  
  public void manageDataEvent(String payload)
  {
    if (DesktopContext.getInstance().isDesktopVerbose())
    {
      String displ = payload;
      while ((displ.endsWith("\r") || displ.endsWith("\n")) && displ.length() >= 0)
        displ = displ.substring(0, displ.length() - 1);
      if ("yes".equals(System.getProperty("headless", "no")))
        System.out.println(displ);
      else
        System.out.println(this.getClass().getName() + ": Read from NMEA :[" + displ + "]");
    }
    
    try
    {
      synchronized (NMEAContext.getInstance().getNMEAListeners())
      {
        NMEACache.getInstance().dispatch(payload);    
      }
      synchronized (NMEAContext.getInstance().getReaderListeners())
      {
        synchronized (NMEAContext.getInstance())
        {
          NMEAContext.getInstance().fireNMEAString(payload);    
        }
      }
    }
    catch (Exception ex)
    {
      System.err.println("-------------------");
//    System.err.println(ex.toString());
      ex.printStackTrace();
      System.err.println("-------------------");
    }
  }
  
  public void stopReader() throws Exception 
  {
    if (DesktopContext.getInstance().isDesktopVerbose())
      System.out.println(this.getClass().getName() + ": Stop Reading requested.");
    this.quit = true;
    nmeaClient.stopReading();
  }

  @Override
  public boolean verbose()
  {
    return DesktopContext.getInstance().isDesktopVerbose();
  }
}
