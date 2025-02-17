public class CalculateDelivery {

    private static final double MIN_DELIVERY_PRICE = 400.0;

    public static double calculateDeliveryCost(int distanceToPoint, String packageSize, boolean isFragile, String serviceLoad) {
        int price = 0;

        if (distanceToPoint > 30) {
            if (isFragile) {
                throw new IllegalArgumentException("Fragile items cannot be delivered beyond 30 km");
            }
            price += 300;
        } else if (distanceToPoint <= 30 && distanceToPoint > 10) {
            price += 200;
        } else if (distanceToPoint <= 10 && distanceToPoint > 2) {
            price += 100;
        } else {
            price += 50; // distance <= 2
        }

        if ("large".equalsIgnoreCase(packageSize)) {
            price += 200;
        } else if ("small".equalsIgnoreCase(packageSize)) {
            price += 100;
        } else {
            throw new IllegalArgumentException("Wrong value for packageDimensions");
        }

        if (isFragile) {
            price += 300;
        }

        double loadCoefficient = 1.0;
        if ("very high".equalsIgnoreCase(serviceLoad)) {
            loadCoefficient = 1.6;
        } else if ("high".equalsIgnoreCase(serviceLoad)) {
            loadCoefficient = 1.4;
        } else if ("increased".equalsIgnoreCase(serviceLoad)) {
            loadCoefficient = 1.2;
        };

        price *= (double) loadCoefficient;

        return Math.max(price, MIN_DELIVERY_PRICE);
    }

    public static void main(String[] args) {
        try {
            double cost = calculateDeliveryCost(20, "small", true, "");
            System.out.println("Delivery cost = " + cost);
        } catch (IllegalArgumentException e) {
            System.out.println("Attention error: " + e.getMessage());
        }
    }

}
