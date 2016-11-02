package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerProgram {
	public static int port;
	public static ServerThread serverThread;
	public static ServerSocket serverSocket;
	public static Socket firstClientConnection;
	public static int currentNumberOfPlayer;
	public static ServerPanel serverPanel;
	static Socket socket;
	static JFrame serverFrame;
	static boolean exitTrue;
	static Thread checkExit;
	

	
	public ServerProgram() throws IOException {
        port=8000;
		serverSocket = new ServerSocket(port);
		port++;
		currentNumberOfPlayer = 0;
		exitTrue=false;
		
	}

	
	

	public static void main(String [] args) throws IOException {
		ServerProgram server = new ServerProgram();
		serverPanel = server.new ServerPanel();
		serverFrame=new JFrame();
		serverFrame.add(serverPanel,BorderLayout.CENTER);
		serverFrame.pack();
		serverFrame.setVisible(true);
		checkExit = new Thread(server.new checkExit());
		checkExit.start();
		
		while(true) {
			
			try {
				socket=serverSocket.accept();
				if(firstClientConnection==null) {
					firstClientConnection=socket;
					serverPanel.currentNumber.setText(++currentNumberOfPlayer + "");
					serverPanel.repaint();
					serverPanel.revalidate();
					serverThread = new ServerThread(port);
					Thread ServerGameThread = new Thread(serverThread);
					ServerGameThread.start();
					PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
					out.println("PortNumber_" + port);
		
				} else { 
					serverPanel.currentNumber.setText(++currentNumberOfPlayer + "");
					serverPanel.repaint();
					serverPanel.revalidate();
					PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
					out.println("PortNumber_" + port++);
					firstClientConnection = null;
				}
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
			

	}
	
	class checkExit implements Runnable{
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				while(Thread.currentThread()!=null){
				if(exitTrue){
					serverPanel.currentNumber.setText(""+(--currentNumberOfPlayer));
					exitTrue=false;
				}
				Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		
	}

	
	class ServerPanel extends JPanel {
		JLabel serverProgramText;
		JLabel currentNumber;
		JButton resetButton;
		public ServerPanel() {
			serverProgramText = new JLabel("Number Of Players");
			add(serverProgramText,BorderLayout.NORTH);
			currentNumber = new JLabel(""+currentNumberOfPlayer);
			add(currentNumber, BorderLayout.CENTER);
			resetButton = new JButton("Reset The Game");
			resetButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						serverThread.resetGame();
				}
				
			});
			add(resetButton, BorderLayout.SOUTH);
			setPreferredSize(new Dimension(200,200));
			setVisible(true);
		}
		

	}

	

}
