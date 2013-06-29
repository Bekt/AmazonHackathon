import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import com.amazon.aft.hackathon.TravelTimeCalculator;

public class Parse {
	static Kattio in;
	static Map<Long, String> bindings = new HashMap<Long, String>();
	static Map<String, Map<String, Integer>> bins = new HashMap<String, Map<String, Integer>>();
	
	static Map<Long, Order> ordersA = new HashMap<Long, Order>();
	static Map<Long, Order> ordersB = new HashMap<Long, Order>();
	static Map<Long, Order> ordersC = new HashMap<Long, Order>();
	static Map<Long, Order> ordersD = new HashMap<Long, Order>();

	static void readBinding() throws Exception {
		in = new Kattio(new FileInputStream("bindings.dat"));
		
		while(in.hasMoreTokens()) {
			bindings.put(in.getLong(), in.getWord());
		}
	}
	
	static void readOrders() throws Exception {
		in = new Kattio(new FileInputStream("demands.dat"));
		
		while(in.hasMoreTokens()) {
			long orderId = in.getLong(),
				 dueTime = in.getLong(),
				 dropTime = in.getLong();
			String itemId = in.getWord();
			String binId = bindings.get(orderId);

			Order order = new Order(orderId, dueTime, dropTime, itemId, binId);

			String modLocation = TravelTimeCalculator.getModForLocationID(binId);
			Map<Long, Order> modOrder;
			
			switch (modLocation) {
				case "A":
					modOrder = ordersA;
					break;
				case "B":
					modOrder = ordersB;
					break;
				case "C":
					modOrder = ordersC;
					break;
				default:
					modOrder = ordersD;
					break;
			}
			
			modOrder.put(orderId, order);
		}
		
	}
	
	static void readInventory() throws Exception {
		in = new Kattio(new FileInputStream("inventory.dat"));
		
		while(in.hasMoreTokens()) {
			String binId = in.getWord(),
				   itemId = in.getWord();
			int quantity = in.getInt();
			
			Map<String, Integer> items = bins.get(binId);
			if(items == null) {
				items = new HashMap<String, Integer>();
				bins.put(binId, items);
			}
			items.put(itemId, quantity);
		}
	}

}
