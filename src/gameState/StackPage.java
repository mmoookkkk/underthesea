package gameState;

public interface StackPage {
	
	public static String LANDINGPAGE = "LANDINGPAGE";
	public static String WAITFORCONNECTION = "WAITFORCONNECTION";
	public static String PLACEYOURSHIP = "PLACEYOURSHIP";
	public static String SHIPREADY = "SHIPREADY";
	public static String WAITFOROPPONENT = "WAITFOROPPONENT";
	public static String BATTLE = "BATTLE";
	public static String END = "END";
	
	public void launch();	
	public void leave();  

	
}
