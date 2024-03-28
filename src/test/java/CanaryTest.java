import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Canary Test")
public class CanaryTest {
    @Test @DisplayName("should pass with true")
    void shouldPass() {
        assertTrue(true);
    }

    @Test @DisplayName("should pass with false")
    void shouldPassToo() {
        assertFalse(false);
    }
}
