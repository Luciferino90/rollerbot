package it.pathfinder.rollerbot.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.janino.ExpressionEvaluator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Formula {

    // Special characters with special meaning. Order is the priority to follow
    private final List<String> graphedBrackets = Arrays.asList("{", "}");
    private final List<String> squareBrackets = Arrays.asList("[", "]");
    private final List<String> roundedBrackets = Arrays.asList("(", ")");


    private final List<List<String>> brakets = Arrays.asList(graphedBrackets, squareBrackets, roundedBrackets);
    private final String bracketPattern = ".*[%s,%s].*";

    private final List<String> specialCharacters = Arrays.asList("*", "x", "/", "^", "%", "+" ,"-" );
    private final List<String> noSenseCharacters = Collections.singletonList("=");
    private final String diceSeparator = "d";
    private final Long initialRandomize = 0L;
    private final List<String> templatingCharacters = Collections.singletonList("$");

    // Patterns
    private final Pattern dicePattern = Pattern.compile("[0-9]+"+diceSeparator+"[0-9]{1,3}");
    private final Pattern dicePatternMax = Pattern.compile("[0-9]+d[0-9]{1,3}h\\([0-9]+\\)");
    private final Pattern dicePatternMin = Pattern.compile("[0-9]+d[0-9]{1,3}l\\([0-9]+\\)");
    private final Pattern dicePatternRetry = Pattern.compile("[0-9]+d[0-9]{1,3}r\\([0-9]+\\)");

    private Long multiplier = 1L;
    private String formula;

    public Formula(String formula)
    {
        this.formula = formula;
    }

    public void parse()
    {
        manageMaxLimit();
        manageMinLimit();
        manageRetry();
        manageStandard();
        manageVariables();
    }

    public Integer evaluate()
    {
        Integer result = 0;
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.cook(formula);
            result = (Integer) ee.evaluate(null);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public String expr(String expression)
    {
        String result = expression;
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.cook(expression);
            result = String.valueOf(ee.evaluate(null));
        } catch (Exception ex){
            //ex.printStackTrace();
        }
        return result;
    }

    private void manageMaxLimit()
    {
        Matcher maxOf = dicePatternMax.matcher(formula);
        while (maxOf.find()){
            resolveMaxOf(maxOf);
        }
    }

    private void resolveMaxOf(Matcher maxOf)
    {
        String interested = maxOf.group();
        Long maxOfValue = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit().filter(Objects::nonNull).sorted(Comparator.reverseOrder()).limit(maxOfValue).map(String::valueOf).collect(Collectors.joining("+"));
        formula = formula.replace(interested, "(" + result + ")");
    }

    private void manageMinLimit()
    {
        Matcher minOf = dicePatternMin.matcher(formula);
        while (minOf.find()){
            resolveMinOf(minOf);
        }
    }

    private void resolveMinOf(Matcher minOf)
    {
        String interested = minOf.group();
        Long minOfValue = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit().filter(Objects::nonNull).sorted(Comparator.naturalOrder()).limit(minOfValue).map(String::valueOf).collect(Collectors.joining("+"));
        formula = formula.replace(interested, "(" + result + ")");
    }

    private void manageRetry()
    {
        Matcher retry = dicePatternRetry.matcher(formula);
        while (retry.find()){
            resolveRetry(retry);
        }
    }

    private void resolveRetry(Matcher retry)
    {
        String interested = retry.group();
        Long notValid = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit(notValid).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.joining("+"));
        setResultString(interested, result);
    }

    private void manageStandard()
    {
        Matcher diceMatcher = dicePattern.matcher(formula);
        while (diceMatcher.find()){
            resolveStandard(diceMatcher);
        }
    }

    private void resolveStandard(Matcher diceMatcher)
    {
        String interested = diceMatcher.group();
        String result = wrapperDicerLimit().filter(Objects::nonNull).map(String::valueOf).collect(Collectors.joining("+"));
        setResultString(interested, result);
    }

    private void manageVariables()
    {
        String stupidTemplate = "$var$";
        changeFormula(stupidTemplate, "30");
    }

    private Stream<Long> diceRollerr()
    {
        String diceFormula;
        Matcher diceMatcher = dicePattern.matcher(formula);
        if (diceMatcher.find()) {
            diceFormula = diceMatcher.group();
            long numberOfDices = Long.valueOf(diceFormula.split(diceSeparator)[0]);
            long numberOfFaces = Long.valueOf(diceFormula.split(diceSeparator)[1]);

            Random randomizer = new Random();

            return randomizer.longs(initialRandomize + 1L, numberOfFaces + 1L).limit(numberOfDices).boxed();
        }
        return null;
    }

    private Stream<Long> wrapperDicerLimit() {
        String diceFormula;
        Matcher diceMatcher = dicePattern.matcher(formula);
        if (diceMatcher.find()) {
            diceFormula = diceMatcher.group();
            long numberOfDices = Long.valueOf(diceFormula.split(diceSeparator)[0]);
            long numberOfFaces = Long.valueOf(diceFormula.split(diceSeparator)[1]);
            return nestedDicer(numberOfFaces).limit(numberOfDices).boxed();
        }
        return null;
    }

    private Stream<Long> wrapperDicerLimit(long retryMinValue) {
        String diceFormula;
        Matcher diceMatcher = dicePattern.matcher(formula);
        if (diceMatcher.find()) {
            diceFormula = diceMatcher.group();
            long numberOfDices = Long.valueOf(diceFormula.split(diceSeparator)[0]);
            long numberOfFaces = Long.valueOf(diceFormula.split(diceSeparator)[1]);
            return nestedDicer(numberOfFaces).filter(val -> val > retryMinValue).limit(numberOfDices).boxed();
        }
        return null;
    }

    private LongStream nestedDicer(long numberOfFaces)
    {
        Random randomizer = new Random();
        return randomizer.longs(initialRandomize + 1L, numberOfFaces + 1L);
    }

    public String getFormula()
    {
        return formula;
    }

    private void setResultString(String toRemove, String result)
    {
        changeFormula(toRemove, String.format("( %s )", result));
    }

    public void changeFormula(String toRemove, String toReplace)
    {
        formula = formula.replace(toRemove, toReplace);
    }

}
