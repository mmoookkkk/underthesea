package gameState;
import java.util.ArrayList;
import java.util.Stack;
import userInterface.UI;

public class StackFunction {
	
	//StackFunction is accessible from all UI via instance main.GSM
	public Stack<StackPage> stackedGameState;
	
	public StackFunction() {
		stackedGameState = new Stack<StackPage>();
	}
	
	public StackPage getCurrentState() {
		return stackedGameState.peek();
	}
	
	//Pop the top state
	//Note: the method does not return an instance of a StackPage
	public void popState() {
		//Leave current state
		stackedGameState.pop().leave();
		//Reveal the state on the top of the stack
//		stackedGameState.peek().revealed();
	}
	
	//Pop the stackedGameState until the destination state is met
	public void popStateUntil(String destStateString) {
		//If already at destination state, do nothing
		if(((UI)stackedGameState.peek()).getStateString().equals(destStateString)) return;
		//If no state with respect to the destination string available, do nothing
		if(!this.containState(destStateString)) return;
		while(!((UI)stackedGameState.peek()).getStateString().equals(destStateString)) {
			stackedGameState.pop().leave();
		}
		//Reveal the destination state
//		stackedGameState.peek().revealed();
	}
	
	//Used only the first time (When stackedCurrentState is empty)
	public void setState(StackPage initialState) {
		//Set new state
		stackedGameState.push(initialState);
		//Enter new state
		initialState.launch();
	}
	
	public void changeState(StackPage nextState) {
		//Leave current state
		stackedGameState.pop().leave();
		//Append new state
		stackedGameState.push(nextState);
		//Enter new state
		nextState.launch();
		
		
		/* Switch string model
		switch(nextState) {
			case StackFunction.MAIN_GAME_STATE:
				main.setBounds(100, 100, 1024, 768);
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.setCurrentPanel(new MainMenuUI(main));
				
			case StackFunction.GAME_SETUP_STATE:
		*/
				
	}
	
	public void pushState(StackPage stackingState) {
		//Call obscuring current state before leaving
//		stackedGameState.peek().obscuring();
		//Push new stacking state into the stack
		stackedGameState.push(stackingState);
		//Enter new state
		stackingState.launch();
	}
	
	private boolean containState(String searchingString) {
		ArrayList<String> stateString = new ArrayList<String>();
		for(StackPage state : stackedGameState) {
			stateString.add(((UI)state).getStateString());
		}
		if(stateString.contains(searchingString)) return true;
		else return false;
	}
}
