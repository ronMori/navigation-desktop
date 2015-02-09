
package olivsoftdesktop.ctx;

import coreutilities.log.Logger;

import java.io.File;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import olivsoftdesktop.DesktopFrame;

import olivsoftdesktop.server.RMI_NMEAServerManager;

import oracle.xml.parser.v2.DOMParser;

public class DesktopContext
{
//public final static String VERSION_NUMBER = "3.0.0.3"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.0.4"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.1.0"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.1.1"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.1.2"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.1.3"; // In sync with the Weather Wizard
//public final static String VERSION_NUMBER = "3.0.1.4"; // In sync with the Weather Wizard
  public final static String VERSION_NUMBER = "3.0.1.5"; // In sync with the Weather Wizard

  public final static String PRODUCT_ID     = "desktop." + VERSION_NUMBER;
  
  public final static String PRODUCT_KEY    = "DESKTOP";
  public final static String STRUCTURE_FILE_NAME = "." + File.separator + "config" + File.separator + "structure.xml";

  private static DesktopContext instance = null;
  private DOMParser parser = null;
  private List<DesktopEventListener> applicationListeners = null;
  private DesktopFrame topFrame = null;
  private RMI_NMEAServerManager serverManager = null;
  
  private boolean readingNMEA = false;
  
  private boolean httpRebroadcastEnable = false;
  private boolean tcpRebroadcastEnable  = false;
  private boolean udpRebroadcastEnable  = false;
  private boolean fileRebroadcastEnable = false;
  
  private boolean httpRebroadcastAvailable = false;
  private boolean tcpRebroadcastAvailable  = false;
  private boolean udpRebroadcastAvailable  = false;
  private boolean fileRebroadcastAvailable = false;
  
  private int httpRebroadcastPort    = 0;

  private List<String> noRebroadcastList = null;

  public void setNoRebroadcastList(List<String> noRebroadcastList)
  {
    this.noRebroadcastList = noRebroadcastList;
  }

  public List<String> getNoRebroadcastList()
  {
    return noRebroadcastList;
  }
  
  public boolean notToBeRebroadcasted(String data)
  {
    boolean b = false;
    try
    {
      if (noRebroadcastList != null)
      {
        for (String s : noRebroadcastList)
        {
          Pattern ptrn = Pattern.compile(s);
          Matcher m = ptrn.matcher(data);
          if (m.find())
          {
      //    System.out.println(">>> DEBUG >>> Excluded from re-broadcast:" + data);
            b = true;
            break;
          }
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
//    if (!b)
//      System.out.println(">>> DEBUG >>> String accepted:" + data);
    return b;
  }

  public void setHttpRebroadcastPort(int httpRebroadcastPort)
  {
    this.httpRebroadcastPort = httpRebroadcastPort;
  }

  public int getHttpRebroadcastPort()
  {
    return httpRebroadcastPort;
  }

  public void setTcpRebroadcastPort(int tcpRebroadcastPort)
  {
    this.tcpRebroadcastPort = tcpRebroadcastPort;
  }

  public int getTcpRebroadcastPort()
  {
    return tcpRebroadcastPort;
  }

  public void setUdpRebroadcastPort(int udpRebroadcastPort)
  {
    this.udpRebroadcastPort = udpRebroadcastPort;
  }

  public int getUdpRebroadcastPort()
  {
    return udpRebroadcastPort;
  }

  public void setFileRebroadcastName(String fileRebroadcastName)
  {
    this.fileRebroadcastName = fileRebroadcastName;
  }

  public String getFileRebroadcastName()
  {
    return fileRebroadcastName;
  }
  private int tcpRebroadcastPort     = 0;
  private int udpRebroadcastPort     = 0;
  private String fileRebroadcastName = "";
  
  private boolean desktopVerbose = "true".equals(System.getProperty("desktop.verbose", "false"));

  private DesktopContext()
  {
    parser = new DOMParser();
    applicationListeners = new ArrayList<DesktopEventListener>(2); 
    serverManager = new RMI_NMEAServerManager();
  }
  
  public synchronized static DesktopContext getInstance()
  {
    if (instance == null)
      instance = new DesktopContext();
    return instance;
  }

  public void setDesktopVerbose(boolean desktopVerbose)
  {
    this.desktopVerbose = desktopVerbose;
    System.setProperty("desktop.verbose", Boolean.toString(desktopVerbose));
  }

  public void setHttpRebroadcastAvailable(boolean httpRebroadcastAvailable)
  {
    this.httpRebroadcastAvailable = httpRebroadcastAvailable;
  }

  public boolean isHttpRebroadcastAvailable()
  {
    return httpRebroadcastAvailable;
  }

  public void setTcpRebroadcastAvailable(boolean tcpRebroadcastAvailable)
  {
    this.tcpRebroadcastAvailable = tcpRebroadcastAvailable;
  }

  public boolean isTcpRebroadcastAvailable()
  {
    return tcpRebroadcastAvailable;
  }

  public void setUdpRebroadcastAvailable(boolean udpRebroadcastAvailable)
  {
    this.udpRebroadcastAvailable = udpRebroadcastAvailable;
  }

  public boolean isUdpRebroadcastAvailable()
  {
    return udpRebroadcastAvailable;
  }

  public void setFileRebroadcastAvailable(boolean fileRebroadcastAvailable)
  {
    this.fileRebroadcastAvailable = fileRebroadcastAvailable;
  }

  public boolean isFileRebroadcastAvailable()
  {
    return fileRebroadcastAvailable;
  }

  public boolean isDesktopVerbose()
  {
    return desktopVerbose;
  }
  
  public void fireResetConsole()
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.resetConsole();
    }    
  }

  public DOMParser getParser()
  {
    return parser;
  }

  public List<DesktopEventListener> getListeners()
  {
    return applicationListeners;
  }
  
  public synchronized void addApplicationListener(DesktopEventListener l)
  {
    if (!this.getListeners().contains(l))
    {      
      this.getListeners().add(l);
    }
  }

  public synchronized void removeApplicationListener(DesktopEventListener l)
  {
    this.getListeners().remove(l);
  }

  public void fireBackgroundImageChanged()
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.backgroundImageChanged();
    }    
  }

  public void fireBGWinColorChanged()
  {    
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.bgWinColorChanged();
    }    
  }
  
  public void fireStartReadingNMEAPort()
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.startReadingNMEAPort();
    }    
  }

  public void fireStartNMEACache()
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.startNMEACache();
    }    
  }

  public RMI_NMEAServerManager getNMEAServerManager()
  {
    return serverManager;
  }

  public void setTopFrame(DesktopFrame topFrame)
  {
    this.topFrame = topFrame;
  }

  public DesktopFrame getTopFrame()
  {
    return topFrame;
  }

  public void setReadingNMEA(boolean readingNMEA)
  {
    this.readingNMEA = readingNMEA;
  }

  public boolean isReadingNMEA()
  {
    return readingNMEA;
  }
  
  public void setHttpRebroadcastEnable(boolean httpRebroadcastEnable)
  {
    this.httpRebroadcastEnable = httpRebroadcastEnable;
    Logger.log((httpRebroadcastEnable ? "Enabling" : "Disabling") + " HTTP at " + (new Date()).toString(), Logger.INFO);
  }

  public boolean isHttpRebroadcastEnable()
  {
    return httpRebroadcastEnable;
  }

  public void setTcpRebroadcastEnable(boolean tcpRebroadcastEnable)
  {
    this.tcpRebroadcastEnable = tcpRebroadcastEnable;
    Logger.log((tcpRebroadcastEnable ? "Enabling" : "Disabling") + " TCP at " + (new Date()).toString(), Logger.INFO);
  }

  public boolean isTcpRebroadcastEnable()
  {
    return tcpRebroadcastEnable;
  }

  public void setUdpRebroadcastEnable(boolean udpRebroadcastEnable)
  {
    this.udpRebroadcastEnable = udpRebroadcastEnable;
    Logger.log((udpRebroadcastEnable ? "Enabling" : "Disabling") + " UDP at " + (new Date()).toString(), Logger.INFO);
  }

  public boolean isUdpRebroadcastEnable()
  {
    return udpRebroadcastEnable;
  }

  public void setFileRebroadcastEnable(boolean fileRebroadcastEnable)
  {
    this.fileRebroadcastEnable = fileRebroadcastEnable;
    Logger.log((fileRebroadcastEnable ? "Enabling" : "Disabling") + " logging at " + (new Date()).toString(), Logger.INFO);
  }

  public boolean isFileRebroadcastEnable()
  {
    return fileRebroadcastEnable;
  }  
  
  public void fireNbClients(int type, int nb)
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.setNbClients(type, nb);
    }
  }
  
  public void fireGetNbClients(int type)
  {
    for (int i=0; i < this.getListeners().size(); i++)
    {
      DesktopEventListener l = this.getListeners().get(i);
      l.getNbClients(type);
    }
  }
}
