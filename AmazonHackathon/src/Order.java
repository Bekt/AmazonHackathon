
public class Order {
	public Long orderId;
	public Long dueTime;
	public Long dropTime;
	public String itemId;
	public String binId;
	
	public Order(Long orderId, Long dueTime, Long dropTime, String itemId, String binId) {
		this.orderId = orderId;
		this.dueTime = dueTime;
		this.dropTime = dropTime;
		this.itemId = itemId;
		this.binId = binId;
	}
	
	public String toString() {
		return String.format(" orderId: %s %n dueTime: %s %n dropTime %s %n itemId %s %n binId %s", 
				orderId, dueTime, dropTime, itemId, binId);
	}
	
}
