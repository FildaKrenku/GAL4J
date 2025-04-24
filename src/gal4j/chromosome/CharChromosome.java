package gal4j.chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * An implementation of a chromosome where each gene is a lowercase letter.
 * 
 * The chromosome can be initialized with random characters and supports basic mutation and cloning operations.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class CharChromosome extends AChromosome<Character> {

    /** Array representing character genes */
    private Character[] genes;

    /** Random generator for gene initialization and mutation */
    private Random rand = new Random();

    /**
     * Constructs a CharChromosome with the specified gene length.
     * 
     * @param length Number of genes in the chromosome.
     */
    public CharChromosome(int length) {
        this.genes = new Character[length];
        setParent(false);
    }

    /**
     * Constructs a CharChromosome with the specified gene length and a given random generator.
     * 
     * @param length Number of genes in the chromosome.
     * @param rand Custom Random instance for testing purposes
     */
    public CharChromosome(int length, Random rand) {
        this.genes = new Character[length];
        setParent(false);
        this.rand = rand;
    }

    /**
     * Initializes the chromosome with random lowercase characters from 'a' to 'z'.
     */
    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (char) ('a' + rand.nextInt(26));
        }
    }

    /**
     * @return Array of character genes.
     */
    @Override
    public Character[] getGenes() {
        return genes;
    }

    /**
     * @return Length of the chromosome.
     */
    @Override
    public int getGenLength() {
        return genes.length;
    }

    /**
     * Returns the gene at the specified position.
     * 
     * @param position Index of the gene to return.
     * @return Character gene at the given position.
     */
    @Override 
    public Character getGen(int position) {
        return genes[position];
    }

    /**
     * Sets the gene at the specified position.
     * 
     * @param position Index of the gene.
     * @param gene New gene value.
     */
    @Override
    public void setGen(int position, Object gene) {
        genes[position] = (Character) gene;
    }

    /**
     * Creates a deep copy of this chromosome with the same genes and resets the parent flag.
     * 
     * @return A new instance of CharChromosome with the same genes.
     */
    @Override
    public AChromosome<Character> clone() {
        CharChromosome copy = new CharChromosome(genes.length);
        System.arraycopy(genes, 0, copy.genes, 0, genes.length);
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
     * Mutates the gene at the given index by replacing it with a different random character.
     * 
     * @param mutationPoint Index of the gene to mutate.
     */
    @Override
    public void mutateGen(int mutationPoint) {
        genes[mutationPoint] = (char) ('a' + rand.nextInt(26));
    }

    /**
     * Prints the gene sequence and the current fitness value to the console.
     * Output format: Genes: [n | c | v | ...] ; FIT: fitness
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
