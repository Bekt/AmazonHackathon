import java.util.Iterator;
import java.util.Map;

import com.amazon.aft.hackathon.TravelTimeCalculator;


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
	boolean getNextOrder(Picker p, Map<Long, Order> orderList) {
		Iterator<Long> it = orderList.keySet().iterator();
		long nextOrderId = 0;
		double maxPriorityRatio = 0;
		long nextTravelTime = 0;
		while(it.hasNext()) {
			Order currentOrder = orderList.get(it.next());
			try {
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
						double priorityRatio = (double)travelTime/(double)timeLeft;
						if(priorityRatio > 0 && (p.time + travelTime <=36000)) {
							// If same priority ratio choose the one with less travel time
							if(priorityRatio == maxPriorityRatio) {
								if(travelTime < nextTravelTime) {
									nextOrderId = currentOrder.orderId;
									nextTravelTime = travelTime;
									maxPriorityRatio = priorityRatio;
								}
							}
							else if(priorityRatio > maxPriorityRatio) {
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
		return false;
	}

}
