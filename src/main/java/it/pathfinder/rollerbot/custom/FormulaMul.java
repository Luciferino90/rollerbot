package it.pathfinder.rollerbot.custom;

import lombok.Data;

@Data
public class FormulaMul {

    private Long multiplier;
    private Formula formula;

    public FormulaMul(Formula formula) {
        this.formula = formula;
        multiplier = 1L;
    }

}
