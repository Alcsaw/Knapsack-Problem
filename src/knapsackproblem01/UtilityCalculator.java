/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblem01;

/**
 *
 * @author Alcsaw
 */
public class UtilityCalculator {
    // this method computes the utility of a given solution
    public double calcUtility(int[] itemWeight, int[] itemValue, int[] solution){
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
    }
}
