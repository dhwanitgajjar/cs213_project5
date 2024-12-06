package com.app.cs213_project5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cs213_project5.R;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter for Pizzas being added to order
 * @author dhwanit gajjar
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, Object>> pizzaOrders;

    /**
     * Constructor for Adapter for initializing the context and pizzaOrders list
     * @param context for finding parent like which activity
     * @param pizzaOrders for list that to be displayed
     */
    public PizzaAdapter(Context context, ArrayList<HashMap<String, Object>> pizzaOrders) {
        this.context = context;
        this.pizzaOrders = pizzaOrders;
    }

    /**
     * For creating the view of adapter layout
     * @param parent for finding parent like which activity
     * @param viewType for viewType like which view either text view, radio button or etc
     */
    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for the pizza order
        View view = LayoutInflater.from(context).inflate(R.layout.item_current_order, parent, false);
        return new PizzaViewHolder(view);
    }

    /**
     * For binding or linking the data to the view created according to position number
     * @param holder for view item
     * @param position for view item position
     */
    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        // Retrieve pizza data
        HashMap<String, Object> pizzaData = pizzaOrders.get(position);

        // Bind views with pizza data
        holder.textPizzaSize.setText("Pizza Size: " + pizzaData.get("size"));
        holder.textPizzaStyle.setText("Pizza Style: " + pizzaData.get("style"));
        holder.textPizzaCrust.setText("Crust: " + pizzaData.get("crust"));


        int imageResId = (int) pizzaData.get("image");
        holder.pizzaImage.setImageResource(imageResId);


        holder.textSelectedToppings.setText("Selected Toppings:");


        // Convert toppings list to bullet points
        ArrayList<String> toppings = (ArrayList<String>) pizzaData.get("toppings");
        StringBuilder toppingsBuilder = new StringBuilder();
        for (String topping : toppings) {
            toppingsBuilder.append("• ").append(topping).append("\n");
        }
        holder.textToppingsList.setText(toppingsBuilder.toString());
        holder.textSubtotal.setText("Subtotal: $" + pizzaData.get("subtotal"));
    }

    /**
     * It will return how many items are currently in our list like how many pizzas are
     * added to order
     * @return size of pizzaOrders list
     */

    @Override
    public int getItemCount() {
        return pizzaOrders.size();
    }

    /**
     * Pizza View Holder class for finding the views using their ids
     * Making it static will make it independent of its outer class that is PizzaAdapter
     */
    public static class PizzaViewHolder extends RecyclerView.ViewHolder {

        TextView textPizzaSize, textPizzaStyle, textPizzaCrust, textSelectedToppings, textToppingsList, textSubtotal;

        ImageView pizzaImage;


        /**
         * For initializing the views according to their ids
         * @param itemView represnting the view that is whole container containing all pizza attributes
         */
        public PizzaViewHolder(View itemView) {
            super(itemView);
            textPizzaSize = itemView.findViewById(R.id.textPizzaSize);
            textPizzaStyle = itemView.findViewById(R.id.textPizzaStyle);
            textPizzaCrust = itemView.findViewById(R.id.textPizzaCrust);
            textSelectedToppings = itemView.findViewById(R.id.textSelectedToppings);
            textToppingsList = itemView.findViewById(R.id.textToppingsList);
            textSubtotal = itemView.findViewById(R.id.textSubtotal);
            pizzaImage = itemView.findViewById(R.id.pizzaImage);

        }
    }
}
