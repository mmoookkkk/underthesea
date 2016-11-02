package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import game.Main;
import game.Ship;
import game.Square;
import gameState.StackPage;

public class PlaceYourShip extends UI {

	public JLabel p2, b1, b2;
	JPanel mainPanel;
	public SquareLabel[][] myTable;
	public boolean shipPlacingEnabled;
	public SquareLabel[] highlighting;
	public String shipPlacingDirection;
	public int currentShipNumber;
	public JButton buttonready;
	public JButton buttonclear;

	public PlaceYourShip(Main main) {

		super(main);
		page = StackPage.PLACEYOURSHIP;
		shipPlacingEnabled = false;
		shipPlacingDirection = "vertical"; // SHIPDIRECTION
		currentShipNumber = 0;

		mainPanel = paintMainPanel(main.background);
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.setPreferredSize(new Dimension(1024, 768));

		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(512, 668));
		west.setOpaque(false);
		mainPanel.add(west, BorderLayout.WEST);

		// NOT READY
		JPanel west1 = new JPanel();
		west1.setPreferredSize(new Dimension(512, 159));
		west1.setOpaque(false);
		west.add(west1, BorderLayout.NORTH);

		ImageIcon notready = Main.createImageIcon("text/notready.png", 300, 100);
		JLabel buttonnotready = new JLabel("");
		buttonnotready.setIcon(notready);
		west1.add(buttonnotready, BorderLayout.CENTER);

		// GameBoard Panel
		JPanel west2 = new JPanel();
		west2.setPreferredSize(new Dimension(512, 350));
		west2.setOpaque(false);
		west.add(west2, BorderLayout.CENTER);

		// create GameBoard
		GridLayout tableLayout = new GridLayout(8, 8);
		west2.setLayout(tableLayout);
		myTable = new SquareLabel[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				SquareLabel squareLabel = new SquareLabel(this.main);
				squareLabel.setPosition(y, x);
				squareLabel.setMyCurrentTableUI();
				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				squareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				myTable[y][x] = squareLabel;
				west2.add(squareLabel);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				squareLabel.addMouseListener(new MouseAdapter() {
					// Mouse clicked
					@Override
					public void mouseClicked(MouseEvent e) {
						if (isShipPlacingEnabled() && currentShipNumber<4) {
							if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
								
								// If highlighting not exist, do nothing
								if (highlighting == null)
									return; 
								
								// Check if any of the label in highlighting is occupied
								// Create a ship on those squares
								System.out.println("shipNumber"+currentShipNumber);
								Ship ship = new Ship(shipPlacingDirection);
								
								// Set ship on board game
								main.client.gridTable.placeShip(ship, currentShipNumber, highlighting);
								currentShipNumber=currentShipNumber+1;
								

								// if (success) { // Success -> set ship
								// graphically
								
								for (int i=0;i<4;i++) {
									Square[] square = ship.getSquareOfThisShip();
									
									
									SquareLabel squareLabel = square[i].getUIOfThisSquare();
							
					
									if (shipPlacingDirection.equals("right")) {
										ImageIcon v=Main.createImageIcon("boat/horizontal-" + (i+1) + ".png",64,44);
;										squareLabel.setIcon(v);
										

									} else {
										ImageIcon h=Main.createImageIcon("boat/vertical-" + (i+1) + ".png",64,44);
									   squareLabel.setIcon(h);
										
									}
										
								}
								buttonclear.setEnabled(true);
								
								// lblPressReady.setText("Press Ready !!");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								// Re-invoke mouse exited on e
								mouseExited(e);
								
								// check if all ships are placed
								if (main.client.gridTable.allShipsReady()) {
									buttonready.setEnabled(true);

									main.repaint();
									main.revalidate();
								}
								setShipPlacingEnabled(false);
								// }
							}
						}
						// Else do nothing
					}

					// CREATE LINEBORDER FOR HIGHLIGHTED SQAURE
					@Override
					public void mouseEntered(MouseEvent e) {
						SquareLabel squareLabel = (SquareLabel) e.getSource();
						if (isShipPlacingEnabled() && currentShipNumber<4) { // If placing mode is
														// enabled
							// Search for eligible label to hightlight
							// Check if any of the label in highlighting is
							// occupied
							highlighting = searchForHighlightableLabel(squareLabel);
							if (highlighting == null)
								return; // If highlighting not exist, do nothing
							for (SquareLabel label : highlighting) {
								label.setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
							}
						}
					}

					// Mouse left a JLabel
					@Override
					public void mouseExited(MouseEvent e) {
						if (isShipPlacingEnabled()) { // If placing mode is
														// enabled
							// Remove highlight from highlighted labels
							if (highlighting == null)
								return; // If highlighting not exist, do nothing
							for (SquareLabel label : highlighting) {
								label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
							}
						}
					}

				});
			}
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// DELETE, READY button
		JPanel west3 = new JPanel();
		west3.setPreferredSize(new Dimension(512, 159));
		west3.setOpaque(false);
		west.add(west3, BorderLayout.SOUTH);

		ImageIcon clear = Main.createImageIcon("button/button-delete.png", 200, 200);
		JButton buttonclear = new JButton("");
		buttonclear.setIcon(clear);

		buttonclear.setPreferredSize(new Dimension(200, 200));
		buttonclear.setOpaque(false);
		buttonclear.setContentAreaFilled(false);
		buttonclear.setBorderPainted(false);
		west3.add(buttonclear, BorderLayout.WEST);

		ImageIcon random = Main.createImageIcon("button/button-random.png", 200, 200);
		JButton buttonrandom = new JButton("");
		buttonrandom.setIcon(random);

		buttonrandom.setPreferredSize(new Dimension(200, 200));
		buttonrandom.setOpaque(false);
		buttonrandom.setContentAreaFilled(false);
		buttonrandom.setBorderPainted(false);
		west3.add(buttonrandom, BorderLayout.EAST);

		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(512, 668));
		east.setOpaque(false);
		mainPanel.add(east, BorderLayout.EAST);

		JPanel east1 = new JPanel();
		east1.setPreferredSize(new Dimension(512, 158));
		east1.setOpaque(false);
		east.add(east1, BorderLayout.NORTH);

		ImageIcon chooseship = Main.createImageIcon("text/chooseship.png", 380, 50);
		JLabel buttonchooseship = new JLabel("");
		buttonchooseship.setIcon(chooseship);
		east1.add(buttonchooseship, BorderLayout.CENTER);

		JPanel east2 = new JPanel();
		east2.setPreferredSize(new Dimension(512, 350));
		east2.setOpaque(false);
		east.add(east2, BorderLayout.CENTER);

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel east2left = new JPanel();
		east2left.setPreferredSize(new Dimension(200, 350));
		east2left.setOpaque(false);
		east2.add(east2left, BorderLayout.WEST);

		ImageIcon verticalShip = Main.createImageIcon("boat/boat-vertical.png", 100, 200);
		JButton buttonvertical = new JButton("");
		buttonvertical.setIcon(verticalShip);
		buttonvertical.setName("ship1");

		buttonvertical.setPreferredSize(new Dimension(100, 200));
		buttonvertical.setOpaque(false);
		buttonvertical.setContentAreaFilled(false);
		buttonvertical.setBorderPainted(false);
		east2left.add(buttonvertical);
		
		buttonvertical.addMouseListener(new MouseAdapter() {
			// Mouse clicked
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isShipPlacingEnabled()) {
						shipPlacingDirection = "vertical";
						// Re-invoke mouse exited and entered on e
//						mouseExited(e);
//						mouseEntered(e);
				}
			}
			
		});

		buttonvertical.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton shipLabel = (JButton) e.getSource();
				// Set ship number
				
				// Clear ship occupation
				Ship ship = main.client.gridTable.getShip(currentShipNumber);
				if (ship != null) { // If there are already ship1 set, clear the
									// occupation
//					main.client.gridTable.removeShip(ship);
					buttonready.setEnabled(false);
					// lblPressReady.setText("");

				}
				// Enable ship placing mode
				if(!main.client.gridTable.allShipsReady()){
					setShipPlacingEnabled(true);
				}
			}
		});
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel east2right = new JPanel();
		east2right.setPreferredSize(new Dimension(200, 350));
		east2right.setOpaque(false);
		east2.add(east2right, BorderLayout.EAST);

		ImageIcon horizontalShip = Main.createImageIcon("boat/boat-horizontal.png", 200, 100);
		JButton buttonhorizontal = new JButton("");
		buttonhorizontal.setIcon(horizontalShip);
		buttonhorizontal.setName("ship2");

		buttonhorizontal.setPreferredSize(new Dimension(200, 100));
		buttonhorizontal.setOpaque(false);
		buttonhorizontal.setContentAreaFilled(false);
		buttonhorizontal.setBorderPainted(false);
		east2right.add(buttonhorizontal, BorderLayout.NORTH);
		
		buttonhorizontal.addMouseListener(new MouseAdapter() {
			// Mouse clicked
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isShipPlacingEnabled()) {
						shipPlacingDirection = "right";
						// Re-invoke mouse exited and entered on e
					mouseExited(e);
					mouseEntered(e);
				}
			}
			
		});

		buttonhorizontal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton shipLabel = (JButton) e.getSource();
				// Set ship number
				// Clear ship occupation
				Ship ship = main.client.gridTable.getShip(currentShipNumber);
	
//				if (ship != null) { // If there are already ship1 set, clear the
//									// occupation
////					main.client.gridTable.removeShip(ship);
//					buttonready.setEnabled(false);
//					// lblPressReady.setText("");
//
//				}
				// Enable ship placing mode
				if(!main.client.gridTable.allShipsReady()){
					setShipPlacingEnabled(true);
				}
				
			}
		});

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		JPanel east3 = new JPanel();
		east3.setPreferredSize(new Dimension(512, 159));
		east3.setOpaque(false);
		east.add(east3, BorderLayout.SOUTH);

		ImageIcon ready = Main.createImageIcon("button/button-ready.png", 200, 200);
		JButton buttonready = new JButton("");
		buttonready.setIcon(ready);

		buttonready.setPreferredSize(new Dimension(200, 200));
		buttonready.setOpaque(false);
		buttonready.setContentAreaFilled(false);
		buttonready.setBorderPainted(false);

		east3.add(buttonready, BorderLayout.SOUTH);

	}

	public SquareLabel[] searchForHighlightableLabel(int y, int x, String direction) {
		SquareLabel[] highlightable = new SquareLabel[4];
		if (myTable[y][x].getSquare().hasShip())
			return null;
		highlightable[0] = myTable[y][x];
		int index = 1, failedAttempt = 0;
		return checkNext(y, x, index, failedAttempt, direction, highlightable);

	}

	public SquareLabel[] searchForHighlightableLabel(SquareLabel startingLabel) {
		int y = startingLabel.getYIndex();
		int x = startingLabel.getXIndex();
		// Find down/right label that don't cause IndexOutOfBoundError
		SquareLabel[] highlightable = new SquareLabel[4];
		if (myTable[y][x].getSquare().hasShip())
			return null; // If the square is already occupied, return null
		highlightable[0] = myTable[y][x]; // Add the first label into the
											// array
		int index = 1, failedAttempt = 0;
		return checkNext(y, x, index, failedAttempt, shipPlacingDirection, highlightable);

	}

	public SquareLabel[] checkNext(int y, int x, int index, int failedAttempt, String direction,
			SquareLabel[] highlightable) {
		// Check to continue;
		if (index <= 3) {
			if (direction.equals("vertical")) { // If down direction
				// Check if the next square exists
				if (y + index <= 7) { // If exists
					// Check occupancy
					if (!myTable[y + index][x].getSquare().hasShip()) { // If
																		// not
																		// occupied
						// Add the label to highlightable
						highlightable[index] = myTable[y + index++][x];
						highlightable = checkNext(y, x, index, failedAttempt, direction, highlightable);
					} else { // If occupied
						// Check previous square occupancy
						highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
					}
				} else { // If not exist
					highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
				}
			} else { // If right direction
				// Check if the next square exists
				if (x + index <= 7) { // If exists
					// Check occupancy
					if (!myTable[y][x + index].getSquare().hasShip()) { // If
																		// not
																		// occupied
						// Add the label to highlightable
						highlightable[index] = myTable[y][x + index++];
						highlightable = checkNext(y, x, index, failedAttempt, direction, highlightable);
					} else { // If occupied
						// Check upper square occupancy
						highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
					}
				} else { // If not exist, check for previous square
					highlightable = checkPrevious(y, x, index, ++failedAttempt, direction, highlightable);
				}
			}
		}
		// Done
		return highlightable;
	}

	public SquareLabel[] checkPrevious(int y, int x, int index, int failedAttempt, String direction,
			SquareLabel[] highlightable) {
		// Check to continue;
		if (index <= 3) {
			if (direction.equals("vertical")) { // If down direction
				// Check if the next square exists
				if (y - failedAttempt >= 0) { // If exists
					// Check occupancy
					if (!myTable[y - failedAttempt][x].getSquare().hasShip()) { // If
																				// not
																				// occupied
						// Add the label to highlightable
						highlightable[index++] = myTable[y - failedAttempt++][x];
						highlightable = checkPrevious(y, x, index, failedAttempt, direction, highlightable);
					} else { // If occupied, return null
						return null;
					}
				} else { // If not exsit, return null
					return null;
				}
			} else { // If right direction
				// Check if the next square exists
				if (x - failedAttempt >= 0) { // If exists
					// Check occupancy
					if (!myTable[y][x - failedAttempt].getSquare().hasShip()) { // If
																				// not
																				// occupied
						// Add the label to highlightable
						highlightable[index++] = myTable[y][x - failedAttempt++];
						highlightable = checkPrevious(y, x, index, failedAttempt, direction, highlightable);
					} else { // If occupied, return null
						return null;
					}
				} else { // If not exsit, return null
					return null;
				}
			}
		}
		// Done
		return highlightable;
	}

	public void setShipPlacingEnabled(boolean setting) {
		shipPlacingEnabled = setting;
	}

	public boolean isShipPlacingEnabled() {
		return shipPlacingEnabled;
	}

	@Override
	 public void launch() {
	  
	  System.out.println(Thread.currentThread().getName() + ": entered " + page);
	  main.replaceCurrentPanel(mainPanel);

	  
	    URL url = null;
	  try {
	   url = new URL("http://i.giphy.com/tWJno5I1qXdni.gif");
	  } catch (MalformedURLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	     
	  Icon icon = new ImageIcon(url);
	        JOptionPane.showMessageDialog(
	                      null,
	                      "",
	                      "WELCOME TO UNDER THE SEA!!!  "+main.player.getName(), JOptionPane.INFORMATION_MESSAGE,
	                      icon);
	        main.setEnabled(true);
	 }

	@Override
	public void leave() {
		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
		main.setEnabled(false);

	}

}
