package level3;

public class Main {
	public static void main(String[] args) throws Exception {
		
		Parse.readInventory(args[2]);
		Parse.readOrders(args[1]);
		//Parse.intersectMaps();
		Solve.run();
		Parse.in.close();

	}
}
