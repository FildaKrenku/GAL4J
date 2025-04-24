package gal4j.operators.crossover;

import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * Implementation of the Cyclic Crossover (CX) operator.
 *
 * This crossover preserves the relative position of genes by identifying cycles between the two parents and alternating them in the offspring.
 */
public class CyclicCrossOver implements ICrossover {

	/** Random number generator for chosing gene indexes */
    private final Random rand;

    /**
     * Constructs a CyclicCrossOver with a new random number generator.
     */
    public CyclicCrossOver() {
        this.rand = new Random();
    }
    
    /**
     * Constructs a CyclicCrossOver with the specified random number generator for testing purposes.
     *
     * @param rand the random number generator to use
     */
    public CyclicCrossOver(Random rand) {
        this.rand = rand;
    }

    
    /**
     * Performs cyclic crossover between two parent chromosomes.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return an offspring chromosome created by applying cyclic crossover
     */
    @Override
    public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
        int length = parent1.getGenLength();

        // Initialize offspring genes with null values
        Object[] offspringGenes = new Object[length];
        boolean[] visited = new boolean[length];  // Track visited indices

        int cycleCount = 0;

        // Identify cycles and assign genes alternately
        for (int startIndex = 0; startIndex < length; startIndex++) {
            if (!visited[startIndex]) {
                int index = startIndex;
                boolean takeFromParent1 = (cycleCount % 2 == 0);  // Alternate cycles
                
                do {
                    // Assign gene from appropriate parent
                    offspringGenes[index] = takeFromParent1 ? parent1.getGen(index) : parent2.getGen(index);
                    visited[index] = true;

                    // Find where this gene appears in Parent 1
                    Object geneFromP2 = parent2.getGen(index);
                    index = indexOfGene(parent1, geneFromP2);

                } while (index != startIndex && !visited[index]);

                cycleCount++;  // Move to the next cycle
            }
        }

        // Create the offspring chromosome
        AChromosome<?> offspring = parent1.clone();
        for (int i = 0; i < length; i++) {
            offspring.setGen(i, offspringGenes[i]);
        }
        
        return offspring;
    }


    /**
     * Helper method to find the index of a gene in a chromosome.
     *
     * @param  parent the chromosome to search in
     * @param  gene the gene to locate
     * @return index of the gene in the chromosome
     * @throws IllegalStateException if the gene is not found
     */
    public int indexOfGene(AChromosome<?> parent, Object gene) {
        for (int i = 0; i < parent.getGenLength(); i++) {
            if (parent.getGen(i).equals(gene)) {
                return i;
            }
        }
        throw new IllegalStateException("Gene not found in parent chromosome!");
    }
}
