package gal4j.multithread;


import gal4j.algorithm.AFitnessFunction;
import gal4j.chromosome.AChromosome;

/**
 * Worker class that calculates fitness for chromosomes assigned by the Master.
 * Each worker runs in its own thread and processes chromosomes one by one.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class Worker implements Runnable {
	
	/** Reference to the master for requesting chromosomes */
	private Master master;
	/** Fitness function to evaluate chromosomes */
	private AFitnessFunction func;
	/** The currently assigned chromosome to evaluate */
	private AChromosome<?> current;
	
	
	/**
     * Constructs a new Worker object.
     *
     * @param master reference to the master controller
     * @param func   the fitness function used for evaluation
     */
	public Worker(Master master, AFitnessFunction func) {
		this.master = master;
		this.func = func;
	}
	
	
	
	/**
     * The main execution loop of the worker.
     * Continuously fetches chromosomes from the master and evaluates their fitness until there are no more chromosomes left.
     */
	@Override
	public void run() {
		
		while(master.getChromosome(this) != false) {
			double fittness = func.calculateFitness(current);
			current.setFitness(fittness);
		}
		
	}


	/**
     * Assigns a chromosome to be processed by this worker.
     *
     * @param current the chromosome to evaluate
     */
	public void setCurrent(AChromosome<?> current) {
		this.current = current;
		
	}

}
