/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblem01;

import java.util.Arrays;

/**
 *
 * @author Alcsaw
 */
public class KnapsackProblem01 {

    /**
     * @param args the command line arguments
     */
    public static int[] itemWeight = {9, 13, 153, 50, 15, 68, 27, 39, 23, 52, 11,
                            32, 24, 48, 73, 42, 43, 22, 7, 18, 4, 30};
    public static int[] itemValue = {150, 35, 200, 160, 60, 45, 60, 40, 30, 10,
                                70, 30, 15, 10, 40, 70, 75, 80, 20, 12, 50, 10};
    public static String[] itemName = {"map", "compass", "water", "sandwich",
            "glucose", "tin", "banana", "apple", "cheese", "beer",
            "suntan cream", "camera", "T-shirt", "trousers", "umbrella",
            "waterproof trousers", "waterproof overclothes", "note-case",
            "sunglasses", "towel", "socks", "book"};
    
    public static void main(String[] args) {
        // TODO code application logic here
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
        TabuSearch tabuSearch = new TabuSearch();
        int[] currentSolution = {1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0};
        //int[] solutionFound = simulatedAnnealing.SA(itemWeight, itemValue, (float)0.9, 10, 100, currentSolution);
        int[] solutionFound = tabuSearch.tabuSearch(itemWeight, itemValue, 0, currentSolution);
        System.out.println(Arrays.toString(solutionFound));
        printItems(solutionFound);
        int[] primeiroResultadoSA = {1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0};
        printItems(primeiroResultadoSA);
    }
    
    public static void printItems(int[] solution){
        int totalWeight = 0;
        int totalValue = 0;
        
        for (int i=0; i <solution.length; i++){
        //for (int item : solution){
            //if (item == 1)
            if (solution[i] == 1){
                totalWeight += itemWeight[i];
                totalValue += itemValue[i];
                System.out.println(itemName[i]);
            }
        }
        System.out.println("Total Weight: " + totalWeight + "\nTotal Value: " + totalValue);
    
    }
    
    
}
