package gal4j.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gal4j.chromosome.AChromosome;
import gal4j.operators.crossover.ICrossover;
import gal4j.operators.mutation.IMutation;
import gal4j.operators.mutation.NonUniformMutation;
import gal4j.operators.selection.ASelection;
import gal4j.utils.Charts;
import gal4j.utils.Checker;

/**
 * Engine is the core class that runs the genetic algorithm process.
 * It manages population initialization, fitness evaluation, selection,
 * crossover, mutation, and termination based on configuration and operators.
 * @author Filip Křenek
 * @version 1.0
 */
public class Engine {

	// Configuration object for the genetic algorithm
	private Config config;

	// Population object that holds all chromosomes in the current generation
	private Population population;

	// Fitness function that evaluates the fitness of chromosomes in the population
	private AFitnessFunction fitness;

	// Default chromosome used to generate new chromosomes during population initialization
	private AChromosome<?> defChromosome;

	// Primary crossover operator used for parent chromosome recombination
	private ICrossover crossoverOperatorPR;

	// Primary mutation operator used for modifying chromosomes
	private IMutation mutationOperatorPR;

	// Selection operator used for selecting chromosomes for reproduction
	private ASelection selectionOperator;

	// Secondary crossover operator used with a certain probability as an alternative to the primary operator
	private ICrossover crossoverOperatorSC;

	// Secondary mutation operator used with a certain probability as an alternative to the primary operator
	private IMutation mutationOperatorSC;

	// Random object used for generating random values in selection, crossover, and mutation processes
	private final Random rand;

	// Utility class to check various configurations and algorithm settings
	private Checker check;

	// List that holds the generations of chromosomes for tracking progress and visualizing evolution
	private List<List<AChromosome<?>>> generations = new ArrayList<>();

	// Flag indicating whether the population has been initialized
	private boolean ready = false;

	/**
	 * Constructs the Engine instance.
	 *
	 * @param config         the configuration object
	 * @param population     the initial population
	 * @param fitness        the fitness function
	 * @param defChromosome  the default chromosome used to generate the population
	 */
	public Engine(Config config, Population population, AFitnessFunction fitness, AChromosome<?> defChromosome) {
		this.config = config;
		this.population = population;
		this.fitness = fitness;
		this.defChromosome = defChromosome;
		this.rand = new Random();
		this.check = new Checker();
		
		check.checkEngine(this);
		check.checkConfig(config);
	}

	/**
	 * Initializes the population using the default chromosome template.
	 */
	public void initializePopulation() {
		for (int i = 0; i < config.getPopulationSize(); i++) {
			AChromosome<?> chromosome = defChromosome.clone();
			chromosome.initialize();
			population.addChromosome(chromosome);
		}
		ready = true;
	}

	/**
	 * Executes the genetic algorithm process until an end condition is met
	 * or the maximum number of generations is reached.
	 */
	public void run() {
		if (!ready) {
			// if population is not initialized, intialize it 
			initializePopulation();
		}

		check.checkOperators(this, config);
		
		
		for (int i = 0; i < config.getGenetarions(); i++) {
			
			// calculate fitenss values of every individual
			if (config.isMultithread()) {
				population.calculateFitPar(fitness, config.getThreads());
			} else {
				population.calculateFitSeq(fitness);
			}

			generations.add(population.getPopulation());

			// check for end condition
			if (checkEndConditions()) {
				System.out.println("End condition is fullfiled");
				System.out.println("Algorithm is terminating on iteration " + population.getGeneration());
				break;
			}
			
			// create a new population
			Population newPopulation = new Population(i + 1);
			
			// perform selection process
			int parentCount = (int) (population.getPopulationSize() * config.getSelectionRate());
			selection(population, newPopulation, parentCount);
			selectionOperator.reset();
			
			// create new individuals by crossovers
			crossover(population, newPopulation, parentCount);
			
			// apply mutation
			mutation(newPopulation);

			newPopulation.setParents();
			population = newPopulation;
			
			// cheack if there are no same instances in the new population
			if (population.isSameInstance()) {
				System.out.println("-----------------------------");
				System.out.println("Same instances of one chromosome are in one population");
				System.out.println("Algorithm is terminating on iteration " + population.getGeneration());
				System.out.println("-----------------------------");
				break;
			}
		}

		if (config.isMultithread()) {
			population.calculateFitPar(fitness, config.getThreads());
		} else {
			population.calculateFitSeq(fitness);
		}
	}

	/**
	 * Checks whether the end conditions for the algorithm have been met.
	 *
	 * @return true if the best fitness is within an acceptable range of the target, false otherwise
	 */
	public boolean checkEndConditions() {
		double fitP = config.getApproximateFinalFit() + config.getDelta();
		double fitN = config.getApproximateFinalFit() - config.getDelta();
		double best = population.getBestChromosome().getFitness();

		boolean res = (fitN < best && best < fitP) || best == fitN || best == fitP;

		if (res) {
			System.out.println("best: " + best + ", AFF: " + fitP + ", " + fitN);
		}

		return res;
	}

	/**
	 * Displays various types of charts based on the evolution of the population.
	 *
	 * @param mode the type of chart to display ("Histogram", "AverageVsMax", "FitnessConvergence")
	 */
	public void makeGraph(String mode) {
		Charts graph = new Charts();

		switch (mode) {
			case "Histogram":
				graph.showFitnessBarChart(population.getPopulation());
				break;
			case "AverageVsMax":
				graph.showAverageVsMaxFitness(generations);
				break;
			case "FitnessConvergence":
				graph.showFitnessConvergenceGraph(generations);
				break;
		}
	}

	
	/**
	 * Selects parent chromosomes for reproduction.
	 * 
	 * @param population 	population where are chromosomes selected
	 * @param newPopulation population to which are selected chromosomes added
	 * @param parentCount	number of chromosome to be selected
	 */
	public void selection(Population population, Population newPopulation, int parentCount) {
		for (int j = 0; j < parentCount; j++) {
			AChromosome<?> selected = selectionOperator.select(population);
			selected.setParent(true);
			newPopulation.addChromosome(selected);
		}
	}

	
	/**
	 * Applies crossover to generate offspring from selected parents.
	 */
	public void crossover(Population population, Population newPopulation, int parentCount) {
		for (int j = parentCount; j < population.getPopulationSize(); j++) {
			AChromosome<?> parent1 = newPopulation.getChromosome(rand.nextInt(newPopulation.getPopulationSize()));
			AChromosome<?> parent2 = newPopulation.getChromosome(rand.nextInt(newPopulation.getPopulationSize()));

			if (crossoverOperatorSC != null && Math.random() <= config.getSecondOperatorRate()) {
				newPopulation.addChromosome(crossoverOperatorSC.crossover(parent1, parent2));
			} else {
				newPopulation.addChromosome(crossoverOperatorPR.crossover(parent1, parent2));
			}
		}
	}

	/**
	 * Applies mutation to the chromosomes in the new population.
	 * 
	 * @param newPopulation population to be mutated
	 */
	public void mutation(Population newPopulation) {
		for (int j = 0; j < newPopulation.getPopulationSize(); j++) {
			if (Math.random() <= config.getMutationRate()) {
				if (mutationOperatorSC != null && Math.random() <= config.getSecondOperatorRate()) {
					mutationOperatorSC.mutate(newPopulation.getChromosome(j));
				} else {
					mutationOperatorPR.mutate(newPopulation.getChromosome(j));
				}
			}
		}

		if (mutationOperatorPR instanceof NonUniformMutation) {
			((NonUniformMutation) mutationOperatorPR).nextGeneration();
		}
		if (mutationOperatorSC instanceof NonUniformMutation) {
			((NonUniformMutation) mutationOperatorSC).nextGeneration();
		}
	}

	// Setters and getters

	public void setCrossoverOperatorPR(ICrossover crossoverOperator) {
		this.crossoverOperatorPR = crossoverOperator;
	}

	public void setMutationOperatorPR(IMutation mutationOperator) {
		this.mutationOperatorPR = mutationOperator;
	}

	public void setSelectionOperator(ASelection selectionOperator) {
		this.selectionOperator = selectionOperator;
	}

	public void setCrossoverOperatorSC(ICrossover crossoverOperator) {
		this.crossoverOperatorSC = crossoverOperator;
	}

	public void setMutationOperatorSC(IMutation mutationOperator) {
		this.mutationOperatorSC = mutationOperator;
	}

	public ICrossover getCrossoverOperatorPR() {
		return crossoverOperatorPR;
	}

	public ICrossover getCrossoverOperatorSC() {
		return crossoverOperatorSC;
	}

	public IMutation getMutationOperatorPR() {
		return mutationOperatorPR;
	}

	public IMutation getMutationOperatorSC() {
		return mutationOperatorSC;
	}

	public ASelection getselectionOperator() {
		return selectionOperator;
	}

	public AFitnessFunction getFitness() {
		return fitness;
	}

	public AChromosome<?> getDefChromosome() {
		return defChromosome;
	}

	public Population getPopulation() {
		return this.population;
	}

	public Config getConfig() {
		return this.config;
	}
}
