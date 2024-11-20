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


public class createApplianceActivity extends AppCompatActivity {

    EditText etCreateApplianceName, etCreateElectricCapacity, etCreateHoursUsed, etCreateDaysUsed;
    Button btnAdd, rtnECC; // rtn = return



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appliance);

        etCreateApplianceName = findViewById(R.id.etCreateApplianceName);
        etCreateElectricCapacity = findViewById(R.id.etCreateElectricCapacity);
        etCreateHoursUsed = findViewById(R.id.etCreateHoursUsed);
        etCreateDaysUsed = findViewById(R.id.etCreateDaysUsed);
        btnAdd = findViewById(R.id.btnEdit);
        rtnECC = findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appliance appliance;    //refactored first to be defined in the try catch block
                try{
                    appliance = new Appliance(-1, etCreateApplianceName.getText().toString(),
                            Float.parseFloat(etCreateElectricCapacity.getText().toString()),
                            Float.parseFloat(etCreateHoursUsed.getText().toString()),
                            Float.parseFloat(etCreateDaysUsed.getText().toString()));

                    applianceDatabaseHelper applianceDatabaseHelper= new applianceDatabaseHelper(createApplianceActivity.this);

                    boolean success = applianceDatabaseHelper.addOne(appliance);
                    Toast.makeText(createApplianceActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(createApplianceActivity.this, compute_activity.class);
                    startActivity(intent);


                }catch (Exception e){
                    Toast.makeText(createApplianceActivity.this, "Error: Invalid input", Toast.LENGTH_SHORT).show();
                }


            }
        });

        rtnECC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(createApplianceActivity.this, compute_activity.class));
            }
        });
    }


}