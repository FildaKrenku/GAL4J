package gal4j.operators.selection;

import java.util.Random;

import gal4j.algorithm.Config;
import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * RouletteElitismSelection combines roulette wheel selection with elitism.
 * With a certain probability, it selects an elite chromosome; otherwise, it performs roulette wheel selection.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class RouleteElitismSelection extends ASelection {
	
	
	/** Random number generator for selection decisions */
	private final Random rand;
	/** The number of elite chromosomes to always consider first */
	private final int eliteCount;
	/** The probability of selecting from the elite group */
	private final double eliteRate;
	/** Index of the next elite chromosome to be returned */
	private int currentEliteIndex;
	/** Tracks the generation number to reset elite cycling per generation */
	private int populationNum = 0;
	
	/**
     * Constructs a selection mechanism with a given configuration and default randomness.
     *
     * @param conf the configuration providing selection rate and population size
     */
	public RouleteElitismSelection(Config conf) {
		this.eliteCount = (int) (conf.getPopulationSize() * conf.getSelectionRate());
		this.eliteRate = conf.getSelectionRate();
		this.rand = new Random();
		this.currentEliteIndex = 0;
	}
	
	
	/**
     * Constructs a selection mechanism with a given configuration and custom random number generator.
     *
     * @param conf the configuration providing selection rate and population size
     * @param rand the random number generator
     */
	public RouleteElitismSelection(Config conf, Random rand) {
		this.eliteCount = (int) (conf.getPopulationSize() * conf.getSelectionRate());
		this.eliteRate = conf.getSelectionRate();
		this.rand = rand;
		this.currentEliteIndex = 0;
	}
	
	
	/**
     * Selects a chromosome either via elitism or roulette wheel selection.
     * Resets elite tracking if a new generation is detected.
     *
     * @param population the population to select from
     * @return the selected chromosome (possibly a clone if already returned)
     */
	@Override
	public AChromosome<?> select(Population population) {
		int newGen = population.getGeneration();
		
		if(newGen != populationNum) {
			populationNum = newGen;
			currentEliteIndex = 0;
		}
		

		if(!population.isSorted()) {
    		population.sortPopulation();
    	}
		
		
		if(rand.nextDouble() <= eliteRate) {
			AChromosome<?> selected = population.getChromosome(currentEliteIndex);
			currentEliteIndex = (currentEliteIndex += 1) % eliteCount;
			return checkReturn(selected);
			
		}
		
		
		double totalFitness = population.getPopulation().stream().mapToDouble(AChromosome::getFitness).sum();
        double spin = rand.nextDouble() * totalFitness;
        double cumulative = 0;
        
        for (AChromosome<?> selected : population.getPopulation()) {
            cumulative += selected.getFitness();
            if (cumulative >= spin) {
                return checkReturn(selected);
            }
        }
		
		
		
		return null;
	}

}
