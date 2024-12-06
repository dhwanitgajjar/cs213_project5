package com.app.cs213_project5.models;

/**
 * Class for the Meatzza pizza
 * @author Dhwanit gajjar
 */
public class Meatzza extends Pizza {

    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;
    private static final Topping[] toppings = {Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM};

    private String style;

    /**
     * Constructor
     * @param crust
     */
    public Meatzza(Crust crust) {
        super(crust);
    }

    /**
     * Gets the style of the Meatzza pizza.
     * @return the style of the pizza.
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style of the Meatzza pizza.
     * @param style the style of the pizza to set.
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Calculates and returns the price of the Meatzza pizza based on its size.
     * 
     * @return The price of the pizza for the specified size: SMALL, MEDIUM, or LARGE.
     */
    public double price() {
        if (super.getSize() == Size.SMALL) {
            return SMALL_PRICE;
        } else if (super.getSize() == Size.MEDIUM) {
            return MEDIUM_PRICE;
        } else {
            return LARGE_PRICE;
        }
    }

    /**
     * Returns an array of strings of the toppings of the Meatzza pizza.
     * @return an array of strings of the toppings of the Meatzza pizza
     */
    public static String[] getToppings() {
        String[] stringToppings = new String[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            stringToppings[i] = toppings[i].toString();
        }
        return stringToppings;
    }

}
