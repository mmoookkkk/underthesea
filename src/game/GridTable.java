//
package game;

import java.util.Arrays;

import userInterface.SquareLabel;

public class GridTable {
	public Square [][] myCurrentTable;
	public Square [][] attackingTable;
	protected Ship [] ships;
	
	public GridTable() {
		myCurrentTable = new Square[8][8];
		attackingTable = new Square[8][8];
		ships = new Ship[4];
		
		for(int y=0; y<8; y++) {
			for(int x=0; x<8; x++) {
				myCurrentTable[y][x] = new Square(y,x);
				attackingTable[y][x] = new Square(y,x);
			}
		}
	}
	
	public boolean attacked(int y, int x) { 
		boolean hit = false;
			myCurrentTable[y][x].clicked = true;
		if(myCurrentTable[y][x].hasShip()) { 
			hit = true;
		}
		return hit;
	}
		
	public void placeShip(Ship ship,int currentShipNumber,SquareLabel[] position) {
		Square[] gridForThisShip = new Square[4];
		for(int i=0; i<4; i++) {
			gridForThisShip[i] = myCurrentTable[position[i].getYIndex()][position[i].getXIndex()];
			gridForThisShip[i].placeShipOn(ship);
		}
		Arrays.sort(gridForThisShip);
		ship.setSquareOfThisShip(gridForThisShip);
        ships[currentShipNumber] = ship;
     
	}
	
	public Ship getShip(int shipNumber) {
		return ships[shipNumber];
	}
	
	public Ship[] getAllShips() {
		return ships;
	}
			
	public boolean checkHasShip(SquareLabel[] highlighting) {
		for(int i=0;i<4;i++) {
			if(highlighting[i].getSquare().hasShip())
			return true;
		}
		return false;
	}	
	
	public void removeAllShip() {
		for(int i=0;i<4;i++) {
			if(ships[i]!=null){
			ships[i] = null;
			Square [] squareOfThisShip = ships[i].getSquareOfThisShip();
			for(int j=0;j<4;j++) {
				squareOfThisShip[j].hasShip = false;
				squareOfThisShip[j].label.setIcon(null);
				squareOfThisShip[j].label.revalidate();
                squareOfThisShip[j].label.repaint();
			}
			}
		}
	}
	
	public boolean allShipsReady() {
		for(int i=0;i<4;i++) {
			if(ships[i]==null) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
}


	
	
	
	

