package gal4j.chromosome;

import java.util.Arrays;
import java.util.Random;

/**
 * An implementation of a chromosome with floating-point genes.
 * 
 * Each gene is a double value randomly initialized in the range [min, max],
 * rounded to two decimal places.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class DoubleChromosome extends AChromosome<Double> {
    
    /** The array of double genes */
    private Double[] genes;

    /** Minimum value for each gene */
    private double min;
    /** Maximum value for each gene */
    private double max;

    /** Random number generator used for initialization and mutation */
    private Random rand = new Random();

    /**
     * Constructs a DoubleChromosome with the given gene length and range.
     * 
     * @param genLength Number of genes in the chromosome.
     * @param min Minimum possible value of a gene.
     * @param max Maximum possible value of a gene.
     */
    public DoubleChromosome(int genLength, double min, double max) {
        genes = new Double[genLength];
        setParent(false);
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a DoubleChromosome with the given gene length, range, and random generator.
     * 
     * @param genLength Number of genes in the chromosome.
     * @param min Minimum possible value of a gene.
     * @param max Maximum possible value of a gene.
     * @param rand Custom Random instance for testing purposes
     */
    public DoubleChromosome(int genLength, double min, double max, Random rand) {
        genes = new Double[genLength];
        setParent(false);
        this.min = min;
        this.max = max;
        this.rand = rand;
    }

    /**
     * Initializes the chromosome by generating random values within [min, max] and rounding them.
     */
    @Override
    public void initialize() {
        for (int i = 0; i < genes.length; i++) {
            double gen = min + (max - min) * rand.nextDouble();
            genes[i] = round(gen);
        }
    }

    /**
     * @return Array containing all genes.
     */
    @Override
    public Double[] getGenes() {
        return genes;
    }

    /**
     * @return The number of genes in the chromosome.
     */
    @Override
    public int getGenLength() {
        return genes.length;
    }

    /**
     * Returns the gene at the specified index.
     * 
     * @param position The index of the gene.
     * @return The value of the gene at the given index.
     */
    @Override
    public Double getGen(int position) {
        return genes[position];
    }

    /**
     * Sets the value of a gene at a specific index.
     * 
     * @param position Index of the gene.
     * @param gen New value to assign.
     */
    @Override
    public void setGen(int position, Object gen) {
        genes[position] = (Double) gen;
    }

    /**
     * Creates a copy of this chromosome with the same genes and resets the parent flag.
     * 
     * @return A cloned instance of DoubleChromosome.
     */
    @Override
    public AChromosome<Double> clone() {
        DoubleChromosome copy = new DoubleChromosome(getGenLength(), this.min, this.max);
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
     * Mutates the gene at the specified index by replacing it with a new random value different from the current one.
     * 
     * @param mutationPoint Index of the gene to mutate.
     */
    @Override
    public void mutateGen(int mutationPoint) {
        double gen = min + (max - min) * rand.nextDouble();
        while (getGen(mutationPoint).equals(gen)) {
            gen = min + (max - min) * rand.nextDouble();
        }
        setGen(mutationPoint, round(gen));
    }

    /**
     * Prints the genes and fitness value to standard output.
     * Output format: Genes: [1.23 | 4.56 | 7.89 | ...] ; FIT: fitness
     */
    @Override
    public void printGenes() {
        System.out.print("Genes: [");
        for (int i = 0; i < genes.length; i++) {
            System.out.print((i == genes.length - 1) ? genes[i] : genes[i] + " | ");
        }
        System.out.println("] ; FIT: " + getFitness());
    }

    /**
     * Rounds a floating-point number to two decimal places.
     * 
     * @param value The number to round.
     * @return Rounded number.
     */
    public double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
