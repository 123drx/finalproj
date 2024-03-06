package daniel.finalproj.Algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import daniel.finalproj.Objects.Schedule;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.Subject;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 400;
    private static int MAX_GENERATIONS = 500;
    private static double MUTATION_RATE = 0.66;
    private static double BIGMUTATION_RATE = 0.25;
    private static int CrossOverPrecent = 10; // what precentage of the population will Save

    public School Geneticalgorithm(School school) {
        int pastgenetarionbestfittnes = 0;
        int count = 0;
        ArrayList<School> population = initializepopulation(school);
        List<Integer> SortedIndexes = new ArrayList<>();
        for (int generation = 1; generation <= MAX_GENERATIONS; generation++) {
            ArrayList<Double> Fitnesses = new ArrayList<>();
            for (int i = 0; i < POPULATION_SIZE; i++) {
                Fitnesses.add(population.get(i).evaluateSchool());
            }
            SortedIndexes = getSortedIndexes(Fitnesses);
            ArrayList<School> newPopulation = new ArrayList<>();
            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (i < (CrossOverPrecent * (POPULATION_SIZE / 100))) {
                    newPopulation.add(new School(population.get(SortedIndexes.get(i))));
                } else {
                    School parent1 = new School();
                    parent1.tournamentSelection(population, Fitnesses, SortedIndexes);
                    School parent2 = new School();
                    parent2.tournamentSelection(population, Fitnesses, SortedIndexes);
                    while (parent2.equals(parent1)) {
                        parent2.tournamentSelection(population, Fitnesses, SortedIndexes);
                    }
                    School child = new School(parent1);
                    child.crossover(parent1, parent2);

                    Random rand = new Random();
                    double randomNumber = (double)rand.nextInt(101)/100;
                    if (randomNumber < BIGMUTATION_RATE) {
                        child.BigMutate(child.getallSubjects());
                    } else if (randomNumber < MUTATION_RATE) {
                        child.Mutate(child.getallSubjects());
                    }
                    newPopulation.add(child);
                }
            }
            population = newPopulation;
            int bestIndex = Fitnesses.indexOf(Collections.max(Fitnesses));
            System.out.println("Generation " + generation + ": Best Fitness : " +
                    Fitnesses.get(bestIndex) + "\t"
                    + "diaversity :  " + calculateDiversity(Fitnesses) + "\tAvrage Fitnnes : "
                    + claculateavragefittnes(Fitnesses) + "\t POPULATION SIZE : " +
                    population.size());
        }
        int bestIndex = evaluateBestScheduleIndex(population);
        population.get(bestIndex).printSchool();
        population.get(bestIndex).printEvaluation();
        double v = population.get(bestIndex).evaluateSchool();
        System.out.println("Fittnes ; " + v);
        System.out.println("Mutation R" + MUTATION_RATE);
        System.out.println("SuperMutation R" + BIGMUTATION_RATE);
        return population.get(bestIndex);
    }


    public int claculateavragefittnes(List<Double> fitnesses) {
        int avrage = 0;
        for (double value : fitnesses) {
            avrage += value;
        }
        avrage = avrage / fitnesses.size();
        return avrage;
    }

    public int calculateDiversity(List<Double> list) {
        // Use a Set to automatically eliminate duplicates
        Set<Double> uniqueElements = new HashSet<>(list);

        // Return the size of the Set, which represents the number of unique elements
        return uniqueElements.size();
    }

    public ArrayList<School> initializepopulation(School school) {
        ArrayList<School> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            School s = new School(school);
            s.FillScheduleWithLessons();
            population.add(s);  
        }
        return population;
    }

    public static ArrayList<Integer> getSortedIndexes(ArrayList<Double> values) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            indices.add(i);
        }

        // Sort indices based on corresponding values
        Collections.sort(indices, Comparator.comparing(values::get).reversed());

        return indices;
    }

    private int evaluateBestScheduleIndex(ArrayList<School> population) {
        List<Double> fitnessValues = new ArrayList<>();
        for (School Schedule : population) {
            fitnessValues.add(Schedule.evaluateSchool());
        }
        return fitnessValues.indexOf(Collections.max(fitnessValues));
    }

}
