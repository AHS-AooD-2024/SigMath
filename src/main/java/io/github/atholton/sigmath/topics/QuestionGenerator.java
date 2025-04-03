package io.github.atholton.sigmath.topics;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.atholton.sigmath.equationtree.ASTNode;
import io.github.atholton.sigmath.equationtree.ShuntingYardParser;
import io.github.atholton.sigmath.util.Lazy;

public class QuestionGenerator {
    private Topic topic;
    private Random random;

    public QuestionGenerator(Topic topic) {
        this.topic = topic;
        this.random = new Random();
    }

    public String generateQuestion() {
        String formula = topic.getFormula(0); // Retrieve a formula for the selected topic
        String formattedQuestion = replaceVariables(formula); // Replace variables with numbers
        return new String(formattedQuestion);
    }

    public static String testReplaceVars(String eq) {
        return new QuestionGenerator(null).replaceVariables(eq);
    }

    private static final Lazy<Pattern> VAR_PATTERN = new Lazy<>(() -> Pattern.compile("(\\\\p?var)|(\\\\coeff)"));

    private String replaceVariables(String equation) {
        Map<String, Double> variables = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int oldI = 0;
        Matcher matcher = VAR_PATTERN.get().matcher(equation);
        while(matcher.find()) {
            int i = matcher.start();
            sb.append(equation.substring(oldI, i));

            int name0 = equation.indexOf("{", i);
            int name1 = equation.indexOf(",", i);
            int name2 = equation.indexOf("}", i);

            if(name1 == -1) {
                name1 = name2;
            }
            
            
            if(name0 == -1 || name1 == -1) {
                throw new IllegalArgumentException("Cannot parse var at index " + i);
            }

            String name = equation.substring(name0, name1);
            double x;
            String group = matcher.group();

            if(variables.containsKey(name)) {
                x = variables.get(name).doubleValue();
            } else {
                int minRange = 0;
                int maxRange = 10;
                int minRange0 = equation.indexOf(",", name0);
                if(minRange0 != -1) {
                    int minRange1 = equation.indexOf(",", minRange0 + 1);
                    int minRange2 = equation.indexOf("}", minRange0 + 1);
                    
                    if(minRange1 == -1) {
                        minRange1 = minRange2;
                    }
                    i = minRange1 + 1;

                    System.out.println("0: " + minRange0 + "\n1: " + minRange1);
                    String trimmed = equation.substring(minRange0 + 1, minRange1).strip();
                    minRange = Integer.parseInt(trimmed);

                    int maxRange0 = equation.indexOf(",", minRange1);
                    if(maxRange0 != -1) {
                        int maxRange1 = equation.indexOf(",", maxRange0 + 1);
                        int maxRange2 = equation.indexOf("}", maxRange0 + 1);
                        
                        if(maxRange1 == -1) {
                            maxRange1 = maxRange2;
                        }
                        i = maxRange1 + 1;
                        
                        String trimmed2 = equation.substring(maxRange0 + 1, maxRange1).strip();
                        maxRange = Integer.parseInt(trimmed2);
                    }
                }

                System.out.println("rand from " + minRange + ".." + maxRange);
                x = random.nextInt(minRange, maxRange);
                variables.put(name, Double.valueOf(x));
            }

            switch (group) {
                case "\\coeff":
                    sb.append(toCoefficientString(x));
                    break;
                case "\\pvar":
                    sb.append(toAdditionString(x));
            
                default:
                    sb.append(toNumberString(x));
                    break;
            }

            oldI = i;
        }
        sb.append(equation.substring(oldI));

        return sb.toString();

        // return equation.replace("a", String.valueOf(random.nextInt(20) + 1))  // Replace 'a' with random number
        //                .replace("b", String.valueOf(random.nextInt(20) + 1)) // Replace 'b' with random number
        //                .replace("c", String.valueOf(random.nextInt(20) + 1))
        //                .replace("d", String.valueOf(random.nextInt(20) + 1))
        //                .replace("e", String.valueOf(random.nextInt(20) + 1))
        //                .replace("n1", String.valueOf(random.nextInt(5) + 1))
        //                .replace("n2", String.valueOf(random.nextInt(5) + 1))
        //                .replace("n3", String.valueOf(random.nextInt(5) + 1));//this is a horrendous line btw
    }

    /**
     * Gets the string representation of a double, with some exceptions.
     * <ul>
     * <li> If the given number is 1, the empty string ({@code ""}) is returned. </li> 
     * <li> If the given number is -1, a single minus sign ({@code "-"}) 
     * is returned. </li>
     * <li> If the given number is a mathematical integer (such that
     * {@code &lfloor;x&rfloor; == x}), the string representation of that
     * integer is returned, without the decimal point (e.g. 2.0 => "2"). </li>
     * <li> If the given number {@link Double#isInfinite(Double) is infinite},
     * the latex command for infinity ({@code " \\infn "}) is returned, 
     * with space padding. </li>
     * <li> If the given number {@link Double#isNaN(Double) is NaN}, simply
     * {@code "NaN"} is returned. </li>
     * <li> Otherwise, the string representation of the given number is
     * returned normally, as though a call to {@link Double#toString(double)}
     * were made. </li>
     * </ul>
     * 
     * @param x The number to represent.
     * @return A string representation of the number as if it were a coefficienct
     */
    private static String toCoefficientString(double x) {
        if(x == 1.0) {
            return "";
        } else if(x == -1.0){ 
            return "-";
        } else if(Double.isFinite(x)) {
            return toNumberString(x);
        } else if(Double.isNaN(x)) {
            return "NaN";
        } else if(x > 0.0) { // inf
            return " \\infn ";
        } else { // -inf
            return " - \\infn ";
        }
    }

    private static String toVariableString(double x) {
        if(Double.isFinite(x)) {
            return toNumberString(x);
        } else if(Double.isNaN(x)) {
            return "NaN";
        } else if(x > 0.0) { // inf
            return " \\infn ";
        } else { // -inf
            return " - \\infn ";
        }
    }

    private static String toNumberString(double x) {
        if(Math.floor(x) == x) {
            return Long.toString((long) x);
        } else {
            return Double.toString(x);
        }
    }

    private static String toAdditionString(double x) {
        if(x == 0.0 || x == -0.0) {
            return "";
        } else if(Double.isFinite(x)) {
            if(x < 0.0) {
                return " - " + toNumberString(-x);
            } else {
                return " + " + toNumberString(x);
            }
        } else if(Double.isNaN(x)) {
            return " + NaN";
        } else if(x > 0.0) { // inf
            return " + \\infn ";
        } else { // -inf
            return " - \\infn ";
        }
    }

    public static void main(String[] args) {
        QuestionGenerator gen = new QuestionGenerator(PolynomialDerivative.get());
        String eq = gen.generateQuestion();
        System.out.println(eq);
        ASTNode equation = ShuntingYardParser.get().convertInfixNotationToAST(eq);
        equation.print();
        Derivative.derive(equation);
        equation.print();
        equation.simplify();
        System.out.println();
        equation.print();
    }
        
}

