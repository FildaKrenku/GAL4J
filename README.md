# GAL4J – Genetic Algorithm Library for Java

**GAL4J** is a flexible and extensible Java library for implementing **sequential** and **parallel** genetic algorithms. It is designed to be lightweight, easy to use, and adaptable to various optimization problems.

---

##  Features

- Support for **sequential** and **parallel** execution
- Custom operators can be easily plugged in
- Lightweight and modular structure
- Built with Maven

---

## Installation

Clone the repository and build the library using Maven:

```bash
git clone https://github.com/FildaKrenku/GAL4J
cd gal4j
mvn clean package
```
Than just add GAL4J.jar to your build path of you project and you are ready.

---

## Requirements

- Java 17 or higher
- Maven 3.9 or higher

---

## Example Usage

- Define your own fitness function
- Chose one type of chromosome
- Configure the genetic algorithm
- Run sequential or parallel evolution

```Java
Config conf = new Config();
conf.setPopulationSize(1000);
conf.setGenetarions(100);
conf.setGenLength(15);
conf.setMutationRate(0.01);
conf.setSelectionRate(0.2);
        
        
                    
Population pop = new Population();
Bag bag = new Bag(58);
AFitnessFunction func = new BagFunc(bag.items, bag.maxWeight);
IChromosome<Integer> def = new BinaryChromosome(conf.getGenLength());
        
        
ASelection selectOp = new TournamentSelection(3);
ICrossover crossOp = new SinglePointCrossOver();
IMutation mutationOp = new SinglePointMutation();
        

Engine engine = new Engine(conf, pop, func, def); 
engine.setSelectionOperator(selectOp);
engine.setCrossoverOperatorPR(crossOp);
engine.setMutationOperatorPR(mutationOp);

engine.initializePopulation();
engine.run();
        
System.out.println("Best solution:");
engine.getPopulation().getBestChromosome().printGenes(); 
```

---

## Used software 

[<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/00/Eclipse_Logo.svg/120px-Eclipse_Logo.svg.png" width="20"/> Eclipse IDE](https://www.eclipse.org/ide/) – Java development environment

---
