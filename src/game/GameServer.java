package game;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable {
	//Network attributes
	public ServerSocket serverSocket;
	private Socket firstClientSocket;
	private Socket secondClientSocket;
	protected SocketThread socketThread1;
	protected SocketThread socketThread2;
	private OutputStream out;
	private InputStream in;
	//private ObjectInputStream ois;
	//private ObjectOutputStream oos;
	//Game attributes
//	private Main.GameClient localClient;
	//private GameClient otherClient;
//	private boolean isWithLocalClient = false;
	private SocketThread currentPlayer;
	
	
	public static void main(String [] args) {
		//Client-Server
	}
	
	protected GameServer(int portAddr) {
		try {
			serverSocket = new ServerSocket(portAddr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		System.out.println("ServerThread: Server is running...");
		Thread.currentThread().setName("Server_thread");
		//Create a connection thread between two clients and the server
		try {
			/*	Accept and start the SocketThreads
			*	If there is a local client, firstClientSocket is accepted before the game server runs (which is always before secondClientSocket connects)
			*	Skip the firstClientSocket acceptance if firstClientSocket is not null
			*/
			
			firstClientSocket = serverSocket.accept();
			System.out.println(Thread.currentThread().getName() + ": first client connection accepted");
			socketThread1 = new SocketThread(firstClientSocket, this);
			socketThread1.setName("Socket_thread_1");
			socketThread1.setClientNumber(1);
			//Create an initial phase lock for thread 1
			CustomLock waitForOtherConnectionLock = new CustomLock(CustomLock.WAIT_FOR_OTHER_CONNECTION_LOCK);
			socketThread1.setLock(waitForOtherConnectionLock);
			//Start the first socket thread
			socketThread1.start();
			synchronized(waitForOtherConnectionLock) {
				//Notify waiting thread after the second client is accepted
				secondClientSocket = serverSocket.accept();
				System.out.println(Thread.currentThread().getName() + ": second client connection accepted");
				waitForOtherConnectionLock.signal(true);
				waitForOtherConnectionLock.notify();
			}
			socketThread2 = new SocketThread(secondClientSocket, this);
			socketThread2.setName("Socket_thread_2");
			socketThread2.setClientNumber(2);
			//Start the second socket thread
			socketThread2.start();
			//Set GAME_SETUP_LOCK

			setupGame();
			setupGame();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			
		}
		
	}
	
	public void setupGame() throws InterruptedException {
		//Set GAME_SETUP_LOCK
		CustomLock gameSetupReadyLock = new CustomLock(CustomLock.GAME_SETUP_READY_LOCK);
		socketThread1.setLock(gameSetupReadyLock);
		socketThread2.setLock(gameSetupReadyLock);
		synchronized(gameSetupReadyLock) {
			while(gameSetupReadyLock.getCounter() != 2) {
				gameSetupReadyLock.wait();
				System.out.println(Thread.currentThread().getName() + ": setup lock waked " + gameSetupReadyLock.getCounter() + " time");
			}
		}
		//Write via sockets
		socketThread1.writeViaSocket(CommandString.SERVER_START_GAME_SETUP);
		socketThread2.writeViaSocket(CommandString.SERVER_START_GAME_SETUP);
		//Set next lock
		CustomLock gameStartReadyLock = new CustomLock(CustomLock.GAME_START_READY_LOCK);
		socketThread1.setLock(gameStartReadyLock);
		socketThread2.setLock(gameStartReadyLock);
		System.out.println(Thread.currentThread().getName() + ": current counter is " + gameStartReadyLock.getCounter());
		synchronized(gameStartReadyLock) {
			while(gameStartReadyLock.getCounter() != 2) {
				gameStartReadyLock.wait();
				System.out.println(Thread.currentThread().getName() + ": start lock waked " + gameStartReadyLock.getCounter() + " time");
			}
		}
		//Write via sockets
		socketThread1.writeViaSocket(CommandString.SERVER_START_GAME);
		socketThread2.writeViaSocket(CommandString.SERVER_START_GAME);
		//Random for the first player
		if(Math.random() < 0.5) {
			currentPlayer = socketThread1;
		} else {
			currentPlayer = socketThread2;
		}
		currentPlayer.writeViaSocket(CommandString.SERVER_GRANT_TURN);
		//START THE GAME
		setupGame();
		
	}
	
	private void print(String input, int forwardNumber) {
		if(forwardNumber == 1) socketThread1.writeViaSocket(input);
		else socketThread2.writeViaSocket(input);
	}
// try playing in two com by removing this method if mai pen rai kor aow ork leoy
//	private void setupClient() {
//		try {
//			serverSocket = new ServerSocket(65536);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		//Determine if the server run with a local client (P2P Case)
//		if(isWithLocalClient) {
//			//Start a thread that wait for another client's connection
//			Thread setupClientThread = new Thread() {
//				@Override
//				public void run() {
//				}
//			};
//			setupClientThread.start();
//			
//			//Wait for the thread to finish
//			
//			
//			/* Notify the other client (Network)
//			 ...
//			 */
//			
//			//Notify the main thread (Local client)
//			notify();
//			
//		}
//		//If is Server-Client case
//		else {
//			// TODO Server-client implementation
//		}
		
//	}
	
	protected void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	 
	protected ServerSocket getServerSocket() {
		return serverSocket;
	}
	
//	protected boolean isWithLocalClient() {
//		return this.isWithLocalClient;
//	}
	
//	protected void setLocalClient(Main.GameClient localClient) {
//		this.localClient = localClient;
//	}
//	
	protected class SocketThread extends Thread {
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		GameServer gameServer;
		int clientNumber;
		private CustomLock currentLock;
		
		public SocketThread(Socket socket, GameServer gameServer) {
			this.socket = socket;
		}
		
		public void setLock(CustomLock newLock) {
			currentLock = newLock;
		}
		
		public void setClientNumber(int clientNumber) {
			this.clientNumber = clientNumber;
		}
		
		public CustomLock getCurrentLock() {
			return currentLock;
		}
		
		public void writeViaSocket(String string) {
			out.println(string);
		}
		
		public void writeObjectViaSocket(Object object) {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(object);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void resetServer() {
			//Go past GAME_START_READY_LOCK
			synchronized(currentLock) {
				currentLock.incrementCounter();
				currentLock.incrementCounter();
				currentLock.notify();
			}
			if(clientNumber == 1) {
				out.println(CommandString.SERVER_RESET_GAME);
				print(CommandString.SERVER_RESET_GAME, 2);
			} else {
				out.println(CommandString.SERVER_RESET_GAME);
				print(CommandString.SERVER_RESET_GAME, 1);
			}
		}
		 
		@Override
		public void run() {
			while(true) {
				String input;
				try {
					out = new PrintWriter(socket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(Thread.currentThread().getName() + ": running");
					/*	initial state
					 * 	check for availability of the other client
					 *  if available, print SERVER_OTHER_CLIENT_AVAILABLE;
					 *  if not available, print SERVER_OTHER_CLIENT_NOT_AVAILABLE -> wait until available
					 */
					if(secondClientSocket == null) { //If the other client is not available
						System.out.println(Thread.currentThread().getName() +": The second client is not available");
						out.println(CommandString.SERVER_OTHER_CLIENT_NOT_AVAILABLE);
						System.out.println(Thread.currentThread().getName() +": SERVER_OTHER_CLIENT_NOT_AVAILABLE sent");
						synchronized(currentLock) {
							//Wait for the other client to connect
							//While loop prevent spurious wakeup's disturbance
							while(!currentLock.wasSignaled()) {
								currentLock.wait();
							}
							System.out.println(Thread.currentThread().getName() + ": waked");
						}
					}
					out.println(CommandString.SERVER_OTHER_CLIENT_AVAILABLE);
					
					//Server logic
					while((input = in.readLine()) != null) {
						System.out.println(Thread.currentThread().getName() + ": " + input + " received");
						switch(input) {
							case CommandString.CLIENT_GAME_SETUP_READY: //Client ready to start game setup -> increment lock counter
								//Verify current lock
								if(currentLock.getLockName().equals(CustomLock.GAME_SETUP_READY_LOCK)) {
									synchronized(currentLock) {
										currentLock.incrementCounter();
										currentLock.notify();
									}
									break;
								} else { //Invalid lock
									System.out.println("Invalid lock, not a " + CustomLock.GAME_SETUP_READY_LOCK + "...");
									break;
								}
							case CommandString.CLIENT_GAME_START_READY: //Client finished game setup, ready to start the game -> increment lock counter
								//Verify current lock
								if(currentLock.getLockName().equals(CustomLock.GAME_START_READY_LOCK)) {
									//Check if the other client is ready
									if(currentLock.getCounter() == 0) { //If not ready
										System.out.println(Thread.currentThread().getName() + ": The other client is not ready");
										out.println(CommandString.SERVER_OPPONENT_NOT_READY);
										if(clientNumber == 1) print(CommandString.SERVER_OPPONENT_READY, 2);
										else print(CommandString.SERVER_OPPONENT_READY, 1);
									}
									synchronized(currentLock) {
										currentLock.incrementCounter();
										currentLock.notify();
									}
								} else { //Invalid lock
									
								}
								break;
								
							case CommandString.CLIENT_WIN:
								if(clientNumber ==1) print(CommandString.SERVER_INDICATE_YOU_LOSE, 2);
								else print(CommandString.SERVER_INDICATE_YOU_LOSE, 1);
								break;
								
							case CommandString.CLIENT_LOSE:
								//TEST
								if(clientNumber == 1) print(CommandString.SERVER_INDICATE_YOU_WIN, 2);
								else print(CommandString.SERVER_INDICATE_YOU_WIN, 1);
								break;
							
//							case CommandString.CLIENT_REQUEST_NEW_GAME:
//								if(currentLock.getCounter() == 0) { //If not ready -> wait for the opponent
//									System.out.println(Thread.currentThread().getName() + ": The other client is not ready");
//									out.println(CommandString.SERVER_OPPONENT_NOT_READY);
//								}
//								synchronized(currentLock) {
//									currentLock.incrementCounter();
//									currentLock.notify();
//								}
//								break;
							
							case CommandString.CLIENT_RESET_GAME:
								//Go past GAME_START_READY_LOCK
								synchronized(currentLock) {
									currentLock.incrementCounter();
									currentLock.incrementCounter();
									currentLock.notify();
								}
								if(clientNumber == 1) {
									out.println(CommandString.SERVER_RESET_GAME);
									print(CommandString.SERVER_RESET_GAME, 2);
								} else {
									out.println(CommandString.SERVER_RESET_GAME);
									print(CommandString.SERVER_RESET_GAME, 1);
								}
								
							default:
								if(input.indexOf("RETURN_MARK") != -1) {
									if(clientNumber == 1) print(input, 2);
									else print(input, 1);
								} else if(input.indexOf("MARK") != -1) { //If is a mark command
									if(clientNumber == 1) print(input, 2);
									else print(input, 1);
								} else if(input.indexOf("CLIENT_NAME") != -1) {
									if(clientNumber == 1) print(input, 2);
									else print(input, 1);
								} else if(input.indexOf("CLIENT_PIC") != -1) {
									if(clientNumber == 1) print(input, 2);
									else print(input, 1);
								} else {
									if(clientNumber == 1) print(input, 2);
									else print(input, 1);
								}
								
						}
						//Send input to GameServer
						//gameServer.validateInput(input);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
				}
			}
		}
	}
}