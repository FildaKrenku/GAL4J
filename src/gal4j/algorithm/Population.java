package gal4j.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gal4j.chromosome.AChromosome;
import gal4j.multithread.Master;

/**
 * Represents a population of chromosomes in a genetic algorithm.
 * 
 * This class manages a list of chromosomes, tracks the generation number,
 * and provides utility methods for fitness evaluation, sorting, and statistics.
 * It supports both sequential and parallel fitness evaluation.
 * 
 * @author Filip Křenek
 */
public class Population {
	
	/** List of chromosomes representing the population */
    private List<AChromosome<?>> chromosomes;

    /** Generation number to which this population belongs */
    private final int generation;

    /** Whether the population is sorted by fitness (descending) */
    private boolean sorted = false;
	
	
	/** 
	 * Constructs an initial population (generation 0)
	 */
	public Population() {
		this.chromosomes = new ArrayList<>();
		this.generation = 0;
	}
	
	
	/**
     * Constructs a population for a specific generation.
     * @param generation the generation number
     */
	public Population(int generation) {
		this.chromosomes = new ArrayList<>();
		this.generation = generation;
	}
	
	
	/**
	 * @return the generation number of the population¨
	 */
	public int getGeneration() {
		return generation;
	}

	
	/**
     * Adds a chromosome to the population.
     * @param chromosome the chromosome to add
     */
	public void addChromosome(AChromosome<?> chromosome) {
		chromosomes.add(chromosome);
	}
	
	/**
     * Returns the chromosome at the specified index.
     * @param index the position of the chromosome
     * @return the chromosome at the index
     */
	public AChromosome<?> getChromosome(int index) {
		return chromosomes.get(index);
	}
	
	/** 
	 * @return the list of chromosomes in this population
	 */
	public List<AChromosome<?>> getPopulation() {
		return chromosomes;
	}
	
	
	/**
     * Returns the chromosome with the highest fitness.
     * Automatically sorts the population if not already sorted.
     * @return the best chromosome
     */
	public AChromosome<?> getBestChromosome() {
		if(sorted == false) {
			sortPopulation();
		}
		return chromosomes.get(0);
	}
	
	/**
     * Returns the chromosome with the lowest fitness.
     * Automatically sorts the population if not already sorted.
     * @return the worst chromosome by fitness
     */
	public AChromosome<?> getWorstChromosome() {
		if(sorted == false) {
			sortPopulation();
		}
		return chromosomes.get(getPopulationSize()-1);
	}
		
	
	
	/** 
	 * Sorts the population by descending fitness 
	 */
	public void sortPopulation() {
        Collections.sort(chromosomes, Comparator.comparingDouble(AChromosome<?>::getFitness).reversed());
        sorted = true;
	}
	
	/** 
	 * @return the number of chromosomes in the population 
	 */
	public int getPopulationSize() {
		return chromosomes.size();
	}
	
	/** 
	 * @return whether the population is sorted by fitness 
	 */
	public boolean isSorted() {
		return sorted;
	}
		
	
	/**
     * Calculates fitness of each chromosome sequentially.
     * @param fitness the fitness function to use
     */
	public void calculateFitSeq(AFitnessFunction fitness) {
		for(int i = 0; i < chromosomes.size(); i++) {
			double value = fitness.calculateFitness(chromosomes.get(i));
			chromosomes.get(i).setFitness(value);
		}
	}
	
	
	/**
     * Calculates fitness of each chromosome in parallel using multiple threads.
     * @param fitness the fitness function to use
     * @param threads number of threads to use
     */
	public void calculateFitPar(AFitnessFunction fitness, int threads) {
		Master master = new Master(chromosomes, threads, fitness);
		Thread thread = new Thread(master);
		
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Prints all chromosomes and their genes
	 */
	public void printPopulation() {
		System.out.println("Generation: " + generation);
		if(!sorted) {
			sortPopulation();
		}
		for(int i = 0; i < getPopulationSize(); i++) {
			chromosomes.get(i).printGenes();
		}
	}
	
	/** 
	 * Resets parent flag for all chromosomes (typically before selection)
	 */
	public void setParents() {
		for(int i = 0; i < chromosomes.size(); i++) {
			chromosomes.get(i).setParent(false);
		}
	}
	
	/**
     * Checks if any chromosome in the population is the same instance.
     * @return true if duplicates (same instance) exist
     */
	public boolean isSameInstance() {
	    Set<AChromosome<?>> uniqueChromosomes = new HashSet<>();
	    boolean res = false;
	   

	    for (int i = 0; i < chromosomes.size(); i++) {
	        AChromosome<?> chromosome = getChromosome(i);

	        // Check if this chromosome is already in the set
	        if (!uniqueChromosomes.add(chromosome)) {
	            // If `add` returns false, it's a duplicate instance
	            System.out.println("Duplicate instance found at index: " + i);
	            res = true;
	        }
	    }
	    return res;
	}
	
	
    /**
     * Counts how many unique fitness values exist in the population.
     * @return number of distinct fitness values
     */
	public int countUniqueFitnessValues() {
        Set<Double> uniqueFitnessValues = new HashSet<>();

        for (AChromosome<?> chromosome : chromosomes) {
            uniqueFitnessValues.add(chromosome.getFitness());
        }
        	
        return uniqueFitnessValues.size();
    }


}
