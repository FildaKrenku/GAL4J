package examples;




import gal4j.algorithm.AFitnessFunction;
import gal4j.algorithm.Config;
import gal4j.algorithm.Engine;
import gal4j.algorithm.Population;
import gal4j.chromosome.AChromosome;
import gal4j.chromosome.DecimalChromosome;
import gal4j.operators.crossover.CyclicCrossOver;
import gal4j.operators.crossover.ICrossover;
import gal4j.operators.crossover.OrderCrossOver;
import gal4j.operators.crossover.SinglePointCrossOver;
import gal4j.operators.mutation.IMutation;
import gal4j.operators.mutation.SinglePointMutation;
import gal4j.operators.mutation.SwapMutation;
import gal4j.operators.selection.ASelection;
import gal4j.operators.selection.TournamentSelection;


public class example1 {

	public static void main(String[] args) {
		// create a configuration
		Config conf = new Config();
		conf.setPopulationSize(1000);
		conf.setGenetarions(20);
		conf.setGenLength(10);
		conf.setMutationRate(0.1);
		conf.setSelectionRate(0.2);
		
		
		
		// area for graph
		Area a = new Area();
		
		// create objects for engine
		Population pop = new Population();
		
		AFitnessFunction func = new TSPFunc(a.towns);
		
		AChromosome<Integer> def = new DecimalChromosome(conf.getGenLength(), true);
 		
		Engine engine = new Engine(conf, pop, func, def);
		
		// create genetic operators
		ASelection selectOp = new TournamentSelection(3);
		ICrossover crossOp = new OrderCrossOver();
		IMutation mutationOp = new SwapMutation();
		
		
		
		
		// set genetic operators to engine
		engine.setSelectionOperator(selectOp); 
		engine.setCrossoverOperatorPR(crossOp);
		engine.setMutationOperatorPR(mutationOp);
		
	
		// initialize population
		engine.initializePopulation();
		
		// start algorithm
		long start = System.currentTimeMillis();
		engine.run();
		long end = System.currentTimeMillis();
		
		
		// print results
		System.out.println("-----------------------------");
		
		engine.getPopulation().printPopulation();
		
		System.out.println("-----------------------------");
		
		System.out.println(engine.getPopulation().getPopulationSize());
		
		if(!engine.getPopulation().isSameInstance()) {
			System.out.println("Check complete. No duplicates found.");
			System.out.println("Generation: " + engine.getPopulation().getGeneration());
		}
		
		
		System.out.println("-----------------------------");
		
		System.out.println("best");
		engine.getPopulation().getBestChromosome().printGenes();
		
		System.out.println("Execution time: " + (end - start) + " ms");
	
		
		
	}

	

}
