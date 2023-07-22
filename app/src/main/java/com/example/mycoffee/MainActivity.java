package com.example.mycoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static  int number = 2,cp =80;
    CheckBox whip_Check ,choco_Check ;
    ImageButton qrBtn;
    public static  String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrBtn = (ImageButton) findViewById(R.id.image_button);

        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),scanActivity.class));
            }
        });
        whip_Check = (CheckBox)findViewById(R.id.whipp);
        choco_Check = (CheckBox)findViewById(R.id.choco);

        whip_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();
                if(checked){
                    Message.message(MainActivity.this,"Extra charges for toppings");
                }
            }
        });
        choco_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if(checked){
                    Message.message(MainActivity.this,"Extra charges for toppings");

                }
            }
        });

    }


    public  void submitOrder(View view){
        EditText text = (EditText) findViewById(R.id.name);
        Editable nameEditable = text.getText();
        String name = nameEditable.toString();

        boolean hasWhipped = whip_Check.isChecked();
        boolean hasChoco = choco_Check.isChecked();

        int amount ;
        amount = calcPrice(number,hasWhipped,hasChoco);
        displayPrice(amount);
        displayMessage();

        String message = createOrderSummary(name, amount, hasWhipped, hasChoco);
        String recipient = "harshparadkar25@gmail.com";
        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

       startActivity(Intent.createChooser(intent,"Choose an email client"));
    }

    private void display(int number){
        TextView quantityTextView = (TextView)  findViewById(R.id.quantity_text_view);
        quantityTextView.setText(" "+ number);
    }

    public void increment(View view)
    {
        if(number<100){
            number += 1;
        }
        display(number);
        int price = number*cp;
        displayPrice(price);
    }

    public void decrement(View view)
    {
        if(number >1){
            number -= 1;
        }

        display(number);
        int price = number*cp;
        displayPrice(price);
    }

    private int calcPrice(int number,boolean hasWhipped,boolean hasChoco)
    {

        int amount ;
        if (hasWhipped&&hasChoco) {
            amount = (number * (cp + 30));
        }
        else if(hasWhipped){
            amount = number * (cp + 20);
        }
        else if(hasChoco ){
            amount = (number * (cp + 10));
        }
        else{
            amount = number*cp;
        }
        return amount;
    }

    private void displayPrice(int value)
    {
        TextView price_text_view = (TextView) findViewById(R.id.price_text_view);
        String tot = "Total ₹";
        price_text_view.setText(tot +" "+ value);
    }

    private void displayMessage()
    {
        TextView greeting = (TextView) findViewById(R.id.greeting);
        greeting.setText("THANK YOU");
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = name;
        priceMessage += "\n Table No. " + text;
        priceMessage += "\n Quantity: " + number;
        priceMessage += "\n Has whipped cream: " +addWhippedCream;
        priceMessage += "\n Has chocolate: "+ addChocolate;
        priceMessage += "\n Bill ₹"+ price;

        return priceMessage;
    }


    public void onCheckBoxClicked(View view) {
    }
}