public enum ServiceLoad {
    VERY_HIGH(1.6),
    HIGH(1.4),
    INCREASED(1.2),
    NORMAL(1);

    private double deliveryRate;

    ServiceLoad(double deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public double getDeliveryRate() {
        return deliveryRate;
    }
}
