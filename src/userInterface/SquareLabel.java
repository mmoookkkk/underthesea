package userInterface;

import javax.swing.JLabel;

import game.Main;
import game.Square;

public class SquareLabel extends JLabel{
	Main main;
	int x,y;
	Square square;
	
	public SquareLabel(Main main){
		this.main=main;
	}

	public void setPosition(int y,int x) {
		this.y=y;
		this.x=x;
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
	
	public void setAttackingTableUI() {
		square = main.client.gridTable.attackingTable[y][x];
		square.setUIOfThisSquare(this);
	}
	
	public void setMyCurrentTableUI() {
		square = main.client.gridTable.myCurrentTable[y][x];
		square.setUIOfThisSquare(this);
	}
	

	
}
