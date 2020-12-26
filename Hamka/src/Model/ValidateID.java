package Model;

public class ValidateID {
	private int xStart;
	private int xEnd;
	private int yStart;
	private int yEnd;
	private Board myBoard;
	private boolean isP1;
	private boolean finish;


	public ValidateID(int xStart, int xEnd, int yStart, int yEnd, Board myBoard, boolean isP1) {
		super();
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.myBoard=myBoard;
		this.isP1 = isP1;
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
	public boolean validateIDs() {
		// check if the end is empty
		if (myBoard.getMyBoard()[yEnd][xEnd].getValue() != 0) {
			if (finish)
				return false;
			System.out.println("Tile isnt empty");
			return false;
		}
		// check if the the player play with his soldiers
		if ((!isP1 && myBoard.getMyBoard()[yStart][xStart].getValue() != 2 && myBoard.getMyBoard()[yStart][xStart].getValue() != 22)
				|| (isP1  && myBoard.getMyBoard()[yStart][xStart].getValue() != 1 && myBoard.getMyBoard()[yStart][xStart].getValue() != 11)) {
			if (finish)
				return false;
			System.out.println("thats not your soldier");
			return false;
		}
		return true;
	}
}
