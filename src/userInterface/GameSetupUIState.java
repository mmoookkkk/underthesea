//package userInterface;
//
//import javax.swing.JPanel;
//
//
//
//import javax.swing.JTextArea;
//
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//
//import javax.swing.JButton;
//import javax.imageio.ImageIO;
//import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//
//import javax.swing.SwingConstants;
//import javax.swing.SwingUtilities;
//
//import java.awt.FlowLayout;
//import java.awt.Color;
//import javax.swing.JTextField;
//import javax.swing.JFormattedTextField;
//import javax.swing.JFrame;
//import javax.swing.JTextPane;
//import javax.swing.border.LineBorder;
//
//import game.Main;
//import game.Ship;
//import game.Square;
//import gameState.StackPage;
//
//import java.awt.SystemColor;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//import java.io.File;
//import java.io.IOException;
//
//import javax.swing.border.BevelBorder;
//import javax.swing.border.CompoundBorder;
//
//public class GameSetupUIState extends UI {
//
//	public JPanel panel;
//	public SquareLabel[][] myBoardLabel;
//	// Ship setting
//	public SquareLabel[] highlighting;
//	public boolean shipPlacingEnabled;
//	public String shipPlacingDirection;
//	public int shipNumber;
//	public JButton readyButton;
//	public JButton cancelButton;
//	public JLabel lblPressReady;
//	public JLabel p2,b1,b2;
//
//	public GameSetupUIState(Main main) {
//		super(main);
//		page = StackPage.PLACEYOURSHIP;
//		shipPlacingEnabled = false;
//		shipPlacingDirection = "vertical"; // SHIPDIRECTION
//		shipNumber = 0;
//		initialize();
//	}
//
//	public void initialize() {
//
//		panel = paintMainPanel(main.background);
//		panel.setLayout(new BorderLayout(0, 0));
//		panel.setPreferredSize(new Dimension(1024, 768));
//
//		// Top panel
//		JPanel top = new JPanel();
//		top.setPreferredSize(new Dimension(1024, 150));
//		top.setLayout(new BorderLayout(0, 0));
//		top.setOpaque(false);
//		panel.add(top, BorderLayout.NORTH);
//
//		JPanel leftTop = new JPanel();
//		JPanel rightTop = new JPanel();
//		leftTop.setPreferredSize(new Dimension(150, 100));
//		leftTop.setOpaque(false);
//		rightTop.setPreferredSize(new Dimension(150, 100));
//		rightTop.setOpaque(false);
//		rightTop.setLayout(new BorderLayout(0, 0));
//
//		JPanel pp1 = new JPanel();
//		pp1.setPreferredSize(new Dimension(25, 35));
//		pp1.setOpaque(false);
//
//		JPanel pp2 = new JPanel();
//		pp2.setPreferredSize(new Dimension(25, 35));
//		pp2.setOpaque(false);
//
//		// sirawich help
//		JButton pp = new JButton();
//		pp.setIcon(new ImageIcon("btn-help.png"));
//		pp.setPreferredSize(new Dimension(100, 35));
//		pp.setBorderPainted(false);
//		pp.setContentAreaFilled(false);
//		pp.setFocusPainted(false);
//		pp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				JFrame mainFrame = new JFrame("Help");
//				mainFrame.setSize(600, 400);
//				JLabel help = new JLabel(new ImageIcon("bg/howtoplay.png"));
//				mainFrame.add(help);
//				mainFrame.setResizable(false);
//				mainFrame.setLocationRelativeTo(null);
//				mainFrame.setVisible(true);
//
//			}
//		});
//		JPanel pp3 = new JPanel();
//		pp3.setPreferredSize(new Dimension(150, 65));
//		pp3.setOpaque(false);
//		rightTop.add(pp1, BorderLayout.WEST);
//		rightTop.add(pp, BorderLayout.CENTER);
//		rightTop.add(pp2, BorderLayout.EAST);
//		rightTop.add(pp3, BorderLayout.SOUTH);
//
//		// Top logo
//		JButton logo = new JButton("");
//		logo.setIcon(new ImageIcon("logo/logo1.png"));
//
//		logo.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//				// logo.setIcon(new ImageIcon("logo/logo1.png"));
//
//				String logo_name = logo.getIcon().toString();
//				String num = logo_name.substring(9, 10);
//				int mynum = Integer.parseInt(num);
//
//				System.out.println("logo name = " + logo_name);
//				System.out.println("string num = " + num);
//
//				int r;
//				while (true) {
//					// random 1-7
//					r = (int) (Math.round(Math.random() * 7) + 1);
//					if (r != mynum) {
//						break;
//					}
//				}
//
//				logo.setIcon(new ImageIcon("logo/logo" + r + ".png"));
//
//			}
//		});
//
//		top.add(leftTop, BorderLayout.WEST);
//		top.add(logo, BorderLayout.CENTER);
//		top.add(rightTop, BorderLayout.EAST);
//
//		/* LEFT BORDER */
//		JPanel west = new JPanel();
//		west.setPreferredSize(new Dimension(150, 568));
//		panel.add(west, BorderLayout.WEST);
//		west.setLayout(new BorderLayout(0, 0));
//		west.setOpaque(false);
//
//		/* CENTER */
//		JPanel center = new JPanel();
//		center.setPreferredSize(new Dimension(724, 568));
//		panel.add(center, BorderLayout.CENTER);
//		center.setLayout(new BorderLayout(0, 0));
//		center.setOpaque(false);
//
//		/* PLAYER1 TABLE */
//		JPanel leftCol = new JPanel();
//		leftCol.setPreferredSize(new Dimension(300, 568));
//		leftCol.setOpaque(false);
//		center.add(leftCol, BorderLayout.WEST);
//
//		JPanel player1 = new JPanel();
//		player1.setPreferredSize(new Dimension(300, 300));
//		// bg of battle table1
//		player1 = paintSubPanel("bg/oceanbg.png", 300, 300);
//
//		JPanel topP1 = new JPanel(); // Panel for label "Place your ships!"
//		topP1.setPreferredSize(new Dimension(300, 100));
//		JLabel lblPlaceYourShip = new JLabel("PLACE YOUR SHIPS!");
//		lblPlaceYourShip.setForeground(Color.WHITE);
//		lblPlaceYourShip.setVerticalAlignment(SwingConstants.BOTTOM);
//		lblPlaceYourShip.setFont(new Font("Avenir", Font.BOLD, 20));
//		lblPlaceYourShip.setHorizontalAlignment(SwingConstants.LEFT);
//		topP1.add(lblPlaceYourShip);
//		topP1.setOpaque(false);
//
//		JPanel bottomP1 = new JPanel(); // gap bottom of battle table1
//		bottomP1.setPreferredSize(new Dimension(300, 168));
//		bottomP1.setOpaque(false);
//
//		leftCol.setLayout(new BorderLayout(0, 0));
//		leftCol.add(topP1, BorderLayout.NORTH);
//		topP1.setLayout(new GridLayout(1, 0, 0, 0));
//		leftCol.add(player1, BorderLayout.CENTER);
//		leftCol.add(bottomP1, BorderLayout.SOUTH);
//		GridLayout tableLayout = new GridLayout(8, 8);
//		player1.setLayout(tableLayout);
//		// Create JLabel for each square
//
//		myBoardLabel = new SquareLabel[8][8];
//		for (int y = 0; y < 8; y++) {
//			for (int x = 0; x < 8; x++) {
//				SquareLabel squareLabel = new SquareLabel(this.main);
////				squareLabel.setName(y + "," + x);
//				squareLabel.setPosition(y,x);
//				squareLabel.setMyCurrentTableUI();
//				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
//				squareLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
//
//				squareLabel.addMouseListener(new MouseAdapter() {
//					// Mouse clicked
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						if (isShipPlacingEnabled()) {
//							if (e.getButton() == MouseEvent.BUTTON3) { // If it
//																		// is a
//																		// right
//																		// button
//																		// click
//								if (shipPlacingDirection.equals("vertical")) {
//									shipPlacingDirection = "right";
//									// Re-invoke mouse exited and entered on e
//									mouseExited(e);
//									mouseEntered(e);
//								} else {
//									shipPlacingDirection = "vertical";
//									mouseExited(e);
//									mouseEntered(e);
//								}
//							} else { // If it is a left click
//								if (highlighting == null)
//									return; // If highlighting not exist, do
//											// nothing
//								// Check if any of the label in highlighting is
//								// occupied
//								// Create a ship on those squares
//								Ship ship = new Ship(shipNumber, shipPlacingDirection);
//								// Set ship on board game
//								main.client.gridTable.placeShip(ship, shipNumber, highlighting);
//
////								if (success) { // Success -> set ship
//												// graphically
//									int i = 1;
//									for (Square square : ship.getSquareOfThisShip()) {
//										SquareLabel squareLabel = square.getUIOfThisSquare();
//										// TODO set ship icon on the board game
//										// label.setText(shipNumber + "");
//										// label.setIcon(new
//										// ImageIcon("ship1.png"));
//										if (shipPlacingDirection.equals("right")) {
//											squareLabel.setIcon(new ImageIcon(
//													"ship/horizontal/ship" + (shipNumber + 1) + "" + (i++) + ".png"));
//
//										} else {
//											squareLabel.setIcon(new ImageIcon(
//													"ship/vertical/ship" + (shipNumber + 1) + "" + (i++) + ".png"));
//										}
//										// label.setIcon(new
//										// ImageIcon("ship"+(shipNumber+1)+".png"));
//										// //PLACESHIP
//										cancelButton.setEnabled(true);
//									}
//
////									main.insertBGM("sound/ship.wav");
//									try {
//										Thread.sleep(1000);
//									} catch (InterruptedException e1) {
//										// TODO Auto-generated catch block
//										e1.printStackTrace();
//									}
//
//									// Re-invoke mouse exited on e
//									mouseExited(e);
//									if (main.client.gridTable.allShipsReady()) {
//										// main.client.startGame();
//										readyButton.setEnabled(true);
//										lblPressReady.setText("Press Ready !!");
//										main.repaint();
//										main.revalidate();
//									}
//									setShipPlacingEnabled(false);
////								}
//							}
//						}
//						// Else do nothing
//					}
//
//					// Mouse entered a JLabel
//					@Override
//					public void mouseEntered(MouseEvent e) {
//						SquareLabel squareLabel = (SquareLabel) e.getSource();
//						if (isShipPlacingEnabled()) { // If placing mode is
//														// enabled
//							// Search for eligible label to hightlight
//							// Check if any of the label in highlighting is
//							// occupied
//							highlighting = searchForHighlightableLabel(squareLabel);
//							if (highlighting == null)
//								return; // If highlighting not exist, do nothing
//							for (SquareLabel label : highlighting) {
//								label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
//							}
//							/*
//							 * if(main.client.boardGame.checkOccupation(
//							 * highlighting)) { //If one of them already
//							 * occupied, do nothing return; }
//							 */
//							// Else do highlighting
//							for (SquareLabel label : highlighting) {
//								label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
//
//							}
//						}
//					}
//
//					// Mouse left a JLabel
//					@Override
//					public void mouseExited(MouseEvent e) {
//						if (isShipPlacingEnabled()) { // If placing mode is
//														// enabled
//							// Remove highlight from highlighted labels
//							if (highlighting == null)
//								return; // If highlighting not exist, do nothing
//							for (SquareLabel label : highlighting) {
//								label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
//							}
//						}
//					}
//				});
//				myBoardLabel[y][x] = squareLabel;
//				player1.add(squareLabel);
//			}
//		}
//
//		/* CENTER GAP */
//		JPanel centerCol = new JPanel();
//		centerCol.setPreferredSize(new Dimension(124, 300));
//		center.add(centerCol, BorderLayout.CENTER);
//		centerCol.setOpaque(false);
//
//		/* PLAYER2 TABLE */
//		JPanel rightCol = new JPanel();
//		rightCol.setPreferredSize(new Dimension(300, 568));
//		rightCol.setOpaque(false);
//		center.add(rightCol, BorderLayout.EAST);
//
//		/*JPanel statusPanel = new JPanel();
//		statusPanel.setPreferredSize(new Dimension(300, 50));
//		statusPanel.setLayout(new BorderLayout(0, 0));
//		statusPanel.setOpaque(false); 
//		*/
//		
//		JPanel topP2 = new JPanel();
//		topP2.setOpaque(false);
//		topP2.setPreferredSize(new Dimension(300, 50));
//		topP2.setLayout(new BorderLayout(0, 0));
//		rightCol.setLayout(new BorderLayout(0, 0));
//		rightCol.add(topP2, BorderLayout.NORTH);
//
//		JPanel bottomP2 = new JPanel();
//		bottomP2.setPreferredSize(new Dimension(300, 100));
//		bottomP2.setOpaque(false);
//
//		JPanel player2 = new JPanel();
//		player2.setPreferredSize(new Dimension(300, 300));
//		player2.setOpaque(false);
//
//	
//		JPanel rightTopP2 = new JPanel();
//		rightTopP2.setBorder(new LineBorder(null, 1, true));
//		rightTopP2.setPreferredSize(new Dimension(260, 40));
//		topP2.add(rightTopP2, BorderLayout.CENTER);
//
//		JPanel gap2 = new JPanel();
//		gap2.setPreferredSize(new Dimension(260, 10));
//		gap2.setOpaque(false);
//		topP2.add(gap2, BorderLayout.SOUTH);
//
//		JLabel status = new JLabel("STATUS:");
//		status.setHorizontalAlignment(SwingConstants.CENTER);
//		status.setFont(new Font("Avenir", Font.PLAIN, 12));
//		status.setPreferredSize(new Dimension(50,40));
//		
//		rightTopP2.setLayout(new GridLayout(1,3));
//		JPanel statusP1 = new JPanel();
//		statusP1.setPreferredSize(new Dimension(105,40));
//		statusP1.setLayout(new BorderLayout());
//		JPanel statusP2 = new JPanel();
//		statusP2.setLayout(new BorderLayout());
//		statusP2.setPreferredSize(new Dimension(105,40));
//		
//		b1 = new JLabel(main.createImageIcon("notready.png",10,10));
//		//b1.setHorizontalAlignment(SwingConstants.RIGHT);
//		JLabel p1 = new JLabel(main.player.getName());
//		p1.setHorizontalAlignment(SwingConstants.CENTER);
//		//p1.setVerticalAlignment(SwingConstants.CENTER);
//		p1.setFont(new Font("Avenir", Font.PLAIN, 12));
//		b2 = new JLabel(main.createImageIcon("notready.png",10,10));
//		p2 = new JLabel("");
//		p2.setHorizontalAlignment(SwingConstants.CENTER);
//		p2.setFont(new Font("Avenir", Font.PLAIN, 12));
//		
//		statusP1.add(b1,BorderLayout.WEST);
//		statusP1.add(p1,BorderLayout.CENTER);
//		statusP2.add(b2,BorderLayout.WEST);
//		statusP2.add(p2,BorderLayout.CENTER);
//		
//		rightTopP2.add(status);
//		rightTopP2.add(statusP1);
//		rightTopP2.add(statusP2);
//		
//		
//
//		rightCol.add(player2, BorderLayout.CENTER);
//		rightCol.add(bottomP2, BorderLayout.SOUTH);
//
//		player2.setLayout(new BorderLayout(0, 0));
//		JPanel northPlayer2 = new JPanel();
//		northPlayer2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
//		northPlayer2.setPreferredSize(new Dimension(300, 130));
//		player2.add(northPlayer2, BorderLayout.NORTH);
//		northPlayer2.setLayout(new BorderLayout(0, 0));
//		northPlayer2.setOpaque(false);
//
//		/* PLAYER PANEL */
//
//		JPanel namePanel = new JPanel();
//		namePanel.setPreferredSize(new Dimension(190, 30));
//		namePanel.setLayout(new BorderLayout(0, 0));
//		JLabel name = new JLabel(main.player.getName());
//		name.setFont(new Font("Avenir", Font.PLAIN, 13));
//		name.setHorizontalAlignment(SwingConstants.CENTER);
//		namePanel.add(name);
//		namePanel.setBackground(Color.GRAY);
//		northPlayer2.add(namePanel, BorderLayout.NORTH);
//
//		JPanel playerPanel = new JPanel();
//		playerPanel.setPreferredSize(new Dimension(300, 100));
//		northPlayer2.add(playerPanel, BorderLayout.SOUTH);
//		playerPanel.setLayout(new BorderLayout(0, 0));
//
//		JLabel profile = new JLabel(main.player.getProfilePhoto());
//		// profile.setIcon(main.player.getImage());
//		profile.setBackground(Color.GRAY);
//		profile.setPreferredSize(new Dimension(80, 100));
//		playerPanel.add(profile, BorderLayout.WEST);
//
//		JPanel gapCol = new JPanel();
//		gapCol.setPreferredSize(new Dimension(10, 100));
//		playerPanel.add(gapCol, BorderLayout.CENTER);
//
//		JPanel buttonPanel = new JPanel();
//		buttonPanel.setPreferredSize(new Dimension(190, 100));
//		playerPanel.add(buttonPanel, BorderLayout.EAST);
//		buttonPanel.setLayout(new BorderLayout(0, 0));
//
//		JPanel keyButton = new JPanel();
//		keyButton.setPreferredSize(new Dimension(190, 69));
//		buttonPanel.add(keyButton, BorderLayout.CENTER);
//		keyButton.setLayout(new BorderLayout(0, 0));
//
//		cancelButton = new JButton("Reset");
//		cancelButton.setEnabled(false);
//		cancelButton.setFont(new Font("Avenir", Font.PLAIN, 13));
//		cancelButton.setPreferredSize(new Dimension(95, 60));
//		cancelButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				// Check if all ship has been set
//				main.client.gridTable.removeAllShip();
//				cancelButton.setEnabled(false);
//				lblPressReady.setText("");
//			}
//		});
//
//		keyButton.add(cancelButton, BorderLayout.WEST);
//
//		readyButton = new JButton("Ready");
//		readyButton.setEnabled(false);
//		readyButton.setFont(new Font("Avenir", Font.PLAIN, 13));
//		readyButton.setPreferredSize(new Dimension(95, 60));
//		readyButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// Check if all ship has been set
//				System.out.println(main.client.gridTable.allShipsReady());
//				if (main.client.gridTable.allShipsReady()) {
//					main.client.startGame();
//
//				}
//			}
//		});
//		keyButton.add(readyButton, BorderLayout.EAST);
//
//		JPanel gapName = new JPanel();
//		gapName.setPreferredSize(new Dimension(190, 10));
//		keyButton.add(gapName, BorderLayout.NORTH);
//
//		JButton randomButton = new JButton("Random Place");
//		randomButton.setFont(new Font("Avenir", Font.PLAIN, 13));
//		randomButton.setPreferredSize(new Dimension(190, 30));
//		buttonPanel.add(randomButton, BorderLayout.SOUTH);
//		// sirawich
//		randomButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				int y;
//				int x;
//				String direction = null;
//				// Clear all ship
//				main.client.gridTable.removeAllShip();
//				for (int i = 0; i <= 3; i++) {
//					SquareLabel[] position = null;
//					while (position == null) {
//						if (Math.random() < 0.5) {
//							direction = "vertical";
//							y = (int) Math.round(Math.random() * 4);
//							x = (int) Math.round(Math.random() * 7);
//							position = searchForHighlightableLabel(y, x, direction);
//						} else {
//							direction = "horizontal";
//							y = (int) Math.round(Math.random() * 7);
//							x = (int) Math.round(Math.random() * 4);
//							position = searchForHighlightableLabel(y, x, direction);
//						}
//					}
//					Ship ship = new Ship(i, direction);
//					// Set ship on board game
//					main.client.gridTable.placeShip(ship, i, position);
//					int j = 1;
//					for (Square square : ship.getSquareOfThisShip()) {
//						SquareLabel squareLabel = square.getUIOfThisSquare();
//						// TODO set ship icon on the board game
//						// label.setText(shipNumber + "");
//						// label.setIcon(new ImageIcon("ship1.png"));
//						if (direction.equals("horizontal")) {
//							squareLabel.setIcon(new ImageIcon("ship/horizontal/ship" + (i + 1) + "" + (j++) + ".png"));
//
//						} else {
//							squareLabel.setIcon(new ImageIcon("ship/vertical/ship" + (i + 1) + "" + (j++) + ".png"));
//						}
//						// label.setIcon(new
//						// ImageIcon("ship"+(shipNumber+1)+".png"));
//						// //PLACESHIP
//						cancelButton.setEnabled(true);
//						lblPressReady.setText("Press Ready !!");
//						readyButton.setEnabled(true);
//					}
//				}
//			}
//
//		});
//
//		/* SHIP PANEL */
//
//		// logo.setIcon ( new ImageIcon ( "logo.png" ) );
//
//		JPanel shipPanel = new JPanel();
//		shipPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
//
//		shipPanel.setPreferredSize(new Dimension(300, 150));
//		player2.add(shipPanel, BorderLayout.SOUTH);
//		shipPanel.setLayout(new GridLayout(4, 0, 0, 0));
//		JButton ship1 = new JButton("");
//		ship1.setName("ship1");
//		ship1.setIcon(new ImageIcon("ship/ship1.png"));
//		ship1.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JButton shipLabel = (JButton) e.getSource();
//				// Set ship number
//				shipNumber = Integer.parseInt(shipLabel.getName().substring(shipLabel.getName().length() - 1)) - 1;
//				// Clear ship occupation
//				Ship ship = main.client.gridTable.getShip(shipNumber);
//				if (ship != null) { // If there are already ship1 set, clear the
//									// occupation
//					main.client.gridTable.removeShip(ship);
//					readyButton.setEnabled(false);
//					lblPressReady.setText("");
//
//				}
//				// Enable ship placing mode
//				setShipPlacingEnabled(true);
//			}
//		});
//		JButton ship2 = new JButton("");
//		ship2.setName("ship2");
//		ship2.setIcon(new ImageIcon("ship/ship2.png"));
//		ship2.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JButton shipLabel = (JButton) e.getSource();
//				// Set ship number
//				shipNumber = Integer.parseInt(shipLabel.getName().substring(shipLabel.getName().length() - 1)) - 1;
//				// Clear ship occupation
//				Ship ship = main.client.gridTable.getShip(shipNumber);
//				if (ship != null) { // If there are already ship2 set, clear the
//									// occupation
//					main.client.gridTable.removeShip(ship);
//					readyButton.setEnabled(false);
//					lblPressReady.setText("");
//				}
//				// Enable ship placing mode
//				setShipPlacingEnabled(true);
//			}
//		});
//		JButton ship3 = new JButton("");
//		ship3.setName("ship3");
//		ship3.setIcon(new ImageIcon("ship/ship3.png"));
//		ship3.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JButton shipLabel = (JButton) e.getSource();
//				// Set ship number
//				shipNumber = Integer.parseInt(shipLabel.getName().substring(shipLabel.getName().length() - 1)) - 1;
//				// Clear ship occupancy
//				Ship ship = main.client.gridTable.getShip(shipNumber);
//				if (ship != null) { // If there are already ship3 set, clear the
//									// occupation
//					main.client.gridTable.removeShip(ship);
//					readyButton.setEnabled(false);
//					lblPressReady.setText("");
//				}
//				// Enable ship placing mode
//				setShipPlacingEnabled(true);
//			}
//		});
//		JButton ship4 = new JButton("");
//		ship4.setName("ship4");
//		ship4.setIcon(new ImageIcon("ship/ship4.png"));
//		ship4.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JButton shipLabel = (JButton) e.getSource();
//				// Set ship number
//				shipNumber = Integer.parseInt(shipLabel.getName().substring(shipLabel.getName().length() - 1)) - 1;
//				// Clear ship occupation
//				Ship ship = main.client.gridTable.getShip(shipNumber);
//				if (ship != null) { // If there are already ship4 set, clear the
//									// occupation
//					main.client.gridTable.removeShip(ship);
//					readyButton.setEnabled(false);
//					lblPressReady.setText("");
//				}
//				// Enable ship placing mode
//				setShipPlacingEnabled(true);
//			}
//		});
//
//		shipPanel.add(ship1);
//		shipPanel.add(ship2);
//		shipPanel.add(ship3);
//		shipPanel.add(ship4);
//
//		JPanel toolTip = new JPanel();
//		player2.add(toolTip, BorderLayout.CENTER);
//		toolTip.setLayout(new BorderLayout(0, 0));
//		toolTip.setOpaque(false);
//
//		lblPressReady = new JLabel("");
//		lblPressReady.setFont(new Font("Avenir", Font.BOLD, 30));
//		lblPressReady.setForeground(Color.WHITE);
//		lblPressReady.setHorizontalAlignment(SwingConstants.CENTER);
//		toolTip.add(lblPressReady);
//
//		/* RIGHT BORDER */
//		JPanel east = new JPanel();
//		east.setPreferredSize(new Dimension(150, 300));
//		panel.add(east, BorderLayout.EAST);
//		east.setLayout(new BorderLayout(0, 0));
//		east.setOpaque(false);
//
//		JPanel bottom = new JPanel();
//		bottom.setPreferredSize(new Dimension(1024, 50));
//		bottom.setOpaque(false);
//		panel.add(bottom, BorderLayout.SOUTH);
//	}
//
//	public SquareLabel[] searchForHighlightableLabel(int y, int x, String direction) {
//		SquareLabel[] highlightable = new SquareLabel[4];
//		if (myBoardLabel[y][x].getSquare().hasShip())
//			return null;
//		highlightable[0] = myBoardLabel[y][x];
//		int index = 1, failedAttempt = 0;
//		return checkNext(y, x, index, failedAttempt, direction, highlightable);
//
//	}
//
//	public SquareLabel[] searchForHighlightableLabel(SquareLabel startingLabel) {
//		int y = startingLabel.getYIndex();
//		int x = startingLabel.getXIndex();
//		// Find down/right label that don't cause IndexOutOfBoundError
//		SquareLabel[] highlightable = new SquareLabel[4];
//		if (myBoardLabel[y][x].getSquare().hasShip())
//			return null; // If the square is already occupied, return null
//		highlightable[0] = myBoardLabel[y][x]; // Add the first label into the
//												// array
//		int index = 1, failedAttempt = 0;
//		return checkNext(y, x, index, failedAttempt, shipPlacingDirection, highlightable);
//		
//	}
//
//	public SquareLabel[] checkNext(int y, int x, int index, int failedAttempt, String direction,
//			SquareLabel[] highlightable) {
//		// Check to continue;
//		if (index <= 3) {
//			if (direction.equals("vertical")) { // If down direction
//				// Check if the next square exists
//				if (y + index <= 7) { // If exists
//					// Check occupancy
//					if (!myBoardLabel[y + index][x].getSquare().hasShip()) { // If
//																				// not
//																				// occupied
//						// Add the label to highlightable
//						highlightable[index] = myBoardLabel[y + index++][x];
//						highlightable = checkNext(y, x, index, failedAttempt, direction, highlightable);
//					} else { // If occupied
//						// Check previous square occupancy
//						highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
//					}
//				} else { // If not exist
//					highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
//				}
//			} else { // If right direction
//				// Check if the next square exists
//				if (x + index <= 7) { // If exists
//					// Check occupancy
//					if (!myBoardLabel[y][x + index].getSquare().hasShip()) { // If
//																				// not
//																				// occupied
//						// Add the label to highlightable
//						highlightable[index] = myBoardLabel[y][x + index++];
//						highlightable = checkNext(y, x, index, failedAttempt, direction, highlightable);
//					} else { // If occupied
//						// Check upper square occupancy
//						highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
//					}
//				} else { // If not exist, check for previous square
//					highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
//				}
//			}
//		}
//		// Done
//		return highlightable;
//	}
//
//	public SquareLabel[] checkPrevious(int y, int x, int index, int failedAttempt, String direction,
//			SquareLabel[] highlightable) {
//		// Check to continue;
//		if (index <= 3) {
//			if (direction.equals("vertical")) { // If down direction
//				// Check if the next square exists
//				if (y - failedAttempt >= 0) { // If exists
//					// Check occupancy
//					if (!myBoardLabel[y - failedAttempt][x].getSquare().hasShip()) { // If
//																						// not
//																						// occupied
//						// Add the label to highlightable
//						highlightable[index++] = myBoardLabel[y - failedAttempt++][x];
//						highlightable = checkPrevious(y, x, index, failedAttempt, direction, highlightable);
//					} else { // If occupied, return null
//						return null;
//					}
//				} else { // If not exsit, return null
//					return null;
//				}
//			} else { // If right direction
//				// Check if the next square exists
//				if (x - failedAttempt >= 0) { // If exists
//					// Check occupancy
//					if (!myBoardLabel[y][x - failedAttempt].getSquare().hasShip()) { // If
//																						// not
//																						// occupied
//						// Add the label to highlightable
//						highlightable[index++] = myBoardLabel[y][x - failedAttempt++];
//						highlightable = checkPrevious(y, x, index, failedAttempt, direction, highlightable);
//					} else { // If occupied, return null
//						return null;
//					}
//				} else { // If not exsit, return null
//					return null;
//				}
//			}
//		}
//		// Done
//		return highlightable;
//	}
//
//	public void setShipPlacingEnabled(boolean setting) {
//		shipPlacingEnabled = setting;
//	}
//
//	public boolean isShipPlacingEnabled() {
//		return shipPlacingEnabled;
//	}
//
//	@Override
//	public void launch() {
//		System.out.println(Thread.currentThread().getName() + ": entered " + page);
//		main.replaceCurrentPanel(panel);
//		JOptionPane.showMessageDialog(main, "Welcome, " + main.player.getName());
//		main.setEnabled(true);
//	}
//
//	@Override
//	public void leave() {
//		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
//		main.setEnabled(false);
//
//	}
//
//
//
//}
