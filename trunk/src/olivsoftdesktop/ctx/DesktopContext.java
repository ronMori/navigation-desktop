
package olivsoftdesktop.ctx;

import java.io.File;

import java.util.ArrayList;

import java.util.List;

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
  public final static String VERSION_NUMBER = "3.0.1.4"; // In sync with the Weather Wizard

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
  
  private boolean desktopVerbose = false;

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
  }

  public boolean isHttpRebroadcastEnable()
  {
    return httpRebroadcastEnable;
  }

  public void setTcpRebroadcastEnable(boolean tcpRebroadcastEnable)
  {
    this.tcpRebroadcastEnable = tcpRebroadcastEnable;
  }

  public boolean isTcpRebroadcastEnable()
  {
    return tcpRebroadcastEnable;
  }

  public void setUdpRebroadcastEnable(boolean udpRebroadcastEnable)
  {
    this.udpRebroadcastEnable = udpRebroadcastEnable;
  }

  public boolean isUdpRebroadcastEnable()
  {
    return udpRebroadcastEnable;
  }

  public void setFileRebroadcastEnable(boolean fileRebroadcastEnable)
  {
    this.fileRebroadcastEnable = fileRebroadcastEnable;
  }

  public boolean isFileRebroadcastEnable()
  {
    return fileRebroadcastEnable;
  }  
}
