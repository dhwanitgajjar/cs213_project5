package com.app.cs213_project5.models;

/**
 * Size of the pizza
 * @author Dhwanit gajjar
 */
public enum Size {
    SMALL,
    MEDIUM,
    LARGE;

    private Size() {}

    /**
     * Returns a string representation of the size of the pizza.
     * @return the string representation of the size of the pizza
     */
    @Override
    public String toString() {
        switch(this) {
            case SMALL: 
                return "Small";
            case MEDIUM: 
                return "Medium";
            case LARGE: 
                return "Large";
            default:
                return "";
        }
    }
}