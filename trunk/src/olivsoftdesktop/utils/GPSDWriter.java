package olivsoftdesktop.utils;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;

import ocss.gpsd.GPSdUtils;

import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.GeoPos;
import ocss.nmea.parser.Speed;
import ocss.nmea.parser.UTCDate;

public class GPSDWriter
{
  private int tcpPort               = 2947;
  private List<Socket> clientSocketlist = new ArrayList<Socket>(1);
//private Socket tcpSocket          = null;
  private ServerSocket serverSocket = null;
  
  private boolean protocolEstablished = false;
  
  BufferedReader  inFromClient = null;
  DataOutputStream outToClient = null;

  public GPSDWriter(int port)
  {
    this.tcpPort = port;

    try 
    { 
      serverSocket = new ServerSocket(tcpPort);
      if (!protocolEstablished)
      {
        Thread socketThread = new SocketThread();
        socketThread.start();
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  protected void setSocket(Socket skt)
  {
//  this.tcpSocket = skt;
    this.clientSocketlist.add(skt);
    System.out.println("- " + clientSocketlist.size() + " TCP/GPSd Client socket(s)");
  }
  
  public void write(byte[] message)
  {
    if ("true".equals(System.getProperty("verbose", "false")))
      System.out.println("TCP write on port " + tcpPort + " [" + new String(message) + "]");
    synchronized (clientSocketlist)
    {
      List<Socket> toRemove = new ArrayList<Socket>();
      for (Socket tcpSocket : clientSocketlist)
      {
        try
        {
          if (tcpSocket != null && protocolEstablished)
          {
            String mess = new String(message);
    //      System.out.println("Rebroadcasting [" + mess + "] on GPSd");
            // Parse appropriate strings...
            if ("GLL".equals(mess.substring(3, 6)) ||
                "RMC".equals(mess.substring(3, 6)))
            {
              NMEADataCache ndc = NMEAContext.getInstance().getCache();
              if (ndc != null)
              {
                UTCDate utcDate = (UTCDate)(NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_DATE_TIME, true));
                GeoPos gp = ((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true));
                double cog = 0d, sog = 0d;
                try { sog = ((Speed)ndc.get(NMEADataCache.SOG)).getValue(); } catch (Exception ex) {}
                try { cog = ((Angle360)ndc.get(NMEADataCache.COG)).getValue(); } catch (Exception ex) {}
                if (utcDate != null && gp != null)
                {
                  String responseSentence = GPSdUtils.produceTPV(utcDate.getValue(), gp, cog, sog);
                  responseSentence += "\n";
                  outToClient.writeBytes(responseSentence);
                  outToClient.flush();
                }
              }
            }
          }
        }
        catch (SocketException se)
        {
          protocolEstablished = false;
          toRemove.add(tcpSocket);
        }
        catch (Exception ex)
        {
          System.err.println("TCPWriter.write:" + ex.getLocalizedMessage());
          ex.printStackTrace();
        }
        if (toRemove.size() > 0)
        {
          for (Socket skt : toRemove)
          {
            this.clientSocketlist.remove(skt);
            System.out.println("- " + clientSocketlist.size() + " TCP/GPSd Client socket(s)");
          }
        }
      }
    }
  }
  
  public void close() throws Exception
  {
    for (Socket tcpSocket : clientSocketlist)
      tcpSocket.close();  
  }

  private class SocketThread extends Thread
  {
    public void run()
    {
      try 
      { 
        Socket skt = serverSocket.accept(); 
        setSocket(skt);
        InputStream dis = skt.getInputStream();
        inFromClient = new BufferedReader(new InputStreamReader(dis));
        outToClient = new DataOutputStream(skt.getOutputStream());
        boolean go = true;
        while (go)
        {
          String clientSentence = inFromClient.readLine();
          String responseSentence  = "";          
          System.out.println("ClientRequest:" + clientSentence);
          
          if (clientSentence != null && clientSentence.trim().length() > 0)
          {
            if (clientSentence.equals(GPSdUtils.VERSION))
            {
              responseSentence = GPSdUtils.produceVersion("3.0", new Date(), 3, 2); 
             // "{\"class\":\"VERSION\",\"release\":\"2.93\",\"rev\":\"2010-03-30T12:18:17\",\"proto_major\":3,\"proto_minor\":2}";
              responseSentence += "\n";
              outToClient.writeBytes(responseSentence);
            }
            else if (clientSentence.startsWith(GPSdUtils.WATCH))
            {
              System.out.println("WATCH command received.");
              responseSentence = GPSdUtils.produceDevices(new String[] { "COM15" }, 
                                                          new Date[] { new Date() }, 
                                                          new int[] { 4800 }, 
                                                          new String[] { "N" }, 
                                                          new int[] { 1 });
//                  responseSentence = "{\"class\":\"DEVICES\",\"devices\":[{\"class\":\"DEVICE\",\"path\":\"COM10\",\"activated\":\"2012-06-18T11:48:20.10Z\",\"native\":0,\"bps\":4800,\"parity\":\"N\",\"stopbits\":1,\"cycle\":1.00}]}";
              responseSentence += "\n";
              outToClient.writeBytes(responseSentence);
              responseSentence = GPSdUtils.produceWatch(); 
//                    "{\"class\":\"WATCH\",\"enable\":true,\"json\":false,\"nmea\":true,\"raw\":0,\"scaled\":false,\"timing\":false}";
              responseSentence += "\n";
              outToClient.writeBytes(responseSentence);
            }
            else if (clientSentence.equals(GPSdUtils.POLL))
            {
              // Release...
              go = false;
              protocolEstablished = true;
            }
          }
        }
      } 
      catch (Exception ex) 
      { System.err.println("SocketThread:" + ex.getLocalizedMessage()); }  
    }
  }
}
