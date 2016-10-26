package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.GameState;

public class WaitForConnectionUIState extends UI {
 
 //Test purpose
 public WaitForConnectionUIState() {
  stateString = GameState.WAIT_FOR_CONNECTION_STATE;
 }
 /**
  * @throws MalformedURLException 
  * @wbp.parser.constructor
  */
 public WaitForConnectionUIState(Main main) throws MalformedURLException {

  super(main);

  stateString = GameState.WAIT_FOR_CONNECTION_STATE;

  dialog = new JDialog(main, "");

  dialog.setSize(new Dimension(300,300));

  dialog.setPreferredSize(new Dimension(300,300));

  dialog.setLocation(Main.getPopUpLocation(this));

  initialize();

  }


  private void initialize() throws MalformedURLException {

  dialog.getContentPane().setLayout(new FlowLayout());

  dialog.setTitle("Waiting for connection");

  URL url = new URL("http://i.giphy.com/9xhU5q2Sb1kWY.gif");

      Icon icon = new ImageIcon(url);

      JLabel waiting = new JLabel(icon);

  dialog.add(waiting, SwingUtilities.CENTER);
  
  dialog.setVisible(true);
  // TODO JDialog implementation

  dialog.pack();

  }
 
 @Override
 public void entered() {
  System.out.println(Thread.currentThread().getName() + ": entered " + stateString);
  dialog.setVisible(true);
  
 }

 @Override
 public void leaving() {
  System.out.println(Thread.currentThread().getName() + ": leaving " + stateString);
  dialog.dispose();
  
 }

 @Override
 public void obscuring() {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void revealed() {
  // TODO Auto-generated method stub
  
 }
 
 
}