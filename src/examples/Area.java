package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {

	public List<Town> towns = new ArrayList<Town>();
	
	public Area() {
		createTowns();
	}
	
	
	
	public void createTowns() {
        Random random = new Random(); 
        for (int i = 0; i <= 1000000; i++) {
            int x = random.nextInt(); 
            int y = random.nextInt();
            towns.add(new Town(x, y, i));
        }
    }
	
	
	
}
