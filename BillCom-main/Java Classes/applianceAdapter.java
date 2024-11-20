package com.secondapplication.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class applianceAdapter extends ArrayAdapter<Appliance> {

    public applianceAdapter(Context context, int resource, List<Appliance> applianceList){
        super(context, resource, applianceList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Appliance appliance = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appliance_cell, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.txtapplianceName);
        TextView tvElectricCapacity = (TextView) convertView.findViewById(R.id.txtElectricCapacity);
        TextView tvConsumptionPerDay = (TextView) convertView.findViewById(R.id.txtConsumptionPerDay);
        TextView tvCostPerDay = (TextView) convertView.findViewById(R.id.txtCostPerDay);
        TextView tvCostPerWeek = (TextView) convertView.findViewById(R.id.txtCostPerWeek);

        tvName.setText(appliance.getApplianceName());
        tvElectricCapacity.setText(String.valueOf(appliance.getElectricCapacity()));
        tvConsumptionPerDay.setText(String.valueOf(appliance.energyConsumptionCalculationperDay()));
        tvCostPerDay.setText(String.valueOf(appliance.electricityCostCalculationperDay()));
        tvCostPerWeek.setText(String.valueOf(appliance.electricityCostCalculationWeek()));
        return convertView;
    }
}