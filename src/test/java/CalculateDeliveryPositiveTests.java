import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Delivery Cost Calculation")
@Feature("Cost Calculation Functionality")
@DisplayName("Positive cases")
public class CalculateDeliveryPositiveTests {

    @ParameterizedTest
    @Tag("Positive")
    @DisplayName("Check different basic distances and package sizes")
    @CsvSource({
            "5, small, false, , 400.0",
            "10, large, false, increased, 400.0",
            "20, small, false, very high, 480.0",
            "30, large, true, high, 980.0"
    })
    void testDeliveryCostForMultipleCases(int distance, String size, boolean isFragile, String serviceLoad, double expectedCost) {
        CargoDimension cargoDimension = CargoDimension.valueOf(size.toUpperCase());
        ServiceLoad load = serviceLoad == null || serviceLoad.isEmpty() ? ServiceLoad.NORMAL : ServiceLoad.valueOf(serviceLoad.toUpperCase().replace(" ", "_"));

        CalculateDelivery calculateDelivery = new CalculateDelivery(distance, cargoDimension, isFragile, load);
        double actualCost = calculateDelivery.calculateDeliveryCost();

        assertEquals(expectedCost, actualCost, "Unexpected delivery cost");
    }


    @Test
    @DisplayName("Check calculate cost correctly for large package, 15 km, high load")
    @Severity(SeverityLevel.NORMAL)
    void shouldCalculateCorrectCost() {
        CalculateDelivery calculateDelivery = new CalculateDelivery(15, CargoDimension.LARGE, false, ServiceLoad.HIGH);
        assertEquals(560.0, calculateDelivery.calculateDeliveryCost());
    }

}
