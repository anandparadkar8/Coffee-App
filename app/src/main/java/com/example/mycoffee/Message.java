package com.example.mycoffee;

import android.content.Context;
import android.widget.Toast;

public class Message {
    public static void message(Context context,String mssg){
        Toast.makeText(context , mssg,Toast.LENGTH_SHORT).show();
    }
}
