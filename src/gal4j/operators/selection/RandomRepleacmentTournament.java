package gal4j.operators.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;

/**
 * RandomReplacementTournament is a selection method where a tournament of a fixed size is held, and the best chromosome is usually selected. 
 * However, with a certain probability, a lower ranking chromosome is selected instead to maintain genetic diversity.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class RandomRepleacmentTournament extends ASelection {
	
	/** The number of individuals competing in each tournament */
	private int tournamentSize;
	/** The probability of selecting a non-best chromosome from the tournament */
	private double replacementProbability;
	/** Random number generator */
	private final Random rand;
	
	
	/**
     * Constructs a RandomReplacementTournament selection with default randomness.
     *
     * @param tournamentSize         the number of individuals in the tournament
     * @param replacementProbability the probability to select a non best individual
     */
	public RandomRepleacmentTournament(int tournamentSize, double replacementProbability) {
		this.tournamentSize = tournamentSize;
		this.replacementProbability = replacementProbability;
        this.rand = new Random();
	}
	
	
	/**
     * Constructs a RandomReplacementTournament selection with custom random number generator.
     *
     * @param tournamentSize         the number of individuals in the tournament
     * @param replacementProbability the probability to select a non best individual
     * @param rand                   the random number generator to use
     */
	public RandomRepleacmentTournament(int tournamentSize, double replacementProbability, Random rand) {
		this.tournamentSize = tournamentSize;
		this.replacementProbability = replacementProbability;
        this.rand = rand;
	}
	
	
	
	/**
     * Performs a tournament selection with occasional replacement by weaker individuals.
     * 
     * @param  population the population to select from
     * @return the selected chromosome
     */
	@Override
	public AChromosome<?> select(Population population) {
		
		List<AChromosome<?>> individuals = population.getPopulation();
		
		
		 // Výběr turnajových jedinců
        List<AChromosome<?>> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            tournament.add(individuals.get(rand.nextInt(individuals.size())));
        }

        // Seřadíme turnaj podle fitness (sestupně)
        tournament.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        // Normálně bychom vybrali nejlepšího, ale někdy vybereme druhého či třetího
        if (tournamentSize > 1 && rand.nextDouble() < replacementProbability) {
            int index = rand.nextInt(1, tournamentSize);
            
            AChromosome<?> selected = tournament.get(index);
            
            return checkReturn(selected);
            
        }

       return checkReturn(tournament.get(0));
		
	}

}
