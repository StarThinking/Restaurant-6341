import java.util.List;

public class Diner implements Runnable {
	
	private int id;
	private Restaurant restaurant;
	private Integer arriveTime;
	private List<Integer> order;
	private Boolean foodIsReady;
	private int tableId = -1;
	
	public Diner(Restaurant r, Integer at, List<Integer> o) {
		this.restaurant = r;
		this.arriveTime = at;
		this.order = o;
		foodIsReady = false;
	}
	
	private boolean checkTime() {
		return (arriveTime <= Timer.getCurrentTime()) ? true : false;
	}
	
	private int seat() throws InterruptedException {
		tableId = -1;
		while(!checkTime()) {
			Thread.sleep(Timer.getTimeUnit());
		}
		tableId = restaurant.getTable(this);
		return tableId;
	}
	
	private void leave(int tableId) {
		restaurant.releaseTable(this, tableId);
	}
	
	private void placeOrder() {
		try {
			restaurant.getCook(this);
			while(!foodIsReady) {
				Thread.sleep(Timer.getTimeUnit());
			}
			long currentTime = Timer.getCurrentTime();
			System.out.print("[" + currentTime + "] ");
			System.out.println("Diner " + id + "'s food is ready and brought to table");
			Reporter.setFoodReadyTime(id, currentTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			int tableId = seat();
			placeOrder();
			// 30 time units to eat food
			Thread.sleep(Timer.getTimeUnit()*30);
			leave(tableId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

	public void setFoodIsReady(Boolean foodIsReady) {
		this.foodIsReady = foodIsReady;
	}

	public void printOrder() {
		int item = 0;
		for(Integer num : order) {
			System.out.println(item + " : " + num);
			item ++;
		}
	}
	
	public List<Integer> getOrder() {
		return order;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getArriveTime() {
		return this.arriveTime;
	}
}
