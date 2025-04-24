package gal4j.operators.mutation;

import gal4j.chromosome.AChromosome;

/**
 * Interface for mutation operators in a genetic algorithm.
 *
 * A mutation operator alters a chromosome in some way, typically by changing one or more of its genes to introduce genetic diversity.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public interface IMutation {
	/**
     * Applies mutation to a given chromosome and returns the mutated result.
     *
     * @param  chromosome the original chromosome to mutate
     * @return a new chromosome with applied mutation
     */
	public AChromosome<?> mutate(AChromosome<?> chromosome);
}
