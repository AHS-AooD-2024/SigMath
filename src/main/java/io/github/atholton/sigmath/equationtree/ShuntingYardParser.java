package io.github.atholton.sigmath.equationtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ShuntingYardParser {

    private final Map<String, Operator> operators;

    //currently only supports functions with one parameter, ie sin, cos, tan
    private final Set<String> functions;

    private void addNode(Stack<ASTNode> stack, String operator) {
        if (operators.containsKey(operator))
        {
            final ASTNode rightASTNode = stack.pop();
            final ASTNode leftASTNode = stack.pop();
            stack.push(new ASTNode(operator, leftASTNode, rightASTNode));
        }
        else
        {
            //is a function
            final ASTNode leftASTNode = stack.pop();
            stack.push(new ASTNode(operator, leftASTNode, null));
        }
    }

    /**
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
        functions = new HashSet<>();
    }

    /**
     * Creates a ShuntingYardParser with default operators
     * 
     */
    public ShuntingYardParser() {
        operators = new HashMap<>();
        operators.put("^", new BaseOperator("^", true, 4));
        operators.put("*", new BaseOperator("*", false, 3));
        operators.put("/", new BaseOperator("/", false, 3));
        operators.put("+", new BaseOperator("+", false, 2));
        operators.put("-", new BaseOperator("-", false, 2));

        functions = new HashSet<>();
        functions.add("sin");
        functions.add("cos");
        functions.add("tan");
        functions.add("sqrt");
    }

    private static boolean isNumber(char c)
    {
        return c == '.' || (c >= '0' &&c <= '9');
    }
    private static boolean isNumber(String c)
    {
        for (int i = 0; i < c.length(); i++)
        {
            if (!isNumber(c.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Tests if token is a function
     * 
     * @param token String to test if is a function
     * @return returns true if token is a supported function
     */
    private boolean isFunction(String token)
    {
        return functions.contains(token);
    }
    /**
     * Tests if writing a function
     * ex) si returns true because it is writing sin
     * 
     * @param letter letter to be added to function
     * @param build current token
     * @return true if writing a function, false otherwise
     */
    private boolean writingFunction(char letter, StringBuilder build)
    {
        for (String function : functions)
        {
            String current = build.toString() + letter;
            //if valid length
            if (!(current.length() > function.length()))
            {
                if (function.substring(0, current.length()).equals(current))
                {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Tokenizes String equation in infix notation
     * @param input equation
     * @return list of tokens
     */
    private List<String> tokenize(final String input)
    {
        List<String> tokens = new ArrayList<>();
        StringBuilder build = new StringBuilder();
        char prevToken = '\0';
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == ' ') continue;
            if (writingFunction(c, build))
            {
                build.append(c);
            }
            else if (isNumber(c))
            {
                build.append(c);
            }
            else if (c == '-' && !isNumber(prevToken))
            {
                build.append(c);
            }
            else
            {
                if (build.length() != 0)
                {
                    tokens.add(build.toString());
                    build.setLength(0);
                }
                tokens.add(String.valueOf(c));
            }
            prevToken = c;
        }
        if (build.length() != 0)
        {
            tokens.add(build.toString());
        }
        return tokens;
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
        List<String> tokens = tokenize(input);

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
                            if (!operatorStack.isEmpty() && isFunction(operatorStack.peek()))
                            {
                                String function = operatorStack.pop();
                                addNode(operandStack, function);
                            }
                        } else {
                            addNode(operandStack, popped);
                        }
                    }
                    break;
                default:
                    if (isFunction(token)) {
                        operatorStack.push(token);
                    }
                    else if (operators.containsKey(token)) {
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
                        //or a function
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