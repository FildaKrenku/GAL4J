package gal4j.utils;

import gal4j.algorithm.Config;
import gal4j.algorithm.DefaultConfig;
import gal4j.algorithm.Engine;

/**
 * Utility class that verifies and adjusts configuration settings and validates the Engine instance.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class Checker {

    /**
     * Verifies the provided configuration and sets default values for any invalid parameters.
     * Also prints a summary of the final configuration values.
     *
     * @param conf The configuration to check and adjust.
     */
    public void checkConfig(Config conf) {
        DefaultConfig def = new DefaultConfig();

        if (conf.getPopulationSize() <= 0) {
            conf.setPopulationSize(def.getPopulationSize());
        }
        if (conf.getGenetarions() <= 0) {
            conf.setGenetarions(def.getGenetarions());
        }
        if (conf.getGenLength() <= 0) {
            conf.setGenLength(def.getGenLength());
        }
        if (conf.getMutationRate() <= 0) {
            conf.setMutationRate(def.getMutationRate());
        }
        if (conf.getSelectionRate() <= 0) {
            conf.setSelectionRate(def.getSelectionRate());
        }

        if (conf.getThreads() > 1 && !conf.isMultithread()) {
            conf.setMultithread(def.isMultithread());
        }

        if (conf.getThreads() <= 1 && conf.isMultithread()) {
            conf.setThreads(def.getThreads());
        }

        System.out.println("START");
        System.out.println("-----------------------------");

        System.out.printf("%-20s %d%n", "Population size:", conf.getPopulationSize());
        System.out.printf("%-20s %d%n", "Generations:", conf.getGenetarions());
        System.out.printf("%-20s %d%n", "Gen length:", conf.getGenLength());
        System.out.printf("%-20s %.2f%n", "Mutation rate:", conf.getMutationRate());
        System.out.printf("%-20s %.2f%n", "Selection rate:", conf.getSelectionRate());
        System.out.printf("%-20s %b%n", "Multithread:", conf.isMultithread());
        System.out.printf("%-20s %d%n", "Threads:", conf.getThreads());

        if (conf.getDelta() > 0 && conf.getApproximateFinalFit() > 0) {
            System.out.printf("%-20s %.2f%n", "Approximate fit:", conf.getApproximateFinalFit());
            System.out.printf("%-20s %.2f%n", "Delta:", conf.getDelta());
        } else {
            conf.setDelta(0);
            conf.setApproximateFinalFit(-1);
        }
        System.out.println("-----------------------------");
    }

    /**
     * Validates that all necessary components of the engine are initialized.
     *
     * @param engine The engine to validate.
     * @throws IllegalArgumentException if any required component is missing.
     */
    public void checkEngine(Engine engine) {
        if (engine.getPopulation() == null) {
            throw new IllegalArgumentException("Population is missing.");
        }

        if (engine.getDefChromosome() == null) {
            throw new IllegalArgumentException("Default chromosome is missing.");
        }

        if (engine.getFitness() == null) {
            throw new IllegalArgumentException("Fittness function is missing.");
        }

        if (engine.getConfig() == null) {
            throw new IllegalArgumentException("Config is missing.");
        }
    }

    /**
     * Validates that all required genetic operators are set in the engine.
     * If second-level operators are defined, a default second operator rate is set in the config.
     *
     * @param engine The engine whose operators are validated.
     * @param conf   The configuration to possibly adjust.
     * @throws NullPointerException if any primary operator is missing.
     */
    public void checkOperators(Engine engine, Config conf) {
        if (engine.getMutationOperatorPR() == null) {
            throw new NullPointerException("Mutation operator is missing.");
        }

        if (engine.getCrossoverOperatorPR() == null) {
            throw new NullPointerException("Crossover operator is missing.");
        }

        if (engine.getselectionOperator() == null) {
            throw new NullPointerException("Selection operator is missing.");
        }

        if (engine.getCrossoverOperatorSC() != null || engine.getMutationOperatorSC() != null) {
            conf.setSecondOperatorRate(0.25);
        }
    }
}
