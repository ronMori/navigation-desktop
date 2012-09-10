
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
  public final static String VERSION_NUMBER = "3.0.0.4"; // In sync with the Weather Wizard
  public final static String PRODUCT_ID     = "desktop." + VERSION_NUMBER;
  
  public final static String PRODUCT_KEY    = "DESKTOP";
  public final static String STRUCTURE_FILE_NAME = "." + File.separator + "config" + File.separator + "structure.xml";

  private static DesktopContext instance = null;
  private DOMParser parser = null;
  private transient List<DesktopEventListener> applicationListeners = null;
  private DesktopFrame topFrame = null;
  private transient RMI_NMEAServerManager serverManager = null;
  
  private boolean readingNMEA = false;
  
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

  public void setReadingNMEA(boolean readingSerial)
  {
    this.readingNMEA = readingSerial;
  }

  public boolean isReadingNMEA()
  {
    return readingNMEA;
  }
}
