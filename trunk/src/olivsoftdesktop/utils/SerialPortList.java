package olivsoftdesktop.utils;

import java.util.ArrayList;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;

import java.util.List;

public class SerialPortList
{
  public static String[] listSerialPorts()
  {
    String[] sa = null;
    List<String> portList = new ArrayList<String>();                                  
    CommPortIdentifier portId;
    try
    {
      Enumeration en = CommPortIdentifier.getPortIdentifiers();
      while (en.hasMoreElements())
      {
        portId = (CommPortIdentifier) en.nextElement();
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
        {
          portList.add(portId.getName());
        }
      }
      sa = portList.toArray(new String[portList.size()]);
      if (sa.length == 0) // Just in case...
      {
        sa = new String[] { "COM1", "COM2", "COM3" };
      }
    }
    catch (UnsatisfiedLinkError ule)
    {
      System.err.println(ule.getMessage());
      System.err.println("Serial port features will be disabled for this session.");
      sa = new String[] {};
    }
    return sa;
  }
}
