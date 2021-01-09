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
	private int direction;
	private Board myBoard;
	private boolean isP1;

	public MoveValidation(int xStart, int xEnd, int yStart, int yEnd, Board myBoard, boolean isP1, boolean finish) {
		super();
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.finish = finish;
		this.myBoard = myBoard;
		this.isP1 = isP1;
		vID = new ValidateID(xStart, xEnd, yStart, yEnd, myBoard, isP1, finish);
		vSkip = new SkipValidation(xStart, xEnd, yStart, yEnd, myBoard, isP1);
		vDistance = new ValidateDistance(xStart, xEnd, yStart, yEnd, myBoard, isP1);
		direction = Direction();
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
		if (!vID.validateIDs()) {
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
		if (x < 0 || x > Consts.COLS - 1 || y < 0 || y > Consts.COLS - 1) {
			return false;
		}

		// Check that it is on a black tile
		if (x % 2 == y % 2) {
			return false;
		}

		return true;
	}

	public int getDirectionNum() {
		return direction;
	}

	public int Direction() {
		direction = 0;
		int d1 = 0, d2 = 0, d3 = 0;
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		int i = xStart, j = yStart;
		int c = 0;
		// check if the road to the target is clear
		int r = 0;
		if ((Math.abs(dx) == Math.abs(dy)) && (Math.abs(dx) + Math.abs(dy) == 8)
				&& myBoard.getMyBoard()[yStart][xStart].isQueen()) {
			while ((i != xEnd && j != yEnd) && r != 20) {
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
						if (i != 0 && i != 7) {
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
						|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier))) {
					c++;
				}
			}
			if (c == 1) {
				d1 = 1;
			}
			dx = xEnd - xStart;
			dy = yEnd - yStart;
			i = xStart;
			j = yStart;
			c = 0;
			// check if the road to the target is clear
			r = 0;
			while ((i != xEnd && j != yEnd) && r != 20) {
				r++;
				if (Math.abs(dx) + Math.abs(dy) == 8) {
					if (i != 0 && i != 7) {
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
				} else if (Math.abs(dx) == Math.abs(dy)) {
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
						|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier))) {
					c++;
				}
			}
			if (c == 1)
				d2 = 2;
			dx = xEnd - xStart;
			dy = yEnd - yStart;
			i = xStart;
			j = yStart;
			c = 0;
			// check if the road to the target is clear
			r = 0;
			while ((i != xEnd && j != yEnd) && r != 20) {
				r++;
				if (Math.abs(dx) + Math.abs(dy) == 8) {
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
				} else if (Math.abs(dx) == Math.abs(dy)) {
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
						|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier))) {
					c++;
				}
			}
			if (c == 1)
				d3 = 3;
			// System.out.println("d1 :"+d1+" d2 :"+d2+" d3 :"+d3+" rows"+xStart);
			if (d1 != 0) {
				direction = d1;
				vDistance.setDirectionNum(direction);
				vSkip.setDirectionnNum(direction);
				if (moveValidation() && thisIsSkip()) {
					return direction;
				}
				direction = 0;
			}
			if (d2 != 0) {
				direction = d2;
				vDistance.setDirectionNum(direction);
				vSkip.setDirectionnNum(direction);
				if (moveValidation() && thisIsSkip()) {
					return direction;
				}
				direction = 0;
			}
			if (d3 != 0) {
				direction = d3;
				vDistance.setDirectionNum(direction);
				vSkip.setDirectionnNum(direction);
				if (moveValidation() && thisIsSkip()) {
					return direction;
				}
			}
				if (d1 != 0) {
					direction = d1;
					vDistance.setDirectionNum(direction);
					vSkip.setDirectionnNum(direction);
					if (moveValidation()) {
						return direction;
					}
					direction = 0;
				}
				if (d2 != 0) {
					direction = d2;
					vDistance.setDirectionNum(direction);
					vSkip.setDirectionnNum(direction);
					if (moveValidation()) {
						return direction;
					}
					direction = 0;
				}
				if (d3 != 0) {
					direction = d3;
					vDistance.setDirectionNum(direction);
					vSkip.setDirectionnNum(direction);
					if (moveValidation()) {
						return direction;
					}
				direction = 0;
				vDistance.setDirectionNum(direction);
				vSkip.setDirectionnNum(direction);
			}
		}
		return direction;
	}
	private boolean thisIsSkip() {
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		int i = xStart, j = yStart;
		boolean isSkip = false; // check if the road to the target is clear
		int c = 0, r = 0;
		int d=direction;
		boolean flag=d==3;
		while ((i != xEnd && j != yEnd) && r != 20) {
			r++;
			if (Math.abs(dx) == Math.abs(dy) && d != 2 && d != 3) {
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
					if (i != 0 && i != 7 && d != 3&&flag!=true) {
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
			d=-1;
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
			if (!isP1 && (myBoard.getMyBoard()[j][i] instanceof WhiteSoldier)
					|| isP1 && (myBoard.getMyBoard()[j][i] instanceof BlackSoldier)) {
				c++;
				isSkip = true;
			}
		}
		return isSkip && c == 1;
	}

	public int getDirectionnNum() {
		return direction;
	}

	public void setDirectionnNum(int directionnNum) {
		this.direction = directionnNum;
	}
}
