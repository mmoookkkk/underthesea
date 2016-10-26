package userInterface;

import javax.swing.JLabel;


import game.Main;
import game.Square;

public class SquareLabel extends JLabel {
		public Main main;
		int y;
		int x;
		boolean shipPlacingEnabled;
		boolean MarkingEnabled;
		Square square;
		
		public SquareLabel(String text, Main main) {
			super(text);
			this.main = main;
		}
		
		public void setIndex() {
			String[] splitted = getName().split(",");
			y = Integer.parseInt(splitted[0]);
			x = Integer.parseInt(splitted[1]);
		}
		
		public void setBoardIndex() {
			String[] splitted = getName().split(",");
			y = Integer.parseInt(splitted[0]);
			x = Integer.parseInt(splitted[1]);
		}
		
		public void setBoardSquare() {
			square = main.client.boardGame.board[y][x];
			square.setSquareLabel(this);
		}
		
		public void setMyBoardSquare() {
			square = main.client.boardGame.myBoard[y][x];
			square.setSquareLabel(this);
		}
		
		public void setShipPlacingEnabled(boolean shipPlacingEnabled) {
			this.shipPlacingEnabled = shipPlacingEnabled;
		}
		
		public boolean isShipPlacingEnabled() {
			return shipPlacingEnabled;
		}
		
		public int getXIndex() {
			return x;
		}
		
		public int getYIndex() {
			return y;
		}
		
		public Square getSquare() {
			return this.square;
		}
	}