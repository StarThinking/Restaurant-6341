
public class Timer {
	
	private static long startTime;
	private static long timeUnit = 1000; 
	
	public static void start() {
		startTime = System.currentTimeMillis();
	}
	
	public static void setTimeUnit(long unit) {
		timeUnit = unit;
	}
	
	public static long getTimeUnit() {
		return timeUnit;
	}
	
	public static long getCurrentTime() {
		long timeNow = System.currentTimeMillis();
		return ((timeNow - startTime) / timeUnit);
	}
	
}
