import java.util.Map;
import java.util.Set;


public class Dummy {
	
	static int count = 0;
	
	public static void main(String[] args) throws Exception {
		Parse.readInventory(args[2]);
		Parse.readBinding(args[3]);
		Parse.readOrders(args[1]);
		
		run(Parse.ordersA);
		run(Parse.ordersB);
		run(Parse.ordersC);
		run(Parse.ordersD);
		
		Parse.in.close();
	}
	
	static void run(Map<Long, Order> map) {
		Set<Long> keys = map.keySet();

		for (Long k : keys) {
			long orderId = map.get(k).orderId;
			String itemId = map.get(k).itemId;
			System.out.printf("picker-%d %s %s %s%n", ++count, orderId,itemId, Parse.bindings.get(orderId));
		}
	}

}
