package game;

import java.awt.Cursor;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import game.*;
import gameState.*;
import userInterface.*;
import gameState.GameStateManager;

public class Main extends JFrame {
	// public JFrame frame;
	public final GameStateManager GSM = new GameStateManager();
	public JPanel currentStatePanel;
	public boolean isClient;
	public boolean start = true;
	public GameClient client;
	private Socket socket;
	public Player player;
	public Image background;
	public String picImage;
	public static boolean song1chosen;

	Clip  losingClip, winningClip;
	public static Clip song1,song2;
	public int point_opponent = 0;

	/**
	 * Launch the application.
	 * 
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main main = new Main();
				main.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		super();
		setBounds(100, 100, 1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// insertBGM("login.wav");
		// start = false;
		player = new Player();
		
	
		if(LandingPage.checkbg){
		background = createImageIcon("bg/background1.png", 1024, 768).getImage();
		song1chosen=true;
		}else{
			background = createImageIcon("bg/background2.png", 1024, 768).getImage();
			song1chosen=false;
		}
		// Change UI state -> MAIN_MENU_STATE
		GSM.setState(new LandingPage(this));
		createSound();

		song1.start();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("cursor_blue.png");
		Point hotSpot = new Point(0, 0);
		Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "cursor");
		this.setCursor(cursor);

	}

	/**
	 * Initialize the contents of the frame.
	 */

	// Server-client case
	public void connect() {
		System.out.println("hello3");
		// Set the server address here
		String serverAddr = "localhost";
		System.out.println(Thread.currentThread().getName() + ": Server-client mode running");
		// Connect to match server
		System.out.println(Thread.currentThread().getName() + ": connecting to the match server...");
		try {
			socket = new Socket(serverAddr, 8000); // Match server use port 8000
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String input = in.readLine();
			String portStr = input.substring(input.indexOf("_") + 1);
			int portNumber = Integer.parseInt(portStr);
			System.out.println(Thread.currentThread().getName() + ": got port number " + portNumber);
			socket.close();
			socket = new Socket(serverAddr, portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = new GameClient(socket);
		client.run();
	}

	public void createSound() {

//		File soundFile1 = new File("sound/menu.wav");
//		File soundFile2 = new File("sound/battle.wav");
//		File soundFile3 = new File("sound/setup.wav");
		File partofyourworld = new File("sound/poyw.wav");
		File underthesea = new File("sound/udts.wav");
		File fail = new File("fail-trombone-01.wav");
		File yay = new File("applause-2.wav");
		AudioInputStream sound1 = null;
		AudioInputStream sound2 = null;
		AudioInputStream sound3 = null;
		AudioInputStream sound4 = null;
	
		try {

			sound1 = AudioSystem.getAudioInputStream(partofyourworld);
			song1 = AudioSystem.getClip();
			song1.open(sound1);

			sound2 = AudioSystem.getAudioInputStream(underthesea);
			song2 = AudioSystem.getClip();
			song2.open(sound2);
			
			sound3 = AudioSystem.getAudioInputStream(fail);
			losingClip = AudioSystem.getClip();
			losingClip.open(sound3);

			sound4 = AudioSystem.getAudioInputStream(yay);
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

	public void insertBGM(String sound) {
		File soundFile = new File(sound);
		AudioInputStream audioIn = null;

		try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			if (start) {

				clip.loop(Clip.LOOP_CONTINUOUSLY);

			} else {

				clip.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void replaceCurrentPanel(JPanel panel) {
		// if currentStatePanel is not null, remove the currentStatePanel
		if (currentStatePanel != null) {
			getContentPane().remove(currentStatePanel);
		}
		currentStatePanel = panel;
		getContentPane().add(panel);
		repaint();
		revalidate();
	}

	public void returnToMainMenu() {
		while (!GSM.stackedGameState.isEmpty()) {
			GSM.popState();
		}
		GSM.setState(new LandingPage(this));
	}

	public static Point getPopUpLocation(UI ui) {

		// frame
		Dimension frame_size = ui.main.getSize();
		int frame_width = frame_size.width;
		int frame_height = frame_size.height;

		Point frame_point = ui.main.getLocation();
		int frame_x = frame_point.x;
		int frame_y = frame_point.y;

		// dialog
		Dimension dialog_size = ui.dialog.getSize();
		int dialog_width = dialog_size.width;
		int dialog_height = dialog_size.height;

		int x_dialog = frame_x + (frame_width / 2) - (dialog_width / 2);
		int y_dialog = frame_y + (frame_height / 2) - (dialog_height / 2);
		Point result = new Point(x_dialog, y_dialog);

		return result;
	}

	public static ImageIcon createImageIcon(String path, int width, int height) {
		Image img = null;
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
		}
		Image resizedImage = img.getScaledInstance(width, height, 0);
		return new ImageIcon(resizedImage);
	}

	public class GameClient implements Runnable {
		// P2P case field
		protected Socket socket;
		protected PrintWriter out;
		protected BufferedReader in;
		protected ObjectOutputStream oos;
		protected ObjectInputStream ois;
		protected GameSetupUIState gameSetupUI;
		protected GameUIState gameUI;
		protected boolean myTurn;
		protected int accumulativeScore;
		protected int currentScore;
		protected int opponentScore;
		public String opponentName, opponentPic;
		public Timer timer_turn_duration;
		// UI field related to GameClient

		// Global serializable field
		public BoardGame boardGame;
		// protected boolean isWithLocalServer = false;
		private String playerState;

		protected GameClient(Socket socket) {
			this.socket = socket;
			playerState = PlayerState.NULL_STATE;
			accumulativeScore = 0;
			initialize();

		}

		protected void initialize() {
			// reset
			myTurn = false;
			currentScore = 0;
			opponentScore = 0;
			// Create a board game
			boardGame = new BoardGame();
		}

		@Override
		public void run() {
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Run background worker thread
			new BackgroundInputReader().execute();

		}

		public void startGameSetup() {
			out.println(CommandString.CLIENT_GAME_SETUP_READY);
			System.out
					.println(Thread.currentThread().getName() + ": " + CommandString.CLIENT_GAME_SETUP_READY + " sent");
		}

		public void startGame() {
			out.println(CommandString.CLIENT_GAME_START_READY);
			System.out
					.println(Thread.currentThread().getName() + ": " + CommandString.CLIENT_GAME_START_READY + " sent");
			playerState = PlayerState.EXPECT_SERVER_START_GAME;
		}

//		public void requestNewGame() {
//			out.println(CommandString.CLIENT_REQUEST_NEW_GAME);
//			playerState = PlayerState.EXPECT_SERVER_START_GAME;
//			myTurn = false;
//			setupClip.start();
//			GSM.popState(); // Pop EndGameDialogUIState
//		}

		public void resetGame() {
			out.println(CommandString.CLIENT_RESET_GAME);
			playerState = PlayerState.EXPECT_SERVER_START_GAME;
			myTurn = false;
		}

		public void mark(int y, int x) {
			// Mark the square y,x
			out.println("MARK_" + y + "," + x);
			playerState = PlayerState.IDLE;
			myTurn = false;
		}

		public void sendMessage(String msg) {
			System.out.println(msg);
			out.println("CHAT_" + msg);

		}

		public boolean isMyTurn() {
			return myTurn;
		}

		protected GameClient(GameServer gameServer) {
			// this.gameServer = gameServer;
			player = new Player();
			boardGame = new BoardGame();
			// isWithLocalServer = true;

		}

		// public void setLocalServer(GameServer gameServer) {
		// // this.gameServer = gameServer;
		//
		// }

		public void setBoardGame(BoardGame boardGame) {
			this.boardGame = boardGame;
		}

		class BackgroundInputReader extends SwingWorker<Void, String> {

			/*
			 * The purpose of BackgroundInputReader is to delegate the input
			 * reading task to worker thread So that it does not block EDT Also
			 * responsible to update UI
			 */
			@Override
			protected Void doInBackground() throws Exception {
				// Repeatedly listen for input message
				while (true) {
					String input = in.readLine();
					System.out.println(Thread.currentThread().getName() + ": " + input + " received");
					publish(input);
				}

			}

			@Override
			protected void process(final List<String> inputList) {
				// Client game logic
				System.out.println(Thread.currentThread().getName() + ": process invoked");
				System.out.println(Thread.currentThread().getName() + ": the size of inputList is " + inputList.size());
				for (String input : inputList) { // Process every input
					if (input == null) {
						// Opponent has left the game
					}
					switch (input) {

					case CommandString.SERVER_OTHER_CLIENT_NOT_AVAILABLE: // If
																			// another
																			// client
																			// has
																			// not
																			// connected
																			// to
																			// the
																			// server
																			// ->
																			// wait
																			// until
																			// another
																			// client's
																			// connection
																			// is
																			// accepted
						if (!(playerState.equals(PlayerState.NULL_STATE)))
							break; // TODO Raise SynchronizeErrorException
						System.out.println(
								Thread.currentThread().getName() + ": The other client is not available. waiting...");
						// Push UI state -> WAIT_FOR_CONNECTION_STATE
						try {
							GSM.pushState(new WaitForConnectionUIState(Main.this));
						} catch (MalformedURLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						// Wait
						// When another client connects, the server returns a
						// string SERVER_ANOTHER_CLIENT_AVAILABLE to all clients
						break;

					case CommandString.SERVER_OTHER_CLIENT_AVAILABLE:
						if (!playerState.equals(PlayerState.NULL_STATE)
								|| !playerState.equals(PlayerState.EXPECT_SERVER_OTHER_CLIENT_AVAILABLE))
							; // Raise SynchronizationErrorException
						// The other client has connected
						// Pop UI state UNTIL MAIN_GAME_STATE
						GSM.popStateUntil(GameState.MAIN_MENU_STATE);
						// Push GAME_SETUP_READY_STATE
						GSM.pushState(new GameSetupReadyUIState(Main.this));
						out.println("CLIENT_PIC_" + picImage);
						// Set playerState EXPECT_SERVER_GAME_SETUP
						playerState = PlayerState.EXPECT_SERVER_GAME_SETUP;
						// Wait for the player to press Ready...
						// System.out.println("name :" + player.name);
						// out.println("CLIENT_NAME_" + player.getName());
						break;

					case CommandString.SERVER_START_GAME_SETUP:
						if (!playerState.equals(PlayerState.EXPECT_SERVER_GAME_SETUP))
							; // Raise SynchronizationErrorException
						// Server is ready to start game setup
						// Start the game setup
						// Pop UI state until MAIN_MENU_STATE
						initialize();
						System.out.print("SENDING" + picImage);
						out.println("CLIENT_NAME_" + player.getName());
						GSM.popStateUntil(GameState.MAIN_MENU_STATE);
						// Change UI state -> GAME_SETUP_STATE
						gameSetupUI = new GameSetupUIState(Main.this);

//						mainmenuClip.stop();
//						setupClip.start();

						GSM.changeState(gameSetupUI);
						playerState = PlayerState.START_GAME_SETUP;

					case CommandString.SERVER_OPPONENT_NOT_READY:
						if (!playerState.equals(PlayerState.EXPECT_SERVER_START_GAME))
							return; // If not pressing ready yet -> do nothing
						// The other client is not ready
						// Wait
						// Push WAIT_FOR_OPPONENT_READY State
						GSM.pushState(new WaitForOpponentReadyUIState(Main.this));
						gameSetupUI.b1.setIcon(createImageIcon("ready.png", 10, 10));
						break;

					case CommandString.SERVER_OPPONENT_READY:
						gameSetupUI.b2.setIcon(createImageIcon("ready.png", 10, 10));
						break;

					case CommandString.SERVER_START_GAME:
						if (playerState.equals(PlayerState.EXPECT_SERVER_START_GAME)
								|| playerState.equals(PlayerState.START_GAME_SETUP)) {
							// Server is ready to start game
							// Start the game
							// Pop UI state until GAME_SETUP_STATE
							GSM.popStateUntil(GameState.GAME_SETUP_STATE);

//							setupClip.close();
//							battleClip.start();
//
//							FloatControl gainControl = (FloatControl) battleClip
//									.getControl(FloatControl.Type.MASTER_GAIN);
//							gainControl.setValue(-5.0f); // Reduce volume by 10
															// decibels.
							// Change UI state -> GAME_STATE
							gameUI = new GameUIState(Main.this);
							GSM.changeState(gameUI);
							playerState = PlayerState.IDLE;
							break;
						}
						// TODO
						break;
					case CommandString.SERVER_GRANT_TURN: // Server give you a
															// turn
						if (playerState.equals(PlayerState.IDLE)) {
							playerState = PlayerState.PLAYING;
							myTurn = true;

							// Sirawich
							ActionListener timerTask = new ActionListener() {
								int countdown = 10;

								@Override
								public void actionPerformed(ActionEvent e) {

									if (countdown == 0) {

										// time up expire random mark(y,x)
										Random r = new Random();
										int Low = 0;
										int High = 8;
										int random_x = r.nextInt(High - Low) + Low;
										int random_y = r.nextInt(High - Low) + Low;

										System.out.println("random:" + random_y + ", " + random_x);
										mark(random_y, random_x);

										gameUI.lblTimer.setText("END");
										timer_turn_duration.stop();

										// System.out.println("end");
									} else {
										gameUI.lblTimer.setText(countdown + "");

										// call start timer of GameUIState
										countdown--;
									}

								}
							};

							timer_turn_duration = new Timer(1000, timerTask);
							timer_turn_duration.start();
							break;
						}

					case CommandString.SERVER_INDICATE_YOU_WIN: // You won the
																// game

						break;

					case CommandString.SERVER_INDICATE_YOU_LOSE: // You lose the
																	// game
						GSM.pushState(new EndGameDialogUIState(Main.this, "You lose."));
						timer_turn_duration.stop();
						// Add current score to accumulative score and reset
						// current score
						accumulativeScore += currentScore;
						currentScore = 0;

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
						break;

					case CommandString.SERVER_RESET_GAME: // Somebody reset the
															// game
						initialize();
						gameSetupUI = new GameSetupUIState(Main.this);
						GSM.changeState(gameSetupUI);
						if (timer_turn_duration != null)
							timer_turn_duration.stop();
						playerState = PlayerState.START_GAME_SETUP;

					default:
						if (input.indexOf("RETURN_MARK") != -1) {
							String index = input.substring(input.indexOf("_", input.indexOf("_") + 1) + 1,
									input.indexOf(",") + 2);
							int y = Integer.parseInt(index.substring(0, 1));
							int x = Integer.parseInt(index.substring(2));
							boolean sunk = Boolean.parseBoolean(input.substring(input.lastIndexOf(",") + 1));
							String sub = input.substring(0, input.lastIndexOf(","));
							boolean hit = Boolean.parseBoolean(sub.substring(sub.lastIndexOf("_") + 1));
							Square markedSquare = boardGame.board[y][x];
							SquareLabel hitSquareLabel = markedSquare.getSquareLabel();
							markedSquare.marked = true;
							if (hit) { // If hit

								// call method to increase point

								// Update UI (hit)
								hitSquareLabel.setIcon(createImageIcon("effect/hit.png", 37, 37));
								gameUI.P1Score.setText(++currentScore + "");

								insertBGM("sound/hit.wav");

								try {
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else { // If not hit
								boardGame.board[y][x].marked = true;
								// Update UI (not hit)
								hitSquareLabel.setIcon(createImageIcon("effect/miss.png", 37, 37));

								insertBGM("sound/miss.wav");

								try {
									Thread.sleep(100);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
							if (currentScore == 16) {
								// Win
								out.println(CommandString.CLIENT_WIN);
								GSM.pushState(new EndGameDialogUIState(Main.this, "Congratulations, You win!"));
								timer_turn_duration.stop();
								// Add current score to accumulative score and
								// reset current score
								accumulativeScore += currentScore;
								currentScore = 0;

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

						} else if (input.indexOf("CHAT") != -1) {

							String msg = input.substring(input.indexOf("_") + 1);
							gameUI.receivedMSG.setText(msg);
						}

						else if (input.indexOf("MARK") != -1) {
							String index = input.substring(input.indexOf("_") + 1);

							// System.out.println("MARK index = "+index);

							int y = Integer.parseInt(index.substring(0, 1));
							int x = Integer.parseInt(index.substring(2));
							boolean[] hitSunk = boardGame.fireShot(y, x);
							boolean hit = hitSunk[0];
							boolean sunk = hitSunk[1];
							boolean lose = hitSunk[2];
							Square markedSquare = boardGame.myBoard[y][x];
							SquareLabel hitSquareLabel = boardGame.myBoard[y][x].getSquareLabel();
							// TODO Update UI
							if (hit) { // If hit
								// Update UI (hit)
								opponentScore++;
								gameUI.P2Score.setText(opponentScore + "");
								hitSquareLabel.setIcon(createImageIcon("effect/hit.png", 37, 37));
								repaint();
								revalidate();
							} else { // If not hit
								// Update UI (not hit)
								hitSquareLabel.setIcon(createImageIcon("effect/miss.png", 37, 37));
							}
							// TODO check if the player won the game
							out.println("RETURN_MARK_" + y + "," + x + "_" + hit + "," + sunk);

							playerState = PlayerState.IDLE;
							myTurn = true;

							// Sirawich
							ActionListener timerTask = new ActionListener() {
								int countdown = 10;

								@Override
								public void actionPerformed(ActionEvent e) {

									if (countdown == 0) {

										// time up expire random mark(y,x)
										Random r = new Random();
										int Low = 0;
										int High = 8;
										int random_x;
										int random_y;
										while (true) {
											random_x = r.nextInt(High - Low) + Low;
											random_y = r.nextInt(High - Low) + Low;
											if (!client.boardGame.board[random_y][random_x].isMarked())
												break;
										}
										mark(random_y, random_x);

										gameUI.lblTimer.setText("END");
										timer_turn_duration.stop();

										// sirawich
										point_opponent = 0;
										for (int i = 0; i < 7; i++) {
											for (int j = 0; j < 7; j++) {
												// if(client.boardGame.board[i][j].isMarked()){
												if (client.boardGame.myBoard[i][j].isMarked()) {
													point_opponent += 1;
												}
											}
										}
										// P2Score
										gameUI.P2Score.setText("" + point_opponent);

										// System.out.println("point oppo is =
										// "+point_opponent);

										// System.out.println("end");
									} else {
										gameUI.lblTimer.setText(countdown + "");

										// call start timer of GameUIState
										countdown--;
									}
								}
							};
							timer_turn_duration = new Timer(1000, timerTask);
							timer_turn_duration.start();

						} else if (input.indexOf("CLIENT_NAME") != -1) {
							opponentName = input.substring(input.lastIndexOf("_") + 1);
							gameSetupUI.p2.setText(opponentName);
							Main.this.repaint();
							Main.this.revalidate();

						} else if (input.indexOf("CLIENT_PIC") != -1) {
							opponentPic = input.substring(input.lastIndexOf("_") + 1);

						}
					}
				}
			}
		}
	}
}
