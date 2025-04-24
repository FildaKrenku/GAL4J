package gal4j.multithread;

import java.util.List;

import gal4j.algorithm.AFitnessFunction;
import gal4j.chromosome.AChromosome;

/**
 * Master class that distributes chromosomes among multiple threads (workers) for parallel fitness evaluation.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class Master implements Runnable {
	
	/** List of chromosomes to evaluate */
	private List<AChromosome<?>> chromosomes;
	/** Number of threads to use */
	private int nthreads;
	/** Fitness function to apply */
	private AFitnessFunction func;
	/** Thread instances */
	private Thread[] threads;
	/** Worker instances */
	private Worker[] workers;
	/** Index of the next chromosome to assign */
	private int cur = 0;
	
	
	/**
     * Constructs a new Master object to control multithreaded fitness computation.
     *
     * @param chromosomes list of chromosomes to evaluate
     * @param nthreads    number of threads to run
     * @param func        fitness function to apply to each chromosome
     */
	public Master(List<AChromosome<?>> chromosomes, int nthreads, AFitnessFunction func) {
		this.chromosomes = chromosomes;
		this.nthreads = nthreads;
		this.func = func;
		threads = new Thread[nthreads];
		workers = new Worker[nthreads];
	}
	
	
	
	/**
     * Starts all worker threads and waits for them to finish.
     */
	@Override
	public void run() {
		// Create and start each worker thread
		for(int i = 0; i < nthreads; i++) {
			workers[i] = new Worker(this, func);
			threads[i] = new Thread(workers[i]);
			threads[i].start();
		}
		
		// Wait for all threads to complete
		for(int i = 0; i < nthreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cur = 0;
		
	}
	
	/**
     * Provides the next chromosome to a worker in a thread-safe manner.
     *
     * @param worker the worker requesting a chromosome
     * @return true if a chromosome was assigned, false if all have been processed
     */
	public synchronized boolean getChromosome(Worker worker) {
		worker.setCurrent(null);
	    while (cur != chromosomes.size()) {
	        worker.setCurrent(chromosomes.get(cur));
	        cur++;
	        return true;
	    }
	    return false;
	}
	
	
	
	

	
	
}
