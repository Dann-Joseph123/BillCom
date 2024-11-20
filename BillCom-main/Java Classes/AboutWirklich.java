package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutWirklich extends AppCompatActivity {
    Button rtrnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_wirklich);

        rtrnButton = (findViewById(R.id.btnBackLoginRegister));

        rtrnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutWirklich.this, loggedInSettingsActivity.class));
            }
        });
    }
}