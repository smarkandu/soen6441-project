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
    Path tempDir;

    private GameContext gameContext;
    private ContinentContext continentContext;
    private CountryContext countryContext;

    @BeforeEach
    public void setup() {
        // Create mocks for GameContext and its managers.
        gameContext = Mockito.mock(GameContext.class);
        continentContext = Mockito.mock(ContinentContext.class);
        countryContext = Mockito.mock(CountryContext.class);

        // Stub the GameContext methods to return our mocks.
        when(gameContext.getContinentManager()).thenReturn(continentContext);
        when(gameContext.getCountryManager()).thenReturn(countryContext);

        // Create stub continents for numeric IDs.
        // For numeric ID 1, we return a continent with ID "NorthAmerica".
        Continent northAmerica = Mockito.mock(Continent.class);
        when(northAmerica.getID()).thenReturn("NorthAmerica");

        // For numeric ID 2, we return a continent with ID "SouthAmerica".
        Continent southAmerica = Mockito.mock(Continent.class);
        when(southAmerica.getID()).thenReturn("SouthAmerica");

        // Stub getContinentByNumericID for continentContext.
        when(continentContext.getContinentByNumericID(1)).thenReturn(northAmerica);
        when(continentContext.getContinentByNumericID(2)).thenReturn(southAmerica);
    }

    /**
     * Test reading a valid map file.
     *
     * @throws Exception if an error occurs during file writing or reading.
     */
    @Test
    public void testReadValidMapFile() throws Exception {
        // Create a temporary file with sample valid map content.
        Path mapFile = tempDir.resolve("test.map");
        String mapContent = """
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
        Files.writeString(mapFile, mapContent);

        // Create an instance of MapFileReader and read the file.
        MapFileReader mapFileReader = new MapFileReader();
        mapFileReader.readMapFile(mapFile.toString(), gameContext);

        // Verify that continents were added.
        // According to the file, the first continent is "NorthAmerica" with bonus 5.
        verify(continentContext).addContinent(1, "NorthAmerica", 5, "yellow");
        // The second continent is "SouthAmerica" with bonus 4.
        verify(continentContext).addContinent(2, "SouthAmerica", 4, "yellow");

        // Verify that countries were added.
        // The country with numeric ID 1 ("Canada") belongs to "NorthAmerica".
        verify(countryContext).addCountry(1, "Canada", "NorthAmerica", 0, 0);
        // The country with numeric ID 2 ("UnitedStates") belongs to "NorthAmerica".
        verify(countryContext).addCountry(2, "UnitedStates", "NorthAmerica", 0, 0);
        // The country with numeric ID 3 ("Mexico") belongs to "SouthAmerica".
        verify(countryContext).addCountry(3, "Mexico", "SouthAmerica", 0, 0);
    }

    /**
     * Test that a FileNotFoundException is thrown when the map file does not exist.
     */
    @Test
    public void testReadMapFileNotFound() {
        MapFileReader mapFileReader = new MapFileReader();
        // Expect a FileNotFoundException when reading a non-existent file.
        try {
            mapFileReader.readMapFile("wrong_name.map", gameContext);
            fail("Expected FileNotFoundException to be thrown");
        } catch (FileNotFoundException e) {
            // Test passes, exception was thrown as expected.
            System.out.println("Test passed! The anticipated exception was handled");
        };
    }
}
