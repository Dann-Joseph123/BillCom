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

public class fuelprice_activity extends AppCompatActivity {
    Button btnMainMenu;
    RecyclerView recviewPetrolPrices;
    FuelAdapter adapter;
    ArrayList<FuelModel> fuelList;
    AlertDialog.Builder builder;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FUELS = "fuels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelprice);

        btnMainMenu = findViewById(R.id.btnMainMenu);
        recviewPetrolPrices = findViewById(R.id.recviewPetrolPrices);
        builder = new AlertDialog.Builder(this);

        if (!isConnected(this)) {
            showCustomDialogue();
            loadData();
            setRecyclerView();
        } else {
            petrolPricesWebscrape petrolPricesWebscrape = new petrolPricesWebscrape();
            petrolPricesWebscrape.execute();
        }

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fuelprice_activity.this, others_activity.class));
            }
        });

    }

    private void saveFuels () {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fuelList);
        editor.putString(FUELS, json);
        editor.apply();
    }

    private void loadData () {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FUELS, null);
        Type type = new TypeToken<ArrayList<FuelModel>>() {
        }.getType();
        fuelList = gson.fromJson(json, type);

        if (fuelList == null) {
            fuelList = new ArrayList<>();
        }
    }

    private void showCustomDialogue () {
        builder.setTitle("Network Error").setMessage("Error Connecting to network. BillComm will use previously scraped data for Currency Exchange.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadData();
                        setRecyclerView();
                    }
                }).show();
    }

    private void setRecyclerView () {
        recviewPetrolPrices.setHasFixedSize(true);
        recviewPetrolPrices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FuelAdapter(this, getList());
        recviewPetrolPrices.setAdapter(adapter);
    }

    private List<FuelModel> getList () {
        return fuelList;
    }

    private class petrolPricesWebscrape extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fuelList = new ArrayList<>();
            org.jsoup.nodes.Document document = null;

            try {
                document = Jsoup.connect("https://www.globalpetrolprices.com/Philippines/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element tableElement = document.select("tbody").get(0);

            for (Element tableRows : tableElement.select("tr")) {
                Log.d("abc", "doInBackground: " + tableRows.text() + " ");
                String fuelType = tableRows.select("th").get(0).text();
                String date = tableRows.select("td").get(0).text();
                String priceInPHP = tableRows.select("td").get(1).text();
                String priceInUSD = tableRows.select("td").get(2).text();
                FuelModel fuelToAdd = new FuelModel(fuelType, date, priceInPHP, priceInUSD);
                fuelList.add(fuelToAdd);
            }
            saveFuels();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setRecyclerView();
        }
    }

    private boolean isConnected(fuelprice_activity fuelprice_activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) fuelprice_activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }else{
            return false;
        }
    }
}