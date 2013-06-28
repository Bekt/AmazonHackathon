import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {
	Map<Long, Order> orderList = new HashMap<Long, Order>();
	Map<Long, String> bindings = new HashMap<Long, String>();
	Inventory inv;
	static Kattio in;
	
	public static void main(String[] args) throws Exception {
		new Main().run();
		in.close();
	}
	
	void run() throws Exception {
		readBinding();
		readOrders();
		inv = new Inventory();
	}
	
	void readBinding() throws Exception {
		in = new Kattio(new FileInputStream("bindings.dat"));
		
		while(in.hasMoreTokens()) {
			bindings.put(in.getLong(), in.getWord());
		}

		System.out.println("Bindings size: " + bindings.size());
	}
	
	void readOrders() throws Exception {
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

		System.out.println("OrderList size: " + orderList.size());
	}
}
