package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

import edu.grinnell.csc207.util.BFRegisterSet;
import edu.grinnell.csc207.util.BFCalculator;
import edu.grinnell.csc207.util.BigFraction;

/**
 * Applies basic mathematic procedures on as many arguments provided as
 * inputted Strings.
 * @author Jafar Jarrar.
 */
public class InteractiveCalculator {

  /** The value of the character a in integer form. */
  private static final int INTEGER_VALUE_OF_A = 97;

  /** The value of the character z in integer form. */
  private static final int INTEGER_VALUE_OF_Z = 122;

  /** The length of the word 'STORE' when entered as input. */
  private static final int LENGTH_OF_STORE_COMMAND = 5;

  /** The index of the inputted character corresponding to the
   * register index. */
  private static final int INDEX_OF_CHARACTER_INPUT = 6;

  /** The length of the command when 'STORE' and a character are entered
   * as input.
   */
  private static final int LENGTH_OF_STORE_CHAR_COMMAND = 7;

  /** The index of the next fraction read when parsing input. */
  private static final int INDEX_OF_NEXT_FRACTION = 3;

  /** Fraction to hold value of the last line of calculations inputted. */
  private static BigFraction mostRecentCalculation;

  /** An instance of the BFRegister class with which store and get
   * commands will be used.
   */
  private static BFRegisterSet register = new BFRegisterSet();

  /**
   * Runs the program.
   * @param args
   * The command line arguments. The program does not expect any and will
   * not use them at any time.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner eyes = new Scanner(System.in);
    pen.print("> ");
    String stuff = eyes.nextLine();
    while (!(stuff.equals("QUIT"))) {
      readInput(stuff);
      pen.print("> ");
      stuff = eyes.nextLine();
      pen.print(stuff);
    } // while
    eyes.close();
  } // main(String[])

  /**
   * Checks if operation to calculator provided by user has any invalid inputs.
   * @param input
   * Input containing fractions, STORE commands, or operands.
   * @return true if no invalid expressions are found, false otherwise.
   */
  public static boolean checkInput(String input) {
    boolean rightSyntax = true;
    for (int i = 0; i < input.length(); i++) {
      char temp = input.charAt(i);
      if ((temp == '-') && BigFraction.isNumber(input.charAt(1))) {
        break;
      } else if (isOperator(temp) && (i == input.length() - 1)) {
        rightSyntax = false;
        break;
      } else if (isOperator(temp) && (i == 0)) {
        rightSyntax = false;
        break;
      } else if (isOperator(temp) && (i == 0)) {
        rightSyntax = false;
        break;
      } else if ((i == input.length() - 1) && (!isLetter(temp) && !BigFraction.isNumber(temp))) {
        rightSyntax = false;
        break;
      } else if (temp == 'x') {
        rightSyntax = false;
        break;
      } // if
    } // for
    if (!rightSyntax) {
      return false;
    } // if
    return true;
  } // checkInput(String)

  /**
   * Checks if given character is an arithmetic operator.
   * @param c
   * Character to be checked.
   * @return true if it is an operator, false otherwise.
   */
  public static boolean isOperator(char c) {
    if (c == '+' || c == '-' || c == '*' || c == '/') {
      return true;
    } // if
    return false;
  } // isOperator(char)

  /** Calculates the length of a fraction within a string of input.
   * @param input
   * The inputted string containing one or more fractions.
   * The length of the first faction is found.
   * @return the length of the fraction.
  */
  public static int lengthOfFrac(String input) {
    int length = 0;
    for (int i = 0; i < input.length(); i++) {
      // Stops for loop when space character is detected.
      if (input.charAt(i) == ' ') {
        break;
      } // if
      length++;
    } // for
    return length;
  } // lengthOfFrac(String)

  /**
   * Prints fraction according to its properties.
   * @param frac
   * Fraction to be printed.
   */
  public static void printFrac(BigFraction frac) {
    PrintWriter pen = new PrintWriter(System.out, true);
    if (frac.numerator().equals(frac.denominator())) {
      pen.println(frac.numerator());
    } else if (frac.denominator().equals(BigInteger.valueOf(1))) {
      pen.println(frac.numerator() + "");
    } else if (frac.denominator().compareTo(BigInteger.ZERO) < 0) {
      pen.println("-" + frac.numerator() + "/" + frac.denominator().toString().substring(1));
    } else {
      pen.println(frac.numerator() + "/" + frac.denominator());
    } // if
  } // printFrac(BigFraction)

  /**
   * Checks if given character is a lowercase letter.
   * @param c
   * The character in which we want to check is a lowercase letter.
   * @return true if it is a lowercase letter, false otherwise.
   */
  public static boolean isLetter(char c) {
    if (c >= INTEGER_VALUE_OF_A && c <= INTEGER_VALUE_OF_Z) {
      return true;
    } // if
    return false;
  } // isLetter(char)

  /**
   * Reads through input provided by user and carries out operation accordingly.
   * @param input
   * Input containing fractions, STORE commands, or operands.
   */
  public static void readInput(String input) {
    PrintWriter pen = new PrintWriter(System.out, true);
    BFCalculator calculator = new BFCalculator();
    BigFraction lastComputedValue = calculator.get();
    if ((input.length() == LENGTH_OF_STORE_COMMAND)
          && (input.substring(0, LENGTH_OF_STORE_COMMAND).equals("STORE")
          && lastComputedValue.numerator().equals(BigInteger.ZERO))) {
      System.err.println("*** ERROR [Store command received invalid register] ***");
      return;
    } else if ((input.length() == LENGTH_OF_STORE_CHAR_COMMAND)
          && (input.substring(0, LENGTH_OF_STORE_COMMAND).equals("STORE"))) {
      if (!isLetter(input.charAt(INDEX_OF_CHARACTER_INPUT))) {
        System.err.println("*** ERROR [Store command received invalid register] ***");
        return;
      } // if
      register.store(input.charAt(INDEX_OF_CHARACTER_INPUT), mostRecentCalculation);
      pen.println("STORED");
      return;
    } else if (isLetter(input.charAt(0)) && (input.length() == 1)) {
      printFrac(register.get(input.charAt(0)));
    } else if (!checkInput(input)) {
      System.err.println("*** ERROR [Invalid Expression] ***");
      return;
    } // if
    // Iterates through each character of input, performing multiple checks on
    // each character to check if it is a number, operand, STORE command, and
    // carries out expressions accordingly.
    for (int i = 0; i < input.length(); i++) {
      char temp = input.charAt(i);
      if (lastComputedValue.numerator().equals(BigInteger.ZERO) && BigFraction.isNumber(temp)) {
        int newIndex = lengthOfFrac(input.substring(i, input.length()));
        calculator.set(new BigFraction(input.substring(i, i + newIndex)));
        lastComputedValue = calculator.get();
        if ((i + newIndex) == input.length()) {
          printFrac(lastComputedValue);
        } // if
        i += newIndex;
      } else if (lastComputedValue.numerator().equals(BigInteger.ZERO) && (temp == '-')) {
        int newIndex = lengthOfFrac(input.substring(i, input.length()));
        calculator.set(new BigFraction(input.substring(i, i + newIndex)));
        lastComputedValue = calculator.get();
        if ((i + newIndex) == input.length()) {
          printFrac(lastComputedValue);
        } // if
        i += newIndex;
      } else if (isOperator(temp) && (BigFraction.isNumber(input.charAt(i + 2))
            || input.charAt(i + 2) == '-')) {
        int newIndex = lengthOfFrac(input.substring(i + 2, input.length()));
        BigFraction newFrac = new BigFraction(input.substring(i + 2, i + newIndex + 2));
        i += newIndex + 2;
        switch (temp) {
          case '+': calculator.add(newFrac);
                    break;
          case '-': calculator.subtract(newFrac);
                    break;
          case '*': calculator.multiply(newFrac);
                    break;
          case '/': calculator.divide(newFrac);
                    break;
          default : break;
        } // switch
        lastComputedValue = calculator.get();
        if (i == input.length()) {
          printFrac(lastComputedValue);
        } // if
      } else if (isLetter(input.charAt(i)) && input.length() > 1) {
        calculator.set(register.get(input.charAt(0)));
        lastComputedValue = calculator.get();
      } else if (isOperator(temp) && isLetter(input.charAt(i + 2))) {
        BigFraction tempFrac = register.get(input.charAt(i + 2));
        i += INDEX_OF_NEXT_FRACTION;
        switch (temp) {
          case '+': calculator.add(tempFrac);
                    break;
          case '-': calculator.subtract(tempFrac);
                    break;
          case '*': calculator.multiply(tempFrac);
                    break;
          case '/': calculator.divide(tempFrac);
                    break;
          default : break;
        } // switch
        lastComputedValue = calculator.get();
        if (i == input.length()) {
          printFrac(lastComputedValue);
        } // if
      } // if
    } // for
    mostRecentCalculation = lastComputedValue;
  } // readInput(String)
} // class InteractiveCalculator
