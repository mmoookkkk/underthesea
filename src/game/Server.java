package game;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Server {
//	public ArrayList<Socket> socketList;
//	public ArrayList<GameServer> gameServerList;
//	public Socket socket1;
	public GameServer gameServer;
	public ServerSocket serverSocket;
	public ExecutorService executor;
	public CustomLock matchingLock;
	public Socket firstClientMatching;
	public Socket secondClientMatching;
	public int port;
	public int currentNumberPlayer;
	public MainPanel mainPanel;
	
	public static void main(String [] args) throws IOException {
		Server server = new Server();
		server.executor.execute(server.new SocketConnectionThread());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				server.mainPanel = server.new MainPanel();
				frame.add(server.mainPanel);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	public Server() throws IOException {
//		socketList = new ArrayList<Socket>();
//		gameServerList = new ArrayList<GameServer>();
		
		executor = Executors.newFixedThreadPool(20);
		serverSocket = new ServerSocket(8000);
		port = 8001;
		currentNumberPlayer = 0;
		matchingLock = new CustomLock();
	}
	
	public void createGameServer() {
		
	}
	
	public int getPortNumber() {
		return port;
	}
	
	public void incrementPortNumber() {
		port++;
	}
	
	class SocketInputThread implements Runnable {
		Socket socket;
		
		public SocketInputThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				System.out.println("this method runs");
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				//Prepare (create) the game server
//				GameServer gameServer = new GameServer(port);
				gameServer = new GameServer(port);
				//Return port number
				out.println("PORTNUM_" + port);
				System.out.println(Thread.currentThread().getName() + ": " + "PORTNUM_" + port + " sent");
//				gameServerList.add(gameServer);
				executor.execute(gameServer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class SocketConnectionThread implements Runnable {
		
		@Override
		public void run() {
			while(true) {
				//Wait for input connection
				Socket socket;
				try {
					System.out.println(Thread.currentThread().getName() + ": waiting for connection...");
					socket = serverSocket.accept();
					if(firstClientMatching == null) {
						System.out.println(Thread.currentThread().getName() + ": first client connected");
						firstClientMatching = socket;
						setCurrentNumberPanel(++currentNumberPlayer);
						executor.execute(new SocketInputThread(socket));
					} else { //Second client connection -> Match
						System.out.println(Thread.currentThread().getName() + ": second client connected");
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println("PORTNUM_" + port++);
						setCurrentNumberPanel(++currentNumberPlayer);
						//Clear first client matching and wait for the next connection
						firstClientMatching = null;
						System.out.println(Thread.currentThread().getName() + ": firstClientMatching cleared");
					}
				} catch (IOException e) {
						e.printStackTrace();
				}
			}
		}	
	}
	
	private void setCurrentNumberPanel(int number) {
		mainPanel.currentNumberPlayerLabel.setText(number + "");
		mainPanel.repaint();
		mainPanel.revalidate();
	}
	
	class MainPanel extends JPanel {
		JLabel currentNumberPlayerLabel;
		JButton resetButton;
		
		public MainPanel() {
			setLayout(new BorderLayout());
			currentNumberPlayerLabel = new JLabel(currentNumberPlayer + "");
			currentNumberPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(currentNumberPlayerLabel, BorderLayout.CENTER);
			resetButton = new JButton("Reset");
			resetButton.addActionListener(new ActionListener() {
				//Reset every Game Server
				@Override
				public void actionPerformed(ActionEvent e) {
//					for(GameServer gameServer : gameServerList) {
						gameServer.socketThread1.resetServer();
//					}
				}
				
			});
			add(resetButton, BorderLayout.EAST);
			setPreferredSize(new Dimension(150,60));
		}
	}
}
