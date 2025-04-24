package gal4j.algorithm;

import gal4j.chromosome.AChromosome;

/**
  * Abstract class that represents a fitness evaluation mechanism for each individual in the population. 
 * This class should be extended to implement problem specific fitness evaluation logic.
 * @author Filip Křenek
 * @version 1.0 
 */
public abstract class AFitnessFunction {
	
	/** 
	 * Calculates and returns the fitness value of the specified chromosome.
	 * @param chromosome the chromosome whose fitness is to be evaluated
	 * @return a double representing the fitness score of the chromosome
	 */
	public abstract double calculateFitness(AChromosome<?> chromosome);
}
