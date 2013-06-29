package level3;

public class Main {
	public static void main(String[] args) throws Exception {
		
		Parse.readInventory("inventory.dat");
		Parse.readOrders("demands.dat");
		//Parse.intersectMaps();
		Solve.run();
		Parse.in.close();

	}
}
