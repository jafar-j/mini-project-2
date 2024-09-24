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
            } else if ((i == input.length() - 1) && (!BigFraction.isNumber(temp) && !isOperator(temp))) {
                rightSyntax = false;
                break;
            } // if
        } // for
        if (rightSyntax == false) {
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
    public static int lengthOfFrac (String input) {
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
    public static void printFrac (BigFraction frac) {
        PrintWriter pen = new PrintWriter(System.out, true);
        if (frac.numerator().equals(frac.denominator())) {
            pen.println(frac.numerator());
        } else if (frac.denominator().equals(BigInteger.valueOf(1))) {
            pen.println(frac.numerator() + "");
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
    public static boolean isLetter (char c) {
        if (c >= 97 && c <= 122) {
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
        BFRegisterSet register = new BFRegisterSet();
        BigFraction lastComputedValue = calculator.get();
        if ((input.length() == 5) && (input.substring(0, 5).equals("STORE") && lastComputedValue.numerator().equals(BigInteger.ZERO))) {
            System.err.println("*** ERROR [Store command received invalid register] ***");
            return;
        } else if ((input.length() == 7) && (input.substring(0,5).equals("STORE"))) {
            register.store(input.charAt(6), lastComputedValue);
            pen.println("STORED");
            return;
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
                    pen.println(input.substring(i, i + newIndex));
                } // if
                i += newIndex;
            } else if (isOperator(temp) && BigFraction.isNumber(input.charAt(i + 2))) {
                int newIndex = lengthOfFrac(input.substring(i + 2, input.length()));
                BigFraction newFrac = new BigFraction(input.substring(i + 2, i + newIndex + 2));
                i += newIndex + 2;
                switch(temp) {
                    case '+': calculator.add(newFrac);
                              break;
                    case '-': calculator.subtract(newFrac);
                              break;
                    case '*': calculator.multiply(newFrac);
                              break;
                    case '/': calculator.divide(newFrac);
                              break;
                } // switch
                lastComputedValue = calculator.get();
                if (i == input.length()) {
                    printFrac(lastComputedValue);
                } // if
            } else if (isOperator(temp) && (input.charAt(i + 2) == '-')) {
                int newIndex = lengthOfFrac(input.substring(i + 3, input.length()));
                BigFraction newFrac = new BigFraction(input.substring(i + 2, i + newIndex + 3));
                i += newIndex + 3;
                switch(temp) {
                    case '+': calculator.add(newFrac);
                              break;
                    case '-': calculator.subtract(newFrac);
                              break;
                    case 'x': calculator.multiply(newFrac);
                              break;
                    case '/': calculator.divide(newFrac);
                              break;
                } // switch
                lastComputedValue = calculator.get();
                if (i == input.length()) {
                    printFrac(lastComputedValue);
                } // if
            } else if (isLetter(temp) && input.length() == 1) {
                pen.println(register.get(temp).numerator() + "/" + register.get(temp).denominator());
            } 
        } // for
    } // readInput(String)
} // class InteractiveCalculator