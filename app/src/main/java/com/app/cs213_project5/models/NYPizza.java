package com.app.cs213_project5.models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for the New York Pizza Factory
 * @author Dhwanit gajjar
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a Brooklyn crust and a predefined set of toppings.
     * 
     * @return a Deluxe pizza with sausage, pepperoni, green pepper, onion, and mushroom toppings.
     */
    public Pizza createDeluxe() {
        Topping[] DELUXE_TOPPINGS = {Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM};
        Pizza pizza = new Deluxe(Crust.BROOKLYN);
        pizza.setToppings(new ArrayList<Topping>(Arrays.asList(DELUXE_TOPPINGS)));
        return pizza;
    }
    /**
     * Creates a Meatzza pizza with a hand-tossed crust and a predefined set of toppings.
     * 
     * @return a Meatzza pizza with sausage, pepperoni, beef, and ham toppings.
     */
	public Pizza createMeatzza() {
        Topping[] MEATZZA_TOPPINGS = {Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM};
        Pizza pizza = new Meatzza(Crust.HAND_TOSSED);
        pizza.setToppings(new ArrayList<Topping>(Arrays.asList(MEATZZA_TOPPINGS)));
        return pizza;
    };
    /**
     * Creates a BBQ Chicken pizza with a thin crust and a predefined set of toppings.
     * 
     * @return a BBQ Chicken pizza with BBQ chicken, green pepper, provolone, and cheddar toppings.
     */
	public Pizza createBBQChicken() {
        Topping[] BBQ_TOPPINGS = {Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR};
        Pizza pizza = new BBQChicken(Crust.THIN);
        pizza.setToppings(new ArrayList<Topping>(Arrays.asList(BBQ_TOPPINGS)));
        return pizza;
    };
    /**
     * Creates a Build Your Own pizza with a hand-tossed crust and no toppings.
     * 
     * @return a Build Your Own pizza with a hand-tossed crust and no toppings.
     */
	public Pizza createBuildYourOwn(){
        return new BuildYourOwn(Crust.HAND_TOSSED);
    };
}
