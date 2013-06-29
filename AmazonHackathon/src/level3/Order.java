package level3;

import java.util.Iterator;

public class Order {
	public Long orderId;
	public Long dueTime;
	public Long dropTime;
	public String itemId;

	public Order(Long orderId, Long dueTime, Long dropTime, String itemId) {
		this.orderId = orderId;
		this.dueTime = dueTime;
		this.dropTime = dropTime;
		this.itemId = itemId;
	}

	public String getClosestBinId(String location) {
		String closestBin = null;
		long travelTime = Long.MAX_VALUE;
		try {
			Iterator<String> it = Parse.binToItems.keySet().iterator();
			while (it.hasNext()) {
				String bin = it.next();
				if (Parse.binToItems.get(bin).containsKey(itemId) 
						&& TravelTimeCalculator.computeTravelTime(location, bin) < travelTime) {
					travelTime = TravelTimeCalculator.computeTravelTime(location, bin);
					closestBin = bin;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return closestBin;
	}

}
