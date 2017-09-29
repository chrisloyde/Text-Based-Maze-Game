import java.util.ArrayList;

public class Player
{
	private ArrayList<Item> inventory;
	private int row;
	private int col;

	public Player(int row, int col) {
		inventory = new ArrayList<Item>();
		this.row = row;
		this.col = col;
	}
	public int getRow() { return this.row; }
	public int getCol() { return this.col; }
	public ArrayList<Item> getInventory() { return this.inventory; }

	public void setRow(int row) { this.row = row; }
	public void setCol(int col) { this.col = col; }
	public void setInventory(ArrayList<Item> inventory) { this.inventory = inventory; }
	public void addItem(Item item) { this.inventory.add(item); }
}
