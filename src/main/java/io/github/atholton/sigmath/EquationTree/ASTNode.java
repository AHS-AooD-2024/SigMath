package io.github.atholton.sigmath.EquationTree;

public class ASTNode {

    private final String value;
    private final ASTNode leftASTNode;
    private final ASTNode rightASTNode;

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
     * Prints the tree as a tree
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