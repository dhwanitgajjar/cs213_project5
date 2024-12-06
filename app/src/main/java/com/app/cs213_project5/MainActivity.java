package com.app.cs213_project5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Dashboard activity main screen.
 * Includes functionality to go to place order screen, current order and make an order screen
 * @author Dhwanit gajjar
 */
public class MainActivity extends AppCompatActivity {


    private Button orderScreen;
    private Button currentOrderScreen;

    private  Button placedOrderScreen;

    /**
     * Creates layout for Main Dashboard Activity,
     * It makes us to go to different screens like placed order, current order and make an order screen
     * @param savedInstanceState representing the instance if we want to obtain something
     * it contains information of previous state of activity, like it can have data and we can
     * store it like on configuration changes
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderScreen = findViewById(R.id.makeOrder);
        currentOrderScreen = findViewById(R.id.currentOrder);
        placedOrderScreen = findViewById(R.id.placedOrders);
        orderScreen.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        });

        currentOrderScreen.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        });
        placedOrderScreen.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, PlacedOrdersActivity.class);
            startActivity(intent);
        });


    }
}