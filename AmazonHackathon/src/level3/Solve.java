package level3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Solve {
	
	static List<Picker> pickers = new ArrayList<Picker>();
	static Picker p = new Picker("picker-1");
	
	public static void run() throws Exception{
		updateBins();
		pickers.add(p);
		
		processOrders(Parse.orderList);
		
		for (Picker picker : pickers) {
			for (Long orderId : picker.completedOrders) {
				Order order = Parse.allOrders.get(orderId);
				
				System.out.printf("%s %s %s %s%n", picker.getId(), order.orderId, 
						order.itemId, Parse.allOrders.get(order.orderId));
			}
		}
	}
	
	private static void updateBins() {
		Iterator<Long> orderIds = Parse.allOrders.keySet().iterator();
		List<Long> removeOrderIds = new ArrayList<Long>();
		while(orderIds.hasNext()){
			Long orderId = orderIds.next();
			Order currentOrder = Parse.allOrders.get(orderId);
			Order cloneOrder = Parse.orderList.get(orderId);
			String itemId = currentOrder.itemId;
			List<String> binList = Parse.itemToBins.get(itemId);
			boolean flag = false;
			for(String binId: binList) {
				Map<String, Integer> itemQty = Parse.binToItems.get(binId);
				if(itemQty.containsKey(itemId) && itemQty.get(itemId)>0){
					itemQty.put(itemId, itemQty.get(itemId) - 1);
					currentOrder.binId = binId;
					cloneOrder.binId = binId;
					flag = true;
					break;
				}
			}
			if(!flag) {
				removeOrderIds.add(orderId);
			}
		}
		for(Long oid:removeOrderIds){
			Parse.allOrders.remove(oid);
			Parse.orderList.remove(oid);
		}
	}

	static void processOrders(Map<Long, Order> orders) throws Exception{
		while (!orders.isEmpty()) {
			if (!PickerOperations.getNextOrder(p, orders)) {
				p = new Picker("picker-" + (pickers.size() + 1));
				pickers.add(p);
			}
		}
	}
	
	static void processAgain(Map<Long, Order> orders) throws Exception{
		int size = pickers.size();
		for (int i = 0; i < size; i++) {
			p = pickers.get(i);
			if (p.time < 34000) {
				while (PickerOperations.getNextOrder(p, orders));
			}
		}
	}
}
