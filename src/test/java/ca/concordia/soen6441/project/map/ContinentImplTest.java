package ca.concordia.soen6441.project.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for ContinentImpl class.
 */
class ContinentImplTest {
    private ContinentImpl d_continent1;
    private ContinentImpl d_continent2;

    /**
     * Setup for each testcase
     */
    @BeforeEach
    void setUp() {
        ContinentImpl.resetCounter();
        d_continent1 = new ContinentImpl(1, "Asia", 5, "red");
        d_continent2 = new ContinentImpl("Europe", 3);
    }

    /**
     * Testcase for GetID
     */
    @Test
    void testGetID() {
        assertEquals("Asia", d_continent1.getID());
        assertEquals("Europe", d_continent2.getID());
    }

    /**
     * Testcase for GetValue
     */
    @Test
    void testGetValue() {
        assertEquals(5, d_continent1.getValue());
        assertEquals(3, d_continent2.getValue());
    }

    /**
     * Testcase for GetNumericID
     */
    @Test
    void testGetNumericID() {
        assertEquals(1, d_continent1.getNumericID());
        assertEquals(2, d_continent2.getNumericID());
    }
}
