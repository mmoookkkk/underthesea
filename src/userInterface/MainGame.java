package userInterface;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import game.Main;
import gameState.StackPage;


public class MainGame extends UI {

 JPanel mainPanel;
 public JTextField sendMSG;
 public JLabel receivedMSG;
 public JLabel showTimer;
 public Timer timer;
 public SquareLabel[][] oponentTable;
 public SquareLabel[][] myTable;
 public JLabel lblTimer;
 public JLabel P1Score;
 public JLabel P2Score;
 int point_opponent;
 public JLabel P2;

 public MainGame(Main main) {
  super(main);
  page = StackPage.BATTLE;

  mainPanel = paintMainPanel(main.backgroundImage);
  mainPanel.setLayout(new BorderLayout(0, 0));
  mainPanel.setPreferredSize(new Dimension(1024, 768));

  JPanel north = new JPanel();
  north.setPreferredSize(new Dimension(1024, 668));
  north.setOpaque(false);
  mainPanel.add(north, BorderLayout.NORTH);
  
  JPanel west = new JPanel();
  west.setPreferredSize(new Dimension(400, 668));
  west.setOpaque(false);
  north.add(west, BorderLayout.WEST);

  JPanel west1 = new JPanel();
  west1.setPreferredSize(new Dimension(400, 100));
  west1.setOpaque(false);
  west.add(west1, BorderLayout.NORTH);

  ImageIcon timer1 = Main.createImageWithSize("text/timer.png", 100, 30);
  JLabel buttontimer = new JLabel("");
  buttontimer.setIcon(timer1);
  west1.add(buttontimer, BorderLayout.WEST);
  
  JPanel west2 = new JPanel();
  west2.setPreferredSize(new Dimension(400, 100));
  west2.setOpaque(false);
  west.add(west2);

  ImageIcon opponentpic = Main.createImageWithSize("character/player1b.png", 70, 70);
  JButton buttonopponent = new JButton("");
  buttonopponent.setIcon(opponentpic);
  buttonopponent.setPreferredSize(new Dimension(70, 70));
  west2.add(buttonopponent);
  
  JPanel west3 = new JPanel();
  west3.setPreferredSize(new Dimension(400,400 ));
  west3.setOpaque(false);
  west.add(west3, BorderLayout.SOUTH);
  
  GridLayout tableLayout = new GridLayout(8, 8);
  west3.setLayout(tableLayout);

  oponentTable = new SquareLabel[8][8];
  for (int y = 0; y < 8; y++) {
   for (int x = 0; x < 8; x++) {
    SquareLabel squareLabel = new SquareLabel(this.main);
    // squareLabel.setName(y + "," + x);
    squareLabel.setPosition(y, x);
    squareLabel.setMyCurrentTableUI();
    squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
    squareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
    oponentTable[y][x] = squareLabel;
    west3.add(squareLabel);
   }
  }
  
  

  JPanel east = new JPanel();
  east.setPreferredSize(new Dimension(400, 668));
  east.setOpaque(false);
  north.add(east, BorderLayout.EAST);
  
  JPanel east1 = new JPanel();
  east1.setPreferredSize(new Dimension(400, 100));
  east1.setOpaque(false);
  east.add(east1, BorderLayout.NORTH);
  
  
  ImageIcon shake = Main.createImageWithSize("button/button-shake.png", 100, 50);
  JButton buttonshake = new JButton("");
  buttonshake.setIcon(shake);

  
  buttonshake.setPreferredSize(new Dimension(100, 100));
  buttonshake.setOpaque(false);
  buttonshake.setContentAreaFilled(false);
  buttonshake.setBorderPainted(false);
  east1.add(buttonshake);
	buttonshake.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		main.clientThread.shakeOpponent();
		}
	});
  
  JPanel east2 = new JPanel();
  east2.setPreferredSize(new Dimension(400, 100));
  east2.setOpaque(false);
  east.add(east2, BorderLayout.CENTER);
  
  ImageIcon mypic = Main.createImageWithSize("character/player2b.png", 70, 70);
  JButton buttonmypic = new JButton("");
  buttonmypic.setIcon(mypic);
  buttonmypic.setPreferredSize(new Dimension(70, 70));
  east2.add(buttonmypic);
  
  JPanel east3 = new JPanel();
  east3.setPreferredSize(new Dimension(400, 400));
  east3.setOpaque(false);
  east.add(east3, BorderLayout.SOUTH);
  
  GridLayout tableLayout2 = new GridLayout(8, 8);
  east3.setLayout(tableLayout2);

  oponentTable = new SquareLabel[8][8];
  for (int y = 0; y < 8; y++) {
   for (int x = 0; x < 8; x++) {
    SquareLabel squareLabel = new SquareLabel(this.main);
    // squareLabel.setName(y + "," + x);
    squareLabel.setPosition(y, x);
    squareLabel.setMyCurrentTableUI();
    squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
    squareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
    oponentTable[y][x] = squareLabel;
    east3.add(squareLabel);
   }
  }
  

  JPanel south = new JPanel();
  south.setPreferredSize(new Dimension(1024, 100));
  south.setOpaque(false);
  mainPanel.add(south, BorderLayout.SOUTH);

  JPanel roomForText = new JPanel();
  sendMSG = new JTextField();
  sendMSG.setFont(new Font("Avenir", Font.PLAIN, 14));
  sendMSG.setColumns(10);
  roomForText.add(sendMSG, BorderLayout.SOUTH);
  JButton enterbutt = new JButton();
  enterbutt.setText("ENTER");
  roomForText.add(enterbutt, BorderLayout.WEST);

  enterbutt.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
    String toBeSend = sendMSG.getText();
    main.clientThread.sendMessage(toBeSend);
    sendMSG.setText("");
   }
  });

  receivedMSG = new JLabel();
  receivedMSG.setText("from your opponent!");
  receivedMSG.setForeground(Color.WHITE);
  receivedMSG.setFont(new Font("Arial", Font.BOLD, 20));
  roomForText.add(receivedMSG, BorderLayout.NORTH);

  south.add(roomForText, BorderLayout.CENTER);

 }

 @Override
 public void launch() {
  System.out.println(Thread.currentThread().getName() + ": entered " + page);
  main.replaceCurrentPanel(mainPanel);
  main.setEnabled(true);
 }

 @Override
 public void leave() {
  System.out.println(Thread.currentThread().getName() + ": leaving " + page);
  main.setEnabled(false);
 }


 

}