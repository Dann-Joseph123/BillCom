package com.secondapplication.app;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
//import android.widget.SearchView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class compute_activity extends AppCompatActivity {

    public static ArrayList<Appliance> applianceList = new ArrayList<Appliance>();
    ListView lvAppliance;
    Button btnCreate, rtnMainmenu;
    applianceDatabaseHelper applianceDatabaseHelper;
    public static Appliance applianceClicked;
    AlertDialog.Builder builder;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COST_KEY = "cost_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        btnCreate = findViewById(R.id.btnCreate);
        rtnMainmenu = findViewById(R.id.btnBacktoMainmenu);
        applianceDatabaseHelper = new applianceDatabaseHelper(compute_activity.this);
        applianceClicked = null;
        builder = new AlertDialog.Builder(this);



        setupDataAndList();
        initSearchWidgets();
        

        if(!isConnected(compute_activity.this)){
            showCustomDialog();
        }else{
            electricityCostWebscrape electricityCostWebscrape= new electricityCostWebscrape();
            electricityCostWebscrape.execute();
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showCreateApplianceActivity = new Intent(compute_activity.this, createApplianceActivity.class);
                startActivity(showCreateApplianceActivity);
            }


        });

        lvAppliance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                applianceClicked = (Appliance) adapterView.getItemAtPosition(i);
                Intent toEditApplianceActivity = new Intent(compute_activity.this, editDeleteApplianceActivity.class);
                startActivity(toEditApplianceActivity);

            }
        });
        rtnMainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(compute_activity.this, MainActivity.class));
            }
        });
    }

    private void showCustomDialog() {
        builder.setTitle("Network Error")
                .setMessage("Cannot connect to the network for updated data. BillCom use previously " +
                        "scraped data for electricity cost, Zero if this is your first time using this app.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Appliance.electricityCost = previousCost();
                            setupDataAndList();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("IO", "IO" + e);
                            Appliance.electricityCost = previousCost();
                        }
                    }
                })
                .show();
    }

    private boolean isConnected(compute_activity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    public void saveCostToSharedPrefs(Float cost){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(COST_KEY, cost);
        editor.apply();
    }

    public float previousCost(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        float prevCost = sharedPreferences.getFloat(COST_KEY, 0);
        return prevCost;
    }

    private class electricityCostWebscrape extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            org.jsoup.nodes.Document document = null;
            try {
                document = Jsoup.connect("https://www.globalpetrolprices.com/Philippines/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element tableElement = document.select("tbody").get(1);

            Appliance.electricityCost = Float.valueOf(tableElement.select("tr").get(0).select("td").get(1).text());
            saveCostToSharedPrefs(Appliance.electricityCost);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            Log.d("abc", "onPostExecute: " + Appliance.electricityCost);
            setupDataAndList();	
        }
    }

    private void initSearchWidgets(){
        SearchView searchView = (SearchView) findViewById(R.id.svApplianceList);
        //searchView.setColo;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Appliance> filteredAppliances = new ArrayList<Appliance>();

                for (Appliance appliance : applianceList){
                    if (appliance.getApplianceName().toLowerCase().contains(s.toLowerCase())){
                        filteredAppliances.add(appliance);
                    }
                }

                applianceAdapter applianceAdapter = new applianceAdapter(getApplicationContext(), 0, filteredAppliances);
                lvAppliance.setAdapter(applianceAdapter);
                return false;
            }
        });
    }

    private void setupList() {
        lvAppliance = (ListView) findViewById(R.id.lvAppliance);
        applianceAdapter applianceAdapter = new applianceAdapter(getApplicationContext(), 0, applianceList);
        lvAppliance.setAdapter(applianceAdapter);
    }

    private void setupData() {
        applianceList = applianceDatabaseHelper.getAllAppliances();
    }

    private void setupDataAndList(){	//eto nagbago
        applianceList = applianceDatabaseHelper.getAllAppliances();
        lvAppliance = (ListView) findViewById(R.id.lvAppliance);
        applianceAdapter applianceAdapter = new applianceAdapter(getApplicationContext(), 0, applianceList);
        lvAppliance.setAdapter(applianceAdapter);
    }
}