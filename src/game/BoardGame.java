package game;

import java.io.Serializable;


import java.util.Arrays;

import game.Square;
import userInterface.SquareLabel;

public class BoardGame implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6942097824451874494L;
	public Square [][] myBoard; //my board game
	public Square [][] board; //playing board
	protected Ship [] ships;
	
	/*	a Board Game contains 4 ships
	 * 	
	 */
	
	public BoardGame() {
		myBoard = new Square[8][8];
		board = new Square[8][8];
		ships = new Ship[4];
		//Create a square for slot
		for(int y=0; y<8; y++) {
			for(int x=0; x<8; x++) {
				board[y][x] = new Square(y,x);
				myBoard[y][x] = new Square(y,x);
			}
		}
	}
	
	public boolean setShip(Ship ship, int shipNumber, SquareLabel[] occupationLabel) {
		//Check if square has already been occupied (by the other ships)
		//...
		//Set occupation
		Square[] occupancy = new Square[4];
		for(int i=0; i<4; i++) {
			Square square = myBoard[occupationLabel[i].getYIndex()][occupationLabel[i].getXIndex()];
			square.setOccupyingShip(ship);
			occupancy[i] = square;
		}
		Arrays.sort(occupancy);
		ship.setOccupation(occupancy);
		//Set ship
		ships[shipNumber] = ship;
		//If setShip succeed, return true
		return true;
	}
	
	public Square[][] getBoard() {
		return myBoard;
	}
	
	public Ship[] getAllShips() {
		return ships;
	}
	
	public Ship getShip(int index) {
		return ships[index];
	}
	
	public boolean checkOccupation(SquareLabel[] highlighting) {
		for(SquareLabel label : highlighting) {
			if(label.getSquare().isOccupied()) //If the square is occupied, return true
			return true;
		}
		return false;
	}
	
	public void clearOccupation(Ship ship) {
		if(ship == null) return;
		//Set array value as null
		ships[ship.shipNumber] = null;
		Square [] occupation = ship.getOccupancy();
		for(Square square : occupation) {
			square.occupied = false;
			//Remove ship graphically
			square.label.setIcon(null);
			square.label.revalidate();
		}
	}
	
	public void clearAllShip() {
		for(Ship ship : ships) {
			clearOccupation(ship);
		}
	}
	
	public boolean isAllShipSet() {
		for(Ship ship : ships) {
			if(ship == null) {
				System.out.println("null");
				return false;
			}
			System.out.println(ship.shipNumber);
		}
		return true;
	}
	//ngong???
//	public boolean mark(Square markingSquare) {
//		return false;
//		//If hit return true
//		//If not hit return false
//	}
	
	public boolean[] fireShot(int y, int x) { //Called when the opponent fireshot on a square
		Square square = myBoard[y][x];
		boolean[] hitSunk = new boolean[3];
		if(square.isOccupied()) { //If the square is already occupied by a ship
			square.marked = true;
			hitSunk[0] = true;
			//Check if the ship is sunk/destroyed
			if(square.occupyingShip.isSunk()) {
				hitSunk[1] = true;
				//Check if the player loses
				for(Ship ship : ships) {
					if(!ship.isSunk()) hitSunk[2] = false;
					else hitSunk[2] = true;				
				}
			}
		}
		return hitSunk;
	}
	
	
	
	// TODO implements a boardgame's actions
}
