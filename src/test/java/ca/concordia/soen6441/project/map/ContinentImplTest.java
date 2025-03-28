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

    @BeforeEach
    void setUp() {
        ContinentImpl.resetCounter();
        d_continent1 = new ContinentImpl(1, "Asia", 5, "red");
        d_continent2 = new ContinentImpl("Europe", 3);
    }

    @Test
    void testGetID() {
        assertEquals("Asia", d_continent1.getID());
        assertEquals("Europe", d_continent2.getID());
    }

    @Test
    void testGetValue() {
        assertEquals(5, d_continent1.getValue());
        assertEquals(3, d_continent2.getValue());
    }

    @Test
    void testGetNumericID() {
        assertEquals(1, d_continent1.getNumericID());
        assertEquals(2, d_continent2.getNumericID());
    }

    @Test
    void testToString() {
        assertEquals("Asia=5", d_continent1.toString());
        assertEquals("Europe=3", d_continent2.toString());
    }

    @Test
    void testToMapString() {
        assertEquals("Asia 5 red", d_continent1.toMapString());
        assertEquals("Europe 3 yellow", d_continent2.toMapString());
    }
}
