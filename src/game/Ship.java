package game;

public class Ship {
	public String direction;
	public int shipNumber;
	boolean shipSunk;
	Square[] squareOfThisShip;
	
	public Ship(int shipNumber, String direction) {
		squareOfThisShip = new Square[4];
		this.shipNumber = shipNumber;
		this.direction = direction;
	}
	
	public void setSquareOfThisShip(Square[] squareOfThisShip) {
		this.squareOfThisShip = squareOfThisShip;
	}

	public Square[] getSquareOfThisShip() {
		return squareOfThisShip;
	}
	
	public boolean isShipSunk() {
		for(int i=0;i<4;i++) {
			if(!squareOfThisShip[i].clicked) {
				return false;
			}
		}
		
		return true;
	}
	
}
