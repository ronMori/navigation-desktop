package olivsoftdesktop;

import coreutilities.Utilities;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import olivsoftdesktop.ctx.DesktopContext;

public class DesktopFrame_AboutBoxPanel
  extends JPanel
{
  private JLabel labelTitle = new JLabel();
  private JLabel labelAuthor = new JLabel();
  private JLabel labelCopyright = new JLabel();
  private JLabel labelCompany = new JLabel();
  private GridBagLayout layoutMain = new GridBagLayout();
  private Border border = BorderFactory.createEtchedBorder();
  private JLabel currentDirLabel = new JLabel();
  private JLabel logoLabel = new JLabel();

  public DesktopFrame_AboutBoxPanel()
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

  private void jbInit()
    throws Exception
  {
    this.setLayout( layoutMain );
    this.setBorder( border );
    this.setBackground(Color.white);
    labelTitle.setText("OlivSoft Navigation Desktop " + DesktopContext.VERSION_NUMBER);
    labelAuthor.setText("<html><a href='mailto:olivier@lediouris.net'>olivier@lediouris.net</a></html>");
    labelAuthor.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          try
          {
//          Desktop.getDesktop().mail(new URI("mailto", "olivier@lediouris.net", null)); // ?subject=From the Navigation Desktop"));
            Desktop.getDesktop().mail(new URI("mailto:olivier@lediouris.net?subject=From+the+Navigation+Desktop"));
          }
          catch (Exception ignore)
          {
          }
        }
      });
    labelCopyright.setText("Copyright OlivSoft, 2009 and beyond");
    labelCompany.setText("<html><a href='http://code.google.com/p/navigation-desktop/'>The Navigation Desktop Project</a></html>");
    labelCompany.addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent e)
        {
          try
          {
            Utilities.openInBrowser("http://code.google.com/p/navigation-desktop/");
          }
          catch (Exception ignore)
          {
          }
        }
      });
    String currDir = new File(".").getAbsolutePath();      
    currentDirLabel.setText("Running from " + currDir);
    logoLabel.setIcon(new ImageIcon(this.getClass().getResource("logo.jpg")));

    this.add( labelTitle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(5, 15, 0, 15), 0, 0));
    this.add( labelAuthor, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 15, 0, 15), 0, 0));
    this.add( labelCopyright, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 15, 0, 15), 0, 0));
    this.add( labelCompany, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 15, 5, 15), 0, 0));
    this.add(currentDirLabel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
          new Insets(0, 15, 0, 0), 0, 0));
    this.add(logoLabel, new GridBagConstraints(0, 0, 1, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
          new Insets(0, 0, 0, 0), 0, 0));
  }
}
