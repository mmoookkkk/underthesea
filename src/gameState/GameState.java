package gameState;
import game.Main;

public interface GameState {
	
	public static final String MAIN_MENU_STATE = "MAIN_MENU_STATE";
	public static final String CHANGE_BG_STATE = "CHANGE_BG_STATE";
	public static final String CONNECT_TO_SERVER_P2P_STATE = "CONNECT_TO_SERVER_P2P_STATE";
	public static final String GAME_SETUP_STATE = "GAME_SETUP_STATE";
	public static final String WAIT_FOR_CONNECTION_STATE = "WAIT_FOR_CONNECTION_STATE";
	public static final String GAME_SETUP_READY_STATE = "GAME_SETUP_READY_STATE";
	public static final String WAIT_FOR_OPPONENT_READY_STATE = "WAIT_FOR_OPPONENT_READY_STATE";
	public static final String GAME_STATE = "GAME_STATE";
	public static final String END_GAME_DIALOG_STATE = "END_GAME_DIALOG_STATE";
	
	public void entered();	//TODO implements function called after entered the state
	public void leaving();   //TODO implements function called right before leaving the state
	public void obscuring(); //TODO implements function called right before the state is stacked
	public void revealed();  //TODO implements function called after the state is revealed
	
}
