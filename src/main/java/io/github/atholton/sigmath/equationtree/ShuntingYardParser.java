package io.github.atholton.sigmath.equationtree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import io.github.atholton.sigmath.equationtree.ASTNode.Type;

/**
 * @author nathanli5722
 * part taken from wikipedia psuedocode
 * part taken from a blog post
 * 
 * 
 */
public class ShuntingYardParser {

    private final Map<String, Operator> operators;

    //currently only supports functions with one parameter, ie sin, cos, tan
    private final Set<String> functions;

    private void addNode(Deque<ASTNode> stack, String operator) {
        if (operators.containsKey(operator))
        {
            final ASTNode rightASTNode = stack.poll();
            final ASTNode leftASTNode = stack.poll();
            stack.push(new ASTNode(operator, leftASTNode, rightASTNode, Type.OPERATOR));
        }
        else
        {
            //is a function
            final ASTNode leftASTNode = stack.poll();
            stack.push(new ASTNode(operator, leftASTNode, null, Type.FUNCTION));
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

        for (BaseOperator o : BaseOperator.operators)
        {
            operators.put(o.getSymbol(), o);
        }
        
        functions = new HashSet<>();
        functions.addAll(Arrays.asList(BaseOperator.functions));
    }

    private static boolean isNumber(char c)
    {
        return c == '.' || (c >= '0' &&c <= '9');
    }
    private static boolean isNumber(String c)
    {
        try
        {
            Double.parseDouble(c);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
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
        return tokenize2(input);
        // List<String> tokens = new ArrayList<>();
        // StringBuilder build = new StringBuilder();
        // char prevToken = '\0';
        // for (int i = 0; i < input.length(); i++)
        // {
        //     char c = input.charAt(i);
        //     if (c == ' ') {
        //         if (build.length() != 0)
        //         {
        //             tokens.add(build.toString());
        //             build.setLength(0);
        //         }
        //         tokens.add(String.valueOf(c));
        //     } else
        //     if (writingFunction(c, build) || isNumber(c))
        //     {
        //         build.append(c);
        //     }
        //     else if (c == '-' && !isNumber(prevToken))
        //     {
        //         tokens.add("-1");
        //         tokens.add("*");
        //     }
        //     else
        //     {
        //         if (build.length() != 0)
        //         {
        //             tokens.add(build.toString());
        //             build.setLength(0);
        //         }
        //         tokens.add(String.valueOf(c));
        //     }
        //     prevToken = c;
        // }
        // if (build.length() != 0)
        // {
        //     tokens.add(build.toString());
        // }
        // return tokens;
    }
    
    private static Pattern operatorPattern = Pattern.compile("[\\+\\-\\*\\/\\^\\(\\)\\[\\]]");
    private static Pattern notOperatorPattern = Pattern.compile("[^\\+\\-\\*\\/\\^\\(\\)\\[\\]]+");
    private List<String> tokenize2(String input) {
        // it is easier to not deal with spaces
        input = input.replace(" ", "");
        List<String> tokens = new ArrayList<>();

        Stream<String> split = operatorPattern.splitAsStream(input);
        Stream<String> antisplit = notOperatorPattern.splitAsStream(input);

        // empty strings make it so much harder, so we just don't
        Iterator<String> it1 = split.filter(s -> !s.isBlank()).iterator();
        Iterator<String> it2 = antisplit.filter(s -> !s.isBlank()).iterator();

        // zipper merge
        while(true) {
            if(it1.hasNext()) {
                tokens.add(it1.next());
                if(it2.hasNext()) {
                    tokens.add(it2.next());
                }
            } else {
                if(it2.hasNext()) {
                    tokens.add(it2.next());
                } else {
                    break;
                }
            }
        }
        
        System.out.println(tokens);
        return tokens;
    }
    
    public ASTNode convertLatexToAST(final String input)
    {
        return convertTokensToAST(tokenizeLaTeX(input));
    }
    /***
     * Convert an expression in infix notation to a tree
     *
     * @param input The expression, in infix notation.
     * @return An {@link ASTNode} that serves as the root of the AST.
     */
    public ASTNode convertInfixNotationToAST(final String input) 
    {
        return convertTokensToAST(tokenize(input));
    }
    private ASTNode convertTokensToAST(List<String> tokens)
    {
        final Deque<String> operatorStack = new ArrayDeque<>();
        final Deque<ASTNode> operandStack = new ArrayDeque<>();

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
                        popped = operatorStack.poll();
                        if("(".equals(popped)) {
                            if (!operatorStack.isEmpty() && isFunction(operatorStack.peek()))
                            {
                                String function = operatorStack.poll();
                                addNode(operandStack, function);
                            }
                            break;
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
                                operatorStack.poll();
                                addNode(operandStack, o2.getSymbol());
                            } else {
                                break;
                            }
                        }
                        operatorStack.push(token);
                    } else {
                        //is a number
                        Type t = Type.VARIABLE;
                        if (isNumber(token))
                        {
                            t = Type.NUMBER;
                        }
                        operandStack.push(new ASTNode(token, null, null, t));
                    }
                    break;
            }
        }
        while(!operatorStack.isEmpty()) {
            addNode(operandStack, operatorStack.poll());
        }
        return operandStack.poll();
    }
    /**
     * TODO: tokenize LaTeX input
     * @param input
     * @return
     */
    private List<String> tokenizeLaTeX(final String input) 
    {
        List<String> tokens = new ArrayList<>();
        StringBuilder build = new StringBuilder();
        char prevToken = '\0';
        boolean inCommand = false; 
    
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == ' ') continue;
            if (writingFunction(c, build) || isNumber(c) || c == '-' && !isNumber(prevToken))
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
}