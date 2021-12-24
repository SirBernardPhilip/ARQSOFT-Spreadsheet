package edu.upc.etsetb.arqsoft.spreadsheet.entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import edu.upc.etsetb.arqsoft.multispreadsheet.entities.exceptions.MultiSpreadsheetException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinate;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.CellCoordinateRange;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidRangeCoordinatesException;

/** @author bernatfelip */
@RunWith(MockitoJUnitRunner.class)
public class CellCoordinateRangeTest {

	public CellCoordinateRangeTest() {
	}

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("Starting tests for class CellCoordinateRange.");
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		System.out.println("Tests for class CellCoordinateRange done.");
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * @throws MultiSpreadsheetException
	 */
	@Test
	public void testConstructorAndGetters() throws MultiSpreadsheetException {
		CellCoordinate topLeft = CellCoordinate.getInstance(1, "A");
		CellCoordinate bottomRight = CellCoordinate.getInstance(3, "C");
		CellCoordinateRange range = CellCoordinateRange.getInstance(topLeft, bottomRight);
		Assert.assertEquals(topLeft, range.getTopLeft());
		Assert.assertEquals(bottomRight, range.getBottomRight());
	}

	/**
	 * @throws MultiSpreadsheetException
	 */
	@Test(expected = InvalidRangeCoordinatesException.class)
	public void testConstructorException() throws MultiSpreadsheetException {
		CellCoordinate topLeft = CellCoordinate.getInstance(3, "C");
		CellCoordinate bottomRight = CellCoordinate.getInstance(1, "A");
		CellCoordinateRange.getInstance(topLeft, bottomRight);
	}

}