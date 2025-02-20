import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Epic("Delivery Cost Calculation")
@DisplayName("Negative cases")
public class CalculateDeliveryNegativeTests {

    @Test
    @Tag("Negative")
    @DisplayName("Check return min price when calculated cost is < than min")
    @Severity(SeverityLevel.CRITICAL)
    void shouldReturnMinimumPriceWhenCostIsLow() {
        CalculateDelivery calculateDelivery = new CalculateDelivery(1, CargoDimension.SMALL, false, ServiceLoad.NORMAL);
        assertEquals(400, calculateDelivery.calculateDeliveryCost(), "Unexpected delivery cost");
    }

    @Test
    @DisplayName("Check throw exception when fragile item distance > 30 km")
    @Severity(SeverityLevel.BLOCKER)
    void shouldThrowExceptionForFragileItemsAbove30km() {
        CalculateDelivery calculateDelivery = new CalculateDelivery(31, CargoDimension.SMALL, true, ServiceLoad.NORMAL);
        Throwable exception = assertThrows(
                UnsupportedOperationException.class,
                calculateDelivery::calculateDeliveryCost
        );
        assertEquals("Fragile cargo cannot be delivered for the distance more than 30", exception.getMessage());
    }

}
