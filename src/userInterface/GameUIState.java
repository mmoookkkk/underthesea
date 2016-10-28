package userInterface;

import javax.swing.JPanel;


import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;


import game.Main;
import game.Main.GameClient;
import gameState.StackPage;
import gameState.StackFunction;
import game.Ship;
import game.Square;
import userInterface.SquareLabel;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

public class GameUIState extends UI {

	public Timer timer;
	public SquareLabel[][] boardLabel;
	public SquareLabel[][] myBoardLabel;
	public JLabel lblTimer;
	public JLabel P1Score;
	public JLabel P2Score;
	int point_opponent;
	public JLabel P2;
	public JTextField sendMSG;
	public JLabel receivedMSG;

	public GameUIState(Main main) {
		super(main);
		page = StackPage.BATTLE;

		panel = paintMainPanel(main.background);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setPreferredSize(new Dimension(1024, 768)); // 768-568-100

		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension(1024, 150));
		panel.add(top, BorderLayout.NORTH);
		top.setLayout(new BorderLayout(0, 0));
		top.setOpaque(false);

		JPanel leftTop = new JPanel();
		JPanel rightTop = new JPanel();
		leftTop.setPreferredSize(new Dimension(150, 100));
		rightTop.setPreferredSize(new Dimension(150, 100));
		leftTop.setOpaque(false);
		rightTop.setOpaque(false);

		JButton logo = new JButton("");
		logo.setIcon(new ImageIcon("logo/logo1.png"));
		top.add(leftTop, BorderLayout.WEST);
		top.add(logo, BorderLayout.CENTER);

		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// logo.setIcon(new ImageIcon("logo/logo1.png"));

				String logo_name = logo.getIcon().toString();
				String num = logo_name.substring(9, 10);
				int mynum = Integer.parseInt(num);

				System.out.println("logo name = " + logo_name);
				System.out.println("string num = " + num);

				int r;
				while (true) {
					// random 1-7
					r = (int) (Math.round(Math.random() * 7) + 1);
					if (r != mynum) {
						break;
					}
				}

				logo.setIcon(new ImageIcon("logo/logo" + r + ".png"));

			}
		});
		JPanel roomForText=new JPanel();
		
		sendMSG = new JTextField();
		sendMSG.setFont(new Font("Avenir", Font.PLAIN, 14));
		sendMSG.setColumns(10);
		roomForText.add(sendMSG, BorderLayout.SOUTH);
		JButton enterbutt=new JButton();
		enterbutt.setText("ENTER");
		roomForText.add(enterbutt,BorderLayout.WEST);
		
		enterbutt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String toBeSend=sendMSG.getText();
				main.client.sendMessage(toBeSend);
				sendMSG.setText("");
			}
		});
		
		
		receivedMSG = new JLabel();
		receivedMSG.setText("from your opponent!");
		receivedMSG.setForeground(Color.WHITE);
		receivedMSG.setFont(new Font("Arial", Font.BOLD, 20));
		roomForText.add(receivedMSG,BorderLayout.NORTH);
	    top.add(roomForText, BorderLayout.SOUTH);
		top.add(rightTop, BorderLayout.EAST);
		
		
		

	

		/* LEFT BORDER */
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(150, 568));
		panel.add(west, BorderLayout.WEST);
		west.setLayout(new BorderLayout(0, 0));
		west.setOpaque(false);

		/* CENTER */
		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(724, 568));
		panel.add(center, BorderLayout.CENTER);
		center.setLayout(new BorderLayout(0, 0));
		center.setOpaque(false);

		/* PLAYER1 TABLE */
		JPanel leftCol = new JPanel();
		leftCol.setPreferredSize(new Dimension(300, 568));
		
		center.add(leftCol, BorderLayout.WEST);

		leftCol.setOpaque(false);

		JPanel player1 = paintSubPanel("bg/oceanbg.png", 300, 300);
		player1.setPreferredSize(new Dimension(300, 300));

		JPanel topP1 = new JPanel();
		topP1.setPreferredSize(new Dimension(300, 100));
		topP1.setOpaque(false);

		JLabel lblPlaceYourShip = new JLabel("HIT THE OPPONENT SHIPS!");
		lblPlaceYourShip.setForeground(Color.WHITE);
		lblPlaceYourShip.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPlaceYourShip.setFont(new Font("Avenir", Font.BOLD, 20));
		lblPlaceYourShip.setHorizontalAlignment(SwingConstants.LEFT);
		topP1.add(lblPlaceYourShip);

		JPanel bottomP1 = new JPanel();
		bottomP1.setPreferredSize(new Dimension(300, 168));
		bottomP1.setOpaque(false);

		leftCol.setLayout(new BorderLayout(0, 0));
		leftCol.add(topP1, BorderLayout.NORTH);
		topP1.setLayout(new GridLayout(1, 0, 0, 0));
		leftCol.add(player1, BorderLayout.CENTER);
		leftCol.add(bottomP1, BorderLayout.SOUTH);
		GridLayout tableLayout = new GridLayout(8, 8);
		player1.setLayout(tableLayout);

		// Create the playing board label
		boardLabel = new SquareLabel[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				SquareLabel squareLabel = new SquareLabel(this.main);
//				squareLabel.setName(y + "," + x);
				squareLabel.setPosition(y,x);
				squareLabel.setAttackingTableUI();
				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				squareLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
				squareLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (isMarkingEnabled()) { // If it is the player's turn
							SquareLabel squareLabel = (SquareLabel) e.getSource();
							int y = squareLabel.y;
							int x = squareLabel.x;
							System.out.println("Marked: " + boardLabel[y][x].getSquare().isClicked());
							System.out.println("Occupied: " + boardLabel[y][x].getSquare().hasShip());
							if (boardLabel[y][x].getSquare().isClicked())
								return;
							main.client.mark(y, x);

							
							//
							// sirawich
							point_opponent = 0;
							for (int i = 0; i < 7; i++) {
								for (int j = 0; j < 7; j++) {
									// if(client.boardGame.board[i][j].isMarked()){
									if (main.client.gridTable.myCurrentTable[i][j].isClicked()) {
										point_opponent += 1;
										
									

									}
								}
							}
							// P2Score
							P2Score.setText("" + point_opponent);

							//

							main.client.timer_turn_duration.stop();
							lblTimer.setText("END");

							// Game client will update the gui
						} else
							return; // do nothing
					}
				});
				boardLabel[y][x] = squareLabel;
				player1.add(squareLabel);
			}
		}

		/*
		 * L[0].addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { L[0].setText("1");
		 * }
		 * 
		 * });
		 * 
		 * L[1].addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { L[1].setText("1");
		 * }
		 * 
		 * });
		 */

		/* CENTER GAP */
		JPanel centerCol = new JPanel();
		centerCol.setPreferredSize(new Dimension(124, 300));
		center.add(centerCol, BorderLayout.CENTER);
		centerCol.setOpaque(false);

		/* PLAYER2 TABLE */
		JPanel rightCol = new JPanel();
		rightCol.setPreferredSize(new Dimension(300, 618)); // 568+50
		center.add(rightCol, BorderLayout.EAST);
		rightCol.setOpaque(false);

		JPanel statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(300, 50));
		statusPanel.setLayout(new BorderLayout(0, 0));
		JPanel topP2 = new JPanel();
		topP2.setOpaque(false);

		JPanel bottomP2 = new JPanel();
		bottomP2.setPreferredSize(new Dimension(300, 168));
		bottomP2.setOpaque(false);

		JPanel player2 = new JPanel();
		player2.setPreferredSize(new Dimension(300, 300));
		player2.setOpaque(false);

		rightCol.setLayout(new BorderLayout(0, 0));
		rightCol.add(topP2, BorderLayout.NORTH);

		topP2.setLayout(new BorderLayout(0, 0));

		JPanel rightTopP2 = new JPanel();
		rightTopP2.setBorder(new LineBorder(null, 1, true));
		rightTopP2.setBackground(SystemColor.control);
		rightTopP2.setPreferredSize(new Dimension(250, 40));
		topP2.add(rightTopP2, BorderLayout.EAST);

		JPanel gap2 = new JPanel();
		gap2.setPreferredSize(new Dimension(220, 10));
		gap2.setOpaque(false);
		topP2.add(gap2, BorderLayout.SOUTH);

		rightTopP2.setLayout(new GridLayout(1, 4, 0, 0));

		JLabel status = new JLabel("TIMER:");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setFont(new Font("Avenir", Font.PLAIN, 12));
		rightTopP2.add(status);

		lblTimer = new JLabel("END");
		lblTimer.setHorizontalAlignment(SwingConstants.LEFT);
		rightTopP2.add(lblTimer);
		JButton reset = new JButton("RESET");
		rightTopP2.add(reset);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.client.resetGame();
			}
		});
		
		/////////////////////////////////// sirawich
		/*
		 * ActionListener timerTask = new ActionListener() {
		 * 
		 * int countdown = 5;
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * if(countdown==0){ lblMinsec.setText("END"); timer.stop();
		 * //System.out.println("end"); }else{ lblMinsec.setText(countdown+"");
		 * countdown--; } } }; timer = new Timer(1000, timerTask);
		 * timer.start();
		 */

		///////////////////////////////////
		rightCol.add(player2, BorderLayout.CENTER);
		rightCol.add(bottomP2, BorderLayout.SOUTH);

		// score
		player2.setLayout(new BorderLayout(0, 0));
		JPanel northPlayer2 = new JPanel();
		northPlayer2.setPreferredSize(new Dimension(300, 100));
		player2.add(northPlayer2, BorderLayout.NORTH);
		northPlayer2.setLayout(new BorderLayout(0, 0));
		northPlayer2.setOpaque(false);

		JPanel clientPanel = new JPanel();
		clientPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		JPanel serverPanel = new JPanel();
		serverPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		JPanel gap4 = new JPanel(); // vertical gap
		gap4.setOpaque(false);
		JPanel gap5 = new JPanel(); // horizontal gap
		gap5.setOpaque(false);
		JPanel scorePanel = new JPanel();
		scorePanel.setPreferredSize(new Dimension(250, 90));
		scorePanel.setOpaque(false);
		clientPanel.setPreferredSize(new Dimension(100, 90));
		serverPanel.setPreferredSize(new Dimension(100, 90));
		gap4.setPreferredSize(new Dimension(50, 10));
		gap5.setPreferredSize(new Dimension(300, 10));
		scorePanel.setLayout(new BorderLayout(0, 0));
		northPlayer2.add(gap4, BorderLayout.WEST);
		northPlayer2.add(scorePanel, BorderLayout.CENTER);
		northPlayer2.add(gap5, BorderLayout.SOUTH);
		scorePanel.add(clientPanel, BorderLayout.WEST);
		scorePanel.add(serverPanel, BorderLayout.EAST);

		// Client
		clientPanel.setLayout(new BorderLayout(0, 0));
		JPanel nameClient = new JPanel();
		nameClient.setBackground(Color.GRAY);
		nameClient.setPreferredSize(new Dimension(100, 30));
		clientPanel.add(nameClient, BorderLayout.NORTH);

		JLabel lblClient = new JLabel(main.player.getName());
		lblClient.setVerticalAlignment(SwingConstants.TOP);
		lblClient.setFont(new Font("Avenir", Font.PLAIN, 12));
		lblClient.setHorizontalAlignment(SwingConstants.CENTER);
		nameClient.add(lblClient);

		JPanel client = new JPanel();
		client.setPreferredSize(new Dimension(100, 60));
		client.setBackground(Color.WHITE);
		client.setLayout(new BorderLayout(0, 0));
		clientPanel.add(client, BorderLayout.SOUTH);

		JPanel profileClient = new JPanel();
		profileClient.setPreferredSize(new Dimension(60, 60));
		JLabel P1 = new JLabel();

		Image img = main.player.getProfilePhoto().getImage();
		Image newimg = img.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		P1.setIcon(new ImageIcon(newimg));

		profileClient.add(P1);
		client.add(profileClient, BorderLayout.WEST);

		JPanel scoreClient = new JPanel();
		scoreClient.setPreferredSize(new Dimension(40, 60));
		client.add(scoreClient, BorderLayout.EAST);
		scoreClient.setLayout(new BorderLayout(0, 0));

		P1Score = new JLabel("0");
		P1Score.setHorizontalAlignment(SwingConstants.CENTER);
		scoreClient.add(P1Score, BorderLayout.CENTER);

		// Server
		serverPanel.setLayout(new BorderLayout(0, 0));
		JPanel nameServer = new JPanel();
		nameServer.setBackground(Color.GRAY);
		nameServer.setPreferredSize(new Dimension(100, 30));
		serverPanel.add(nameServer, BorderLayout.NORTH);

		JPanel server = new JPanel();
		server.setPreferredSize(new Dimension(100, 60));
		server.setBackground(Color.WHITE);
		server.setLayout(new BorderLayout(0, 0));
		serverPanel.add(server, BorderLayout.SOUTH);

		JLabel lblServer = new JLabel(main.client.opponentName);
		lblServer.setVerticalAlignment(SwingConstants.TOP);
		lblServer.setFont(new Font("Avenir", Font.PLAIN, 12));
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		nameServer.add(lblServer);

		JPanel scoreServer = new JPanel();
		scoreServer.setPreferredSize(new Dimension(40, 60));
		server.add(scoreServer, BorderLayout.WEST);
		scoreServer.setLayout(new BorderLayout(0, 0));

		P2Score = new JLabel("0");
		P2Score.setHorizontalAlignment(SwingConstants.CENTER);
		scoreServer.add(P2Score, BorderLayout.CENTER);

		// P1Score = new JLabel("0");
		// P1Score.setHorizontalAlignment(SwingConstants.CENTER);
		// scoreClient.add(P1Score, BorderLayout.CENTER);

		JPanel profileServer = new JPanel();
		profileServer.setPreferredSize(new Dimension(60, 60));
		server.add(profileServer, BorderLayout.EAST);
		
		P2 = new JLabel();
		P2.setIcon(Main.createImageIcon(main.client.opponentPic, 60, 60));
		System.out.print("test name" + main.client.opponentPic);
	
		profileServer.add(P2);
		JPanel vsPanel = new JPanel();
		vsPanel.setPreferredSize(new Dimension(70, 90));
		vsPanel.setOpaque(false);
		scorePanel.add(vsPanel, BorderLayout.CENTER);

		JLabel lblVs = new JLabel("VS");
		lblVs.setForeground(Color.WHITE);
		lblVs.setHorizontalAlignment(SwingConstants.CENTER);
		lblVs.setFont(new Font("Avenir", Font.PLAIN, 13));
		vsPanel.add(lblVs);

		// Image background =
		// Toolkit.getDefaultToolkit().createImage("Background.png");
		// profileClient.drawImage(background, 0, 0, null);

		JPanel gap3 = new JPanel();
		gap3.setOpaque(false);
		gap3.setPreferredSize(new Dimension(50, 250));
		JPanel southPlayer2 = paintSubPanel("bg/oceanbg.png", 300, 300);
		southPlayer2.setPreferredSize(new Dimension(250, 250));
		player2.add(gap3, BorderLayout.WEST);
		player2.add(southPlayer2, BorderLayout.CENTER);
		southPlayer2.setLayout(new GridLayout(8, 8, 0, 0));

		// Create player's own board label
		myBoardLabel = new SquareLabel[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				SquareLabel squareLabel = new SquareLabel(this.main);
//				squareLabel.setName(y + "," + x);
				squareLabel.setPosition(y,x);
				squareLabel.setMyCurrentTableUI();
				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				squareLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
				// Set own ship
				myBoardLabel[y][x] = squareLabel;
				southPlayer2.add(squareLabel);
			}
		}

		// Set ships in myBoardLabel
		for (Ship ship : main.client.gridTable.getAllShips()) {
			int i = 1;
			for (Square square : ship.getSquareOfThisShip()) {
				if (ship.direction.equals("horizontal")) {
					myBoardLabel[square.getY()][square.getX()].setIcon(
							new ImageIcon("ship/horizontal/ship" + (ship.shipNumber + 1) + "" + (i++) + ".png"));
				} else {
					myBoardLabel[square.getY()][square.getX()]
							.setIcon(new ImageIcon("ship/vertical/ship" + (ship.shipNumber + 1) + "" + (i++) + ".png"));
				}
			}
		}

		/* RIGHT BORDER */
		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(150, 300));
		panel.add(east, BorderLayout.EAST);
		east.setLayout(new BorderLayout(0, 0));
		east.setOpaque(false);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(1024, 50));
		bottom.setOpaque(false);
		panel.add(bottom, BorderLayout.SOUTH);
	}

	public boolean isMarkingEnabled() {
		return main.client.isMyTurn();
	}

	public static ImageIcon createImageIcon(String path, int width, int height) {
		Image img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
		}
		Image resizedImage = img.getScaledInstance(width, height, 0);
		return new ImageIcon(resizedImage);
	}

	@Override
	public void launch() {
		System.out.println(Thread.currentThread().getName() + ": entered " + page);
		main.replaceCurrentPanel(panel);
		main.setEnabled(true);
	}

	@Override
	public void leave() {
		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
		main.setEnabled(false);
	}


	

}
