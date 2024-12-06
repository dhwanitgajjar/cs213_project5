package com.app.cs213_project5.models;

/**
 * Class for the Deluxe pizza
 * @author Dhwanit gajjar
 */
public class Deluxe extends Pizza {

    
    private static final double SMALL_PRICE = 16.99;
    private static final double MEDIUM_PRICE = 18.99;
    private static final double LARGE_PRICE = 20.99;
    private static final Topping[] toppings = {Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM};

    private String style;

    public Deluxe(Crust crust) {
        super(crust);
    }

    /**
     * Gets the style of the pizza
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style of the pizza.
     * @param style the style to set for the pizza
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Calculates and returns the price of the Deluxe pizza based on its size.
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
     * Returns an array of strings of the toppings of the Deluxe pizza.
     * @return an array of strings of the toppings of the Deluxe pizza
     */
    public static String[] getToppings() {
        String[] stringToppings = new String[toppings.length];
        for (int i = 0; i < toppings.length; i++) {
            stringToppings[i] = toppings[i].toString();
        }
        return stringToppings;
    }

}
