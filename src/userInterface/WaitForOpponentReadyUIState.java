package userInterface;
import java.awt.Dimension;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import game.Main;
import gameState.GameState;

public class WaitForOpponentReadyUIState extends UI {
	
	public WaitForOpponentReadyUIState(Main main) {
		super(main);
		stateString = GameState.WAIT_FOR_OPPONENT_READY_STATE;
		dialog = new JDialog(main, "");
		dialog.setLocation(Main.getPopUpLocation(this));
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
	public void entered() {
		System.out.println(Thread.currentThread().getName() + ": entered " + stateString);
		dialog.setVisible(true);
		
	}

	@Override
	public void leaving() {
		System.out.println(Thread.currentThread().getName() + ": leaving " + stateString);
		dialog.dispose();
		
	}

	@Override
	public void obscuring() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void revealed() {
		// TODO Auto-generated method stub
		
	}

}
