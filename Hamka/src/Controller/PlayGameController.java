package Controller;

import Model.Game;
import Model.Player;

public class PlayGameController {
	private static PlayGameController instance;
	private PlayGameController() {}
	
	public static PlayGameController getInstance() {
		if(instance == null)
			instance = new PlayGameController();
		return instance;
	}
	
	public void startGame(String username1 , String username2){
		Player player1 = new Player(username1);
		Player player2 = new Player(username2);
		Game game = new Game(player1, player2);
		game.run();

	}
}
