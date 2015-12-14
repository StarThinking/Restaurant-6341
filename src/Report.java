import java.util.HashMap;
import java.util.Map;

public class Report {
	private long arriveTime = -1;
	private long getTableTime = -1;
	private long getCookTime = -1;
	private long foodReadyTime = -1;
	private long leaveTime = -1;
	private Map<Integer, Long> machineTime = new HashMap<Integer, Long>();
	private long tableId = -1;
	private long cookId = -1;
	
	public String getMachineTime(int id) {
		if(!machineTime.containsKey(id))
			return "N/A";
		else
			return String.valueOf(machineTime.get(id));
	}
	
	public void setMachineTime(int id, long time) {
		this.machineTime.put(id, time);
	}
	
	public long getArriveTime() {
		return arriveTime;
	}
	
	public void setArriveTime(long arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public long getGetTableTime() {
		return getTableTime;
	}
	
	public void setGetTableTime(long getTableTime) {
		this.getTableTime = getTableTime;
	}
	
	public long getGetCookTime() {
		return getCookTime;
	}
	
	public void setGetCookTime(long getCookTime) {
		this.getCookTime = getCookTime;
	}
	
	public long getFoodReadyTime() {
		return foodReadyTime;
	}
	
	public void setFoodReadyTime(long foodReadyTime) {
		this.foodReadyTime = foodReadyTime;
	}
	
	public long getLeaveTime() {
		return leaveTime;
	}
	
	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	public long getTableId() {
		return tableId;
	}
	
	public void setTableId(long tableId) {
		this.tableId = tableId;
	}
	
	public long getCookId() {
		return cookId;
	}
	
	public void setCookId(long cookId) {
		this.cookId = cookId;
	}
}
