package examples;

import java.util.ArrayList;
import java.util.List;

import gal4j.chromosome.AChromosome;

public class Bag {
	
	public List<Item> items;
	public int maxWeight;
	
	
	public Bag(int size) {
		this.items = new ArrayList<Item>();
		this.maxWeight = size;
		createItems();
		
	}
	
	
	public void createItems() {
		items.add(new Item(60, 10));
        items.add(new Item(100, 20));
        items.add(new Item(120, 30));
        items.add(new Item(80, 15)); 
        items.add(new Item(50, 5));  
        items.add(new Item(70, 25));  
        items.add(new Item(90, 12));  
        items.add(new Item(110, 22));
        items.add(new Item(130, 35)); 
        items.add(new Item(95, 18)); 
        items.add(new Item(55, 7));   
        items.add(new Item(85, 28));  
        items.add(new Item(125, 40)); 
        items.add(new Item(140, 45)); 
        items.add(new Item(75, 10));
	}
	
	public void print(AChromosome<?> chromosome) {
		int weight = 0;
	    for(int i = 0; i < chromosome.getGenLength(); i++) {
			if((int)chromosome.getGen(i) == 1) {
				 System.out.println("Hodnota: " + items.get(i).value + ", Váha: " + items.get(i).weight);
				 weight += items.get(i).weight;
			}
		}
	    System.out.println("Váha batohu: " + weight);
	}

}
