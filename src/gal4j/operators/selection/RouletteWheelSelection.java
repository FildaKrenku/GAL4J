package gal4j.operators.selection;

import java.util.Random;

import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * RouletteWheelSelection implements a probabilistic selection method based on fitness proportion. 
 * Chromosomes with higher fitness have a higher chance of being selected.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class RouletteWheelSelection extends ASelection {

	/** Random number generator used for selection */
    private final Random rand;
    
    /**
     * Constructs a RouletteWheelSelection with a new random generator.
     */
    public RouletteWheelSelection() {
        this.rand = new Random();
       
    }
    
    /**
     * Constructs a RouletteWheelSelection using a custom random generator.
     *
     * @param rand the Random instance to use
     */
    public RouletteWheelSelection(Random rand) {
        this.rand = rand;
       
    }

    /**
     * Selects a chromosome from the population using roulette wheel selection.
     *
     * @param  population the population to select from
     * @return the selected chromosome 
     */
    @Override
    public AChromosome<?> select(Population population) {
    

        // Calculate total fitness for non-selected chromosomes
        double totalFitness = 0.0;
        for (int i = 0; i < population.getPopulationSize(); i++) {
            AChromosome<?> chromosome = population.getChromosome(i);
            totalFitness += chromosome.getFitness();
        }

        // Random value between 0 and totalFitness
        double targetFitness = rand.nextDouble() * totalFitness;

        // Find the chromosome whose cumulative fitness exceeds the targetFitness
        double cumulativeFitness = 0.0;
        for (int i = 0; i < population.getPopulationSize(); i++) {
            AChromosome<?> selected = population.getChromosome(i);
            
            // Increment cumulative fitness
            cumulativeFitness += selected.getFitness();

            // Check if this chromosome matches the target
            if (cumulativeFitness >= targetFitness) {
            	return checkReturn(selected);               
            }
        }

       

        return null; // This should never occure
    }



}
