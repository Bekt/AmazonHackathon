package level3;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parse {
	static Kattio in;
	
	static Map<String, HashMap<String, Integer>> binToItems = new HashMap<String, HashMap<String, Integer>>();
	static Map<String, List<Order>> itemToOrders = new HashMap<String, List<Order>>();
	
	static Map<String, List<Order>> binToOrders = new HashMap<String, List<Order>>();
	
	static void readOrders(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
		while(in.hasMoreTokens()) {
			long orderId = in.getLong(),
				 dueTime = in.getLong(),
				 dropTime = in.getLong();
			String itemId = in.getWord();

			List<Order> orders = itemToOrders.get(itemId);
			if(orders == null) {
				orders = new ArrayList<Order>();
				itemToOrders.put(itemId, orders);
			}
			orders.add(new Order(orderId, dueTime, dropTime, itemId));
		}
	}
	
	static void readInventory(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
		while(in.hasMoreTokens()) {
			String binId = in.getWord(),
				   itemId = in.getWord();
			int quantity = in.getInt();
					
			HashMap<String, Integer> item;
			if(binToItems.get(binId) == null) {
				item = new HashMap<String, Integer>();
				item.put(itemId, quantity);
				binToItems.put(binId, item);
			}
		}
	}
	
	static void intersectMaps() {
		Set<String> bins = binToItems.keySet();
		
		for (String binId : bins) {
			HashMap<String, Integer> items = (HashMap<String, Integer>) binToItems.get(binId);
			Iterator<String> names = items.keySet().iterator();
			while(names.hasNext()) {
				String itemName = names.next();
				//items.put(itemName, items.get(itemName) + 1);
				if(items.get(itemName) > 0) {
					List<Order> orderItems = itemToOrders.get(itemName);
					List<Order> orderBins = binToOrders.get(binId);

					if (orderBins == null) {
						orderBins = new ArrayList<>();
						binToOrders.put(binId, orderBins);
					}

					for (Order orderItem : orderItems) {
						orderBins.add(orderItem);
					}
					//item.quantity--;
				}
			}
		}
	}
	
}
