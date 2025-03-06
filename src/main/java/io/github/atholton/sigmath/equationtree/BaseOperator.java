
package io.github.atholton.sigmath.equationtree;
public class BaseOperator implements Operator {
    public static BaseOperator[] operators = {
        new BaseOperator("^", true, 4),
        new BaseOperator("*", false, 3),
        new BaseOperator("/", false, 3),
        new BaseOperator("+", false, 2),
        new BaseOperator("-", false, 2),
    };
    public static String[] functions = {
        "sin",
        "cos",
        "tan",
        "sqrt",
    };
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
    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (other instanceof Operator)
        {
            Operator o = (Operator)other;
            if (o.getSymbol().equals(symbol)) return true;
        }
        else if (other instanceof String)
        {
            String o = (String)other;
            if (o.equals(symbol)) return true;
        }
        return false;
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