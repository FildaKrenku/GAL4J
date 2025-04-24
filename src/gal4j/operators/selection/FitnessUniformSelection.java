package gal4j.operators.selection;

import java.util.List;
import java.util.Random;

import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * Fitness Uniform Selection is a selection strategy that chooses chromosomes
 * based on a randomly generated target fitness value within the current fitness range.
 * It then selects the chromosome whose fitness is closest to this target.
 *
 * @author Filip Křenek
 * @version 1.0
 */
public class FitnessUniformSelection extends ASelection {

	/** Random number generator used for fitness sampling */
    private final Random rand;

    /**
     * Constructs a FitnessUniformSelection with a new random generator.
     */
    public FitnessUniformSelection() {
        this.rand = new Random();
    }
    
    /**
     * Constructs a FitnessUniformSelection with a given random generator.
     *
     * @param rand Custon random number generator
     */
    public FitnessUniformSelection(Random rand) {
        this.rand = rand;
    }
    

    /**
     * Selects a chromosome whose fitness is closest to a randomly generated target fitness.
     *
     * @param  population The population from which to select.
     * @return A chromosome closest in fitness to the target fitness.
     */
    @Override
    public AChromosome<?> select(Population population) {
        List<AChromosome<?>> chromosomes = population.getPopulation();
       

        if(!population.isSorted()) {
        	population.sortPopulation();
        }
        
        double minFitness = population.getWorstChromosome().getFitness();
        double maxFitness = population.getBestChromosome().getFitness();
        
        

        // Náhodně vyber fitness hodnotu v rozsahu <minFitness, maxFitness>
        double targetFitness = minFitness + rand.nextDouble() * (maxFitness - minFitness);

        // Najdi jedince s fitness nejblíže k targetFitness
        AChromosome<?> selected = null;
        double bestDifference = Double.MAX_VALUE;

        for (AChromosome<?> chromosome : chromosomes) {
            double diff = Math.abs(chromosome.getFitness() - targetFitness);
            if (diff < bestDifference) {
                bestDifference = diff;
                selected = chromosome;
            }
        }

        return checkReturn(selected);
    }
}
