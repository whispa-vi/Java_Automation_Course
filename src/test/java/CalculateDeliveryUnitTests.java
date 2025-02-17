import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CalculateDeliveryUnitTests {
    @Test
    @Tag("smoke")
    @DisplayName("Should return minimum price when calculated cost is lower than minimum")
    void shouldReturnMinimumPriceWhenCostIsLow() {
        double cost = CalculateDelivery.calculateDeliveryCost(1, "small", false, "normal");
        assertEquals(400.0, cost, "The minimum price should be 400.0");
    }

    @Test
    @DisplayName("Should throw exception when fragile item distance exceeds 30 km")
    void shouldThrowExceptionForFragileItemsAbove30km() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> CalculateDelivery.calculateDeliveryCost(31, "small", true, "normal"));
        assertEquals("Fragile items cannot be delivered beyond 30 km", exception.getMessage());
    }

    @Test
    @DisplayName("Should calculate cost correctly for large package, 15 km, high load")
    void shouldCalculateCorrectCost() {
        double cost = CalculateDelivery.calculateDeliveryCost(15, "large", false, "high");
        assertEquals(560.0, cost, "Expected delivery cost should be 560.0");
    }

    @Disabled
    @ParameterizedTest
    @DisplayName("Parameterized test for different distances and package sizes")
    @CsvSource({
            "5, small, false, , 400.0",
            "10, large, false, increased, 480.0",
            "20, small, false, very high, 640.0",
            "30, large, true, high, 840.0"
    })
    void testDeliveryCostForMultipleCases(int distance, String packageSize, boolean isFragile, String serviceLoad, double expectedCost) {
        double actualCost = CalculateDelivery.calculateDeliveryCost(distance, packageSize, isFragile, serviceLoad);
        assertEquals(expectedCost, actualCost, "Unexpected delivery cost");
    }

    @Disabled
    @ParameterizedTest
    @DisplayName("Should throw exception when parameters are missing")
    @CsvSource({
            ", small, false, high, 400",   // if distance null
            "10, , false, high",      // if packageSize null
            "10, small, , high",      // if isFragile null
            "10, small, false, "      // if serviceLoad null
    })
    void shouldThrowExceptionWhenParametersAreMissing(String distance, String packageSize, String isFragile, String serviceLoad) {
        Exception exception = assertThrows(Exception.class,
                () -> CalculateDelivery.calculateDeliveryCost(
                        distance != null ? Integer.parseInt(distance) : 10,
                        packageSize != null ? packageSize : "small",
                        isFragile != null ? Boolean.parseBoolean(isFragile) : false,
                        serviceLoad
                )
        );

        assertTrue(exception instanceof IllegalArgumentException || exception instanceof NullPointerException,
                "Expected IllegalArgumentException or NullPointerException");
    }

}
