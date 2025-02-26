package tests;                                       // This defines the package (matching our project structure).

import org.junit.jupiter.api.Test;                   // JUnit 5 annotation for marking test methods.
import static org.junit.jupiter.api.Assertions.*;    // Assertion to check if a condition is true.

public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        String message = "Hello, World!";
        assertEquals("Hello, World!", message);       // // This assertion always evaluates to "true", meaning the test will never fail.
    }
}
