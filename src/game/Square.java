package game;

import java.io.Serializable;


import javax.swing.JLabel;
import userInterface.SquareLabel;

public class Square implements Comparable<Square> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7539706428845824277L;
	protected int x;
	protected int y;
	protected boolean marked;
	protected boolean occupied;
	protected Ship occupyingShip;
	//Non-serializable
	protected transient SquareLabel label;
	
	public Square(int y, int x) {
		this.y = y;
		this.x = x;
		marked = false;
		occupied = false;
	}
	public void setOccupyingShip(Ship ship) {
		occupyingShip = ship;
		occupied = true;
	}
	
	public void setSquareLabel(SquareLabel squareLabel) {
		label = squareLabel;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public SquareLabel getSquareLabel() {
		return label;
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	@Override
	public int compareTo(Square o) {
		if(occupyingShip.direction.equals("down")) {
			if(y < o.y) return -1;
			else if(y > o.y) return 1;
			else return 0;
		} else {
			if(x < o.x) return -1;
			else if(x > o.x) return 1;
			else return 0;
		}
	}
	
}
