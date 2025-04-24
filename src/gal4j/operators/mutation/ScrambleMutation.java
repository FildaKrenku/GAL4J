package gal4j.operators.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import gal4j.chromosome.AChromosome;

/**
 * A mutation operator that applies a scrambling mutation to a selected portion of a chromosome.
 * The genes in a randomly selected sub-section of the chromosome are scrambled (shuffled).
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class ScrambleMutation implements IMutation {
	
	
	
	/** Random number generator */
	private final Random rand;
	
	/**
     * Default constructor that uses a new Random instance.
     */
	public ScrambleMutation() {
		this.rand = new Random();
	}
	
	/**
     * Constructor that allows for a custom Random instance.
     *
     * @param rand the Random instance to use for testing
     */
	public ScrambleMutation(Random rand) {
		this.rand =	rand;
	}
	
	
	/**
     * Mutates a chromosome by scrambling a randomly chosen subsection of genes.
     * 
     * @param chromosome the chromosome to mutate
     * @return the mutated chromosome
     */
	@Override
	public AChromosome<?> mutate(AChromosome<?> chromosome) {
		int length = chromosome.getGenLength();
		    if (length < 2) return chromosome; 
		
	
		    
		int start = rand.nextInt(length);
		int end = rand.nextInt(start, length);
		while(start == end ) {
			end = rand.nextInt(start, length);
		}



		List<Object> subGenes = new ArrayList<>();
		for (int i = start; i <= end; i++) {
		    subGenes.add(chromosome.getGen(i));
		}

		if (subGenes.size() == 2) {
			Collections.shuffle(subGenes);
			if (subGenes.get(0).equals(chromosome.getGen(start)) && subGenes.get(1).equals(chromosome.getGen(end))) {
				Collections.swap(subGenes, 0, 1);  // Zamění pořadí
			}
		} else {
			Collections.shuffle(subGenes);
		}
		
		
	    for (int i = start; i <= end; i++) {
	        chromosome.setGen(i, subGenes.get(i - start));
	    }
	  
	
	    return chromosome;
	}
}


