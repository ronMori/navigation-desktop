package olivsoftdesktop.ctx;

import java.util.EventListener;

public abstract class DesktopEventListener implements EventListener 
{
  public void backgroundImageChanged() {}
  public void startReadingNMEAPort() {}
  public void startNMEACache() {}
}
