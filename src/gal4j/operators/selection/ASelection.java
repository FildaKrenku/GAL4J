package gal4j.operators.selection;

import java.util.HashSet;
import java.util.Set;

import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * Abstract base class for selection operators.
 * Maintains a set of returned chromosomes to prevent repeated references.
 * Subclasses should implement the select method.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public abstract class ASelection {
	
	/**
     * A set of chromosomes that have already been returned by the selection process.
     * This helps to avoid returning the same reference multiple times.
     */
	protected Set<AChromosome<?>> returnedChromosomes = new HashSet<>();
	
	/**
     * Selects a chromosome from the given population according to the specific
     * selection strategy implemented by the subclass.
     *
     * @param population The population from which to select a chromosome.
     * @return A selected chromosome.
     */
	public abstract AChromosome<?> select(Population population);
	
	/**
     * Clears the set of returned chromosomes.
     * This should be called after the full selection process to reset tracking of which chromosomes have been returned.
     */
	public void reset() {
		returnedChromosomes.clear();
	}
	
	/**
     * Ensures that the same chromosome reference is not returned multiple times.
     * If the chromosome has not been returned before, it is added to the set and returned.
     * Otherwise, a clone is returned instead.
     *
     * @param  selected The selected chromosome.
     * @return The original chromosome if it hasn't been returned before, otherwise a clone.
     */
	public AChromosome<?> checkReturn(AChromosome<?> selected) {
		if(!returnedChromosomes.contains(selected)) {
        	returnedChromosomes.add(selected);
        	return selected;
        }
        else {
        	AChromosome<?> copy = selected.clone();
        	return copy;
        }
	}
	
}
