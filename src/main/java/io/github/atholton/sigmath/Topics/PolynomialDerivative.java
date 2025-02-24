package io.github.atholton.sigmath.Topics;

import java.util.ArrayList;

/**
 * Represents all polynomial derivatives
 * @author Abhay Nagaraj
 */
public class PolynomialDerivative extends Derivative{
    public PolynomialDerivative() {
        super();
        formulaList.add("a*x^n");
        formulaList.add("a*x^n + b*x^n-1");
        formulaList.add("a*x^n + b*x^n-1 + c*x^n-2");
    }
}