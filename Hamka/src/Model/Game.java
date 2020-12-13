package Model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;

import Utils.Consts;
import Utils.GameStatus;

public class Game{
	private static int Serial = 0;
	private int id;
	private Player player1;
	private Player player2;
	private Date gameDate;
	private Board board;
	private Player winner;
	private Timer gameDuration;
	private static boolean isP1Turn;
	private TimeForPlayer myTimer;
    static boolean notFinished=false;
    private int Time = 0;

	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
		board = new Board();
		gameDate = java.util.Calendar.getInstance().getTime();
		id = ++Serial;
		isP1Turn = true;
		notFinished = true;
	    myTimer = new TimeForPlayer(new ColorTilesHandler() {
			
			@Override
			public void showColor(Color color) {
				// TODO Auto-generated method stub
				ArrayList<Tile> suggestedTiles = getSuggestedTilesArray();
				if(color.equals(Color.green)) {
					board.colorRandomTile(suggestedTiles, color);
//					System.out.println("ADDED GREEN TILE ");
//					System.out.println(getGameState());
				}
				else if(color.equals(Color.orange)) {
					board.colorAllTiles(suggestedTiles, color);
//					System.out.println("ADDED ORANGE TILE ");
//					System.out.println(getGameState());;
				}
				refreshBoard();
			}

			private void refreshBoard() {
				// TODO Auto-generated method stub
				
				System.out.println(getGameState());
			}
		});
		Runtime(myTimer);
		checkColorsToShow();
	}



	public Game() {

	}
	
	public void run() {
		GameStatus status = GameStatus.FINISH;
		Scanner runGameScanner = new Scanner(System.in);
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
			System.out.println("Time for previos turn : "+getTime());
			scoresStatus = "Current scores status: " + getPlayer1().getUsername() + ": "
					+getPlayer1().getScore()  + " , " + getPlayer2().getUsername() + ": "
					+ getPlayer2().getScore() ;
			System.out.println(scoresStatus);
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
								move(fromX, fromY, toX, toY);//////////
								legalSecondMoveInput = true;
							} else {
								System.out.println("Illegal move input! Try Again");
							}
						}
						legalFirstMoveInput = true;
					}
				}
			}
		

		}
	}
	
	public boolean validateMoveInput(String moveInput) {
		// TODO Auto-generated method stub
		// format (x,y)
		// String regex = "/n(X,Y)/";
		// Pattern pattern = Pattern.compile(regex);
		// System.out.println(pattern.matcher(moveInput).find());
		// return pattern.matcher(moveInput).find();
		if (moveInput.length() > 5)
			return false;
		if (moveInput.charAt(0) != '(' || moveInput.charAt(2) != ',' || moveInput.charAt(4) != ')')
			return false;
		if (Character.isDigit(moveInput.charAt(1)) && Character.isDigit(moveInput.charAt(3))) {
			int x = Integer.parseInt(String.valueOf(moveInput.charAt(1)));
			int y = Integer.parseInt(String.valueOf(moveInput.charAt(3)));
			return x >= 0 && x < 8 && y >= 0 && y < 8;
		}
		return false;
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

	public void setP1Turn(boolean isP1Turn) {
		this.isP1Turn = isP1Turn;
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
		}
		else if (status.equals(GameStatus.FINISH) && board.checkAvailableMoves(isP1Turn).isEmpty()) {
			if (player1.getScore() > player2.getScore())
				winner = player1;
			else
				winner = player2;
			notFinished = false;
			return true;
		} else
			return false;
	}

	public void popQuestion() {
	}
	
	public void Runtime(TimeForPlayer myTimer) {
		Thread t = new Thread(myTimer);
		t.start();
	}
	private int scoreForPlayer(int time) {

		return 60 - time;
	}

	public boolean move(int xStart, int yStart, int xEnd, int yEnd) {
		Player p = null;
		p = isP1Turn ? player1 : player2;
		if (board.move(xStart, yStart, xEnd, yEnd, isP1Turn, p)) {
			board.upgradeQueen(xEnd, yEnd);	
			Time = myTimer.getSecond();
			int score = p.getScore();
			score += scoreForPlayer(Time);
			p.setScore(score);
            isP1Turn = !isP1Turn;
            notFinished=true;
            myTimer.reset();
			Runtime(myTimer);
			
			return true;
		}
		return false;
	}

	public String getGameState() {
		return board.toString();
	}

	
	// ************************************** Colord tiles ****************************************
	
	public void checkColorsToShow() {
		// TODO Auto-generated method stub
		board.turnOffAllTilesColor();
		board.showYellowTiles();
		
		ArrayList<Tile> suggestedTiles = getSuggestedTilesArray() ;
		
		//************ Red tile **********
		HashMap<Tile, ArrayList<Tile>> suggestedSkips = board.checkAvailableSkips(isP1Turn);
		if(suggestedSkips.isEmpty()) {
			board.colorAllTiles(suggestedTiles, Color.red);
		}
		
		//*********** Blue tile **********
		int qCount=0, sCount=0 , queen, soldier;
		if(isP1Turn) {
			queen = 11;
			soldier = 1;
		}else {
			queen = 22;
			soldier = 2;
		}
		
		for(int i=0 ; i<Consts.ROWS ; i++) {
			for(int j=0 ; j<Consts.COLS ; j++) {
				Tile tile = board.getMyBoard()[i][j];
				if(tile.getValue() == soldier)
					sCount++;
				if(tile.getValue() == queen)
					qCount++;
			}
		}
		
		if(sCount == 2 && qCount == 1)
			board.colorRandomTile(suggestedTiles, Color.blue);
	}
	
	
	
	private ArrayList<Tile> getSuggestedTilesArray() {
		// TODO Auto-generated method stub
		ArrayList<Tile> tiles = new ArrayList<>();
		HashMap<Tile, ArrayList<Tile>> suggested = board.checkAvailableMoves(isP1Turn);
		for(ArrayList<Tile> arr : suggested.values()) {
			for(Tile tile : arr)
				tiles.add(tile);
		}
		return tiles;
	}



	@Override
	public String toString() {
		return "Game id=" + id + ", player1=" + player1 + ", player2=" + player2 + ",\ngameDate=" + gameDate
				+ ", winner=" + winner + ", gameDuration=" + gameDuration + "\n" + board;
	}

	public String briefToString() {
		return "Game id=" + id + ", player1=" + player1 + ", player2=" + player2 + ",\ngameDate=" + gameDate
				+ ", winner=" + winner + ", gameDuration=" + gameDuration + "]";
	}
	public static void main(String[] args) {
		Game n = new Game(new Player("Nagwan"), new Player("Tony"));
//		n.move(5, 0, 4, 1);
//		n.move(2, 3, 3, 2);
//		n.move(4, 1, 2, 3);
//		n.move(2, 5, 3, 4);
//		n.move(5, 2, 4, 1);
//		n.move(1, 6, 2, 5);
//		n.move(4, 1, 3, 2);
//		n.move(0, 5, 1, 6);
//		n.move(2, 3, 0, 5);
//		n.move(1, 2, 2, 3);
//		n.move(5, 6, 4, 7);
//		n.move(2, 1, 3, 0);
//		n.move(3, 2, 2, 1);
//		n.move(3, 0, 4, 1);
//		n.move(0, 5, 3, 2);
//		n.move(3, 4, 4, 5);
//		n.move(3, 2, 5, 0);
//		n.move(4,5,5,6);
//		n.move(4, 7, 3, 6);
//		n.move(2, 5, 4, 7);
//		n.move(5, 0, 3, 6);
		System.out.println("Bye");
		System.out.println(n.getGameState());
		System.out.println("BYEBYE");
		n.finishGame(GameStatus.QUIT);
	}

}