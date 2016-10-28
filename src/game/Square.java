package game;


import userInterface.SquareLabel;

public class Square implements Comparable <Square>{
	
	int x;
	int y;
	boolean clicked;
	boolean hasShip;
	Ship shipOnThisSquare;
	SquareLabel label;
	
	public Square(int y, int x) {
		this.x = x;
		this.y = y;
		hasShip = false;
		clicked = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public int compareTo(Square other) {
		if(shipOnThisSquare.direction.equals("vertical")) {
			if(y > other.y) return 1;
			else if(y < other.y) return -1;
			else return 0;
		} else {
			if(x > other.x) return 1;
			else if(x < other.x) return -1;
			else return 0;
		}
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public boolean hasShip() {
		return hasShip;
	}
	
	public void placeShipOn(Ship ship) {
		shipOnThisSquare = ship;
		hasShip = true;
	}
	
	public void setUIOfThisSquare(SquareLabel label) {
		this.label = label;
	}
	
	
	public SquareLabel getUIOfThisSquare() {
		return label;
	}
			
	
}
