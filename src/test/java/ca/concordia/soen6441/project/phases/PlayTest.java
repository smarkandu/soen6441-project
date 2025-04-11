package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.behaviour.PlayerBehaviorType;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for class Play (abstract)
 */
class PlayTest
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

    /**
     * Setup method called before each test case
     */
    @BeforeEach
    void setup()
    {
        d_play = new PlayStub();
        d_mockLogBuffer = mock(LogEntryBuffer.class);
        LogEntryBuffer.setInstance(d_mockLogBuffer);
    }


    /**
     * Testcase for a successful load
     *
     * @throws IOException may occur due to file operations
     */
    @Test
    void testLoadGame_SuccessfulLoad() throws IOException
    {
        GameEngine l_mockGameEngine = mock(GameEngine.class);
        GameDriver.setGameEngine(l_mockGameEngine);

        File l_tempFile = File.createTempFile("test_game", ".ser");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(l_tempFile)))
        {
            out.writeObject(l_mockGameEngine);
        }

        d_play.loadGame(l_tempFile.getAbsolutePath());

        assertEquals(l_mockGameEngine.getClass(), GameDriver.getGameEngine().getClass());
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Game loaded from: " + l_tempFile.getAbsolutePath()), eq(true));
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
        File l_tempFile = File.createTempFile("invalid", ".ser");
        try (FileOutputStream l_fileOutputStream = new FileOutputStream(l_tempFile))
        {
            l_fileOutputStream.write("not a serialized object".getBytes());
        }

        assertDoesNotThrow(() -> d_play.loadGame(l_tempFile.getAbsolutePath()));
        verify(d_mockLogBuffer).appendToBuffer(startsWith("Issue loading game: " + l_tempFile.getAbsolutePath()), eq(true));
    }
}