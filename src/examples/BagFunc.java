package examples;

import java.util.List;

import gal4j.algorithm.AFitnessFunction;
import gal4j.chromosome.AChromosome;

public class BagFunc extends AFitnessFunction {
	
	
	
	public List<Item> items;
	public int maxWeight;
	
	
	public BagFunc(List<Item> items, int size) {
		this.items = items;
		this.maxWeight = size;
		
		
	}
	
	
	@Override
	public double calculateFitness(AChromosome<?> chromosome) {
		double fitValue = 0;
		int chWeight = 0;
		for(int i = 0; i < chromosome.getGenLength(); i++) {
			if((int)chromosome.getGen(i) == 1) {
				fitValue += items.get(i).value;
				chWeight += items.get(i).weight;
			}
		}
		
		return (chWeight > maxWeight) ? 0 : fitValue;
	}

	
	
    
    
	



}
