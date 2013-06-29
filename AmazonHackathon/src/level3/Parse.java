package level3;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parse {
	static Kattio in;
	
	static Map<String, List<Item>> binToItems = new HashMap<String, List<Item>>();
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
			
			List<Item> items = binToItems.get(binId);			
			if(items == null) {
				items = new ArrayList<Item>();
				binToItems.put(binId, items);
			}
			items.add(new Item(itemId, quantity));
		}
	}
	
	static void intersectMaps() {
		Set<String> bins = binToItems.keySet();
		
		for (String binId : bins) {
			List<Item> items = binToItems.get(binId);
			for (Item item : items) {
				if(item.quantity > 0) {
					List<Order> orderItems = itemToOrders.get(item.itemId);
					List<Order> orderBins = binToOrders.get(binId);

					if (orderBins == null) {
						orderBins = new ArrayList<>();
						binToOrders.put(binId, orderBins);
					}

					for (Order orderItem : orderItems) {
						orderBins.add(orderItem);
					}
					
					item.quantity--;
				}
			}
		}
	}
	
}
