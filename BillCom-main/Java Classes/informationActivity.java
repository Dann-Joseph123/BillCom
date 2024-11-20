package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class informationActivity extends AppCompatActivity {

    Button adminBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        adminBtn = findViewById(R.id.adminBtn);
        backBtn = findViewById(R.id.tomainMenu);

        adminBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(informationActivity.this, LoginRegister.class));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(informationActivity.this, MainActivity.class));
            }
        });


    }
}