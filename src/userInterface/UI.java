package userInterface;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JPanel;

import game.Main;
import gameState.StackPage;

public abstract class UI implements StackPage{

	public Main main;
	public JPanel panel;
	public JDialog dialog;
	public String page;
	
	
	public UI(Main main) {
		this.main = main;
	}
	
    
	public static JPanel paintMainPanel(Image background) {
		return new JPanel() {			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				((Graphics2D)g.create()).drawImage(background, 0, 0, 1024, 768, this);
			}
			
		};
		
		
	}
	
	public static JPanel paintSubPanel(String fileName, int width, int height) {
		return new JPanel() {
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				((Graphics2D)g.create()).drawImage(Main.createImageIcon(fileName,width,height).getImage(), 0, 0, width, height, this);
			}
			
		};
	}
	
}