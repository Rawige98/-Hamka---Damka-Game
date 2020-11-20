package Model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Board {
	private Tile[][] myBoard;

	public Board() {
		setMyBoard(new Tile[8][8]);
		initBoard();
	}

	public Tile[][] getMyBoard() {
		return myBoard;
	}

	public void setMyBoard(Tile[][] myBoard) {
		this.myBoard = myBoard;
	}
	public void initBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (toIndex(i, j) >= 0 && toIndex(i, j) <= 11) {
					myBoard[i][j] = new Tile(2, Color.black, i, j);
				} else {
					if (toIndex(i, j) >= 20 && toIndex(i, j) <= 31)
						myBoard[i][j] = new Tile(1, Color.white, i, j);
					else {
						myBoard[i][j] = new Tile(0, Color.black, i, j);
					}
				}
			}
		}
	}

	/**
	 * Converts a point to an index of a black tile on the checker board, such that
	 * (1, 0) is index 0, (3, 0) is index 1, ... (7, 7) is index 31.
	 * 
	 * @param x the x-coordinate on the board (from 0 to 7 inclusive).
	 * @param y the y-coordinate on the board (from 0 to 7 inclusive).
	 * @return the index of the black tile or -1 if the point is not a black tile.
	 * @see {@link #toIndex(Point)}, {@link #toPoint(int)}
	 */
	public static int toIndex(int x, int y) {
		// Invalid (x, y) (i.e. not in board, or white tile)
		if (!isValidPoint(x, y)) {
			return -1;
		}
		return y * 4 + x / 2;
	}

	/**
	 * Checks if a point corresponds to a black tile on the checker board.
	 * 
	 * @param testPoint the point to check.
	 * @return true if and only if the point is on the board, specifically on a
	 *         black tile.
	 */
	public static boolean isValidPoint(int x, int y) {
		// Check that it is on the board
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			return false;
		}

		// Check that it is on a black tile
		if (x % 2 == y % 2) {
			return false;
		}

		return true;
	}

	public boolean move(int xStart, int yStart, int xEnd, int yEnd) {
		return true;
	}
	/**
	 * Determines if the specified move is valid based on the rules of checkers.
	 * 
	 * @param isP1Turn   the flag indicating if it is player 1's turn.
	 * @param startIndex the start index of the move.
	 * @param endIndex   the end index of the move.
	 * @param skipIndex ??  the index of the last skip this turn.
	 * @return true if the move is legal according to the rules of checkers.
	 */
	public boolean moveValidation(int xStart, int yStart, int xEnd, int yEnd,boolean isP1Turn) {
		if(toIndex(xStart, yStart)==toIndex(xEnd, yEnd))
			return false;
		if(toIndex(xStart, yStart)==-1||toIndex(xEnd, yEnd)==-1)
			return false;
		if(toIndex(xStart, yStart)<0||toIndex(xStart, yStart)>31||toIndex(xEnd, yEnd)<0||toIndex(xEnd, yEnd)>31)
			return false;
		if (!validateIDs(isP1Turn,xStart,yStart,xEnd,yEnd)) {
			return false;
		}
		
		return true;
	}

	private boolean validateIDs(boolean isP1Turn, int xStart, int yStart, int xEnd, int yEnd) {
		if(myBoard[xEnd][yEnd].getValue()!=0)
			return false;
		if ((isP1Turn &&myBoard[xStart][yStart].getValue()!=2&&myBoard[xStart][yStart].getValue()!=22)
				|| (!isP1Turn &&myBoard[xStart][yStart].getValue()!=1&&myBoard[xStart][yStart].getValue()!=11)) {
			return false;
		}
		//skip moves ?
		return true;
	}

	public void upgradeQueen(int x, int y) {
	}

	public ArrayList<Tile> checkAvailableMoves() {
		return null;
	}
}