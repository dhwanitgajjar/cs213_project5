package com.app.cs213_project5;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.cs213_project5.models.Order;
import com.app.cs213_project5.models.Pizza;
import java.util.ArrayList;


/**
 * Activity for getting details of selected order using spinner value
 * Includes functionality to clear the order and navigate back to the main menu.
 * @author Dhwanit gajjar
 */
public class PlacedOrdersActivity extends AppCompatActivity {

    private Spinner orderSpinner;
    private ListView pizzaListView;
    private TextView totalOrderPriceField;
    private Button clearOrderButton, returnMainMenuButton;

    private ArrayList<Order> placedOrders;
    private Order selectedOrder;
    private ArrayAdapter<String> pizzaListAdapter;

    /**
     * Creates layout for Placed Order Activity,
     * It initialized the total order text view and list of pizzas for order selection
     * @param savedInstanceState representing the instance if we want to obtain something
     * it contains information of previous state of activity, like it can have data and we can
     * store it like on configuration changes
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_orders);

        // Initialize UI components
        orderSpinner = findViewById(R.id.spinnerOrders);
        pizzaListView = findViewById(R.id.listViewPizzas);
        totalOrderPriceField = findViewById(R.id.textViewOrderTotalPrice);
        clearOrderButton = findViewById(R.id.buttonClearOrder);
        returnMainMenuButton = findViewById(R.id.buttonMainMenuPlaced);


        // Set up Clear Order button
        clearOrderButton.setOnClickListener(v -> clearSelectedOrder());

        // Set up Main Menu button
        returnMainMenuButton.setOnClickListener(v -> navigateToMainMenu());

        // Fetch placed orders from GlobalData
        placedOrders = GlobalData.getInstance().getOrders();

        // Check if placedOrders is empty or null
        if (placedOrders == null || placedOrders.isEmpty()) {
            Toast.makeText(this, "No placed orders available.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Populate spinner with order IDs
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getOrderIDs()
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(spinnerAdapter);

        // Handle spinner selection
        orderSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                selectedOrder = placedOrders.get(position);
                updateOrderDetails();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });


    }

    /**
     * Function to return list of order ids of placed orders
     * @return orderIds representing list of order ids of Order objects
     */
    private ArrayList<String> getOrderIDs() {
        ArrayList<String> orderIDs = new ArrayList<>();
        for (Order order : placedOrders) {
            if (order != null) {
                orderIDs.add("Order #" + order.getNumber());
            }
            else {
                Toast.makeText(this, "Hey there, Id not found.", Toast.LENGTH_SHORT).show();
            }
        }
        return orderIDs;
    }


    /**
     * Called when the order number spinner selection changes
     * Sets the order list view to the list of pizzas in the selected order
     * and sets the order total text view to the total price of the selected order
     * If the order number spinner is null, does nothing
     */
    private void updateOrderDetails() {
        if (selectedOrder != null) {
            // Update ListView with selected order's pizzas
            ArrayList<String> pizzaDescriptions = new ArrayList<>();
            for (Pizza pizza : selectedOrder.getPizzas()) {
                pizzaDescriptions.add(pizza.toString());
            }

            pizzaListAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    pizzaDescriptions
            );
            pizzaListView.setAdapter(pizzaListAdapter);

            // Update total order price
            totalOrderPriceField.setText(String.format("$%.2f", selectedOrder.getTotalPrice()));
        }
    }

    /**
     * Cancels the order with the number currently selected in the order number Spinner.
     * Removes the order from the Global Data list of orders and updates the Spinner
     * to reflect the removal. Clears the order list view and order total text view to 0.00.
     */
    private void clearSelectedOrder() {
        if (selectedOrder != null) {
            GlobalData.getInstance().removeOrder(selectedOrder);
            placedOrders.remove(selectedOrder);

            // Refresh spinner and reset details
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    getOrderIDs()
            );
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            orderSpinner.setAdapter(spinnerAdapter);

            selectedOrder = null;
            pizzaListView.setAdapter(null);
            totalOrderPriceField.setText("$0.00");

            Toast.makeText(this, "Hey there, Order cleared.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Hey there, No order selected to clear.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigates to the main menu.
     */
    private void navigateToMainMenu() {
        Toast.makeText(this, "Returning to main menu...", Toast.LENGTH_SHORT).show();
        finish(); // Simulate navigating back
    }
}
