package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class editDeleteApplianceActivity extends AppCompatActivity {
    EditText etEditApplianceName, etEditElectricCapacity, etEditHoursUsed, etEditDaysUsed;
    Button btnEdit, btnDelete, btnBacktoECC;
    Appliance selectedAppliance;
    AlertDialog.Builder builder;
    applianceDatabaseHelper applianceDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_appliance);

        etEditApplianceName = findViewById(R.id.etEditApplianceName);
        etEditElectricCapacity = findViewById(R.id.etEditElectricCapacity);
        etEditHoursUsed = findViewById(R.id.etEditHoursUsed);
        etEditDaysUsed = findViewById(R.id.etEditDaysUsed);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBacktoECC = findViewById(R.id.btnBack);

        applianceDatabaseHelper = new applianceDatabaseHelper(editDeleteApplianceActivity.this);
        builder = new AlertDialog.Builder(this);

        //get selected appliance
        selectedAppliance = compute_activity.applianceClicked;

        //set selected appliance
        etEditApplianceName.setText(selectedAppliance.getApplianceName());
        etEditElectricCapacity.setText(String.valueOf(selectedAppliance.getElectricCapacity()));
        etEditHoursUsed.setText(String.valueOf(selectedAppliance.getHoursUsed()));
        etEditDaysUsed.setText(String.valueOf(selectedAppliance.getDaysUsed()));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Delete Appliance")
                        .setMessage("Delete ID: " + selectedAppliance.getId() + ", " + selectedAppliance.getApplianceName() +"?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (selectedAppliance != null){
                                    applianceDatabaseHelper.deleteOne(selectedAppliance);
                                    //showAppliancesOnListView(applianceDatabaseHelper);
                                    Toast.makeText(editDeleteApplianceActivity.this, "Appliance " + selectedAppliance.getApplianceName() + " deleted. ", Toast.LENGTH_SHORT).show();
                                    compute_activity.applianceClicked = null;
                                    Intent tocompute_activity = new Intent(editDeleteApplianceActivity.this, compute_activity.class);
                                    startActivity(tocompute_activity);
                                } else{
                                    Toast.makeText(editDeleteApplianceActivity.this, "Appliance not found.", Toast.LENGTH_SHORT).show();
                                }
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String updatedApplianceName = etEditApplianceName.getText().toString();
                    float updatedElectricCapacity = Float.parseFloat(etEditElectricCapacity.getText().toString());
                    float updatedHoursUsed = Float.parseFloat(etEditHoursUsed.getText().toString());
                    float updatedDaysUsed = Float.parseFloat(etEditDaysUsed.getText().toString());
                    String id = String.valueOf(selectedAppliance.getId());

                    Appliance applianceToUpdate = selectedAppliance;
                    String applianceID = String.valueOf(applianceToUpdate.getId());
                    applianceToUpdate.setApplianceName(updatedApplianceName);
                    applianceToUpdate.setElectricCapacity(updatedElectricCapacity);
                    applianceToUpdate.setHoursUsed(updatedHoursUsed);
                    applianceToUpdate.setDaysUsed(updatedDaysUsed);

                    //applianceDatabaseHelper applianceDatabaseHelper = new applianceDatabaseHelper(editDeleteApplianceActivity.this);
                    Boolean checkUpdateData = applianceDatabaseHelper.updateAppliance(applianceToUpdate);

                    compute_activity.applianceClicked = null;
                    if (checkUpdateData == true){
                        Toast.makeText(editDeleteApplianceActivity.this, applianceID + " updated.", Toast.LENGTH_SHORT).show();
                        Intent toMainActivity = new Intent(editDeleteApplianceActivity.this, compute_activity.class);
                        startActivity(toMainActivity);
                    }
                    else{
                        Toast.makeText(editDeleteApplianceActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(editDeleteApplianceActivity.this, "Error updating", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBacktoECC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(editDeleteApplianceActivity.this, compute_activity.class));
            }
        });

    }
}