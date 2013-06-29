package level3;
import java.util.ArrayList;

public class Picker {
	
	private String id;
	public ArrayList<Long> completedOrders = new ArrayList<Long>();
	public String location;
	public long time;
	
	public Picker(String id) {
		this.id = id;
		location = "P-1-A-0000000000";
		time = 0;
	}

	public String getId() {
		return id;
	}
	
	public String toString() {
		return "picker " + id + " has " + time + " time and completed " + completedOrders.size() + " orders";
	}
}
