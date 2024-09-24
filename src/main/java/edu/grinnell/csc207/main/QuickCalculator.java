package edu.grinnell.csc207.main;

import java.io.PrintWriter;

/**
 * Uses readInput method in InteractiveCalculator class to breakdown command line
 * inputs and perform calculations.
 * Applies basic mathematic procedures on multiple arguments. 
 * @author Jafar Jarrar.
 */
public class QuickCalculator {
    /**
     * Runs the program.
     * @param args
     * The command line arguments. They should contain fractions, operations, STORE
     * commands, or letters corresponding to register values.
     */
    public static void main(String[] args) {
        PrintWriter pen = new PrintWriter(System.out, true);
        for (int i = 0; i < args.length; i++) {
            pen.printf(args[i] + " -> ");
            InteractiveCalculator.readInput(args[i]);
        } // for
        pen.close();
    } // main(String[])
} // class QuickCalculator