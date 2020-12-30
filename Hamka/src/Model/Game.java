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
import Utils.MoveType;
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

	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
		gameDate = java.util.Calendar.getInstance().getTime();
		id = ++Serial;
		isP1Turn = true;
		notFinished = true;
		runGameScanner = new Scanner(System.in);
		checkColorsToShow();
	}

	public Game() {

	}

	/*public void run() {
		GameStatus status = GameStatus.FINISH;
		String turnString, scoresStatus, moveInput;
		Player playerToPlay;
		int fromX, fromY, toX, toY;
		System.out.println(
				"NOTE:\nIn each turn the player should enter the indexes of the soldier he wants to move, then the indexes of the new tile.\nBut he also can write: quit, pause, resume, save game");
		System.out.println();
		while (!finishGame(status)) {
			playerToPlay = isP1Turn() ? getPlayer1() : getPlayer2();
			turnString = String.format("%s's turn:", playerToPlay.getUsername());
			System.out.println(turnString);
			System.out.println("Time for previos turn : " + getTime());
			scoresStatus = "Current scores status: " + getPlayer1().getUsername() + ": " + getPlayer1().getScore()
					+ " , " + getPlayer2().getUsername() + ": " + getPlayer2().getScore();
			System.out.println(scoresStatus);
			checkColorsToShow();
			System.out.println(getGameState());
			boolean legalFirstMoveInput = false;
			while (!legalFirstMoveInput) {
				System.out
				.println("Please enter the indexes [format: (row,col)] of the soldier that you want to move:");
				moveInput = runGameScanner.nextLine();
				if (moveInput.equals("quit")) {
					status = GameStatus.QUIT;
					finishGame(status);
					System.out.println("Game finished. " + playerToPlay.getUsername() + " had quited");
					System.out.println(getWinner().getUsername() + " wins !!!");
					SysData.getInstance().addFinishedGame(this);
					legalFirstMoveInput = true;
				} else if (moveInput.equals("save game")) {
					SysData.getInstance().addPausedGame(this);
					System.out.println("This game had been paused and saved\nSee you later");
					status = GameStatus.PAUSE;
					legalFirstMoveInput = true;
				} else {
					if (validateMoveInput(moveInput)) {
						boolean legalSecondMoveInput = false;
						char[] moveArr = moveInput.toCharArray();
						fromX = Integer.parseInt(String.valueOf(moveArr[1]));
						fromY = Integer.parseInt(String.valueOf(moveArr[3]));
						while (!legalSecondMoveInput) {
							System.out.println("Please enter the indexes [format: (row,col)] of the distination tile:");
							moveInput = runGameScanner.nextLine();
							if (validateMoveInput(moveInput)) {
								moveArr = moveInput.toCharArray();
								toX = Integer.parseInt(String.valueOf(moveArr[1]));
								toY = Integer.parseInt(String.valueOf(moveArr[3]));
								move(fromX, fromY, toX, toY);
								board.turnOffAllTilesColor();
								legalSecondMoveInput = true;
							} else {
								System.out.println("Illegal move input! Try Again");
							}
						}
						legalFirstMoveInput = true;
					}
				}
				if (!legalFirstMoveInput)
					System.out.println("Illegal move input! Try Again");
			}

		}
	} */

	public Player showPlayerTurn() {

		return (isP1Turn() ? getPlayer1() : getPlayer2());

	}

//	public boolean validateMoveInput(String moveInput) {
//
//		// TODO Auto-generated method stub
//		// format (x,y)
//		// String regex = "/n(X,Y)/";
//		// Pattern pattern = Pattern.compile(regex);
//		// System.out.println(pattern.matcher(moveInput).find());
//		// return pattern.matcher(moveInput).find();
//		if (moveInput.length() > 5)
//			return false;
//		if (moveInput.charAt(0) != '(' || moveInput.charAt(2) != ',' || moveInput.charAt(4) != ')')
//			return false;
//		if (Character.isDigit(moveInput.charAt(1)) && Character.isDigit(moveInput.charAt(3))) {
//			int x = Integer.parseInt(String.valueOf(moveInput.charAt(1)));
//			int y = Integer.parseInt(String.valueOf(moveInput.charAt(3)));
//			return x >= 0 && x < 8 && y >= 0 && y < 8;
//		}
//		return false;
//	}

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

	public static  boolean getIsP1Turn() {
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
		} else if (status.equals(GameStatus.FINISH) && board.checkAvailableMoves(isP1Turn).isEmpty()) {
			if (player1.getScore() > player2.getScore())
				winner = player1;
			else
				winner = player2;
			notFinished = false;
			return true;
		} else
			return false;
	}
/*
	public void popQuestion() {
		Question question = SysData.getInstance().popQuestion();
		System.out.println("You Got a bunos question :D ===>");
		System.out.println("Question: " + question.getText());
		System.out.print("Answers: ");
		int i = 0;
		for (String answer : question.getAnswers()) {
			System.out.print("\t" + i + ". " + answer);
		}
		System.out.println();

		System.out.println("Enter the number of the right question:");
		Scanner scan = new Scanner(System.in);
		int ans = scan.nextInt();
		int score = question.checkAnswer(ans);
		if (score > 0)
			System.out.println("Good job!! You had earned: " + score + " points.");
		else
			System.out.println("Bad luck :( You had lost: " + score + " points.");
		Player player = isP1Turn ? player1 : player2;
		player.updateScore(score);
	}
*/

	public MoveType move(int xStart, int yStart, int xEnd, int yEnd) {
		Player p = null;
		p = isP1Turn ? player1 : player2;
//		HashMap<Tile, ArrayList<Tile>> mp = board.checkAvailableSkips(isP1Turn);
//		Tile start,end;
//		if(board.getMyBoard()[yStart][xStart] instanceof BlackSoldier)
//			start=new BlackSoldier(yStart,xStart);
//		else
//			start=new WhiteSoldier(yEnd,xEnd);
//		if(board.getMyBoard()[yEnd][xEnd] instanceof BlackSoldier)
//			end=new BlackSoldier(yEnd,xEnd);
//		else
//			end=new WhiteSoldier(yEnd,xEnd);
//		boolean isIgnoreSkip = false;
//		boolean isSkip = false;
//		ArrayList<Tile> availableTilesToSkip = mp.get(start);
//		if (availableTilesToSkip == null) {
//			if (!mp.isEmpty())
//				isIgnoreSkip = true;
//		} else {
//			if (!availableTilesToSkip.contains(end)) {
//				isIgnoreSkip = true;
//			} else {
//				isSkip = true;
//			}
//		}
		MoveType MType=board.move(xStart, yStart, xEnd, yEnd, isP1Turn, p);
		if (MType.equals(MoveType.KILL)||MType.equals(MoveType.NORMAL)) {
			board.upgradeQueen(xEnd, yEnd);
		//	Tile destinationTile = board.getTile(xEnd, yEnd);
			//checkDestinatonTile(destinationTile);
//			if (!(isSkip && board.checkAvailableSkips(isP1Turn).containsKey(board.getMyBoard()[xEnd][yEnd])) && !board.getMyBoard()[xEnd][yEnd].getColor().equals(Color.YELLOW) )
//				isP1Turn = !isP1Turn;
//			notFinished = true;
//			if (isIgnoreSkip) {
//				((Tile) mp.keySet().toArray()[new Random().nextInt(mp.size())]).setValue(0);
//			}
			return MType;
		}
		return MType;
	}
	
	public void switchTurn() {
		isP1Turn = !isP1Turn;
	}
	
	/*private void checkDestinatonTile(Tile destinationTile) {
		// TODO Auto-generated method stub
		if (destinationTile.getColor().equals(Color.YELLOW))
			popQuestion();
		else if (destinationTile.getColor().equals(Color.BLUE)) {
			System.out.println("Congrats! you had earned a chance to bring back a soldier from death.");
			String tileInput;
			boolean leaglTile = false;
			Scanner scanner = new Scanner(System.in);
			while (!leaglTile) {
				do {
					System.out.println(
							"Please enter the tile coordinates [format: (row,col)] that you want the soldier to return to:");
					tileInput = scanner.nextLine();
				} while (!validateMoveInput(tileInput));
				scanner.close();
				char[] moveArr = tileInput.toCharArray();
				int x = Integer.parseInt(String.valueOf(moveArr[1]));
				int y = Integer.parseInt(String.valueOf(moveArr[3]));
				Tile tile = board.getTile(x, y);
				if (tile.getColor().equals(Color.WHITE)) {
					System.out.println("You must choose a black tile. Try again!");
				} else {
					HashMap<Tile, ArrayList<Tile>> suggestedSkips = board.checkAvailableSkips(isP1Turn);
					if (suggestedSkips.containsKey(tile))
						System.out.println("Illegal tile! You are to close of the contest's soldiers");
					else {
						leaglTile = true;
						tile.setValue(isP1Turn ? 1 : 2);
					}
				}
			}
		} else if (destinationTile.getColor().equals(Color.GREEN)) {
			Player player = isP1Turn ? player1 : player2;
			player.updateScore(Consts.POINTS_FOR_GREEN_TILE);
			System.out.println("You had earned " + Consts.POINTS_FOR_GREEN_TILE + " points.");
		} else if (destinationTile.getColor().equals(Color.RED)) {
			System.out.println("You had won a one more turn with the same soldier. [indexes: ("
					+ destinationTile.getRows() + "," + destinationTile.getCols() + ")");
			Scanner scanner = new Scanner(System.in);
			String input;
			do {
				System.out.println("Enter a new destination tile");
				input = scanner.nextLine();
			} while (!validateMoveInput(input));
			char[] moveArr = input.toCharArray();
			int x = Integer.parseInt(String.valueOf(moveArr[1]));
			int y = Integer.parseInt(String.valueOf(moveArr[3]));
			move(destinationTile.getRows(), destinationTile.getCols(), x, y);
		}
	}
*/
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
//		HashMap<Tile, ArrayList<Tile>> suggestedSkips = board.checkAvailableSkips(isP1Turn);
//		System.out.println("***************** SKIPS ************************");
//		for(Tile key : suggestedSkips.keySet()) {
//			System.out.println(key.longString());
//			for(Tile tile : suggestedSkips.get(key))
//					System.out.println("\t"+tile.longString());
//		}
//		System.out.println(getGameState());
//		System.out.println();
//		if (suggestedSkips.isEmpty()) {
//			board.colorAllTiles(suggestedTiles, Color.RED);
//		}

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

		//*********** Yellow tiles ****************
		board.showYellowTiles();

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

	@Override
	public String toString() {
		return "--------------------------------------------------\nPlayer 1 :"+player1.getUsername()+" Player 2 :"+player2.getUsername()+"\n--------------------------------------------------";
	}

	public String briefToString() {
		return "Game id=" + id + ", player1=" + player1 + ", player2=" + player2 + ",\ngameDate=" + gameDate
				+ ", winner=" + winner + ", gameDuration=" + gameDuration + "]";
	}
}