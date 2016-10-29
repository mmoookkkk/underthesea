package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

	public PlaceYourShip(Main main) {

		super(main);
		page = StackPage.PLACEYOURSHIP;

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

		// GameBoard
		JPanel west2 = new JPanel();
		west2.setPreferredSize(new Dimension(512, 350));
		west2.setOpaque(false);
		west.add(west2, BorderLayout.CENTER);
		
		GridLayout tableLayout = new GridLayout(8, 8);
		west2.setLayout(tableLayout);

		myTable = new SquareLabel[8][8];
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				SquareLabel squareLabel = new SquareLabel(this.main);
				// squareLabel.setName(y + "," + x);
				squareLabel.setPosition(y, x);
				squareLabel.setMyCurrentTableUI();
				squareLabel.setHorizontalAlignment(SwingConstants.CENTER);
				squareLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				myTable[y][x] = squareLabel;
				west2.add(squareLabel);
			}
		}
		
		

		// DELETE, READY button
		JPanel west3 = new JPanel();
		west3.setPreferredSize(new Dimension(512, 159));
		west3.setOpaque(false);
		west.add(west3, BorderLayout.SOUTH);

		ImageIcon delete = Main.createImageIcon("button/button-delete.png", 200, 200);
		JButton buttondelete = new JButton("");
		buttondelete.setIcon(delete);

		buttondelete.setPreferredSize(new Dimension(200, 200));
		buttondelete.setOpaque(false);
		buttondelete.setContentAreaFilled(false);
		buttondelete.setBorderPainted(false);
		west3.add(buttondelete, BorderLayout.WEST);
		
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

		JPanel east2left = new JPanel();
		east2left.setPreferredSize(new Dimension(200, 350));
		east2left.setOpaque(false);
		east2.add(east2left, BorderLayout.WEST);

		ImageIcon verticalShip = Main.createImageIcon("boat/boat-vertical.png", 100, 200);
		JButton buttonvertical = new JButton("");
		buttonvertical.setIcon(verticalShip);

		buttonvertical.setPreferredSize(new Dimension(100, 200));
		buttonvertical.setOpaque(false);
		buttonvertical.setContentAreaFilled(false);
		buttonvertical.setBorderPainted(false);
		east2left.add(buttonvertical);

		JPanel east2right = new JPanel();
		east2right.setPreferredSize(new Dimension(200, 350));
		east2right.setOpaque(false);
		east2.add(east2right, BorderLayout.EAST);
		

		ImageIcon horizontalShip = Main.createImageIcon("boat/boat-horizontal.png", 200, 100);
		JButton buttonhorizontal = new JButton("");
		buttonhorizontal.setIcon(horizontalShip);

		buttonhorizontal.setPreferredSize(new Dimension(200, 100));
		buttonhorizontal.setOpaque(false);
		buttonhorizontal.setContentAreaFilled(false);
		buttonhorizontal.setBorderPainted(false);
		east2right.add(buttonhorizontal, BorderLayout.NORTH);


		 JPanel east3 = new JPanel();
		 east3.setPreferredSize(new Dimension(512, 159));
		 east3.setOpaque(false);
		 east.add(east3,BorderLayout.SOUTH);
		
		 ImageIcon ready = Main.createImageIcon("button/button-ready.png", 200, 200);
			JButton buttonready = new JButton("");
			buttonready.setIcon(ready);
	
			buttonready.setPreferredSize(new Dimension(200, 200));
			buttonready.setOpaque(false);
			buttonready.setContentAreaFilled(false);
			buttonready.setBorderPainted(false);
			
			east3.add(buttonready, BorderLayout.SOUTH);

	}

	@Override
	public void launch() {
		System.out.println(Thread.currentThread().getName() + ": entered " + page);
		main.replaceCurrentPanel(mainPanel);
		JOptionPane.showMessageDialog(main, "Mai Welcome, " + main.player.getName());
		main.setEnabled(true);
	}

	@Override
	public void leave() {
		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
		main.setEnabled(false);

	}

}
