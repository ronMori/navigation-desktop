package olivsoftdesktop.utils;

import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.Point;

import java.io.File;

import java.io.FileOutputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileFilter;


import nmea.server.utils.Utils;

import olivsoftdesktop.ctx.DesktopContext;

import olivsoftdesktop.param.ParamData;
import olivsoftdesktop.param.ParamPanel;

import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

public class DesktopUtilities
{
  public static String replaceString(String orig, String oldStr, String newStr)
  {
    String ret = orig;
    int indx = 0;
    for (boolean go = true; go; )
    {
      indx = ret.indexOf(oldStr, indx);
      if (indx < 0)
      {
        go = false;
      }
      else
      {
        ret = ret.substring(0, indx) + newStr + ret.substring(indx + oldStr.length());
        indx += 1 + oldStr.length();
      }
    }
    return ret;
  }

  public static String chooseFile(int mode, String flt, String desc)
  {
    return chooseFile(mode, new String[]
        { flt }, desc);
  }

  public static String chooseFile(int mode, String[] flt, String desc)
  {
    return chooseFile(mode, flt, desc, ".");
  }

  public static String chooseFile(int mode, String[] flt, String desc, String where)
  {
    return chooseFile(mode, flt, desc, where, null, null);
  }

  public static String chooseFile(int mode, String[] flt, String desc, String where, String buttonLabel, String dialogLabel)
  {
    return chooseFile(null, mode, flt, desc, where, buttonLabel, dialogLabel);
  }

  public static String chooseFile(Component parent, int mode, String[] flt, String desc, String where, String buttonLabel, String dialogLabel)
  {
    String fileName = "";
    JFileChooser chooser = new JFileChooser();
    // TODO_IF_POSSIBLE Sort the file by date, most recent on top. If possible... :(
    ToolFileFilter filter = new ToolFileFilter(flt, desc);
    chooser.addChoosableFileFilter(filter);
    chooser.setFileFilter(filter);
    chooser.setFileSelectionMode(mode);

    if (buttonLabel != null)
      chooser.setApproveButtonText(buttonLabel);
    if (dialogLabel != null)
      chooser.setDialogTitle(dialogLabel);

    if (where == null) where = ".";
    File ff = new File(where);
    if (ff.isDirectory())
      chooser.setCurrentDirectory(ff);
    else
    {
      File f = new File(".");
      String currPath = f.getAbsolutePath();
      f = new File(currPath.substring(0, currPath.lastIndexOf(File.separator)));
      chooser.setCurrentDirectory(f);
    }
    int retval = chooser.showOpenDialog(parent);
    switch (retval)
    {
      case JFileChooser.APPROVE_OPTION:
        fileName = chooser.getSelectedFile().toString();
        break;
      case JFileChooser.CANCEL_OPTION:
        break;
      case JFileChooser.ERROR_OPTION:
        break;
    }
    return fileName;
  }
  
  public static void doOnExit()
  {
    doOnExit(null);
  }
  
  public static void doOnExit(URL sound2play)
  {
    ParamPanel.saveParameters();
    
    if (sound2play != null)
    {
      boolean play = ((Boolean)(ParamPanel.getData()[ParamData.PLAY_SOUNDS][ParamPanel.PRM_VALUE])).booleanValue();
      if (play)
      {
        try { Utils.playSound(sound2play); }
        catch (Exception ex) { ex.printStackTrace(); }
      }
    }
    Dimension dim = DesktopContext.getInstance().getTopFrame().getSize();
    Point p = DesktopContext.getInstance().getTopFrame().getLocation();
//  System.out.println("Storing position: " + p.x + ", " + p.y);
    XMLDocument doc = new XMLDocument();
    XMLElement root = (XMLElement)doc.createElement("desktop-pos");
    doc.appendChild(root);
    root.setAttribute("x", Integer.toString(p.x));
    root.setAttribute("y", Integer.toString(p.y));
    root.setAttribute("w", Integer.toString(dim.width));
    root.setAttribute("h", Integer.toString(dim.height));
    try
    {
      doc.print(new FileOutputStream("desktop.xml"));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    System.exit(0);
  }

  public static void saveInternalFrameConfig(String fileName, String rootName, JInternalFrame jif)
  {
    Dimension dim = jif.getSize();
    Point p = jif.getLocation();
    //  System.out.println("Storing position: " + p.x + ", " + p.y);
    XMLDocument doc = new XMLDocument();
    XMLElement root = (XMLElement)doc.createElement(rootName);
    doc.appendChild(root);
    root.setAttribute("x", Integer.toString(p.x));
    root.setAttribute("y", Integer.toString(p.y));
    root.setAttribute("w", Integer.toString(dim.width));
    root.setAttribute("h", Integer.toString(dim.height));
    try
    {
      doc.print(new FileOutputStream(fileName));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public static Color buildColor(String str)
  {
    String[] st = str.split(";");
    int b = 0, g = 0, r = 0;
    if (st.length != 3)
      throw new RuntimeException("Bad color definition: [" + str + "]");
    r = Integer.parseInt(st[0]);
    g = Integer.parseInt(st[1]);
    b = Integer.parseInt(st[2]);
    Color c = new Color(r, g, b);
    return c;
  }

  public static String colorToString(Color c)
  {
    String s = Integer.toString(c.getRed()) + ";" + Integer.toString(c.getGreen()) + ";" + Integer.toString(c.getBlue());
    return s;
  }

  public static class ToolFileFilter
    extends FileFilter
  {

    public boolean accept(File f)
    {
      if (f != null)
      {
        if (f.isDirectory())
          return true;
        String extension = getExtension(f);
        if (filters == null)
          return true;
        if (extension != null && filters.get(getExtension(f)) != null)
          return true;
      }
      return false;
    }

    public String getExtension(File f)
    {
      if (f != null)
      {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1)
          return filename.substring(i + 1).toLowerCase();
      }
      return null;
    }

    public void addExtension(String extension)
    {
      if (filters == null)
        filters = new Hashtable<String, Object>(5);
      filters.put(extension.toLowerCase(), this);
      fullDescription = null;
    }

    public String getDescription()
    {
      if (fullDescription == null)
      {
        if (description == null || isExtensionListInDescription())
        {
          if (description != null)
            fullDescription = description;
          if (filters != null)
          {
            fullDescription += " (";
            Enumeration extensions = filters.keys();
            if (extensions != null)
              for (fullDescription += "." + (String) extensions.nextElement(); extensions.hasMoreElements(); fullDescription += ", " + (String) extensions.nextElement())
                ;
            fullDescription += ")";
          }
          else
            fullDescription = "";
        }
        else
        {
          fullDescription = description;
        }
      }
      return fullDescription;
    }

    public void setDescription(String description)
    {
      this.description = description;
      fullDescription = null;
    }

    public void setExtensionListInDescription(boolean b)
    {
      useExtensionsInDescription = b;
      fullDescription = null;
    }

    public boolean isExtensionListInDescription()
    {
      return useExtensionsInDescription;
    }

    private String TYPE_UNKNOWN;
    private String HIDDEN_FILE;
    private Hashtable<String, Object> filters;
    private String description;
    private String fullDescription;
    private boolean useExtensionsInDescription;

    public ToolFileFilter()
    {
      this((String) null, null);
    }

    public ToolFileFilter(String extension)
    {
      this(extension, null);
    }

    public ToolFileFilter(String extension, String description)
    {
      this(new String[]
          { extension }, description);
    }

    public ToolFileFilter(String[] filters)
    {
      this(filters, null);
    }

    public ToolFileFilter(String[] filter, String description)
    {
      TYPE_UNKNOWN = "Type Unknown";
      HIDDEN_FILE = "Hidden File";
      this.filters = null;
      this.description = null;
      fullDescription = null;
      useExtensionsInDescription = true;
      if (filter != null)
      {
        this.filters = new Hashtable<String, Object>(filter.length);
        for (int i = 0; i < filter.length; i++)
          addExtension(filter[i]);
      }
      setDescription(description);
    }
  }

}
