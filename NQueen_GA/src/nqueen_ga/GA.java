/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nqueen_ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA {
    private int n;
    private int populationSize;
    private int generations;
    private double mutationRate;
    private Random random;

    public GA(int n, int populationSize, int generations, double mutationRate) {
        this.n = n;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
        random = new Random();
    }

    public List<int[]> solve() {
        List<int[]> initialPopulation = initializePopulation();
        List<int[]> population = initialPopulation;

        for (int i = 0; i < generations; i++) {
            List<int[]> matingPool = new ArrayList<>();

            for (int j = 0; j < populationSize; j++) {
                matingPool.add(tournamentSelection(population));
            }

            population = recombineAndMutate(matingPool);
        }

        int[] bestSolution = findFittest(population);
        List<int[]> solutions = new ArrayList<>();
        solutions.add(bestSolution);
        return solutions;
    }

    private List<int[]> initializePopulation() {
        List<int[]> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            int[] chromosome = new int[n];
            for (int j = 0; j < n; j++) {
                chromosome[j] = random.nextInt(n);
            }
            population.add(chromosome);
        }
        return population;
    }

    private int[] tournamentSelection(List<int[]> population) {
        int tournamentSize = 5;
        int[] tournament = population.get(random.nextInt(populationSize));
        for (int i = 0; i < tournamentSize; i++) {
            int[] challenger = population.get(random.nextInt(populationSize));
            if (fitness(challenger) > fitness(tournament)) {
                tournament = challenger;
            }
        }
        return tournament;
    }

    private List<int[]> recombineAndMutate(List<int[]> matingPool) {
        List<int[]> population = new ArrayList<>();

        for (int i = 0; i < matingPool.size(); i++) {
            int[] parent1 = matingPool.get(i);
            int[] parent2 = matingPool.get(random.nextInt(matingPool.size()));
            int[] child = recombine(parent1, parent2);
            mutate(child, mutationRate);
            population.add(child);
        }
        return population;
    }

    private int[] recombine(int[] parent1, int[] parent2) {
        int chromosomeLength = parent1.length;
        int crossoverPoint = random.nextInt(chromosomeLength);

        int[] child = new int[chromosomeLength];
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
        }
        for (int i = crossoverPoint; i < chromosomeLength; i++) {
            child[i] = parent2[i];
        }
        return child;
    }

    private void mutate(int[] chromosome, double mutationRate) {
        for (int i = 0; i < chromosome.length; i++) {
            if (random.nextDouble() <= mutationRate) {
                chromosome[i] = random.nextInt(n);
            }
        }
    }

    private int fitness(int[] chromosome) {
        int collisions = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (chromosome[i] == chromosome[j]) {
                    collisions++;
                }
                if (Math.abs(i - j) == Math.abs(chromosome[i] - chromosome[j])) {
                    collisions++;
                }
            }
        }
        return n - collisions;
    }
    //pso sert à optmiser une fonction en faissant évoluer une population de particules 
    //en fonction de leurs propre experience et celle de leur voisins 
    //Finds the fittest individual in the population 
    private int[] findFittest(List<int[]> population) {
        int[] fittest = population.get(0);
        for (int[] chromosome : population) {
            if (fitness(chromosome) > fitness(fittest)) {
                fittest = chromosome;
            }
        }
        return fittest;
    }
  public static boolean is_valid(int[] state) {
        int n = state.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (state[i] == state[j] || Math.abs(state[i] - state[j]) == Math.abs(i - j)) {
                    count++;
                }
            }
        }
        return count == 0;
    }
    public static void main(String[] args) {
        double [] time;
        int j =0;
        int fail=0;
        int succes =0;
        int max =10;
        time = new double[max];
        for(int i =0; i< max ; i ++){
         long debut = System.currentTimeMillis();
           GA genetic = new GA(8, 1000, 1000, 1); 
           List<int[]> solutions = genetic.solve();
           int[] sol = solutions.get(0);
           if(is_valid(sol)) {
               succes ++;
               //System.out.println(Arrays.toString(sol));
           }
           else fail++;
         long fin = System.currentTimeMillis();
         long duree = fin - debut;
         double dureeEnSecondes = (double) duree / 1000;
         time[j] = dureeEnSecondes;
         j++;
         
        }
        
      double  sum = 0;
      for (int i = 0; i < time.length; i++) {
         sum += time[i];
      }
     double averageTime = sum / (double) time.length; // calculate the average time
     String formattedTime = String.format("%.3f", averageTime); 
     System.out.println("Average Duration : " + formattedTime + "s");
     System.out.println("NUMBER OF SUCCESSES : "+succes);
     System.out.println("NUMBER OF FAILURES : "+fail);
     double successRate = (double)succes/max ;
     System.out.println("SUCCESS RATE : "+ successRate );
    } 
}
