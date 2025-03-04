package io.github.atholton.sigmath.equationtree;

public class ASTNode {

    private String value;
    private ASTNode leftASTNode;
    private ASTNode rightASTNode;

    /***
     * Constructs a new AST node. There's no explicit specialization for leaf
     * nodes. Leaves are denoted by nodes where both the left and right node
     * is null.
     *
     * @param value The value held by the node.
     * @param leftASTNode The left node, or <code>null</code> if there isn't one.
     * @param rightASTNode The right node, or <code>null</code> if there isn't one.
     */
    public ASTNode(String value, ASTNode leftASTNode, ASTNode rightASTNode) {
        this.value = value;
        this.leftASTNode = leftASTNode;
        this.rightASTNode = rightASTNode;
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
        rightASTNode = new ASTNode(getValue(), null, null);
        leftASTNode = new ASTNode(Double.toString(factor), null, null);
        setValue("*");
    }

    public void addExponent(double exponent) {
        rightASTNode = new ASTNode(Double.toString(exponent), null, null);
        leftASTNode = new ASTNode(getValue(), null, null);
        setValue("^");
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
    public String convertToLatex()
    {
        return convertToLatex(this);
    }
    /**
     * The goal of this function is to simplify it into more readable terms.
     * 1) to remove exponents to the 1th power
     * 2) to combine like terms
     * 3) Distribute/Foil --> please help me
     */
    public void simplify()
    {
        //combine like terms first? or remove exponents...
        simplifyExponent(this);
        combineLikeTerms(this);
    }
    private static boolean isNumber(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }
    //combine like terms when....
    //
    private void combineLikeTerms(ASTNode node)
    {
        if (node == null) return;
        
        ASTNode left = node.getLeftASTNode();
        ASTNode right = node.getRightASTNode();

        combineLikeTerms(left);
        combineLikeTerms(right);

        switch(node.getValue())
        { 
            case "+":
                if (isNumber(left.getValue()) && isNumber(right.getValue()))
                {
                    double num = Double.parseDouble(left.getValue()) + Double.parseDouble(right.getValue());
                    replaceNode(node, new ASTNode(String.valueOf(num), null, null));
                }
        }
    }
    private void simplifyExponent(ASTNode node)
    {
        if (node == null) return;
        if (node.getValue().equals("^"))
        {
            if (isNumber(node.getRightASTNode().getValue()) && Double.parseDouble(node.getRightASTNode().getValue()) == 1.0)
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
    }
    
    /**
     * Prints the tree as a tree
     * Kinda scuffed but mostly readable
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