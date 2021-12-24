package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import java.util.Objects;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.ICellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidNumberCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidStringCoordinatesException;

/**
 * Cell coordinate implementation
 * 
 */
public class CellCoordinate implements ICellCoordinate {

    /**
     * Row coordinate
     */
    private final Integer row;

    /**
     * Column coordinate
     */
    private final String column;

    /**
     * hashCode (for key indexing)
     */
    private final int hashCode;

    /**
     * Create cell coordinate
     * 
     * @param row
     * @param column
     */
    private CellCoordinate(Integer row, String column)
            throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
        if (!CellCoordinate.isValidNumber(row)) {
            throw new InvalidNumberCoordinatesException(row);
        }
        if (!CellCoordinate.isValidString(column)) {
            throw new InvalidStringCoordinatesException(column);
        }
        this.row = row;
        this.column = column;
        this.hashCode = Objects.hash(row, column);
    }

    public static CellCoordinate getInstance(Integer row, String column)
            throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
        return new CellCoordinate(row, column);
    }

    /**
     * Create a coordinate from it's string form
     * 
     * @param cellCoordinateString
     * @return CellCoordinate
     * @throws InvalidStringCoordinatesException
     * @throws InvalidNumberCoordinatesException
     * @throws NumberFormatException
     */
    public static CellCoordinate fromString(String cellCoordinateString)
            throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException, NumberFormatException {
        // FIXME: Do this with regex, this iteration is ugly
        int foundNum = -1;
        for (int i = 0; (foundNum == -1) && (i < cellCoordinateString.length()); ++i) {
            if (Character.isDigit(cellCoordinateString.charAt(i))) {
                foundNum = i;
            }
        }
        if (foundNum == 0 || foundNum == -1) {
            throw new InvalidStringCoordinatesException(cellCoordinateString);
        }
        String column = cellCoordinateString.substring(0, foundNum);
        Integer row = Integer.parseInt(cellCoordinateString.substring(foundNum));
        return new CellCoordinate(row, column);
    }

    /**
     * To string method, useful for debugging
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "(Row: " + this.row.toString() + "; Column: " + this.column + ")";
    }

    /**
     * Since we want to use this class as keys for a map we need to define our
     * equals and hashcode methods properly. I tried using the wizard but it just
     * calls super(), which is not what I want.
     * 
     * @return int
     */
    @Override
    public int hashCode() {
        return this.hashCode;
    }

    /**
     * Needed to use this class as a key in a hash map
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CellCoordinate)) {
            return false;
        }

        CellCoordinate c = (CellCoordinate) obj;

        return this.row == c.row && this.column.equals(c.column);
    }

    /**
     * Validates the string as a valid column string
     * 
     * @param column
     * @return boolean
     */
    private static boolean isValidString(String column) {
        return column.matches("[A-Z]+") && !column.isEmpty();
    }

    /**
     * Validates the number as a valid row number
     * 
     * 
     * @param row
     * @return boolean
     */
    private static boolean isValidNumber(Integer row) {
        return row >= 1;
    }

    /**
     * @return Integer
     */
    @Override
    public Integer getRow() {
        return this.row;
    }

    /**
     * @return String
     */
    @Override
    public String getColumn() {
        return this.column;
    }

    /**
     * Non-static method of the column number conversion
     * 
     * @return Integer
     * @throws InvalidStringCoordinatesException
     */
    @Override
    public Integer getColumnNumber() throws InvalidStringCoordinatesException {
        return CellCoordinate.getColumnNumber(this.column);
    }

    /**
     * Converts the alphabetical column to it's corresponding number
     * 
     * @param columnString
     * @return Integer
     * @throws InvalidStringCoordinatesException
     */
    public static Integer getColumnNumber(String columnString) throws InvalidStringCoordinatesException {
        if (!CellCoordinate.isValidString(columnString)) {
            throw new InvalidStringCoordinatesException(columnString);
        }
        int columnNumber = 0;
        for (int i = 0; i < columnString.length(); i++) {
            columnNumber *= 26;
            columnNumber += columnString.charAt(i) - 'A' + 1;
        }
        return columnNumber;
    }

    /**
     * Converts the alphabetical number to it's corresponding alphabetical name
     * 
     * @param columnNumber
     * @return String
     * @throws InvalidNumberCoordinatesException
     */
    public static String getColumnName(int columnNumber) throws InvalidNumberCoordinatesException {
        if (!CellCoordinate.isValidNumber(columnNumber)) {
            throw new InvalidNumberCoordinatesException(columnNumber);
        }
        StringBuilder columnName = new StringBuilder();

        while (columnNumber > 0) {
            int r = columnNumber % 26;
            if (r == 0) {
                columnNumber = (columnNumber / 26) - 1;
                columnName.append("Z");
            } else {
                columnNumber = columnNumber / 26;
                columnName.append((char) ((r - 1) + 'A'));
            }
        }

        return columnName.reverse().toString();
    }
}
