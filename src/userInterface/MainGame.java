package userInterface;

//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import game.Main;
import game.Ship;
import game.Square;
import gameState.StackPage;

public class MainGame extends UI {

	JPanel mainPanel;
	public JTextField sendMSG;
	public JLabel receivedMSG;
	public JLabel showTimer;
	public Timer timer;
	public SquareLabel[][] opponentTable;
	public SquareLabel[][] myTable;
	public JLabel lblTimer;
	public JLabel player1score;
	public JLabel player2score;
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
		
		lblTimer=new JLabel();
		lblTimer.setText("END");
		lblTimer.setFont(new Font("Courier New", Font.BOLD, 40));
		lblTimer.setForeground(Color.WHITE);
		west1.add(lblTimer, BorderLayout.SOUTH);
		

		JPanel west2 = new JPanel();
		west2.setPreferredSize(new Dimension(400, 100));
		west2.setOpaque(false);
		west.add(west2);

		ImageIcon opponentpic = Main.createImageWithSize("character/player1b.png", 70, 70);
		JButton buttonopponent = new JButton("");
		buttonopponent.setIcon(opponentpic);
		buttonopponent.setPreferredSize(new Dimension(70, 70));
		west2.add(buttonopponent);
		
		JLabel player1 = new JLabel("opponent"+"'s score:");
		player1.setFont(new Font("Courier New", Font.BOLD, 24));
		player1.setForeground(Color.WHITE);
		west2.add(player1);
		
		player1score = new JLabel("0");
		player1score.setFont(new Font("Courier New", Font.BOLD, 24));
		player1score.setForeground(Color.WHITE);
		west2.add(player1score);
		

		JPanel west3 = new JPanel();
		west3.setPreferredSize(new Dimension(400, 400));
		west3.setOpaque(false);
		west.add(west3, BorderLayout.SOUTH);

		GridLayout tableLayout = new GridLayout(8, 8);
		west3.setLayout(tableLayout);

		opponentTable = new SquareLabel[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				SquareLabel squareLabel = new SquareLabel(this.main);
				// squareLabel.setName(y + "," + x);
				squareLabel.setPosition(y, x);
				squareLabel.setMyCurrentTableUI();
				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				squareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				squareLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (main.clientThread.isMyTurn()) { 
							SquareLabel squareLabel = (SquareLabel) e.getSource();
							int y = squareLabel.y;
							int x = squareLabel.x;
							if (opponentTable[y][x].getSquare().isClicked())
								return;
							main.clientThread.attack(y, x);

							main.clientThread.countDownTime.stop();
							lblTimer.setText("END");

						} else
							return; 
					}
				});
				opponentTable[y][x] = squareLabel;
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
		
		JLabel player2 = new JLabel("my"+"'s score:");
		player2.setFont(new Font("Courier New", Font.BOLD, 24));
		player2.setForeground(Color.WHITE);
		east2.add(player2);
		
		player2score = new JLabel("0");
		player2score.setFont(new Font("Courier New", Font.BOLD, 24));
		player2score.setForeground(Color.WHITE);
		east2.add(player2score);

		JPanel east3 = new JPanel();
		east3.setPreferredSize(new Dimension(400, 400));
		east3.setOpaque(false);
		east.add(east3, BorderLayout.SOUTH);

		GridLayout tableLayout2 = new GridLayout(8, 8);
		east3.setLayout(tableLayout2);

		myTable = new SquareLabel[8][8];
		SquareLabel mysquareLabel;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				mysquareLabel = new SquareLabel(this.main);
				// squareLabel.setName(y + "," + x);
				mysquareLabel.setPosition(y, x);
				mysquareLabel.setMyCurrentTableUI();
				mysquareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				mysquareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				myTable[y][x] = mysquareLabel;
				east3.add(mysquareLabel);
			}
		}
		// Set ships in myBoardLabel
		Ship[] ships = main.clientThread.gridTable.getAllShips();
		for (int i = 0; i < 4; i++) {
			Ship ship = ships[i];
			Square[] squares = ship.getSquareOfThisShip();
			for (int j = 0; j < 4; j++) {
				Square square = squares[j];
				if (ship.direction.equals("horizontal")) {
					ImageIcon h = Main.createImageWithSize("boat/horizontal-" + (j + 1) + ".png",50, 50);
					myTable[square.getY()][square.getX()].setIcon(h);
				} else {
					ImageIcon v = Main.createImageWithSize("boat/vertical-" + (j + 1) + ".png", 50, 50);
					myTable[square.getY()][square.getX()].setIcon(v);
				}
			}
		}

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1024, 100));
		south.setOpaque(false);
		mainPanel.add(south, BorderLayout.SOUTH);

		JPanel roomForText = new JPanel();
		
		roomForText.setBackground(Color.PINK);
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