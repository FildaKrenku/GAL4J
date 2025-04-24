package gal4j.operators.mutation;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * A mutation operator that applies mutation with a probability that decreases over generations.
 * The mutation probability decreases non linearly as the number of generations increases.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class NonUniformMutation implements IMutation {
	
	/** Controls the strength of the mutation */
    private final double b;
    /** Maximum number of generations */
    private final int maxGenerations;
    /** Current generation counter */
    private int currentGeneration;
    /** Random number generator */
    private final Random rand;

    /**
     * Constructs a NonUniformMutation with given mutation strength and max generations.
     *
     * @param b the mutation strength (exponent controlling how fast the probability decreases)
     * @param maxGenerations the total number of generations for which mutation probability will decrease
     */
    public NonUniformMutation(double b, int maxGenerations) {
        this.b = b;
        this.maxGenerations = maxGenerations;
        this.currentGeneration = 0;
        this.rand = new Random();
    }
    
    /**
     * Creates a NonUniformMutation with a custom random generator.
     *
     * @param b the mutation strength (exponent controlling how fast the probability decreases)
     * @param maxGenerations the total number of generations for which mutation probability will decrease
     * @param rand the Random instance to use
     */
    public NonUniformMutation(double b, int maxGenerations, Random rand) {
        this.b = b;
        this.maxGenerations = maxGenerations;
        this.currentGeneration = 0;
        this.rand = rand;
    }
    
    
    /**
     * Mutates a chromosome based on the nonuniform mutation probability.
     *
     * @param  chromosome the chromosome to mutate
     * @return the mutated chromosome 
     */
    @Override
    public AChromosome<?> mutate(AChromosome<?> chromosome) {
        AChromosome<?> mutated = chromosome.clone();

        for (int i = 0; i < chromosome.getGenLength(); i++) {
        	double mutationProb = mutationProbability();
            if (rand.nextDouble() < mutationProb) { 
                mutated.mutateGen(i);
            }
        }

        return mutated;
    }

    /**
     * Calculates the mutation probability based on the current generation and total number of generations.
     * The probability decreases as the number of generations increases.
     *
     * @return the mutation probability for the current generation
     */
    public double mutationProbability() {
        return Math.pow(1.0 - (double) currentGeneration / maxGenerations, b);
    }

    /**
     * Advances to the next generation and updates the current generation counter.
     */
    public void nextGeneration() {
        if (currentGeneration < maxGenerations) {
            currentGeneration++;
        }
    }
}
