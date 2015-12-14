import java.util.ArrayList;
import java.util.List;

public class Cook extends Thread{

	private Restaurant restaurant;
	private int id;
	private boolean shouldRun;
	private Diner diner;
	
	public Cook(Restaurant r, int i) {
		this.restaurant = r;
		this.id = i;
		this.shouldRun = true;
		this.diner = null;
	}
	
	@Override
	public void run() {
		while(shouldRun) {
			try {
				if(diner != null) {				
					List<Integer> order = diner.getOrder();
					while(true) {
						List<Integer> neededMachineList = neededMachineList(order);
						if(neededMachineList.size() == 0)
							break;					
						int machineId = restaurant.getMachine(this, neededMachineList); // get a machine from restaurant
						int timeForCooking = getCookTime(machineId);
						int foodNum = order.get(machineId);
						Thread.sleep(timeForCooking*foodNum*Timer.getTimeUnit());
						order.set(machineId, 0); // set to 0
						restaurant.releaseMachine(this, machineId);
					}				
					diner.setFoodIsReady(true);
					diner = null;
					restaurant.relaseCook(this);
				} else {
					Thread.sleep(Timer.getTimeUnit());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Integer> neededMachineList(List<Integer> order) {
		List<Integer> neededMachineList  = new ArrayList<Integer>();
		for(int i=0; i<order.size(); i++) {
			if(order.get(i) > 0) {
				neededMachineList.add(i);
			}
		}
		return neededMachineList;
	}
	
	private int getCookTime(int machineId) {
		switch(machineId) {
			case 0: return 5;
			case 1: return 3;
			case 2: return 2;
			case 3: return 1;
			default: return -1;
		}
	}

	public void setDiner(Diner diner) {
		this.diner = diner;
	}

	public Diner getDiner() {
		return this.diner;
	}
	
	public int getCookId() {
		return id;
	}

	public void offwork() {
		this.shouldRun = false;
	}
}
