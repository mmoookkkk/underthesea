package game;

import javax.swing.ImageIcon;

public class Player {
	String name;
	ImageIcon profilePhoto;
	
	public Player(){
		profilePhoto=new ImageIcon("character/player1b");
		name="Anonymous";
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setProfilePhoto(ImageIcon photo) {
		profilePhoto = photo;
	}
	public ImageIcon getProfilePhoto(){
		return profilePhoto;
	}
}