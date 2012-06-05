package olivsoftdesktop.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.server.UnicastRemoteObject;

import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import rmi.nmea.rmiserver.NMEAServer;

public class RMI_NMEAServerManager
{
  private NMEAServer nmeaServer = null;
  private Registry registry     = null;
  private int registryPort      = 1099;

  public RMI_NMEAServerManager()
  {
    super();
  }
  
  public void startServer()
  {
    JFormattedTextField portField = new JFormattedTextField(new DecimalFormat("####0"));
    portField.setHorizontalAlignment(JTextField.CENTER);
    portField.setText(Integer.toString(registryPort));
    JOptionPane.showMessageDialog(null, portField, "RMI Port Number", JOptionPane.QUESTION_MESSAGE);
    try { registryPort = Integer.parseInt(portField.getText()); } 
    catch (NumberFormatException nfe) 
    {
      System.out.println(nfe.toString());
      System.out.println("Reseting to " + Integer.toString(registryPort));  
    }
    startServer(registryPort);
  }
  
  public void startServer(int port)
  {
    registryPort = port;
    // Start registry first
    try
    {
      registry = LocateRegistry.createRegistry(registryPort);
      System.out.println("Registry started");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    try
    {
      nmeaServer = new NMEAServer();
      nmeaServer.startServer(registryPort, "nmea"); // TODO Parameters
      System.out.println("Server started...");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "RMI Server won't start.\nIs the rmiregistry running?", "RMI Server", JOptionPane.WARNING_MESSAGE);
    }
  }  
  
  public void stopServer()
  {
    try { nmeaServer.stopServer(); }
    catch (Exception ex) { ex.printStackTrace(); }
    
    try
    {
      System.out.println("Stopping registry");
      UnicastRemoteObject.unexportObject(registry, true);
      System.out.println("Stopped");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
