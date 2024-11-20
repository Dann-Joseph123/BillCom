package com.secondapplication.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

public class loginactivity extends AppCompatActivity {

    Button btnBackLogin,btnLogin, btnForgetPassword;
    AlertDialog.Builder builder;

    EditText etUsernameLogin, etPasswordLogin;
    userDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBackLogin = findViewById(R.id.btnBackLogin);
        builder = new AlertDialog.Builder(this);
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnForgetPassword = findViewById(R.id.btnForgetPassword);
        btnLogin = findViewById(R.id.btnLogInLogin);
        db = new userDBHelper(loginactivity.this);

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showForgetPasswordActivity = new Intent(loginactivity.this, forgetPasswordActivity.class);
                startActivity(showForgetPasswordActivity);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsernameLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if(username.equals("") || password.equals(""))
                    Toast.makeText(loginactivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserpass = db.checkUsernamePassword(username, password);
                    if (checkUserpass){
                        MainActivity.userLoggedIn = db.getSingleUserInfo(username);
                        Toast.makeText(loginactivity.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                        Intent showMain = new Intent(loginactivity.this, MainActivity.class);
                        startActivity(showMain);
                        finish();
                    }else{
                        Toast.makeText(loginactivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(loginactivity.this, MainActivity.class));
                builder.setTitle("Warning")
                        .setMessage("Do you want to go back to the Main menu? Any inputs won't" +
                                " be saved.")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                startActivity(new Intent(loginactivity.this,
                                        MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });
    }

}