//package battleship.src.userInterface;
//
//import battleship.src.game.Main;
//
//import battleship.src.gameState.GameState;
//
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.SwingConstants;
//
//public class WinnerEnd extends UI {
//	public JLabel textLabel;
//	
//	public WinnerEnd(Main main, String text) {
//		super(main);
//		stateString = GameState.END_GAME_DIALOG_STATE;
//		dialog = new JDialog(main);
//		dialog.setSize(400, 300);
//		dialog.setPreferredSize(new Dimension(400, 300));
//		dialog.setLocation(Main.getPopUpLocation(this));
//		dialog.getContentPane().setBackground(Color.BLACK);
//		initialize(text);
//		
//	}
//	
//	private void initialize(String text) {
//		JPanel panel = new JPanel();
//		panel.setBackground(Color.BLACK);
//		panel.setOpaque(false);
//		dialog.getContentPane().add(panel);
//		JPanel mainP = new JPanel();
//		mainP.setPreferredSize(new Dimension(400,200));
//		mainP.setLayout(new BorderLayout());
//		textLabel = new JLabel(text);
//		textLabel.setFont(new Font("Arial", Font.BOLD, 16));
//		textLabel.setForeground(Color.BLACK);
//		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		mainP.add(textLabel, BorderLayout.CENTER);
//		panel.setLayout(new BorderLayout());
//		panel.add(mainP,BorderLayout.NORTH);
//		
//		JPanel gap1 = new JPanel();
//		gap1.setPreferredSize(new Dimension(20,50));
//		
//		JPanel gap2 = new JPanel();
//		gap2.setPreferredSize(new Dimension(20,50));
//		panel.add(gap1, BorderLayout.WEST);
//		panel.add(gap2, BorderLayout.EAST);
//		
//		
//		JPanel button = new JPanel();
//		button.setPreferredSize(new Dimension(300,50));
//		button.setLayout(new BorderLayout());
//		panel.add(button,BorderLayout.CENTER);
//		
//		JButton cont = new JButton(Main.createImageIcon("btn-cont.png",150,50));
//		cont.setBorderPainted(false);
//		cont.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//Request to start a new game
//				main.client.requestNewGame();
//				
//			}
//		});
//		
//		
//		JButton exit =new JButton(Main.createImageIcon("btn-jexit.png",150,50));
//		exit.setBorderPainted(false);
//		exit.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				System.exit(0);
//			}
//			
//		});
//		
//		
//		button.add(cont, BorderLayout.WEST);
//		button.add(exit, BorderLayout.EAST);
//		
//		JPanel south = new JPanel();
//		south.setPreferredSize(new Dimension(400,20));
//		panel.add(south, BorderLayout.SOUTH);
//		
//		dialog.pack();
//	}
//
//	@Override
//	public void entered() {
//		System.out.println(Thread.currentThread().getName() + ": entered " + stateString);
//		dialog.setVisible(true);
//		
//	}
//
//	@Override
//	public void leaving() {
//		dialog.dispose();
//		
//	}
//
//	@Override
//	public void obscuring() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void revealed() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}

package userInterface;

import game.Main;
import gameState.StackPage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class WinnerEnd extends UI {
 public JLabel textLabel;
 Image image ;
 ImageIcon ic;
 JLabel mink;
 JPanel mainPanel;
 public WinnerEnd(Main main) {
  super(main);
  page = "END";
  
  
  mainPanel = new JPanel();
  Image bg = Main.createImageWithSize("background-winner 2.png", 800, 600).getImage();
  mainPanel = UI.paintMainPanel(bg);
  mainPanel.setLayout(new BorderLayout(0, 0));
//  mainPanel.setPreferredSize(new Dimension(800, 600));
  
  JPanel west = new JPanel();
  west.setPreferredSize(new Dimension(800, 600));
  west.setOpaque(false);
  mainPanel.add(west, BorderLayout.WEST);

  JPanel west1 = new JPanel();
  west1.setPreferredSize(new Dimension(800, 100));
  west1.setOpaque(false);
  west.add(west1, BorderLayout.NORTH);
  
  JPanel west2 = new JPanel();
  west2.setPreferredSize(new Dimension(800, 400));
  west2.setOpaque(false);
  west.add(west2, BorderLayout.CENTER);
  
  
  
  JPanel west3 = new JPanel();
  west3.setPreferredSize(new Dimension(450, 100));
  west3.setOpaque(false);
  west.add(west3, BorderLayout.SOUTH);
  
  ImageIcon takephoto = Main.createImageWithSize("button-takephoto 2.png", 150, 100);
  JButton buttontakephoto = new JButton("");
  buttontakephoto.setIcon(takephoto);

  
  buttontakephoto.setPreferredSize(new Dimension(150, 100));
  buttontakephoto.setOpaque(false);
  buttontakephoto.setContentAreaFilled(false);
  buttontakephoto.setBorderPainted(false);
  west3.add(buttontakephoto,BorderLayout.WEST);
  
  buttontakephoto.addActionListener(new ActionListener(){

   @Override
   public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    TakePhoto tp=new TakePhoto();

    File sourceimage = new File("/Users/phanthilasaengthong/git/underthesea/test.png");
    try {
     image = ImageIO.read(sourceimage);
     ic=new ImageIcon(image);
     mink=new JLabel(ic);
    
     
    west2.add(mink,BorderLayout.CENTER);
     mainPanel.repaint();
     mainPanel.revalidate();
    } catch (IOException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    

    
   }
   
  });
  
  ImageIcon fb = Main.createImageWithSize("button-fb.png", 250, 200);
  JButton buttonfb = new JButton("");
  buttonfb.setIcon(fb);

  
  buttonfb.setPreferredSize(new Dimension(250, 200));
  buttonfb.setOpaque(false);
  buttonfb.setContentAreaFilled(false);
  buttonfb.setBorderPainted(false);
  west3.add(buttonfb);
  
  buttonfb.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			FacebookPost fbp=new FacebookPost(main);
			fbp.launch();
		}
	});
  
 }
@Override
public void launch() {
	main.replaceCurrentPanel(mainPanel);
	// TODO Auto-generated method stub
	
}
@Override
public void leave() {
	main.getContentPane().remove(main.currentPage);
	// TODO Auto-generated method stub
	
}


}


