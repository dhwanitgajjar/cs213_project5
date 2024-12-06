package com.app.cs213_project5.models;

import java.util.ArrayList;

/**
 * Class for the Pizza
 * @author Dhwanit gajjar
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;
    public abstract double price();

    public Pizza() {
        toppings = new ArrayList<>();
    }

    public Pizza(Crust crust) {
        this.crust = crust;
        toppings = new ArrayList<>();
    }

    /**
     * Adds a topping to the pizza
     * @param topping the topping to add
     */
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    /**
     * Sets the toppings of the pizza
     * @param toppings the list of toppings to set
     */
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Sets the size of the pizza.
     * @param size the size to set
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Sets the crust of the pizza.
     * @param crust the crust to set
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Returns a string representation of the toppings on the pizza, separated by commas.
     * @return a string representation of the toppings
     */
    private String toppingsString() {
        String toppingsString = "";
        for (Topping topping : toppings) {
            toppingsString += topping.toString() + ", ";
        }

        return toppingsString;
    }

    /**
     * Returns the number of toppings on the pizza.
     * @return the number of toppings on the pizza
     */
    public int amountOfToppings() {
        return toppings.size();
    }

    /**
     * Retrieves the size of the pizza.
     * 
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**
     * Returns a string representation of the pizza, including its style, type, crust, toppings, size, and price.
     * @return a string representation of the pizza
     */
    @Override
    public String toString() {
        String type = "";
        String style = "";

        if (this instanceof BBQChicken) {
            type = "BBQ Chicken";
            if (((BBQChicken) this).getStyle() != null) {
                style = ((BBQChicken) this).getStyle();
            }
        } else if (this instanceof Deluxe) {
            type = "Deluxe";
            if (((Deluxe) this).getStyle() != null) {
                style = ((Deluxe) this).getStyle();
            }
        } else if (this instanceof Meatzza) {
            type = "Meatzza";
            if (((Meatzza) this).getStyle() != null) {
                style = ((Meatzza) this).getStyle();
            }
        } else if (this instanceof BuildYourOwn) {
            type = "Build Your Own";
            if (((BuildYourOwn) this).getStyle() != null) {
                style = ((BuildYourOwn) this).getStyle();
            }
        }

        return style+ " Style, " + type +", (" + crust.toString() + " Crust) "+", " +
                toppingsString()+ "(Size " + size.toString() + "), $"+Double.toString(price());
    }
}