package gal4j.operators.crossover;

import gal4j.chromosome.AChromosome;

/**
 * An implementation of a noise reduction crossover operator.
 * 
 * This crossover averages the genes from both parents to reduce random noise.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class NoiseReductionCrossOver implements ICrossover {
	
	/**
     * Performs the noise reduction crossover between two parent chromosomes.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new chromosome containing averaged gene values
     */
	@Override
    public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
        AChromosome<?> crossed = parent1.clone();

        for (int i = 0; i < parent1.getGenLength(); i++) {
            Object gene1 = parent1.getGen(i);
            Object gene2 = parent2.getGen(i);
            Object newGene = averageGenes(gene1, gene2);
            crossed.setGen(i, newGene);
        }

        return crossed;
    }

	/**
     * Calculates the average of two genes, if they are of a supported type.
     * Supported types are Integer, Double and Character
     *
     * @param  gene1 the first gene
     * @param  gene2 the second gene
     * @return the average value of the two genes
     */
	private Object averageGenes(Object gene1, Object gene2) {
        if (gene1 instanceof Integer && gene2 instanceof Integer) {
            return ((Integer) gene1 + (Integer) gene2) / 2;
        } else if (gene1 instanceof Double && gene2 instanceof Double) {
            return (((Double) gene1) + ((Double) gene2)) / 2.0;
        } else if (gene1 instanceof Character && gene2 instanceof Character) {
            return (char) ((((Character) gene1) + ((Character) gene2)) / 2);
        } else {
            throw new IllegalArgumentException("Unsupported gene types or mismatched types: " 
                + gene1.getClass() + " & " + gene2.getClass());
        }
    }
	
	
	
	

}
