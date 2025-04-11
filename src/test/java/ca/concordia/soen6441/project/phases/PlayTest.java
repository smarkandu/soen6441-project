package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.OverallFactory;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

/**
 * Test class for class Play (abstract)
 */
public class PlayTest
{
    /**
     * Stub class created to test "Play" methods
     */
    class PlayStub extends Play
    {

        /**
         * {@inheritDoc}
         */
        @Override
        public void loadMap(String p_filename)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void gamePlayerAdd(String p_playerName, PlayerBehaviorType p_playerBehaviorType)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void gamePlayerRemove(String p_playerName)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void assignCountries()
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void deploy(String p_countryID, int p_toDeploy)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void bomb(String p_countryID)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void blockade(String p_countryID)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void negotiate(String p_playerID)
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void next()
        {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void saveGame(String p_filename)
        {

        }
    }

    private Play d_play;
    private LogEntryBuffer d_mockLogBuffer;
    private File d_tempSaveGameFile;

    /**
     * Setup method called before each test case
     */
    @BeforeEach
    void setup() throws IOException
    {
        d_play = new PlayStub();
        d_mockLogBuffer = mock(LogEntryBuffer.class);
        LogEntryBuffer.setInstance(d_mockLogBuffer);
        d_tempSaveGameFile = File.createTempFile("test_game", ".ser");
    }

    @AfterEach
    void tearDown()
    {
        d_tempSaveGameFile.delete();
        LogEntryBuffer.setInstance(null);
        OverallFactory.setInstance(null);
    }

    /**
     * Testcase for a successful load game
     *
     * @throws IOException may occur due to file operations
     */
    @Test
    void testLoadGame_SuccessfulLoad() throws IOException
    {
        GameEngine l_mockGameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(l_mockGameEngine);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(d_tempSaveGameFile.getAbsolutePath())))
        {
            out.writeObject(l_mockGameEngine);
        }

        d_play.loadGame(d_tempSaveGameFile.getAbsolutePath());

        assertEquals(l_mockGameEngine.getClass(), GameDriver.getGameEngine().getClass());
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Game loaded from: " + d_tempSaveGameFile.getAbsolutePath()), eq(true));
    }

    /**
     * Testcase for when the given file is not found
     */
    @Test
    void testLoadGame_FileNotFound()
    {
        String l_nonExistentPath = "non_existent_file.ser";
        assertDoesNotThrow(() -> d_play.loadGame(l_nonExistentPath));
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Issue loading game: " + l_nonExistentPath), eq(true));
    }

    /**
     * Testcase for when the object to be deserialized is invalid
     *
     * @throws IOException may occur due to file operations
     */
    @Test
    void testLoadGame_InvalidObjectFormat() throws IOException
    {
        // Create Invalid File
        try (FileOutputStream l_fileOutputStream = new FileOutputStream(d_tempSaveGameFile.getAbsolutePath()))
        {
            l_fileOutputStream.write("not a serialized object".getBytes());
        }
        assertDoesNotThrow(() -> d_play.loadGame(d_tempSaveGameFile.getAbsolutePath()));

        // Try to load invalid file
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Issue loading game: " + d_tempSaveGameFile.getAbsolutePath()), eq(true));
    }

    /**
     * Testcase for a valid series of inputs, the queue is generated with the appropriate number of objects
     */
    @Test
    void testTournamentQueueSizeMatchesExpected() {
        List<String> mapFiles = List.of("F:\\Github\\soen6441-project\\sample_maps\\valid\\america.map", "F:\\Github\\soen6441-project\\sample_maps\\valid\\northamerica.map");
        List<String> playerStrategies = List.of("aggressive", "benevolent");
        int numberOfGames = 2;
        int maxTurns = 10;
        GameEngine l_gameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(l_gameEngine);
        OverallFactory l_overallFactory = mock(OverallFactory.class);
        OverallFactory.setInstance(l_overallFactory);
        Startup l_startup = mock(Startup.class);
        when(l_overallFactory.CreateStartup()).thenReturn(l_startup);
        when(l_overallFactory.CreateGameEngine()).thenReturn(l_gameEngine);
        when(GameDriver.getGameEngine().getPhase()).thenReturn(l_startup);

        // Call the tournament commands
        d_play.tournament(mapFiles, playerStrategies, numberOfGames, maxTurns);

        // Verify the # of objects on queue
        // Assert: 2 maps × 2 games = 4 tournament games + 1 original context restored
        assertEquals(5, GameDriver.getTournamentQueue().size());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs less
     * than two maps
     */
    @Test
    public void testTournament_LessThanTwoMaps_ShouldThrow() {
        List<String> maps = Collections.singletonList("map1.map");
        List<String> strategies = Arrays.asList("Aggressive", "Benevolent");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 3, 20)
        );

        assertEquals("Tournament must have at least two map files.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs less
     * than two strategies
     */
    @Test
    public void testTournament_LessThanTwoStrategies_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Collections.singletonList("Aggressive");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 3, 20)
        );

        assertEquals("Tournament must have between 2 and 4 player strategies.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs more
     * than four strategies
     */
    @Test
    public void testTournament_MoreThanFourStrategies_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("A", "B", "C", "D", "E");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 3, 20)
        );

        assertEquals("Tournament must have between 2 and 4 player strategies.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs less
     * than one game
     */
    @Test
    public void testTournament_GamesLessThanOne_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("Aggressive", "Random");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 0, 20)
        );

        assertEquals("Tournament must have between 1 and 5 games per map.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs more
     * than five games
     */
    @Test
    public void testTournament_GamesMoreThanFive_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("Aggressive", "Random");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 6, 20)
        );

        assertEquals("Tournament must have between 1 and 5 games per map.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs less
     * than ten turns
     */
    @Test
    public void testTournament_TurnsLessThanTen_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("Aggressive", "Random");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 3, 5)
        );

        assertEquals("Tournament must have between 10 and 50 turns per game.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs more
     * than fifty turns
     */
    @Test
    public void testTournament_TurnsMoreThanFifty_ShouldThrow() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("Aggressive", "Random");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 3, 60)
        );

        assertEquals("Tournament must have between 10 and 50 turns per game.", exception.getMessage());
    }

    /**
     * Testcase that verifies an exception is thrown when user inputs an
     * unrecognized behavior enum value
     */
    @Test
    public void testTournament_InvalidStrategyName_ShouldThrowException() {
        List<String> maps = Arrays.asList("map1.map", "map2.map");
        List<String> strategies = Arrays.asList("InvalidStrategy", "Random");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                d_play.tournament(maps, strategies, 2, 20)
        );

        assertTrue(exception.getMessage().contains("No enum constant"));
    }

}