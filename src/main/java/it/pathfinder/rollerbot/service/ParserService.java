package it.pathfinder.rollerbot.service;

import it.pathfinder.rollerbot.custom.Formula;
import it.pathfinder.rollerbot.custom.FormulaMul;
import it.pathfinder.rollerbot.response.dices.Dices;
import it.pathfinder.rollerbot.response.Info;
import it.pathfinder.rollerbot.response.dices.SingleDiceResponse;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

@Service
public class ParserService {

    private static final String MULTIPLIER_SEPARATOR = "x";
    private static final Pattern MULTIPLY = Pattern.compile("^[0-9]+["+ MULTIPLIER_SEPARATOR +"]");

    /**
     * Recursive Method, has to read a formula and resolve it
     * First we had to separate the string using math expressions.
     *
     * This will suck a lot.
     * @return
     */
    public Info parseFormula(String formulaString, String username)
    {
        Dices multiResponse = new Dices(username);
        FormulaMul formulaMul = manageMultiplier(formulaString);
        LongStream.rangeClosed(0L, formulaMul.getMultiplier() - 1)
                .forEach(l -> multiResponse.addSingleDiceResponse(manageSingleFormula(formulaMul.getFormula())));
        return multiResponse;
    }
    //4d6
    /**
     * Manage multiplier at the start of the request
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
     * @param formula
     * @return
     */
    private SingleDiceResponse manageSingleFormula(Formula formula)
    {
        SingleDiceResponse singleDiceResponse = new SingleDiceResponse();
        String formulaString = formula.getFormulaString();
        Formula formulaASync = new Formula(formulaString);
        formulaASync.parse();
        singleDiceResponse.setPartialResult(formulaASync.getFormulaString());
        singleDiceResponse.setResult(String.valueOf(formulaASync.evaluate()));
        return singleDiceResponse;
    }

}
