package game;
//
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.List;

import gameState.StackFunction;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import userInterface.AreYouReady;
import userInterface.EndGameDialogUIState;
import userInterface.GameUIState;
import userInterface.MainGame;
import userInterface.LandingPage;
import userInterface.PlaceYourShip;
import userInterface.ShakeWindow;
import userInterface.SquareLabel;
import userInterface.UI;
import userInterface.WaitForConnectionUIState;
import userInterface.WaitForOpponentReadyUIState;

public class Main extends JFrame{
	public JPanel currentPage;
	public ClientThread clientThread;
	public Socket socket;
	public Clip  losingClip,winningClip,hitClip,missClip;
	public static Clip song1,song2;
	public static boolean song1chosen;
	public Player player;
	public Image backgroundImage;
	public String profilePic;
	public StackFunction stack = new StackFunction();
	public boolean firstTrial=true;
	
	public Main() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(50, 50, 1024, 768);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		            clientThread.exitGame();
		            System.exit(0);
		      
		    }
		});
		
		if(LandingPage.checkbg){
			backgroundImage = createImageWithSize("bg/background1.png", 1024, 768).getImage();
			song1chosen=true;
			}else{
				backgroundImage = createImageWithSize("bg/background2.png", 1024, 768).getImage();
				song1chosen=false;
			}
		
		player = new Player();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("cursor_ariel.png");
		this.setCursor(toolkit.createCustomCursor(image, new Point(0,0), "cursor"));
		stack.pushPage(new LandingPage(this));
		initializeSound();
		song1.start();
		song1.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	
	public static ImageIcon createImageWithSize(String fileName, int width, int height) {
		Image image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		}
		return new ImageIcon(image.getScaledInstance(width, height, 0));
	}
	
	// put it in each UI page in the end ///////////////////////////////////////////////////////////////////
		public void replaceCurrentPanel(JPanel panel) {
			// if currentPage is not null, remove the currentPage
			if (currentPage != null) {
				getContentPane().remove(currentPage);
			}
			currentPage = panel;
			getContentPane().add(panel);
			repaint();
			revalidate();
		}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Point middleOfWindow(UI ui) {

		int xLocation = ui.main.getLocation().x;
		int yLocation = ui.main.getLocation().y;
		int windowWidth = ui.main.getSize().width;
		int windowHeight = ui.main.getSize().height;
		int dialogWidth = ui.dialog.getSize().width;
		int dialogHeight = ui.dialog.getSize().height;
		int middleX = xLocation + (windowWidth / 2) - (dialogWidth / 2);
		int middleY = yLocation + (windowHeight / 2) - (dialogHeight / 2);
		Point middlePoint = new Point(middleX, middleY);

		return middlePoint;
	}

    
	public void initializeSound() {

		File partofyourworld = new File("sound/poyw.wav");
		File underthesea = new File("sound/udts.wav");
		File fail = new File("fail-trombone-01.wav");
		File yay = new File("applause-2.wav");

	
		try {

			AudioInputStream sound1 = AudioSystem.getAudioInputStream(partofyourworld);
			song1 = AudioSystem.getClip();
			song1.open(sound1);

			AudioInputStream sound2 = AudioSystem.getAudioInputStream(underthesea);
			song2 = AudioSystem.getClip();
			song2.open(sound2);
			
			AudioInputStream sound3 = AudioSystem.getAudioInputStream(fail);
			losingClip = AudioSystem.getClip();
			losingClip.open(sound3);

			AudioInputStream sound4 = AudioSystem.getAudioInputStream(yay);
			winningClip = AudioSystem.getClip();
			winningClip.open(sound4);

		} catch (UnsupportedAudioFileException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (LineUnavailableException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}
	public void insertHitEffect() throws LineUnavailableException{
		File hitSound = new File("sound/explosion-01.wav");
		try {
			AudioInputStream hitEffect = AudioSystem.getAudioInputStream(hitSound);
			hitClip = AudioSystem.getClip();
			hitClip.open(hitEffect);
			hitClip.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			hitClip.close();
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	

	public void insertMissEffect() throws LineUnavailableException{
		File missSound = new File("sound/miss.wav");
		try {
			AudioInputStream missEffect = AudioSystem.getAudioInputStream(missSound);
			missClip = AudioSystem.getClip();
			missClip.open(missEffect);
			missClip.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			missClip.close();
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	public void connect2Server() {
		try {
			socket = new Socket("localhost", 8000);
			String inputMsg = (new BufferedReader(new InputStreamReader(socket.getInputStream()))).readLine();
			String portForLater = inputMsg.substring(inputMsg.indexOf("_") + 1);
			socket.close();
			socket = new Socket("localhost", Integer.parseInt(portForLater));
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientThread = new ClientThread(socket);
		Thread clientThread1=new Thread(clientThread);
		clientThread1.start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		main.setVisible(true);
	}
	
	public class ClientThread implements Runnable {

		public Socket socket;
		public PrintWriter out;
		public BufferedReader in;
		public PlaceYourShip placeYourShipPage;
		public MainGame battlePage;
		public Timer countDownTime;
		public GridTable gridTable;
		public String clientState;
		public boolean myTurn;
		public int myCurrentScore;
		public int opponentCurrentScore;
		public String opponentName;
		public String opponentProfilePic;
		
		

		public ClientThread(Socket socket) {
			this.socket = socket;
			clientState="Initial";
			gridTable = new GridTable();
			myCurrentScore = 0;
			opponentCurrentScore = 0;
			myTurn = false;
			

		}	

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Client2Server().execute();
		}
		
		public void startPlaceShip() {
			out.println("CLIENT_ReadyToPlaceShip");
		}
		
		public void startGame() {
			System.out.println("///////START GAME///////");
			out.println("CLIENT_ReadyToBattle");
			clientState = "waitingToBattle";
		}
		public void shakeOpponent(){
			out.println("CLIENT_SHAKE");
		}
		
		public boolean isMyTurn() {
			return myTurn;
		}
		
		public void countDownTimer(){
		
			ActionListener timer = new ActionListener() {
				int timeLeft = 10;
				int xLabel,yLabel;
				@Override
				public void actionPerformed(ActionEvent e) {
					if (timeLeft == 0) {
						battlePage.lblTimer.setText(timeLeft + "");
						while (true) {
							xLabel = (int)Math.ceil(Math.random()*7);
							yLabel = (int)Math.ceil(Math.random()*7);
							System.out.println("choose"+yLabel+xLabel);
							if (!clientThread.gridTable.attackingTable[yLabel][xLabel].isClicked()){
								System.out.println("attack"+yLabel+xLabel);
								break;
							}
						}
						attack(yLabel, xLabel);
						System.out.println("////attack,"+yLabel+","+xLabel);
///////////////////////need to be changed
						countDownTime.stop();
						battlePage.lblTimer.setText("END");
					
						opponentCurrentScore = 0;
						for (int i = 0; i < 7; i++) {
							for (int j = 0; j < 7; j++) {
								if (clientThread.gridTable.myCurrentTable[i][j].isClicked() && clientThread.gridTable.myCurrentTable[i][j].hasShip()) {
									opponentCurrentScore = opponentCurrentScore+1;
								}
							}
						}
					
						battlePage.P2Score.setText(opponentCurrentScore+"");
                    return;
					} else {
						battlePage.lblTimer.setText(timeLeft + "");
						timeLeft--;
					}
				}
			};
			countDownTime = new Timer(1000, timer);
			countDownTime.start();
		}
		
		public void attack(int y, int x) {
			out.println("ATTACK_" + y + "," + x);
			clientState = "pause";
			myTurn = false;
		}
		
		public void sendMessage(String msg) {
			System.out.println(msg);
			out.println("CHAT_" + msg);

		}
		
		public void exitGame(){
			out.println("CLIENT_Exit");
		}
		
		
		class Client2Server extends SwingWorker<Void, String> {

			@Override
			public Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				while (true) {
					publish(in.readLine());
				}
			}
			
			@Override
			public void process(List<String> inputList){
				for (int i=0;i<inputList.size();i++) {
					if(inputList.get(i)!=null){
						if(inputList.get(i).contains("CLIENT_NAME")){
							opponentName = inputList.get(i).substring(inputList.get(i).lastIndexOf("_") + 1);
//							placeYourShipPage.p2.setText(opponentName);
							Main.this.repaint();
							Main.this.revalidate();
						}else if(inputList.get(i).contains("CLIENT_PIC")){
							opponentProfilePic = inputList.get(i).substring(inputList.get(i).lastIndexOf("_") + 1);
						}else if(inputList.get(i).equals("SERVER_NoOpponent")){
							if ((clientState.equals("Initial"))){
								try {
									stack.pushPage(new WaitForConnectionUIState(Main.this));
								} catch (MalformedURLException e2) {
									e2.printStackTrace();
								}
							}
							
						}else if(inputList.get(i).equals("SERVER_HaveOpponent")){
							stack.popPage();
							try {
								stack.pushPage(new AreYouReady(Main.this));
							} catch (MalformedURLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							out.println("CLIENT_NAME_" + player.getName());
							out.println("CLIENT_PIC_" + profilePic);
						}else if(inputList.get(i).equals("SERVER_ReadyToPlaceShip")){
							gridTable = new GridTable();
							myCurrentScore = 0;
							opponentCurrentScore = 0;
							myTurn = false;
							out.println("CLIENT_NAME_" + player.getName());
							out.println("CLIENT_PIC_" + profilePic);
							stack.popPage();
							placeYourShipPage = new PlaceYourShip(Main.this);
							stack.pushPage(placeYourShipPage);
						}else if(inputList.get(i).equals("SERVER_OpponentNotReadyToBattle")){
							if (!clientState.equals("waitingToBattle")){
								return;
							}
							try {
								stack.pushPage(new WaitForOpponentReadyUIState(Main.this));
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
//							
						}else if(inputList.get(i).equals("SERVER_OpponentReadyToBattle")){
							placeYourShipPage.buttonnotready.setIcon(createImageWithSize("text/ready.png", 300, 100));
						}
						else if(inputList.get(i).equals("SERVER_changelabelplease!")){
							placeYourShipPage.buttonnotready.setIcon(createImageWithSize("text/ready.png", 300, 100));
							}else if(inputList.get(i).equals("SERVER_ReadyToBattle")){
							if (clientState.equals("waitingToBattle")){

								System.out.println("//////////waitingToBattle/////////////");
								stack.popPage();
								battlePage = new MainGame(Main.this);
								stack.pushPage(battlePage);
								clientState = "pause";
							}
							
						}else if(inputList.get(i).equals("SERVER_YourTurn")){
							if (clientState.equals("pause")) {
								clientState = "continue";
								myTurn = true;
								countDownTimer();
							}
						}else if(inputList.get(i).equals("SERVER_Reset")){
							gridTable = new GridTable();
							myCurrentScore = 0;
							opponentCurrentScore = 0;
							myTurn = false;
							if (countDownTime != null){
								countDownTime.stop();
							clientState = "Initial";
						}
							placeYourShipPage = new PlaceYourShip(Main.this);
							stack.popPage();
							stack.pushPage(placeYourShipPage);
						}else if(inputList.get(i).equals("SERVER_YouLose")){
						
							countDownTime.stop();
							stack.pushPage(new EndGameDialogUIState(Main.this, "You lose."));
							if(song1chosen){
								song1.close();
							}else{
								song2.close();
							}
							losingClip.start();

							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else if(inputList.get(i).contains("ATTACK")){
							
							String attackedLabel = inputList.get(i).substring(inputList.get(i).indexOf("_") + 1);
							int yLabel = Integer.parseInt(attackedLabel.substring(0,1));
							int xLabel = Integer.parseInt(attackedLabel.substring(2));
							boolean hitShip = gridTable.attacked(yLabel, xLabel);
							out.println("RESULT_" + yLabel + "," + xLabel + "_" + hitShip);
							SquareLabel hitSquareLabel = gridTable.myCurrentTable[yLabel][xLabel].getUIOfThisSquare();
							
							if (hitShip) { 
								opponentCurrentScore=opponentCurrentScore+1;
								battlePage.P2Score.setText(opponentCurrentScore + "");
								hitSquareLabel.setIcon(createImageWithSize("effect/hit.png", 37, 37));
								repaint();
								revalidate();
							} else { 
								hitSquareLabel.setIcon(createImageWithSize("effect/miss.png", 37, 37));
								repaint();
								revalidate();
							}
							myTurn = true;
						countDownTimer();

						}else if(inputList.get(i).contains("RESULT")){
							String attackedLabel = inputList.get(i).substring(inputList.get(i).indexOf("T")+2,inputList.get(i).lastIndexOf("_"));
							int yLabel = Integer.parseInt(attackedLabel.substring(0,1));
							int xLabel = Integer.parseInt(attackedLabel.substring(2));
							Square returnSquare = gridTable.attackingTable[yLabel][xLabel];
							SquareLabel returnSquareLabel = returnSquare.getUIOfThisSquare();
							returnSquare.clicked = true;
							boolean hit = Boolean.parseBoolean(inputList.get(i).substring(inputList.get(i).lastIndexOf("_")+1, inputList.get(i).length()));
							if (hit) {
								returnSquareLabel.setIcon(createImageWithSize("effect/hit.png", 37, 37));
								battlePage.P1Score.setText(++myCurrentScore + "");
								try {
									insertHitEffect();
								} catch (LineUnavailableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else { 
								returnSquareLabel.setIcon(createImageWithSize("effect/miss.png", 37, 37));
								try {
									insertMissEffect();
								} catch (LineUnavailableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							
							if (myCurrentScore == 16) {
								out.println("CLIENT_IWin");
								stack.pushPage(new EndGameDialogUIState(Main.this, "Congratulations, You win!"));
								countDownTime.stop();		
								if(song1chosen){
									song1.close();
								}else{
									song2.close();
								}
								winningClip.start();
								try {
									Thread.sleep(500);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						}else if(inputList.get(i).contains("CHAT")){
							String msg = inputList.get(i).substring(inputList.get(i).indexOf("_") + 1);
							battlePage.receivedMSG.setText(msg);
						}else if(inputList.get(i).equals("CLIENT_SHAKE")){
							ShakeWindow sw=new ShakeWindow(Main.this);
						}
					}
				}
			}
			
		}
	}
	


}
