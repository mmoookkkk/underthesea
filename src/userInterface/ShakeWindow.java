package userInterface;

//
import game.Main;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ShakeWindow {
  JFrame frame;
  Point naturalLocation;
  Timer shakeTimer;

  public ShakeWindow(Main main) {
	  this.frame=main;
	  startShake();
  }

  public void startShake() {
    final long startTime;
    
    naturalLocation = frame.getLocation();
    startTime = System.currentTimeMillis();
    shakeTimer = new Timer(5, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        double TWO_PI = Math.PI * 2.0;
        double SHAKE_CYCLE = 50;

        long elapsed = System.currentTimeMillis() - startTime;
        double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
        double angle = waveOffset * TWO_PI;

        int SHAKE_DISTANCE = 10;

        int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
        frame.setLocation(shakenX, naturalLocation.y);
        frame.repaint();

        int SHAKE_DURATION = 1000;
        if (elapsed >= SHAKE_DURATION)
          stopShake();
      }
    });
    shakeTimer.start();
  }

  public void stopShake() {
    shakeTimer.stop();
    frame.setLocation(naturalLocation);
    frame.repaint();
  }



}