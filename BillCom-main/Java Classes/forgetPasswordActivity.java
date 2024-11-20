package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class forgetPasswordActivity extends AppCompatActivity {
    EditText etUsernameForget, etBirthYearForget, etBirthMonthForget, etBirthDateForget, etNewPasswordForget, etRetypePasswordForget;
    Button btnResetPassword, btnBackForget;

    userDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etUsernameForget = findViewById(R.id.etUsernameForget);
        etBirthYearForget = findViewById(R.id.etBirthYearForget);
        etBirthMonthForget = findViewById(R.id.etBirthMonthForget);
        etBirthDateForget = findViewById(R.id.etBirthDateForget);
        etNewPasswordForget = findViewById(R.id.etNewPasswordForget);
        etRetypePasswordForget = findViewById(R.id.etRetypePasswordForget);

        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnBackForget = findViewById(R.id.btnBackForget);

        db = new userDBHelper(this);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsernameForget.getText().toString();
                String birthYear = etBirthYearForget.getText().toString();
                String birthMonth = etBirthMonthForget.getText().toString();
                String birthDate = etBirthDateForget.getText().toString();
                String newPassword = etNewPasswordForget.getText().toString();
                String retypedPassword = etRetypePasswordForget.getText().toString();

                if (username.equals("") || birthYear.equals("") || birthMonth.equals("") || birthDate.equals("") || newPassword.equals("") || retypedPassword.equals("")){
                    Toast.makeText(forgetPasswordActivity.this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    if (db.checkUsernameBirthday(username, birthYear, birthMonth, birthDate)){
                        if(newPassword.equals(retypedPassword)){
                            Boolean checkUpdatePassword = db.updateUserPassword(username, newPassword);
                            if (checkUpdatePassword){
                                Toast.makeText(forgetPasswordActivity.this, "Password Updated.", Toast.LENGTH_SHORT).show();
                                MainActivity.userLoggedIn = null;
                                Intent showMain = new Intent(forgetPasswordActivity.this, MainActivity.class);
                                startActivity(showMain);
                                finish();
                            }else { Toast.makeText(forgetPasswordActivity.this, "Password updated failed.", Toast.LENGTH_SHORT).show(); }
                        }else { Toast.makeText(forgetPasswordActivity.this, "Retyped wrong password.", Toast.LENGTH_SHORT).show(); }
                    }else { Toast.makeText(forgetPasswordActivity.this, "User information not found or does not match.", Toast.LENGTH_SHORT).show(); }
                }
            }
        });

        btnBackForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showLoginActivity = new Intent(forgetPasswordActivity.this, loginactivity.class);
                startActivity(showLoginActivity);
            }
        });
    }
}