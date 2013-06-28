
public class Main {
	Inventory inv = new Inventory();
	
	public static void main(String[] args) throws Exception {
		Parse.readInventory();
		Parse.readBinding();
		Parse.readOrders();

		Parse.in.close();
	}
	
}
