package userInterface;
import java.awt.Dimension;


import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.StackPage;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.StackPage;

public class WaitForOpponentReadyUIState extends UI {
 
 public WaitForOpponentReadyUIState(Main main) throws MalformedURLException {
  super(main);
  page = StackPage.WAITFOROPPONENT;

    dialog = new JDialog(main, "");


    dialog.setPreferredSize(new Dimension(350,270));

    dialog.setLocation(Main.getPopUpLocation(this));

    dialog.getContentPane().setLayout(new FlowLayout());

    dialog.setTitle("Waiting for opponent");

    URL url = new URL("http://i.giphy.com/13uaMxgBhGP9ba.gif");

        Icon icon = new ImageIcon(url);

        JLabel waiting = new JLabel(icon);

    dialog.add(waiting, SwingUtilities.CENTER);
    
    dialog.setVisible(true);
    // TODO JDialog implementation

    dialog.pack();
  
 }
 
 @Override
 public void launch() {
  System.out.println(Thread.currentThread().getName() + ": entered " + page);
  dialog.setVisible(true);
  
 }

 @Override
 public void leave() {
  System.out.println(Thread.currentThread().getName() + ": leaving " + page);
  dialog.dispose();
  
 }



}