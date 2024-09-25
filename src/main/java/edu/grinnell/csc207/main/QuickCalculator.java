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
    String[] badInput = new String[args.length];
    boolean[] inputIsBad = new boolean[args.length];
    for (int i = 0; i < args.length; i++) {
      inputIsBad[i] = true;
    } // for
    if (args[0].length() == 0) {
      System.err.println("FAILED [Invalid expression]");
      return;
    } // if
    for (int i = 0; i < args.length; i++) {
      if (!InteractiveCalculator.checkInput(args[i])) {
        badInput[i] = args[i];
        inputIsBad[i] = false;
      } // if
      if (inputIsBad[i]) {
        pen.printf(args[i] + " -> ");
        InteractiveCalculator.readInput(args[i]);
      } else {
        pen.println(badInput[i] + ": FAILED [Invalid expression]");
      } // if
    } // for
    pen.close();
  } // main(String[])
} // class QuickCalculator
