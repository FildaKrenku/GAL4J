package gal4j.operators.crossover;

import gal4j.chromosome.AChromosome;

/**
 * Interface representing a crossover operator.
 * 
 * Implementations of this interface define how two parent chromosomes are combined to produce a single offspring.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public interface ICrossover {
	/**
     * Performs crossover between two parent chromosomes and produces a new offspring.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new chromosome resulting from the crossover of the two parents
     */
	public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2);
}
