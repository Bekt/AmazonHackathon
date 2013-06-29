import java.util.Iterator;
import java.util.Map;



/**
 * @author gollapud
 *
 */
public class PickerOperations {



	/**
	 * @param p - Picker
	 * @param orderList - orderList of a Quadrant
	 * 
	 * Updates picker with the next order value
	 * and removes the order from OrderList
	 *  
	 * @return true - if update successful 
	 * false - if no order found
	 */
	static boolean getNextOrder(Picker p, Map<Long, Order> orderList) {
		Iterator<Long> it = orderList.keySet().iterator();
		long nextOrderId = 0;
		double maxPriorityRatio = 0;
		long nextTravelTime = 0;
		long smallestStartTime = Integer.MAX_VALUE;
		long smallestOrderId = 0;
		int orderCount = 0;
		while(it.hasNext() && orderCount<1000) {
			orderCount++;
			Order currentOrder = orderList.get(it.next());
			try {
				if(currentOrder.dropTime < smallestStartTime) {
					smallestStartTime = currentOrder.dropTime;
					smallestOrderId = currentOrder.orderId;
				}
				long travelTime = TravelTimeCalculator.computeTravelTime(p.location, currentOrder.binId);
				long timeLeft = currentOrder.dueTime - p.time - travelTime;
				// Ignore the order if it is not ready yet
				if(travelTime+p.time > currentOrder.dropTime) {
					// If there is order at the same location pick it up at no cost
					// If there is an order which can be completed in deadline complete that
					if(travelTime == 0 || timeLeft == 0) {
						if((p.time + travelTime) <= 36000) {
							p.completedOrders.add(currentOrder.orderId);
							p.location = currentOrder.binId;
							p.time += travelTime;
							orderList.remove(currentOrder.orderId);
							return true;
						}
					}
					// Else pick the order with highPriorityRatio
					else {
						//double priorityRatio = (double)travelTime/(double)timeLeft;
						double priorityRatio = (1000/(double)timeLeft) 
								- (Math.pow((double)travelTime, 2) / 2500000);
						if(priorityRatio > 0 && (p.time + travelTime <=36000)) {
							// If same priority ratio choose the one with less travel time
							if(priorityRatio == maxPriorityRatio) {
								if(travelTime < nextTravelTime) {
									nextOrderId = currentOrder.orderId;
									nextTravelTime = travelTime;
									maxPriorityRatio = priorityRatio;
								}
							} else if(priorityRatio > maxPriorityRatio) {
								nextOrderId = currentOrder.orderId;
								nextTravelTime = travelTime;
								maxPriorityRatio = priorityRatio;
							}
						}
					}
				}

			} catch (Exception e) {
				System.out.println("Exception:: "+ e.getMessage());
			}
		}

		if(nextOrderId!= 0) {
			Order currentOrder = orderList.get(nextOrderId);
			p.completedOrders.add(currentOrder.orderId);
			p.location = currentOrder.binId;
			p.time += nextTravelTime;
			orderList.remove(currentOrder.orderId);
			return true;
		}
		if(p.time == 0 && smallestOrderId!=0) {
			p.time = smallestStartTime;
			Order currentOrder = orderList.get(smallestOrderId);
			p.completedOrders.add(currentOrder.orderId);
			p.location = currentOrder.binId;
			orderList.remove(currentOrder.orderId);
			return true;
		}
		return false;
	}

}
