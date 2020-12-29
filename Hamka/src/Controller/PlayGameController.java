package Controller;

import java.util.ArrayList;

import Model.Game;
import Model.Player;
import Model.Tile;
import Utils.GameStatus;
import Utils.MoveType;
import javafx.scene.paint.Color;

public class PlayGameController {
	private static PlayGameController instance;
	private Game game;
	
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

	public MoveType movePiece(int oldX, int oldY, int newX, int newY) {
		MoveType type = game.move(oldX, oldY, newX, newY);
		Tile tile = game.getBoard().getMyBoard()[newY][newX];
//		if(type.equals(MoveType.KILL) || type.equals(MoveType.NORMAL)) {
//			if(tile.getColor().equals(Color.YELLOW))
//				
//		}
		
		return type;
	}

	public void switchTurnNow() {
		game.switchTurn();
	}
	
//	public ArrayList<Tile> returnYellowTiles() {
//		return (game.getBoard().showYellowTiles());
//	}

	public boolean isYellowTile(int x, int y) {
		if (game.getBoard().getTile(y, x).getColor().equals(Color.YELLOW)) {
			return true;
		}

		return false;
	}

	/*
	 * public void startGame(String username1, String username2) {
	 * 
	 * GameStatus status = GameStatus.FINISH; String turnString, scoresStatus,
	 * moveInput; Player playerToPlay; Player player1 = new Player(username1);
	 * Player player2 = new Player(username2); Game game = new Game(player1,
	 * player2);
	 * 
	 * while (!game.finishGame(status)) { playerToPlay = game.showPlayerTurn();
	 * turnString = String.format("%s's turn:", playerToPlay.getUsername()); //
	 * check the score of each player to view on the game view
	 * game.checkColorsToShow(); game.finishGame(status); boolean
	 * legalFirstMoveInput = false; while (!legalFirstMoveInput) {
	 * 
	 * // get the moves input
	 * 
	 * // game.move(fromX, fromY, toX, toY); game.getBoard().turnOffAllTilesColor();
	 * 
	 * }
	 * 
	 * }
	 * 
	 * // game.run();
	 * 
	 * }
	 */
	public static void setInstance(PlayGameController instance) {
		PlayGameController.instance = instance;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void checkTilesToBeColored() {
		game.checkColorsToShow();
	}

	public Color getTileColor(int x, int y) {
		return game.getBoard().getMyBoard()[x][y].getColor();
	}
	
	public ArrayList<Tile> getSuggestedTilesArrayForPlayer(){
		return game.getSuggestedTilesArray();
	}
	
	public void colorRandomTile(ArrayList<Tile> tiles , Color color) {
		game.getBoard().colorRandomTile(tiles, color);
	}
	
	public void colorAllTiles(ArrayList<Tile> tiles , Color color) {
		game.getBoard().colorAllTiles(tiles, color);
	}

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return game.isP1Turn() ? game.getPlayer1() : game.getPlayer2();
	}
}
