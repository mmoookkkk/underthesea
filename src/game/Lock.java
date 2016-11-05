//
package game;

public class Lock {
	
	String name;	
	int counter;

	
	public Lock(String name){
		counter=0;
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public synchronized void incrementCounter(){
		counter++;
	}
	
	public synchronized int getCounter(){
		return counter;
	}

}
