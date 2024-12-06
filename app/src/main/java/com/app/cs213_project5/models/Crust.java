package com.app.cs213_project5.models;

/**
 * Class for the Crust
 * @author Dhwanit gajjar
 */
public enum Crust {
    THIN,
    DEEP_DISH,
    PAN,
    STUFFED,
    HAND_TOSSED,
    BROOKLYN;

    private Crust() {}

    /**
     * Returns the name of the crust as a string.
     * @return The name of the crust as a string
     */
    public String toString() {
        switch(this) {
            case THIN:
                return "Thin";
            case DEEP_DISH:
                return "Deep Dish";
            case PAN:
                return "Pan";
            case STUFFED:
                return "Stuffed";
            case HAND_TOSSED:
                return "Hand-Tossed";
            case BROOKLYN:
                return "Brooklyn";
            default:
                return "";
        }
    }
}