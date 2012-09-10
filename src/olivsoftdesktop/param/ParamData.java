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
  public final static int NMEA_SIMULATION              = 10;  // .... UNUSED!!
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

  public final static int NB_PARAMETERS                = 33;

  private final static String[] LABELS =
  {
    "NMEA Source Channel",                                                     //  0
    "NMEA Source Serial Port",                                                 //  1
    "NMEA Source TCP Port",                                                    //  2
    "NMEA Source HTTP Port",                                                   //  3
    "NMEA Source Host",                                                        //  4
    "NMEA Config File",                                                        //  5
    "SailFax Repository File",                                                 //  6
    "DB directory or URL (for Chart Library, Tides & NMEA Console Journal)",   //  7
    "Background Image",                                                        //  8
    "NMEA Source Baud Rate",                                                   //  9
    "NMEA Source Simulation Data File",                                        // 10. UNUSED
    "Delta T in seconds",                                                      // 11
    "NMEA Source Data Stream",                                                 // 12
    "Airmail Location",                                                        // 13
    "Play sounds",                                                             // 14
    "NMEA Source UDP Port",                                                    // 15
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
    "Boat ID"                                                                  // 32
  };
  
  public final static String[] getLabels()
  {
    return LABELS.clone();
  }
  
  private final static String[] HELPTEXT =
  {
    /*  0 */ "NMEA Source: Serial, TCP, UDP or RMI. Serial is the most common, other methods can be used when re-broadcasting the NMEA stream",
    /*  1 */ "If the Channel is Serial, then this field should be set. That would be COM1, COM2, or something like that.",
    /*  2 */ "If the Channel is TCP, then this field should be set. Usually a 4-digit number (like 3001). Used along with the NMEA Source Host",
    /*  3 */ "Port used to read NMEA Data from HTTP Broadcasting. Used along with the NMEA Source Host",
    /*  4 */ "Name of the \"broadcaster\" host of the NMEA Data through HTTP, UDP or TCP.",
    /*  5 */ "NMEA Config file, containing the NMEA Sentences prefixes to log.",
    /*  6 */ "SailFax repository, an XML file containing all the transmissions.",
    /*  7 */ "Directory where the HypersonicSQL Database Files are located (for standalone driver), or database URL (for server driver). For standalone, enter something like \".." + File.separator + "all-db\", for server driver, this would be like \"//localhost:1234/tides\".",
    /*  8 */ "Desktop Background Image",
    /*  9 */ "Baud Rate for the Serial NMEA input",
    /* 10 */ "NMEA Data File containg data for simulation, incase you're not connected to a GPS or NMEA Station",
    /* 11 */ "Inserted leap seconds, check out http://aa.usno.navy.mil/data/docs/celnavtable.php, http://maia.usno.navy.mil/ser7/deltat.data and http://maia.usno.navy.mil/",
    /* 12 */ "How to read NMEA Data read from the NMEA port, to feed the background windows of the Desktop.",
    /* 13 */ "Directory in which Airmail is installed. Will be used by SailFax to locate SkedFile.txt",
    /* 14 */ "Play gooffy sounds (like R2D2 and similar crap...)",
    /* 15 */ "If the Channel is UCP, then this field should be set. Usually a 4-digit number (like 8001). Used along with the NMEA Source Host",
    /* 16 */ "How the Tide data (harmonic coefficients) are stored. If the falvor is SQL, we'll use an Hypersonic SQL database, stored in the DB Directory mentionned above. If the flavor is XML, the data are stored in the \"xml.data\" directory (2 files). SQLITE will use a database named sqlite" + File.separator + "tidebd.",
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
    /* 32 */ "Boat ID used by the Google Locator to retrieve your boat name. You can request a boat ID by going to http://donpedro.lediouris.net/php/locator/idform.html or by sending an email to olivier@lediouris.net"
  };
  
  public final static String[] getHelpText()
  {
    return HELPTEXT.clone();
  }
  
  public final static String PARAM_FILE_NAME = "config" + File.separator + "desktop-prms.xml"; 
}