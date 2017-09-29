public class Item
{
	private String name = "null";
	private String usage = "null";
	private String description = "null";

	public Item(String name, String usage, String description) {
		this.name = name;
		this.usage = usage;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public String getUsage() {
		return this.usage;
	}

	public String getDescription() {
		return this.description;
	}
}
