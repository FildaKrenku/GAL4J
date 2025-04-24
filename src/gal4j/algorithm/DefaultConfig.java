package gal4j.algorithm;

/**
 * Default configuration for the genetic algorithm.
 * 
 * This class extends {@link Config} and sets commonly used default values.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class DefaultConfig extends Config {
	
	public DefaultConfig() {
		setPopulationSize(10000);
		setGenetarions(100);
		setGenLength(16);
		setMutationRate(0.01);
		setSelectionRate(0.2);
		setMultithread(true);
		setThreads(4);
		
	}
}
