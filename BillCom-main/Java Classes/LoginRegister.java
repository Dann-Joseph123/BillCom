package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class LoginRegister extends AppCompatActivity {

    private static String PREFERENCES = "preferences";

    Button Register,Login,Logout, btnBack;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnSettingsLogin, btnSettingsRegister, btnLoginBack;
    userDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        Register = (Button) findViewById(R.id.btnRegisterLoginRegister);
        Login = (Button) findViewById(R.id.btnLoginLoginRegister);
        btnBack = (Button) findViewById(R.id.btnBackLoginRegister);

        sharedPreferences = getSharedPreferences(PREFERENCES,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        db = new userDBHelper(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.queryNumEntries() < 4){
                    Intent showRegisterActivity = new Intent(LoginRegister.this, Register.class);
                    startActivity(showRegisterActivity);
                } else{
                    Toast.makeText(LoginRegister.this, "Exceeded number of registered users.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.queryNumEntries() > 0){
                    Intent showLoginActivity = new Intent(LoginRegister.this, loginactivity.class);
                    startActivity(showLoginActivity);
                } else{
                    Toast.makeText(LoginRegister.this, "There are no currently registered users.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), informationActivity.class);
                startActivity(intent); //Goes back to informationActivity
            }
        });

    }
}