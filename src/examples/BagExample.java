package examples;

import gal4j.algorithm.AFitnessFunction;
import gal4j.algorithm.Config;
import gal4j.algorithm.Engine;
import gal4j.algorithm.Population;
import gal4j.chromosome.BinaryChromosome;
import gal4j.chromosome.AChromosome;
import gal4j.operators.crossover.ICrossover;
import gal4j.operators.crossover.SinglePointCrossOver;
import gal4j.operators.mutation.IMutation;
import gal4j.operators.mutation.SinglePointMutation;
import gal4j.operators.selection.ASelection;
import gal4j.operators.selection.TournamentSelection;

public class BagExample {
    public static void main(String[] args) {
    	// create config
        Config conf = new Config();
        conf.setPopulationSize(1000);
        conf.setGenetarions(100);
        conf.setGenLength(15);
        conf.setMutationRate(0.01);
        conf.setSelectionRate(0.2);
        
        
                    
        // bag for items
        Bag bag = new Bag(58);
        
        // create objects for engine
        Population pop = new Population();
        AFitnessFunction func = new BagFunc(bag.items, bag.maxWeight);
        AChromosome<Integer> def = new BinaryChromosome(conf.getGenLength());
        
        
        Engine engine = new Engine(conf, pop, func, def); 
        
        // create genetic operators
        ASelection selectOp = new TournamentSelection(3);
        ICrossover crossOp = new SinglePointCrossOver();
        IMutation mutationOp = new SinglePointMutation();
        
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

        //print results
        System.out.println("Best solution:");
        engine.getPopulation().getBestChromosome().printGenes(); 
        System.out.println("Execution time: " + (end - start) + " ms");
        System.out.println("-----------------------------");
        bag.print(engine.getPopulation().getBestChromosome());
    }
}

