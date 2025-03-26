package io.github.atholton.sigmath.topics;

import java.util.List;

import static io.github.atholton.sigmath.equationtree.ASTNode.*;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;
import io.github.atholton.sigmath.equationtree.ASTNode;

public class Derivative
{
    /**
    * Derives equation
    */
    public static void derive(ASTNode equation)
    {
        List<ASTNode> flattened = flatten(equation, BaseOperator.getOperator("+"));
        for (ASTNode term : flattened) {
            chainRule(term);
        }
        replaceNode(equation, rebuild(flattened, BaseOperator.getOperator("+"));
    }
    /**
     * Expects a * operator to do derivative to. Using Product rule
     */
    public static void productRule(ASTNode node) {
        if (node == null) return;
        if (node.getValue().equals("*"))
        {
            ASTNode left = node.getLeftASTNode();
            ASTNode right = node.getRightASTNode();

            ASTNode tempLeft = copy(left);
            chainRule(tempLeft);
            ASTNode tempRight = copy(right);
            chainRule(tempRight);

            ASTNode temp = new ASTNode("+", 
                new ASTNode("*", tempLeft, copy(right), Type.OPERATOR),
                new ASTNode("*", copy(left), tempRight, Type.OPERATOR),
                Type.OPERATOR
                );
            replaceNode(node, temp);
        }
    }
    /**
     * Recursive Function meant to derive all/almost all terms.
     * @param node
     */
    public static void chainRule(ASTNode node) {
        //exit cases for recursion
        if (node.type == Type.NUMBER) {
            node.setValue(0);
        }
        else if (node.type == Type.VARIABLE) {
            replaceNode(node, new ASTNode("1", null, null, Type.NUMBER));
        }
        else if (node.type == Type.FUNCTION) {
            deriveFunction(node);
            chainRule(node);
        }
        else if (node.type == Type.OPERATOR) {
            if (node.getValue().equals("*")) {
                //powerRule calls chainrule until end, so then end
                productRule(node);
            }
                //doesn't work if smth like x^x or 2^x
            else if (node.getValue().equals("^")) {
                powerRule(node);
                chainRule(node.getLeftASTNode());
            }    
        }
    }
    /**
     * expects something like x ^ 2, expects carrot operator for simple powerRule
     * RETURNS POWER RULED NODE, THE REFERENCE TO NODE SHOULD NOT BE USED AS IT IS MANIPULATED WITHOUT BEING COPIED
     * @param node
     */
    public static void powerRule(ASTNode node) {
        if (node.type == Type.OPERATOR)
        {
            if (node.getValue().equals("^"))
            {
                ASTNode right = node.getRightASTNode();
                double num = Double.parseDouble(right.getValue());
                right.setValue(num - 1);
                replaceNode(node, new ASTNode("*", new ASTNode(String.valueOf(num), null, null, Type.NUMBER), 
                    node, Type.OPERATOR));
            }
        }
    }
    /**
     * WILL ONLY CHANGE A FUNCTION, like SIN TO COS, should be used in chain rule
     * @param node
     */
    public static void deriveFunction(ASTNode node) {
        if (node.type == Type.FUNCTION) {
            if (node.getValue().equals("sin")) {
                node.setValue("cos");
            }
            else if (node.getValue().equals("cos")) {
                //
            }
        }
    }
}
