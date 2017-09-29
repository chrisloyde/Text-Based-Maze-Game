public class Room
{
	private int row;
	private int col;
	private int N;
	private int E;
	private int S;
	private int W;
	private Item itemProvided;
	private Item itemRequired;
	private String description;

	public Room(int row, int col, int N, int E, int S, int W, String description) {
		this.row = row;
		this.col = col;
		this.N = N;
		this.E = E;
		this.S = S;
		this.W = W;
		this.description = description;
		this.itemProvided = null;
		this.itemRequired = null;
	}

	public int getRow() { return this.row; }
	public int getCol() { return this.col; }
	public int getNorth() { return this.N; }
	public int getEast() { return this.E; }
	public int getSouth() { return this.S; }
	public int getWest() { return this.W; }
	public Item getItemProvided() { return this.itemProvided; }
	public Item getItemRequired() { return this.itemRequired; }
	public String getDescription() { return this.description; }

	public void setNorth(int n) { this.N = n; }
	public void setEast(int e) { this.E = e; }
	public void setSouth(int s) { this.S = s; }
	public void setWest(int w) { this.W = w; }
	public void setItemProvided(Item item) { this.itemProvided = item; }
	public void setItemRequired(Item item) { this.itemRequired = item; }
	public void setDescription(String desc) { this.description = desc; }
}
