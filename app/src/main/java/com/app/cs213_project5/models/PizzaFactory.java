package com.app.cs213_project5.models;

/**
 * Interface for creating different types of pizzas.
 * @author Dhwanit gajjar
 */
public interface PizzaFactory {
	Pizza createDeluxe();
	Pizza createMeatzza();
	Pizza createBBQChicken();
	Pizza createBuildYourOwn();
}