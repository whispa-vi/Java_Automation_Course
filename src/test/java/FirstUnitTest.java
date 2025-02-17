import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstUnitTest {
    @Test
    void sumNumbersTests() {
        int a = 3;
        int b = 2;

        int expectedSum = 5;
        int actualSum = a + b;

        assertEquals(expectedSum, actualSum);
    }
}
