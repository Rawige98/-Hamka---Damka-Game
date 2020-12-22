package Utils;

public enum PieceType {
	RED(1), BLUE(-1);

	public final int moveDir;
	
	PieceType(int moveDir){
		this.moveDir = moveDir;
	}
	
}
