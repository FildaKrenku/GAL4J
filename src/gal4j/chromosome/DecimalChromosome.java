package gal4j.chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * An implementation of a chromosome with decimal integer genes.
 * 
 * This chromosome can be initialized in two ways:
 * 1. With potentially repeated numbers (randomly chosen in range [0, genLength))
 * 2. With unique numbers only once (permutation of numbers from 0 to genLength - 1)
 *
 *@author Filip Křenek
 *@version 1.0
 */
public class DecimalChromosome extends AChromosome<Integer> {
    
    /** The array of integer genes */
    private Integer[] genes;

    /** Indicates whether each number should appear only once */
    private boolean numberOnlyOnce;

    /** Random number generator used for initialization and mutation */
    private Random rand = new Random();

    /**
     * Constructs a decimal chromosome with a given length and uniqueness setting.
     * 
     * @param genLength Length of the chromosome (number of genes).
     * @param numberOnlyOnce If true, each number from 0 to genLength-1 will appear exactly once in random order.
     */
    public DecimalChromosome(int genLength, boolean numberOnlyOnce) {
        this.numberOnlyOnce = numberOnlyOnce;
        genes = new Integer[genLength];
        setParent(false);
    }

    /**
     * Constructs a decimal chromosome with a given length, uniqueness setting, and custom Random instance.
     * 
     * @param genLength Length of the chromosome (number of genes).
     * @param numberOnlyOnce If true, each number from 0 to genLength-1 will appear exactly once in random order.
     * @param rand A custom Random instance for testing purposes
     */
    public DecimalChromosome(int genLength, boolean numberOnlyOnce, Random rand) {
        this.numberOnlyOnce = numberOnlyOnce;
        genes = new Integer[genLength];
        setParent(false);
        this.rand = rand;
    }

    /**
     * Initializes the chromosome either as a permutation or with random integers.
     */
    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            if (numberOnlyOnce) {
                genes[i] = i;
            } else {
                genes[i] = rand.nextInt(getGenLength());
            }
        }

        if (numberOnlyOnce) {
            // Shuffle to make it a random permutation
            for (int i = genes.length - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                int temp = genes[i];
                genes[i] = genes[j];
                genes[j] = temp;
            }
        }
    }

    /**
     * @return An array containing all the genes.
     */
    @Override
    public Integer[] getGenes() {
        return genes;
    }

    /**
     * @return The length of the gene array.
     */
    @Override
    public int getGenLength() {
        return genes.length;
    }

    /**
     * Returns the gene at the given position.
     * 
     * @param position The index of the gene to retrieve.
     * @return The value of the gene at the given index.
     */
    @Override
    public Integer getGen(int position) {
        return genes[position];
    }

    /**
     * Sets the gene at the given position.
     * 
     * @param position The index at which the gene should be set.
     * @param gen The new value for the gene.
     */
    @Override
    public void setGen(int position, Object gen) {
        genes[position] = (Integer) gen;
    }

    /**
     * Creates a copy of this chromosome with the same genes and resets the parent flag.
     * 
     * @return A cloned instance of DecimalChromosome.
     */
    @Override
    public AChromosome<Integer> clone() {
        DecimalChromosome copy = new DecimalChromosome(getGenLength(), numberOnlyOnce);
        System.arraycopy(this.genes, 0, copy.genes, 0, genes.length);
        copy.setParent(false);
        return copy;
    }
    
    /**
     * Resets the chromosome by clearing fitness and genes, and marking it as non parent.
     */
    @Override
    public void clean() {
        setFitness(0);
        setParent(false);
        Arrays.fill(this.genes, null);
    }

    /**
     * Mutates the gene at the given point to a new, different value within the valid range.
     * 
     * @param mutationPoint Index of the gene to mutate.
     */
    @Override
    public void mutateGen(int mutationPoint) {
        int gen = rand.nextInt(getGenLength());
        while (getGen(mutationPoint).equals(gen)) {
            gen = rand.nextInt(getGenLength());
        }
        setGen(mutationPoint, gen);
    }

    /**
     * Prints the genes and fitness value to standard output.
     * Output format: Genes: [1 | 4 | 0 | ...] ; FIT: fitness
     */
    @Override
    public void printGenes() {
        System.out.print("Genes: [");
        for (int i = 0; i < genes.length; i++) {
            System.out.print((i == genes.length - 1) ? genes[i] : genes[i] + " | ");
        }
        System.out.println("] ; FIT: " + getFitness());
    }
}
