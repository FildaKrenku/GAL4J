package examples;

import java.util.List;

import gal4j.algorithm.AFitnessFunction;
import gal4j.chromosome.AChromosome;

public class TSPFunc extends AFitnessFunction {
	
	
	
	List<Town> towns;
	
	public TSPFunc(List<Town> towns) {
		this.towns = towns;
	}
	
	
	

	 @Override
	    public double calculateFitness(AChromosome<?> chromosome) {

	        double totalDistance = 0.0;

	        for (int i = 0; i < chromosome.getGenLength() - 1; i++) {
	            Town town1 = towns.get((int) chromosome.getGen(i));      
	            Town town2 = towns.get((int) chromosome.getGen(i + 1));  
	            totalDistance += distance(town1, town2);         
	        }

	        Town firstTown = towns.get((int) chromosome.getGen(0));   
	        Town lastTown = towns.get((int) chromosome.getGen(chromosome.getGenLength() - 1));  
	        totalDistance += distance(firstTown, lastTown);
	        
	        return totalDistance == 0 ? Double.MAX_VALUE : 1.0 / totalDistance; 
	        
	    }

	    private double distance(Town a, Town b) {
	        int dx = a.x - b.x;
	        int dy = a.y - b.y;
	        return Math.sqrt(dx * dx + dy * dy);
	    }
	    
	    
	    

}
