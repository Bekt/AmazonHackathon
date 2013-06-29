import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazon.aft.hackathon.TravelTimeCalculator;

public class Parse {
	static Kattio in;
	static Map<Long, String> bindings = new HashMap<Long, String>();
	static Map<String, Map<String, Integer>> bins = new HashMap<String, Map<String, Integer>>();
	static Map<Long, Order> allOrders = new HashMap<Long, Order>();
	static List<Map<Long, Order>> orders = new ArrayList<Map<Long, Order>>();
	
	static void readBinding(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
		while(in.hasMoreTokens()) {
			bindings.put(in.getLong(), in.getWord());
		}
	}
	
	static void readOrders(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
		orders.add(new HashMap<Long, Order>());
		orders.add(new HashMap<Long, Order>());
		orders.add(new HashMap<Long, Order>());
		orders.add(new HashMap<Long, Order>());
		
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
					modOrder = orders.get(0);
					break;
				case "B":
					modOrder = orders.get(1);
					break;
				case "C":
					modOrder = orders.get(2);
					break;
				default:
					modOrder = orders.get(3);
					break;
			}
			
			modOrder.put(orderId, order);
			allOrders.put(orderId, order);
		}
		
		Collections.sort(orders, new Comp());
	}
	
	static void readInventory(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
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
	
	static class Comp implements Comparator<Map<Long, Order>> {

		@Override
		public int compare(Map<Long, Order> mapOne, Map<Long, Order> mapTwo) {
			Integer sizeOne = mapOne.size();
			Integer sizeTwo = mapTwo.size();
			
			return sizeOne.compareTo(sizeTwo);
		}
	}

}
