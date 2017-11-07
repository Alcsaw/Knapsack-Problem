/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblem01;

import java.util.Random;

/**
 *
 * @author Alcsaw
 */
public class SimulatedAnnealing {
    // this method computes the utility of a given solution
    /*public double calcUtility(int[] itemWeight, int[] itemValue, int[] solution){
        double utility = 0;
        int totalWeight = 0;
        int totalValue = 0;
        int maxWeight = 400;
        int maxValue = 0;   // the alpha
        
        for (int i=0; i < itemValue.length; i++){
            maxValue = maxValue + itemValue[i];
        }
        
        for (int i=0; i < itemWeight.length; i++){
            totalValue = totalValue + itemValue[i] * solution[i];
            totalWeight = totalWeight + itemWeight[i] * solution[i];
            
            utility = utility + (totalValue - maxValue * Math.max(0, totalWeight - maxWeight));
        }
        return utility;
    }*/
    UtilityCalculator utilityCalculator = new UtilityCalculator();
    
    
    public int[] SA(int[] itemWeight, int[] itemValue, float temperatureDecrementor, int SAmax, double T0, int [] currentSolution){
        int [] bestSolution = currentSolution;    //Best solution to be returned
        int iterations = 0;     //Iterations counter
        double currentTemperature = T0;
        int bitToChange = 0;
        int[] neighbor;
        double delta = 0;
        double bestSolutionUtility = utilityCalculator.calcUtility(itemWeight, itemValue, bestSolution);
        double currentSolutionUtility = utilityCalculator.calcUtility(itemWeight, itemValue, currentSolution);
        
        while (currentTemperature > 0.0001){
            while (iterations < SAmax){
                iterations ++;
                
                //Creating a randomly neighbor of the current best solution:
                bitToChange = new Random().nextInt(itemValue.length);
                neighbor = currentSolution.clone();
                if (neighbor[bitToChange] == 1)
                    neighbor[bitToChange] = 0;
                else
                    neighbor[bitToChange] = 1;
                double neighborUtility = utilityCalculator.calcUtility(itemWeight, itemValue, neighbor);
                delta = neighborUtility - currentSolutionUtility;
                
                if (delta > 0){
                    currentSolution = neighbor;
                    currentSolutionUtility = utilityCalculator.calcUtility(itemWeight, itemValue, currentSolution);
                    if (neighborUtility > bestSolutionUtility){
                        bestSolution = neighbor;
                        bestSolutionUtility = utilityCalculator.calcUtility(itemWeight, itemValue, bestSolution);
                    }
                }
                else{
                    //Computes chance of accepting a not-better solution
                    float random = new Random().nextFloat();
                    if (random < Math.pow(Math.E, -(delta/currentTemperature))){
                        currentSolution = neighbor;
                        currentSolutionUtility = utilityCalculator.calcUtility(itemWeight, itemValue, currentSolution);
                    }
                }
            }
            currentTemperature = currentTemperature * temperatureDecrementor;
            iterations = 0;
        }
        
        return bestSolution;
    }
}
