package com.secondapplication.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class currencyexchange_activity extends AppCompatActivity {

    Button btnCEmainMenu;
    RecyclerView currenciesRecyclerView;
    CurrencyAdapter adapter;
    AlertDialog.Builder builder;
    ArrayList<CurrencyModel> currencies;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CURRENCIES = "currencies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        btnCEmainMenu = findViewById(R.id.btnCEmainMenu);
        currenciesRecyclerView = findViewById(R.id.currenciesRecyclerView);
        builder = new AlertDialog.Builder(this);

        if(!isConnected(this)){
            showCustomDialogue();
            loadData();
            setRecyclerView();
        }else{
            currencyExchangeWebscrape cw = new currencyExchangeWebscrape();
            cw.execute();
        }

        btnCEmainMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(currencyexchange_activity.this, others_activity.class));
            }
        });

    }

    private boolean isConnected(currencyexchange_activity currencyexchange_activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) currencyexchange_activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    private void showCustomDialogue() {
        builder.setTitle("Network Error").setMessage("Error Connecting to network. BillComm will use previously scraped data for Currency Exchange.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadData();
                        setRecyclerView();
                    }
                }).show();
    }

    private void saveCurrencies(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currencies);
        editor.putString(CURRENCIES, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CURRENCIES, null);
        Type type = new TypeToken<ArrayList<CurrencyModel>>() {}.getType();
        currencies = gson.fromJson(json, type);

        if (currencies == null){
            currencies = new ArrayList<>();
        }
    }

    private void setRecyclerView() {
        currenciesRecyclerView.setHasFixedSize(true);
        currenciesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CurrencyAdapter(this, getList());
        currenciesRecyclerView.setAdapter(adapter);
    }


    private List<CurrencyModel> getList() {return currencies;}

    private class currencyExchangeWebscrape extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
             currencies = new ArrayList<>();
            org.jsoup.nodes.Document document = null;
            try {
                document = Jsoup.connect("https://www.x-rates.com/table/?from=PHP&amount=1").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element tableElement = document.getElementsByClass("ratesTable").select("tbody").first();

            for(Element tableRows : tableElement.select("tr")){
                String currency = tableRows.select("td").get(0).text();
                String toPeso = tableRows.select("td").get(1).text();
                String toCurrency = tableRows.select("td").get(2).text();

                CurrencyModel currencyToAdd = new CurrencyModel(currency, toPeso, toCurrency);
                currencies.add(currencyToAdd);

            }
            for (CurrencyModel currency : currencies){
                Log.d("abc", "doInBackground: " + currency.toString());
            }
            saveCurrencies();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            setRecyclerView();
        }
    }
}