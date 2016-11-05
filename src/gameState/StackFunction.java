package gameState;
import java.util.Stack;
//

public class StackFunction {
	

	public Stack<StackPage> stackedPage;
	
	public StackFunction() {
		stackedPage = new Stack<StackPage>();
	}
	
	public StackPage topPage() {
		return stackedPage.peek();
	}
	

	public void popPage() {
		stackedPage.pop().leave();
	}
	
		
	public void pushPage(StackPage stackingState) {
		stackedPage.push(stackingState);
		stackingState.launch();
	}
	

}