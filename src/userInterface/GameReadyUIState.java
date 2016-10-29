package userInterface;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import game.Main;
import gameState.StackPage;

public class GameReadyUIState extends UI {
	public JButton readyBtn;
	

	/**
	 * @wbp.parser.constructor
	 */
	public GameReadyUIState(Main main) {
		super(main);
		page = StackPage.SHIPREADY;
		dialog = new JDialog(main, "Ready!");
//		dialog.setSize(300, 80);
		dialog.setPreferredSize(new Dimension(300,80));
//		dialog.setLocation(Main.getPopUpLocation(this));
		initialize();
	}

	private void initialize() {
		dialog.getContentPane().setLayout(new BorderLayout());
		JLabel label = new JLabel("Press ready to start the game", SwingConstants.CENTER);
		readyBtn = new JButton("Waiting for your opponent");
		readyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.client.startGameSetup();
				readyBtn.setText("Waiting for your opponent...");
				readyBtn.setEnabled(false);
				
			}
		});
		dialog.getContentPane().add(label, BorderLayout.NORTH);
		dialog.getContentPane().add(readyBtn, BorderLayout.SOUTH);
		dialog.pack();
	}
	
	@Override
	public void launch() {
		dialog.setVisible(true);
		
	}

	@Override
	public void leave() {
		dialog.dispose();
		
	}



}
