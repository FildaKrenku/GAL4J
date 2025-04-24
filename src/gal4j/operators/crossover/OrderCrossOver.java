package gal4j.operators.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * An implementation of the Order Crossover (OX) operator.
 *
 * This crossover preserves the relative order of genes from the second parent while copying a subsequence from the first parent into the offspring. 
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class OrderCrossOver implements ICrossover {
	
	/** Random number generator for chosing gene indexes */
	private final Random rand;
	
	/**
     * Constructs a new OrderCrossOver with a default random number generator.
     */
	public OrderCrossOver() {
		this.rand = new Random();
	}
	
	/**
     * Constructs a new OrderCrossOver with the specified random number generator for testing purposes
     *
     * @param rand the random number generator
     */
	public OrderCrossOver(Random rand) {
		this.rand = rand;
	}
	

	/**
     * Performs the order crossover between two parent chromosomes.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new offspring chromosome created using order crossover
     */
	@Override
	public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
		AChromosome<?> crossed = parent1.clone();
		
		
		
		
		// Ensure that both parents have the same length (chromosome size)
        int length = parent1.getGenLength();
        
        // Randomly choose two crossover points
        int point1 = rand.nextInt(length);
        int point2 = rand.nextInt(length);
        
        // Ensure point1 < point2 for simplicity
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }
      
        
        
        // Extract the section of genes from parent1 and parent2
        List<Object> offspringGenes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            offspringGenes.add(null);  // Initialize the offspring with null genes
        }

        // Copy the genes from the first parent to the offspring in the range [point1, point2]
        for (int i = point1; i <= point2; i++) {
            offspringGenes.set(i, parent1.getGen(i));
        }

        // Now, fill the remaining positions in offspring with genes from parent2, while maintaining order
        int parent2Index = 0;
        for (int i = 0; i < length; i++) {
            if (offspringGenes.get(i) == null) {
                // Find the next gene from parent2 that isn't already in the offspring
                while (offspringGenes.contains(parent2.getGen(parent2Index))) {
                    parent2Index++;
                }
                
                offspringGenes.set(i, parent2.getGen(parent2Index));
                parent2Index++;
               
            }
        }
        
        for(int i = 0; i < length; i++) {
        	crossed.setGen(i, offspringGenes.get(i));
        }
        return crossed;
    }

}
