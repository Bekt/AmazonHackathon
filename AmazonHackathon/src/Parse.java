import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Parse {
	static Kattio in;
	static Map<Long, String> bindings = new HashMap<Long, String>();
	static Map<String, Map<String, Integer>> bins = new HashMap<String, Map<String, Integer>>();
	static Map<Long, Order> allOrders = new HashMap<Long, Order>();
	static List<Map<Long, Order>> orders = new ArrayList<Map<Long, Order>>();
	static Map<String, List<Long>> reverseBindings = new HashMap<String, List<Long>>();
	
	static void readBinding(String filename) throws Exception {
		in = new Kattio(new FileInputStream(filename));
		
		while(in.hasMoreTokens()) {
			bindings.put(in.getLong(), in.getWord());
		}
		fillReverseBindings();
	}
	
	private static void fillReverseBindings() {
		Iterator<Long> orderIds = bindings.keySet().iterator();
		while(orderIds.hasNext()) {
			Long orderId = orderIds.next();
			String binId = bindings.get(orderId);
			List<Long> orderList;
			
			if(reverseBindings.containsKey(binId))
				orderList = reverseBindings.get(binId);
			else
				orderList = new ArrayList<Long>();
			
			orderList.add(orderId);
			reverseBindings.put(binId, orderList);
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
			
			if(modLocation.equals("A")) {
				modOrder = orders.get(0);
			}
			else if(modLocation.equals("B")) {
				modOrder = orders.get(1);
			}
			else if(modLocation.equals("C")) {
				modOrder = orders.get(2);
			}
			else {
				modOrder = orders.get(3);
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
