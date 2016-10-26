package game;

import java.awt.Image;


import javax.swing.ImageIcon;

public class Player {
	String name;
	ImageIcon imageIcon;
	
	public Player() {
		
	}
	
	public void setImage(ImageIcon icon) {
		imageIcon = icon;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ImageIcon getImage(){
		return imageIcon;
	}
}
