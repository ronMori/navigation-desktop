package olivsoftdesktop;

import app.almanac.ctx.APContext;
import app.almanac.ctx.APEventListener;
import app.almanac.gui.AlmanacPublisherInternalFrame;

import app.clear.ClearLunarInternalFrame;
import app.clear.ctx.LDContext;
import app.clear.ctx.LDEventListener;

import app.realtimealmanac.RealTimeAlmanacInternalFrame;
import app.realtimealmanac.ctx.RTAContext;
import app.realtimealmanac.ctx.RTAEventListener;

import app.sightreduction.SightReductionInternalFrame;
import app.sightreduction.ctx.SRContext;
import app.sightreduction.ctx.SREventListener;

import app.starfinder.SkyInternalFrame;
import app.starfinder.ctx.SFContext;
import app.starfinder.ctx.StarFinderEventListener;

import calculation.SightReductionUtil;

import chartlib.ctx.ChartLibContext;

import chartlib.event.ChartLibListener;

import coreutilities.Utilities;

import coreutilities.sql.SQLUtil;

import generatelocator.GenInternalFrame;

import generatelocator.ctx.LocatorContext;
import generatelocator.ctx.LocatorEventListener;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.lang.reflect.Constructor;

import java.net.URL;

import java.sql.Connection;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import nauticalalmanac.Anomalies;
import nauticalalmanac.Context;
import nauticalalmanac.Core;
import nauticalalmanac.Jupiter;
import nauticalalmanac.Mars;
import nauticalalmanac.Moon;
import nauticalalmanac.Saturn;
import nauticalalmanac.Venus;

import nightsky.ctx.SkyMapContext;
import nightsky.ctx.SkyMapEventListener;

import nightsky.full.DualInternalFrame;

import nmea.event.NMEAListener;

import nmea.server.constants.Constants;
import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;
import nmea.server.datareader.CustomNMEAClient;
import nmea.server.utils.HTTPServer;

import nmea.ui.NMEAInternalFrame;
import nmea.ui.viewer.elements.AWDisplay;

import ocss.nmea.parser.Angle180;
import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Depth;
import ocss.nmea.parser.Distance;
import ocss.nmea.parser.GeoPos;
import ocss.nmea.parser.SVData;
import ocss.nmea.parser.Speed;
import ocss.nmea.parser.Temperature;
import ocss.nmea.parser.UTCDate;
import ocss.nmea.parser.UTCTime;

import olivsoftdesktop.ctx.DesktopContext;
import olivsoftdesktop.ctx.DesktopEventListener;

import olivsoftdesktop.param.CategoryPanel;
import olivsoftdesktop.param.ParamData;
import olivsoftdesktop.param.ParamPanel;
import olivsoftdesktop.param.RebroadcastPanel;

import olivsoftdesktop.utils.BackgroundWindow;
import olivsoftdesktop.utils.DesktopNMEAReader;
import olivsoftdesktop.utils.DesktopUtilities;
import olivsoftdesktop.utils.GPSDWriter;
import olivsoftdesktop.utils.HTTPClient;
import olivsoftdesktop.utils.TCPWriter;
import olivsoftdesktop.utils.UDPWriter;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import org.w3c.dom.NodeList;

import sailfax.ctx.SailFaxContext;

import sailfax.event.SailfaxEventListener;

import sailfax.generation.ui.TransmissionSelectorInternalFrame;

import tideengineimplementation.gui.TideInternalFrame;
import tideengineimplementation.gui.ctx.TideContext;
import tideengineimplementation.gui.ctx.TideEventListener;

import user.util.GeomUtil;
import user.util.TimeUtil;

import util.NMEACache;


public class DesktopFrame
  extends JFrame
{
  @SuppressWarnings("compatibility:-245439477831537528")
  private final static long serialVersionUID = 1L;
  private final static String LISTENER_FAMILY = "DESKTOP_SERIALPORT_BROADCASTER";
  
  private boolean fullscreen = false;
  
  private DesktopFrame instance = this;
  private boolean minimizeNMEAConsole = false;
  
  private final static SimpleDateFormat SDF  = new SimpleDateFormat("dd MMM yyyy '\n'HH:mm:ss 'UTC'");
  private final static SimpleDateFormat SDF2 = new SimpleDateFormat("'Solar Time:' HH:mm:ss");
//private final static SimpleDateFormat SDF3 = new SimpleDateFormat("HH:mm:ss 'UTC'");
  private final static DecimalFormat DF2     = new DecimalFormat("00");
  private final static DecimalFormat DF22    = new DecimalFormat("00.00");
  
  private BorderLayout layoutMain = new BorderLayout();
  private JMenuBar menuBar = new JMenuBar();
  
  private JMenu menuFile = new JMenu();
  
  private JMenuItem chartLibMenuItem    = new JMenuItem();
  private JMenu nmeaMenuItem            = new JMenu();
  private JMenuItem nmeaConsoleMenuItem = new JMenuItem();
  private JCheckBoxMenuItem backGroundNMEARead = new JCheckBoxMenuItem();
//private JCheckBoxMenuItem backGroundNMEAServer = new JCheckBoxMenuItem();
  private JMenuItem replayNmeaMenuItem  = new JMenuItem();
  private JMenuItem starFinderMenuItem  = new JMenuItem();
  private JMenuItem d2102StarFinderMenuItem  = new JMenuItem();
  private JMenuItem sailFaxMenuItem     = new JMenuItem();
  private JMenuItem lunarMenuItem       = new JMenuItem();
  private JMenuItem srMenuItem          = new JMenuItem();
  private JMenuItem almanacMenuItem     = new JMenuItem();
  private JMenuItem locatorMenuItem     = new JMenuItem();
  private JMenuItem tideMenuItem        = new JMenuItem();
  private JMenuItem realTimeAlmanacMenuItem = new JMenuItem();
  
  private JMenuItem menuFileExit = new JMenuItem();
  
  private JMenu menuTools = new JMenu();
  private JMenuItem menuToolsPreferences = new JMenuItem();
  private JMenuItem menuToolsFullScreen = new JMenuItem();
  
  private JMenuItem menuToolsDisplayTime           = new JMenuItem();
  private JMenuItem menuToolsDisplayGPSTime        = new JMenuItem();
  private JMenuItem menuToolsDisplayGPSSignal      = new JMenuItem();
  private JMenuItem menuToolsDisplayGPSPos         = new JMenuItem();
  private JMenuItem menuToolsDisplayGPSSpeedCourse = new JMenuItem();
  private JMenuItem menuToolsDisplayNMEA           = new JMenuItem();
  private JMenuItem menuToolsDisplayAW             = new JMenuItem();
  private JMenuItem menuToolsDisplayRTA            = new JMenuItem();
  
  private JMenu menuHelp = new JMenu();
  private JMenuItem menuHelpAbout = new JMenuItem();
  private JPanel bottomPanel = new JPanel(new BorderLayout());
  private JLabel statusBar = new JLabel();
  private JProgressBar progressBar = new JProgressBar();

  private String timeString    = "00:00:00";
  private String posString     = "-\n-";
  private String hsString      = "0 kts\n0\272";
  private String gpsTimeString = "00:00:00";
  private String nmeaString    = "00:00:00";
  private String rtaString     = "Almanac";
  
  private String windString    = "00.00 000\n";
  private String bspString     = "00.00\n";
  private String hdgString     = "0\272\n";
  private String logString     = "000 000\n";
  private String depthString   = "00.0 m\n";
  private String tempString    = "0\272";
  
  private transient List<GPSSatellite> satList = null;
  
  private transient Thread nmeaDataThread = null;
  private transient DesktopNMEAReader nmeaReader = null;
  
  private JDesktopPane desktop = new JDesktopPane()
    {
      @SuppressWarnings("compatibility:-2193568848390199696")
      private final static long serialVersionUID = 1L;
      private Polygon buildReflectPolygon(JDesktopPane panel)
      {
        // Build 101 points + 2 (bottom)
        int[] x = new int[103];
        int[] y = new int[103];

        for (int i=0; i<=100; i++)
        {
          x[i] = (int)((float)i * (float)panel.getWidth() / 100f);
          double _x = ((i / 100d) * 30d) - 15d;
          double _y = ((_x / 10d) * (_x / 10d) * (_x / 10d));
//        System.out.println("i=" + i + ", x:" + x[i] + ", X:" + _x + ", y:" + _y);
          y[i] = (int)(panel.getHeight() / 2d) - (int)((panel.getHeight() / 12d) * _y);
        }
        x[101] = panel.getWidth(); y[101] = panel.getHeight();
        x[102] = 0;                y[102] = panel.getHeight();

        Polygon p = new Polygon(x, y, 103);
        
        return p;
      }
      
      public void paintComponent(Graphics gr)
      {
        ((Graphics2D)gr).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);      
        ((Graphics2D)gr).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                          RenderingHints.VALUE_ANTIALIAS_ON);      
        Color startColor = Color.black; // new Color(255, 255, 255);
        Color endColor   = Color.blue;  // new Color(102, 102, 102);
        GradientPaint gradient = new GradientPaint(0, this.getHeight(), startColor, 0, 0, endColor); // vertical, upside down
        ((Graphics2D)gr).setPaint(gradient);
        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
        
//      gr.setColor(Color.LIGHT_GRAY);
        gr.setColor(Color.GREEN);
        Font f = gr.getFont();
        gr.setFont(f.deriveFont(Font.PLAIN, 32f));
        String s = (ParamPanel.getData()[ParamData.DESKTOP_MESSAGE][ParamPanel.PRM_VALUE]).toString();
        String[] line = s.split("\\\\n"); // Regexp
        int fontSize = gr.getFont().getSize();
        int strY = (this.getHeight() / 2) - ((line.length - 1) * fontSize) - (fontSize / 2);
        for (int i=0; i<line.length; i++)
        {
          int strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(line[i].trim());
          gr.drawString(line[i].trim(), (this.getWidth() / 2) - (strWidth / 2), strY);
          strY += fontSize;
        }
        gr.setFont(f);
        
        if (false) // C'est moche
        {
          startColor = new Color(255, 255, 255);
          endColor   = new Color(102, 102, 102);
          // Transparency
          ((Graphics2D)gr).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
          gradient = new GradientPaint(0, this.getHeight(), startColor, 0, 0, endColor); // vertical, upside down
          ((Graphics2D)gr).setPaint(gradient);
          Polygon p = new Polygon(new int[] {0, 0, this.getWidth(), this.getWidth()},
                                  new int[] {(int)(3 * this.getHeight() / 4), this.getHeight(), this.getHeight(), (int)(1 * this.getHeight() / 4)}, 
                                  4);
          p = buildReflectPolygon(this);
          gr.fillPolygon(p);
        }
        // Reset Transparency
        float alphaForEveryone = ((Float)ParamPanel.getData()[ParamData.INTERNAL_FRAMES_TRANSPARENCY][ParamPanel.PRM_VALUE]).floatValue();
        ((Graphics2D)gr).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaForEveryone));

        if (bgImage != null)
        {
          Dimension mfSize = desktop.getSize();
          int bgW = bgImage.getIconWidth();
          int bgH = bgImage.getIconHeight();
          int x = (mfSize.width / 2) - (bgW / 2);
          int y = (mfSize.height / 2) - (bgH / 2);
          if (x < 0) x = 0;
          if (y < 0) y = 0;
          bgLabel.setBounds(x, y, bgImage.getIconWidth(), bgImage.getIconHeight());    
          gr.drawImage(bgImage.getImage(), x, y, null); // , bgImage.getIconWidth(), bgImage.getIconHeight());    
        }
        for (BackgroundWindow bgw : bgwal)
        {
          if (bgw.isDisplayBGWindow())
          {
            if (bgw.getWinTitle().equals(TIME_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, timeString);
            else if (bgw.getWinTitle().equals(GPS_TIME_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, gpsTimeString);
            else if (bgw.getWinTitle().equals(POSITION_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, posString);
            else if (bgw.getWinTitle().equals(SOG_COG_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, hsString);
            else if (bgw.getWinTitle().equals(NMEA_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, nmeaString);
            else if (bgw.getWinTitle().equals(AW_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, windString);
            else if (bgw.getWinTitle().equals(REALTIME_ALMANAC_BG_WINDOW_TITLE))
              bgw.displayBackGroundWindow(gr, rtaString);
            else
              bgw.paintBackgroundWindow(gr);
          }
        }
      }
    };

  private static List<BackgroundWindow> bgwal = new ArrayList<BackgroundWindow>(1);
  
  private JInternalFrame sailfaxFrame    = null;
  private JInternalFrame nmeaDataFrame   = null;
  private JInternalFrame starFinder      = null;
  private JInternalFrame skyMap          = null;
  private JInternalFrame chartLib        = null;
  private JInternalFrame clearLunar      = null;
  private JInternalFrame sightReduction  = null;
  private JInternalFrame locator         = null;
  private JInternalFrame almanac         = null;
  private JInternalFrame tides           = null;
  private JInternalFrame realTimeAlmanac = null;
  
  private transient Connection chartConnection = null;
//private Connection logConnection = null;
  
  private int TCPPort  = -1;
  private int UDPPort  = -1;
  private int HTTPPort = -1;
  private int RMIPort  = -1;
  private int GPSDPort = -1;
  private transient UDPWriter udpWriter   = null;
  private transient TCPWriter tcpWriter   = null;
  private transient GPSDWriter gpsdWriter = null;
  private RebroadcastPanel rebroadcastPanel = null;
  private boolean rebroadcastVerbose = "true".equals(System.getProperty("verbose", "false"));

  public DesktopFrame()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private final static int SAILFAX              =  0;
  private final static int NMEA_CONSOLE         =  1;
  private final static int CHARTLIB             =  2;
  private final static int STARFINDER           =  3;
  private final static int LUNAR                =  4;
  private final static int SR                   =  5;
  private final static int LOCATOR              =  6;
  private final static int ALMANAC              =  7;
  private final static int READ_REBROADCAST     =  8;
  private final static int REPLAY_NMEA          =  9;
//private final static int RMI_NMEA_SERVER      = 10; // Unused, removed
  private final static int TIDES                = 11;
  private final static int STARFINDER_2012D     = 12;
  private final static int REAL_TIME_ALMANAC    = 13;

  private final static String TIME_BG_WINDOW_TITLE       = "UTC";
  private final static String GPS_SIGNAL_BG_WINDOW_TITLE = "GPS Signal";
  private final static String GPS_TIME_BG_WINDOW_TITLE   = "GPS Time";
  private final static String POSITION_BG_WINDOW_TITLE   = "GPS Fix";
  private final static String SOG_COG_BG_WINDOW_TITLE    = "GPS SOG & COG";
  private final static String AW_BG_WINDOW_TITLE         = "App.Wind";

  private final static String NMEA_BG_WINDOW_TITLE       = "NMEA Data";
  private final static String REALTIME_ALMANAC_BG_WINDOW_TITLE = "Real time Almanac";

  private transient BackgroundWindow timeBGWindow = new BackgroundWindow(TIME_BG_WINDOW_TITLE);
  private transient BackgroundWindow gpsSignalBGWindow = new BackgroundWindow(GPS_SIGNAL_BG_WINDOW_TITLE, 
                                                            ((ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString().equals("HTTP") ?
                                                            ("http://" +  
                                                             (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" +
                                                             (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString() + "/") :
                                                            // otherwise, like COM1:4800, UDP:8001, TCP:7001
                                                            ((ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE]).toString().equals("Serial") ?
                                                            ((ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString() + ":" +
                                                             (ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE]).toString()) : 
                                                               ((ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE]).toString().equals("TCP") ?
                                                                "TCP " + (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString() :
                                                                "UDP " + (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString()) )))
  {
    private int ONE_SAT_COLUMN_WIDTH  =  20;
    private int ONE_SAT_COLUMN_HEIGHT = 100;
    
    @Override
    public void calculateWinHeight(Graphics graphics, String dataString)
    {
      super.calculateWinHeight(graphics, dataString);
      if (satList != null)
      {
        int h = 20 /* 10 above and 10 below */ 
              + ONE_SAT_COLUMN_HEIGHT + BG_WINDOW_HEADER_SIZE + (2 * BG_WINDOW_BORDER_SIZE);
        if (h > this.bgWinH)
          this.bgWinH = h;
      }
    }

    @Override
    public void calculateWinWidth(Graphics graphics)
    {
      super.calculateWinWidth(graphics);
      if (satList != null)
      {
        if (this.bgWinW < 5 + (satList.size() * ONE_SAT_COLUMN_WIDTH))
        {
          this.bgWinW = (2 * (BG_WINDOW_BORDER_SIZE + BG_WINDOW_DATA_OFFSET_SIZE) ) +
                        5 + (satList.size() * ONE_SAT_COLUMN_WIDTH);
        }
      }
    }

    @Override
    public void drawData(Graphics graphics, String dataString, int xs, int ys)
    {
      super.drawData(graphics, dataString, xs, ys);
      if (satList != null)
      {
        xs += 5;
        Font f = new Font("Courier New", Font.PLAIN, 10);
        graphics.setFont(f);
        int bottomLine = ys + ONE_SAT_COLUMN_HEIGHT;
        for (GPSSatellite gs : satList)
        {
          int snr = gs.getSnr();
          int h = (int)((double)ONE_SAT_COLUMN_HEIGHT * ((double)snr / 100d));
//        System.out.println("Snr:" + snr + ", H:" + h);
          graphics.setColor(Color.gray);
          graphics.fillRect(xs, bottomLine - h, ONE_SAT_COLUMN_WIDTH, h);
          graphics.setColor(Color.black);
          graphics.drawRect(xs, bottomLine - h, ONE_SAT_COLUMN_WIDTH, h);
          
          String s = Integer.toString(gs.getId());
          int strWidth = graphics.getFontMetrics(dataFont).stringWidth(s);
          int middle = xs + (int)((double)ONE_SAT_COLUMN_WIDTH / 2d);
          graphics.drawString(s, 
                              middle - (int)((double)strWidth / 2d), 
                              bottomLine - (5 + f.getSize()));
          
          xs += ONE_SAT_COLUMN_WIDTH;
        }
      }
    }
  };
  private transient BackgroundWindow gpsTimeBGWindow  = new BackgroundWindow(GPS_TIME_BG_WINDOW_TITLE);
  private transient BackgroundWindow positionBGWindow = new BackgroundWindow(POSITION_BG_WINDOW_TITLE);
  private transient BackgroundWindow sogcogBGWindow   = new BackgroundWindow(SOG_COG_BG_WINDOW_TITLE);
  private transient BackgroundWindow nmeaBGWindow     = new BackgroundWindow(NMEA_BG_WINDOW_TITLE);
  private transient BackgroundWindow awBGWindow       = new BackgroundWindow(AW_BG_WINDOW_TITLE)
    {
      private AWDisplay awDisplay = new AWDisplay("App.Wind", "00.00", false);
      private int awDisplayWidth  = 150;
      private int awDisplayHeight = 150;
      
      @Override
      public void drawData(Graphics graphics, String dataString, int xs, int ys)
      {
//      System.out.println("DataString:[" + dataString + "]");
        // Parse Data String
        double awa = 0d;
        double aws = 0d;
        int idxAWS = dataString.indexOf("AWS:"); 
        int idxAWA = dataString.indexOf("AWA:"); 
        if (idxAWS > -1)
        {
          try
          {
            aws = Double.parseDouble(dataString.substring(idxAWS + "AWS:".length(), dataString.indexOf(" kts,")));
          }
          catch (NumberFormatException nfe)
          {
            System.err.println(nfe.getLocalizedMessage());
          }
        }
        if (idxAWA > -1)
        {
          try
          {
            awa = Double.parseDouble(dataString.substring(idxAWA + "AWA:".length()));
          }
          catch (NumberFormatException nfe)
          {
            System.err.println(nfe.getLocalizedMessage());
          }
        }
        // Draw AW Display here
        awDisplay.setOpaque(false);
        awDisplay.setVisible(true);
        Dimension size = new Dimension(awDisplayWidth, awDisplayHeight);
        this.setViewPortDimension(size);
        this.setRecalculateWinDimension(false);
        this.setWithZoomButtons(false);
        awDisplay.setSize(size);
        awDisplay.setPreferredSize(size);
        awDisplay.setGraphicOffsets(xs, ys);
        // Set values here
        awDisplay.setAWA(awa);
        awDisplay.setAWS(aws);
        awDisplay.paintComponent(graphics);
        String awsStr = AWDisplay.speedFmt.format(aws);
        graphics.setFont(this.dataFont);
        graphics.setColor(Color.red);
        int strW = graphics.getFontMetrics(dataFont).stringWidth(awsStr);
        graphics.drawString(awsStr.trim(), 
                            xs + BG_WINDOW_BORDER_SIZE + (awDisplayWidth / 2) - (strW / 2), 
                            ys + BG_WINDOW_HEADER_SIZE + (awDisplayHeight / 2) - 5); // AWS
      }
    };
  private transient BackgroundWindow rtaBGWindow = new BackgroundWindow(REALTIME_ALMANAC_BG_WINDOW_TITLE);
  
  ImageIcon bgImage = null;
  JLabel bgLabel = null;
  
  private void jbInit()
    throws Exception
  {
    desktop.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent e)
        {
          super.mouseClicked(e);
          for (BackgroundWindow bgw : bgwal)
          {
            int button = bgw.isMouseOnBGWindowButton(e);
            if (bgw.isMouseInBGWindow(e) && button != 0)
            {
  //          System.out.println("Button Clicked:" + button);
              if (button == BackgroundWindow.CLOSE_IMAGE)
              {
                bgw.setDisplayBGWindow(false);
                menuToolsDisplayTime.setEnabled(!(getBGWinByTitle(TIME_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayGPSTime.setEnabled(!(getBGWinByTitle(GPS_TIME_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayGPSSignal.setEnabled(!(getBGWinByTitle(GPS_SIGNAL_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayGPSPos.setEnabled(!(getBGWinByTitle(POSITION_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayGPSSpeedCourse.setEnabled(!(getBGWinByTitle(SOG_COG_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayNMEA.setEnabled(!(getBGWinByTitle(NMEA_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayAW.setEnabled(!(getBGWinByTitle(AW_BG_WINDOW_TITLE).isDisplayBGWindow()));
                menuToolsDisplayRTA.setEnabled(!(getBGWinByTitle(REALTIME_ALMANAC_BG_WINDOW_TITLE).isDisplayBGWindow()));
              }
              else if (button == BackgroundWindow.ZOOMEXPAND_IMAGE)
              {                                     
                bgw.increaseSize();
              }
              else if (button == BackgroundWindow.ZOOMSHRINK_IMAGE)  
              {                                     
                bgw.decreaseSize();
              }
              repaint();
            }
          }
        }

        /*
        @Override
        public void mouseDragged(MouseEvent e)
        {
          super.mouseDragged(e);
          for (BackgroundWindow bgw : bgwal)
          {
            if (bgw.isBgWindowBeingDragged()) 
            {
              System.out.println("MouseListener: Mouse dragged in AltWin, " + e.getX() + ", " + e.getY());
              int x = e.getX();
              int y = e.getY();
              bgw.setBgWinX(bgw.getBgWinX() + (x + bgw.getDragStartX()));
              bgw.setBgWinY(bgw.getBgWinY() + (y + bgw.getDragStartY()));
              bgw.setDragStartX(x);
              bgw.setDragStartY(y);
              repaint();
            }
          }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
          super.mouseMoved(e);
        }
        */
        @Override
        public void mousePressed(MouseEvent e)
        {
          super.mousePressed(e);
          for (BackgroundWindow bgw : bgwal)
          {
            if (bgw.isMouseInBGWindow(e))
            {
  //          System.out.println("Pressed: It's in!");
              bgw.setBgWindowBeingDragged(true);
              bgw.setDragStartX(e.getX());
              bgw.setDragStartY(e.getY());
            }
          }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
          super.mouseReleased(e);
          for (BackgroundWindow bgw : bgwal)
          {
            bgw.setBgWindowBeingDragged(false);
          }
        }
      });
    desktop.addMouseMotionListener(new MouseMotionAdapter()
      {
        @Override
        public void mouseDragged(MouseEvent e)
        {
          super.mouseDragged(e);
          for (BackgroundWindow bgw : bgwal)
          {
            if (bgw.isBgWindowBeingDragged())
            {
//            System.out.println("MouseMotionListener :Mouse dragged in BGWin, " + e.getX() + ", " + e.getY());
              int x = e.getX();
              int y = e.getY();
              
//            System.out.println("BGWinX:" + bgw.getBgWinX() + ", BGWinY:" + bgw.getBgWinY());
//            System.out.println("StartDX:" + bgw.getDragStartX() + ", StartDY:" + bgw.getDragStartY());
              
              bgw.setBgWinX(bgw.getBgWinX() + (x - bgw.getDragStartX()));
              bgw.setBgWinY(bgw.getBgWinY() + (y - bgw.getDragStartY()));
              bgw.setDragStartX(x);
              bgw.setDragStartY(y);
              repaint();
            }
          }
        }
        
        /*
        @Override
        public void mouseMoved(MouseEvent e)
        {
          super.mouseMoved(e);
        }
        */
      });

    timeBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    bgwal.add(timeBGWindow);
    
    bgwal.add(gpsSignalBGWindow);
    gpsSignalBGWindow.setBgWinX(100);
    gpsSignalBGWindow.setBgWinY(10);
    gpsSignalBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    gpsSignalBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(positionBGWindow);
    positionBGWindow.setBgWinX(100);
    positionBGWindow.setBgWinY(40);
    
    positionBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    positionBGWindow.setMinNumLine(2);
    positionBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(sogcogBGWindow);
    sogcogBGWindow.setBgWinX(100);
    sogcogBGWindow.setBgWinY(70);
    sogcogBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    sogcogBGWindow.setMinNumLine(2);
    sogcogBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(gpsTimeBGWindow);
    gpsTimeBGWindow.setBgWinX(100);
    gpsTimeBGWindow.setBgWinY(100);
    gpsTimeBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    gpsTimeBGWindow.setMinNumLine(1);
    gpsTimeBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(nmeaBGWindow);
    nmeaBGWindow.setBgWinX(120);
    nmeaBGWindow.setBgWinY(120);
    nmeaBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    nmeaBGWindow.setMinNumLine(1);
    nmeaBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(awBGWindow);
    awBGWindow.setBgWinX(140);
    awBGWindow.setBgWinY(140);
    awBGWindow.setDataFontColor(Color.cyan);
    awBGWindow.setMinNumLine(5);
    awBGWindow.setDisplayBGWindow(false);
    
    bgwal.add(rtaBGWindow);
    rtaBGWindow.setAlignment(new int[] {Utilities.LEFT_ALIGNED, Utilities.LEFT_ALIGNED, Utilities.RIGHT_ALIGNED});
    rtaBGWindow.setBgWinX(120);
    rtaBGWindow.setBgWinY(120);
    rtaBGWindow.setDataFontColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
    rtaBGWindow.setMinNumLine(1);
    rtaBGWindow.setDisplayBGWindow(false);
    
    DesktopContext.getInstance().setTopFrame(this);
    setBackgroundImage();    
//  desktop.setBackground(Color.red);
    
//  desktop.add(bgLabel, new Integer(Integer.MIN_VALUE + 1));
    
    this.setJMenuBar( menuBar );
    this.getContentPane().setLayout( layoutMain );

//  this.setSize(new Dimension(800, 600));
    this.setTitle( "OlivSoft Desktop" );
    this.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          this_windowClosing(e);
        }
      });
    this.addComponentListener(new ComponentAdapter()
      {
        public void componentResized(ComponentEvent evt) 
        {
          Component c = (Component)evt.getSource();
          if (c instanceof DesktopFrame)
          {
            centerBG();
          }
          else
            System.out.println("It's a " + c.getClass().getName());
        }
      });
    
    menuFile.setText( "Applications" );
    
    chartLibMenuItem.setText("Chart Library");
    nmeaMenuItem.setText("NMEA Data");
    nmeaConsoleMenuItem.setText("NMEA Console");
    replayNmeaMenuItem.setText("Replay NMEA Data");
    starFinderMenuItem.setText("Planetarium");
    d2102StarFinderMenuItem.setText("Star Finder (2102-D)");
    sailFaxMenuItem.setText("SailFax");
    lunarMenuItem.setText("Clear Lunar Distance");
    srMenuItem.setText("Sight Reduction");
    almanacMenuItem.setText("Almanac Publisher");
    realTimeAlmanacMenuItem.setText("Real Time Almanac");
    locatorMenuItem.setText("Google Locator");
    boolean activate = true;
    try { Class.forName("generatelocator.GenInternalFrame"); } catch (ClassNotFoundException cnfe) { activate = false; }
    locatorMenuItem.setEnabled(activate);
    tideMenuItem.setText("Tides");
    
    backGroundNMEARead.setText("Read NMEA Port");
    backGroundNMEARead.setToolTipText("<html>Read NMEA Port<br>and re-broadcast.<br>See Preferences for details</html>");
    backGroundNMEARead.setSelected(false);
    
//  backGroundNMEAServer.setText("Start RMI NMEA Server");
//  backGroundNMEAServer.setToolTipText("<html>RMI NMEA Server<br>For remote access</html>");
//  backGroundNMEAServer.setSelected(false);
    
    chartLibMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(CHARTLIB); } } );
    nmeaConsoleMenuItem.addActionListener( new ActionListener() 
      { 
        public void actionPerformed( ActionEvent ae ) 
        { 
          // Prompt user to start reading serial port if it is down
          if (!DesktopContext.getInstance().isReadingNMEA())
          {
            String nmeaPortName = "";
            String channel = ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE].toString();
            if ("Serial".equals(channel))
            {
              nmeaPortName = channel + " " + ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE].toString() + ":" + ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE].toString();
            }
            else if ("UDP".equals(channel))
            {
              nmeaPortName = channel + " " + ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE].toString() + ":" + ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE].toString();
            }
            else if ("TCP".equals(channel))
            {
              nmeaPortName = channel + " " + ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE].toString() + ":" + ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE].toString();
            }
            int resp = JOptionPane.showConfirmDialog(instance, "NMEA port is not being read.\n" + nmeaPortName + "\nDo you want to start reading it?", "NMEA Console", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION)
            {
              System.out.println("Starting NMEA port reading.");
              DesktopContext.getInstance().fireStartReadingNMEAPort();
            }
          }
          // Launch Console
          appRequest_ActionPerformed(NMEA_CONSOLE); 
        } 
      });
    replayNmeaMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(REPLAY_NMEA); } } );
    starFinderMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(STARFINDER); } } );
    d2102StarFinderMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(STARFINDER_2012D); } } );
    sailFaxMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(SAILFAX); } } );
    lunarMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LUNAR); } } );
    srMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(SR); } } );
    realTimeAlmanacMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(REAL_TIME_ALMANAC); } } );
    almanacMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(ALMANAC); } } );
    locatorMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LOCATOR); } } );
    tideMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(TIDES); } } );
    
    backGroundNMEARead.addActionListener(new ActionListener()  { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(READ_REBROADCAST); } } );
//  backGroundNMEAServer.addActionListener(new ActionListener()  { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(RMI_NMEA_SERVER); } } );
    
    menuFileExit.setText( "Exit" );
    menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); } } );
    menuHelp.setText( "Help" );
    menuHelpAbout.setText( "About" );
    menuHelpAbout.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpAbout_ActionPerformed( ae ); } } );
    statusBar.setText( " Ready" );
    // Rebroadcast option
    statusBar.addMouseListener(new MouseListener()
      {
        public void mouseClicked(MouseEvent e)
        {
          // Right click: popup -> Reset
          int mask = e.getModifiers();
          // Right-click only (Actually: no left-click)
          if ((mask & MouseEvent.BUTTON2_MASK) != 0 || (mask & MouseEvent.BUTTON3_MASK) != 0)
          {
            String statusText = statusBar.getText();
            if (!statusText.equals("Ready"))
            {
              RebroadcastPopup popup = new RebroadcastPopup();
              popup.show(statusBar, e.getX(), e.getY());
            }
          }
        }

        public void mousePressed(MouseEvent e)
        {
        }

        public void mouseReleased(MouseEvent e)
        {
        }

        public void mouseEntered(MouseEvent e)
        {
        }

        public void mouseExited(MouseEvent e)
        {
        }
      });
    
    int nbMenuItem = 0;
    boolean separatorIsLast = false;
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_CHART_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      nbMenuItem++;
      try
      {
        String toLoad = "chartlib.ui.components.ChartLibInternalFrame"; // Not enough
     /* Class c = */ Class.forName(toLoad);
        menuFile.add(chartLibMenuItem);
        menuFile.add(new JSeparator());
        separatorIsLast = true;
      }
      catch (Exception ex)
      {
        System.out.println("ChartLib skipped");
      }
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_SAILFAX_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(sailFaxMenuItem);
      nbMenuItem++;
      menuFile.add(new JSeparator());
      separatorIsLast = true;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_NMEA_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      if (nbMenuItem > 0)
        menuFile.add(nmeaMenuItem);
      nmeaMenuItem.add(nmeaConsoleMenuItem);
      nmeaMenuItem.add(backGroundNMEARead);
//    nmeaMenuItem.add(backGroundNMEAServer);
      nmeaMenuItem.add(replayNmeaMenuItem);
      menuFile.add(new JSeparator());
      separatorIsLast = true;
      nbMenuItem++;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_STAR_FINDER_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(starFinderMenuItem);
      nbMenuItem++;
      menuFile.add(d2102StarFinderMenuItem);
      nbMenuItem++;
      separatorIsLast = false;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_LUNAR_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(lunarMenuItem);
      nbMenuItem++;
      separatorIsLast = false;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_SIGHT_RED_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(srMenuItem);
      nbMenuItem++;
      separatorIsLast = false;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_PUBLISHER_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(almanacMenuItem);
      nbMenuItem++;
      menuFile.add(realTimeAlmanacMenuItem);
      nbMenuItem++;
      separatorIsLast = false;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_GOOGLE_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      if (nbMenuItem > 0 && !separatorIsLast)        
        menuFile.add(new JSeparator());
      menuFile.add(locatorMenuItem);
      nbMenuItem++;
    }
    if (nbMenuItem > 0 && !separatorIsLast)
    {
      menuFile.add(new JSeparator());
      separatorIsLast = true;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_TIDES_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(tideMenuItem);
      nbMenuItem++;
      separatorIsLast = false;
    }
    if (nbMenuItem > 0 && !separatorIsLast)
      menuFile.add(new JSeparator());
    
    menuFile.add(menuFileExit);
    menuBar.add( menuFile );
    
    menuTools.setText("Tools");
    menuToolsPreferences.setText("Preferences");
    menuBar.add(menuTools);
    menuTools.add(menuToolsPreferences);
    menuTools.add(new JSeparator());
    menuToolsPreferences.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { showPreferencesDialog(); } } );
    menuToolsFullScreen.setText("Full Screen");
    menuTools.add(menuToolsFullScreen);
    menuToolsFullScreen.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fullScreen(); } } );
    // Temp
    menuToolsFullScreen.setEnabled(false);

    if (((Boolean)(ParamPanel.getData()[ParamData.USE_NMEA_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuToolsDisplayTime.setText("Display Time Window");
      menuTools.add(menuToolsDisplayTime);
      menuToolsDisplayTime.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(TIME_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayTime.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        TimeThread tt = new TimeThread();
                                                        tt.start();
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayGPSSignal.setText("Display GPS Signal");
      menuTools.add(menuToolsDisplayGPSSignal);
      menuToolsDisplayGPSSignal.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(GPS_SIGNAL_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayGPSSignal.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread(); // TASK Check if necessary
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayGPSTime.setText("Display GPS Time");
      menuTools.add(menuToolsDisplayGPSTime);
      menuToolsDisplayGPSTime.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(GPS_TIME_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayGPSTime.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread(); 
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayGPSPos.setText("Display GPS Position");
      menuTools.add(menuToolsDisplayGPSPos);
      menuToolsDisplayGPSPos.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(POSITION_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayGPSPos.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread();
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayGPSSpeedCourse.setText("Display GPS COG & SOG");
      menuTools.add(menuToolsDisplayGPSSpeedCourse);
      menuToolsDisplayGPSSpeedCourse.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(SOG_COG_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayGPSSpeedCourse.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread();
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayNMEA.setText("Display NMEA Data");
      menuTools.add(menuToolsDisplayNMEA);
      menuToolsDisplayNMEA.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(NMEA_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayNMEA.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread();
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayAW.setText("Display Apparent Wind");
      menuTools.add(menuToolsDisplayAW);
      menuToolsDisplayAW.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(AW_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayAW.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread();
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
      menuToolsDisplayRTA.setText("Display Real time Almanac");
      menuTools.add(menuToolsDisplayRTA);
      menuToolsDisplayRTA.addActionListener( new ActionListener() 
                                              { 
                                                public void actionPerformed( ActionEvent ae ) 
                                                { 
                                                  for (BackgroundWindow bgw : bgwal)
                                                  {
                                                    if (bgw.getWinTitle().equals(REALTIME_ALMANAC_BG_WINDOW_TITLE))
                                                    {
                                                      bgw.setDisplayBGWindow(!bgw.isDisplayBGWindow()); 
                                                      menuToolsDisplayRTA.setEnabled(!bgw.isDisplayBGWindow());
                                                      if (bgw.isDisplayBGWindow())
                                                      {
                                                        if (nmeaDataThread == null || !nmeaDataThread.isAlive())
                                                        {
                                                          String streamType = (ParamPanel.getData()[ParamData.NMEA_DATA_STREAM][ParamPanel.PRM_VALUE]).toString();
                                                          if (streamType.equals("NMEA Port"))
                                                            nmeaDataThread = new NMEACacheReaderThread();
                                                          else
                                                            nmeaDataThread = new HTTPNMEAReaderThread();
                                                          nmeaDataThread.start();
                                                        }
                                                      }
                                                      break;
                                                    }
                                                  }
                                                } 
                                              });
    }
    menuHelp.add( menuHelpAbout );
    menuBar.add( menuHelp );
    bottomPanel.add(statusBar, BorderLayout.WEST);
    bottomPanel.add(progressBar, BorderLayout.EAST);
    progressBar.setVisible(false);
    this.getContentPane().add( bottomPanel, BorderLayout.SOUTH );

    this.getContentPane().add( desktop, BorderLayout.CENTER );
    
    DesktopContext.getInstance().addApplicationListener(new DesktopEventListener()
      {
        public void backgroundImageChanged() 
        {
          setBackgroundImage();
        }
        
        public void bgWinColorChanged()
        {
          setBGWinColor();
        }
        
        public void startReadingNMEAPort() 
        {
          System.out.println("NMEA port reading requested...");
          backGroundNMEARead.setSelected(true);
          appRequest_ActionPerformed(READ_REBROADCAST); // Forwarding event
        }      
        
        public void startNMEACache() 
        {
          if (!DesktopContext.getInstance().isReadingNMEA())
          {
            System.out.println("Starting NMEA port reading.");
            setStatus("Starting NMEA port reading.");
            DesktopContext.getInstance().fireStartReadingNMEAPort();
          }
          System.out.println("Starting NMEA Cache");
          minimizeNMEAConsole = true;
          appRequest_ActionPerformed(NMEA_CONSOLE);
          minimizeNMEAConsole = false;
        }

      });    
    SFContext.getInstance().addApplicationListener(new StarFinderEventListener()
      {
       public void internalFrameClosed() 
       {
         starFinder.setVisible(false);
         starFinderMenuItem.setEnabled(true);
       }
     });
    SkyMapContext.getInstance().addApplicationListener(new SkyMapEventListener()
       {
         public void internalFrameClosed() 
         {
           skyMap.setVisible(false);
           d2102StarFinderMenuItem.setEnabled(true);
         }
       });
    ChartLibContext.getInstance().addChartLibListener(new ChartLibListener()
      {
        public void internalFrameClosed() 
        {
          try { SQLUtil.shutdown(chartConnection); } catch (Exception ex) { ex.printStackTrace(); }
          chartLib.setVisible(false);
          chartLibMenuItem.setEnabled(true);
        }
      });
    NMEAContext.getInstance().addNMEAListener(new NMEAListener()
      {
        public void internalFrameClosed() 
        {
          DesktopUtilities.saveInternalFrameConfig("nmeaconsole.xml", "nmea-console-pos", nmeaDataFrame);
//        try { SQLUtil.shutdown(logConnection); } catch (Exception ex) { ex.printStackTrace(); }
          nmeaDataFrame.setVisible(false);
          nmeaConsoleMenuItem.setEnabled(true);
          replayNmeaMenuItem.setEnabled(true);
          NMEAContext.getInstance().removeNMEAListenerGroup(Constants.NMEA_SERVER_LISTENER_GROUP_ID);
        }
      });
    SailFaxContext.getInstance().addSailFaxListener(new SailfaxEventListener()
      {
        public void internalFrameClosed() 
        {
          sailfaxFrame.setVisible(false);
          sailFaxMenuItem.setEnabled(true);
        }
      });
    SRContext.getInstance().addSRListener(new SREventListener()
      {
        public void internalFrameClosed() 
        {
          sightReduction.setVisible(false);
          srMenuItem.setEnabled(true);
        }
      });
    RTAContext.getInstance().addRTAListener(new RTAEventListener()
      {
        public void internalFrameClosed() 
        {
          realTimeAlmanac.setVisible(false);
          realTimeAlmanacMenuItem.setEnabled(true);
        }
      });
    LDContext.getInstance().addLDListener(new LDEventListener()
      {
        public void internalFrameClosed() 
        {
          clearLunar.setVisible(false);
          lunarMenuItem.setEnabled(true);
        }
      });
    APContext.getInstance().addAPListener(new APEventListener()
      {
        public void internalFrameClosed() 
        {
          almanac.setVisible(false);
          almanacMenuItem.setEnabled(true);
        }
      });
    try
    {
      LocatorContext.getInstance().addLocatorListener(new LocatorEventListener()
        {
          public void internalFrameClosed() 
          {
            locator.setVisible(false);
            locatorMenuItem.setEnabled(true);
          }
        });
    }
    catch (NoClassDefFoundError ncdfe)
    {
      System.out.println("Not loading the Google Locator");
    }
    TideContext.getInstance().addTideListener(new TideEventListener()
      {
        public void internalFrameClosed() 
        {
          tides.setVisible(false);
          tideMenuItem.setEnabled(true);
        }

        public void beginLoad() 
        {
          Thread load = new Thread()
            {
              public void run()
              {
                progressBar.setVisible(true);
                progressBar.setIndeterminate(true);
                progressBar.setString("Loading...");
                progressBar.setStringPainted(true);
                progressBar.repaint();
              }
            };
          load.start();
        }
        
        public void stopLoad() 
        {
          progressBar.setVisible(false);
        }        
        
      });
    
//  fullScreen();
  }

  private void setStatus(String st)
  {
    this.statusBar.setText(st);
  }
  
  private BackgroundWindow getBGWinByTitle(String s)
  {
    BackgroundWindow bgwin = null;
    for (BackgroundWindow bgw : bgwal)
    {
      if (bgw.getWinTitle().equals(s))
      {
        bgwin = bgw;
        break;
      }
    }
    return bgwin;
  }
  
  private void fullScreen()
  {
    if (fullscreen)
    {
      fullscreen = false;
      try
      {
        this.setVisible(false);
        this.setUndecorated(false);
        this.setVisible(true);
      }
      catch (Exception ex)
      {
        this.setVisible(true);
      }
      menuToolsFullScreen.setText("Full Screen");
    }
    else
    {
      int state = this.getExtendedState();
      state |= Frame.MAXIMIZED_BOTH;
      this.setExtendedState(state);      
      fullscreen = true;
      try
      {
        this.setVisible(false);
        this.setUndecorated(true);
        this.setVisible(true);
        menuToolsFullScreen.setText("Normal Screen");
      }
      catch (Exception ex)
      {
        this.setVisible(true);
      }
    }
  }
  
  private void setBGWinColor()
  {
    Color c = ((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.BG_WIN_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor();
    for (BackgroundWindow bgw : bgwal)
    {
//    if (!bgw.getWinTitle().equals(GPS_SIGNAL_BG_WINDOW_TITLE))
      {
        bgw.setDataFontColor(c);
      }
    }
  }
  
  private void setBackgroundImage()
  {
    final String imageFileName = ((ParamPanel.DataFile) ParamPanel.getData()[ParamData.BACKGROUND_IMAGE][ParamPanel.PRM_VALUE]).toString();
    URL imgURL = null;
    try
    {
      if (imageFileName != null || imageFileName.trim().length() > 0 || (new File(imageFileName)).exists())
        imgURL = new File(imageFileName).toURI().toURL();
  //  else
  //    imgURL = this.getClass().getResource("OlivPacCup.jpg");
    }
    catch (Exception ex)
    {
      imgURL = null; // this.getClass().getResource("OlivPacCup.jpg");
    }
//  System.out.println("Setting BG Image to " + imgURL.toString());
    if (imgURL != null)
    {
      bgImage = new ImageIcon(imgURL);
  //  bgImage = new ImageIcon(this.getClass().getResource("DrakesBay.jpg"));
  //  bgImage = new ImageIcon(this.getClass().getResource("donpedrologo2.png"));
      if (bgLabel == null)
        bgLabel = new JLabel(bgImage);
      else
        bgLabel.setIcon(bgImage);
      centerBG();
      bgLabel.repaint();
    }
  }
  
  public boolean isNMEAConsoleRunning() { return !nmeaConsoleMenuItem.isEnabled(); }
  public boolean isStarFinderRunning() { return !starFinderMenuItem.isEnabled(); }
  public boolean isLocatorRunning() { return !locatorMenuItem.isEnabled(); }
  public boolean isChartLibRunning() { return !chartLibMenuItem.isEnabled(); }
  public boolean isSailFaxRunning() { return !sailFaxMenuItem.isEnabled(); }
  public boolean isSRRunning() { return !srMenuItem.isEnabled(); }
  public boolean isAPRunning() { return !almanacMenuItem.isEnabled(); }
  public boolean isTideRunning() { return !tideMenuItem.isEnabled(); }

  private transient NMEAListener nmeaListener = null;
  
  private void appRequest_ActionPerformed(int app)
  {
    final String channel = (ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE]).toString();

    final Dimension masterDim = this.getSize();
    System.setProperty("play.sounds", (ParamPanel.getData()[ParamData.PLAY_SOUNDS][ParamPanel.PRM_VALUE]).toString());
    switch (app)
    {
      case SAILFAX:
        System.setProperty("airmail.location", (ParamPanel.getData()[ParamData.AIRMAIL_LOCATION][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("transmission.catalog", (ParamPanel.getData()[ParamData.SAILFAX_CATALOG][ParamPanel.PRM_VALUE]).toString());
        String bulletinData = System.getProperty("transmission.catalog", "fax-data");
        try
        {
          sailfaxFrame = new TransmissionSelectorInternalFrame(bulletinData); 
          sailfaxFrame.setIconifiable(true);
          sailfaxFrame.setClosable(true);
          sailfaxFrame.setMaximizable(true);
          sailfaxFrame.setResizable(true);
          desktop.add(sailfaxFrame);
          sailfaxFrame.setVisible(true);
          sailFaxMenuItem.setEnabled(false);
          centerFrame(masterDim, sailfaxFrame);
        }
        catch (NoClassDefFoundError ncdfe)
        {
          System.out.println("No TransmissionSelectorInternalFrame");
        }
        break;
      case CHARTLIB:
        System.setProperty("chart.db.location", (ParamPanel.getData()[ParamData.DB_LOCATION][ParamPanel.PRM_VALUE]).toString());
        String dbLocation = System.getProperty("chart.db.location", ".." + File.separator + "all-db");
        try
        {
          try { chartConnection = SQLUtil.getConnection(dbLocation, "CHART", "chart", "chart"); } catch (Exception ex) { ex.printStackTrace(); }        
          String toLoad = "chartlib.ui.components.ChartLibInternalFrame";
          Class c = Class.forName(toLoad);
    //    @SuppressWarnings("unchecked")
          Constructor constr = c.getConstructor(new Class<?>[] { Connection.class });
          Object obj = constr.newInstance(chartConnection);
          chartLib = (JInternalFrame) obj;
//        chartLib = new ChartLibInternalFrame(conn);
          chartLib.setIconifiable(true);
          chartLib.setClosable(true);
          chartLib.setMaximizable(true);
          chartLib.setResizable(true);
          desktop.add(chartLib);
          chartLib.setVisible(true);
          chartLibMenuItem.setEnabled(false);
          centerFrame(masterDim, chartLib);
        }
        catch (ClassNotFoundException ncdfe)
        {
          System.out.println("No ChartLibInternalFrame");
        }
        catch (Exception e)
        {
          e.printStackTrace();          
        }
        break;
      case NMEA_CONSOLE:
        System.setProperty("db.location", (ParamPanel.getData()[ParamData.DB_LOCATION][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.config.file", (ParamPanel.getData()[ParamData.NMEA_CONFIG_FILE][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.serial.port", (ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.tcp.port", (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.udp.port", (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.host.name", (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("nmea.baud.rate", (ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE]).toString());
//      System.setProperty("nmea.simulation.data.file", (ParamPanel.getData()[ParamData.NMEA_SIMULATION][ParamPanel.PRM_VALUE]).toString());

        String nmeaProperties = System.getProperty("nmea.config.file", "nmea-config.properties");
        String serialPort     = System.getProperty("nmea.serial.port", null);
        int br                = Integer.parseInt(System.getProperty("nmea.baud.rate", "0"));
        String tcp            = System.getProperty("nmea.tcp.port", null);
        String udp            = System.getProperty("nmea.udp.port", null);
        String simulation     = System.getProperty("nmea.simulation.data.file", null);
        String nmeaHostName   = System.getProperty("nmea.host.name", "localhost");
        
        if ("TCP".equals(channel))
        {
          serialPort = null;
          udp = null;
        }
        else if ("UDP".equals(channel))
        {
          tcp = null;
          serialPort = null;
        }
        else // Serial
        {
          udp = null;
          tcp = null;
        }
        
        nmeaDataFrame = new NMEAInternalFrame(false,
                                              serialPort,
                                              br,
                                              tcp,
                                              udp,
                                              nmeaHostName,
                                              simulation,
                                              nmeaProperties); 
        nmeaDataFrame.setIconifiable(true);
        nmeaDataFrame.setClosable(true);
        nmeaDataFrame.setMaximizable(true);
        nmeaDataFrame.setResizable(true);
        desktop.add(nmeaDataFrame);
        nmeaDataFrame.setVisible(true);
        if (minimizeNMEAConsole) // Must be done AFTER the setVisible
        {
//        System.out.println("Minimizing Window here");
//        nmeaDataFrame.setState(Frame.ICONIFIED);
          try { nmeaDataFrame.setIcon(true); } catch (Exception ignore) { ignore.printStackTrace(); }
        }

        nmeaConsoleMenuItem.setEnabled(false);
        replayNmeaMenuItem.setEnabled(false);
        centerFrame(masterDim, nmeaDataFrame);
        break;
      case REPLAY_NMEA:
        if (backGroundNMEARead.isSelected()) // Already reading NMEA port
        {
          String message = "NMEA Port is being read.\n" +
                           "Please stop reading it before replaying NMEA Data.";
          JOptionPane.showMessageDialog(this, message, "NMEA Replay", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
          String nmeaProps = System.getProperty("nmea.config.file", "nmea-config.properties");
          String simul = coreutilities.Utilities.chooseFile(JFileChooser.FILES_ONLY,
                                                            "nmea",
                                                            "NMEA Data",
                                                            "Load NMEA Data",
                                                            "Load");
          if (simul.trim().length() > 0)
          {
            nmeaListener = new NMEAListener(LISTENER_FAMILY)
            {
              public void manageNMEAString(String str)
              {
                if (str == null)
                  return;
                
                String prefix = "File:";
                String message = str;
                if (TCPPort != -1 && str != null)
                {
                  if (rebroadcastVerbose)
                    System.out.println("Rebroadcasting on TCP Port " + TCPPort + ":" + str);
                  if (tcpWriter != null)
                  {
                    tcpWriter.write((str + "\n").getBytes());
                    prefix += (" => TCP " + TCPPort);
                  }
                }
                if (UDPPort != -1 && str != null)
                {
                  if (rebroadcastVerbose)
                    System.out.println("Rebroadcasting on UDP Port " + UDPPort + ":" + str);
                  udpWriter.write(str.getBytes());
                  prefix += (" => UDP " + UDPPort);
                }
                if (GPSDPort != -1 && str != null)
                {
                  if (rebroadcastVerbose)
                    System.out.println("Rebroadcasting on GPSD Port " + GPSDPort + ":" + str);
                  gpsdWriter.write(str.getBytes());
                  prefix += (" => GPSD " + GPSDPort);
                }
                if (HTTPPort != -1 && str != null)
                {
                  if (rebroadcastVerbose)
                    System.out.println("Rebroadcasting on HTTP Port " + HTTPPort + ":" + str);
                  prefix += (" => XML/HTTP " + HTTPPort);
                }
                if (RMIPort != -1 && str != null)
                {
                  if (rebroadcastVerbose)
                    System.out.println("Rebroadcasting on RMI Port " + RMIPort + ":" + str);
                  prefix += (" => RMI " + RMIPort);
                }
                // Display NMEA String in the status bar
                setStatus(prefix + " " + message);
              }
            };
            NMEAContext.getInstance().addNMEAListener(nmeaListener);       

            serialPort = null;
            tcp = null;
            udp = null;
            br = 0;
            nmeaDataFrame = new NMEAInternalFrame(false,
                                                  serialPort,
                                                  br,
                                                  tcp,
                                                  udp,
                                                  (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString(),
                                                  simul,
                                                  nmeaProps); 
            nmeaDataFrame.setIconifiable(true);
            nmeaDataFrame.setClosable(true);
            nmeaDataFrame.setMaximizable(true);
            nmeaDataFrame.setResizable(true);
            desktop.add(nmeaDataFrame);
            nmeaDataFrame.setVisible(true);
            nmeaConsoleMenuItem.setEnabled(false);
            replayNmeaMenuItem.setEnabled(false);
            centerFrame(masterDim, nmeaDataFrame);
          }
        }
        break;
      case STARFINDER:
        System.setProperty("http.host", (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("http.port", (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());

        starFinder = new SkyInternalFrame();
        starFinder.setIconifiable(true);
        starFinder.setClosable(true);
        starFinder.setMaximizable(true);
        starFinder.setResizable(true);
        desktop.add(starFinder);
        starFinder.setVisible(true);
        starFinderMenuItem.setEnabled(false);
        centerFrame(masterDim, starFinder);
        break;
      case STARFINDER_2012D:
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        skyMap = new DualInternalFrame();
        skyMap.setIconifiable(true);
        skyMap.setClosable(true);
        skyMap.setMaximizable(true);
        skyMap.setResizable(true);
        desktop.add(skyMap);
        skyMap.setVisible(true);
        d2102StarFinderMenuItem.setEnabled(false);
        centerFrame(masterDim, skyMap);
        break;
      case LUNAR:
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        clearLunar = new ClearLunarInternalFrame();
        clearLunar.setIconifiable(true);
        clearLunar.setClosable(true);
        clearLunar.setMaximizable(true);
        clearLunar.setResizable(true);
        desktop.add(clearLunar);
        clearLunar.setVisible(true);
        lunarMenuItem.setEnabled(false);
        centerFrame(masterDim, clearLunar);
        break;
      case SR:
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        sightReduction = new SightReductionInternalFrame();
        sightReduction.setIconifiable(true);
        sightReduction.setClosable(true);
        sightReduction.setMaximizable(true);
        sightReduction.setResizable(true);
        desktop.add(sightReduction);
        sightReduction.setVisible(true);
        srMenuItem.setEnabled(false);
        centerFrame(masterDim, sightReduction);
        break;
      case REAL_TIME_ALMANAC:
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        realTimeAlmanac = new RealTimeAlmanacInternalFrame();
        realTimeAlmanac.setIconifiable(true);
        realTimeAlmanac.setClosable(true);
        realTimeAlmanac.setMaximizable(true);
        realTimeAlmanac.setResizable(true);
        desktop.add(realTimeAlmanac);
        realTimeAlmanac.setVisible(true);
        realTimeAlmanacMenuItem.setEnabled(false);
        centerFrame(masterDim, realTimeAlmanac);
        break;
      case ALMANAC:
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        boolean rtRunning = !realTimeAlmanacMenuItem.isEnabled();
        boolean rtBGWinRunning = !menuToolsDisplayRTA.isEnabled();
        if (rtRunning || rtBGWinRunning) // Real Time Almanac or RT BG Window
        {
          String message = "Please close the Real Time Almanac applications (App or Background Window)\n" +
                           "before running the Almanac Publisher.\n" +
                           "Running them together would result in erroneous data.";
          JOptionPane.showMessageDialog(this, message, "Almanac Publisher", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
          almanac = new AlmanacPublisherInternalFrame();
          almanac.setIconifiable(true);
          almanac.setClosable(true);
          almanac.setMaximizable(true);
          almanac.setResizable(true);
          desktop.add(almanac);
          almanac.setVisible(true);
          almanacMenuItem.setEnabled(false);
          centerFrame(masterDim, almanac);
        }
        break;
      case LOCATOR:
        System.setProperty("boat.id",   (ParamPanel.getData()[ParamData.BOAT_ID][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("http.host", (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("http.port", (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString());

        locator = new GenInternalFrame();
        locator.setIconifiable(true);
        locator.setClosable(true);
        locator.setMaximizable(true);
        locator.setResizable(true);
        desktop.add(locator);
        locator.setVisible(true);
        locatorMenuItem.setEnabled(false);
        centerFrame(masterDim, locator);
        break;
      case READ_REBROADCAST:
        if (backGroundNMEARead.isSelected())
        {          
          nmeaListener = new NMEAListener(LISTENER_FAMILY)
          {
            public void manageNMEAString(String str)
            {
//            String channel = (ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE]).toString();
              String prefix = channel + " ";
              if (channel.equals("Serial"))
                prefix += (ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString() + 
                        ", " + (ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE]).toString();
              else if (channel.equals("TCP"))
                prefix += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString());
              else if (channel.equals("UDP"))
                prefix += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString());
              else if (channel.equals("RMI"))
                prefix += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString());
              
              // Re-broadcast ?
              if (TCPPort != -1)
              {
                if (rebroadcastVerbose)
                  System.out.println("Rebroadcasting on TCP Port " + TCPPort + ":" + str);
                if (tcpWriter != null)
                {
                  tcpWriter.write((str + "\n").getBytes());
                  prefix += (" => TCP " + TCPPort);
                }
              }
              if (UDPPort != -1)
              {
                if (rebroadcastVerbose)
                  System.out.println("Rebroadcasting on UDP Port " + UDPPort + ":" + str);
                udpWriter.write(str.getBytes());
                prefix += (" => UDP " + UDPPort);
              }
              if (GPSDPort != -1)
              {
                if (rebroadcastVerbose)
                  System.out.println("Rebroadcasting on GPSd Port " + GPSDPort + ":" + str);
                if (gpsdWriter != null)
                {
                  gpsdWriter.write((str + "\n").getBytes());
                  prefix += (" => GPSd " + GPSDPort);
                }
              }
              if (HTTPPort != -1)
              {
                if (rebroadcastVerbose)
                  System.out.println("Rebroadcasting on HTTP Port " + HTTPPort + ":" + str);
                prefix += (" => XML/HTTP " + HTTPPort);
              }
              if (RMIPort != -1)
              {
                if (rebroadcastVerbose)
                  System.out.println("Rebroadcasting on RMI Port " + RMIPort + ":" + str);
                prefix += (" => RMI " + RMIPort);
              }
              String message = prefix + ":" + str;
              // Display NMEA String in the status bar
              setStatus(message);
            }
          };
          NMEAContext.getInstance().addNMEAListener(nmeaListener);       
          String comPort = (ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString();
          String tcpPort = (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString();
          String udpPort = (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString();
          String rmiPort = (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString();
// TODO   String gpsdPort = (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString();
          String brate   = (ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE]).toString();
          int option = -1;
          String port = "";
          if (channel.equals("UDP"))
          {
            option = CustomNMEAClient.UDP_OPTION;
            port = udpPort;
            comPort = "";
            brate = "0";
          }
          else if (channel.equals("TCP"))
          {
            option = CustomNMEAClient.TCP_OPTION;
            port = tcpPort;
            comPort = "";
            brate = "0";
          }
          else if (channel.equals("RMI"))
          {
            option = CustomNMEAClient.RMI_OPTION;
            port = rmiPort;
            comPort = "";
            brate = "0";
          }
          else
          {
            // ?... Serial
          }
          
          nmeaReader = new DesktopNMEAReader("true".equals(System.getProperty("verbose", "false")),  // Verbose
                                             comPort,                                                // Serial Port
                                             Integer.parseInt(brate),                                // Baud Rate
                                             port,                                                   // TCP/UDP
                                             option,
                                             (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString(),
                                             "",                                                     // Data file (simul).
                                             "");                                                    // pFile

          String mess = channel + " ";
          if (channel.equals("Serial"))
            mess += (ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString() + 
                    ":" + (ParamPanel.getData()[ParamData.NMEA_BAUD_RATE][ParamPanel.PRM_VALUE]).toString();
          else if (channel.equals("TCP"))
            mess += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString());
          else if (channel.equals("UDP"))
            mess += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString());
          else if (channel.equals("RMI"))
            mess += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString());

          this.setStatus(" Reading " + mess + " ...");
          DesktopContext.getInstance().setReadingNMEA(true);
        }
        else // Unchecked.
        {
          try { nmeaReader.stopReader(); }
          catch (Exception ex)
          {
            System.err.println("Stopping:");
            ex.printStackTrace();
          }
          this.setStatus(" Ready");
//        NMEAContext.getInstance().removeNMEAListener(nmeaListener);
          NMEAContext.getInstance().removeNMEAListenerGroup(LISTENER_FAMILY);
          DesktopContext.getInstance().setReadingNMEA(false);
        }
        break;
//      case RMI_NMEA_SERVER: 
//        if (backGroundNMEAServer.isSelected())
//        {
//          // Start
//          DesktopContext.getInstance().getNMEAServerManager().startServer();
//        }
//        else
//        {
//          // Stop
//          DesktopContext.getInstance().getNMEAServerManager().stopServer();
//        }
//        break;
      case TIDES:
        System.setProperty("db.location", (ParamPanel.getData()[ParamData.DB_LOCATION][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("tide.flavor", (ParamPanel.getData()[ParamData.TIDE_FLAVOR][ParamPanel.PRM_VALUE]).toString().toLowerCase());
        // Set display.sun.moon.data
        System.setProperty("display.sun.moon.data", ((Boolean)(ParamPanel.getData()[ParamData.COMPUTE_SUN_MOON_DATA][ParamPanel.PRM_VALUE])).toString());
        System.setProperty("max.recent.stations", ((Integer)(ParamPanel.getData()[ParamData.MAX_TIDE_RECENT_STATIONS][ParamPanel.PRM_VALUE])).toString());
        System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        tideengineimplementation.utils.AstroComputer.setDeltaT(((Double)(ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE])).doubleValue());
//      System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        Thread tideThread = new Thread()
          {
            public void run()
            {
              boolean prevPosFound = false;
//            if (tides == null)
              {
                tides = new TideInternalFrame(instance);
                tides.setIconifiable(true);
                tides.setClosable(true);
                tides.setMaximizable(true);
                tides.setResizable(true);
                // Previous location ?                
                try
                {
                  Properties ifProps = new Properties();
                  FileInputStream fip = new FileInputStream(new File(TideInternalFrame.TIDE_INTERNAL_FRAME_PROP_FILE));
                  ifProps.load(fip);
                  tides.setLocation(Integer.parseInt(ifProps.getProperty(TideInternalFrame.TOP_LEFT_X_PROP)), 
                                    Integer.parseInt(ifProps.getProperty(TideInternalFrame.TOP_LEFT_Y_PROP)));
                  Dimension dim = new Dimension(Integer.parseInt(ifProps.getProperty(TideInternalFrame.WIDTH_PROP)), 
                                                Integer.parseInt(ifProps.getProperty(TideInternalFrame.HEIGHT_PROP)));
                  tides.setSize(dim);
                  tides.setPreferredSize(dim);
                  fip.close();
                  prevPosFound = true;
                }
                catch (IOException ioe)
                {
                  System.err.println(ioe.getLocalizedMessage());                
                }
                catch (NumberFormatException nfe)
                {
                  System.err.println(nfe.getLocalizedMessage());
                }
                desktop.add(tides);
              }
              tides.setVisible(true);
              tideMenuItem.setEnabled(false);
              if (!prevPosFound)
                centerFrame(masterDim, tides);
            }
          };
        tideThread.start();  
        break;
      default:
        break;
    }
  }

  void fileExit_ActionPerformed(ActionEvent e)
  {
    DesktopUtilities.doOnExit(this.getClass().getResource("vista.wav"));
  }

  void helpAbout_ActionPerformed(ActionEvent e)
  {
    JOptionPane.showMessageDialog(this, new DesktopFrame_AboutBoxPanel(), "About", JOptionPane.PLAIN_MESSAGE);
  }

  private void this_windowClosing(WindowEvent e)
  {
    URL sound = this.getClass().getResource("vista.wav");
    DesktopUtilities.doOnExit(sound);
  }
  
  private void centerFrame(Dimension masterDim, JInternalFrame fr)
  {
    Dimension dim = fr.getSize();
    int x = (masterDim.width / 2) - (dim.width / 2);
    int y = (masterDim.height / 2) - (dim.height / 2);
    if (y < 0) y = 0;
    
    if (fr instanceof NMEAInternalFrame)
    {
      File consolePos = new File("nmeaconsole.xml");
      boolean dataOk = true;
      if (consolePos.exists())
      {
        DOMParser parser = DesktopContext.getInstance().getParser();
        synchronized (parser)
        {
          try { parser.parse(consolePos.toURI().toURL()); }
          catch (Exception ex)
          { 
            ex.printStackTrace(); 
            dataOk = false;
          }        
        }
        if (dataOk)
        {
          try
          {
            XMLDocument doc = parser.getDocument();
            XMLElement root = (XMLElement)doc.selectNodes("/nmea-console-pos").item(0);
            x = Integer.parseInt(root.getAttribute("x"));
            y = Integer.parseInt(root.getAttribute("y"));
            int w = Integer.parseInt(root.getAttribute("w"));
            int h = Integer.parseInt(root.getAttribute("h"));
  //        System.out.println("Restoring in " + x + ", " + y);
            Dimension consoleDim = new Dimension(w, h);
            fr.setSize(consoleDim);
            fr.setLocation(x, y);
          }
          catch (Exception ex)
          {
            dataOk = false;
            ex.printStackTrace();
          }
        }
      }
    }
    else
      fr.setLocation(x, y);    
  }
  
  private void centerBG()
  {
    int bgW = bgImage.getIconWidth();
    int bgH = bgImage.getIconHeight();
    Dimension mfSize = desktop.getSize();
    int x = (mfSize.width / 2) - (bgW / 2);
    int y = (mfSize.height / 2) - (bgH / 2);
    if (x < 0) x = 0;
    if (y < 0) y = 0;
    bgLabel.setBounds(x, y, bgImage.getIconWidth(), bgImage.getIconHeight());    
  }

  private void showPreferencesDialog()
  {
    CategoryPanel cp = new CategoryPanel();
    int opt = 
      JOptionPane.showConfirmDialog(this, 
                                    cp, 
                                    "Application Parameters", 
                                    JOptionPane.OK_CANCEL_OPTION, 
                                    JOptionPane.DEFAULT_OPTION);
    if (opt == JOptionPane.OK_OPTION)
      cp.finalPrmUpdate();
  }
  
  private class TimeThread extends Thread
  {
    public void run()
    {
//    System.out.println("Starting timer");
      while (getBGWinByTitle(TIME_BG_WINDOW_TITLE).isDisplayBGWindow())
      {
        Date ut = TimeUtil.getGMT();
        timeString = "System:\n" + SDF.format(ut);
        int offset = TimeUtil.getGMTOffset();
        String strOffset = Integer.toString(offset);
        if (offset > 0) strOffset = "+" + strOffset;
        timeString += "\nUTC Offset:" + strOffset;
        desktop.repaint();
        try { Thread.sleep(1000L); } catch (Exception ignore) {}
      }
//    System.out.println("End of timer");
    }
  }

  private class NMEACacheReaderThread extends Thread
  {
    public void run()
    {
      System.out.println("Starting NMEA Cache Thread");
      while (getBGWinByTitle(GPS_SIGNAL_BG_WINDOW_TITLE).isDisplayBGWindow() ||
             getBGWinByTitle(GPS_TIME_BG_WINDOW_TITLE).isDisplayBGWindow()   ||
             getBGWinByTitle(POSITION_BG_WINDOW_TITLE).isDisplayBGWindow()   ||
             getBGWinByTitle(SOG_COG_BG_WINDOW_TITLE).isDisplayBGWindow()    ||
             getBGWinByTitle(NMEA_BG_WINDOW_TITLE).isDisplayBGWindow()       || 
             getBGWinByTitle(AW_BG_WINDOW_TITLE).isDisplayBGWindow()         ||
             getBGWinByTitle(REALTIME_ALMANAC_BG_WINDOW_TITLE).isDisplayBGWindow())
      {
        try
        {
          double g = 0d;
          
          posString = "";
          try 
          { 
//          posString += ("L:" + GeomUtil.decToSex(NMEACache.getInstance().getLat(), GeomUtil.SWING, GeomUtil.NS)); 
            posString += ("L:" + GeomUtil.decToSex(((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lat, GeomUtil.SWING, GeomUtil.NS)); 
          } 
          catch (Exception ex) 
          { 
            System.err.println(ex.getLocalizedMessage());
            posString += "-"; 
          }
          posString += "\n";
          try 
          { 
//          g = NMEACache.getInstance().getLng();
            g = ((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true)).lng; 
            posString += ("G:" + GeomUtil.decToSex(g, GeomUtil.SWING, GeomUtil.EW)); 
          } 
          catch (Exception ex) 
          { 
            System.out.println(ex.getLocalizedMessage());
            posString += "-"; 
          }
          hsString = "COG:";
          try 
          { 
//          hsString += Integer.toString(((int)NMEACache.getInstance().getCog())); 
            hsString += Integer.toString((int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.COG, true)).getValue()); 
          } 
          catch (Exception ex) 
          { 
            System.err.println("COG in Cache:" + ex.getLocalizedMessage()); 
            hsString += "-"; 
          }
          hsString += " \272\nSOG:";
          try 
          { 
//          hsString += (NMEACache.getInstance().getSog()); 
            hsString += DF22.format(((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.SOG, true)).getDoubleValue()); 
          } 
          catch (Exception ex) { System.err.println("SOG in Cache"); hsString += "-"; } // TODO A Format
          hsString += " kts";
          
          String solarTime = "xx:xx:xx\n";
          
          try 
          { 
            try 
            { 
//            gpsTimeString = "GPS Time:" + sdf3.format(NMEACache.getInstance().getGpsDate());
//            gpsTimeString = "GPS Time:" + sdf3.format(((UTCDate)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_DATE_TIME, true)).getValue());
              gpsTimeString = SDF.format(((UTCDate)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_DATE_TIME, true)).getValue());
            } 
            catch (Exception ignore) 
            {
              System.err.println(ignore.getLocalizedMessage());
            }
            long time = 0L;
            try 
            { 
//            time = NMEACache.getInstance().getGpsDate().getTime(); 
              time = ((UTCTime)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_TIME, true)).getValue().getTime(); 
            } 
            catch (NullPointerException npe) { System.err.println("NPE for GPS Time..."); }
            catch (Exception oops) { oops.printStackTrace(); }
//          System.out.println("[time:" + time + ", g:" + g + "]");
            double offset = (g / 15d) * 3600d * 1000d; // in milliseconds
            time += offset;
            solarTime = "\n" + SDF2.format(new Date(time));
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
          
//        double aws = NMEACache.getInstance().getAws();          
//        int awa    = NMEACache.getInstance().getAwa();
//          
//        double bsp = NMEACache.getInstance().getBsp();
//        int hdg    = NMEACache.getInstance().getHdg();
//        double bl  = NMEACache.getInstance().getBigLog();
//        double sl  = NMEACache.getInstance().getSmallLog();
          
          double aws = 0d;
          try { aws = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.AWS, true)).getDoubleValue(); } catch (Exception ex) {}
          int awa    = 0;
          try { awa = (int)((Angle180)NMEAContext.getInstance().getCache().get(NMEADataCache.AWA, true)).getDoubleValue(); } catch (Exception ex) {}
          double bsp = 0d;
          try { bsp = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.BSP, true)).getDoubleValue(); } catch (Exception ex) {}
          int hdg    = 0;
          try { hdg = (int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.HDG_TRUE, true)).getDoubleValue(); } catch (Exception ex) {}
          
          double bl = 0d;
          try { bl = ((Distance)NMEAContext.getInstance().getCache().get(NMEADataCache.LOG, true)).getDoubleValue(); } catch (Exception ex) {}          
          double sl = 0d;
          try { sl = ((Distance)NMEAContext.getInstance().getCache().get(NMEADataCache.DAILY_LOG, true)).getDoubleValue(); } catch (Exception ex) {}     
          double depth = 0d;
          try { depth = ((Depth)NMEAContext.getInstance().getCache().get(NMEADataCache.DBT, true)).getDoubleValue(); } catch (Exception ex) {}
          double temp  = 0d;
          try { temp = ((Temperature)NMEAContext.getInstance().getCache().get(NMEADataCache.WATER_TEMP, true)).getValue(); } catch (Exception ex) {}
          
          windString  = "AWS:" + DF22.format(aws) + " kts, AWA:" + Integer.toString(awa) + "\272\n";
          bspString   = "BSP:" + DF22.format(bsp) + " kts\n";
          hdgString   = "HDG:" + Integer.toString(hdg) + "\n";
          logString   = "Log: " + DF22.format(bl) + " nm, " + DF22.format(sl) + " nm\n";
          depthString = "DBT: " + DF22.format(depth) + " m\n";
          tempString  = "Water Temp:" + DF22.format(temp) + "\272C";
          
          nmeaString = gpsTimeString + "\n" + 
                       posString + "\n" +
                       hsString +
                       solarTime + (solarTime.endsWith("\n")?"":"\n") +
                       windString + 
                       bspString +
                       hdgString +
                       logString +
                       depthString +
                       tempString;
          
          satList = new ArrayList<GPSSatellite>();
          Map<Integer, SVData> satmap = NMEACache.getInstance().getSatellites();

          if (satmap != null)
          {
            Set<Integer> k = satmap.keySet();
            for (Integer satKey : k)
            {
              SVData svdata = satmap.get(satKey);
              GPSSatellite gps = new GPSSatellite(svdata.getSvID(),
                                                  svdata.getElevation(),
                                                  svdata.getAzimuth(),
                                                  svdata.getSnr());
              satList.add(gps);
            }
          }
          
          if (getBGWinByTitle(REALTIME_ALMANAC_BG_WINDOW_TITLE).isDisplayBGWindow()) // Calculate Almanac
          {
            GeoPos pos = ((GeoPos)NMEAContext.getInstance().getCache().get(NMEADataCache.POSITION, true));
            UTCDate utcDate = (UTCDate)NMEAContext.getInstance().getCache().get(NMEADataCache.GPS_DATE_TIME, true);
            if (utcDate == null) // Then get System date
            {
              Date ut = TimeUtil.getGMT();
              utcDate = new UTCDate(ut);
            }
            // Almanac Data
            Calendar cal = Calendar.getInstance();
            cal.setTime(utcDate.getValue());
            double deltaT = ((Double)(ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE])).doubleValue();
            Core.julianDate(cal.get(Calendar.YEAR), 
                            cal.get(Calendar.MONTH) + 1, 
                            cal.get(Calendar.DAY_OF_MONTH), 
                            cal.get(Calendar.HOUR_OF_DAY), 
                            cal.get(Calendar.MINUTE), 
                            cal.get(Calendar.SECOND), 
                            deltaT);
            
            Anomalies.nutation();
            Anomalies.aberration();
            
            Core.aries();
            Core.sun();
            Venus.compute();
            Mars.compute();
            Jupiter.compute();
            Saturn.compute();
            Moon.compute();
            Core.polaris();
            Core.moonPhase();
            Core.weekDay();
            SightReductionUtil sru = null;
            if (pos != null)
            {
              sru = new SightReductionUtil();
              sru.setL(pos.lat);
              sru.setG(pos.lng);
            }
            rtaString = "Aries\tGHA\t" + GeomUtil.decToSex(Context.GHAAtrue, GeomUtil.SWING, GeomUtil.NONE);                
            rtaString += "\nSun\tGHA\t" + GeomUtil.decToSex(Context.GHAsun, GeomUtil.SWING, GeomUtil.NONE);                 
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECsun, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);
            if (pos != null)
            {
              sru.setAHG(Context.GHAsun);
              sru.setD(Context.DECsun);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
            rtaString += "\nMoon\tGHA\t" + GeomUtil.decToSex(Context.GHAmoon, GeomUtil.SWING, GeomUtil.NONE);                  
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECmoon, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);                 
            if (pos != null)
            {
              sru.setAHG(Context.GHAmoon);
              sru.setD(Context.DECmoon);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
            rtaString += "\nVenus\tGHA\t" + GeomUtil.decToSex(Context.GHAvenus, GeomUtil.SWING, GeomUtil.NONE);                  
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECvenus, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);                 
            if (pos != null)
            {
              sru.setAHG(Context.GHAvenus);
              sru.setD(Context.DECvenus);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
            rtaString += "\nMars\tGHA\t" + GeomUtil.decToSex(Context.GHAmars, GeomUtil.SWING, GeomUtil.NONE);                  
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECmars, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);                 
            if (pos != null)
            {
              sru.setAHG(Context.GHAmars);
              sru.setD(Context.DECmars);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
            rtaString += "\nJupiter\tGHA\t" + GeomUtil.decToSex(Context.GHAjupiter, GeomUtil.SWING, GeomUtil.NONE);                  
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECjupiter, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);                 
            if (pos != null)
            {
              sru.setAHG(Context.GHAjupiter);
              sru.setD(Context.DECjupiter);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
            rtaString += "\nSaturn\tGHA\t" + GeomUtil.decToSex(Context.GHAsaturn, GeomUtil.SWING, GeomUtil.NONE);                  
            rtaString += "\n\tD\t" + GeomUtil.decToSex(Context.DECsaturn, GeomUtil.SWING, GeomUtil.NS, GeomUtil.LEADING_SIGN);                 
            if (pos != null)
            {
              sru.setAHG(Context.GHAsaturn);
              sru.setD(Context.DECsaturn);    
              sru.calculate();  
              rtaString += "\n\tAlt\t" + GeomUtil.decToSex(sru.getHe(), GeomUtil.SWING, GeomUtil.NONE);
              rtaString += "\n\tZ\t" + GeomUtil.decToSex(sru.getZ(), GeomUtil.SWING, GeomUtil.NONE);
            }
          }
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }

        desktop.repaint();
        // One second sleep
        try { Thread.sleep(1000L); } catch (Exception ignore) {}
      }
      System.out.println("End of NMEA Cache Thread");
    }
  }
  
  private class HTTPNMEAReaderThread extends Thread
  {
    private final String nmeaServerURL = "http://" +  
                                         (ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" +
                                         (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString() + "/";
    public void run()
    {
      System.out.println("Starting HTTP Thread");
      while (getBGWinByTitle(GPS_SIGNAL_BG_WINDOW_TITLE).isDisplayBGWindow() ||
             getBGWinByTitle(GPS_TIME_BG_WINDOW_TITLE).isDisplayBGWindow()   ||
             getBGWinByTitle(POSITION_BG_WINDOW_TITLE).isDisplayBGWindow()   ||
             getBGWinByTitle(SOG_COG_BG_WINDOW_TITLE).isDisplayBGWindow()    ||
             getBGWinByTitle(NMEA_BG_WINDOW_TITLE).isDisplayBGWindow()       ||
             getBGWinByTitle(AW_BG_WINDOW_TITLE).isDisplayBGWindow()) // TODO REAL_TIMNE_ALMANAC
      {
        String allNMEAData = "";
        try { allNMEAData = HTTPClient.getContent(nmeaServerURL); } catch (Exception ex) { ex.printStackTrace(); }
        DOMParser parser = DesktopContext.getInstance().getParser();
        synchronized (parser)
        {
          try
          {
            double g = 0d;
            int h = 0, m = 0, s = 0;
            boolean longitudeOk = true, 
                    uctOk = true;
            
            parser.parse(new StringReader(allNMEAData));
            XMLDocument dataDoc = parser.getDocument();
            posString = "";
            try { posString += ("L:" + GeomUtil.decToSex(Double.parseDouble(dataDoc.selectNodes("/data/lat").item(0).getFirstChild().getNodeValue()), GeomUtil.SWING, GeomUtil.NS)); } catch (Exception ex) { posString += "-"; }
            posString += "\n";
            try 
            { 
              g = Double.parseDouble(dataDoc.selectNodes("/data/lng").item(0).getFirstChild().getNodeValue());
              posString += ("G:" + GeomUtil.decToSex(g, GeomUtil.SWING, GeomUtil.EW)); 
            } 
            catch (Exception ex) 
            { 
              posString += "-"; 
              longitudeOk = false;
            }
            hsString = "COG:";
            try { hsString += Integer.toString(((int)Double.parseDouble(dataDoc.selectNodes("/data/cog").item(0).getFirstChild().getNodeValue()))); } catch (Exception ex) { hsString += "-"; }
            hsString += " \272\nSOG:";
            try { hsString += (Double.parseDouble(dataDoc.selectNodes("/data/sog").item(0).getFirstChild().getNodeValue())); } catch (Exception ex) { hsString += "-"; }
            hsString += " kts";
            
            gpsTimeString = "";
            
            try 
            { 
              h = Integer.parseInt(dataDoc.selectNodes("/data/utc/@h").item(0).getNodeValue());
              gpsTimeString += ("GPS Time:" + DF2.format(h)) ;
            } 
            catch (Exception ex) 
            { 
              uctOk = false;
              gpsTimeString += "-"; 
            }
            gpsTimeString += ":";
            try 
            { 
              m = Integer.parseInt(dataDoc.selectNodes("/data/utc/@m").item(0).getNodeValue());
              gpsTimeString += DF2.format(m); 
            } 
            catch (Exception ex) 
            { 
              uctOk = false;
              gpsTimeString += "-"; 
            }
            gpsTimeString += ":";
            try 
            { 
              s = (int)Double.parseDouble(dataDoc.selectNodes("/data/utc/@s").item(0).getNodeValue());
              gpsTimeString += DF2.format(s); 
            } 
            catch (Exception ex) 
            { 
              uctOk = false;
              gpsTimeString += "-"; 
            }
            gpsTimeString += " UTC";
            
            String solarTime = "";
            
            if (longitudeOk && uctOk)
            {
              Calendar cal = Calendar.getInstance();
              cal.set(Calendar.HOUR_OF_DAY, h);
              cal.set(Calendar.MINUTE, m);
              cal.set(Calendar.SECOND, s);
              long time = cal.getTimeInMillis();              
              double offset = (g / 15d) * 3600d * 1000d; // in milliseconds
              time += offset;
              solarTime = "\n" + SDF2.format(new Date(time));
            }
            
            nmeaString = gpsTimeString + "\n" + 
                         posString + "\n" +
                         hsString +
                         solarTime;
            
            satList = new ArrayList<GPSSatellite>();
            NodeList sat = dataDoc.selectNodes("/data/satellites/sv");
            for (int i=0; i<sat.getLength(); i++)
            {
              XMLElement xe = (XMLElement)sat.item(i);
              GPSSatellite gps = new GPSSatellite(Integer.parseInt(xe.getAttribute("id")),
                                                  Integer.parseInt(xe.getAttribute("elev")),
                                                  Integer.parseInt(xe.getAttribute("z")),
                                                  Integer.parseInt(xe.getAttribute("snr")));
              satList.add(gps);
            }
            
  //          System.out.println("Pos:" + posString);
  //          System.out.println("H&S:" + hsString);
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
        }
        desktop.repaint();
        try { Thread.sleep(1000L); } catch (Exception ignore) {}
      }
      System.out.println("End of HTTP Thread");
    }
  }

  private class GPSSatellite
  {
    private int id, elev, z, snr;
    public GPSSatellite(int id, int elev, int z, int snr)
    {
      this.id = id;
      this.elev = elev;
      this.z = z;
      this.snr = snr;
    }

    public int getId()
    {
      return id;
    }

    public int getElev()
    {
      return elev;
    }

    public int getZ()
    {
      return z;
    }

    public int getSnr()
    {
      return snr;
    }
  }
  
  public static void main1(String[] args)
  {
    String pattern = "\\\\n";
    String str = "Akeu\\nCoucou\\nLarigou";
    String[] line = str.split(pattern);
    for (int i=0; i<line.length; i++)
      System.out.println(line[i]);
  }
  
  private class RebroadcastPopup extends JPopupMenu
                         implements ActionListener,
                                    PopupMenuListener
  {
    private JMenuItem rebroadcast;

    private final static String REBROADCAST = "Re-broadcast...";

    public RebroadcastPopup()
    {
      super();
      rebroadcast = new JMenuItem(REBROADCAST);
      this.add(rebroadcast);
      rebroadcast.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event)
    {
      if (event.getActionCommand().equals(REBROADCAST))
      {
        if (rebroadcastPanel == null)
          rebroadcastPanel = new RebroadcastPanel();
        int resp = JOptionPane.showConfirmDialog(this, rebroadcastPanel, "Rebroadcast", JOptionPane.OK_CANCEL_OPTION);
        if (resp == JOptionPane.OK_OPTION)
        {
          if (rebroadcastPanel.isHTTPSelected() && HTTPPort == -1)
          {
            HTTPPort = rebroadcastPanel.getHTTPPort();
            System.out.println("Starting HTTP Server on port " + HTTPPort);
            System.setProperty("http.port", Integer.toString(HTTPPort));
            new HTTPServer(new String[] { "-verbose=" + (rebroadcastPanel.httpVerbose()?"y":"n"), "-fmt=xml" }, null, null); 
          }
          else if (HTTPPort != -1 && !rebroadcastPanel.isHTTPSelected())
          {
            String port = System.getProperty("http.port", Integer.toString(HTTPPort));
            System.out.println("Stoping HTTP Server on port " + port);
            // Stop the server
            try { HTTPClient.getContent("http://localhost:" + port + "/exit"); } 
            catch (Exception ex) {}
            HTTPPort = -1;
          }
          
          if (rebroadcastPanel.isUDPSelected() && UDPPort == -1)
          {
            UDPPort = rebroadcastPanel.getUDPPort();
            System.out.println("Creating UDP writer on " + rebroadcastPanel.udpHost() + ":" + UDPPort);
            udpWriter = new UDPWriter(UDPPort, rebroadcastPanel.udpHost()); 
          }
          else if (!rebroadcastPanel.isUDPSelected() && UDPPort != -1)
            UDPPort = -1;
          
          if (rebroadcastPanel.isTCPSelected() && TCPPort == -1)
          {
            TCPPort = rebroadcastPanel.getTCPPort();
//          System.out.println("Creating TCP writer on " + rebroadcastPanel.tcpHost() + ":" + TCPPort);
            System.out.println("Creating TCP writer on port " + TCPPort);
            tcpWriter = new TCPWriter(TCPPort); // , rebroadcastPanel.tcpHost()); 
          }
          else if (!rebroadcastPanel.isTCPSelected() && TCPPort != -1)
          {
            TCPPort = -1;
            try { tcpWriter.close(); }
            catch (Exception ex) { System.err.println(ex.getLocalizedMessage()); }
          }
          
          if (rebroadcastPanel.isGPSDSelected() && GPSDPort == -1)
          {
//          JOptionPane.showMessageDialog(this, "Implemented soon", "GPSd", JOptionPane.INFORMATION_MESSAGE);
            GPSDPort = rebroadcastPanel.getGPSDPort();
            System.out.println("Creating GPSD writer on port " + GPSDPort);
            gpsdWriter = new GPSDWriter(GPSDPort); // , rebroadcastPanel.tcpHost()); 
          }
          else if (!rebroadcastPanel.isGPSDSelected() && GPSDPort != -1)
          {
            GPSDPort = -1;
            try { gpsdWriter.close(); }
            catch (Exception ex) { System.err.println(ex.getLocalizedMessage()); }
          }

          if (rebroadcastPanel.isRMISelected() && RMIPort == -1)
          {
            RMIPort = rebroadcastPanel.getRMIPort();
            System.out.println("Creating RMI writer on port " + RMIPort);
            DesktopContext.getInstance().getNMEAServerManager().startServer(RMIPort);
          }
          else if (!rebroadcastPanel.isRMISelected() && RMIPort != -1)
          {
            RMIPort = -1;
            DesktopContext.getInstance().getNMEAServerManager().stopServer();
          }
        }
      }
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e)
    {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
    {
    }

    public void popupMenuCanceled(PopupMenuEvent e)
    {
    }
  }
}
