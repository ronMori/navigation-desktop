package olivsoftdesktop.ctx;

import java.util.EventListener;

public abstract class DesktopEventListener implements EventListener 
{
  public final static int HTTP_TYPE = 0;
  public final static int TCP_TYPE  = 1;
  public final static int UDP_TYPE  = 2;
  public final static int WS_TYPE   = 3;
  
  public void backgroundImageChanged() {}
  public void bgWinColorChanged() {}
  public void startReadingNMEAPort() {}
  public void startNMEACache() {}
  public void resetConsole() {}
  public void getNbClients(int connectionType) {} // request
  public void setNbClients(int connectionType, int nb) {} // response
}
