/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guwsat;

/**
 *
 * @author Atakan
 */
public class Main {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Usage: GUWSAT 0.1 1000 inFile outFile");
            System.exit(-1);
        }

        //ThreeCNF.generateRandomInput("input.txt", 15, 40); //Generate an input file with 15 variables and 40 clauses.

        double wp = Double.parseDouble(args[0]); //Possibility of random steps
        int maxSteps = Integer.parseInt(args[1]); //Maximum number of steps to try
        String inFile = args[2]; //Relative input file path
        String outFile = args[3]; //Relative output file path

        ThreeCNF CNF = new ThreeCNF(inFile); //Generate a 3-CNF by reading the input file

        int steps = GUWSAT.solve(CNF, wp, maxSteps); //Solve the 3-SAT problem and return the number of steps

        if (CNF.isSatisfied()) {
            System.out.println("Problem is solved after " + steps + " steps.");
        } else {
            System.out.println("Problem is NOT solved after " + steps + " steps.");
        }

        CNF.writeOutput(outFile);
        System.out.println("Final output is written to the file " + outFile);
    }
}
