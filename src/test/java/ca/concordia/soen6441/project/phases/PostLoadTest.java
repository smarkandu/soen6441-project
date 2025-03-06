package ca.concordia.soen6441.project.phases;


import ca.concordia.soen6441.project.GameEngine;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;
import ca.concordia.soen6441.project.phases.PostLoad;


class PostLoadTest { // Define test class for PostLoad


    @Test // Mark this method as a unit test
    void testSaveMap() {
        GameEngine l_gameEngine = mock(GameEngine.class); // Mock GameEngine (we don't call real logic)
        PostLoad l_postLoad = spy(new PostLoad(l_gameEngine)); // Create a spy for PostLoad to instantiate


        // Ensure toMapString() returns a valid map string
        when(l_gameEngine.toMapString()).thenReturn("[continents]\nAsia 5\n[countries]\n1 India Asia\n[borders]\n1 2 3");


        ByteArrayOutputStream l_outContent = new ByteArrayOutputStream(); // Capture console output
        System.setOut(new PrintStream(l_outContent)); // Redirect System.out to capture printed text


        l_postLoad.saveMap("test.map"); // Call the method (this should trigger console output)


        String l_output = l_outContent.toString().trim(); // Get printed output
        assertTrue(l_output.contains("Map successfully saved"), "Expected output should contain 'Map successfully saved'");


        // Verify that saveMap() was called once
        verify(l_postLoad, times(1)).saveMap("test.map");
    }
}
