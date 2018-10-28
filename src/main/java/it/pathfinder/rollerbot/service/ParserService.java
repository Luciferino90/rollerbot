package it.pathfinder.rollerbot.service;

import dto.generic.Error;
import dto.generic.GenericDTO;
import dto.generic.dices.Dices;
import dto.generic.dices.SingleDiceResponse;
import it.pathfinder.rollerbot.custom.Formula;
import it.pathfinder.rollerbot.custom.FormulaMul;
import it.pathfinder.rollerbot.exception.InvalidExpression;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

@Service
public class ParserService {

    private static final String MULTIPLIER_SEPARATOR = "x";
    private static final Pattern MULTIPLY = Pattern.compile("^[0-9]+[" + MULTIPLIER_SEPARATOR + "]");

    /**
     * Recursive Method, has to read a formula and resolve it
     * First we had to separate the string using math expressions.
     * <p>
     * This will suck a lot.
     *
     * @return
     */
    public GenericDTO parseFormula(String formulaString, String username) {
        Dices dicesResult = new Dices(username);
        FormulaMul formulaMul = manageMultiplier(formulaString);
        try {
            LongStream.rangeClosed(0L, formulaMul.getMultiplier() - 1)
                    .forEach(l ->
                            dicesResult.addSingleDiceResponse(manageSingleFormula(formulaMul.getFormula()))
                    );
        } catch (InvalidExpression ex) {
            return new Error(ex.getMessage());
        }
        return dicesResult;
    }

    /**
     * Manage multiplier at the start of the request
     *
     * @param formula
     * @return
     */
    private FormulaMul manageMultiplier(String formula) {
        FormulaMul formulaMul = new FormulaMul(new Formula(formula));
        Matcher m = MULTIPLY.matcher(formula);
        if (m.find()) {
            formulaMul.setMultiplier(Long.valueOf(m.group().split(MULTIPLIER_SEPARATOR)[0]));
            formulaMul.setFormula(new Formula(formula.replace(m.group(), "")));
        }
        return formulaMul;
    }

    /**
     * Manage entire formula once
     *
     * @param formula
     * @return
     */
    private SingleDiceResponse manageSingleFormula(Formula formula) {
        SingleDiceResponse singleDiceResponse = new SingleDiceResponse();
        String formulaString = formula.getFormulaString();
        Formula formulaASync = new Formula(formulaString);
        formulaASync.parse();
        singleDiceResponse.setPartialResult(formulaASync.getFormulaString());
        try {
            singleDiceResponse.setResult(String.valueOf(formulaASync.evaluate()));
        } catch (CompileException | Parser.ParseException | Scanner.ScanException | InvocationTargetException ex) {
            throw new InvalidExpression("Invalid formula " + formula.getFormulaString());
        }
        return singleDiceResponse;
    }

}
