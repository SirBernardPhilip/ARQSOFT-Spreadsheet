package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.formula.evaluation.visitorStack;

/**
 * Class for the stack values that will just be a number
 */
public class StackDoubleValue implements IStackValue {

    private Double value;

    /**
     * Create the stack value with the given value
     * @param value
     */
    public StackDoubleValue(Double value) {
        this.value = value;
    }

    
    /** 
     * Returns the value
     * @return Double
     */
    public Double getStackValue() {
        return this.value;
    }

}
