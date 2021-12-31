package edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidNumberCoordinatesException;
import edu.upc.etsetb.arqsoft.multispreadsheet.spreadsheet.entities.exceptions.InvalidStringCoordinatesException;

/** @author bernatfelip */
@RunWith(MockitoJUnitRunner.class)
public class CellCoordinateTest {

	public CellCoordinateTest() {
	}

	
	/** 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("Starting tests for class CellCoordinate.");
	}

	
	/** 
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		System.out.println("Tests for class CellCoordinate done.");
	}
	
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test(expected = InvalidStringCoordinatesException.class)
	public void testCellCoordinateInvalidStringCoordinatesException()
			throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate.getInstance(1, "1A3");
	}


	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test(expected = InvalidNumberCoordinatesException.class)
	public void testCellCoordinateInvalidNumberCoordinatesException()
			throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate.getInstance(0, "A");
	}

	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test
	public void testCellCoordinateConstructorAndGetters() throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate c = CellCoordinate.getInstance(1, "AAABBC");
		Assert.assertEquals(1, c.getRow().intValue());
		Assert.assertEquals("AAABBC", c.getColumn());
	}


	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test
	public void testEqualsObject() throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate c1 = CellCoordinate.getInstance(1, "A");
		CellCoordinate c2 = CellCoordinate.getInstance(1, "A");
		CellCoordinate c3 = CellCoordinate.getInstance(2, "A");
		CellCoordinate c4 = CellCoordinate.getInstance(1, "B");
		Assert.assertTrue(c1.equals(c2));
		Assert.assertFalse(c1.equals(c3));
		Assert.assertFalse(c1.equals(c4));
	}

	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test
	public void testHashCode() throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate c1 = CellCoordinate.getInstance(1, "A");
		CellCoordinate c2 = CellCoordinate.getInstance(1, "A");
		Assert.assertEquals(c1.hashCode(), c2.hashCode());
		// Strictly speaking we don't have to test inequality, should we do it either way?
	}

	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test
	public void testGetColumnNumber() throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		CellCoordinate c1 = CellCoordinate.getInstance(1, "A");
		Assert.assertEquals(1, c1.getColumnNumber().intValue());
		CellCoordinate c2 = CellCoordinate.getInstance(1, "AZ");
		Assert.assertEquals(52, c2.getColumnNumber().intValue());
		CellCoordinate c3 = CellCoordinate.getInstance(1, "ZA");
		Assert.assertEquals(677, c3.getColumnNumber().intValue());
	}

	
	/** 
	 * @throws InvalidStringCoordinatesException
	 * @throws InvalidNumberCoordinatesException
	 */
	@Test
	public void testGetColumnName() throws InvalidStringCoordinatesException, InvalidNumberCoordinatesException {
		Assert.assertEquals("A", CellCoordinate.getColumnName(1));
		Assert.assertEquals("AZ", CellCoordinate.getColumnName(52));
		Assert.assertEquals("ZA", CellCoordinate.getColumnName(677));
	}

}