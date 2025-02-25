package io.github.atholton.sigmath.EquationTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ShuntingYardParser {

    private final Map<String, Operator> operators;

    private static void addNode(Stack<ASTNode> stack, String operator) {
        final ASTNode rightASTNode = stack.pop();
        final ASTNode leftASTNode = stack.pop();
        stack.push(new ASTNode(operator, leftASTNode, rightASTNode));
    }

    /***
     * Creates a new ShuntingYardParser for the given operators.
     *
     * @param operators A collection of operators that should be recognized by
     * the parser.
     */
    public ShuntingYardParser(Collection<Operator> operators) {
        this.operators = new HashMap<>();
        for(Operator o : operators) {
            this.operators.put(o.getSymbol(), o);
        }
    }
    public ShuntingYardParser() {
        operators = new HashMap<>();
        operators.put("^", new BaseOperator("^", true, 4));
        operators.put("*", new BaseOperator("*", false, 3));
        operators.put("/", new BaseOperator("/", false, 3));
        operators.put("+", new BaseOperator("+", false, 2));
        operators.put("-", new BaseOperator("-", false, 2));
    }

    private static boolean isNumber(char c)
    {
        return c == '.' || (c >= '0' &&c <= '9');
    }
    /***
     * Convert an expression in infix notation to a tree
     *
     * @param input The expression, in infix notation.
     * @return An {@link ASTNode} that serves as the root of the AST.
     */
    public ASTNode convertInfixNotationToAST(final String input) {
        final Stack<String> operatorStack = new Stack<>();
        final Stack<ASTNode> operandStack = new Stack<>();
        //Splits String into tokens
        List<String> tokens = new ArrayList<>();

        String build = "";
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == ' ') continue;
            if (isNumber(c))
            {
                build += String.valueOf(c);
            }
            else
            {
                if (!build.equals(""))
                {
                    tokens.add(build);
                    build = "";
                }
                tokens.add(String.valueOf(c));
            }
        }
        if (!build.equals(""))
        {
            tokens.add(build);
        }

        main:
        for(String token : tokens) {
            String popped;
            switch(token) {
                case " ":
                    break;
                case "(":
                    operatorStack.push("(");
                    break;
                case ")":
                    while(!operatorStack.isEmpty()) {
                        popped = operatorStack.pop();
                        if("(".equals(popped)) {
                            continue main;
                        } else {
                            addNode(operandStack, popped);
                        }
                    }
                    throw new IllegalStateException("Unbalanced right " +
                            "parentheses");
                default:
                    if(operators.containsKey(token)) {
                        final Operator o1 = operators.get(token);
                        Operator o2;
                        while(!operatorStack.isEmpty() && null != (o2 =
                                operators.get(operatorStack.peek()))) {
                            if((!o1.isRightAssociative() &&
                                    0 == o1.comparePrecedence(o2)) ||
                                    o1.comparePrecedence(o2) < 0) {
                                operatorStack.pop();
                                addNode(operandStack, o2.getSymbol());
                            } else {
                                break;
                            }
                        }
                        operatorStack.push(token);
                    } else {
                        //is a number
                        operandStack.push(new ASTNode(token, null, null));
                    }
                    break;
            }
        }
        while(!operatorStack.isEmpty()) {
            addNode(operandStack, operatorStack.pop());
        }
        return operandStack.pop();
    }
}