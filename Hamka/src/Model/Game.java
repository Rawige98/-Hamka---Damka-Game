package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

import org.hamcrest.core.IsSame;

import Utils.Consts;
import Utils.GameStatus;
import Utils.MoveResult;
import Utils.MoveType;
import View.PlayController;
import javafx.scene.paint.Color;

public class Game {
	private static int Serial = 0;
	private int id;
	private Player player1;
	private Player player2;
	private Date gameDate;
	private Board board;
	private Player winner;
	private Timer gameDuration;
	private static boolean isP1Turn;
	public static boolean notFinished = false;
	private int Time = 0;
	Scanner runGameScanner;
	private static boolean ownKill = false;
	private static Tile killedSoldier;
	private String LoadedFileName;

	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		if (player1.getColor().equals(player2.getColor())) {
			player2.setColor(Color.GREY);
		}
		board = new Board();
		gameDate = java.util.Calendar.getInstance().getTime();
		id = ++Serial;
		isP1Turn = true;
		notFinished = true;
		runGameScanner = new Scanner(System.in);
		checkColorsToShow();
		LoadedFileName=null;
	}
	public Game() {

	}

	public Player showPlayerTurn() {

		return (isP1Turn() ? getPlayer1() : getPlayer2());

	}

	// ------------------------Getters and Setters---------------------

	public int getTime() {
		return Time;
	}

	public void setTime(int time) {
		Time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Timer getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(Timer gameDuration) {
		this.gameDuration = gameDuration;
	}

	public boolean isP1Turn() {
		return isP1Turn;
	}

	public static boolean getIsP1Turn() {
		return isP1Turn;
	}

	public static void setP1Turn(boolean isP1Turn) {
		Game.isP1Turn = isP1Turn;
	}
	// ---------------------------------------------------

	public void pauseGame() {
	}

	/**
	 * we check if the game is finished by the number of Soldiers for the players
	 * 
	 * @param isQuit flag to know is the gamed had finished after a player quit
	 * @return
	 */
	public boolean finishGame(GameStatus status) {
		if (status.equals(GameStatus.QUIT)) {
			winner = isP1Turn ? player2 : player1;
			notFinished = false;
			return true;
		} else if (status.equals(GameStatus.PAUSE)) {
			notFinished = false;
			return true;
		} else if (status.equals(GameStatus.FINISH) /* && board.checkAvailableMoves(!isP1Turn).isEmpty() */) {
			if (player1.getScore() > player2.getScore())
				winner = player1;
			else
				winner = player2;
			notFinished = false;
			return true;
		} else
			return false;
	}

	public MoveType move(int xStart, int yStart, int xEnd, int yEnd) {
		Player p = null;
		p = isP1Turn ? player1 : player2;
		HashMap<Tile, ArrayList<Tile>> mp = board.checkAvailableSkips(isP1Turn);
		MoveType MType = board.move(xStart, yStart, xEnd, yEnd, isP1Turn, p);
		if (MType.equals(MoveType.KILL) || MType.equals(MoveType.NORMAL)) {
			board.upgradeQueen(xEnd, yEnd);
			if (MType.equals(MoveType.NORMAL) && !mp.isEmpty()) {
				Tile t = ((Tile) mp.keySet().toArray()[new Random().nextInt(mp.size())]);
				if (t.getCols() == yStart && t.getRows() == xStart) {
					t.setCols(yEnd);
					t.setRows(xEnd);
				}
				board.getMyBoard()[t.getCols()][t.getRows()] = new BlackTile(t.getCols(), t.getRows());
				ownKill = true;
				killedSoldier = t;
			} else {
				ownKill = false;
				killedSoldier = null;
			}
			return MType;
		}
		return MType;
	}

	public void switchTurn() {
		isP1Turn = !isP1Turn;
	}

	public String getGameState() {
		return board.toString();
	}

	// ************************************** Colord tiles
	// ****************************************

	public void checkColorsToShow() {
		// TODO Auto-generated method stub
		board.turnOffAllTilesColor();

		ArrayList<Tile> suggestedTiles = getSuggestedTilesArray();

		// ************ Red tile **********
		HashMap<Tile, ArrayList<Tile>> suggestedSkips = board.checkAvailableSkips(isP1Turn);

		if (suggestedSkips.isEmpty()) {
			board.colorRandomTile(suggestedTiles, Color.RED);
		}

		// *********** Blue tile **********
		int qCount = 0, sCount = 0, queen, soldier;
		if (isP1Turn) {
			queen = 11;
			soldier = 1;
		} else {
			queen = 22;
			soldier = 2;
		}

		for (int i = 0; i < Consts.ROWS; i++) {
			for (int j = 0; j < Consts.COLS; j++) {
				Tile tile = board.getMyBoard()[i][j];
				if (tile.getValue() == soldier)
					sCount++;
				if (tile.getValue() == queen)
					qCount++;
			}
		}

		if (sCount == 2 && qCount == 1)
			board.colorRandomTile(suggestedTiles, Color.BLUE);

		// *********** Yellow tiles ****************
		board.showYellowTiles();

	}

	public ArrayList<Tile> getBlueSuggtedTiles(boolean isBlack) {

		ArrayList<Tile> tiles = new ArrayList<Tile>();

		for (int i = 0; i < Consts.ROWS; i++) {
			for (int j = 0; j < Consts.COLS; j++) {
				Tile tile = board.getMyBoard()[i][j];
				if (isBlack) {
					if (tile.getValue() == 0 && checkMove(tile, isBlack)
							&& MoveValidation.isValidPoint(tile.getRows(), tile.getCols())) {
						tiles.add(tile);
					}
				}

				else {
					if (tile.getValue() == 0 && checkMove(tile, isBlack)
							&& MoveValidation.isValidPoint(tile.getRows(), tile.getCols())) {
						tiles.add(tile);
					}
				}
			}
		}

		return tiles;
	}

	public boolean checkMove(Tile tile, boolean isBlack) {

		int row1 = tile.getRows() + 1;
		int row_1 = tile.getRows() - 1;
		int row2 = tile.getRows() + 2;
		int row_2 = tile.getRows() - 2;

		int col1 = tile.getCols() + 1;
		int col_1 = tile.getCols() - 1;
		int col2 = tile.getCols() + 2;
		int col_2 = tile.getCols() - 2;

		if (!isBlack) {

			if (MoveValidation.isValidPoint(row_2, col_2)) {
				if (board.getTile(row_2, col_2).getValue() == 1 || board.getTile(row_2, col_2).getValue() == 11)
					return false;
			}
			if (MoveValidation.isValidPoint(row_2, col2)) {

				if ((board.getTile(row_2, col2).getValue() == 1 || board.getTile(row_2, col2).getValue() == 11)) {
					return false;
				}

			}
			if (MoveValidation.isValidPoint(row2, col_2)) {
				if ((board.getTile(row2, col_2).getValue() == 1 || board.getTile(row2, col_2).getValue() == 11)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row2, col2)) {
				if ((board.getTile(row2, col2).getValue() == 1 || board.getTile(row2, col2).getValue() == 11)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row_1, col_1)) {
				if ((board.getTile(row_1, col_1).getValue() == 1 || board.getTile(row_1, col_1).getValue() == 11)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row_1, col1)) {
				if ((board.getTile(row_1, col1).getValue() == 1 || board.getTile(row_1, col1).getValue() == 11)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row1, col_1)) {
				if ((board.getTile(row1, col_1).getValue() == 1 || board.getTile(row1, col_1).getValue() == 11)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row1, col1)) {
				if ((board.getTile(row1, col1).getValue() == 1 || board.getTile(row1, col1).getValue() == 11)) {
					return false;
				}
			}
			return true;

		}

		if (isBlack) {
			if (MoveValidation.isValidPoint(row_2, col_2)) {
				if (board.getTile(row_2, col_2).getValue() == 2 || board.getTile(row_2, col_2).getValue() == 22)
					return false;
			}
			if (MoveValidation.isValidPoint(row_2, col2)) {
				if ((board.getTile(row_2, col2).getValue() == 2 || board.getTile(row_2, col2).getValue() == 22)) {
					return false;
				}

			}
			if (MoveValidation.isValidPoint(row2, col_2)) {
				if ((board.getTile(row2, col_2).getValue() == 2 || board.getTile(row2, col_2).getValue() == 22)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row2, col2)) {
				if ((board.getTile(row2, col2).getValue() == 2 || board.getTile(row2, col2).getValue() == 22)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row_1, col_1)) {
				if ((board.getTile(row_1, col_1).getValue() == 2 || board.getTile(row_1, col_1).getValue() == 22)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row_1, col1)) {
				if ((board.getTile(row_1, col1).getValue() == 2 || board.getTile(row_1, col1).getValue() == 22)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row1, col_1)) {
				if ((board.getTile(row1, col_1).getValue() == 2 || board.getTile(row1, col_1).getValue() == 22)) {
					return false;
				}
			}
			if (MoveValidation.isValidPoint(row1, col1)) {
				if ((board.getTile(row1, col1).getValue() == 2 || board.getTile(row1, col1).getValue() == 22)) {
					return false;
				}
			}
			return true;

		}

		return false;
	}

	public ArrayList<Tile> getSuggestedTilesArray() {
		// TODO Auto-generated method stub
		ArrayList<Tile> tiles = new ArrayList<>();
		HashMap<Tile, ArrayList<Tile>> suggested = board.checkAvailableMoves(isP1Turn);
		for (ArrayList<Tile> arr : suggested.values()) {
			for (Tile tile : arr)
				tiles.add(tile);
		}
		return tiles;
	}

	public HashMap<Tile, ArrayList<Tile>> getAvailableMoves() {
		return board.checkAvailableMoves(isP1Turn);
	}
	@Override
	public String toString() {
		if(LoadedFileName==null) {
		return "-----------------------------------------------------------------\n"
				+"Player 1 :" + player1.getUsername() + " Player 2 :" + player2.getUsername()
				+ "\n-----------------------------------------------------------------";
		}
		else {
			return "This is a loaded game from "+LoadedFileName+" file "
					+ "\n-----------------------------------------------------------------";
		}
	}
	public String briefToString() {
		return "Game id=" + id + ", player1=" + player1 + ", player2=" + player2 + ",\ngameDate=" + gameDate
				+ ", winner=" + winner + ", gameDuration=" + gameDuration + "]";
	}

	public static boolean isOwnKill() {
		return ownKill;
	}

	public static void setOwnKill(boolean ownKill) {
		Game.ownKill = ownKill;
	}

	public static Tile getKilledSoldier() {
		return killedSoldier;
	}

	public static void setKilledSoldier(Tile killedSoldier) {
		Game.killedSoldier = killedSoldier;
	}

	public void addSoldier(int x, int y) {
		Tile[][] m = board.getMyBoard();
		if (isP1Turn) {
			m[x][y] = new WhiteSoldier(x, y);
			m[x][y].upgradeToQueen();
		} else {
			m[x][y] = new BlackSoldier(x, y);
			m[x][y].upgradeToQueen();
		}
		board.setMyBoard(m);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getLoadedFileName() {
		return LoadedFileName;
	}

	public void setLoadedFileName(String loadedFileName) {
		LoadedFileName = loadedFileName;
	}
	
}