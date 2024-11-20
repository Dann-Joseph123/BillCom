package com.secondapplication.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.ViewHolder> {

    Context context;
    List<FuelModel> fuelModelList;

    public FuelAdapter(Context context, List<FuelModel> fuelModelList) {
        this.context = context;
        this.fuelModelList = fuelModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fuel_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (fuelModelList != null && fuelModelList.size() > 0){
            FuelModel model = fuelModelList.get(position);
            holder.txtFuel.setText(model.getFuelType());
            holder.txtDate.setText(model.getDate());
            holder.txtPHP.setText(model.getPriceInPHP());
            holder.txtUSD.setText(model.getPriceInUSD());
        }else {

        }

    }

    @Override
    public int getItemCount() {
        return fuelModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFuel, txtDate, txtPHP, txtUSD;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFuel = itemView.findViewById(R.id.txtFuel);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtPHP = itemView.findViewById(R.id.txtPHP);
            txtUSD = itemView.findViewById(R.id.txtUSD);
        }
    }
}