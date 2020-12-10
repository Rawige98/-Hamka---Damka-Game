package Model;

public class ColorTilesAdapter implements ShowColordTilesHandler{
	
	private static ColorTilesAdapter instance;
	
	private ColorTilesAdapter() {}
	public static ColorTilesAdapter getInstance() {
		if(instance == null) {
			instance = new ColorTilesAdapter();
		}
		return instance;
	}
	
	
	@Override
	public void showColorTile(ColorTilesHandler handler) {
		// TODO Auto-generated method stub
		
	}
	
	
}
