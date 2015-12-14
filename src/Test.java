import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Test {
	
	private static List<Diner> initDiner(int dinerNum, List<List<Integer>> dinerLines, Restaurant restaurant) {
		List<Diner> diners = new ArrayList<Diner>();
		for(int i=0; i<dinerNum; i++) {
			List<Integer> line = dinerLines.get(i);
			Diner diner = new Diner(restaurant, line.get(0), line.subList(1, line.size()));
			diner.setId(i);
			System.out.println("Diner " + i + " created");
			Reporter.addDiner(i);
			diners.add(diner);
		}
		return diners;
	}
	
	public static void main(String[] args) {

		// configure Timer
		Timer.setTimeUnit(50);
		
		// configure
		int DINERNUM = -1;
		int TABLENUM = -1;
		int COOKNUM = -1;
		List<List<Integer>> dinerLines = new ArrayList<List<Integer>>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));	
			String line;
			line = br.readLine();
			DINERNUM = Integer.valueOf(line.trim());
			line = br.readLine();
			TABLENUM = Integer.valueOf(line.trim());
			line = br.readLine();
			COOKNUM = Integer.valueOf(line.trim());
			for(int i=0; i<DINERNUM; i++) {
				line = br.readLine();
				StringTokenizer st = new StringTokenizer(line);
				List<Integer> dinerLine = new ArrayList<Integer>();
				while(st.hasMoreTokens()) {
					String token = st.nextToken();
					dinerLine.add(Integer.valueOf(token));
				}
				dinerLines.add(dinerLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("In Restaurant 6431, there are " + TABLENUM + " tables, " + COOKNUM + " cooks, and "
				+ DINERNUM + " dinner will be coming");
		
		Restaurant restaurant = new Restaurant(DINERNUM, TABLENUM, COOKNUM);
		List<Diner> diners = initDiner(DINERNUM, dinerLines, restaurant);
		List<Thread> dinerThreads = new ArrayList<Thread>();
		Timer.start();
		for(Diner diner : diners) {
			Thread dt = new Thread(diner);
			dinerThreads.add(dt);
			dt.start();
		}
	}

}
