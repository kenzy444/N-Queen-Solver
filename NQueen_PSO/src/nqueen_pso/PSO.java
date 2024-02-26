/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nqueen_pso;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
//a traver cette prsentation je vais 
public class PSO{
    private final int n; // size of the board
    private final int populationSize; // number of particles in the population
    private final int maxIterations; // maximum number of iterations
    private final double c1, c2, w; // PSO parameters
    private int[] globalBest; // best solution found by the swarm
    private int globalBestFitness; // fitness value of the best solution

    public PSO(int n, int populationSize, int maxIterations, double c1, double c2, double w) {
        this.n = n;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
    }

    // Initializes the population of particles
    private List<int[]> initializePopulation() {
        List<int[]> population = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            int[] individual = new int[n];
            for (int j = 0; j < n; j++) {
                individual[j] = random.nextInt(n);
            }
            population.add(individual);
        }
        return population;
    }

    // Evaluates the fitness of a solution (number of conflicts)
    private int evaluateFitness(int[] solution) {
        int conflicts = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (solution[i] == solution[j] || Math.abs(i - j) == Math.abs(solution[i] - solution[j])) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    // Updates the best solution found by the swarm
    private void updateGlobalBest() {
        List<int[]> population = initializePopulation();
        int[] localBest = initializePopulation().get(0);
        int localBestFitness = evaluateFitness(localBest);
        for (int[] particle : population) {
            int fitness = evaluateFitness(particle);
            if (fitness < localBestFitness) {
                localBest = particle;
                localBestFitness = fitness;
            }
        }
        if (globalBest == null || localBestFitness < globalBestFitness) {
            globalBest = localBest;
            globalBestFitness = localBestFitness;
        }
    }

    // Updates the velocity and position of a particle
    private void updateParticle(int[] particle, int[] personalBest) {
        int[] velocity = new int[n];
        for (int i = 0; i < n; i++) {
            double r1 = ThreadLocalRandom.current().nextDouble();
            double r2 = ThreadLocalRandom.current().nextDouble();
            velocity[i] = (int) (w * particle[i] + c1 * r1 * (personalBest[i] - particle[i]) + c2 * r2 * (globalBest[i] - particle[i]));
        }
        for (int i = 0; i < n; i++) {
            particle[i] = (particle[i] + velocity[i] + n) % n;
        }
    }

    // Runs the PSO algorithm
    public int[] solve() {
        int[]personalBest;
        List<int[]> population = initializePopulation();
        updateGlobalBest();
        for (int iter = 0; iter < maxIterations; iter++) {
            for (int[] particle : population) {
                 personalBest = particle;
                 evaluateFitness(personalBest);
                updateParticle(particle, personalBest);
                 evaluateFitness(particle);
            }
            updateGlobalBest();
        }
        return globalBest;
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
        int fail=0;
        int succes =0;
        double [] time;
        int max=10;
        int j = 0;
        int n =10; // size of the board
        int populationSize = 100; // number of particles in the population
        int maxIterations = 1000; // maximum number of iterations
        double c1 = 2; // cognitive parameter (controls the influence of the particle's personal best position on its movement)
        double c2 = 2; // social parameter  (controls the influence of the global best position on the particle's movement)
        double w = 0.5; // inertia  (controls the balance between the particle's current velocity and its previous velocity.)
        time = new double[max];
        for (int i = 0; i <max ; i++) {
             long debut = System.currentTimeMillis();
            PSO pso = new PSO(n, populationSize, maxIterations, c1, c2, w);
            int[] sol = pso.solve();
            if(is_valid(sol)) {
                succes ++;
               
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
        //System.out.println("NUMBER OF SUCCESSES: "+succes);
        //System.out.println("NUMBER OF FAILURES: "+fail);
        double successRate = (double)succes/max ;
        System.out.println("SUCCESS RATE : "+ successRate );
    }
}


