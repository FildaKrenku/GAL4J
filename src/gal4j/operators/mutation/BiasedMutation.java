package gal4j.operators.mutation;

import java.util.Random;

import gal4j.chromosome.AChromosome;


/**
 * A mutation operator that applies mutation to genes with a probability biased by fitness.
 *
 * The lower the fitness, the higher the mutation probability.
 * This encourages exploration for weaker solutions and preserves stronger ones.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class BiasedMutation implements IMutation {
	
	/** Random number generator for chosing gene indexes */
	private final Random rand;

	
	/**
     * Creates a BiasedMutation with a default random generator.
     */
	public BiasedMutation() {
		this.rand = new Random();
	}
	
	/**
     * Creates a BiasedMutation with a custom random generator.
     *
     * @param rand the Random instance to use
     */
	public BiasedMutation(Random rand) {
		this.rand = rand;
	}
	
	
	/**
     * Mutates genes in a chromosome based on a fitness-biased probability.
     *
     * @param chromosome the chromosome to mutate
     * @return the mutated chromosome 
     */
	@Override
	public AChromosome<?> mutate(AChromosome<?> chromosome) {
       int length = chromosome.getGenLength();
       if (length == 0) return chromosome; 

       double fitness = chromosome.getFitness();
    
       double normProb = 1.0 / (fitness + 1.0);
        
       for (int i = 0; i < length; i++) {
           if (rand.nextDouble() < normProb) { 
               chromosome.mutateGen(i);
           }
       }

       return chromosome;
	}

}
