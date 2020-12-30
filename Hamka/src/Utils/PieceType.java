package Utils;

public enum PieceType {
	WHITE(1), GREY(-1);

	public final int moveDir;
	
	PieceType(int moveDir){
		this.moveDir = moveDir;
	}
	
}
