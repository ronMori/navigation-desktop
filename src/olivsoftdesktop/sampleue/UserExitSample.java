package olivsoftdesktop.sampleue;

import nmea.event.NMEAReaderListener;
import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;
import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Speed;
import olivsoftdesktop.DesktopUserExitInterface;

public class UserExitSample
  implements DesktopUserExitInterface
{
  public UserExitSample()
  {
    super();
  }

  @Override
  public void start()
  {
    System.out.println("User exit is starting...");
    NMEAContext.getInstance().addNMEAReaderListener(new NMEAReaderListener()
    {
        @Override
        public void manageNMEAString(String nmeaString)
        {
//        System.out.println("     ... From user exit, got NMEA Data [" + nmeaString + "]");
          NMEADataCache dc = NMEAContext.getInstance().getCache();
          double tws = ((Speed) dc.get(NMEADataCache.TWS)).getValue();
          double twd = ((Angle360) dc.get(NMEADataCache.TWD)).getValue();
          if (tws > 10 && !Double.isInfinite(tws))
          {
            System.out.println("Wind is over 10 kts:" + tws + ", TWD:" + twd);        
            // TODO Send an email...
          }
        }
    });
  }

  @Override
  public void stop()
  {
    System.out.println("Terminating User exit");
  }
}
