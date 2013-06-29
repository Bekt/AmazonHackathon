package level3;

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
	
}
