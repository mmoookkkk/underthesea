package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable{
    ServerSocket serverSocket;
    Socket client1Socket;
    Socket client2Socket;
    Server2ClientThread client1SocketThread;
    Server2ClientThread client2SocketThread;
    Lock readyToPlaceShip;
    
	public ServerThread(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			client1Socket=serverSocket.accept();
			client1SocketThread = new Server2ClientThread(client1Socket);
			client1SocketThread.setClient(1);
			Lock waitForOtherConnection = new Lock("waitForOtherConnection");
			client1SocketThread.setLock(waitForOtherConnection);
            client1SocketThread.start();	
			synchronized(waitForOtherConnection) {
					client2Socket = serverSocket.accept();
					waitForOtherConnection.incrementCounter();
					waitForOtherConnection.notify();
			}

			client2SocketThread = new Server2ClientThread(client2Socket);
			client2SocketThread.setClient(2);
			client2SocketThread.start();
			placeShipAndBattle();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void resetGame(){
		synchronized(readyToPlaceShip){
			readyToPlaceShip.incrementCounter();
			readyToPlaceShip.incrementCounter();
			readyToPlaceShip.notify();
		}
			client1SocketThread.out.println("SERVER_Reset");
			client2SocketThread.out.println("SERVER_Reset");
		
	}
	
	
	public void placeShipAndBattle() throws InterruptedException{
	
		readyToPlaceShip = new Lock("readyToPlaceShip");
		client1SocketThread.setLock(readyToPlaceShip);
		client2SocketThread.setLock(readyToPlaceShip);
		synchronized(readyToPlaceShip) {
			while(readyToPlaceShip.getCounter() != 2) {
				readyToPlaceShip.wait();
			}
		}
		client1SocketThread.out.println("SERVER_ReadyToPlaceShip");
		client2SocketThread.out.println("SERVER_ReadyToPlaceShip");
		
		Lock readyToBattle = new Lock("readyToBattle");
		client1SocketThread.setLock(readyToBattle);
		client2SocketThread.setLock(readyToBattle);
		synchronized(readyToBattle) {
			while(readyToBattle.getCounter() != 2) {
				readyToBattle.wait();
			}	
		}
		client1SocketThread.out.println("SERVER_ReadyToBattle");
		client2SocketThread.out.println("SERVER_ReadyToBattle");
		
		if(Math.floor((Math.random())*5) %2==0) {
			System.out.println(Math.floor((Math.random())*5) %2==0);
			client1SocketThread.out.println("SERVER_YourTurn");
		} else {
			client2SocketThread.out.println("SERVER_YourTurn");
		}
		
		placeShipAndBattle();
		

	}
	
	
	class Server2ClientThread extends Thread{
		Socket socket;
		Lock lock;
		int clientNum;
		PrintWriter out;
		BufferedReader in;
		String inputMsg;
		
		public Server2ClientThread(Socket socket){
			this.socket=socket;
		}
		
		public void setClient(int clientNumber){
			this.clientNum=clientNumber;
		}
		public void setLock(Lock lock){
			this.lock=lock;
		}
		
		public Lock getLock(Lock lock){
			return lock;
		}
		
		
		@Override
		public void run(){
			while(true){
				try {
					out=new PrintWriter(socket.getOutputStream(),true);
					in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					if(client2Socket==null){
						out.println("SERVER_NoOpponent");
						synchronized(lock) {
							while(lock.getCounter()!=1) {
								lock.wait();
							}
						
						}
					}
						out.println("SERVER_HaveOpponent");
						while((inputMsg=in.readLine())!=null){
							switch(inputMsg){
							case "CLIENT_ReadyToPlaceShip":
								if(lock.getName().equals("readyToPlaceShip")) {
									System.out.println("readytoplaceship!!");
									synchronized(lock) {
										lock.incrementCounter();
										lock.notify();
									}
								} 
								break;
								
							case "CLIENT_ReadyToBattle":
								if(lock.getName().equals("readyToBattle")) {
									if(lock.getCounter() == 0) {
										out.println("SERVER_OpponentNotReady");
										if(clientNum == 1){
											client1SocketThread.out.println("SERVER_OpponentReady");
										}
										else{
											client2SocketThread.out.println("SERVER_OpponentReady");
										}
									}
									synchronized(lock) {
										lock.incrementCounter();
										lock.notify();
									}
								} 
								break;
							case "CLIENT_Exit":
								ServerProgram.exitTrue=true;
										
							break;
							
							case "CLIENT_IWin":
								if(clientNum ==1) {
									client2SocketThread.out.println("SERVER_YouLose");
								}
								else{
									client1SocketThread.out.println("SERVER_YouLose");
								}
								break;
								
							default :
							if(inputMsg.indexOf("RETURN_MARK") != -1) {
								if(clientNum == 1) {
									client2SocketThread.out.println(inputMsg);
								}
								else {
									client1SocketThread.out.println(inputMsg);
								}
							} else if(inputMsg.indexOf("MARK") != -1) { 
								if(clientNum == 1) {
									client2SocketThread.out.println(inputMsg);
								}
								else {
									client1SocketThread.out.println(inputMsg);
								}
							} else if(inputMsg.indexOf("CLIENT_NAME") != -1) {
								if(clientNum == 1){
									client2SocketThread.out.println(inputMsg);
								}
								else {
									client1SocketThread.out.println(inputMsg);
								}
							} else if(inputMsg.indexOf("CLIENT_PIC") != -1) {
								if(clientNum == 1) {
									client2SocketThread.out.println(inputMsg);
								}
								else {
									client1SocketThread.out.println(inputMsg);
								}
							} else {
								if(clientNum == 1) {
									client2SocketThread.out.println(inputMsg);
								}
								else {
									client1SocketThread.out.println(inputMsg);
								}
							}
							}
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
