package com.secondapplication.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class loggedInSettingsActivity extends AppCompatActivity {
    TextView tvUsername, tvAge;
    Button btnEdit, btnDelete, btnSettingsBack, btnLogout, aboutBtn;
    AlertDialog.Builder builder;
    userDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_settings);

        tvUsername = findViewById(R.id.tvUsername);
        tvAge = findViewById(R.id.tvAge);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnSettingsBack = findViewById(R.id.btnSettingsBack);
        btnLogout = findViewById(R.id.btnLogout);
        aboutBtn =findViewById(R.id.btnAbout);

        db = new userDBHelper(this);
        builder = new AlertDialog.Builder(this);

        tvUsername.setText(MainActivity.userLoggedIn.getUserName());
        tvAge.setText(String.valueOf(MainActivity.userLoggedIn.getAge()));


        aboutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(loggedInSettingsActivity.this, AboutWirklich.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.userLoggedIn = null;
                Intent showMain = new Intent(loggedInSettingsActivity.this, MainActivity.class);
                startActivity(showMain);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showEditUserActivity = new Intent(loggedInSettingsActivity.this, editUserActivity.class);
                startActivity(showEditUserActivity);
            }
        });

        btnSettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showMain = new Intent(loggedInSettingsActivity.this, MainActivity.class);
                startActivity(showMain);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Delete Appliance")
                        .setMessage("Delete User: " + MainActivity.userLoggedIn.getUserName() + "?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (MainActivity.userLoggedIn != null){
                                    Boolean checkDelete = db.deleteOne(MainActivity.userLoggedIn);
                                    if (checkDelete){
                                        Toast.makeText(loggedInSettingsActivity.this, MainActivity.userLoggedIn.getUserName() + " deleted.", Toast.LENGTH_SHORT).show();
                                        MainActivity.userLoggedIn = null;
                                        Intent showMain = new Intent(loggedInSettingsActivity.this, MainActivity.class);
                                        startActivity(showMain);
                                        finish();
                                    }else { Toast.makeText(loggedInSettingsActivity.this, "Delete Failed.", Toast.LENGTH_SHORT).show(); }
                                }else{ Toast.makeText(loggedInSettingsActivity.this, "User not logged in.", Toast.LENGTH_SHORT).show(); }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
    }
}