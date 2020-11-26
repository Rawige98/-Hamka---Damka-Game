package Model;

import java.util.ArrayList;
import java.util.Scanner;

import Utils.Difficulty;
import Utils.JsonParser;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		Scanner scanner = new Scanner(System.in);
		int option;
		System.out.println("~~ Welcome to Hamka ~~");
		System.out.println();
		SysData.getInstance().loadPausedGames();
		SysData.getInstance().loadQuestions(null);
		SysData.getInstance().loadFinishedGames();
		SysData.getInstance().loadRules();
		boolean exit = false;
		while(!exit) {
			main.printDashboard();
			option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
			case 1:
				System.out.println("Enter player1 username:");
				String p1 = scanner.nextLine();
				Player player1 = new Player(p1);
				System.out.println("Enter player2 username:");
				String p2 = scanner.nextLine();
				Player player2 = new Player(p2);
				System.out.println();
				System.out.println("Let's start the game ===>");
				System.out.println();
				System.out.println(player1.getUsername() + " => White (1) , " + player2.getUsername() + " => Black (2)");
				System.out.println();
				Game game = new Game(player1, player2);
				main.runGame(game);
				break;
			case 2:
				main.printRules();
				break;
			case 3:
				main.printQuestions();
				break;
			case 4:
				break;
			case 5:
				exit = true;
				scanner.close();
				System.out.println("Game ended.\nSee you later");
				SysData.getInstance().writePausedGames();
				break;
			default:
				System.err.println("Illegal option! Try again.");
				break;
			}
		}
	}


	private void runGame(Game game) {
		// TODO Auto-generated method stub
		boolean finished = false;
		Scanner scanner = new Scanner(System.in);
		String turnString, scoresStatus, moveInput;
		Player playerToPlay;
		int fromX, fromY, toX, toY;
		System.out.println("NOTE:\nIn each turn the player should enter the indexes of the soldier he wants to move, then the indexes of the new tile.\nBut he also can write: quit, pause, resume");
		System.out.println();
		while(!finished) {
			playerToPlay = game.isP1Turn() ? game.getPlayer1() : game.getPlayer2();
			turnString = String.format("%s's turn:", playerToPlay.getUsername());
			System.out.println(turnString);

			scoresStatus = "Current scores status: "+ game.getPlayer1().getUsername() +": " + game.getPlayer1().getScore() + " , " +  game.getPlayer2().getUsername() +": " + game.getPlayer2().getScore();
			System.out.println(scoresStatus);			
			System.out.println(game.getGameState());
			System.out.println("Please enter the indexes (row,col) of the soldier that you want to move:");
			moveInput = scanner.nextLine();
			if(moveInput.equals("quit")) {
				//game.finishGame();
				Player winPlayer = game.isP1Turn() ? game.getPlayer2() : game.getPlayer1();
				game.setWinner(winPlayer);
				System.out.println("Game finished. " + playerToPlay.getUsername() + " had quited");
				System.out.println(winPlayer.getUsername() + " wins !!!");
				finished = true;
				SysData.getInstance().getPausedGames().add(game);
			}else {
				if(validateMoveInput(moveInput)) {
					char[] moveArr = moveInput.toCharArray(); 
					fromX = Integer.parseInt(String.valueOf(moveArr[1]));
					fromY = Integer.parseInt(String.valueOf(moveArr[3]));
					System.out.println("Please enter the indexes (row,col) of the distination tile:");
					moveInput = scanner.nextLine();
					if(validateMoveInput(moveInput)) {
						moveArr = moveInput.toCharArray(); 
						toX = Integer.parseInt(String.valueOf(moveArr[1]));
						toY = Integer.parseInt(String.valueOf(moveArr[3]));
						game.move(fromX, fromY, toX, toY);
					}
				}
			}
		}
	}

	private boolean validateMoveInput(String moveInput) {
		// TODO Auto-generated method stub
		// format (x,y)
		return true;
	}

	public void printDashboard() {
		String toPrint = String.format("Please choose a game option:\n\t1.Play\n\t2.Game Rules\n\t3.Questions\n\t4.View History\n\t5.Exit");
		System.out.println(toPrint);
	}

	public void printRules() {
		System.out.println();
		for(String str : SysData.getInstance().getRules()) {
			System.out.println(str);
		}
		System.out.println();
	}

	private void printQuestions() {
		// TODO Auto-generated method stub
		System.out.println();
		for(Difficulty difficulty : SysData.getInstance().getQuestions().keySet()) {
			System.out.println("Difficulty level - " + difficulty + ":");
			for(Question question : SysData.getInstance().getQuestions().get(difficulty)) {
				System.out.println(question);
			}
			System.out.println();
		}
		System.out.println();
	}
}
