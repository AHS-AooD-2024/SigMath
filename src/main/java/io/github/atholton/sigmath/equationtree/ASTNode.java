package io.github.atholton.sigmath.equationtree;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author nathanli5722
 * 
 * TODO: other than other todos, need to convert d x * x into x^2
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
    /**
     * @author dxmbname
     * @param factor
     */
    public void multByConstant(double factor) {
        rightASTNode = new ASTNode(getValue(), null, null, type);
        leftASTNode = new ASTNode(Double.toString(factor), null, null, Type.NUMBER);
        setValue("*");
        type = Type.OPERATOR;
    }
    
    /**
     * @author dxmbname
     * @param factor
     */
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
        simplify(this);
        simplificationRules(this);
        arithmetic(this);
    }
    private void simplify(ASTNode node)
    {
        if (node == null) return;

        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        simplify(left);
        simplify(right);

        //can improve
        if (node.type == Type.OPERATOR)
        {
            if (left.type == Type.NUMBER && right.type == Type.NUMBER)
            {
                arithmeticSolve(node);
            }
            else if (left.type == Type.OPERATOR || right.type == Type.OPERATOR)
            {
                distribute(node);

                //maybe combine like terms???
                combineLikeTerms(node);
            }
            else
            {
                //i don't even know whats left anymore
            }
        }
        //try to solve function, should change sqrt to ^ 0.5
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
                //could be operator or variable in function
                //might need to factor or smth to simplify if sqrt
                if (node.value.equals("sqrt"))
                {
                    ASTNode sqrt = new ASTNode("0.5", null, null, Type.NUMBER);
                    replaceNode(node, new ASTNode("^", node.leftASTNode, sqrt, Type.OPERATOR));
                }
            }
        }
    }
    /**
     * combine numbers, like 2 * 2 = 4;
     * @param node
     */
    private void arithmetic(ASTNode node)
    {
        if (node == null) return;
    
        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        arithmetic(left);
        arithmetic(right);

        if (node.type == Type.OPERATOR)
        {
            if (left.type == Type.NUMBER && right.type == Type.NUMBER)
            {
                arithmeticSolve(node);
            }
        }
    }
    private void arithmeticSolve(ASTNode node)
    {
        if (node == null) return;

        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        double leftValue = Double.parseDouble(left.getValue());
        double rightValue = Double.parseDouble(right.getValue());
        double value = computeOperator.get(node.getValue()).apply(leftValue, rightValue);
        replaceNode(node, new ASTNode(String.valueOf(value), null, null, Type.NUMBER));
    }

    //combine like terms when....
    private void combineLikeTerms(ASTNode node)
    {
        if (node == null) return;

        simplificationRules(node);

        //dunno if recursion needed
        //ASTNode left = node.getLeftASTNode();
        //ASTNode right = node.getRightASTNode();
        //for "+", but can you combine when not +???
        if (node.type == Type.OPERATOR && node.value.equals("+"))
        {
            //flattened list of nodes that are separated by + sign

            List<ASTNode> flattenedNodes = flatten(node, "+");
            System.out.println(flattenedNodes);

            Map<ASTNode, List<ASTNode>> likeTerms = new HashMap<>();

            //Get the "base" of each flattened node, and the coefficient of each as well.
            //if the base is equal, we can
            for (ASTNode flattenedNode : flattenedNodes) {
                ASTNode base = getBase(flattenedNode);
                ASTNode coefficient = getCoefficient(flattenedNode);

                likeTerms.putIfAbsent(base, new ArrayList<>());
                likeTerms.get(base).add(coefficient);
            }

            flattenedNodes = combine(likeTerms);
            System.out.println(flattenedNodes);
            System.out.println();
            ASTNode newTree = rebuild(flattenedNodes, "+");
            replaceNode(node, newTree);
        }

    }
    private List<ASTNode> combine(Map<ASTNode, List<ASTNode>> map)
    {
        List<ASTNode> list = new ArrayList<>();
        for (Map.Entry<ASTNode, List<ASTNode>> entry : map.entrySet())
        {
            //add all the coefficients
            //only expects numerical coefficients, which is always true unless this is also used in a factoring context
            double num = 0;
            List<ASTNode> coefficients = entry.getValue();
            for (ASTNode coefficient : coefficients)
            {
                if (coefficient.type == Type.NUMBER)
                {
                    num += Double.parseDouble(coefficient.value);
                }
                else
                {
                    System.out.println("Issue in combining like terms\nMissed something");
                }
            }
            if (num == 1.0)
            {
                list.add(entry.getKey());
            }
            else
            {
                ASTNode coefficient = new ASTNode(String.valueOf(num), null, null, Type.NUMBER);
            //multiply it with the base
                    list.add(new ASTNode("*", coefficient, entry.getKey(), Type.OPERATOR));
            }
        }
        return list;
    }
    private ASTNode getBase(ASTNode node)
    {
        if (node.type == Type.OPERATOR && node.value.equals("*")) 
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            //pick the other side
            if (left.type == Type.NUMBER) return right;
            if (right.type == Type.NUMBER) return left;
        }
        else if (node.type == Type.OPERATOR && node.value.equals("/"))
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            //pick the other side
            if (left.type == Type.NUMBER) 
            {
                //given 3/(x+1). The base is 1/(x+1)
                ASTNode temp = new ASTNode("/", new ASTNode("1.0", null, null, Type.NUMBER), right, Type.OPERATOR);
                return temp;
            }
            if (right.type == Type.NUMBER) return left;
        }
        //if its a number, then its just a constant, without any base
        //though its being multiplied so just put in 1
        if (node.type == Type.NUMBER) return new ASTNode("1.0", null, null, Type.NUMBER);

        //assume whole thing is base, but this is not always the case...
        return node;
    }
    private ASTNode getCoefficient(ASTNode node)
    {
        if (node.type == Type.NUMBER) return node;

        if (node.type == Type.OPERATOR && node.value.equals("*")) 
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            if (left.type == Type.NUMBER) return left;
            if (right.type == Type.NUMBER) return right;
        }
        else if (node.type == Type.OPERATOR && node.value.equals("*")) 
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            if (left.type == Type.NUMBER) return left;
            if (right.type == Type.NUMBER) 
            {
                ASTNode temp = new ASTNode("/", new ASTNode("1.0", null, null, Type.NUMBER), right, Type.OPERATOR);
                return temp;
            }
        }

        // if only, base then coefficient is one ex) (x + y) is a base with coefficient of 1
        return new ASTNode("1", null, null, Type.NUMBER);
    }
    //try to avoid factoring, cuz idk what im doing lol
    //just use quadratic formula and pretend the rest don't exist
    private void factor(ASTNode node)
    {

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

                    replaceNode(node, treeNode);
                    distributeToAll(node, otherOp, otherNode, op);

                    //do the current node operator to everything on tree Node with other node's value (number)
                    simplify(node);
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
                            ASTNode tree = rebuild(Arrays.asList(trees), "*");

                            replaceNode(node, tree);
                            simplify(node);
                        }
                    }
                }

            }
            else
            {
                //neither are trees...
                //lol

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
    /**
     * flattens tree, ie split into expressions by + or -
     * @param node
     * @param targetOperator
     * @return
     */
    private static List<ASTNode> flatten(ASTNode node, String targetOperator) {
        List<ASTNode> result = new ArrayList<>();
        flatten(node, targetOperator, result);
        return result;
    }

    private static void flatten(ASTNode node, String targetOperator, List<ASTNode> result) {
        if (node == null) return;

        if (node.type == Type.OPERATOR && node.value.equals("+")) {
            flatten(node.leftASTNode, targetOperator, result);
            flatten(node.rightASTNode, targetOperator, result);
        } 
        else if (node.type == Type.OPERATOR && node.value.equals("-"))
        {

        }
        else {
            result.add(node);
        }
    }
    /**
     * Rebuilds tree after it has been flattened
     * @param nodes
     * @param operator
     * @return
     */
    private static ASTNode rebuild(List<ASTNode> nodes, String operator) {
        if (nodes == null || nodes.isEmpty()) return null;
        ASTNode root = nodes.get(0);
    
        for (int i = 1; i < nodes.size(); i++) {
            root = new ASTNode(operator, root, nodes.get(i), Type.OPERATOR);
        }
    
        return root;
    }
    private ASTNode copy(final ASTNode node)
    {
        if (node == null) return null;
        return new ASTNode(node.getValue(), copy(node.getLeftASTNode()), copy(node.getRightASTNode()), node.type);
    }
    private void simplificationRules(ASTNode node)
    {
        if (node == null || node.type != Type.OPERATOR) return;

        String value = node.getValue();

        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        if (value.equals("^"))
        {
            if (right.type == Type.NUMBER)
            {
                if (Double.parseDouble(right.getValue()) == 1.0)
                {
                    replaceNode(node, left);
                }
            }
            else if (left.type == Type.NUMBER)
            {
                //0 ^ any real number
                if (Double.parseDouble(left.getValue()) == 0.0)
                {
                    replaceNode(node, left);
                }
            }
        }
        else if (value.equals("*"))
        {
            if (left.equals(right))
            {
                node.setValue("^");
                node.type = Type.OPERATOR;
                node.rightASTNode = new ASTNode("2.0", null, null, Type.NUMBER);
            }
            else if (left.type == Type.NUMBER)
            {
                double val = Double.parseDouble(left.getValue());
                if (val == 0.0)
                {
                    replaceNode(node, left);
                }
                else if (val == 1.0)
                {
                    replaceNode(node, right);
                }
            }
            else if (right.type == Type.NUMBER)
            {
                double val = Double.parseDouble(right.getValue());
                if (val == 0.0)
                {

                    replaceNode(node, right);
                }
                else if (val == 1.0)
                {
                    replaceNode(node, left);
                }
            }
        }
        else
        {
            simplificationRules(left);
            simplificationRules(right);
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
    private static void replaceNode(ASTNode node1, ASTNode node2)
    {
        //replacing everything should 
        node1.value = node2.value;
        node1.leftASTNode = node2.leftASTNode;
        node1.rightASTNode = node2.rightASTNode;
        node1.type = node2.type;
    }
    /**
     * Not meant to be used in application, LaTeX preferred
     * @return infix notation equation
     */
    public String toInfix()
    {
        StringBuilder s = new StringBuilder();
        toInfix(this, s);
        return s.toString();
    }
    private void toInfix(ASTNode node, StringBuilder s) {
        if (node == null) return;
    
        // Numbers and variables are appended directly
        if (node.type == Type.NUMBER || node.type == Type.VARIABLE) {
            s.append(node.getValue());
            return;
        }

        if (node.type == Type.FUNCTION) {
            s.append(node.getValue()).append("(");
            toInfix(node.getLeftASTNode(), s);
            s.append(")");
            return;
        }

        if (node.type == Type.OPERATOR) {
            s.append("(");
            
            toInfix(node.getLeftASTNode(), s);
            s.append(" ").append(node.getValue()).append(" ");
            toInfix(node.getRightASTNode(), s);
            
            s.append(")");
        }
    }
    /**
     * Prints the tree as a tree
     * Kinda scuffed but mostly readable
     * StackOverflow Code goes hard
     */
    public void printTree()
    {
        StringBuilder s = new StringBuilder();
        print(s, "", "");
        System.out.println(s.toString());
    }
    public void printInfix()
    {
        System.out.println(toInfix());
    }
    public void print()
    {
        printInfix();
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
    public String toString()
    {
        /*
        StringBuilder s = new StringBuilder();
        print(s, "", "");
        return s.toString();
        */
        return toInfix();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same reference
        if (obj == null || getClass() != obj.getClass()) return false;

        ASTNode other = (ASTNode) obj;

        // Check value and type
        if (!this.value.equals(other.value) || this.type != other.type) {
            return false;
        }

        // Recursively check left and right children
        return Objects.equals(leftASTNode, other.leftASTNode) && Objects.equals(rightASTNode, other.rightASTNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, leftASTNode, rightASTNode);
    }
}