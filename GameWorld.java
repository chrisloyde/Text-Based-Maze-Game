import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class GameWorld
{
	public static void main(String[] args) {
		String line, description;
		String[] lineParse;
		ArrayList<Item> items;
		Room[][] board;
		Player player;
		int row, col, maxRow, maxCol, numOfRooms;
		try {
			InputStream fis = new FileInputStream("input.txt");
			InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader reader = new BufferedReader(isr);
			row = 0;
			col = 0;
			items = new ArrayList<Item>();
			// parse first line
			line = reader.readLine();
			lineParse = line.split(" ");

			maxRow = Integer.parseInt(lineParse[0]);
			maxCol = Integer.parseInt(lineParse[1]);
			numOfRooms = maxCol*maxRow;
			board = new Room[maxRow][maxCol];
			player = new Player(Integer.parseInt(lineParse[2]), Integer.parseInt(lineParse[3]));

			while((line = reader.readLine()) != null) {
				lineParse = line.split(" ");

				if (lineParse[0].equalsIgnoreCase("room:")) {
					// parse room line
					description = "";
					if (col > (maxCol-1)) {
						col = 0;
						row++;
					}
					for (int k = 7; k < lineParse.length; k++) {
						description += lineParse[k] + " ";
					}
					board[row][col] = new Room(row, col, Integer.parseInt(lineParse[1]), Integer.parseInt(lineParse[2]), Integer.parseInt(lineParse[3]),
							Integer.parseInt(lineParse[4]), description.trim());

					for (int k = 0; k < items.size(); k++) {
						if (board[row][col].getItemProvided() == null) {
							if (items.get(k).getName().equalsIgnoreCase(lineParse[5])) {
								board[row][col].setItemProvided(items.get(k));
							} else {
								board[row][col].setItemProvided(null);
							}
						}
						if (board[row][col].getItemRequired() == null) {
							if (items.get(k).getUsage().equalsIgnoreCase(lineParse[6])) {
								board[row][col].setItemRequired(items.get(k));
							} else {
								board[row][col].setItemRequired(null);
							}
						}
					}

					numOfRooms--;
					col++;
				} else if (lineParse[0].equalsIgnoreCase("item:")) {
					//parse item line
					description = "";
					for (int k = 3; k < lineParse.length; k++) {
						description += lineParse[k] + " ";
					}
					items.add(new Item(lineParse[1], lineParse[2], description.trim()));
				} else {
					continue;
				}
			}
			if (numOfRooms != 0) {
				System.out.println("Error: Incorrect number of rooms given in file.");
			}
			System.out.println("File processing finished");
			playGame(player, board);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playGame(Player player, Room[][] board) {
		String input;
		Scanner scanner = new Scanner(System.in);
		Room currentRoom;
		int x = -1;

		System.out.println("You wake in a mysterious dungeon.");

		while(scanner.hasNext()) {
			input = scanner.nextLine();
			input = input.toLowerCase();
			currentRoom = board[player.getRow()][player.getCol()];

			if (input.equalsIgnoreCase("exit")) {
				return;
			}

			if (input.equalsIgnoreCase("look")) {
				if (currentRoom.getItemProvided() != null) {
					System.out.println(currentRoom.getDescription() + "\nYou see " + currentRoom.getItemProvided().getName());
				} else {
					System.out.println(currentRoom.getDescription() + "\nThere are no items in this room");
				}
				System.out.print(getPathWays(currentRoom));
			}

			if (input.equalsIgnoreCase("move north")) {
				if (currentRoom.getNorth() == 0) {
					player.setRow(player.getRow()-1);
					System.out.println("You moved North");
				}
			}

			if (input.equalsIgnoreCase("move east")) {
				if (currentRoom.getEast() == 0) {
					player.setCol(player.getCol()+1);
					System.out.println("You moved East");
				}
			}

			if (input.equalsIgnoreCase("move south")) {
				if (currentRoom.getSouth() == 0) {
					player.setRow(player.getRow()+1);
					System.out.println("You moved South");
				}
			}

			if (input.equalsIgnoreCase("move west")) {
				if (currentRoom.getWest() == 0) {
					player.setCol(player.getCol()-1);
					System.out.println("You moved West");
				}
			}

			if (input.equalsIgnoreCase("grab")) {
				if (currentRoom.getItemProvided() != null) {
					player.addItem(currentRoom.getItemProvided());
					System.out.println("You've added "+currentRoom.getItemProvided().getName()+" to your inventory.");
					currentRoom.setItemProvided(null);
				} else {
					System.out.println("There is no item in this room.");
				}
			}

			if (input.startsWith("use ") && input.split(" ").length == 2) {
				if ((x =isInInventory(player.getInventory(), input.split(" ")[1])) != -1) {
					if (currentRoom.getItemRequired() != null) {
						if (currentRoom.getItemRequired().getName().equalsIgnoreCase(player.getInventory().get(x).getName())) {
							if (currentRoom.getNorth() == 2) {
								System.out.println(player.getInventory().get(x).getDescription());
								player.getInventory().remove(x);
								currentRoom.setNorth(0);
							} else if (currentRoom.getEast() == 2) {
								System.out.println(player.getInventory().get(x).getDescription());
								player.getInventory().remove(x);
								currentRoom.setEast(0);
							} else if (currentRoom.getSouth() == 2) {
								System.out.println(player.getInventory().get(x).getDescription());
								player.getInventory().remove(x);
								currentRoom.setSouth(0);
							} else if (currentRoom.getWest() == 2){
								System.out.println(player.getInventory().get(x).getDescription());
								player.getInventory().remove(x);
								currentRoom.setWest(0);
							} else {
								System.out.println("There are no doors in this room.");
							}
						} else {
							System.out.println("You are not using the correct item.");
						}
					} else {
						System.out.println("There is nothing in this room that requires an item.");
					}
				} else {
					System.out.println("You do not have that item in your inventory.");
				}
			}

			if (input.equalsIgnoreCase("inventory")) {
				System.out.println(getInventory(player.getInventory()));
			}

			if (board[player.getRow()][player.getCol()].getDescription().equalsIgnoreCase("finish")) {
				System.out.println("You escaped!");
				break;
			}
		}
	}

	public static int isInInventory(ArrayList<Item> inventory, String itemName) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().equalsIgnoreCase(itemName)) {
				return i;
			}
		}

		return -1;
	}

	public static String getInventory(ArrayList<Item> inventory) {
		String ret = "";
		for (int i = 0; i < inventory.size(); i++) {
			ret += inventory.get(i).getName() + " ";
		}
		return ret.trim();
	}

	public static String getPathWays(Room room) {
		String ret = "";
		if (room.getNorth() == 0) {
			ret += "You see a pathway to the North.\n";
		} else if (room.getNorth() == 2) {
			ret += "You see a door to the North.\n";
		}

		if (room.getEast() == 0) {
			ret += "You see a pathway to the East.\n";
		} else if (room.getEast() == 2) {
			ret += "You see a door to the East.\n";
		}

		if (room.getSouth() == 0) {
			ret += "You see a pathway to the South.\n";
		} else if (room.getSouth() == 2) {
			ret += "You see a door to the South.\n";
		}

		if (room.getWest() == 0) {
			ret += "You see a pathway to the West.\n";
		} else if (room.getWest() == 2) {
			ret += "You see a door to the West\n";
		}

		return ret;
	}
}
