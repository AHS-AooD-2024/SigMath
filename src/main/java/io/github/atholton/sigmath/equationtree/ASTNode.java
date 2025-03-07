package io.github.atholton.sigmath.equationtree;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.HashMap;

/**
 * @author nathanli5722
 */
public class ASTNode {
    private String value;
    public Type type;
    private ASTNode leftASTNode;
    private ASTNode rightASTNode;

    public enum Type{
        NUMBER,
        VARIABLE,
        OPERATOR,
        FUNCTION
    }

    public static Map<String, BiFunction<Double, Double, Double>> computeOperator;
    public static Map<String, Function<Double, Double>> computeFunction;
    /***
     * Constructs a new AST node. There's no explicit specialization for leaf
     * nodes. Leaves are denoted by nodes where both the left and right node
     * is null.
     *
     * @param value The value held by the node.
     * @param leftASTNode The left node, or <code>null</code> if there isn't one.
     * @param rightASTNode The right node, or <code>null</code> if there isn't one.
     */
    public ASTNode(String value, ASTNode leftASTNode, ASTNode rightASTNode, Type type) {
        this.value = value;
        this.leftASTNode = leftASTNode;
        this.rightASTNode = rightASTNode;
        this.type = type;
    }

    /***
     *
     * @return The value held by the node.
     */
    public String getValue() {
        return value;
    }

    public void setValue(double d) {
        value = Double.toString(d);
    }

    public void setValue(String s) {
        value = s;
    }

    /***
     *
     * @return The left node, or <code>null</code> if there isn't one.
     */
    public ASTNode getLeftASTNode() {
        return leftASTNode;
    }

    /***
     *
     * @return The right node, or <code>null</code> if there isn't one.
     */
    public ASTNode getRightASTNode() {
        return rightASTNode;
    }

    public void multByConstant(double factor) {
        rightASTNode = new ASTNode(getValue(), null, null, type);
        leftASTNode = new ASTNode(Double.toString(factor), null, null, Type.NUMBER);
        setValue("*");
        type = Type.OPERATOR;
    }

    public void addExponent(double exponent) {
        rightASTNode = new ASTNode(Double.toString(exponent), null, null, Type.NUMBER);
        leftASTNode = new ASTNode(getValue(), null, null, type);
        setValue("^");
        type = Type.OPERATOR;
    }
    /**
     * Method to Convert a {@link ASTNode} tree into LaTeX format String
     * @param node Root node of tree
     * @return Equation in LaTeX
     */
    //TODO: make it work
    private String convertToLatex(ASTNode node) {
        if (node == null) return "";
        
        String value = node.getValue();
        
        // If it's a leaf node, return its value directly
        if (node.getLeftASTNode() == null && node.getRightASTNode() == null) {
            return value;
        }

        // Recursively process left and right subtrees
        String left = convertToLatex(node.getLeftASTNode());
        String right = convertToLatex(node.getRightASTNode());

        // Handle different operators
        switch (value) {
            case "+":
            case "-":
                return "(" + left + " " + value + " " + right + ")";
            case "*":
                return left + " \\times " + right;
            case "/":
                return "\\frac{" + left + "}{" + right + "}";
            case "^":
                return "{" + left + "}^{" + right + "}";
            default:
                return value; // If it's a number or variable, return as is
        }
    }
    /**
     * Method to Convert a {@link ASTNode} tree into LaTeX format String
     * @return Equation in LaTeX
     */
    public String convertToLatex()
    {
        return convertToLatex(this);
    }
    /**
     * ignore this method.
     * Only used to initialize things needed for simplify
     */
    public static void initializeFuncs()
    {
        if (computeOperator == null && computeFunction == null)
        {
            computeOperator = new HashMap<>();
            computeOperator.put("+", (a, b) -> a + b);
            computeOperator.put("-", (a, b) -> a - b);
            computeOperator.put("*", (a, b) -> a * b);
            computeOperator.put("/", (a, b) -> a / b);
            computeOperator.put("^", (a, b) -> Math.pow(a, b));
            
            computeFunction = new HashMap<>();
            //Funciton reference operator ohmygosh its so cool
            computeFunction.put("sin", Math::sin);
            computeFunction.put("cos", Math::cos);
            computeFunction.put("tan", Math::tan);
            computeFunction.put("sqrt", Math::sqrt);
        }
    }
    /**
     * The goal of this function is to simplify it into more readable terms.
     * 1) to remove exponents to the 1th power
     * 2) to combine like terms
     * 3) Distribute/Foil --> please help me
     */
    public void simplify()
    {
        //add stuff to computeOperators
        initializeFuncs();
        //combine like terms first? or remove exponents...
        simplifyExponent(this);
        combineLikeTerms(this);
    }
    //combine like terms when....
    //TODO: combine when 2 * x + 4 * x to 6 * x, maybe through factor
    //TODO: work when like 3 + x + 4, or 2 * x * 4 ,cuz these can simplify, but x is getting in the way
    private void combineLikeTerms(ASTNode node)
    {
        if (node == null) return;

        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        combineLikeTerms(left);
        combineLikeTerms(right);

        //can improve
        if (node.type == Type.OPERATOR)
        {
            if (left.type == Type.NUMBER && right.type == Type.NUMBER)
            {
                double leftValue = Double.parseDouble(left.getValue());
                double rightValue = Double.parseDouble(right.getValue());
                double value = computeOperator.get(node.getValue()).apply(leftValue, rightValue);
                replaceNode(node, new ASTNode(String.valueOf(value), null, null, Type.NUMBER));
            }
            else if (left.type == Type.OPERATOR || right.type == Type.OPERATOR)
            {
                //try combine like terms, ex) 2 + x + 3, is a tree with + 2 + x 3
                if (isLikeTerms(left, right))
                {

                }
                //try distribute
                distribute(node);
            }
            else
            {
                //i don't even know anymore
            }
        }
        else if (node.type == Type.FUNCTION)
        {
            if (left.type == Type.NUMBER)
            {
                double leftValue = Double.parseDouble(left.getValue());
                double value = computeFunction.get(node.getValue()).apply(leftValue);
                replaceNode(node, new ASTNode(String.valueOf(value), null, null, Type.NUMBER));
            }
            else
            {
                //could be operator or variable
                //might need to factor or smth to simplify if sqrt
            }
        }
    }
    //3 + x + 4 will return true b/c 3 and 4 in x + 4 are like terms, so try to reorganize and then combine
    //or other way of combining idk
    //same with 2 * x * 4
    //should maybe work with 2 * x + 4 * x, idk
    //Idea -> 
    private boolean isLikeTerms(ASTNode node1, ASTNode node2)
    {
        return false;
    }

    //try to avoid factoring, cuz idk what im doing lol
    private void factor(ASTNode node)
    {
        //we're cooked
        //check for lower precedence, then higher.
        //if same base, like var, or exponent base, then do stuff
    }
    private void distribute(ASTNode node)
    {
        //no recursion... yet...
        //node is * and one of below is not number
        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        if (node.type == Type.OPERATOR)
        {
            BaseOperator op = BaseOperator.getOperator(node.getValue());
            if (left.type == Type.OPERATOR || right.type == Type.OPERATOR)
            {
                //reordering nodes
                ASTNode treeNode = left.type == Type.OPERATOR ? left : right;
                //otherNode could be a tree as well
                ASTNode otherNode = left.type == Type.OPERATOR ? right : left;


                BaseOperator otherOp = BaseOperator.getOperator(treeNode.getValue());

                //child should be lower precedent, ex) * (1 + x), + is child
                int num;
                if ((num = op.comparePrecedence(otherOp)) == 1)
                {
                    //something like 2 * (1 + x) is allowed here
                    //turns into 2 * 1 + 2 * x, + is top
                    //issue if is (x + 1)^2

                    replaceNode(node, treeNode);
                    distributeToAll(node, otherOp, otherNode, op);

                    //do the current node operator to everything on tree Node with other node's value (number)
                    combineLikeTerms(node);
                }
                else if (num == 2)
                {
                    //to the power of smth, am cooked lmao
                    //ex) (x + 1) ^ 2
                    //can convert into (x + 1) * (x + 1) and then distribute
                    if (otherNode.type == Type.NUMBER)
                    {
                        int number = (int)Double.parseDouble(otherNode.getValue());

                        //if it is an integer, cuz cant expand 2.5
                        if (number == Double.parseDouble(otherNode.getValue()))
                        {
                            ASTNode[] trees = new ASTNode[number];
                            for (int i = 0; i < trees.length; i++)
                            {
                                trees[i] = copy(treeNode);
                            }
                            for (int i = 0; i < trees.length - 1; i++)
                            {
                                ASTNode temp = new ASTNode("*", null, null, Type.OPERATOR);

                            }
                        }
                    }
                }

            }
            else
            {
                //etiher neither are trees
                //or both are trees but not actually anymore im a god

            }

        }
    }
    /**
     * distributes number or variable to tree, all checks are from distribute function
     * @param tree tree to add all the distributes numbers
     * @param lowerPrecedence if 2 * (x + 1), + is the lower precedence, b/c 2 * (x + 4 + 3 * x), should only dist once to 3 * x we need this to compare
     * @param numToDistribute  num or var applied to all of tree
     * @param operatorToDistribute operator that is used to apply num to tree, the * in 2 * (x + 1)
     */
    private void distributeToAll(ASTNode tree, BaseOperator lowerPrecedence, final ASTNode numToDistribute, final BaseOperator operatorToDistribute)
    {
        if (tree == null) return;

        //what is here is like 1 + 2 with *x, or smth
        if (tree.type == Type.OPERATOR && lowerPrecedence.comparePrecedence(BaseOperator.getOperator(tree.getValue())) == 0)
        {
            distributeToAll(tree.getLeftASTNode(), lowerPrecedence, numToDistribute, operatorToDistribute);    
            distributeToAll(tree.getRightASTNode(), lowerPrecedence, numToDistribute, operatorToDistribute);
        }
        else
        {
            ASTNode extra = new ASTNode(operatorToDistribute.getSymbol(), copy(tree), copy(numToDistribute), Type.OPERATOR);
            replaceNode(tree, extra);
        }

    }
    private ASTNode copy(final ASTNode node)
    {
        if (node == null) return null;
        return new ASTNode(node.getValue(), copy(node.getLeftASTNode()), copy(node.getRightASTNode()), node.type);
    }
    private void simplifyExponent(ASTNode node)
    {
        if (node == null) return;
        if (node.getValue().equals("^"))
        {
            if (node.getRightASTNode().type == Type.NUMBER && Double.parseDouble(node.getRightASTNode().getValue()) == 1.0)
            {
                replaceNode(node, node.getLeftASTNode());
            }
        }
        else
        {
            simplifyExponent(node.getLeftASTNode());
            simplifyExponent(node.getRightASTNode());
        }
    }
    /**
     * replaces first node with the second node,
     * removing the first node and putting the second node and 
     * its children at the first node's location
     * 
     * @param node1 node to be replaced
     * @param node2 node that will replace node1
     */
    public static void replaceNode(ASTNode node1, ASTNode node2)
    {
        //replacing everything should 
        node1.value = node2.value;
        node1.leftASTNode = node2.leftASTNode;
        node1.rightASTNode = node2.rightASTNode;
        node1.type = node2.type;
    }
    
    /**
     * Prints the tree as a tree
     * Kinda scuffed but mostly readable
     * StackOverflow Code goes hard
     */
    public void print()
    {
        StringBuilder s = new StringBuilder();
        print(s, "", "");
        System.out.println(s.toString());
    }
    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(value);
        buffer.append('\n');
        if (leftASTNode != null)
        {
            if (rightASTNode != null)
                leftASTNode.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            else
                leftASTNode.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        if (rightASTNode != null)
        {
            if (rightASTNode.rightASTNode != null)
                rightASTNode.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            else
                rightASTNode.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
    }
}