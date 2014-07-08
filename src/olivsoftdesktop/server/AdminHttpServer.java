package olivsoftdesktop.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import nmea.server.ctx.NMEAContext;

import olivsoftdesktop.ctx.DesktopContext;


/**
 * Dedicated HTTP Server.
 * This is NOT J2EE Compliant, not even CGI.
 *
 * Runs the communication between an HTTP client and the
 * features of the Data server to be displayed remotely.
 */
public class AdminHttpServer 
{
  public AdminHttpServer()
  {
  }
  private boolean verbose = "true".equals(System.getProperty("verbose", "false"));
  private int _port = 0;
  
  private final static String TURN_HTTP_ON     = "/put/http/on";
  private final static String TURN_HTTP_OFF    = "/put/http/off";
  private final static String TURN_TCP_ON      = "/put/tcp/on";
  private final static String TURN_TCP_OFF     = "/put/tcp/off";
  private final static String TURN_UDP_ON      = "/put/udp/on";
  private final static String TURN_UDP_OFF     = "/put/udp/off";
  private final static String TURN_LOGFILE_ON  = "/put/log/on";
  private final static String TURN_LOGFILE_OFF = "/put/log/off";
  
  private final static String TURN_VERBOSE_ON  = "/put/verbose/on";
  private final static String TURN_VERBOSE_OFF = "/put/verbose/off";
  
  // Statuses
  private final static String HTTP_STATUS      = "/get/http";
  private final static String TCP_STATUS       = "/get/tcp";
  private final static String UDP_STATUS       = "/get/udp";
  private final static String LOG_STATUS       = "/get/log";
  private final static String VERBOSE_STATUS   = "/get/verbose";
  
  private final static String LOGFILE_NAME     = "logfile";
  
  public AdminHttpServer(int port)
  {
    System.out.println(">>> Starting Admin HTTP server on port " + port);
    this._port = port;    
    
    // Infinite loop, waiting for requests
    Thread httpListenerThread = new Thread()
    {
      public void run()
      {
        boolean go = true;
        try
        {
          Map<String, String> header = new HashMap<String, String>();
          ServerSocket ss = new ServerSocket(_port);
          boolean help = false;
          while (go)
          {
            Socket client = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter   out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            
            String command = "";
            String commandReply = "";
            Map<String, String> prmMap = new HashMap<String, String>();
            String line;
            String fileToFetch = "";
            command = "";
            commandReply = "Good";
            help = false;
            while (go && (line = in.readLine()) != null)
            {
              if (verbose) System.out.println("HTTP Request:" + line);
          //  command = "";
          //  commandReply = "Good";
              try 
              {
                command = line.substring(line.indexOf("/"));
                command = command.substring(0, command.indexOf(" ")).trim();
                if (command.indexOf("?") > -1)
                {
                  String prms = command.substring(command.indexOf("?") + 1);
                  command = command.substring(0, command.indexOf("?"));
                  String[] pa = prms.split("&");
                  for (String p : pa)
                  {
                    String[] nv = p.split("=");
                    prmMap.put(nv[0], nv[1]);
                  }
                }
//              System.out.println("Command: [" + command + "]");
              }
              catch (Exception ex)
              {                
              }
              if (line.length() == 0)
                break;
              else if (line.startsWith("POST /exit") || line.startsWith("GET /exit"))
              {
//             System.out.println("Received an exit signal");
                go = false;
              }
              else if (line.startsWith("POST /help") || line.startsWith("GET /help"))
              {
//              System.out.println("Received an help request");
                help = true;
              }
              else if (command.trim().length() > 0)
              {
//              System.out.println("Managing Admin command [" + command + "]");
                // Manage it here
                if (TURN_HTTP_ON.equals(command))
                {
//                System.out.println("Enabling HTTP");
                  if (DesktopContext.getInstance().isHttpRebroadcastAvailable())
                  {
                    DesktopContext.getInstance().setHttpRebroadcastEnable(true);
                    NMEAContext.getInstance().fireEnableHTTPserver(true);
                  }
                  else
                    commandReply = "HTTP re-broadcasting NOT available";
                }
                else if (TURN_HTTP_OFF.equals(command))
                {
//                System.out.println("Disabling HTTP");
                  if (DesktopContext.getInstance().isHttpRebroadcastAvailable())
                  {
                    DesktopContext.getInstance().setHttpRebroadcastEnable(false);
                    NMEAContext.getInstance().fireEnableHTTPserver(false);
                  }
                  else
                    commandReply = "HTTP re-broadcasting NOT available";

                }
                else if (TURN_TCP_ON.equals(command))
                {
//                System.out.println("Enabling TCP");
                  if (DesktopContext.getInstance().isTcpRebroadcastAvailable())
                    DesktopContext.getInstance().setTcpRebroadcastEnable(true);
                  else
                    commandReply = "TCP re-broadcasting NOT available";
                }
                else if (TURN_TCP_OFF.equals(command))
                {
//                System.out.println("Disabling TCP");
                  if (DesktopContext.getInstance().isTcpRebroadcastAvailable())
                    DesktopContext.getInstance().setTcpRebroadcastEnable(false);
                  else
                    commandReply = "TCP re-broadcasting NOT available";
                }
                else if (TURN_UDP_ON.equals(command))
                {
//                System.out.println("Enabling UDP");
                  if (DesktopContext.getInstance().isUdpRebroadcastAvailable())
                    DesktopContext.getInstance().setUdpRebroadcastEnable(true);
                  else
                    commandReply = "UDP re-broadcasting NOT available";
                }
                else if (TURN_UDP_OFF.equals(command))
                {
//                System.out.println("Disabling UDP");
                  if (DesktopContext.getInstance().isUdpRebroadcastAvailable())
                    DesktopContext.getInstance().setUdpRebroadcastEnable(false);
                  else
                    commandReply = "UDP re-broadcasting NOT available";
                }
                else if (TURN_LOGFILE_ON.equals(command))
                {
//                System.out.println("Enabling Logging");                  
                  if (DesktopContext.getInstance().isFileRebroadcastAvailable())
                  {
//                    String fileName = "logged-data/data.nmea";
//                    try
//                    {
//                      String fName = prmMap.get(LOGFILE_NAME);
//                      if (fName != null && fName.trim().length() > 0)
//                        fileName = fName;
//                      System.out.println(">>> LogFile name: [" + fileName + "]");
//                    }
//                    catch (Exception ex) {}
                    DesktopContext.getInstance().setFileRebroadcastEnable(true); // TODO A file name
                  }
                  else
                    commandReply = "FILE re-broadcasting NOT available";
                }
                else if (TURN_LOGFILE_OFF.equals(command))
                {
//                System.out.println("Disabling Logging");                  
                  if (DesktopContext.getInstance().isFileRebroadcastAvailable())
                    DesktopContext.getInstance().setFileRebroadcastEnable(false);
                  else
                    commandReply = "FILE re-broadcasting NOT available";
                } 
                else if (TURN_VERBOSE_ON.equals(command))
                {
//                System.out.println("Enabling verbose");                  
                  DesktopContext.getInstance().setDesktopVerbose(true);
                } 
                else if (TURN_VERBOSE_OFF.equals(command))
                {
//                System.out.println("Disabling verbose");                  
                  DesktopContext.getInstance().setDesktopVerbose(false);
                } 
                else if (HTTP_STATUS.equals(command))
                {
//                System.out.println("HTTP_STATUS");        
                  if (DesktopContext.getInstance().isHttpRebroadcastAvailable())
                    commandReply = DesktopContext.getInstance().isHttpRebroadcastEnable() ? "on" : "off";
                  else 
                    commandReply = "HTTP re-broadcast not available";
                }
                else if (TCP_STATUS.equals(command))
                {
//                System.out.println("TCP_STATUS");                  
                  if (DesktopContext.getInstance().isTcpRebroadcastAvailable())
                    commandReply = DesktopContext.getInstance().isTcpRebroadcastEnable() ? "on" : "off";
                  else 
                    commandReply = "TCP re-broadcast not available";
                }  
                else if (UDP_STATUS.equals(command))
                {
//                System.out.println("UDP_STATUS");                  
                  if (DesktopContext.getInstance().isUdpRebroadcastAvailable())
                    commandReply = DesktopContext.getInstance().isUdpRebroadcastEnable() ? "on" : "off";
                  else 
                    commandReply = "UDP re-broadcast not available";
                }  
                else if (LOG_STATUS.equals(command))
                {
//                System.out.println("LOG_STATUS");                  
                  if (DesktopContext.getInstance().isFileRebroadcastAvailable())
                    commandReply = DesktopContext.getInstance().isFileRebroadcastEnable() ? "on" : "off";
                  else 
                    commandReply = "File re-broadcast not available";
                }     
                else if (VERBOSE_STATUS.equals(command))
                {
//                System.out.println("VERBOSE_STATUS");                  
                  commandReply = DesktopContext.getInstance().isDesktopVerbose() ? "on" : "off";
                }  
                else
                {
               // commandReply = "Unknown query";
//                System.out.println("Unmanaged: [" + line + "]");
                }
              }
              else if (line.startsWith("GET /")) // display a file
              {
                fileToFetch = line.substring("GET /".length());
                fileToFetch = fileToFetch.substring(0, fileToFetch.indexOf(" "));
//              System.out.println("********** File to fetch:[" + fileToFetch + "] *************");
              }
//            System.out.println("Read:[" + line + "]");
              if (line.indexOf(":") > -1) // Header?
              {
                String headerKey = line.substring(0, line.indexOf(":"));
                String headerValue = line.substring(line.indexOf(":") + 1);
                header.put(headerKey, headerValue);
              }
            }
            String contentType = "text/plain";

            String content = commandReply;
            if (help)          
            {
              content = (generateHelpContent());
              contentType = "text/html";
            }
            else if (fileToFetch.trim().length() > 0)
            {
              File f = new File(fileToFetch);
              if (!f.exists())
                out.println(fileToFetch + " not found from " + System.getProperty("user.dir"));
              else
              {
                if (fileToFetch.toUpperCase().endsWith(".HTML") ||
                    fileToFetch.toUpperCase().endsWith(".XHTML"))
                  contentType = "text/html";
                else if (fileToFetch.toUpperCase().endsWith(".XML") ||
                         fileToFetch.toUpperCase().endsWith(".XSD"))
                  contentType = "text/xml";
                else if (fileToFetch.toUpperCase().endsWith(".XSL"))
                  contentType = "text/xsl";
                else if (fileToFetch.toUpperCase().endsWith(".TXT"))
                  contentType = "text/plain";
                else if (fileToFetch.toUpperCase().endsWith(".JS"))
                  contentType = "text/javascript";
                else if (fileToFetch.toUpperCase().endsWith(".CSS"))
                  contentType = "text/css";
                else
                  System.out.println("File extension not managed for " + fileToFetch); // We don't read binaries. See below.
//              System.out.println("............... Reading " + f.getAbsolutePath());
                BufferedReader br = new BufferedReader(new FileReader(f));
                String data = "";

                out.print("HTTP/1.1 200 \r\n"); 
                out.print("Content-Type: " + contentType + "\r\n");
//              out.print("Content-Length: " + content.length() + "\r\n");
//              out.print("Access-Control-Allow-Origin: *\r\n"); 
                out.print("\r\n"); // End Of Header
                //
                boolean ok = true;
                while (ok)
                {
                  data = br.readLine();
                  if (data == null)
                    ok = false;
                  else
                    out.println(data);
                }
                br.close();
              }
            }
            
            if (content.length() > 0)
            {
              // Headers?
              out.print("HTTP/1.1 200 \r\n"); 
              out.print("Content-Type: " + contentType + "\r\n");
              out.print("Content-Length: " + content.length() + "\r\n");
              out.print("Access-Control-Allow-Origin: *\r\n"); 
              out.print("\r\n"); // End Of Header
              //
              out.println(content);
            }
            out.flush();
            out.close();
            in.close();
            client.close();
          }
          ss.close();
          System.out.println("Exiting.");
        }
        catch (Exception e)
        {
          System.err.println(e.toString());
          e.printStackTrace();
        }
        System.out.println("HTTP Admin Server done.");
      }
    };
    httpListenerThread.start();
  }

  private String generateHelpContent()
  {
    String str = ""; // "Content-Type: text/html\r\n\r\n";
    str += "<html><head><title>Admin server help</title></head><body><pre>\n";
    str += ("Date is:" + new Date().toString() + "\n\n");
    str += "------------------------------------\n";
    str += "Available commands are:";
    str += "<ul>";
    str += "<li>/help</li>";
    str += "<li>/exit</li>";
    str += "<li>/put/http/on</li>";
    str += "<li>/put/http/off</li>";
    str += "<li>/put/tcp/on</li>";
    str += "<li>/put/tcp/off</li>";
    str += "<li>/put/udp/on</li>";
    str += "<li>/put/udp/off</li>";
    str += "<li>/put/log/on</li>"; // ?logfile=[file.name]</li>";
    str += "<li>/put/log/off</li>";
    str += "<li>/put/verbose/on</li>";
    str += "<li>/put/verbose/off</li>";
    str += "<li>/get/http</li>";
    str += "<li>/get/tcp</li>";
    str += "<li>/get/udp</li>";
    str += "<li>/get/log</li>";
    str += "<li>/get/verbose</li>";
    str += "</ul>";
    str += "------------------------------------\n";
    str += "</pre></body></html>\n";    
    return str;
  }
    
  //  For dev tests
  public static void main(String[] args) throws Exception
  {
//  System.setProperty("admin.http.port", "8080");
    int port = 8080;
    new AdminHttpServer(port);
    Thread t = new Thread()
      {
        public void run()
        {
          synchronized(this)
          {
            try {  this.wait(); } catch (Exception ex) {}
          }
        }
      };
    t.start();
  }
}