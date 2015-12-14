import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {

	private volatile Map<Integer, Boolean> tables = new HashMap<Integer, Boolean>();
	private volatile Map<Cook, Boolean> cooks = new HashMap<Cook, Boolean>();
	private volatile Map<Integer, Boolean> machines = new HashMap<Integer, Boolean>();
	private volatile Integer dinerServed = 0;
	private int dinerNum;
	private final int machineNum = 4;
	private final String[] FoodType = {"Buckeye Burge", "Brutus Fries", "Coke", "TBDBITL Sundae"};
	
	public Restaurant(int dinerNum, int tableNum, int cookNum) {
		this.dinerNum = dinerNum;	
		// initialize tables (false : available, true : occupied)
		for(int i=0; i<tableNum; i++) {
			//System.out.println("Table " + i + " created");
			tables.put(i, false); 
		}	
		// initialize cooks (false : available, true : busy)
		for(int i=0; i<cookNum; i++) {
			System.out.println("Cook " + i + " created");
			Cook cook = new Cook(this, i);
			cook.start();
			cooks.put(cook, false); 
		}
		// initialize machines (false : available, true : occupied)
		for(int i=0; i<machineNum; i++) {
			machines.put(i, false); 
		}		
	}
	
	public int getTable(Diner diner) throws InterruptedException {
		synchronized(tables) {
			boolean found = false;
			Integer id = null;
			while(!found) {
				for(Integer tableId : tables.keySet()) {
					if(!tables.get(tableId)) {
						found = true;
						tables.put(tableId, true);
						id = tableId;	
						long currentTime = Timer.getCurrentTime();
						System.out.print("[" + currentTime + "] ");
						System.out.println("Diner " + diner.getId() + " is seated at table " + tableId);
						Reporter.setArriveTime(diner.getId(), diner.getArriveTime());
						Reporter.setGetTableTime(diner.getId(), currentTime);
						Reporter.setTableId(diner.getId(), id);
						break;
					}
				}
				if(!found) {
					System.out.print("[" + Timer.getCurrentTime() + "] ");
					System.out.println("Diner " + diner.getId() + " is waiting for a table...");
					tables.wait();
				}
			}
			return id;
		}
	}
	
	public void releaseTable(Diner diner, Integer id) {
		synchronized(tables) {
			long currentTime = Timer.getCurrentTime();
			System.out.print("[" + currentTime + "] ");
			System.out.println("Diner " + diner.getId() + " leaves");
			Reporter.setLeaveTime(diner.getId(), currentTime);
			tables.put(id, false);
			tables.notifyAll();
		}
		synchronized(dinerServed) {
			dinerServed ++;
			if(dinerServed == dinerNum) {
				System.out.print("[" + Timer.getCurrentTime() + "] ");
				System.out.println("The last diner has just left and the restaurant is to be closed");
				// reached the dinerNum and restaurant can be closed
				for(Cook cook : cooks.keySet()) {
					cook.offwork();
				}
				Reporter.doReport();
			}
		}
	}
	
	public int getCook(Diner diner) throws InterruptedException {
		synchronized(cooks) {
			boolean found = false;
			Integer id = null;
			while(!found) {
				for(Cook cook : cooks.keySet()) {
					if(!cooks.get(cook)) {
						found = true;
						cooks.put(cook, true);
						cook.setDiner(diner);
						id = cook.getCookId();
						long currentTime  = Timer.getCurrentTime();
						System.out.print("[" + currentTime + "] ");
						System.out.println("Diner " + diner.getId() + "'s order will be processed by Cook " + id);
						Reporter.setCookId(diner.getId(), id);
						Reporter.setGetCookTime(diner.getId(), currentTime);
						break;
					}
				}
				if(!found) {
					System.out.print("[" + Timer.getCurrentTime() + "] ");
					System.out.println("Diner " + diner.getId() + " is waiting for a cook...");
					cooks.wait();
				}
			}
			return id;
		}
	}
	
	public void relaseCook(Cook cook) {
		synchronized(cooks) {
			cooks.put(cook, false);
			cooks.notifyAll();
		}
	}
	
	public int getMachine(Cook cook, List<Integer> neededMachineList) throws InterruptedException {		
		synchronized(machines) {
			boolean found = false;
			Integer id = null;
			while(!found) {
				for(Integer machineId : neededMachineList) {
					if(!machines.get(machineId)) {
						found = true;
						machines.put(machineId, true);
						id = machineId;	
						long currentTime = Timer.getCurrentTime();
						System.out.print("[" + currentTime + "] ");
						System.out.println("Cook " + cook.getCookId() + " gets the machine for " + FoodType[machineId]);
						Reporter.setMachineTime(cook.getDiner().getId(), machineId, currentTime);
						break;
					}
				}
				if(!found) {
					System.out.print("[" + Timer.getCurrentTime() + "] ");
					System.out.println("Cook " + cook.getCookId() + " is waiting for machine...");
					machines.wait();
				}
			}	
			return id;
		}
	}

	public void releaseMachine(Cook cook, int machineId) {
		synchronized(machines) {
			machines.put(machineId, false);
			machines.notifyAll();
		}
	}
	
}
