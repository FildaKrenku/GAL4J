package gal4j.utils;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import gal4j.chromosome.AChromosome;

/**
 * Utility class for generating charts using JFreeChart to visualize various fitness-related metrics.
 * 
 * @author Filip Křenek
 * @version 1.0
 */
public class Charts {

    /**
     * Displays a bar chart showing the frequency of fitness values
     * across the given population of chromosomes.
     *
     * @param chromosomes the list of chromosomes to analyze
     */
    public void showFitnessBarChart(List<AChromosome<?>> chromosomes) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Count occurrences of each fitness value
        Map<Double, Integer> fitnessCounts = new TreeMap<>();
        for (AChromosome<?> chromosome : chromosomes) {
            double fitness = chromosome.getFitness();
            fitnessCounts.put(fitness, fitnessCounts.getOrDefault(fitness, 0) + 1);
        }

        // Populate the dataset for the chart
        for (Map.Entry<Double, Integer> entry : fitnessCounts.entrySet()) {
            String category = String.format("%.5f", entry.getKey());
            dataset.addValue(entry.getValue(), "Frequency", category);
        }

        // Create the bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Fitness Distribution",
                "Fitness",
                "Frequency",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Configure the chart's appearance
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Adjust font size based on number of bars
        if (fitnessCounts.size() >= 50) {
            renderer.setDefaultItemLabelFont(new Font("Calibri", Font.PLAIN, 12));
        } else {
            renderer.setDefaultItemLabelFont(new Font("Calibri", Font.PLAIN, 20));
        }

        plot.setRenderer(renderer);

        // Create and display the chart in a JFrame
        JFrame frame = new JFrame("Fitness Bar Chart");
        frame.setSize(1000, 800);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.setVisible(true);
    }

    /**
     * Displays a line chart comparing average and maximum fitness
     * across all generations.
     *
     * @param generations list of populations for each generation
     */
    public void showAverageVsMaxFitness(List<List<AChromosome<?>>> generations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double minFirstGen = 0;

        for (int generationIndex = 0; generationIndex < generations.size(); generationIndex++) {
            List<AChromosome<?>> chromosomes = generations.get(generationIndex);

            // Extract fitness values
            double[] fitnessValues = chromosomes.stream()
                    .mapToDouble(AChromosome::getFitness)
                    .toArray();

            // Calculate average fitness
            double avgFitness = 0;
            for (double fitness : fitnessValues) {
                avgFitness += fitness;
            }
            avgFitness /= fitnessValues.length;

            // Store lower bound for y-axis based on first generation
            if (generationIndex == 0) {
                minFirstGen = avgFitness - 0.1;
            }

            // Find maximum fitness
            double maxFitness = Double.NEGATIVE_INFINITY;
            for (double fitness : fitnessValues) {
                if (fitness > maxFitness) {
                    maxFitness = fitness;
                }
            }

            // Add data to dataset
            dataset.addValue(avgFitness, "Average Fitness", "Generation " + (generationIndex + 1));
            dataset.addValue(maxFitness, "Maximum Fitness", "Generation " + (generationIndex + 1));
        }

        // Create the line chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Average vs Max Fitness",
                "Generation",
                "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize chart appearance
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getRangeAxis().setLowerBound(minFirstGen);

        // Add item labels to points
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("#.#####")));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Calibri", Font.PLAIN, 15));

        plot.setRenderer(renderer);

        // Create and display chart window
        JFrame frame = new JFrame("Average vs Max Fitness");
        frame.setSize(1000, 800);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.setVisible(true);
    }

    /**
     * Displays a line chart showing the progression of the maximum
     * fitness value over time (generations), representing convergence.
     *
     * @param generations list of populations for each generation
     */
    public void showFitnessConvergenceGraph(List<List<AChromosome<?>>> generations) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double minFirstGen = 0;

        for (int generationIndex = 0; generationIndex < generations.size(); generationIndex++) {
            List<AChromosome<?>> chromosomes = generations.get(generationIndex);

            // Extract fitness values
            double[] fitnessValues = chromosomes.stream()
                    .mapToDouble(AChromosome::getFitness)
                    .toArray();

            // Determine maximum fitness
            double maxFitness = Double.NEGATIVE_INFINITY;
            for (double fitness : fitnessValues) {
                if (fitness > maxFitness) {
                    maxFitness = fitness;
                }
            }

            // Store lower bound for first generation
            if (generationIndex == 0) {
                minFirstGen = maxFitness - 0.1;
            }

            // Add to dataset
            dataset.addValue(maxFitness, "Fitness", "Generation " + (generationIndex + 1));
        }

        // Create line chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Fitness Convergence",
                "Generation",
                "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize chart appearance
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getRangeAxis().setLowerBound(minFirstGen);

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("#.#####")));
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Calibri", Font.PLAIN, 15));

        plot.setRenderer(renderer);

        // Display the chart in a window
        JFrame frame = new JFrame("Fitness Convergence");
        frame.setSize(1000, 800);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);
        frame.setVisible(true);
    }
}
