package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * Applies basic mathematic procedures on a given BigFraction using
 * the BigFraction class procedures.
 * @author Jafar Jarrar.
 */
public class BFCalculator {

  /** Holds the value of the most recent BigFraction that an operation
   * has been carried out on.
   */
  private BigFraction mostRecentValueCalculated = new BigFraction("0");

  /**
   * Gets the value of the most recent fraction that a calculation has
   * been done on.
   * @return a BigFraction.
   */
  public BigFraction get() {
    return mostRecentValueCalculated;
  } // get()

  /**
   * Sets the most recently modified fraction to a given BigFraction value.
   * @param frac
   * A fraction of type BigFraction.
   */
  public void set(BigFraction frac) {
    mostRecentValueCalculated = new BigFraction(frac.numerator(), frac.denominator());
  } // set()

  /**
   * Calls the add function from the BigFraction class to add a fraction to
   * the most recently modified fraction.
   * @param val
   * A fraction of type BigFraction.
   */
  public void add(BigFraction val) {
    if (mostRecentValueCalculated.numerator().equals(BigInteger.ZERO)) {
      mostRecentValueCalculated = new BigFraction(val.numerator(), val.denominator());
    } else {
      mostRecentValueCalculated = mostRecentValueCalculated.add(val);
    } // if
  } // add(BigFraction)

  /**
   * Calls the subtract function from the BigFraction class to subtract a fraction
   * from the most recently modified fraction.
   * @param val
   * A fraction of type BigFraction.
   */
  public void subtract(BigFraction val) {
    if (mostRecentValueCalculated.numerator().equals(BigInteger.ZERO)) {
      BigInteger negativeNumerator =
          val.numerator().subtract((val.numerator()).multiply(BigInteger.valueOf(2)));
      mostRecentValueCalculated = new BigFraction(negativeNumerator, val.denominator());
    } else {
      mostRecentValueCalculated =  mostRecentValueCalculated.subtract(val);
    } // if
  } // subtract(BigFraction)

  /**
   * Calls the multiply function from the BigFraction class to multiply a
   * fraction by the most recently modified fraction.
   * @param val
   * A fraction of type BigFraction.
   */
  public void multiply(BigFraction val) {
    if (mostRecentValueCalculated.numerator().equals(BigInteger.ZERO)) {
      return;
    } else {
      mostRecentValueCalculated = mostRecentValueCalculated.multiply(val);
    } // if
  } // multiply(BigFraction)

  /**
   * Calls the divide function from the BigFraction class to add a fraction
   * to the most recently modified fraction.
   * @param val
   * A fraction of type BigFraction.
   */
  public void divide(BigFraction val) {
    mostRecentValueCalculated = mostRecentValueCalculated.divide(val);
  } // divide(BigFraction)

  /**
   * Sets the most recently modified fraction to the value of 0.
   */
  public void clear() {
    mostRecentValueCalculated =
    mostRecentValueCalculated.subtract(mostRecentValueCalculated);
  } // clear()

} // class BFCalculator
