
public class Main {
	
	public static void main(String[] args) throws Exception {
		Parse.readInventory(args[2]);
		Parse.readBinding(args[3]);
		Parse.readOrders(args[1]);
		
		Solve.run();
		
		Parse.in.close();
	}	
}
