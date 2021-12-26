package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack;

import java.util.LinkedList;
import java.util.List;

public class StackListDoubleValue implements IStackValue {

    private List<Double> values;

    public StackListDoubleValue(Double value) {
        this.values = new LinkedList<Double>();
        this.values.add(value);
    }

    public void addValue(Double value) {
        this.values.add(value);
    }

    public List<Double> getStackValue() {
        return this.values;
    }

}
