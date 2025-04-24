package gal4j.operators.mutation;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * The TwoPointMutation class applies a mutation by altering two distinct genes at randomly selected positions within the chromosome.
 * 
 * @author Filip 
 * @version 1.0
 */
public class TwoPointMutation implements IMutation {
	
	/** Random object used for generating mutation points */
	private final Random rand;
	
	/**
     * Default constructor that initializes the random number generator.
     */
	public TwoPointMutation() {
		this.rand = new Random();
	}
	
	/**
     * Constructor that accepts a custom Random object for mutation operations.
     *
     * @param rand A Random instance to use for generating mutation points.
     */
	public TwoPointMutation(Random rand) {
		this.rand = rand;
	}
	

	
	/**
     * Applies twopoint mutation to the given chromosome.
     *
     * @param  chromosome The chromosome to mutate.
     * @return The chromosome after mutation, with two genes altered.
     */
	@Override
	public AChromosome<?> mutate(AChromosome<?> chromosome) {
		
		
		
		int mutationPoint1 = rand.nextInt(chromosome.getGenLength());
		int mutationPoint2 = rand.nextInt(chromosome.getGenLength());
		
		while(mutationPoint1 == mutationPoint2) {
			mutationPoint2 = rand.nextInt(chromosome.getGenLength());
		}
		
		chromosome.mutateGen(mutationPoint1);
		chromosome.mutateGen(mutationPoint2);
		
		return chromosome;
		
	}
	

}
