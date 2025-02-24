
package io.github.atholton.sigmath.EquationTree;
public class BaseOperator implements Operator {

    private final String symbol;
    private final boolean rightAssociative;
    private final int precedence;

    /***
     * Creates a new BaseOperator.
     *
     * @param symbol The symbol used to represent the operator.
     * @param rightAssociative <code>true</code> if the operator is right
     * associative, and false otherwise.
     * @param precedence A numerical precedence for the operator relative to
     * all other operators in use.
     */
    public BaseOperator(String symbol, boolean rightAssociative,
                        int precedence) {
        this.symbol = symbol;
        this.rightAssociative = rightAssociative;
        this.precedence = precedence;
    }

    @Override
    public boolean isRightAssociative() {
        return rightAssociative;
    }

    @Override
    public int comparePrecedence(Operator o) {
        if(o instanceof BaseOperator) {
            BaseOperator other = (BaseOperator) o;
            return precedence > other.precedence ? 1 :
                    other.precedence == precedence ? 0 : -1;
        } else {
            // Defer the comparison to the second operator reflectively
            return -o.comparePrecedence(this);
        }
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}