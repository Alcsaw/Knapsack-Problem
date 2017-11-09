/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knapsackproblem01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import javafx.util.Pair;

/**
 *
 * @author Alcsaw
 */
public class TabuSearch {
    
    UtilityCalculator utilityCalculator = new UtilityCalculator();
    
    //public int[] tabuSearch(int[] itemWeight, int[] itemValue, int TSmax, int [] currentSolution, int tabuListCardinality){
    public int[] tabuSearch(int[] itemWeight, int[] itemValue, int TSmax, int [] currentSolution){
        //TSmax is the max number of iterations with no improvement for the bestSolution or the max number of iterations at all
        //tabuListCardinality is cardinality of tabuList, i.e., how many changes tabuList is going to remember
        int [] bestSolution = currentSolution;  //Best solution to be returned
        double bestSolutionUtility = 0;
        int [] neighbor;
        double neighborUtility = 0;
        List<Integer> tabuList = new ArrayList<>();
        int iterations = 0;                     //Iterations counter
        int iterationsUntilChange = 0;          //Counts the number of iterations until change the bestSolution
        NeighborAndUtility neighborAndutility;
        
        while (iterations - iterationsUntilChange <= TSmax){
            iterations++;
            
            neighborAndutility = getBestNeighbor(currentSolution, tabuList, itemWeight, itemValue, bestSolutionUtility);
            neighbor = neighborAndutility.getNeighbor();
            neighborUtility = neighborAndutility.getUtility();
            
            if (neighborUtility > bestSolutionUtility){
                bestSolution = neighbor.clone();
                bestSolutionUtility = neighborUtility;
                iterationsUntilChange = iterations;
            }
        }
        
        return bestSolution;
    }
    
    //public int[] getBestNeighbor(int[] currentSolution, List<Integer> tabuList, int[] itemWeight, int[] itemValue, double bestSolutionUtility){
    //public Pair<int[], Double> getBestNeighbor(int[] currentSolution, List<Integer> tabuList, int[] itemWeight, int[] itemValue, double bestSolutionUtility){
    public NeighborAndUtility getBestNeighbor(int[] currentSolution, List<Integer> tabuList, int[] itemWeight, int[] itemValue, double bestSolutionUtility){
    
        int length = currentSolution.length;
        int[][] neighbors = new int[length][length];
        int[] currentNeighbor;
        double[] neighborsUtility = new double[length];
        //int[] orderedNeighbors;
        
        //Defines all neighbors
        for (int i=0; i<length; i++){
            currentNeighbor = currentSolution.clone();
            if (currentSolution[i] == 0)
                currentNeighbor[i] = 1;
            else
                currentNeighbor[i] = 0;
            
            neighbors[i] = currentNeighbor.clone();
        }
        
        //Selects the best neighbor
        if (tabuList.size() >= length){
            //means that there are no moves allowed, default is to use the first (oldest) move on the list
            //Update tabuList
            currentNeighbor = neighbors[tabuList.get(0)];
            tabuList.remove(0);
            //return currentNeighbor;
            return new NeighborAndUtility(currentNeighbor, utilityCalculator.calcUtility(itemWeight, itemValue, currentNeighbor));
        }
        
        //if there are still allowed movements, search for the best option:
        //first, gets all neighbors utilities
        for (int i=0; i<length; i++)
            neighborsUtility[i] = utilityCalculator.calcUtility(itemWeight, itemValue, neighbors[i]);

        //then, order them by their utility
        int[] order = new int[length];  //contains only the indexes of neihbors for a reference of the real array
        double[] neiborsOrder = neighborsUtility.clone();
        for (int i=0; i <length; i++)
            order[i] = i;
        //System.out.println("Original order: " + Arrays.toString(order));
        
        
        for (int i=0; i<length; i++){
            for (int j=i; j<length; j++){
                if (neiborsOrder[i] < neiborsOrder[j]){
                    double temp = neiborsOrder[i];
                    int temp2 = order[i];
                            
                    neiborsOrder[i] = neiborsOrder[j];
                    order[i] = order[j];
                    
                    neiborsOrder[j] = temp;
                    order[j] = temp2;
                }
            }
        }
        //System.out.println("New order" + Arrays.toString(order));
        //and then, checks if the best neighbor is allowed
            
            /*  since tabuList keeps track of what index had changed and the neighbors array is created
                following the same indexes, if index = tabuList[0] the neighbors[index] is on tabuList*/

        
        //if (Arrays.toString(tabuList).contains(Integer.toString())){
        for (int index : order){
            
            //if the neighbor with best utility is on tabuList
            //if (tabuList.toString().contains(Integer.toString(index))){
            if (tabuList.contains(index)){
                //System.out.println("a produção selecionada está na lista tabu - " + Arrays.toString(neighbors[index]));
                //Check the Aspiration Criteria for the current neighbor
                if (utilityCalculator.calcUtility(itemWeight, itemValue, neighbors[index]) > bestSolutionUtility){
                    tabuList.remove(tabuList.indexOf(index));
                    tabuList.add(index);    //tabuList updated
                    //return neighbors[index];
                    return new NeighborAndUtility(neighbors[index], neighborsUtility[index]);
                }
                //else takes the next one(ordered by best utility)
            }
            else{
                //System.out.println("Produção selecionada não está proibida! " + Arrays.toString(neighbors[index]));
                if (tabuList.size() >= length){
                    tabuList.remove(0);
                }
                tabuList.add(index);
                //return neighbors[index];
                return new NeighborAndUtility(neighbors[index], neighborsUtility[index]);
            }
        }
        //System.out.println("ERROR: Couldn't get any neighbor");
        currentNeighbor = neighbors[tabuList.get(0)];
        tabuList.remove(0);
        //return currentNeighbor;
        return new NeighborAndUtility(currentNeighbor, utilityCalculator.calcUtility(itemWeight, itemValue, currentNeighbor));
    }

}

final class NeighborAndUtility {
    private final int[] neighbor;
    private final double utility;

    public NeighborAndUtility(int[] neighbor, double utility) {
        this.neighbor = neighbor;
        this.utility = utility;
    }

    public int[] getNeighbor() {
        return neighbor;
    }

    public double getUtility() {
        return utility;
    }
}
