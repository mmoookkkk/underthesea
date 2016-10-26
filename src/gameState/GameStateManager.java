package gameState;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.Stack;

import javax.swing.JFrame;

import game.Main;
import userInterface.UI;

public class GameStateManager {
	
	//GameStateManager is accessible from all UI via instance main.GSM
	public Stack<GameState> stackedGameState;
	
	public GameStateManager() {
		stackedGameState = new Stack<GameState>();
	}
	
	public GameState getCurrentState() {
		return stackedGameState.peek();
	}
	
	//Pop the top state
	//Note: the method does not return an instance of a GameState
	public void popState() {
		//Leave current state
		stackedGameState.pop().leaving();
		//Reveal the state on the top of the stack
		stackedGameState.peek().revealed();
	}
	
	//Pop the stackedGameState until the destination state is met
	public void popStateUntil(String destStateString) {
		//If already at destination state, do nothing
		if(((UI)stackedGameState.peek()).getStateString().equals(destStateString)) return;
		//If no state with respect to the destination string available, do nothing
		if(!this.containState(destStateString)) return;
		while(!((UI)stackedGameState.peek()).getStateString().equals(destStateString)) {
			stackedGameState.pop().leaving();
		}
		//Reveal the destination state
		stackedGameState.peek().revealed();
	}
	
	//Used only the first time (When stackedCurrentState is empty)
	public void setState(GameState initialState) {
		//Set new state
		stackedGameState.push(initialState);
		//Enter new state
		initialState.entered();
	}
	
	public void changeState(GameState nextState) {
		//Leave current state
		stackedGameState.pop().leaving();
		//Append new state
		stackedGameState.push(nextState);
		//Enter new state
		nextState.entered();
		
		
		/* Switch string model
		switch(nextState) {
			case GameStateManager.MAIN_GAME_STATE:
				main.setBounds(100, 100, 1024, 768);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setCurrentPanel(new MainMenuUI(main));
				
			case GameStateManager.GAME_SETUP_STATE:
		*/
				
	}
	
	public void pushState(GameState stackingState) {
		//Call obscuring current state before leaving
		stackedGameState.peek().obscuring();
		//Push new stacking state into the stack
		stackedGameState.push(stackingState);
		//Enter new state
		stackingState.entered();
	}
	
	private boolean containState(String searchingString) {
		ArrayList<String> stateString = new ArrayList<String>();
		for(GameState state : stackedGameState) {
			stateString.add(((UI)state).getStateString());
		}
		if(stateString.contains(searchingString)) return true;
		else return false;
	}
}
