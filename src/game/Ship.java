package game;

import java.io.Serializable;


import game.Square;
import userInterface.SquareLabel;

public class Ship implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -651467608234216669L;
	public Square [] occupancy;
	public boolean sunk;
	public int shipNumber;
	public String direction;
	
	public Ship(int shipNumber, String direction) {
		occupancy = new Square[4];
		this.shipNumber = shipNumber;
		this.direction = direction;
	}
	
	public void setOccupation(Square[] occupancy) {
		this.occupancy = occupancy;
	}
	
	public Square[] getOccupancy() {
		return occupancy;
	}
	
	public boolean isSunk() {
		for(Square square : occupancy) {
			if(!square.marked) return false;
		}
		System.out.println(Thread.currentThread().getName() + ": ship " + shipNumber + " has been sunk!" );
		return true;
	}
	
}
