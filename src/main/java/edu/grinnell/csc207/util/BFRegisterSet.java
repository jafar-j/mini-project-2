package edu.grinnell.csc207.util;

/**
 * Stores and gets values from a register containing BigFraction values.
 * @author Jafar Jarrar.
 */
public class BFRegisterSet {

  /** The length of the registers array. */
  private static final int NUMBER_OF_LETTERS = 26;

  /** The array of fractions stored according to the user's letter command. */
  private BigFraction[] registers = new BigFraction[NUMBER_OF_LETTERS];

  /** The value of the character a in integer form. */
  private static final int INTEGER_VALUE_OF_A = 97;

  /**
   * Stores a BigFraction in the registers array according to the
   * index corresponding to the character value.
   * @param register
   * The character value specifying the index that the fraction
   * should be stored at.
   * @param val
   * The BigFraction to be stored in the register.
   */
  public void store(char register, BigFraction val) {
    int index = register - INTEGER_VALUE_OF_A;
    registers[index] = val;
  } // store(char, BigFraction)

  /**
   * Gets a BigFraction from the register containing the fraction
   * values.
   * @param register
   * The character value specifying the index that the fraction
   * should be extracted from.
   * @return the BigFraction that is sought.
   */
  public BigFraction get(char register) {
    int index = register - INTEGER_VALUE_OF_A;
    return registers[index];
  } // get(char)

} // class BFRegisterSet
