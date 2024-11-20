package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.DatabaseUtils;

import java.util.Calendar;

import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    EditText username, password, repassword, etRegisterBirthYear, etRegisterBirthMonth, etRegisterBirthDate;
    Button btnRegister, btnBack;
    TextInputLayout HiderTop, HiderBottom;

    userDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.usernameRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        repassword = (EditText) findViewById(R.id.repasswordRegister);
        btnRegister = (Button) findViewById(R.id.btnRegisterRegister);
        btnBack = (Button) findViewById(R.id.btnBackRegister);
        HiderTop = (TextInputLayout) findViewById(R.id.HiderTopRegister);
        HiderBottom = (TextInputLayout) findViewById(R.id.HiderBottomRegister);
        etRegisterBirthYear = findViewById(R.id.etRegisterBirthYear);
        etRegisterBirthMonth = findViewById(R.id.etRegisterBirthMonth);
        etRegisterBirthDate = findViewById(R.id.etRegisterBirthDate);
        db = new userDBHelper(this);

        HiderTop.setEndIconCheckable(false);
        HiderBottom.setEndIconCheckable(false);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String strYr = etRegisterBirthYear.getText().toString();
                String strMo = etRegisterBirthMonth.getText().toString();
                String strDate = etRegisterBirthDate.getText().toString();

                // walang nalagay si user ng kahit ano
                if(user.equals("") || pass.equals("") || repass.equals("") || strYr.equals("") || strMo.equals("") || strDate.equals(""))
                    Toast.makeText(Register.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                else {
                    if(pass.equals(repass)){
                        Boolean checkUser = db.checkUsername(user);
                        if (db.queryNumEntries() <= 4){
                            if (!checkUser){
                                if(dobIsValid(Integer.parseInt(strYr), Integer.parseInt(strMo), Integer.parseInt(strDate))){
                                    try{
                                        User userToAdd = new User(user, pass, Integer.parseInt(strYr), Integer.parseInt(strMo), Integer.parseInt(strDate));
                                        boolean success = db.addOne(userToAdd);
                                        Toast.makeText(Register.this, "Success: " + success, Toast.LENGTH_SHORT).show();
                                        Intent showMain = new Intent(Register.this, MainActivity.class);
                                        startActivity(showMain);
                                        finish();
                                    }catch (Exception e){
                                        Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }else{ Toast.makeText(Register.this, "Invalid Date.", Toast.LENGTH_SHORT).show(); }
                            }else{ Toast.makeText(Register.this, "User already exist.", Toast.LENGTH_SHORT).show(); }
                        }else{ Toast.makeText(Register.this, "Exceeded maximum amount of users (4)", Toast.LENGTH_SHORT).show(); }
                    }else{ Toast.makeText(Register.this, "Retyped the wrong password.", Toast.LENGTH_SHORT).show(); }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginRegister.class);
                startActivity(intent); //Goes back to Main activity
            }
        });
    }
    public boolean dobIsValid(int year, int month, int day){
        int currentyear = Calendar.getInstance().get(Calendar.YEAR);
        if (year < currentyear && (month >= 1 && month <= 12)){ //if valid year and month
            //for feb
            if(month == 2){
                return day >= 1 && day <= 29;
            }
            else if (((month < 8 && (month % 2 == 1)) || (month >= 8 && (month % 2 == 0))) && (day >= 1 && day <= 31)){ return true; }
            else return ((month < 9 && (month % 2 == 0)) || (month >= 9 && (month % 2 == 1))) && (day >= 1 && day <= 30);
        }else{ return false; }
    }
}