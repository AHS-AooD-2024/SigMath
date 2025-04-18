package io.github.atholton.sigmath.equationtree;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.atholton.sigmath.util.Arrays2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author nathanli5722
 * 
 * 
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
        FUNCTION,
        PARENTHESIS
    }

    public static Map<String, BiFunction<Double, Double, Double>> computeOperator;
    public static Map<String, Function<Double, Double>> computeFunction;

    public static final String IMPLICIT_TIMES = "\\!implicit_times";

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

    private static String latexToken(String str) {
        if(Arrays2.contains(BaseOperator.specialCharacters, str)) {
            return " \\" + str + " ";
        } else if(Arrays2.contains(BaseOperator.functions, str)) {
            return " \\" + str + " ";
        }

        return str;
    }


    /**
     * Method to Convert a {@link ASTNode} tree into LaTeX format String
     * @param node Root node of tree
     * @return Equation in LaTeX
     */
    //TODO: make it work
    public static String convertToLatex(ASTNode node) {
        // return texify(node); // My previous attempt
        if (node == null) return "";
        
        String value = node.getValue();
        
        // If it's a leaf node, return its value directly
        if (node.getLeftASTNode() == null && node.getRightASTNode() == null) {
            return latexToken(value);
        }

        // Recursively process left and right subtrees
        String leftstr = convertToLatex(node.getLeftASTNode());
        String rightstr = convertToLatex(node.getRightASTNode());

        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        boolean doParen = false;
        if(node.type == Type.OPERATOR) {
            Operator nop = BaseOperator.getOperator(node.getValue());
            Operator lop = null;
            if(left != null)
                lop = BaseOperator.getOperator(left.getValue());
            Operator rop = null;
            if(right != null)
                rop = BaseOperator.getOperator(right.getValue());
    
            doParen = true;
            int comp = 0;
            if(lop != null) {
                comp = nop.comparePrecedence(lop);
            } else if (rop != null) {
                comp = nop.comparePrecedence(rop);
            } else {
                doParen = false;
            }

            if(doParen) {
                // if deeper op has lower precedence, we have to 
                // surround this op with parens
                // 
                // no parens
                // 3 * x ^ 2
                //   *      // prec = 2
                //  / \
                // 3   ^    // prec = 3
                //    / \
                //   x   2  // 2 < 3
                // yes parens
                // (3 * x) ^ 2
                //     ^    // prec = 3
                //    / \
                //   *   2  // prec = 2
                //  / \
                // 3   x    // 3 > 2
                doParen = comp > 0; 
            }
        }

        String leftParen;
        String rightParen;

        if(doParen) {
            leftParen = "\\left(";
            rightParen = "\\right)";
        } else {
            leftParen = "";
            rightParen = "";
        }

        // Handle different operators
        switch (value) {
            case "+":
            case "-":
            case "=":
                return leftParen + leftstr + " " + value + " " + rightstr + rightParen;
            case "*":
                return leftParen + leftstr + " \\times " + rightstr + rightParen;
            case IMPLICIT_TIMES:
                return implicitTimesTex(node.getLeftASTNode(), node.getRightASTNode(), leftstr, rightstr);
            case "/":
                return leftParen + "\\frac{" + leftstr + "}{" + rightstr + "}" + rightParen;
            case "^":
                return leftParen + "{" + leftstr + "}" + rightParen + "^{" + rightstr + "}";
            case "sqrt":
                return "\\sqrt{" + leftstr + "}";
            case "cbrt":
                return "\\sqrt[3]{" + leftstr + "}";
            default:
                return defaultLatex(node, value, leftstr);
                
        }
    }

    private static String defaultLatex(ASTNode node, String value, String left) {
        if(node.type == Type.FUNCTION && !node.value.contains("(")) // The dumbest assertion I have ever had to make, and I have had to
                                                                        // nonnull assert things that have been checked for null and enums ansd -o Wifojsmdzf lnkm I feel like Im going insane
            return "\\" + node.value + " \\left(" + left + "\\right)";    
        else 
            return latexToken(value); // If it's a number or variable, return as is
    }

    public static String texify(ASTNode node) {
        StringBuilder sb = new StringBuilder();
        texify(node, sb);
        // return sb.substring(1, sb.length() - 1);
        return sb.toString();
    }

    private static void texify(ASTNode node, StringBuilder sb) {
        if(node != null){
            System.out.println(node.type + " =================== ");
            switch (node.type) {
                case OPERATOR:
                    texifyOperator(node, sb);
                    break;

                case FUNCTION:
                    texifyFunction(node, sb);
                    break;
                
                case NUMBER:
                    sb.append(node.getValue());
                    break;

                case VARIABLE:
                    texifyVariable(node, sb);
                    break;
            
                default:
                    break;
            }
        }
    }

    private static void texifyOperator(ASTNode node, StringBuilder sb) {
        // special case; division fractions act closer to functions
        if(node.getValue().equals("/")) {
            sb.append("\\frac{");
            texify(node.getLeftASTNode(), sb);
            sb.append("}{");
            texify(node.getRightASTNode(), sb);
            sb.append("}");
        } else {
            sb.append("{");
            texify(node.getLeftASTNode(), sb);
            sb.append("}")
                .append(texifyOperatorString(node.getValue()))
                .append("{");
            texify(node.getRightASTNode(), sb);
            sb.append("}");
        }
    }

    private static void texifyFunction(ASTNode node, StringBuilder sb) {
        sb.append("\\")
            .append(node.getValue())
            .append("{");
        texify(node.getLeftASTNode(), sb);
        sb.append("}");
    }

    private static void texifyVariable(ASTNode node, StringBuilder sb) {
        if(Arrays2.contains(BaseOperator.specialCharacters, node.getValue())) {
            sb.append("\\");
        }
        sb.append(node.getValue());
    }

    private static String texifyOperatorString(String operatorString) {
        if(operatorString.equals("*")) return " \\cdot ";
        else                                    return operatorString;
    }

    private static String implicitTimesTex(ASTNode left, ASTNode right, String leftStr, String rightStr) {
        if(left == null) return rightStr;
        if(right == null) return leftStr;
        if(
            // xy
            left.type == Type.VARIABLE && right.type == Type.VARIABLE ||
            // 2x
            left.type == Type.NUMBER && right.type != Type.NUMBER ||
            left.value == IMPLICIT_TIMES || right.value == IMPLICIT_TIMES
            ) {
            return "\\left(" + leftStr + rightStr + "\\right)";
        } else {
            return "\\left(" + leftStr + " \\cdot " + rightStr + "\\right)";
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
            computeFunction.put("ln", Math::log);
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
        rewrite(this);
        //add stuff to computeOperators
        initializeFuncs();
        for (int i = 0; i < 50; i++)
        {
            ASTNode copy = copy(this);
            simplify(this);
            if (copy.equals(this))
            {
                break;
            }
        }
        //re order terms for polynomials
        reorderTerms();
    }
    private void rewrite(ASTNode node)
    {
        //writing negative into * -1 is created in tokenizer
        if (node == null) return;
        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        rewrite(left);
        rewrite(right);

        if (node.value.equals("/"))
        {
            ASTNode temp = new ASTNode("*", left, new ASTNode(
                "^", right, new ASTNode(
                    "-1", null, null, Type.NUMBER
                ), Type.OPERATOR
            ), Type.OPERATOR);
            replaceNode(node, temp);
        }
        if (node.value.equals("sqrt"))
        {
            ASTNode sqrt = new ASTNode("0.5", null, null, Type.NUMBER);
            replaceNode(node, new ASTNode("^", node.leftASTNode, sqrt, Type.OPERATOR));
        }
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
            simplificationRules(node);
            arithmetic(node);
            if (left.type == Type.OPERATOR || right.type == Type.OPERATOR)
            {
                distribute(node);
                //maybe combine like terms???
                combineLikeTerms(node);
            }
            else
            {
                //i don't even know whats left anymore
            }
            simplificationRules(node);
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
    private void reorderTerms()
    {
        Operator plus = BaseOperator.getOperator("+");
        List<ASTNode> split = flatten(this, plus);
        
        //do stuff
    }
    /**
     * combine numbers, like 2 * 2 = 4;
     * @param node
     */
    private void arithmetic(ASTNode node)
    {
        if (node.type != Type.OPERATOR) return;
        Operator op = BaseOperator.getOperator(node.value);
        List<ASTNode> list = flatten(copy(node), op);
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = i + 1; j < list.size(); j++)
            {
                if (j >= list.size() || i >= list.size()) continue;
                if (list.get(i).type == Type.NUMBER && list.get(j).type == Type.NUMBER)
                {
                    ASTNode a = list.get(i), b = list.get(j);
                    double leftValue = Double.parseDouble(a.value);
                    double rightValue = Double.parseDouble(b.value);
                    double value = computeOperator.get(node.getValue()).apply(leftValue, rightValue);
                    list.remove(a);
                    list.remove(b);
                    list.add(new ASTNode(String.valueOf(value), null, null, Type.NUMBER));
                }
            }
        }
        replaceNode(node, rebuild(list, op.getSymbol()));
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

        //dunno if recursion needed
        //ASTNode left = node.getLeftASTNode();
        //ASTNode right = node.getRightASTNode();
        //for "+", but can you combine when not +???
        if (node.type == Type.OPERATOR && (node.value.equals("+") || node.value.equals("*")))
        {
            //flattened list of nodes that are separated by + sign
            Operator operator = BaseOperator.getOperator(node.value);

            List<ASTNode> flattenedNodes = flatten(node, operator);

            //System.out.println(node.value);
            //System.out.println(flattenedNodes);

            Map<ASTNode, List<ASTNode>> likeTerms = new HashMap<>();

            //Get the "base" of each flattened node, and the coefficient of each as well.
            //if the base is equal, we can
            for (ASTNode flattenedNode : flattenedNodes) {
                ASTNode base = getBase(flattenedNode, operator);
                ASTNode coefficient = getCoefficient(flattenedNode, operator);

                likeTerms.putIfAbsent(base, new ArrayList<>());
                likeTerms.get(base).add(coefficient);
            }

            flattenedNodes = combine(likeTerms, operator);
            //System.out.println(flattenedNodes);
            //System.out.println();
            ASTNode newTree = rebuild(flattenedNodes, operator.getSymbol());
            replaceNode(node, newTree);
        }

    }
    private List<ASTNode> combine(Map<ASTNode, List<ASTNode>> map, Operator operator)
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
                    //num = computeOperator.get(operator.getSymbol()).apply(Double.parseDouble(coefficient.value), num);
                    num += Double.parseDouble(coefficient.value);
                }
                else
                {
                    System.err.println("Issue in combining like terms\nMissed something");
                }
            }
            if (operator.getSymbol().equals("+") && num == 0)
            {
                continue;
            }
            else if (num == 1.0)
            {
                list.add(entry.getKey());
            }
            else
            {
                ASTNode coefficient = new ASTNode(String.valueOf(num), null, null, Type.NUMBER);
                //multiply it with the base
                String above = operator.getSymbol().equals("+") ? "*" : "^";

                //Absolute spaghetti code LMAO
                if (above.equals("^") && entry.getKey().type == Type.NUMBER)
                {
                    list.add(new ASTNode(above, coefficient, entry.getKey(), Type.OPERATOR));
                }
                else
                {
                    list.add(new ASTNode(above, entry.getKey(), coefficient, Type.OPERATOR));
                }
            }
        }
        return list;
    }
    //Won't combine if both sides are variables, because it doesn't know which is the base (it could be one, or the other or both, just depends on the other terms)
    private ASTNode getBase(ASTNode node, Operator operator)
    {
        String above = operator.getSymbol().equals("+") ? "*" : "^";

        if (node.type == Type.OPERATOR && node.value.equals(above)) 
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            //pick the other side
            if (left.type == Type.NUMBER) return right;
            if (right.type == Type.NUMBER) return left;
        }
        //if its a number, then its just a constant, without any base
        //though its being multiplied so just put in 1
        if (node.type == Type.NUMBER && operator.getSymbol().equals("+")) return new ASTNode("1.0", null, null, Type.NUMBER);

        //assume whole thing is base, but this is not always the case...
        return node;
    }
    
    private ASTNode getCoefficient(ASTNode node, Operator operator)
    {
        if (node.type == Type.NUMBER && operator.getSymbol().equals("+")) return node;

        String above = operator.getSymbol().equals("+") ? "*" : "^";

        if (node.type == Type.OPERATOR && node.value.equals(above)) 
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            if (left.type == Type.NUMBER) return left;
            if (right.type == Type.NUMBER) return right;
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

                if (op.comparePrecedence(otherOp) != 1)
                {
                    treeNode = right.type == Type.OPERATOR ? right : left;
                    otherNode = right.type == Type.OPERATOR ? left : right;

                    otherOp = BaseOperator.getOperator(treeNode.getValue());
                }

                //child should be lower precedent, ex) * (1 + x), + is child
                int num;
                if ((num = op.comparePrecedence(otherOp)) == 1)
                {
                    //something like 2 * (1 + x) is allowed here
                    //turns into 2 * 1 + 2 * x, + is top

                    replaceNode(node, treeNode);
                    distributeToAll(node, otherOp, otherNode, op);

                    //do the current node operator to everything on tree Node with other node's value (number)
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
                            distribute(node);
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
    public static List<ASTNode> flatten(ASTNode node, Operator targetOperator) {
        List<ASTNode> result = new ArrayList<>();
        flatten(node, targetOperator.getSymbol(), result);
        return result;
    }

    private static void flatten(ASTNode node, String targetOperator, List<ASTNode> result) {
        if (node == null) return;

        if (node.type == Type.OPERATOR && node.value.equals(targetOperator)) {
            flatten(node.leftASTNode, targetOperator, result);
            flatten(node.rightASTNode, targetOperator, result);
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
    public static ASTNode rebuild(List<ASTNode> nodes, String operator) {
        if (nodes == null || nodes.isEmpty()) return null;
        ASTNode root = nodes.get(0);
    
        for (int i = 1; i < nodes.size(); i++) {
            root = new ASTNode(operator, root, nodes.get(i), Type.OPERATOR);
        }
    
        return root;
    }
    public static ASTNode copy(final ASTNode node)
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
                if (Double.parseDouble(right.getValue()) == 0.0)
                {
                    replaceNode(node, new ASTNode("1", null, null, Type.NUMBER));
                }
            }
            else if (left.type == Type.NUMBER)
            {
                //0 ^ any real number
                if (Double.parseDouble(left.getValue()) == 0.0 || Double.parseDouble(left.getValue()) == 1.0)
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
        simplificationRules(left);
        simplificationRules(right);
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

    public static void printTree(ASTNode node) {
        if(node == null) System.out.println("");
        else             node.printTree();
    }
    public static void printInfix(ASTNode node) {
        if(node == null) System.out.println("");
        else             node.printInfix();
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