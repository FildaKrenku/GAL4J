package gal4j.operators.selection;


import gal4j.algorithm.Config;
import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;


/**
 * A selection strategy that returns the top performing chromosomes (elites) from the population. The number of elites is determined by the selection rate.
 * 
 * This implementation cycles through the top N elite individuals and returns them one by one.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class EliteSelection extends ASelection {
	
	/** The number of elite chromosomes to select from the population */
    private final int eliteCount;
    /** Index of the next elite chromosome to return */
    private int currentEliteIndex;

    
    /**
     * Constructs an EliteSelection based on the configuration.
     *
     * @param conf Configuration object containing population size and selection rate.
     */
    public EliteSelection(Config conf) {
        this.eliteCount = (int) (conf.getPopulationSize() * conf.getSelectionRate());
        this.currentEliteIndex = 0;
    }
    

    /**
     * Selects the next elite chromosome from the top of the population.
     * The population is sorted if not already sorted.
     *
     * @param population The population from which to select the elite chromosome.
     * @return The next elite chromosome
     */
    @Override
    public AChromosome<?> select(Population population) {
        
    	if(!population.isSorted()) {
    		population.sortPopulation();
    	}

        AChromosome<?> selectedElite = population.getChromosome(currentEliteIndex);
        
        if(currentEliteIndex != eliteCount) {
        	currentEliteIndex = (currentEliteIndex += 1) % eliteCount;
        	return selectedElite;
        }
        
		return null;
            
    }


	
   
}
