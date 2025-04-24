package gal4j.operators.crossover;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * Implements the classic singlepoint crossover operator.
 *
 * A single crossover point is chosen randomly, and genes from the first parent
 * are taken up to that point. The remaining genes are taken from the second parent.
 */
public class SinglePointCrossOver implements ICrossover {
	
	private final Random rand;
	
	/**
     * Constructs a SinglePointCrossOver with a default random number generator.
     */
	public SinglePointCrossOver() {
		this.rand = new Random();
	}
	
	/**
     * Constructs a SinglePointCrossOver with a custom random number generator for testing purposes
     *
     * @param rand the random number generator to use
     */
	public SinglePointCrossOver(Random rand) {
		this.rand =	rand;
	}
	

	
	/**
     * Performs a singlepoint crossover between two parents.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new offspring chromosome formed by combining genes from both parents
     */
	@Override
	public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
		AChromosome<?> crossed = parent1.clone();
		
		int genLength = parent1.getGenLength();
		
	    int crossoverPoint = rand.nextInt(genLength);
	    
	    
	    for (int i = 0; i < genLength; i++) {
            if (i < crossoverPoint) {
                crossed.setGen(i, parent1.getGen(i));
            } else {
                crossed.setGen(i, parent2.getGen(i));
            }
        }
	
		return crossed;
	}
	
}
