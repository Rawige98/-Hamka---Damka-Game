package Model;

import java.util.Scanner;
import java.util.regex.Pattern;

import Utils.DataType;
import Utils.Difficulty;
import Utils.GameStatus;

/**
 * Main class is the class that responsable for activate the whole application
 * 
 * @author rawix
 *
 */
public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		Scanner scanner = new Scanner(System.in);
		String option;
		int optionNumbr;
		System.out.println("~~ Welcome to Hamka ~~");
		initSysData();
		boolean exit = false;
		while (!exit) {
			main.printDashboard();
			System.out.println();
			option = scanner.nextLine();
			optionNumbr = validateOptionInput(option);
			switch (optionNumbr) {
			case 1:
				System.out.println("Enter player1 username:");
				String p1 = scanner.nextLine();
				Player player1 = new Player(p1);
				System.out.println("Enter player2 username:");
				String p2 = scanner.nextLine();
				Player player2 = new Player(p2);
				System.out.println();
				System.out.println("Let's start the game ===>");
				System.out
						.println(player1.getUsername() + " => White (1) , " + player2.getUsername() + " => Black (2)");
				System.out.println();
				Game game = new Game(player1, player2);
//				main.runGame(game);
//				game.run();
				break;
			case 2:
				main.printRules();
				break;
			case 3:
				System.out.println("Paused games:");
				int i = 1;
				for (Game g : SysData.getInstance().getPausedGames()) {
					System.out.println(i++ + ". " + g.toString());
				}
				break;
			case 4:
				main.printQuestions();
				break;
			case 5:
				System.out.println("Finished games history:");
				int j = 1;
				for (Game g : SysData.getInstance().getGames()) {
					System.out.println(j++ + ". " + g.toString());
				}
				break;

			case 6:
				exit = true;
				scanner.close();
				System.out.println("Thanks for playing.\nSee you later");
				if (SysData.getInstance().existGame())
					System.out.println("\nSuccessfully saved game data");
				else
					System.err.println("\nFailed to save game data");
				break;
			default:
				System.err.println("Illegal option! Try again.\n");
				break;
			}
		}
	}

	private static int validateOptionInput(String option) {
		// TODO Auto-generated method stub
		if (!option.matches("[0-9]"))
			return -1;
		return Integer.parseInt(option);
	}

	private static void initSysData() {
		// TODO Auto-generated method stub
		SysData.getInstance().loadData(DataType.FINISHED_GAMES);
		SysData.getInstance().loadQuestions(null);
		SysData.getInstance().loadData(DataType.PAUSED_GAMES);
		SysData.getInstance().loadData(DataType.RULES);
	}
	/*
	 * private void runGame(Game game) { // TODO Auto-generated method stub
	 * GameStatus status = GameStatus.FINISH; Scanner runGameScanner = new
	 * Scanner(System.in); String turnString, scoresStatus, moveInput; Player
	 * playerToPlay; int fromX, fromY, toX, toY; System.out.println(
	 * "NOTE:\nIn each turn the player should enter the indexes of the soldier he wants to move, then the indexes of the new tile.\nBut he also can write: quit, pause, resume, save game"
	 * ); System.out.println(); while (!game.finishGame(status)) { playerToPlay =
	 * game.isP1Turn() ? game.getPlayer1() : game.getPlayer2(); turnString =
	 * String.format("%s's turn:", playerToPlay.getUsername());
	 * System.out.println(turnString);
	 * System.out.println("Time for previos turn : "+game.getTime()); scoresStatus =
	 * "Current scores status: " + game.getPlayer1().getUsername() + ": " +
	 * game.getPlayer1().getScore() + " , " + game.getPlayer2().getUsername() + ": "
	 * + game.getPlayer2().getScore() ; System.out.println(scoresStatus);
	 * System.out.println(game.getGameState()); boolean legalFirstMoveInput = false;
	 * while (!legalFirstMoveInput) { System.out
	 * .println("Please enter the indexes [format: (row,col)] of the soldier that you want to move:"
	 * ); moveInput = runGameScanner.nextLine(); if (moveInput.equals("quit")) {
	 * status = GameStatus.QUIT; game.finishGame(status);
	 * System.out.println("Game finished. " + playerToPlay.getUsername() +
	 * " had quited"); System.out.println(game.getWinner().getUsername() +
	 * " wins !!!"); SysData.getInstance().addFinishedGame(game);
	 * legalFirstMoveInput = true; } else if (moveInput.equals("save game")) {
	 * SysData.getInstance().addPausedGame(game);
	 * System.out.println("This game had been paused and saved\nSee you later");
	 * status = GameStatus.PAUSE; legalFirstMoveInput = true; } else { if
	 * (validateMoveInput(moveInput)) { boolean legalSecondMoveInput = false; char[]
	 * moveArr = moveInput.toCharArray(); fromX =
	 * Integer.parseInt(String.valueOf(moveArr[1])); fromY =
	 * Integer.parseInt(String.valueOf(moveArr[3])); while (!legalSecondMoveInput) {
	 * System.out.
	 * println("Please enter the indexes [format: (row,col)] of the distination tile:"
	 * ); moveInput = runGameScanner.nextLine(); if (validateMoveInput(moveInput)) {
	 * moveArr = moveInput.toCharArray(); toX =
	 * Integer.parseInt(String.valueOf(moveArr[1])); toY =
	 * Integer.parseInt(String.valueOf(moveArr[3])); game.move(fromX, fromY, toX,
	 * toY);////////// legalSecondMoveInput = true; } else {
	 * System.out.println("Illegal move input! Try Again"); } } legalFirstMoveInput
	 * = true; } } }
	 * 
	 * 
	 * } }
	 * 
	 */

	
	
	

	private boolean validateMoveInput(String moveInput) {
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

	public void printDashboard() {
		String toPrint = String.format(
				"Please choose a game option:\n\t1.Play\n\t2.Game Rules\n\t3.Load games\n\t4.Questions\n\t5.View History\n\t6.Exit");
		System.out.println(toPrint);
	}

	public void printRules() {
		System.out.println();
		for (String str : SysData.getInstance().getRules()) {
			System.out.println(str);
		}
		System.out.println();
	}

	private void printQuestions() {
		// TODO Auto-generated method stub
		System.out.println("Game questions:");
		for (Difficulty difficulty : SysData.getInstance().getQuestions().keySet()) {
			System.out.println("\tDifficulty level - " + difficulty + ":");
			for (Question question : SysData.getInstance().getQuestions().get(difficulty)) {
				System.out.println("\t" + question);
			}
			System.out.println();
		}
		System.out.println();
	}
}
