package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack;


public class StackDoubleValue implements IStackValue {

    private Double value;

    public StackDoubleValue(Double value) {
        this.value = value;
    }

    public Double getStackValue() {
        return this.value;
    }

}
