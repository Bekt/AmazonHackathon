
public class Main {
	public static void main(String[] args) throws Exception {
		Parse.readInventory("inventory.dat");
		Parse.readBinding("bindings.dat");
		Parse.readOrders("demands.dat");

		PickerOperations ops = new PickerOperations();
		Picker p = new Picker("picker-1");
		int count = 1;
		
		while (Parse.ordersA.size() > 0) {
			if (!ops.getNextOrder(p, Parse.ordersA)) {
				System.out.println(p.toString());
				count++;
				p = new Picker("picker-" + count);						
			}
		}
		System.out.println(p.toString());
		
		count++;
		p = new Picker("picker-"+ count);
		while (!Parse.ordersB.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersB)) {
				System.out.println(p.toString());
				count++;
				p = new Picker("picker-" + count);
			}
		}
		System.out.println(p.toString());
		
		count++;
		p = new Picker("picker-"+ count);
		while (!Parse.ordersC.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersC)) {
				System.out.println(p.toString());
				count++;
				p = new Picker("picker-" + count);
						
			}
		}
		System.out.println(p.toString());
		
		count++;
		p = new Picker("picker-"+ count);
		while (!Parse.ordersD.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersD)) {
				System.out.println(p.toString());
				count++;
				p = new Picker("picker-" + count);
						
			}
		}
		System.out.println(p.toString());
		
		System.out.println("used " + (count-1) + " pickers");
		Parse.in.close();
	}
	
}
