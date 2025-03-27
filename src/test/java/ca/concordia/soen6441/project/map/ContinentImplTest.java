package ca.concordia.soen6441.project.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for ContinentImpl class.
 */
class ContinentImplTest {
    private ContinentImpl continent1;
    private ContinentImpl continent2;

    @BeforeEach
    void setUp() {
        ContinentImpl.resetCounter();
        continent1 = new ContinentImpl(1, "Asia", 5, "red");
        continent2 = new ContinentImpl("Europe", 3);
    }

    @Test
    void testGetID() {
        assertEquals("Asia", continent1.getID());
        assertEquals("Europe", continent2.getID());
    }

    @Test
    void testGetValue() {
        assertEquals(5, continent1.getValue());
        assertEquals(3, continent2.getValue());
    }

    @Test
    void testGetNumericID() {
        assertEquals(1, continent1.getNumericID());
        assertEquals(2, continent2.getNumericID());
    }

    @Test
    void testToString() {
        assertEquals("Asia=5", continent1.toString());
        assertEquals("Europe=3", continent2.toString());
    }

    @Test
    void testToMapString() {
        assertEquals("Asia 5 red", continent1.toMapString());
        assertEquals("Europe 3 yellow", continent2.toMapString());
    }
}
