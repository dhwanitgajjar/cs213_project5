package com.app.cs213_project5;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.cs213_project5.models.Pizza;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


/**
 * Activity for seeing current order.
 * Includes functionality to place the order and navigate back to the main menu.
 * @author Dhwanit gajjar
 */
public class CurrentOrderActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> pizzaDescriptions;

    private EditText orderNumberField;
    private TextView subtotalField, salesTaxField, orderTotalField;
    private Button clearOrderButton, mainMenuButton, placeOrderButton;
    private double subtotal = 0.0, salesTax = 0.0, orderTotal = 0.0;


    /**
     * Creates layout for Current Order Activity,
     * It initialized the subtotal field and list of pizzas for current order
     * @param savedInstanceState representing the instance if we want to obtain something
     * it contains information of previous state of activity, like it can have data and we can
     * store it like on configuration changes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        // Initialize UI components
        listView = findViewById(R.id.listViewCurrentOrder);
        orderNumberField = findViewById(R.id.editTextOrderNumber);
        subtotalField = findViewById(R.id.textSubtotal);
        salesTaxField = findViewById(R.id.textSalesTax);
        orderTotalField = findViewById(R.id.textOrderTotal);
        clearOrderButton = findViewById(R.id.buttonClearOrder);
        mainMenuButton = findViewById(R.id.buttonReturnMainMenu);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        // Fetch current order from GlobalData
        pizzaDescriptions = new ArrayList<>();
        for (Pizza pizza : GlobalData.getInstance().getCurrentOrder().getPizzas()) {
            pizzaDescriptions.add(pizza.toString());
        }

        // Initialize order number
        orderNumberField.setText(String.valueOf(GlobalData.getInstance().getCurrentOrder().getNumber()));

        // Set up ListView adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pizzaDescriptions);
        listView.setAdapter(adapter);

        // Calculate initial values
        calculateOrderValues();

        // Set up Clear Order button
        clearOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOrder();
            }
        });

        // Set up Placed Order button
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        mainMenuButton.setOnClickListener(v ->
        {
            Toast.makeText(this, "Returning to main menu...", Toast.LENGTH_SHORT).show();
            finish(); // Simulate navigating back
        });

        // Set up long click listener to remove pizzas
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            removePizza(position);
            return true;
        });
    }

    /**
     * Calculating the subtotal, order-total and tax values
     */
    private void calculateOrderValues() {
        BigDecimal bd_subtotal = BigDecimal.ZERO; // Reset subtotal to 0

        // Calculate subtotal by iterating through the pizzas
        for (Pizza pizza : GlobalData.getInstance().getCurrentOrder().getPizzas()) {
            Log.d("Price: ", String.valueOf(pizza.price()));
            bd_subtotal = bd_subtotal.add(new BigDecimal(pizza.price()));
        }

        // Round and set the subtotal
        subtotal = bd_subtotal.setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Recalculate tax and total
        changeTax();
        orderTotal = bd_subtotal.add(new BigDecimal(salesTax)).setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Update UI fields
        subtotalField.setText("Subtotal: $ " + (subtotal));
        salesTaxField.setText("Sales Tax: $ "  + (salesTax));
        orderTotalField.setText("Order Total: $ " + (orderTotal));
    }


    /**
     * function for onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        refreshOrderData();
    }

    /**
     * Function to place order and store data using GlobalData class singleton pattern
     * Places the current order by adding it to the homepage's list of placed orders,
     * clearing the current order, and updating the current order to the next new order.
     * If the current order is empty, no action is taken.
     */
    private void placeOrder() {
        // Check if the current order contains pizzas
        if (!GlobalData.getInstance().getCurrentOrder().getPizzas().isEmpty()) {
            // Add current order to the list of placed orders in GlobalData
            GlobalData.getInstance().addToOrdersArray( GlobalData.getInstance().getCurrentOrder());

            // Clear the current order
            clearOrder();

            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No pizzas in the order to place.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Refresh the order data
     */
    private void refreshOrderData() {
        // Fetch current order from GlobalData
        pizzaDescriptions.clear();
        for (Pizza pizza : GlobalData.getInstance().getCurrentOrder().getPizzas()) {
            pizzaDescriptions.add(pizza.toString());
        }

        // Update order number
        orderNumberField.setText(String.valueOf(GlobalData.getInstance().getCurrentOrder().getNumber()));

        // Notify adapter to refresh ListView
        adapter.notifyDataSetChanged();

        // Recalculate order values
        calculateOrderValues();
    }

    /**
     * Removes the selected pizza from the current order. The subtotal is updated
     * by subtracting the price of the removed pizza. The order list view is
     * refreshed to reflect the removal, and the current order is updated.
     */
    private void removePizza(int position) {
        Pizza pizza = GlobalData.getInstance().getCurrentOrder().getPizzas().get(position);

        // Remove pizza from GlobalData and update ListView
        GlobalData.getInstance().getCurrentOrder().getPizzas().remove(position);
        pizzaDescriptions.remove(position);
        adapter.notifyDataSetChanged();

        // Update subtotal
        changeSubtotal(-pizza.price());
        Toast.makeText(this, "Pizza removed.", Toast.LENGTH_SHORT).show();
    }


    /**
     * Clears the current order by calling the GlobalData Instance clearCurrentOrder method
     * and updating the current order. Clears the order list and updates the order list view
     * to reflect the change. Resets the subtotal to zero and updates the subtotal text view.
     * Clears the order number text view.
     */
    private void clearOrder() {
        GlobalData.getInstance().clearCurrentOrder();
        pizzaDescriptions.clear();
        adapter.notifyDataSetChanged();

        subtotal = 0.0;
        salesTax = 0.0;
        orderTotal = 0.0;

        changeSubtotal(0.0);
        orderNumberField.setText("");
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Changes the subtotal of the order by the given amount.
     * The new subtotal is rounded to two decimal places with half-up rounding.
     * The subtotal text field is updated, and the sales tax and order total
     * are recalculated.
     * @param amount the amount to add to the subtotal
     */
    private void changeSubtotal(Double amount) {

        BigDecimal result = new BigDecimal(Double.toString(subtotal));
        result = result.add(new BigDecimal(amount));
        subtotal = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        subtotalField.setText("Subtotal: $ " + (subtotal));

        changeTax();
        changeOrderTotal();
    }


    /**
     * Calculates the sales tax for the current order by multiplying the subtotal
     * by a factor of 0.06625. The result is rounded to two decimal places with
     * half-up rounding. Updates the sales tax text field with the result.
     */
    protected void changeTax() {
        BigDecimal result = new BigDecimal(Double.toString(subtotal));
        result = result.multiply(new BigDecimal(0.06625));
        salesTax = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        salesTaxField.setText("Sales Tax: $ "  + (salesTax));
    }

    /**
     * Calculates the order total by adding the subtotal and sales tax.
     * The new order total is rounded to two decimal places with half-up rounding.
     * The order total text field is updated.
     */
    private void changeOrderTotal() {
        BigDecimal result = new BigDecimal(Double.toString(subtotal));
        result = result.add(new BigDecimal(salesTax));
        orderTotal = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        orderTotalField.setText("Order Total: $ " + (orderTotal));
    }

}
