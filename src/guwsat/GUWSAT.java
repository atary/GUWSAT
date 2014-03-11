/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guwsat;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Atakan
 */
public abstract class GUWSAT {

    public static int solve(ThreeCNF CNF, double wp, int maxSteps) {
        Random r = new Random();
        initialize(CNF); //Uniformly randomize the variable at the beginning
        int steps = 0;
        int x = -1;
        while (!CNF.isSatisfied() && steps < maxSteps) {
            if (r.nextDouble() < wp) { //With a possiblity of wp
                x = r.nextInt(CNF.getVariables().length); //Choose a random variable to complement (move to a random neighbour)
            } else { //With a possiblity of 1-wp
                ArrayList<Integer> bestIndices = getBestIndices(CNF); //Get the indices corresponding to the best neigbours
                if (!bestIndices.isEmpty()) { //Do nothing if no better neigbour exists
                    x = bestIndices.get(r.nextInt(bestIndices.size())); //If not, choose a best neighbour randomly
                }
            }
            if (x != -1) {
                CNF.toogleVariable(x); //Move to the selected neigbour by complementing corresponding index
            }
            steps++; //Increase the number of step in all cases
        }
        return steps;
    }

    private static void initialize(ThreeCNF CNF) { //Uniformly randomizes the variable values 
        Random r = new Random();
        boolean[] variables = CNF.getVariables();
        for (int i = 0; i < variables.length; i++) {
            variables[i] = r.nextBoolean();
        }
        CNF.setVariables(variables);
    }

    private static ArrayList<Integer> getBestIndices(ThreeCNF CNF) { //Returns a set of indices such that complementing them results in the neighbours which has the least number of unsatisfied clauses
        ArrayList<Integer> bestIndices = new ArrayList<>(); //Empty list
        int nv = CNF.getVariables().length;
        boolean[][] neighbourhood = new boolean[nv][nv]; //Neigbours of the current position
        int minNumUnsat = CNF.numUnsat(); //minimum number of unsatisfied clauses is initialized with current value
        for (int i = 0; i < nv; i++) {
            neighbourhood[i] = CNF.getVariables(); //Copy current variables
            neighbourhood[i][i] = !neighbourhood[i][i]; //Change only one variable
            int numUnsat = CNF.numUnsat(neighbourhood[i]); //Calculate the score of the neigbour
            if (numUnsat < minNumUnsat) {
                minNumUnsat = numUnsat; //It is the new best if it better than the current best
            }
        }
        for (int i = 0; i < nv; i++) {
            int numUnsat = CNF.numUnsat(neighbourhood[i]);
            if (numUnsat == minNumUnsat) {
                bestIndices.add(i); //Choose only neigbours with the best score
            }
        }
        return bestIndices;
    }
}
