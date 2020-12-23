package Controller;

public class BoardController {
	private static BoardController instance;
	private BoardController() {}
	
	public static BoardController getInstance() {
		if(instance == null)
			instance = new BoardController();
		return instance;
	}
}
