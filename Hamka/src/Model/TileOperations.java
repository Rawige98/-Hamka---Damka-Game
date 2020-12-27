package Model;

public interface TileOperations {
	/**
	 * in this method we upgrade the soldier to queen
	 * 
	 * @param TileView T
	 * @return true if the soldier upgraded successfully
	 */
	public abstract boolean upgradeToQueen() ;
	public abstract boolean isQueen();
	public abstract Tile makeCopy();
}
