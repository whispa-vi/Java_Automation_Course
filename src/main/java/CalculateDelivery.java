public class CalculateDelivery {

    private static final double MIN_DELIVERY_COST = 400.0;

    public static double calculateDeliveryCost(int distanceToPoint, String packageSize, boolean isFragile, String serviceLoad) {
        int cost = 0;

        if (distanceToPoint > 30) {
            if (isFragile) {
                throw new IllegalArgumentException("Fragile items cannot be delivered beyond 30 km");
            }
            cost += 300;
        } else if (distanceToPoint <= 30 && distanceToPoint > 10) {
            cost += 200;
        } else if (distanceToPoint <= 10 && distanceToPoint > 2) {
            cost += 100;
        } else {
            cost += 50; // distance <= 2
        }

        if ("large".equalsIgnoreCase(packageSize)) {
            cost += 200;
        } else if ("small".equalsIgnoreCase(packageSize)) {
            cost += 100;
        } else {
            throw new IllegalArgumentException("Wrong value for packageDimensions");
        }

        if (isFragile) {
            cost += 300;
        }

        double loadCoefficient = switch (serviceLoad.toLowerCase()) {
            case "very high" -> 1.6;
            case "high" -> 1.4;
            case "increased" -> 1.2;
            default -> 1.0;
        };

        cost *= (double) loadCoefficient;

        return Math.max(cost, MIN_DELIVERY_COST);
    }

    public static void main(String[] args) {
        try {
            double cost = calculateDeliveryCost(30, "small", true, "high");
            System.out.println("Delivery cost = " + cost);
        } catch (IllegalArgumentException e) {
            System.out.println("Attention error: " + e.getMessage());
        }
    }

}
