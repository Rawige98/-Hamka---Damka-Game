package Model;

import java.awt.Point;

import Utils.Consts;

public class MoveValidation {

	private int xStart;
	private int xEnd;
	private int yStart;
	private int yEnd;
	private boolean finish;
	private ValidateDistance vDistance;
	private ValidateID vID;
	private SkipValidation vSkip;
		public MoveValidation(int xStart, int xEnd, int yStart, int yEnd, Board myBoard, boolean isP1, boolean finish) {
		super();
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.finish = finish;
		vDistance=new ValidateDistance(xStart, xEnd, yStart, yEnd, myBoard, isP1);
		vID=new ValidateID(xStart, xEnd, yStart, yEnd, myBoard, isP1,finish);
		vSkip=new SkipValidation(xStart, xEnd, yStart, yEnd, myBoard, isP1);
	}
	/**
	 * Determines if the specified move is valid based on the rules of checkers.
	 * 
	 * @param isP1Turn   the flag indicating if it is player 1's turn.
	 * @param startIndex the start index of the move.
	 * @param endIndex   the end index of the move.
	 * @return true if the move is legal according to the rules of checkers.
	 */
	public boolean moveValidation() {
		if (toIndex(xStart, yStart) == -1 || toIndex(xEnd, yEnd) == -1) {
			if (finish)
				return false;
			System.out.println("you cant move to white Tile");
			return false;
		}
		if (toIndex(xStart, yStart) == toIndex(xEnd, yEnd)) {
			if (finish)
				return false;
			System.out.println("you insert the same coordinates");
			return false;
		}
		if (toIndex(xStart, yStart) < 0 || toIndex(xStart, yStart) > 31 || toIndex(xEnd, yEnd) < 0
				|| toIndex(xEnd, yEnd) > 31) {
			if (finish)
				return false;
			System.out.println("illegal coordinates");
			return false;
		}
		if (! vID.validateIDs()) {
			if (finish)
				return false;
			System.out.println("ValidID");
			return false;
		}
		if (!vDistance.validateDistance()) {
			if (finish)
				return false;
			System.out.println("validDistnace");
			return false;
		}
		if (!vSkip.skipValidation()) {
			if (finish)
				return false;
			System.out.println("validSkip");
			return false;
		}
		return true;
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
		if (!MoveValidation.isValidPoint(x, y)) {
			return -1;
		}
		return x * 4 + y / 2;
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
		if (x < 0 || x > Consts.COLS -1 || y < 0 || y > Consts.COLS -1) {
			return false;
		}

		// Check that it is on a black tile
		if (x % 2 == y % 2) {
			return false;
		}

		return true;
	}
}
