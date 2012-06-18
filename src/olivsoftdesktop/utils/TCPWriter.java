package olivsoftdesktop.utils;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPWriter
{
  private int tcpPort               = 7001;
  private Socket tcpSocket          = null;
  private ServerSocket serverSocket = null;
  
  public TCPWriter(int port)
  {
    this.tcpPort = port;

    try 
    { 
      serverSocket = new ServerSocket(tcpPort);
      Thread socketThread = new Thread()
        {
          public void run()
          {
            try 
            { 
              Socket skt = serverSocket.accept(); 
              setSocket(skt);
            } 
            catch (Exception ex) 
            { System.err.println("SocketThread:" + ex.getLocalizedMessage()); }  
          }
        };
      socketThread.start();      
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  protected void setSocket(Socket skt)
  {
    this.tcpSocket = skt;
  }
  
  public void write(byte[] message)
  {
    if ("true".equals(System.getProperty("verbose", "false")))
      System.out.println("TCP write on port " + tcpPort + " [" + new String(message) + "]");
    try
    {
      if (tcpSocket != null)
      {
        DataOutputStream out = new DataOutputStream(tcpSocket.getOutputStream());
        out.write(message);
        out.flush();
//      out.close();
      }
    }
    catch (SocketException se)
    {
      System.err.println("SocketException:[" + se.getMessage() + "]");
      if (se.getMessage().indexOf("Connection reset by peer") > -1) // TODO Catch more errors
      {
        setSocket(null);
        try
        {
//        serverSocket = new ServerSocket(tcpPort);
          Thread socketThread = new Thread()
            {
              public void run()
              {
//              System.out.println("...accept");
                try 
                { 
                  Socket skt = serverSocket.accept(); 
//                System.out.println("Accepted.");
                  setSocket(skt);
                } 
                catch (Exception ex) 
                { System.err.println("SocketThread:" + ex.getLocalizedMessage()); }  
              }
            };
          socketThread.start();
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
      else
        se.printStackTrace();
    }
    catch (Exception ex)
    {
      System.err.println("TCPWriter.write:" + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
  }
  
  public void close() throws Exception
  {
    tcpSocket.close();  
  }
  
  public static void main(String[] args)
  {
    String gpsd = "{\"class\":\"TVP\",\"tag\":\"MID2\",\"time\":\"2010-04-30T11:48:20.10Z\",\"ept\":0.005,\"lat\":46.498204497,\"lon\":7.568061439,\"alt\":1327.689,\"epx\":15.319,\"epy\":17.054,\"epv\":124.484,\"track\":10.3797,\"speed\":0.091,\"climb\":-0.085,\"eps\",34.11,\"mode\":3}";
    String wpl  = "$GPWPL,3739.856,N,12222.812,W,OPMRNA*59";
    try
    {
      TCPWriter tcpw = new TCPWriter(2947);
//    TCPWriter tcpw = new TCPWriter(7001);
//    TCPWriter tcpw = new TCPWriter(7001, "theketch-lap.mshome.net");
      for (int i=0; i<50; i++)
      {
        System.out.println("Ping...");
        try { tcpw.write(gpsd.getBytes()); } catch (Exception ex) { System.err.println(ex.getLocalizedMessage()); }
        try { Thread.sleep(1000L); } catch (Exception ex) { ex.printStackTrace(); }
      }
    }
    catch (Exception e)
    {
      // TODO: Add catch code
      e.printStackTrace();
    }
  }
}
