import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Delivery Cost Calculation")
@Feature("Cost Calculation Functionality")
@Story("User calculates delivery cost")
public class CalculateDeliveryUnitTests {

    @ParameterizedTest
    @DisplayName("Parameterized test for different distances and package sizes")
    @Description("Runs multiple test cases for various distances, package sizes, and service loads.")
    @CsvSource({
            "5, small, false, , 400.0",
            "10, large, false, increased, 400.0",
            "20, small, false, very high, 480.0",
            "30, large, true, high, 979.0"
    })
    @Severity(SeverityLevel.NORMAL)
    void testDeliveryCostForMultipleCases(int distance, String packageSize, boolean isFragile, String serviceLoad, double expectedCost) {
        Allure.step(String.format("Calling calculateDeliveryCost with distance=%d, packageSize=%s, isFragile=%b, serviceLoad=%s",
                distance, packageSize, isFragile, serviceLoad));
        double actualCost = CalculateDelivery.calculateDeliveryCost(distance, packageSize, isFragile, serviceLoad);
        Allure.step("Verifying that the expected cost matches the actual cost");
        assertEquals(expectedCost, actualCost, "Unexpected delivery cost");
    }

    @Test
    @Tag("smoke")
    @DisplayName("Should return minimum price when calculated cost is lower than minimum")
    @Description("Ensures that the minimum delivery price of 400.0 is applied when calculated cost is below it.")
    @Severity(SeverityLevel.CRITICAL)
    void shouldReturnMinimumPriceWhenCostIsLow() {
        Allure.step("Calling calculateDeliveryCost with distance=1, packageSize=small, isFragile=false, serviceLoad=normal");
        double cost = CalculateDelivery.calculateDeliveryCost(1, "small", false, "normal");
        Allure.step("Verifying that the cost is equal to 400.0");
        assertEquals(400.0, cost, "The minimum price should be 400.0");
    }

    @Test
    @DisplayName("Should throw exception when fragile item distance exceeds 30 km")
    @Description("Verifies that an exception is thrown if a fragile item is delivered beyond 30 km.")
    @Severity(SeverityLevel.BLOCKER)
    void shouldThrowExceptionForFragileItemsAbove30km() {
        Allure.step("Calling calculateDeliveryCost with distance=31, packageSize=small, isFragile=true, serviceLoad=normal");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> CalculateDelivery.calculateDeliveryCost(31, "small", true, "normal"));
        Allure.step("Checking that the exception message is correct");
        assertEquals("Fragile items cannot be delivered beyond 30 km", exception.getMessage());
    }

    @Test
    @DisplayName("Should calculate cost correctly for large package, 15 km, high load")
    @Description("Tests delivery cost calculation for a large package at 15 km distance with high load.")
    @Severity(SeverityLevel.NORMAL)
    void shouldCalculateCorrectCost() {
        Allure.step("Calling calculateDeliveryCost with distance=15, packageSize=large, isFragile=false, serviceLoad=high");
        double cost = CalculateDelivery.calculateDeliveryCost(15, "large", false, "high");
        Allure.step("Verifying that the expected cost is 560.0");
        assertEquals(560.0, cost, "Expected delivery cost should be 560.0");
    }

    @Disabled
    @ParameterizedTest
    @DisplayName("Should throw exception when parameters are missing")
    @Description("Checks behavior when some parameters are missing.")
    @CsvSource({
            ", small, false, high, 400",   // if distance null
            "10, , false, high",      // if packageSize null
            "10, small, , high",      // if isFragile null
            "10, small, false, "      // if serviceLoad null
    })
    @Severity(SeverityLevel.MINOR)
    void shouldThrowExceptionWhenParametersAreMissing(String distance, String packageSize, String isFragile, String serviceLoad) {
        Allure.step(String.format("Calling calculateDeliveryCost with distance=%s, packageSize=%s, isFragile=%s, serviceLoad=%s",
                distance, packageSize, isFragile, serviceLoad));
        Exception exception = assertThrows(Exception.class,
                () -> CalculateDelivery.calculateDeliveryCost(
                        distance != null ? Integer.parseInt(distance) : 10,
                        packageSize != null ? packageSize : "small",
                        isFragile != null ? Boolean.parseBoolean(isFragile) : false,
                        serviceLoad
                )
        );
        Allure.step("Verifying that the thrown exception is either IllegalArgumentException or NullPointerException");
        assertTrue(exception instanceof IllegalArgumentException || exception instanceof NullPointerException,
                "Expected IllegalArgumentException or NullPointerException");
    }
}
