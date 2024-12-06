package com.app.cs213_project5.models;

/**
 * The Topping enum represents different toppings that can be added to a pizza.
 * 
 * @author Dhwanit gajjar
 */
public enum Topping {
    SAUSAGE,
    PEPPERONI,
    CHEDDAR,
    ONION,
    GREEN_PEPPER,
    PINEAPPLE,
    HAM,
    BBQ_CHICKEN,
    BEEF,
    MUSHROOM,
    OLIVE,
    BACON,
    PROVOLONE,
    RICOTTA,
    CARAMELIZED_ONIONS,
    ARUGULA,
    SPINACH;

    private final static double PRICE = 1.69;

    private Topping() {}

    /**
     * Returns the price of the topping.
     * 
     * @return the price of the topping
     */
    public double getPrice() {
        return PRICE;
    }

    /**
     * Returns the String representation of the topping, which is the name of the topping
     * with the first letter capitalized.
     * 
     * @return the String representation of the topping
     */
    public String toString() {
        switch(this) {
            case SAUSAGE:
                return "Sausage";
            case PEPPERONI:
                return "Pepperoni";
            case CHEDDAR:
                return "Cheddar";
            case ONION:
                return "Onion";
            case GREEN_PEPPER:
                return "Green Pepper";
            case PINEAPPLE:
                return "Pineapple";
            case HAM:
                return "Ham";
            case BBQ_CHICKEN:
                return "BBQ Chicken";
            case BEEF:
                return "Beef";
            case MUSHROOM:
                return "Mushroom";
            case OLIVE:
                return "Olive";
            case BACON:
                return "Bacon";
            case PROVOLONE:
                return "Provolone";
            case RICOTTA:
                return "Ricotta";
            case CARAMELIZED_ONIONS:
                return "Caramelized Onions";
            case ARUGULA:
                return "Arugula";
            case SPINACH:
                return "Spinach";
            default:
                return "";
        }
    }
}