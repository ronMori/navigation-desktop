package olivsoftdesktop;


import app.almanac.AlmanacComputer;
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

import astro.calc.GeoPoint;

import calculation.AstroComputer;
import calculation.SightReductionUtil;

import chart.components.ui.ChartPanel;

import chart.components.ui.ChartPanelInterface;

import chart.components.ui.ChartPanelParentInterface;

import chart.components.util.World;

import chartlib.ctx.ChartLibContext;

import chartlib.event.ChartLibListener;

import coreutilities.Utilities;

import coreutilities.gui.HeadingPanel;
import coreutilities.gui.JumboDisplay;

import coreutilities.sql.SQLUtil;

import generatelocator.GenInternalFrame;

import generatelocator.ctx.LocatorContext;
import generatelocator.ctx.LocatorEventListener;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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

import java.awt.geom.AffineTransform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.sql.Connection;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;

import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

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

import nmea.event.NMEAReaderListener;

import nmea.server.constants.Constants;
import nmea.server.ctx.NMEAContext;
import nmea.server.ctx.NMEADataCache;
import nmea.server.datareader.CustomNMEAClient;
import nmea.server.utils.HTTPServer;

import nmea.server.utils.Utils;

import nmea.ui.NMEAAnalyzerInternalFrame;
import nmea.ui.NMEAInternalFrame;
import nmea.ui.NMEAtoGPXInternalFrame;
import nmea.ui.viewer.elements.AWDisplay;

import nmea.ui.viewer.spot.SPOTInternalFrame;

import nmea.ui.viewer.spot.WindGaugePanel;
import nmea.ui.viewer.spot.ctx.SpotCtx;

import nmea.ui.viewer.spot.ctx.SpotEventListener;

import ocss.nmea.parser.Angle180;
import ocss.nmea.parser.Angle360;
import ocss.nmea.parser.Current;
import ocss.nmea.parser.Depth;
import ocss.nmea.parser.Distance;
import ocss.nmea.parser.GeoPos;
import ocss.nmea.parser.Pressure;
import ocss.nmea.parser.SVData;
import ocss.nmea.parser.Speed;
import ocss.nmea.parser.Temperature;
import ocss.nmea.parser.UTCDate;
import ocss.nmea.parser.UTCTime;

import ocss.nmea.utils.WindUtils;

import olivsoftdesktop.ctx.DesktopContext;
import olivsoftdesktop.ctx.DesktopEventListener;

import olivsoftdesktop.param.CategoryPanel;
import olivsoftdesktop.param.ParamData;
import olivsoftdesktop.param.ParamPanel;
import olivsoftdesktop.param.RebroadcastPanel;

import olivsoftdesktop.utils.BackgroundWindow;
import olivsoftdesktop.utils.DesktopNMEAReader;
import olivsoftdesktop.utils.DesktopUtilities;
// import olivsoftdesktop.utils.GPSDWriter;
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
  @SuppressWarnings("compatibility:5264052920075352797")
  private final static long serialVersionUID = 1L;
  private final static String LISTENER_FAMILY = "DESKTOP_SERIALPORT_BROADCASTER";
  
  private boolean fullscreen = false;
  
  private DesktopFrame instance = this;
  private boolean minimizeNMEAConsole = false;
  
  private final static SimpleDateFormat SDF  = new SimpleDateFormat("dd MMM yyyy '\n'HH:mm:ss 'UTC'");
  static { SDF.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); }
  private final static SimpleDateFormat SUN_RISE_SET_SDF = new SimpleDateFormat("E dd-MMM-yyyy HH:mm (z)");
  private final static SimpleDateFormat SDF2 = new SimpleDateFormat("'Solar Time:' HH:mm:ss");
  static { SDF2.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); }
  private final static SimpleDateFormat SDF3 = new SimpleDateFormat("'Solar Time:' HH:mm");
  static { SDF3.setTimeZone(TimeZone.getTimeZone("Etc/UTC")); }
//private final static SimpleDateFormat SDF3 = new SimpleDateFormat("HH:mm:ss 'UTC'");
  private final static DecimalFormat DF2     = new DecimalFormat("00");
  private final static DecimalFormat DF22    = new DecimalFormat("00.00");
  private final static DecimalFormat DF3     = new DecimalFormat("##0");
  private final static DecimalFormat DF31    = new DecimalFormat("###0.0");
  
  private BorderLayout layoutMain = new BorderLayout();
  private JMenuBar menuBar = new JMenuBar();
  
  private JMenu menuFile = new JMenu();
  
  private JMenuItem chartLibMenuItem    = new JMenuItem();
  private JMenu nmeaMenuItem            = new JMenu();
  private JMenuItem nmeaConsoleMenuItem = new JMenuItem();
  private JCheckBoxMenuItem backGroundNMEARead = new JCheckBoxMenuItem();
//private JCheckBoxMenuItem backGroundNMEAServer = new JCheckBoxMenuItem();
  private JMenuItem replayNmeaMenuItem   = new JMenuItem();
  private JMenuItem logToGPXMenuItem     = new JMenuItem();
  private JMenuItem logAnalyzerMenuItem  = new JMenuItem();
  
  private JMenuItem starFinderMenuItem  = new JMenuItem();
  private JMenuItem d2102StarFinderMenuItem  = new JMenuItem();
  private JMenuItem spotMenuItem        = new JMenuItem();
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
  
  private JCheckBoxMenuItem foregroundDataMenuItem = new JCheckBoxMenuItem();

  private JMenu menuHelp = new JMenu();
  private JMenuItem menuHelpAbout = new JMenuItem();
  private JMenuItem menuHelpRebroadcast = new JMenuItem();
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

  private Boolean foregroundData = Boolean.valueOf(false);
  private JInternalFrame specialInternalFrame = new JInternalFrame(); // ForeGround window, transparent
  private List<String> grabbedData = new ArrayList<String>();
  
  double sunAlt = 0, sunZ = 0, moonAlt = 0, moonZ = 0;
  double sunGHA = 0, sunD = 0;

  private class ChartPanelDesktopPane extends JDesktopPane implements ChartPanelParentInterface
    {
      private ChartPanel chartPanel; 
      private final int CHART_PANEL_WIDTH  = 400;
      private final int CHART_PANEL_HEIGHT = 400;
      private GeoPos currentPos = null;
      
      private HeadingPanel headingPanel;
      private final int HEADING_PANEL_WIDTH  = 380;
      private final int HEADING_PANEL_HEIGHT =  40;
      
      private JumboDisplay bspJumbo;
      private DecimalFormat df22 = new DecimalFormat("00.00");
      
      private JumboDisplay beaufortJumbo;
    
      private WindGaugePanel windGaugePanel;
      private final int WINDGAUGE_PANEL_WIDTH  =  50;
      private final int WINDGAUGE_PANEL_HEIGHT = 100;
      
      private JCheckBox sunDayLightCheckBox;
      private JCheckBox sunAzimuthCheckBox;
      private JCheckBox moonAzimuthCheckBox;

      private JCheckBox marqueeCheckBox;
      
      private JCheckBox globeCheckBox;
      private JCheckBox roseCheckBox;
      
      private boolean withMarquee = true;
      private MarqueePanel marqueePanel;
      private final int MARQUEE_PANEL_WIDTH  = 250;
      private final int MARQUEE_PANEL_HEIGHT = 200;
      
      @SuppressWarnings("compatibility:-2193568848390199696")
      private final static long serialVersionUID = 1L;

      public ChartPanelDesktopPane()
      {
        super();
        chartPanel = new ChartPanel(this, CHART_PANEL_WIDTH, CHART_PANEL_HEIGHT);
        chartPanel.setProjection(ChartPanelInterface.GLOBE_VIEW);
        double nLat  =   90D;
        double sLat  =  -90D;
        double wLong = -180.0D;
        double eLong =  180.0D; // chartPanel.calculateEastG(nLat, sLat, wLong);
        
        chartPanel.setPositionToolTipEnabled(false);
        
        chartPanel.setGlobeViewLngOffset(0);
        chartPanel.setGlobeViewRightLeftRotation(23.4);  // Tilt
        chartPanel.setGlobeViewForeAftRotation(0);
        
        chartPanel.setTransparentGlobe(false);
        
        chartPanel.setEastG(eLong);
        chartPanel.setWestG(wLong);
        chartPanel.setNorthL(nLat);
        chartPanel.setSouthL(sLat);
        chartPanel.setHorizontalGridInterval(10D);
        chartPanel.setVerticalGridInterval(10D);
        chartPanel.setWithScale(false);
        Color wpFontColor = ((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.LIVE_WALLPAPER_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor();
        chartPanel.setChartColor(wpFontColor);
        chartPanel.setGridColor(wpFontColor);
        chartPanel.setOpaque(false);
        chartPanel.setCleanFirst(true);
        chartPanel.setChartBackGround(new Color(0f, 0f, 0f, 0f));
        chartPanel.setPostitBGColor(new Color(0f, 0f, 0f, 0.5f));
        chartPanel.setBounds((this.getWidth() - CHART_PANEL_WIDTH) / 2, (this.getHeight() - CHART_PANEL_HEIGHT) / 2, CHART_PANEL_WIDTH, CHART_PANEL_HEIGHT);
        this.add(chartPanel); 
        chartPanel.setVisible(false);
        
        headingPanel = new HeadingPanel(false);
        headingPanel.setTtPrefix("True heading ");
        headingPanel.setPreferredSize(new Dimension(HEADING_PANEL_WIDTH, HEADING_PANEL_HEIGHT));
        headingPanel.setSize(new Dimension(HEADING_PANEL_WIDTH, HEADING_PANEL_HEIGHT));
        headingPanel.setBounds(this.getWidth() - (HEADING_PANEL_WIDTH + 30), 30, HEADING_PANEL_WIDTH, HEADING_PANEL_HEIGHT);
        headingPanel.setVisible(false);
        headingPanel.setDraggable(false);
        headingPanel.setWithColorGradient(false);
        headingPanel.setWithCustomColors(true);
        headingPanel.setBgColor(new Color(0f, 0f, 0f, 0f));
        headingPanel.setTickColor(wpFontColor);
        headingPanel.setBackground(new Color(0f, 0f, 0f, 0f)); // Transparent
        headingPanel.setWithNumber(true);
        this.add(headingPanel); 
        
        bspJumbo = new JumboDisplay("BSP", "00.00", "Boat Speed", 24);
        bspJumbo.setCustomBGColor(new Color(0f, 0f, 0f, 0f));
        bspJumbo.setWithGlossyBG(false);
        bspJumbo.setDisplayColor(wpFontColor);
        bspJumbo.setGridColor(wpFontColor);
        bspJumbo.setVisible(false);        
        bspJumbo.setBounds(this.getWidth() - (bspJumbo.getSize().width + 30), 30 + 5 + HEADING_PANEL_HEIGHT, bspJumbo.getSize().width, bspJumbo.getSize().height);
        this.add(bspJumbo); 
        
        beaufortJumbo = new JumboDisplay("BEAUFORT", "F 0", "True Wind", 24);
        beaufortJumbo.setCustomBGColor(new Color(0f, 0f, 0f, 0f));
        beaufortJumbo.setWithGlossyBG(false);
        beaufortJumbo.setDisplayColor(wpFontColor);
        beaufortJumbo.setGridColor(wpFontColor);
        beaufortJumbo.setVisible(false);        
//      beaufortJumbo.setBounds(this.getWidth() - 2 * (bspJumbo.getSize().width + 30), 30 + 5 + HEADING_PANEL_HEIGHT, bspJumbo.getSize().width, bspJumbo.getSize().height);
        this.add(beaufortJumbo); 
        
        globeCheckBox = new JCheckBox("Boat Position");
        globeCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        globeCheckBox.setForeground(wpFontColor);
        globeCheckBox.setSelected(true);
        globeCheckBox.setOpaque(false);
        globeCheckBox.setVisible(false);
//        globeCheckBox.setBounds(this.getWidth() - (globeCheckBox.getSize().width + 30), 
//                                30 + 5 + HEADING_PANEL_HEIGHT + 5 + bspJumbo.getSize().height, 
//                                globeCheckBox.getSize().width, 
//                                globeCheckBox.getSize().height);
        this.add(globeCheckBox);
        globeCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });
        
        roseCheckBox  = new JCheckBox("Compass Rose");
        roseCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        roseCheckBox.setForeground(wpFontColor);
        roseCheckBox.setSelected(true);
        roseCheckBox.setOpaque(false);
        roseCheckBox.setVisible(false);
//        roseCheckBox.setBounds(this.getWidth() - (roseCheckBox.getSize().width + 30), 
//                                30 + 5 + HEADING_PANEL_HEIGHT + 5 + bspJumbo.getSize().height, 
//                                roseCheckBox.getSize().width, 
//                                roseCheckBox.getSize().height);
        this.add(roseCheckBox);
        roseCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });
        
        sunDayLightCheckBox = new JCheckBox("Show daylight");
        sunDayLightCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        sunDayLightCheckBox.setForeground(wpFontColor);
        sunDayLightCheckBox.setSelected(false);
        sunDayLightCheckBox.setOpaque(false);
        sunDayLightCheckBox.setVisible(false);
//        sunDayLightCheckBox.setBounds(this.getWidth() - (sunDayLightCheckBox.getSize().width + 30), 
//                                30 + 5 + HEADING_PANEL_HEIGHT + 5 + bspJumbo.getSize().height, 
//                                sunDayLightCheckBox.getSize().width, 
//                                sunDayLightCheckBox.getSize().height);
        this.add(sunDayLightCheckBox);
        sunDayLightCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });

        sunAzimuthCheckBox = new JCheckBox("Sun Azimuth");
        sunAzimuthCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        sunAzimuthCheckBox.setForeground(wpFontColor);
        sunAzimuthCheckBox.setSelected(true);
        sunAzimuthCheckBox.setOpaque(false);
        sunAzimuthCheckBox.setVisible(false);
//        sunAzimuthCheckBox.setBounds(this.getWidth() - (sunAzimuthCheckBox.getSize().width + 30), 
//                                30 + 5 + HEADING_PANEL_HEIGHT + 
//                                     5 + bspJumbo.getSize().height + 
//                                     5 + sunDayLightCheckBox.getSize().height, 
//                                sunAzimuthCheckBox.getSize().width, 
//                                sunAzimuthCheckBox.getSize().height);
        this.add(sunAzimuthCheckBox);
        sunAzimuthCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });

        moonAzimuthCheckBox = new JCheckBox("Moon Azimuth");
        moonAzimuthCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        moonAzimuthCheckBox.setForeground(wpFontColor);
        moonAzimuthCheckBox.setSelected(true);
        moonAzimuthCheckBox.setOpaque(false);
        moonAzimuthCheckBox.setVisible(false);
//        moonAzimuthCheckBox.setBounds(this.getWidth() - (moonAzimuthCheckBox.getSize().width + 30), 
//                                30 + 5 + HEADING_PANEL_HEIGHT + 
//                                     5 + bspJumbo.getSize().height + 
//                                     5 + sunDayLightCheckBox.getSize().height + 
//                                     5 + sunAzimuthCheckBox.getSize().height, 
//                                moonAzimuthCheckBox.getSize().width, 
//                                moonAzimuthCheckBox.getSize().height);
        this.add(moonAzimuthCheckBox);
        moonAzimuthCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });
        
        marqueeCheckBox = new JCheckBox("Marquee");
        marqueeCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
        marqueeCheckBox.setForeground(wpFontColor);
        marqueeCheckBox.setSelected(false);
        marqueeCheckBox.setOpaque(false);
        marqueeCheckBox.setVisible(true);
        this.add(marqueeCheckBox);
        marqueeCheckBox.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              repaint();
            }
          });
        
        windGaugePanel = new WindGaugePanel();
        windGaugePanel.setPreferredSize(new Dimension(WINDGAUGE_PANEL_WIDTH, WINDGAUGE_PANEL_HEIGHT));
        windGaugePanel.setSize(new Dimension(WINDGAUGE_PANEL_WIDTH, WINDGAUGE_PANEL_HEIGHT));
//      windGaugePanel.setBounds(this.getWidth() - (WINDGAUGE_PANEL_WIDTH + 30), 30, WINDGAUGE_PANEL_WIDTH, WINDGAUGE_PANEL_HEIGHT);
        windGaugePanel.setVisible(false);
        windGaugePanel.setGlossy(false);
        windGaugePanel.setCustomBG(new Color(0f, 0f, 0f, 0f));
        windGaugePanel.setBackground(new Color(0f, 0f, 0f, 0f)); // Transparent
        this.add(windGaugePanel);     
        
        if (withMarquee)
        {
          marqueePanel = new MarqueePanel();
          marqueePanel.setPreferredSize(new Dimension(MARQUEE_PANEL_WIDTH, MARQUEE_PANEL_HEIGHT));
          marqueePanel.setSize(new Dimension(MARQUEE_PANEL_WIDTH, MARQUEE_PANEL_HEIGHT));
  //      miscDataPanel.setBounds(this.getWidth() - (MISC_DATA_PANEL_WIDTH + 30), 30, MISC_DATA_PANEL_WIDTH, MISC_DATA_PANEL_HEIGHT);
          marqueePanel.setVisible(false);
          marqueePanel.setBackground(new Color(0f, 0f, 0f, 0f)); // Transparent
          this.add(marqueePanel);     
        }
      }
      
      private void drawGlossyRectangularDisplay(Graphics2D g2d, Point topLeft, Point bottomRight, Color lightColor, Color darkColor, float transparency)
      {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        g2d.setPaint(null);

        g2d.setColor(darkColor);

        int width  = bottomRight.x - topLeft.x;
        int height = bottomRight.y - topLeft.y;

        g2d.fillRoundRect(topLeft.x , topLeft.y, width, height, 30, 30);

        Point gradientOrigin = new Point(0, //topLeft.x + (width) / 2,
                                         0);
        GradientPaint gradient = new GradientPaint(gradientOrigin.x, 
                                                   gradientOrigin.y, 
                                                   lightColor, 
                                                   gradientOrigin.x, 
                                                   gradientOrigin.y + (height / 3), 
                                                   darkColor); // vertical, light on top
        g2d.setPaint(gradient);
        int offset = 1; //(int)(width * 0.025);
        int arcRadius = 5;
        g2d.fillRoundRect(topLeft.x + offset, topLeft.y + offset, (width - (2 * offset)), (height - (2 * offset)), 2 * arcRadius, 2 * arcRadius); 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      }

      private void setBSP(double d)
      {
        if (d != -Double.MAX_VALUE)
        {
          if (bspJumbo.isVisible())
          {
            bspJumbo.setValue(df22.format(d));
          }
        }
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
        
        if (foregroundData.booleanValue())// aka Live Wallpaper
        {
      //  System.out.println("Displaying live wall paper.");
          Color wpFontColor = ((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.LIVE_WALLPAPER_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor();
          bspJumbo.setDisplayColor(wpFontColor);
          beaufortJumbo.setDisplayColor(wpFontColor);
          headingPanel.setTickColor(wpFontColor);
          globeCheckBox.setForeground(wpFontColor);
          roseCheckBox.setForeground(wpFontColor);
          sunDayLightCheckBox.setForeground(wpFontColor);
          sunAzimuthCheckBox.setForeground(wpFontColor);
          moonAzimuthCheckBox.setForeground(wpFontColor);
          
          if (true) // Glossy effect
          {
            startColor = new Color(255, 255, 255);
            endColor   = new Color(102, 102, 102);
            drawGlossyRectangularDisplay((Graphics2D)gr, 
                                     new Point(10, 10), 
                                     new Point(this.getWidth() - 10, this.getHeight() - 10), 
                                     endColor, 
                                     startColor, 
                                     0.25f);
            // Background Grid
            Stroke orig = ((Graphics2D)gr).getStroke();
            Stroke stroke5 = new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
            Stroke dotted = new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] {5f}, 0f);

            ((Graphics2D)gr).setStroke(stroke5);
            gr.drawRoundRect(10, 10, this.getWidth() - 20, this.getHeight() - 20, 30, 30);
            ((Graphics2D)gr).setStroke(orig);
            float[] comps = wpFontColor.getColorComponents(new float[3]);
            Color gridColor = new Color(comps[0], comps[1], comps[2], 0.15f);
            gr.setColor(gridColor);
            for (int i=30; i<this.getWidth() - 1; i+=30)
            {
              if (i % 150 == 0)
                ((Graphics2D)gr).setStroke(orig);
              else
                ((Graphics2D)gr).setStroke(dotted);
              gr.drawLine(i, 10, i, this.getHeight() - 10);
            }
            for (int i=30; i<this.getHeight() - 1; i+=30)
            {
              if (i % 150 == 0)
                ((Graphics2D)gr).setStroke(orig);
              else
                ((Graphics2D)gr).setStroke(dotted);
              gr.drawLine(10, i, this.getWidth() - 10, i);
            }
            ((Graphics2D)gr).setStroke(orig);
            gr.setColor(wpFontColor);            
          }
          // Live Wallpaper, data grabber
          Font digiFont = null; 
       // Font jumboFont   = JumboDisplay.tryToLoadFont("TRANA___.TTF", null);
       // Font bgJumboFont = JumboDisplay.tryToLoadFont("TRANGA__.TTF", null);

          if (false)
          {
            try { digiFont = JumboDisplay.tryToLoadFont("ds-digi.ttf", null); }
            catch (Exception ex) { System.err.println(ex.getMessage()); }
          }
          else
          {             
            try 
            { 
              digiFont = ((Font) ParamPanel.getData()[ParamData.WALLPAPER_FONT][ParamPanel.PRM_VALUE]); 
//            System.out.println("Loaded Font [" + digiFont.toString() + "]");
            }
            catch (Exception ex) 
            { 
              try { digiFont = new Font("Courier New", 18, Font.BOLD); }
              catch (Exception ex2) 
              { 
                System.err.println(ex2.getMessage()); 
              }
            }
          }
          if (digiFont == null)
          {
            try { digiFont = new Font("Courier New", 18, Font.BOLD); }
            catch (Exception ex2) 
            { 
              System.err.println(ex2.getMessage()); 
            }
        //  System.out.println("--> 2. Font is " + digiFont.toString());
          }
          gr.setFont(digiFont);
  //      gr.setColor(Color.lightGray);
          gr.setColor(wpFontColor);

          int y = 40;
          synchronized (grabbedData)
          {
            List<String> data = new ArrayList<String>(grabbedData); // Clone
            for (String s : data)
            {
              String displayString = s; // .replace('\272', ' ').replace('\260', ' ');
  //            gr.setFont(bgJumboFont.deriveFont(Font.BOLD, 20f));
  //            gr.setColor(Color.darkGray);
  //            gr.drawString(displayString, 15, y);
  //            gr.setFont(jumboFont.deriveFont(Font.BOLD, 20f));
  //            gr.setColor(Color.lightGray);
              gr.drawString(displayString, 25, y);
              y += (gr.getFont().getSize() + 2);
            }
          }
          // Some drawings...
          NMEADataCache ndc = NMEAContext.getInstance().getCache();
          boolean displayRose = true;
          int twd    = 0;
          int hdg    = 0;
          try { synchronized (ndc) { twd = (int)((Angle360)ndc.get(NMEADataCache.TWD, true)).getDoubleValue(); } } catch (Exception ex) { ex.printStackTrace(); }
          // HDG (true)
          try { synchronized (ndc) { hdg = (int)((Angle360)ndc.get(NMEADataCache.HDG_TRUE, true)).getDoubleValue(); } } catch (Exception ex) { ex.printStackTrace(); }
          if (displayRose && roseCheckBox.isSelected())
          {
            Dimension dim = this.getSize();
            double radius = (Math.min(dim.width, dim.height) * 0.9) / 2d;
            // Rose
         // gr.setColor(Color.cyan); // was darkGray. TODO Preference
            int graphicXOffset = 0;
            int graphicYOffset = 0;
            for (int i=0; i<360; i+= 5)
            {
              int x1 = (dim.width / 2) + (int)((radius - (i%45==0?20:10)) * Math.cos(Math.toRadians(i)));  
              int y1 = (dim.height / 2) + (int)((radius - (i%45==0?20:10)) * Math.sin(Math.toRadians(i)));  
              int x2 = (dim.width / 2) + (int)((radius) * Math.cos(Math.toRadians(i)));  
              int y2 = (dim.height / 2) + (int)((radius) * Math.sin(Math.toRadians(i)));  
              gr.drawLine(x1 + graphicXOffset, y1 + graphicYOffset, x2 + graphicXOffset, y2 + graphicYOffset);
            }
            // Draw arrows here
            // TWD
            int x1 = (dim.width / 2)  + (int)((radius * 0.75) * Math.cos(Math.toRadians(twd + 90)));  
            int y1 = (dim.height / 2) + (int)((radius * 0.75) * Math.sin(Math.toRadians(twd + 90)));  
            int x2 = (dim.width / 2)  - (int)((radius * 0.75) * Math.cos(Math.toRadians(twd + 90)));  
            int y2 = (dim.height / 2) - (int)((radius * 0.75) * Math.sin(Math.toRadians(twd + 90)));  
            Point from = new Point(x1, y1), to = new Point(x2, y2);
            Graphics2D g2 = (Graphics2D)gr;
            Utils.drawHollowArrow(g2, from, to, wpFontColor);
            AffineTransform oldTx = g2.getTransform();

            g2.transform(AffineTransform.getTranslateInstance((dim.width / 2), (dim.height / 2)));
            g2.transform(AffineTransform.getRotateInstance(Math.toRadians(twd)));  
            String str = "TWD";
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            int strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
            g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.70) - 20);        
            str = Integer.toString(twd % 360) + "\272";
            strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
            g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.70));        
            g2.setTransform(oldTx);

            x1 = (dim.width / 2)  + (int)((radius * 0.75) * Math.cos(Math.toRadians(hdg + 90)));  
            y1 = (dim.height / 2) + (int)((radius * 0.75) * Math.sin(Math.toRadians(hdg + 90)));  
            x2 = (dim.width / 2)  - (int)((radius * 0.75) * Math.cos(Math.toRadians(hdg + 90)));  
            y2 = (dim.height / 2) - (int)((radius * 0.75) * Math.sin(Math.toRadians(hdg + 90)));  
            from = new Point(x1, y1); 
            to = new Point(x2, y2);
            Utils.drawHollowArrow(g2, from, to, wpFontColor);
        //  AffineTransform oldTx = g2.getTransform();
            // An inner "rose", for the twd
            graphicXOffset = 0;
            graphicYOffset = 0;
            radius *= 0.95;
            for (int i=twd; i<twd+360; i+= 1)
            {
              int notchLength = ((i-twd)%45==0?20:((i-twd)%5==0?15:10));
              int _x1 = (dim.width / 2) + (int)((radius - notchLength) * Math.cos(Math.toRadians(i)));  
              int _y1 = (dim.height / 2) + (int)((radius - notchLength) * Math.sin(Math.toRadians(i)));  
              int _x2 = (dim.width / 2) + (int)((radius) * Math.cos(Math.toRadians(i)));  
              int _y2 = (dim.height / 2) + (int)((radius) * Math.sin(Math.toRadians(i)));  
              gr.drawLine(_x1 + graphicXOffset, _y1 + graphicYOffset, _x2 + graphicXOffset, _y2 + graphicYOffset);
            }

            g2.transform(AffineTransform.getTranslateInstance((dim.width / 2), (dim.height / 2)));
            g2.transform(AffineTransform.getRotateInstance(Math.toRadians(hdg)));  
            str = "HDG";
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
            g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.70) - 20);        
            str = Integer.toString(hdg % 360) + "\272";
            strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
            g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.70));        
            g2.setTransform(oldTx);
            // An inner "rose", for the heading
            graphicXOffset = 0;
            graphicYOffset = 0;
            radius *= 0.95;
            for (int i=hdg; i<hdg+360; i+= 1)
            {
              int notchLength = ((i-hdg)%45==0?20:((i-hdg)%5==0?15:10));
              int _x1 = (dim.width / 2) + (int)((radius - notchLength) * Math.cos(Math.toRadians(i)));  
              int _y1 = (dim.height / 2) + (int)((radius - notchLength) * Math.sin(Math.toRadians(i)));  
              int _x2 = (dim.width / 2) + (int)((radius) * Math.cos(Math.toRadians(i)));  
              int _y2 = (dim.height / 2) + (int)((radius) * Math.sin(Math.toRadians(i)));  
              gr.drawLine(_x1 + graphicXOffset, _y1 + graphicYOffset, _x2 + graphicXOffset, _y2 + graphicYOffset);
            }
            
            // Sun & Moon
            if (sunAlt >= 0 && sunAzimuthCheckBox.isSelected())
            {
              x1 = (dim.width / 2);  
              y1 = (dim.height / 2);  
              x2 = (dim.width / 2)  - (int)((radius * 0.8) * Math.cos(Math.toRadians(sunZ + 90)));  
              y2 = (dim.height / 2) - (int)((radius * 0.8) * Math.sin(Math.toRadians(sunZ + 90)));  
              from = new Point(x1, y1); 
              to = new Point(x2, y2);
              Utils.drawArrow(g2, from, to, wpFontColor);
              g2.transform(AffineTransform.getTranslateInstance((dim.width / 2), (dim.height / 2)));
              g2.transform(AffineTransform.getRotateInstance(Math.toRadians(sunZ)));  
              str = "Sun";
              g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
              strWidth = gr.getFontMetrics(g2.getFont()).stringWidth(str.trim());
              g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.70));        
              str = GeomUtil.decToSex(sunAlt, GeomUtil.SWING, GeomUtil.NONE);
              strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
              g2.drawString(str, -strWidth / 2, ((int)((dim.height / 2) * -0.70)) - 18);        
              g2.setTransform(oldTx);
            }
            if (moonAlt >= 0 && moonAzimuthCheckBox.isSelected())
            {
              x1 = (dim.width / 2);  
              y1 = (dim.height / 2);  
              x2 = (dim.width / 2)  - (int)((radius * 0.8) * Math.cos(Math.toRadians(moonZ + 90)));  
              y2 = (dim.height / 2) - (int)((radius * 0.8) * Math.sin(Math.toRadians(moonZ + 90)));  
              from = new Point(x1, y1); 
              to = new Point(x2, y2);
              Utils.drawArrow(g2, from, to, wpFontColor);
              g2.transform(AffineTransform.getTranslateInstance((dim.width / 2), (dim.height / 2)));
              g2.transform(AffineTransform.getRotateInstance(Math.toRadians(moonZ)));  
              str = "Moon";
              g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));
              strWidth = gr.getFontMetrics(g2.getFont()).stringWidth(str.trim());
              g2.drawString(str, -strWidth / 2, (int)((dim.height / 2) * -0.75));        
              str = GeomUtil.decToSex(moonAlt, GeomUtil.SWING, GeomUtil.NONE);
              strWidth = gr.getFontMetrics(gr.getFont()).stringWidth(str.trim());
              g2.drawString(str, -strWidth / 2, ((int)((dim.height / 2) * -0.75)) - 18);        
              g2.setTransform(oldTx);
            }
          }
          // Position on a globe?
          synchronized (ndc) { currentPos = (GeoPos)ndc.get(NMEADataCache.POSITION, false); }
          if (currentPos != null)
          {
            chartPanel.setVisible(globeCheckBox.isSelected());
            chartPanel.setChartColor(wpFontColor);
            chartPanel.setGridColor(wpFontColor);
            chartPanel.setGlobeViewLngOffset(currentPos.lng);
//          chartPanel.setGlobeViewRightLeftRotation(23.0);  // Tilt
            chartPanel.setGlobeViewForeAftRotation(currentPos.lat);
            chartPanel.setBounds((this.getWidth() - CHART_PANEL_WIDTH) / 2, 
                                 (this.getHeight() - CHART_PANEL_HEIGHT) / 2, 
                                 CHART_PANEL_WIDTH, 
                                 CHART_PANEL_HEIGHT);
            
            int currentYPos = 30;
            headingPanel.setBounds(this.getWidth() - (HEADING_PANEL_WIDTH + 30), 
                                   currentYPos, 
                                   HEADING_PANEL_WIDTH, 
                                   HEADING_PANEL_HEIGHT);
            headingPanel.setVisible(true);
            gr.drawRoundRect(this.getWidth() - (HEADING_PANEL_WIDTH + 30), 
                             30, 
                             HEADING_PANEL_WIDTH, 
                             HEADING_PANEL_HEIGHT, 10, 10);
            headingPanel.setHdg(hdg);
            
            bspJumbo.setVisible(true);   
            currentYPos += (5 + HEADING_PANEL_HEIGHT);
            bspJumbo.setBounds(this.getWidth() - (bspJumbo.getSize().width + 30), 
                               currentYPos, 
                               bspJumbo.getSize().width, 
                               bspJumbo.getSize().height);
            gr.drawRoundRect(this.getWidth() - (bspJumbo.getSize().width + 30), 
                             currentYPos, 
                             bspJumbo.getSize().width, 
                             bspJumbo.getSize().height, 5, 5);
            double bsp = 0d;
            try { synchronized (ndc) { bsp = ((Speed)ndc.get(NMEADataCache.BSP, true)).getDoubleValue(); } } catch (Exception ex) { ex.printStackTrace(); }
            setBSP(bsp);
            
            beaufortJumbo.setVisible(true);   
            beaufortJumbo.setBounds(this.getWidth() - (2 * bspJumbo.getSize().width + 30) - 5, 
                                    currentYPos, 
                                    bspJumbo.getSize().width, 
                                    bspJumbo.getSize().height);
            gr.drawRoundRect(this.getWidth() - (2 * bspJumbo.getSize().width + 30) - 5, 
                             currentYPos, 
                             bspJumbo.getSize().width, 
                             bspJumbo.getSize().height, 5, 5);
            // Display, below
            globeCheckBox.setVisible(true);
            currentYPos += (5 + bspJumbo.getSize().height);
            globeCheckBox.setBounds(this.getWidth() - (globeCheckBox.getSize().width + 30), 
                                    currentYPos, 
                                    globeCheckBox.getPreferredSize().width, 
                                    globeCheckBox.getPreferredSize().height);
            
            roseCheckBox.setVisible(true);
            currentYPos += (5 + globeCheckBox.getSize().height);
            roseCheckBox.setBounds(this.getWidth() - (roseCheckBox.getSize().width + 30), 
                                    currentYPos, 
                                    roseCheckBox.getPreferredSize().width, 
                                    roseCheckBox.getPreferredSize().height);
            
            sunDayLightCheckBox.setVisible(true);
            sunDayLightCheckBox.setEnabled(globeCheckBox.isSelected());
            currentYPos += (5 + roseCheckBox.getPreferredSize().height);
            sunDayLightCheckBox.setBounds(this.getWidth() - (sunDayLightCheckBox.getSize().width + 30), 
                                    currentYPos, 
                                    sunDayLightCheckBox.getPreferredSize().width, 
                                    sunDayLightCheckBox.getPreferredSize().height);
            
            sunAzimuthCheckBox.setVisible(true);
            sunAzimuthCheckBox.setEnabled(sunAlt >= 0 && roseCheckBox.isSelected());
            currentYPos += (5 + sunDayLightCheckBox.getPreferredSize().height);
            sunAzimuthCheckBox.setBounds(this.getWidth() - (sunAzimuthCheckBox.getSize().width + 30), 
                                    currentYPos, 
                                    sunAzimuthCheckBox.getPreferredSize().width, 
                                    sunAzimuthCheckBox.getPreferredSize().height);
            
            moonAzimuthCheckBox.setVisible(true);
            moonAzimuthCheckBox.setEnabled(moonAlt >= 0 && roseCheckBox.isSelected());
            currentYPos += (5 + sunAzimuthCheckBox.getPreferredSize().height);
            moonAzimuthCheckBox.setBounds(this.getWidth() - (moonAzimuthCheckBox.getSize().width + 30), 
                                          currentYPos, 
                                          moonAzimuthCheckBox.getPreferredSize().width, 
                                          moonAzimuthCheckBox.getPreferredSize().height);
            currentYPos += (5 + moonAzimuthCheckBox.getPreferredSize().height);

            marqueeCheckBox.setBounds(this.getWidth() - (marqueeCheckBox.getSize().width + 30), 
                                      currentYPos, 
                                      marqueeCheckBox.getPreferredSize().width, 
                                      marqueeCheckBox.getPreferredSize().height);
            currentYPos += (5 + marqueeCheckBox.getPreferredSize().height);

            windGaugePanel.setBounds(this.getWidth() - (WINDGAUGE_PANEL_WIDTH + 30), 
                                     currentYPos, 
                                     WINDGAUGE_PANEL_WIDTH, 
                                     WINDGAUGE_PANEL_HEIGHT);
            windGaugePanel.setVisible(true);
            gr.drawRoundRect(this.getWidth() - (WINDGAUGE_PANEL_WIDTH + 30), 
                             currentYPos, 
                             WINDGAUGE_PANEL_WIDTH, 
                             WINDGAUGE_PANEL_HEIGHT, 10, 10);
            double tws = 0d;
            try { synchronized (ndc) { tws = ((Speed)ndc.get(NMEADataCache.TWS, true)).getDoubleValue(); } } catch (Exception ex) { ex.printStackTrace(); }
            windGaugePanel.setTws((float)tws);
            windGaugePanel.setToolTipText("TWS " + df22.format(tws) + " kts");
            beaufortJumbo.setValue("F " + DF3.format(WindUtils.getBeaufort(tws)));
            if (withMarquee)
            {
              currentYPos += (5 + WINDGAUGE_PANEL_HEIGHT);
              marqueePanel.setBounds(this.getWidth() - (MARQUEE_PANEL_WIDTH + 30), 
//                                   currentYPos,                                    // Next in line...
                                     this.getHeight() - (MARQUEE_PANEL_HEIGHT + 30), // Bottom of the wallpaper
                                     MARQUEE_PANEL_WIDTH, 
                                     MARQUEE_PANEL_HEIGHT);
              if (marqueeCheckBox.isSelected())
              {
                marqueePanel.setVisible(true);
//                gr.drawRoundRect(this.getWidth() - (MARQUEE_PANEL_WIDTH + 30), 
////                               currentYPos,                                    // Next in line...
//                                 this.getHeight() - (MARQUEE_PANEL_HEIGHT + 30), // Bottom of the wallpaper
//                                 MARQUEE_PANEL_WIDTH, 
//                                 MARQUEE_PANEL_HEIGHT, 10, 10);
                if (!marqueePanel.isRunning())
                {
                  marqueePanel.setup(); // "--- Place holder for transient data ---");
                  marqueePanel.start();
                }
              }
              else
              {
                marqueePanel.setVisible(false);
                if (marqueePanel.isRunning())
                {
                  marqueePanel.stop();
                }
              }
            }
          }
        }
        else
        {
          bspJumbo.setVisible(false);        
          beaufortJumbo.setVisible(false);    
          globeCheckBox.setVisible(false);
          roseCheckBox.setVisible(false);
          sunDayLightCheckBox.setVisible(false);
          sunAzimuthCheckBox.setVisible(false);
          moonAzimuthCheckBox.setVisible(false);
          headingPanel.setVisible(false);
          windGaugePanel.setVisible(false);
          if (withMarquee)
            marqueePanel.setVisible(false);
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
  //    if (foregroundData.booleanValue())
  //      specialInternalFrame.moveToFront();
//      System.out.println(">>> DEBUG >>> Bottom of the paintComponent");
      }

      @Override
      public void chartPanelPaintComponent(Graphics gr)
      {
        if (foregroundData.booleanValue() && currentPos != null)
        {
          chartPanel.setVisible(true);
          Graphics2D g2d = null;
          if (gr instanceof Graphics2D)
            g2d = (Graphics2D)gr;
          World.drawChart(chartPanel, gr);
          // Plot current pos
          double ls = currentPos.lat;
          double gs = currentPos.lng;
          GeoPoint currPos = new GeoPoint(currentPos.lat, currentPos.lng);
          Point gp = chartPanel.getPanelPoint(ls, gs);
          if (isVisible(ls, gs))
          {
            gr.setColor(Color.red);
            gr.fillOval(gp.x - 3, gp.y - 3, 6, 6);
            chartPanel.postit(gr, "GPS Position", gp.x + 2, gp.y - 2, Color.yellow);
          }
          if (sunDayLightCheckBox.isSelected())
          {
            boolean debug = false;
            // Day light
            double dayCenterLongitude = 0;
            double dayCenterLatitude = 0;
            if (sunGHA < 180)
              dayCenterLongitude = -sunGHA;
            if (sunGHA >= 180)
              dayCenterLongitude = 360 - sunGHA;
            dayCenterLatitude = sunD;
            
            GeoPoint sunPos = new GeoPoint(dayCenterLatitude, dayCenterLongitude);
      //    System.out.println("Sun Position:" + sunPos.toString());
            Map<Double, Double> nightMap = new HashMap<Double, Double>();
  
            // the border of the night
            for (int i=0; i<360; i++)
            {
              GeoPoint nightGp = deadReckoning(sunPos, 90 * 60, i);
              double lng = nightGp.getG();
              if (lng < -180)
                lng += 360;
              if (lng > 180)
                lng -= 360;
              if (debug)
              {
                Point pp = chartPanel.getPanelPoint(nightGp);
                gr.setColor(Color.green);
                gr.fillOval(pp.x - 1, pp.y - 1, 2, 2);
              }
              if (isVisible(nightGp.getL(), lng))
                nightMap.put(lng, nightGp.getL());
            }
    
            // Sort the points of the night, and then the points of the limb.
            // Store the Points (graphical). Sort on y, top to bottom
            Map<Integer, Integer> nightBorderMap = new HashMap<Integer, Integer>();
            
            Set<Double> lngSet = nightMap.keySet();
            for (Double lng : lngSet)
            {
              Double lat = nightMap.get(lng);
              Point pp = chartPanel.getPanelPoint(new GeoPoint(lat, lng));
              nightBorderMap.put(pp.y, pp.x);
            }
            SortedSet<Integer> sortedNightBorder = new TreeSet<Integer>(nightBorderMap.keySet());
            Polygon night = new Polygon();
            Point previous = null;
            int nb = 1;
            if (debug)
            {
              gr.setColor(Color.yellow);
              gr.setFont(gr.getFont().deriveFont(14f));
            }
            
            // Night
            Point firstPoint = null, lastPoint = null;
            for (Integer y : sortedNightBorder)
            {
              int x = nightBorderMap.get(y).intValue();
              Point pp = new Point(x, y);
              if (firstPoint == null)
                firstPoint = pp;
              lastPoint = pp;
              night.addPoint(pp.x, pp.y);
              if (debug)
              {
  //            System.out.println("Nigh:" + pp);
                if (previous != null)
                {
                  gr.drawLine(previous.x, previous.y, pp.x, pp.y);
                  if ((nb - 1) % 10 == 0)
                    gr.drawString(Integer.toString(nb), pp.x, pp.y);
                  nb++;
                }
                previous = pp;
              }
            }
            // We are at the bottom
            Point center = chartPanel.getPanelPoint(currPos);
            double startAngle   = GeomUtil.getDir(lastPoint.x - center.x,  center.y - lastPoint.y), 
                   arrivalAngle = GeomUtil.getDir(firstPoint.x - center.x, center.y - firstPoint.y);
            if (debug)
            {
              gr.setColor(Color.yellow);
              gr.drawString("Start (" + (int)startAngle + "\272)", lastPoint.x, lastPoint.y);
              gr.drawString("Finish (" + (int)arrivalAngle + "\272)", firstPoint.x, firstPoint.y);
            }
            int incr = 0, firstBoundary = 0, lastBoundary = 0;
            // For the direction (esat/west), see LHA, instead sunAlt
            double lhaSun = sunGHA + currPos.getG();
            while (lhaSun < 0)
              lhaSun += 360;
            while (lhaSun > 360)
              lhaSun -= 360;
            if (debug)
              System.out.println("Sun LHA:" + lhaSun);
        //  if (sunAlt > 0) // Daylight, follow the limb, from the bottom toward the west (clockwise)
            if (lhaSun > 180)
            {
              if (debug)
                System.out.println("From " + startAngle + " to " + arrivalAngle + ", going West");
              incr = 1;
              firstBoundary = (int)Math.ceil(startAngle);
              lastBoundary = (int)Math.floor(arrivalAngle);
              while (lastBoundary < firstBoundary)
                lastBoundary += 360;
            }
            else            // Night, follow the limb, from the bottom toward the east (counter-clockwise)
            {
              if (debug)
                System.out.println("From " + startAngle + " to " + arrivalAngle + ", going East");
              incr = -1;
              firstBoundary = (int)Math.floor(startAngle);
              lastBoundary = (int)Math.ceil(arrivalAngle);
              while (lastBoundary > firstBoundary)
                firstBoundary += 360;
            }
            for (int i=firstBoundary; (incr>0 && i<=lastBoundary) || (incr<0 && i>=lastBoundary); i+=incr)
            {
              GeoPoint earthCirc = deadReckoning(currPos, 90 * 60, i);
  //            double lng = earthCirc.getG();
  //            if (lng < -180)
  //              lng += 360;
  //            if (lng > 180)
  //              lng -= 360;
              Point limbPt = chartPanel.getPanelPoint(earthCirc);
              night.addPoint(limbPt.x, limbPt.y);
              if (debug)
              {
  //            System.out.println("Limb:" + limbPt);
                if (previous != null)
                {
                  gr.drawLine(previous.x, previous.y, limbPt.x, limbPt.y);
                  if ((nb - 1) % 10 == 0)
                    gr.drawString(Integer.toString(nb), limbPt.x, limbPt.y);
                  nb++;
                }
                previous = limbPt;
              }
            }
            // Fill the night polygon
            if (true)
            {
              gr.setColor(Color.darkGray);
              ((Graphics2D)gr).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
               gr.fillPolygon(night);
              ((Graphics2D)gr).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));        
            }
          }
        }
        else
          chartPanel.setVisible(false);
      }
  
      /**
       * Spherical Model used here
       *
       * @param start in degrees
       * @param dist in nautical miles
       * @param bearing in degrees
       * @return
       */
      private GeoPoint deadReckoning(GeoPoint start, double dist, double bearing)
      {
        GeoPoint reached = null;
        double radianDistance = Math.toRadians(dist / 60d);
        double finalLat = (Math.asin((Math.sin(Math.toRadians(start.getL())) * Math.cos(radianDistance)) +
                                      (Math.cos(Math.toRadians(start.getL())) * Math.sin(radianDistance) * Math.cos(Math.toRadians(bearing))))); 
        double finalLng = Math.toRadians(start.getG()) + Math.atan2(Math.sin(Math.toRadians(bearing)) * Math.sin(radianDistance) * Math.cos(Math.toRadians(start.getL())), 
                                                                    Math.cos(radianDistance) - Math.sin(Math.toRadians(start.getL())) * Math.sin(finalLat));
        finalLat = Math.toDegrees(finalLat);
        finalLng = Math.toDegrees(finalLng);
        
        reached = new GeoPoint(finalLat, finalLng);
        return reached;
      }
  
      private boolean isVisible(double l, double g)
      {
        boolean plot = true;
        if (chartPanel.getProjection() == ChartPanelInterface.GLOBE_VIEW)
        {
          if (!chartPanel.isTransparentGlobe() && chartPanel.isBehind(l, g - chartPanel.getGlobeViewLngOffset()))
            plot = false;
        }
        return plot;
      }
      
      @Override
      public boolean onEvent(EventObject eventObject, int i)
      {
        // TODO Implement this method
        return false;
      }
  
      @Override
      public String getMessForTooltip()
      {
        // TODO Implement this method
        return null;
      }
  
      @Override
      public boolean replaceMessForTooltip()
      {
        // TODO Implement this method
        return false;
      }
  
      @Override
      public void videoCompleted()
      {
        // TODO Implement this method
      }
  
      @Override
      public void videoFrameCompleted(Graphics graphics, Point point)
      {
        // TODO Implement this method
      }
  
      @Override
      public void zoomFactorHasChanged(double d)
      {
        // TODO Implement this method
      }
  
      @Override
      public void chartDDZ(double d, double d2, double d3, double d4)
      {
        // TODO Implement this method
      }
    };

  private JDesktopPane desktop = new ChartPanelDesktopPane();
  
  private static List<BackgroundWindow> bgwal = new ArrayList<BackgroundWindow>(1);
  
  private JInternalFrame sailfaxFrame    = null;
  private JInternalFrame spotFrame       = null;
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
  private JInternalFrame logToGpx        = null;
  private JInternalFrame logAnalyzer     = null;
  
  private transient Connection chartConnection = null;
//private Connection logConnection = null;
  
  private int TCPPort  = -1;
  private int UDPPort  = -1;
  private int HTTPPort = -1;
  private int RMIPort  = -1;
//private int GPSDPort = -1;
  private transient HTTPServer httpServer = null;
  private transient UDPWriter udpWriter   = null;
  private transient TCPWriter tcpWriter   = null;
//private transient GPSDWriter gpsdWriter = null;
  private RebroadcastPanel rebroadcastPanel = null;

  private static String NMEA_EOS = new String(new char[] {0x0A, 0x0D});
  
  private RebroadcastPopup popup = null;
  private List<DesktopUserExitInterface> userExitList = null;
  
  public DesktopFrame()
  {
    this(null);
  }
  public DesktopFrame(List<DesktopUserExitInterface> userExitList)
  {
    DesktopContext.getInstance().setDesktopVerbose("true".equals(System.getProperty("verbose", "false")));
    this.userExitList = userExitList;
//  NMEA_EOS = new String(new char[] {0x0A, 0x0D}); //(System.getProperty("os.name").contains("Windows")?NMEAParser.STANDARD_NMEA_EOS:NMEAParser.LINUX_NMEA_EOS);
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
  private final static int SPOT                 = 14;
  private final static int LOG_TO_GPX           = 15;
  private final static int LOG_ANALYZER         = 16;

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
    if (foregroundData.booleanValue())
      startDataGrabber();

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
    logToGPXMenuItem.setText("Log to GPX");
    logToGPXMenuItem.setToolTipText("Can be imported in OpenCPN, GoogleEarth, etc.");
    logAnalyzerMenuItem.setText("Log Analyzer");
    
    spotMenuItem.setText("SPOT Bulletins");
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
    
    foregroundDataMenuItem.setText("Show live wallpaper"); // "Show foreground data");
//  foregroundDataMenuItem.setToolTipText("<html>Shows current data <br><b>on top</b> of all windows.</html>");
    foregroundDataMenuItem.setSelected(foregroundData.booleanValue());
    
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
    logToGPXMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LOG_TO_GPX); } } );
    logAnalyzerMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LOG_ANALYZER); } } );

    starFinderMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(STARFINDER); } } );
    d2102StarFinderMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(STARFINDER_2012D); } } );
    sailFaxMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(SAILFAX); } } );
    spotMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(SPOT); } } );
    lunarMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LUNAR); } } );
    srMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(SR); } } );
    realTimeAlmanacMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(REAL_TIME_ALMANAC); } } );
    almanacMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(ALMANAC); } } );
    locatorMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(LOCATOR); } } );
    tideMenuItem.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(TIDES); } } );
    
    backGroundNMEARead.addActionListener(new ActionListener()  { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(READ_REBROADCAST); } } );
//  backGroundNMEAServer.addActionListener(new ActionListener()  { public void actionPerformed( ActionEvent ae ) { appRequest_ActionPerformed(RMI_NMEA_SERVER); } } );
    
    foregroundDataMenuItem.addActionListener(new ActionListener()  
    { 
      public void actionPerformed( ActionEvent ae ) 
      { 
        foregroundData = Boolean.valueOf(foregroundDataMenuItem.isSelected());
//      specialInternalFrame.setVisible(foregroundData.booleanValue());   
        if (foregroundData.booleanValue())
          startDataGrabber();
        repaint();
      } 
    });
    
    menuFileExit.setText( "Exit" );
    menuFileExit.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { fileExit_ActionPerformed( ae ); } } );
    menuHelp.setText( "Help" );
    menuHelpAbout.setText( "About" );
    menuHelpAbout.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpAbout_ActionPerformed( ae ); } } );
    menuHelpRebroadcast.setText("Rebroadcast help");
    menuHelpRebroadcast.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpRebroadcast_ActionPerformed( ae ); } } );
    
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
              if (popup == null)
                popup = new RebroadcastPopup();
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
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_SPOT_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      menuFile.add(spotMenuItem);
      nbMenuItem++;
//    menuFile.add(new JSeparator());
      separatorIsLast = false;
    }
    if (((Boolean)(ParamPanel.getData()[ParamData.USE_NMEA_APP][ParamPanel.PRM_VALUE])).booleanValue())
    {
      if (nbMenuItem > 0)
        menuFile.add(nmeaMenuItem);
      nmeaMenuItem.add(nmeaConsoleMenuItem);
      nmeaMenuItem.add(backGroundNMEARead);
//    nmeaMenuItem.add(backGroundNMEAServer);
      nmeaMenuItem.add(replayNmeaMenuItem);
      nmeaMenuItem.add(new JSeparator());
      nmeaMenuItem.add(logToGPXMenuItem);
      nmeaMenuItem.add(logAnalyzerMenuItem);
      
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
//  menuToolsFullScreen.setEnabled(false);
    
    menuTools.add(foregroundDataMenuItem);
    menuTools.add(new JSeparator());        

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
    menuHelp.add( menuHelpRebroadcast );
    
    menuBar.add( menuHelp );
    bottomPanel.add(statusBar, BorderLayout.WEST);
    bottomPanel.add(progressBar, BorderLayout.EAST);
    progressBar.setVisible(false);
    this.getContentPane().add( bottomPanel, BorderLayout.SOUTH );
    this.getContentPane().add( desktop, BorderLayout.CENTER );

    InternalFrameUI ifu = specialInternalFrame.getUI();
    ((BasicInternalFrameUI)ifu).setNorthPane(null);   // Remove the title bar   

    specialInternalFrame.setLocation(10, 10);
    specialInternalFrame.setSize(500, 500);
    specialInternalFrame.setOpaque(false);
    specialInternalFrame.setBorder(null);    // Remove the border
    Color bg = new Color(0f, 0f, 0f, 0f); // This color is transparent black
    specialInternalFrame.setBackground(bg);
    specialInternalFrame.getContentPane().setLayout(new BorderLayout());
    specialInternalFrame.getContentPane().add(new JPanel()
                                              {
                                                @SuppressWarnings("compatibility:-7001108031039269352")
                                                private final static long serialVersionUID = 1L;
                                                
                                                @Override
                                                protected void paintComponent(Graphics g)
                                                {
                                                  if (foregroundData.booleanValue())
                                                  {
                                                    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                                                                     RenderingHints.VALUE_TEXT_ANTIALIAS_ON);      
                                                    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                                                                     RenderingHints.VALUE_ANTIALIAS_ON);      
                                                    this.setOpaque(false);
                                                    g.setColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.LIVE_WALLPAPER_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
                                                    g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
                                                    
                                                    // Display the dynamic data here
                                                    boolean displayRose = false;
                                                    
                                                    if (displayRose)
                                                    {
                                                      Dimension dim = this.getSize();
                                                      double radius = (Math.min(dim.width, dim.height) - 10d) / 2d;
                                                      // Rose
                                                      g.setColor(Color.green); // was darkGray. TODO Preference
                                                      int graphicXOffset = 0;
                                                      int graphicYOffset = 0;
                                                      for (int i=0; i<360; i+= 5)
                                                      {
                                                        int x1 = (dim.width / 2) + (int)((radius - (i%45==0?20:10)) * Math.cos(Math.toRadians(i)));  
                                                        int y1 = (dim.height / 2) + (int)((radius - (i%45==0?20:10)) * Math.sin(Math.toRadians(i)));  
                                                        int x2 = (dim.width / 2) + (int)((radius) * Math.cos(Math.toRadians(i)));  
                                                        int y2 = (dim.height / 2) + (int)((radius) * Math.sin(Math.toRadians(i)));  
                                                        g.drawLine(x1 + graphicXOffset, y1 + graphicYOffset, x2 + graphicXOffset, y2 + graphicYOffset);
                                                      }
                                                    }
                                                    g.setColor(((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.LIVE_WALLPAPER_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor());
                                                 // g.setFont(g.getFont().deriveFont(Font.BOLD));
                                                    Font digiFont = null; // TODO Preference!!@!@@!
                                                    if (false)
                                                    {
                                                      try { digiFont = JumboDisplay.tryToLoadFont("ds-digi.ttf", null); }
                                                      catch (Exception ex) { System.err.println(ex.getMessage()); }
                                                    }
                                                    else
                                                    {
                                                      try { digiFont = new Font("Source Code Pro", 10, Font.PLAIN); }
                                                      catch (Exception ex) 
                                                      { 
                                                        try { digiFont = new Font("Courier New", 10, Font.PLAIN); }
                                                        catch (Exception ex2) 
                                                        { 
                                                          System.err.println(ex2.getMessage()); 
                                                        }
                                                      }
                                                    }
                                                    if (digiFont != null)
                                                      g.setFont(digiFont.deriveFont(Font.BOLD, 16f));
                                                    else
                                                      g.setFont(g.getFont().deriveFont(Font.BOLD));
                                                    int y = 20;
                                                    synchronized (grabbedData)
                                                    {
                                                      List<String> data = new ArrayList<String>(grabbedData); // Clone
                                                      for (String s : data)
                                                      {
                                                        g.drawString(s, 5, y);
                                                        y += (g.getFont().getSize() + 2);
                                                      }
                                                    }
                                                  }
                                                }                                                
                                              }, 
                                              BorderLayout.CENTER);    
//  specialInternalFrame.setVisible(foregroundData.booleanValue());
//  desktop.add(specialInternalFrame);
    
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
    SpotCtx.getInstance().addApplicationListener(new SpotEventListener()
      {
       public void internalFrameClosed() 
       {
         spotFrame.setVisible(false);
         spotMenuItem.setEnabled(true);
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
    NMEAContext.getInstance().addNMEAReaderListener(new NMEAReaderListener("Desktop", "Desktop Frame")
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
        public void internalTxFrameClosed() 
        {
          logToGpx.setVisible(false);
          logToGPXMenuItem.setEnabled(true);
        }
        public void internalAnalyzerFrameClosed() 
        {
          logAnalyzer.setVisible(false);
          logAnalyzerMenuItem.setEnabled(true);
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
          Thread load = new Thread("Spalsh Loader")
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
    
    if (((Boolean)(ParamPanel.getData()[ParamData.FULL_SCREEN_DESKTOP][ParamPanel.PRM_VALUE])).booleanValue())
      fullScreen();
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
        this.dispose(); // Mandatory!!!
        this.setUndecorated(false);
        this.setVisible(true);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
        this.setVisible(true);
      }
      menuToolsFullScreen.setText("Full Screen");
    }
    else
    {
      fullscreen = true;
      try
      {
        final JFrame instance = this;
        SwingUtilities.invokeLater(new Runnable()
         {
           public void run()
           {
             try
             {
               instance.setVisible(false);   
               instance.dispose(); // Important and Mandatory!
               int state = instance.getExtendedState();
               state |= Frame.MAXIMIZED_BOTH;
               instance.setExtendedState(state);      
               instance.setUndecorated(true);
//             instance.pack();
               instance.setVisible(true);
             }
             catch (Exception ex)
             {
               ex.printStackTrace();
               instance.setVisible(true);
             }
                                         
           }
         });
        menuToolsFullScreen.setText("Normal Screen");
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
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

  private transient NMEAReaderListener nmeaReaderListener = null;
  
  private void appRequest_ActionPerformed(int app)
  {
    final String channel = (ParamPanel.getData()[ParamData.NMEA_CHANNEL][ParamPanel.PRM_VALUE]).toString();

    final Dimension masterDim = desktop.getSize(); // this.getSize();
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
      case LOG_ANALYZER:
        logAnalyzer = new NMEAAnalyzerInternalFrame();
        logAnalyzer.setIconifiable(true);
        logAnalyzer.setClosable(true);
        logAnalyzer.setMaximizable(true);
        logAnalyzer.setResizable(true);
        desktop.add(logAnalyzer);
        logAnalyzer.setVisible(true);
        logAnalyzerMenuItem.setEnabled(false);
        centerFrame(masterDim, logAnalyzer);
        break;
      case LOG_TO_GPX:
        logToGpx = new NMEAtoGPXInternalFrame();
        logToGpx.setIconifiable(true);
        logToGpx.setClosable(true);
        logToGpx.setMaximizable(true);
        logToGpx.setResizable(true);
        desktop.add(logToGpx);
        logToGpx.setVisible(true);
        logToGPXMenuItem.setEnabled(false);
        centerFrame(masterDim, logToGpx);
        break;
      case SPOT:
        try
        {
          System.setProperty("airmail.location", (ParamPanel.getData()[ParamData.AIRMAIL_LOCATION][ParamPanel.PRM_VALUE]).toString());
          System.setProperty("airmail.id", (ParamPanel.getData()[ParamData.AIRMAIL_ID][ParamPanel.PRM_VALUE]).toString());
          spotFrame = new SPOTInternalFrame(); 
          spotFrame.setIconifiable(true);
          spotFrame.setClosable(true);
          spotFrame.setMaximizable(true);
          spotFrame.setResizable(true);
          desktop.add(spotFrame);
          spotFrame.setVisible(true);
          spotMenuItem.setEnabled(false);
          centerFrame(masterDim, spotFrame);
        }
        catch (NoClassDefFoundError ncdfe)
        {
          System.out.println("No SPOTInternalFrame");
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
   //     Constructor constr = c.getConstructor(new Class<?>[] { Connection.class });
          Constructor constr = c.getConstructor(Connection.class);
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

        System.setProperty("max.analog.bsp", (ParamPanel.getData()[ParamData.MAX_ANALOG_BSP][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("max.analog.tws", (ParamPanel.getData()[ParamData.MAX_ANALOG_TWS][ParamPanel.PRM_VALUE]).toString());

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
        System.setProperty("max.analog.bsp", (ParamPanel.getData()[ParamData.MAX_ANALOG_BSP][ParamPanel.PRM_VALUE]).toString());
        System.setProperty("max.analog.tws", (ParamPanel.getData()[ParamData.MAX_ANALOG_TWS][ParamPanel.PRM_VALUE]).toString());
        if ("no".equals(System.getProperty("headless", "no")) && backGroundNMEARead.isSelected()) // Already reading NMEA port
        {
          String message = "NMEA Port is being read.\n" +
                           "Please stop reading it before replaying NMEA Data.";
          JOptionPane.showMessageDialog(this, message, "NMEA Replay", JOptionPane.WARNING_MESSAGE);
        }
        else
        {
          String nmeaProps = System.getProperty("nmea.config.file", "nmea-config.properties");
          String simul = "";
          if ("no".equals(System.getProperty("headless", "no")))
            simul = coreutilities.Utilities.chooseFile(JFileChooser.FILES_ONLY,
                                                       "nmea",
                                                       "NMEA Data",
                                                       "Load NMEA Data",
                                                       "Load");
          else
            simul = System.getProperty("logged.nmea.data", "");
          
          if (simul.trim().length() > 0)
          {
            nmeaReaderListener = new NMEAReaderListener(LISTENER_FAMILY, "Desktop Frame (2)")
            {
              public void manageNMEAString(String str)
              {
                if (str == null)
                  return;
                
                String prefix = "File:";
                String message = str;
                if (TCPPort != -1 && str != null && !DesktopContext.getInstance().notToBeRebroadcasted(str))
                {
                  if (DesktopContext.getInstance().isDesktopVerbose())
                    System.out.println("Rebroadcasting on TCP Port " + TCPPort + ": [" + str + "]");
                  if (tcpWriter != null)
                  {
                    tcpWriter.write((DesktopUtilities.superTrim(str) + getNMEA_EOS()).getBytes());
                    prefix += (" => TCP " + TCPPort);
                  }
                }
                if (UDPPort != -1 && str != null && !DesktopContext.getInstance().notToBeRebroadcasted(str))
                {
                  if (DesktopContext.getInstance().isDesktopVerbose())
                    System.out.println("Rebroadcasting on UDP Port " + UDPPort + ":" + str);
                  udpWriter.write((DesktopUtilities.superTrim(str) + getNMEA_EOS()).getBytes());
                  prefix += (" => UDP " + rebroadcastPanel.udpHost() + ":" + UDPPort);
                }
//                if (GPSDPort != -1 && str != null)
//                {
//                  if (rebroadcastVerbose)
//                    System.out.println("Rebroadcasting on GPSD Port " + GPSDPort + ":" + str);
//                  gpsdWriter.write(DesktopUtilities.superTrim(str).getBytes());
//                  prefix += (" => GPSD " + GPSDPort);
//                }
                if (HTTPPort != -1 && str != null)
                {
                  if (DesktopContext.getInstance().isDesktopVerbose())
                    System.out.println("Rebroadcasting on HTTP Port " + HTTPPort + ":" + str);
                  prefix += (" => " + rebroadcastPanel.getHttpFlavor() + "/HTTP " + HTTPPort);
                }
                if (RMIPort != -1 && str != null)
                {
                  if (DesktopContext.getInstance().isDesktopVerbose())
                    System.out.println("Rebroadcasting on RMI Port " + RMIPort + ":" + str);
                  prefix += (" => RMI " + RMIPort);
                }
                // Display NMEA String in the status bar
                setStatus(prefix + " " + message);
              }
            };
            NMEAContext.getInstance().addNMEAReaderListener(nmeaReaderListener);       

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
            if ("no".equals(System.getProperty("headless", "no")))
            {
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
        if ("yes".equals(System.getProperty("headless", "no")) || backGroundNMEARead.isSelected())
        {          
          nmeaReaderListener = new NMEAReaderListener(LISTENER_FAMILY, "Desktop Frame (3)")
          {
            public void manageNMEAString(String str) // Receives, Rebroadcasts
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
              else if (channel.startsWith("XML"))
                prefix += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString());
              
              // Re-broadcast ?
              // Add condition set by the Admin HTTP Port...
              if (TCPPort != -1)
              {
                if (tcpWriter != null && DesktopContext.getInstance().isTcpRebroadcastEnable() && !DesktopContext.getInstance().notToBeRebroadcasted(str))
                {
                  if (DesktopContext.getInstance().isDesktopVerbose())
                    System.out.println("Rebroadcasting on TCP Port " + TCPPort + ":" + str);
                  tcpWriter.write((DesktopUtilities.superTrim(str) + getNMEA_EOS()).getBytes());
                  prefix += (" => TCP " + TCPPort);
                }
              }
              if (UDPPort != -1 && udpWriter != null && DesktopContext.getInstance().isUdpRebroadcastEnable() && !DesktopContext.getInstance().notToBeRebroadcasted(str))
              {
                if (DesktopContext.getInstance().isDesktopVerbose())
                  System.out.println("Rebroadcasting on UDP Port " + UDPPort + ":" + str);
                udpWriter.write((DesktopUtilities.superTrim(str) + getNMEA_EOS()).getBytes());
                prefix += (" => UDP " + rebroadcastPanel.udpHost() + ":" + UDPPort);
              }
//              if (GPSDPort != -1)
//              {
//                if (rebroadcastVerbose)
//                  System.out.println("Rebroadcasting on GPSd Port " + GPSDPort + ":" + str);
//                if (gpsdWriter != null)
//                {
//                  gpsdWriter.write((DesktopUtilities.superTrim(str) + "\n").getBytes());
//                  prefix += (" => GPSd " + GPSDPort);
//                }
//              }
              if (HTTPPort != -1)
              {
                if (DesktopContext.getInstance().isDesktopVerbose())
                  System.out.println("Rebroadcasting on HTTP Port " + HTTPPort + ":" + str);
                prefix += (" => " + rebroadcastPanel.getHttpFlavor() + "/HTTP " + HTTPPort);
              }
              if (RMIPort != -1)
              {
                if (DesktopContext.getInstance().isDesktopVerbose())
                  System.out.println("Rebroadcasting on RMI Port " + RMIPort + ":" + str);
                prefix += (" => RMI " + RMIPort);
              }
              String message = prefix + ":" + str;
              // Display NMEA String in the status bar
              setStatus(message);
            }
          };
          NMEAContext.getInstance().addNMEAReaderListener(nmeaReaderListener);       
          String comPort = (ParamPanel.getData()[ParamData.NMEA_SERIAL_PORT][ParamPanel.PRM_VALUE]).toString();
          String tcpPort = (ParamPanel.getData()[ParamData.NMEA_TCP_PORT][ParamPanel.PRM_VALUE]).toString();
          String udpPort = (ParamPanel.getData()[ParamData.NMEA_UDP_PORT][ParamPanel.PRM_VALUE]).toString();
          String rmiPort = (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString();
// TODO   String gpsdPort = (ParamPanel.getData()[ParamData.NMEA_RMI_PORT][ParamPanel.PRM_VALUE]).toString();
// TODO XML over HTTP, in input          
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
          } // TODO XML over HTTP
          else
          {
            // ?... Serial
            System.out.println("Unmanaged (for now) channel [" + channel + "]");
          }
          
          Utils.readNMEAParameters(); // default calibration values, nmea-prms.properties
          // Init dev curve
          String deviationFileName = (String) NMEAContext.getInstance().getCache().get(NMEADataCache.DEVIATION_FILE);
          NMEAContext.getInstance().setDeviation(Utils.loadDeviationCurve(deviationFileName));

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
          else if (channel.startsWith("XML"))
            mess += ((ParamPanel.getData()[ParamData.NMEA_HOST_NAME][ParamPanel.PRM_VALUE]).toString() + ":" + (ParamPanel.getData()[ParamData.NMEA_HTTP_PORT][ParamPanel.PRM_VALUE]).toString());

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
        calculation.AstroComputer.setDeltaT(((Double)(ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE])).doubleValue());
//      System.setProperty("deltaT", (ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE]).toString());
        Thread tideThread = new Thread("Tide Thread")
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
  
  void helpRebroadcast_ActionPerformed(ActionEvent e)
  {
    showHelp();
  }

  private void showHelp()
  {
    JPanel helpPanel = new JPanel();
    helpPanel.setPreferredSize(new Dimension(500, 500));
    JEditorPane jEditorPane = new JEditorPane();
    JScrollPane jScrollPane = new JScrollPane();
    helpPanel.setLayout(new BorderLayout());
    jEditorPane.setEditable(false);
    jEditorPane.setFocusable(false);
    jEditorPane.setFont(new Font("Verdana", 0, 10));
    jEditorPane.setBackground(Color.lightGray);
    jScrollPane.getViewport().add(jEditorPane, null);

    try
    {
      jEditorPane.setPage(this.getClass().getResource("rebroadcast.help.html"));
      jEditorPane.repaint();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    helpPanel.add(jScrollPane, BorderLayout.CENTER);
    JOptionPane.showMessageDialog(this, helpPanel, "NMEA Rebroadcasting Help", JOptionPane.PLAIN_MESSAGE); 
  }

  private void this_windowClosing(WindowEvent e)
  {
    // Close user exits
    if (userExitList != null)
    {
      if (userExitList != null && userExitList.size() > 0)
      {
        for (DesktopUserExitInterface ue : userExitList)
        {
          System.out.println("Stopping userExit " + ue.getClass().getName());
          ue.stop();
        }
      }
    }
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
//    else if (fr instanceof SPOTInternalFrame)
//    {
//      Dimension consoleDim = new Dimension(950, 650);
//      fr.setSize(consoleDim);
//      fr.setLocation(x, y);
//    }
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
  
  private void startDataGrabber()
  {
    Thread dataGrabber = new Thread("Data Grabber")
      {
        public void run()
        {
          String degSymbol = "\272"; // " "
          try
          {
            while (foregroundData.booleanValue())
            {
//            System.out.println((">>> DEBUG >>> DataGrabber - top of the loop"));
              synchronized (grabbedData)
              {
                grabbedData = new ArrayList<String>();
                // Time
                Date ut = new Date(); // TimeUtil.getGMT();
                grabbedData.add("System:" + SDF.format(ut));
                double offset = TimeUtil.getGMTOffset();
                String strOffset = Integer.toString((int)offset);
                if (offset > 0) strOffset = "+" + strOffset;
                grabbedData.add("UTC Offset:" + strOffset);
                NMEADataCache ndc = NMEAContext.getInstance().getCache();
                // NMEA Data
                String str = "";
                try 
                { 
                  synchronized (ndc) { str += ("L:" + lpad(GeomUtil.decToSex(((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lat, GeomUtil.SWING, GeomUtil.NS), " ", 12)); }
                } 
                catch (Exception ex) 
                { 
                  System.err.println(ex.getLocalizedMessage());
                  str += "-"; 
                }
                grabbedData.add(str);            
                str = "";
                double g = 0;
                try 
                { 
                  synchronized (ndc) { g = ((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lng; }
                  str += ("G:" + lpad(GeomUtil.decToSex(g, GeomUtil.SWING, GeomUtil.EW), " " , 12)); 
                } 
                catch (Exception ex) 
                { 
                  System.out.println(ex.getLocalizedMessage());
                  str += "-"; 
                }
                grabbedData.add(str);            
                str = "COG:";
                try 
                { 
                  synchronized (ndc) { str += Integer.toString((int)((Angle360)ndc.get(NMEADataCache.COG, true)).getValue()); }
                } 
                catch (Exception ex) 
                { 
                  System.err.println("COG in Cache:" + ex.getLocalizedMessage()); 
                  str += "-"; 
                }
                str += (degSymbol + " SOG:");
                try 
                { 
                  synchronized (ndc) { str += DF22.format(((Speed)ndc.get(NMEADataCache.SOG, true)).getDoubleValue()); }
                } 
                catch (Exception ex) { System.err.println("SOG in Cache"); hsString += "-"; } // TODO A Format
                str += " kts";
                grabbedData.add(str);            
                
                str = "xx:xx:xx";            
                try 
                { 
                  try 
                  { 
                    UTCDate utcDate = null;
                    synchronized (ndc) { utcDate = (UTCDate)ndc.get(NMEADataCache.GPS_DATE_TIME, true); }
                    str = "GPS Time: " + SDF.format(utcDate.getValue());
                    grabbedData.add(str);            
                  } 
                  catch (Exception ignore) 
                  {
                    System.err.println(ignore.getLocalizedMessage());
                  }
                  long time = 0L;
                  try 
                  { 
                    synchronized (ndc) { time = ((UTCTime)ndc.get(NMEADataCache.GPS_TIME, true)).getValue().getTime(); }
                  } 
                  catch (NullPointerException npe) { System.err.println("NPE for GPS Time..."); }
                  catch (Exception oops) { oops.printStackTrace(); }
                //          System.out.println("[time:" + time + ", g:" + g + "]");
                  offset = (g / 15d) * 3600d * 1000d; // in milliseconds
                  time += offset;
                  str = "  " + SDF2.format(new Date(time));
                  grabbedData.add(str);         
                  str = "Solar Offset:" + decimalHoursToHMS(offset / (3600 * 1000));                
                  grabbedData.add(str);         
                  if (g != 0)
                  {
                    // Try sun rise & set from current position
                    try 
                    {
                      double l = 0;
                      UTCDate utcDate = null;
                      synchronized (ndc) 
                      { 
                        l = ((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lat; 
                        g = ((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lng; 
                        utcDate = (UTCDate)ndc.get(NMEADataCache.GPS_DATE_TIME, true); 
                      }
                      // Get dayligh duration the day before
                      Calendar dayBefore = Calendar.getInstance(TimeZone.getTimeZone("etc/UTC"));
                      dayBefore.setTimeInMillis(utcDate.getValue().getTime());
                      dayBefore.add(Calendar.DATE, -1);
                      AstroComputer.setDateTime(dayBefore.get(Calendar.YEAR), 
                                                dayBefore.get(Calendar.MONTH) + 1, 
                                                dayBefore.get(Calendar.DAY_OF_MONTH), 
                                                dayBefore.get(Calendar.HOUR_OF_DAY), // 12 - (int)Math.round(AstroComputer.getTimeZoneOffsetInHours(TimeZone.getTimeZone(ts.getTimeZone()))), 
                                                dayBefore.get(Calendar.MINUTE), 
                                                dayBefore.get(Calendar.SECOND));
                      AstroComputer.calculate();
                      double[] rsSunYesterday  = AstroComputer.sunRiseAndSet(l, g);
                      double daylightYesterday = (rsSunYesterday[AstroComputer.UTC_SET_IDX] - rsSunYesterday[AstroComputer.UTC_RISE_IDX]);
    
                      Calendar today = Calendar.getInstance(TimeZone.getTimeZone("etc/UTC"));
                      today.setTimeInMillis(utcDate.getValue().getTime());
                      AstroComputer.setDateTime(today.get(Calendar.YEAR), 
                                                today.get(Calendar.MONTH) + 1, 
                                                today.get(Calendar.DAY_OF_MONTH), 
                                                today.get(Calendar.HOUR_OF_DAY), // 12 - (int)Math.round(AstroComputer.getTimeZoneOffsetInHours(TimeZone.getTimeZone(ts.getTimeZone()))), 
                                                today.get(Calendar.MINUTE), 
                                                today.get(Calendar.SECOND));
                      AstroComputer.calculate();
                      double sunD = AstroComputer.getSunDecl();
                      double[] rsSunToday  = AstroComputer.sunRiseAndSet(l, g);
                      double daylightToday = (rsSunToday[AstroComputer.UTC_SET_IDX] - rsSunToday[AstroComputer.UTC_RISE_IDX]);
                      double deltaDaylight = daylightToday - daylightYesterday;           
                      SDF.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
                      Calendar sunRise = null;
                      Calendar sunSet = null;
                      Calendar now = GregorianCalendar.getInstance();
                      now.setTimeInMillis(utcDate.getValue().getTime()); // From the GPS
  
                      sunRise = new GregorianCalendar();
                      sunRise.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
                      sunRise.set(Calendar.YEAR, now.get(Calendar.YEAR));
                      sunRise.set(Calendar.MONTH, now.get(Calendar.MONTH));
                      sunRise.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
                      sunRise.set(Calendar.SECOND, 0);
                                    
                      double r = rsSunToday[AstroComputer.UTC_RISE_IDX] /* + Utils.daylightOffset(sunRise) */ + AstroComputer.getTimeZoneOffsetInHours(TimeZone.getTimeZone("etc/UTC" /*ts.getTimeZone()*/), sunRise.getTime());
                      int min = (int)((r - ((int)r)) * 60);
                      sunRise.set(Calendar.MINUTE, min);
                      sunRise.set(Calendar.HOUR_OF_DAY, (int)r);
                      
                      sunSet = new GregorianCalendar();
                      sunSet.setTimeZone(TimeZone.getTimeZone("etc/UTC"));
                      sunSet.set(Calendar.YEAR, now.get(Calendar.YEAR));
                      sunSet.set(Calendar.MONTH, now.get(Calendar.MONTH));
                      sunSet.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
                      sunSet.set(Calendar.SECOND, 0);
                      r = rsSunToday[AstroComputer.UTC_SET_IDX] /* + Utils.daylightOffset(sunSet) */ + AstroComputer.getTimeZoneOffsetInHours(TimeZone.getTimeZone("etc/UTC"/*ts.getTimeZone()*/), sunSet.getTime());
                      min = (int)((r - ((int)r)) * 60);
                      sunSet.set(Calendar.MINUTE, min);
                      sunSet.set(Calendar.HOUR_OF_DAY, (int)r);
  
                      long daylight = (sunSet.getTimeInMillis() - sunRise.getTimeInMillis()) / 1000L;
    
                      if (!Double.isNaN(rsSunToday[AstroComputer.UTC_RISE_IDX]))
                      {
                        double solarOffset = (g / 15d) * 3600d * 1000d; // in milliseconds
                        time += solarOffset;
                        SDF2.format(new Date(time));
  
                        str = "Sun Rise    : " + SUN_RISE_SET_SDF.format(sunRise.getTime());
                        grabbedData.add(str);           
                        str = "              Z:" + DF3.format(rsSunToday[AstroComputer.RISE_Z_IDX]) + degSymbol;
                        grabbedData.add(str);           
  //                    System.out.println("Added:[" + str + "]");
                        str = "  " + SDF3.format(new Date(sunRise.getTime().getTime() + (long)solarOffset));
                        grabbedData.add(str);           
  //                    System.out.println("Added:[" + str + "]");
  
                        str = "Sun Set     : " + SUN_RISE_SET_SDF.format(sunSet.getTime());
                        grabbedData.add(str);           
                        str = "              Z:" + DF3.format(rsSunToday[AstroComputer.SET_Z_IDX]) + degSymbol;
                        grabbedData.add(str);           
  //                    System.out.println("Added:[" + str + "]");
                        str = "  " + SDF3.format(new Date(sunSet.getTime().getTime() + (long)solarOffset));
                        grabbedData.add(str);           
  //                    System.out.println("Added:[" + str + "]");
                        if (daylightToday > 0)
                        {
  //                      System.out.println("Daylight:" + daylightToday);
                          str = "Daylight    : " + DF2.format(daylight / 3600) + "h " + DF2.format((daylight % 3600) / 60L) + "m";
                          grabbedData.add(str);           
  //                      System.out.println("Added:[" + str + "]");
                          str = " Delta      : " + decimalHoursToHMS(deltaDaylight);
                          grabbedData.add(str);           
  //                      System.out.println("Added:[" + str + "]");
                          double dz = Math.abs(l - sunD);
                          double maxSunAlt = (90 - dz);
                          str = "Sun Max Alt : " + ((int)maxSunAlt) + degSymbol + DF2.format((maxSunAlt - ((int)maxSunAlt)) * 60) + "'";
                          grabbedData.add(str);           
                        }
                      }
                    } 
                    catch (Exception ex) 
                    { 
                      System.err.println(ex.getLocalizedMessage());
                      str += "-"; 
                      grabbedData.add(str);            
                    }
                  }
                }
                catch (Exception ex)
                {
                  ex.printStackTrace();
                }
                // Sun & Moon Altitude & Azimuth
                if (true) // TODO Option
                {
                  // Second prm: use damping
                  UTCDate utcDate = null;
                  GeoPos gpsPos = null;
                  synchronized (ndc) 
                  {
                    utcDate = (UTCDate)ndc.get(NMEADataCache.GPS_DATE_TIME, false);
                    gpsPos = (GeoPos)ndc.get(NMEADataCache.POSITION, false); 
                  }
                  if (utcDate != null && gpsPos != null)
                  {
                    Date date = utcDate.getValue();
                  
                    double deltaT = ((Double)(ParamPanel.getData()[ParamData.DELTA_T][ParamPanel.PRM_VALUE])).doubleValue();
                    GeoPoint gp = new GeoPoint(gpsPos.lat, gpsPos.lng);
  
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("etc/UTC"));
                    cal.setTime(date);
                    
                    AlmanacComputer.calculate(cal.get(Calendar.YEAR), 
                                              cal.get(Calendar.MONTH) + 1, 
                                              cal.get(Calendar.DAY_OF_MONTH), 
                                              cal.get(Calendar.HOUR_OF_DAY), 
                                              cal.get(Calendar.MINUTE), 
                                              cal.get(Calendar.SECOND), 
                                              deltaT);
                    SightReductionUtil sru = new SightReductionUtil();
                    
                    sru.setL(gp.getL());
                    sru.setG(gp.getG());
                    
                    double ghaSun = 0d, ghaMoon = 0d;
                    double decSun = 0d, decMoon = 0d;
                    
                    ghaSun = Context.GHAsun;
                    decSun = Context.DECsun;
                    
                    sunGHA = ghaSun;
                    sunD = decSun;
  
                    ghaMoon = Context.GHAmoon;
                    decMoon = Context.DECmoon;
                    
                    sru.setAHG(ghaSun);
                    sru.setD(decSun);    
                    sru.calculate();     
                    
                    Double heSun = sru.getHe();
                    Double zSun  = sru.getZ();
                    sunAlt = heSun.doubleValue();
                    sunZ = zSun.doubleValue();
                    
                    sru.setAHG(ghaMoon);
                    sru.setD(decMoon);    
                    sru.calculate();     
                    
                    Double heMoon = sru.getHe();
                    Double zMoon  = sru.getZ();
                    moonAlt = heMoon.doubleValue();
                    moonZ = zMoon.doubleValue();
                    
  //                printout("GHA:" + GeomUtil.decToSex(ghaSun, GeomUtil.SWING, GeomUtil.NONE));
  //                printout("Dec:" + GeomUtil.decToSex(decSun, GeomUtil.SWING, GeomUtil.NS));
  
                    if (heSun.doubleValue() > 0)
                    {
                      grabbedData.add("Sun Alt:" + GeomUtil.decToSex(heSun, GeomUtil.SWING, GeomUtil.NONE)); // + " (" + heSun + ")");
                      grabbedData.add("Sun Z  :" + GeomUtil.decToSex(zSun, GeomUtil.SWING, GeomUtil.NONE));
                    }
                    else
                      grabbedData.add("Sun under the horizon (" + GeomUtil.decToSex(heSun, GeomUtil.SWING, GeomUtil.NONE) + ")");            
                    
                    if (heMoon.doubleValue() > 0)
                    {
                      grabbedData.add("Moon Alt:" + GeomUtil.decToSex(heMoon, GeomUtil.SWING, GeomUtil.NONE)); // + " (" + heMoon + ")");
                      grabbedData.add("Moon Z  :" + GeomUtil.decToSex(zMoon, GeomUtil.SWING, GeomUtil.NONE));
                    }
                    else
                      grabbedData.add("Moon under the horizon (" + GeomUtil.decToSex(heMoon, GeomUtil.SWING, GeomUtil.NONE) + ")");            
                  }
                }
                double aws = 0d;
                int awa    = 0;
                double bsp = 0d;
                int hdg    = 0;
                double bl = 0d; // Big Log
                double sl = 0d; // Small log
                double depth = 0d;
                double temp  = 0d;
                double atemp  = Double.MIN_VALUE;
                double curSpeed = 0d;
                int curDir = 0;
                int currentTimeBuffer = 0;
                double bpress  = Double.MIN_VALUE;
                synchronized (ndc) 
                {
                  try { aws = ((Speed)ndc.get(NMEADataCache.AWS, true)).getDoubleValue(); } catch (Exception ex) {}
                  try { awa = (int)((Angle180)ndc.get(NMEADataCache.AWA, true)).getDoubleValue(); } catch (Exception ex) {}
                  try { bsp = ((Speed)ndc.get(NMEADataCache.BSP, true)).getDoubleValue(); } catch (Exception ex) {}
                  try { hdg = (int)((Angle360)ndc.get(NMEADataCache.HDG_TRUE, true)).getDoubleValue(); } catch (Exception ex) {}
                  
                  try { bl = ((Distance)ndc.get(NMEADataCache.LOG, true)).getDoubleValue(); } catch (Exception ex) {}          
                  try { sl = ((Distance)ndc.get(NMEADataCache.DAILY_LOG, true)).getDoubleValue(); } catch (Exception ex) {}     
                  try { depth = ((Depth)ndc.get(NMEADataCache.DBT, true)).getDoubleValue(); } catch (Exception ex) {}
                  try { temp = ((Temperature)ndc.get(NMEADataCache.WATER_TEMP, true)).getValue(); } catch (Exception ex) {}
                  try { atemp = ((Temperature)ndc.get(NMEADataCache.AIR_TEMP, true)).getValue(); } catch (Exception ex) {}              
                  try { bpress = ((Pressure)ndc.get(NMEADataCache.BARO_PRESS, true)).getValue(); } catch (Exception ex) {}              
    
                  try
                  {
                    Current current = (Current)ndc.get(NMEADataCache.VDR_CURRENT, true);
                    if (current == null)
                    {
                      Map<Long, NMEADataCache.CurrentDefinition> currentMap = 
                                          ((Map<Long, NMEADataCache.CurrentDefinition>)ndc.get(NMEADataCache.CALCULATED_CURRENT));  //.put(bufferLength, new NMEADataCache.CurrentDefinition(bufferLength, new Speed(speed), new Angle360(dir)));
                      Set<Long> keys = currentMap.keySet();
                      if (keys.size() != 1)
                        System.out.println("1 - Nb entry(ies) in Calculated Current Map:" + keys.size());
                      for (Long l : keys)
                      {
                        int tbl = (int)(l / (60 * 1000));
                        if (tbl > currentTimeBuffer) // Take the bigger one.
                        {
                          currentTimeBuffer = tbl;
                          curSpeed = currentMap.get(l).getSpeed().getValue();
                          curDir = (int)Math.round(currentMap.get(l).getDirection().getValue());
                        }
                      }
                    }
                    else
                    {
                      curSpeed = current.speed;
                      curDir = current.angle;
                    }
                  }
                  catch (Exception ignore) {}
                }
                grabbedData.add("AWS:" + DF22.format(aws) + " kts, AWA:" + Integer.toString(awa) + degSymbol);
                grabbedData.add("BSP:" + DF22.format(bsp) + " kts");
                grabbedData.add("HDG:" + Integer.toString(hdg % 360) + degSymbol);
                grabbedData.add("Log: " + DF22.format(bl) + " nm, " + DF22.format(sl) + " nm");
                grabbedData.add("DBT: " + DF22.format(depth) + " m");
                grabbedData.add("Water Temp   : " + DF22.format(temp) + degSymbol + "C");
                if (atemp != Double.MIN_VALUE)
                  grabbedData.add("Air Temp     : " + DF22.format(atemp) + degSymbol + "C");
                if (bpress != Double.MIN_VALUE)
                  grabbedData.add("Baro Pressure: " + DF31.format(bpress) + " hPa");
                if (currentTimeBuffer > 0)
                {
                  grabbedData.add("Current Calculated on " + currentTimeBuffer + " min.");
                  grabbedData.add("  Speed:" + DF22.format(curSpeed) + " kts");
                  grabbedData.add("    Dir:" + Integer.toString(curDir % 360) + degSymbol);
                }
                              
  //            specialInternalFrame.repaint();
//              System.out.println("... >>> DEBUG >>> Desktop.repaint");
                desktop.repaint();
//                try
//                {
//                  SwingUtilities.invokeAndWait(new Runnable()
//                    {
//                      public void run()
//                      {
//                        desktop.repaint();
//                      }
//                    });
//                }
//                catch (InvocationTargetException ite)
//                {
//                  ite.printStackTrace();
//                }
//                catch (InterruptedException ie)
//                {
//                  ie.printStackTrace();
//                }
                try { Thread.sleep(1000L); } catch (Exception ex) {} 
              }
            }
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
       // System.out.println("DataGrabber thread terminated.");
        }
      };
    dataGrabber.start();
  }
  
  private String decimalHoursToHMS(double diff)
  {
    double dh = Math.abs(diff);
    String s = "";
    if (dh >= 1)
      s += (DF2.format((int)dh) + "h ");
    double remainder = dh - ((int)dh);
    double minutes = remainder * 60;
    if (s.trim().length() > 0 || minutes >= 1)
      s += (DF2.format((int)minutes) + "m ");
    remainder = minutes - (int)minutes;
    double seconds = remainder * 60;
    s += (DF2.format((int)seconds) + "s");
    if (diff < 0)
      s = "- " + s;
    else
      s = "+ " + s;
    return s.trim();
  }
  
  public static String getNMEA_EOS()
  {
    return NMEA_EOS;
  }

  private class TimeThread extends Thread
  {
    public void run()
    {
//    System.out.println("Starting timer");
      while (getBGWinByTitle(TIME_BG_WINDOW_TITLE).isDisplayBGWindow())
      {
        Date ut = new Date(); // TimeUtil.getGMT();
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
        NMEADataCache ndc = NMEAContext.getInstance().getCache();
        try
        {
          double g = 0d;
          
          posString = "";
          try 
          { 
//          posString += ("L:" + GeomUtil.decToSex(NMEACache.getInstance().getLat(), GeomUtil.SWING, GeomUtil.NS)); 
            synchronized (ndc) { posString += ("L:" + GeomUtil.decToSex(((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lat, GeomUtil.SWING, GeomUtil.NS)); }
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
            synchronized (ndc) { g = ((GeoPos)ndc.get(NMEADataCache.POSITION, true)).lng; }
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
            synchronized (ndc) { hsString += Integer.toString((int)((Angle360)ndc.get(NMEADataCache.COG, true)).getValue()); }
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
            synchronized (ndc) { hsString += DF22.format(((Speed)ndc.get(NMEADataCache.SOG, true)).getDoubleValue()); }
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
              synchronized (ndc) { gpsTimeString = SDF.format(((UTCDate)ndc.get(NMEADataCache.GPS_DATE_TIME, true)).getValue()); }
            } 
            catch (Exception ignore) 
            {
              System.err.println(ignore.getLocalizedMessage());
            }
            long time = 0L;
            try 
            { 
//            time = NMEACache.getInstance().getGpsDate().getTime(); 
              synchronized (ndc) { time = ((UTCTime)ndc.get(NMEADataCache.GPS_TIME, true)).getValue().getTime(); }
            } 
            catch (NullPointerException npe) { System.err.println("NPE for GPS Time..."); }
            catch (Exception oops) { oops.printStackTrace(); }
//          System.out.println("[time:" + time + ", g:" + g + "]");
            double offset = (g / 15d) * 3600d * 1000d; // in milliseconds
            time += offset;
            solarTime = "\n" + SDF2.format(new Date(time)) + "\nSolar Offset:" + decimalHoursToHMS(offset / (3600 * 1000));
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
          synchronized (ndc) 
          { 
            double aws = 0d;
            try { aws = ((Speed)ndc.get(NMEADataCache.AWS, true)).getDoubleValue(); } catch (Exception ex) {}
            int awa    = 0;
            try { awa = (int)((Angle180)ndc.get(NMEADataCache.AWA, true)).getDoubleValue(); } catch (Exception ex) {}
            double bsp = 0d;
            try { bsp = ((Speed)ndc.get(NMEADataCache.BSP, true)).getDoubleValue(); } catch (Exception ex) {}
            int hdg    = 0;
            try { hdg = (int)((Angle360)ndc.get(NMEADataCache.HDG_TRUE, true)).getDoubleValue(); } catch (Exception ex) {}
            
            double bl = 0d;
            try { bl = ((Distance)ndc.get(NMEADataCache.LOG, true)).getDoubleValue(); } catch (Exception ex) {}          
            double sl = 0d;
            try { sl = ((Distance)ndc.get(NMEADataCache.DAILY_LOG, true)).getDoubleValue(); } catch (Exception ex) {}     
            double depth = 0d;
            try { depth = ((Depth)ndc.get(NMEADataCache.DBT, true)).getDoubleValue(); } catch (Exception ex) {}
            double temp  = 0d;
            try { temp = ((Temperature)ndc.get(NMEADataCache.WATER_TEMP, true)).getValue(); } catch (Exception ex) {}
            
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
              GeoPos pos = ((GeoPos)ndc.get(NMEADataCache.POSITION, true));
              UTCDate utcDate = (UTCDate)ndc.get(NMEADataCache.GPS_DATE_TIME, true);
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
  
  public static String lpad(String str, String with, int len)
  {
    while (str.length() < len)
      str = with + str;
    return str;
  }
  
  public static String rpad(String str, String with, int len)
  {
    while (str.length() < len)
      str += with;
    return str;
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
//            solarTime = "\n" + SDF2.format(new Date(time));
              solarTime = "\n" + SDF2.format(new Date(time)) + "\nSolar Offset:" + decimalHoursToHMS(offset / (3600 * 1000));

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
    
    String string = "123\27245.67'";
    System.out.println(string);
    System.out.println(string.replace('\272', ' '));
  }
  
  private class RebroadcastPopup extends JPopupMenu
                         implements ActionListener,
                                    PopupMenuListener
  {
    private JMenuItem rebroadcast;
    private JMenuItem html5Console;

    private final static String REBROADCAST   = "Re-broadcast...";
    private final static String HTML5_CONSOLE = "HTML5 Console";

    public RebroadcastPopup()
    {
      super();
      rebroadcast = new JMenuItem(REBROADCAST);
      this.add(rebroadcast);
      rebroadcast.addActionListener(this);
      html5Console = new JMenuItem(HTML5_CONSOLE);
      this.add(html5Console);
      html5Console.addActionListener(this);
      html5Console.setEnabled(false);
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
            try
            {
              if (rebroadcastPanel.getHttpFlavor().equals("XML"))
              {
                httpServer = new HTTPServer(new String[] { "-verbose=" + (rebroadcastPanel.httpVerbose()?"y":"n"), "-fmt=xml" }, null, null); 
                html5Console.setEnabled(true);
              }
              if (rebroadcastPanel.getHttpFlavor().equals("json"))
                httpServer = new HTTPServer(new String[] { "-verbose=" + (rebroadcastPanel.httpVerbose()?"y":"n"), "-fmt=json" }, null, null); 
            }
            catch (Exception ex)
            {
              ex.printStackTrace();              
            }
            // Remind the URL of the html console (in the clipboard)
            String consoleURL = "http://localhost:" + Integer.toString(HTTPPort) + "/html5/console.html";
            String vanillaURL = "http://localhost:" + Integer.toString(HTTPPort) + "/";
            String mess = "";
            if (rebroadcastPanel.getHttpFlavor().equals("XML"))
              mess = "If your browser supports HTML5, you can see\n" + 
                     consoleURL + " (in the clipboard, type Ctrl+V)\n" +
                     "otherwise, use\n" +  vanillaURL;
            else if (rebroadcastPanel.getHttpFlavor().equals("json"))
              mess = "Your json data are available at \n" + vanillaURL;
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(consoleURL);
            clipboard.setContents(stringSelection, null);          
            JOptionPane.showMessageDialog(this, mess, "HTTP Re-broadcast", JOptionPane.INFORMATION_MESSAGE);
          }
          else if (HTTPPort != -1 && !rebroadcastPanel.isHTTPSelected())
          {
            String port = System.getProperty("http.port", Integer.toString(HTTPPort));
            System.out.println("Stoping HTTP Server on port " + port);
            // Stop the server
            try { HTTPClient.getContent("http://localhost:" + port + "/exit"); } 
            catch (Exception ex) {}
            HTTPPort = -1;
            html5Console.setEnabled(false);
          }
          
          if (rebroadcastPanel.isUDPSelected() && UDPPort == -1)
          {
            UDPPort = rebroadcastPanel.getUDPPort();
            System.out.println("Creating UDP writer on " + rebroadcastPanel.udpHost() + ":" + UDPPort);
            try
            {
              udpWriter = new UDPWriter(UDPPort, rebroadcastPanel.udpHost()); 
            }
            catch (Exception ex)
            {
              System.err.println("Cannot create UDPWriter:");
              ex.printStackTrace();
            }
          }
          else if (!rebroadcastPanel.isUDPSelected() && UDPPort != -1)
            UDPPort = -1;
          
          if (rebroadcastPanel.isTCPSelected() && TCPPort == -1)
          {
            TCPPort = rebroadcastPanel.getTCPPort();
//          System.out.println("Creating TCP writer on " + rebroadcastPanel.tcpHost() + ":" + TCPPort);
            System.out.println("Creating TCP writer on port " + TCPPort);
            try
            {
              tcpWriter = new TCPWriter(TCPPort); // , rebroadcastPanel.tcpHost()); 
            }
            catch (Exception ex)
            {
              System.err.println("Cannot create TCPWriter:");
              ex.printStackTrace();
            }
          }
          else if (!rebroadcastPanel.isTCPSelected() && TCPPort != -1)
          {
            TCPPort = -1;
            try { tcpWriter.close(); }
            catch (Exception ex) { System.err.println(ex.getLocalizedMessage()); }
          }
          
//          if (rebroadcastPanel.isGPSDSelected() && GPSDPort == -1)
//          {
////          JOptionPane.showMessageDialog(this, "Implemented soon", "GPSd", JOptionPane.INFORMATION_MESSAGE);
//            GPSDPort = rebroadcastPanel.getGPSDPort();
//            System.out.println("Creating GPSD writer on port " + GPSDPort);
//            gpsdWriter = new GPSDWriter(GPSDPort); // , rebroadcastPanel.tcpHost()); 
//          }
//          else if (!rebroadcastPanel.isGPSDSelected() && GPSDPort != -1)
//          {
//            GPSDPort = -1;
//            try { gpsdWriter.close(); }
//            catch (Exception ex) { System.err.println(ex.getLocalizedMessage()); }
//          }

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
      else if (event.getActionCommand().equals(HTML5_CONSOLE))
      {
        if (HTTPPort != -1)
        {
          String url = "http://localhost:" + Integer.toString(HTTPPort) + "/html5/console.html";
          try { Utilities.openInBrowser(url); }
          catch (Exception ex)
          {
            JOptionPane.showMessageDialog(this, ex.toString(), "HTML5 Console", JOptionPane.ERROR_MESSAGE);
          }
        }
        else
          JOptionPane.showMessageDialog(this, "No HTTP Port available??", "HTML5 Console", JOptionPane.WARNING_MESSAGE);
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
  
  private class MarqueePanel
    extends JPanel
    implements ActionListener
  {
    private static final int RATE = 1; // The lower the slower
    private final Timer timer = new Timer(1000 / RATE, this);
    private final JLabel label = new JLabel();
    private String s = "";
    private int n = 1;
    private int index;
    
    private boolean running = false;

    public boolean isRunning()
    {
      return running;
    }

    public MarqueePanel()
    {
    }
    
    public void setup() // String s)
    {
      String s = composeStringFromCache();
      
      Color wpFontColor = ((ParamPanel.ParamColor)ParamPanel.getData()[ParamData.LIVE_WALLPAPER_FONT_COLOR][ParamPanel.PRM_VALUE]).getColor();
      Font f = ((Font) ParamPanel.getData()[ParamData.WALLPAPER_FONT][ParamPanel.PRM_VALUE]); 
      label.setFont(f.deriveFont(Font.BOLD, 1.5f * f.getSize()));
      this.n = 1; // s.length();
      String str = s;
      boolean ok = true;  // TEMP Not here!
      while (!ok)
      {
        int strWidth = label.getFontMetrics(label.getFont()).stringWidth(str.trim());
        if (strWidth < label.getWidth()) // Label width
        {
          ok = true;
          this.n = str.length();
        }
        else
          str = str.substring(0, str.length() - 1);
      }

      StringBuilder sb = new StringBuilder(n);
      for (int i=0; i<n; i++)
      {
        sb.append(' ');
      }
      this.s = sb + s + sb;
      
      label.setForeground(wpFontColor);
   // label.setBackground(new Color(0f, 0f, 0f, 0f));
      Color bg = this.getParent().getBackground();
      this.setBackground(new Color(bg.getRGB()));    
      this.setOpaque(false);
//    label.setBackground(Color.yellow);      
      label.setText(sb.toString());
      
      this.setLayout(new BorderLayout());
      this.add(label, BorderLayout.NORTH);      
    }
    
    public void start()
    {
      running = true;
      timer.start();
    }

    public void stop()
    {
      running = false;
      timer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
  //  System.out.println("Action Performed, timer event");
      this.s = composeStringFromCache(); // Compose the string from the cache

      String str = composeDataString(s);
      label.setText(str);
    }
    
    private String composeDataString(String s)
    {
      String[] sa = s.split("\n");
      String fmt = "";
      fmt += "<html>";
      for (int i=index; i<sa.length; i++)
        fmt += (sa[i] + "<br>");
      for (int i=0; i<index; i++)
        fmt += (sa[i] + "<br>");
      fmt += "</html>";
      
      index++;
      if (index > sa.length)
        index = 0;
      
      return fmt;
    }
    
    private final Format DIR_FMT   = new DecimalFormat("###'\272t'");
    private final Format ANGLE_FMT = new DecimalFormat("###'\272'");
    private final Format SPEED_FMT = new DecimalFormat("#0.00' kt'");
    private final Format DEPTH_FMT = new DecimalFormat("#0.00' m'");
    private final Format TEMP_FMT  = new DecimalFormat("#0.00'\272C'");
    private final Format DIST_FMT  = new DecimalFormat("###0.00' nm'");
    private final Format PRESS_FMT = new DecimalFormat("###0.0' mb'");
    private final Format VOLT_FMT  = new DecimalFormat("00.00' V'");
    
    private String composeStringFromCache()
    {
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
      int twd = 0;
      try { twd = (int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.TWD, true)).getDoubleValue(); } catch (Exception ex) {}
      double tws = 0d;
      try { tws = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.TWS, true)).getDoubleValue(); } catch (Exception ex) {}
      double twa = 0d; 
      try { twa = ((Angle180) NMEAContext.getInstance().getCache().get(NMEADataCache.TWA, true)).getValue(); } catch (Exception ignore) {}
      int cdr = 0; 
//    try { cdr = (int)((Angle360)NMEAContext.getInstance().getCache().get(NMEADataCache.CDR, true)).getDoubleValue(); } catch (Exception ex) {}
      double csp = 0;
//    try { csp = ((Speed)NMEAContext.getInstance().getCache().get(NMEADataCache.CSP, true)).getDoubleValue(); } catch (Exception ex) {}
      try
      {
        Current current = (Current)NMEAContext.getInstance().getCache().get(NMEADataCache.VDR_CURRENT);
        if (current == null)
        {
          Map<Long, NMEADataCache.CurrentDefinition> currentMap = 
                              ((Map<Long, NMEADataCache.CurrentDefinition>)NMEAContext.getInstance().getCache().get(NMEADataCache.CALCULATED_CURRENT));  //.put(bufferLength, new NMEADataCache.CurrentDefinition(bufferLength, new Speed(speed), new Angle360(dir)));
          Set<Long> keys = currentMap.keySet();
          for (Long l : keys)
          {
            cdr = (int)Math.round(currentMap.get(l).getDirection().getValue());
            csp = currentMap.get(l).getSpeed().getValue();
          }
        }
        else
        {
          cdr = current.angle;
          csp = current.speed;
        }
      }
      catch (Exception ignore) {}
                  
      float bat = 0;
      try { bat = ((Float)NMEAContext.getInstance().getCache().get(NMEADataCache.BATTERY, true)).floatValue(); } catch (Exception ignore) {}
      double xte = 0;
      try { xte = ((Distance)NMEAContext.getInstance().getCache().get(NMEADataCache.XTE, true)).getValue(); } catch (Exception ignore) {}
      String nwp = "";
      nwp = (String)NMEAContext.getInstance().getCache().get(NMEADataCache.TO_WP, true);

      // Air temperature, Pressure
      double airtemp = -Double.MAX_VALUE; 
      try { airtemp = ((Temperature) NMEAContext.getInstance().getCache().get(NMEADataCache.AIR_TEMP, true)).getValue(); } catch (Exception ignore) {}
      double prmsl = 0d; 
      try { prmsl = ((Pressure) NMEAContext.getInstance().getCache().get(NMEADataCache.BARO_PRESS, true)).getValue(); } catch (Exception ignore) {}
      
      // A preference for the data to diaplay in the marquee
      ParamPanel.MarqueeDataList mdl = ((ParamPanel.MarqueeDataList)ParamPanel.getData()[ParamData.MARQUEE_DATA][ParamPanel.PRM_VALUE]);
      String dl = mdl.toString();
      String[] da = dl.split(",");
      String str = "";
      for (String id : da)
      {
        if ("BSP".equals(id))
          str += ("BSP:" + lpad(SPEED_FMT.format(bsp), " ", 8)    + "\n");
        else if ("HDG".equals(id))
          str += ("HDG:" + lpad(DIR_FMT.format(hdg), " ", 5)      + "\n");
        else if ("TWD".equals(id))
          str += ("TWD:" + lpad(DIR_FMT.format(twd), " ", 5)      + "\n");
        else if ("TWS".equals(id))
          str += ("TWS:" + lpad(SPEED_FMT.format(tws), " ", 8)    + "\n");
        else if ("TWA".equals(id))
          str += ("TWA:" + lpad(ANGLE_FMT.format(twa), " ", 5)    + "\n");
        else if ("AWS".equals(id))
          str += ("AWS:" + lpad(SPEED_FMT.format(aws), " ", 8)    + "\n");
        else if ("AWA".equals(id))
          str += ("AWA:" + lpad(ANGLE_FMT.format(awa), " ", 5)    + "\n");
        else if ("DBT".equals(id))
          str += ("DBT:" + lpad(DEPTH_FMT.format(depth), " ", 7)  + "\n");
        else if ("MWT".equals(id))
          str += ("MWT:" + lpad(TEMP_FMT.format(temp), " ", 7)    + "\n");
        else if ("LOG".equals(id))
          str += ("LOG:" + lpad(DIST_FMT.format(bl), " ", 11)     + "\n");
        else if ("MAT".equals(id) && airtemp != -Double.MAX_VALUE)
          str += ("MAT:" + lpad(TEMP_FMT.format(airtemp), " ", 7) + "\n");
        else if ("PRS".equals(id) /* && prmsl > 0 */)
          str += ("PRS:" + lpad(PRESS_FMT.format(prmsl), " ", 10) + "\n");
        else if ("CDR".equals(id))
          str += ("CDR:" + lpad(DIR_FMT.format(cdr), " ", 5) + "\n");
        else if ("CSP".equals(id))
          str += ("CSP:" + lpad(SPEED_FMT.format(csp), " ", 8)    + "\n");
        else if ("BAT".equals(id))
          str += ("BAT:" + lpad(VOLT_FMT.format(bat), " ", 7)    + "\n");
        else if ("XTE".equals(id))
          str += ("XTE:" + lpad(DIST_FMT.format(xte), " ", 11)     + "\n");
        else if ("NWP".equals(id))
          str += ("NWP:" + lpad(nwp, " ", 6)    + "\n");
      } 
//    System.out.println("Composed " + str);
      return str;
    }
  }
  
  private static boolean isEastOf(double reference, double toCompare)
  {
    return (Math.sin(Math.toRadians(toCompare - reference)) > 0);
  }
  
  private static boolean isWestOf(double reference, double toCompare)
  {
    return (Math.sin(Math.toRadians(toCompare - reference)) < 0);
  }
  
  public static void main_(String[] args)
  {
    System.out.println("3 is " + (isEastOf(-122, 3)?"":"NOT ") + "at the east of -122");
    System.out.println("-123 is " + (isEastOf(-122, -123)?"":"NOT ") + "at the east of -122");
    System.out.println("-123 is " + (isWestOf(-122, -123)?"":"NOT ") + "at the west of -122");
    System.out.println("60 is " + (isWestOf(-122, 60)?"":"NOT ") + "at the west of -122");
  }
}
