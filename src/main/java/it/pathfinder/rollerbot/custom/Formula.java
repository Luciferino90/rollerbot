package it.pathfinder.rollerbot.custom;

import org.codehaus.janino.ExpressionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Formula {

    private static final String DICE_SEPARATOR = "d";
    private static final Long INITIAL_RANDOMIZE = 0L;
    // Patterns
    private static final Pattern DICE_PATTERN = Pattern.compile("[0-9]+" + DICE_SEPARATOR + "[0-9]{1,3}");
    private static final Pattern DICE_PATTERN_MAX = Pattern.compile("[0-9]+d[0-9]{1,3}h\\([0-9]+\\)");
    private static final Pattern DICE_PATTERN_MIN = Pattern.compile("[0-9]+d[0-9]{1,3}l\\([0-9]+\\)");
    private static final Pattern DICE_PATTERN_RETRY = Pattern.compile("[0-9]+d[0-9]{1,3}r\\([0-9]+\\)");
    private Logger logger = LoggerFactory.getLogger(Formula.class);
    private String formulaString;

    public Formula(String formula) {
        this.formulaString = formula;
    }

    public void parse() {
        manageVariables();
        manageMaxLimit();
        manageMinLimit();
        manageRetry();
        manageStandard();
    }

    public Integer evaluate() {
        Integer result = 0;
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.cook(formulaString);
            result = (Integer) ee.evaluate(null);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public String expr(String expression) {
        String result = expression;
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.cook(expression);
            result = String.valueOf(ee.evaluate(null));
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        return result;
    }

    private void manageMaxLimit() {
        Matcher maxOf = DICE_PATTERN_MAX.matcher(formulaString);
        while (maxOf.find()) {
            resolveMaxOf(maxOf);
        }
    }

    private void resolveMaxOf(Matcher maxOf) {
        String interested = maxOf.group();
        Long maxOfValue = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit()
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .limit(maxOfValue)
                .map(String::valueOf)
                .collect(Collectors.joining("+"));
        formulaString = formulaString.replace(interested, "(" + result + ")");
    }

    private void manageMinLimit() {
        Matcher minOf = DICE_PATTERN_MIN.matcher(formulaString);
        while (minOf.find()) {
            resolveMinOf(minOf);
        }
    }

    private void resolveMinOf(Matcher minOf) {
        String interested = minOf.group();
        Long minOfValue = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit()
                .filter(Objects::nonNull)
                .sorted(Comparator.naturalOrder())
                .limit(minOfValue)
                .map(String::valueOf)
                .collect(Collectors.joining("+"));
        formulaString = formulaString.replace(interested, "(" + result + ")");
    }

    private void manageRetry() {
        Matcher retry = DICE_PATTERN_RETRY.matcher(formulaString);
        while (retry.find()) {
            resolveRetry(retry);
        }
    }

    private void resolveRetry(Matcher retry) {
        String interested = retry.group();
        Long notValid = Long.valueOf(interested.split("\\(")[1].split("\\)")[0]);
        String result = wrapperDicerLimit(notValid)
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining("+"));
        setResultString(interested, result);
    }

    private void manageStandard() {
        Matcher diceMatcher = DICE_PATTERN.matcher(formulaString);
        while (diceMatcher.find()) {
            resolveStandard(diceMatcher);
        }
    }

    private void resolveStandard(Matcher diceMatcher) {
        String interested = diceMatcher.group();
        String result = wrapperDicerLimit()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining("+"));
        setResultString(interested, result);
    }

    private void manageVariables() {
        String stupidTemplate = "$var$";
        changeFormula(stupidTemplate, "30");
    }

    private Stream<Long> wrapperDicerLimit() {
        String diceFormula;
        Matcher diceMatcher = DICE_PATTERN.matcher(formulaString);
        if (diceMatcher.find()) {
            diceFormula = diceMatcher.group();
            long numberOfDices = Long.parseLong(diceFormula.split(DICE_SEPARATOR)[0]);
            long numberOfFaces = Long.parseLong(diceFormula.split(DICE_SEPARATOR)[1]);
            return nestedDicer(numberOfFaces)
                    .limit(numberOfDices)
                    .boxed();
        }
        return Stream.empty();
    }

    private Stream<Long> wrapperDicerLimit(long retryMinValue) {
        String diceFormula;
        Matcher diceMatcher = DICE_PATTERN.matcher(formulaString);
        if (diceMatcher.find()) {
            diceFormula = diceMatcher.group();
            long numberOfDices = Long.parseLong(diceFormula.split(DICE_SEPARATOR)[0]);
            long numberOfFaces = Long.parseLong(diceFormula.split(DICE_SEPARATOR)[1]);
            return nestedDicer(numberOfFaces)
                    .filter(val -> val > retryMinValue)
                    .limit(numberOfDices)
                    .boxed();
        }
        return Stream.empty();
    }

    private LongStream nestedDicer(long numberOfFaces) {
        Random randomizer = new Random();
        return randomizer
                .longs(INITIAL_RANDOMIZE + 1L, numberOfFaces + 1L);
    }

    public String getFormulaString() {
        return formulaString;
    }

    private void setResultString(String toRemove, String result) {
        changeFormula(toRemove, String.format("( %s )", result));
    }

    public void changeFormula(String toRemove, String toReplace) {
        formulaString = formulaString.replace(toRemove, toReplace);
    }

}
