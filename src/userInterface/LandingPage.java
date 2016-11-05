package userInterface;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import game.Main;
import gameState.StackPage;

public class LandingPage extends UI {
	public JPanel mainPanel;
	public ImageIcon profilephoto;
	public JTextField profilename;
	public static boolean checkbg=true;

	public LandingPage(Main main) {

		super(main);
		page = StackPage.LANDINGPAGE;
		initialize();
	}

	protected void initialize() {
		
		mainPanel = paintMainPanel(main.backgroundImage);
		
		// game logo panel
		JPanel north = new JPanel();
		north.setPreferredSize(new Dimension(1200, 100));
		north.setOpaque(false);
		mainPanel.add(north, BorderLayout.NORTH);
		

		ImageIcon logo = Main.createImageWithSize("text/logo.png", 500, 100);
		JLabel buttonlogo = new JLabel("");
		buttonlogo.setIcon(logo);
		north.add(buttonlogo, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(1200,900));
		south.setOpaque(false);
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS ) );
		mainPanel.add(south, BorderLayout.SOUTH );

		// game setting panel
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(800, 800));
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		west.setOpaque(false);
		south.add(west);

		// set character
		JPanel west1 = new JPanel();
		west1.setOpaque(false);
		west1.setPreferredSize(new Dimension(800, 200));
		west.add(west1);
		west.add(Box.createVerticalStrut(-50));

		JPanel west1u = new JPanel();
		west1.add(west1u, BorderLayout.NORTH);
		west1u.setOpaque(false);
		west1u.setPreferredSize(new Dimension(800, 50));
		
		
		ImageIcon choosecharacter = Main.createImageWithSize("text/choosecharacter.png", 400, 40);
		JLabel buttonchar = new JLabel("");
		buttonchar.setIcon(choosecharacter);
		west1u.add(buttonchar, BorderLayout.CENTER);
		

		JPanel west1d = new JPanel();
		west1.add(west1d, BorderLayout.SOUTH);
		west1d.setOpaque(false);
		west1d.setPreferredSize(new Dimension(600, 100));
		west1d.setLayout(new BoxLayout(west1d, BoxLayout.X_AXIS));
		
		ImageIcon player1 = Main.createImageWithSize("character/player1b.png", 100, 100);
		JButton buttonp1 = new JButton("");
		buttonp1.setIcon(player1);

		
		buttonp1.setPreferredSize(new Dimension(100, 100));
		buttonp1.setOpaque(false);
		buttonp1.setContentAreaFilled(false);
		buttonp1.setBorderPainted(false);
		west1d.add(buttonp1);

		ImageIcon player2 = Main.createImageWithSize("character/player2b.png", 100, 100);
		JButton buttonp2 = new JButton("");
		buttonp2.setIcon(player2);
//		buttonp2.setOpaque(false);

		buttonp2.setPreferredSize(new Dimension(100, 100));
		buttonp2.setOpaque(false);
		buttonp2.setContentAreaFilled(false);
		buttonp2.setBorderPainted(false);
		west1d.add(buttonp2);

		ImageIcon player3 = Main.createImageWithSize("character/player3b.png", 100, 100);
		JButton buttonp3 = new JButton("");
		buttonp3.setIcon(player3);

		
		buttonp3.setPreferredSize(new Dimension(100, 100));
		buttonp3.setOpaque(false);
		buttonp3.setContentAreaFilled(false);
		buttonp3.setBorderPainted(false);
		west1d.add(buttonp3);

		ImageIcon player4 = Main.createImageWithSize("character/player4b.png", 100, 100);
		JButton buttonp4 = new JButton("");
		buttonp4.setIcon(player4);

		
		buttonp4.setPreferredSize(new Dimension(100, 100));
		buttonp4.setOpaque(false);
		buttonp4.setContentAreaFilled(false);
		buttonp4.setBorderPainted(false);
		west1d.add(buttonp4);

		ImageIcon player5 = Main.createImageWithSize("character/player1b.png", 100, 100);
		JButton buttonp5 = new JButton("");
		buttonp5.setIcon(player5);

		
		buttonp5.setPreferredSize(new Dimension(100, 100));

		west1d.add(buttonp5);
		profilephoto=player1;
		main.profilePic="character/player1b.png";
		
		buttonp1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonp5.setIcon(player1);
				main.profilePic="character/player1b.png";
				profilephoto=player1;
				main.player.setProfilePhoto(profilephoto);
			}
		});
		buttonp2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonp5.setIcon(player2);
				main.profilePic="character/player2b.png";
				profilephoto=player2;
				main.player.setProfilePhoto(profilephoto);
			}
		});
		buttonp3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonp5.setIcon(player3);
				main.profilePic="character/player3b.png";
				profilephoto=player3;
				main.player.setProfilePhoto(profilephoto);
			}
		});
		buttonp4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonp5.setIcon(player4);
				profilephoto=player4;
				main.profilePic="character/player4b.png";
				main.player.setProfilePhoto(profilephoto);
			}
		});

		// set name
		JPanel west2 = new JPanel();
		west2.setOpaque(false);
		west2.setPreferredSize(new Dimension(800, 200));
		west.add(west2);
		west.add(Box.createVerticalStrut(-50));
		
		west2.setLayout(new BoxLayout(west2, BoxLayout.Y_AXIS));

		JPanel west2u = new JPanel();
		west2.add(west2u, BorderLayout.NORTH);
		west2.add(Box.createVerticalStrut(-50));
		west2u.setOpaque(false);
		ImageIcon name = Main.createImageWithSize("text/name.png", 120, 50);
		JLabel buttonname = new JLabel("");
		buttonname.setIcon(name);
		west2u.add(buttonname, BorderLayout.CENTER);
		

		JPanel west2d = new JPanel();
		west2.add(west2d, BorderLayout.SOUTH);
		
		west2d.setOpaque(false);
		JTextField nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 24));
		nameField.setColumns(10);
		west2d.add(nameField);

		// set bg
		JPanel west3 = new JPanel();
		west3.setOpaque(false);
		west3.setPreferredSize(new Dimension(800, 200));
		west.add(west3);
		west3.setLayout(new BoxLayout(west3, BoxLayout.Y_AXIS));

		JPanel west3u = new JPanel();
		west3.add(west3u, BorderLayout.NORTH);
		west3.add(Box.createVerticalStrut(-100));
		west3u.setOpaque(false);
		ImageIcon chooseBg = Main.createImageWithSize("choosetheme.png", 350, 50);
		JLabel buttonBg = new JLabel("");
		buttonBg.setIcon(chooseBg);
		west3u.add(buttonBg, BorderLayout.CENTER);

		JPanel west3d = new JPanel();
		west3.add(west3d, BorderLayout.SOUTH);
		west3d.setOpaque(false);
		west3d.setLayout(new BoxLayout(west3d, BoxLayout.X_AXIS));
		
		ImageIcon bg1 = Main.createImageWithSize("bg/background1.png", 100, 70);
		JButton buttonBg1 = new JButton("");
		buttonBg1.setIcon(bg1);
		buttonBg1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			    
			    
			     main.backgroundImage = Main.createImageWithSize("bg/background1.png", 1024, 768).getImage();
			     //Push this state and change MAIN_MENU_STATE to refresh the backgroundImage
			     main.stack.popPage();
			     main.stack.pushPage(new LandingPage(main));
			     if(!(main.song1chosen)){
			    	 main.song2.close();
			    	 main.initializeSound();
			    	 main.song1.start();
			    	 main.song1.loop(Clip.LOOP_CONTINUOUSLY);
			    	 main.song1chosen=true;
			     }
			     
			}
		});
		
	
		

		buttonBg1.setPreferredSize(new Dimension(100, 70));
		buttonBg1.setOpaque(false);
		buttonBg1.setContentAreaFilled(false);
		buttonBg1.setBorderPainted(false);
		west3d.add(buttonBg1);
		west3d.add(Box.createHorizontalStrut(10));
		
		


		ImageIcon bg2 = Main.createImageWithSize("bg/background2.png", 100, 70);
		JButton buttonBg2 = new JButton("");
		buttonBg2.setIcon(bg2);
		buttonBg2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			    
			    
			     main.backgroundImage = Main.createImageWithSize("bg/background2.png", 1024, 768).getImage();
			     //Push this state and change MAIN_MENU_STATE to refresh the backgroundImage
			    
                 main.stack.popPage();
			     main.stack.pushPage(new LandingPage(main));
			     if(main.song1chosen){
			    	 main.song1.close();
			    	 main.initializeSound();
			    	 main.song2.start();
			    	 main.song2.loop(Clip.LOOP_CONTINUOUSLY);
			    	 main.song1chosen=false;
			     }
			}
		});
		buttonBg2.setPreferredSize(new Dimension(100, 70));
		buttonBg2.setOpaque(false);
		buttonBg2.setContentAreaFilled(false);
		buttonBg2.setBorderPainted(false);
		west3d.add(buttonBg2);


		// empty box
		JPanel west4 = new JPanel();
		west4.setOpaque(false);
		west4.setPreferredSize(new Dimension(800, 300));
		west.add(west4);
		west4.setLayout(new BoxLayout(west4, BoxLayout.Y_AXIS));


		// game connect button and profile pic panel
		JPanel east = new JPanel();
		east.setPreferredSize(new Dimension(400, 800));
		east.setOpaque(false);
		south.add(east);
		
		ImageIcon connect = Main.createImageWithSize("button/button-connect2server.png", 300, 200);
		JButton buttonConnect = new JButton("");
		buttonConnect.setIcon(connect);

		buttonConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.player.setProfilePhoto(profilephoto);
				if(!nameField.getText().isEmpty()){
				main.player.setName(nameField.getText());
				}
				main.connect2Server();
			}
		});
		buttonConnect.setPreferredSize(new Dimension(300, 200));
		buttonConnect.setOpaque(false);
		buttonConnect.setContentAreaFilled(false);
		buttonConnect.setBorderPainted(false);
		east.add(buttonConnect);

	}
	
	

	
	@Override
	public void launch() {
		System.out.println(Thread.currentThread().getName() + ": entered " + page);
		main.replaceCurrentPanel(mainPanel);
	}

	@Override
	public void leave() {
		// Buffer the MAIN_MENU_STATE
		System.out.println(Thread.currentThread().getName() + ": leaving " + page);
		main.getContentPane().remove(main.currentPage);
		// main.stack.storeBufferedState(StackFunction.MAIN_MENU_STATE, this);

	}



}
