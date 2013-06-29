
public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Parse.readInventory(args[2]);
		Parse.readBinding(args[3]);
		Parse.readOrders(args[1]);

//		Parse.readInventory("inventory.dat");
//		Parse.readBinding("bindings.dat");
//		Parse.readOrders("demands.dat");
		
		Solve.run();
		
		Parse.in.close();
	}	
}
