package com.app.cs213_project5;

import com.app.cs213_project5.models.Order;
import com.app.cs213_project5.models.Pizza;
import java.util.ArrayList;



/**
 * Class for sharing data among activities using singleton pattern
 ** @author Dhwanit gajjar
 */
public class GlobalData {
    private static GlobalData instance;

    // List of all placed orders
    private ArrayList<Order> orders;

    // Counter for unique order numbers
    private int orderNumber;

    // Current order being processed
    private Order currentOrder;

    /**
     * Private Constructor for global data initializing
     * orderNumber and currentOrder object instantiated with orderNumber
     */
    private GlobalData() {
        orders = new ArrayList<>();
        orderNumber = 1; // Initialize to 1
        currentOrder = new Order(orderNumber);
    }

    // Get the Singleton instance
    /**
     * Get the Singleton instance if instance is null it will initialize otherwise it will return
     * same object to main consistency and enable sharing among activities
     */
    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    /**
     * Retrieves the list of all placed orders.
     *
     * @return an ArrayList of Order objects
     */
    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    /**
     * Adds a pizza to the current order.
     *
     * @param pizza The pizza to be added
     * @return true if the pizza was successfully added, false if the order is full
     */
    public boolean addToOrder(Pizza pizza) {
        return currentOrder.addPizza(pizza);
    }

    /**
     * Adds an order to the list of placed orders and increments the order number.
     *
     * @param order The order to be added
     * @return true if successfully added, false otherwise
     */
    public boolean addToOrdersArray(Order order) {
        this.orderNumber++; // Increment order number for the next order
        return this.orders.add(order);
    }

    /**
     * Clears the current order by creating a new order with the next order number.
     */
    public void clearCurrentOrder() {
        this.currentOrder = new Order(orderNumber);
    }

    /**
     * Removes the given order from the list of all placed orders.
     *
     * @param order The order to be removed
     * @return true if successfully removed, false otherwise
     */
    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    /**
     * Retrieves the current order being processed.
     *
     * @return the current Order object
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }
}
