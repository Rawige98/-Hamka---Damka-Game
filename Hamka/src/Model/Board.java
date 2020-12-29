package Model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Utils.Consts;
import Utils.MoveType;

public class Board implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tile[][] myBoard;
	public List<Tile> r=new ArrayList<Tile>();
	public Board() {
		setMyBoard(new Tile[Consts.ROWS][Consts.COLS]);
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
		TileFactory factory = new TileFactory();

		for (int i = 0; i < Consts.ROWS; i++) {
			for (int j = 0; j < Consts.COLS; j++) {
				myBoard[j][i] = factory.makeTile(i, j);
			}
		}
	}

	/**
	 * Checks if a point corresponds to a black tile on the checker board.
	 * 
	 * @param testPoint the point to check.
	 * @return true if and only if the point is on the board, specifically on a
	 *         black tile.
	 */
//	public static boolean isValidPoint(int x, int y) {
//		// Check that it is on the board
//		if (x < 0 || x > Consts.COLS - 1 || y < 0 || y > Consts.COLS - 1) {
//			return false;
//		}
//
//		// Check that it is on a black tile
//		if (x % 2 == y % 2) {
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * in this method we move the soldier after check the validation
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @return true if the soldier move successfully
	 */
	public MoveType move(int xStart, int yStart, int xEnd, int yEnd, boolean isP1Turn, Player p) {
		MoveValidation validation = new MoveValidation(xStart, xEnd, yStart, yEnd, this, isP1Turn, false);
		if (validation.moveValidation()) {
			int dx = xEnd - xStart;
			int dy = yEnd - yStart;
			if (!myBoard[yStart][xStart].isQueen()) {
				// if its not a skip
				if (Math.abs(dx) != 2) {
					myBoard[yEnd][xEnd] = myBoard[yStart][xStart].makeCopy();
					myBoard[yStart][xStart] = new BlackTile(yStart, xStart);
					return MoveType.NORMAL;
				} else {
					int xmid = (xStart + xEnd) / 2;
					int ymid = (yStart + yEnd) / 2;
					myBoard[ymid][xmid] = new BlackTile(ymid, xmid);
					myBoard[yEnd][xEnd] = myBoard[yStart][xStart].makeCopy();
					myBoard[yStart][xStart] = new BlackTile(yStart, xStart);
					p.setScore(100);
					return MoveType.KILL;
				}
			}
			// Queen
			else {
				int i = xStart, j = yStart;
				boolean isSkip = false;
				int col = -1, row = -1;
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
					if (!isP1Turn && (myBoard[j][i] instanceof WhiteSoldier)
							|| isP1Turn && (myBoard[j][i] instanceof BlackSoldier)) {
						col = j;
						row = i;
						isSkip = true;
					}
				}
				if (isSkip) {
					myBoard[col][row] = new BlackTile(col, row);
					myBoard[yEnd][xEnd] = myBoard[yStart][xStart].makeCopy();
					myBoard[yStart][xStart] = new BlackTile(yStart, xStart);
					p.setScore(100);
					return MoveType.KILL;
				} else {
					myBoard[yEnd][xEnd] = myBoard[yStart][xStart].makeCopy();
					myBoard[yStart][xStart] = new BlackTile(yStart, xStart);
					return MoveType.NORMAL;
				}
			}
		}
		return MoveType.NONE;
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
		return myBoard[y][x].upgradeToQueen();
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
					if (myBoard[i][j] instanceof WhiteSoldier)
						mySoldiers.add(myBoard[i][j]);
				} else {
					if (myBoard[i][j] instanceof BlackSoldier)
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

		for (int i = 0; i < myBoard.length; i++) {
			for (int j = 0; j < myBoard.length; j++) {
				MoveValidation v = new MoveValidation(t.getRows(), t.getCols(), i, j, this, isP1Turn, true);
				if (v.moveValidation()) {
					mymoves.add(myBoard[j][i]);
				}
			}
		}
		return mymoves;
	}

	// ******************* Colord tiles methods
	// *******************************************************

	/**
	 * this methods randomly choose tile and if her color is black, then it will be
	 * changed to yellow
	 */
	public void showYellowTiles() {
		int x, y, yellowCount = 0;
		Random random = new Random();
		boolean done = false;
		while (!done) {
			if (yellowCount == Consts.MAX_YELLOW_TILE)
				done = true;
			else {
				x = random.nextInt(Consts.ROWS);
				y = random.nextInt(Consts.COLS);
				Tile randomTile = myBoard[x][y];
				if (!randomTile.getColor().equals(Color.white) && randomTile.getColor().equals(Color.black)
						&& !randomTile.getColor().equals(Color.yellow)) {
					randomTile.setColor(Color.yellow);
					yellowCount++;
				}
			}
		}
	}

//	public void showRedGreenTile(boolean isP1Turn , Color color) {
//		HashMap<Tile, ArrayList<Tile>> suggested = checkAvailableMoves(isP1Turn);
//		Tile randomTile = getRandomTile((Tile[])suggested.values().toArray());
//		randomTile.setColor(color);
//	}
//
//	public void showBlueTile(boolean isP1Turn) {
//		HashMap<Tile, ArrayList<Tile>> suggested = checkAvailableMoves(isP1Turn);
//		Tile randomTile = getRandomTile((Tile[])suggested.values().toArray());
//		randomTile.setColor(Color.blue);
//	}

	public void colorRandomTile(ArrayList<Tile> tiles, Color color) {
		Random random = new Random();
		int index = random.nextInt(tiles.size());
		tiles.get(index).setColor(color);
	}

	public void colorAllTiles(ArrayList<Tile> tiles, Color color) {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).setColor(color);
		}
	}

//	private Tile getRandomTile(Tile[] tiles) {
//		Random random = new Random();
//		int index = random.nextInt(tiles.length);
//		return tiles[index];
//	}

	public void turnOffTileColor(Tile tile) {
		tile.setColor(Color.black);
	}

	public void turnOffAllTilesColor() {
		Tile tile;
		for (int i = 0; i < myBoard.length; i++) {
			for (int j = 0; j < myBoard[i].length; j++) {
				tile = myBoard[i][j];
				if (!tile.getColor().equals(Color.white))
					tile.setColor(Color.black);
			}
		}
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
					if (myBoard[i][j] instanceof WhiteSoldier)
						mySoldiers.add(myBoard[i][j]);
				} else {
					if (myBoard[i][j] instanceof BlackSoldier)
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
		for (int i = 0; i < myBoard.length; i++) {
			for (int j = 0; j < myBoard.length; j++) {
				MoveValidation v = new MoveValidation(t.getRows(), t.getCols(), i, j, this, isP1Turn, true);
				if (v.moveValidation()) {
					if (isSkip(t.getRows(), t.getCols(), i, j, isP1Turn))
						mymoves.add(myBoard[j][i]);
				}
			}
		}
		return mymoves;
	}

	public Tile getTile(int x, int y) {
		return myBoard[x][y];
	}

	public boolean isSkip(int xStart, int yStart, int xEnd, int yEnd, boolean isP1Turn) {
		int dx = xEnd - xStart;
		int dy = yEnd - yStart;
		if (!myBoard[yStart][xStart].isQueen()) {
			// if its not a skip
			if (Math.abs(dx) == 2) {
				return false;
			} else {
				return true;
			}
		}
		// Queen
		else {
			int i = xStart, j = yStart;
			boolean isSkip = false; // check if the road to the target is clear
			int c = 0;
			while ((i != xEnd && j != yEnd)) {
				c++;
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
				if (!isP1Turn && (myBoard[j][i] instanceof WhiteSoldier)
						|| isP1Turn && (myBoard[j][i] instanceof BlackSoldier)) {
					isSkip = true;
				}
			}
			return isSkip && c == 1;
		}
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