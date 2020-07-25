package rs.dev.plasticstore.model;

public enum OrderStatus {

    CANCELED("OTKAZANA"), ORDERED("PORUČENA"), IN_PREPARATION("U_PRIPREMI"), SENT("POSLATA"), DELIVERED("DOSTAVLJENA");

    OrderStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    private final String displayValue;
}
