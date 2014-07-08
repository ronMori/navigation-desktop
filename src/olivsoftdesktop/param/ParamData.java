package olivsoftdesktop.param;

import java.io.File;

public class ParamData 
{
  public final static int NMEA_CHANNEL                 =  0;  // Serial, TCP, UDP
  public final static int NMEA_SERIAL_PORT             =  1;  // COMXX
  public final static int NMEA_TCP_PORT                =  2;  // 3005
  public final static int NMEA_HTTP_PORT               =  3;  // 6666
  public final static int NMEA_HOST_NAME               =  4;  // localhost
  public final static int NMEA_CONFIG_FILE             =  5;  // nmea.properties
  public final static int SAILFAX_CATALOG              =  6;  // pac-fax-data.xml  
  public final static int DB_LOCATION                  =  7;  // ..\all-db
  public final static int BACKGROUND_IMAGE             =  8;  // ....
  public final static int NMEA_BAUD_RATE               =  9;  // ....
  public final static int LIVE_WALLPAPER_FONT_COLOR    = 10;  // Foreground Window Font Color
  public final static int DELTA_T                      = 11;  // 65.984
  public final static int NMEA_DATA_STREAM             = 12;  // NMEA port, HTTP // for the desktop BGWindows
  public final static int AIRMAIL_LOCATION             = 13;  // C:\Program Files\Airmail
  public final static int PLAY_SOUNDS                  = 14;  // true
  public final static int NMEA_UDP_PORT                = 15;  // 8001
  public final static int TIDE_FLAVOR                  = 16;  // XML
  public final static int DESKTOP_MESSAGE              = 17;  // Navigation Console
  public final static int INTERNAL_FRAMES_TRANSPARENCY = 18;  // Navigation Console
  public final static int COMPUTE_SUN_MOON_DATA        = 19;
  public final static int USE_CHART_APP                = 20;
  public final static int USE_NMEA_APP                 = 21;
  public final static int USE_STAR_FINDER_APP          = 22;
  public final static int USE_SAILFAX_APP              = 23;
  public final static int USE_LUNAR_APP                = 24;
  public final static int USE_SIGHT_RED_APP            = 25;
  public final static int USE_PUBLISHER_APP            = 26;
  public final static int USE_GOOGLE_APP               = 27;
  public final static int USE_TIDES_APP                = 28;
  public final static int NMEA_RMI_PORT                = 29; // 1099
  public final static int BG_WIN_FONT_COLOR            = 30;
  public final static int MAX_TIDE_RECENT_STATIONS     = 31;
  public final static int BOAT_ID                      = 32;
  public final static int MAX_ANALOG_BSP               = 33;
  public final static int MAX_ANALOG_TWS               = 34;
  public final static int DEFAULT_FONT                 = 35;
  public final static int USE_SPOT_APP                 = 36;
  public final static int FULL_SCREEN_DESKTOP          = 37;
  public final static int WALLPAPER_FONT               = 38;
  public final static int MARQUEE_DATA                 = 39;

  public final static int NB_PARAMETERS                = 40;

  private final static String[] LABELS =
  {
    "NMEA Source Channel",                                                     //  0
    "NMEA Source Serial Port",                                                 //  1
    "NMEA Source TCP Port",                                                    //  2
    "NMEA Source HTTP Port",                                                   //  3
    "NMEA Source Host",                                                        //  4
    "NMEA Config File",                                                        //  5
    "SailFax Repository File",                                                 //  6
    "DB directory or URL (for Chart Library & NMEA Console Journal)",          //  7
    "Background Image",                                                        //  8
    "NMEA Source Baud Rate",                                                   //  9
    "Live Wallpaper Font Color",                                               // 10.
    "Delta T in seconds",                                                      // 11
    "NMEA Source Data Stream",                                                 // 12
    "Airmail Location",                                                        // 13
    "Play sounds",                                                             // 14
    "NMEA Source UDP Port (Multicast)",                                        // 15
    "Tide database flavor",                                                    // 16
    "Desktop Background message",                                              // 17
    "Internal Frames transparency",                                            // 18
    "Display Sun & Moon Altitude & Azimuth",                                   // 19
    // Applications
    "Chart Library",                                                           // 20
    "NMEA Console",                                                            // 21
    "Star Finder",                                                             // 22
    "SailFax",                                                                 // 23
    "Clear Lunar Distance",                                                    // 24
    "Sight Reduction",                                                         // 25
    "Almanac Publisher",                                                       // 26
    "Google Locator",                                                          // 27
    "Tides",                                                                   // 28
    // 
    "NMEA Source RMI Port",                                                    // 29
    "Background Windows Font color",                                           // 30
    "Max number of recent stations for the Tides",                             // 31
    //
    "Boat ID",                                                                 // 32
    //
    "Max value for BSP Analog Display",                                        // 33
    "Max value for TWS Analog Display",                                        // 34
    "Default Swing font",                                                      // 35
    // More App
    "SPOT Bulletins",                                                          // 36 
    "Full screen desktop on open",                                             // 37 
    "Font of the live wallpaper",                                              // 38 
    "Marquee Data (live wallpapaer)"                                           // 39
  };
  
  public final static String[] getLabels()
  {
    return LABELS.clone();
  }
  
  private final static String[] HELPTEXT =
  {
    /*  0 */ "NMEA Source: Serial, TCP, UDP or RMI. Serial is the most common, other methods can be used when re-broadcasting the NMEA stream",
    /*  1 */ "If the Channel is Serial, then this field should be set. That would be COM1, COM2, or something like that.",
    /*  2 */ "If the Channel is TCP, then this field should be set. Usually a 4-digit number (like 3001). Used along with the NMEA Source Host. 127.0.0.1 is the default address for localhost.",
    /*  3 */ "Port used to read NMEA Data from HTTP Broadcasting. Used along with the NMEA Source Host",
    /*  4 */ "Name of the \"broadcaster\" host of the NMEA Data through HTTP, UDP or TCP. Can be a name (like localhost), or an IP address (like 127.0.0.1, 230.0.0.1, etc).",
    /*  5 */ "NMEA Config file, containing the NMEA Sentences prefixes to log.",
    /*  6 */ "SailFax repository, an XML file containing all the transmissions.",
    /*  7 */ "Directory where the HypersonicSQL Database Files are located (for standalone driver), or database URL (for server driver). For standalone, enter something like \".." + File.separator + "all-db\", for server driver, this would be like \"//localhost:1234/tides\".",
    /*  8 */ "Desktop Background Image",
    /*  9 */ "Baud Rate for the Serial NMEA input",
    /* 10 */ "Color of the font used to display the data in the live wallpaper. Can be tweaked to be visible, depending on what your background looks like.",
    /* 11 */ "Inserted leap seconds, check out http://aa.usno.navy.mil/data/docs/celnavtable.php, http://maia.usno.navy.mil/ser7/deltat.data and http://maia.usno.navy.mil/",
    /* 12 */ "How to read NMEA Data read from the NMEA port, to feed the background windows of the Desktop.",
    /* 13 */ "Directory in which Airmail is installed. Will be used by SailFax to locate SkedFile.txt",
    /* 14 */ "Play gooffy sounds (like R2D2 and similar crap...)",
    /* 15 */ "If the Channel is UDP, then this field should be set. Usually a 4-digit number (like 8001). Used along with the NMEA Source Host. Warning: you need a multicast address to use this option. Otherwise, prefer TCP. Default multicast address for localhost is usually 230.0.0.1",
    /* 16 */ // "How the Tide data (harmonic coefficients) are stored. If the falvor is SQL, we'll use an Hypersonic SQL database, stored in the DB Directory mentionned above. If the flavor is XML, the data are stored in the \"xml.zip\" archive, part of a jar-file. SQLITE will use a database named sqlite" + File.separator + "tidebd.",
    /* 16 */ "How the Tide data (harmonic coefficients) are stored. Reduced to only XML on January 2013.",
    /* 17 */ "The message displayed in the desktop's background next time you restart.",
    /* 18 */ "Transparency used for the internal frames, from 0 to 1. 0 is fully transparent, 1 is opaque.",
    /* 19 */ "Compute and display the Altitude and Azimuth for Sun and Moon.",
    /* 20 */ "Paper Chart Library, with hSQL. Modification will require restart.",
    /* 21 */ "NMEA Console, requires connection to your NMEA Station. Modification will require restart.",
    /* 22 */ "Star Finder & Planetarium. Modification will require restart.",
    /* 23 */ "SailFax, manage the transmission from SailMail (GetFax). Modification will require restart.",
    /* 24 */ "Clear Lunar Distance, calculate your Lunar distances. Modification will require restart.",
    /* 25 */ "Sight Reduction, calculate azimuth and altitude for any celestial body. Modification will require restart.",
    /* 26 */ "Almanac Publisher, by month, by year. Real Time Almanac. Modification will require restart.",
    /* 27 */ "Google Locator, generates a URL on Google Maps. Modification will require restart.",
    /* 28 */ "Tides, world wide tide predictions. Modification will require restart.",
    /* 29 */ "Port used to read NMEA Data from RMI. Used along with the NMEA Source Host",
    /* 30 */ "Font color for the background windows",        
    /* 31 */ "For the Tides application, specifies the max number of recent tide station in the 'Recent Stations' menu item",
    /* 32 */ "Boat ID used by the Google Locator to retrieve your boat name. You can request a boat ID by going to http://donpedro.lediouris.net/php/locator/idform.html or by sending an email to olivier@lediouris.net",
    /* 33 */ "Maximum value for the boat speed analog display",
    /* 34 */ "Maximum value for the true wind speed analog display",
    /* 35 */ "Default Swing font. Maybe increased or decreased for certain Look & Feels. Negative values will be ignored. Restart required after modification.",
    /* 36 */ "Spot Bulletins",
    /* 37 */ "Display the desktop in full screen by default",
    /* 38 */ "Font to use for the live wallpaper data",
    /* 39 */ "Select the data to display in the marquee of the live wallpaper"
  };
  
  public final static String[] getHelpText()
  {
    return HELPTEXT.clone();
  }
  
  public final static String PARAM_FILE_NAME = "config" + File.separator + "desktop-prms.xml"; 
}