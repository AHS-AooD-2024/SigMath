package io.github.atholton.sigmath.topics;

import java.util.List;

import static io.github.atholton.sigmath.equationtree.ASTNode.*;
import io.github.atholton.sigmath.equationtree.ASTNode.Type;
import io.github.atholton.sigmath.equationtree.BaseOperator;
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
        replaceNode(equation, rebuild(flattened, "+"));
    }
    /**
     * Expects a * operator to do derivative to. Using Product rule
     */
    private static void productRule(ASTNode node) {
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
    private static void chainRule(ASTNode node) {
        //exit cases for recursion
        if (node.type == Type.NUMBER) {
            node.setValue(0);
        }
        else if (node.type == Type.VARIABLE) {
            replaceNode(node, new ASTNode("1", null, null, Type.NUMBER));
        }
        else if (node.type == Type.FUNCTION) {
            ASTNode left = copy(node.getLeftASTNode());
            deriveFunction(node);
            //multiple with node that will be chain ruled
            chainRule(left);
            replaceNode(node, new ASTNode("*",  copy(node), copy(left), Type.OPERATOR));
        }
        else if (node.type == Type.OPERATOR) {
            if (node.getValue().equals("*")) {
                //powerRule calls chainrule until end, so then end
                productRule(node);
            }
                //doesn't work if smth like x^x or 2^x
            else if (node.getValue().equals("^")) {
                ASTNode left = copy(node.getLeftASTNode());
                powerRule(node);
                //multiple with node that will be chain ruled
                chainRule(left);
                replaceNode(node, new ASTNode("*",  copy(node), copy(left), Type.OPERATOR));
            }    
        }
    }
    /**
     * expects something like x ^ 2, expects carrot operator for simple powerRule
     * RETURNS POWER RULED NODE, THE REFERENCE TO NODE SHOULD NOT BE USED AS IT IS MANIPULATED WITHOUT BEING COPIED
     * @param node
     */
    private static void powerRule(ASTNode node) {
        if (node.type == Type.OPERATOR)
        {
            if (node.getValue().equals("^"))
            {
                ASTNode right = node.getRightASTNode();
                double num = Double.parseDouble(right.getValue());
                right.setValue(num - 1);
                replaceNode(node, new ASTNode("*", new ASTNode(String.valueOf(num), null, null, Type.NUMBER), 
                    copy(node), Type.OPERATOR));
            }
        }
    }
    /**
     * WILL ONLY CHANGE A FUNCTION, like SIN TO COS, should be used in chain rule
     * @param node
     */
    private static void deriveFunction(ASTNode node) {
        if (node.type == Type.FUNCTION) {
            if (node.getValue().equals("sin")) {
                node.setValue("cos");
            }
            else if (node.getValue().equals("cos")) {
                node.setValue("sin");
                ASTNode temp = new ASTNode("*", 
                    new ASTNode("-1", null, null, Type.NUMBER),
                    copy(node),
                    Type.OPERATOR
                );
                replaceNode(node, temp);
            }
            else if (node.getValue().equals("tan")) {
                node.setValue("sec");
                ASTNode temp = new ASTNode("^", 
                    copy(node),
                    new ASTNode("2", null, null, Type.NUMBER),
                    Type.OPERATOR
                );
                replaceNode(node, temp);
            }
            else if (node.getValue().equals("sec"))
            {
                ASTNode temp = new ASTNode("*", 
                    new ASTNode("tan", node.getLeftASTNode(), null, Type.FUNCTION),
                    copy(node),
                    Type.OPERATOR
                );
                replaceNode(node, temp);
            }
            else if (node.getValue().equals("ln")) 
            {
                //DO THINGS
            }
        }
    }
}
