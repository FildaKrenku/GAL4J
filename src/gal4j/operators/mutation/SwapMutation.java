package gal4j.operators.mutation;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * Swap mutaion performs a mutation on a chromosome by swapping two randomly selected genes within the chromosome.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class SwapMutation implements IMutation {
    
	/** Random object for generating random numbers */
    private final Random rand;

    /**
     * Default constructor that initializes the random number generator.
     */
    public SwapMutation() {
        this.rand = new Random();
    }
    
    /**
     * Constructor that allows a custom Random object to be passed in.
     * 
     * @param rand A Random object to be used for generating random numbers.
     */
    public SwapMutation(Random rand) {
        this.rand = rand;
    }

    
    /**
     * Mutates the given chromosome by swapping two randomly selected genes.
     * 
     * @param  chromosome The chromosome to be mutated.
     * @return The mutated chromosome with two genes swapped.
     */
    @Override
    public AChromosome<?> mutate(AChromosome<?> chromosome) {
        int length = chromosome.getGenLength();
        if (length < 2) {
            return chromosome;  
        }

        // Select two distinct random indices
        int index1 = rand.nextInt(length);
        int index2;
        do {
            index2 = rand.nextInt(length);
        } while (index1 == index2);

        // Swap the genes
        Object gene1 = chromosome.getGen(index1);
        Object gene2 = chromosome.getGen(index2);

        chromosome.setGen(index1, gene2);
        chromosome.setGen(index2, gene1);

        return chromosome;
    }
}
