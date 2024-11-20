package com.secondapplication.app;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class editUserActivity extends AppCompatActivity {
    Button btnEditDetails, backbtn;
    EditText etNewUsername, etNewBirthYear, etNewBirthMonth, etNewBirthDate;
    userDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        btnEditDetails = findViewById(R.id.btnEditDetails);
        backbtn = findViewById(R.id.bckbtn);

        db = new userDBHelper(this);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewBirthYear = findViewById(R.id.etNewBirthYear);
        etNewBirthMonth = findViewById(R.id.etNewBirthMonth);
        etNewBirthDate = findViewById(R.id.etNewBirthDate);

        etNewUsername.setText(MainActivity.userLoggedIn.getUserName());
        etNewBirthYear.setText(String.valueOf(MainActivity.userLoggedIn.getBirthYear()));
        etNewBirthMonth.setText(String.valueOf(MainActivity.userLoggedIn.getBirthMonth()));
        etNewBirthDate.setText(String.valueOf(MainActivity.userLoggedIn.getBirthDate()));


        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedUserName = etNewUsername.getText().toString();
                String password = MainActivity.userLoggedIn.getPassword();
                String strUpdatedBirthYear = etNewBirthYear.getText().toString();
                String strUpdatedBirthMonth = etNewBirthMonth.getText().toString();
                String strUpdatedBirthDate = etNewBirthDate.getText().toString();

                if(updatedUserName.equals("") || strUpdatedBirthYear.equals("") || strUpdatedBirthMonth.equals("") || strUpdatedBirthDate.equals("")){
                    Toast.makeText(editUserActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else{
                    if (dobIsValid(Integer.parseInt(strUpdatedBirthYear), Integer.parseInt(strUpdatedBirthMonth), Integer.parseInt(strUpdatedBirthDate))){
                        User updatedUser = new User(updatedUserName, password, Integer.parseInt(strUpdatedBirthYear), Integer.parseInt(strUpdatedBirthMonth), Integer.parseInt(strUpdatedBirthDate));
                        Boolean checkUpdateData = db.updateUserDetails(MainActivity.userLoggedIn, updatedUser);
                        if (checkUpdateData){
                            MainActivity.userLoggedIn = updatedUser;
                            Toast.makeText(editUserActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                            Intent showLoggedInSettingsActivity = new Intent(editUserActivity.this, loggedInSettingsActivity.class);
                            startActivity(showLoggedInSettingsActivity);
                            finish();
                        }else{ Toast.makeText(editUserActivity.this, "Update Failed", Toast.LENGTH_SHORT).show(); }
                    }
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(editUserActivity.this, loggedInSettingsActivity.class));
            }
        });

    }
    public boolean dobIsValid(int year, int month, int day){
        int currentyear = Calendar.getInstance().get(Calendar.YEAR);
        if (year < currentyear && (month >= 1 && month <= 12)){ //if valid year and month
            //for feb
            if(month == 2){ return day >= 1 && day <= 29; }
            else if (((month < 8 && (month % 2 == 1)) || (month >= 8 && (month % 2 == 0))) && (day >= 1 && day <= 31)){ return true; }
            else return ((month < 9 && (month % 2 == 0)) || (month >= 9 && (month % 2 == 1))) && (day >= 1 && day <= 30);
        }else{ return false; }
    }
}