import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void main(String[] args) throws Exception {
		Parse.readInventory("inventory.dat");
		Parse.readBinding("bindings.dat");
		Parse.readOrders("demands.dat");

		List<Picker> pickerList = new ArrayList<Picker>();
		PickerOperations ops = new PickerOperations();
		Picker p = new Picker("picker-1");
		pickerList.add(p);
		
		System.out.println("Order A size: " + Parse.ordersA.size());
		System.out.println("Order B size: " + Parse.ordersB.size());
		System.out.println("Order C size: " + Parse.ordersC.size());
		System.out.println("Order D size: " + Parse.ordersD.size());
		
		while (Parse.ordersD.size() > 0) {
			if (!ops.getNextOrder(p, Parse.ordersD)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("New pickerList size: " + pickerList.size());
				System.out.println("OrdersD size: " + Parse.ordersD.size());
				pickerList.add(p);
			}
		}
		
		int totalCount = 0;
		for(int i=0;i<pickerList.size();i++){
			System.out.println(pickerList.get(i).toString());
			totalCount += pickerList.get(i).completedOrders.size();
		}
		System.out.println("used " + pickerList.size() + " pickers");
		System.out.println("Total: " + totalCount);
		
		System.exit(0);
		
		while (Parse.ordersA.size() > 0) {
			if (!ops.getNextOrder(p, Parse.ordersA)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				pickerList.add(p);
			}
		}
		
		System.out.println("Completed A");
		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			while(ops.getNextOrder(p, Parse.ordersB)) {
				
			}
		}
		while (!Parse.ordersB.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersB)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				pickerList.add(p);
			}
		}
		System.out.println("Completed B");

		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			while(ops.getNextOrder(p, Parse.ordersC)) {
			}
		}
		while (!Parse.ordersC.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersC)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				pickerList.add(p);
			}
		}
		System.out.println("Completed C");
		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			while(ops.getNextOrder(p, Parse.ordersD)) {
			}
		}
		while (!Parse.ordersD.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersD)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				pickerList.add(p);
			}
		}
		
		System.out.println("Completed D");
		//int totalCount = 0;
		for(int i=0;i<pickerList.size();i++){
			System.out.println(pickerList.get(i).toString());
			totalCount += pickerList.get(i).completedOrders.size();
		}
		System.out.println("used " + pickerList.size() + " pickers");
		System.out.println("Total: " + totalCount);
		Parse.in.close();
	}	
}
