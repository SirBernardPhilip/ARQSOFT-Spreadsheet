package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

public class NumericalContent extends ACellContent {

    /**
     * Double value
     */
    private Double value;

    /**
     * Constructor of numerical content
     * @param content
     */
    private NumericalContent(String content) {
        super(content);
        this.setValue(content);
    }

    /**
     * Get instance
     * 
     * @param content
     * @return NumericalContent
     */
    public static NumericalContent getInstance(String content) {
        return new NumericalContent(content);
    }


    /**
     * 
     * Parse the content to the corresponding value
     * If it is parseable has already been checked so it will not throw an error
     * 
     * @param content
     */
    protected final void setValue(String content) {
        this.value = Double.parseDouble(content.trim());
    }

    /**
     * Get the value as a number (as is)
     * 
     * @return Double
     * @throws NumberFormatException
     */
    @Override
    public Double getNumericalValue() throws NumberFormatException {
        return this.value;
    }

    /**
     * 
     * Parse the value to a string
     * 
     * @return String
     */
    @Override
    public String getStringValue() {
        return String.valueOf(this.value);
    }
}
