/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        int price = calculatePrice();
        String orderMessage = createOrderSummary(price, hasWhippedCream(), hasChocolate(), customerName());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + customerName());
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);

        startActivity(intent);
    }

    /**
     * Calculates the price of the order based on the current quantity
     *
     * @return the price
     */
    private int calculatePrice() {
        boolean whippedCreamToppings = hasWhippedCream();
        boolean chocolateToppings = hasChocolate();

        int cupPrice = 5;

        if (chocolateToppings) {
            cupPrice = (cupPrice + 2);
        }

        if (whippedCreamToppings) {
            cupPrice = (cupPrice + 1);
        }

        return cupPrice * quantity;
    }

    /**
     * Creates an order summary of customer
     *
     * @param price the amount of total order
     * @param addWhippedCream checks if user wants whipped cream topping
     * @param addChocolate checks if the user wants chocolate toppings
     * @param name of the the customer
     * @return a message
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        return "Name: " + name + "\n" +
                "Add Whipped Cream? " + addWhippedCream + "\n" +
                "Add Chocolate? " + addChocolate + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: $" + (price) + "\n" +
                "Thank you!";
    }

    /**
    This method is called when the minus button, -, is clicked
     */
    public void decrement(View view) {
        if (quantity < 2) {
            //Show error message as toast
            Toast.makeText(this,"You cannot order below one cup",Toast.LENGTH_SHORT).show();
            //Exit the method early
            return;
        }

        quantity--;
        displayQuantity(quantity);
    }

    /**
     This method is called when the plus button, +, is clicked
     */
    public void increment(View view) {
        if (quantity >= 30) {
            //Show an error message as toast
            Toast.makeText(this,"You cannot order above 30 cups",Toast.LENGTH_SHORT).show();
            //Exit the method early
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantityOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantityOfCoffees);
    }

    /**
     * This method checks to see if the whipped cream checkbox is ticked
     * @return a boolean containing checkbox text and isChecked boolean
     */
    private boolean hasWhippedCream() {
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        return whippedCreamCheckbox.isChecked();
    }

    /**
     * This method checks if chocolate checkbox is checked
     * @return a boolean containing checkbox text and isChecked boolean
     */
    private boolean hasChocolate() {
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        return chocolateCheckbox.isChecked();
    }

    /**
     *Methods that fetches the name from the view
     * @return the name
     */
    private String customerName() {
        EditText customerNameView = findViewById(R.id.name_text_input);
        return customerNameView.getText().toString();
    }

}