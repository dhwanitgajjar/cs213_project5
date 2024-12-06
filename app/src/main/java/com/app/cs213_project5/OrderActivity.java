package com.app.cs213_project5;

import static android.app.ProgressDialog.show;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cs213_project5.adapters.PizzaAdapter;
import com.app.cs213_project5.models.BBQChicken;
import com.app.cs213_project5.models.BuildYourOwn;
import com.app.cs213_project5.models.ChicagoPizza;
import com.app.cs213_project5.models.Crust;
import com.app.cs213_project5.models.Deluxe;
import com.app.cs213_project5.models.Meatzza;
import com.app.cs213_project5.models.NYPizza;
import com.app.cs213_project5.models.Pizza;
import com.app.cs213_project5.models.PizzaFactory;
import com.app.cs213_project5.models.Size;
import com.app.cs213_project5.models.Topping;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Activity for creating pizzas for order.
 * Provides options to select pizza size, type, toppings, and style using spinners.
 * Includes functionality to navigate back to the main menu.
 * @author Dhwanit gajjar
 */

public class OrderActivity extends AppCompatActivity {

    private Spinner sizeSpinner, styleSpinner, typeSpinner;
    private TextView crustTextField, subTotalTextView;
    private LinearLayout toppingsLayout;
    private Button addToOrderButton, mainMenuButton;
    private  ImageView pizzaImage;
    private String selectedStyle, selectedType;

    private int resImageId;

    private  Size selectedSizeObject;

    private double subtotal;

    private static final int MAX_SELECTED_TOPPINGS = 7;

    private int totalSelected = 0;
    private ArrayList<String> selectedToppings;
    private RecyclerView pizzaRecyclerView;
    private PizzaAdapter pizzaAdapter;
    private ArrayList<HashMap<String, Object>> pizzaOrders;;


    /**
     * Creates layout for Order Activity,
     * It initialized the spinner data and have item listeners in case of
     * item clicked of spinners.
     * @param savedInstanceState representing the instance if we want to obtain something
     * it contains information of previous state of activity, like it can have data and we can
     * store it like on configuration changes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize UI components
        sizeSpinner = findViewById(R.id.sizeSpinner);
        styleSpinner = findViewById(R.id.styleSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        crustTextField = findViewById(R.id.crustTextField);
        subTotalTextView = findViewById(R.id.subTotalTextView);
        toppingsLayout = findViewById(R.id.toppingsLayout);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        pizzaImage = findViewById(R.id.imagePizza);
        pizzaRecyclerView = findViewById(R.id.rvPizzas);
        pizzaRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        pizzaOrders = new ArrayList<>();
        pizzaAdapter = new PizzaAdapter(this, pizzaOrders);

        pizzaRecyclerView.setAdapter(pizzaAdapter);
        selectedToppings = new ArrayList<>();

        // Populate spinners
        populateSpinners();

        // Generate checkboxes for toppings
        generateToppingCheckboxes();

        // Handle add to order button click
        addToOrderButton.setOnClickListener(v -> confirmOrder());

        // Handle main menu button click
        mainMenuButton.setOnClickListener(v -> navigateToMainMenu());

        // Set type spinner listener to update crust field
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = typeSpinner.getSelectedItem().toString();
                chooseTypeHandler();
                chooseStyleHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        styleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStyle = styleSpinner.getSelectedItem().toString();
                chooseStyleHandler();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Getting the selected enum value
                Size selectedSize = (Size) parent.getItemAtPosition(position);
                selectedSizeObject = selectedSize;
                chooseSizeHandler(selectedSize);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing here
            }
        });
    }

    /**
     * Populates spinners with their respective data.
     */
    private void populateSpinners() {

        ArrayAdapter<Size> sizeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Size.values() // Directly use the enum values
        );
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Chicago", "New York"});
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(styleAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Deluxe", "Meatzza", "BBQ Chicken", "Build Your Own"});
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

    }

    /**
     * Called when the size spinner selection is changed.
     * Retrieves the value of the size spinner and the type spinner
     * and creates a Pizza of the selected type with the selected size.
     * Updates the subtotal by calling the price method of the created
     * Pizza. If the selected type is "Build Your Own", it adds the price
     * of all the toppings to the subtotal. Finally, it updates the
     * subtotal text field with the new subtotal.
     * @param selectedSizeObject of Size class
     */
    public void chooseSizeHandler(Size selectedSizeObject) {
        String selectedSize = sizeSpinner.getSelectedItem() != null ? sizeSpinner.getSelectedItem().toString() : null;
        String selectedType = typeSpinner.getSelectedItem() != null ? typeSpinner.getSelectedItem().toString() : null;

        if (selectedSize != null && selectedType != null) {
            // Get a Pizza object for the selected type
            Pizza pizza = getPizzaOfType(selectedType);

            // Set the selected size
            pizza.setSize(selectedSizeObject);

            // Calculate the subtotal
             subtotal = pizza.price();

            // Add additional cost for "Build Your Own" based on selected toppings
            if (selectedType.equals("Build Your Own")) {
                changeSubtotal(selectedToppings.size() * 1.69);
            }

            // Update the subtotal TextView
            subTotalTextView.setText(String.format("%.2f", subtotal));
        }
    }

    /**
     * Called when the style selection spinner value is changed.
     * Retrieves the value of the style spinner and the type spinners
     * and sets the crust text field to the correct crust type based
     * on the selected style and type. If the selected style is "Chicago",
     * the crust type is "Deep Dish", "Stuffed", or "Pan" if the type is
     * "Deluxe", "Meatzza", or "BBQ Chicken", respectively. If the selected
     * type is "Build Your Own", the crust type is "Pan". If the selected
     * style is "New York", the crust type is "Brooklyn", "Hand-Tossed", or
     * "Thin" if the type is "Deluxe", "Meatzza", or "BBQ Chicken", respectively.
     * If the selected type is "Build Your Own", the crust type is "Hand-Tossed".
     */
    public void chooseStyleHandler() {
        String selectedStyle = styleSpinner.getSelectedItem().toString();
        String selectedType = typeSpinner.getSelectedItem().toString();

        if ((selectedStyle == "") || (selectedType == "")) {
            return; // Do nothing if no selection
        }

        if (selectedStyle.equals("Chicago")) {
            switch (selectedType) {
                case "Deluxe":
                    crustTextField.setText("Deep Dish");
                    break;
                case "Meatzza":
                    crustTextField.setText("Stuffed");
                    break;
                case "BBQ Chicken":
                    crustTextField.setText("Pan");
                    break;
                default:
                    crustTextField.setText("Pan");
                    break;
            }
        } else if (selectedStyle.equals("New York")) {
            switch (selectedType) {
                case "Deluxe":
                    crustTextField.setText("Brooklyn");
                    break;
                case "Meatzza":
                    crustTextField.setText("Hand-Tossed");
                    break;
                case "BBQ Chicken":
                    crustTextField.setText("Thin");
                    break;
                default:
                    crustTextField.setText("Hand-Tossed");
                    break;
            }
        }
    }

    /**
     * Handles the selection change in the type spinner .
     * If the selected type is "Build Your Own", enables the toppings
     * selection options and updates the image to "buildyourownpizza.png".
     * Clears any previously selected toppings.
     * If another type is selected, toppings selection will be of checkboxes types, updates the
     * image to the corresponding pizza type, and sets the default toppings
     * for the selected type. Calls the chooseStyleHandler and chooseSizeHandler
     * methods if respective spinner values are not null.
     */
    public void chooseTypeHandler() {
        if (selectedType.equals("Build Your Own")) {
            toppingsLayout.removeAllViews(); // Clear any previously added checkboxes
            selectedToppings.clear(); // Clear previous toppings
            generateToppingCheckboxes(); // Generate checkboxes for "Build Your Own"

            pizzaImage.setImageResource(R.drawable.buildyourownpizza);
            resImageId = R.drawable.buildyourownpizza;
        } else {
            selectedToppings.clear(); // Clear previous toppings
            toppingsLayout.removeAllViews(); // Clear checkboxes


            if (selectedType.equals("Deluxe")) {
                pizzaImage.setImageResource(R.drawable.deluxepizza);
                resImageId = R.drawable.deluxepizza;
                setSelectedToppings(Deluxe.getToppings());
                generateToppingTextViews(selectedToppings);

            } else if (selectedType.equals("Meatzza")) {
                pizzaImage.setImageResource(R.drawable.meatzza);
                resImageId = R.drawable.meatzza;
                setSelectedToppings(Meatzza.getToppings());
                generateToppingTextViews(selectedToppings);

            } else if (selectedType.equals("BBQ Chicken")) {
                pizzaImage.setImageResource(R.drawable.bbqchickenpizza);
                resImageId = R.drawable.bbqchickenpizza;
                setSelectedToppings(BBQChicken.getToppings());
                generateToppingTextViews(selectedToppings);

            }
        }

        // Handle style and size selections if already selected
        if (styleSpinner.getSelectedItem() != null) {
            chooseStyleHandler();
        }
        if (sizeSpinner.getSelectedItem() != null) {
            chooseSizeHandler(selectedSizeObject);
        }
    }

    /**
     * Initialize Selected toppings according to pizza style
     * @param stringToppings representing strings of toppings
     */
    public void setSelectedToppings(String[] stringToppings) {
        selectedToppings = new ArrayList<>(Arrays.asList(stringToppings));
    }

    /**
     * Generates checkboxes for available toppings.
     */

    private void generateToppingCheckboxes() {
        ArrayList<String> toppings = new ArrayList<>();
        for (Topping topping: Topping.values()) {
            toppings.add(topping.toString());
        }
        for (String topping : toppings) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(topping);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (totalSelected < MAX_SELECTED_TOPPINGS) {
                        selectedToppings.add(topping);
                        changeSubtotal(1.69);
                        totalSelected++;
                    } else {
                        // Prevent further selection
                        checkBox.setChecked(false); // Revert the checkbox state
                        Toast.makeText(this, "You can select up to " + MAX_SELECTED_TOPPINGS + " toppings only.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    selectedToppings.remove(topping);
                    if (this.subtotal > 0) {
                        changeSubtotal(-1.69);
                    }
                    totalSelected--;
                }

                //updateSubTotal();
            });
            toppingsLayout.addView(checkBox);
        }
    }

    private void generateToppingTextViews(ArrayList<String> toppings) {

        for (String topping : toppings) {
            TextView textView = new TextView(this);
            textView.setText(topping);


           // updateSubTotal();
            toppingsLayout.addView(textView );

    }

}


    /**
     * Gets a pizza object of the selected type.
     *
     * @param type The type of pizza.
     * @return A Pizza object.
     */
    private Pizza getPizzaOfType(String type) {
        NYPizza factory = new NYPizza();
        switch (type) {
            case "Deluxe":
                return factory.createDeluxe();
            case "Meatzza":
                return factory.createMeatzza();
            case "BBQ Chicken":
                return factory.createBBQChicken();
            default:
                return factory.createBuildYourOwn();
        }
    }

    /**
     * Confirms the order with an alert dialog.
     */
    private void confirmOrder() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Order")
                .setMessage("Are you sure you want to place this order?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    createPizza();
                    Toast.makeText(this, "Pizza added to current order successfully!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Navigates to the main menu.
     */
    private void navigateToMainMenu() {
        Toast.makeText(this, "Returning to main menu...", Toast.LENGTH_SHORT).show();
        finish(); // Simulate navigating back
    }

    public void createPizza() {
        Size size;
        String style;
        String type;
        Pizza pizza = null;

        if (selectedSizeObject != null) {
            size =    selectedSizeObject;;
        }
        else {
            Toast.makeText(this, "Please select a size", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedStyle!= null) {
            style = selectedStyle;
        } else {

            Toast.makeText(this, "Please select a style.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedType != null) {
            type = selectedType;
        } else {
            Toast.makeText(this, "Please select a type.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (style.compareTo("New York")==0) {
            PizzaFactory nyFactory = new NYPizza();
            if (type.compareTo("Deluxe")==0) {
                pizza = nyFactory.createDeluxe();
                ((Deluxe)pizza).setStyle(style);
            } else if (type.compareTo("Meatzza")==0) {
                pizza = nyFactory.createMeatzza();
                ((Meatzza)pizza).setStyle(style);
            } else if (type.compareTo("BBQ Chicken")==0) {
                pizza = nyFactory.createBBQChicken();
                ((BBQChicken)pizza).setStyle(style);
            } else {
                pizza = nyFactory.createBuildYourOwn();
                ((BuildYourOwn)pizza).setStyle(style);
            }
        } else if (style.compareTo("Chicago")==0) {
            PizzaFactory chicagoFactory = new ChicagoPizza();
            if (type.compareTo("Deluxe")==0) {
                pizza = chicagoFactory.createDeluxe();
                ((Deluxe)pizza).setStyle(style);
            } else if (type.compareTo("Meatzza")==0) {
                pizza = chicagoFactory.createMeatzza();
                ((Meatzza)pizza).setStyle(style);
            } else if (type.compareTo("BBQ Chicken")==0) {
                pizza = chicagoFactory.createBBQChicken();
                ((BBQChicken)pizza).setStyle(style);
            } else {
                pizza = chicagoFactory.createBuildYourOwn();
                ((BuildYourOwn)pizza).setStyle(style);
            }
        }

        assert pizza != null;
           pizza.setSize(size);
        pizza.setCrust(getCrust((String) crustTextField.getText()));

        if (type.compareTo("Build Your Own")==0) {
            for (String topping : selectedToppings) {
                pizza.addTopping(getTopping(topping));
            }
        }

        // Access the GlobalData singleton
        GlobalData globalData = GlobalData.getInstance();

        if (globalData.addToOrder(pizza)) {

            // Create a new pizza data entry
            HashMap<String, Object> pizzaData = new HashMap<>();
            pizzaData.put("size", selectedSizeObject.toString());
            pizzaData.put("style", selectedStyle);
            pizzaData.put("crust", getCrust((String) crustTextField.getText() ) );
            pizzaData.put("toppings", new ArrayList<>(selectedToppings)); // Clone toppings
            pizzaData.put("subtotal", subtotal);
            pizzaData.put("image", resImageId);

            Log.d("SubTotal",String.valueOf(subtotal));

            // Add to pizzaOrders list and refresh adapter
            pizzaOrders.add(pizzaData);
            pizzaAdapter.notifyDataSetChanged();




        }
        else {
            // Failed to add pizza
            Toast.makeText(this, "Failed to add pizza. Order might be full.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Updates the subtotal of the order by adding the specified amount.
     * The updated subtotal is rounded to two decimal places using half-up rounding.
     * This method does not update the subtotal text field or recalculate the sales tax
     * and order total.
     *
     * @param amount the amount to add to the current subtotal
     */
    private void changeSubtotal(Double amount) {

        BigDecimal result = new BigDecimal(Double.toString(subtotal));
        result = result.add(new BigDecimal(amount));
        subtotal = result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        subTotalTextView.setText("Subtotal : " + subtotal);
    }

    /**
     * Converts a string representation of a crust type to the corresponding Crust enum.
     *
     * @param crustString the string representation of the crust type
     * @return the corresponding Crust enum value, or null if the crustString does not match any known crust types
     */
    private Crust getCrust(String crustString) {
        switch(crustString) {
            case "Brooklyn":
                return Crust.BROOKLYN;
            case "Deep Dish":
                return Crust.DEEP_DISH;
            case "Hand-Tossed":
                return Crust.HAND_TOSSED;
            case "Pan":
                return Crust.PAN;
            case "Stuffed":
                return Crust.STUFFED;
            case "Thin":
                return Crust.THIN;
            default:
                return null;
        }
    }

    /**
     * Given a String representation of a topping, returns the corresponding Topping enum.
     * If the topping is not found, returns null.
     *
     * @param toppingString the String representation of the topping
     * @return the corresponding Topping enum, or null if not found
     */
    private Topping getTopping(String toppingString) {
        switch(toppingString) {
            case "Pepperoni":
                return Topping.PEPPERONI;
            case "Sausage":
                return Topping.SAUSAGE;
            case "Green Pepper":
                return Topping.GREEN_PEPPER;
            case "Onion":
                return Topping.ONION;
            case "Mushroom":
                return Topping.MUSHROOM;
            case "Bacon":
                return Topping.BACON;
            case "Beef":
                return Topping.BEEF;
            case "Ham":
                return Topping.HAM;
            case "Pineapple":
                return Topping.PINEAPPLE;
            case "Olive":
                return Topping.OLIVE;
            case "BBQ Chicken":
                return Topping.BBQ_CHICKEN;
            case "Cheddar":
                return Topping.CHEDDAR;
            case "Provolone":
                return Topping.PROVOLONE;
            case "Ricotta":
                return Topping.RICOTTA;
            case "Caramelized Onions":
                return Topping.CARAMELIZED_ONIONS;
            case "Arugula":
                return Topping.ARUGULA;
            case "Spinach":
                return Topping.SPINACH;
            default:
                return null;
        }
    }
}