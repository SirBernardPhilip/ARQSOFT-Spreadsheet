package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for the stack values that will be a list of numbers (for the functions)
 */
public class StackListDoubleValue implements IStackValue {

    private List<Double> values;

    /**
     * Create the stack list value with the given values
     * @param value
     */
    public StackListDoubleValue(Double value) {
        this.values = new LinkedList<Double>();
        this.values.add(value);
    }

    /**
     * Add a value to the list
     * @param value
     */
    public void addValue(Double value) {
        this.values.add(value);
    }

    /**
     * Return the list of values
     * @return
     */
    public List<Double> getStackValue() {
        return this.values;
    }

}
