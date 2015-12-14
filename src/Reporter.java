import java.util.HashMap;
import java.util.Map;

public class Reporter {
	
	private static Map<Integer, Report> report = new HashMap<Integer, Report>();
	
	public static void doReport() {
		System.out.println("Summary Report:");
		String format = "%-12s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-12s%-12s%-12s%-12s%n";
		System.out.printf(format, "Diner\\Time", "Arrive", "GetTable", "TableId", "GetCook", "FoodReady", "Leave", 
				"CookId", "BurgeMachn", "FryMachn", "CokeMachn", "SundaeMachn");
		int reportSize = report.size();
		for(int i=0; i<reportSize; i++) {
			Report line = report.get(i);
			System.out.printf(format, i, line.getArriveTime(), line.getGetTableTime(), line.getTableId(), line.getGetCookTime(),
					line.getFoodReadyTime(), line.getLeaveTime(), line.getCookId(), line.getMachineTime(0), 
					line.getMachineTime(1), line.getMachineTime(2), line.getMachineTime(3));
		}
	}
	
	public static void addDiner(int id) {
		Report r = new Report();
		report.put(id, r);
	}
	
	public static void setArriveTime(Integer id, long time) {
		report.get(id).setArriveTime(time);
	}

	public static void setGetTableTime(Integer id, long time) {
		report.get(id).setGetTableTime(time);
	}

	public static void setGetCookTime(Integer id, long time) {
		report.get(id).setGetCookTime(time);
	}

	public static void setFoodReadyTime(Integer id, long time) {
		report.get(id).setFoodReadyTime(time);
	}

	public static void setLeaveTime(Integer id, long time) {
		report.get(id).setLeaveTime(time);
	}

	public static void setMachineTime(Integer dinerId, Integer machineId, long time) {
		report.get(dinerId).setMachineTime(machineId, time);
	}

	public static void setCookId(Integer dinerId, long cookId) {
		report.get(dinerId).setCookId(cookId);
	}
	
	public static void setTableId(Integer dinerId, long tableId) {
		report.get(dinerId).setTableId(tableId);
	}
	
	
}
