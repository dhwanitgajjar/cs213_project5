package com.app.cs213_project5.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Class for the Order
 * @author Dhwanit gajjar
 */
public class Order {
    private int number;
    private ArrayList<Pizza> pizzas;

    public Order(int number) {
        this.number = number;
        this.pizzas = new ArrayList<Pizza>();
    }

    /**
     * Retrieves the list of all pizzas in the order.
     * 
     * @return an ArrayList of Pizza objects representing the pizzas in the order
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Retrieves the number of the order.
     * 
     * @return the order number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Adds the given pizza to the list of pizzas in the order.
     * 
     * @param pizza the pizza to add to the order
     * @return true if the pizza was successfully added to the order, false if the pizza was already in the order
     */
    public boolean addPizza(Pizza pizza) {
        return pizzas.add(pizza);
    }

    /**
     * Removes the specified pizza from the list of pizzas in the order.
     * 
     * @param pizza the pizza to remove from the order
     * @return true if the pizza was successfully removed, false if the pizza was not found in the order
     */
    public boolean removePizza(Pizza pizza) {
        return pizzas.remove(pizza);
    }

    /**
     * Calculates and returns the total price of all pizzas in the order.
     * 
     * @return the total price of all pizzas in the order
     */
    public double getTotalPrice() {

        double total = 0;
        BigDecimal result = new BigDecimal(Double.toString(total));

        for (Pizza pizza : pizzas) {
            result = result.add(new BigDecimal(pizza.price()));
        }
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();

    }

}