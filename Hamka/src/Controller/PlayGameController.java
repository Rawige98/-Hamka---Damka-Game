package Controller;

import Model.Game;
import Model.Player;
import Utils.GameStatus;
import Utils.MoveType;

public class PlayGameController {
	private static PlayGameController instance;
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	private  Player player1;
	private  Player player2;
	private  Game game;

	private PlayGameController() {
	}

	public static PlayGameController getInstance() {
		if (instance == null)
			instance = new PlayGameController();
		return instance;
	}

	public void startGame(Player p1, Player p2) {
		player1=p1;
		player2=p2;
		game = new Game(p1, p2);
	}

	public MoveType movePiece(int oldX, int oldY, int newX, int newY,Player p,boolean isTurn) {
		
		return game.getBoard().move(oldX, oldY, newX, newY, isTurn, p);
	}

	public static void setInstance(PlayGameController instance) {
		PlayGameController.instance = instance;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}


	
	
	
}
