package userInterface;
import java.awt.Dimension;


import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.StackPage;

public class WaitForOpponentReadyUIState extends UI {
	
	public WaitForOpponentReadyUIState(Main main) {
		super(main);
		page = StackPage.WAITFOROPPONENT;
		dialog = new JDialog(main, "");
//		dialog.setLocation(Main.getPopUpLocation(this));
		dialog.setPreferredSize(new Dimension(300,50));
		initialize();
	}
	
	private void initialize() {
		dialog.getContentPane().setLayout(new FlowLayout());
		dialog.setTitle("Waiting for your opponent...");
		JLabel waiting = new JLabel("Waiting for your opponent...");
		dialog.add(waiting, SwingUtilities.CENTER);
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
