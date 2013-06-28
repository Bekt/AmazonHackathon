import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Parse {
	static Kattio in;
	static Map<Long, String> bindings = new HashMap<Long, String>();
	static Map<Long, Order> orderList = new HashMap<Long, Order>();
	static Map<String, Map<String, Integer>> bins = new HashMap<String, Map<String, Integer>>();

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
			orderList.put(orderId, order);
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
