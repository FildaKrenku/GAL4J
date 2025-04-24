package gal4j.chromosome;


/**
 * Abstract class representing a chromosome in the genetic algorithm. A chromosome holds a set of genes
 * and provides methods for gene manipulation, fitness evaluation, and mutation.
 * The class is designed to be extended for specific types of chromosomes with different gene types.
 * 
 * @param <T> The type of the genes. This is a generic type parameter that can represent any data type for the genes.
 * @author Filip Křenek
 * @version 1.0
 */
public abstract class AChromosome<T> {
	
	/** The fitness value of the chromosome */
    private double fitness;
    
    /** Flag indicating if the chromosome is selected as a parent for reproduction */
    private boolean isParent;
	

	
    /**
     * Initializes the chromosome by setting its genes to appropriate values.
     * This method must be implemented by subclasses to define how the genes are initialized.
     */
    public abstract void initialize();
    
    /**
     * Returns an array representing the genes of the chromosome.
     * 
     * @return The array of genes.
     */
    public abstract T[] getGenes();  

    /**
     * Returns the length of the chromosome (i.e., the number of genes).
     * 
     * @return The length of the chromosome.
     */
    public abstract int getGenLength();
    
    /**
     * Gets the gene at a specific position in the chromosome.
     * 
     * @param position The position of the gene.
     * @return The gene at the specified position.
     */
    public abstract T getGen(int position);
    
    /**
     * Sets the gene at a specific position in the chromosome.
     * 
     * @param position The position where the gene will be set.
     * @param gene The new gene value to be set at the specified position.
     */
    public abstract void setGen(int position, Object gene);
    
    /**
     * Creates a clone of the chromosome, producing a new instance with the same genes.
     * 
     * @return A new cloned chromosome.
     */
    public abstract AChromosome<T> clone();
    
    /**
     * Cleans or resets the chromosome, usually by clearing or resetting its genes.
     * This method can be used to prepare the chromosome for a new generation.
     */
    public abstract void clean();
    
    /**
     * Mutates the gene at a specific mutation point, modifying its value.
     * 
     * @param mutationPoint The position of the gene to mutate.
     */
    public abstract void mutateGen(int mutationPoint);
    
    /**
     * Prints the genes of the chromosome, typically for debugging or visualization purposes.
     */
    public abstract void printGenes();

    
    /**
     * Gets the fitness of the chromosome.
     * 
     * @return The fitness value of the chromosome.
     */
    public double getFitness() {
        return fitness;
    }

    
    /**
     * Sets the fitness of the chromosome.
     * 
     * @param fitness The new fitness value to be set.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    /**
     * Checks if the chromosome is a parent.
     * 
     * @return True if the chromosome is a parent, otherwise false.
     */
    public boolean isParent() {
        return isParent;
    }


    /**
     * Sets the parent status of the chromosome.
     * 
     * @param isParent True if the chromosome is a parent, otherwise false.
     */
    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }
	

}
