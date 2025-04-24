package gal4j.operators.crossover;

import gal4j.chromosome.AChromosome;

/**
 * An implementation of an eccentric crossover operator.
 * 
 * This crossover compares the distance of each gene to the average of the two parent genes. 
 * The gene farther from the average is selected. If the distances are equal, the larger gene is chosen.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class EccentricCrossOver implements ICrossover {

	/**
     * Performs the eccentric crossover between two parent chromosomes.
     *
     * @param  parent1 the first parent chromosome
     * @param  parent2 the second parent chromosome
     * @return a new offspring chromosome based on eccentric selection of genes
     */
    @Override
    public AChromosome<?> crossover(AChromosome<?> parent1, AChromosome<?> parent2) {
       
        AChromosome<?> crossed = parent1.clone();

        // Pro každý gen provádíme křížení podle typu genů
        for (int i = 0; i < parent1.getGenLength(); i++) {
            Object gene1 = parent1.getGen(i);
            Object gene2 = parent2.getGen(i);

            // Pokud jsou geny stejného typu, použijeme průměr a rozhodujeme na základě hodnot
            if (gene1.getClass().equals(gene2.getClass())) {
                crossed.setGen(i, averageGenes(gene1, gene2));
            } else {
                throw new IllegalArgumentException("Genes must be of the same type: " 
                        + gene1.getClass() + " & " + gene2.getClass());
            }
        }

        return crossed;
    }

    /**
     * Chooses a gene based on which value is farther from the average (or greater if equal distance).
     *
     * @param  gene1 the gene from parent1
     * @param  gene2 the gene from parent2
     * @return the more "eccentric" gene value
     */
    private Object averageGenes(Object gene1, Object gene2) {
        if (gene1 instanceof Integer && gene2 instanceof Integer) {
            return getExtremeGene((Integer) gene1, (Integer) gene2);
        } else if (gene1 instanceof Double && gene2 instanceof Double) {
            return getExtremeGene((Double) gene1, (Double) gene2);
        } else if (gene1 instanceof Character && gene2 instanceof Character) {
            return getExtremeGene((Character) gene1, (Character) gene2);
        } else {
            throw new IllegalArgumentException("Unsupported gene types or mismatched types: " 
                    + gene1.getClass() + " & " + gene2.getClass());
        }
    }

    /**
     * Chooses a more eccentric integer gen
     * @param  gene1 the gene from parent1
     * @param  gene2 the gene from parent2
     * @return the more "eccentric" gene value
     */
    private Integer getExtremeGene(Integer gene1, Integer gene2) {
        double average = (double) (gene1 + gene2) / 2;
        average = Math.floor(average);
        if (Math.abs(gene1 - average) > Math.abs(gene2 - average)) {
            return gene1;
        } else if (Math.abs(gene1 - average) == Math.abs(gene2 - average)) {
            return gene1 >= gene2 ? gene1 : gene2;
        } else {
            return gene2;
        }
    }

    /**
     * Chooses a more eccentric double gen
     * @param  gene1 the gene from parent1
     * @param  gene2 the gene from parent2
     * @return the more "eccentric" gene value
     */
    private Double getExtremeGene(Double gene1, Double gene2) {
        double average = (gene1 + gene2) / 2;
        if (Math.abs(gene1 - average) > Math.abs(gene2 - average)) {
            return gene1;
        } else if (Math.abs(gene1 - average) == Math.abs(gene2 - average)) {
            return gene1 >= gene2 ? gene1 : gene2;
        } else {
            return gene2;
        }
    }

    /**
     * Chooses a more eccentric char gen
     * @param  gene1 the gene from parent1
     * @param  gene2 the gene from parent2
     * @return the more "eccentric" gene value
     */
    private Character getExtremeGene(Character gene1, Character gene2) {
        char average = (char) ((gene1 + gene2) / 2);
        if (Math.abs(gene1 - average) > Math.abs(gene2 - average)) {
            return gene1;
        } else if (Math.abs(gene1 - average) == Math.abs(gene2 - average)) {
            return gene1 >= gene2 ? gene1 : gene2;
        } else {
            return gene2;
        }
    }
}
