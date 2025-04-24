package gal4j.operators.mutation;


import java.util.Random;

import gal4j.chromosome.AChromosome;


/**
 * A mutation operator that applies a singlepoint mutation to a chromosome.
 * A randomly selected gene is mutated.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class SinglePointMutation implements IMutation {
	
	/** Random number generator */
	private final Random rand;
	
	/**
     * Default constructor that uses a new Random instance for mutation.
     */
	public SinglePointMutation() {
		this.rand = new Random();
	}
	
	/**
     * Constructor that allows for a custom Random instance.
     *
     * @param rand the Random instance to use for mutation
     */
	public SinglePointMutation(Random rand) {
		this.rand = rand;
	}

	/**
     * Mutates a chromosome by selecting a random gene and applying mutation to it.
     *
     * @param chromosome the chromosome to mutate
     * @return the mutated chromosome
     */
	@Override
	public AChromosome<?> mutate(AChromosome<?> chromosome) {
		
		int mutationPoint = rand.nextInt(chromosome.getGenLength());
		
		chromosome.mutateGen(mutationPoint);
		
		
		return chromosome;
		
	}
	
	
}
