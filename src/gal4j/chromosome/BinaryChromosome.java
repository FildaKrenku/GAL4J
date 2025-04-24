package gal4j.chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * An implementation of a binary chromosome where each gene is an Integer with a value of 0 or 1.
 * This chromosome is commonly used for problems with binary representations in genetic algorithms.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class BinaryChromosome extends AChromosome<Integer> {
	
	
	
    /** Array of genes, each representing either 0 or 1 */
    private Integer[] genes;
    
    /** Random number generator used for initialization and mutation */
    private Random rand = new Random();

    /**
     * Constructor that creates a binary chromosome of a specified length.
     * Uses default random generator.
     *
     * @param genLength Length of the chromosome (number of genes).
     */
    public BinaryChromosome(int genLength) {
        genes = new Integer[genLength];
        setParent(false);
    }

    /**
     * Constructor that creates a binary chromosome of a specified length using a custom Random instance.
     *
     * @param genLength Length of the chromosome (number of genes).
     * @param rand Custom Random instance for testing purposes
     */
    public BinaryChromosome(int genLength, Random rand) {
        genes = new Integer[genLength];
        setParent(false);
        this.rand = rand;
    }

    /**
     * Initializes the chromosome with random binary values.
     */
    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = rand.nextInt(0, 2); // Generates 0 or 1
        }
    }

    /**
     * Returns the array of binary genes.
     * 
     * @return An array of Integer genes.
     */
    @Override
    public Integer[] getGenes() {
        return genes;
    }

    /**
     * Returns the number of genes in the chromosome.
     * 
     * @return The length of the gene array.
     */
    @Override
    public int getGenLength() {
        return genes.length;
    }

    /**
     * Returns the gene at the specified index.
     * 
     * @param position The index of the gene to retrieve.
     * @return The gene value at the given index.
     */
    @Override
    public Integer getGen(int position) {
        return genes[position];
    }

    /**
     * Sets the gene at the specified index to a new value.
     * 
     * @param position The index at which the gene will be set.
     * @param gen The new gene value.
     */
    @Override
    public void setGen(int position, Object gen) {
        genes[position] = (Integer) gen;
    }

    /**
     * Creates a copy of the chromosome with the same genes and resets the parent flag.
     * 
     * @return A cloned instance of the BinaryChromosome.
     */
    @Override
    public AChromosome<Integer> clone() {
        BinaryChromosome copy = new BinaryChromosome(getGenLength());
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
     * Mutates the gene at a specific point by flipping its value (0 becomes 1 and 1 becomes 0).
     * 
     * @param mutationPoint Index of the gene to mutate.
     */
    @Override
    public void mutateGen(int mutationPoint) {
        int gen = getGen(mutationPoint);
        gen = (gen == 0) ? 1 : 0;
        setGen(mutationPoint, gen);
    }

    /**
     * Prints the genes in the chromosome and its fitness value.
     * Output format: Genes: [0 | 1 | 0 | ...] ; FIT: fitness
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
