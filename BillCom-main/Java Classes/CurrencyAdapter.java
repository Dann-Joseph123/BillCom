package com.secondapplication.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    Context context;
    List<CurrencyModel> currencyList;

    public CurrencyAdapter(Context context, List<CurrencyModel> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (currencyList != null && currencyList.size() > 0){
            CurrencyModel model = currencyList.get(position);
            holder.txtCurrencies.setText(model.getCurrency());
            holder.txtToPeso.setText(model.getToPeso());
            holder.txtInv.setText(model.getInv());
        }
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCurrencies, txtToPeso, txtInv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCurrencies = itemView.findViewById(R.id.txtCurrencies);
            txtToPeso = itemView.findViewById(R.id.txtToPeso);
            txtInv = itemView.findViewById(R.id.txtInv);
        }
    }
}
