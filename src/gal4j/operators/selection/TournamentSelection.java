package gal4j.operators.selection;

import java.util.Random;

import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * TournamentSelection implements the tournament selection method.
 * A number of individuals are randomly chosen from the population, and the one with the highest fitness among them is selected.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class TournamentSelection extends ASelection {

	/** Number of individuals participating in each tournament */
    private int tournamentSize;
    /** Random number generator used for selection */
    private final Random rand;

   
    /**
     * Constructs a TournamentSelection with the given tournament size and a new Random instance.
     *
     * @param tournamentSize number of individuals in each tournament
     */
    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        this.rand = new Random();
       
    }
    
    /**
     * Constructs a TournamentSelection with the given tournament size and random generator.
     *
     * @param tournamentSize number of individuals in each tournament
     * @param rand the Random instance to use
     */
    public TournamentSelection(int tournamentSize, Random rand) {
        this.tournamentSize = tournamentSize;
        this.rand = rand;
       
    }

    
    /**
     * Selects a chromosome using tournament selection.
     * Randomly selects tournamentSize individuals and returns the fittest among them.
     *
     * @param  population the population to select from
     * @return the selected chromosome 
     */
    @Override
    public AChromosome<?> select(Population population) {
       
        AChromosome<?> candidate = null;
        for (int i = 0; i < tournamentSize; i++) {
            AChromosome<?> potential = population.getChromosome(rand.nextInt(population.getPopulationSize()));
            if (candidate == null || potential.getFitness() > candidate.getFitness()) {
                candidate = potential;
            }
        }
        
       
        return checkReturn(candidate);
    }

    
}