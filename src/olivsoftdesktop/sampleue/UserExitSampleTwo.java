package olivsoftdesktop.sampleue;

import nmea.event.NMEAReaderListener;

import nmea.server.ctx.NMEAContext;

import nmea.server.ctx.NMEADataCache;

import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Speed;

import olivsoftdesktop.DesktopUserExitInterface;

public class UserExitSampleTwo
  implements DesktopUserExitInterface
{
  private Thread watcher = null;
  private boolean keepWatching = true;
  
  public UserExitSampleTwo()
  {
    super();
  }

  @Override
  public void start()
  {
    System.out.println("User exit is starting...");
    
    watcher = new Thread("Thread for UserExitSampleTwo")
      {
        public void run()
        {
          while (keepWatching)
          {
            NMEADataCache dc = NMEAContext.getInstance().getCache();
            try
            {
              double tws = ((Speed) dc.get(NMEADataCache.TWS)).getValue();
              double twd = ((Angle360) dc.get(NMEADataCache.TWD)).getValue();
              if (tws > 10 && !Double.isInfinite(tws))
              {
                System.out.println("Wind is over 10 kts:" + tws + ", TWD:" + twd);        
                // TODO Send an email?...
              }
            }
            catch (NullPointerException npe)
            {
              // Just wait til next time...
            }
            synchronized (this)
            {
              try { wait(10000L); } 
              catch (InterruptedException ie)
              {
                System.out.println("Told to stop!");
                keepWatching = false;
              }
            }
          }
          System.out.println("Stop waiting.");
        }
      };
    keepWatching = true;
    watcher.start();
  }

  @Override
  public void stop()
  {
    System.out.println("Terminating User exit");
    keepWatching = false;
    synchronized (watcher)
    {
      watcher.notify();
    }
  }

  @Override
  public void describe()
  {
    System.out.println("This is a simple user-exit example.");
    System.out.println("It checks the NMEA cache every 10 seconds, and displays a message if the TWS is above 10 knots.");
  }
}
