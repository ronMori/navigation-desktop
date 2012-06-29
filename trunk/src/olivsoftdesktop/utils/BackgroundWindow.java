package olivsoftdesktop.utils;

import coreutilities.Utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import javax.swing.ImageIcon;

public class BackgroundWindow
{
  private boolean verbose = false;
  
  protected Font dataFont  = new Font("Tahoma", Font.BOLD, 12);
  protected Font titleFont = new Font("Tahoma", Font.BOLD, 12);
  protected final int BG_WINDOW_HEADER_SIZE        =  30; // Height
  protected final int BG_WINDOW_BORDER_SIZE        =   5;
  protected final int BG_WINDOW_DATA_OFFSET_SIZE   =   2;
  protected final int BG_WINDOW_MIN_HEIGHT         =  40;
  protected final int BG_WINDOW_MIN_WIDTH          = 100;
  protected final int BG_WINDOW_MIN_NUM_LINES      =   1;
  protected final int BG_WINDOW_TITLE_MIN_BASELINE =  20;
  protected final int BG_WINDOW_TITLE_OFFSET       =  10;
  
  protected final int BG_WINDOW_3BUTTON_WIDTH = 80;
  
  public final static int CLOSE_IMAGE       = 1;
  public final static int ZOOMEXPAND_IMAGE  = 2;
  public final static int ZOOMSHRINK_IMAGE  = 3;
  
  protected int bgWinX = 10;
  protected int bgWinY = 10;
  protected int bgWinW = BG_WINDOW_MIN_WIDTH;
  protected int bgWinH = BG_WINDOW_MIN_HEIGHT;
  
  protected int minNumLine = BG_WINDOW_MIN_NUM_LINES;

  private ImageIcon closeImage   = null;
  private ImageIcon zoomInImage  = null;
  private ImageIcon zoomOutImage = null;
  private final int buttonWidth = 15;
  
  private boolean bgWindowBeingDragged = false;
  private int dragStartX = 0, 
              dragStartY = 0;
  private boolean displayBGWindow = false; 

  private String winTitle = "";
  private String winData  = "";
  
  private Color dataFontColor = Color.green;
  
  public final static int BUTTON_ALIGNED_RIGHT = 0;
  public final static int BUTTON_ALIGNED_LEFT  = 1;
  
  private int buttonAlignment = BUTTON_ALIGNED_LEFT;
  
  private boolean recalculateWinDimension = true;
  private boolean withZoomButtons = true;
  
  public BackgroundWindow()
  { 
    dataFont = loadDigiFont();
    if (dataFont == null)
      dataFont = new Font("Tahoma", Font.BOLD, 12);
    else
      dataFont = dataFont.deriveFont(Font.BOLD, 16);
    loadIcons();
  }
  
  public BackgroundWindow(String s)
  {
    this();
    this.winTitle = s;
  }
  
  public BackgroundWindow(String str1, String str2)
  {
    this();
    this.winTitle = str1;
    this.winData  = str2;
  }
  
  private void loadIcons()
  {
    try 
    { 
      URL imgURL = BackgroundWindow.class.getResource("resources/close.gif");
//    System.out.println("closeImage:" + imgURL.toString());
      closeImage   = new ImageIcon(imgURL); 
    } 
    catch (Exception ex)
    {
      System.err.println("Cannot load resources/close.gif");
      System.err.println("Loaded from " + BackgroundWindow.class.getResource("."));
      ex.printStackTrace();
      closeImage = null;
    }
    try 
    { 
      URL imgURL = BackgroundWindow.class.getResource("resources/zoomexpand.gif");
//    System.out.println("zoomInImage:" + imgURL.toString());
      zoomInImage   = new ImageIcon(imgURL); 
    } 
    catch (Exception ex)
    {
      System.err.println("Cannot load resources/zoomexpand.gif");
      System.err.println("Loaded from " + BackgroundWindow.class.getResource("."));
      ex.printStackTrace();
      zoomInImage = null;
    }
    try 
    { 
      URL imgURL = BackgroundWindow.class.getResource("resources/zoomshrink.gif");
//    System.out.println("zoomOutImage:" + imgURL.toString());
      zoomOutImage   = new ImageIcon(imgURL); 
    } 
    catch (Exception ex)
    {
      System.err.println("Cannot load resources/zoomshrink.gif");
      System.err.println("Loaded from " + BackgroundWindow.class.getResource("."));
      ex.printStackTrace();
      zoomOutImage = null;
    }
  }
  
  public void setWinData(String s)
  {
    if (closeImage == null || zoomInImage == null || zoomOutImage == null)
      loadIcons();
    this.winData = s;
  }
  
  public void paintBackgroundWindow(Graphics g)
  {
    displayBackGroundWindow(g, this.winTitle, this.winData);
  }

  public void increaseSize()
  {
    bgWinW *= 1.1;  
    bgWinH *= 1.1;  
    dataFont = dataFont.deriveFont(dataFont.getSize() * 1.1f);
  }
  
  public void decreaseSize()
  {
    bgWinW /= 1.1;  
    bgWinH /= 1.1;  
    dataFont = dataFont.deriveFont(dataFont.getSize() / 1.1f);
  }

  public void displayBackGroundWindow(Graphics graphics, String dataString)
  {
    displayBackGroundWindow(graphics, this.winTitle, dataString);
  }
  
  private static int imageWidth = 24;
  
  public void displayBackGroundWindow(Graphics graphics, String winTitle, String dataString)
  {
    // Icons ?
    synchronized (this)
    {
      if (closeImage == null)
      {
        try 
        { 
          URL imgURL = this.getClass().getResource("resources/close.gif");
          System.out.println("closeImage:" + imgURL.toString());
          closeImage   = new ImageIcon(imgURL); 
        } 
        catch (Exception ex)
        {
          System.err.println(winTitle + ":Cannot load resources/close.gif");
//        ex.printStackTrace();
        }
      }
      if (zoomInImage == null)
      {
        try 
        { 
          URL imgURL = this.getClass().getResource("resources/zoomexpand.gif");
          System.out.println("zoomInImage:" + imgURL.toString());
          zoomInImage   = new ImageIcon(imgURL); 
        } 
        catch (Exception ex)
        {
          System.err.println(winTitle + ":Cannot load resources/zoomexpand.gif");
//        ex.printStackTrace();
        }
      }
      if (zoomOutImage == null)
      {
        try 
        { 
          URL imgURL = this.getClass().getResource("resources/zoomshrink.gif");
          System.out.println("zoomOutImage:" + imgURL.toString());
          zoomOutImage   = new ImageIcon(imgURL); 
        } 
        catch (Exception ex)
        {
          System.err.println(winTitle + ":Cannot load resources/zoomshrink.gif");
//        ex.printStackTrace();
        }
      }
    }

    Color endColor   = new Color(0.0f, 0.0f, 0.05f, 0.75f);
    Color startColor = new Color(0.0f, 0.0f, 0.75f, 0.25f);
    
    if (recalculateWinDimension)
    {
      bgWinW = BG_WINDOW_MIN_WIDTH;
      bgWinH = BG_WINDOW_MIN_HEIGHT;
    }
    // Measure dimensions, based on the title and the data to display.
    if (winTitle != null && recalculateWinDimension)
    {
      calculateWinWidth(graphics);
    }
    if (dataString != null && recalculateWinDimension)
    {
      calculateWinHeight(graphics, dataString);
    }    
//  System.out.println("Repainting AltWin:" + altTooltipX + ", " + altTooltipY);

    int x = 0;
    int y = 0;    
    
    GradientPaint gradient = new GradientPaint(x + bgWinX, y + bgWinY, startColor, x + bgWinX + bgWinW, y + bgWinY + bgWinH, endColor); // Diagonal, top-left to bottom-right
  //  GradientPaint gradient = new GradientPaint(x + altTooltipX, x + altTooltipX + altTooltipH, startColor, y + altTooltipY + altTooltipW, y + altTooltipY, endColor); // Horizontal
  //  GradientPaint gradient = new GradientPaint(x + altTooltipX, y + altTooltipY, startColor, x + altTooltipX, x + altTooltipX + altTooltipH, endColor); // vertical
  //  GradientPaint gradient = new GradientPaint(x + altTooltipX, x + altTooltipX + altTooltipH, startColor, x + altTooltipX, y + altTooltipY, endColor); // vertical, upside down
    ((Graphics2D)graphics).setPaint(gradient);

  //  Color bgColor = new Color(0.0f, 0.0f, 0.75f, 0.55f);
  //  graphics.setColor(bgColor);
    graphics.fillRoundRect(x + bgWinX, y + bgWinY, bgWinW, bgWinH, 10, 10);

    int xi = 0;
    int yi = 0;

    if (buttonAlignment == BUTTON_ALIGNED_RIGHT)
      xi = bgWinX + bgWinW - (imageWidth);
    else
      xi = bgWinX + ((withZoomButtons?2:0) * imageWidth);
    yi = bgWinY;
    if (closeImage != null) graphics.drawImage(closeImage.getImage(), x + xi, y + yi, null);

    if (buttonAlignment == BUTTON_ALIGNED_RIGHT)
      xi = bgWinX + bgWinW - (2 * imageWidth);
    else
      xi = bgWinX + (imageWidth);
      
    yi = bgWinY;
    if (zoomInImage != null && withZoomButtons) graphics.drawImage(zoomInImage.getImage(), x + xi, y + yi, null);

    if (buttonAlignment == BUTTON_ALIGNED_RIGHT)
      xi = bgWinX + bgWinW - (3 * imageWidth);
    else
      xi = bgWinX;
      
    yi = bgWinY;
    if (zoomOutImage != null && withZoomButtons) graphics.drawImage(zoomOutImage.getImage(), x + xi, y + yi, null);
    
    // The data frame (area)
    int xs = x + bgWinX + BG_WINDOW_BORDER_SIZE;
    int ys = y + bgWinY + BG_WINDOW_HEADER_SIZE;
    graphics.setColor(new Color(1f, 1f, 1f, 0.5f)); // Data BackGround
    graphics.fillRoundRect(xs, 
                           ys, 
                           bgWinW - (2 * BG_WINDOW_BORDER_SIZE), 
                           bgWinH - ((2 * BG_WINDOW_BORDER_SIZE) + BG_WINDOW_HEADER_SIZE), 
                           10, 10);
    
    // Win Title here
    if (winTitle != null)
    {
      graphics.setFont(titleFont);
      graphics.setColor(Color.white); 
      int baseLine = BG_WINDOW_TITLE_MIN_BASELINE;
      if ((titleFont.getSize() + 2) > baseLine)
        baseLine = titleFont.getSize() + 2;
      if (buttonAlignment == BUTTON_ALIGNED_RIGHT)      
        graphics.drawString(winTitle, x + bgWinX + BG_WINDOW_TITLE_OFFSET, y + bgWinY + baseLine);
      else
        graphics.drawString(winTitle, x + bgWinX + BG_WINDOW_TITLE_OFFSET + ((withZoomButtons?3:1) * imageWidth), y + bgWinY + baseLine);
    }
    if (dataFont.getName().equals("DS-Digital"))
      dataString = DesktopUtilities.replaceString(DesktopUtilities.replaceString(dataString, "º", " "), "°", " ");
    // Draw Data Here
    drawData(graphics, dataString, xs, ys);
  }
  
  private int[] alignment = null;
  
  public void setAlignment(int[] ia)
  {
    this.alignment = ia;
  }
  
  private final static int BETWEEN_COLS_IN_TABLE = 10;
  
  public void drawData(Graphics graphics, String dataString, int xs, int ys)
  {
    if (dataString != null)
    {
      graphics.setFont(dataFont);
      String[] dataLine = dataString.split("\n");
      
      graphics.setColor(dataFontColor);
      if (dataString.indexOf("\t") > -1)
      {
        List<String[]> table = new ArrayList<String[]>();
        int width = 0;
        for (String s : dataLine)
        {
          String[] cols = s.split("\t");
          width = cols.length;
          table.add(cols);
        }
        String[][] data = new String[dataLine.length][width];
        data = table.toArray(data);
        ys = Utilities.drawPanelTable(data, graphics, new Point(xs + BG_WINDOW_DATA_OFFSET_SIZE, ys + dataFont.getSize()), BETWEEN_COLS_IN_TABLE, 2, alignment);
      }
      else
      {
        for (int i=0; i<dataLine.length; i++)
        {
          graphics.drawString(dataLine[i], 
                              xs + BG_WINDOW_DATA_OFFSET_SIZE, 
                              ys + dataFont.getSize());
          ys += (dataFont.getSize() + 2);
        }
      }
    }
  }
  
  public void calculateWinWidth(Graphics graphics)
  {
    if (recalculateWinDimension)
    {
      int strWidth = graphics.getFontMetrics(titleFont).stringWidth(winTitle);
      if ((strWidth + BG_WINDOW_TITLE_OFFSET + BG_WINDOW_3BUTTON_WIDTH) > bgWinW)
        bgWinW = strWidth + BG_WINDOW_TITLE_OFFSET + BG_WINDOW_3BUTTON_WIDTH;
      if (verbose)
        System.out.println("BG Win W:" + bgWinW);
    }
  }
  
  /*
   * Also sets the Width, bawsed on the dataString
   */
  public void calculateWinHeight(Graphics graphics, String dataString)
  {
    if (recalculateWinDimension)
    {
      graphics.setFont(dataFont);
      String[] dataLine = dataString.split("\n");
      int strHeight = dataFont.getSize();
      int progressWidth = 0;
      if (dataString.indexOf("\t") > -1) // Table
      {
        List<Integer> maxColLength = new ArrayList<Integer>();
        for (int i=0; i<dataLine.length; i++)
        {
          if (dataLine[i].indexOf("\t") > -1)
          {          
            String[] cols = dataLine[i].split("\t");
            int w = 0;
            for (int j=0; j<cols.length; j++)
            {
              if (maxColLength.size() <= j)
                maxColLength.add(0);
              maxColLength.set(j, Math.max(maxColLength.get(j), graphics.getFontMetrics(dataFont).stringWidth(cols[j])));
            }
          }
        }
        for (Integer i : maxColLength)
          progressWidth += (i.intValue() + BETWEEN_COLS_IN_TABLE);
        progressWidth -= BETWEEN_COLS_IN_TABLE;
      }
      else
      {
        for (int i=0; i<dataLine.length; i++)
        {
          int strWidth = graphics.getFontMetrics(dataFont).stringWidth(dataLine[i]);
          progressWidth = Math.max(strWidth, progressWidth);
        }
      }
      if ((progressWidth + (2 * BG_WINDOW_DATA_OFFSET_SIZE) + (2 * BG_WINDOW_BORDER_SIZE)) > bgWinW)
        bgWinW = (progressWidth + (2 * BG_WINDOW_DATA_OFFSET_SIZE) + (2 * BG_WINDOW_BORDER_SIZE));
  
      int nl = dataLine.length;
      if (nl < minNumLine)
        nl = minNumLine;
      if (((nl * (strHeight + 2)) + BG_WINDOW_HEADER_SIZE + (2 * BG_WINDOW_BORDER_SIZE)) > bgWinH)
        bgWinH = ((nl * (strHeight + 2)) + BG_WINDOW_HEADER_SIZE + (2 * BG_WINDOW_BORDER_SIZE));
      if (verbose)
        System.out.println("BG Win H:" + bgWinH);
    }
  }
    
  public void setViewPortDimension(Dimension dim)
  {
    bgWinH = (int)Math.ceil(dim.getHeight()) + BG_WINDOW_HEADER_SIZE + (2 * BG_WINDOW_BORDER_SIZE);  
    bgWinW = (int)Math.ceil(dim.getWidth()) + (2 * BG_WINDOW_BORDER_SIZE);
  }
  
  public boolean isMouseInBGWindow(MouseEvent me)
  {
    boolean resp = false;
    if (displayBGWindow)
    {
      int x = me.getX(), 
          y = me.getY();
      if (x > bgWinX &&
          y > bgWinY &&
          x < (bgWinX + bgWinW) &&
          y < (bgWinY + bgWinH))
        resp = true;
    }
    return resp;
  }
  
  public int isMouseOnBGWindowButton(MouseEvent me)
  {
    int button = 0;
    if (!displayBGWindow)
      return button;
    int x = me.getX(), 
        y = me.getY();
  //  System.out.println("X:" + x + ", Y:" + y + " (winY:" + altTooltipY + ", winX:" + altTooltipX + ", winW:" + altTooltipW);
    if (y > bgWinY + 7 &&
        y < bgWinY + 21)
    {
      if ((buttonAlignment == BUTTON_ALIGNED_RIGHT && 
          (x < (bgWinX + bgWinW - 3) && 
           x > (bgWinX + bgWinW - (3 + buttonWidth)))) ||
          (buttonAlignment == BUTTON_ALIGNED_LEFT && 
           (x > (bgWinX + ((withZoomButtons?2:0) * imageWidth)) &&
            x < (buttonWidth + bgWinX + ((withZoomButtons?2:0) * imageWidth)))))
      {
//      System.out.println("Close");
        button = CLOSE_IMAGE;
      }
      else if (withZoomButtons && 
               ((buttonAlignment == BUTTON_ALIGNED_RIGHT &&
               (x < (bgWinX + bgWinW - 30) && 
                x > (bgWinX + bgWinW - (30 + buttonWidth)))) ||
               (buttonAlignment == BUTTON_ALIGNED_LEFT &&
                (x > (bgWinX + (imageWidth)) &&
                 x < (buttonWidth + bgWinX + (imageWidth))))))
      {
  //    System.out.println("Expand");
        button = ZOOMEXPAND_IMAGE;
      }
      else if (withZoomButtons && 
               ((buttonAlignment == BUTTON_ALIGNED_RIGHT && 
               (x < (bgWinX + bgWinW - 50) && 
                 x > (bgWinX + bgWinW - (50 + buttonWidth)))) ||
               (buttonAlignment == BUTTON_ALIGNED_LEFT &&
                (x > (bgWinX ) &&
                 x < (buttonWidth + bgWinX)))))
      {
  //    System.out.println("Shrink");
        button = ZOOMSHRINK_IMAGE;
      }
    }
    return button;
  }

  public void setBgWindowBeingDragged(boolean bgWindowBeingDragged)
  {
    this.bgWindowBeingDragged = bgWindowBeingDragged;
  }

  public boolean isBgWindowBeingDragged()
  {
    return bgWindowBeingDragged;
  }

  public void setDragStartX(int dragStartX)
  {
    this.dragStartX = dragStartX;
  }

  public int getDragStartX()
  {
    return dragStartX;
  }

  public void setDragStartY(int dragStartY)
  {
    this.dragStartY = dragStartY;
  }

  public int getDragStartY()
  {
    return dragStartY;
  }

  public void setWinTitle(String winTitle)
  {
    this.winTitle = winTitle;
  }

  public void setDisplayBGWindow(boolean displayBGWindow)
  {
    this.displayBGWindow = displayBGWindow;
  }

  public boolean isDisplayBGWindow()
  {
    return displayBGWindow;
  }

  public void setBgWinX(int bgWinX)
  {
    this.bgWinX = bgWinX;
//  System.out.println("BGWinX:" + bgWinX);
  }

  public int getBgWinX()
  {
    return bgWinX;
  }

  public void setBgWinY(int bgWinY)
  {
    this.bgWinY = bgWinY;
//  System.out.println("BGWinY:" + bgWinY);
  }

  public int getBgWinY()
  {
    return bgWinY;
  }

  public void setBgWinW(int bgWinW)
  {
    this.bgWinW = bgWinW;
  }

  public int getBgWinW()
  {
    return bgWinW;
  }

  public void setBgWinH(int bgWinH)
  {
    this.bgWinH = bgWinH;
  }

  public int getBgWinH()
  {
    return bgWinH;
  }

  public String getWinTitle()
  {
    return winTitle;
  }

  public void setDataFontColor(Color dataFontColor)
  {
    this.dataFontColor = dataFontColor;
  }

  public Color getDataFontColor()
  {
    return dataFontColor;
  }

  public void setMinNumLine(int minNumLine)
  {
    this.minNumLine = minNumLine;
  }

  public void setVerbose(boolean verbose)
  {
    this.verbose = verbose;
  }

  public void setButtonAlignment(int buttonAlignment)
  {
    this.buttonAlignment = buttonAlignment;
  }

  private Font loadDigiFont()
  {
    Font f = null;
    try { f = tryToLoadFont("ds-digi.ttf"); }
    catch (Exception ex) { System.err.println(ex.getMessage()); }
    if (f == null)
      f = new Font("Courier New", Font.BOLD, 12);
    else
      f = f.deriveFont(Font.BOLD, 12);
    return f;
  }

  public Font tryToLoadFont(String fontName) 
  {
    final String RESOURCE_PATH = "resources" + "/"; // A slash! Not File.Separator, it is a URL.
    try 
    {
      String fontRes = RESOURCE_PATH + fontName;
      Class cls = // this.getClass();
                  BackgroundWindow.class;
      InputStream fontDef = // cls.getResourceAsStream(fontRes);
                            cls.getResourceAsStream(fontRes);
      if (fontDef == null) 
      {
        throw new NullPointerException("Could not find font resource \"" + fontName +
                                       "\"\n\t\tin \"" + fontRes +
                                       "\"\n\t\tfor \"" + cls.getName() +
                                       "\"\n\t\ttry: " + cls.getResource(fontRes));
      } 
      else
        return Font.createFont(Font.TRUETYPE_FONT, fontDef);
    } 
    catch (FontFormatException e) 
    {
      System.err.println("getting font " + fontName);
      e.printStackTrace();
    } 
    catch (IOException e) 
    {
      System.err.println("getting font " + fontName);
      e.printStackTrace();
    }
    return null;
  }

  public void setRecalculateWinDimension(boolean recalculateWinDimension)
  {
    this.recalculateWinDimension = recalculateWinDimension;
  }

  public void setWithZoomButtons(boolean withZoomButtons)
  {
    this.withZoomButtons = withZoomButtons;
  }
}
