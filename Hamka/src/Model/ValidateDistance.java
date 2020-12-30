package Model;

public class ValidateDistance {
	private int xStart;
	private int xEnd;
	private int yStart;
	private int yEnd;
	private Board myBoard;
	private boolean isP1;
	public ValidateDistance(int xStart, int xEnd, int yStart, int yEnd, Board myBoard, boolean isP1 ) {
		super();
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.myBoard = myBoard;
		this.isP1 = isP1;
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
	public boolean validateDistance() {
		// Check that it was a diagonal move
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		if (!myBoard.getMyBoard()[yStart][xStart].isQueen()) {
			if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) > 2 || dx == 0) {
				return false;
			}
			// Check that it was in the right direction
			if ((myBoard.getMyBoard()[yStart][xStart] instanceof WhiteSoldier && dx > 0)
					|| (myBoard.getMyBoard()[yStart][xStart] instanceof BlackSoldier && dx < 0)) {
				return false;
			}
		} else {
			// Queen
			// Walls
			if (!ValidateQueenMove())
				return false;
		}
		return true;
	}
	/**
	 * in this method we check the validate of the queen move
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @return True if the move is legal
	 */
	private boolean ValidateQueenMove() {
		int dy = yEnd - yStart;
		int dx = xEnd - xStart;
		int i = xStart, j = yStart;
		int r=0;
		if (Math.abs(dx) != Math.abs(dy) && Math.abs(dx) + Math.abs(dy) != 8)
			// check if the road to the target is clear
			while ((i != xEnd && j != yEnd)&&r!=20) {
				r++;
				if (Math.abs(dx) == Math.abs(dy)) {
					if (dx > 0 && dy > 0) {
						i++;
						j++;
					}
					if (dx < 0 && dy > 0) {
						i--;
						j++;
					}
					if (dx > 0 && dy < 0) {
						j--;
						i++;

					}
					if (dx < 0 && dy < 0) {
						i--;
						j--;

					}
				} else {
					if (Math.abs(dx) + Math.abs(dy) == 8) {
						if (xEnd == 0 || xEnd == 7) {
							if (dx > 0 && dy > 0) {
								i++;
								j--;
							}
							if (dx < 0 && dy > 0) {
								i--;
								j--;
							}
							if (dx > 0 && dy < 0) {
								j++;
								i++;
							}
							if (dx < 0 && dy < 0) {
								i--;
								j++;
							}
						} else {
							if (dx > 0 && dy > 0) {
								i--;
								j++;
							}
							if (dx < 0 && dy > 0) {
								j++;
								i++;
							}
							if (dx > 0 && dy < 0) {

								i--;
								j--;
							}
							if (dx < 0 && dy < 0) {

								i++;
								j--;
							}

						}
					}
				}
				if (i == -1)
					i = 7;
				if (i == 8)
					i = 0;
				if (j == -1)
					j = 7;
				if (j == 8)
					j = 0;
				dy = yEnd - j;
				dx = xEnd - i;
				if ((!isP1 && (myBoard.getMyBoard()[j][i] instanceof WhiteSoldier))
						|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier))
					return false;
			}
		return true;
	}

}
