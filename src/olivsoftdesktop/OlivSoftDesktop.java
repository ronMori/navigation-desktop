package olivsoftdesktop;

import coreutilities.CheckForUpdateThread;
import coreutilities.NotificationCheck;
import coreutilities.Utilities;

import coreutilities.ctx.CoreContext;

import coreutilities.ctx.CoreEventListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import java.util.TimeZone;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

//import nmea.rmiserver.NMEAServer;

import javax.swing.event.HyperlinkEvent;

import javax.swing.event.HyperlinkListener;

import olivsoftdesktop.ctx.DesktopContext;

import olivsoftdesktop.param.ParamData;
import olivsoftdesktop.param.ParamPanel;

import olivsoftdesktop.utils.DesktopUtilities;

import olivsoftdesktop.utils.gui.UpdatePanel;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

public class OlivSoftDesktop
{
  private OlivSoftDesktop instance = this;
  private JFrame frame = null;
  
  public OlivSoftDesktop()
  {
    ParamPanel.setUserValues();

    frame = new DesktopFrame();
    try { frame.setIconImage(new ImageIcon(this.getClass().getResource("camel.gif")).getImage()); } catch (Exception ignore) {}
    File desktopPos = new File("desktop.xml");
    boolean dataOk = true;
    if (desktopPos.exists())
    {
      DOMParser parser = DesktopContext.getInstance().getParser();
      synchronized (parser)
      {
        try { parser.parse(desktopPos.toURI().toURL()); }
        catch (Exception ex)
        { 
          System.err.println(ex.getLocalizedMessage());
//        ex.printStackTrace(); 
          dataOk = false;
        }        
      }
      if (dataOk)
      {
        try
        {
          XMLDocument doc = parser.getDocument();
          XMLElement root = (XMLElement)doc.selectNodes("/desktop-pos").item(0);
          int x = Integer.parseInt(root.getAttribute("x"));
          int y = Integer.parseInt(root.getAttribute("y"));
          int w = Integer.parseInt(root.getAttribute("w"));
          int h = Integer.parseInt(root.getAttribute("h"));
//        System.out.println("Restoring in " + x + ", " + y);
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          GraphicsDevice[] screenDevices = ge.getScreenDevices();
          boolean foundMatch = false;
          for (GraphicsDevice curGs: screenDevices)
          {
            GraphicsConfiguration[] gc = curGs.getConfigurations();
            for (GraphicsConfiguration curGc: gc)
            {
              Rectangle bounds = curGc.getBounds();
//            System.out.println(bounds.getX() + "," + bounds.getY() + " " + bounds.getWidth() + " x " + bounds.getHeight());
              if (x > bounds.getX() && x < (bounds.getX() + bounds.getWidth()) && y > bounds.getY() && y < (bounds.getY() + bounds.getHeight()))
              {
                foundMatch = true;
                break;
              }
            }
          }

          if (!foundMatch)
          {
            System.out.println("Frame position has been saved on another screen configuration. Reseting.");
            dataOk = false;
          }
          else
          {
            Dimension dim = new Dimension(w, h);
            frame.setSize(dim);
            frame.setLocation(x, y);
          }
        }
        catch (Exception ex)
        {
          dataOk = false;
          System.err.println(ex.getLocalizedMessage());
//        ex.printStackTrace();
        }
      }
    }
    else
      dataOk = false;
  
    if (!dataOk)
    {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = null;
      System.out.println("Defaulting Desktop Dimensions...");
      frame.setSize(new Dimension((int)(screenSize.width * 0.8), (int)(screenSize.height * 0.8)));
      frameSize = frame.getSize();
      frame.setLocation( ( screenSize.width - frameSize.width ) / 2, ( screenSize.height - frameSize.height ) / 2 );
    }
    frame.addWindowListener(new WindowAdapter() 
      {
        public void windowClosing(WindowEvent e)
        {
          DesktopUtilities.doOnExit(instance.getClass().getResource("vista.wav"));
        }
      });    
//  frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setVisible(true);
    checkForUpdate(frame);
    Date compiledDate = null;
    String lastModified = "";
    String fullPath2Class = this.getClass().getName();
    // try manifest first
    // Count number of dots
    int nbdots = 0;
    int i = 0;
    String str = fullPath2Class;
    while (i != -1)
    {
      i = str.indexOf(".", i);
      if (i != -1)
      {
        nbdots += 1;
        str = str.substring(i + 1);
      }
    }
    //  System.out.println("Found " + nbdots + " dot(s)");
    String resource = "";
    for (i=0; i<nbdots; i++)
      resource += (".." + "/");
    resource += ("meta-inf" + "/" + "Manifest.mf");

    String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1) + ".class";
    URL me = this.getClass().getResource(className);
    //  System.out.println("Resource:" + me);
    String strURL = me.toString();
    
    String jarIdentifier = ".jar!/";
    if (strURL.indexOf(jarIdentifier) > -1)
    {
      try 
      { 
        String jarFileURL = strURL.substring(0, strURL.indexOf(jarIdentifier) + jarIdentifier.length()); // Must end with ".jar!/"
    //  System.out.println("Trying to reach [" + jarFileURL + "]");
        URL jarURL = new URL(jarFileURL);
        JarFile myJar = ((JarURLConnection)jarURL.openConnection()).getJarFile();
        Manifest manifest = myJar.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        lastModified = attributes.getValue("Compile-Date");
        System.out.println("Compile-Date found in manifest:[" + lastModified + "]");
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    
    if (lastModified == null || lastModified.trim().length() == 0)
    {
      strURL = strURL.substring(0, strURL.lastIndexOf(className));
      strURL += resource;
      try { me = new URL(strURL); } catch (Exception ex) { System.err.println(ex.toString()); }
      System.out.println("URL:" + me);
    
      try
      {
        URLConnection con = null;
        try { con = me.openConnection(); }
        catch (Exception ex)
        {
          System.out.println("Will try the class...");
        }
        if (con == null)
        {
          me = this.getClass().getResource(className);
          con = me.openConnection();
        }
        lastModified = con.getHeaderField("Last-modified");
        if (lastModified == null)
        {
    //        System.out.println("Manifest not found");
          me = this.getClass().getResource(className);
          con = me.openConnection();
          lastModified = con.getHeaderField("Last-modified");
        }      
    //      else
    //        System.out.println("Found manifest");
    //    System.out.println(me.toExternalForm() + ", Last Modified:[" + lastModified + "]");
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    try
    {
      String datePattern = "E MM/dd/yyyy HH:mm:ss.SS";
      SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.ENGLISH);
      sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
      compiledDate = sdf.parse(lastModified);
      System.out.println("Compile Date parsed with [" + datePattern + "]");
    }
    catch (ParseException pe)
    {
      // From the class ? like Sun, 19 Feb 2012 03:21:22 GMT 
      try
      {
        String datePattern = "E, dd MMM yyyy HH:mm:ss Z";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Pacific/Los_Angeles"));
        compiledDate = sdf.parse(lastModified);        
        System.out.println("Compile Date parsed with [" + datePattern + "]");
      }
      catch (ParseException pe2)
      {
        try
        {
          String datePattern = "E MM/dd/yyyy HH:mm:ss";
          SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.ENGLISH);
          sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
          compiledDate = sdf.parse(lastModified);        
          System.out.println("Compile Date parsed with [" + datePattern + "]");
        }
        catch (ParseException pe3)
        {
          // Give up...
          System.err.println("Trying to parse [" + lastModified + "] as [E MM/dd/yyyy HH:mm:ss]");
          System.err.println(pe3.getLocalizedMessage());
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    checkForNotification(compiledDate);
  }

  private static boolean proceed = false;
  
  public static void checkForUpdate(final JFrame parent)
  {
    // Checking for update
    proceed = true; // TODO A parameter...
    Thread checkForUpdate = // new CheckForUpdateThread("weather_assistant");
      new CheckForUpdateThread(DesktopContext.PRODUCT_ID, 
                               new DOMParser(), 
                               DesktopContext.STRUCTURE_FILE_NAME, 
                               proceed);
    // Add listener
    CoreContext.getInstance().addApplicationListener(new CoreEventListener()
     {
       public void updateCompleted(List<String> fList)
       {
         System.out.println("Update Completed by the Core Context");
         if (fList != null && fList.size() > 0)
         {
           String downloadMess = "";
           for (String s : fList)
             downloadMess += (s + "\n");
           // Display file list
           UpdatePanel updatePanel = new UpdatePanel();
           if (proceed)
             updatePanel.setTopLabel("Following file(s) updated");
           else
             updatePanel.setTopLabel("Available update(s):");
           updatePanel.setFileList(downloadMess);
           JOptionPane.showMessageDialog(parent, updatePanel, "Automatic updates", JOptionPane.INFORMATION_MESSAGE);
         }
         // TODO Remove CoreContext listener
       }
     });
    checkForUpdate.start();
  }

  private final static String NOTIFICATION_PROP_FILE_NAME = "notification_" + DesktopContext.PRODUCT_KEY + ".properties";
  private final static SimpleDateFormat SDF = new SimpleDateFormat("E dd MMM yyyy, HH:mm:ss z");
  
  public static void checkForNotification(final Date manifestDate)
  {
    // Checking for notification
    proceed = true; // A Parameter 
    if (proceed)
    {
      Thread checkForNotification = new Thread()
        {
          public void run()
          {
            String notificationDate = "";
            Date providedDate = manifestDate;
            
            Properties props = new Properties();
            try
            {
              FileInputStream fis = new FileInputStream(NOTIFICATION_PROP_FILE_NAME);
              props.load(fis);
              fis.close();
              notificationDate = props.getProperty("date"); // UTC date
            }
            catch (Exception ex)
            {
              System.out.println("Properties file [" + NOTIFICATION_PROP_FILE_NAME + "] not found");
            }    
            try
            {
              if (providedDate != null)
              {
                Date propertiesDate = null;
                try
                {
                  propertiesDate = NotificationCheck.getDateFormat().parse(notificationDate);
                }
                catch (ParseException pe)
                {
                  if (notificationDate.trim().length() > 0)
                    System.err.println(pe.getLocalizedMessage());                  
                }
  //              System.out.println("Properties Date:" + propertiesDate.toString() + ", Provided Date:" + providedDate.toString());
                if (notificationDate == null || notificationDate.trim().length() == 0 || propertiesDate.before(providedDate))
                  notificationDate = NotificationCheck.getDateFormat().format(providedDate);          
              }
              NotificationCheck nc = new NotificationCheck(DesktopContext.PRODUCT_KEY, notificationDate);
              Map<Date, String> map = nc.check();
              String productName = nc.getProductName();
              // Display Notification Here.
              if (map.size() > 0)
              {
                String title = "Notifications";
                if (productName.trim().length() > 0)
                  title += (" for " + productName); // LOCALIZE
                int resp = displayNotification(title, nc);
                if (resp == JOptionPane.OK_OPTION)
                {
                  props.setProperty("date", NotificationCheck.getDateFormat().format(new Date())); // Write UTC date
                  FileOutputStream fos = new FileOutputStream(NOTIFICATION_PROP_FILE_NAME);
                  props.store(fos, "Last notification date");
                  fos.close();
                }
              }
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
            }    
          }
        };
      checkForNotification.start();           
    }
  }

  private static int displayNotification(String title, NotificationCheck nc)
  {    
    String result = 
    "<html><head><style type='text/css'>" +
    "body { background : #efefef; color : #000000; font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "h1 { color: white; font-style: italic; font-size: 14pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular; background-color: black; padding-left: 5pt }\n" + 
    "h2 { font-size: 12pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "h3 { font-style: italic; font-weight: bold; font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular; bold:  }\n" + 
    "li { font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "p { font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "td { font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "small { font-size: 8pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "blockquote{ font-style: italic; font-size: 10pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }-->\n" + 
    "em { font-size: 10pt; font-style: italic; font-weight: bold; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "pre { font-size: 9pt; font-family: Courier New, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "address { font-size: 8pt; font-family: Arial, Helvetica, Geneva, Swiss, SunSans-Regular }\n" + 
    "a:link { color : #000000} \n" + 
    "a:active { color: #000000} \n" + 
    "a:visited { color : #000000}\n" +
    "</style></head><body>\n";
    Map<Date, String> map = nc.check();
    // Display Notification Here.
    if (map.size() > 0)
    {
      Set<Date> keys = map.keySet();
      Date[] da = keys.toArray(new Date[keys.size()]);
      Arrays.sort(da);
      for (Date d : da)
      {
        String mess = map.get(d);
        result += ("<br><i><b>" + SDF.format(d) + "</b></i><br>" + mess + "<br>"); 
      }
    }
    // Display list
    result += ("</body></html>");
    // Produce clickable list here
    JPanel notificationPanel = new JPanel();
    notificationPanel.setPreferredSize(new Dimension(500, 300));
    JEditorPane jEditorPane = new JEditorPane();
    JScrollPane jScrollPane = new JScrollPane();
    notificationPanel.setLayout(new BorderLayout());
    jEditorPane.setEditable(false);
    jEditorPane.setFocusable(false);
    jEditorPane.setFont(new Font("Verdana", 0, 10));
    jEditorPane.setBackground(Color.lightGray);
    jScrollPane.getViewport().add(jEditorPane, null);
    jEditorPane.addHyperlinkListener(new HyperlinkListener()
      {
        public void hyperlinkUpdate(HyperlinkEvent he)
        {
          try
          {
            if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
    //            System.out.println("URL Activated:" + he.getURL().toString());
              String activatedURL = he.getURL().toString();
              String value = activatedURL;
              if (!value.startsWith("http://"))
                value = value.substring(value.indexOf("http://"));
              if (value.startsWith("http://"))
              {
                if (value.endsWith(".html/"))
                  value = value.substring(0, value.length() - 1);
                if (value.endsWith("\\"))
                  value = value.substring(0, value.length() - 1);
                Utilities.openInBrowser(value);
              }
            }
          }
          catch (Exception ioe)
          {
            ioe.printStackTrace();
          }
        }
      });

    try
    {
      File tempFile = File.createTempFile("data", ".html");
      tempFile.deleteOnExit();
      BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
      bw.write(result);
      bw.flush();
      bw.close();              
      jEditorPane.setPage(tempFile.toURI().toURL());
      jEditorPane.repaint();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    notificationPanel.add(jScrollPane, BorderLayout.CENTER);
  //  JLabel nbDayLabel = new JLabel();
  //  nbDayLabel.setText("Blah-blah-blah.");
  //  notificationPanel.add(nbDayLabel, BorderLayout.SOUTH);

    int resp = JOptionPane.showConfirmDialog(null, notificationPanel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    return resp;
  }

  public static void main(String[] args)
  {
    String lnf = System.getProperty("swing.defaultlaf");
    if (lnf == null) // Let the -Dswing.defaultlaf do the job.
    {
      try
      {
        if (System.getProperty("swing.defaultlaf") == null)
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    JFrame.setDefaultLookAndFeelDecorated(true); // L&F at the main frame level.
    
    new OlivSoftDesktop();
  }
}
