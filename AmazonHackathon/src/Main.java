import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void main(String[] args) throws Exception {
		Parse.readInventory("inventory.dat");
		Parse.readBinding("bindings.dat");
		Parse.readOrders("demands.dat");

		List<Picker> pickerList = new ArrayList<Picker>();
		PickerOperations ops = new PickerOperations();
		Picker p = new Picker("picker-1");
		pickerList.add(p);
		
		System.out.println("Order A size: " + Parse.ordersA.size());
		System.out.println("Order B size: " + Parse.ordersB.size());
		System.out.println("Order C size: " + Parse.ordersC.size());
		System.out.println("Order D size: " + Parse.ordersD.size());
		
		long start = System.currentTimeMillis();
		long totalTime = 0;
		while (Parse.ordersD.size() > 0) {
			if (!ops.getNextOrder(p, Parse.ordersD)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("Added picker number of orders left in OrdersC: " + Parse.ordersD.size());
				System.out.println("Completed in sec " + (System.currentTimeMillis() - start)/1000);
				totalTime += (System.currentTimeMillis() - start)/1000;
				System.out.println("Total time: "+totalTime);
				start = System.currentTimeMillis();
				pickerList.add(p);
			}
		}
		
		
		/*while (Parse.ordersA.size() > 0) {
			if (!ops.getNextOrder(p, Parse.ordersA)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("Added picker number of orders left in OrdersA: " + Parse.ordersA.size());
				pickerList.add(p);
			}
		}
		
		System.out.println("Completed A with pickers "+ pickerList.size());
		*/
		
		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			if(p.time < 30000) {
				while(ops.getNextOrder(p, Parse.ordersB)) {
					
				}
			}
		}
		while (!Parse.ordersB.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersB)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("Added picker number of orders left in OrdersB: " + Parse.ordersB.size());
				System.out.println("Completed in sec " + (System.currentTimeMillis() - start)/1000);
				totalTime += (System.currentTimeMillis() - start)/1000;
				System.out.println("Total time: "+totalTime);
				start = System.currentTimeMillis();
				pickerList.add(p);
			}
		}
		System.out.println("Completed B with pickers "+pickerList.size());

		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			if(p.time < 30000) {
				while(ops.getNextOrder(p, Parse.ordersC)) {
				}
			}
		}
		while (!Parse.ordersC.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersC)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("Added picker number of orders left in OrdersC: " + Parse.ordersC.size());
				System.out.println("Completed in sec " + (System.currentTimeMillis() - start)/1000);
				totalTime += (System.currentTimeMillis() - start)/1000;
				System.out.println("Total time: "+totalTime);
				start = System.currentTimeMillis();
				pickerList.add(p);
			}
		}
		System.out.println("Completed C with pickers "+pickerList.size());
		for(int i=0;i<pickerList.size();i++) {
			p = pickerList.get(i);
			if(p.time < 30000) {
				while(ops.getNextOrder(p, Parse.ordersA)) {
				}
			}
		}
		while (!Parse.ordersA.isEmpty()) {
			if (!ops.getNextOrder(p, Parse.ordersA)) {
				p = new Picker("picker-" + (pickerList.size() + 1));
				System.out.println("Added picker number of orders left in OrdersD: " + Parse.ordersA.size());
				System.out.println("Completed in sec " + (System.currentTimeMillis() - start)/1000);
				totalTime += (System.currentTimeMillis() - start)/1000;
				System.out.println("Total time: "+totalTime);
				start = System.currentTimeMillis();
				pickerList.add(p);
			}
		}
		
		System.out.println("Completed D with pickers "+pickerList.size());
		int totalCount = 0;
		for(int i=0;i<pickerList.size();i++){
			System.out.println(pickerList.get(i).toString());
			totalCount += pickerList.get(i).completedOrders.size();
		}
		System.out.println("used " + pickerList.size() + " pickers");
		System.out.println("Time taken: " + (System.currentTimeMillis() - start));
		System.out.println("Total: " + totalCount);
		Parse.in.close();
	}	
}
