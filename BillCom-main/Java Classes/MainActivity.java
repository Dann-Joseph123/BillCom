package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.util.Log;




public class MainActivity extends AppCompatActivity
{

    public static User userLoggedIn;
    Button button1, button2, button3;
    ImageButton imageButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_SecondApplication);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageButton4 = findViewById(R.id.imageButton4);

        Log.d("abc", "onCreate: " + userLoggedIn);
        button2 = findViewById(R.id.button2);
        imageButton4 = findViewById(R.id.imageButton4);
        Log.d("abc", "onCreate: " + userLoggedIn);

        button2.setEnabled(userLoggedIn != null);


        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Intent MA = new Intent(MainActivity.this, compute_activity.class);
                MA.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(MA);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(MainActivity.this, recordbills_activity.class));
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(MainActivity.this, others_activity.class));
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if(userLoggedIn == null){
                    Intent showNotLoggedInSettingsActivity = new Intent(MainActivity.this, informationActivity.class);
                    startActivity(showNotLoggedInSettingsActivity);
                }else{
                    Intent showLoggedInSettingsActivity = new Intent(MainActivity.this, loggedInSettingsActivity.class);
                    startActivity(showLoggedInSettingsActivity);
                }
            }
        });

    }

}