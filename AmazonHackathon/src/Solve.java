import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Solve {
	
	static List<Picker> pickers = new ArrayList<Picker>();
	static Picker p = new Picker("picker-1");
	
	public static void run() {
		pickers.add(p);
		
		PickerOperations.fillOrderIds();
		
		processOrders(Parse.orders.get(0));		
		processAgain(Parse.orders.get(1));

		processOrders(Parse.orders.get(1));		
		processAgain(Parse.orders.get(2));

		processOrders(Parse.orders.get(2));		
		processAgain(Parse.orders.get(3));
		
		processOrders(Parse.orders.get(3));		
		for (Picker picker : pickers) {
			for (Long orderId : picker.completedOrders) {
				Order order = Parse.allOrders.get(orderId);
				
				System.out.printf("%s %s %s %s%n", picker.getId(), order.orderId, 
						order.itemId, Parse.bindings.get(order.orderId));
			}
		}
	}
	
	static void processOrders(Map<Long, Order> orders) {
		while (!orders.isEmpty()) {
			if (!PickerOperations.getNextOrder(p, orders)) {
				p = new Picker("picker-" + (pickers.size() + 1));
				pickers.add(p);
			}
		}
	}
	
	static void processAgain(Map<Long, Order> orders) {
		int size = pickers.size();
		for (int i = 0; i < size; i++) {
			p = pickers.get(i);
			if (p.time < 34000) {
				while (PickerOperations.getNextOrder(p, orders));
			}
		}
	}
}
