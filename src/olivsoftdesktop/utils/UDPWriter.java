package olivsoftdesktop.utils;

import coreutilities.log.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import olivsoftdesktop.ctx.DesktopContext;

public class UDPWriter
{
  private int udpPort         = 8001;
  private InetAddress address = null;
  
  private final static String DEFAULT_HOST = "127.0.0.1"; // "230.0.0.1"
  private String hostName = DEFAULT_HOST; 
  
  public UDPWriter(int port) throws Exception
  {
    this(port, DEFAULT_HOST);
  }
  
  public UDPWriter(int port, String host) throws Exception
  {
    this.hostName = host;
    this.udpPort = port;
    try 
    { 
      address = InetAddress.getByName(this.hostName); // For Broadcasting, multicast address. 
    }
    catch (Exception ex)
    {
      Logger.log(ex.getLocalizedMessage(), Logger.INFO);      
      throw ex;
//    ex.printStackTrace();
    }
  }
  
  public void write(byte[] message)
  {
    if (DesktopContext.getInstance().isDesktopVerbose())
      System.out.println("UDP write on " + this.hostName + ":" + udpPort + " [" + new String(message) + "]");
    try
    {
      // Create datagram socket
      DatagramSocket dsocket = null;
      if (address.isMulticastAddress())
      {
        dsocket = new MulticastSocket(udpPort);
        ((MulticastSocket)dsocket).joinGroup(address);      
      }  
      else
        dsocket = new DatagramSocket(udpPort, address);

      // Initialize a datagram
      DatagramPacket packet = new DatagramPacket(message, message.length, address, udpPort);      
      dsocket.send(packet);
      if (address.isMulticastAddress())
        ((MulticastSocket)dsocket).leaveGroup(address);
      dsocket.close();
    }
    catch (Exception ex)
    {
      if ("No such device".equals(ex.getMessage()))
        System.out.println("No such devide [" + address + "] (from " + this.getClass().getName() + ")");
      else
        ex.printStackTrace();
    }
  }
}
