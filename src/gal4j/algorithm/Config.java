package gal4j.algorithm;

/**
 * Configuration class for genetic algorithm parameters.
 * 
 * This class stores various configuration values used during the 
 * execution of the genetic algorithm, such as population size, mutation 
 * rate, number of generations, and others.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class Config {
	
	/** Number of individuals in each generation */
	private int populationSize = 0;

	/** Total number of generations the algorithm will run */
	private int genetarions = 0;

	/** Number of genes in each chromosome (chromosome length) */
	private int genLength = 0;

	/** Probability (0.0–1.0) of mutating a gene in a chromosome */
	private double mutationRate = 0;

	/** Proportion (0.0–1.0) of the population selected for reproduction */
	private double selectionRate = 0;

	/** Optional: expected fitness value (used as early stopping criterion) */
	private double approximateFinalFit = -1;

	/** Optional: minimal improvement in fitness to continue (used for convergence check) */
	private double delta = 0;

	/** Whether the algorithm should use multithread approach to calculating fitness values */
	private boolean multithread;

	/** Number of threads to be used if multithreading is enabled */
	private int threads = 0;

	/** Probability (0.0–1.0) of using a secondary operator (alternate crossover and mutation) */
	private double secondOperatorRate = 0;

	

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}


	public int getGenetarions() {
		return genetarions;
	}


	
	public void setGenetarions(int genetarions) {
		this.genetarions = genetarions;
	}

	
	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	
	public int getGenLength() {
		return genLength;
	}

	
	public void setGenLength(int genLength) {
		this.genLength = genLength;
	}

	
	public double getSelectionRate() {
		return selectionRate;
	}


	public void setSelectionRate(double selectionRate) {
		this.selectionRate = selectionRate;
	}

	
	public boolean isMultithread() {
		return multithread;
	}


	public void setMultithread(boolean multithread) {
		this.multithread = multithread;
	}

	
	public int getThreads() {
		return threads;
	}


	public void setThreads(int threads) {
		this.threads = threads;
	}


	public double getApproximateFinalFit() {
		return approximateFinalFit;
	}


	public void setApproximateFinalFit(double approximateFinalFit) {
		this.approximateFinalFit = approximateFinalFit;
	}


	public double getDelta() {
		return delta;
	}


	public void setDelta(double delta) {
		this.delta = delta;
	}


	public double getSecondOperatorRate() {
		return secondOperatorRate;
	}


	public void setSecondOperatorRate(double secondOperatorRate) {
		this.secondOperatorRate = secondOperatorRate;
	}
	
	
	
}
