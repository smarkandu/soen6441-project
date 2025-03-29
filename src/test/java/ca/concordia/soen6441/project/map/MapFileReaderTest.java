package ca.concordia.soen6441.project.map;
import ca.concordia.soen6441.project.interfaces.Continent;
import ca.concordia.soen6441.project.interfaces.context.CountryContext;
import ca.concordia.soen6441.project.interfaces.context.ContinentContext;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class MapFileReaderTest {

    @TempDir
    Path d_tempDir;

    private GameContext d_gameContext;
    private ContinentContext d_continentContext;
    private CountryContext d_countryContext;

    @BeforeEach
    public void setup() {
        // Create mocks for GameContext and its managers.
        d_gameContext = Mockito.mock(GameContext.class);
        d_continentContext = Mockito.mock(ContinentContext.class);
        d_countryContext = Mockito.mock(CountryContext.class);

        // Stub the GameContext methods to return our mocks.
        when(d_gameContext.getContinentManager()).thenReturn(d_continentContext);
        when(d_gameContext.getCountryManager()).thenReturn(d_countryContext);

        // Create stub continents for numeric IDs.
        // For numeric ID 1, we return a continent with ID "NorthAmerica".
        Continent l_northAmerica = Mockito.mock(Continent.class);
        when(l_northAmerica.getID()).thenReturn("NorthAmerica");

        // For numeric ID 2, we return a continent with ID "SouthAmerica".
        Continent l_southAmerica = Mockito.mock(Continent.class);
        when(l_southAmerica.getID()).thenReturn("SouthAmerica");

        // Stub getContinentByNumericID for continentContext.
        when(d_continentContext.getContinentByNumericID(1)).thenReturn(l_northAmerica);
        when(d_continentContext.getContinentByNumericID(2)).thenReturn(l_southAmerica);
    }

    /**
     * Test reading a valid map file.
     *
     * @throws Exception if an error occurs during file writing or reading.
     */
    @Test
    public void testReadValidMapFile() throws Exception {
        // Create a temporary file with sample valid map content.
        Path l_mapFile = d_tempDir.resolve("test.map");
        String l_mapContent = """
                [continents]
                NorthAmerica 5 yellow
                SouthAmerica 4 yellow

                [countries]
                1 Canada 1 0 0
                2 UnitedStates 1 0 0
                3 Mexico 2 0 0

                [borders]
                1 3 2
                2 1 3
                3 1 2
                """;
        Files.writeString(l_mapFile, l_mapContent);

        // Create an instance of MapFileReader and read the file.
        MapFileReader l_mapFileReader = new MapFileReader();
        l_mapFileReader.readMapFile(l_mapFile.toString(), d_gameContext);

        // Verify that continents were added.
        // According to the file, the first continent is "NorthAmerica" with bonus 5.
        verify(d_continentContext).addContinent(1, "NorthAmerica", 5, "yellow");
        // The second continent is "SouthAmerica" with bonus 4.
        verify(d_continentContext).addContinent(2, "SouthAmerica", 4, "yellow");

        // Verify that countries were added.
        // The country with numeric ID 1 ("Canada") belongs to "NorthAmerica".
        verify(d_countryContext).addCountry(1, "Canada", "NorthAmerica", 0, 0);
        // The country with numeric ID 2 ("UnitedStates") belongs to "NorthAmerica".
        verify(d_countryContext).addCountry(2, "UnitedStates", "NorthAmerica", 0, 0);
        // The country with numeric ID 3 ("Mexico") belongs to "SouthAmerica".
        verify(d_countryContext).addCountry(3, "Mexico", "SouthAmerica", 0, 0);
    }

    /**
     * Test that a FileNotFoundException is thrown when the map file does not exist.
     */
    @Test
    public void testReadMapFileNotFound() {
        MapFileReader l_mapFileReader = new MapFileReader();
        // Expect a FileNotFoundException when reading a non-existent file.
        try {
            l_mapFileReader.readMapFile("wrong_name.map", d_gameContext);
            fail("Expected FileNotFoundException to be thrown");
        } catch (FileNotFoundException e) {
            // Test passes, exception was thrown as expected.
            System.out.println("Test passed! The anticipated exception was handled");
        };
    }
}
