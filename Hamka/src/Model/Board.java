package Model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

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

	/**
	 * Initialize the game board to start the game
	 */
	public void initBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (toIndex(i, j) >= 0 && toIndex(i, j) <= 11) {
					myBoard[i][j] = new Tile(2, Color.black, i, j);

				} else {
					if (toIndex(i, j) >= 20 && toIndex(i, j) <= 31) {
						myBoard[i][j] = new Tile(1, Color.black, i, j);

					} else {
						if (i % 2 != j % 2)
							myBoard[i][j] = new Tile(0, Color.white, i, j);
						else
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

	/**
	 * in this method we move the soldier after check the validation
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @return true if the soldier move successfully
	 */
	public boolean move(int xStart, int yStart, int xEnd, int yEnd, boolean isP1Turn, Player p) {
		if (moveValidation(xStart, yStart, xEnd, yEnd, isP1Turn, false)) {
			int dx = xEnd - xStart;
			// if its not a skip
			if (Math.abs(dx) != 2) {
				myBoard[yEnd][xEnd].setValue(myBoard[yStart][xStart].getValue());
				myBoard[yStart][xStart].setValue(0);
				return true;
			} else {
				int xmid = (xStart + xEnd) / 2;
				int ymid = (yStart + yEnd) / 2;
				myBoard[ymid][xmid].setValue(0);
				myBoard[yEnd][xEnd].setValue(myBoard[yStart][xStart].getValue());
				myBoard[yStart][xStart].setValue(0);
				p.setScore(100);
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if the specified move is valid based on the rules of checkers.
	 * 
	 * @param isP1Turn   the flag indicating if it is player 1's turn.
	 * @param startIndex the start index of the move.
	 * @param endIndex   the end index of the move.
	 * @return true if the move is legal according to the rules of checkers.
	 */
	public boolean moveValidation(int xStart, int yStart, int xEnd, int yEnd, boolean isP1Turn, boolean finish) {
		if (toIndex(xStart, yStart) == toIndex(xEnd, yEnd)) {
			if (finish)
				return false;
			System.out.println("you insert the same coordinates");
			return false;
		}
		if (toIndex(xStart, yStart) == -1 || toIndex(xEnd, yEnd) == -1) {
			if (finish)
				return false;
			System.out.println("you cant move to white Tile");
			return false;
		}
		if (toIndex(xStart, yStart) < 0 || toIndex(xStart, yStart) > 31 || toIndex(xEnd, yEnd) < 0
				|| toIndex(xEnd, yEnd) > 31) {
			if (finish)
				return false;
			System.out.println("illegal coordinates");
			return false;
		}
		if (!validateIDs(isP1Turn, xStart, yStart, xEnd, yEnd, finish)) {
			if (finish)
				return false;
			System.out.println("ValidID");
			return false;
		}
		if (!validateDistance(isP1Turn, xStart, yStart, xEnd, yEnd)) {
			if (finish)
				return false;
			System.out.println("validDistnace");
			return false;
		}
		if (!skipValidation(isP1Turn, xStart, yStart, xEnd, yEnd)) {
			if (finish)
				return false;
			System.out.println("validSkip");
			return false;
		}
		return true;
	}

	/**
	 * in this method we check if the skip is legal by check the distance
	 * 
	 * @param isP1Turn
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @return true if the skip is legal
	 */
	public boolean skipValidation(boolean isP1Turn, int xStart, int yStart, int xEnd, int yEnd) {
		int dx = xEnd - xStart;
		// if its not a skip
		if (Math.abs(dx) != 2) {
			return true;
		}

		int xmid = (xStart + xEnd) / 2;
		int ymid = (yStart + yEnd) / 2;
		if ((myBoard[yStart][xStart].getValue() == 1 && myBoard[ymid][xmid].getValue() != 2)
				|| (myBoard[yStart][xStart].getValue() == 2 && myBoard[ymid][xmid].getValue() != 1))
			return false;
		if (!isValidPoint(xEnd, yEnd))
			return false;
		if (myBoard[yEnd][xEnd].getValue() != 0)
			return false;

		return true;
	}

	/**
	 * Validates all ID related values for the start, end, and middle (if the move
	 * is a skip).
	 * 
	 * @param board      the current board to check against.
	 * @param isP1Turn   the flag indicating if it is player 1's turn.
	 * @param startIndex the start index of the move.
	 * @param endIndex   the end index of the move.
	 * @return true if and only if all IDs are valid.
	 */
	private boolean validateIDs(boolean isP1Turn, int xStart, int yStart, int xEnd, int yEnd, boolean finish) {
		// check if the end is empty
		if (myBoard[yEnd][xEnd].getValue() != 0) {
			if (finish)
				return false;
			System.out.println("Tile isnt empty");
			return false;
		}
		// check if the the player play with his soldiers
		if ((!isP1Turn && myBoard[yStart][xStart].getValue() != 2 && myBoard[yStart][xStart].getValue() != 22)
				|| (isP1Turn && myBoard[yStart][xStart].getValue() != 1 && myBoard[yStart][xStart].getValue() != 11)) {
			if (finish)
				return false;
			System.out.println("thats not your soldier");
			return false;
		}
		return true;
	}

	/**
	 * Checks that the move is diagonal and magnitude 1 or 2 in the correct
	 * direction. If the magnitude is not 2 (i.e. not a skip), it checks that no
	 * skips are available by other checkers of the same player.
	 * 
	 * @param board      the current board to check against.
	 * @param isP1Turn   the flag indicating if it is player 1's turn.
	 * @param startIndex the start index of the move.
	 * @param endIndex   the end index of the move.
	 * @return true if and only if the move distance is valid.
	 */
	private boolean validateDistance(boolean isP1Turn, int xStart, int yStart, int xEnd, int yEnd) {
		// Check that it was a diagonal move
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) > 2 || dx == 0) {
			return false;
		}
		// Check that it was in the right direction
		if ((myBoard[yStart][xStart].getValue() == 1 && dx > 0)
				|| (myBoard[yStart][xStart].getValue() == 2 && dx < 0)) {
			return false;
		}
		return true;
	}

	/**
	 * in this method we choose a Tile by the Coordinates X and Y and upgrade the
	 * soldier to queen
	 * 
	 * @param x
	 * @param y
	 * @return true if the soldier upgraded successfully
	 */
	public boolean upgradeQueen(int x, int y) {
		return myBoard[x][y].upgradeToQueen();
	}

	/**
	 * in this method we built a hash that have all the available moves for a player
	 * 
	 * @param isP1Turn
	 * @return hashmap with a key is a soldier in the board for the player and value
	 *         is a list that have the available moves for that soldier
	 */
	public HashMap<Tile, ArrayList<Tile>> checkAvailableMoves(boolean isP1Turn) {
		ArrayList<Tile> mySoldiers = new ArrayList<Tile>();
		HashMap<Tile, ArrayList<Tile>> availableMoves = new HashMap<Tile, ArrayList<Tile>>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isP1Turn) {
					if (myBoard[i][j].getValue() == 1 || myBoard[i][j].getValue() == 11)
						mySoldiers.add(myBoard[i][j]);
				} else {
					if (myBoard[i][j].getValue() == 2 || myBoard[i][j].getValue() == 22)
						mySoldiers.add(myBoard[i][j]);

				}
			}
		}
		for (Tile t : mySoldiers) {
			availableMoves.put(t, avilableMovesForTile(t, isP1Turn));
		}
		return availableMoves;
	}

	/**
	 * in this method we find the available moves for a specific soldier
	 * 
	 * @param t
	 * @param isP1Turn
	 * @return array list that have all the permitted moves for a soldier
	 */
	public ArrayList<Tile> avilableMovesForTile(Tile t, boolean isP1Turn) {
		ArrayList<Tile> mymoves = new ArrayList<Tile>();
		if (moveValidation(t.getRows(), t.getCols(), t.getRows() + 1, t.getCols() + 1, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() + 1][t.getRows() + 1]);
		if (moveValidation(t.getRows(), t.getCols(), t.getRows() - 1, t.getCols() - 1, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() - 1][t.getRows() - 1]);
		if (moveValidation(t.getRows(), t.getCols(), t.getRows() - 1, t.getCols() + 1, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() + 1][t.getRows() - 1]);
		if (moveValidation(t.getRows(), t.getCols(), t.getRows() + 1, t.getCols() - 1, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() - 1][t.getRows() + 1]);
		return mymoves;
	}

	/**
	 * in this method we built a hash that have all the available moves for a player
	 * 
	 * @param isP1Turn
	 * @return hashmap with a key is a soldier in the board for the player and value
	 *         is a list that have the available moves for that soldier
	 */
	public HashMap<Tile, ArrayList<Tile>> checkAvailableSkips(boolean isP1Turn) {
		ArrayList<Tile> mySoldiers = new ArrayList<Tile>();
		HashMap<Tile, ArrayList<Tile>> availableMoves = new HashMap<Tile, ArrayList<Tile>>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isP1Turn) {
					if (myBoard[i][j].getValue() == 1 || myBoard[i][j].getValue() == 11)
						mySoldiers.add(myBoard[i][j]);
				} else {
					if (myBoard[i][j].getValue() == 2 || myBoard[i][j].getValue() == 22)
						mySoldiers.add(myBoard[i][j]);

				}
			}
		}
		ArrayList<Tile> tt = null;
		for (Tile t : mySoldiers) {
			tt = avilableSkipsForTile(t, isP1Turn);
			if (tt != null)
				availableMoves.put(t, tt);
		}
		return availableMoves;
	}

	/**
	 * in this method we find the available moves for a specific soldier
	 * 
	 * @param t
	 * @param isP1Turn
	 * @return array list that have all the permitted moves for a soldier
	 */
	public ArrayList<Tile> avilableSkipsForTile(Tile t, boolean isP1Turn) {
		ArrayList<Tile> mymoves = new ArrayList<Tile>();
		if (moveValidation(t.getRows(), t.getCols(), t.getRows() + 2, t.getCols() + 2, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() + 2][t.getRows() + 2]);
		if (moveValidation(t.getCols(), t.getRows(), t.getCols() - 2, t.getRows() - 2, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() - 2][t.getRows() - 2]);
		if (moveValidation(t.getCols(), t.getRows(), t.getCols() + 2, t.getRows() - 2, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() + 2][t.getRows() - 2]);
		if (moveValidation(t.getCols(), t.getCols(), t.getCols() - 2, t.getRows() + 2, isP1Turn, true))
			mymoves.add(myBoard[t.getCols() - 2][t.getRows() + 2]);
		return mymoves;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String wall = "|";

		builder.append(" ");
		for (int i = 0; i < myBoard.length; i++) {
			if (i < (myBoard.length - 1)) {
				builder.append("____");
			} else {
				builder.append("___ ");
			}
		}
		builder.append(newLine);
		for (int i = 0; i < myBoard.length; i++) {

			for (int j = 0; j < myBoard.length; j++) {
				builder.append(wall);
				builder.append(" ");
				builder.append(myBoard[j][i]);
				builder.append(" ");
			}
			builder.append(wall);
			builder.append(newLine);
			for (int j = 0; j < myBoard.length; j++) {
				builder.append(wall);
				builder.append("___");
			}
			builder.append(wall);
			builder.append(newLine);
		}
		return builder.toString();
	}
}