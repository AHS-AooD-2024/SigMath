package io.github.atholton.sigmath.topics;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;

public class Derivative
{

    /**
     * Expects a * operator to do derivative to. Using Product rule
     */
    public static void productRule(ASTNode node) {
        
    }
    /**
     * Expects a function in a function
     * @param node
     */
    public static void chainRule(ASTNode node) {

    }
    /**
     * expects something like x ^ 2, expects carrot operator for simple powerRule
     * or expects x.
     * @param node
     */
    public static void powerRule(ASTNode node) {

    }
    /**
     * WILL ONLY CHANGE A FUNCTION, like SIN TO COS, should be used in chain rule
     * @param node
     */
    public static void deriveFunction(ASTNode node) {

    }
}
