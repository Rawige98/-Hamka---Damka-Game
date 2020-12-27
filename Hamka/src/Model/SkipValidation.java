package Model;

public class SkipValidation {
	private int xStart;
	private int xEnd;
	private int yStart;
	private int yEnd;
	private Board myBoard;
	private boolean isP1;
	
	

	public SkipValidation(int xStart, int xEnd, int yStart, int yEnd, Board myBoard, boolean isP1) {
		super();
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.myBoard = myBoard;
		this.isP1 = isP1;
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
	public boolean skipValidation() {
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		if (!myBoard.getMyBoard()[yStart][xStart].isQueen()) {
			// if its not a skip
			if (Math.abs(dx) != 2) {
				return true;
			}
			int xmid = (xStart + xEnd) / 2;
			int ymid = (yStart + yEnd) / 2;
			if ((myBoard.getMyBoard()[yStart][xStart] instanceof WhiteSoldier &&!(myBoard.getMyBoard()[ymid][xmid] instanceof BlackSoldier))
					|| (myBoard.getMyBoard()[yStart][xStart] instanceof BlackSoldier && !(myBoard.getMyBoard()[ymid][xmid] instanceof WhiteSoldier)))
				return false;
			if (!MoveValidation.isValidPoint(xEnd, yEnd))
				return false;
			if (myBoard.getMyBoard()[yEnd][xEnd].getValue() != 0)
				return false;
		} else {
			// Walls
			// Queen Skip Validate
			int i = xStart, j = yStart;
			int c = 0;
			// check if the road to the target is clear
			while ((i != xEnd && j != yEnd)) {
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
				if ((!isP1 && (myBoard.getMyBoard()[j][i] instanceof WhiteSoldier)
						|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier)))
					c++;
			}
			if (c != 1)
				return false;
		}
		return true;
	}

}
