

package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.StackPage;

public class AreYouReady extends UI  {



public AreYouReady(Main main) throws MalformedURLException {

super(main);

page = StackPage.SHIPREADY;

dialog = new JDialog(main, "");


dialog.setPreferredSize(new Dimension(500,500));

dialog.setLocation(Main.getPopUpLocation(this));

dialog.getContentPane().setLayout(new FlowLayout());
JLabel label = new JLabel("Are you ready?");
label.setFont(new Font("Arial", Font.BOLD, 50));
dialog.setBackground(Color.black);
label.setBackground(Color.white);




URL url = new URL("http://i.giphy.com/3fe9Bw3Ej4GTS.gif");

    Icon icon = new ImageIcon(url);

    JLabel waiting = new JLabel(icon);

dialog.add(waiting, SwingUtilities.CENTER);


JButton ready = new JButton("READY");

ready.setPreferredSize(new Dimension(80,80));


ready.addMouseListener(new MouseAdapter() {
@Override
public void mouseClicked(MouseEvent e) {

   main.client.startPlaceShip();
     ready.setText("Please wait");
     ready.setEnabled(false);

}
});

dialog.getContentPane().add(label);
dialog.getContentPane().add(waiting);
dialog.getContentPane().add(ready);
dialog.setVisible(true);
dialog.pack();



// TODO JDialog implementation

// dialog.pack();

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