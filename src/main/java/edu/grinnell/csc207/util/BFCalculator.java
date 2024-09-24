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
    public BigFraction MOST_RECENT_VALUE_CALCULATED = new BigFraction("0");

    /**
     * Gets the value of the most recent fraction that a calculation has
     * been done on.
     * @return a BigFraction.
     */
    public BigFraction get() {
        return MOST_RECENT_VALUE_CALCULATED;
    } // get()

    /**
     * Sets the most recently modified fraction to a given BigFraction value.
     * @param frac
     * A fraction of type BigFraction.
     */
    public void set(BigFraction frac) {
        MOST_RECENT_VALUE_CALCULATED = MOST_RECENT_VALUE_CALCULATED.add(frac);
    } // set()

    /**
     * Calls the add function from the BigFraction class to add a fraction to
     * the most recently modified fraction.
     * @param val
     * A fraction of type BigFraction.
     */
    public void add(BigFraction val) {
        if (MOST_RECENT_VALUE_CALCULATED.numerator().equals(BigInteger.ZERO)) {
            MOST_RECENT_VALUE_CALCULATED = new BigFraction(val.numerator(), val.denominator());
        } else {
            MOST_RECENT_VALUE_CALCULATED = MOST_RECENT_VALUE_CALCULATED.add(val);
        } // if
    } // add(BigFraction)

    /**
     * Calls the subtract function from the BigFraction class to subtract a fraction
     * from the most recently modified fraction.
     * @param val
     * A fraction of type BigFraction.
     */
    public void subtract(BigFraction val) {
        if (MOST_RECENT_VALUE_CALCULATED.numerator().equals(BigInteger.ZERO)) {
            BigInteger negativeNumerator = val.numerator().subtract((val.numerator()).multiply(BigInteger.valueOf(2)));
            MOST_RECENT_VALUE_CALCULATED = new BigFraction(negativeNumerator, val.denominator());
        } else {
            MOST_RECENT_VALUE_CALCULATED =  MOST_RECENT_VALUE_CALCULATED.subtract(val);
        } // if
    } // subtract(BigFraction)

    /**
     * Calls the multiply function from the BigFraction class to multiply a 
     * fraction by the most recently modified fraction.
     * @param val
     * A fraction of type BigFraction.
     */
    public void multiply(BigFraction val) {
        if (MOST_RECENT_VALUE_CALCULATED.numerator().equals(BigInteger.ZERO)) {
            return;
        } else {
            MOST_RECENT_VALUE_CALCULATED = MOST_RECENT_VALUE_CALCULATED.multiply(val);
        } // if
    } // multiply(BigFraction)

    /**
     * Calls the divide function from the BigFraction class to add a fraction
     * to the most recently modified fraction.
     * @param val
     * A fraction of type BigFraction.
     */
    public void divide(BigFraction val) {
        MOST_RECENT_VALUE_CALCULATED = MOST_RECENT_VALUE_CALCULATED.divide(val);
    } // divide(BigFraction)

    /**
     * Sets the most recently modified fraction to the value of 0.
     */
    public void clear() {
        MOST_RECENT_VALUE_CALCULATED = MOST_RECENT_VALUE_CALCULATED.subtract(MOST_RECENT_VALUE_CALCULATED);
    } // clear()

} // class BFCalculator