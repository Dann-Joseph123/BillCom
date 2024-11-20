package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class others_activity extends AppCompatActivity {
    public Button btnFuelPrice, btnCurrencyExchange, toMainmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        btnFuelPrice = findViewById(R.id.btnFuelPrice);
        btnFuelPrice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(others_activity.this, fuelprice_activity.class));
            }
        });
        btnCurrencyExchange = findViewById(R.id.btnCurrencyExchange);
        btnCurrencyExchange.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(others_activity.this, currencyexchange_activity.class));
            }
        });

        toMainmenu = findViewById(R.id.toMainmenu);
        toMainmenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(others_activity.this, MainActivity.class));
            }
        });
    }
} 