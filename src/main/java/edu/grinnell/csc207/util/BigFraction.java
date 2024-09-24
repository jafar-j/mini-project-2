package edu.grinnell.csc207.util;

import java.math.BigInteger;

/**
 * Creates and applies methods on a value of type BigFraction.
 * 
 * @author Samuel Rebelsky, modified by Jafar Jarrar.
 */
public class BigFraction {

  /** The numerator of the fraction. Can be positive, zero or negative. */
  BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * Warning! Not yet stable.
   *
   * @param numerator
   *                    The numerator of the fraction.
   * @param denominator
   *                    The denominator of the fraction.
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    BigInteger greatestCommonDivisor = numerator.gcd(denominator);
    this.num = numerator.divide(greatestCommonDivisor);
    this.denom = denominator.divide(greatestCommonDivisor);
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   *
   * Warning! Not yet stable.
   *
   * @param numerator
   *                    The numerator of the fraction.
   * @param denominator
   *                    The denominator of the fraction.
   */
  public BigFraction(int numerator, int denominator) {
    BigInteger greatestCommonDivisor = BigInteger.valueOf(numerator).gcd(BigInteger.valueOf(denominator));
    this.num = BigInteger.valueOf(numerator).divide(greatestCommonDivisor);
    this.denom = BigInteger.valueOf(denominator).divide(greatestCommonDivisor);
  } // BigFraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   *
   * Warning! Not yet implemented.
   *
   * @param str
   *            The fraction in string form
   */
  public BigFraction(String str) {
    if (!str.contains("/")) {
      this.num = new BigInteger(str);
      this.denom = BigInteger.valueOf(1);
    } else if (str.contains("-")) {
      int indexOfSlash = countDigits(str.substring(1, str.length()));
      BigInteger numerator = new BigInteger(str.substring(0, indexOfSlash + 1));
      BigInteger denominator = new BigInteger(str.substring(indexOfSlash + 2, str.length()));
      BigInteger greatestCommonDivisor = numerator.gcd(denominator);
      this.num = numerator.divide(greatestCommonDivisor);
      this.denom = denominator.divide(greatestCommonDivisor);
    } else {
      int indexOfSlash = countDigits(str);
      BigInteger numerator = new BigInteger(str.substring(0, indexOfSlash));
      BigInteger denominator = new BigInteger(str.substring(indexOfSlash + 1, str.length()));
      BigInteger greatestCommonDivisor = numerator.gcd(denominator);
      this.num = numerator.divide(greatestCommonDivisor);
      this.denom = denominator.divide(greatestCommonDivisor);
    } // if
  } // BigFraction(String)

  /**
   * Build a new BigFraction with only a numerator and no denominator.
   * This is to check for the case that a whole number is to be
   * converted to a fraction.
   * 
   * @param number
   *               Whole number to be converted to a BigFraction.
   */
  public BigFraction(BigInteger number) {
    this.num = number;
  } // BigFraction(BigInteger)

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /** Checks if given character is a number.
   * @param c
   * Character to be checked.
   * @return true if it is a number, false otherwise.
   */
  public static boolean isNumber(char c) {
    if (c >= 48 && c <= 57) {
      return true;
    } // if
    return false;
  } // isNumber(char)

  /**
   * Counts the number of digits before the slash in a fraction.
   * @param input
   * BigFraction for which the digits in the numerator or denominator
   * should be counted.
   * @return number of digits
   */
  public static int countDigits(String input) {
    int finalIndex = 0;
    for (int i = 0; i < input.length(); i++) {
      if (!(isNumber(input.charAt(i)))) {
        break;
      } // if
      finalIndex++;
    } // for
    return finalIndex;
  } // countDigits(String)

  /**
   * Add another faction to this fraction.
   * @param addend
   * The fraction to add.
   * @return the result of the addition.
   */
  public BigFraction add(BigFraction addend) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the product of this object's
    // denominator and addend's denominator
    resultDenominator = this.denom.multiply(addend.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(addend.denom)).add(addend.num.multiply(this.denom));

    // Return the computed value
    return (new BigFraction(resultNumerator, resultDenominator)).simplify();
  } // add(BigFraction)

  /**
   * Multiplies the fraction that the method is called on by
   * the fraction in the parameter.
   * @param multiplend
   * One of the fractions to be multiplied. 
   * @return BigFraction of the result of multiplication.
   */
  public BigFraction multiply(BigFraction multiplend) {
    BigInteger resultDenominator = this.denom.multiply(multiplend.denom);
    BigInteger resultNumerator = this.num.multiply(multiplend.num);
    return ((new BigFraction(resultNumerator, resultDenominator)).simplify());
  } // multiply(BigFraction)

  /**
   * Divides the fraction that the method is called on by
   * the fraction in the parameter.
   * @param dividend
   * One of the fractions to be divided. 
   * @return BigFraction of the result of division.
   */
  public BigFraction divide(BigFraction dividend) {
    BigFraction reciprocal = new BigFraction(dividend.denom, dividend.num);
    BigInteger resultDenominator = this.multiply(reciprocal).denom;
    BigInteger resultNumerator = this.multiply(reciprocal).num;
    return (new BigFraction(resultNumerator, resultDenominator)).simplify();
  } // divide(BigFraction)

  /**
   * Subtracts the fraction in the parameter from the fraction
   * that the method is being called on.
   * @param subtractend
   * The fraction to be subtracted.
   * @return BigFraction of the result of subtraction.
   */
  public BigFraction subtract(BigFraction subtractend) {
    BigInteger resultNumerator = (this.num.multiply(subtractend.denom)).subtract(subtractend.num.multiply(this.denom));
    BigInteger resultDenominator = this.denom.multiply(subtractend.denom);
    return (new BigFraction(resultNumerator, resultDenominator)).simplify();
  } // subtract(BigFraction)

  /**
   * Simplifies the fraction that it is called on tby finding and dividing
   * by the greatest common divisor.
   * @return the new simplified fraction.
   */
  public BigFraction simplify() {
    BigInteger greatestCommonDivisor = this.num.gcd(this.denom);
    BigInteger resultNumerator = this.num.divide(greatestCommonDivisor);
    BigInteger resultDenominator = this.denom.divide(greatestCommonDivisor);
    if (resultDenominator.compareTo(BigInteger.ZERO) < 0) {
      resultDenominator.add((BigInteger.valueOf(2)).multiply(resultDenominator));
      resultNumerator.subtract(resultNumerator.multiply(BigInteger.valueOf(2)));
    } // if
    return new BigFraction(resultNumerator, resultDenominator);
  } // simplify()

  /**
   * Get the denominator of this fraction.
   *
   * @return the denominator
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   *
   * @return the numerator
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this fraction to a string for ease of printing.
   *
   * @return a string that represents the fraction.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
    } else if (this.denom.equals(BigInteger.valueOf(1))) {
      return this.num + "";
    } // if

    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()

} // class BigFraction
