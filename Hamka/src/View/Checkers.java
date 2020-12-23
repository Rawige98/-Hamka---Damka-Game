package View;
////////////////////////////////////////////////////////////////
/************************************************************/
/**** CHECKER GAME APPLICATION APPLICATION USING JAVAFX *****/
/*****************  AUTHOR - TECH - RANCH *******************/
/**************     DATED  - FEBRUARY 23 2020 , SUNDAY  *****/
/************************************************************/
///////////////////////////////////////////////////////////////
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
public class Checkers extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	CheckersBoard board; 
	private Button newGameButton;  // Button for starting a new game.
	private Button resignButton;   // Button that a player can use to end 
	private Label message;  // Label for displaying messages to the user.
	public void start(Stage stage) {
		message = new Label("Click \"New Game\" to begin.");
		message.setTextFill( Color.rgb(100,255,100) ); // Light green.
		message.setFont( Font.font(null, FontWeight.BOLD, 18) );
		newGameButton = new Button("New Game");
		resignButton = new Button("Resign");
		board = new CheckersBoard(); // a subclass of Canvas, defined below
		board.drawBoard();  // draws the content of the checkerboard
		newGameButton.setOnAction( e -> board.doNewGame() );
		resignButton.setOnAction( e -> board.doResign() );
		board.setOnMousePressed( e -> board.mousePressed(e) );
		board.relocate(20,20);
		newGameButton.relocate(370, 120);
		resignButton.relocate(370, 200);
		message.relocate(20, 370);
		resignButton.setManaged(false);
		resignButton.resize(100,30);
		newGameButton.setManaged(false);
		newGameButton.resize(100,30);
		Pane root = new Pane();
		root.setPrefWidth(500);
		root.setPrefHeight(420);
		root.getChildren().addAll(board, newGameButton, resignButton, message);
		root.setStyle("-fx-background-color: darkgreen; "
				+ "-fx-border-color: darkred; -fx-border-width:3");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Tech-Ranch presents Checkers!");
		stage.show();
	} // end start()
	private static class CheckersMove {
		int fromRow, fromCol;  // Position of piece to be moved.
		int toRow, toCol;      // Square it is to move to.
		CheckersMove(int r1, int c1, int r2, int c2) {
			fromRow = r1;
			fromCol = c1;
			toRow = r2;
			toCol = c2;
		}
		boolean isJump() {
			return (fromRow - toRow == 2 || fromRow - toRow == -2);
		}
	}  // end class CheckersMove.
	private class CheckersBoard extends Canvas {
		CheckersData board; // The data for the checkers board is kept here.
		boolean gameInProgress; // Is a game currently in progress?
		int currentPlayer;      // Whose turn is it now?  The possible values
		int selectedRow, selectedCol;   // If the current player has selected a piece to
		CheckersMove[] legalMoves;  // An array containing the legal moves for the
		CheckersBoard() {
			super(324,324);  // canvas is 324-by-324 pixels
			board = new CheckersData();
			doNewGame();
		}
		void doNewGame() {
			if (gameInProgress == true) {
				// This should not be possible, but it doesn't hurt to check.
				message.setText("Finish the current game first!");
				return;
			}
			board.setUpGame();   // Set up the pieces.
			currentPlayer = CheckersData.RED;   // RED moves first.
			legalMoves = board.getLegalMoves(CheckersData.RED);  // Get RED's legal moves.
			selectedRow = -1;   // RED has not yet selected a piece to move.
			message.setText("Red:  Make your move.");
			gameInProgress = true;
			newGameButton.setDisable(true);
			resignButton.setDisable(false);
			drawBoard();
		}
		void doResign() {
			if (gameInProgress == false) {  // Should be impossible.
				message.setText("There is no game in progress!");
				return;
			}
			if (currentPlayer == CheckersData.RED)
				gameOver("RED resigns.  BLACK wins.");
			else
				gameOver("BLACK resigns.  RED wins.");
		}
		void gameOver(String str) {
			message.setText(str);
			newGameButton.setDisable(false);
			resignButton.setDisable(true);
			gameInProgress = false;
		}
		void doClickSquare(int row, int col) {
			for (int i = 0; i < legalMoves.length; i++)
				if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
					selectedRow = row;
					selectedCol = col;
					if (currentPlayer == CheckersData.RED)
						message.setText("RED:  Make your move.");
					else
						message.setText("BLACK:  Make your move.");
					drawBoard();
					return;
				}
			if (selectedRow < 0) {
				message.setText("Click the piece you want to move.");
				return;
			}
			for (int i = 0; i < legalMoves.length; i++)
				if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
				&& legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
					doMakeMove(legalMoves[i]);
					return;
				}

			message.setText("Click the square you want to move to.");

		}  // end doClickSquare()
		void doMakeMove(CheckersMove move) {
			board.makeMove(move);
			if (move.isJump()) {
				legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
				if (legalMoves != null) {
					if (currentPlayer == CheckersData.RED)
						message.setText("RED:  You must continue jumping.");
					else
						message.setText("BLACK:  You must continue jumping.");
					selectedRow = move.toRow;  // Since only one piece can be moved, select it.
					selectedCol = move.toCol;
					drawBoard();
					return;
				}
			}
			if (currentPlayer == CheckersData.RED) {
				currentPlayer = CheckersData.BLACK;
				legalMoves = board.getLegalMoves(currentPlayer);
				if (legalMoves == null)
					gameOver("BLACK has no moves.  RED wins.");
				else if (legalMoves[0].isJump())
					message.setText("BLACK:  Make your move.  You must jump.");
				else
					message.setText("BLACK:  Make your move.");
			}
			else {
				currentPlayer = CheckersData.RED;
				legalMoves = board.getLegalMoves(currentPlayer);
				if (legalMoves == null)
					gameOver("RED has no moves.  BLACK wins.");
				else if (legalMoves[0].isJump())
					message.setText("RED:  Make your move.  You must jump.");
				else
					message.setText("RED:  Make your move.");
			}

			selectedRow = -1;
			if (legalMoves != null) {
				boolean sameStartSquare = true;
				for (int i = 1; i < legalMoves.length; i++)
					if (legalMoves[i].fromRow != legalMoves[0].fromRow
					|| legalMoves[i].fromCol != legalMoves[0].fromCol) {
						sameStartSquare = false;
						break;
					}
				if (sameStartSquare) {
					selectedRow = legalMoves[0].fromRow;
					selectedCol = legalMoves[0].fromCol;
				}
			}
			drawBoard();
		}  // end doMakeMove();
		public void drawBoard() {
			GraphicsContext g = getGraphicsContext2D();
			g.setFont( Font.font(18) );
			g.setStroke(Color.DARKRED);
			g.setLineWidth(2);
			g.strokeRect(1, 1, 322, 322);
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if ( row % 2 == col % 2 )
						g.setFill(Color.LIGHTGRAY);
					else
						g.setFill(Color.GRAY);
					g.fillRect(2 + col*40, 2 + row*40, 40, 40);
					switch (board.pieceAt(row,col)) {
					case CheckersData.RED:
						g.setFill(Color.RED);
						g.fillOval(8 + col*40, 8 + row*40, 28, 28);
						break;
					case CheckersData.BLACK:
						g.setFill(Color.BLACK);
						g.fillOval(8 + col*40, 8 + row*40, 28, 28);
						break;
					case CheckersData.RED_KING:
						g.setFill(Color.RED);
						g.fillOval(8 + col*40, 8 + row*40, 28, 28);
						g.setFill(Color.WHITE);
						g.fillText("K", 15 + col*40, 29 + row*40);
						break;
					case CheckersData.BLACK_KING:
						g.setFill(Color.BLACK);
						g.fillOval(8 + col*40, 8 + row*40, 28, 28);
						g.setFill(Color.WHITE);
						g.fillText("K", 15 + col*40, 29 + row*40);
						break;
					}
				}
			}

			if (gameInProgress) {
				g.setStroke(Color.CYAN);
				g.setLineWidth(4);
				for (int i = 0; i < legalMoves.length; i++) {
					g.strokeRect(4 + legalMoves[i].fromCol*40, 4 + legalMoves[i].fromRow*40, 36, 36);
				}
				if (selectedRow >= 0) {
					g.setStroke(Color.YELLOW);
					g.setLineWidth(4);
					g.strokeRect(4 + selectedCol*40, 4 + selectedRow*40, 36, 36);
					g.setStroke(Color.LIME);
					g.setLineWidth(4);
					for (int i = 0; i < legalMoves.length; i++) {
						if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow) {
							g.strokeRect(4 + legalMoves[i].toCol*40, 4 + legalMoves[i].toRow*40, 36, 36);
						}
					}
				}
			}

		}  // end drawBoard()

		public void mousePressed(MouseEvent evt) {
			if (gameInProgress == false)
				message.setText("Click \"New Game\" to start a new game.");
			else {
				int col = (int)((evt.getX() - 2) / 40);
				int row = (int)((evt.getY() - 2) / 40);
				if (col >= 0 && col < 8 && row >= 0 && row < 8)
					doClickSquare(row,col);
			}
		}
	}  // end class Board
	private static class CheckersData {
		static final int
		EMPTY = 0,
		RED = 1,
		RED_KING = 2,
		BLACK = 3,
		BLACK_KING = 4;
		int[][] board;  // board[r][c] is the contents of row r, column c.  
		CheckersData() {
			board = new int[8][8];
			setUpGame();
		}
		void setUpGame() {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if ( row % 2 == col % 2 ) {
						if (row < 3)
							board[row][col] = BLACK;
						else if (row > 4)
							board[row][col] = RED;
						else
							board[row][col] = EMPTY;
					}
					else {
						board[row][col] = EMPTY;
					}
				}
			}
		}  // end setUpGame()
		int pieceAt(int row, int col) {
			return board[row][col];
		}
		void makeMove(CheckersMove move) {
			makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
		}
		void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
			board[toRow][toCol] = board[fromRow][fromCol];
			board[fromRow][fromCol] = EMPTY;
			if (fromRow - toRow == 2 || fromRow - toRow == -2) {
				int jumpRow = (fromRow + toRow) / 2;  // Row of the jumped piece.
				int jumpCol = (fromCol + toCol) / 2;  // Column of the jumped piece.
				board[jumpRow][jumpCol] = EMPTY;
			}
			if (toRow == 0 && board[toRow][toCol] == RED)
				board[toRow][toCol] = RED_KING;
			if (toRow == 7 && board[toRow][toCol] == BLACK)
				board[toRow][toCol] = BLACK_KING;
		}
		CheckersMove[] getLegalMoves(int player) {
			if (player != RED && player != BLACK)
				return null;
			int playerKing;  // The constant representing a King belonging to player.
			if (player == RED)
				playerKing = RED_KING;
			else
				playerKing = BLACK_KING;
			ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  // Moves will be stored in this list.
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {
					if (board[row][col] == player || board[row][col] == playerKing) {
						if (canJump(player, row, col, row+1, col+1, row+2, col+2))
							moves.add(new CheckersMove(row, col, row+2, col+2));
						if (canJump(player, row, col, row-1, col+1, row-2, col+2))
							moves.add(new CheckersMove(row, col, row-2, col+2));
						if (canJump(player, row, col, row+1, col-1, row+2, col-2))
							moves.add(new CheckersMove(row, col, row+2, col-2));
						if (canJump(player, row, col, row-1, col-1, row-2, col-2))
							moves.add(new CheckersMove(row, col, row-2, col-2));
					}
				}
			}
			if (moves.size() == 0) {
				for (int row = 0; row < 8; row++) {
					for (int col = 0; col < 8; col++) {
						if (board[row][col] == player || board[row][col] == playerKing) {
							if (canMove(player,row,col,row+1,col+1))
								moves.add(new CheckersMove(row,col,row+1,col+1));
							if (canMove(player,row,col,row-1,col+1))
								moves.add(new CheckersMove(row,col,row-1,col+1));
							if (canMove(player,row,col,row+1,col-1))
								moves.add(new CheckersMove(row,col,row+1,col-1));
							if (canMove(player,row,col,row-1,col-1))
								moves.add(new CheckersMove(row,col,row-1,col-1));
						}
					}
				}
			}
			if (moves.size() == 0)
				return null;
			else {
				CheckersMove[] moveArray = new CheckersMove[moves.size()];
				for (int i = 0; i < moves.size(); i++)
					moveArray[i] = moves.get(i);
				return moveArray;
			}

		}  // end getLegalMoves
		CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
			if (player != RED && player != BLACK)
				return null;
			int playerKing;  // The constant representing a King belonging to player.
			if (player == RED)
				playerKing = RED_KING;
			else
				playerKing = BLACK_KING;
			ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  // The legal jumps will be stored in this list.
			if (board[row][col] == player || board[row][col] == playerKing) {
				if (canJump(player, row, col, row+1, col+1, row+2, col+2))
					moves.add(new CheckersMove(row, col, row+2, col+2));
				if (canJump(player, row, col, row-1, col+1, row-2, col+2))
					moves.add(new CheckersMove(row, col, row-2, col+2));
				if (canJump(player, row, col, row+1, col-1, row+2, col-2))
					moves.add(new CheckersMove(row, col, row+2, col-2));
				if (canJump(player, row, col, row-1, col-1, row-2, col-2))
					moves.add(new CheckersMove(row, col, row-2, col-2));
			}
			if (moves.size() == 0)
				return null;
			else {
				CheckersMove[] moveArray = new CheckersMove[moves.size()];
				for (int i = 0; i < moves.size(); i++)
					moveArray[i] = moves.get(i);
				return moveArray;
			}
		}  // end getLegalJumpsFrom()
		private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
			if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
				return false;  // (r3,c3) is off the board.
			if (board[r3][c3] != EMPTY)
				return false;  // (r3,c3) already contains a piece.
			if (player == RED) {
				if (board[r1][c1] == RED && r3 > r1)
					return false;  // Regular red piece can only move up.
				if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
					return false;  // There is no black piece to jump.
				return true;  // The jump is legal.
			}
			else {
				if (board[r1][c1] == BLACK && r3 < r1)
					return false;  // Regular black piece can only move downn.
				if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
					return false;  // There is no red piece to jump.
				return true;  // The jump is legal.
			}

		}  // end canJump()
		private boolean canMove(int player, int r1, int c1, int r2, int c2) {

			if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
				return false;  // (r2,c2) is off the board.

			if (board[r2][c2] != EMPTY)
				return false;  // (r2,c2) already contains a piece.

			if (player == RED) {
				if (board[r1][c1] == RED && r2 > r1)
					return false;  // Regular red piece can only move down.
				return true;  // The move is legal.
			}
			else {
				if (board[r1][c1] == BLACK && r2 < r1)
					return false;  // Regular black piece can only move up.
				return true;  // The move is legal.
			}
		}  // end canMove()
	} // end class CheckersData

} // end class Checkers
///////////////////////////////////////////////////////////////
////////////////////LET's MAKE CODING FUN! ///////////////////
///////////////////////////////////////////////////////////////