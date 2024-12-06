package com.app.cs213_project5.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class for the Build Your Own Pizza
 * @author Dhwanit gajjar
 */
public class BuildYourOwn extends Pizza{

    private static final double SMALL_PRICE = 8.99;
    private static final double MEDIUM_PRICE = 10.99;
    private static final double LARGE_PRICE = 12.99;

    private String style;

    /**
     * Constructor
     * @param crust
     */
    public BuildYourOwn(Crust crust) {
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
     * Converts a double to a BigDecimal, and sets the scale to 2 places with
     * rounding mode of HALF_UP.
     * @param d the double to be formatted
     * @return the formatted BigDecimal
     */
    private BigDecimal formatDouble(double d) {
        BigDecimal result = new BigDecimal(Double.toString(d));
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the price of the toppings on the pizza.
     * @return the total price of the toppings
     */
    private BigDecimal toppingsPrice() {


        return formatDouble(1.69 * amountOfToppings());
    }

    /**
     * Calculates and returns the price of the Build Your Own pizza based on its size.
     * 
     * @return The price of the pizza for the specified size: SMALL, MEDIUM, or LARGE.
     */
    public double price() {
        double basePrice = 0;

        if (super.getSize() == Size.SMALL) {
            basePrice = SMALL_PRICE;
        } else if (super.getSize() == Size.MEDIUM) {
            basePrice = MEDIUM_PRICE;
        } else {
            basePrice = LARGE_PRICE;
        }

        return (formatDouble(basePrice).add(toppingsPrice())).doubleValue();

    } 

}
