package it.pathfinder.rollerbot.service;

import it.pathfinder.rollerbot.custom.Formula;
import it.pathfinder.rollerbot.custom.FormulaMul;
import it.pathfinder.rollerbot.response.MultiResponse;
import it.pathfinder.rollerbot.response.SingleDiceResponse;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

@Service
public class ParserService {

    private final String multiplierSeparator = "x";
    private final Pattern multiply = Pattern.compile("^[0-9]+["+multiplierSeparator+"]");

    /**
     * Recursive Method, has to read a formula and resolve it
     * First we had to separate the string using math expressions.
     *
     * This will suck a lot.
     * @return
     */
    public MultiResponse parseFormula(String formulaString, String username)
    {
        MultiResponse multiResponse = new MultiResponse(username);
        FormulaMul formulaMul = manageMultiplier(formulaString);
        LongStream.rangeClosed(0L, formulaMul.getMultiplier() - 1)
                .forEach(l -> multiResponse.addSingleDiceResponse(manageSingleFormula(l, formulaMul.getFormula(), username)));
        return multiResponse;
    }

    /**
     * Manage multiplier at the start of the request
     * @param formula
     * @return
     */
    private FormulaMul manageMultiplier(String formula) {
        FormulaMul formulaMul = new FormulaMul(new Formula(formula));
        Matcher m = multiply.matcher(formula);
        if (m.find()) {
            formulaMul.setMultiplier(Long.valueOf(m.group().split(multiplierSeparator)[0]));
            formulaMul.setFormula(new Formula(formula.replace(m.group(), "")));
        }
        return formulaMul;
    }

    /**
     * Manage entire formula once
     * @param counter
     * @param formula
     * @return
     */
    private SingleDiceResponse manageSingleFormula(Long counter, Formula formula, String username)
    {
        SingleDiceResponse singleDiceResponse = new SingleDiceResponse();
        singleDiceResponse.setUsername(username);
        String formulaString = formula.getFormula();
        Formula formulaASync = new Formula(formulaString);
        formulaASync.parse();
        singleDiceResponse.setPartialResult(formulaASync.getFormula());
        singleDiceResponse.setResult(String.valueOf(formulaASync.evaluate()));
        return singleDiceResponse;
    }

}
