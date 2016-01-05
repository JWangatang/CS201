package gameComponents;

public class Tile {

	TileType type;
	int row, column;
	Letter L;
	
	public Tile (TileType type, int row, int column) {
		this.type = type;
		this.row = row;
		this.column = column;
		L = null;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public TileType getTileType(){
		return type;
	}
	
}