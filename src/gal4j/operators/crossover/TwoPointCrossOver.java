package gal4j.operators.crossover;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * Implements the twopoint crossover operator.
 *
 * Two distinct crossover points are randomly selected. The genes between these two points are taken from the second parent, and the rest are taken from the first parent.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class TwoPointCrossOver implements ICrossover {
	
	/** Random number generator for chosing gene indexes */
	private final Random rand;
	
	/**
     * Constructs a TwoPointCrossOver with a default random number generator.
     */
	public TwoPointCrossOver() {
		this.rand = new Random();
	}
	
	
	/**
     * Constructs a TwoPointCrossOver with a custom random number generator used for testing.
     *
     * @param rand the random number generator to use
     */
	public TwoPointCrossOver(Random rand) {
		this.rand = rand;
	}
	

	/**
     * Performs a twopoint crossover between two parents.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new offspring chromosome formed by mixing segments from both parents
     */
	@Override
	public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
		AChromosome<?> crossed = parent1.clone();
		
		int geneLength = parent1.getGenLength();
		
		int crossPoint1 = rand.nextInt(geneLength);
		int crossPoint2 = rand.nextInt(geneLength);
		
		while(crossPoint1 == crossPoint2) {
			crossPoint2 = rand.nextInt(geneLength);
		}
		
		
		
		if (crossPoint1 > crossPoint2) {
            int temp = crossPoint1;
            crossPoint1 = crossPoint2;
            crossPoint2 = temp;
        }
		
		
		
		 for (int i = 0; i < geneLength; i++) {
	            if (i >= crossPoint1 && i < crossPoint2) {
	                crossed.setGen(i, parent2.getGen(i));
	            } else {
	                crossed.setGen(i, parent1.getGen(i));
	            }
	        }
		
		return crossed;
		
	}

}
