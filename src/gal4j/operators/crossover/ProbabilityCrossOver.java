package gal4j.operators.crossover;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * An implementation of a probabilistic crossover operator.
 *
 * For each gene, a random decision is made to determine whether it will be inherited from the first or second parent, based on the specified probability.
 *
 * @author Filip Křenek
 * @version 1.0
 */

public class ProbabilityCrossOver implements ICrossover {
	
	/** probability whether the gene will be from parent one */
    private final double probability; 
    /** Random number generator */
    private final Random rand;

    /**
     * Constructs a new ProbabilityCrossOver with a given probability and a default random generator.
     *
     * @param probability the probability of taking a gene from the first parent
     */
    public ProbabilityCrossOver(double probability) {
        this.probability = probability;
        this.rand = new Random();
    }
    
    /**
     * Constructs a new ProbabilityCrossOver with a given probability and specified random number generator for testing purposes
     *
     * @param probability the probability of taking a gene from the first parent
     * @param rand the random number generator to use
     */
    public ProbabilityCrossOver(double probability, Random rand) {
        this.probability = probability;
        this.rand = rand;
    }

    
    
    /**
     * Performs the probabilistic crossover between two parents.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new offspring chromosome created via probabilistic crossover
     */
    @Override
    public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
    	AChromosome<?> crossed = parent1.clone();;
		
		
        
        for (int i = 0; i < parent1.getGenLength(); i++) {
            if (rand.nextDouble() < probability) {
            	crossed.setGen(i, parent1.getGen(i)); // Gene from parent1
            } else {
            	crossed.setGen(i, parent2.getGen(i)); // Gene from parent2
            }
        }

        return crossed;
    }
}
